package com.danjam.room;

import com.danjam.dorm.Dorm;
import com.danjam.dorm.DormRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final DormRepository DORMREPOSITORY;
    private final RoomRepository ROOMREPOSITORY;


    @Transactional
    public Long insert(RoomAddDTO roomAddDTO){

        Optional<Dorm> dormOptional = DORMREPOSITORY.findById(roomAddDTO.getDormId());

        Dorm dorm = dormOptional.orElseThrow(() -> new RuntimeException("Dorm not found"));

        Room room = Room.builder()
                .name(roomAddDTO.getName())
                .description(roomAddDTO.getDescription())
                .person(roomAddDTO.getPerson())
                .price(roomAddDTO.getPrice())
                .type(roomAddDTO.getType())
                .dorm(dorm)
                .build();

       return ROOMREPOSITORY.save(room).getId();
    }

    @Override
    public List<Long> getRoomIdsByDormId(Long dormId) {    // 돔 아이디에 속한 룸아이디 조회
        return ROOMREPOSITORY.findByDormId(dormId)
                .stream()
                .map(Room::getId)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Long> findRoomIdByDormId(Long dormId) {
        //return roomRepositoryCustom.findRoomIdByDormId(dormId);
        return ROOMREPOSITORY.findByDormId(dormId).stream().findFirst().map(Room::getId);
    }

   /*
   0816,.0248 메서드 이름 중복되서 우선 주석처리
   @Override
    public List<RoomDTO> getRoomByDormId(Long dormId) {

        List<Room> rooms = ROOMREPOSITORY.getRoomByDormId(dormId);


        //rooms.forEach(room -> Hibernate.initialize(room.getDorm()));


        //return ROOMREPOSITORY.findByDormId(dormId);
        //return rooms;
        return rooms.stream().map(RoomDTO::new).collect(Collectors.toList());
    }*/

    @Override
    public List<RoomDTO> getRoomByDormId(Long dormId) {
        List<Room> rooms = ROOMREPOSITORY.findByDormId(dormId);
        return rooms.stream()
                .map(RoomDTO::new)
                .collect(Collectors.toList());
    }


}
