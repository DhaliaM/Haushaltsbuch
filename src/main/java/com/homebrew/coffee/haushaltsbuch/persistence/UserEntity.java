package com.homebrew.coffee.haushaltsbuch.persistence;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
public class UserEntity {
    @Id
    @GeneratedValue
    @Column(name = "userId")
    private Long userId;

    public Long getUserId() {
        return userId;
    }

    @Column(name = "userName", unique = true, nullable = false)
    private String userName;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "role", nullable = false)
    private String role;

    @OneToMany(targetEntity = ProductEntity.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "userId")
    private List<ProductEntity> products = new ArrayList<>();

    @OneToMany(targetEntity = PurchaseEntity.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "userId")
    private List<PurchaseEntity> purchases = new ArrayList<>();

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<ProductEntity> getProducts() {
        return products;
    }

    public void setProducts(List<ProductEntity> items) {
        this.products = items;
    }

    public List<PurchaseEntity> getPurchases() {
        return purchases;
    }

    public void setPurchases(List<PurchaseEntity> purchases) {
        this.purchases = purchases;
    }
}
