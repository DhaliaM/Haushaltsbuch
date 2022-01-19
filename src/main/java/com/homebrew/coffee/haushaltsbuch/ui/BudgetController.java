package com.homebrew.coffee.haushaltsbuch.ui;

import com.homebrew.coffee.haushaltsbuch.configurations.MyUserDetails;
import com.homebrew.coffee.haushaltsbuch.persistence.ItemEntity;
import com.homebrew.coffee.haushaltsbuch.persistence.UserEntity;
import com.homebrew.coffee.haushaltsbuch.service.DatabaseService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

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

        return "registration";
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

        return "login";
    }

    @GetMapping("/addItem")
    public String addItem(Model model) {
        ItemDto itemDto = new ItemDto();
        model.addAttribute("item", itemDto);

        return "addItem";
    }

    @PostMapping("/addItem")
    public void addItem(@ModelAttribute ItemDto itemDto, Model model) {
        MyUserDetails auth = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ItemEntity item = new ItemEntity();
        model.addAttribute("item", itemDto);

        item.setProductName(itemDto.getProductName());
        item.setCategory(itemDto.getCategory());
        item.setQuantity(itemDto.getQuantity());
        item.setPricePerQuantity(itemDto.getPricePerQuantity());

        System.out.println(auth.getUserId());
        System.out.println(auth.getPassword());
    }
}
