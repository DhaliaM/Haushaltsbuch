package com.homebrew.coffee.haushaltsbuch.ui;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.homebrew.coffee.haushaltsbuch.configurations.MyUserDetails;
import com.homebrew.coffee.haushaltsbuch.persistence.ProductEntity;
import com.homebrew.coffee.haushaltsbuch.persistence.PurchaseEntity;
import com.homebrew.coffee.haushaltsbuch.persistence.UserEntity;
import com.homebrew.coffee.haushaltsbuch.service.DatabaseService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Ein Spring REST Controller.
 *
 * @author Dhalia
 */
@Controller
public class BudgetController {
    private DatabaseService databaseService;
    private PasswordEncoder passwordEncoder;

    public BudgetController(DatabaseService databaseService,
                            PasswordEncoder passwordEncoder) {
        this.databaseService = databaseService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/registration")
    public String showRegistrationForm(Model model) {

        UserDto userDto = new UserDto();
        model.addAttribute("user", userDto);

        return "/registration";
    }

    @PostMapping("/registration")
    public String addUser(@ModelAttribute UserDto userDto, Model model) {

        UserEntity user = new UserEntity();
        model.addAttribute("user", userDto);

        // validation needed later
        user.setUserName(userDto.getUserName());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRole("user");
        databaseService.addUser(user);

        return "/login";
    }

    @GetMapping("/addItem")
    public String addItem(Model model) {

        ProductDto productDto = new ProductDto();
        model.addAttribute("product", productDto);

        return "/addItem";
    }

    @PostMapping("/addItem")
    public void addItem(@ModelAttribute ProductDto productDto, Model model) {

        MyUserDetails auth = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("product", productDto);


        productDto.setUserId(auth.getUserId());
        databaseService.addProduct(productDto);
    }

    @GetMapping("/home")
    public String items(Model model) {

        MyUserDetails auth = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("productList", databaseService.getItemsById(auth.getUserId()));
        PurchaseDto purchaseDto = new PurchaseDto();
        model.addAttribute("purchaseDto", purchaseDto);

        return "/home";
    }

    @PostMapping("/home")
    public void purchase(@RequestBody String jsonData) throws JsonProcessingException {

        MyUserDetails auth = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ObjectMapper objectMapper = new ObjectMapper();
        PurchaseDto purchaseDto = objectMapper.readValue(jsonData, PurchaseDto.class);

        PurchaseEntity purchaseEntity = new PurchaseEntity();
        ProductEntity productEntity = databaseService.getProduct(purchaseDto.getProductName(), auth.getUserId());

        purchaseEntity.setDateBought(LocalDate.now());
        purchaseEntity.setPricePerUnit(purchaseDto.getPricePerUnit());
        purchaseEntity.setUserId(auth.getUserId());
        purchaseEntity.setQuantityBought(purchaseDto.getQuantityBought());
        purchaseEntity.setProductId(productEntity.getProductId());
        purchaseEntity.setCategory(productEntity.getCategory());

        databaseService.addPurchase(purchaseEntity);
    }

    @GetMapping("/expenditure")
    public String getExpenditure(Model model) {

        List<PurchaseEntity> listOfPurchases = databaseService.getExpenditureByDate(16L, LocalDate.now());
        System.out.println(listOfPurchases.toString());

        List<String> listOfCategories = listOfPurchases.stream()
                .map(PurchaseEntity::getCategory)
                .distinct()
                .collect(Collectors.toList());

        HashMap<String, Double> sumPricePerCategory = new HashMap<>();
        for (String category : listOfCategories) {
            sumPricePerCategory.put(category, 0.00);
        }

        for (String category : listOfCategories) {
            for (PurchaseEntity purchase : listOfPurchases) {
                if (purchase.getCategory().equals(category)) {
                    sumPricePerCategory.put(category,
                            (sumPricePerCategory.get(category) + (purchase.getPricePerUnit() * purchase.getQuantityBought())));
                }
            }
        }
        model.addAttribute("expenditure", sumPricePerCategory);
        System.out.println(listOfCategories);
        System.out.println(sumPricePerCategory);
        return "/expenditure";
    }
}
