import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class UpiValidator {

    // Predefined list of valid bank handles
    private static final Set<String> VALID_BANK_HANDLES = new HashSet<>(Arrays.asList("okhdfc", "okicici", "oksbi", "okaxis"));

    public static boolean isValidUpi(String upiId) {
        // Check if the UPI ID is null or empty
        if (upiId == null || upiId.isEmpty()) {
            return false;
        }

        // Split the UPI ID by '@'
        String[] parts = upiId.split("@");
        
        // Check if there is exactly one '@' and if the name part is valid
        if (parts.length != 2 || !isValidName(parts[0])) {
            return false;
        }

        // Check if the bank handle is valid
        String bankHandle = parts[1];
        return VALID_BANK_HANDLES.contains(bankHandle);
    }

    private static boolean isValidName(String name) {
        // Check if the name is alphanumeric and its length is between 3 and 30 characters
        return name.matches("^[a-zA-Z0-9]{3,30}$");
    }

    public static void main(String[] args) {
        // Test cases
        System.out.println(isValidUpi("raj@okicici")); // true
        System.out.println(isValidUpi("john@okhdfc")); // true
        System.out.println(isValidUpi("a@okaxis")); // false (name too short)
        System.out.println(isValidUpi("username@invalidbank")); // false (invalid bank handle)
        System.out.println(isValidUpi("user@okhdfc123")); // false (invalid name)
        System.out.println(isValidUpi("user@okhdfc")); // true
        System.out.println(isValidUpi("user@okicici")); // true
        System.out.println(isValidUpi("user@oksbi")); // true
        System.out.println(isValidUpi("user@okaxis")); // true
        System.out.println(isValidUpi("user@okhdfc!")); // false (invalid character)
    }
}
