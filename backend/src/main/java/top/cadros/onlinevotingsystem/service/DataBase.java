package top.cadros.onlinevotingsystem.service;

import top.cadros.onlinevotingsystem.object.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DataBase {
    private static JdbcTemplate jdbcTemplate;

    private DataBase(JdbcTemplate jdbcTemplate) {
        DataBase.jdbcTemplate = jdbcTemplate;
    }

    public static User queryUserByAccount(User user) throws Exception {
        String sql = "SELECT * FROM user WHERE account = ? AND password = ?";
        return jdbcTemplate.queryForObject(sql, User.class, user.getAccount(), user.getPassword());
    }
}
