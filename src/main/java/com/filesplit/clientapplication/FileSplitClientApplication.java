package com.filesplit.clientapplication;

import com.filesplit.clientapplication.components.UserScreen;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan(basePackages = {
		"com.filesplit.clientapplication.components.*",
		"com.filesplit.clientapplication.config.*",
		"com.filesplit.clientapplication.model.*",
		"com.filesplit.clientapplication.user.*"})
public class FileSplitClientApplication {

	public static void main(String[] args) {
		System.out.println("Starting the client application for file split");
		UserScreen usr =new UserScreen();
		usr.setVisible(true);
		usr.setSize(800,300);
		SpringApplication.run(FileSplitClientApplication.class, args);
	}

}
