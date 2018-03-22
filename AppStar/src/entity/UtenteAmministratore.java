package entity;

import persistence.FileDao;
import persistence.UtenteDao;

import java.io.File;
import java.sql.SQLException;

public class UtenteAmministratore extends UtenteRegistrato {
    //Specializzazione di UtenteRegistrato.

    public UtenteAmministratore(String nome, String cognome, String userID, String password, String email){
        //Costruttore di UtenteAmministratore.

        super(nome, cognome, userID, password, email); //Costruttore di UtenteRegistrato.
        setAmministratore(true);
        //La creazione di un'istanza di UtenteAmministratore imposta automaticamente il booleano 'amministratore' al
        //valore 'true'.
    }

    public void registraNuovoUtente(String nome, String cognome, String userID, String password, String email,
                                    boolean admin){
        try {
            UtenteDao.inserisciAccount(nome, cognome, userID, password, email, admin);
        } catch (SQLException e) {
            System.out.print(e.getMessage());
        }
    }

    public void importaFileCSV(File file, int RB){

        try{
            if (RB == 1){
                FileDao.importaFile(file, "contorni_imp");
            }else if(RB == 2){
                FileDao.importaFile(file, "filamenti_imp");
            }else if(RB == 3){
                FileDao.importaFile(file, "scheletri_imp");
            }else if(RB == 4){
                FileDao.importaFile(file, "stelle_imp");
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
}
