package com.danjam.dorm;

public interface DormService {

    Long insert(DormAddDTO dormAddDTO);

    DormDTO getDormById(Long id);
}
