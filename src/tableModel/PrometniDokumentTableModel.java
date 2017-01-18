package tableModel;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import db.DBConnection;
import model.PrometniDokument;
import model.StavkaPrometnogDokumenta;

public class PrometniDokumentTableModel extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void addPD(PrometniDokument pd) throws SQLException{
		
		String stmtIf="";
		Integer v=null;
		Integer p = null;
		if(pd.getPoslovniPartnerID()!=null){
			p= pd.getPoslovniPartnerID();
			stmtIf=", [poslovni partner] ";
			
			if(pd.getVrstaDokumenta()!=2)
				v=1;
			else
				v=2;
			
		}		
		
		if(pd.getMagacinID()!=null){
			p= pd.getMagacinID();
			stmtIf=", [magacin_id] ";
			v=3;
		}
		
			
		PreparedStatement stmt=DBConnection.getConnection().prepareStatement(""
				+ "Insert into [Prometni dokument] ([datum nastanka], [status dokumenta], "
				+ "[poslovna godina], [vrsta dokumenta id] "+stmtIf +") values "
				+ "(?,?,?,?,?)");
			
			
		Date d=new Date(pd.getDatumNastanka().getTime()); 
		
		stmt.setDate(1, d);
		stmt.setInt(2, 1);
		stmt.setInt(3, pd.getPoslovnaGodID());
		stmt.setInt(4, v);
		stmt.setInt(5, p);
		
