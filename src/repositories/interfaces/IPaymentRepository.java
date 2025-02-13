package repositories.interfaces;

import models.Payment;
import java.util.List;

public interface IPaymentRepository {
    boolean createPayment(int bookingId);
    boolean createPayment(Payment payment); // Добавьте этот метод
    Payment getPaymentById(int id);
    List<Payment> getAllPayments();
    boolean updatePaymentStatus(int id, String newStatus);
}
