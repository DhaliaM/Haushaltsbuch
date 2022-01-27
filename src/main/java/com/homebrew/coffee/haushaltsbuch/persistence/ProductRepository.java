package com.homebrew.coffee.haushaltsbuch.persistence;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Laden der Produktdaten aus einer Datenbank mittels SpringData.
 *
 * @author Dhalia
 */
@Repository
public interface ProductRepository extends CrudRepository <ProductEntity, Long>{

    @Query("SELECT n FROM ProductEntity n WHERE n.productName=?1 and n.userId=?2")
    ProductEntity findByProductNameAndUserId(String productName, Long userId);

    @Query("SELECT n FROM ProductEntity n WHERE n.userId=?1")
    List<ProductEntity> findAllByUserId(Long userId);

    @Query("SELECT n from ProductEntity n where n.productId=?1")
    ProductEntity findByProductId(Long productId);
}
