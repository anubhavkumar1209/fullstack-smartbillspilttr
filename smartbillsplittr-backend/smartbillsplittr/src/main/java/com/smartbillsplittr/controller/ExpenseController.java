package com.smartbillsplittr.controller;

import com.smartbillsplittr.dto.request.AddExpenseRequest;
import com.smartbillsplittr.dto.response.ExpenseResponse;
import com.smartbillsplittr.service.ExpenseService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import com.smartbillsplittr.security.UserPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    private Long currentUserId() {
        return ((UserPrincipal)SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal()).getId();
    }

    @PostMapping
    public ResponseEntity<ExpenseResponse> addExpense(
            @Valid @RequestBody AddExpenseRequest request) {
        ExpenseResponse res = expenseService.addExpense(request, currentUserId());
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ExpenseResponse>> listByGroup(
            @RequestParam Long groupId) {
        return ResponseEntity.ok(expenseService.getExpensesForGroup(groupId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExpenseResponse> updateExpense(
            @PathVariable Long id,
            @Valid @RequestBody AddExpenseRequest request) {
        return ResponseEntity.ok(expenseService.updateExpense(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExpense(@PathVariable Long id) {
        expenseService.deleteExpense(id);
        return ResponseEntity.noContent().build();
    }
}
