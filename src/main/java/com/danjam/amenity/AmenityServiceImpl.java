package com.danjam.amenity;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class AmenityServiceImpl implements AmenityService {

    private final AmenityRepository AMENITYREPOSITORY;

    @Override
    public List<AmenityListDTO> list() {

        List<Amenity> amenities = AMENITYREPOSITORY.findAll();

        return amenities.stream()
                .map(dcategory -> AmenityListDTO.builder()
                .id(dcategory.getId())
                .name(dcategory.getName())
                .build())
                .collect(Collectors.toList());

    }
}

