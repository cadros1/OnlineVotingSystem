package top.cadros.onlinevotingsystem.object;

import java.time.Instant;

/**
 * <p>回答记录数据类</p>
 * <p>用于存储回答记录信息</p>
 * @auther 高洪森
 */
public class AnswerLog implements java.io.Serializable {
    int vote_id;
    String userAccount;
    Instant answerTime;

    public AnswerLog(int vote_id, String userAccount, Instant answerTime) {
        this.vote_id = vote_id;
        this.userAccount = userAccount;
        this.answerTime = answerTime;
    }

    // Getters and Setters
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

    public Instant getAnswerTime() {
        return answerTime;
    }

    public void setAnswerTime(Instant answerTime) {
        this.answerTime = answerTime;
    }
}
