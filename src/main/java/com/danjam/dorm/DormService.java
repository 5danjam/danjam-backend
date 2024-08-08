package com.danjam.dorm;

import com.danjam.review.Review;
import com.danjam.room.Room;
import com.danjam.room.RoomImg;
import com.danjam.exception.ResourceNotFoundException;
import com.danjam.review.ReviewRepository;
import com.danjam.room.RoomImgRepository;
import com.danjam.room.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DormService {

    @Autowired
    private DormRepository dormRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private RoomImgRepository roomImgRepository;

    public DormDTO selectOne(int id) {
        return selectOne(id);
    }

    // 각 메서드들을 특정 비즈니스 로직으로 처리
    public DormDTO getDormDetails(Long dormId) {
        return dormRepository.findById(dormId).orElseThrow(() -> new ResourceNotFoundException("Dorm not found"));
    }

    // 호텔에 속한 방 목록을 반환
    public List<Room> getDormRooms(Long dormId) {
        DormDTO dormDTO = getDormDetails(dormId);
        return roomRepository.findByDormOrderByPriceDesc(dormDTO);
    }

    // 호텔에 대한 리뷰 목록을 반환
    public List<Review> getDormReviews(Long dormId) {
        DormDTO dormDTO = getDormDetails(dormId);
        return reviewRepository.findByDormOrderByRatingDesc(dormDTO);
    }

    // 방 이미지 목록 반환
    public List<RoomImg> getRoomImages(Long roomId) {
        Room room = roomRepository.findById(roomId).orElseThrow(() -> new ResourceNotFoundException("Room not found"));
        return roomImgRepository.findByRoom(room);
    }

    // 전체 호텔 목록 반환
    public List<DormDTO> getAllDorms() {
        return dormRepository.findAll();
    }


}
