package com.epam.concurrency;

import com.epam.concurrency.bank.Bank;
import com.epam.concurrency.counter.TransactionCounter;
import com.epam.concurrency.service.AccountService;
import com.epam.concurrency.utils.TransferGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TransactionTest {
    private AccountService accountService = AccountService.getInstance();

    @BeforeEach
    void setUp() {
        accountService.deleteAccounts();
        accountService.createAccounts(10);
    }

    @Test
    void initialTotalBalanceShouldBeEqualToFinalTotalBalance() throws InterruptedException {
        long initialTotalBalance = accountService.getTotalBalance();
        ExecutorService executorService = Executors.newFixedThreadPool(20);
        IntStream.range(0, 1000).forEach(i -> executorService.submit(
                new Bank(TransferGenerator.generate())));
        executorService.shutdown();
        executorService.awaitTermination(30, TimeUnit.SECONDS);
        long finalTotalBalance = accountService.getTotalBalance();
        assertEquals(initialTotalBalance, finalTotalBalance);
    }

    @Test
    void overallNumberOfTransactionsShouldBeEqualToSucceedPlusSkippedTransactions() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(20);
        IntStream.range(0, 1000).forEach(i -> executorService.submit(
                new Bank(TransferGenerator.generate())));
        executorService.shutdown();
        executorService.awaitTermination(30, TimeUnit.SECONDS);
        assertEquals(TransactionCounter.overall.get(), TransactionCounter.skipped.get() +
                TransactionCounter.succeed.getAndIncrement());
    }
}
