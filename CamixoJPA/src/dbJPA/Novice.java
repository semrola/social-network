package dbJPA;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the novice database table.
 * 
 */
@Entity
@Table(name="novice")
public class Novice implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_novica")
	private int idNovica;

	@Column(name="datum_objave")
	private Timestamp datumObjave;

	private String naziv;

    @Lob()
	private String vsebina;

	//bi-directional many-to-one association to Uporabnik
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_avtor")
	private Uporabnik uporabnik;

    public Novice() {
    }

	public int getIdNovica() {
		return this.idNovica;
	}

	public void setIdNovica(int idNovica) {
		this.idNovica = idNovica;
	}

	public Timestamp getDatumObjave() {
		return this.datumObjave;
	}

	public void setDatumObjave(Timestamp datumObjave) {
		this.datumObjave = datumObjave;
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

	public Uporabnik getUporabnik() {
		return this.uporabnik;
	}

	public void setUporabnik(Uporabnik uporabnik) {
		this.uporabnik = uporabnik;
	}
	
}