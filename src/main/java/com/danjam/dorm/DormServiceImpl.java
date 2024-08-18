package com.danjam.dorm;

import com.danjam.d_amenity.DamenityRepository;
import com.danjam.d_category.Dcategory;
import com.danjam.d_category.DcategoryRepository;
import com.danjam.dorm.querydsl.DormBookingListDTO;
import com.danjam.roomImg.RoomImgRepository;
import com.danjam.users.Users;
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
    private final DamenityRepository DAMENITYREPOSITORY;

    @Transactional
    @Override
    public Long insert(DormAddDTO dormAddDTO) {

        boolean exists = DORMREPOSITORY.existsByAddress(dormAddDTO.getAddress());

        if (exists) {
            System.out.println("중복 발생");
            throw new RuntimeException("A Dorm with this address already exists");
        }

        Optional<Users> usersOptional = USERSREPOSITORY.findById(dormAddDTO.getUsersId());
        Optional<Dcategory> dCategoryOptional = DCATEGORYREPOSITORY.findById(dormAddDTO.getCategoryId());

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

    public boolean deleteDorm(Long dormId) {

        if (DORMREPOSITORY.existsById(dormId)) {
            DORMREPOSITORY.deleteById(dormId);
            return true;
        }
        return false;
    }

    public List<DormListDTO> findByStatus() {

        String status = "N";

        List<Dorm> dorms = DORMREPOSITORY.findByStatus(status);

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

    public void updateDorms(List<Long> dormIds) {
        System.out.println("dormIds: " + dormIds);
        List<Dorm> dormsToUpdate = DORMREPOSITORY.findAllById(dormIds);

        for (Dorm dorm : dormsToUpdate) {
            dorm.setStatus("Y");
        }
        DORMREPOSITORY.saveAll(dormsToUpdate);
    }

    @Override
    public DormDTO getDormById(Long id) {
        Dorm foundDorm = DORMREPOSITORY.findById(id)
                .orElseThrow(() -> new RuntimeException("DormServiceImpl not found"));

        if (foundDorm == null) {
            throw new RuntimeException("돔 찾을 수 없음. 돔 서비스엘 임피엘 확인용");
        }

        List<String> dormImages = ROOMIMGREPOSITORY.findRoomImgNamesByDormId(foundDorm.getId());
        System.out.println("dormImages" + dormImages);

        return new DormDTO(
                foundDorm.getId(),
                foundDorm.getName(),
                foundDorm.getDescription(),
                foundDorm.getContactNum(),
                foundDorm.getCity(),
                foundDorm.getTown(),
                foundDorm.getAddress(),
                foundDorm.getStatus(),
                dormImages
        );
    }
}
