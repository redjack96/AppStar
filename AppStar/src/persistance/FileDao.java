package persistance;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FileDao {

    private static String canc = "DELETE FROM ";

    public static void importaFileContorni(File file) throws SQLException {
        //TODO se il file non ha le stesse colonne dei requisiti, annullare l'operazione

        Connessione.connettiti();
        String importazione = "COPY contorni FROM \'" + file.getPath() + "\' DELIMITER \',\' csv HEADER";

        try {
            System.out.println("-----------Importazione in corso-------------");
            //prepara la cancellazione
            PreparedStatement ps1 = Connessione.CONN.prepareStatement(canc + "contorni");
            ps1.executeUpdate();


            PreparedStatement ps2 = Connessione.CONN.prepareStatement(importazione);
            ps2.execute();
            System.out.println("Tabella aggiornata!");
        } catch (SQLException e) {
            System.out.print(e.getMessage());
        } finally {
            Connessione.CONN.close();
        }
    }
}