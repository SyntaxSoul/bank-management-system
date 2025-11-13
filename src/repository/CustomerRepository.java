package repository;

import entity.Customer;

import java.util.HashMap;
import java.util.Map;

public class CustomerRepository {

    private static Map<String, Customer> customers = new HashMap<>();

    public void saveCustomer(Customer customer) {
        CustomerRepository.customers.put(customer.getCustomerId(), customer);
    }

    public Customer getCustomerByCustomerId(String customerId) {
        return CustomerRepository.customers.get(customerId);
    }
}
