package com.homebrew.coffee.haushaltsbuch.persistence;

import org.springframework.lang.NonNull;

import javax.persistence.*;

/**
 * Entität eines Objekts für die "product" Tabelle.
 */
@Entity
@Table(name = "product")
public class ProductEntity {

    @Id
    @GeneratedValue
    @Column(name = "productId")
    private Long productId;

    @Column(name = "userId")
    private Long userId;

    @Column(name = "productName",unique = true)
    @NonNull
    private String productName;

    @Column(name = "category")
    private String category;

    @Column(name = "minQuantity")
    private Integer minQuantity;

    @Column(name = "quantity", columnDefinition = "int default 0")
    private int quantity;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getMinQuantity() {
        return minQuantity;
    }

    public void setMinQuantity(Integer minQuantity) {
        this.minQuantity = minQuantity;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "ProductEntity{" +
                "productId=" + productId +
                ", userId=" + userId +
                ", productName='" + productName + '\'' +
                ", category='" + category + '\'' +
                ", minQuantity=" + minQuantity +
                ", quantity=" + quantity +
                '}';
    }
}
