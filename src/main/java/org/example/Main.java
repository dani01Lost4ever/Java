package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        String ANSI_RESET = "\u001B[0m";
        String ANSI_RED = "\u001B[31m";
        String ANSI_GREEN = "\u001B[32m";
        try {
            DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());
            String dburl = "jdbc:sqlserver://localhost;databaseName=Verifica_java;integratedSecurity=true;encrypt=true;trustServerCertificate=true;";
            Connection connection = DriverManager.getConnection(dburl);
            if(connection!= null) {
                System.out.println("---Connected---");
                System.out.printf("%-10s %-80s%n",
                        "Numero", "Descrizione");
                System.out.printf("%-10s %-80s%n",
                        "0", "Per la lista degli articoli con prezzo Ivato");
                System.out.printf("%-10s %-80s%n",
                        "1", "Per inserire un nuovo articolo");
                System.out.printf("%-10s %-80s%n",
                        "2", "Per elimare un articolo");
                System.out.printf("%-10s %-80s%n",
                        "3", "Per effettuare un aggiornamento percentuale del prezzo dell'articolo selezionato");
                System.out.printf("%-10s %-80s%n",
                        "4", "Per vedere il valore completo del magazzino");

                int scelta = System.in.read();
                Statement statement = connection.createStatement();
                switch(scelta) {
                    case '0':
                        // Code to execute when scelta is 0
                        flush();
                        String sql = "select * from TArticoli";
                        ResultSet resultSet = statement.executeQuery(sql);
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
                        break;
                    case '1':
                        // Code to execute when scelta is 1
                        flush();
                        Scanner scanner = new Scanner(System.in);
                        String temp = scanner.nextLine();
                        System.out.println("Enter a Name:");
                        String Name = scanner.nextLine();
                        System.out.println("Enter a Description:");
                        String description = scanner.nextLine();
                        System.out.println("Enter a PrezzoUnitarioVendita:");
                        String PrezzoUnitarioVendita = scanner.nextLine();
                        System.out.println("Enter a AliquotaIVA:");
                        String AliquotaIVA = scanner.nextLine();
                        System.out.println("Enter a Giacenza:");
                        String Giacenza = scanner.nextLine();
                        Float PrezzoUnitarioVendita2 = Float.parseFloat(PrezzoUnitarioVendita);
                        int AliquotaIVA2 = Integer.parseInt(AliquotaIVA);
                        int Giacenza2 = Integer.parseInt(Giacenza);
                        String insertSql2 = "insert into Tarticoli(Nome, Descrizione, PrezzoUnitarioVendita, AliquotaIVA, Giacenza) values('"+Name+"', '"+description+"' , '"+PrezzoUnitarioVendita2+"' , '"+AliquotaIVA2+"' , '"+Giacenza2+"')";
                        var result = statement.executeUpdate(insertSql2);
                        if(result !=0){
                            System.out.println(ANSI_GREEN+"done"+ANSI_RESET);
                        }else{
                            System.out.println(ANSI_RED+"error"+ANSI_RESET);
                        }
                        break;
                    case '2':
                        // Code to execute when scelta is 2
                        flush();
                        Scanner scanner2 = new Scanner(System.in);
                        String temp2 = scanner2.nextLine();
                        System.out.println("Enter a ArticoloID:");
                        String ArticoloID = scanner2.nextLine();
                        int ArticoloID2 = Integer.parseInt(ArticoloID);
                        String deleteSql = "delete from TArticoli where ArticoloID = '"+ArticoloID2+"'";
                        var result1 = statement.executeUpdate(deleteSql);
                        if(result1 !=0){
                            System.out.println(ANSI_GREEN+"done"+ANSI_RESET);
                        }else{
                            System.out.println(ANSI_RED+"error"+ANSI_RESET);
                        }
                        break;

                    case '3':
                        // Code to execute when scelta is 3
                        flush();
                        Scanner scanner3 = new Scanner(System.in);
                        String temp3 = scanner3.nextLine();
                        System.out.println("Enter a ArticoloID:");
                        String ArticoloID3 = scanner3.nextLine();
                        int ArticoloID4 = Integer.parseInt(ArticoloID3);
                        System.out.println("Enter a Percentuale:");
                        String Percentuale = scanner3.nextLine();
                        int Percentuale2 = Integer.parseInt(Percentuale);
                        String updateSql = "update TArticoli set PrezzoUnitarioVendita = PrezzoUnitarioVendita + (PrezzoUnitarioVendita * '"+Percentuale2+"' / 100) where ArticoloID = '"+ArticoloID4+"'";
                        statement.executeUpdate(updateSql);
                        var result2 = statement.executeUpdate(updateSql);
                        if(result2 !=0){
                            System.out.println(ANSI_GREEN+"done"+ANSI_RESET);
                        }else{
                            System.out.println(ANSI_RED+"error"+ANSI_RESET);
                        }
                        break;

                    case '4':
                        // Code to execute when scelta is 4
                        flush();
                        String sql2 = "select * from TArticoli";
                        ResultSet resultSet2 = statement.executeQuery(sql2);
                        float totale=0;
                        while(resultSet2.next()){
                            totale+=resultSet2.getDouble("PrezzoUnitarioVendita")*resultSet2.getInt("Giacenza");
                        }
                        System.out.println("Il valore totale del magazzino è: "+ANSI_GREEN+totale+ANSI_RESET+"€");
                        break;

                    default:
                        System.out.println("Invalid choice");
                        break;
                }
            }
            else {
                System.out.println("Not Connected");
            }
            connection.close();
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }

    }
    public static void flush(){
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}