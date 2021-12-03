package by.epam.xml.entity;

public abstract class CashDeposit extends AbstractDeposit{
    private Currency currency;
    private double amountOnDeposit;
    private double profitability;
    private boolean capitalization;

    public CashDeposit() { }

    public CashDeposit(String accountId, String bankName, Country country, String depositor,
                       Currency currency, double amountOnDeposit, double profitability,
                       boolean capitalization) {
        super(accountId, bankName, country, depositor);
        this.currency = currency;
        this.amountOnDeposit = amountOnDeposit;
        this.profitability = profitability;
        this.capitalization = capitalization;
    }

    public double getAmountOnDeposit() {
        return amountOnDeposit;
    }

    public void setAmountOnDeposit(double amountOnDeposit) {
        this.amountOnDeposit = amountOnDeposit;
    }

    public double getProfitability() {
        return profitability;
    }

    public void setProfitability(double profitability) {
        this.profitability = profitability;
    }

    public boolean isCapitalization() {
        return capitalization;
    }

    public void setCapitalization(boolean capitalization) {
        this.capitalization = capitalization;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        CashDeposit that = (CashDeposit) o;

        if (Double.compare(that.amountOnDeposit, amountOnDeposit) != 0) return false;
        if (Double.compare(that.profitability, profitability) != 0) return false;
        if (capitalization != that.capitalization) return false;
        return currency == that.currency;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        long temp;
        result = 31 * result + (currency != null ? currency.hashCode() : 0);
        temp = Double.doubleToLongBits(amountOnDeposit);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(profitability);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (capitalization ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(super.toString());
        sb.append(", currency='").append(currency).append('\'');
        sb.append(", amountOnDeposit=").append(amountOnDeposit);
        sb.append(", profitability=").append(profitability);
        sb.append(", capitalization=").append(capitalization);
        return sb.toString();
    }
}
