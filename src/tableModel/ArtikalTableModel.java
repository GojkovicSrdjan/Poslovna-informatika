package tableModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.table.DefaultTableModel;

import db.DBConnection;
import form.MainForm;
import model.Artikal;

public class ArtikalTableModel extends DefaultTableModel {

	private static final long serialVersionUID = 1L;
	
//	  private String basicQuery = "SELECT artikal_id, naziv, jedMere, pakovanje,"
//	  		+ " [grupa artikala] as ga, nazivGrupe FROM Artikal, [Grupa artikala] where [grupa artikala]=ga_id ";
	 private String basicQuery="SELECT  artikal_id, a.naziv as naziv, jedMere, pakovanje, [grupa artikala] as ga, nazivGrupe "
	 		+ "from Artikal a, [Magacinska kartica] b, Magacin c, Sektor d, Preduzece e, [Grupa artikala] f "
	 		+ "where artikal_id=b.artikal and b.magacin=c.magacin_id and c.sektor=d.sektor_id and d.PIB=e.PIB and f.ga_id=a.[grupa artikala] and e.pib="+MainForm.getInstance().selectedPred.getPIB(); 
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
	      String jedMere = rset.getString("jedMere");
	      String pakovanje = rset.getString("pakovanje");
	      String grupa = rset.getString("nazivGrupe");

