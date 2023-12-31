package com.filesplit.clientapplication.components;

import com.filesplit.clientapplication.config.DBConnection;
import com.filesplit.clientapplication.user.UserActionsImpl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.Random;


public class Register extends JFrame
{
    JPanel p1,p2;
    JLabel l1,l2,l3;
    JButton b1,b2;
    Font f1,f2;
    JTextField tf1,tf2,tf3;
    UserScreen users;


    public int getRandom(){
        Random r = new Random();
        return r.nextInt(2);
    }
    public Register(UserScreen usr){
        users = usr;
        setTitle("Cloud User Screen");
        f1 = new Font("Times New Roman",Font.BOLD,18);
        p1 = new JPanel();
        l1 = new JLabel("<HTML><BODY><CENTER>AUTHENTICATING CLOUD USER</CENTER></BODY></HTML>".toUpperCase());
        l1.setFont(this.f1);
        l1.setForeground(new Color(0,0,0));
        p1.add(l1);
        p1.setBackground(new Color(128,128,128));

        f2 = new Font("Times New Roman",Font.BOLD,14);

        p2 = new JPanel();
        p2.setLayout(null);

        l1 = new JLabel("Username");
        l1.setFont(f2);
        l1.setBounds(100,20,120,30);
        p2.add(l1);
        tf1 = new JTextField();
        tf1.setFont(f2);
        tf1.setBounds(220,20,120,30);
        p2.add(tf1);

        l2 = new JLabel("Password");
        l2.setFont(f2);
        l2.setBounds(100,70,120,30);
        p2.add(l2);
        tf2 = new JPasswordField();
        tf2.setFont(f2);
        tf2.setBounds(220,70,120,30);
        p2.add(tf2);

        l3 = new JLabel("Email ID");
        l3.setFont(f2);
        l3.setBounds(100,120,120,30);
        p2.add(l3);
        tf3 = new JTextField();
        tf3.setFont(f2);
        tf3.setBounds(220,120,120,30);
        p2.add(tf3);

        b1 = new JButton("Register");
        b1.setFont(f2);
        b1.setBounds(120,170,200,30);
        p2.add(b1);
        b1.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){
                register();
            }
        });

        b2 = new JButton("Back");
        b2.setFont(f2);
        b2.setBounds(340,170,100,30);
        p2.add(b2);
        b2.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){
                setVisible(false);
                users.setVisible(true);
            }
        });

        getContentPane().add(p1, "North");
        getContentPane().add(p2, "Center");
    }
    public void register(){
        String user = tf1.getText();
        String pass = tf2.getText();
        String email = tf3.getText();
        if(user == null || user.trim().length() <= 0){
            JOptionPane.showMessageDialog(this,"Username must be enter");
            tf1.requestFocus();
            return;
        }
        if(pass == null || pass.trim().length() <= 0){
            JOptionPane.showMessageDialog(this,"Password must be enter");
            tf2.requestFocus();
            return;
        }
        if(email == null || email.trim().length() <= 0){
            JOptionPane.showMessageDialog(this,"Emailid must be enter");
            tf3.requestFocus();
            return;
        }
        if(!CheckMail.checkMail(email)){
            JOptionPane.showMessageDialog(this,"Enter valid mailid");
            tf3.requestFocus();
            return;
        }
        try{
            DBConnection dbConnection=new DBConnection();
            Connection connection=dbConnection.initializeConnection();
            UserActionsImpl userActions=new UserActionsImpl();
            String returnMsg=userActions.addUser(connection,user,pass,email);
            if("user registration completed".equalsIgnoreCase(returnMsg)){
                setVisible(false);
                JOptionPane.showMessageDialog(Register.this,"user registration completed");
                users.setVisible(true);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
