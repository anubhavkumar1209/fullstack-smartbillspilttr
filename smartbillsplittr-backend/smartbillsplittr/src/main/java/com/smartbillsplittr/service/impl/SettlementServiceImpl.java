package com.smartbillsplittr.service.impl;

import com.smartbillsplittr.dto.request.SettleExpenseRequest;
import com.smartbillsplittr.dto.response.BalanceResponse;
import com.smartbillsplittr.exception.ResourceNotFoundException;
import com.smartbillsplittr.model.*;
import com.smartbillsplittr.repository.ExpenseSplitRepository;
import com.smartbillsplittr.repository.GroupRepository;
import com.smartbillsplittr.repository.SettlementRepository;
import com.smartbillsplittr.repository.UserRepository;
import com.smartbillsplittr.service.SettlementService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class SettlementServiceImpl implements SettlementService {

    private final SettlementRepository settlementRepository;
    private final ExpenseSplitRepository expenseSplitRepository;
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    private final ModelMapper mapper = new ModelMapper();

    public SettlementServiceImpl(SettlementRepository settlementRepository,
                                 ExpenseSplitRepository expenseSplitRepository,
                                 UserRepository userRepository,
                                 GroupRepository groupRepository) {
        this.settlementRepository = settlementRepository;
        this.expenseSplitRepository = expenseSplitRepository;
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
    }

    @Override
    public void settleUp(SettleExpenseRequest request, Long fromUserId) {

        User fromUser = userRepository.findById(fromUserId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", fromUserId));
        User toUser = userRepository.findById(request.getToUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", request.getToUserId()));
        Group group = groupRepository.findById(request.getGroupId())
                .orElseThrow(() -> new ResourceNotFoundException("Group", "id", request.getGroupId()));

        Settlement settlement = new Settlement(fromUser, toUser, group, request.getAmount());
        settlementRepository.save(settlement);

        // Mark related splits as settled (simplified)
        List<ExpenseSplit> splits = expenseSplitRepository.findByGroupIdAndUserId(group.getId(), fromUserId);
        splits.stream()
                .filter(es -> !es.getIsSettled())
                .forEach(es -> es.setIsSettled(true));
    }

    @Override
    @Transactional(readOnly = true)
    public List<BalanceResponse> getBalancesForGroup(Long groupId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new ResourceNotFoundException("Group", "id", groupId));

        // Net balance per user = paid - owe
        Map<User, BigDecimal> net = new HashMap<>();
        for (User member : group.getMembers()) {
            BigDecimal paid = Optional.ofNullable(
                    settlementRepository.getTotalReceivedByUser(member.getId())).orElse(BigDecimal.ZERO);
            BigDecimal owe = Optional.ofNullable(
                    settlementRepository.getTotalPaidByUser(member.getId())).orElse(BigDecimal.ZERO);
            net.put(member, paid.subtract(owe));
        }
        return net.entrySet().stream()
                .map(e -> new BalanceResponse(e.getKey().getId(), e.getKey().getFullName(), e.getValue()))
                .collect(Collectors.toList());
    }
}
