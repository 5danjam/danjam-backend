package com.danjam.search;

import com.danjam.search.querydsl.AmenityDto;
import com.danjam.search.querydsl.BookingDto;
import com.danjam.search.querydsl.DormDto;
import com.danjam.search.querydsl.SearchRepoImpl;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.CollectionIdType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequiredArgsConstructor
public class SearchController {
    private final SearchService searchService;
    private final SearchRepoImpl searchRepoImpl;

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
        System.out.println(">>>>>>>>>>>>>>searchDto: " + searchDto);
        System.out.println("checkIn: " + searchDto.getCheckIn());
        searchDto.setCheckIn(searchDto.getCheckIn().withHour(15).withMinute(0).withSecond(0).withNano(0));
        searchDto.setCheckOut(searchDto.getCheckOut().withHour(11).withMinute(0).withSecond(0).withNano(0));
        System.out.println("checkIn:" + searchDto.getCheckIn() + " checkOut:" + searchDto.getCheckOut());

        Map<String, Object> resultMap = new HashMap();

        List<DormDto> list = searchService.cheapRoom(searchDto);
//        List<DormDto> list = searchService.findList();
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

    @PostMapping("/search/amenity")
//    public ResponseEntity<Map<String, Object>> searchByCondition(@RequestBody List<AmenityDto> amenities) {
    public ResponseEntity<Map<String, Object>> searchByCondition(/*@RequestParam(name = "search") SearchDto searchDto,*/
            /*@RequestParam(name = "amenity") List<AmenityDto> amenities,*/
//                                                             @RequestParam(name = "town") List<String> cities
            @RequestBody List<String> cities) {
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>/search/amenity");
//        System.out.println(searchDto.toString());
//        System.out.println(amenities);
        System.out.println(cities);
        Map<String, Object> resultMap = new HashMap();

//        List<DormDto> list = searchService.findByAmenity(amenities);
        List<DormDto> list = searchService.findList();

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
