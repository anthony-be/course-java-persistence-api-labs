package be.cocoding.jpa.exercise.entity.vehicle;

import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.Objects;

@Entity
//@DiscriminatorValue("VA")   // Ex 2: Single Table
public class Van extends Truck {

    private int insideVolume;

    public int getInsideVolume() {
        return insideVolume;
    }

    public void setInsideVolume(int insideVolume) {
        this.insideVolume = insideVolume;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Van van = (Van) o;
        return insideVolume == van.insideVolume;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), insideVolume);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("insideVolume", insideVolume)
                .toString();
    }
}
