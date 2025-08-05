package com.smartbillsplittr.dto.response;

import java.time.LocalDateTime;
import java.util.List;

public class GroupResponse {
    private Long id;
    private String name;
    private String description;
    private UserResponse createdBy;
    private List<UserResponse> members;
    private LocalDateTime createdAt;

    // Constructors
    public GroupResponse() {}

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public UserResponse getCreatedBy() { return createdBy; }
    public void setCreatedBy(UserResponse createdBy) { this.createdBy = createdBy; }

    public List<UserResponse> getMembers() { return members; }
    public void setMembers(List<UserResponse> members) { this.members = members; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
