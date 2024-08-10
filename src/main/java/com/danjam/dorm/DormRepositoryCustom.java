package com.danjam.dorm;

import java.util.List;

public interface DormRepositoryCustom {
    List<DormBookingListDTO> findBookingsBySellerId(Long userId);
}

