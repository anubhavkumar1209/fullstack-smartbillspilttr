package com.smartbillsplittr.repository;

import com.smartbillsplittr.model.Expense;
import com.smartbillsplittr.model.Group;
import com.smartbillsplittr.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    List<Expense> findByGroup(Group group);

    List<Expense> findByPaidBy(User paidBy);

    List<Expense> findByGroupOrderByExpenseDateDesc(Group group);

    List<Expense> findByGroupAndExpenseDateBetween(Group group, LocalDateTime startDate, LocalDateTime endDate);

    @Query("SELECT e FROM Expense e WHERE e.group.id = :groupId AND e.category = :category")
    List<Expense> findByGroupIdAndCategory(@Param("groupId") Long groupId, @Param("category") String category);

    @Query("SELECT SUM(e.amount) FROM Expense e WHERE e.group.id = :groupId")
    BigDecimal getTotalExpensesByGroupId(@Param("groupId") Long groupId);

    @Query("SELECT SUM(e.amount) FROM Expense e WHERE e.paidBy.id = :userId")
    BigDecimal getTotalExpensesByUserId(@Param("userId") Long userId);

    @Query("SELECT e FROM Expense e JOIN e.expenseSplits es WHERE es.user.id = :userId")
    List<Expense> findExpensesByParticipantUserId(@Param("userId") Long userId);
}
