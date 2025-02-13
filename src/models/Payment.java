package models;

public class Payment {
    private int id;
    private int bookingId;
    private double amount;
    private String paymentMethod;
    private String status;
    private String createdAt;


    // Конструкторы
    public Payment() {}

    public Payment(int bookingId, double amount, String paymentMethod, String status, String createdAt) {
        this.bookingId = bookingId;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.status = status;
        this.createdAt = createdAt;
    }



    public Payment(int id, int bookingId, double amount, String paymentMethod, String status, String createdAt) {
        this(bookingId, amount, paymentMethod, status, createdAt);
        this.id = id;
    }

    // Геттеры и сеттеры
    public int getId() {
        return id;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }




    @Override
    public String toString() {
        return "Payment{" +
                "id=" + id +
                ", bookingId=" + bookingId +
                ", amount=" + amount +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", status='" + status + '\'' +
                ", createdAt='" + createdAt + '\'' +
                '}';
    }
}
