public class Payment {
    private String transactionID;
    private String name;
    private String mobileNumber;
    private String email;
    private double paymentAmount;

    public Payment(String transactionID, String name, String mobileNumber, String email, double paymentAmount) {
        this.transactionID = transactionID;
        this.name = name;
        this.mobileNumber = mobileNumber;
        this.email = email;
        this.paymentAmount = paymentAmount;
    }

    public String getTransactionID() {
        return transactionID;
    }

    public String getName() {
        return name;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public String getEmail() {
        return email;
    }

    public double getPaymentAmount() {
        return paymentAmount;
    }
}
