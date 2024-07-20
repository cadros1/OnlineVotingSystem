package top.cadros.onlinevotingsystem.service;

import top.cadros.onlinevotingsystem.object.User;
import top.cadros.onlinevotingsystem.object.Vote;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
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

    public static List<Vote> queryVotesByAccount(User user) throws Exception {
        String sql = "SELECT * FROM votes "+
                     "JOIN users ON votes.user_account = users.account "+
                     "WHERE users.account = ?";
        RowMapper<Vote> rowMapper = (rs, rowNum) -> new Vote(rs.getInt("vote_id"),
                                                             rs.getString("title"),
                                                             rs.getString("description"),
                                                             new User(rs.getString("user_account"),
                                                                      rs.getString("username")));
        
        List<Vote> votes = jdbcTemplate.query(sql, rowMapper, user.getAccount());
        if (votes.isEmpty()) {
            return null;
        } else {
            return votes;
        }
    }

    public static Vote queryVoteByVoteId(int vote_id) throws Exception {
        String sql = "SELECT * FROM votes "+
                     "JOIN users ON votes.user_account=users.account "+
                     "WHERE vote_id = ?";
        RowMapper<Vote> rowMapper = (rs, rowNum) -> new Vote(rs.getInt("vote_id"),
                                                             rs.getString("title"),
                                                             rs.getString("description"),
                                                             new User(rs.getString("user_account"),
                                                                      rs.getString("username")));
        
        List<Vote> votes = jdbcTemplate.query(sql, rowMapper, vote_id);
        if (votes.size()==0) {
            throw new Exception("此问卷不存在");
        } else {
            return votes.get(0);
        }
    }
}
