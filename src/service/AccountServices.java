package service;

import dto.AccountInfo;
import entity.*;
import repository.AccountFileRepository;
import repository.AccountRepository;
import repository.CustomerFileRepository;
import repository.CustomerRepository;
import utility.IdProvider;

import java.util.List;

public class AccountServices {
    //AccountRepository accountRepository = new AccountInMemoryRepository();
    AccountRepository accountRepository =new AccountFileRepository();
    CustomerRepository customerRepository=new CustomerFileRepository();

    //Modified
    public void createAccount(Customer newCustomer, AccountType accountType) {
        if(accountType.equals(AccountType.SAVINGS)){
            Account account = new SavingsAccount(newCustomer.getCustomerId(),IdProvider.generateAccountNumber(),0.0);
            accountRepository.saveAccount(account);
        }
        else if(accountType.equals(AccountType.CURRENT)){
            Account account = new CurrentAccount(newCustomer.getCustomerId(),IdProvider.generateAccountNumber(),0.0);
            accountRepository.saveAccount(account);
        }
        else {
            throw new IllegalArgumentException("Unknown account type: "+accountType);
        }
    }

    //Modified
    public boolean deposit(AccountInfo accountInfo, double amount) {
        Account account=accountRepository.getAccountByAccountNumber(accountInfo.getAccountNumber());
        List<Transaction> transactions= account.deposit(amount);
        accountRepository.updateAccount(account);
        accountRepository.addTransaction(account,transactions);

        return true;
    }

    //Modified
    public boolean withdraw(AccountInfo accountInfo, double amount) {
        Account account=accountRepository.getAccountByAccountNumber(accountInfo.getAccountNumber());
        List<Transaction> transactions= account.withdraw(amount);
        accountRepository.updateAccount(account);
        accountRepository.addTransaction(account,transactions);
        return true;
    }

    //Modified
    public boolean fundTransfer(AccountInfo fromAccount,String toAccount , int amount) {
        Account debitAccount=accountRepository.getAccountByAccountNumber(fromAccount.getAccountNumber());
        Account creditAccount=accountRepository.getAccountByAccountNumber(toAccount);
        List<Transaction> outTransactions=debitAccount.transferOut(amount);
        accountRepository.updateAccount(debitAccount);
        accountRepository.addTransaction(debitAccount,outTransactions);
        List<Transaction> inTransactions=creditAccount.transferIn(amount);
        accountRepository.updateAccount(creditAccount);
        accountRepository.addTransaction(creditAccount,inTransactions);
        return true;
    }

    public List<Transaction> getTransaction(AccountInfo accountInfo){
        return accountInfo.getTransaction();
    }


    public AccountInfo getAccountInfo(String customerID){
        Customer customer= customerRepository.getCustomerByCustomerId(customerID);
        Account account= accountRepository.getAccountByCustomer(customer);

        return new AccountInfo(customer.getCustomerId(),
                customer.getName(),customer.getMobileNumber(),
                customer.getAddress(),customer.getEmail(),customer.getDob(),
                account.getAccountNumber(),account.getBalance(),account.getCreatedOn()
                ,account.getAccountType(),account.getTransaction());
    }

}

//Changes made at "Modified" comment
//Modify -> createAccount() - Made use of Account constructor and removed assigning values outside the class
//Modify -> account. replaced - accountRepository.
