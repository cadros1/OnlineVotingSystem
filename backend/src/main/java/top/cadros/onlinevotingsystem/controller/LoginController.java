package top.cadros.onlinevotingsystem.controller;

import org.springframework.web.bind.annotation.RestController;

import top.cadros.onlinevotingsystem.object.User;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
public class LoginController{

    @PostMapping("/login")
    public ResponseEntity<?> postMethodName(@RequestBody User user) {
        try{
            User entity = top.cadros.onlinevotingsystem.service.DataBase.queryUserByAccount(user);
            if(entity != null){
                return ResponseEntity.ok(entity);
            }else{
                return ResponseEntity.badRequest().body("账号或密码错误");
            }
        }catch(Exception e){
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }
}