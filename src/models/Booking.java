package models;

public class Booking {
    private int id;
    private int customerId;
    private int roomId;
    private String checkInDate;
    private String checkOutDate;
    private String status;
    private String createdAt;
    private double price;


    public Booking() {}

    public Booking(int customerId, int roomId, String checkInDate, String checkOutDate, String status, String createdAt, double price) {
        this.customerId = customerId;
        this.roomId = roomId;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.status = status;
        this.createdAt = createdAt;
        this.price = price;
    }

    public Booking(int id, int customerId, int roomId, String checkInDate, String checkOutDate, String status, String createdAt, double price) {
        this(customerId, roomId, checkInDate, checkOutDate, status, createdAt, price);
        this.id = id;
    }

    // Геттеры и сеттеры
    public int getId() {
        return id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(String checkInDate) {
        this.checkInDate = checkInDate;
    }

    public String getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(String checkOutDate) {
        this.checkOutDate = checkOutDate;
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

    public double getPrice(){
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "id=" + id +
                ", customerId=" + customerId +
                ", roomId=" + roomId +
                ", checkInDate='" + checkInDate + '\'' +
                ", checkOutDate='" + checkOutDate + '\'' +
                ", status='" + status + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", price=" + price +
                '}';
    }
}
