package top.cadros.onlinevotingsystem;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import io.jsonwebtoken.Claims;
import top.cadros.onlinevotingsystem.object.User;
import top.cadros.onlinevotingsystem.service.JwtService;

@SpringBootTest
public class TestJwtService {

    @Test
    public void testJwtService(){
        try{
            String token=JwtService.generateToken(new User("111","111"));
            Claims claim=JwtService.getClaimsFromToken(token);
            assert claim.get("account").equals("111");
            assert claim.get("password").equals("111");
        }catch(Exception e){
            assert false:e.getMessage();
        }
    }

    @Test
    public void testTokenExpired(){
        try{
            String token=JwtService.generateToken(new User("111","111"));
            Thread.sleep(60*60*1000+1000);
            JwtService.getClaimsFromToken(token);
            assert false:"Token should be expired";
        }catch(Exception e){
            assert e.getMessage().equals("JWT expired"):e.getMessage();
        }
    }
}
