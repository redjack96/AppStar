package persistence;

import java.sql.*;

public class Connessione {
    private static final String USER = "postgres";
    private static final String PASSWORD = "password";
    public static Connection CONN;

    /**
     * Attraverso persistence.Connessione.connettiti() viene stabilita una connessione col DBMS PostgreSQL 9.6.
     */
    public static void connettiti(){
        try{
            String URL = "jdbc:postgresql://localhost:5433/CONTORNI_E_FILAMENTI";
            CONN = DriverManager.getConnection(URL, USER, PASSWORD);
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
}