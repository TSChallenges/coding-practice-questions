import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class TransactionIdGenerator {

    // Method to generate a unique transaction reference ID
    public static String generateTransactionId() {
        // Get the current timestamp in the format YYYYMMDD
        String timestamp = new SimpleDateFormat("yyyyMMdd").format(new Date());

        // Generate a random 4-digit number
        int random4Digit = new Random().nextInt(10000); // Generates a number between 0 and 9999

        // Format the random number to ensure it is 4 digits (e.g., 837 becomes 0837)
        String formattedRandom = String.format("%04d", random4Digit);

        // Construct the transaction ID
        return "TXN_" + timestamp + "_" + formattedRandom;
    }

    public static void main(String[] args) {
        // Generate and print a unique transaction reference ID
        String transactionId = generateTransactionId();
        System.out.println("Generated Transaction ID: " + transactionId);
    }
}
