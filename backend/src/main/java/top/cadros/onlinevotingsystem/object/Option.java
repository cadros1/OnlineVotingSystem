package top.cadros.onlinevotingsystem.object;

public class Option implements java.io.Serializable{
    private int option_id;
    private String option_text;
    private boolean is_other;

    public Option() {
    }

    public Option(int option_id,  String option_text, boolean is_other) {
        this.option_id = option_id;
        this.option_text = option_text;
        this.is_other = is_other;
    }

    public int getOption_id() {
        return option_id;
    }

    public void setOption_id(int option_id) {
        this.option_id = option_id;
    }

    public String getOption_text() {
        return option_text;
    }

    public void setOption_text(String option_text) {
        this.option_text = option_text;
    }

    public boolean isIs_other() {
        return is_other;
    }

    public void setIs_other(boolean is_other) {
        this.is_other = is_other;
    }
}
