package com.smartbillsplittr.repository;

import com.smartbillsplittr.model.Group;
import com.smartbillsplittr.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {

    List<Group> findByCreatedBy(User createdBy);

    @Query("SELECT g FROM Group g JOIN g.members m WHERE m.id = :userId")
    List<Group> findGroupsByUserId(@Param("userId") Long userId);

    @Query("SELECT g FROM Group g WHERE g.name LIKE %:keyword% OR g.description LIKE %:keyword%")
    List<Group> searchGroups(@Param("keyword") String keyword);

    @Query("SELECT COUNT(m) FROM Group g JOIN g.members m WHERE g.id = :groupId")
    Long countMembersByGroupId(@Param("groupId") Long groupId);
}
