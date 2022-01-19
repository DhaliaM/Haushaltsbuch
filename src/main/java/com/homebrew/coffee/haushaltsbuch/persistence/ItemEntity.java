package com.homebrew.coffee.haushaltsbuch.persistence;

import javax.persistence.*;

@Entity
@Table(name = "items")
public class ItemEntity {

    @Id
    @GeneratedValue
    private Long id;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "userId", referencedColumnName = "UserId")
//    private UserEntity userEntity;
//
//    public UserEntity getUser() {
//        return userEntity;
//    }
//
//    public void setUser(UserEntity userEntity) {
//        this.userEntity = userEntity;
//    }

    @Column(name = "UserId")
    private Long userId;

    @Column(name = "Product_name")
    private String productName;

    @Column(name = "Category")
    private String category;

    @Column(name = "Quantity")
    private Integer quantity;

    @Column(name = "Min_Quantity")
    private Integer minQuantity;

    @Column(name = "PricePerQuantity")
    private Double pricePerQuantity;

    @Column(name = "DateBought")
    private String dateBought;

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

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getMinQuantity() {
        return minQuantity;
    }

    public void setMinQuantity(Integer minQuantity) {
        this.minQuantity = minQuantity;
    }

    public Double getPricePerQuantity() {
        return pricePerQuantity;
    }

    public void setPricePerQuantity(Double pricePerQuantity) {
        this.pricePerQuantity = pricePerQuantity;
    }

    public String getDateBought() {
        return dateBought;
    }

    public void setDateBought(String dateBought) {
        this.dateBought = dateBought;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "ItemsEntity{" +
                "id=" + id +
//                ", userEntity=" + userEntity +
//                ", userId=" + userId +
                ", productName='" + productName + '\'' +
                ", category='" + category + '\'' +
                ", quantity=" + quantity +
                ", minQuantity=" + minQuantity +
                ", pricePerQuantity=" + pricePerQuantity +
                ", dateBought=" + dateBought +
                '}';
    }
}
