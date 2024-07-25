package top.cadros.onlinevotingsystem.object;

/**
 * <p>问题分析数据类</p>
 * <p>用于存储问题分析数据</p>
 * @author 高洪森
 */
public class QuestionAnalyseData implements java.io.Serializable{
    int questionId;
    String[] optionText;
    int[] count;

    public QuestionAnalyseData() {
    }

    public QuestionAnalyseData(String[] optionText, int[] count) {
        this.optionText = optionText;
        this.count = count;
    }
    public String[] getOptionText() {
        return optionText;
    }
    public void setOptionText(String[] optionText) {
        this.optionText = optionText;
    }
    public int[] getCount() {
        return count;
    }
    public void setCount(int[] count) {
        this.count = count;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }
}
