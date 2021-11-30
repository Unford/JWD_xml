package by.epam.xml.entity;

public enum Country {
    RUSSIA, BELARUS, CANADA, USA, UK, UKRAINE;

    public static Country parseCountry(String text){
        return Country.valueOf(text.toUpperCase());
    }
}
