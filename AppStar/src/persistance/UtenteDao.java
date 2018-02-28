package persistance;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UtenteDao {

    public static ArrayList<String> controlloAccount(String userID, String password) throws SQLException{
        int b = -1;
        ArrayList<String> infoUtente = null;
        Connessione.connettiti();
        try{
            String controlloQuery = "SELECT * FROM utenti WHERE 'USER_ID' = ? AND 'PASSWORD' = ? ";
            PreparedStatement ps = Connessione.CONN.prepareStatement(controlloQuery);
            ps.setString(1, userID);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                if (rs.getBoolean("AMMINISTRATORE")){
                    b = 1;
                    infoUtente.add(rs.getString("NOME"));
                    infoUtente.add(rs.getString("COGNOME"));
                    infoUtente.add(userID);
                    infoUtente.add(password);
                    infoUtente.add(rs.getString("EMAIL"));
                    infoUtente.add("amministratore");
                }else if (!rs.getBoolean("AMMINISTRATORE")){
                    b = 0;
                    infoUtente.add(rs.getString("NOME"));
                    infoUtente.add(rs.getString("COGNOME"));
                    infoUtente.add(userID);
                    infoUtente.add(password);
                    infoUtente.add(rs.getString("EMAIL"));
                    infoUtente.add("notAmministratore");
                }
            }
            System.out.println("OPERAZIONI CONCLUSE CON SUCCESSO");
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }finally{
            Connessione.CONN.close();
            System.out.println(infoUtente);
            return infoUtente;
        }
    }
}
