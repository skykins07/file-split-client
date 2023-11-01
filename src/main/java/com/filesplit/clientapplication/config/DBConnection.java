package com.filesplit.clientapplication.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;

@Configuration
public class DBConnection {
    public Connection initializeConnection() {
        Connection connectionObj=null;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            connectionObj = DriverManager.getConnection("jdbc:mysql://localhost/drops","root","Test@123");
        }catch(Exception e){
            e.printStackTrace();
        }
        return connectionObj;
    }
}
