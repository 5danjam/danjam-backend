package com.danjam.search;

import com.danjam.search.querydsl.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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

        List<DormDto> list = searchService.findAllList();
        if (list.isEmpty()) {
            resultMap.put("result", "fail");
            resultMap.put("dormList", null);
        } else {
            resultMap.put("result", "success");
            resultMap.put("dormList", list);
        }

        System.out.println(searchService.findAllList());

        return ResponseEntity.ok(resultMap);
    }

    @PostMapping("/search")
    public ResponseEntity<Map<String, Object>> showByCondition(@RequestBody SearchDto searchDto) {
        System.out.println(">>>>>>>>>>>>>>searchDto: " + searchDto);
        System.out.println("checkIn:" + searchDto.getCheckIn() + " checkOut:" + searchDto.getCheckOut());

        Map<String, Object> resultMap = new HashMap();

        List<DormDto> list = searchService.findList(searchDto);
        System.out.println("findList: " + list);

        if (list.isEmpty()) {
            resultMap.put("result", "fail");
            resultMap.put("dormList", null);
        } else {
            resultMap.put("result", "success");
            resultMap.put("dormList", list);
        }
        return ResponseEntity.ok(resultMap);
    }

    @PostMapping("/search/filter")
    public ResponseEntity<Map<String, Object>> searchByCondition(@RequestBody FilterDto filterDto) {
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>/search/amenity");
        System.out.println(filterDto);
        Map<String, Object> resultMap = new HashMap();

        List<DormDto> list = searchService.findByFilter(filterDto);
        System.out.println("filterList: " + list);

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
