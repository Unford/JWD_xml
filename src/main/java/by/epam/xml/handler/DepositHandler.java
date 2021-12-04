package by.epam.xml.handler;

import by.epam.xml.entity.*;
import by.epam.xml.tag.DepositXmlTag;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.time.LocalDate;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

import static by.epam.xml.tag.DepositXmlTag.*;

public class DepositHandler extends DefaultHandler {
    private Set<AbstractDeposit> deposits;
    private AbstractDeposit currentDeposit;
    private DepositXmlTag currentXmlTag;
    private EnumSet<DepositXmlTag> withText;

    public DepositHandler() {
        deposits = new HashSet<>();
        withText = EnumSet.range(COUNTRY, TIME_CONSTRAINTS);
    }

    public Set<AbstractDeposit> getDeposits(){
        return deposits;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        if (METAL_DEPOSIT.toString().equals(qName) || TERM_DEPOSIT.toString().equals(qName)){
            currentXmlTag = DepositXmlTag.parseDepositXmlTag(qName);
            switch (currentXmlTag){
                case TERM_DEPOSIT -> currentDeposit = new TermDeposit();
                case METAL_DEPOSIT -> currentDeposit = new MetalDeposit();
            }
            currentXmlTag = null;

            String accountId = attributes.getValue(ACCOUNT_ID.toString());
            currentDeposit.setAccountId(accountId);

            String bankName = attributes.getValue(BANK_NAME.toString());
            currentDeposit.setBankName(bankName);
        }else {
            DepositXmlTag temp = DepositXmlTag.parseDepositXmlTag(qName);
            if (withText.contains(temp)){
                currentXmlTag = temp;
            }
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        String data = new String(ch, start, length).trim();
        if (currentXmlTag != null){
            switch (currentXmlTag){
                case COUNTRY -> currentDeposit.setCountry(Country.parseCountry(data));
                case DEPOSITOR -> currentDeposit.setDepositor(data);
                case MASS -> ((MetalDeposit) currentDeposit).setMass(Double.parseDouble(data));
                case METAL -> ((MetalDeposit) currentDeposit).setMetal(Metal.parseMetal(data));
                case CURRENCY -> ((TermDeposit) currentDeposit).setCurrency(Currency.parseCurrency(data));
                case AMOUNT_ON_DEPOSIT -> ((TermDeposit) currentDeposit).setAmountOnDeposit(Double.parseDouble(data));
                case PROFITABILITY -> ((TermDeposit) currentDeposit).setProfitability(Double.parseDouble(data));
                case CAPITALIZATION -> ((TermDeposit) currentDeposit).setCapitalization(Boolean.parseBoolean(data));
                case TIME_CONSTRAINTS -> ((TermDeposit) currentDeposit).setTimeConstraints(LocalDate.parse(data));
            }
        }
        currentXmlTag = null;
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        if (METAL_DEPOSIT.toString().equals(qName) || TERM_DEPOSIT.toString().equals(qName)){
            deposits.add(currentDeposit);
        }
    }
}
