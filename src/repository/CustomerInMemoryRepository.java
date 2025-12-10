package repository;

import entity.Customer;

import java.util.HashMap;
import java.util.Map;

public class CustomerInMemoryRepository implements CustomerRepository{

    private static Map<String, Customer> customers = new HashMap<>();

    @Override
    public void saveCustomer(Customer customer) {
        CustomerInMemoryRepository.customers.put(customer.getCustomerId(), customer);
    }

    @Override
    public Customer getCustomerByCustomerId(String customerId) {
        return CustomerInMemoryRepository.customers.get(customerId);
    }
}

