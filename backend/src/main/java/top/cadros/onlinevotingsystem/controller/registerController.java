package top.cadros.onlinevotingsystem.controller;

import top.cadros.onlinevotingsystem.object.*;
import top.cadros.onlinevotingsystem.service.DataBase;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>注册控制器</p>
 * <p>用于处理注册请求</p>
 * @Author 高洪森
 */
@RestController
public class registerController {

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> registerHandler(@RequestBody User user) {
        try{
            DataBase.insertUser(user);
            return ResponseEntity.ok(new ApiResponse(20000, "注册成功", null, null));
        }catch(Exception e){
            return ResponseEntity.status(500).body(new ApiResponse(50000, "服务器错误，请联系管理员", null, e.getMessage()));
        }
    }
}
