
class InsufficientFundsException extends Exception {
    public InsufficientFundsException(String message) {
        super(message);
    }
}

public class UserWallet {
    private double balance;

    public UserWallet() {
        this.balance = 0.0; // Initialize balance to 0
    }

    // Method to credit the wallet
    public void credit(double amount) {
        if (amount > 0) {
            balance += amount; // Increase balance
        } else {
            throw new IllegalArgumentException("Credit amount must be positive.");
        }
    }

    // Method to debit the wallet
    public void debit(double amount) throws InsufficientFundsException {
        if (amount <= 0) {
            throw new IllegalArgumentException("Debit amount must be positive.");
        }
        if (balance < amount) {
            throw new InsufficientFundsException("Insufficient funds for this debit operation.");
        }
        balance -= amount; // Decrease balance
    }

    // Method to get the current balance
    public double getBalance() {
        return balance;
    }

    public static void main(String[] args) {
        try {
            UserWallet wallet = new UserWallet();
            wallet.credit(100.0);
            System.out.println("Current Balance: " + wallet.getBalance()); // Should print 100.0

            wallet.debit(30.0);
            System.out.println("Current Balance after debit: " + wallet.getBalance()); // Should print 70.0

            wallet.debit(80.0); // This should throw InsufficientFundsException
        } catch (InsufficientFundsException e) {
            System.out.println(e.getMessage()); // Handle insufficient funds
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage()); // Handle invalid arguments
        }
    }
}