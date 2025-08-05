package com.smartbillsplittr.repository;

import com.smartbillsplittr.model.Expense;
import com.smartbillsplittr.model.ExpenseSplit;
import com.smartbillsplittr.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ExpenseSplitRepository extends JpaRepository<ExpenseSplit, Long> {

    List<ExpenseSplit> findByExpense(Expense expense);

    List<ExpenseSplit> findByUser(User user);

    List<ExpenseSplit> findByUserAndIsSettled(User user, Boolean isSettled);

    @Query("SELECT es FROM ExpenseSplit es WHERE es.expense.group.id = :groupId AND es.user.id = :userId")
    List<ExpenseSplit> findByGroupIdAndUserId(@Param("groupId") Long groupId, @Param("userId") Long userId);

    @Query("SELECT SUM(es.amount) FROM ExpenseSplit es WHERE es.user.id = :userId AND es.isSettled = false")
    BigDecimal getTotalUnsettledAmountByUserId(@Param("userId") Long userId);

    @Query("SELECT SUM(es.amount) FROM ExpenseSplit es WHERE es.expense.group.id = :groupId AND es.user.id = :userId")
    BigDecimal getTotalSplitAmountByGroupAndUser(@Param("groupId") Long groupId, @Param("userId") Long userId);

    @Query("SELECT es FROM ExpenseSplit es WHERE es.expense.group.id = :groupId AND es.isSettled = false")
    List<ExpenseSplit> findUnsettledSplitsByGroupId(@Param("groupId") Long groupId);
}
