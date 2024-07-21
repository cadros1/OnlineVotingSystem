package top.cadros.onlinevotingsystem.object;

import java.util.ArrayList;

public class Question implements java.io.Serializable{
    private int question_id;
    private String question_text;
    private int question_type;
    private boolean required;
    private int nextQuestionId;
    private ArrayList<Option> options;

    public Question() {
    }

    public Question(int question_id,  String question_text, int question_type, boolean required) {
        this.question_id = question_id;
        this.question_text = question_text;
        this.question_type = question_type;
        this.required = required;
    }

    public void addOption(Option option) {
        this.options.add(option);
    }


    // Getters and Setters
    public int getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(int question_id) {
        this.question_id = question_id;
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
    public ArrayList<Option> getOptions() {
        return options;
    }

    public void setOptions(ArrayList<Option> options) {
        this.options = options;
    }

    public int getNextQuestionId() {
        return nextQuestionId;
    }

    public void setNextQuestionId(int nextQuestionId) {
        this.nextQuestionId = nextQuestionId;
    }
}
