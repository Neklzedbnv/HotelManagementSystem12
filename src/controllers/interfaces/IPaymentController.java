package controllers.interfaces;

import models.Payment;

public interface IPaymentController {
    String createPayment(int bookingId);

    String createPayment(Payment payment);

    String getPaymentById(int id);
    String getAllPayments();
    String updatePaymentStatus(int id, String newStatus);
}
