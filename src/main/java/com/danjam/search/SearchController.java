package com.danjam.search;

import com.danjam.search.querydsl.DormDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequiredArgsConstructor
public class SearchController {
    private final SearchService searchService;

    @PostMapping("/town/list")
    public ResponseEntity<Map<String, Object>> showTownList(@RequestBody SearchDto searchDto) {
        Map<String, Object> resultMap = new HashMap();
        List<String> list = searchService.findByCity(searchDto.getCity());

        System.out.println(searchDto.getCity() + " townList: " + list);

        if (list.isEmpty()) {
            resultMap.put("result", "fail");
            resultMap.put("townList", null);
        } else {
            resultMap.put("result", "success");
            resultMap.put("townList", list);
        }
        return ResponseEntity.ok(resultMap);
    }

    @GetMapping("/showAll")
    public ResponseEntity<Map<String, Object>> showList() {
        Map<String, Object> resultMap = new HashMap();

        resultMap.put("result", "success");
        resultMap.put("dormList", searchService.findList());
        System.out.println(searchService.findList());

        return ResponseEntity.ok(resultMap);
    }

    @PostMapping("/search")
    public ResponseEntity<Map<String, Object>> showByCondition(@RequestBody SearchDto searchDto) {
        System.out.println(">>>>>>>>>>>>>>searchDto: "+searchDto);
//        System.out.println("localdatetime: "+ LocalDate.now());

        Map<String, Object> resultMap = new HashMap();

        List<DormDto> list = searchService.cheapRoom(searchDto.getCity(), searchDto.getCheckIn(), searchDto.getCheckOut(), searchDto.getPerson());
        System.out.println("cheapRoom: " + list);

        if (list.isEmpty()) {
            resultMap.put("result", "fail");
            resultMap.put("dormList", null);
        } else {
            resultMap.put("result", "success");
            resultMap.put("dormList", list);
        }
        return ResponseEntity.ok(resultMap);
    }

    @PostMapping("/search/condition")
    public ResponseEntity<Map<String, Object>> searchByCondition(@RequestBody SearchDto searchDto) {
        System.out.println(searchDto);
        Map<String, Object> resultMap = new HashMap();

        List<DormDto> list = searchService.findByPerson(searchDto.getCity(), searchDto.getPerson());
        System.out.println(list);

        if (list.isEmpty()) {
            resultMap.put("result", "fail");
            resultMap.put("dormList", null);
        } else {
            resultMap.put("result", "success");
            resultMap.put("dormList", list);
        }
        return ResponseEntity.ok(resultMap);
    }
}
