package persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import static persistence.Connessione.CONN;

public class TestDao {

    public void req_fun_3_deleteData() throws SQLException{

        Connessione.connettiti();

        String deleteBanda = "DELETE FROM bande WHERE \"STRUMENTO\" = '"+"ANDREA"+"'";

        String deleteStrumento = "DELETE FROM strumenti WHERE \"STRUMENTO\" = '"+"ANDREA"+"'";

        String deleteAmministratore = "DELETE FROM utenti WHERE \"USER_ID\" = '"+"albertone"+"'";

        try{
            PreparedStatement ps1 = CONN.prepareStatement(deleteBanda);
            PreparedStatement ps2 = CONN.prepareStatement(deleteStrumento);
            PreparedStatement ps3 = CONN.prepareStatement(deleteAmministratore);

            ps1.executeUpdate(); ps2.executeUpdate(); ps3.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            CONN.close();
        }
    }

    public int req_fun_6_getNoFilamenti(float lum, float ellipt1, float ellipt2)
            throws SQLException, NumberFormatException{

        Connessione.connettiti();

        int numRic;
        double contrasto = 1.0 + (lum/100.0);

        String queryCount =     "SELECT COUNT(*) AS \"CONTEGGIO\" " +
                "FROM filamenti f JOIN misurazione m ON f.\"NAME\" = m.\"FILAMENTO\" "+
                "WHERE m.\"CONTRAST\" > '" + contrasto + "' AND m.\"ELLIPTICITY\" > '" + ellipt1 +
                "' AND m.\"ELLIPTICITY\" < '" + ellipt2 + "' ";

        try{
            Statement st2 = CONN.createStatement();
            ResultSet rs2 = st2.executeQuery(queryCount);
            rs2.next();
            numRic = rs2.getInt(1);
            System.out.println("NUMERO RICORRENZE TROVATE: " + numRic);
        }catch (SQLException e){
            System.out.println(e.getMessage());
            numRic = 0;
        } finally{
            CONN.close();
        }
        return numRic;
    }

    public int req_fun_7_getNoFilamenti(int seg1, int seg2) throws SQLException{

        Connessione.connettiti();

        if ((seg2 - seg1)<= 2){
            return 0;
        }
        int numRic;

        String queryCount = "SELECT COUNT(*) " +
                "FROM filamenti " +
                "WHERE \"NUM_SEG\" >= ? AND \"NUM_SEG\" <= ?";

        try{
            PreparedStatement ps2 = CONN.prepareStatement(queryCount);
            ps2.setInt(1, seg1);
            ps2.setInt(2, seg2);
            ResultSet rs2 = ps2.executeQuery();
            rs2.next();
            numRic = rs2.getInt(1);
            System.out.println("NUMERO RICORRENZE TROVATE: " + numRic);
        }catch (SQLException e){
            System.out.println(e.getMessage());
            numRic = 0;
        } finally{
            CONN.close();
        }
        return numRic;
    }

