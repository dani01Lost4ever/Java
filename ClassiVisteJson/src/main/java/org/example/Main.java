package org.example;
import java.sql.*;
// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        String ANSI_RESET = "\u001B[0m";
        String ANSI_RED = "\u001B[31m";
        String ANSI_GREEN = "\u001B[32m";
        try {
            String dbUrl = "jdbc:sqlserver://localhost;databaseName=Northwind;"+
                    "integratedSecurity=true;encrypt=true;trustServerCertificate=true;";
            ResultSet resultSet = null;
            Connection myConnection = DriverManager.getConnection(dbUrl);
            Statement myStatement = myConnection.createStatement();
            String StrSQL = "select * from TArticoli";
            resultSet = myStatement.executeQuery(StrSQL);
            System.out.println("Sono pronto per popolare una lista");
            System.out.printf("%-10s %-20s %-80s %20s %-15s %-15s %20s"+ANSI_RESET+"%n",
                    "ArticoloID", "Nome", "Descrizione", "Prezzo Vendita", "Aliquota IVA", "Giacenza", "Prezzo IVA");

            boolean colorSwitch = false;
            while(resultSet.next()){
                String color = colorSwitch ? ANSI_RED : ANSI_GREEN;
                System.out.printf(color + "%-10d %-20s %-80s %19.2f€ %14d%% %-15d %18.2f€"+ANSI_RESET+"%n",
                        resultSet.getInt("ArticoloID"),
                        resultSet.getString("Nome"),
                        resultSet.getString("Descrizione"),
                        resultSet.getDouble("PrezzoUnitarioVendita"),
                        resultSet.getInt("AliquotaIVA"),
                        resultSet.getInt("Giacenza"),
                        (resultSet.getDouble("PrezzoUnitarioVendita") * (1 + resultSet.getInt("AliquotaIVA") / 100.0)));
                colorSwitch = !colorSwitch;
            }

        }
        catch (SQLException e){
            System.out.println(e.getMessage());
            //e.printStackTrace();
        }
        finally {

        }
    }
}