package db;

import java.sql.Timestamp;

public class Novica extends Entiteta {
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private int idNovica;
	private String naziv;
	private int idAvtor;
	private String vsebina;
	private Timestamp datumObjave;

	public int getIdNovica() {
		return idNovica;
	}
	public void setIdNovica(int idNovica) {
		this.idNovica = idNovica;
	}
	public String getNaziv() {
		return naziv;
	}
	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}
	public int getIdAvtor() {
		return idAvtor;
	}
	
	public void setIdAvtor(int idAvtor) {
		this.idAvtor = idAvtor;
	}
	public String getVsebina() {
		return vsebina;
	}
	public void setVsebina(String vsebina) {
		this.vsebina = vsebina;
	}
	public Timestamp getDatumObjave() {
		return datumObjave;
	}
	public void setDatumObjave(Timestamp datumObjave) {
		this.datumObjave = datumObjave;
	}
}
