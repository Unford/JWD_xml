package by.epam.xml.tag;

public enum DepositXmlTag {
    BANKS,
    METAL_DEPOSIT,
    TERM_DEPOSIT,
    COUNTRY,
    DEPOSITOR,
    CURRENCY,
    AMOUNT_ON_DEPOSIT,
    PROFITABILITY,
    CAPITALIZATION,
    METAL,
    MASS,
    TIME_CONSTRAINTS,
    BANK_NAME,
    ACCOUNT_ID;

    public static final char HYPHEN = '-';
    public static final char UNDERSCORE = '_';

    @Override
    public String toString(){
        return name().toLowerCase().replace(UNDERSCORE, HYPHEN);
    }

    public static DepositXmlTag parseDepositXmlTag(String text){
        return DepositXmlTag.valueOf(text.toUpperCase().replace(HYPHEN, UNDERSCORE));
    }
}