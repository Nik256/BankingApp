package com.epam.concurrency.service;

import com.epam.concurrency.dao.AccountDao;
import com.epam.concurrency.exception.InsufficientFundsException;
import com.epam.concurrency.dto.Account;
import com.epam.concurrency.utils.AccountGenerator;
import com.epam.concurrency.utils.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

public class AccountService {
    private static final Logger logger = LogManager.getLogger(AccountService.class.getName());
    private static AccountService instance;
    private static AccountDao accountDao = AccountDao.getInstance();

    private AccountService() {
    }

    public static AccountService getInstance() {
        if (instance == null) {
            instance = new AccountService();
        }
        return instance;
    }

    public void createAccounts(int accountNumber) {
        for (int i = 0; i < accountNumber; i++) {
            accountDao.save(AccountGenerator.generateAccount());
        }
    }

    public void deleteAccounts() {
        accountDao.deleteAll();
    }

    public void showAccountsInfo() {
        accountDao.getAll().forEach(logger::info);
        long sum = accountDao.getAll().stream().mapToLong(Account::getBalance).sum();
        logger.info("Total Balance: " + sum);
    }
}
