package com.danjam.users;


import com.danjam.users.Users;
import com.danjam.users.UsersDto;
import com.danjam.users.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
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
        System.out.println(usersDto.getName());
        System.out.println(usersDto.getPassword());
        System.out.println(usersDto.getEmail());
        System.out.println(usersDto.getPhoneNum());

        // 중복 검사
        List<Users> list = usersService.findAll();
        for (Users user : list) {
            if(user.getEmail().equals(usersDto.getEmail())) {
                resultMap.put("result", "fail");
                return resultMap;
            }
        }

        try{
            usersDto.setPassword(passwordEncoder.encode(usersDto.getPassword()));
            Users users = usersDto.toEntity();
            System.out.println("회원가입 성공");
            System.out.println(users.getName());
            System.out.println(users.getPassword());
            System.out.println(users.getEmail());
            System.out.println(users.getPhoneNum());
            usersService.save(users);
            resultMap.put("result", "success");
        }catch (Exception e) {
            e.printStackTrace();
            resultMap.put("result", "fail");
        }
        return resultMap;
    }

    @RequestMapping("auth/authSuccess")
    public ResponseEntity<Map<String, Object>> authSuccess(Authentication authentication) {
        HashMap<String, Object> response = new HashMap<>();
        Users user = (Users) authentication.getPrincipal();

        response.put("result", "success");
        response.put("id", user.getId());
        response.put("nickname", user.getName());
        response.put("role", user.getRole());

        return ResponseEntity.ok(response);
    }

    @RequestMapping("auth/authFailure")
    public ResponseEntity<Map<String, Object>> authFailure() {
        HashMap<String, Object> response = new HashMap<>();

        response.put("result", "fail");
        return ResponseEntity.ok(response);
    }
}
