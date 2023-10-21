package com.filesplit.clientapplication.user;

import org.springframework.stereotype.Service;

import java.sql.*;

@Service
public class UserActionsImpl implements UserActions{
    @Override
    public String verifyUser(Connection connection, String userId, String password) {
        String msg="login failed: user not found";
        Statement stmt= null;
        try {
            stmt = connection.createStatement();
            ResultSet rs=stmt.executeQuery("select username from users where username='"+userId+"' && password='"+password+"'");
            if(rs.next()){
                msg = "success";
            }
            rs.close();
            stmt.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return msg;
    }

    @Override
    public String addUser(Connection connection, String userName, String password, String email) {
        String msg="Error in data user registration";
        boolean userExists=false;

        Statement stmt= null;
        try {
            stmt = connection.createStatement();
            ResultSet rs=stmt.executeQuery("select username from users where username='"+userName+"'");
            if(rs.next()){
                userExists=true;
                msg = "Username already exist";
            }
            if(!userExists){
                PreparedStatement stat=connection.prepareStatement("insert into users values(?,?,?)");
                stat.setString(1,userName);
                stat.setString(2,password);
                stat.setString(3,email);
                int i=stat.executeUpdate();
                if(i > 0){
                    msg = "user registration completed";
                }
                stat.close();
            }
            rs.close();
            stmt.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return msg;
    }
}
