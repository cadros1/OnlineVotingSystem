package top.cadros.onlinevotingsystem.object;

import java.time.Instant;
import java.util.Map;

/**
 * <p>问卷类</p>
 * <p>用于存储问卷信息</p>
 * @author 高洪森
 */
public class Vote implements java.io.Serializable {
    private int vote_id;
    private String title;
    private String description;
    private User user;
    private int rootQuestionId;
    private boolean isPublic;
    private Instant publishTime;
    private Map<Integer,Question> questionMap;

    public Vote() {
    }

    public Vote(int vote_id, String title, String description, User user, int rootQuestionId, Instant publishTime, Map<Integer, Question> questionMap) {
        this.vote_id = vote_id;
        this.title = title;
        this.description = description;
        this.user = user;
        this.rootQuestionId = rootQuestionId;
        this.publishTime = publishTime;
        this.questionMap = questionMap;
    }

    public Vote(int vote_id, String title, String description, User user, int rootQuestionId, boolean isPublic, Instant publishTime) {
        this.vote_id = vote_id;
        this.title = title;
        this.description = description;
        this.user = user;
        this.rootQuestionId = rootQuestionId;
        this.isPublic = isPublic;
        this.publishTime = publishTime;
    }

    public Vote(String title, String description, User user) {
        this.title = title;
        this.description = description;
        this.user = user;
    }

    public Vote(int vote_id,User user){
        this.vote_id = vote_id;
        this.user = user;
    }

    // Getters and Setters
    public int getVote_id() {
        return vote_id;
    }

    public void setVote_id(int vote_id) {
        this.vote_id = vote_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Map<Integer, Question> getQuestionMap() {
        return questionMap;
    }

    public int getRootQuestionId() {
        return rootQuestionId;
    }

    public void setRootQuestionId(int rootQuestionId) {
        this.rootQuestionId = rootQuestionId;
    }

    public void setQuestionMap(Map<Integer, Question> questionMap) {
        this.questionMap = questionMap;
    }

    public boolean getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }

    public Instant getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Instant publishTime) {
        this.publishTime = publishTime;
    }

    
}
