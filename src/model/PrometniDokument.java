package model;

import java.util.Date;

public class PrometniDokument {
	Integer id;
	Integer brojMagacina;
	Date datumNastanka;
	Date datumKnjizenja;
	String statusDokumenta;
	Integer poslovnaGodID;
	Integer poslovniPartnerID;
	String vrstaDokumenta;
	public PrometniDokument() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getBrojMagacina() {
		return brojMagacina;
	}
	public void setBrojMagacina(Integer brojMagacina) {
		this.brojMagacina = brojMagacina;
	}
	public Date getDatumNastanka() {
		return datumNastanka;
	}
	public void setDatumNastanka(Date datumNastanka) {
		this.datumNastanka = datumNastanka;
	}
	public Date getDatumKnjizenja() {
		return datumKnjizenja;
	}
	public void setDatumKnjizenja(Date datumKnjizenja) {
		this.datumKnjizenja = datumKnjizenja;
	}
	public String getStatusDokumenta() {
		return statusDokumenta;
	}
	public void setStatusDokumenta(String statusDokumenta) {
		this.statusDokumenta = statusDokumenta;
	}
	public Integer getPoslovnaGodID() {
		return poslovnaGodID;
	}
	public void setPoslovnaGodID(Integer poslovnaGodID) {
		this.poslovnaGodID = poslovnaGodID;
	}
	public Integer getPoslovniPartnerID() {
		return poslovniPartnerID;
	}
	public void setPoslovniPartnerID(Integer poslovniPartnerID) {
		this.poslovniPartnerID = poslovniPartnerID;
	}
	public String getVrstaDokumenta() {
		return vrstaDokumenta;
	}
	public void setVrstaDokumenta(String vrstaDokumenta) {
		this.vrstaDokumenta = vrstaDokumenta;
	}
	

}
