package tableModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.table.DefaultTableModel;

import db.DBConnection;
import model.Artikal;

public class ArtikalTableModel extends DefaultTableModel {

	private static final long serialVersionUID = 1L;
	
	  private String basicQuery = "SELECT artikal_id, naziv, opis, jedMere, pakovanje,"
	  		+ " [grupa artikala] as ga, nazivGrupe FROM Artikal, [Grupa artikala] where [grupa artikala]=ga_id ";
	  private String orderBy = " ORDER BY artikal_id";
	
	public ArtikalTableModel(Object[] colNames, int rowCount){
		super(colNames, rowCount);
	}
	
	private void fillData(String sql) throws SQLException {
	    setRowCount(0);
	    Statement stmt = DBConnection.getConnection().createStatement();
	    ResultSet rset = stmt.executeQuery(sql);
	    while (rset.next()) {
	      String id = rset.getString("artikal_id");
	      String naziv = rset.getString("naziv");
	      String opis = rset.getString("opis");
	      String jedMere = rset.getString("jedMere");
	      String pakovanje = rset.getString("pakovanje");
	      String grupa = rset.getString("nazivGrupe");
	      addRow(new String[]{id, naziv, opis, jedMere, pakovanje, grupa});
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
			        "DELETE FROM Artikal where artikal_id=?");
			    stmt.setInt(1, Integer.parseInt(id));
			    int rowsAffected = stmt.executeUpdate();
			    stmt.close();
			    DBConnection.getConnection().commit();
			    if (rowsAffected > 0) {
			      // i brisanje iz TableModel-a
			      removeRow(index);
				  fireTableDataChanged();
			    }
			  }

		 public void search(Artikal a) throws SQLException{
			 PreparedStatement stmt = DBConnection.getConnection().prepareStatement(basicQuery
			 		+ " and artikal_id like ? and naziv like ? and jedMere like ? and pakovanje like ? and opis like ? ");
			 stmt.setString(1, "%"+a.getId()+"%");
			 stmt.setString(2, "%"+a.getNaziv()+"%");
			 stmt.setString(3, "%"+a.getJedinicaMere()+"%");
			 stmt.setString(4, "%"+a.getPakovanje()+"%");
			 stmt.setString(5, "%"+a.getOpis()+"%");
			 
			 ResultSet rset = stmt.executeQuery();
			    while (rset.next()) {
				      String id = rset.getString("artikal_id");
				      String naziv = rset.getString("naziv");
				      String jedMere = rset.getString("jedMere");
				      String pakovanje = rset.getString("pakovanje");
				      String opis = rset.getString("opis");
				      
			      addRow(new String[]{id, naziv, jedMere, pakovanje, opis});
			    }
			    
			    rset.close();
			    stmt.close();
			    fireTableDataChanged();
		 }
		 
		 public int editRow(Artikal a, int index) throws SQLException{
			 int retVal = 0;
			 PreparedStatement stmt= DBConnection.getConnection().prepareStatement("UPDATE Artikal set"
			 		+ " naziv=?, jedMere=?, pakovanje=?, opis=?"
	  		+ "  where artikal_id=?");
			 stmt.setString(1, a.getNaziv());
			 stmt.setString(2, a.getJedinicaMere());
			 stmt.setString(3, a.getPakovanje());
			 stmt.setString(4, a.getOpis());
			 stmt.setInt(5, a.getId());
			 int rowsAffected=stmt.executeUpdate();
			 stmt.close();
			 DBConnection.getConnection().commit();
			 if (rowsAffected > 0) {
				 retVal = index;
				 fillData(basicQuery + orderBy);
			      fireTableDataChanged();
			    }
			    return retVal;
					 
		 }
			

		  public int insertRow(Artikal a) throws SQLException {
			    int retVal = 0;
			    PreparedStatement stmt = DBConnection.getConnection().prepareStatement(
			      "INSERT INTO Artikal (naziv, jedMere, pakovanje, opis, [grupa artikala]) VALUES (?,?,?,?,?)");

			    stmt.setString(1, a.getNaziv());
			    stmt.setString(2, a.getJedinicaMere());
			    stmt.setString(3, a.getPakovanje());
			    stmt.setString(4, a.getOpis());
			    stmt.setInt(5, a.getGrupaArtikalaID());
			    int rowsAffected = stmt.executeUpdate();
			    stmt.close();
			    DBConnection.getConnection().commit();
			    if (rowsAffected > 0) {
			      fillData(basicQuery + orderBy);
			      fireTableDataChanged();
			    }
			    return retVal;
			  }
		  
		  public void openAsChild(String parent){
			  String sqlStmt="select artikal_id, pakovanje, jedMere, naziv,opis, nazivGrupe from Artikal, [Magacinska kartica], [Grupa artikala]  where artikal_id=artikal and [grupa artikala]=ga_id and magacin="+parent;
			  
			 try {
				fillData(sqlStmt+orderBy);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		  }

}
