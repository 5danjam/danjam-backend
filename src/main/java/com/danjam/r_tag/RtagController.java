package com.danjam.r_tag;

import com.danjam.d_amenity.DamenityAddDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@CrossOrigin
@RequiredArgsConstructor
public class RtagController {

    private final RtagServiceImpl RTAGSERVICE;


    @PostMapping("/rtag/insert")
    public HashMap<String, Object> insert(@RequestBody RtagAddDTO rtagAddDTO) {

        HashMap<String, Object> resultMap = new HashMap();

        System.out.println("damenityAddDTO: "+ rtagAddDTO);

        try {
            RTAGSERVICE.insert(rtagAddDTO);
            resultMap.put("result", "success");

        } catch (Exception e) {
            e.printStackTrace();
            resultMap.put("result", "fail");
        }

        return resultMap;
    }
}
