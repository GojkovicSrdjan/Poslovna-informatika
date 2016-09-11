package model;

import java.util.Date;

public class PopisniDokument {
	Integer id;
	String naziv;
	Date datum;
	Integer magacinID;
	Integer poslovnaGodID;
	
	public PopisniDokument() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public Date getDatum() {
		return datum;
	}

	public void setDatum(Date datum) {
		this.datum = datum;
	}

	public Integer getMagacinID() {
		return magacinID;
	}

	public void setMagacinID(Integer magacinID) {
		this.magacinID = magacinID;
	}

	public Integer getPoslovnaGodID() {
		return poslovnaGodID;
	}

	public void setPoslovnaGodID(Integer poslovnaGodID) {
		this.poslovnaGodID = poslovnaGodID;
	}
	
	

}
