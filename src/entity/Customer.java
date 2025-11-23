package entity;

public class Customer {
    private String customerId;
    private String name;
    private String mobileNumber;
    private String address;
    private String email;
    private String dob;
    private String password;

    public Customer(String customerId, String name, String dob, String mobileNumber, String address, String email, String password) {
        this.customerId = customerId;
        this.name = name;
        this.dob = dob;
        this.mobileNumber = mobileNumber;
        this.address = address;
        this.email = email;
        this.password = password;
    }


    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
