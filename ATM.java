package hi;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

class Transaction {
    private double amount;
    private String type;
    private Date timestamp;

    public Transaction(double amount, String type) {
        this.amount = amount;
        this.type = type;
        this.timestamp = new Date();
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "amount=" + amount +
                ", type='" + type + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}

class User {
    private String userId;
    private String pin;
    private ArrayList<Transaction> transactionHistory;

    public User(String userId, String pin) {
        this.userId = userId;
        this.pin = pin;
        this.transactionHistory = new ArrayList<>();
    }

    public ArrayList<Transaction> getTransactionHistory() {
        return transactionHistory;
    }

    public void addTransaction(Transaction transaction) {
        transactionHistory.add(transaction);
    }

    public boolean validatePin(String inputPin) {
        return pin.equals(inputPin);
    }
}

public class ATM {
    private User currentUser;

    public ATM(User user) {
        this.currentUser = user;
    }

    public void showMenu() {
        System.out.println("ATM Menu:");
        System.out.println("1. Transaction History");
        System.out.println("2. Withdraw");
        System.out.println("3. Deposit");
        System.out.println("4. Transfer");
        System.out.println("5. Quit");
    }

    public void processMenuInput(int choice, Scanner scanner) {
        switch (choice) {
            case 1:
                displayTransactionHistory();
                break;
            case 2:
                withdraw(scanner);
                break;
            case 3:
                deposit(scanner);
                break;
            case 4:
                transfer(scanner);
                break;
            case 5:
                System.out.println("Exiting ATM. Goodbye!");
                System.exit(0);
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    private void displayTransactionHistory() {
        ArrayList<Transaction> transactions = currentUser.getTransactionHistory();
        System.out.println("Transaction History:");
        for (Transaction transaction : transactions) {
            System.out.println(transaction);
        }
    }

    private void withdraw(Scanner scanner) {
        System.out.print("Enter amount to withdraw: ");
        double amount = scanner.nextDouble();
        if (amount <= 0) {
            System.out.println("Invalid amount.");
            return;
        }
        currentUser.addTransaction(new Transaction(-amount, "Withdraw"));
        System.out.println("$" + amount + " withdrawn successfully.");
    }

    private void deposit(Scanner scanner) {
        System.out.print("Enter amount to deposit: ");
        double amount = scanner.nextDouble();
        if (amount <= 0) {
            System.out.println("Invalid amount.");
            return;
        }
        currentUser.addTransaction(new Transaction(amount, "Deposit"));
        System.out.println("$" + amount + " deposited successfully.");
    }

    private void transfer(Scanner scanner) {
        System.out.print("Enter recipient's user ID: ");
        String recipientId = scanner.next();
        // You can implement logic to find recipient's account based on ID
        System.out.print("Enter amount to transfer: ");
        double amount = scanner.nextDouble();
        if (amount <= 0) {
            System.out.println("Invalid amount.");
            return;
        }
        currentUser.addTransaction(new Transaction(-amount, "Transfer to " + recipientId));
        System.out.println("$" + amount + " transferred to user " + recipientId + " successfully.");
    }

    public static void main(String[] args) {
        User user = new User("12345", "6789");
        user.addTransaction(new Transaction(100.0, "Initial deposit"));

        ATM atm = new ATM(user);
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the ATM.");
        System.out.print("Enter user ID: ");
        String userId = scanner.next();
        System.out.print("Enter PIN: ");
        String pin = scanner.next();

        if (user.validatePin(pin)) {
            System.out.println("Login successful.");
            while (true) {
                atm.showMenu();
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();
                atm.processMenuInput(choice, scanner);
            }
        } else {
            System.out.println("Invalid credentials. Exiting.");
        }
    }
}
