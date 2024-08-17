package com.danjam.dorm;

import com.danjam.room.RoomDTO;
import com.danjam.room.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequiredArgsConstructor
public class DormController {

    private final DormServiceImpl DORMSERVICE;
    private final RoomService ROOMSERVICE;

    @PostMapping("/dorm/insert")
    public HashMap<String, Object> insert(@RequestBody DormAddDTO dormAddDTO) {

        HashMap<String, Object> resultMap = new HashMap();

        System.out.println("dormAddDTO: " + dormAddDTO);

        try {
            Long dormId = DORMSERVICE.insert(dormAddDTO);
            resultMap.put("result", "success");
            resultMap.put("resultId", dormId);

        } catch (Exception e) {
            e.printStackTrace();
            resultMap.put("result", "fail");
        }

        return resultMap;
    }

    //todo 0816일 02:39 호텔에 대한 상세정보
    @GetMapping("/dorms/{id}")
    public HashMap<String, Object> getDormById(@PathVariable Long id) {

        HashMap<String, Object> resultController = new HashMap();

        System.out.println("id: " + id); // 여기까지는 됨.

        try {
            //Dorm dorm = DORMSERVICE.getDormById(id);
            com.danjam.dorm.DormDTO dorm = DORMSERVICE.getDormById(id);
            System.out.println("dorm" + dorm);
            resultController.put("id", dorm.getId());
            resultController.put("name", dorm.getName());
            resultController.put("description", dorm.getDescription());
            resultController.put("contactNum", dorm.getContactNum());
            resultController.put("city", dorm.getCity());
            resultController.put("town", dorm.getTown());
            resultController.put("address", dorm.getAddress());


            List<String> dormImageURLs = dorm.getDormImages().stream()
                    .map(imageName -> "http://localhost:8080/uploads/" + imageName.trim())
                    .collect(Collectors.toList());
            resultController.put("dormImages", dormImageURLs);  // URL 리스트를 resultController에 추가

            resultController.put("result", "success");
            // 콘솔 확인용 입니다.
            System.out.println("돔 이미지가 드러오는지 확인하는 코드 " + dorm.getDormImages());
            List<RoomDTO> rooms = ROOMSERVICE.getRoomByDormId(id);
            resultController.put("rooms", rooms);
        } catch (Exception e) {
            e.printStackTrace();
            resultController.put("result", "fail");
        }
        return resultController;
    }


    //todo -송준한 0816, 02:45 돔,룸 매핑
    @GetMapping("/dorms/{id}/rooms")
    public HashMap<String, Object> getRoomsByDormId(@PathVariable Long id) {
        HashMap<String, Object> response = new HashMap<>();
        try {
            List<RoomDTO> rooms = ROOMSERVICE.getRoomByDormId(id);
            response.put("result", "success");
            response.put("rooms", rooms);
        } catch (Exception e) {
            e.printStackTrace();
            response.put("result", "fail");
            response.put("error", e.getMessage());
        }
        return response;
    }

}
