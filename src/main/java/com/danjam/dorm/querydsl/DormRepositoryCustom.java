package com.danjam.dorm.querydsl;

import java.util.List;

public interface DormRepositoryCustom {
    List<DormBookingListDTO> findBookingsBySellerId(Long userId);
}

