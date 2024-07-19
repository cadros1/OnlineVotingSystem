package top.cadros.onlinevotingsystem.object;

import java.util.ArrayList;

public class QuestionNode {
    private Question question;
    private ArrayList<QuestionNode> children;

    public QuestionNode(Question question) {
        this.question = question;
        this.children = new ArrayList<>();
    }

    public void addChildren(Question question) {
        children.add(new QuestionNode(question));
    }

    public Question getQuestion() {
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

    public void addChild(QuestionNode child) {
        children.add(child);
    }

    public void removeChild(QuestionNode child) {
        children.remove(child);
    }
}
