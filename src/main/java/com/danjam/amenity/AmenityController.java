package com.danjam.amenity;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
@CrossOrigin
@RequiredArgsConstructor
public class AmenityController {

    private final AmenityServiceImpl AMENITYSERVICE;

    @GetMapping("/amenity/list")
    public HashMap<String, Object> list() {
        HashMap<String, Object> resultMap = new HashMap();
        List<AmenityListDTO> amenityList = AMENITYSERVICE.list();
      
        try {
            resultMap.put("amenityList", amenityList);
            resultMap.put("result", "success");

        } catch (Exception e) {
            e.printStackTrace();
            resultMap.put("result", "fail");
        }
        return resultMap;
    }
}
