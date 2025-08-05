package com.smartbillsplittr.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;

public class AddExpenseRequest {
    @NotBlank
    @Size(max = 200)
    private String description;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal amount;

    @NotNull
    private Long groupId;

    @Size(max = 100)
    private String category;

    @NotNull
    private List<Long> participantIds;

    // Constructors
    public AddExpenseRequest() {}

    public AddExpenseRequest(String description, BigDecimal amount, Long groupId, String category, List<Long> participantIds) {
        this.description = description;
        this.amount = amount;
        this.groupId = groupId;
        this.category = category;
        this.participantIds = participantIds;
    }

    // Getters and Setters
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public Long getGroupId() { return groupId; }
    public void setGroupId(Long groupId) { this.groupId = groupId; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public List<Long> getParticipantIds() { return participantIds; }
    public void setParticipantIds(List<Long> participantIds) { this.participantIds = participantIds; }
}
