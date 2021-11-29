package by.epam.xml.tag;

public enum DepositXmlTag {
    BANKS("banks"),
    METAL_DEPOSIT("metal-deposit"),
    TERM_DEPOSIT("term-deposit"),
    COUNTRY("country"),
    DEPOSITOR("depositor"),
    AMOUNT_ON_DEPOSIT("amount-on-deposit"),
    PROFITABILITY("profitability"),
    CAPITALIZATION("capitalization"),
    METAL("metal"),
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
