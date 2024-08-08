package com.danjam.dorm;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

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
}
