package com.smartbillsplittr.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public class SettleExpenseRequest {
    @NotNull
    private Long toUserId;

    @NotNull
    private Long groupId;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal amount;

    // Constructors
    public SettleExpenseRequest() {}

    public SettleExpenseRequest(Long toUserId, Long groupId, BigDecimal amount) {
        this.toUserId = toUserId;
        this.groupId = groupId;
        this.amount = amount;
    }

    // Getters and Setters
    public Long getToUserId() { return toUserId; }
    public void setToUserId(Long toUserId) { this.toUserId = toUserId; }

    public Long getGroupId() { return groupId; }
    public void setGroupId(Long groupId) { this.groupId = groupId; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
}
