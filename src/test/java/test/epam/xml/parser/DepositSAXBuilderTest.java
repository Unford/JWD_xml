package test.epam.xml.parser;

import by.epam.xml.entity.*;
import by.epam.xml.exception.DepositXmlException;
import by.epam.xml.parser.AbstractDepositBuilder;
import by.epam.xml.parser.DepositBuilderFactory;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class DepositSAXBuilderTest {
    private final static String SCHEMA = "input/deposits.xsd";
    private AbstractDepositBuilder depositBuilder;


    public DepositSAXBuilderTest() throws DepositXmlException {
        depositBuilder = DepositBuilderFactory.createDepositBuilder("Sax");
    }

    @Test(description = "Parsing valid xml by SAX",
            dataProvider = "dataSAXBuilder")
    public void testSAXBuilder(String filepath, Set<AbstractDeposit> expected) throws DepositXmlException {
        depositBuilder.buildSetDeposits(filepath, SCHEMA);
        Set<AbstractDeposit> actual = depositBuilder.getDeposits();
        assertThat(actual).containsAll(expected);
    }

    @DataProvider(name = "dataSAXBuilder")
    public Object[][] dataSAXBuilder(){
        return new Object[][]{
                {"input/shortDeposits.xml",
                        Set.of(
                                new MetalDeposit("BY23GDSE313123461543",
                                        "Техно-банк", Country.BELARUS,
                                        "Фещенко Владимир Дмитриевич",
                                        Metal.GOLD, 1.4 ),

                                new TermDeposit("BY13MSJO134137053543",
                                        "Альфа-банк", Country.RUSSIA,
                                        "Маковский Петр Николаевич",
                                        Currency.RUB, 1132.5, 0.4,
                                        false, LocalDate.of(2022,12,1)),

                                new MetalDeposit("US63BGSJ064137153443",
                                        null, Country.USA,
                                        "Гумилев Николай Степанович",
                                        Metal.PALLADIUM, 14.2 ))},
        };
    }

    @Test(description = "Parsing invalid xml by SAX",
            dataProvider = "dataInvalidSAXBuilder",
            expectedExceptions = DepositXmlException.class)
    public void testInvalidSAXBuilder(String filepath)throws DepositXmlException{
        depositBuilder.buildSetDeposits(filepath, SCHEMA);
    }

    @DataProvider(name = "dataInvalidSAXBuilder")
    public Object[][] dataInvalidSAXBuilder(){
        return new Object[][]{
                {"input/empty.xml"},
                {"input/depositsInvalid.xml"},
        };
    }
}
