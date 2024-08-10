package com.danjam.dorm;

import com.danjam.booking.BookingDto;
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
    public HashMap<String, Object> insert(@RequestBody DormAddDTO dormAddDTO) {

        HashMap<String, Object> resultMap = new HashMap();

        System.out.println("dormAddDTO: "+dormAddDTO);

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

    @GetMapping("/Sellerlist/{id}")
    public ResponseEntity<List<DormBookingListDTO>> findSellerById(@PathVariable Long id) {

        List<DormBookingListDTO> dorms = DORMSERVICE.findBookingsBySellerId(id);

        System.out.println(dorms);

        return new ResponseEntity<>(dorms, HttpStatus.OK);
    }
}
