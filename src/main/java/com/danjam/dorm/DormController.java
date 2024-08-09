package com.danjam.dorm;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/dorm")
public class DormController {

    private final DormServiceImpl DORMSERVICE;

    @PostMapping("/insert")
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

    @GetMapping
    public ResponseEntity<Page<DormDTO>> getDorms(@RequestParam(value = "city", required = false) String city,
                                                  @RequestParam(value = "person", required = false) Integer person,
                                                  @RequestParam(value = "minPrice", required = false) Integer minPrice,
                                                  @RequestParam(value = "maxPrice", required = false) Integer maxPrice,
                                                  @RequestParam(value = "type", required = false) String type,
                                                  Pageable pageable) {
        Page<DormDTO> dorms = DORMSERVICE.searchDorms(pageable);
        return ResponseEntity.ok(dorms);
    }
}
