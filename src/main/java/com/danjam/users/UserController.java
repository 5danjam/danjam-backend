package com.danjam.users;


import com.danjam.logInSecurity.UserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
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
        response.put("email", user.getEmail());
        response.put("phoneNum", user.getPhoneNum());
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

    @GetMapping
    public ResponseEntity<List<UsersDto>> findAll() {
        List<UsersDto> users = usersService.findAll()
                .stream()
                .map(UsersDto::fromEntity)
                .toList();

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<UsersDto> findById(@PathVariable Long id) {
        Users findByUser = usersService.findById(id);
        if (findByUser == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        UsersDto user = UsersDto.fromEntity(findByUser);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @Transactional
    @PatchMapping("{id}")
    public ResponseEntity<UsersDto> changePassword(@PathVariable Long id, @RequestBody Map<String, String> requestMap) {
        Users findByUser = usersService.findById(id);
        if (findByUser == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        findByUser.setPassword(passwordEncoder.encode(requestMap.get("password")));
        usersService.save(findByUser);
        UsersDto user = UsersDto.fromEntity(findByUser);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @Transactional
    @PatchMapping("{id}/phone")
    public ResponseEntity<Void> changePhone(@PathVariable Long id, @RequestBody Map<String, Integer> requestMap) {
        Users findByUser = usersService.findById(id);
        if (findByUser == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        findByUser.setPhoneNum(requestMap.get("phoneNum"));
        usersService.save(findByUser);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Transactional
    @PatchMapping("{id}/cancel")
    public ResponseEntity<Void> cancelMember(@PathVariable Long id) {
        Users findByUser = usersService.findById(id);
        if (findByUser == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        usersService.cancel(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("UsersList")
    public ResponseEntity<List<Users>> findUsersList() {
        List<Users> userList = usersService.findUsersList();

        return new ResponseEntity<>(userList, HttpStatus.OK);
    }
}
