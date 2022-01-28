package com.homebrew.coffee.haushaltsbuch.persistence;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

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

    @Basic
    @Column(name = "dateBought")
    private LocalDate dateBought;

    @Column(name = "quantityBought")
    private Integer quantityBought;

    @Column(name = "pricePerUnit")
    private Double pricePerUnit;

    @Column(name = "category")
    private String category;

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

    public LocalDate getDateBought() {
        return dateBought;
    }

    public void setDateBought(LocalDate dateBought) {
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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
                ", category='" + category + '\'' +
                '}';
    }
}
