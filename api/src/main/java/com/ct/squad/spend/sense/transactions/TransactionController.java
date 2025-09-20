package com.ct.squad.spend.sense.transactions;

import com.ct.squad.spend.sense.transactions.services.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/transaction")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping("/count")
    public ResponseEntity<Long> getTransactionCount() {
        return ResponseEntity.ok(transactionService.countData());
    }
}
