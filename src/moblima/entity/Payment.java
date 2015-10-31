package moblima.entity;
/**
 * Represents a payment during movie ticket booking
 * Payment contains the information of transactionID,
 * name, mobile number and email of payer, and
 * amount of this payment
 * @author SSP2 Team 1
 */
public class Payment {
    /**
     * The transactionID of this Payment
     */
    private String transactionID;
    /**
     * The name of the payer
     */
    private String name;
    /**
     * The mobile number of the payer
     */
    private String mobileNumber;
    /**
     * The email of the payer
     */
    private String email;
    /**
     * The amount of this Payment
     */
    private double paymentAmount;
    /**
     * Creates a new Payment with given
     * transactionID of this Payment,
     * name, mobile number and email of payer, and
     * amount of this payment
     */
    public Payment(String transactionID, String name, String mobileNumber, String email, double paymentAmount) {
        this.transactionID = transactionID;
        this.name = name;
        this.mobileNumber = mobileNumber;
        this.email = email;
        this.paymentAmount = paymentAmount;
    }
    /**
     * Gets the transaction ID of this payment
     * @return the transaction ID of this payment
     */
    public String getTransactionID() {
        return transactionID;
    }
    /**
     * Gets the name of the payer
     * @return the name of the payer
     */
    public String getName() {
        return name;
    }
    /**
     * Gets the mobile number of the payer
     * @return the mobile number of the payer
     */
    public String getMobileNumber() {
        return mobileNumber;
    }
    /**
     * Gets the email of the payer
     * @return the email of the payer
     */
    public String getEmail() {
        return email;
    }
    /**
     * Gets the paymentAmount of this payment
     * @return the payment amount of this payment
     */
    public double getPaymentAmount() {
        return paymentAmount;
    }

    public String toString() {
        return "TransactionID: " + transactionID + "\nName: " + name + "\nMobile Number: " + mobileNumber + "\nEmail: " + email + "\nPayment amount: " + paymentAmount + "SGD";
    }
}
