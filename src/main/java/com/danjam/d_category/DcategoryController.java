package com.danjam.d_category;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
@CrossOrigin
@RequiredArgsConstructor
public class DcategoryController {

    private final DcategoryServiceImpl DCATEGORYSERVICE;

    @GetMapping("/dcategory/list")
    public HashMap<String, Object> list(){
        HashMap<String, Object> resultMap = new HashMap();
        List<DcategoryListDTO> dcategoryList = DCATEGORYSERVICE.list();

        try {
            resultMap.put("dcategoryList", dcategoryList);
            resultMap.put("result", "success");

        } catch (Exception e) {
            e.printStackTrace();
            resultMap.put("result", "fail");
        }
        return resultMap;
    }
}
