package com.danjam.room;


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

    // 돔 아이디에 속한 룸아이디 조회 38~ 80 송준한
    @GetMapping("/room/{id}")
    public HashMap<String, Object> getRoomIdsByDormId(@PathVariable("id") Long dormId) {
        HashMap<String, Object> roomController = new HashMap<>();

        System.out.println("id: " + dormId); // 여기도 여기까지 됨.

        try {
            List<Long> room = ROOMSERVICE.getRoomIdsByDormId(dormId);
            //List<Room> roomList = ROOMSERVICE.getRoomByDormId(dormId);
            roomController.put("result", "success");
            roomController.put("rooms ", room);
            roomController.put("roomController ", room);

            //System.out.println(roomList);


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
            List<RoomDTO> rooms = ROOMSERVICE.getRoomByDormId(dormId);
            response.put("result", "success");
            //response.put("rooms", rooms.getClass().getName());
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
