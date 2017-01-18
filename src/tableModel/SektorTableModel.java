package tableModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.table.DefaultTableModel;

import db.DBConnection;
import form.MainForm;
import model.GrupaArtikala;
import model.Sektor;

public class SektorTableModel extends DefaultTableModel {
	
	private static final long serialVersionUID = 1L;
	
	  private String basicQuery = "SELECT sektor_id, naziv, adresa FROM Sektor where PIB="+MainForm.getInstance().selectedPred.getPIB();
	  private String orderBy = " ORDER BY sektor_id";
	
	public SektorTableModel(Object[] colNames, int rowCount){
		super(colNames, rowCount);
	}
	
	private void fillData(String sql) throws SQLException {
	    setRowCount(0);
	    Statement stmt = DBConnection.getConnection().createStatement();
	    ResultSet rset = stmt.executeQuery(sql);
	    while (rset.next()) {
	      String Id = rset.getString("sektor_id");
	      String naziv = rset.getString("naziv");
	      String adresa= rset.getString("adresa");
	      addRow(new String[]{Id, naziv, adresa});
	    }
	    rset.close();
	    stmt.close();
	    fireTableDataChanged();
	  } 
	
	  public void open() throws SQLException {
		    fillData(basicQuery + orderBy);
		  }
	  
		 public void deleteRow(int index, String id) throws SQLException {
			 //checkRow(index);
			    PreparedStatement stmt = DBConnection.getConnection().prepareStatement(
			        "DELETE FROM Sektor where sektor_id=?");
//			    String sifra = (String)getValueAt(index, 0);
			    stmt.setInt(1, Integer.parseInt(id));
			    int rowsAffected = stmt.executeUpdate();
			    stmt.close();
			    DBConnection.getConnection().commit();
			    if (rowsAffected > 0) {
			      removeRow(index);
				  fireTableDataChanged();
			    }
			  }
		 
		 public void search(Sektor s) throws SQLException{
			 PreparedStatement stmt = DBConnection.getConnection().prepareStatement(
					 basicQuery + " and sektor_id like ? and naziv like ? and adresa like ? ");
			 stmt.setString(1, "%"+s.getId()+"%");
			 stmt.setString(2, "%"+s.getNaziv()+"%");
			 stmt.setString(3, "%"+s.getAdresa()+"%");
			 ResultSet rset = stmt.executeQuery();
			    while (rset.next()) {
				      String Id = rset.getString("sektor_id");
				      String naziv = rset.getString("naziv");
				      String adresa = rset.getString("adresa");
			      addRow(new String[]{Id, naziv, adresa});
			    }
			    
			    rset.close();
			    stmt.close();
			    fireTableDataChanged();
		 }
		 
		 public int editRow(Sektor s, int index) throws SQLException{
			 int retVal = 0;
			 PreparedStatement stmt= DBConnection.getConnection().prepareStatement("UPDATE Sektor set naziv=?, adresa=? where sektor_id=?");
			 stmt.setString(1, s.getNaziv());
			 stmt.setString(2, s.getAdresa());
			 stmt.setInt(3, s.getId());
			 int rowsAffected=stmt.executeUpdate();
			 stmt.close();
			 DBConnection.getConnection().commit();
			 if (rowsAffected > 0) {
				 retVal = index;
				 setValueAt(s.getNaziv(), index, 1);
				 setValueAt(s.getAdresa(), index, 2);
			      fireTableDataChanged();
			    }
			    return retVal;
					 
		 }
		 
		  public int insertRow(Sektor s) throws SQLException {
			  MainForm mf=new MainForm();
			    int retVal = 0;
			    PreparedStatement stmt = DBConnection.getConnection().prepareStatement(
			      "INSERT INTO Sektor (naziv, adresa, pib) VALUES (?,?,?)");

//			    		stmt.setInt(1, ga.getId());
			    stmt.setString(1, s.getNaziv());
			    stmt.setString(2, s.getAdresa());
			    stmt.setInt(3, MainForm.getInstance().selectedPred.getPIB());
			    int rowsAffected = stmt.executeUpdate();
			    stmt.close();
			    DBConnection.getConnection().commit();
			    if (rowsAffected > 0) {
			      fillData(basicQuery + orderBy);
			      fireTableDataChanged();
			    }
			    return retVal;
			  }

}
