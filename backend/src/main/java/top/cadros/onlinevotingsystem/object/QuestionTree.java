package top.cadros.onlinevotingsystem.object;

import java.util.Map;

public class QuestionTree implements java.io.Serializable{
    private QuestionNode rootQuestionNode;

    public QuestionTree() {
    }

    public QuestionTree(Map<Integer,Question> questions){
        Map<Integer,QuestionNode> questionNodes=new java.util.HashMap<>();
        for(Question question:questions.values()){
            questionNodes.put(question.getQuestion_id(),new QuestionNode(question));
        }
        rootQuestionNode=questionNodes.get(1);
        for(QuestionNode questionNode:questionNodes.values()){
            for(Integer childId:questionNode.getData().getchildrenDependencies().keySet()){
                questionNode.addChildNode(questionNodes.get(childId));
            }
        }
    }

    public Map<Integer,Question> generateQuestionMap(QuestionNode node,Map<Integer,Question> questionMap){
        questionMap.put(node.getData().getQuestion_id(),node.getData());
        for(QuestionNode child:node.getChildren()){
            generateQuestionMap(child,questionMap);
        }
        return questionMap;
    }

    // Getters and Setters
    public QuestionNode getRootQuestionNode() {
        return rootQuestionNode;
    }

    public void setRootQuestionNode(QuestionNode rootQuestionNode) {
        this.rootQuestionNode = rootQuestionNode;
    }
}
