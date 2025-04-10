import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

class User {
    private String name;
    private LocalDate kycExpiry;

    public User(String name, LocalDate kycExpiry) {
        this.name = name;
        this.kycExpiry = kycExpiry;
    }

    public String getName() {
        return name;
    }

    public LocalDate getKycExpiry() {
        return kycExpiry;
    }
}

public class KYCReminderSystem {

    public static List<User> getUsersWithExpiringKYC(List<User> users) {
        List<User> expiringUsers = new ArrayList<>();
        LocalDate currentDate = LocalDate.now();
        LocalDate expiryThreshold = currentDate.plusDays(7);

        for (User  user : users) {
            if (user.getKycExpiry().isAfter(currentDate) && user.getKycExpiry().isBefore(expiryThreshold)) {
                expiringUsers.add(user);
            }
        }

        return expiringUsers;
    }

    public static void main(String[] args) {
        // Sample users
        List<User> users = new ArrayList<>();
        users.add(new User("Alice", LocalDate.now().plusDays(5)));  // KYC expiring in 5 days
        users.add(new User("Bob", LocalDate.now().plusDays(10)));    // KYC expiring in 10 days
        users.add(new User("Charlie", LocalDate.now().plusDays(3))); // KYC expiring in 3 days
        users.add(new User("David", LocalDate.now().plusDays(8)));   // KYC expiring in 8 days
        users.add(new User("Eve", LocalDate.now().plusDays(1)));     // KYC expiring in 1 day

        // Get users with expiring KYC
        List<User> expiringUsers = getUsersWithExpiringKYC(users);

        // Print the names of users whose KYC is expiring in the next 7 days
        System.out.println("Users whose KYC is expiring in the next 7 days:");
        for (User  user : expiringUsers) {
            System.out.println(user.getName());
        }
    }
}
