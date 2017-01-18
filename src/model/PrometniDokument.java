package model;

import java.util.Date;

public class PrometniDokument {
	Integer id;
	Integer magacinID;
	Date datumNastanka;
	Date datumKnjizenja;
	Integer statusDokumenta;
	Integer poslovnaGodID;
	Integer poslovniPartnerID;
	Integer vrstaDokumenta;
	
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
	public Integer getMagacinID() {
		return magacinID;
	}
	public void setMagacinID(Integer magacinID) {
		this.magacinID = magacinID;
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
	public Integer getStatusDokumenta() {
		return statusDokumenta;
	}
	public void setStatusDokumenta(Integer statusDokumenta) {
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
	public Integer getVrstaDokumenta() {
		return vrstaDokumenta;
	}
	public void setVrstaDokumenta(Integer vrstaDokumenta) {
		this.vrstaDokumenta = vrstaDokumenta;
	}
	

}
