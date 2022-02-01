package com.homebrew.coffee.haushaltsbuch.ui;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * Ein Dto um Einkäufe zu übertragen.
 */
public class PurchaseDto {

    @NotEmpty
    @Min(value=1, message = "Minimum 1")
    private Integer quantityBought;

    @NotEmpty
    private Double pricePerUnit;

    @NotEmpty
    @Size(min=1, max = 200, message = "1-200 Zeichen erlaubt")
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
