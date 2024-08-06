package com.danjam.payment;

public record PaymentRequestDTO(
        String paymentKey,
        String orderId,
        int amount
) {
}
