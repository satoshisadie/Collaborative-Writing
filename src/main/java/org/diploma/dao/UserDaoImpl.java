package org.diploma.dao;

import org.diploma.dao.mappers.UserRowMapper;
import org.diploma.model.User;
import org.diploma.utils.CommonUtils;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Optional;

public class UserDaoImpl implements UserDao {
    private JdbcTemplate jdbcTemplate;

    public UserDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<User> getUserById(int id) {
        final String sql =
                "SELECT * " +
                "FROM [user] u " +
                "WHERE u.id = ?";

        return CommonUtils.selectOne(jdbcTemplate, sql, new UserRowMapper(), id);
    }

    @Override
    public Optional<User> getUserByLogin(String login) {
        final String sql =
                "SELECT * " +
                "FROM [user] u " +
                "WHERE u.login = ?";

        return CommonUtils.selectOne(jdbcTemplate, sql, new UserRowMapper(), login);
    }
}
