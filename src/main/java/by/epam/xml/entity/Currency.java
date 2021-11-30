package by.epam.xml.entity;

import by.epam.xml.tag.DepositXmlTag;

public enum Currency {
    USD, EUR, GBP, BYN, RUB, CAD, UAH;

    public static Currency parseCurrency(String text){
        return Currency.valueOf(text.toUpperCase());
    }
}
