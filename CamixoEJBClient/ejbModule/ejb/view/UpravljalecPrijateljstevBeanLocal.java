package ejb.view;

import java.util.List;

import javax.ejb.Local;
import dbJPA.Prijateljstvo;
import dbJPA.Uporabnik;

@Local
public interface UpravljalecPrijateljstevBeanLocal {
	public void posljiProsnjoZaPrijateljstvo(int idUpPobudnik, int idUpPrejemnik);

    public List<Prijateljstvo> vrniOdprteProsnjeZaPrijateljstvo(int idUporabnika);

    public List<Uporabnik> vrniVsePrijatelje(int idUporabnika);

    public void spremeniStatusPrijateljstvu(int idPrijateljstva, int noviStatus);

}
