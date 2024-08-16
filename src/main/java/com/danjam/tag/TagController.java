package com.danjam.tag;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
@CrossOrigin
@RequiredArgsConstructor
public class TagController {

    private final TagServiceImpl TAGSERVICE;

    @GetMapping("/tag/list")
    public HashMap<String, Object> list(){
        HashMap<String, Object> resultMap = new HashMap();
        List<TagDto> tagList = TAGSERVICE.list();
        try {
            resultMap.put("tagList", tagList);
            resultMap.put("result", "success");

        } catch (Exception e) {
            e.printStackTrace();
            resultMap.put("result", "fail");
        }
        return resultMap;
    }
}
