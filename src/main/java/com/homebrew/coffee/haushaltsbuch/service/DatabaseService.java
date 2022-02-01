package com.homebrew.coffee.haushaltsbuch.service;

import com.homebrew.coffee.haushaltsbuch.persistence.*;
import com.homebrew.coffee.haushaltsbuch.ui.ProductDto;
import com.homebrew.coffee.haushaltsbuch.ui.PurchaseDto;
import com.homebrew.coffee.haushaltsbuch.ui.UserDto;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.List;

/**
 * Ein Service um verschiedene Datenbankoperationen durchzuführen.
 * Zugriff auf UserRepository, ProductRepository und PurchaseRepository.
 */
@Service
public class DatabaseService {
    private UserRepository userRepository;
    private ProductRepository productRepository;
    private PurchaseRepository purchaseRepository;
    private PasswordEncoder passwordEncoder;

    public DatabaseService(UserRepository userRepository,
                           ProductRepository productRepository,
                           PurchaseRepository purchaseRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.purchaseRepository = purchaseRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Fügt einen Nutzer der Datenbank hinzu.
     *
     * @param userDto Nutzer Objekt vom Typ UserDto
     */
    public void addUser(UserDto userDto) {

        if (userRepository.findByUserName(userDto.getUserName()) == null) {
            UserEntity userEntity = new UserEntity();
            userEntity.setUserName(userDto.getUserName());
            userEntity.setPassword(passwordEncoder.encode(userDto.getPassword()));
            userEntity.setPassword(userEntity.getPassword());
            userEntity.setRole("user");
            userRepository.save(userEntity);
        }
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
    public void addProduct(ProductDto productDto) throws DataAlreadyExists {

        if (productRepository.findByProductNameAndUserId(productDto.getProductName(), productDto.getUserId()) == null) {
            ProductEntity productEntity = new ProductEntity();
            productEntity.setProductName(productDto.getProductName());
            productEntity.setUserId(productDto.getUserId());
            productEntity.setCategory(productDto.getCategory());
            productEntity.setMinQuantity(productDto.getMinQuantity());
            productRepository.save(productEntity);
        } else {
            throw new DataAlreadyExists();
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

        if (productRepository.findByProductNameAndUserId(productName, userId) == null) {
            ProductEntity productEntity = new ProductEntity();
            productEntity.setProductName(productName);
            productEntity.setUserId(userId);
            productRepository.save(productEntity);
        }

        return productRepository.findByProductNameAndUserId(productName, userId);
    }

    /**
     * Diese Methode fügt einen Einkauf zur Datenbank hinzu und erhöht die Menge des jeweiligen Produktes.
     *
     * @param purchaseDto Einkaufs Objekt vom Typ PurchaseDto
     */
    public void addPurchase(PurchaseDto purchaseDto, Long userID) {

        ProductEntity productEntity = getProduct(purchaseDto.getProductName(), userID);
        PurchaseEntity purchaseEntity = new PurchaseEntity();

        purchaseEntity.setDateBought(LocalDate.now());
        purchaseEntity.setPricePerUnit(purchaseDto.getPricePerUnit());
        purchaseEntity.setUserId(userID);
        purchaseEntity.setQuantityBought(purchaseDto.getQuantityBought());
        purchaseEntity.setProductId(productEntity.getProductId());
        purchaseEntity.setCategory(productEntity.getCategory());
        purchaseRepository.save(purchaseEntity);

        productEntity.setQuantity(productEntity.getQuantity() + purchaseEntity.getQuantityBought());
        productRepository.save(productEntity);
    }

    /**
     * Gibt alle Einkäufe zurück, welche innerhalb der Woche des angegebenen Datums für den jeweiligen User
     * in der Datenbank existieren.
     *
     * @param userId UserId vom Typ Long
     * @param date Datum vom Typ LocalDate
     * @return Liste vom Typ PurchaseEntity, mit allen passenden Einträgen
     */
    public List<PurchaseEntity> getExpenditureByDate(Long userId, LocalDate date) {

        LocalDate startWeek = date.with(WeekFields.ISO.getFirstDayOfWeek());
        LocalDate endWeek = startWeek.plusWeeks(1);

        return purchaseRepository.findAllBetweenDatesForUserId(userId, startWeek, endWeek);
    }

}
