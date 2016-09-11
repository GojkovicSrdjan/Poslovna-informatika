package model;

public class Artikal {
	
	Integer id;
	String pakovanje;
	String jedinicaMere;
	String naziv;
	String opis;
	Integer grupaArtikalaID;
	Integer stavkePopisaID;
	
	public Artikal() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPakovanje() {
		return pakovanje;
	}

	public void setPakovanje(String pakovanje) {
		this.pakovanje = pakovanje;
	}

	public String getJedinicaMere() {
		return jedinicaMere;
	}

	public void setJedinicaMere(String jedinicaMere) {
		this.jedinicaMere = jedinicaMere;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public String getOpis() {
		return opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	public Integer getGrupaArtikalaID() {
		return grupaArtikalaID;
	}

	public void setGrupaArtikalaID(Integer grupaArtikalaID) {
		this.grupaArtikalaID = grupaArtikalaID;
	}

	public Integer getStavkePopisaID() {
		return stavkePopisaID;
	}

	public void setStavkePopisaID(Integer stavkePopisaID) {
		this.stavkePopisaID = stavkePopisaID;
	}

}
