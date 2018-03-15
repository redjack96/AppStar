package persistance;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FileDao {

    public static void importaFile(File file, String relazione) throws SQLException {

        Connessione.connettiti();
        String importazione = "COPY " + relazione +" FROM \'" + file.getPath() + "\' DELIMITER \',\' csv HEADER";
        //Si e notato che il path del file da importare non deve contenere \Desktop\.

        /*file.setReadable(true);
        file.setWritable(true);*/

        try {
            PreparedStatement ps1 = Connessione.CONN.prepareStatement("DELETE FROM " + relazione);
            ps1.executeUpdate();


            PreparedStatement ps2 = Connessione.CONN.prepareStatement(importazione);
            ps2.execute();
        } catch (SQLException e) {
            System.out.print(e.getMessage());
        } finally {
            Connessione.CONN.close();
        }
    }
}