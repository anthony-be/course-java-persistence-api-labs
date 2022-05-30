package be.cocoding.jpa.exercise.entity.customer;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Objects;

public class Country {

    private Integer id;

    private String isoCode;

    private String name;

    public Country() {
    }

    public Integer getId() {
        return id;
    }

    public String getIsoCode() {
        return isoCode;
    }

    public void setIsoCode(String isoCode) {
        this.isoCode = isoCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Country country = (Country) o;
        return Objects.equals(id, country.id) && Objects.equals(isoCode, country.isoCode) && Objects.equals(name, country.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, isoCode, name);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("isoCode", isoCode)
                .append("name", name)
                .toString();
    }
}
