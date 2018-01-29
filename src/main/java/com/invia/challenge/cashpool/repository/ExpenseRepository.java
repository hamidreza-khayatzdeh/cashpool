package com.invia.challenge.cashpool.repository;

import com.invia.challenge.cashpool.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;

/**
 * Created by khayatzadeh on 1/25/2018.
 */
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    @Query("SELECT sum(amount) FROM Expense WHERE tripTravelerRel.id = :tripTravelerRelId")
    BigDecimal getTotalSpentAmount(@Param("tripTravelerRelId") Long tripTravelerRelId);

}
