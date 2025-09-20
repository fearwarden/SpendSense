package com.ct.squad.spend.sense.transactions;

import com.ct.squad.spend.sense.transactions.dto.request.CreateTransactionDto;
import com.ct.squad.spend.sense.transactions.models.Transaction;
import com.ct.squad.spend.sense.transactions.services.TransactionService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transaction")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping("/count")
    public ResponseEntity<Long> getTransactionCount() {
        return ResponseEntity.ok(transactionService.countData());
    }

    @PostMapping
    public ResponseEntity<Transaction> createTransaction(@RequestBody @Validated CreateTransactionDto body) throws JsonProcessingException {
        return ResponseEntity.ok(transactionService.createTransaction(body));
    }
}