    public int req_fun_9_getNoStelle(int idFil, String satellite) throws SQLException{

        Connessione.connettiti();

        int unbound;
        int prestellar;
        int protostellar;
        int tot;

        // Serve per la verifica iniziale e trova le stelle
        String query = "SELECT *\n" +
                "FROM stelle\n" +
                "WHERE \"IDSTAR\" IN ( SELECT stella FROM stelle_in_filamenti_tmp WHERE  in_filamento = (" +
                "SELECT \"NAME\" \n" +
                "FROM filamenti \n" +
                "WHERE \"IDFIL\" = ? AND \"SATELLITE\" = ?))";
        // trova il numero di stelle per ogni tipo
        String query2 = "SELECT s.\"TYPE\", COUNT(*) AS numero_tipo \n" +
                "FROM stelle s \n" +
                "WHERE s.\"IDSTAR\" IN ( SELECT stella \n" +
                "FROM stelle_in_filamenti_tmp \n" +
                "WHERE  in_filamento = (SELECT \"NAME\" \n" +
                "FROM filamenti \n" +
                "WHERE \"IDFIL\" = ? AND \"SATELLITE\" = ?)) \n" +
                "GROUP BY s.\"TYPE\" \n" +
                "ORDER BY s.\"TYPE\" ";
        //inserisce le stelle se la prima query di verifica fallisce
        String insert = "INSERT INTO stelle_in_filamenti_tmp  (\n" +
                "SELECT \"IDSTAR\", f.\"NAME\"\n" +
                "FROM punti_contorni p\n" +
                "     JOIN punti_contorni p2 ON (p2.\"N\" = (p.\"N\" + 1) AND p.\"SATELLITE\" = p2.\"SATELLITE\")\n" +
                "     JOIN contorni c ON (p.\"N\" = c.\"NPCONT\" AND p.\"SATELLITE\" = c.\"SATELLITE\")\n" +
                "     JOIN filamenti f ON (c.\"NAME_FIL\" = f.\"NAME\" AND c.\"SATELLITE\" = f.\"SATELLITE\"), stelle\n" +
                "WHERE f.\"SATELLITE\" =  ? AND f.\"IDFIL\"= ?   AND \"IDSTAR\" % 2 != 0 AND   \"IDSTAR\" % 3 != 0  --aggiungiamo queste 2 per velocizzare\n" +
                "GROUP BY \"IDSTAR\", f.\"NAME\"\n" +
                "HAVING abs(sum(atan(((p.\"GLON_CONT\"-\"GLON_ST\" )*(p2.\"GLAT_CONT\"-\"GLAT_ST\")-(p.\"GLAT_CONT\"-\"GLAT_ST\")*(p2.\"GLON_CONT\"-\"GLON_ST\"))/\n" +
                "\t\t    ((p.\"GLON_CONT\"-\"GLON_ST\")* (p2.\"GLON_CONT\"-\"GLON_ST\") + ((p.\"GLAT_CONT\"-\"GLAT_ST\"))*( (p2.\"GLAT_CONT\"-\"GLAT_ST\")))))) >= 0.01)\n" +
                "ON CONFLICT DO NOTHING";

        try{
            //Prova a cercare i filamenti nella tabella temporanea.
            PreparedStatement ps1 = CONN.prepareStatement(query);
            ps1.setInt(1, idFil);
            ps1.setString(2, satellite);
            ResultSet rs1 = ps1.executeQuery();

            if (!rs1.next()) { //se rs1 non trova i filamenti nella tabella temporanea...
                //...allora li inserisce...
                System.out.println("Inserisco stelle del filamento  nella tabella");
                PreparedStatement ps2 = CONN.prepareStatement(insert);
                ps2.setString(1, satellite); ps2.setInt(2, idFil);
                ps2.executeUpdate();
                //... e ripete la ricerca.
                rs1.close();
                rs1 = ps1.executeQuery(); // ps1 perche' riesegue la query iniziale
            }

            unbound = 0; prestellar = 0; protostellar = 0;
            System.out.println("conto i tipi");
            //In ogni caso vengono contati i tipi di stelle.
            PreparedStatement ps3 = CONN.prepareStatement(query2);
            ps3.setInt(1, idFil);
            ps3.setString(2, satellite);
            ResultSet rs3 = ps3.executeQuery();
            int i = 0;
            while (rs3.next()) {
                switch (i) {
                    case 0:
                        prestellar = rs3.getInt("numero_tipo");
                        break;
                    case 1:
                        protostellar = rs3.getInt("numero_tipo");
                        break;
                    case 2:
                        unbound = rs3.getInt("numero_tipo");
                        break;
                    default:
                        break;
                }
                i++;
            }
            System.out.println("prestar: "+ prestellar  + "\nprotostar: "+ protostellar + "\nunbound: " + unbound);

            //array contiene [numero prestellar, numero protostellar, numero unbound].*/
            tot = prestellar + protostellar + unbound;
            System.out.println("FINE");
        }catch (SQLException e){
            tot = 0;
            System.out.println(e.getMessage());
        } finally {
            CONN.close();
        }
        return tot;
    }

    public ArrayList<Float> req_fun_11_getDistanzaSegCon(int idSeg, int idFil, String Satellite) throws SQLException{
        return FileDao.calcolaDistSegCon(idSeg, idFil, Satellite);
    }

