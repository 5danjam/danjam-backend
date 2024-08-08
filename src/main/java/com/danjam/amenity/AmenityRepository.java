package com.danjam.amenity;



import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;


public interface AmenityRepository extends JpaRepository<Amenity, Long> {

    @Override
    List<Amenity> findAll();

    @Override
    Optional<Amenity> findById(Long id);
}
