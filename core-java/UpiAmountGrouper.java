import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UpiAmountGrouper {

    // Method to group total amounts by UPI handle
    public static Map<String, Double> groupAmountsByUpiHandle(List<UpiTransaction> transactions) {
        Map<String, Double> upiHandleToTotalAmount = new HashMap<>();

        for (UpiTransaction transaction : transactions) {
            // Extract the UPI handle from the UPI ID
            String upiHandle = transaction.getUpiId().split("@")[1];

            // Update the total amount for the UPI handle
            upiHandleToTotalAmount.put(upiHandle, upiHandleToTotalAmount.getOrDefault(upiHandle, 0.0) + transaction.getAmount());
        }

        return upiHandleToTotalAmount;
    }

    public static void main(String[] args) {
        // Example transactions
        List<UpiTransaction> transactions = List.of(
            new UpiTransaction("raj@oksbi", 100),
            new UpiTransaction("neha@okhdfc", 250),
            new UpiTransaction("amit@oksbi", 150)
        );

        // Group amounts by UPI handle
        Map<String, Double> result = groupAmountsByUpiHandle(transactions);

        // Print the result
        System.out.println(result);
    }
}

// Class to represent a UPI transaction
class UpiTransaction {
    private String upiId;
    private double amount;

    public UpiTransaction(String upiId, double amount) {
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
