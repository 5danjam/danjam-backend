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

    @Override
    public DormDTO getDormById(Long id) {
      /* 0815,23:11 분 코드
      Dorm foundDorm = DORMREPOSITORY.findById(id)
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("DormServiceImpl not found"));*/

        Dorm foundDorm = DORMREPOSITORY.findById(id)
                .orElseThrow(() -> new RuntimeException("DormServiceImpl not found"));

        // 확인용
        System.out.println("foundDorm:돔서비스엘임피엘 돔파운드 확인용 " + foundDorm);

        if (foundDorm == null) {
            throw new RuntimeException("돔 찾을 수 없음. 돔 서비스엘 임피엘 확인용");
        }

        System.out.println("foundDorm.getId 돔 서비스엘 임피엘 겟 아이디 확인용" + foundDorm.getId());

        List<String> dormImages = ROOMIMGREPOSITORY.findRoomImgNamesByDormId(foundDorm.getId());
        System.out.println("dormImages" + dormImages);
        // Dorm 객체를 DormDTO로 변환


        return new DormDTO(
                foundDorm.getId(),
                foundDorm.getName(),
                foundDorm.getDescription(),
                foundDorm.getContactNum(),
                foundDorm.getCity(),
                foundDorm.getTown(),
                foundDorm.getAddress(),
                foundDorm.getStatus(),
                dormImages  // 이미지 리스트를 추가합니다.
        );
    }
}