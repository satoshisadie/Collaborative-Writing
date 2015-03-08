package org.diploma.dao.mappers;

import org.diploma.model.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        final User user = new User();

        user.setId(rs.getInt("id"));
        user.setLogin(rs.getNString("login"));
        user.setEmail(rs.getNString("email"));
        user.setPassword(rs.getNString("password"));

        return user;
    }
}
