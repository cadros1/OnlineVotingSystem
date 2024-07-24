package top.cadros.onlinevotingsystem.object;

public class Answer {
    private int vote_id;
    private int question_id;
    private String user_account;
    private int selected_option_id;
    private String custom_answer;

    public Answer() {
    }

    public Answer(int vote_id, int question_id, String user_account, int selected_option_id, String custom_answer) {
        this.vote_id=vote_id;
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

    // Getters and Setters
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

    public int getVote_id() {
        return vote_id;
    }

    public void setVote_id(int vote_id) {
        this.vote_id = vote_id;
    }
}
