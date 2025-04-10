import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

class Transaction {
    double amount;
    long timestamp; // in seconds

    public Transaction(double amount, long timestamp) {
        this.amount = amount;
        this.timestamp = timestamp;
    }
}

public class FraudDetector {

    public static boolean isFraudulent(List<Transaction> transactions) {
        // Map to store amounts and their corresponding timestamps
        HashMap<Double, List<Long>> amountToTimestamps = new HashMap<>();

        for (Transaction transaction : transactions) {
            // Get the list of timestamps for the current amount
            List<Long> timestamps = amountToTimestamps.getOrDefault(transaction.amount, new ArrayList<>());

            // Add the current timestamp to the list
            timestamps.add(transaction.timestamp);

            // Remove timestamps that are older than 10 seconds from the current timestamp
            timestamps.removeIf(ts -> ts < transaction.timestamp - 10);

            // Update the map with the filtered timestamps
            amountToTimestamps.put(transaction.amount, timestamps);

            // Check if there are 3 or more timestamps within the last 10 seconds
            if (timestamps.size() >= 3) {
                return true; // Fraud detected
            }
        }

        return false; // No fraud detected
    }

    public static void main(String[] args) {
        // Example transactions
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction(100.0, 1));
        transactions.add(new Transaction(100.0, 2));
        transactions.add(new Transaction(100.0, 3));
        transactions.add(new Transaction(100.0, 12)); // This one is outside the 10 seconds window

        // Check for fraud
        boolean isFraud = isFraudulent(transactions);
        System.out.println("Fraud detected: " + isFraud); // Should print true

        // Another example with no fraud
        transactions.clear();
        transactions.add(new Transaction(100.0, 1));
        transactions.add(new Transaction(100.0, 2));
        transactions.add(new Transaction(100.0, 15)); // This one is outside the 10 seconds window

        // Check for fraud
        isFraud = isFraudulent(transactions);
        System.out.println("Fraud detected: " + isFraud); // Should print false
    }
}
