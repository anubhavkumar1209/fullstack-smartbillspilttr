package com.smartbillsplittr.service;

import com.smartbillsplittr.dto.request.SettleExpenseRequest;
import com.smartbillsplittr.dto.response.BalanceResponse;

import java.util.List;

public interface SettlementService {

    void settleUp(SettleExpenseRequest request, Long fromUserId);

    List<BalanceResponse> getBalancesForGroup(Long groupId);
}
