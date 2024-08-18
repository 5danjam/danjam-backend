package com.danjam.dorm;

import com.danjam.amenity.AmenityListDTO;
import com.danjam.amenity.AmenityService;
import com.danjam.dorm.querydsl.DormBookingListDTO;
import com.danjam.room.RoomDetailDTO;
import com.danjam.room.RoomService;
import com.danjam.search.SearchDto;
import com.danjam.search.SearchService;
import com.danjam.search.querydsl.AmenityDto;
import com.danjam.search.querydsl.RoomDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@CrossOrigin
@RequiredArgsConstructor
public class DormController {

    private final DormServiceImpl DORMSERVICE;
    private final RoomService ROOMSERVICE;
    private final SearchService SEARCHSERVICE;

    @PostMapping("/dorm/insert")
    public ResponseEntity<HashMap<String, Object>> insert(@RequestBody DormAddDTO dormAddDTO) {
        HashMap<String, Object> resultMap = new HashMap<>();

        try {
            Long dormId = DORMSERVICE.insert(dormAddDTO);
            resultMap.put("result", "success");
            resultMap.put("resultId", dormId);
            return ResponseEntity.ok(resultMap);

        } catch (RuntimeException e) {
            if (e.getMessage().contains("A Dorm with this address already exists")) {
                System.out.println("중복 발생");
                resultMap.put("result", "fail");
                resultMap.put("message", e.getMessage());
                return ResponseEntity.badRequest().body(resultMap);
            }

            resultMap.put("result", "fail");
            resultMap.put("message", "An unexpected error occurred");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resultMap);
        }
    }


    @GetMapping("/dorm/Sellerlist/{id}")
    public HashMap<String, Object> getAllDorms(@PathVariable Long id) {

        HashMap<String, Object> resultMap = new HashMap<>();
        System.out.println(id);
        List<DormListDTO> dorms = DORMSERVICE.findByUser(id);

        try {
            System.out.println(dorms);
            resultMap.put("result", "success");
            resultMap.put("dormList", dorms);

        } catch (Exception e) {
            e.printStackTrace();
            resultMap.put("result", "fail");
        }

        return resultMap;

    }

    @GetMapping("/SellerCalendar/{id}")
    public ResponseEntity<List<DormBookingListDTO>> findSellerById(@PathVariable Long id) {

        List<DormBookingListDTO> dorms = DORMSERVICE.findBookingsBySellerId(id);

        System.out.println(dorms);

        return new ResponseEntity<>(dorms, HttpStatus.OK);
    }

    @DeleteMapping("/dorm/delete/{dormId}")
    public ResponseEntity<String> deleteDorm(@PathVariable Long dormId) {
        boolean isDeleted = DORMSERVICE.deleteDorm(dormId);
        if (isDeleted) {
            return new ResponseEntity<>("Dorm deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Dorm not found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/dorm/Approvelist")
    public HashMap<String, Object> findStatusN() {

        HashMap<String, Object> resultMap = new HashMap<>();

        List<DormListDTO> dormList = DORMSERVICE.findByStatus();

        try {
            System.out.println("dormList" + dormList);
            resultMap.put("result", "success");
            resultMap.put("dormList", dormList);

        } catch (Exception e) {
            e.printStackTrace();
            resultMap.put("result", "fail");
        }
        return resultMap;
    }

    @PostMapping("/dorm/Update")
    public ResponseEntity<String> updateDorms(@RequestBody List<Long> selectedDorms) {
        System.out.println("dorms: " + selectedDorms);
        try {
            DORMSERVICE.updateDorms(selectedDorms);
            return ResponseEntity.ok(selectedDorms.size() + " Dorms updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating dorms");
        }
    }

    //todo 0816일 02:39 호텔에 대한 상세정보
    @PostMapping("/dorms/{id}")
    public HashMap<String, Object> getDormById(@PathVariable Long id, @RequestBody SearchDto searchDto) {

        HashMap<String, Object> resultController = new HashMap<>();

        System.out.println("id: " + id);

        try {
            DormDTO dorm = DORMSERVICE.getDormById(id);
            resultController.put("id", dorm.getId());
            resultController.put("name", dorm.getName());
            resultController.put("description", dorm.getDescription());
            resultController.put("contactNum", dorm.getContactNum());
            resultController.put("city", dorm.getCity());
            resultController.put("town", dorm.getTown());
            resultController.put("address", dorm.getAddress());

            List<String> dormImageURLs = dorm.getDormImages().stream()
                    .map(imageName -> "http://localhost:8080/uploads/" + imageName.trim())
                    .toList();
            resultController.put("dormImages", dormImageURLs);  // URL 리스트를 resultController에 추가

            resultController.put("result", "success");
            List<RoomDetailDTO> rooms = SEARCHSERVICE.findAllRoom(searchDto, id);
            System.out.println("rooms: " + rooms);
            resultController.put("rooms", rooms);

            List<AmenityListDTO> amenities = SEARCHSERVICE.findAmenity(id);
            System.out.println("amenities: " + amenities);
            resultController.put("amenities", amenities);
        } catch (Exception e) {
            e.printStackTrace();
            resultController.put("result", "fail");
        }
        return resultController;
    }

    @GetMapping("/dorms/{id}/rooms")
    public HashMap<String, Object> getRoomsByDormId(@PathVariable Long id) {
        HashMap<String, Object> response = new HashMap<>();
        try {
            List<RoomDetailDTO> rooms = ROOMSERVICE.getRoomByDormId(id);
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
