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

/*
public enum DepositXmlTag {//todo: override toString?????, toLowerCase + replace(_,-)
    BANKS("banks"),
    METAL_DEPOSIT("metal-deposit"),
    TERM_DEPOSIT("term-deposit"),
    COUNTRY("country"),
    DEPOSITOR("depositor"),
    CURRENCY("currency"),
    AMOUNT_ON_DEPOSIT("amount-on-deposit"),
    PROFITABILITY("profitability"),
    CAPITALIZATION("capitalization"),
    METAL("metal"),
    MASS("mass"),
    TIME_CONSTRAINTS("time-constraints"),
    BANK_NAME("bank-name"),
    ACCOUNT_ID("account-id");

    private String tag;

    DepositXmlTag(String tag){
        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }
}
 */