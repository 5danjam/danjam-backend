package com.danjam.booking;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final BookingRepository bookingRepository;
}
