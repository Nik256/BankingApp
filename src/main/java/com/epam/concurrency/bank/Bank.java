package com.epam.concurrency.bank;

import com.epam.concurrency.dto.Transfer;
import com.epam.concurrency.service.TransactionService;

public class Bank implements Runnable {
    private TransactionService transactionService = TransactionService.getInstance();
    private Transfer transfer;

    public Bank(Transfer transfer) {
        this.transfer = transfer;
    }

    @Override
    public void run() {
        transactionService.transfer(transfer);
    }
}
