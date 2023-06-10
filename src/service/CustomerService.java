package service;

import model.customer.Customer;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CustomerService {
    // The Singleton pattern ensures that only one instance of the class is created
    // and provides a global and shared access point to that instance.
    private static final CustomerService instance = new CustomerService();
    private final Map<String, Customer> customers = new HashMap<>();

    private CustomerService() {
        // Private constructor to prevent instantiation outside the class
    }

    public static CustomerService getInstance() {
        // It provides a global point of access to the instance,
        // allowing other parts of the application to use the singleton object.
        return instance;
    }

    public void addCustomer(String firstName, String lastName, String email) {
        Customer customer = new Customer(firstName, lastName, email);
        customers.put(email, customer);
    }

    public Customer getCustomer(String customerEmail) {
        return customers.get(customerEmail);
    }

    public Collection<Customer> getAllCustomers() {
        return customers.values();
    }
}
// Here's an example of how to use the Singleton class:
//CustomerService customerService = CustomerService.getInstance();