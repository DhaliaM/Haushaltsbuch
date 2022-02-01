package com.homebrew.coffee.haushaltsbuch.persistence;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Laden der Einkaufsdaten aus einer Datenbank mittels SpringData.
 *
 * @author Dhalia
 */
@Repository
public interface PurchaseRepository extends CrudRepository<PurchaseEntity, Long> {

    @Query("SELECT n FROM PurchaseEntity n WHERE n.userId=?1 and n.dateBought BETWEEN ?2 and ?3")
    List<PurchaseEntity> findAllBetweenDatesForUserId(Long userId, LocalDate dateStart, LocalDate dateEnd);
}
