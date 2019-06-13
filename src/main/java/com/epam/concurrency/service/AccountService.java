package com.epam.concurrency.service;

import com.epam.concurrency.dao.AccountDao;
import com.epam.concurrency.exception.InsufficientFundsException;
import com.epam.concurrency.model.Account;

public class AccountService {
    private AccountDao accountDao;

    public AccountService() {
        accountDao = new AccountDao();
    }

    public AccountService(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    public Long getBalance(Long id) {
        return accountDao.get(id).getBalance();
    }

    public void deposit(Account account, Long amount) {
        account.setBalance(account.getBalance() + amount);
        accountDao.save(account);
    }

    public void withdraw(Account account, Long amount) throws InsufficientFundsException {
        if ((account.getBalance() - amount) > 0) {
            account.setBalance(account.getBalance() - amount);
        } else {
            throw new InsufficientFundsException();
        }
        accountDao.save(account);
    }

    public void transfer(Account fromAccount, Account toAccount, Long amount) throws InsufficientFundsException {
        withdraw(fromAccount, amount);
        deposit(toAccount, amount);
        accountDao.save(fromAccount);
        accountDao.save(toAccount);
    }
}
