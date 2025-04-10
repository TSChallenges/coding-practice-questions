import java.util.*;
import java.util.stream.Collectors;

class Transaction {
    private String userId;
    private double amount;
    private Date date;

    public Transaction(String userId, double amount, Date date) {
        this.userId = userId;
        this.amount = amount;
        this.date = date;
    }

    public String getUserId() {
        return userId;
    }

    public double getAmount() {
        return amount;
    }

    public Date getDate() {
        return date;
    }
}

public class CashbackEligibility {

    public static List<String> getEligibleUsers(List<Transaction> transactions) {
        // Map to hold user transactions grouped by month
        Map<String, Map<String, List<Transaction>>> userTransactions = new HashMap<>();

        // Group transactions by user and month
        for (Transaction transaction : transactions) {
            String userId = transaction.getUserId();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(transaction.getDate());
            String monthYear = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1); // Format: YYYY-MM

            userTransactions
                .computeIfAbsent(userId, k -> new HashMap<>())
                .computeIfAbsent(monthYear, k -> new ArrayList<>())
                .add(transaction);
        }

        List<String> eligibleUsers = new ArrayList<>();

        // Check eligibility for each user
        for (Map.Entry<String, Map<String, List<Transaction>>> userEntry : userTransactions.entrySet()) {
            String userId = userEntry.getKey();
            for (Map.Entry<String, List<Transaction>> monthEntry : userEntry.getValue().entrySet()) {
                List<Transaction> userMonthTransactions = monthEntry.getValue();
                if (userMonthTransactions.size() >= 5) {
                    double totalSpending = userMonthTransactions.stream()
                            .mapToDouble(Transaction::getAmount)
                            .sum();
                    if (totalSpending > 1000) {
                        eligibleUsers.add(userId);
                        break; // No need to check other months for this user
                    }
                }
            }
        }

        return eligibleUsers;
    }

    public static void main(String[] args) {
        // Sample transactions
        List<Transaction> transactions = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();

        // User 1 transactions
        calendar.set(2023, Calendar.OCTOBER, 1);
        transactions.add(new Transaction("user1", 300, calendar.getTime()));
        calendar.set(2023, Calendar.OCTOBER, 5);
        transactions.add(new Transaction("user1", 400, calendar.getTime()));
        calendar.set(2023, Calendar.OCTOBER, 10);
        transactions.add(new Transaction("user1", 350, calendar.getTime()));
        calendar.set(2023, Calendar.OCTOBER, 15);
        transactions.add(new Transaction("user1", 200, calendar.getTime()));
        calendar.set(2023, Calendar.OCTOBER, 20);
        transactions.add(new Transaction("user1", 100, calendar.getTime()));
        calendar.set(2023, Calendar.OCTOBER, 25);
        transactions.add(new Transaction("user1", 150, calendar.getTime())); // Total = 1600, eligible

        // User 2 transactions
        calendar.set(2023, Calendar.OCTOBER, 1);
        transactions.add(new Transaction("user2", 200, calendar.getTime()));
        calendar.set(2023, Calendar.OCTOBER, 5);
        transactions.add(new Transaction("user2", 300, calendar.getTime()));
        calendar.set(2023, Calendar.OCTOBER, 10);
        transactions.add(new Transaction("user2", 150, calendar.getTime()));
        calendar.set(2023, Calendar.OCTOBER, 15);
        transactions.add(new Transaction("user2", 100, calendar.getTime()));
        calendar.set(2023, Calendar.OCTOBER, 20);
        transactions.add(new Transaction("user2", 50, calendar.getTime())); // Total = 800, not eligible

        // User 3 transactions
        calendar.set(2023, Calendar.OCTOBER, 1);
        transactions.add(new Transaction("user3", 500, calendar.getTime()));
        calendar.set(2023, Calendar.OCTOBER, 5);
        transactions.add(new Transaction("user3", 600, calendar.getTime()));
        calendar.set(2023, Calendar.OCTOBER, 10);
        transactions.add(new Transaction("user3", 200, calendar.getTime()));
        calendar.set(2023, Calendar.OCTOBER, 15);
        transactions.add(new Transaction("user3", 300, calendar.getTime()));
        calendar.set(2023, Calendar.OCTOBER, 20);
        transactions.add(new Transaction("user3", 150, calendar.getTime())); // Total = 1800, eligible

        // User 4 transactions (not eligible)
        calendar.set(2023, Calendar.OCTOBER, 1);
        transactions.add(new Transaction("user4", 100, calendar.getTime()));
        calendar.set(2023, Calendar.OCTOBER, 5);
        transactions.add(new Transaction("user4", 200, calendar.getTime()));
        calendar.set(2023, Calendar.OCTOBER, 10);
        transactions.add(new Transaction("user4", 150, calendar.getTime()));
        calendar.set(2023, Calendar.OCTOBER, 15);
        transactions.add(new Transaction("user4", 50, calendar.getTime()));
        calendar.set(2023, Calendar.OCTOBER, 20);
        transactions.add(new Transaction("user4", 100, calendar.getTime())); // Total = 600, not eligible

        // Get eligible users
        List<String> eligibleUsers = getEligibleUsers(transactions);

        // Print eligible users
        System.out.println("Eligible users for cashback: " + eligibleUsers);
    }
}