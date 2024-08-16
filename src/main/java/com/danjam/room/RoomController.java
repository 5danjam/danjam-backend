package com.danjam.room;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;


@RestController
@CrossOrigin
@RequiredArgsConstructor
public class RoomController {

    private final RoomServiceImpl ROOMSERVICE;

    @PostMapping("/room/insert")
    public HashMap<String, Object> insert(@RequestBody RoomAddDTO roomAddDTO) {

        HashMap<String, Object> resultMap = new HashMap();

        System.out.println("roomAddDTO: "+roomAddDTO);

        try {
            Long roomId = ROOMSERVICE.insert(roomAddDTO);
            resultMap.put("result", "success");
            resultMap.put("resultId", roomId);

        } catch (Exception e) {
            e.printStackTrace();
            resultMap.put("result", "fail");
        }

        return resultMap;
    }
}
