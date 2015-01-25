package org.diploma.dao;

import org.diploma.model.User;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Optional;

public class UserDaoImpl implements UserDao {
    private JdbcTemplate jdbcTemplate;

    public UserDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<User> getUserById(int id) {
        return null;
    }
}
