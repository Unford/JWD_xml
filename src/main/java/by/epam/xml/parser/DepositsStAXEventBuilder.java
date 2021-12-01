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
import java.io.IOException;
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
        FileInputStream inputStream = null;
        try {
            logger.log(Level.INFO, "StAX started parsing {}", filename);
            inputStream = new FileInputStream(filename);
            XMLEventReader reader = inputFactory.createXMLEventReader(inputStream);
            while (reader.hasNext()){
                XMLEvent event = reader.nextEvent();
                if (event.isStartElement()){
                    StartElement startElement = event.asStartElement();
                    String textElement = startElement.getName().getLocalPart();
                    DepositXmlTag currentXmlTag = DepositXmlTag.parseDepositXmlTag(textElement);

                    switch (currentXmlTag){
                        case METAL_DEPOSIT:
                            deposit = new MetalDeposit();
                            buildDepositAttributes(startElement, deposit);
                            break;

                        case TERM_DEPOSIT:
                            deposit = new TermDeposit();
                            buildDepositAttributes(startElement, deposit);
                            break;

                        case COUNTRY:
                            event = reader.nextEvent();
                            Country country = Country.parseCountry(event.asCharacters().getData());
                            deposit.setCountry(country);
                            break;

                        case DEPOSITOR:
                            event = reader.nextEvent();
                            deposit.setDepositor(event.asCharacters().getData());
                            break;

                        case MASS:
                            event = reader.nextEvent();
                            double mass = Double.parseDouble(event.asCharacters().getData());
                            ((MetalDeposit) deposit).setMass(mass);
                            break;

                        case METAL:
                            event = reader.nextEvent();
                            Metal metal = Metal.parseMetal(event.asCharacters().getData());
                            ((MetalDeposit) deposit).setMetal(metal);
                            break;

                        case CURRENCY:
                            event = reader.nextEvent();
                            Currency currency = Currency.parseCurrency(event.asCharacters().getData());
                            ((TermDeposit) deposit).setCurrency(currency);
                            break;

                        case AMOUNT_ON_DEPOSIT:
                            event = reader.nextEvent();
                            double amountOnDeposit = Double.parseDouble(event.asCharacters().getData());
                            ((TermDeposit) deposit).setAmountOnDeposit(amountOnDeposit);
                            break;

                        case PROFITABILITY:
                            event = reader.nextEvent();
                            double profitability = Double.parseDouble(event.asCharacters().getData());
                            ((TermDeposit) deposit).setProfitability(profitability);
                            break;

                        case CAPITALIZATION:
                            event = reader.nextEvent();
                            boolean capitalization = Boolean.parseBoolean(event.asCharacters().getData());
                            ((TermDeposit) deposit).setCapitalization(capitalization);
                            break;

                        case TIME_CONSTRAINTS:
                            event = reader.nextEvent();
                            LocalDate date = LocalDate.parse(event.asCharacters().getData());
                            ((TermDeposit) deposit).setTimeConstraints(date);
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
        }finally {
            try {
                if (inputStream != null){
                    inputStream.close();
                }
            } catch (IOException e) {
                logger.log(Level.ERROR, "Impossible to close file", e);
            }

        }
        logger.log(Level.INFO, "StAX parsing {} has finished successfully", filename);
    }

    private void buildDepositAttributes(StartElement startElement, AbstractDeposit deposit){
        Attribute bankName = startElement.getAttributeByName(QName.valueOf(BANK_NAME.toString()));
        if (bankName != null){
            deposit.setBankName(bankName.getValue());
        }
        Attribute accountId = startElement.getAttributeByName(QName.valueOf(ACCOUNT_ID.toString()));
        deposit.setAccountId(accountId.getValue());
    }
}
