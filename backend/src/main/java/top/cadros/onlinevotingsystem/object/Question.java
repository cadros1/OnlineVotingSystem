package top.cadros.onlinevotingsystem.object;

import java.util.ArrayList;
import java.util.Map;

public class Question implements java.io.Serializable{
    private int question_id;
    private String question_text;
    private int question_type;
    private boolean required;
    private Map<Integer,Integer> childrenDependencies;
    private ArrayList<Option> options;

    public Question() {
    }

    public Question(int question_id,  String question_text, int question_type, boolean required, Map<Integer,Integer> childrenDependencies) {
        this.question_id = question_id;
        this.question_text = question_text;
        this.question_type = question_type;
        this.required = required;
        this.childrenDependencies = childrenDependencies;
    }

    public void addOption(Option option) {
        this.options.add(option);
    }

    public void addChildDependency(int childId, int dependentOptionId) {
        this.childrenDependencies.put(childId, dependentOptionId);
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

    public Map<Integer,Integer> getchildrenDependencies() {
        return childrenDependencies;
    }

    public void setChildrenId(Map<Integer,Integer> childrenDependencies) {
        this.childrenDependencies = childrenDependencies;
    }

    public ArrayList<Option> getOptions() {
        return options;
    }

    public void setOptions(ArrayList<Option> options) {
        this.options = options;
    }

    
}
