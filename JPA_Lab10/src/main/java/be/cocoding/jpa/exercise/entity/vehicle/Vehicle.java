package be.cocoding.jpa.exercise.entity.vehicle;

import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;
import java.util.Objects;
@Entity
// ****  Ex 2: Single Table ****
//@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
//@DiscriminatorColumn(name = "VEHICLE_TYPE")
//@DiscriminatorValue("VE")
// *****************************
// ****  Ex 3: Joined Table ****
@Inheritance(strategy = InheritanceType.JOINED)
// *****************************
public class Vehicle {

    @Id
    private String serialNumber;

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vehicle vehicle = (Vehicle) o;
        return Objects.equals(serialNumber, vehicle.serialNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(serialNumber);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("serialNumber", serialNumber)
                .toString();
    }
}
