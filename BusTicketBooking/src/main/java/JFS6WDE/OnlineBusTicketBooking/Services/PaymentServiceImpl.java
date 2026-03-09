package JFS6WDE.OnlineBusTicketBooking.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import JFS6WDE.OnlineBusTicketBooking.Entities.Payment;
import JFS6WDE.OnlineBusTicketBooking.Repository.PaymentRepository;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public Payment getPaymentByBookingId(Long bookingId) {
        return paymentRepository.findByBookingId(bookingId);
    }

    @Override
    public void updatePaymentStatus(Long bookingId, String status) {
        Payment payment = paymentRepository.findByBookingId(bookingId);
        if (payment != null) {
            payment.setPaymentStatus(status);
            paymentRepository.save(payment);
        }
    }
}
