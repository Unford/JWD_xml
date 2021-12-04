package by.epam.xml.parser;

import by.epam.xml.entity.*;
import by.epam.xml.exception.DepositXmlException;
import by.epam.xml.tag.DepositXmlTag;
import by.epam.xml.validator.DepositXmlValidator;
import by.epam.xml.validator.impl.DepositXmlValidatorImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.time.LocalDate;

import static by.epam.xml.tag.DepositXmlTag.*;

public class DepositsDOMBuilder extends AbstractDepositsBuilder {
    static Logger logger = LogManager.getLogger();
    private DocumentBuilder documentBuilder;

    public DepositsDOMBuilder() throws DepositXmlException {
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        try {
            documentBuilder = builderFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            logger.log(Level.ERROR, "DocumentBuilder can't be created", e);
            throw new DepositXmlException("DocumentBuilder can't be created", e);
        }
    }

    @Override
    public void buildSetDeposits(String filename)throws DepositXmlException{
        DepositXmlValidator validator = DepositXmlValidatorImpl.getInstance();
        if (!validator.isValidFilepath(filename)){
            throw new DepositXmlException("File path is invalid: " + filename);
        }
        try {
            logger.log(Level.INFO, "DOM started parsing {}", filename);
            Document document = documentBuilder.parse(filename);
            Element root = document.getDocumentElement();

            NodeList metalDepositsList = root.getElementsByTagName(METAL_DEPOSIT.toString());
            addAllDeposits(metalDepositsList);

            NodeList termDepositsList = root.getElementsByTagName(TERM_DEPOSIT.toString());
            addAllDeposits(termDepositsList);

        } catch (IOException | SAXException e) {
            logger.log(Level.ERROR, "IO or SAX error while reading file {}", filename, e);
            throw new DepositXmlException("IO or SAX error while reading file " + filename, e);
        }
        logger.log(Level.INFO, "DOM parsing {} has finished successfully", filename);
    }

    private void addAllDeposits(NodeList depositNodeList) throws DepositXmlException {
        for (int i = 0; i < depositNodeList.getLength(); i++) {
            Element depositElement = (Element) depositNodeList.item(i);
            AbstractDeposit deposit = buildDeposit(depositElement);
            deposits.add(deposit);
        }
    }

    private AbstractDeposit buildDeposit(Element depositElement) throws DepositXmlException {
        AbstractDeposit deposit;

        DepositXmlTag depositName = DepositXmlTag.parseDepositXmlTag(depositElement.getTagName());

        switch (depositName){
            case METAL_DEPOSIT:
                deposit = createMetalDeposit(depositElement);
                break;
            case TERM_DEPOSIT:
                deposit = createTermDeposit(depositElement);
                break;
            default:
                throw new DepositXmlException("Can't find this tag name");
        }
        String accountId = depositElement.getAttribute(ACCOUNT_ID.toString());
        deposit.setAccountId(accountId);

        String bankName = depositElement.getAttribute(BANK_NAME.toString());
        deposit.setBankName(bankName);

        String depositor = getElementTextContent(depositElement, DEPOSITOR);
        deposit.setDepositor(depositor);

        Country country = Country.parseCountry(getElementTextContent(depositElement, COUNTRY));
        deposit.setCountry(country);

        return deposit;
    }

    private AbstractDeposit createMetalDeposit(Element depositElement){
        MetalDeposit metalDeposit = new MetalDeposit();

        Metal metal = Metal.parseMetal(getElementTextContent(depositElement, METAL));
        metalDeposit.setMetal(metal);

        double mass = Double.parseDouble(getElementTextContent(depositElement, MASS));
        metalDeposit.setMass(mass);

        return metalDeposit;
    }

    private AbstractDeposit createTermDeposit(Element depositElement){
        TermDeposit termDeposit = new TermDeposit();

        Currency currency = Currency.parseCurrency(getElementTextContent(depositElement, CURRENCY));
        termDeposit.setCurrency(currency);

        double amountOnDeposit = Double.parseDouble(getElementTextContent(depositElement, AMOUNT_ON_DEPOSIT));
        termDeposit.setAmountOnDeposit(amountOnDeposit);

        double profitability = Double.parseDouble(getElementTextContent(depositElement, PROFITABILITY));
        termDeposit.setProfitability(profitability);

        boolean capitalization = Boolean.parseBoolean(getElementTextContent(depositElement, CAPITALIZATION));
        termDeposit.setCapitalization(capitalization);

        LocalDate date = LocalDate.parse(getElementTextContent(depositElement, TIME_CONSTRAINTS));
        termDeposit.setTimeConstraints(date);

        return termDeposit;
    }

    private String getElementTextContent(Element element, String elementName){
        NodeList nodeList = element.getElementsByTagName(elementName);
        Node node = nodeList.item(0);
        String text = node.getTextContent();
        return text;
    }

    private String getElementTextContent(Element element, DepositXmlTag elementName){
        return getElementTextContent(element, elementName.toString());
    }
}
