package org.example;
import java.util.Scanner;
import java.io.IOException;
import java.sql.*;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        System.out.println("Hello and welcome!");

        try{

            System.out.println("Hello and welcome!".substring(0, 5));
            DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());
            String dburl ="jdbc:sqlserver://localhost;databaseName=Northwind;integratedSecurity=true;encrypt=true;trustServerCertificate=true;";
            Connection connection = DriverManager.getConnection(dburl);
            if(connection!= null){
                System.out.println("Connected");

                System.out.println("Scrivere 0 per stampare, 1 per inserire");
                int scelta = System.in.read();
                Statement statement = connection.createStatement();
                switch(scelta) {
                    case '0':
                        // Code to execute when scelta is 0
                        String sql = "select * from Categories";
                        ResultSet resultSet = statement.executeQuery(sql);
                        while(resultSet.next()){
                            System.out.println(resultSet.getString("CategoryID") + " " + resultSet.getString("CategoryName")+ " " + resultSet.getString("Description"));
                        }
                        break;
                    case '1':
                        // Code to execute when scelta is 1
                        Scanner scanner = new Scanner(System.in);
                        String temp = scanner.nextLine();
                        System.out.println("Enter a CategoryName:");
                        String category = scanner.nextLine();
                        System.out.println("Enter a Description:");
                        String description = scanner.nextLine();
                        String insertSql2 = "insert into Categories(CategoryName, Description) values('"+category+"', '"+description+"')";
                        ResultSet result = statement.executeQuery(insertSql2);
                        System.out.println("done");
                        break;
                    default:
                        // Code to execute when scelta does not match any cases
                        System.out.println("Invalid choice");
                        break;
                }
                connection.close();
            }
            else{
                System.out.println("Not Connected");
            }

        } catch (SQLException | IOException e) {

            System.out.println("Exception: " + e.getMessage());
        }
    }
}