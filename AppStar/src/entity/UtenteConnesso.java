package entity;

//Implementazione del pattern SINGLETON------------------------------------------------------------------------------------

import persistance.UtenteDao;

import java.sql.SQLException;
import java.util.ArrayList;

public class UtenteConnesso {

    public static UtenteRegistrato utente = null; // riferimento all'istanza

    private UtenteConnesso(){} // costruttore

    public static void disconnettiUtente(){
        utente = null;
    }

    public static UtenteRegistrato getInstance(String userID, String password){
        if (utente == null){
            synchronized (UtenteConnesso.class){
                if ((utente == null)){
                    try{
                        ArrayList<String> infoUtente = UtenteDao.controlloAccount(userID, password);
                        if (infoUtente.get(5).equals("amministratore")){
                            utente = new UtenteAmministratore(infoUtente.get(0), infoUtente.get(1), infoUtente.get(2),
                                    infoUtente.get(3), infoUtente.get(4));
                        }else if (infoUtente.get(5).equals("notAmministratore")) {
                            utente = new UtenteRegistrato(infoUtente.get(0), infoUtente.get(1), infoUtente.get(2),
                                    infoUtente.get(3), infoUtente.get(4));
                        }
                    }catch (SQLException e){
                        System.out.println(e.getMessage());
                    }
                }
            }
        }
        return utente;
    }
}
