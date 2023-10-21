package com.filesplit.clientapplication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@SpringBootApplication
@ComponentScan(basePackages = "com.filesplit.clientapplication.*")
public class FileSplitClientApplication {

	public static void main(String[] args) {
		System.out.println("Starting the client application for file split");
		UserScreen usr =new UserScreen();
		usr.setVisible(true);
		usr.setSize(800,300);
		SpringApplication.run(FileSplitClientApplication.class, args);
	}

}
