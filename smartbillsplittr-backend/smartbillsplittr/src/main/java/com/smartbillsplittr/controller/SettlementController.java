package com.smartbillsplittr.controller;

import com.smartbillsplittr.dto.request.SettleExpenseRequest;
import com.smartbillsplittr.dto.response.BalanceResponse;
import com.smartbillsplittr.service.SettlementService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import com.smartbillsplittr.security.UserPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/settlements")
public class SettlementController {

    private final SettlementService settlementService;

    public SettlementController(SettlementService settlementService) {
        this.settlementService = settlementService;
    }

    private Long currentUserId() {
        return ((UserPrincipal)SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal()).getId();
    }

    @PostMapping
    public ResponseEntity<Void> settleUp(
            @Valid @RequestBody SettleExpenseRequest request) {
        settlementService.settleUp(request, currentUserId());
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<BalanceResponse>> getBalances(
            @RequestParam Long groupId) {
        return ResponseEntity.ok(settlementService.getBalancesForGroup(groupId));
    }
}
