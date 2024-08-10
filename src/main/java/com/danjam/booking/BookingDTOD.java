package com.danjam.booking;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
public class BookingDTOD {

        private Long id;
        private LocalDate checkIn;
        private LocalDate checkOut;
        private String userName;

    @Builder
        public BookingDTOD(Long id, LocalDate checkIn, LocalDate checkOut, String userName) {
            this.id = id;
            this.checkIn = checkIn;
            this.checkOut = checkOut;
            this.userName = userName;
        }

        // Getters and setters


}
