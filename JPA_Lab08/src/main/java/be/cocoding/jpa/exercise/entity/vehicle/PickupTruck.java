package be.cocoding.jpa.exercise.entity.vehicle;

import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.Objects;

@Entity
//@DiscriminatorValue("PI")  // Ex 2: Single Table
public class PickupTruck extends Truck {

    private int outsideVolume;

    public int getOutsideVolume() {
        return outsideVolume;
    }

    public void setOutsideVolume(int outsideVolume) {
        this.outsideVolume = outsideVolume;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        PickupTruck that = (PickupTruck) o;
        return outsideVolume == that.outsideVolume;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), outsideVolume);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("outsideVolume", outsideVolume)
                .toString();
    }
}
