package tableModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.table.DefaultTableModel;

import db.DBConnection;
import form.MainForm;
import model.Preduzece;

public class PoslovniPartnerTableModel extends DefaultTableModel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	  private String basicQuery = "SELECT pib, naziv, brTelefona, adresa, mesto from Preduzece where PIB!="+MainForm.getInstance().selectedPred.getPIB();
	  private String orderBy = " ORDER BY Pib";

	public PoslovniPartnerTableModel(Object[] colNames, int rowCount){
		super(colNames, rowCount);
	}
	
	private void fillData(String sql) throws SQLException {
	    setRowCount(0);
	    Statement stmt = DBConnection.getConnection().createStatement();
	    ResultSet rset = stmt.executeQuery(sql);
	    while (rset.next()) {
	      String pib = rset.getString("pib");
	      String naziv = rset.getString("naziv");
	      String telefon = rset.getString("brTelefona");
	      String adresa = rset.getString("adresa");
	      String mesto= rset.getString("mesto");
	      addRow(new String[]{pib, naziv, telefon, adresa, mesto});
	    }
	    rset.close();
	    stmt.close();
	    fireTableDataChanged();
	  } 
	
	  public void open() throws SQLException {
		    fillData(basicQuery + orderBy);
		  }
	
	  
	  public void insertRow(Preduzece pred) throws SQLException{
		  PreparedStatement stmt=DBConnection.getConnection().prepareStatement("Insert into Preduzece (Pib, naziv,"
		  		+ "adresa, brTelefona, mesto) values(?,?,?,?,?)");
		  stmt.setInt(1, pred.getPIB());
		  stmt.setString(2, pred.getNaziv());
		  stmt.setString(3, pred.getAdresa());
		  stmt.setInt(4, pred.getBrTelefona());
		  stmt.setString(5, pred.getMesto());
		  int rowsAffected=stmt.executeUpdate();
		  stmt.close();
		  DBConnection.getConnection().commit();
		  
		  if(rowsAffected>0){
			  fillData(basicQuery+orderBy);
			  fireTableDataChanged();
		  }
		  
	  }
	  
	  public void editRow(Preduzece pred) throws SQLException{
		  PreparedStatement stmt=DBConnection.getConnection().prepareStatement("Update Preduzece set naziv=?, adresa=?, "
		  		+ "brTelefona=?, mesto=? where Pib=?");
		  stmt.setString(1, pred.getNaziv());
		  stmt.setString(2, pred.getAdresa());
		  stmt.setInt(3, pred.getBrTelefona());
		  stmt.setString(4, pred.getMesto());
		  stmt.setInt(5, pred.getPIB());
		  int rowsAffected=stmt.executeUpdate();
		  stmt.close();
		  DBConnection.getConnection().commit();
		  
		  if(rowsAffected>0){
			  fillData(basicQuery+orderBy);
			  fireTableDataChanged();
		  }
	  }

	  
	  public void deleteRow(String pib, int index) throws SQLException{
		  PreparedStatement stmt=DBConnection.getConnection().prepareStatement("Delete from Preduzece where Pib=?");
		  stmt.setInt(1, Integer.parseInt(pib));
		  int rowsAffected=stmt.executeUpdate();
		  stmt.close();
		  DBConnection.getConnection().commit();
		  
		  
		  if(rowsAffected>0){
			  removeRow(index);
			  fireTableDataChanged(); 
		  }
	  }
	  
	  public void search(Preduzece pred) throws SQLException{
		  PreparedStatement stmt=DBConnection.getConnection().prepareStatement(basicQuery
		  		+ " and pib like ? and naziv like ? and adresa like ? and brTelefona like ? and mesto like ?");
		  stmt.setString(1, "%"+pred.getPIB()+"%");
		  stmt.setString(2, "%"+pred.getNaziv()+"%");
		  stmt.setString(3, "%"+pred.getAdresa()+"%");
		  stmt.setString(4, "%"+pred.getBrTelefona()+"%");
		  stmt.setString(5, "%"+pred.getMesto()+"%");
		  
		  ResultSet rset=stmt.executeQuery();
		  while(rset.next()){
			  String pib = rset.getString("pib");
		      String naziv = rset.getString("naziv");
		      String telefon = rset.getString("brTelefona");
		      String adresa = rset.getString("adresa");
		      String mesto=rset.getString("mesto");
		      addRow(new String[]{pib, naziv, telefon, adresa, mesto});
		    }
		    rset.close();
		    stmt.close();
		    fireTableDataChanged();
		  
		  
	  }
}
