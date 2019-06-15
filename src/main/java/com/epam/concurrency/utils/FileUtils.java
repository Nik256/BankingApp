package com.epam.concurrency.utils;

import com.epam.concurrency.dto.Account;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class FileUtils {
    private static final Logger logger = LogManager.getLogger(FileUtils.class.getName());

    public static synchronized void writeAccountToFile(Account account, String filename) {
        File file = new File(getAccountsFolderPath(), filename);
        try (FileOutputStream fileOutputStream = new FileOutputStream(file);
             ObjectOutputStream out = new ObjectOutputStream(fileOutputStream)) {
            out.writeObject(account);
        } catch (IOException e) {
            logger.error(e);
        }
    }

    public static synchronized Account readAccountFromFile(String filename) {
        File file = new File(getAccountsFolderPath(), filename);
        Account account = null;
        try (FileInputStream fileInputStream = new FileInputStream(file);
             ObjectInputStream in = new ObjectInputStream(fileInputStream)) {
            account = (Account) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            logger.error(e);
        }
        return account;
    }

    public static synchronized List<File> getAllFiles() {
        File folder = new File(getAccountsFolderPath());
        return Arrays.asList(Objects.requireNonNull(folder.listFiles()));
    }

    private static synchronized String getAccountsFolderPath() {
        return Thread.currentThread().getContextClassLoader()
                .getResource("accounts")
                .getPath();
    }

    public static void deleteAllFiles() {
        for (File file : getAllFiles()) {
            file.delete();
        }
    }
}
