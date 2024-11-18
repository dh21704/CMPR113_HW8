package hw8;

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
import javax.swing.JOptionPane;

/**
 *
 * @author Danny
 */
public class c2 extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Retail Price Calculator");
        
        Label label1 = new Label("Wholesale Cost of Item:");
        TextField field1 = new TextField();
        
        Label label2 = new Label("Enter its Markup Percentage: ");
        TextField field2 = new TextField();
        
        Label nameLabel = new Label("Enter Name of Product:");
        TextField field3 = new TextField();
        
        Button button = new Button("Submit");
        
        GridPane pane = new GridPane();
        pane.setVgap(10);
        pane.setHgap(10);
        
        pane.add(label1, 0, 0);
        pane.add(field1, 1, 0);
        pane.add(label2, 0, 2);
        pane.add(field2, 1, 2);
        pane.add(nameLabel, 0, 3);
        pane.add(field3, 1, 3);
        
        pane.add(button, 0, 5);
        
        button.setOnAction(e ->
        {
          String f1 = field1.getText();   
          String f2 = field2.getText();
          String name = field3.getText();
            
          try
          {
              double wholeSaleCost = Double.parseDouble(f1);
              double markupPerecentage = wholeSaleCost * (Double.parseDouble(f2) / 100);
          
             double retailPrice = wholeSaleCost + markupPerecentage;
             
             //insert data into sqlite database
            try
            {insertDataIntoDataBase(name, retailPrice);
            
            } catch(SQLException ex)
            {
                System.out.println("Database erorr: " + ex.getMessage());
            }
          }catch(NumberFormatException ex)
          {
            //handle invalid number format error 
              System.out.println("Invalid Input: " + ex.getMessage());
          }    
        }
        
        );
        
        
        Scene scene = new Scene(pane, 300, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
        
    }
    
    private void insertDataIntoDataBase(String name, double retailPrice) throws SQLException
    {
                String db = "jdbc:sqlite:C:/Users/Danny/OneDrive - Rancho Santiago Community College District/Documents/NetBeansProjects/HW8/retail.db";
                  
                  String insertSQL = "INSERT INTO retail (name, retailPrice) VALUES (?, ?)";

                  try(Connection conn = DriverManager.getConnection(db);
                          
                  PreparedStatement pstmt = conn.prepareStatement(insertSQL))
                  {
                      
                    pstmt.setString(1, name);
                    pstmt.setDouble(2, retailPrice);
                    
                    //execute the update
                    pstmt.executeUpdate();
                      System.out.println("Data inserted Successfully");
                      
                      JOptionPane.showMessageDialog(null, "Retail Price: " + retailPrice);
                      
                  } catch(SQLException e)
                  {
                      System.out.println("DataBase error; " + e.getMessage());
                  }

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
