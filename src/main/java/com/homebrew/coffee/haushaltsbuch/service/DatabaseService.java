package com.homebrew.coffee.haushaltsbuch.service;

import com.homebrew.coffee.haushaltsbuch.persistence.*;
import com.homebrew.coffee.haushaltsbuch.ui.ExpenditureDto;
import com.homebrew.coffee.haushaltsbuch.ui.ProductDto;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.time.temporal.WeekFields;
import java.util.Date;
import java.util.List;

/**
 * Ein Service um verschieden Datenbankoperationen durchzuführen.
 * Zugriff auf userRepository, ProductRepository und PurchaseRepository.
 */
@Service
public class DatabaseService {
    private UserRepository userRepository;
    private ProductRepository productRepository;
    private PurchaseRepository purchaseRepository;

    public DatabaseService(UserRepository userRepository,
                           ProductRepository productRepository,
                           PurchaseRepository purchaseRepository) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.purchaseRepository = purchaseRepository;
    }

    /**
     * Fügt einen Nutzer der Datenbank hinzu.
     *
     * @param userEntity Nutzer Objekt vom Typ UserEntity
     */
    public void addUser(UserEntity userEntity) {

        userRepository.save(userEntity);
    }

    /**
     * Liefert eine Liste von Produkten aus der Datenbank, zugehörig zur übergebenen UserId.
     *
     * @param userId vom Typ Long
     * @return
     */
    public List<ProductEntity> getItemsById(Long userId) {

        return productRepository.findAllByUserId(userId);
    }

    /**
     * Legt ein neues Produkt in der Datenbank an.
     *
     * @param productDto Produkt Objekt vom Typ ProductDto
     */
    public void addProduct(ProductDto productDto) {

        if (productRepository.findByProductNameAndUserId(productDto.getProductName(), productDto.getUserId()) == null) {
            ProductEntity productEntity = new ProductEntity();
            productEntity.setProductName(productDto.getProductName());
            productEntity.setUserId(productDto.getUserId());
            productEntity.setCategory(productDto.getCategory());
            productEntity.setMinQuantity(productDto.getMinQuantity());
            productRepository.save(productEntity);
        }
    }

    /**
     * Holt ein vorhandenes Produkt über einen Abgleich von Produktname und UserId aus der Datenbank.
     * Oder legt ein neues Produkt mit den übergebenen Parametern an.
     *
     * @param productName Produktname als String
     * @param userId      userId als Long
     * @return Produkt Objekt vom Typ ProductEntity
     */
    public ProductEntity getProduct(String productName, Long userId) {

        ProductEntity productEntity;
        if (productRepository.findByProductNameAndUserId(productName, userId) == null) {
            productEntity = new ProductEntity();
            productEntity.setProductName(productName);
            productEntity.setUserId(userId);
            productRepository.save(productEntity);
        }
        productEntity = productRepository.findByProductNameAndUserId(productName, userId);

        return productEntity;
    }

    /**
     * Diese Methode fügt einen Einkauf zur Datenbank hinzu und erhöht die Menge des jeweiligen Produktes.
     *
     * @param purchaseEntity Einkaufs Objekt vom Typ PurchaseEntity
     */
    public void addPurchase(PurchaseEntity purchaseEntity) {

        purchaseRepository.save(purchaseEntity);
        ProductEntity productEntity = productRepository.findByProductId(purchaseEntity.getProductId());
        productEntity.setQuantity(productEntity.getQuantity() + purchaseEntity.getQuantityBought());
        productRepository.save(productEntity);
    }

    public List<PurchaseEntity> getExpenditureByDate(Long userId, LocalDate date) {

        LocalDate startWeek = date.with(WeekFields.ISO.getFirstDayOfWeek());
        LocalDate endWeek = startWeek.plusWeeks(1);

        return purchaseRepository.findAllBetweenDatesAndId(userId,startWeek,endWeek);
    }

}
