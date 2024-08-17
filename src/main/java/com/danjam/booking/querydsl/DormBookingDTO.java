package com.danjam.booking.querydsl;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
public class DormBookingDTO {

        private Long id;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
        private LocalDateTime checkIn;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
        private LocalDateTime checkOut;

        private String userName;

    @Builder
        public DormBookingDTO(Long id, LocalDateTime checkIn, LocalDateTime checkOut, String userName) {
            this.id = id;
            this.checkIn = checkIn;
            this.checkOut = checkOut;
            this.userName = userName;
        }

}
