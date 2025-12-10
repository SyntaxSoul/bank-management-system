package repository;

import entity.Customer;

import java.io.*;

public class CustomerFileRepository implements CustomerRepository {
    //Removed absolute path as it makes code machine dependent.
    private final String customerFilePath = "data\\customer.csv";
    File customersFile = new File( customerFilePath);

    public void writeCustomer(Customer customer) {
        customersFile.getParentFile().mkdirs();
        boolean newFile = customersFile.exists() || customersFile.length() == 0;
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(customersFile,true))) {
            if (newFile) {
                bw.write("customer_id,name,mobile_number,address,email,dob,password");
                bw.newLine();
            }
            String customerId = customer.getCustomerId();
            String name = customer.getName();
            String mobileNumber = customer.getMobileNumber();
            String address = customer.getAddress();
            String email = customer.getEmail();
            String dob = customer.getDob();
            String password = customer.getPassword();
            bw.write(customerId + "," + name + "," + mobileNumber + "," + address + "," + email + "," + dob + "," + password);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public Customer readCustomer(String customerId) {
        if (!customersFile.exists()) {
            return null;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(customersFile))) {
            String currentLine;
            boolean firstLine = true;
            while ((currentLine = br.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue;
                }
                String[] customerDetails = currentLine.split(",");
                if (customerDetails[0].equals(customerId)) {
                    String name = customerDetails[1];
                    String mobileNumber = customerDetails[2];
                    String address = customerDetails[3];
                    String email = customerDetails[4];
                    String dob = customerDetails[5];
                    String password = customerDetails[6];
                    return new Customer(customerId, name, mobileNumber, address, email, dob, password);
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return null;

    }

    @Override
    public void saveCustomer(Customer customer) {
        writeCustomer(customer);
    }

    @Override
    public Customer getCustomerByCustomerId(String customerId) {
        return readCustomer(customerId);
    }
}