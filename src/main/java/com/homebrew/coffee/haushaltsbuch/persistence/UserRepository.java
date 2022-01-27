package com.homebrew.coffee.haushaltsbuch.persistence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
/**
 * Laden der Nutzerdaten aus einer Datenbank mittels SpringData.
 *
 * @author Dhalia
 */
@Repository
public interface UserRepository extends CrudRepository<UserEntity,Long> {

    UserEntity findByUserName(String userName);

}
