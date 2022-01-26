package com.homebrew.coffee.haushaltsbuch.ui;

public class PurchaseDto {
    private Integer quantityBought;
    private Double pricePerUnit;
    private String productName;

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

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    @Override
    public String toString() {
        return "PurchaseDto{" +
                "quantityBought=" + quantityBought +
                ", pricePerUnit=" + pricePerUnit +
                ", productName=" + productName + '\'' +
                '}';
    }
}
