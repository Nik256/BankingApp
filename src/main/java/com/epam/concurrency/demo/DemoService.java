package com.epam.concurrency.demo;

import com.epam.concurrency.dao.AccountDao;
import com.epam.concurrency.model.Account;
import com.epam.concurrency.utils.AccountGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DemoService {
    private static final Logger logger = LogManager.getLogger(DemoService.class.getName());
    private static final int NUMBER_OF_ACCOUNTS = 10;

    AccountDao accountDao = new AccountDao();

    public void execute() {
        //createAccounts(NUMBER_OF_ACCOUNTS);
        showAccountsInfo();
    }

    private void createAccounts(int numberOfAccounts) {
        for (int i = 0; i < numberOfAccounts; i++) {
            accountDao.save(AccountGenerator.generateAccount());
        }
    }

    private void showAccountsInfo() {
        accountDao.getAll().forEach(logger::info);
        long sum = accountDao.getAll().stream().mapToLong(Account::getBalance).sum();
        logger.info("Summary: " + sum);
    }
}
