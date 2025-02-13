package repositories;

import data.interfaces.IDB;
import models.Booking;
import repositories.interfaces.IBookingRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookingRepository implements IBookingRepository {
    private final IDB db;

    public BookingRepository(IDB db) {
        this.db = db;
    }

    @Override
    public boolean createBooking(Booking booking) {
        Connection conn = db.getConnection();
        if (conn == null) {
            System.out.println("SQL Error: Connection is closed.");
            return false;
        }

        try {
            String sql = "INSERT INTO bookings (customer_id, room_id, check_in_date, check_out_date, status, price) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, booking.getCustomerId());
            st.setInt(2, booking.getRoomId());
            st.setDate(3, java.sql.Date.valueOf(booking.getCheckInDate()));
            st.setDate(4, java.sql.Date.valueOf(booking.getCheckOutDate()));
            st.setString(5, "booked");
            st.setDouble(6, booking.getPrice());

            st.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
            return false;
        }
    }


    @Override
    public Booking getBookingById(int id) {
        try (Connection conn = db.getConnection()) {
            String sql = "SELECT * FROM bookings WHERE id = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                return new Booking(
                        rs.getInt("id"),
                        rs.getInt("customer_id"),
                        rs.getInt("room_id"),
                        rs.getString("check_in_date"),
                        rs.getString("check_out_date"),
                        rs.getString("status"),
                        rs.getString("created_at"),
                        rs.getDouble("price")
                );

            }
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Booking> getAllBookings() {
        List<Booking> bookings = new ArrayList<>();
        try (Connection conn = db.getConnection();
             PreparedStatement st = conn.prepareStatement("SELECT * FROM bookings");
             ResultSet rs = st.executeQuery()) {

            while (rs.next()) {
                bookings.add(new Booking(
                        rs.getInt("id"),
                        rs.getInt("customer_id"),
                        rs.getInt("room_id"),
                        rs.getString("check_in_date"),
                        rs.getString("check_out_date"),
                        rs.getString("status"),
                        rs.getString("created_at"),
                        rs.getDouble("price")
                ));
            }
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
        return bookings;
    }

    @Override
    public boolean cancelBooking(int id) {
        try (Connection conn = db.getConnection()) {
            String sql = "UPDATE bookings SET status = 'canceled' WHERE id = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, id);
            int rowsUpdated = st.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean isRoomAvailable(int roomId, String checkInDate, String checkOutDate) {
        try (Connection conn = db.getConnection()) {
            String sql = "SELECT COUNT(*) FROM bookings WHERE room_id = ? AND check_out_date > ? AND check_in_date < ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, roomId);
            st.setDate(2, java.sql.Date.valueOf(checkInDate)); // исправили setString()
            st.setDate(3, java.sql.Date.valueOf(checkOutDate)); // исправили setString()

            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) == 0;
            }
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
        return false;
    }


    @Override
    public void cancelUnpaidBookings() {
        try (Connection conn = db.getConnection()) {
            String sql = "UPDATE bookings SET status = 'canceled' WHERE id IN " +
                    "(SELECT b.id FROM bookings b " +
                    "LEFT JOIN payments p ON b.id = p.booking_id " +
                    "WHERE p.id IS NULL AND b.created_at < NOW() - INTERVAL '24 hours')";
            PreparedStatement st = conn.prepareStatement(sql);
            int rowsUpdated = st.executeUpdate();
            System.out.println(rowsUpdated + " unpaid bookings were canceled.");
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
    }


    @Override
    public List<Booking> getBookingsByCustomerId(int customerId) {
        List<Booking> bookings = new ArrayList<>();
        String sql = "SELECT id, customer_id, room_id, check_in_date, check_out_date, status, created_at, price FROM bookings WHERE customer_id = ?";
        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, customerId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                bookings.add(new Booking(
                        rs.getInt("id"),
                        rs.getInt("customer_id"),
                        rs.getInt("room_id"),
                        rs.getString("check_in_date"),
                        rs.getString("check_out_date"),
                        rs.getString("status"),
                        rs.getString("created_at"),
                        rs.getDouble("price")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookings;
    }
    @Override
    public int createBooking1(Booking booking) {
        String sql = "INSERT INTO bookings (customer_id, room_id, check_in_date, check_out_date, status, created_at) " +
                "VALUES (?, ?, ?, ?, ?, NOW()) RETURNING id";
        try (Connection conn = db.getConnection();
             PreparedStatement st = conn.prepareStatement(sql)) {

            st.setInt(1, booking.getCustomerId());
            st.setInt(2, booking.getRoomId());
            st.setString(3, booking.getCheckInDate());
            st.setString(4, booking.getCheckOutDate());
            st.setString(5, booking.getStatus());

            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getInt("id"); // Возвращаем ID созданного бронирования
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Если ошибка
    }


}
