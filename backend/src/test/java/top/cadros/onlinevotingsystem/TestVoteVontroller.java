package top.cadros.onlinevotingsystem;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import top.cadros.onlinevotingsystem.controller.VoteController;
import top.cadros.onlinevotingsystem.object.*;
import top.cadros.onlinevotingsystem.service.VoteFileService;

@SpringBootTest
public class TestVoteVontroller {

    @Test
    public void testStoreQuestions() {
//        try{
//            Map<Integer,Question> entity = new java.util.HashMap<>();
//
//            VoteController voteController = new VoteController();
//            voteController.storeQuestions(entity, 1);
//
//            Vote vote = VoteFileService.readVoteFromFile(1);
//            Map<Integer,Question> questionMap= vote.getQuestionTree().generateQuestionMap(vote.getQuestionTree().getRootQuestionNode(), new HashMap<>());
//            String string = "";
//            for(Question question:questionMap.values()){
//                string+=question.getQuestion_id()+" ";
//            }
//            assert questionMap.size()==7:"\n\n#####\n"+string+"\n#####\n\n";
//        }catch(Exception e){
//            assert false:"\n\n#####\n"+e.getMessage()+"\n"+e.toString()+"\n#####\n\n";
//        }
    }
}
