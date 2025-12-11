package entity;

import java.time.LocalDateTime;
import java.util.List;

public class CurrentAccount extends Account {
    public CurrentAccount(String customerId, String accountNumber, Double initialBalance) {
        super(customerId, accountNumber, initialBalance, AccountType.CURRENT);
    }

    //Modified
    public CurrentAccount(String customerId, String accountNumber, Double balance, LocalDateTime createdOn, List<Transaction> transaction) {
        super(customerId, accountNumber, balance, createdOn, AccountType.CURRENT, transaction);
    }

    private final double charges = 0.02;

    protected double calculateCharges(double amount) {
        return amount * charges;
    }

}
