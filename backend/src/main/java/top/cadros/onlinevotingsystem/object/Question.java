package top.cadros.onlinevotingsystem.object;

import java.util.ArrayList;

/**
 * <p>问题数据类</p>
 * <p>用于存储问题信息</p>
 * @Author 高洪森
 */
public class Question implements java.io.Serializable{
    private int question_id;
    private String question_text;
    private int question_type;
    private boolean required;
    private ArrayList<String> options;
    private ArrayList<Integer> jumpLogic;
    private boolean hasOther;

    public Question() {
    }

    public Question(int question_id,  String question_text, int question_type, boolean required) {
        this.question_id = question_id;
        this.question_text = question_text;
        this.question_type = question_type;
        this.required = required;
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

    public ArrayList<String> getOptions() {
        return options;
    }

    public void setOptions(ArrayList<String> options) {
        this.options = options;
    }

    public ArrayList<Integer> getJumpLogic() {
        return jumpLogic;
    }

    public void setJumpLogic(ArrayList<Integer> jumpLogic) {
        this.jumpLogic = jumpLogic;
    }

    public boolean isHasOther() {
        return hasOther;
    }

    public void setHasOther(boolean hasother) {
        this.hasOther = hasother;
    }
}
