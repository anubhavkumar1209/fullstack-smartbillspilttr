package com.smartbillsplittr.repository;

import com.smartbillsplittr.model.Group;
import com.smartbillsplittr.model.Settlement;
import com.smartbillsplittr.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SettlementRepository extends JpaRepository<Settlement, Long> {

    List<Settlement> findByGroup(Group group);

    List<Settlement> findByFromUser(User fromUser);

    List<Settlement> findByToUser(User toUser);

    List<Settlement> findByGroupOrderBySettlementDateDesc(Group group);

    @Query("SELECT s FROM Settlement s WHERE (s.fromUser.id = :userId OR s.toUser.id = :userId) AND s.group.id = :groupId")
    List<Settlement> findSettlementsByUserAndGroup(@Param("userId") Long userId, @Param("groupId") Long groupId);

    @Query("SELECT SUM(s.amount) FROM Settlement s WHERE s.fromUser.id = :userId")
    BigDecimal getTotalPaidByUser(@Param("userId") Long userId);

    @Query("SELECT SUM(s.amount) FROM Settlement s WHERE s.toUser.id = :userId")
    BigDecimal getTotalReceivedByUser(@Param("userId") Long userId);

    @Query("SELECT s FROM Settlement s WHERE s.group.id = :groupId AND s.settlementDate BETWEEN :startDate AND :endDate")
    List<Settlement> findByGroupAndDateRange(@Param("groupId") Long groupId,
                                             @Param("startDate") LocalDateTime startDate,
                                             @Param("endDate") LocalDateTime endDate);
}
