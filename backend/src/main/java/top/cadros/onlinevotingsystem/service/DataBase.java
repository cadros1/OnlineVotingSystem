package top.cadros.onlinevotingsystem.service;

import top.cadros.onlinevotingsystem.object.User;

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

    public static User queryUserByAccount(User user) throws Exception {
        String sql = "SELECT * FROM users WHERE account = ? AND password = ?";
        RowMapper<User> rowMapper = (rs, rowNum) -> new User(rs.getString("account"), rs.getString("password"));
        
        List<User> users = jdbcTemplate.query(sql, rowMapper, user.getAccount(), user.getPassword());
        
        if (users.isEmpty()) {
            return null;
        } else {
            return users.get(0);
        }
    }
}
