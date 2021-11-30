package by.epam.xml.parser;

import by.epam.xml.entity.*;
import by.epam.xml.exception.DepositXmlException;
import by.epam.xml.tag.DepositXmlTag;
import by.epam.xml.validator.DepositXmlValidator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.time.LocalDate;

import static by.epam.xml.tag.DepositXmlTag.*;

public class DepositsStAXEventBuilder extends AbstractDepositBuilder{
    static Logger logger = LogManager.getLogger();
    private XMLInputFactory inputFactory;

    public DepositsStAXEventBuilder() {
        inputFactory = XMLInputFactory.newInstance();
    }

    @Override
    public void buildSetDeposits(String filename)throws DepositXmlException {
        DepositXmlValidator validator = new DepositXmlValidator();
        if (!validator.isValidFilepath(filename)){
            throw new DepositXmlException("File path is invalid: " + filename);
        }
        AbstractDeposit deposit = null;
        try {
            logger.log(Level.INFO, "StAX started parsing {}", filename);
            XMLEventReader reader = inputFactory.createXMLEventReader(new FileInputStream(filename));

            while (reader.hasNext()){
                XMLEvent event = reader.nextEvent();
                if (event.isStartElement()){
                    StartElement startElement = event.asStartElement();
                    String textElement = startElement.getName().getLocalPart();
                    DepositXmlTag currentXmlTag = DepositXmlTag.parseDepositXmlTag(textElement);

                    switch (currentXmlTag){
                        case METAL_DEPOSIT:
                            deposit = new MetalDeposit();
                            Attribute bankName = startElement.getAttributeByName(QName.valueOf(BANK_NAME.toString()));
                            if (bankName != null){
                                deposit.setBankName(bankName.getValue());
                            }
                            Attribute accountId = startElement.getAttributeByName(QName.valueOf(ACCOUNT_ID.toString()));
                            deposit.setAccountId(accountId.getValue());
                            break;

                        case TERM_DEPOSIT:
                            deposit = new TermDeposit();
                            bankName = startElement.getAttributeByName(QName.valueOf(BANK_NAME.toString()));
                            if (bankName != null){
                                deposit.setBankName(bankName.getValue());
                            }
                            accountId = startElement.getAttributeByName(QName.valueOf(ACCOUNT_ID.toString()));
                            deposit.setAccountId(accountId.getValue());
                            break;

                        case COUNTRY:
                            event = reader.nextEvent();
                            deposit.setCountry(Country.parseCountry(event.asCharacters().getData()));
                            break;

                        case DEPOSITOR:
                            event = reader.nextEvent();
                            deposit.setDepositor(event.asCharacters().getData());
                            break;

                        case MASS:
                            event = reader.nextEvent();
                            ((MetalDeposit) deposit).setMass(Double.parseDouble(event.asCharacters().getData()));
                            break;

                        case METAL:
                            event = reader.nextEvent();
                            ((MetalDeposit) deposit).setMetal(Metal.parseMetal(event.asCharacters().getData()));
                            break;

                        case CURRENCY:
                            event = reader.nextEvent();
                            ((TermDeposit) deposit).setCurrency(Currency.parseCurrency(event.asCharacters().getData()));
                            break;

                        case AMOUNT_ON_DEPOSIT:
                            event = reader.nextEvent();
                            ((TermDeposit) deposit).setAmountOnDeposit(Double.parseDouble(event.asCharacters().getData()));
                            break;

                        case PROFITABILITY:
                            event = reader.nextEvent();
                            ((TermDeposit) deposit).setProfitability(Double.parseDouble(event.asCharacters().getData()));
                            break;

                        case CAPITALIZATION:
                            event = reader.nextEvent();
                            ((TermDeposit) deposit).setCapitalization(Boolean.parseBoolean(event.asCharacters().getData()));
                            break;

                        case TIME_CONSTRAINTS:
                            event = reader.nextEvent();
                            ((TermDeposit) deposit).setTimeConstraints(LocalDate.parse(event.asCharacters().getData()));
                            break;
                    }
                }
                if (event.isEndElement()){
                    EndElement endElement = event.asEndElement();
                    String textElement = endElement.getName().getLocalPart();
                    if (textElement.equals(TERM_DEPOSIT.toString()) || textElement.equals(METAL_DEPOSIT.toString())){
                        deposits.add(deposit);
                    }
                }
            }
        } catch (XMLStreamException | FileNotFoundException e) {
            logger.log(Level.ERROR, "There is an error with the underlying XML or File {} not found ", filename, e);

        }
        logger.log(Level.INFO, "StAX parsing {} has finished successfully", filename);

    }
}
