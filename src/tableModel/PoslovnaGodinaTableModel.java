package tableModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.table.DefaultTableModel;

import db.DBConnection;
import form.MainForm;
import model.PoslovnaGodina;

public class PoslovnaGodinaTableModel extends DefaultTableModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	String basicQuery="Select pg_id, godina, PIB, zakljucena from [Poslovna godina] where pib="+MainForm.getInstance().selectedPred.getPIB().toString();
	String orderBy=" Order by godina";

	public PoslovnaGodinaTableModel(Object[] colNames,int rowCount){
		super(colNames, rowCount);
	}
	
	private void fillData(String sql) throws SQLException{
		setRowCount(0);
		Statement stmt=DBConnection.getConnection().createStatement();
		ResultSet rset=stmt.executeQuery(sql);
		while(rset.next()){
			String id=rset.getString(1);
			String godina=rset.getString(2);
			String pib=rset.getString(3);
			boolean zakljucena=rset.getBoolean(4);
			String z;
			if(zakljucena!=true)
				z="Nije zakljucena";
			else
				z="Zakljucena";
			addRow(new String[]{id, godina, z});		
		}
		rset.close();
	    stmt.close();
	    fireTableDataChanged();
		
	}
	
	public void open() throws SQLException{
		fillData(basicQuery+orderBy);
	}
	
	public void insertRow(PoslovnaGodina pg) throws SQLException{
		PreparedStatement stmt=DBConnection.getConnection().prepareStatement("Insert into [Poslovna godina] (pib,godina,zakljucena) values (?,?,0)");
		stmt.setInt(1, MainForm.getInstance().selectedPred.getPIB());
		stmt.setInt(2, pg.getGodina());
		int rowsAffected=stmt.executeUpdate();
		stmt.close();
	    DBConnection.getConnection().commit();
	    if (rowsAffected > 0) {
	      fillData(basicQuery + orderBy);
	      fireTableDataChanged();
	    }
	}
	
	public int editRow(PoslovnaGodina pg, int index) throws SQLException{
		boolean otvorenaNova=otvorenaNova(pg.getId());
		boolean dokUFormiranju=dokUFormiranju(pg.getId());
		
		if(otvorenaNova==true && dokUFormiranju==false){
			PreparedStatement stmt=DBConnection.getConnection().prepareStatement("Update [Poslovna godina] set zakljucena=? where pg_id=?");
			stmt.setBoolean(1, pg.isZakljucena());
			stmt.setInt(2, pg.getId());
			int rowsAffected=stmt.executeUpdate();
			stmt.close();
			DBConnection.getConnection().commit();
			if (rowsAffected > 0) {
				String z;
				if(pg.isZakljucena()!=true)
					z="Nije zakljucena";
				else
					z="Zakljucena";
				 setValueAt(z, index, 2);
			     fireTableDataChanged();
			}
			return 0;
		}else if(otvorenaNova==true && dokUFormiranju!=false)
			return 1;
		else
			return 2;
	}		
	
	public void search(PoslovnaGodina pg) throws SQLException{
		PreparedStatement stmt=DBConnection.getConnection().prepareStatement(basicQuery+ " where pg_id like ? and godina like ?");
		stmt.setString(1, "%"+pg.getId()+"%");
		stmt.setString(2, "%"+pg.getGodina()+"%");
		ResultSet rset=stmt.executeQuery();
		while(rset.next()){
			String id=rset.getString(1);
			String godina=rset.getString(2);
			String pib=rset.getString(3);
			boolean zakljucena=rset.getBoolean(4);
			String z;
			if(zakljucena!=true)
				z="Nije zakljucena";
			else
				z="Zakljucena";
			addRow(new String[]{id, godina, z});		
		}
		rset.close();
	    stmt.close();
	    fireTableDataChanged();
		
	}
	
	 public boolean deleteRow(int index, String id) throws SQLException {
		 boolean otvorenaNova=otvorenaNova(Integer.parseInt(id));
			 if(otvorenaNova==true){
			    PreparedStatement stmt = DBConnection.getConnection().prepareStatement(
			        "DELETE FROM [Poslovna godina] where pg_id=?");
			    stmt.setInt(1, Integer.parseInt(id));
			    int rowsAffected = stmt.executeUpdate();
			    stmt.close();
			    DBConnection.getConnection().commit();
			    if (rowsAffected > 0) {
			      removeRow(index);
				  fireTableDataChanged();
			    }
			    return true;
			 }else
				 return false;
		 }
	
	 public void openNZ(){
		 try {
			fillData(basicQuery+" and zakljucena=0 "+orderBy);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }
	 
	 private boolean otvorenaNova(Integer id) throws SQLException{
		 
		 
		 Statement stmt=DBConnection.getConnection().createStatement();
		 ResultSet rset=stmt.executeQuery("Select pg_id, godina from [Poslovna godina] where zakljucena=0 and pg_id!="+id);
		 if(rset.next()==true)
			 return true;
		 else
			 return false;
	 }
	 
	 private boolean dokUFormiranju(Integer id) throws SQLException{
		 
		 Statement stmt=DBConnection.getConnection().createStatement();
		 ResultSet rset=stmt.executeQuery("Select pg_id, godina from [Poslovna godina] a,   [Prometni dokument] b where pg_id=[poslovna godina] and [status dokumenta]=1 and pg_id="+id);
		 if(rset.next()!=true)
			 return false;
		 else
			 return true;
	 }
	
}
