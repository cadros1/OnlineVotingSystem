package top.cadros.onlinevotingsystem.object;

import java.util.ArrayList;

public class QuestionNode implements java.io.Serializable{
    private Question question;
    private ArrayList<QuestionNode> children;

    public QuestionNode() {
    }

    public QuestionNode(Question question) {
        this.question = question;
        this.children = new ArrayList<>();
    }

    public void addChildNode(QuestionNode node){
        this.children.add(node);
    }


    // Getters and Setters
    public Question getData() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public ArrayList<QuestionNode> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<QuestionNode> children) {
        this.children = children;
    }
}
