package com.danjam.dorm;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DormRepository extends JpaRepository<DormDTO,Long> {
    // 추가적인 쿼리 메서드를 정의할 수 있음.
}
