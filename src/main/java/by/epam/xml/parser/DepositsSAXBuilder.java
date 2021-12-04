package by.epam.xml.parser;

import by.epam.xml.exception.DepositXmlException;
import by.epam.xml.handler.DepositErrorHandler;
import by.epam.xml.handler.DepositHandler;
import by.epam.xml.validator.DepositXmlValidator;
import by.epam.xml.validator.impl.DepositXmlValidatorImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;

public class DepositsSAXBuilder extends AbstractDepositBuilder{
    static Logger logger = LogManager.getLogger();
    private DepositHandler handler;
    private XMLReader reader;

    public DepositsSAXBuilder() throws DepositXmlException{
        SAXParserFactory factory = SAXParserFactory.newInstance();
        handler = new DepositHandler();
        try {
            SAXParser parser = factory.newSAXParser();
            reader = parser.getXMLReader();
        } catch (ParserConfigurationException e) {
            logger.log(Level.ERROR, "SAX parser cannot be created which satisfies the requested configuration", e);
            throw new DepositXmlException("SAX parser can't be created", e);
        } catch (SAXException e) {
            logger.log(Level.ERROR, "Any SAX errors occur during processing", e);
            throw new DepositXmlException("Any SAX errors occur during processing", e);
        }
        reader.setErrorHandler(new DepositErrorHandler());
        reader.setContentHandler(handler);
    }

    @Override
    public void buildSetDeposits(String filename)throws DepositXmlException {
        DepositXmlValidator validator = DepositXmlValidatorImpl.getInstance();
        if (!validator.isValidFilepath(filename)){
            throw new DepositXmlException("File path is invalid: " + filename);
        }
        try {
            logger.log(Level.INFO, "SAX started parsing {}", filename);
            reader.parse(filename);
        } catch (IOException | SAXException e) {
            logger.log(Level.ERROR, "Any SAX or IO exception from the parser {}", filename, e);
            throw new DepositXmlException("Any SAX or IO exception from the parser "+ filename, e);
        }
        deposits = handler.getDeposits();
        logger.log(Level.INFO, "SAX parsing {} has finished successfully", filename);

    }
}
