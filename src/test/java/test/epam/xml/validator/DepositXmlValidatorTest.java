package test.epam.xml.validator;

import by.epam.xml.validator.DepositXmlValidator;
import by.epam.xml.validator.impl.DepositXmlValidatorImpl;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class DepositXmlValidatorTest {
    private final static String SCHEMA = "input/deposits.xsd";
    private DepositXmlValidator validator = DepositXmlValidatorImpl.getInstance();

    @Test(description = "Validation xml by xsd",
            dataProvider = "dataXmlFileValidation")
    public void testXmlFileValidation(String filepath, boolean expected){
        boolean actual = validator.isValidXmlFile(filepath, SCHEMA);
        Assert.assertEquals(actual, expected);
    }

    @DataProvider(name = "dataXmlFileValidation")
    public Object[][] dataXmlFileValidation(){
        return new Object[][]{
                {"input/deposits.xml", true},
                {"input/depositsInvalid.txt", false},
                {"input/empty.xml", false},
                {"input/notExist.xml", false},
                {"input/\\escapeChar.xml", false}
        };
    }
}
