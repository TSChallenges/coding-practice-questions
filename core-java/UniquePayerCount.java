import java.util.HashSet;
import java.util.List;
import java.util.Set;

class Transaction {
    private String upiId;
    private double amount;

    public Transaction(String upiId, double amount) {
        this.upiId = upiId;
        this.amount = amount;
    }

    public String getUpiId() {
        return upiId;
    }

    public double getAmount() {
        return amount;
    }
}

public class UniquePayerCount {

    public static int countUniquePayers(List<Transaction> transactions) {
        // Create a set to store unique UPI IDs
        Set<String> uniqueUpiIds = new HashSet<>();

        // Iterate through each transaction
        for (Transaction transaction : transactions) {
            // Add the UPI ID to the set
            uniqueUpiIds.add(transaction.getUpiId());
        }

        // Return the number of unique UPI IDs
        return uniqueUpiIds.size();
    }

    public static void main(String[] args) {
        // Example usage
        List<Transaction> transactions = List.of(
            new Transaction("raj@okicici", 100),
            new Transaction("neha@okhdfc", 200),
            new Transaction("amit@oksbi", 150),
            new Transaction("user1@okaxis", 300),
            new Transaction("neha@okhdfc", 250) // Duplicate UPI ID
        );

        int uniquePayersCount = countUniquePayers(transactions);
        System.out.println("Number of unique payers: " + uniquePayersCount);
    }
}
