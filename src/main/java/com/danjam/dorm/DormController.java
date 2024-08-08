package com.danjam.dorm;

import com.danjam.review.Review;
import com.danjam.room.Room;
import com.danjam.room.RoomImg;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@CrossOrigin
@RestController
@RequestMapping("/dorm") // 기본 경로
public class DormController {

    @Autowired
    private DormService dorm_Service;

    @Autowired
    public DormController(DormService dormService) {
         dorm_Service = dormService;
    }



    @GetMapping("DormOne{id}")
    public DormDTO selectOne(@PathVariable int id) {
        return dorm_Service.selectOne(id);
       // List<DcategoryListDTO> dcategoryList = DCATEGORYSERVICE.list();

    }

    // 호텔에 속한 방 목록을 반환
    @GetMapping("/{id}/rooms")
    public ResponseEntity<List<Room>> getDormRooms(@PathVariable Long id) {

        List<Room> rooms = dorm_Service.getDormRooms(id);
        return new ResponseEntity<>(rooms, HttpStatus.OK);
    }

    // 특정 호텔에 대한 리뷰 목록을 반환
    @GetMapping("/{id}/reviews")
    public ResponseEntity<List<Review>> getDormReviews(@PathVariable Long id) {
        List<Review> reviews = dorm_Service.getDormReviews(id);
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    // 특정 방의 이미지 목록을 반환
    @GetMapping("/rooms/{roomId}/images")
    public ResponseEntity<List<RoomImg>> getRoomImages(@PathVariable Long roomId) {
        List<RoomImg> images = dorm_Service.getRoomImages(roomId);
        return new ResponseEntity<>(images, HttpStatus.OK);
    }

    // 호텔 목록을 반환하는 엔드포인트
    @GetMapping
    public ResponseEntity<List<DormDTO>> getAllDorms() {
        List<DormDTO> dormDTOS = dorm_Service.getAllDorms();
        return new ResponseEntity<>(dormDTOS, HttpStatus.OK);
    }



}

// 기숙사 상세 정보를 반환
    /*@GetMapping("DormOne/{id}")
    public ResponseEntity<Dorm> getDormDetails(@PathVariable Long id) {
        Dorm dorm = dorm_Service.getDormDetails(id);
        return new ResponseEntity<>(dorm, HttpStatus.OK);
    }*/