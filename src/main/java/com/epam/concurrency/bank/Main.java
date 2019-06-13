package com.epam.concurrency.bank;

import com.epam.concurrency.dao.Account;
import com.epam.concurrency.utils.AccountGenerator;
import com.epam.concurrency.utils.FileUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class.getName());

    public static void main(String[] args) {
        Account account = AccountGenerator.generateAccount();
        logger.info(account);
        FileUtil.writeAccountToFile(account, "account1");
        Account accFromFile = FileUtil.readAccountFromFile("account1");
        logger.info(accFromFile);
    }
}
