package by.epam.xml.entity;

public class MetalDeposit extends AbstractDeposit{
    private Metal metal;

    public MetalDeposit() {}

    public MetalDeposit(String bankName, String country, String depositor, String accountId,
                        double amountOnDeposit, double profitability, boolean capitalization, Metal metal) {
        super(bankName, country, depositor, accountId, amountOnDeposit, profitability, capitalization);
        this.metal = metal;
    }

    public Metal getMetal() {
        return metal;
    }

    public void setMetal(Metal metal) {
        this.metal = metal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MetalDeposit)) return false;
        if (!super.equals(o)) return false;

        MetalDeposit that = (MetalDeposit) o;

        return metal == that.metal;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (metal != null ? metal.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Metal deposit:{");
        sb.append(super.toString());
        sb.append(", metal=").append(metal).append('}');
        return sb.toString();
    }
}
