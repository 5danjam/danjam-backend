package com.danjam.payment;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public record PaymentResponseDTO(
        String paymentKey,
        String orderId,
        int totalAmount,
        String status,
        String approvedAt
) {

    public Payment toEntity() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
        ZonedDateTime zonedDateTime = ZonedDateTime.parse(approvedAt, formatter);

        return Payment.builder()
                .paymentKey(paymentKey)
                .orderId(orderId)
                .totalAmount(totalAmount)
                .status(status)
                .approvedAt(zonedDateTime.toLocalDateTime())
                .build();
    }
}
