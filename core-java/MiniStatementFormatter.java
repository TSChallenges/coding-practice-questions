import java.util.*;
import java.time.LocalDate;

public class MiniStatementFormatter {

    // Enum for clarity
    enum TransactionType {
        CREDIT, DEBIT
    }

    // Transaction class
    static class Transaction {
        LocalDate date;
        double amount;
        TransactionType type;

        public Transaction(LocalDate date, double amount, TransactionType type) {
            this.date = date;
            this.amount = amount;
            this.type = type;
        }
    }

    // Method to print the last 5 transactions in a table format
    public static void printMiniStatement(List<Transaction> transactions) {
        System.out.printf("%-11s| %-7s| %-6s%n", "Date", "Amount", "Type");
        System.out.println("------------|--------|--------");

        int start = Math.max(0, transactions.size() - 5);
        List<Transaction> lastFive = transactions.subList(start, transactions.size());

        for (Transaction tx : lastFive) {
            System.out.printf("%-11s| %-7.2f| %-6s%n",
                    tx.date.toString(), tx.amount, tx.type);
        }
    }

    // Demo
    public static void main(String[] args) {
        List<Transaction> txList = Arrays.asList(
            new Transaction(LocalDate.of(2025, 4, 1), 500.0, TransactionType.CREDIT),
            new Transaction(LocalDate.of(2025, 4, 2), 200.0, TransactionType.DEBIT),
            new Transaction(LocalDate.of(2025, 4, 3), 300.0, TransactionType.CREDIT),
            new Transaction(LocalDate.of(2025, 4, 4), 100.0, TransactionType.DEBIT),
            new Transaction(LocalDate.of(2025, 4, 5), 150.0, TransactionType.CREDIT),
            new Transaction(LocalDate.of(2025, 4, 6), 75.0, TransactionType.DEBIT)
        );

        printMiniStatement(txList);
    }
}
