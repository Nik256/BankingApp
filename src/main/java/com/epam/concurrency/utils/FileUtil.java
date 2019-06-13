package com.epam.concurrency.utils;

import com.epam.concurrency.dao.Account;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;

public class FileUtil {
    private static final Logger logger = LogManager.getLogger(FileUtil.class.getName());

    public static void writeAccountToFile(Account account, String filename) {
        try (FileOutputStream file = new FileOutputStream(filename);
             ObjectOutputStream out = new ObjectOutputStream(file)) {
            out.writeObject(account);
        } catch (IOException e) {
            logger.error(e);
        }
    }

    public static Account readAccountFromFile(String filename) {
        Account account = null;
        try (FileInputStream file = new FileInputStream(filename);
             ObjectInputStream in = new ObjectInputStream(file)) {
            account = (Account) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            logger.error(e);
        }
        return account;
    }
}
