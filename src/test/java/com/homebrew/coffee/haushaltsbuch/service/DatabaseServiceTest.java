package com.homebrew.coffee.haushaltsbuch.service;

import com.homebrew.coffee.haushaltsbuch.persistence.ProductEntity;
import com.homebrew.coffee.haushaltsbuch.persistence.ProductRepository;
import com.homebrew.coffee.haushaltsbuch.persistence.UserEntity;
import com.homebrew.coffee.haushaltsbuch.persistence.UserRepository;
import com.homebrew.coffee.haushaltsbuch.ui.ProductDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class DatabaseServiceTest {

    @InjectMocks
    DatabaseService databaseService;

    @Mock
    UserRepository userRepository;

    @Mock
    ProductRepository productRepository;
    @Autowired
    private ProductEntity product;
    private ProductDto productDto;

    @BeforeEach
    public void setUp(){

        product = new ProductEntity();
        product.setUserId(1L);
        product.setCategory("Nahrung");
        product.setProductName("Kekse");
        product.setMinQuantity(0);
        product.setQuantity(5);
        product.setProductId(3L);

        productDto = new ProductDto();
        productDto.setProductName("Kekse");
        productDto.setCategory("Nahrung");
        productDto.setUserId(3L);

    }

    @AfterEach
    public void tearDown() {

        productRepository.deleteAll();
        product = null;
        productDto = null;
    }

    @Test
    void addUser() {

    }

    @Test
    void getItemsById() {

        List<ProductEntity> productList = new ArrayList<>();
        productList.add(product);

        Mockito.when(productRepository.findAllByUserId(1L)).thenReturn(productList);
        List<ProductEntity> testProducts = databaseService.getItemsById(1L);
        assertEquals(product,testProducts.get(0));

    }

    @Test
    public void addProduct(){

        databaseService.addProduct(productDto);
        Mockito.when(productRepository.findByProductNameAndUserId(productDto.getProductName(),productDto.getUserId())).thenReturn(product);
        ProductEntity fetchedProduct = databaseService.getProduct(productDto.getProductName(),productDto.getUserId());
        assertEquals(productDto.getProductName(), fetchedProduct.getProductName());

    }

    @Test
    void getProduct() {
    }

    @Test
    void addPurchase() {
    }

    @Test
    void getExpenditureByDate() {
    }
}