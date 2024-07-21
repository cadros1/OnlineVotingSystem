package top.cadros.onlinevotingsystem.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

import top.cadros.onlinevotingsystem.object.*;
import top.cadros.onlinevotingsystem.service.DataBase;
import top.cadros.onlinevotingsystem.service.VoteFileService;


@RestController
public class VoteController {

    @PostMapping("/vote/{vote_id}")
    public ResponseEntity<ApiResponse> storeVote(@RequestBody Vote vote) {
        try{
            VoteFileService.outputVoteToFile(vote);
            return ResponseEntity.ok(new ApiResponse(20000, "问卷保存成功", "OK", null));
        }catch(Exception e){
            return ResponseEntity.status(500).body(new ApiResponse(50000, "服务器错误，请联系管理员", "OK", null));
        }
    }

    @GetMapping("/vote/{vote_id}")
    public ResponseEntity<ApiResponse> sendVote(@RequestBody int vote_id){
        try{
            Vote vote = VoteFileService.readVoteFromFile(vote_id);
            return ResponseEntity.ok(new ApiResponse(20000, "问卷读取成功", "OK", vote));
        }catch(Exception e){
            return ResponseEntity.status(500).body(new ApiResponse(50000, "服务器错误，请联系管理员", "OK", null));
        }
    }

    @PostMapping("/vote/newvote")
    public ResponseEntity<ApiResponse> createVote(@RequestBody Vote vote){
        try{
            Vote newVote = DataBase.insertVote(vote);
            VoteFileService.outputVoteToFile(newVote);
            return ResponseEntity.ok(new ApiResponse(20000, "问卷创建成功", "OK", null));
        }catch(Exception e){
            return ResponseEntity.status(500).body(new ApiResponse(50000, "服务器错误，请联系管理员", "OK", null));
        }
    }

    
    @GetMapping("/vote")
    public String sendVoteList(@RequestParam int page) {
        return new String();//TODO
    }
    
}
