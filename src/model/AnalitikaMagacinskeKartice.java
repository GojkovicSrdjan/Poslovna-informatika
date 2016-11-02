package model;

import java.util.Date;

public class AnalitikaMagacinskeKartice {

	Integer id;
	Integer rbr;
	Date datumPromene;
	Date vrDok;
	Integer sifraDok;
	Integer kolicina;
	Double cena;
	Double vrednost;
	String tipPromene;
	Integer magacinskaKarticaID;
	Integer stavkaPrometnogDokID;
	String smer;
	
	public AnalitikaMagacinskeKartice() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getRbr() {
		return rbr;
	}
	public void setRbr(Integer rbr) {
		this.rbr = rbr;
	}
	public Date getDatumPromene() {
		return datumPromene;
	}
	public void setDatumPromene(Date datumPromene) {
		this.datumPromene = datumPromene;
	}
	public Date getVrDok() {
		return vrDok;
	}
	public void setVrDok(Date vrDok) {
		this.vrDok = vrDok;
	}
	public Integer getSifraDok() {
		return sifraDok;
	}
	public void setSifraDok(Integer sifraDok) {
		this.sifraDok = sifraDok;
	}
	public Integer getKolicina() {
		return kolicina;
	}
	public void setKolicina(Integer kolicina) {
		this.kolicina = kolicina;
	}
	public Double getCena() {
		return cena;
	}
	public void setCena(Double cena) {
		this.cena = cena;
	}
	public Double getVrednost() {
		return vrednost;
	}
	public void setVrednost(Double vrednost) {
		this.vrednost = vrednost;
	}
	public String getTipPromene() {
		return tipPromene;
	}
	public void setTipPromene(String tipPromene) {
		this.tipPromene = tipPromene;
	}
	public Integer getMagacinskaKarticaID() {
		return magacinskaKarticaID;
	}
	public void setMagacinskaKarticaID(Integer magacinskaKarticaID) {
		this.magacinskaKarticaID = magacinskaKarticaID;
	}
	public Integer getStavkaPrometnogDokID() {
		return stavkaPrometnogDokID;
	}
	public void setStavkaPrometnogDokID(Integer stavkaPrometnogDokID) {
		this.stavkaPrometnogDokID = stavkaPrometnogDokID;
	}
	public String getSmer() {
		return smer;
	}
	public void setSmer(String smer) {
		this.smer = smer;
	}
}
