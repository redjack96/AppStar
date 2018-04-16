package entity;

import persistence.FileDao;
import persistence.UtenteDao;
import java.io.File;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;

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
    //chiamato da control.FileSatelliteController in importaFile()
    public int importaFileCSV(File file, int RB, String satellite){
        int r;
        //fa in modo che se il nome del satellite non e' compreso nel file, non si procede all'importazione.
        if (!(file.getName().toLowerCase().contains(satellite.toLowerCase()))){
            System.out.println("Il nome del file non contiene il satellite " + satellite);
            return -1;
        }
        try{
            if (RB == 1){
                FileDao.importaFile(file, "contorni_imp", satellite);
            }else if(RB == 2){
                FileDao.importaFile(file, "filamenti_imp", satellite);
            }else if(RB == 3){
                FileDao.importaFile(file, "scheletri_imp", satellite);
            }else if(RB == 4){
                FileDao.importaFile(file, "stelle_imp", satellite);
            }
            r = 1;
        }catch (SQLException e){
            System.out.println(e.getMessage());
            r = -1;
        }
        return r;
    }

    public void inserisciNuoviDatiSatellite(String nomeAgenzia, String nomeSatellite, LocalDate dataInizio,
                                            Period durata){
        try{
            FileDao.inserisciFileDatiSatellite(nomeAgenzia, nomeSatellite, dataInizio, durata);
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public void inserisciNuoviDatiStrumento(float banda, String strumento){

        try{
            FileDao.inserisciDatiStrumento(banda, strumento);
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public void troncaDatiSatellite(){

        try{
            FileDao.troncaImp();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
}
