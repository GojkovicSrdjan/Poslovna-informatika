package model;

public class PoslovnaGodina {

	Integer id;
	Integer godina;
	Integer PIB;
	boolean zakljucena;
	
	public PoslovnaGodina() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getGodina() {
		return godina;
	}

	public void setGodina(Integer godina) {
		this.godina = godina;
	}

	public Integer getPIB() {
		return PIB;
	}

	public void setPIB(Integer pIB) {
		PIB = pIB;
	}

	public boolean isZakljucena() {
		return zakljucena;
	}

	public void setZakljucena(boolean zakljucena) {
		this.zakljucena = zakljucena;
	}
	
	
	
}
