package com.homebrew.coffee.haushaltsbuch.service;

import com.homebrew.coffee.haushaltsbuch.persistence.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DatabaseService {
    private UserRepository userRepository;
    private ProductRepository productRepository;
    private PurchaseRepository purchaseRepository;

    public DatabaseService(UserRepository userRepository, ProductRepository productRepository, PurchaseRepository purchaseRepository) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.purchaseRepository = purchaseRepository;
    }

    public void addUser(UserEntity userEntity) {
        userRepository.save(userEntity);
    }

    public List<ProductEntity> getItemsById(Long userId) {
        return productRepository.findAllById(userId);
    }

    public ProductEntity getProduct(String productName, Long userId){
        ProductEntity productEntity;
        if (productRepository.findByProductNameAndUserId(productName, userId) == null) {
            productEntity = new ProductEntity();
            productEntity.setProductName(productName);
            productEntity.setUserId(userId);
            productRepository.save(productEntity);
        }
        productEntity = productRepository.findByProductNameAndUserId(productName,userId);
        return productEntity;
    }

    public void addPurchase(PurchaseEntity purchaseEntity) {
        purchaseRepository.save(purchaseEntity);

    }

}
