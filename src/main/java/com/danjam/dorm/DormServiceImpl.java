package com.danjam.dorm;

import com.danjam.d_category.Dcategory;
import com.danjam.roomImg.RoomImgRepository;
import com.danjam.users.Users;
import com.danjam.d_category.DcategoryRepository;
import com.danjam.users.UsersRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

    @Transactional
    @Override
    public Long insert(DormAddDTO dormAddDTO) {
        Optional<Users> usersOptional = USERSREPOSITORY.findById(dormAddDTO.getUsersId());
        Optional<Dcategory> dCategoryOptional = DCATEGORYREPOSITORY.findById(dormAddDTO.getCategoryId());

        System.out.println("dCategoryOptional : "+dCategoryOptional);

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
    @Transactional
    public List<DormListDTO> findByUser(Long id) {
        Optional<Users> usersOptional = USERSREPOSITORY.findById(id);
        Users user = usersOptional.orElseThrow(() -> new RuntimeException("User not found"));

        List<Dorm> dorms = DORMREPOSITORY.findByUser(user);

        return dorms.stream()
                .map(dorm -> {
                    List<String> roomImgNames = ROOMIMGREPOSITORY.findRoomImgNamesByDormId(dorm.getId());
                    return new DormListDTO(
                            dorm.getId(),
                            dorm.getName(),
                            dorm.getDescription(),
                            dorm.getContactNum(),
                            dorm.getCity(),
                            dorm.getTown(),
                            dorm.getAddress(),
                            dorm.getStatus(),
                            roomImgNames
                    );
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public List<DormBookingListDTO> findBookingsBySellerId(Long id) {
        return DORMREPOSITORY.findBookingsBySellerId(id);
    }

}
