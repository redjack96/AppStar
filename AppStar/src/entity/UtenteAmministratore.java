package entity;

import persistence.FileImportazioneDao;
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
                FileImportazioneDao.importaFile(file, "contorni_imp", satellite);
            }else if(RB == 2){
                FileImportazioneDao.importaFile(file, "filamenti_imp", satellite);
            }else if(RB == 3){
                FileImportazioneDao.importaFile(file, "scheletri_imp", satellite);
            }else if(RB == 4){
                FileImportazioneDao.importaFile(file, "stelle_imp", satellite);
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
            FileImportazioneDao.inserisciFileDatiSatellite(nomeAgenzia, nomeSatellite, dataInizio, durata);
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public boolean inserisciNuoviDatiStrumento(float banda, String strumento, String satellite){

        try{
            FileImportazioneDao.inserisciDatiStrumento(banda, strumento, satellite);
            return true;
        }catch (SQLException e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    public void troncaDatiSatellite(){

        try{
            FileImportazioneDao.troncaImp();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
}
