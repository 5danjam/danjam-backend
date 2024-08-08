package com.danjam.dorm;

import com.danjam.d_category.Dcategory;
import com.danjam.users.Users;
import com.danjam.d_category.DcategoryRepository;
import com.danjam.users.UsersRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class DormServiceImpl implements DormService {

    private final DormRepository DORMREPOSITORY;
    private final UsersRepository USERSREPOSITORY;
    private final DcategoryRepository DCATEGORYREPOSITORY;

    @Transactional
    public Long insert(DormAddDTO dormAddDTO){


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
                .contact_num(dormAddDTO.getContact_num())
                .dcategory(dcategory)
                .user(user)
                .build();

       return DORMREPOSITORY.save(dorm).getId();
    }

}
