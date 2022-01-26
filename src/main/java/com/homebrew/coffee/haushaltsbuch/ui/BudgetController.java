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
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class BudgetController {
    private DatabaseService databaseService;
    private PasswordEncoder passwordEncoder;

    public BudgetController(DatabaseService databaseService, PasswordEncoder passwordEncoder) {
        this.databaseService = databaseService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/registration")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new UserDto());

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

        ProductEntity productEntity = databaseService.getProduct(productDto.getProductName(), auth.getUserId());
        PurchaseEntity purchaseEntity = new PurchaseEntity();
        purchaseEntity.setDateBought(LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        purchaseEntity.setPricePerUnit(productDto.getPricePerUnit());
        purchaseEntity.setUserId(productEntity.getUserId());
        purchaseEntity.setQuantityBought(productDto.getQuantityBought());
        purchaseEntity.setProductId(productEntity.getProductId());
        databaseService.addPurchase(purchaseEntity);
    }

    @GetMapping("/home")
    public String items(Model model) {
        MyUserDetails auth = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("productList", databaseService.getItemsById(auth.getUserId()));
        PurchaseDto purchaseDto = new PurchaseDto();
        model.addAttribute("purchaseDto", purchaseDto);

        return "/home";
    }

    @PostMapping("/saveChange")
    public void saveChanges(@ModelAttribute List<ProductDto> listItemDto, Model model) {
        model.addAttribute("itemList", listItemDto);

        System.out.println(listItemDto.toString());
    }

    @PostMapping("/home")
    public void purchase(@RequestBody String jsonData) throws JsonProcessingException {
        MyUserDetails auth = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ObjectMapper objectMapper = new ObjectMapper();
        PurchaseDto purchaseDto = objectMapper.readValue(jsonData, PurchaseDto.class);

        PurchaseEntity purchaseEntity = new PurchaseEntity();
        ProductEntity productEntity = databaseService.getProduct(purchaseDto.getProductName(), auth.getUserId());

        purchaseEntity.setDateBought(LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        purchaseEntity.setPricePerUnit(purchaseDto.getPricePerUnit());
        purchaseEntity.setUserId(productEntity.getUserId());
        purchaseEntity.setQuantityBought(purchaseDto.getQuantityBought());
//        purchaseEntity.setProductName(productEntity.getProductName());
//        purchaseEntity.setProductId(productEntity.getProductId());

        System.out.println(productEntity);
        databaseService.addPurchase(purchaseEntity);


    }
}
