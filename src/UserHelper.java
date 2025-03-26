import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class UserHelper {
    private static final Map<String, String> adminAccounts = new HashMap<>();
    private final Scanner scanner = new Scanner(System.in);

    public UserHelper() {
        // Default admin credentials
        adminAccounts.put("root", "root");
    }

    public int authenticateAdmin(String username, String password) {
        return isAdminExists(username, password) ? 1 : -1;
    }

    public void registerAdmin() {
        System.out.print("\nEnter Username to Register: ");
        String username = scanner.nextLine();
        System.out.print("Enter Password to Register: ");
        String password = scanner.nextLine();

        if (adminAccounts.containsKey(username)) {
            System.out.println("ERROR! Admin username already exists. Try again.");
            return;
        }

        adminAccounts.put(username, password);
        System.out.println("Admin registered successfully!");
    }

    public boolean isAdminExists(String username, String password) {
        return adminAccounts.containsKey(username) && adminAccounts.get(username).equals(password);
    }
}
