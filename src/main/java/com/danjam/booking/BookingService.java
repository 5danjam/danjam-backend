package com.danjam.booking;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final BookingRepository bookingRepository;

    @Transactional
    public List<BookingResponse> findByUsersId(Long id) {
        return bookingRepository.findByUsersId(id)
                .stream()
                .map((booking) -> new BookingResponse().fromEntity(booking))
                .toList();

    }
}
