package top.cadros.onlinevotingsystem.service;

import top.cadros.onlinevotingsystem.object.*;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.Instant;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

@Component
public class DataBase {
    private static JdbcTemplate jdbcTemplate;

    private DataBase(JdbcTemplate jdbcTemplate) {
        DataBase.jdbcTemplate = jdbcTemplate;
    }

    public static User queryUserForLogin(User user) throws Exception {
        String sql = "SELECT * FROM users WHERE account = ? AND password = ?";
        RowMapper<User> rowMapper = (rs, rowNum) -> new User(rs.getString("account"), rs.getString("password"), rs.getString("username"));
        
        List<User> users = jdbcTemplate.query(sql, rowMapper, user.getAccount(), user.getPassword());
        
        if (users.isEmpty()) {
            return null;
        } else {
            return users.get(0);
        }
    }

    public static void insertUser(User user) throws Exception {
        String sql = "INSERT INTO users(account, password, username) VALUES(?, ?, ?)";
        jdbcTemplate.update(sql, user.getAccount(), user.getPassword(), user.getUsername());
    }

    public static User queryUserByAccount(String account) throws Exception {
        String sql = "SELECT * FROM users WHERE account = ?";
        RowMapper<User> rowMapper = (rs, rowNum) -> new User(rs.getString("account"), rs.getString("password"), rs.getString("username"));
        
        List<User> users = jdbcTemplate.query(sql, rowMapper, account);
        if (users.isEmpty()) {
            throw new Exception("此用户不存在");
        }
        return users.get(0);
    }

