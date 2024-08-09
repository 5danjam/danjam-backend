package com.danjam.dorm;

import com.danjam.d_category.Dcategory;
import com.danjam.review.ReviewRepository;
import com.danjam.room.Room;
import com.danjam.room.RoomDTO;
import com.danjam.room.RoomRepository;
import com.danjam.roomImg.RoomImgDTO;
import com.danjam.roomImg.RoomImgRepository;
import com.danjam.users.Users;
import com.danjam.d_category.DcategoryRepository;
import com.danjam.users.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class DormServiceImpl implements DormService {

    private final DormRepository DORMREPOSITORY;
    private final UsersRepository USERSREPOSITORY;
    private final DcategoryRepository DCATEGORYREPOSITORY;
    private final RoomImgRepository ROOMIMGREPOSITORY;
    private final ReviewRepository REVIEWREPOSITORY;
    private final RoomRepository ROOMREPOSITORY;

    @Transactional
    @Override
    public Long insert(DormAddDTO dormAddDTO) {
        Optional<Users> usersOptional = USERSREPOSITORY.findById(dormAddDTO.getUsersId());
        Optional<Dcategory> dCategoryOptional = DCATEGORYREPOSITORY.findById(dormAddDTO.getCategoryId());

        System.out.println("dCategoryOptional : " + dCategoryOptional);

        Users user = usersOptional.orElseThrow(() -> new RuntimeException("User not found"));
        Dcategory dcategory = dCategoryOptional.orElseThrow(() -> new RuntimeException("Dcategory not found"));

        Dorm dorm = Dorm.builder()
                .name(dormAddDTO.getName())
                .description(dormAddDTO.getDescription())
                .city(dormAddDTO.getCity())
                .town(dormAddDTO.getTown())
                .address(dormAddDTO.getAddress())
                .contactNum(dormAddDTO.getContactNum())
                .dcategory(dcategory)
                .user(user)
                .build();

        return DORMREPOSITORY.save(dorm).getId();
    }

    public Page<Dorm> getDorms(Pageable pageable) {
        return DORMREPOSITORY.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Page<DormDTO> searchDorms(Pageable pageable) {
        Page<Dorm> dorms = DORMREPOSITORY.findAll(pageable);

        return dorms.map(dorm -> {
            List<Room> rooms = ROOMREPOSITORY.findByDorm(dorm);

            List<RoomDTO> roomDTOs = rooms.stream()
                    .map(room -> {
                        List<RoomImgDTO> roomImgDTOs = ROOMIMGREPOSITORY.findByRoom(room).stream()
                                .map(RoomImgDTO::new)
                                .collect(Collectors.toList());
                        return new RoomDTO(room, roomImgDTOs);
                    })
                    .collect(Collectors.toList());

            Double averageRating = REVIEWREPOSITORY.findAverageRatingByDormId(dorm.getId());

            Integer lowestPrice = ROOMREPOSITORY.findLowestRoomPriceByDormId(dorm.getId());

            return new DormDTO(dorm, averageRating, lowestPrice, roomDTOs);
        });
    }


}
