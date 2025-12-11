package repository;

import entity.Customer;

public interface CustomerRepository {
    void saveCustomer(Customer customer);

    Customer getCustomerByCustomerId(String customerId);
}
