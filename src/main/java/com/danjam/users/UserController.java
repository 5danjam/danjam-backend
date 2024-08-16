package com.danjam.users;


import com.danjam.logInSecurity.UserDetail;
import com.danjam.users.querydsl.UsersListDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/users/")
public class UserController {
    private final UsersService usersService;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UsersService usersService, BCryptPasswordEncoder passwordEncoder) {
        this.usersService = usersService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("validate")
    public Map<String, Object> validate(@RequestBody String email) {
        Map<String, Object> resultMap = new HashMap<>();
        // 값 이상하게 넘어와서 replace 해줌
        email = email.replace("%40", "@");
        email = email.replace("=", "");

        if (usersService.findByEmail(email) == null) {
            resultMap.put("result", "success");
        } else {
            resultMap.put("result", "fail");
        }
        return resultMap;
    }

    @PostMapping("signUp")
    public HashMap<String, Object> signUp(@RequestBody UsersDto usersDto) {
        HashMap<String, Object> resultMap = new HashMap<>();
        System.out.println("회원가입 접속");

        try{
            usersDto.setPassword(passwordEncoder.encode(usersDto.getPassword()));
            System.out.println("회원가입 성공");
            usersService.save(usersDto.toEntity());
            resultMap.put("result", "success");
        }catch (Exception e) {
            e.printStackTrace();
            resultMap.put("result", "fail");
        }
        return resultMap;
    }

    @RequestMapping("authSuccess")
    public ResponseEntity<Map<String, Object>> authSuccess(@AuthenticationPrincipal UserDetail userDetail) {
        HashMap<String, Object> response = new HashMap<>();
        Users user = userDetail.getUser();

        response.put("result", "success");
        response.put("id", user.getId());
        response.put("name", user.getName());
        response.put("role", user.getRole());

        return ResponseEntity.ok(response);
    }

    @RequestMapping("authFailure")
    public ResponseEntity<Map<String, Object>> authFailure() {
        HashMap<String, Object> response = new HashMap<>();

        response.put("result", "fail");
        return ResponseEntity.ok(response);
    }

    @RequestMapping("logoutSuccess")
    public ResponseEntity<Map<String, Object>> logoutSuccess() {
        HashMap<String, Object> response = new HashMap<>();

        response.put("result", "success");
        return ResponseEntity.ok(response);
    }


    @GetMapping("UsersList")
    public ResponseEntity<List<Users>> findUsersList() {
        List<Users> userList = usersService.findUsersList();

        return new ResponseEntity<>(userList, HttpStatus.OK);
    }
}
