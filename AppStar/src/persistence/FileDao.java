package persistence;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static persistence.Connessione.CONN;

public class FileDao {

    public static ArrayList calcolaCentroide(String nomeFil, String idFil, String satellite) throws SQLException{
        //Calcola la posizione del centroide del filamento specificato.

        ArrayList<String> centroide = new ArrayList<>(2);

        Connessione.connettiti();
        String query1 = "SELECT AVG(p.\"GLON_CONT\") AS lon_centroide, AVG(p.\"GLAT_CONT\") AS lat_centroide\n" +
                "FROM punti_contorni p JOIN contorni c ON p.\"N\" = c.\"NPCONT\" AND p.\"SATELLITE\" = c.\"SATELLITE\"\n" +
                "WHERE c.\"NAME_FIL\" = ?";

        String query2 = "SELECT AVG(p.\"GLON_CONT\") AS lon_centroide, AVG(p.\"GLAT_CONT\") AS lat_centroide\n" +
                "FROM punti_contorni p JOIN contorni c ON p.\"N\" = c.\"NPCONT\" " +
                "JOIN filamenti f ON f.\"NAME\" = c.\"NAME_FIL\" " +
                "WHERE f.\"IDFIL\" = ? AND p.\"SATELLITE\" = ?";

        try{
            PreparedStatement ps1;
            if (idFil.equals("")){
                ps1 = CONN.prepareStatement(query1);
                ps1.setString(1, nomeFil);

            }else{
                ps1 = CONN.prepareStatement(query2);
                ps1.setString(1, idFil);
                ps1.setString(2, satellite);

            }
            ResultSet rs1 = ps1.executeQuery();
            rs1.next();
            BigDecimal lon_centroide = rs1.getBigDecimal(1);
            BigDecimal lat_centroide = rs1.getBigDecimal(2);
            centroide.add(0, lon_centroide.toString());
            centroide.add(1, lat_centroide.toString());
            return centroide;
        }catch (SQLException e){
            System.out.println(e.getMessage());
            return new ArrayList(2);
        } finally{
            CONN.close();
        }
    }
}
