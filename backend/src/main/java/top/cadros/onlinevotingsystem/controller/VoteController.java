package top.cadros.onlinevotingsystem.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import top.cadros.onlinevotingsystem.object.*;
import top.cadros.onlinevotingsystem.service.DataBase;
import top.cadros.onlinevotingsystem.service.VoteFileService;


@Controller
public class VoteController {

    @PostMapping("/vote/{vote_id}")
    public ResponseEntity<ApiResponse> storeQuestions(@RequestBody Map<Integer,Question> entity, int vote_id) {
        try{
            Vote vote = DataBase.queryVoteByVoteId(vote_id);
            vote.setQuestionTree(new QuestionTree(entity));
            VoteFileService.outputVoteToFile(vote);
            return ResponseEntity.ok(new ApiResponse(20000, "问卷保存成功", "OK", null));
        }catch(Exception e){
            return ResponseEntity.status(500).body(new ApiResponse(50000, "服务器错误，请联系管理员", "OK", null));
        }
    }
}
