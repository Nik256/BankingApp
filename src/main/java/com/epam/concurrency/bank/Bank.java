package com.epam.concurrency.bank;

import com.epam.concurrency.exception.InsufficientFundsException;
import com.epam.concurrency.model.Account;
import com.epam.concurrency.service.AccountService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.locks.ReentrantLock;

public class Bank implements Runnable {
    private static final Logger logger = LogManager.getLogger(Bank.class.getName());

    private AccountService accountService;
    private ReentrantLock locker;

    public Bank(AccountService accountService, ReentrantLock locker) {
        this.accountService = accountService;
        this.locker = locker;
    }

    @Override
    public void run() {
        try {
            Account from = accountService.getAccountById(151578L);
            Account to = accountService.getAccountById(244658L);
            accountService.transfer(from, to, 100L);
        } catch (InsufficientFundsException e) {
            logger.error(e);
        }
    }

    public Long getTotalBalance() {
       return accountService.getAllAccounts()
               .stream()
               .mapToLong(Account::getBalance)
               .sum();
    }
}
