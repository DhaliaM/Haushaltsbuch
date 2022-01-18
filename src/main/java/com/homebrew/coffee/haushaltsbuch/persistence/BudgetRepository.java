package com.homebrew.coffee.haushaltsbuch.persistence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BudgetRepository extends CrudRepository <User, Long>{

}
