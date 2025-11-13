package entity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;

public class Transaction {
    private String id;
    private String date;
    private String time;
    private int amount;
    private String comment;
    TransactionType transactionType;

    public enum TransactionType {
        Cr,
        Dr
    }

    // String comment;

    public Transaction(int amount, TransactionType transactionType) {
        this.id = IdProvider.generateTransactionId();
        this.amount = amount;
        this.transactionType = transactionType;
        setDate();
        setTime();
    }

    public Transaction(int amount, TransactionType transactionType, String comment) {
        this.id = IdProvider.generateTransactionId();
        this.amount = amount;
        this.transactionType = transactionType;
        setDate();
        setTime();
        this.comment = comment;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setDate() {
        this.date = LocalDate.now(ZoneId.of("Asia/Kolkata")).toString();
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setTime() {
        this.time = LocalTime.now(ZoneId.of("Asia/Kolkata")).toString();
    }

    @Override
    public String toString() {
        return "{\n" + this.id + "\n" + this.date + "\n" + this.time + "\n" + this.amount + "\n" + this.transactionType + "\n" + this.comment + "\n}";
    }
}
