package com.epam.concurrency.dao;

import com.epam.concurrency.model.Account;
import com.epam.concurrency.utils.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AccountDao {
    public void save(Account account) {
        FileUtils.writeAccountToFile(account, account.getId().toString());
    }

    public Account get(Long id) {
        return FileUtils.readAccountFromFile(id.toString());
    }

    public List<Account> getAll() {
        List<Account> accounts = new ArrayList<>();
        for (File file : FileUtils.getAllFiles()) {
            accounts.add(FileUtils.readAccountFromFile(file.getName()));
        }
        return accounts;
    }
}
