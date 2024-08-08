package com.danjam.booking;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @GetMapping
    public ResponseEntity<Void> findAll() {
        log.info("Find all bookings");

        return ResponseEntity.ok().build();
    }
}
