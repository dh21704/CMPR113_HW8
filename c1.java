/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author Danny
 */
public class c1 extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Celsius and Fahrenheit Temperature Converter");
        
        Label lbl1 = new Label("Enter a temperature: ");
        TextField field1 = new TextField();
        
        Button button1 = new Button("Convert to Fahrenheit.");
        Button button2 = new Button("Convert to Celsius.");
        
        
        GridPane pane = new GridPane();
        
        pane.add(lbl1, 0, 0);
        pane.add(field1, 1, 0);
        pane.add(button1, 0, 3);
        pane.add(button2, 2, 3);
        
        button1.setOnAction(e ->
        {
            String celsius = field1.getText();
            
            double celsiusDouble = Double.parseDouble(celsius);
            
            double fahrenheit = (celsiusDouble * 1.8) + 32;
            
            System.out.println("Fahrenheit: " + fahrenheit);
            
            try
            {
            insertData(fahrenheit, celsiusDouble);
                
            } catch(SQLException ex)
            {
                System.out.println("Error: " + ex.getMessage());
            }

        });
        
        button2.setOnAction(e ->
        {
            String f = field1.getText();
            
            
            
            double fahrenheit = Double.parseDouble(f);
            
            System.out.println(fahrenheit);
            
            double celsius = (fahrenheit - 32) * (5.0/9);
            
            System.out.println("Celsius: " + celsius);
            
            try
            {
                insertData(fahrenheit, celsius);
                
            } catch(SQLException ex)
            {
                System.out.println("invalid input: " + ex.getMessage());
            }
        }
        );
        
        Scene scene = new Scene(pane, 300, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    private void insertData(double f, double c) throws SQLException
    {
        String db = "jdbc:sqlite:C:/Users/Danny/OneDrive - Rancho Santiago Community College District/Documents/NetBeansProjects/HW8/temperature.db";
 
        String insertSQL = "INSERT INTO temperature (celsius, fahrenheit) VALUES (?, ?)";
        
        try(Connection conn = DriverManager.getConnection(db);
                
        PreparedStatement pstmt = conn.prepareStatement(insertSQL))
        {
             pstmt.setDouble(1, c);
             pstmt.setDouble(2, f);
             
             //execute update 
             pstmt.executeUpdate();
             System.out.println("Data inserted successfully");
        } catch(SQLException e)
        {
            System.out.println("DataBase error: " + e.getMessage());
        }
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
