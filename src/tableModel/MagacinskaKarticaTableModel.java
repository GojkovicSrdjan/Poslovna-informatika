package tableModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.table.DefaultTableModel;

import db.DBConnection;
import model.MagacinskaKartica;

public class MagacinskaKarticaTableModel extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private String basicQuery="Select mk_id, [prosecna cena] as pCena, [kolicina ulaza] as kUlaza, [vrednost ulaza] as vUlaza,"
			+ " [kolicina izlaza] as kIzlaza, [vrednost izlaza] as vIzlaza,  [pocetna kolicina] as pKolicina, [zadnja nabavna cena] as znc, [zadnja prodajna cena] as zpc,"
			+ " [maloprodajna cena] as mc,[pocetna vrednost] as pv, [ukupno stanje] as us, artikal, magacin from [magacinska kartica] ";
	private String orderBy=" order by mk_id";
	
	
	
	private MagacinskaKartica fillData(String sql) throws SQLException {
		MagacinskaKartica mk=new MagacinskaKartica();
	    setRowCount(0);
	    Statement stmt = DBConnection.getConnection().createStatement();
	    ResultSet rset = stmt.executeQuery(sql);
	    while (rset.next()) {
	      mk.setId(Integer.parseInt(rset.getString("mk_id")));
	      mk.setProsecnaCena(Double.parseDouble(rset.getString("pCena")));
	      mk.setKolicinaUlaza(Integer.parseInt(rset.getString("kUlaza")));
	      mk.setVrednostUlaza(Double.parseDouble(rset.getString("vUlaza")));
	      mk.setKolicinaIzlaza(Integer.parseInt(rset.getString("kIzlaza")));
	      mk.setVrednostIzlaza(Double.parseDouble(rset.getString("vIzlaza")));
	      mk.setPocetnaVrednost(Double.parseDouble(rset.getString("pv")));
	      mk.setUkupnoStanje(Double.parseDouble(rset.getString("us")));
	      mk.setArtikalID(Integer.parseInt(rset.getString("artikal")));
	      mk.setMagacinID(Integer.parseInt(rset.getString("magacin")));
	      mk.setPocetnaKolicina(Integer.parseInt(rset.getString("pKolicina")));
	      mk.setZadnjaNabavnaCena(Double.parseDouble(rset.getString("znc")));
	      mk.setMaloprodajnaCena(Double.parseDouble(rset.getString("mc")));
	      mk.setZadnjaProdajnaCena(Double.parseDouble(rset.getString("zpc")));
	    }
	    rset.close();
	    stmt.close();
	    fireTableDataChanged();
	    
	    return mk;
	  } 
	
	  
	  public MagacinskaKartica openAsChild(String artikal){
		  String sqlStmt=basicQuery+"where artikal= "+artikal;
		  MagacinskaKartica mk=new MagacinskaKartica();
		  try {
			mk=fillData(sqlStmt+orderBy);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  return mk;
	  }
	  
}
