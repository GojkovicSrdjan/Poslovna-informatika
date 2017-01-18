package tableModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.table.DefaultTableModel;

import db.DBConnection;
import model.GrupaArtikala;

public class GrupaArtikalaTableModel extends DefaultTableModel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	  private String basicQuery = "SELECT ga_id, nazivGrupe FROM [Grupa artikala]";
	  private String orderBy = " ORDER BY ga_id";

	public GrupaArtikalaTableModel(Object[] colName, int rowCount){
		super(colName, rowCount);
	}
	
	private void fillData(String sql) throws SQLException {
	    setRowCount(0);
	    Statement stmt = DBConnection.getConnection().createStatement();
	    ResultSet rset = stmt.executeQuery(sql);
	    while (rset.next()) {
	      String sifra = rset.getString("ga_id");
	      String nazivGrupe = rset.getString("nazivGrupe");
	      addRow(new String[]{sifra, nazivGrupe});
	    }
	    rset.close();
	    stmt.close();
	    fireTableDataChanged();
	  } 
	
	  public void open() throws SQLException {
		    fillData(basicQuery + orderBy);
		  }

	  
		 public void deleteRow(int index, String id) throws SQLException {
			 
			    PreparedStatement stmt = DBConnection.getConnection().prepareStatement(
			        "DELETE FROM [Grupa artikala] where ga_id=?");
			    stmt.setInt(1, Integer.parseInt(id));
			    int rowsAffected = stmt.executeUpdate();
			    stmt.close();
			    DBConnection.getConnection().commit();
			    if (rowsAffected > 0) {
			      removeRow(index);
				  fireTableDataChanged();
			    }
			  }
		 

		 public void search(GrupaArtikala ga) throws SQLException{
			 PreparedStatement stmt = DBConnection.getConnection().prepareStatement(
				        "Select ga_id, nazivGrupe from [Grupa artikala] where ga_id like ? and nazivGrupe like ?");
			 stmt.setString(1, "%"+ga.getId()+"%");
			 stmt.setString(2, "%"+ga.getNazivGrupe()+"%");
			 ResultSet rset = stmt.executeQuery();
			    while (rset.next()) {
				      String sifra = rset.getString("ga_id");
				      String nazivGrupe = rset.getString("nazivGrupe");
			      addRow(new String[]{sifra, nazivGrupe});
			    }
			    
			    rset.close();
			    stmt.close();
			    fireTableDataChanged();
		 }
		 
		 public int editRow(GrupaArtikala ga, int index) throws SQLException{
			 int retVal = 0;
			 PreparedStatement stmt= DBConnection.getConnection().prepareStatement("UPDATE [Grupa artikala] set nazivGrupe=? where ga_id=?");
			 stmt.setString(1, ga.getNazivGrupe());
			 stmt.setInt(2, ga.getId());
			 int rowsAffected=stmt.executeUpdate();
			 stmt.close();
			 DBConnection.getConnection().commit();
			 if (rowsAffected > 0) {
				 retVal = index;
				 setValueAt(ga.getNazivGrupe(), index, 1);
			      fireTableDataChanged();
			    }
			    return retVal;
					 
		 }
		 

		  public int insertRow(GrupaArtikala ga) throws SQLException {
			    int retVal = 0;
			    PreparedStatement stmt = DBConnection.getConnection().prepareStatement(
			      "INSERT INTO [Grupa artikala] (nazivGrupe) VALUES (?)");
			    stmt.setString(1, ga.getNazivGrupe());
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
