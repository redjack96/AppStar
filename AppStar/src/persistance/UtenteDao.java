package persistance;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UtenteDao {

    public static ArrayList controlloAccount(String userID, String password) throws SQLException{
        int b = -1;
        ArrayList infoUtente = new ArrayList(6);
        Connessione.connettiti();
        try{
            String inQueryUserID = "USER_ID";
            String inQueryPassword = "PASSWORD";
            String controlloQuery = "SELECT * FROM utenti WHERE ("+'"' + inQueryUserID + '"' + " = ? AND "+ '"' +
                    inQueryPassword + '"' + " = ?)";
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
                System.out.println("INFORMAZIONI UTENTE COMPILATE CON SUCCESSO " + b);
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }finally{
            Connessione.CONN.close();
        }
        System.out.println("Credenziali e informazioni dell'utente connesso :" + infoUtente);
        return infoUtente;
    }
}
