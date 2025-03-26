import java.util.ArrayList;
import java.util.List;

public class CustomerService {
    private static final List<Customer> customersList = new ArrayList<>();

    public void addCustomer(Customer customer) {
        customersList.add(customer);
        System.out.println("Customer added successfully!");
    }

    public void showCustomers() {
        for (Customer customer : customersList) {
            System.out.println(customer);
        }
    }

    public void findCustomer(String customerID) {
        for (Customer customer : customersList) {
            if (customer.getUserID().equals(customerID)) {
                System.out.println("Found: " + customer);
                return;
            }
        }
        System.out.println("Customer not found.");
    }

    public void modifyCustomer(String customerID) {
        for (Customer customer : customersList) {
            if (customer.getUserID().equals(customerID)) {
                System.out.print("Enter new name: ");
                customer.setName(System.console().readLine());
                System.out.println("Customer updated!");
                return;
            }
        }
        System.out.println("Customer not found.");
    }

    public void removeCustomer(String customerID) {
        customersList.removeIf(customer -> customer.getUserID().equals(customerID));
        System.out.println("Customer removed!");
    }

    public static List<Customer> getAllCustomers() {
        return customersList;
    }
}
