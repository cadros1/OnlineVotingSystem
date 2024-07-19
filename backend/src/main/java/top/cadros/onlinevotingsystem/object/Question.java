package top.cadros.onlinevotingsystem.object;

import java.util.ArrayList;

public class Question {
    private int question_id;
    private int vote_id;
    private String question_text;
    private int question_type;
    private boolean required;
    private ArrayList<Option> options;

    public Question(int question_id, int vote_id, String question_text, int question_type, boolean required, int sequence) {
        this.question_id = question_id;
        this.vote_id = vote_id;
        this.question_text = question_text;
        this.question_type = question_type;
        this.required = required;
    }

    public Question(int vote_id, String question_text, int question_type, boolean required, int sequence) {
        this.vote_id = vote_id;
        this.question_text = question_text;
        this.question_type = question_type;
        this.required = required;
    }

    public void addOptions(Option option) {
        this.options.add(option);
    }

    public int getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(int question_id) {
        this.question_id = question_id;
    }

    public int getVote_id() {
        return vote_id;
    }

    public void setVote_id(int vote_id) {
        this.vote_id = vote_id;
    }

    public String getQuestion_text() {
        return question_text;
    }

    public void setQuestion_text(String question_text) {
        this.question_text = question_text;
    }

    public int getQuestion_type() {
        return question_type;
    }

    public void setQuestion_type(int question_type) {
        this.question_type = question_type;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }
}
