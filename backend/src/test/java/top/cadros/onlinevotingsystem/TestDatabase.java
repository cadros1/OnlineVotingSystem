package top.cadros.onlinevotingsystem;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import top.cadros.onlinevotingsystem.object.User;
import top.cadros.onlinevotingsystem.service.DataBase;

@SpringBootTest
public class TestDatabase {
    @Test
    public void testQueryVotesByAccount() {
        try{
            assert DataBase.queryVotesByAccount(new User("111","高洪森")).size()!=0;
        }catch(Exception e){
            assert false:"\n#####\n"+e.getMessage()+"\n"+e.getCause()+"\n#####\n";
        }
    }
}
