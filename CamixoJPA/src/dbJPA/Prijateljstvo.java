package dbJPA;

import java.io.Serializable;
import javax.persistence.*;
import dbJPA.Uporabnik;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


/**
 * The persistent class for the prijateljstvo database table.
 * 
 */

@Entity
@Table(name="prijateljstvo")
@NamedQueries({
	@NamedQuery(name="preveriPrijateljstvo", 
				query="SELECT Prijateljstvo FROM Prijateljstvo p WHERE (p.idUporabnikPobudnik=:pobudnik AND p.idUporabnikPrejemnik=:prejemnik)"
			+ "OR (p.idUporabnikPobudnik=:prejemnik AND p.idUporabnikPrejemnik=:pobudnik)"),

	@NamedQuery(name="vrniVsePrijatelje", 
		query="SELECT Prijateljstvo FROM Prijateljstvo p WHERE ((p.idUporabnikPrejemnik=:idUporabnik or p.idUporabnikPobudnik=:idUporabnik) AND p.status=1)")

})


public class Prijateljstvo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_prijateljstvo")
	private int idPrijateljstvo;

	private int status;

	@ManyToOne
	@JoinColumn(name="id_uporabnik_pobudnik")
	private Uporabnik idUporabnikPobudnik;

	@ManyToOne
	@JoinColumn(name="id_uporabnik_prejemnik")
	private Uporabnik idUporabnikPrejemnik;

    public Prijateljstvo() {
    }

	public int getIdPrijateljstvo() {
		return this.idPrijateljstvo;
	}

	public void setIdPrijateljstvo(int idPrijateljstvo) {
		this.idPrijateljstvo = idPrijateljstvo;
	}

	public int getStatus() {
		return this.status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Uporabnik getIdUporabnikPobudnik() {
		return this.idUporabnikPobudnik;
	}

	public void setIdUporabnikPobudnik(Uporabnik idUporabnikPobudnik) {
		this.idUporabnikPobudnik = idUporabnikPobudnik;
	}

	public Uporabnik getIdUporabnikPrejemnik() {
		return this.idUporabnikPrejemnik;
	}

	public void setIdUporabnikPrejemnik(Uporabnik idUporabnikPrejemnik) {
		this.idUporabnikPrejemnik = idUporabnikPrejemnik;
	}

}