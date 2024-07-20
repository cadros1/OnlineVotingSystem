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
        try{
            Map<Integer,Question> entity = new java.util.HashMap<>();
            Question question1=new Question(1,"question_text", 1, true, new HashMap<>());
            Question question2=new Question(2,"question_text", 1, true, new HashMap<>());
            Question question3=new Question(3,"question_text", 1, true, new HashMap<>());
            Question question4=new Question(4,"question_text", 1, true, new HashMap<>());
            Question question5=new Question(5,"question_text", 1, true, new HashMap<>());
            Question question6=new Question(6,"question_text", 1, true, new HashMap<>());
            Question question7=new Question(7,"question_text", 1, true, new HashMap<>());

            question1.addChildDependency(2, 0);
            question2.addChildDependency(3, 0);
            question3.addChildDependency(4, 1);
            question3.addChildDependency(5, 2);
            question4.addChildDependency(6, 0);
            question5.addChildDependency(7, 0);

            entity.put(1, question1);
            entity.put(2, question2);
            entity.put(3, question3);
            entity.put(4, question4);
            entity.put(5, question5);
            entity.put(6, question6);
            entity.put(7, question7);

            VoteController voteController = new VoteController();
            voteController.storeQuestions(entity, 1);

            Vote vote = VoteFileService.readVoteFromFile(1);
            Map<Integer,Question> questionMap= vote.getQuestionTree().generateQuestionMap(vote.getQuestionTree().getRootQuestionNode(), new HashMap<>());
            String string = "";
            for(Question question:questionMap.values()){
                string+=question.getQuestion_id()+" ";
            }
            assert questionMap.size()==7:"\n\n#####\n"+string+"\n#####\n\n";
        }catch(Exception e){
            assert false:"\n\n#####\n"+e.getMessage()+"\n"+e.toString()+"\n#####\n\n";
        }
    }
}
