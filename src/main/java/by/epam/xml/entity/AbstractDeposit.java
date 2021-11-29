package by.epam.xml.entity;

public class AbstractDeposit {
    private String bankName;
    private String country;
    private String depositor;
    private String accountId;
    private double amountOnDeposit;
    private double profitability;
    private boolean capitalization;

    public AbstractDeposit(){}
    public AbstractDeposit(String bankName, String country, String depositor, String accountId,
                           double amountOnDeposit, double profitability, boolean capitalization) {
        this.bankName = bankName;
        this.country = country;
        this.depositor = depositor;
        this.accountId = accountId;
        this.amountOnDeposit = amountOnDeposit;
        this.profitability = profitability;
        this.capitalization = capitalization;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDepositor() {
        return depositor;
    }

    public void setDepositor(String depositor) {
        this.depositor = depositor;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AbstractDeposit that = (AbstractDeposit) o;

        if (Double.compare(that.amountOnDeposit, amountOnDeposit) != 0) return false;
        if (Double.compare(that.profitability, profitability) != 0) return false;
        if (bankName != null ? !bankName.equals(that.bankName) : that.bankName != null) return false;
        if (country != null ? !country.equals(that.country) : that.country != null) return false;
        if (depositor != null ? !depositor.equals(that.depositor) : that.depositor != null) return false;
        return accountId != null ? accountId.equals(that.accountId) : that.accountId == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = bankName != null ? bankName.hashCode() : 0;
        result = 31 * result + (country != null ? country.hashCode() : 0);
        result = 31 * result + (depositor != null ? depositor.hashCode() : 0);
        result = 31 * result + (accountId != null ? accountId.hashCode() : 0);
        temp = Double.doubleToLongBits(amountOnDeposit);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(profitability);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("bankName='").append(bankName).append('\'');
        sb.append(", country='").append(country).append('\'');
        sb.append(", depositor='").append(depositor).append('\'');
        sb.append(", accountId='").append(accountId).append('\'');
        sb.append(", amountOnDeposit=").append(amountOnDeposit);
        sb.append(", profitability=").append(profitability);
        return sb.toString();
    }
}
