package by.epam.xml.main;

import by.epam.xml.entity.jaxb.*;
import by.epam.xml.exception.DepositXmlException;
import by.epam.xml.parser.AbstractDepositBuilder;
import by.epam.xml.parser.DepositBuilderFactory;
import by.epam.xml.parser.DepositsDOMBuilder;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.SchemaOutputResolver;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

public class Main {
    static Logger logger = LogManager.getLogger();

    public static void main(String[] args) throws JAXBException, SAXException, DepositXmlException {
        MetalDeposit deposit = new MetalDeposit("Альфа-банк",
                "Belarus",
                "Фещенко Владимир Дмитриевич",
                "1234Vewr3131",
                333.5,
                1.3,true , Metal.GOLD);
        TermDeposit termDeposit = new TermDeposit("Альфа-банк",
                "Belarus",
                "Фещенко Владимир Дмитриевич",
                "1234Vewr3131",
                333.5,
                1.3,true , LocalDate.of(2021,12,23));
        DepositList list = new DepositList();
        list.add(deposit);
        list.add(termDeposit);
        list.add(deposit);
        list.add(termDeposit);
        /*
        JAXBContext context = JAXBContext.newInstance(DepositList.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        marshaller.marshal(list, new File("temp2.xml"));
        createSchema(DepositList.class, "temp2.xsd");
         */
        AbstractDepositBuilder builder = DepositBuilderFactory.createDepositBuilder("DOM");
        builder.buildSetDeposits("src/main/resources/input/deposits.xml");
        logger.log(Level.INFO, builder.getDeposits());
    }


    private static void createSchema(Class type,String filePath){
        try {

            JAXBContext.newInstance(type).generateSchema(new SchemaOutputResolver() {
                @Override
                public Result createOutput(String s, String s1) throws IOException {
                    return new StreamResult(new File(filePath));
                }
            });
        }catch (JAXBException e){
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }

    }
}
