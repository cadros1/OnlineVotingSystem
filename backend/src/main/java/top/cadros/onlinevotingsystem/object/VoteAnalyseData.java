package top.cadros.onlinevotingsystem.object;

import java.util.List;

/**
 * <p>问卷分析数据类</p>
 * <p>用于存储问卷分析数据</p>
 * @author 高洪森
 */
public class VoteAnalyseData implements java.io.Serializable{
    Vote vote;
    int answeredNumber;
    List<QuestionAnalyseData> questionAnalyseDatas;

    public int getAnsweredNumber() {
        return answeredNumber;
    }

    public void setAnsweredNumber(int answeredNumber) {
        this.answeredNumber = answeredNumber;
    }

    public List<QuestionAnalyseData> getQuestionAnalyseDatas() {
        return questionAnalyseDatas;
    }

    public void setQuestionAnalyseDatas(List<QuestionAnalyseData> questionAnalyseDatas) {
        this.questionAnalyseDatas = questionAnalyseDatas;
    }

    public Vote getVote() {
        return vote;
    }

    public void setVote(Vote vote) {
        this.vote = vote;
    }
}
