package org.diploma.dao;

import org.diploma.model.User;

import java.util.Optional;

public interface UserDao {

    Optional<User> getUserById(int id);
    Optional<User> getUserByLogin(String login);

}
