package com.danjam.d_amenity;

import com.danjam.amenity.Amenity;
import com.danjam.amenity.AmenityRepository;
import com.danjam.dorm.Dorm;
import com.danjam.dorm.DormRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@Transactional
@RequiredArgsConstructor
public class DamenityServiceImpl implements DamenityService {

    private final DormRepository DORMREPOSITORY;
    private final DamenityRepository DAMENITYREPOSITORY;
    private final AmenityRepository AMENITYREPOSITORY;

    @Transactional
    public void insert(DamenityAddDTO damenityAddDTO) {
        Dorm dorm = DORMREPOSITORY.findById(damenityAddDTO.getDormId())
                .orElseThrow(() -> new RuntimeException("Dorm not found"));

        for (Long amenityId : damenityAddDTO.getAmenityId()) {
            Amenity amenity = AMENITYREPOSITORY.findById(amenityId)
                    .orElseThrow(() -> new RuntimeException("Amenity not found"));

            Damenity damenity = Damenity.builder()
                    .amenity(amenity)
                    .dorm(dorm)
                    .build();

            DAMENITYREPOSITORY.save(damenity);
        }
    }
}
