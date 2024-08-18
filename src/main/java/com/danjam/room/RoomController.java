package com.danjam.room;


import com.danjam.search.SearchDto;
import com.danjam.search.SearchService;
import com.danjam.search.querydsl.RoomDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;


@RestController
@CrossOrigin
@RequiredArgsConstructor
public class RoomController {

    private final RoomServiceImpl ROOMSERVICE;

    @PostMapping("/room/insert")
    public HashMap<String, Object> insert(@RequestBody RoomAddDTO roomAddDTO) {

        HashMap<String, Object> resultMap = new HashMap();

        System.out.println("roomAddDTO: " + roomAddDTO);

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

    @GetMapping("/room/{id}")
    public HashMap<String, Object> getRoomIdsByDormId(@PathVariable("id") Long dormId) {
        HashMap<String, Object> roomController = new HashMap<>();

        System.out.println("id: " + dormId);

        try {
            List<Long> room = ROOMSERVICE.getRoomIdsByDormId(dormId);
            roomController.put("result", "success");
            roomController.put("rooms ", room);
            roomController.put("roomController ", room);
        } catch (Exception e) {
            e.printStackTrace();
            roomController.put("success", "fail");
        }
        return roomController;
    }

    @GetMapping("/rooms/{id}")
    public HashMap<String, Object> getRoomByDormId(@PathVariable("id") Long dormId) {
        HashMap<String, Object> response = new HashMap<>();

        try {
            List<RoomDetailDTO> rooms = ROOMSERVICE.getRoomByDormId(dormId);
            response.put("result", "success");
            System.out.println(rooms);
            response.put("rooms", rooms);
        } catch (Exception e) {
            e.printStackTrace();
            response.put("result", "fail");
            response.put("error", e.getMessage());
        }

        return response;
    }
}
