package com.danjam.payment;

import com.danjam.booking.Booking;
import com.danjam.booking.BookingRepository;
import com.danjam.booking.BookingService;
import com.danjam.room.Room;
import com.danjam.room.RoomRepository;
import com.danjam.users.Users;
import com.danjam.users.UsersRepository;
import com.danjam.users.UsersService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private static final String TOSS_API_URL = "https://api.tosspayments.com/v1/payments/confirm";

    private final PaymentRepository paymentRepository;
    private final UsersRepository usersRepository;
    private final RoomRepository roomRepository;
    private final BookingRepository bookingRepository;

    @Value("${payments.toss.secret.key}")
    private String widgetSecretKey;

    public Payment confirmPayment(PaymentRequestDTO paymentRequestDTO) {
        // 토스페이먼츠 API는 시크릿 키를 사용자 ID로 사용하고, 비밀번호는 사용하지 않습니다.
        // 비밀번호가 없다는 것을 알리기 위해 시크릿 키 뒤에 콜론을 추가합니다.
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", getEncodedAuthHeader());
        headers.set("Content-Type", "application/json");

        HttpEntity<PaymentRequestDTO> request = new HttpEntity<>(paymentRequestDTO, headers);

        // 결제를 승인하면 결제수단에서 금액이 차감돼요.
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<PaymentResponseDTO> response = restTemplate.exchange(
                TOSS_API_URL,
                HttpMethod.POST,
                request,
                PaymentResponseDTO.class
        );

        if (response.getStatusCode() == HttpStatus.OK) {
            PaymentResponseDTO paymentResponseDTO = response.getBody();
            log.info("paymentResponseDTO: {}", paymentResponseDTO);
            if (paymentResponseDTO != null) {
                Users user = usersRepository.findById(paymentRequestDTO.userId())
                        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
                Room room = roomRepository.findById(paymentRequestDTO.roomId())
                        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 방입니다."));
                Payment payment = paymentResponseDTO.toEntity(user);
                Payment savedPayment = paymentRepository.save(payment);

                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime checkIn = LocalDateTime.parse(paymentRequestDTO.checkIn(), dateTimeFormatter);
                LocalDateTime checkOut = LocalDateTime.parse(paymentRequestDTO.checkOut(), dateTimeFormatter);

                Booking booking = Booking.builder()
                        .users(user)
                        .room(room)
                        .payment(savedPayment)
                        .person(paymentRequestDTO.person())
                        .checkIn(checkIn)
                        .checkOut(checkOut)
                        .build();
                bookingRepository.save(booking);

                return payment;
            }
        }

        return null;
    }

    private String getEncodedAuthHeader() {
        String auth = widgetSecretKey + ":";
        byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes(StandardCharsets.UTF_8));

        return "Basic " + new String(encodedAuth);
    }
}
