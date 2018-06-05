package persistence;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import static persistence.Connessione.CONN;

public class TestDao {
    public void deleteAlberto() throws SQLException {
        Connessione.connettiti();

        String deleteAmministratore = "DELETE FROM utenti WHERE \"USER_ID\" = '" + "albertone" + "'";
        try {
            PreparedStatement ps3 = CONN.prepareStatement(deleteAmministratore);
            ps3.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            CONN.close();
        }
    }

    public void req_fun_3_deleteDataStrumento() throws SQLException {

        Connessione.connettiti();

        String deleteBanda = "DELETE FROM bande WHERE \"STRUMENTO\" = '" + "ANDREA" + "'";

        String deleteStrumento = "DELETE FROM strumenti WHERE \"STRUMENTO\" = '" + "ANDREA" + "'";

        try {
            PreparedStatement ps1 = CONN.prepareStatement(deleteBanda);
            PreparedStatement ps2 = CONN.prepareStatement(deleteStrumento);

            ps1.executeUpdate();
            ps2.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            CONN.close();
        }
    }

    public void req_fun_3_deleteDataSatellite() throws SQLException {

        Connessione.connettiti();

        String deleteAgenzia = "DELETE FROM agenzie WHERE \"NAME_AG\" = '" + "ASI" + "'";

        String deleteSatellite = "DELETE FROM satelliti WHERE \"NAME_SAT\" = '" + "AGILE" + "'";

        try {
            PreparedStatement ps1 = CONN.prepareStatement(deleteAgenzia);
            PreparedStatement ps2 = CONN.prepareStatement(deleteSatellite);

            ps1.executeUpdate();
            ps2.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            CONN.close();
        }
    }
}
