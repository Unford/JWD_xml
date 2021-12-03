package by.epam.xml.entity;

import java.time.LocalDate;

public class TermDeposit extends CashDeposit{
    private LocalDate timeConstraints;

    public TermDeposit() {}

    public TermDeposit(String bankName, Country country, String depositor, String accountId,
                       Currency currency, double amountOnDeposit, double profitability,
                       boolean capitalization, LocalDate timeConstraints) {
        super(bankName, country, depositor, accountId, currency, amountOnDeposit, profitability, capitalization);
        this.timeConstraints = timeConstraints;
    }

    public LocalDate getTimeConstraints() {
        return timeConstraints;
    }

    public void setTimeConstraints(LocalDate timeConstraints) {
        this.timeConstraints = timeConstraints;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        TermDeposit that = (TermDeposit) o;

        return timeConstraints != null ? timeConstraints.equals(that.timeConstraints) : that.timeConstraints == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (timeConstraints != null ? timeConstraints.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Term deposit:{");
        sb.append(super.toString());
        sb.append(", timeConstraints=").append(timeConstraints).append("}\n");
        return sb.toString();
    }
}
