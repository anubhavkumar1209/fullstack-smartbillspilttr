package com.smartbillsplittr.service;

import com.smartbillsplittr.dto.request.AddExpenseRequest;
import com.smartbillsplittr.dto.response.ExpenseResponse;

import java.util.List;

public interface ExpenseService {

    ExpenseResponse addExpense(AddExpenseRequest request, Long payerId);

    List<ExpenseResponse> getExpensesForGroup(Long groupId);

    ExpenseResponse updateExpense(Long expenseId, AddExpenseRequest request);

    void deleteExpense(Long expenseId);
}
