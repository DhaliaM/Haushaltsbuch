package com.homebrew.coffee.haushaltsbuch.ui;

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
