package by.epam.xml.validator;

public interface DepositXmlValidator {
    boolean isValidFilepath(String filepath);

    boolean isValidXmlFile(String filepath, String schemaFilepath);
}
