/*
    Name: Jessica Hernandez
    Course: CNT 4714 Spring 2025
    Assignment title: Project 3 – A Two-tier Client-Server Application
    Date: March 14, 2025
    Class: Enterprise Computing
*/

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class Main {    
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/project3";
        String user = "root";
        String password = "";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            System.out.println("Connected to the database!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}