package service;

import entity.*;
import repository.AccountRepository;

public class AccountServices {
    AccountRepository accountRepository = new AccountRepository();
    Account account = new Account();

    public void createAccount(Customer newCustomer, AccountType accountType) {
        account.setAccountType(accountType);
        account.setBalance(0.0f);
        account.setAccountNumber(IdProvider.generateAccountNumber());
        accountRepository.saveAccount(newCustomer, account);
    }

    public Float balance(Account account) {
        return account.getBalance();
    }

    public void deposit(Account account, int amount) {
        int charges = calculateCharges(amount, account.getAccountType());
        Transaction trDeposit = new Transaction(amount, Transaction.TransactionType.Cr);
        account.addTransaction(trDeposit);
        Transaction trCharges = new Transaction(charges, Transaction.TransactionType.Dr, "Transaction Charges");
        account.addTransaction(trCharges);
        account.setBalance(account.getBalance() + (amount - charges));

    }

    public boolean withdraw(Account account, int amount) {
        float balance = account.getBalance();
        if (amount <= balance) {
            int charges = calculateCharges(amount, account.getAccountType());
            Transaction trWithdraw = new Transaction(amount, Transaction.TransactionType.Dr);
            account.addTransaction(trWithdraw);
            Transaction trCharges = new Transaction(charges, Transaction.TransactionType.Dr, "Transaction Charges");
            account.addTransaction(trCharges);
            account.setBalance(balance - (amount + charges));
            return true;
        }
        return false;
    }

    public boolean transfer(Account debitAccount, Account creditAccount, int amount) {
        float balance = debitAccount.getBalance();
        if (amount <= balance) {
            {
                int charges = calculateCharges(amount, debitAccount.getAccountType());
                Transaction trTransfer = new Transaction(amount, Transaction.TransactionType.Dr, "To A/C: " + creditAccount.getAccountNumber());
                debitAccount.addTransaction(trTransfer);
                Transaction trCharges = new Transaction(charges, Transaction.TransactionType.Dr, "Transaction Charges");
                debitAccount.addTransaction(trCharges);
                debitAccount.setBalance(debitAccount.getBalance() - (amount + charges));
            }
            {
                Transaction trTransfer = new Transaction(amount, Transaction.TransactionType.Cr, "From A/C: " + debitAccount.getAccountNumber());
                creditAccount.addTransaction(trTransfer);
                creditAccount.setBalance(creditAccount.getBalance() + amount);
            }
            return true;
        }
        return false;

    }

    public int calculateCharges(int amount, AccountType accountType) {
        int charges = 0;
        if (accountType.equals(AccountType.SAVINGS)) {
            charges = amount * 1 / 100;
        } else if (accountType.equals(AccountType.CURRENT)) {
            charges = amount * 2 / 100;
        }
        return charges;
    }

}
