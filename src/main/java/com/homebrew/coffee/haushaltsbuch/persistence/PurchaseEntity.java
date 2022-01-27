package com.homebrew.coffee.haushaltsbuch.persistence;

import javax.persistence.*;

/**
 * Entität eines Objekts für die "purchase" Tabelle.
 */
@Entity
@Table(name = "purchase")
public class PurchaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long purchaseId;

    @Column(name = "userId")
    private long userId;

    @Column(name = "productId")
    private long productId;

    @Column(name = "dateBought")
    private String dateBought;

    @Column(name = "quantityBought")
    private Integer quantityBought;

    @Column(name = "pricePerUnit")
    private Double pricePerUnit;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public String getDateBought() {
        return dateBought;
    }

    public void setDateBought(String dateBought) {
        this.dateBought = dateBought;
    }

    public Integer getQuantityBought() {
        return quantityBought;
    }

    public void setQuantityBought(Integer quantityBought) {
        this.quantityBought = quantityBought;
    }

    public Double getPricePerUnit() {
        return pricePerUnit;
    }

    public void setPricePerUnit(Double pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
    }

    @Override
    public String toString() {
        return "PurchaseEntity{" +
                "purchaseId=" + purchaseId +
                ", userId=" + userId +
                ", productId=" + productId +
                ", dateBought='" + dateBought + '\'' +
                ", quantityBought=" + quantityBought +
                ", pricePerUnit=" + pricePerUnit +
                '}';
    }
}
