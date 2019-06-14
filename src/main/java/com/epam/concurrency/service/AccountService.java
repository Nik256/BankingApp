package com.epam.concurrency.service;

import com.epam.concurrency.dao.AccountDao;
import com.epam.concurrency.exception.InsufficientFundsException;
import com.epam.concurrency.model.Account;
import com.epam.concurrency.utils.AccountGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class AccountService {
    private static final Logger logger = LogManager.getLogger(AccountService.class.getName());
    private static final int NUMBER_OF_ACCOUNTS = 10;

    private AccountDao accountDao;
    private Lock locker;

    public AccountService() {
        locker = new ReentrantLock();
        accountDao = new AccountDao();
    }

    public AccountService(AccountDao accountDao, Lock locker) {
        this.accountDao = accountDao;
        this.locker = locker;
    }

    public Long getBalance(Long id) {
        return accountDao.get(id).getBalance();
    }

    public Account getAccountById(Long id) {
        locker.lock();
        try {
            return accountDao.get(id);
        } finally {
            locker.unlock();
        }
    }

    public void deposit(Account account, Long amount) {
        locker.lock();
        try {
            account.setBalance(account.getBalance() + amount);
            accountDao.save(account);
        } finally {
            locker.unlock();
        }
    }

    public void withdraw(Account account, Long amount) throws InsufficientFundsException {
        locker.lock();
        try {
            if ((account.getBalance() - amount) > 0) {
                account.setBalance(account.getBalance() - amount);
            } else {
                throw new InsufficientFundsException();
            }
            accountDao.save(account);
        } finally {
            locker.unlock();
        }
    }

    public void transfer(Account fromAccount, Account toAccount, Long amount) throws InsufficientFundsException {
        locker.lock();
        try {
            withdraw(fromAccount, amount);
            deposit(toAccount, amount);
            accountDao.save(fromAccount);
            accountDao.save(toAccount);
            logger.info(Thread.currentThread().getName() + " / " + "Transfer " + amount + ": " + fromAccount + " -> " + toAccount);
        } finally {
            locker.unlock();
        }
    }

    public void createAccounts() {
        for (int i = 0; i < NUMBER_OF_ACCOUNTS; i++) {
            accountDao.save(AccountGenerator.generateAccount());
        }
    }

    public void showAccountsInfo() {
        accountDao.getAll().forEach(logger::info);
        long sum = accountDao.getAll().stream().mapToLong(Account::getBalance).sum();
        logger.info("Summary: " + sum);
    }

    public List<Account> getAllAccounts() {
        locker.lock();
        try {
            return accountDao.getAll();
        } finally {
            locker.unlock();
        }
    }
}
