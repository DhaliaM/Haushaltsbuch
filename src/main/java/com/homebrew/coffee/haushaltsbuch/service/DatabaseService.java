package com.homebrew.coffee.haushaltsbuch.service;

import com.homebrew.coffee.haushaltsbuch.persistence.ItemEntity;
import com.homebrew.coffee.haushaltsbuch.persistence.ItemsRepository;
import com.homebrew.coffee.haushaltsbuch.persistence.UserEntity;
import com.homebrew.coffee.haushaltsbuch.persistence.UserRepository;
import com.homebrew.coffee.haushaltsbuch.ui.ItemDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DatabaseService {
    private UserRepository userRepository;
    private ItemsRepository itemsRepository;

    public DatabaseService(UserRepository userRepository, ItemsRepository itemsRepository) {
        this.userRepository = userRepository;
        this.itemsRepository = itemsRepository;
    }

    public void addUser(UserEntity userEntity) {
        userRepository.save(userEntity);
    }

    public void addSingleItem(ItemEntity itemEntity) {
        if (itemsRepository.findByProductNameAndUserId(itemEntity.getProductName(), itemEntity.getUserId()) == null) {
            itemsRepository.save(itemEntity);
        }
    }

    public List<ItemEntity> getItemsById(Long userId){
        return itemsRepository.findAllById(userId);
    }
    public void addItems(List<ItemDto> itemDtoList) {

    }

}
