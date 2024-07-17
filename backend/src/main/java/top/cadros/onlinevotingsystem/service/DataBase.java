package top.cadros.onlinevotingsystem.service;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DataBase {
    private static JdbcTemplate jdbcTemplate;

    private DataBase(JdbcTemplate jdbcTemplate) {
        DataBase.jdbcTemplate = jdbcTemplate;
    }

    // 示例方法：查询数据库test表中的所有id
    public List<Integer> findAllUsers() {
        return jdbcTemplate.query("SELECT * FROM test",
                (rs, rowNum) -> rs.getInt("id"));
    }
}
