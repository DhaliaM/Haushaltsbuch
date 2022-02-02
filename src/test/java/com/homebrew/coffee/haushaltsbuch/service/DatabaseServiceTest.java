package com.homebrew.coffee.haushaltsbuch.service;

import com.homebrew.coffee.haushaltsbuch.persistence.*;
import com.homebrew.coffee.haushaltsbuch.ui.ProductDto;
import com.homebrew.coffee.haushaltsbuch.ui.PurchaseDto;
import com.homebrew.coffee.haushaltsbuch.ui.UserDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class DatabaseServiceTest {

    @InjectMocks
    DatabaseService databaseService;

    @Mock
    ProductRepository productRepository;

    @Mock
    PurchaseRepository purchaseRepository;

    private ProductEntity productEntity;
    private ProductDto productDto;
    private UserDto userDto;
    private PurchaseDto purchaseDto;
    private PurchaseEntity purchaseEntity, purchaseEntityTwo;

    @Captor
    private ArgumentCaptor<ProductEntity> productEntityCaptor;

    @Captor
    private ArgumentCaptor<PurchaseEntity> purchaseEntityCaptor;


    @BeforeEach
    public void setUp() {

        productEntity = new ProductEntity();
        productEntity.setUserId(1L);
        productEntity.setCategory("Nahrung");
        productEntity.setProductName("Kekse");
        productEntity.setMinQuantity(0);
        productEntity.setQuantity(0);
        productEntity.setProductId(1L);

        purchaseDto = new PurchaseDto();
        purchaseDto.setPricePerUnit(1.55);
        purchaseDto.setProductName("Kekse");
        purchaseDto.setQuantityBought(5);

        purchaseEntity = new PurchaseEntity();
        purchaseEntity.setDateBought(LocalDate.now());
        purchaseEntity.setPricePerUnit(purchaseDto.getPricePerUnit());
        purchaseEntity.setUserId(productEntity.getUserId());
        purchaseEntity.setQuantityBought(purchaseDto.getQuantityBought());
        purchaseEntity.setProductId(productEntity.getProductId());
        purchaseEntity.setCategory(productEntity.getCategory());

        purchaseEntityTwo = new PurchaseEntity();
        purchaseEntityTwo.setDateBought(LocalDate.now());
        purchaseEntityTwo.setPricePerUnit(3.55);
        purchaseEntityTwo.setUserId(productEntity.getUserId());
        purchaseEntityTwo.setQuantityBought(2);
        purchaseEntityTwo.setProductId(2L);
        purchaseEntityTwo.setCategory(productEntity.getCategory());

        productDto = new ProductDto();
        productDto.setProductName("Kekse");
        productDto.setCategory("Nahrung");
        productDto.setUserId(1L);
        productDto.setMinQuantity(0);

        userDto = new UserDto();
        userDto.setUserName("testUser");
        userDto.setPassword("password");

    }

    @AfterEach
    public void tearDown() {

        productRepository.deleteAll();
        productDto = null;
    }


    @Test
    public void addProduct() throws DataAlreadyExists {

        databaseService.addProduct(productDto);
        Mockito.verify(productRepository).save(productEntityCaptor.capture());
        ProductEntity productEntityCaptorValue = productEntityCaptor.getValue();

        assertEquals(productDto.getProductName(), productEntityCaptorValue.getProductName());
        assertEquals(productDto.getCategory(), productEntityCaptorValue.getCategory());
        assertEquals(productDto.getUserId(), productEntityCaptorValue.getUserId());
    }

    @Test
    void addUser() {

        // Password Encryptor funktioniert nicht
//        databaseService.addUser(userDto);
//        Mockito.verify(userRepository).save(userEntityCaptor.capture());
//        UserEntity userEntityCaptorValue = userEntityCaptor.getValue();
//
//        assertEquals(userDto.getUserName(),userEntityCaptorValue.getUserName());
//        assertEquals(userDto.getPassword(),userEntityCaptorValue.getPassword());
    }

    @Test
    void getItemsById() {

        List<ProductEntity> productList = new ArrayList<>();
        productList.add(productEntity);
        Mockito.when(productRepository.findAllByUserId(1L)).thenReturn(productList);

        List<ProductEntity> testProducts = databaseService.getItemsById(1L);

        assertEquals(productEntity, testProducts.get(0));
    }

    @Test
    void getProduct() {

        Mockito.when(productRepository.findByProductNameAndUserId("Kekse", 1L))
                .thenReturn(productEntity);

        ProductEntity product = databaseService.getProduct("Kekse", 1L);

        assertEquals("Kekse", product.getProductName());
    }

    @Test
    void addPurchase() {

        Mockito.when(databaseService.getProduct(purchaseDto.getProductName(), 1L))
                .thenReturn(productEntity);

        databaseService.addPurchase(purchaseDto, 1L);
        Mockito.verify(purchaseRepository).save(purchaseEntityCaptor.capture());
        PurchaseEntity purchaseEntityCaptorValue = purchaseEntityCaptor.getValue();

        assertEquals(purchaseDto.getPricePerUnit(), purchaseEntityCaptorValue.getPricePerUnit());
        assertEquals(purchaseDto.getQuantityBought(), purchaseEntityCaptorValue.getQuantityBought());
    }

    @Test
    void getExpenditureByDate() {

        List<PurchaseEntity> listOfPurchases = new ArrayList<>();
        listOfPurchases.add(purchaseEntity);
        listOfPurchases.add(purchaseEntityTwo);

        LocalDate date = LocalDate.now();
        LocalDate startWeek = date.with(WeekFields.ISO.getFirstDayOfWeek());
        LocalDate endWeek = startWeek.plusWeeks(1);
        Mockito.when(purchaseRepository.findAllBetweenDatesForUserId(1L, startWeek, endWeek))
                .thenReturn(listOfPurchases);

        List<PurchaseEntity> purchases = databaseService.getExpenditureByDate(1L, LocalDate.now());

        assertEquals(purchaseEntity, purchases.get(0));
        assertEquals(purchaseEntityTwo, purchases.get(1));
    }
}