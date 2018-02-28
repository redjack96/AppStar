package persistance;

import java.sql.*;

public class Connessione {
    private static String URL = "jdbc:postgresql://localhost:5432/CONTORNI_E_FILAMENTI";
    private static final String USER = "postgres";
    private static final String PASSWORD = "password";
    public static Connection CONN;

    public static Connection connettiti(){
        try{
            CONN = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connesso a PostgreSQL");
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return CONN;
    }
}