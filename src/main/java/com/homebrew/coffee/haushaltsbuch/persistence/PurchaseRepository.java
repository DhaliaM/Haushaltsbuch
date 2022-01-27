package com.homebrew.coffee.haushaltsbuch.persistence;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
/**
 * Laden der Einkaufsdaten aus einer Datenbank mittels SpringData.
 *
 * @author Dhalia
 */
@Repository
public interface PurchaseRepository extends CrudRepository<PurchaseEntity, Long> {

//    @Query("SELECT n FROM PurchaseEntity n WHERE n.productId=?1 and n.userId=?2")
//    PurchaseEntity findByProductName(Long productId, Long userId);
}
