package com.danjam.d_amenity;

import com.danjam.amenity.AmenityServiceImpl;
import com.danjam.dorm.DormServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@CrossOrigin
@RequiredArgsConstructor
public class DamenityController {

    private final DamenityServiceImpl DAMENITYSERVICE;
    private final DormServiceImpl DORMSERVICE;
    private final AmenityServiceImpl AMENITYSERVICE;

    @PostMapping("/damenity/insert")
    public HashMap<String, Object> insert(@RequestBody DamenityAddDTO damenityAddDTO) {

        HashMap<String, Object> resultMap = new HashMap();

        try {
            DAMENITYSERVICE.insert(damenityAddDTO);
            resultMap.put("result", "success");

        } catch (Exception e) {
            e.printStackTrace();
            resultMap.put("result", "fail");
        }

        return resultMap;
    }
}
