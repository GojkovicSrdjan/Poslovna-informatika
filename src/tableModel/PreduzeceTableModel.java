package tableModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.table.DefaultTableModel;

import db.DBConnection;
import model.Preduzece;

public class PreduzeceTableModel extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public PreduzeceTableModel(){
		super();
	}
	
	public Preduzece selectPreduzece(){
		Preduzece p=new Preduzece();
		
		try {
			Statement stmt = DBConnection.getConnection().createStatement();
	
		ResultSet rset=stmt.executeQuery("Select  [PIB], [naziv], [brTelefona], [adresa] from Preduzece where pib=12345");
		while(rset.next()){
			p.setPIB(rset.getInt(1));
			p.setNaziv(rset.getString(2));
			p.setBrTelefona(rset.getInt(3));
			p.setAdresa(rset.getString(4));
		}
	    rset.close();
	    stmt.close();
	    } catch (SQLException e) {
			e.printStackTrace();
		}
		return p;
	}
	
	public Preduzece login(String naziv, String pib) throws SQLException{
		Preduzece p=null;
		
		Statement stmt=DBConnection.getConnection().createStatement();
		ResultSet rset=stmt.executeQuery("select naziv, PIB, brTelefona, adresa from Preduzece where naziv like '"+naziv+"' and PIB="+pib);
		
		while(rset.next()){
			p=new Preduzece();
			p.setNaziv(rset.getString(1));
			p.setPIB(rset.getInt(2));
			p.setBrTelefona(rset.getInt(3));
			p.setAdresa(rset.getString(4));
		}
		rset.close();
		stmt.close();
		
		return p;
	}


}
