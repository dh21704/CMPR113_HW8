/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classexercise7;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author Danny
 */
public class LoginApp extends Application {
    
    private static final String DB_URL = "jdbc:sqlite:C:/Users/Danny/OneDrive - Rancho Santiago Community College District/Documents/NetBeansProjects/ClassExercise7/users7.db";
    
    @Override
    public void start(Stage primaryStage) {
        //create login controls
        Label lblUsername = new Label("Username:");
        TextField txtUsername = new TextField();
        Label lblPassword = new Label("Password");
        PasswordField txtPassword = new PasswordField();
        Button buttonLogin = new Button("Login");
        
        Button clearButton = new Button("Clear");
        
        clearButton.setOnAction(e ->
        {
            txtUsername.clear();
            txtPassword.clear();
        
        }
        );
        
        //set button size
        buttonLogin.setPrefWidth(100);
        buttonLogin.setPrefHeight(40);
        
        //create layout container
        VBox layout = new VBox(20);
        layout.getChildren().addAll(lblUsername, txtUsername, lblPassword, txtPassword, buttonLogin, clearButton);
                
        
        //create scene
        Scene scene = new Scene(layout, 300, 200);
        
        //set the scene on the stage
        primaryStage.setScene(scene);
        
        //set the stage title and show it
        primaryStage.setTitle("Login App");
        primaryStage.show();
        
        //handle login button click
        buttonLogin.setOnAction(event ->
        {
            String username = txtUsername.getText();
            String password = txtPassword.getText();
            
            try
            {
             Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT username, password FROM users7");
             
             boolean valid = false;
             while(rs.next())
             {
                if (rs.getString("username").equals(username) &&
                        rs.getString("password").equals(password)){
                        valid = true;
                        break;
                }
             }
                rs.close();
                stmt.close();
                conn.close();
                
                if(valid)
                {
                    System.out.println("login successful");   
                } else
                {
                    System.out.println("invalid username or password");
                }
                     
            } catch(SQLException e)
            {
                e.printStackTrace();
            }
        }
        );
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
