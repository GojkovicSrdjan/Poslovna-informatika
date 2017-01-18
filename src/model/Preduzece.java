package model;

public class Preduzece {
	
	Integer PIB;
	String naziv;
	Integer brTelefona;
	String adresa;
	String mesto;
	
	public Preduzece() {
		super();
	}
	
	public Integer getPIB() {
		return PIB;
	}
	public void setPIB(Integer pIB) {
		PIB = pIB;
	}
	public String getNaziv() {
		return naziv;
	}
	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}
	public Integer getBrTelefona() {
		return brTelefona;
	}
	public void setBrTelefona(Integer brTelefona) {
		this.brTelefona = brTelefona;
	}
	public String getAdresa() {
		return adresa;
	}
	public void setAdresa(String adresa) {
		this.adresa = adresa;
	}

	public String getMesto() {
		return mesto;
	}

	public void setMesto(String mesto) {
		this.mesto = mesto;
	}
	
	

}
