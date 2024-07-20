package top.cadros.onlinevotingsystem.controller;

import top.cadros.onlinevotingsystem.object.*;
import top.cadros.onlinevotingsystem.service.DataBase;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class registerController {

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> registerHandler(@RequestBody User user) {
        try{
            DataBase.insertUser(user);
            return ResponseEntity.ok(new ApiResponse(20000, "注册成功", null, null));
        }catch(Exception e){
            return ResponseEntity.status(500).body(new ApiResponse(50000, e.getMessage(), null, null));
        }
    }
}
