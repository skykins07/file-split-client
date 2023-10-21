package com.filesplit.clientapplication;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserScreen extends JFrame {
    JPanel p1,p2;
    JLabel l1;
    JButton b1,b2;
    Font f1,f2;

    public UserScreen(){
        setTitle("Cloud User Screen");
        f1 = new Font("Courier New",Font.BOLD+Font.ITALIC,18);
        p1 = new JPanel();
        l1 = new JLabel("<HTML><BODY><CENTER>DROPS: Division and Replication of Data in Cloud for Optimal<br/>Performance and Security</CENTER></BODY></HTML>".toUpperCase());
        l1.setFont(this.f1);
        l1.setForeground(new Color(125,254,120));
        p1.add(l1);
        p1.setBackground(new Color(100,30,40));

        f2 = new Font("Courier New",Font.BOLD,14);

        p2 = new JPanel();
        p2.setLayout(null);
        b1 = new JButton("User Login");
        b1.setFont(f2);
        b1.setBounds(200,20,200,30);
        p2.add(b1);
        b1.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){
                setVisible(false);
                Login log = new Login(UserScreen.this);
                log.setVisible(true);
                log.setSize(650,300);
            }
        });

        b2 = new JButton("User Register");
        b2.setFont(f2);
        b2.setBounds(200,70,200,30);
        p2.add(b2);
        b2.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){
                setVisible(false);
                Register reg = new Register(UserScreen.this);
                reg.setVisible(true);
                reg.setSize(650,300);
            }
        });

        getContentPane().add(p1, "North");
        getContentPane().add(p2, "Center");
    }
}
