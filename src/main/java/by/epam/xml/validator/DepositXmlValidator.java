package by.epam.xml.validator;

import by.epam.xml.handler.DepositErrorHandler;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;

public class DepositXmlValidator {
    static Logger logger = LogManager.getLogger();

    public boolean isValidFilepath(String filepath){
        if (filepath == null || filepath.isBlank()){
            return false;
        }
        File file = new File(filepath);
        return file.exists();
    }

    public boolean isValidXmlFile(String filepath, String schemaFilepath){
        boolean result = false;

        if (isValidFilepath(filepath) && isValidFilepath(schemaFilepath)){//todo if path invalid log error
            String language = XMLConstants.W3C_XML_SCHEMA_NS_URI;
            SchemaFactory factory = SchemaFactory.newInstance(language);
            File schemaLocation = new File(schemaFilepath);
            try {
                Schema schema = factory.newSchema(schemaLocation);
                Validator validator = schema.newValidator();
                Source source = new StreamSource(filepath);
                validator.setErrorHandler(new DepositErrorHandler());
                validator.validate(source);
                result = true;
                logger.log(Level.INFO, "File - {} is valid, xsd file - {}", filepath, schemaFilepath);
            } catch (SAXException | IOException e) {
                logger.log(Level.ERROR, "Xml - {} or xsd file - {} invalid", filepath, schemaFilepath);
            }
        }
        return result;
    }
}
