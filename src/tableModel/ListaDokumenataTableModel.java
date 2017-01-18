package tableModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.table.DefaultTableModel;

import db.DBConnection;

public class ListaDokumenataTableModel extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	String baseQuery="select pd_id, [datum nastanka], [status dokumenta],[poslovna godina], [poslovni partner], [vrsta dokumenta id], [magacin_id] from [Prometni dokument]  ";
	String orderBy=" order by pd_id";
	
	public ListaDokumenataTableModel(Object[] colNames, int rowCount){
		super(colNames, rowCount);
	}
	
	private void fillData(String sql) throws SQLException{
	    setRowCount(0);
	    Statement stmt = DBConnection.getConnection().createStatement();
	    ResultSet rset = stmt.executeQuery(sql);
	    while (rset.next()) {
	      String id = rset.getString(1);
	      String datumNastanka = rset.getString(2);
	      Integer status =rset.getInt(3);
	      String statusS = null;
	      if(status==1)
	    	  statusS="U formiranju";
	      
	      else if (status==2) 
			statusS="Proknjizen";
	      
	      else
	    	  statusS="Storniran";
	    	  
	      String pg = rset.getString(4);
	      String pp = rset.getString(5);
	      String magacin=rset.getString(7);
	      Integer vd=rset.getInt(6);
	      String vdS=null;
	      if(vd==1)
	    	  vdS="Primka";
	      
	      else if(vd==2)
	    	  vdS="Otpremnica";
	      
	      else
	    	  vdS="Medjumagacinski dokument";
	      
	      addRow(new String[]{id, datumNastanka, statusS, vdS, pg, pp, magacin});
	    }
	    rset.close();
	    stmt.close();
	    fireTableDataChanged();
	}
	
	public void open(String magacinId, String pib) throws SQLException{
		if(magacinId==null)
			fillData(baseQuery+ "where [vrsta dokumenta id]!=2 and [poslovni partner]="+pib+orderBy);
		else
			fillData(baseQuery+"where [vrsta dokumenta id]!=2  and magacin_id="+magacinId+orderBy);
	}

	public void openOtpremnice(String pib) throws SQLException{
		fillData(baseQuery+ "where [vrsta dokumenta id]=2 and [poslovni partner]="+pib+orderBy);
	}
}
