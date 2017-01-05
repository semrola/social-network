package dbJPA;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;
import javax.persistence.OneToMany;
import javax.persistence.FetchType;
import dbJPA.Prijateljstvo;
import dbJPA.Obvestilo;
import javax.persistence.OneToOne;


/**
 * The persistent class for the uporabnik database table.
 * 
 */
@Entity
@Table(name="uporabnik")
@NamedQueries({
	@NamedQuery(name="vrniUporabnika", query="SELECT u FROM Uporabnik u WHERE u.username=:user AND u.geslo=:pass"),
	@NamedQuery(name="isciUporabnika", query="SELECT DISTINCT u FROM Uporabnik u WHERE u.username LIKE :search OR u.ime LIKE :search OR u.priimek LIKE :search"),
	@NamedQuery(name="getUporabnikByUsername", query="SELECT DISTINCT u FROM Uporabnik u WHERE u.username=:user"),
	@NamedQuery(name="vrniVseUporabnike", query="SELECT DISTINCT u FROM Uporabnik u")
})

public class Uporabnik implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_uporabnik")
	private int idUporabnik;

	@Column(name="datum_diplome")
	private int datumDiplome;

	@Column(name="datum_rojstva")
	private int datumRojstva;

	private String email;

	private String geslo;

	private String ime;

	private String naslov;

	private String priimek;

	private String salt;

	private String spol;

	@Column(name="stopnja_studija")
	private String stopnjaStudija;

	@Column(name="studijska_smer")
	private String studijskaSmer;

	private int tipUporabnika;

	private String username;

	@Column(name="vrsta_studija")
	private String vrstaStudija;

	//bi-directional many-to-one association to Novice
	@OneToMany(mappedBy="uporabnik")
	private List<Novice> novices;

	@OneToMany(mappedBy="idUporabnikPobudnik",fetch=FetchType.EAGER)
	private List<Prijateljstvo> prijateljstvoListPobudnik;

	@OneToMany(mappedBy="idUporabnikPrejemnik",fetch=FetchType.EAGER)
	private List<Prijateljstvo> prijateljstvoListPrejemnik;

	@OneToOne(mappedBy="idAvtor")
	private Obvestilo obvestilo;

    public Uporabnik() {
    }

	public int getIdUporabnik() {
		return this.idUporabnik;
	}

	public void setIdUporabnik(int idUporabnik) {
		this.idUporabnik = idUporabnik;
	}

	public int getDatumDiplome() {
		return this.datumDiplome;
	}

	public void setDatumDiplome(int datumDiplome) {
		this.datumDiplome = datumDiplome;
	}

	public int getDatumRojstva() {
		return this.datumRojstva;
	}

	public void setDatumRojstva(int datumRojstva) {
		this.datumRojstva = datumRojstva;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGeslo() {
		return this.geslo;
	}

	public void setGeslo(String geslo) {
		this.geslo = geslo;
	}

	public String getIme() {
		return this.ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public String getNaslov() {
		return this.naslov;
	}

	public void setNaslov(String naslov) {
		this.naslov = naslov;
	}

	public String getPriimek() {
		return this.priimek;
	}

	public void setPriimek(String priimek) {
		this.priimek = priimek;
	}

	public String getSalt() {
		return this.salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getSpol() {
		return this.spol;
	}

	public void setSpol(String spol) {
		this.spol = spol;
	}

	public String getStopnjaStudija() {
		return this.stopnjaStudija;
	}

	public void setStopnjaStudija(String stopnjaStudija) {
		this.stopnjaStudija = stopnjaStudija;
	}

	public String getStudijskaSmer() {
		return this.studijskaSmer;
	}

	public void setStudijskaSmer(String studijskaSmer) {
		this.studijskaSmer = studijskaSmer;
	}

	public int getTipUporabnika() {
		return this.tipUporabnika;
	}

	public void setTipUporabnika(int tipUporabnika) {
		this.tipUporabnika = tipUporabnika;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getVrstaStudija() {
		return this.vrstaStudija;
	}

	public void setVrstaStudija(String vrstaStudija) {
		this.vrstaStudija = vrstaStudija;
	}

	public List<Novice> getNovices() {
		return this.novices;
	}

	public void setNovices(List<Novice> novices) {
		this.novices = novices;
	}

	public List<Prijateljstvo> getPrijateljstvoListPobudnik() {
		return this.prijateljstvoListPobudnik;
	}

	public void setPrijateljstvoListPobudnik(List<Prijateljstvo> prijateljstvoListPobudnik) {
		this.prijateljstvoListPobudnik = prijateljstvoListPobudnik;
	}

	public List<Prijateljstvo> getPrijateljstvoListPrejemnik() {
		return this.prijateljstvoListPrejemnik;
	}

	public void setPrijateljstvoListPrejemnik(List<Prijateljstvo> prijateljstvoListPrejemnik) {
		this.prijateljstvoListPrejemnik = prijateljstvoListPrejemnik;
	}

	public Obvestilo getObvestilo() {
		return this.obvestilo;
	}

	public void setObvestilo(Obvestilo obvestilo) {
		this.obvestilo = obvestilo;
	}
	
}