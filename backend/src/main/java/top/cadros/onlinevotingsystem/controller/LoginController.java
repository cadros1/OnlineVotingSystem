package top.cadros.onlinevotingsystem.controller;

import org.springframework.web.bind.annotation.RestController;

import top.cadros.onlinevotingsystem.object.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
public class LoginController{

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> postMethodName(@RequestBody User user) {
        try{
            User entity = top.cadros.onlinevotingsystem.service.DataBase.queryUserByAccount(user);
            if(entity != null){
                return ResponseEntity.ok(new ApiResponse(20000, "登录成功", entity));
            }else{
                return ResponseEntity.status(401).body(new ApiResponse(40101, "账号或密码不正确", entity));
            }
        }catch(Exception e){
            return ResponseEntity.status(500).body(new ApiResponse(50000, e.getMessage(), null));
        }
    }
}