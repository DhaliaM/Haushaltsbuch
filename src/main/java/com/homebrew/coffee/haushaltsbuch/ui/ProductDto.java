package com.homebrew.coffee.haushaltsbuch.ui;

import java.util.Date;

public class ProductDto {
    private Long userId;
    private String productName;
    private String category;
    private Integer quantityBought;
    private Integer minQuantity;
    private Double pricePerUnit;
    private Date dateBought;

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

    public Integer getQuantityBought() {
        return quantityBought;
    }

    public void setQuantityBought(Integer quantityBought) {
        this.quantityBought = quantityBought;
    }

    public Integer getMinQuantity() {
        return minQuantity;
    }

    public void setMinQuantity(Integer minQuantity) {
        this.minQuantity = minQuantity;
    }

    public Double getPricePerUnit() {
        return pricePerUnit;
    }

    public void setPricePerUnit(Double pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
    }

    public Date getDateBought() {
        return dateBought;
    }

    public void setDateBought(Date dateBought) {
        this.dateBought = dateBought;
    }

    @Override
    public String toString() {
        return "ItemDto{" +
                "userId=" + userId +
                ", productName='" + productName + '\'' +
                ", category='" + category + '\'' +
                ", quantity=" + quantityBought +
                ", minQuantity=" + minQuantity +
                ", pricePerQuantity=" + pricePerUnit +
                ", dateBought=" + dateBought +
                '}';
    }
}
