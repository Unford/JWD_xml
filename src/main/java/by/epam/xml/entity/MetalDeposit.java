package by.epam.xml.entity;

public class MetalDeposit extends AbstractDeposit{
    private Metal metal;
    private double mass;

    public MetalDeposit() {}

    public MetalDeposit(String bankName, Country country, String depositor,
                        String accountId, Metal metal, double mass) {
        super(bankName, country, depositor, accountId);
        this.metal = metal;
        this.mass = mass;
    }

    public Metal getMetal() {
        return metal;
    }

    public void setMetal(Metal metal) {
        this.metal = metal;
    }

    public double getMass() {
        return mass;
    }

    public void setMass(double mass) {
        this.mass = mass;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        MetalDeposit deposit = (MetalDeposit) o;

        if (Double.compare(deposit.mass, mass) != 0) return false;
        return metal == deposit.metal;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        long temp;
        result = 31 * result + (metal != null ? metal.hashCode() : 0);
        temp = Double.doubleToLongBits(mass);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Metal deposit:{");
        sb.append(super.toString());
        sb.append(", metal='").append(metal).append('\'');
        sb.append(", mass=").append(mass).append("}\n");
        return sb.toString();
    }
}