    public boolean req_fun_12_getSuccess(int idFil, String satellite) throws SQLException{
        Connessione.connettiti();

        // Serve per la verifica iniziale e trova le stelle
        String queryStelle = "SELECT *\n" +
                "FROM stelle\n" +
                "WHERE \"IDSTAR\" IN ( SELECT stella FROM stelle_in_filamenti_tmp WHERE  in_filamento = (" +
                "SELECT \"NAME\" \n" +
                "FROM filamenti \n" +
                "WHERE \"IDFIL\" = ? AND \"SATELLITE\" = ?))" +
                "LIMIT 1";
        // calcola la distanza minimo di ogni stella nel filamento dalla spina dorsale
        String queryDistanze =
                "--Questa serve per scrivere idfil al posto del nome del filamento\n" +
                        "CREATE OR REPLACE VIEW nome_filamento AS (SELECT \"NAME\" \n" +
                        "FROM filamenti f\n" +
                        "WHERE f.\"SATELLITE\" =  '"+satellite+"' AND f.\"IDFIL\" = '"+idFil+"' )";

        // query vera e propria
        String queryDistanze2 =
                "--Questa fa il calcolo vero e proprio. Sostituire solo la clausola ORDER BY a seconda di quale --->RADIOBUTTON<----- scegli: ordina per distanza o per flusso.\n" +
                        "SELECT u.\"IDSTAR\", u.\"NAME_STAR\", u.\"GLON_ST\", u.\"GLAT_ST\", u.\"FLUX\", u.\"TYPE\", \n" +
                        "round(min(sqrt((u.\"GLON_ST\"- pp.\"GLON_BR\")^2 + (u.\"GLAT_ST\" - pp.\"GLAT_BR\")^2)),5) as distanza\n" +
                        "FROM stelle u , punti_segmenti pp\n" +
                        "--WHERE la stella e' contenuta nel filamento\n" +
                        "WHERE \"IDSTAR\" in (SELECT stella  \n" +
                        "FROM stelle_in_filamenti_tmp\n" +
                        "WHERE in_filamento IN (SELECT \"NAME\" \n" +
                        "FROM nome_filamento)) \n" +
                        "  AND  (pp.\"GLON_BR\",pp.\"GLAT_BR\") IN (SELECT \"GLON_BR\", \"GLAT_BR\" \n" +
                        "FROM punti_segmenti ppp JOIN segmenti ss \n" +
                        "ON (ppp.\"SEGMENTO\" = ss.\"IDBRANCH\" AND \n" +
                        "ppp.\"NAME_FIL\" = ss.\"NAME_FIL\")\n" +
                        "WHERE ss.\"TYPE\" = 'S' AND ppp.\"NAME_FIL\" IN (SELECT \"NAME\" FROM nome_filamento))\n" +
                        "GROUP BY  u.\"IDSTAR\", u.\"NAME_STAR\", u.\"GLON_ST\", u.\"GLAT_ST\", u.\"FLUX\", u.\"TYPE\"";
        //inserisce le stelle se la prima query di verifica fallisce
        String insert = "INSERT INTO stelle_in_filamenti_tmp  (\n" +
                "SELECT \"IDSTAR\", f.\"NAME\"\n" +
                "FROM punti_contorni p\n" +
                "     JOIN punti_contorni p2 ON (p2.\"N\" = (p.\"N\" + 1) AND p.\"SATELLITE\" = p2.\"SATELLITE\")\n" +
                "     JOIN contorni c ON (p.\"N\" = c.\"NPCONT\" AND p.\"SATELLITE\" = c.\"SATELLITE\")\n" +
                "     JOIN filamenti f ON (c.\"NAME_FIL\" = f.\"NAME\" AND c.\"SATELLITE\" = f.\"SATELLITE\"), stelle\n" +
                "WHERE f.\"SATELLITE\" =  ? AND f.\"IDFIL\"= ?   AND \"IDSTAR\" % 2 != 0 AND   \"IDSTAR\" % 3 != 0  --aggiungi queste 2 per velocizzare\n" +
                "GROUP BY \"IDSTAR\", f.\"NAME\"\n" +
                "HAVING abs(sum(atan(((p.\"GLON_CONT\"-\"GLON_ST\" )*(p2.\"GLAT_CONT\"-\"GLAT_ST\")-(p.\"GLAT_CONT\"-\"GLAT_ST\")*(p2.\"GLON_CONT\"-\"GLON_ST\"))/\n" +
                "\t\t    ((p.\"GLON_CONT\"-\"GLON_ST\")* (p2.\"GLON_CONT\"-\"GLON_ST\") + ((p.\"GLAT_CONT\"-\"GLAT_ST\"))*( (p2.\"GLAT_CONT\"-\"GLAT_ST\")))))) >= 0.01)\n" +
                "ON CONFLICT DO NOTHING";

//------------------------------------------------INSERT PER RICERCA GENERICA-------------------------------------------
        // sostituire alla clausola where della query precedente la seguente clausola
        //"WHERE f.\"SATELLITE\" =  ? AND f.\"IDFIL\"= ?   \n"
//------------------------------------------------------------------------------------------------------------------------
        try{
            //Prova a cercare i filamenti nella tabella temporanea.
            PreparedStatement ps1 = CONN.prepareStatement(queryStelle);
            ps1.setInt(1, idFil);
            ps1.setString(2, satellite);

            ResultSet rs1 = ps1.executeQuery();

            if (!rs1.next()) { //se rs1 non trova i filamenti nella tabella temporanea...
                //...allora li inserisce...
                System.out.println("Inserisco stelle del filamento  nella tabella");
                PreparedStatement ps2 = CONN.prepareStatement(insert);
                ps2.setString(1, satellite); ps2.setInt(2, idFil);
                ps2.executeUpdate();
                //... e ripete la ricerca.
                rs1.close();
            }

            PreparedStatement psView = CONN.prepareStatement(queryDistanze);
            psView.executeUpdate();
            PreparedStatement ps3 = CONN.prepareStatement(queryDistanze2);
            ResultSet rs3 = ps3.executeQuery();
            if (rs3.next()){
                return true;
            }else {
                return false;
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
            return false;
        } finally {
            CONN.close();
        }
    }
}

