package com.danjam.dorm.querydsl;

import com.danjam.dorm.Dorm;

import java.util.List;

public interface DormRepositoryCustom {
    List<DormBookingListDTO> findBookingsBySellerId(Long userId);

    List<DormDto> findList();
}