	      addRow(new String[]{id, naziv, jedMere, pakovanje, grupa});
		  
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
			      removeRow(index);
				  fireTableDataChanged();
			    }
			  }

		 public void search(Artikal a) throws SQLException{
			 PreparedStatement stmt = DBConnection.getConnection().prepareStatement(basicQuery
			 		+ " and artikal_id like ? and naziv like ? and jedMere like ? and pakovanje like ? ");
			 stmt.setString(1, "%"+a.getId()+"%");
			 stmt.setString(2, "%"+a.getNaziv()+"%");
			 stmt.setString(3, "%"+a.getJedinicaMere()+"%");
			 stmt.setString(4, "%"+a.getPakovanje()+"%");
			 
			 ResultSet rset = stmt.executeQuery();
			    while (rset.next()) {
				      String id = rset.getString("artikal_id");
				      String naziv = rset.getString("naziv");
				      String jedMere = rset.getString("jedMere");
				      String pakovanje = rset.getString("pakovanje");
				      
			      addRow(new String[]{id, naziv, jedMere, pakovanje});
			    }
			    
			    rset.close();
			    stmt.close();
			    fireTableDataChanged();
		 }
		 
		 public int editRow(Artikal a, int index) throws SQLException{
			 int retVal = 0;
			 PreparedStatement stmt= DBConnection.getConnection().prepareStatement("UPDATE Artikal set"
			 		+ " naziv=?, jedMere=?, pakovanje=? "
	  		+ "  where artikal_id=?");
			 stmt.setString(1, a.getNaziv());
			 stmt.setString(2, a.getJedinicaMere());
			 stmt.setDouble(3, a.getPakovanje());
			 stmt.setInt(4, a.getId());
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
			      "INSERT INTO Artikal (naziv, jedMere, pakovanje, [grupa artikala]) VALUES (?,?,?,?)");

			    stmt.setString(1, a.getNaziv());
			    stmt.setString(2, a.getJedinicaMere());
			    stmt.setDouble(3, a.getPakovanje());
			    stmt.setInt(4, a.getGrupaArtikalaID());
			    int rowsAffected = stmt.executeUpdate();
			    stmt.close();
			    DBConnection.getConnection().commit();
			    if (rowsAffected > 0) {
				      fireTableDataChanged();
//			      fillData(basicQuery + orderBy);
				      Integer id=selectLastAdded();
				      addRow(new String[]{id.toString(), a.getNaziv(), a.getJedinicaMere(), a.getPakovanje().toString(), a.getGrupaArtikalaID().toString()});
			    }
			    return retVal;
			  }
		  
		  public void openAsChild(String parent, String pg){
				  
			  String sqlStmt="select artikal_id, pakovanje, jedMere, naziv, nazivGrupe from Artikal, [Magacinska kartica], [Grupa artikala]  where artikal_id=artikal and [grupa artikala]=ga_id and magacin="+parent;
			  if(pg!=null)
				   sqlStmt="select artikal_id, pakovanje, jedMere, naziv, nazivGrupe from Artikal, [Magacinska kartica], [Grupa artikala]  where artikal_id=artikal and [grupa artikala]=ga_id and magacin="+parent+" and [poslovna godina]="+pg;

			 try {
				fillData(sqlStmt+orderBy);
			} catch (SQLException e) {
				e.printStackTrace();
			} 
		  }
		  
		  public void openWithPib(String pib){
			  String sqlStmt="select pakovanje,cast(jedMere as varchar)  jedMere, cast(a.naziv as varchar) naziv, min(artikal_id) artikal_id, cast([grupa artikala] as varchar) [grupa artikala], cast(nazivGrupe as varchar) nazivGrupe from Artikal a, [Magacinska kartica] b, Magacin c, Sektor d, Preduzece e, [Grupa artikala] f where artikal_id=b.artikal and b.magacin=c.magacin_id and c.sektor=d.sektor_id and d.PIB=e.PIB and f.ga_id=a.[grupa artikala] and e.pib="+pib;
			  
			  String groupBy=" group by pakovanje, cast(jedMere as varchar), cast(a.naziv as varchar),cast([grupa artikala] as varchar),cast(nazivGrupe as varchar)";
				 try {
					fillData(sqlStmt+groupBy+orderBy);
				} catch (SQLException e) {
					e.printStackTrace();
				} 
		  }

		public void insertMK(Double cena, Integer kolicina, String magacin) throws SQLException {
			PreparedStatement stmt=DBConnection.getConnection().prepareStatement("Insert into [Magacinska kartica] ([zadnja nabavna cena], [kolicina ulaza], [vrednost ulaza], magacin, artikal, [prosecna cena], [kolicina izlaza],"
					+ " [vrednost izlaza], [poslovna godina], [pocetna vrednost], [ukupno stanje], [maloprodajna cena], [pocetna kolicina], [zadnja prodajna cena]) "
					+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			
			stmt.setDouble(1, cena);
			stmt.setInt(2, 0);
			stmt.setDouble(3, 0);
			stmt.setInt(4, Integer.parseInt(magacin));
			stmt.setInt(5, selectLastAdded());
			stmt.setDouble(6, 0);
			stmt.setInt(7, 0);
			stmt.setInt(8, 0);
			stmt.setInt(9, selectPG());
			stmt.setDouble(10, cena*kolicina);
			stmt.setInt(11, 0);
			stmt.setDouble(12, cena);
			stmt.setInt(13, kolicina);
			stmt.setDouble(14, cena);
			
			stmt.executeUpdate();
			DBConnection.getConnection().commit();
			stmt.close();
		}
		
		private int selectPG() throws SQLException{
			int id=0;
			Statement stmt=DBConnection.getConnection().createStatement();
			ResultSet rset=stmt.executeQuery("Select pg_id from [Poslovna godina] where zakljucena=0 and pib="+MainForm.getInstance().selectedPred.getPIB());
			while(rset.next())
				id=rset.getInt(1);

		    rset.close();
		    stmt.close();
			
			return id;
			
		}
		
		private int selectLastAdded() throws SQLException{
			int id=0;
			Statement stmt=DBConnection.getConnection().createStatement();
			ResultSet rset=stmt.executeQuery("SELECT TOP 1 artikal_id FROM Artikal ORDER BY artikal_id DESC");
			while(rset.next())
				id=rset.getInt(1);

		    rset.close();
		    stmt.close();
			
			return id;
		}

}
