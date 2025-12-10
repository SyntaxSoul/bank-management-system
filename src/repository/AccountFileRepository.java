package repository;

import entity.*;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class AccountFileRepository implements AccountRepository {

    //Removed absolute path as it makes code system dependent.
    private final String accountFilePath = "data\\accounts.csv";
    private final String transactionFilePath = "data\\transactions\\";
    private final File accountFile = new File(accountFilePath);

    private List<Account> readAllAccounts() {
        List<Account> accountArrayList = new ArrayList<>();
        if (!accountFile.exists()) {
            return null;
        }
        try (
                BufferedReader br = new BufferedReader(new FileReader(accountFile))) {
            String currentLine;
            List<Transaction> transactions;
            boolean firstLine = true;
            while ((currentLine = br.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue;
                }
                String[] accountSplit = currentLine.split(",");
                String customerId = accountSplit[0];
                String accountNumber = accountSplit[1];
                AccountType accountType = AccountType.valueOf(accountSplit[2]);
                double balance = Double.parseDouble(accountSplit[3]);
                LocalDateTime createdOn = LocalDateTime.parse(accountSplit[4]);
                transactions = readTransaction(transactionFilePath + accountNumber + ".csv");

                if (accountType.equals(AccountType.SAVINGS)) {
                    accountArrayList.add(new SavingsAccount(customerId, accountNumber, balance, createdOn, transactions));
                } else if (accountType.equals(AccountType.CURRENT)) {
                    accountArrayList.add(new CurrentAccount(customerId, accountNumber, balance, createdOn, transactions));
                } else {
                    throw new IllegalArgumentException("Unknown account type : " + accountType);
                }
            }
            return accountArrayList;
        } catch (
                Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    private void writeAllAccounts(List<Account> accounts) {

        accountFile.getParentFile().mkdirs();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(accountFile))) {
            bw.write("customer_id,account_number,account_type,balance,created_date");
            for (Account account : accounts) {
                bw.newLine();
                bw.write(account.getCustomerId() + "," + account.getAccountNumber() + "," + account.getAccountType() + "," + account.getBalance() + "," + account.getCreatedOn());
            }

        } catch (Exception e) {
            System.out.println("Error writing account: " + e.getMessage());
        }
    }

    private void writeAccount(Account account) {
        accountFile.getParentFile().mkdirs();
        boolean newFile = !accountFile.exists() || accountFile.length() == 0;
        System.out.println("Executing");
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(accountFile, true))) {
            if (newFile) {
                bw.write("customer_id,account_number,account_type,balance,created_date");
                bw.newLine();
            }
            bw.write(account.getCustomerId() + "," + account.getAccountNumber() + "," + account.getAccountType() + "," + account.getBalance() + "," + account.getCreatedOn());
            bw.newLine();
        } catch (Exception e) {
            System.out.println("Error writing account: " + e.getMessage());
        }
    }

    private Account findAccountByAccountNumber(String accountNumber) {
        List<Account> accountsArrayList = readAllAccounts();
        if (accountsArrayList != null) {
            for (Account account : accountsArrayList) {
                if (accountNumber.equals(account.getAccountNumber())) {
                    return account;
                }
            }
        }
        return null;
    }

    private Account findAccountByCustomerId(String customerId) {
        List<Account> accountsArrayList = readAllAccounts();
        if (accountsArrayList != null) {
            for (Account account : accountsArrayList) {
                if (customerId.equals(account.getCustomerId())) {
                    return account;
                }
            }
        }
        return null;
    }

    private void writeTransaction(String filePath, Transaction transaction) {
        File transactionFile = new File(filePath);
        transactionFile.getParentFile().mkdirs();
        boolean newFile = !transactionFile.exists() || transactionFile.length() == 0;
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(transactionFile, true))) {
            if (newFile) {
                bw.write("transaction_id,date,transaction_type,amount,balance_after,comment");
                bw.newLine();
            }
            bw.write(transaction.getId() + ","
                    + transaction.getDate() + ","
                    + transaction.getTransactionType() + ","
                    + transaction.getAmount() + ","
                    + transaction.getBalanceAfter() + ","
                    + transaction.getComment());
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Error writing file :" + e.getMessage());
        }
    }

    private List<Transaction> readTransaction(String filePath) {
        File transactionFile = new File(filePath);
        List<Transaction> transactions = new ArrayList<>();
        boolean firstLine = true;
        if (!transactionFile.exists()) {
            return transactions;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(transactionFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue;
                }
                String[] transactionSplit = line.split(",");
                String transactionId = transactionSplit[0];
                LocalDateTime date = LocalDateTime.parse(transactionSplit[1]);
                Transaction.TransactionType type = Transaction.TransactionType.valueOf(transactionSplit[2]);
                double amount = Double.parseDouble(transactionSplit[3]);
                double balanceAfter = Double.parseDouble(transactionSplit[4]);
                String comment = transactionSplit[5];
                transactions.add(new Transaction(transactionId, date, type, amount, balanceAfter, comment));
            }
            return transactions;
        } catch (IOException e) {
            System.out.println("Error reading transactions :" + e.getMessage());
        }
        return transactions;
    }

    @Override
    public void saveAccount(Account account) {
        writeAccount(account);
    }

    @Override
    public void updateAccount(Account updateAccount) {
        List<Account> accountArrayList = readAllAccounts();
        for (Account account : accountArrayList) {
            if (account.getAccountNumber().equals(updateAccount.getAccountNumber())) {
                accountArrayList.remove(account);
                accountArrayList.add(updateAccount);
                break;
            }
        }
        writeAllAccounts(accountArrayList);
    }

    @Override
    public Account getAccountByCustomer(Customer customer) {
        return findAccountByCustomerId(customer.getCustomerId());
    }

    @Override
    public Account getAccountByAccountNumber(String accountNumber) {
        return findAccountByAccountNumber(accountNumber);
    }

    @Override
    public void addTransaction(Account account, List<Transaction> transactions) {
        String transactionFile = transactionFilePath + account.getAccountNumber() + ".csv";
        for (Transaction transaction : transactions) {
            account.addTransaction(transaction);
            writeTransaction(transactionFile, transaction);
        }

    }
}