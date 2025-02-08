package jnu.edu.controller;

import jnu.edu.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 黄嘉轩
 * @date 2025-02-08 17:38:16
 */
@RestController
@CrossOrigin
public class AuthController {
    @Autowired
    AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<Map<String,String>> login(@RequestBody Map<String, String> request) {
        System.out.println(request);
        try{
            authService.register(request.get("username"), request.get("password"));
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new HashMap<>());
        }
        Map<String,String> response = new HashMap<>();
        response.put("message","success");
        return ResponseEntity.ok(response);
    }
}
