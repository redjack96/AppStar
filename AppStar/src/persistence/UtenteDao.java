package persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UtenteDao {

    /**
     * Il metodo sottoscritto e' una verifica dell'esistenza di un eventuale account con le credenziali inserite
     * dall'utente (attraverso una QuerySQL) nella table 'utenti' contenuta nel database 'CONTORNI_E_FILAMENTI'.
     * QuerySQL:
     *      SELECT *
     *      FROM utenti
     *      WHERE (USER_ID = 'userID inserito dall'utente'
     *          AND PASSOWRD = 'password inserita dall'utente')
     *
     * @param userID: String.
     * @param password: String.
     * @return infoUtente: ArrayList(Nome, Cognome, UserID, Password, eMail, isAdmin).
     * @throws SQLException
     */
    public static ArrayList controlloAccount(String userID, String password) throws SQLException{
        int b = -1;
        ArrayList infoUtente = new ArrayList(6);//Inizializzazione ArrayList infoUtente.
        Connessione.connettiti();
        try{
            String inQueryUserID = "USER_ID";
            String inQueryPassword = "PASSWORD";
            String controlloQuery = "SELECT * " +
                                    "FROM utenti " +
                                    "WHERE ("+'"' + inQueryUserID + '"' + " = ? " +
                                        "AND "+ '"' + inQueryPassword + '"' + " = ?)";
            PreparedStatement ps = Connessione.CONN.prepareStatement(controlloQuery);
            ps.setString(1, userID);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                infoUtente.add(rs.getString("NOME"));       //infoUtente = (Nome, _, _, _, _, _).
                infoUtente.add(rs.getString("COGNOME"));    //infoUtente = (-, Cognome, _, _, _, _).
                infoUtente.add(userID);                                 //infoUtente = (-, -, UserID, _, _, _).
                infoUtente.add(password);                               //infoUtente = (-, -, -, Password, _, _).
                infoUtente.add(rs.getString("EMAIL"));      //infoUtente = (-, -, -, -, eMail, _).
                if (rs.getBoolean("AMMINISTRATORE")){
                    //se l'utente verificato e' un amministratore...
                    b = 1;
                    infoUtente.add("amministratore");                   //infoUtente = (-, -, -, -, -, "amministratore").
                }else if (!rs.getBoolean("AMMINISTRATORE")){
                    //... altrimenti.
                    b = 0;
                    infoUtente.add("notAmministratore");                //infoUtente = (-, -, -, -, -, "notAmministratore").
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

    public static void inserisciAccount(String nome, String cognome, String userID, String password, String email,
                                        boolean admin) throws SQLException{

        Connessione.connettiti();
        try{
            //String NOME = "NOME"; String COGNOME = "COGNOME"; String USER_ID = "USER_ID"; String PASSWORD = "PASSWORD";
            //String EMAIL = "EMAIL"; String AMMINISTRATORE = "AMMINISTRATORE";
            String utenteInsert = "INSERT INTO utenti VALUES(?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = Connessione.CONN.prepareStatement(utenteInsert);
            ps.setString(1, nome);
            ps.setString(2, cognome);
            ps.setString(3, userID);
            ps.setString(4, password);
            ps.setString(5, email);
            ps.setBoolean(6, admin);
            ps.executeUpdate();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }finally{
            Connessione.CONN.close();
        }
    }
}
