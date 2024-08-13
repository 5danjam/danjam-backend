package com.danjam.payment;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final PaymentService paymentService;

    @PostMapping("/confirm")
    public ResponseEntity<Payment> confirmPayment(
            @RequestBody PaymentRequestDTO paymentRequestDTO,
            @AuthenticationPrincipal Authentication authentication) {
        log.info("authentication: {}", authentication);
        log.info("paymentRequestDTO: {}", paymentRequestDTO);
        Payment payment = paymentService.confirmPayment(paymentRequestDTO);

        return new ResponseEntity<>(payment, HttpStatus.OK);
    }
}
