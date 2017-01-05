

import java.io.Serializable;

public class Sporocilo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int id;
	String vsebina;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getVsebina() {
		return vsebina;
	}
	public void setVsebina(String vsebina) {
		this.vsebina = vsebina;
	}
}
