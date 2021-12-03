package by.epam.xml.entity;

public class AbstractDeposit {
    private String accountId;
    private String bankName;
    private Country country;
    private String depositor;

    public AbstractDeposit() {}

    public AbstractDeposit(String accountId, String bankName, Country country, String depositor) {
        this.accountId = accountId;
        this.bankName = bankName;
        this.country = country;
        this.depositor = depositor;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AbstractDeposit deposit = (AbstractDeposit) o;

        if (bankName != null ? !bankName.equals(deposit.bankName) : deposit.bankName != null) return false;
        if (country != deposit.country) return false;
        if (depositor != null ? !depositor.equals(deposit.depositor) : deposit.depositor != null) return false;
        return accountId != null ? accountId.equals(deposit.accountId) : deposit.accountId == null;
    }

    @Override
    public int hashCode() {
        int result = bankName != null ? bankName.hashCode() : 0;
        result = 31 * result + (country != null ? country.hashCode() : 0);
        result = 31 * result + (depositor != null ? depositor.hashCode() : 0);
        result = 31 * result + (accountId != null ? accountId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("bankName='").append(bankName).append('\'');
        sb.append(", country='").append(country).append('\'');
        sb.append(", depositor='").append(depositor).append('\'');
        sb.append(", accountId='").append(accountId).append('\'');
        return sb.toString();
    }
}
