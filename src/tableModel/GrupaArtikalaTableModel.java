package tableModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.table.DefaultTableModel;

import db.DBConnection;
import utils.SortUtils;

public class GrupaArtikalaTableModel extends DefaultTableModel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	  private String basicQuery = "SELECT ga_id, nazivGrupe FROM [Grupa artikala]";
	  private String orderBy = " ORDER BY ga_id";
	  private String whereStmt = "";

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
		    fillData(basicQuery + whereStmt + orderBy);
		  }
	  
	  private void checkRow(int index) throws SQLException {

			DBConnection.getConnection().
				setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
			PreparedStatement selectStmt = DBConnection.getConnection().prepareStatement(basicQuery +
					" where ga_id =?");

			String sifra = (String)getValueAt(index, 0);
			selectStmt.setString(1, sifra);

			ResultSet rset = selectStmt.executeQuery();

			String sifraArtikla = "", nazivGrupe = "";
			Boolean postoji = false;
			String errorMsg = "";
			while (rset.next()) {
				sifraArtikla = rset.getString("ga_id").trim();
				nazivGrupe = rset.getString("nazivGrupe");
				postoji = true;
			}
			if (!postoji) {
				removeRow(index);
				fireTableDataChanged();
				errorMsg = "Obrisan";
			}
			else if ((SortUtils.getLatCyrCollator().compare(sifraArtikla, ((String)getValueAt(index, 0)).trim()) != 0) ||
					(SortUtils.getLatCyrCollator().compare(nazivGrupe, (String)getValueAt(index, 1)) != 0))  {
				setValueAt(sifraArtikla, index, 0);
				setValueAt(nazivGrupe, index, 1);
				fireTableDataChanged();
				errorMsg = "Promenjen";
			}
			rset.close();
			selectStmt.close();
			DBConnection.getConnection().setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
			if (errorMsg != "") {
				DBConnection.getConnection().commit();
				throw new SQLException(errorMsg,  errorMsg, 5000);
			}
		}
	  
		 public void deleteRow(int index) throws SQLException {
			 checkRow(index);
			    PreparedStatement stmt = DBConnection.getConnection().prepareStatement(
			        "DELETE FROM [Grupa artikala] ga_id=?");
			    String sifra = (String)getValueAt(index, 0);
			    stmt.setString(1, sifra);
			    //Brisanje iz baze 
			    int rowsAffected = stmt.executeUpdate();
			    stmt.close();
			    DBConnection.getConnection().commit();
			    if (rowsAffected > 0) {
			      // i brisanje iz TableModel-a
			      removeRow(index);
				  fireTableDataChanged();
			    }
			  }
		 

		 public void search(String sifraPretraga, String nazivGrupePretraga) throws SQLException{
			 PreparedStatement stmt = DBConnection.getConnection().prepareStatement(
				        "Select ga_id, nazivGrupe from [Grupa artikala] where ga_id like ? and nazivGrupe like ?");
			 stmt.setString(1, "%"+sifraPretraga+"%");
			 stmt.setString(2, "%"+nazivGrupePretraga+"%");
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
		 
		 public int editRow(String sifra, String nazivGrupe,int index) throws SQLException{
			 int retVal = 0;
			 PreparedStatement stmt= DBConnection.getConnection().prepareStatement("UPDATE [Grupa artikala] set nazivGrupe=? where ga_id=?");
			 stmt.setString(1, nazivGrupe);
			 stmt.setString(2, sifra);
			 int rowsAffected=stmt.executeUpdate();
			 stmt.close();
			 DBConnection.getConnection().commit();
			 if (rowsAffected > 0) {
				 retVal = index;
				 setValueAt(nazivGrupe, index, 1);
			      fireTableDataChanged();
			    }
			    return retVal;
					 
		 }
		 

		  private int sortedInsert(String sifra, String nazivGrupe) { 
			    int left = 0;
			    int right = getRowCount() - 1;   
			    int mid = (left + right) / 2;
			    while (left <= right ) {      
			      mid = (left + right) / 2;
			      String aSifra = (String)getValueAt(mid, 0);      
			      if (SortUtils.getLatCyrCollator().compare(sifra, aSifra) > 0) 
			        left = mid + 1;
			      else if (SortUtils.getLatCyrCollator().compare(sifra, aSifra) < 0)
			        right = mid - 1;
			      else 
			        // ako su jednaki: to ne moze da se desi ako tabela ima primarni kljuc
			        break;      
			    }
			    insertRow(left, new String[] {sifra, nazivGrupe});
			    return left;
			  }
			

			  public int insertRow(String sifra, String nazivGrupe) throws SQLException {
			    int retVal = 0;
			    PreparedStatement stmt = DBConnection.getConnection().prepareStatement(
			      "INSERT INTO [Grupa artikala] (ga_id, nazivGrupe) VALUES (? ,?)");
			    stmt.setString(1, sifra);
			    stmt.setString(2, nazivGrupe);
			    int rowsAffected = stmt.executeUpdate();
			    stmt.close();
			    //Unos sloga u bazu
			    DBConnection.getConnection().commit();
			    if (rowsAffected > 0) {
			      // i unos u TableModel  
			      retVal = sortedInsert(sifra, nazivGrupe);
			      fireTableDataChanged();
			    }
			    return retVal;
			  }

}
