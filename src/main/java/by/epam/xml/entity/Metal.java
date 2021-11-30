package by.epam.xml.entity;

import by.epam.xml.tag.DepositXmlTag;

public enum Metal {
    GOLD, SILVER, PLATINUM, PALLADIUM;

    public static Metal parseMetal(String text){
        return Metal.valueOf(text.toUpperCase());
    }
}
