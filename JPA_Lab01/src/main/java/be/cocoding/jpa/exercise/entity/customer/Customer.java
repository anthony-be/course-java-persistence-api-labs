package be.cocoding.jpa.exercise.entity.customer;

import org.apache.commons.lang3.NotImplementedException;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

import static javax.persistence.CascadeType.*;
import static javax.persistence.EnumType.STRING;
import static javax.persistence.TemporalType.DATE;

public class Customer {

    public Customer() {
    }

    @Override
    public boolean equals(Object o) {
        //TODO Implement me !
        throw new NotImplementedException("Method not implemented");
    }

    @Override
    public int hashCode() {
        //TODO Implement me !
        throw new NotImplementedException("Method not implemented");
    }
}