    public static int insertVote(String vote_title, String vote_descruption, int root_question_id, String user_account, boolean isPublic, Instant publishTime) throws Exception {
        final String sql = "INSERT INTO votes(title, description, user_account, root_question_id, is_public, publish_time) VALUES(?, ?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, vote_title);
            ps.setString(2, vote_descruption);
            ps.setString(3, user_account);
            ps.setInt(4, root_question_id);
            ps.setBoolean(5, isPublic);
            ps.setTimestamp(6, java.sql.Timestamp.from(publishTime));
            return ps;
        }, keyHolder);

        // 获取生成的自增ID
        Number key = keyHolder.getKey();
        return key.intValue();
    }

    public static Vote queryVoteByVoteId(int vote_id) throws NoSuchElementException{
        String sql = "SELECT * FROM votes "+
                     "JOIN users ON users.account=votes.user_account "+
                     "WHERE vote_id = ?";
        RowMapper<Vote> rowMapper = (rs, rowNum) -> new Vote(rs.getInt("vote_id"),
                                                             new User(rs.getString("user_account"),
                                                                      rs.getString("password"),
                                                                      rs.getString("username")));
        
        List<Vote> votes = jdbcTemplate.query(sql, rowMapper, vote_id);
        if (votes.isEmpty()) {
            throw new NoSuchElementException("此问卷不存在");
        }else{
            return votes.get(0);
        }
    }

    public static void deleteVoteByVoteId(int vote_id){
        String sql1="DELETE FROM votes WHERE vote_id = ?";
        String sql2="DELETE FROM answeredUsers WHERE vote_id = ?";
        String sql3="DELETE FROM answers WHERE vote_id = ?";
        jdbcTemplate.update(sql3, vote_id);
        jdbcTemplate.update(sql2, vote_id);
        jdbcTemplate.update(sql1, vote_id);
    }

    /**
     * 向数据库中插入一条问卷答案
     * @param vote_id 问卷id
     * @param question_id 问题id
     * @param user_account 用户账号
     * @param select_option_id 选项id
     * @param custom_answer 自定义答案
     */
    public static void insertAnswer(int vote_id, int question_id, String user_account, int select_option_id, String custom_answer)throws DuplicateKeyException{
        String sql = "INSERT INTO answers(vote_id, question_id, user_account, selected_option_id, custom_answer) VALUES(?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, vote_id, question_id, user_account, select_option_id, custom_answer);
    }

    /**
     * 向数据库中插入一条用户回答记录
     * @param vote_id 问卷id
     * @param user_account 用户账号
     */
    public static void insertAnswerLog(int vote_id, String user_account) throws DuplicateKeyException{
        String sql = "INSERT INTO answeredUsers(vote_id, user_account) VALUES(?, ?)";
        jdbcTemplate.update(sql, vote_id, user_account);
    }

    public static List<AnswerLog> queryAnswerLogsByVoteId(int vote_id){
        String sql = "SELECT * FROM answer_logs WHERE vote_id = ?";
        RowMapper<AnswerLog> rowMapper = (rs, rowNum) -> new AnswerLog(rs.getInt("vote_id"),
                                                                      rs.getString("user_account"),
                                                                      rs.getTimestamp("answer_time").toInstant());
        List<AnswerLog> answerLogs= jdbcTemplate.query(sql, rowMapper, vote_id);
        if(answerLogs.isEmpty()){
            throw new NoSuchElementException("请求的问卷没有回答记录");
        }else{
            return answerLogs;
        }
    }

    /**
     * 确认用户是否已经回答过某个问卷
     */
    public static boolean confirmAnswerLog(int vote_id,String user_account){
        String sql = "SELECT * FROM answer_logs WHERE vote_id = ? AND user_account = ?";
        RowMapper<AnswerLog> rowMapper = (rs, rowNum) -> new AnswerLog(rs.getInt("vote_id"),
                                                                      rs.getString("user_account"),
                                                                      rs.getTimestamp("answer_time").toInstant());
        List<AnswerLog> answerLogs= jdbcTemplate.query(sql, rowMapper, vote_id, user_account);
        return !answerLogs.isEmpty();
    }

    public static List<Answer> queryAnswersByVoteIdAndUserAccount(int vote_id, String user_account){
        String sql = "SELECT * FROM answers WHERE vote_id = ? AND user_account = ?";
        RowMapper<Answer> rowMapper = (rs, rowNum) -> new Answer(rs.getInt("vote_id"),
                                                              rs.getInt("question_id"),
                                                              rs.getString("user_account"),
                                                              rs.getInt("select_option_id"),
                                                              rs.getString("custom_answer"));
        List<Answer> answers = jdbcTemplate.query(sql, rowMapper, vote_id, user_account);
        return answers;
    }

    /**
     * 查询所有问卷
     */
    public static List<Vote> queryAllVotes(){
        String sql="SELECT * FROM votes JOIN users ON users.account=votes.user_account";
        RowMapper<Vote> rowMapper = (rs, rowNum) -> new Vote(rs.getInt("vote_id"),
                                                             rs.getString("title"),
                                                             rs.getString("description"),
                                                             new User(rs.getString("user_account"),
                                                                      rs.getString("username")),
                                                             rs.getInt("root_question_id"),
                                                             rs.getBoolean("is_public"),
                                                             rs.getTimestamp("publish_time").toInstant());
        List<Vote> votes = jdbcTemplate.query(sql, rowMapper);
        return votes;
    }

    public static List<Vote> queryPublicVotes(){
        String sql="SELECT * FROM votes JOIN users ON users.account=votes.user_account AND is_public = true";
        RowMapper<Vote> rowMapper = (rs, rowNum) -> new Vote(rs.getInt("vote_id"),
                                                             rs.getString("title"),
                                                             rs.getString("description"),
                                                             new User(rs.getString("user_account"),
                                                                      rs.getString("username")),
                                                             rs.getInt("root_question_id"),
                                                             rs.getBoolean("is_public"),
                                                             rs.getTimestamp("publish_time").toInstant());
        List<Vote> votes = jdbcTemplate.query(sql, rowMapper);
        return votes;
    }

    /**
     * 查询某个用户发布的所有问卷
     */
    public static List<Vote> queryVotesByAccount(String userAccount){
        String sql="SELECT * FROM votes JOIN users ON users.account=votes.user_account WHERE user_account = ?";
        RowMapper<Vote> rowMapper = (rs, rowNum) -> new Vote(rs.getInt("vote_id"),
                                                             rs.getString("title"),
                                                             rs.getString("description"),
                                                             new User(rs.getString("user_account"),
                                                                      rs.getString("username")),
                                                             rs.getInt("root_question_id"),
                                                             rs.getBoolean("is_public"),
                                                             rs.getTimestamp("publish_time").toInstant());
        List<Vote> votes = jdbcTemplate.query(sql, rowMapper, userAccount);
        return votes;
    }

    public static void deleteVoteById(int voteId){
        String sql = "DELETE FROM votes WHERE vote_id = ?";
        jdbcTemplate.update(sql, voteId);
    }

    /**
     * 查询
     * @return
     */
    public static Map<Integer, Double> calculateOptionRatios(int vote_id, int question_id) {
        String sql = "SELECT selected_option_id, COUNT(*) as option_count FROM answers WHERE vote_id = ? AND question_id = ? GROUP BY selected_option_id";
        RowMapper<Map.Entry<Integer, Long>> rowMapper = (rs, rowNum) -> new AbstractMap.SimpleEntry<>(rs.getInt("selected_option_id"), rs.getLong("option_count"));
        
        List<Map.Entry<Integer, Long>> counts = jdbcTemplate.query(sql, rowMapper, vote_id, question_id);
        long total = counts.stream().mapToLong(Map.Entry::getValue).sum();
        
        Map<Integer, Double> ratios = new HashMap<>();
        for (Map.Entry<Integer, Long> entry : counts) {
            ratios.put(entry.getKey(), entry.getValue() / (double) total);
        }
        
        return ratios;
    }
}
