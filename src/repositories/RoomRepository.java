package repositories;

import data.interfaces.IDB;
import models.Room;
import repositories.interfaces.IRoomRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoomRepository implements IRoomRepository {
    private final IDB db;

    public RoomRepository(IDB db) {
        this.db = db;
    }

    @Override
    public boolean createRoom(Room room) {
        try (Connection conn = db.getConnection()) {
            String sql = "INSERT INTO rooms (room_number, room_type, price, status) VALUES (?,?,?,?)";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, room.getRoomNumber());
            st.setString(2, room.getRoomType());
            st.setDouble(3, room.getPrice());
            st.setString(4, room.getStatus());
            st.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Room getRoomById(int id) {
        try (Connection conn = db.getConnection()) {
            String sql = "SELECT id, room_number, room_type, price, status, created_at FROM rooms WHERE id = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                return new Room(
                        rs.getInt("id"),
                        rs.getString("room_number"),
                        rs.getString("room_type"),
                        rs.getDouble("price"),
                        rs.getString("status"),
                        rs.getString("created_at")
                );
            }
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
        return null;
    }

    public double getRoomPriceById(int roomId) {
        try (Connection conn = db.getConnection()) {
            String sql = "SELECT price FROM rooms WHERE id = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, roomId);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getDouble("price");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0; // Если комната не найдена, возвращаем 0
    }

    @Override
    public List<Room> getAllRooms() {
        List<Room> rooms = new ArrayList<>();
        try (Connection conn = db.getConnection();
             PreparedStatement st = conn.prepareStatement("SELECT * FROM rooms");
             ResultSet rs = st.executeQuery()) {

            while (rs.next()) {
                rooms.add(new Room(
                        rs.getInt("id"),
                        rs.getString("room_number"),
                        rs.getString("room_type"),
                        rs.getDouble("price"),
                        rs.getString("status"),
                        rs.getString("created_at")
                ));
            }
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
        return rooms;
    }

    @Override
    public boolean updateRoomStatus(int id, String newStatus) {
        try (Connection conn = db.getConnection()) {
            String sql = "UPDATE rooms SET status = ? WHERE id = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, newStatus);
            st.setInt(2, id);

            int rowsUpdated = st.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<Room> findAvailableRooms(String checkIn, String checkOut, double maxPrice) {
        List<Room> rooms = new ArrayList<>();
        try (Connection conn = db.getConnection()) {
            String sql = "SELECT * FROM rooms r WHERE r.price <= ? AND r.id NOT IN " +
                    "(SELECT room_id FROM bookings WHERE check_out_date > ? AND check_in_date < ?)";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setDouble(1, maxPrice);
            st.setString(2, checkIn);
            st.setString(3, checkOut);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                rooms.add(new Room(
                        rs.getInt("id"),
                        rs.getString("room_number"),
                        rs.getString("room_type"),
                        rs.getDouble("price"),
                        rs.getString("status"),
                        rs.getString("created_at")
                ));
            }
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
        return rooms;
    }
}
