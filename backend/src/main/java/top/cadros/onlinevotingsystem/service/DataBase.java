package top.cadros.onlinevotingsystem.service;

import top.cadros.onlinevotingsystem.object.User;
import top.cadros.onlinevotingsystem.object.Vote;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

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

    public static Vote insertVote(Vote vote) throws Exception {
        final String sql = "INSERT INTO votes(title, description, user_account, root_question_id) VALUES(?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, vote.getTitle());
            ps.setString(2, vote.getDescription());
            ps.setString(3, vote.getUser().getAccount());
            ps.setInt(4, vote.getRootQuestionId());
            return ps;
        }, keyHolder);

        // 获取生成的自增ID
        Number key = keyHolder.getKey();
        if (key != null) {
            int generatedId = key.intValue();
            vote.setVote_id(generatedId);
        }
        return vote;
    }

    public static Vote queryVoteByVoteId(int vote_id) throws Exception{
        String sql = "SELECT * FROM votes "+
                     "JOIN users ON users.account=votes.user_account "+
                     "WHERE vote_id = ?";
        RowMapper<Vote> rowMapper = (rs, rowNum) -> new Vote(rs.getInt("vote_id"),
                                                             new User(rs.getString("user_account"),
                                                                      rs.getString("password"),
                                                                      rs.getString("username")));
        
        List<Vote> votes = jdbcTemplate.query(sql, rowMapper, vote_id);
        if (votes.isEmpty()) {
            throw new Exception("此问卷不存在");
        }else{
            return votes.get(0);
        }
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
