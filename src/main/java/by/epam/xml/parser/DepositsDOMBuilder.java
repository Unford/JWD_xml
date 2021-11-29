package by.epam.xml.parser;

import by.epam.xml.entity.AbstractDeposit;
import by.epam.xml.entity.Metal;
import by.epam.xml.entity.MetalDeposit;
import by.epam.xml.entity.TermDeposit;
import by.epam.xml.exception.DepositXmlException;
import by.epam.xml.tag.DepositXmlTag;
import by.epam.xml.validator.DepositXmlValidator;
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

public class DepositsDOMBuilder extends AbstractDepositBuilder{
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
        DepositXmlValidator validator = new DepositXmlValidator();
        if (!validator.isValidFilepath(filename)){
            throw new DepositXmlException("File path is invalid: " + filename);
        }
        try {
            Document document = documentBuilder.parse(filename);
            Element root = document.getDocumentElement();

            NodeList metalDepositsList = root.getElementsByTagName(METAL_DEPOSIT.getTag());
            addAllDeposits(metalDepositsList);

            NodeList termDepositsList = root.getElementsByTagName(TERM_DEPOSIT.getTag());
            addAllDeposits(termDepositsList);

        } catch (IOException | SAXException e) {
            logger.log(Level.ERROR, "IO or SAX error while reading file", e);
            throw new DepositXmlException("IO or SAX error while reading file", e);
        }
        logger.log(Level.INFO, "DOM parsing has finished successfully");
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

        DepositXmlTag tagName = DepositXmlTag.valueOf(depositElement.getTagName().replace("-","_").toUpperCase());//todo to constants

        switch (tagName){
            case METAL_DEPOSIT:
                deposit = new MetalDeposit();
                Metal tag = Metal.valueOf(
                        getElementTextContent(depositElement, METAL.getTag())
                                .toUpperCase());
                ((MetalDeposit) deposit).setMetal(tag);
                break;
            case TERM_DEPOSIT:
                deposit = new TermDeposit();
                LocalDate date = LocalDate.parse(
                        getElementTextContent(depositElement, TIME_CONSTRAINTS.getTag()));
                ((TermDeposit) deposit).setTimeConstraints(date);
                break;
            default:
                throw new DepositXmlException("Can't find this tag name");//todo unreachable
        }

        deposit.setAccountId(depositElement.getAttribute(ACCOUNT_ID.getTag()));
        String tmpBankName = depositElement.getAttribute(BANK_NAME.getTag());

        if (!tmpBankName.isEmpty()){//todo delete??
            deposit.setBankName(tmpBankName);
        }

        deposit.setDepositor(getElementTextContent(depositElement, DEPOSITOR.getTag()));
        deposit.setCountry(getElementTextContent(depositElement, COUNTRY.getTag()));

        deposit.setAmountOnDeposit(
                Double.parseDouble(
                        getElementTextContent(depositElement, AMOUNT_ON_DEPOSIT.getTag())));
        deposit.setProfitability(
                Double.parseDouble(
                        getElementTextContent(depositElement, PROFITABILITY.getTag())));
        deposit.setCapitalization(
                Boolean.parseBoolean(
                        getElementTextContent(depositElement, CAPITALIZATION.getTag())));

        return deposit;
    }

    private static String getElementTextContent(Element element, String elementName){
        NodeList nodeList = element.getElementsByTagName(elementName);
        Node node = nodeList.item(0);
        String text = node.getTextContent();
        return text;
    }


}
