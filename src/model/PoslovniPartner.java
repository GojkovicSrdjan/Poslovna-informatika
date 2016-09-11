package model;

public class PoslovniPartner {
	Integer id;
	Integer PIB;
	String adresa;
	String naziv;
	String delatnost;
	public PoslovniPartner() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getPIB() {
		return PIB;
	}
	public void setPIB(Integer pIB) {
		PIB = pIB;
	}
	public String getAdresa() {
		return adresa;
	}
	public void setAdresa(String adresa) {
		this.adresa = adresa;
	}
	public String getNaziv() {
		return naziv;
	}
	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}
	public String getDelatnost() {
		return delatnost;
	}
	public void setDelatnost(String delatnost) {
		this.delatnost = delatnost;
	}
	
	

}
