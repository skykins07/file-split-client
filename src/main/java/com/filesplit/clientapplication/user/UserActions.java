package com.filesplit.clientapplication.user;

import com.filesplit.clientapplication.config.DBConnection;

import java.sql.Connection;

public interface UserActions {

    String verifyUser(Connection connection, String userId, String password);

    String addUser(Connection connection, String userName, String password, String email);
}
