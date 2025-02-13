package controllers;

import controllers.interfaces.IPaymentController;
import repositories.interfaces.IPaymentRepository;
import models.Payment;

import java.util.List;

public class PaymentController implements IPaymentController {
    private final IPaymentRepository repository;

    public PaymentController(IPaymentRepository repository) {
        this.repository = repository;
    }

    @Override
    public String createPayment(int bookingId) {
        boolean created = repository.createPayment(bookingId);
        return created ? "Payment successfully created!" : "Failed to create payment. Check booking details.";
    }

    @Override
    public String createPayment(Payment payment) {
        boolean created = repository.createPayment(payment);
        return created ? "Payment successfully created!" : "Failed to create payment. Check booking details.";
    }

    @Override
    public String getPaymentById(int id) {
        Payment payment = repository.getPaymentById(id);
        return payment != null ? payment.toString() : "Payment not found.";
    }

    @Override
    public String getAllPayments() {
        List<Payment> payments = repository.getAllPayments();
        return payments.isEmpty() ? "No payments found." : payments.toString();
    }

    @Override
    public String updatePaymentStatus(int id, String newStatus) {
        boolean updated = repository.updatePaymentStatus(id, newStatus);
        return updated ? "Payment status updated successfully!" : "Failed to update payment status.";
    }
}
