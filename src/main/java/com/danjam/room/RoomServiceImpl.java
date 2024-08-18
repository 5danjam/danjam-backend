package com.danjam.room;

import com.danjam.dorm.Dorm;
import com.danjam.dorm.DormRepository;
import com.danjam.search.SearchDto;
import com.danjam.search.SearchService;
import com.danjam.search.querydsl.RoomDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final DormRepository DORMREPOSITORY;
    private final RoomRepository ROOMREPOSITORY;

    @Transactional
    public Long insert(RoomAddDTO roomAddDTO) {

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
    public List<Long> getRoomIdsByDormId(Long dormId) {
        return ROOMREPOSITORY.findByDormId(dormId)
                .stream()
                .map(Room::getId)
                .toList();
    }

    @Override
    public Optional<Long> findRoomIdByDormId(Long dormId) {
        return ROOMREPOSITORY.findByDormId(dormId).stream().findFirst().map(Room::getId);
    }

    @Override
    public List<RoomDetailDTO> getRoomByDormId(Long dormId) {
    List<Room> rooms = ROOMREPOSITORY.findByDormId(dormId);
        return rooms.stream()
                .map(RoomDetailDTO::new)
                .toList();
    }
}
