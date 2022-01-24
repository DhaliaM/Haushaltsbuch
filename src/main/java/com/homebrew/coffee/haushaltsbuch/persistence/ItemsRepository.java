package com.homebrew.coffee.haushaltsbuch.persistence;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemsRepository extends CrudRepository <ItemEntity, Long>{

    @Query("SELECT n FROM ItemEntity n WHERE n.productName=?1 and n.userId=?2")
    ItemEntity findByProductNameAndUserId(String productName, Long userId);

    @Query("SELECT n FROM ItemEntity n WHERE n.userId=?1")
    List<ItemEntity> findAllById(Long userId);

}
