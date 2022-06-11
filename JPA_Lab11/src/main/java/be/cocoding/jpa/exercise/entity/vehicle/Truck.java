package be.cocoding.jpa.exercise.entity.vehicle;

import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.MappedSuperclass;
import java.util.Objects;

@MappedSuperclass
public class Truck extends Vehicle {

    private int horsePower;

    public int getHorsePower() {
        return horsePower;
    }

    public void setHorsePower(int horsePower) {
        this.horsePower = horsePower;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Truck truck = (Truck) o;
        return horsePower == truck.horsePower;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), horsePower);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("horsePower", horsePower)
                .toString();
    }
}
