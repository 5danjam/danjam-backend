package com.danjam.search;

import com.danjam.search.querydsl.*;
import com.querydsl.core.Tuple;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    /*@GetMapping("/showAll")
    public ResponseEntity<Map<String, Object>> getDorms(Pageable pageable) {
        Map<String, Object> resultMap = new HashMap();
        Page<DormDto> list = (Page<DormDto>) searchService.findAllList();
        if (list.isEmpty()) {
            resultMap.put("result", "fail");
            resultMap.put("dormList", null);
        } else {
            resultMap.put("result", "success");
            resultMap.put("dormList", list);
        }
        return ResponseEntity.ok(resultMap);
    }*/
//    @GetMapping("/showAll")
//    public ResponseEntity<Map<String, Object>> findAllList() {
//        Map<String, Object> resultMap = new HashMap();
//
//        List<DormDto> list = searchService.findAllList();
//        // List<DormDto> list = searchService.findAllList();
////        List<Tuple> list = searchService.findAllList();
//        System.out.println("findAllList: "+list);
//        if (list.isEmpty()) {
//            resultMap.put("result", "fail");
//            resultMap.put("dormList", null);
//        } else {
//            resultMap.put("result", "success");
//            resultMap.put("dormList", list);
//        }
//
//        System.out.println(searchService.findAllList());
//
//        return ResponseEntity.ok(resultMap);
//    }

    @GetMapping("/showAll")
    public ResponseEntity<Map<String, Object>> findAllList(Pageable pageable) {
        System.out.println("pageable: " + pageable);
        Map<String, Object> resultMap = new HashMap();

        Page<DormDto> list = searchService.findAllList(pageable);
        // List<DormDto> list = searchService.findAllList();
//        List<Tuple> list = searchService.findAllList();
        System.out.println("findAllList: "+list);
        if (list.isEmpty()) {
            resultMap.put("result", "fail");
            resultMap.put("dormList", null);
        } else {
            resultMap.put("result", "success");
            resultMap.put("dormList", list);
        }

        System.out.println("findAllList: " + searchService.findAllList(pageable));

        return ResponseEntity.ok(resultMap);
    }

    @PostMapping("/search")
    public ResponseEntity<Map<String, Object>> findList(@RequestBody SearchDto searchDto) {
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
    public ResponseEntity<Map<String, Object>> findByFilter(@RequestBody FilterDto filterDto) {
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
