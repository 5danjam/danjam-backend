package com.danjam.users;


import com.danjam.logInSecurity.UserDetail;
import com.danjam.users.Users;
import com.danjam.users.UsersDto;
import com.danjam.users.UsersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/users")
public class UserController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final UsersService usersService;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserController(UsersService usersService, BCryptPasswordEncoder passwordEncoder) {
        this.usersService = usersService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/validate")
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

    @PostMapping("/signUp")
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

    @RequestMapping("/authSuccess")
    public ResponseEntity<Map<String, Object>> authSuccess(@AuthenticationPrincipal UserDetail userDetail) {
        HashMap<String, Object> response = new HashMap<>();
        Users user = userDetail.getUser();

        response.put("result", "success");
        response.put("id", user.getId());
        response.put("nickname", user.getName());
        response.put("role", user.getRole());

        return ResponseEntity.ok(response);
    }

    @RequestMapping("/authFailure")
    public ResponseEntity<Map<String, Object>> authFailure() {
        HashMap<String, Object> response = new HashMap<>();

        response.put("result", "fail");
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

    @GetMapping("/{id}")
    public ResponseEntity<UsersDto> findById(@PathVariable Long id) {
        Users findByUser = usersService.findById(id);
        if (findByUser == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        UsersDto user = UsersDto.fromEntity(findByUser);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @Transactional
    @PatchMapping("/{id}")
    public ResponseEntity<UsersDto> changePassword(@PathVariable Long id, @RequestBody Map<String, String> requestMap) {
        log.info("password: {}", requestMap);
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
    @PatchMapping("/{id}/phone")
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
    @PatchMapping("/{id}/cancel")
    public ResponseEntity<Void> cancelMember(@PathVariable Long id) {
        Users findByUser = usersService.findById(id);
        if (findByUser == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        usersService.cancel(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
