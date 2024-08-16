package com.danjam.tag;

import com.danjam.amenity.Amenity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface TagRepository extends JpaRepository<Tag, Long> {
    @Override
    List<Tag> findAll();

}
