package be.cocoding.jpa.exercise.entity.vehicle;

import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.Objects;

@Entity
//@DiscriminatorValue("CA")   // Ex 2: Single Table
public class Car extends Vehicle {

    private int seatNbr;

    public int getSeatNbr() {
        return seatNbr;
    }

    public void setSeatNbr(int seatNbr) {
        this.seatNbr = seatNbr;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Car car = (Car) o;
        return seatNbr == car.seatNbr;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), seatNbr);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("seatNbr", seatNbr)
                .toString();
    }
}
