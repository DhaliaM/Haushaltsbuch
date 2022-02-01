package com.homebrew.coffee.haushaltsbuch.ui;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * Ein Dto um Produkte zu übertragen.
 */
public class ProductDto {
    private Long userId;
    private String productName;
    private String category;
    private Integer minQuantity;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @NotEmpty
    @Size(min=1, max = 200, message = "1-200 Zeichen erlaubt")
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    @NotEmpty
    @Size(min=1, max = 200, message = "1-200 Zeichen erlaubt")
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

    @Override
    public String toString() {
        return "ProductDto{" +
                "userId=" + userId +
                ", productName='" + productName + '\'' +
                ", category='" + category + '\'' +
                ", minQuantity=" + minQuantity +
                '}';
    }
}
