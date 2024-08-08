package com.danjam.roomImg;

import com.danjam.room.Room;
import com.danjam.room.RoomRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class RoomImgServiceImpl implements RoomImgService {

    private final RoomRepository ROOMREPOSITORY;
    private final RoomImgRepository ROOMIMGREPOSITORY;


    @Transactional
    public Long insert(RoomImgAddDTO roomImgAddDTO) {
        Optional<Room> roomOptional = ROOMREPOSITORY.findById(roomImgAddDTO.getRoomId());

        Room room = roomOptional.orElseThrow(() -> new RuntimeException("Room not found"));

        // RoomImg 객체를 생성하고 설정합니다.
        RoomImg roomImg = RoomImg.builder()
                .room(room) // 관계가 설정된 경우
                .name(roomImgAddDTO.getName()) // UUID
                .nameOriginal(roomImgAddDTO.getNameOriginal()) // 원본 파일 이름
                .size(roomImgAddDTO.getSize()) // 파일 크기
                .ext(roomImgAddDTO.getExt()) // 파일 확장자
                .build();

        // RoomImg 엔티티를 데이터베이스에 저장합니다.
        return ROOMIMGREPOSITORY.save(roomImg).getId();
    }
}
