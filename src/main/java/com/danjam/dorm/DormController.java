package com.danjam.dorm;

import com.danjam.dorm.querydsl.DormBookingListDTO;
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


    @GetMapping ("/dorm/Sellerlist/{id}")
    public HashMap<String, Object>  getAllDorms(@PathVariable Long id) {

        HashMap<String, Object> resultMap = new HashMap();
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

    @GetMapping ("/dorm/Approvelist")
    public HashMap<String, Object>  findStatusN() {

        HashMap<String, Object> resultMap = new HashMap();

        List<DormListDTO> dormList = DORMSERVICE.findByStatus();

        try {
            System.out.println("dormList"+dormList);
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

}
