package by.epam.xml.validator.impl;

import by.epam.xml.handler.DepositErrorHandler;
import by.epam.xml.validator.DepositXmlValidator;
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

public class DepositXmlValidatorImpl implements DepositXmlValidator {
    static Logger logger = LogManager.getLogger();

    private static DepositXmlValidatorImpl instance;

    private DepositXmlValidatorImpl(){}

    public static DepositXmlValidatorImpl getInstance(){
        if (instance == null){
            instance = new DepositXmlValidatorImpl();
        }
        return instance;
    }

    @Override
    public boolean isValidFilepath(String filepath){
        boolean isValid = false;
        if (filepath != null && !filepath.isBlank()){
            File file = new File(filepath);
            isValid = file.exists();
        }
        return isValid;
    }

    @Override
    public boolean isValidXmlFile(String filepath, String schemaFilepath){
        boolean isValid = false;
        if (isValidFilepath(filepath) && isValidFilepath(schemaFilepath)){
            String language = XMLConstants.W3C_XML_SCHEMA_NS_URI;
            SchemaFactory factory = SchemaFactory.newInstance(language);
            File schemaLocation = new File(schemaFilepath);
            try {
                Schema schema = factory.newSchema(schemaLocation);
                Validator validator = schema.newValidator();
                Source source = new StreamSource(filepath);
                validator.setErrorHandler(new DepositErrorHandler());
                validator.validate(source);
                isValid = true;
                logger.log(Level.INFO, "File - {} is valid, xsd file - {}", filepath, schemaFilepath);
            } catch (SAXException | IOException e) {
                logger.log(Level.ERROR, "Xml - {} or xsd file - {} invalid", filepath, schemaFilepath);
            }
        }
        return isValid;
    }
}
