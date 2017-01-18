package tableModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.table.DefaultTableModel;

import db.DBConnection;
import model.StavkaPrometnogDokumenta;

public class StavkePDTableModel extends DefaultTableModel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	String baseQuery="Select rbr, cena, kolicina, vrednost, jedMere, naziv, pakovanje from [Stavka prometnog dokumenta], Artikal where artikal=artikal_id ";
	String orderBy=" order by rbr";
	
	public StavkePDTableModel(Object[] colNames, int rowCount){
		super(colNames, rowCount);
	}
	
	public Double findCena(Integer artikal, String magacin, String pg, String pib) throws SQLException{
		Statement stmt=DBConnection.getConnection().createStatement();
		ResultSet rset=stmt.executeQuery("Select [zadnja prodajna cena] as cena from [Magacinska kartica] where artikal="+artikal.toString()+ " and magacin="+magacin+ " and [poslovna godina]="+pg);
		
		if(pib!=null)
			rset=stmt.executeQuery("Select [zadnja prodajna cena] as cena from [Magacinska kartica] a, Sektor b, [Poslovna godina] c where artikal="+artikal+" and c.pg_id=[poslovna godina]  "
					+ " and b.PIB="+pib +" and c.godina like (select godina from [Poslovna godina] where pg_id="+pg+")");
		
		Double cena=null;
		
		while(rset.next()){
			cena=rset.getDouble("cena");
		}
		
		rset.close();
		stmt.close();
		
		return cena;
	}

	
	public void open() throws SQLException{
		fillData(baseQuery+orderBy);
	}
	
	private void fillData(String sql) throws SQLException{
	    setRowCount(0);
	    Statement stmt = DBConnection.getConnection().createStatement();
	    ResultSet rset = stmt.executeQuery(sql);
	    while (rset.next()) {
		      String rbr = rset.getString(1);
		      String cena = rset.getString(2);
		      String kolicina =rset.getString(3);
		      String vrednost=rset.getString(4);
		      String jedMere=rset.getString(5);
		      String naziv=rset.getString(6);
		      String pakovanje=rset.getString(7);
		      
		      addRow(new String[]{rbr, naziv, pakovanje, cena, kolicina, jedMere, vrednost});
		    }
		    rset.close();
		    stmt.close();
		    fireTableDataChanged();
	}
	
	public void insert(StavkaPrometnogDokumenta s) throws SQLException{
		PreparedStatement stmt=DBConnection.getConnection().prepareStatement("Insert into [Stavka prometnog dokumenta]"
				+ " ([prometni dokument], kolicina, cena, vrednost, artikal) values(?,?,?,?,?)");
		
		stmt.setInt(1, s.getPrometniDokID());
		stmt.setInt(2, s.getKolicina());
		stmt.setDouble(3, s.getCena());
		stmt.setDouble(4, s.getVrednost());
		stmt.setInt(5, s.getArtikalID());
		
		int ra=stmt.executeUpdate();
		stmt.close();
		DBConnection.getConnection().commit();
		if(ra>0){
			fillData(baseQuery+" and [prometni dokument]="+s.getPrometniDokID()+orderBy);
			fireTableDataChanged();
		}
		
	}
	
	public void deleteStavka(String id, Integer pd) throws SQLException{
		PreparedStatement stmt=DBConnection.getConnection().prepareStatement("Delete from [Stavka prometnog dokumenta] where rbr=? and [prometni dokument]=?");
		stmt.setInt(1, Integer.parseInt(id));
		stmt.setInt(2, pd);
		int ra= stmt.executeUpdate();
		stmt.close();
		DBConnection.getConnection().commit();
		if(ra>0)
			fillData(baseQuery+" and [prometni dokument]="+pd+orderBy);
//			fireTableDataChanged();
	}
	
	public void openAsChild(String pd){
		String sqlStmt=baseQuery+" and [prometni dokument]="+pd;
		try {
			fillData(sqlStmt);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public boolean checkArtikal(Integer artikalId, Integer pd) throws SQLException{
		Statement stmt=DBConnection.getConnection().createStatement();
		ResultSet rset=stmt.executeQuery("Select rbr from [Stavka prometnog dokumenta] where artikal="+artikalId+" and [prometni dokument]="+pd);
		
		if(rset.next()==true)
			return false;
		else
			return true;
	}
	
}
