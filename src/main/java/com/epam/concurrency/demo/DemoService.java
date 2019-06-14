package com.epam.concurrency.demo;

import com.epam.concurrency.bank.Bank;
import com.epam.concurrency.model.Account;
import com.epam.concurrency.service.AccountService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class DemoService {
    private static final Logger logger = LogManager.getLogger(DemoService.class.getName());

    private final static int THREAD_NUMBER = 20;
    private final static int OPERATION_NUMBER = 1000;

    public void execute() {
        AccountService accountService = new AccountService();
        Account from = accountService.getAccountById(151578L);
        Account to = accountService.getAccountById(244658L);
        logger.info(from);
        logger.info(to);
        logger.info("Final total balance: " + (from.getBalance() + to.getBalance()));

        ExecutorService threadPool = Executors.newFixedThreadPool(THREAD_NUMBER);
        ReentrantLock locker = new ReentrantLock();

        for (int i = 0; i < THREAD_NUMBER; i++) {
            threadPool.execute(new Bank(accountService, locker));
        }
        threadPool.shutdown();

        try {
            threadPool.awaitTermination(1, TimeUnit.DAYS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        from = accountService.getAccountById(151578L);
        to = accountService.getAccountById(244658L);
        logger.info(from);
        logger.info(to);
        logger.info("Final total balance: " + (from.getBalance() + to.getBalance()));
    }
}
