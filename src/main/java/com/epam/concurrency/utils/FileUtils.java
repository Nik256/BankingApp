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
    private static final String FOLDER_PATH = "src/main/resources/accounts";

    public static void writeAccountToFile(Account account, String filename) {
        File file = new File(FOLDER_PATH, filename);
        try (FileOutputStream fileOutputStream = new FileOutputStream(file);
             ObjectOutputStream out = new ObjectOutputStream(fileOutputStream)) {
            out.writeObject(account);
        } catch (IOException e) {
            logger.error(e);
        }
    }

    public static Account readAccountFromFile(String filename) {
        File file = new File(FOLDER_PATH, filename);
        Account account = null;
        try (FileInputStream fileInputStream = new FileInputStream(file);
             ObjectInputStream in = new ObjectInputStream(fileInputStream)) {
            account = (Account) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            logger.error(e);
        }
        return account;
    }

    public static List<File> getAllFiles() {
        File folder = new File(FOLDER_PATH);
        return Arrays.asList(Objects.requireNonNull(folder.listFiles()));
    }

    public static void deleteAllFiles() {
        for (File file : getAllFiles()) {
            file.delete();
        }
    }
}
