package dbJPA;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import dbJPA.Uporabnik;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;


/**
 * The persistent class for the obvestilo database table.
 * 
 */
@Entity
@Table(name="obvestilo")
public class Obvestilo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_obvestilo")
	private int idObvestilo;

	private Timestamp datum;

	private String naziv;

    @Lob()
	private String vsebina;

	@OneToOne
	@JoinColumn(name="id_avtor")
	private Uporabnik idAvtor;

    public Obvestilo() {
    }

	public int getIdObvestilo() {
		return this.idObvestilo;
	}

	public void setIdObvestilo(int idObvestilo) {
		this.idObvestilo = idObvestilo;
	}

	public Timestamp getDatum() {
		return this.datum;
	}

	public void setDatum(Timestamp datum) {
		this.datum = datum;
	}

	public String getNaziv() {
		return this.naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public String getVsebina() {
		return this.vsebina;
	}

	public void setVsebina(String vsebina) {
		this.vsebina = vsebina;
	}

	public Uporabnik getIdAvtor() {
		return this.idAvtor;
	}

	public void setIdAvtor(Uporabnik idAvtor) {
		this.idAvtor = idAvtor;
	}

}