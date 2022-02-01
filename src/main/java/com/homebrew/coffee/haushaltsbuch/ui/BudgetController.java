package com.homebrew.coffee.haushaltsbuch.ui;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.homebrew.coffee.haushaltsbuch.configurations.MyUserDetails;
import com.homebrew.coffee.haushaltsbuch.persistence.DataAlreadyExists;
import com.homebrew.coffee.haushaltsbuch.persistence.PurchaseEntity;
import com.homebrew.coffee.haushaltsbuch.service.DatabaseService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Ein Spring REST Controller.
 *
 * @author Dhalia
 */
@Controller
public class BudgetController {
    private DatabaseService databaseService;

    public BudgetController(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    @GetMapping("/registration")
    public String showRegistrationForm(Model model) {

        UserDto userDto = new UserDto();
        model.addAttribute("user", userDto);

        return "/registration";
    }

    @PostMapping("/registration")
    public String addUser(@ModelAttribute UserDto userDto, Model model) {

        model.addAttribute("user", userDto);

        databaseService.addUser(userDto);

        return "/login";
    }

    @GetMapping("/addItem")
    public String addItem(Model model) {
        model.addAttribute("error", "");
        ProductDto productDto = new ProductDto();
        model.addAttribute("product", productDto);

        return "/addItem";
    }

    @PostMapping("/addItem")
    public String addItem(@ModelAttribute ProductDto productDto, Model model) {

        MyUserDetails auth = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("product", productDto);

        productDto.setUserId(auth.getUserId());
        try {
            databaseService.addProduct(productDto);
        } catch (DataAlreadyExists e) {
            model.addAttribute("errorMessage","Produkt existiert schon.");

            return "/addItem";
        }

        return "redirect:home";
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

        databaseService.addPurchase(purchaseDto, auth.getUserId());
    }

    @GetMapping("/expenditure")
    public String getExpenditure(Model model) {

        MyUserDetails auth = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<PurchaseEntity> listOfPurchases = databaseService.getExpenditureByDate(auth.getUserId(), LocalDate.now());
        System.out.println(listOfPurchases.toString());

        List<String> listOfCategories = listOfPurchases.stream()
                .map(PurchaseEntity::getCategory)
                .distinct()
                .collect(Collectors.toList());

        model.addAttribute("expenditure", sumPricePerCategory(listOfCategories,listOfPurchases));

        return "/expenditure";
    }

    private HashMap sumPricePerCategory(List<String> listOfCategories, List<PurchaseEntity> listOfPurchases){

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

        return sumPricePerCategory;
    }
}
