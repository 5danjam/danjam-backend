package com.danjam.d_category;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class DcategoryServiceImpl implements DcategoryService {

    private final DcategoryRepository DCATEGORYREPOSITORY;

    @Override
    public List<DcategoryListDTO> list() {

        List<Dcategory> dcategories = DCATEGORYREPOSITORY.findAll();

        return dcategories.stream()
                .map(dcategorie -> DcategoryListDTO.builder()
                .id(dcategorie.getId())
                .name(dcategorie.getName())
                .build())
                .collect(Collectors.toList());

    }
}