		stmt.executeUpdate();
		stmt.close();
		DBConnection.getConnection().commit();
		
	}
	
	public int selectLastAdded() throws SQLException{
		Statement stmt=DBConnection.getConnection().createStatement();
		ResultSet rset=stmt.executeQuery("SELECT TOP 1 pd_id FROM [Prometni dokument] ORDER BY pd_id DESC ");
		Integer id=null;
		while(rset.next()){
			id=rset.getInt(1);
		}
		rset.close();
		stmt.close();
		return id;
		
	}
	
	public void insertKupac(Integer magacin, Integer pd) throws SQLException{
		PreparedStatement stmt=DBConnection.getConnection().prepareStatement("Insert into kupac (magacin,dokument) values (?,?) ");
		
		stmt.setInt(1, magacin);
		stmt.setInt(2, pd);
		stmt.executeUpdate();
		stmt.close();
		DBConnection.getConnection().commit();
		
	}
	
	public boolean check(Integer id) throws SQLException{
		boolean retVal=false;
		Statement stmt=DBConnection.getConnection().createStatement();
		ResultSet rset=stmt.executeQuery("select * from [Prometni dokument] where pd_id in"
				+ "(select [prometni dokument] from [Stavka prometnog dokumenta] where pd_id=[prometni dokument])"
				+ "and [poslovna godina] in(select pg_id from [Poslovna godina] where pg_id= [poslovna godina] and zakljucena=0) and pd_id="+id);
		
		if(rset.next()!=false)
			retVal=true;
		rset.close();
		stmt.close();
			
		return retVal;
	}
	
	public boolean proknjizi(PrometniDokument pd) throws SQLException, ParseException{
		Integer vrstaPrometa=checkVrsta(pd.getId());
		boolean res=false;
		boolean narucilac=false;
		boolean dobavljac=false;
				
		if(vrstaPrometa==3){
		 	narucilac= insertNarucilac(pd, vrstaPrometa, "knjizenje");
			dobavljac=insertDobavljac(pd, vrstaPrometa, "knjizenje");
			if(narucilac==true && dobavljac==true){
				updateDok(pd.getId(),"knjizenje");
				res=true;
			}
		}else if(vrstaPrometa==1){
			narucilac= insertNarucilac(pd, vrstaPrometa, "knjizenje");
			if(narucilac==true){
				updateDok(pd.getId(),"knjizenje");
				res=true;
			}
		}
		else{
		 	dobavljac= insertDobavljac(pd, vrstaPrometa, "knjizenje");
		 	if(dobavljac==true){
				updateDok(pd.getId(),"knjizenje");
				res=true;
		 	}
		}
		if(res==false)
			updateDok(pd.getId(), "storniranje");

		return res;
	}
	

	public void storniraj(PrometniDokument pd) throws SQLException, ParseException {
		Integer vrstaPrometa=checkVrsta(pd.getId());
		if(vrstaPrometa==3){
			insertNarucilac(pd, vrstaPrometa, "storniranje");
			insertDobavljac(pd, vrstaPrometa, "storniranje");
		}else if(vrstaPrometa==1)
			insertNarucilac(pd, vrstaPrometa, "storniranje");
		else
			insertDobavljac(pd, vrstaPrometa, "storniranje");
		
		updateDok(pd.getId(), "storniranje");
		
		
	}
	
	private boolean insertNarucilac(PrometniDokument pd, Integer vrDok, String tip) throws SQLException{
		boolean res = false;
		Integer narucilacMK = null;
		Integer narucilacMagacin=checkNarucilacMagacin(pd.getId());
		List<PreparedStatement> plist=new ArrayList<>();
		List<StavkaPrometnogDokumenta> stavke=selectStavke(pd.getId());
		int counter = 0;
		Double ukupnaVrUlaza = null;
		Integer ukupnaKolUlaza = null;
		 PreparedStatement stmt1=DBConnection.getConnection().prepareStatement("Insert into [Analitika magacinske kartice] "
					+ "([stavka prometnog dok],vrDok,smer,[magacinska kartica],[tip promene], kolicina, vrednost) values (?,?,?,?,?,?,?)");
		
		PreparedStatement stmt=DBConnection.getConnection().prepareStatement("Update [Magacinska kartica] set [zadnja nabavna cena]=?, [kolicina ulaza]=[kolicina ulaza]+?, [vrednost ulaza]=[vrednost ulaza]+?,"
				+ " [prosecna cena]=([pocetna vrednost]+[vrednost ulaza]-[vrednost izlaza]+[kolicina ulaza]*[zadnja nabavna cena])/([pocetna kolicina]+[kolicina ulaza]-[kolicina izlaza]+[kolicina ulaza]) where magacin=? and artikal=?");
		
	
		if(tip=="knjizenje"){
			for (StavkaPrometnogDokumenta st : stavke) {
				narucilacMK=checkNarucilacMK(pd.getId(),st.getArtikalID());
				if(narucilacMK==null)
					stmt =DBConnection.getConnection().prepareStatement("Insert into [Magacinska kartica] ([zadnja nabavna cena], [kolicina ulaza], [vrednost ulaza], [prosecna cena], magacin, artikal, [kolicina izlaza],"
							+ " [vrednost izlaza], [poslovna godina], [pocetna vrednost], [ukupno stanje], [maloprodajna cena], [pocetna kolicina], [zadnja prodajna cena]) "
							+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
				else
					stmt=DBConnection.getConnection().prepareStatement("Update [Magacinska kartica] set [zadnja nabavna cena]=?, [kolicina ulaza]=[kolicina ulaza]+?, [vrednost ulaza]=[vrednost ulaza]+?,"
							+ " [prosecna cena]=([pocetna vrednost]+[vrednost ulaza]-[vrednost izlaza]+[kolicina ulaza]*[zadnja nabavna cena])/([pocetna kolicina]+[kolicina ulaza]-[kolicina izlaza]+[kolicina ulaza]) where magacin=? and artikal=?");
				
				if(narucilacMK==null){
					stmt.setDouble(1, st.getCena());
					stmt.setInt(2, st.getKolicina());
					stmt.setDouble(3, st.getVrednost());
					stmt.setDouble(4, (st.getVrednost()+st.getKolicina()*st.getCena())/(st.getKolicina()+st.getKolicina()));
					stmt.setInt(5, narucilacMagacin);
					stmt.setInt(6, st.getArtikalID());
					stmt.setInt(7, 0);
					stmt.setInt(8, 0);
					stmt.setInt(9, pd.getPoslovnaGodID());
					stmt.setInt(10, 0);
					stmt.setInt(11, 0);
					stmt.setDouble(12, st.getCena());
					stmt.setInt(13, 0);
					stmt.setDouble(14, st.getCena());
				}else{
					stmt.setDouble(1, st.getCena());
					stmt.setInt(2, st.getKolicina());
					stmt.setDouble(3, st.getVrednost());
					stmt.setInt(4, narucilacMagacin);
					stmt.setInt(5, st.getArtikalID());
				}
//				stmt.addBatch();
				plist.add(stmt);
				
				Integer ukupnaKolicina;
				if(pd.getMagacinID()!=null)
					ukupnaKolicina=checkUkupnaKol(pd.getMagacinID(), st.getArtikalID(), pd.getPoslovnaGodID(), null);
				else
					ukupnaKolicina=checkUkupnaKol(null, st.getArtikalID(), pd.getPoslovnaGodID(), pd.getPoslovniPartnerID());

				if(ukupnaKolicina-st.getKolicina()>=0)
					counter++;
									
			}
			
			if(stavke.size()==counter){
				for (PreparedStatement st : plist) {
					st.executeUpdate();
				}
//				stmt.executeBatch();
//				stmt.clearBatch();
				for (StavkaPrometnogDokumenta st : stavke) {
					stmt1.setInt(1, st.getRbr());
					stmt1.setInt(2, vrDok);
					stmt1.setString(3, "U");
					stmt1.setInt(4, checkNarucilacMK(pd.getId(),st.getArtikalID()));
					stmt1.setInt(5, 1);
					stmt1.setInt(6, st.getKolicina());
					stmt1.setDouble(7, st.getVrednost());
					
					stmt1.addBatch();
				}
				stmt1.executeBatch();
				stmt1.clearBatch();
				DBConnection.getConnection().commit();
				res=true;
			}else{
				DBConnection.getConnection().rollback();
				res=false;
			}
			stmt1.close();
			stmt.close();
		}
		else{
			for (StavkaPrometnogDokumenta st : stavke) {
				stmt.setDouble(1, st.getCena());
				stmt.setInt(2, -st.getKolicina());
				stmt.setDouble(3, -st.getVrednost());
				stmt.setInt(4, narucilacMagacin);
				stmt.setInt(5, st.getArtikalID());
				stmt.executeUpdate();
				
				 stmt1=DBConnection.getConnection().prepareStatement("Insert into [Analitika magacinske kartice] "
							+ "([stavka prometnog dok],vrDok,smer,[magacinska kartica],[tip promene], kolicina, vrednost) values (?,?,?,?,?,?,?)");
				stmt1.setInt(1, st.getRbr());
				stmt1.setInt(2, vrDok);
				stmt1.setString(3, "U");
				stmt1.setInt(4, checkNarucilacMK(pd.getId(), st.getArtikalID()));
				stmt1.setInt(5, 1);
				stmt1.setInt(6, -st.getKolicina());
				stmt1.setDouble(7, -st.getVrednost());
				ukupnaVrUlaza=+st.getVrednost();
				ukupnaKolUlaza=+st.getKolicina();
				stmt1.executeUpdate();
				
				DBConnection.getConnection().commit();
				res=true;
			}
			
			stmt.close();
		}
		return res;
			
	}
	
	private boolean insertDobavljac(PrometniDokument pd, Integer vrDok, String tip) throws SQLException{
		boolean res = false;
		List<StavkaPrometnogDokumenta> stavke=selectStavke(pd.getId());
		int counter = 0;
		int mk;
		Double ukupnaVrIzlaza = null;
		Integer ukupnaKolIzlaza = null;
		Integer artikal=null;
		PreparedStatement stmt1=DBConnection.getConnection().prepareStatement("Update [Magacinska kartica] set [kolicina izlaza]= [kolicina izlaza]+?, [vrednost izlaza]=[vrednost izlaza]+? where mk_id=?");
		
		PreparedStatement stmt=DBConnection.getConnection().prepareStatement("Insert into [Analitika magacinske kartice] "
				+ "([stavka prometnog dok],vrDok,smer,[magacinska kartica],[tip promene], kolicina, vrednost) values (?,?,?,?,?,?,?)");
		
		if(tip=="knjizenje"){
			for (StavkaPrometnogDokumenta st : stavke) {
				stmt.setInt(1, st.getRbr());
				stmt.setInt(2, vrDok);
				stmt.setString(3, "I");
				artikal=st.getArtikalID();
				if(pd.getVrstaDokumenta()!=2)
					stmt.setInt(4, mk=checkDobavljac(pd.getMagacinID(), st.getArtikalID(),null));
				else
					stmt.setInt(4, mk=checkDobavljac(null, st.getArtikalID(),pd.getPoslovniPartnerID()));
					
				stmt.setInt(5, 1);
				stmt.setInt(6, st.getKolicina());
				stmt.setDouble(7, st.getVrednost());
				stmt.addBatch();
				

				ukupnaVrIzlaza=+st.getVrednost();
				ukupnaKolIzlaza=+st.getKolicina();
				stmt1.setInt(1, ukupnaKolIzlaza);
				stmt1.setDouble(2, ukupnaVrIzlaza);
				stmt1.setInt(3, mk);
				
				stmt1.addBatch();
//				plist.add(stmt1);
				
				Integer ukupnaKolicina;
				if(pd.getMagacinID()!=null)
					ukupnaKolicina=checkUkupnaKol(pd.getMagacinID(), st.getArtikalID(), pd.getPoslovnaGodID(), null);
				else
					ukupnaKolicina=checkUkupnaKol(null, st.getArtikalID(), pd.getPoslovnaGodID(), pd.getPoslovniPartnerID());
				
				if(ukupnaKolicina-st.getKolicina()>=0)
					counter++;
			}
			if(stavke.size()==counter){
				stmt1.executeBatch();
//				for (PreparedStatement st : plist) {
//					st.executeUpdate();
//				}
				stmt.executeBatch();
				stmt1.clearBatch();
				stmt.clearBatch();
				DBConnection.getConnection().commit();
				res=true;
			}else{
				DBConnection.getConnection().rollback();
				res=false;
			}
			stmt1.close();
			stmt.close();
		}else{
			for (StavkaPrometnogDokumenta st : stavke) {
				stmt.setInt(1, st.getRbr());
				stmt.setInt(2, vrDok);
				stmt.setString(3, "I");
				artikal=st.getArtikalID();
				if(pd.getVrstaDokumenta()!=2)
					stmt.setInt(4, mk=checkDobavljac(pd.getMagacinID(), st.getArtikalID(),null));
				else
					stmt.setInt(4, mk=checkDobavljac(null, st.getArtikalID(),pd.getPoslovniPartnerID()));
				
				stmt.setInt(5, 1);
				stmt.setInt(6, -st.getKolicina());
				stmt.setDouble(7, -st.getVrednost());
				ukupnaVrIzlaza=+st.getVrednost();
				ukupnaKolIzlaza=+st.getKolicina();
				stmt.executeUpdate();
				

				stmt1=DBConnection.getConnection().prepareStatement("Update [Magacinska kartica] set [kolicina izlaza]= [kolicina izlaza]+?, [vrednost izlaza]=[vrednost izlaza]+? where mk_id=?");
				stmt1.setInt(1, -ukupnaKolIzlaza);
				stmt1.setDouble(2, -ukupnaVrIzlaza);
				stmt1.setInt(3, mk);
				
				stmt1.executeUpdate();
				
				DBConnection.getConnection().commit();
				res=true;
			}
			

			stmt.close();
		}
		return res;
	}
	
	private Integer checkUkupnaKol(Integer magacin, Integer artikal, Integer pg, Integer pib) throws SQLException{
		Integer kolicina = null;
		Statement stmt=DBConnection.getConnection().createStatement();
		ResultSet rset=stmt.executeQuery("Select [pocetna kolicina], [kolicina ulaza], [kolicina izlaza] from [Magacinska kartica] where magacin="+magacin+" and artikal="+artikal+" and [poslovna godina]="+pg);
		if(pib!=null)
			rset=stmt.executeQuery("Select [pocetna kolicina], [kolicina ulaza], [kolicina izlaza] from [Magacinska kartica] a, Sektor b, [Poslovna godina] c where artikal="+artikal+" and c.pg_id=[poslovna godina] and b.PIB="+pib +" and c.godina like (select godina from [Poslovna godina] where pg_id="+pg+")");
		
		while(rset.next()){
			kolicina=rset.getInt(1);
			kolicina+=rset.getInt(2);
			kolicina-=rset.getInt(3);
		}
		
		return kolicina;
	}
	
	private Integer checkVrsta(Integer id) throws SQLException{
		Integer vrsta = null;
		Statement stmt=DBConnection.getConnection().createStatement();
		ResultSet rset=stmt.executeQuery("Select [vrsta dokumenta id] from [Prometni dokument] where pd_id="+id);
		
		while(rset.next()){
			vrsta=rset.getInt(1);
		}
		rset.close();
		stmt.close();
		
		return vrsta;
	}
	
	private Integer checkNarucilacMK(Integer id, Integer artikal) throws SQLException{
		Integer narucilac = null;
		Statement stmt=DBConnection.getConnection().createStatement();
		ResultSet rset=stmt.executeQuery("Select mk_id from [Magacinska kartica] c, Kupac a, [Prometni dokument] b where c.magacin=a.magacin and a.dokument=b.pd_id and pd_id="+id+" and artikal="+artikal);
		
		while(rset.next()){
			narucilac=rset.getInt(1);
		}
		rset.close();
		stmt.close();
		
		return narucilac;
	}
	
	private Integer checkNarucilacMagacin(Integer pd) throws SQLException{
		Integer magacin = null;
		Statement stmt=DBConnection.getConnection().createStatement();
		ResultSet rset=stmt.executeQuery("Select Magacin from Kupac where dokument="+pd);
		
		while(rset.next()){
			magacin=rset.getInt(1);
		}
		rset.close();
		stmt.close();
		
		return magacin;
	}
	
	private Integer checkDobavljac(Integer magacin, Integer artikal, Integer pib) throws SQLException{
		Integer narucilac = null;
		Statement stmt=DBConnection.getConnection().createStatement();
		ResultSet rset=stmt.executeQuery("Select mk_id from [Magacinska kartica] where magacin="+magacin+" and artikal="+artikal);
		if(pib!=null)
			rset=stmt.executeQuery("Select mk_id from [Magacinska kartica] a, Sektor b, Artikal c, Magacin d where b.PIB="+pib+" and a.artikal=c.artikal_id and a.magacin=d.magacin_id and d.sektor=b.sektor_id and artikal="+artikal);
		
		while(rset.next()){
			narucilac=rset.getInt(1);
		}
		rset.close();
		stmt.close();
		
		return narucilac;
	}
	
	public List<StavkaPrometnogDokumenta> selectStavke(Integer id) throws SQLException{
		List<StavkaPrometnogDokumenta> stavke=new ArrayList<>();
		Statement stmt=DBConnection.getConnection().createStatement();
		ResultSet rset=stmt.executeQuery("SELECT [rbr],[kolicina],[cena],[vrednost],[artikal] FROM [Stavka prometnog dokumenta] where [prometni dokument]="+id);
		while(rset.next()){
			StavkaPrometnogDokumenta s=new StavkaPrometnogDokumenta();
			s.setRbr(rset.getInt(1));
			s.setKolicina(rset.getInt(2));
			s.setCena(rset.getDouble(3));
			s.setVrednost(rset.getDouble(4));
			s.setArtikalID(rset.getInt(5));
			stavke.add(s);
		}
		rset.close();
	    stmt.close();
		return stavke;
	}
	
	private java.util.Date getDate() throws ParseException{
		String timeStamp = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
		DateFormat df=new SimpleDateFormat("yyyy-MM-dd");
		
		java.util.Date d=df.parse(timeStamp);
		
		return d;
	}
	
	private void updateDok(Integer id, String tip) throws SQLException, ParseException{
		PreparedStatement stmt=DBConnection.getConnection().prepareStatement("Update [prometni dokument] set [datum knjizenja]=?, [status dokumenta]=? where [pd_id]=?");
		if(tip=="knjizenje"){
			Date d=new Date(getDate().getTime()); 
			stmt.setDate(1, d);
			stmt.setInt(2, 2);
			stmt.setInt(3, id);
		}else{
			stmt=DBConnection.getConnection().prepareStatement("Update [prometni dokument] set [status dokumenta]=? where [pd_id]=?");
			stmt.setInt(1, 3);
			stmt.setInt(2, id);
		}
		
		stmt.executeUpdate();
		stmt.close();
		DBConnection.getConnection().commit();
	}
	
	public void removePD(Integer id) throws SQLException{
		PreparedStatement stmt=DBConnection.getConnection().prepareStatement("Delete from Kupac where dokument=?");
		stmt.setInt(1, id);
		int afr= stmt.executeUpdate();
		if(afr>0){
			stmt=DBConnection.getConnection().prepareStatement("Delete from [prometni dokument] where pd_id=?");
			stmt.setInt(1, id);
			stmt.executeUpdate();
		}
		stmt.close();
		DBConnection.getConnection().commit();
	}
	
	public void addStavkeOtpremnice(Integer otpremnica, Integer primka) throws SQLException{
		PreparedStatement stmt=DBConnection.getConnection().prepareStatement("Insert into [Stavka prometnog dokumenta]([prometni dokument], kolicina, cena, vrednost, artikal)"
				+ " select ? , kolicina, cena, vrednost, artikal  from [Stavka prometnog dokumenta] where [prometni dokument] = ?");
		stmt.setInt(1, otpremnica);
		stmt.setInt(2, primka);
		stmt.executeUpdate();
		stmt.close();
		DBConnection.getConnection().commit();
	}
	
	public PrometniDokument selectPD(Integer id) throws SQLException{
		PrometniDokument pd=new PrometniDokument();
		Statement stmt=DBConnection.getConnection().createStatement();
		ResultSet rset=stmt.executeQuery("Select magacin_id, [datum knjizenja], [datum nastanka], [status dokumenta], pd_id, [poslovna godina], [poslovni partner], [vrsta dokumenta id] from [Prometni dokument] where pd_id="+id);
		
		while(rset.next()){
			pd.setMagacinID(rset.getInt(1));
			pd.setDatumKnjizenja(rset.getDate(2));
			pd.setDatumNastanka(rset.getDate(3));
			pd.setStatusDokumenta(rset.getInt(4));
			pd.setId(rset.getInt(5));
			pd.setPoslovnaGodID(rset.getInt(6));
			pd.setPoslovniPartnerID(rset.getInt(7));
			pd.setVrstaDokumenta(rset.getInt(8));
			
		}
		
		return pd;
	}
}
