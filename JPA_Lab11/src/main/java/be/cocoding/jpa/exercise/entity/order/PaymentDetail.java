package be.cocoding.jpa.exercise.entity.order;

import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "PAYMENT_DETAIL")
public class PaymentDetail {

    @Id
    private Integer id;

    @Basic
    private BigDecimal amount;

    @Basic
    private String provider;

    @Column(name = "PROVIDER_PAYMENT_UUID")
    private String providerTransactionId;

    public PaymentDetail() {
    }

    public Integer getId() {
        return id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getProviderTransactionId() {
        return providerTransactionId;
    }

    public void setProviderTransactionId(String providerTransactionId) {
        this.providerTransactionId = providerTransactionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaymentDetail that = (PaymentDetail) o;
        return Objects.equals(id, that.id) && Objects.equals(amount, that.amount)
                && Objects.equals(provider, that.provider) && Objects.equals(providerTransactionId, that.providerTransactionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, amount, provider, providerTransactionId);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("amount", amount)
                .append("provider", provider)
                .append("providerTransactionId", providerTransactionId)
                .toString();
    }
}
