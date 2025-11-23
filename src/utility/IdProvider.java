package utility;

public class IdProvider {
    static Long customerId = 100000L;
    static Long accountNumber = 100_000_000L;
    static Integer transactionId = 1;


    public static String generateCustomerId() {
        return (customerId++).toString();
    }

    public static String generateAccountNumber() {
        return (accountNumber++).toString();
    }

    public static String generateTransactionId() {
        return (transactionId++).toString();
    }
}
