package model;

public class PopisnaKomisija {
	Integer id;
	String naziv;
	String opis;
	Integer popisniDokID;
	
	public PopisnaKomisija() {
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

	public String getOpis() {
		return opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	public Integer getPopisniDokID() {
		return popisniDokID;
	}

	public void setPopisniDokID(Integer popisniDokID) {
		this.popisniDokID = popisniDokID;
	}
	

}
