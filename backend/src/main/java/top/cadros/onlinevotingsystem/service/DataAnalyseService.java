package top.cadros.onlinevotingsystem.service;

import top.cadros.onlinevotingsystem.object.*;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class DataAnalyseService {
    public static VoteAnalyseData analyseVoteByVoteId(int voteId) throws Exception {
        Vote vote=VoteFileService.readVoteFromFile(voteId);
        List<AnswerLog> answerLogs=DataBase.queryAnswerLogsByVoteId(voteId);
        VoteAnalyseData voteAnalyseData=new VoteAnalyseData();
        voteAnalyseData.setAnsweredNumber(answerLogs.size());
        voteAnalyseData.setQuestionAnalyseDatas(new ArrayList<QuestionAnalyseData>());
        if(answerLogs.size()<5){
            throw new IllegalStateException("回答数量不足以产生有效统计");
        }
        for(Question question:vote.getQuestionMap().values()){
            if(question.getQuestion_type()==0||question.getQuestion_type()==2){
                voteAnalyseData.getQuestionAnalyseDatas().add(DataBase.analyseAnswers(vote.getVote_id(),question.getQuestion_id()));
            }
        }
        return voteAnalyseData;
    }
}
