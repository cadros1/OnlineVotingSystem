package top.cadros.onlinevotingsystem.controller;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.databind.ObjectMapper;

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
    public ResponseEntity<ApiResponse> sendVote(@RequestParam int vote_id){
        try{
            Vote vote = VoteFileService.readVoteFromFile(vote_id);
            return ResponseEntity.ok(new ApiResponse(20000, "问卷读取成功", "OK", vote));
        }catch(Exception e){
            return ResponseEntity.status(500).body(new ApiResponse(50000, "服务器错误，请联系管理员", null, e.getMessage()));
        }
    }

    @PostMapping("/vote/newvote")
    public ResponseEntity<ApiResponse> createVote(@RequestBody VoteRequestBody voteRequestBody) {
        try{
            int vote_id = DataBase.insertVote(voteRequestBody.getTitle(),
                                              voteRequestBody.getDescription(),
                                              voteRequestBody.getRootQuestionId(),
                                              voteRequestBody.getAccount(),
                                              voteRequestBody.isPublic(),
                                              voteRequestBody.getPublishTime());
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
            newVote.setPublishTime(Instant.now());
            VoteFileService.outputVoteToFile(newVote);
            return ResponseEntity.ok(new ApiResponse(20000, "问卷创建成功", "OK", null));
        }catch(Exception e){
            return ResponseEntity.status(500).body(new ApiResponse(50000, "服务器错误，请联系管理员", "OK", null));
        }
    }

    @PostMapping("/vote/{vote_id}/answer")
    public ResponseEntity<ApiResponse> postAnswer(@RequestBody AnswerRequestBody answerRequestBody) {
        try{//尝试检查问卷是否存在
            DataBase.queryVoteByVoteId(answerRequestBody.getVote_id());
        }catch(NoSuchElementException e){
            return ResponseEntity.status(400).body(new ApiResponse(40001, "问卷不存在", null, null));
        }
        try{//尝试插入回答记录
            DataBase.insertAnswerLog(0, null);
        }catch(DuplicateKeyException e){
            return ResponseEntity.status(400).body(new ApiResponse(40002, "您已经回答过此问卷", null, null));
        }
        try{//尝试插入回答答案
            for(Answer answer: answerRequestBody.getAnswers()){
                DataBase.insertAnswer(answerRequestBody.getVote_id(),
                                      answer.getQuestion_id(),
                                      answerRequestBody.getUserAccount(),
                                      answer.getSelected_option_id(),
                                      answer.getCustom_answer());
            }
        }catch(DuplicateKeyException e){
            return ResponseEntity.status(400).body(new ApiResponse(40003, "answer数据有误", null, null));
        }
        return ResponseEntity.ok(new ApiResponse(20000, "回答提交成功", "OK", null));
    }

    /**
     * 前端使用get请求某个问卷的回答者列表
     * @url /vote/{vote_id}/answer
     */
    @GetMapping("/vote/{vote_id}/answer")
    public ResponseEntity<ApiResponse> getAnswerList(@RequestParam String param) {
        try{
            List<AnswerLog> answerLogs = DataBase.queryAnswerLogsByVoteId(Integer.parseInt(param));
            return ResponseEntity.ok(new ApiResponse(20000, "回答列表获取成功", "OK", answerLogs));
        }catch(NoSuchElementException e){
            return ResponseEntity.status(400).body(new ApiResponse(40004, "请求的问卷没有回答记录", null, null));
        }catch(Exception e){
            return ResponseEntity.status(500).body(new ApiResponse(50000, "服务器错误，请联系管理员", null, null));
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
    Instant publishTime;
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
    public Instant getPublishTime() {
        return publishTime;
    }
    public void setPublishTime(Instant publishTime) {
        this.publishTime = publishTime;
    }
}

class AnswerRequestBody{
    int vote_id;
    String userAccount;
    Answer[] answers;

    //getter and setter
    public int getVote_id() {
        return vote_id;
    }
    public void setVote_id(int vote_id) {
        this.vote_id = vote_id;
    }
    public String getUserAccount() {
        return userAccount;
    }
    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }
    public Answer[] getAnswers() {
        return answers;
    }
    public void setAnswers(Answer[] answers) {
        this.answers = answers;
    }
}
