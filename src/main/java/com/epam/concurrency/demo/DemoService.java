package com.epam.concurrency.demo;

import com.epam.concurrency.bank.Bank;
import com.epam.concurrency.service.AccountService;
import com.epam.concurrency.utils.TransferGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class DemoService {
    private static final Logger logger = LogManager.getLogger(DemoService.class.getName());
    private static final int ACCOUNTS_NUMBER = 10;
    private static final int THREAD_NUMBER = 20;
    private static final int TRANSACTION_NUMBER = 1000;
    private AccountService accountService = AccountService.getInstance();

    public void execute() {
        //accountService.deleteAccounts();
        //accountService.createAccounts(ACCOUNTS_NUMBER);
        accountService.showAccountsInfo();

        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_NUMBER);
        IntStream.range(0, TRANSACTION_NUMBER).forEach(i -> executorService.submit(
                new Bank(TransferGenerator.generate())));
        executorService.shutdown();
        try {
            executorService.awaitTermination(1, TimeUnit.DAYS);
        } catch (InterruptedException e) {
            logger.error(e);
        }
        accountService.showAccountsInfo();
    }
}
