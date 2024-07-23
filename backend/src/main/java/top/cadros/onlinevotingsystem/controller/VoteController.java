package top.cadros.onlinevotingsystem.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

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
    public ResponseEntity<ApiResponse> createVote(@RequestBody VoteRequestBody voteRequestBody) {
        try{
            int vote_id = DataBase.insertVote(voteRequestBody.getTitle(), voteRequestBody.getDescription(), voteRequestBody.getRootQuestionId(), voteRequestBody.getAccount(), voteRequestBody.isPublic());
            Vote newVote=new Vote();
            ObjectMapper mapper = new ObjectMapper();
            List<List<String>> keyValuePairs = mapper.readValue(voteRequestBody.getQuestionMap(), List.class);
            Map<Integer, Question> map = new LinkedHashMap<>();
            for (List<String> pair : keyValuePairs) {
                map.put(Integer.parseInt(pair.get(0)), mapper.convertValue(pair.get(1), Question.class));
            }
            newVote.setVote_id(vote_id);
            newVote.setTitle(voteRequestBody.getTitle());
            newVote.setDescription(voteRequestBody.getDescription());
            newVote.setRootQuestionId(voteRequestBody.getRootQuestionId());
            newVote.setQuestionMap(map);
            newVote.setUser(DataBase.queryUserByAccount(voteRequestBody.getAccount()));
            newVote.setPublic(voteRequestBody.isPublic());
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

class VoteRequestBody{
    String title;
    String description;
    int rootQuestionId;
    String questionMap;
    String account;
    boolean isPublic;
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String Description) {
        this.description = Description;
    }
    public int getRootQuestionId() {
        return rootQuestionId;
    }
    public void setRootQuestionId(int rootQuestionId) {
        this.rootQuestionId = rootQuestionId;
    }
    public String getAccount() {
        return account;
    }
    public void setAccount(String account) {
        this.account = account;
    }
    public String getQuestionMap() {
        return questionMap;
    }
    public void setQuestionMap(String questionMap) {
        this.questionMap = questionMap;
    }
    public boolean isPublic() {
        return isPublic;
    }
    public void setPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }
}
