package tableModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.table.DefaultTableModel;

import db.DBConnection;

public class AnalitikaMKTableModel extends DefaultTableModel {

	private static final long serialVersionUID = 1L;
	
	String baseQuery="Select  smer, b.kolicina, b.[vrednost], d.naziv as vrsta, e.naziv as tip, [pocetna kolicina], [pocetna vrednost], [prosecna cena], [zadnja nabavna cena], [maloprodajna cena], [zadnja prodajna cena] from [Magacinska kartica] a, [Analitika magacinske kartice] b, [Vrsta dokumenta] d, [Tip promene] e where  mk_id=b.[magacinska kartica]  and vrDok=[vrsta dokumenta id] and [tip promene]=tip_id and a.mk_id=b.[magacinska kartica] ";
	
	String orderBy=" order by amk_id";
	
	private void fillData(String sql) throws SQLException{
		Integer ukupnaKolicina=0;
		Double ukupnaVrednost=0.0;
		setRowCount(0);
		Statement stmt = DBConnection.getConnection().createStatement();
	    ResultSet rset = stmt.executeQuery(sql);
	    while(rset.next()){
	    	String smer=rset.getString(1);
	    	Integer kolicina=rset.getInt(2);
	    	Double vrednost=rset.getDouble(3);
	    	String vrsta=rset.getString(4);
	    	String tip=rset.getString(5);
	    	Integer pocKolicina=rset.getInt(6);
	    	
	    	
	    	if(ukupnaKolicina==0){
	    		if(smer.equals("U"))
	    			ukupnaKolicina+=pocKolicina+kolicina;
	    		else
	    			ukupnaKolicina+=pocKolicina-kolicina;
	    	}
	    	else{
	    		if(smer.equals("U"))
	    			ukupnaKolicina+=kolicina;
	    		else
	    			ukupnaKolicina-=kolicina;
	    	}

	    	Double pocVrednost=rset.getDouble(7);
	    	
	    	if(ukupnaVrednost==0.0){
	    		if(smer.equals("U"))
	    			ukupnaVrednost+=pocVrednost+vrednost;
	    		else
	    			ukupnaVrednost+=pocVrednost-vrednost;
	    	}else{
	    		if(smer.equals("U"))
		    		ukupnaVrednost+=vrednost;
		    	else
		    		ukupnaVrednost-=vrednost;
	    	}
	    	
	    	Double pCena=rset.getDouble(8);
	    	Double znc=rset.getDouble(9);
	    	Double mpc=rset.getDouble(10);
	    	Double zpc=rset.getDouble(11);
	    	
	    	addRow(new String[]{ smer, kolicina.toString(), vrednost.toString(), vrsta, tip,
	    			pocKolicina.toString(), ukupnaKolicina.toString() ,
	    			pocVrednost.toString(), ukupnaVrednost.toString(), pCena.toString(), znc.toString(),
	    			mpc.toString(), zpc.toString()});
	    	
	    }
	    rset.close();
	    stmt.close();
	    fireTableDataChanged();
	}
	
	public AnalitikaMKTableModel(Object [] colNames, int rowCount){
		super(colNames, rowCount);
	}

	public void open(String magacin, String artikal, String poslovnaGod) {
		try {
			fillData(baseQuery+" and magacin="+magacin+" and a.artikal="+artikal+" and [poslovna godina]="+poslovnaGod+orderBy);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
