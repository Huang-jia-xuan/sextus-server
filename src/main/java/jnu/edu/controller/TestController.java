package jnu.edu.controller;

import jnu.edu.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 黄嘉轩
 * @date 2025-02-08 00:12:59
 */

@RestController
@CrossOrigin
public class TestController {
    @Autowired
    AuthService authService;
    @PostMapping("/login")
    public ResponseEntity<Map<String,String>> login(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String password = request.get("password");
        String token = authService.login(username, password);
        Map<String,String> response = new HashMap<>();
        response.put("token",token);
        System.out.println(username);
        return ResponseEntity.ok(response);
    }
}
