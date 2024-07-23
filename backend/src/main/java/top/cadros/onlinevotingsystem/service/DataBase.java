package top.cadros.onlinevotingsystem.service;

import top.cadros.onlinevotingsystem.object.User;
import top.cadros.onlinevotingsystem.object.Vote;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
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

    public static int insertVote(String vote_title, String vote_descruption, int root_question_id, String user_account, boolean isPublic) throws Exception {
        final String sql = "INSERT INTO votes(title, description, user_account, root_question_id, is_public) VALUES(?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, vote_title);
            ps.setString(2, vote_descruption);
            ps.setString(3, user_account);
            ps.setInt(4, root_question_id);
            ps.setBoolean(5, isPublic);
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
        String sql = "DELETE FROM votes WHERE vote_id = ?";
        jdbcTemplate.update(sql, vote_id);
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
        String sql = "INSERT INTO answers(vote_id, question_id, user_account, select_option_id, custom_answer) VALUES(?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, vote_id, question_id, user_account, select_option_id, custom_answer);
    }

    /**
     * 向数据库中插入一条用户回答记录
     * @param vote_id 问卷id
     * @param user_account 用户账号
     */
    public static void insertAnswerLog(int vote_id, String user_account) throws DuplicateKeyException{
        String sql = "INSERT INTO answer_logs(vote_id, user_account) VALUES(?, ?)";
        jdbcTemplate.update(sql, vote_id, user_account);
    }

    //public static List<Vote> queryVotesByAccount(User user) throws Exception {
    //    String sql = "SELECT * FROM votes "+
    //                 "JOIN users ON votes.user_account = users.account "+
    //                 "WHERE users.account = ?";
    //    RowMapper<Vote> rowMapper = (rs, rowNum) -> new Vote(rs.getInt("vote_id"),
    //                                                         rs.getString("title"),
    //                                                         rs.getString("description"),
    //                                                         new User(rs.getString("user_account"),
    //                                                                  rs.getString("username")));
    //    
    //    List<Vote> votes = jdbcTemplate.query(sql, rowMapper, user.getAccount());
    //    if (votes.isEmpty()) {
    //        return null;
    //    } else {
    //        return votes;
    //    }
    //}
//
    //public static Vote queryVoteByVoteId(int vote_id) throws Exception {
    //    String sql = "SELECT * FROM votes "+
    //                 "JOIN users ON votes.user_account=users.account "+
    //                 "WHERE vote_id = ?";
    //    RowMapper<Vote> rowMapper = (rs, rowNum) -> new Vote(rs.getInt("vote_id"),
    //                                                         rs.getString("title"),
    //                                                         rs.getString("description"),
    //                                                         new User(rs.getString("user_account"),
    //                                                                  rs.getString("username")));
    //    
    //    List<Vote> votes = jdbcTemplate.query(sql, rowMapper, vote_id);
    //    if (votes.size()==0) {
    //        throw new Exception("此问卷不存在");
    //    } else {
    //        return votes.get(0);
    //    }
    //}
}
