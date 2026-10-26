interface Auditable {
    String getAuditSummary();
}

abstract class BaseAccount implements Auditable {
    protected String accountNumber;
    protected double balance;

    public BaseAccount(String accountNumber, double balance) {
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            this.balance += amount;
        }
    }

    public abstract boolean withdraw(double amount);

    @Override
    public String getAuditSummary() {
        return "Account Number: " + this.accountNumber + " | Balance: " + this.balance;
    }
}

class SavingAccount extends BaseAccount {

    public SavingAccount(String accountNumber, double balance) {
        super(accountNumber, balance); // Explicitly invoke parent constructor
    }

    @Override
    public boolean withdraw(double amount) {
        if (canWithdraw(amount)) {
            this.balance -= amount;
            return true;
        }
        return false;
    }

    private boolean canWithdraw(double amount) {
        if (amount <= 0) {
            System.out.println("Withdrawal amount must be greater than 0");
            return false;
        }
        // Rule: remaining balance after withdrawal must be >= 500
        if ((this.balance - amount) < 500) {
            System.out.println("Insufficient funds: Minimum balance of 500 required");
            return false;
        }
        return true;
    }
}

public class Exercise2 {
    public static void main(String[] args) {
        SavingAccount acc = new SavingAccount("ACC-98765", 1200.0);

        System.out.println("Initial: " + acc.getAuditSummary());

        // Attempt valid withdrawal
        boolean success1 = acc.withdraw(300.0);
        System.out.println("Withdraw $300: " + (success1 ? "SUCCESS" : "FAILED"));
        System.out.println("Current: " + acc.getAuditSummary());

        // Attempt invalid withdrawal (leaves balance < 500)
        boolean success2 = acc.withdraw(600.0);
        System.out.println("Withdraw $600: " + (success2 ? "SUCCESS" : "FAILED"));
        System.out.println("Current: " + acc.getAuditSummary());
    }
}
