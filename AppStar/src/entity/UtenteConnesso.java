package entity;

//---------------------------------------------------------
//Implementazione del pattern SINGLETON--------------------
//---------------------------------------------------------

import persistence.UtenteDao;

import java.sql.SQLException;
import java.util.ArrayList;

public class UtenteConnesso {

    public static UtenteRegistrato utente = null; // riferimento all'istanza

    private UtenteConnesso(){} // costruttore

    /**
     * Assegna il valore
     */
    public static void disconnettiUtente(){
        utente = null;
    }

    /**
     * #1) Se l'istanza del Singleton contiene il valore 'null' allora nessun utente e' connesso
     *  verra' assegnata ad essa un nuovo utente (amministratore o non).
     * #2) Se invece l'istanza del Singleton contiene un utente (amministratore o non) il metodo
     *  getInstance(String userID, String password) viene chiamato esclusivamente per ottenere
     *  l'oggetto UtenteConnesso che ritornera' un utente (amministratore o non) tramite una
     *  chiamata del tipo getInstance(null, null).
     *
     * @param userID: String.
     * @param password: String.
     * @return utente: UtenteRegistrato.
     */
    public static UtenteRegistrato getInstance(String userID, String password){
        if (utente == null){
            synchronized (UtenteConnesso.class){ //sincronizzazione.
                if ((utente == null)){

                    try{
                        ArrayList<String> infoUtente = UtenteDao.controlloAccount(userID, password);
                        //Vedi persistence.UtenteDao.controlloAccount(String userID, String password).
                        //infoUtente e' un ArrayList contenente le informazioni relative all'utente connesso.

                        if (!infoUtente.isEmpty()){
                            //Se l'utente non è stato identificato.

                            if (infoUtente.get(5).equals("amministratore")){
                            //Se l'utente e' un amministratore l'istanza del Singleton sara' un utente amministratore...

                            utente = new UtenteAmministratore(infoUtente.get(0), infoUtente.get(1),
                                    infoUtente.get(2), infoUtente.get(3), infoUtente.get(4));

                            }else if (infoUtente.get(5).equals("notAmministratore")) {
                            //... altrimenti sara' un utente registrato.

                            utente = new UtenteRegistrato(infoUtente.get(0), infoUtente.get(1), infoUtente.get(2),
                                    infoUtente.get(3), infoUtente.get(4));
                        }}
                    }catch (SQLException e){
                        System.out.println(e.getMessage());
                    }
                }
            }
        }
        return utente;
    }
}
