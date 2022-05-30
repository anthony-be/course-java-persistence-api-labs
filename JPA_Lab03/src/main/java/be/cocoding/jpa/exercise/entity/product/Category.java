package be.cocoding.jpa.exercise.entity.product;

import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "PRODUCT_CATEGORY")
public class Category {

    @Id
    private Integer id;

    @Basic
    private String name;

    @Basic
    private String description;

    @Column(name = "CREATED_ON")
    private Timestamp createdOn;

    @Column(name = "UPDATED_ON")
    private Timestamp updatedOn;

    public Category() {
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Timestamp getCreatedOn() {
        return createdOn;
    }

    public Timestamp getUpdatedOn() {
        return updatedOn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(id, category.id) && Objects.equals(name, category.name)
                && Objects.equals(description, category.description) && Objects.equals(createdOn, category.createdOn)
                && Objects.equals(updatedOn, category.updatedOn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, createdOn, updatedOn);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("name", name)
                .append("description", description)
                .append("createdOn", createdOn)
                .append("updatedOn", updatedOn)
                .toString();
    }
}
