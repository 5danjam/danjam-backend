package com.danjam.room;

import com.danjam.dorm.Dorm;
import com.danjam.dorm.DormRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomRepositoryCustom{

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

}
