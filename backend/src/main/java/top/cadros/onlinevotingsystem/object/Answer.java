package top.cadros.onlinevotingsystem.object;

public class Answer {
    private int answer_id;
    private int question_id;
    private String user_account;
    private int selected_option_id;
    private String custom_answer;

    public Answer(int answer_id, int question_id, String user_account, int selected_option_id, String custom_answer) {
        this.answer_id = answer_id;
        this.question_id = question_id;
        this.user_account = user_account;
        this.selected_option_id = selected_option_id;
        this.custom_answer = custom_answer;
    }

    public Answer(int question_id, String user_account, int selected_option_id, String custom_answer) {
        this.question_id = question_id;
        this.user_account = user_account;
        this.selected_option_id = selected_option_id;
        this.custom_answer = custom_answer;
    }

    public int getAnswer_id() {
        return answer_id;
    }

    public void setAnswer_id(int answer_id) {
        this.answer_id = answer_id;
    }

    public int getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(int question_id) {
        this.question_id = question_id;
    }

    public String getUser_account() {
        return user_account;
    }

    public void setUser_account(String user_account) {
        this.user_account = user_account;
    }

    public int getSelected_option_id() {
        return selected_option_id;
    }

    public void setSelected_option_id(int selected_option_id) {
        this.selected_option_id = selected_option_id;
    }

    public String getCustom_answer() {
        return custom_answer;
    }

    public void setCustom_answer(String custom_answer) {
        this.custom_answer = custom_answer;
    }
}
