package tableModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.table.DefaultTableModel;

import db.DBConnection;
import form.MainForm;
import model.Magacin;

public class MagacinTableModel extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	  private String basicQuery = "SELECT magacin_id, a.naziv, b.naziv as s FROM Magacin a, sektor b where a.sektor=b.sektor_id and b.pib="+MainForm.getInstance().selectedPred.getPIB();
	  private String orderBy = " ORDER BY magacin_id";
	
	public MagacinTableModel(Object[] colNames, int rowCount){
		super(colNames, rowCount);
	}
	
	
	private void fillData(String sql) throws SQLException {
	    setRowCount(0);
	    Statement stmt = DBConnection.getConnection().createStatement();
	    ResultSet rset = stmt.executeQuery(sql);
	    while (rset.next()) {
	      String id = rset.getString("magacin_id");
	      String naziv = rset.getString("naziv");
	      String sektor =rset.getString("s");
	      
	      addRow(new String[]{id, naziv, sektor});
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
			        "DELETE FROM Magacin where magacin_id=?");
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
		 

		 public void search(Magacin m) throws SQLException{
			 PreparedStatement stmt = DBConnection.getConnection().prepareStatement(
				        "SELECT magacin_id, a.naziv, b.naziv as s, a.sektor FROM Magacin a, sektor b where a.sektor=b.sektor_id and a.magacin_id like ? and a.naziv like ?");
			 stmt.setString(1, "%"+m.getId()+"%");
			 stmt.setString(2, "%"+m.getNaziv()+"%");
			 ResultSet rset = stmt.executeQuery();
			    while (rset.next()) {
				      String id = rset.getString("magacin_id");
				      String naziv = rset.getString("naziv");
				      String sektor=rset.getString("s");
			      addRow(new String[]{id, naziv, sektor});
			    }
			    
			    rset.close();
			    stmt.close();
			    fireTableDataChanged();
		 }
		 
		 public int editRow(Magacin m, int index) throws SQLException{
			 int retVal = 0;
			 PreparedStatement stmt= DBConnection.getConnection().prepareStatement("UPDATE magacin set naziv=? where magacin_id=?");
			 stmt.setString(1, m.getNaziv());
			 stmt.setInt(2, m.getId());
			 int rowsAffected=stmt.executeUpdate();
			 stmt.close();
			 DBConnection.getConnection().commit();
			 if (rowsAffected > 0) {
				 retVal = index;
				 setValueAt(m.getNaziv(), index, 1);
			      fireTableDataChanged();
			    }
			    return retVal;
			
		 }
		 
		 
		  public int insertRow(Magacin m) throws SQLException {
			    int retVal = 0;
			    PreparedStatement stmt = DBConnection.getConnection().prepareStatement(
			      "INSERT INTO magacin (naziv, sektor) VALUES (?,?)");

			    stmt.setString(1, m.getNaziv());
			    stmt.setInt(2, m.getSektorID());
			    int rowsAffected = stmt.executeUpdate();
			    stmt.close();
			    DBConnection.getConnection().commit();
			    if (rowsAffected > 0) {
			      fillData(basicQuery + orderBy);
			      fireTableDataChanged();
			    }
			    return retVal;
			  }
		  
		  public void openAsChild(String sektor){
			  String sqlStmt="SELECT magacin_id, a.naziv, b.naziv as s FROM Magacin a, Sektor b where sektor="+sektor+" and b.sektor_id="+sektor;
			  try {
				fillData(sqlStmt+orderBy);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		  }

}
