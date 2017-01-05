package ejb;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.TransactionManagement;
import javax.ejb.EJBContext;
import javax.ejb.Local;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.UserTransaction;


import dbJPA.Prijateljstvo;
import dbJPA.Uporabnik;
import ejb.view.UpravljalecPrijateljstevBeanLocal;

/**
 * Session Bean implementation class UpravljalecPrijateljstevBean
 * @param <UporabnikManager>
 */
@Stateless
@Local(UpravljalecPrijateljstevBeanLocal.class)
@LocalBean
@TransactionManagement(TransactionManagementType.BEAN)
public class UpravljalecPrijateljstevBean implements UpravljalecPrijateljstevBeanLocal {
	@PersistenceContext( unitName="CamixoJPA" )
	private EntityManager em;
    
	@Resource
	  private EJBContext kon;
	/**
     * Default constructor. 
     */
    public UpravljalecPrijateljstevBean() {
        // TODO Auto-generated constructor stub
    }

	@Override
	public void posljiProsnjoZaPrijateljstvo(int idUpPobudnik, int idUpPrejemnik) {
		
		Prijateljstvo p=getPrijateljstva(idUpPobudnik, idUpPrejemnik);
		if(p==null){
			//vstavi prijateljsvo
			try {
				vstaviPrijateljstvo(idUpPrejemnik, idUpPobudnik);
			} catch (SQLException e) {
				System.out.print("Napaka pri vstavljanju prijateljstva");
			}
		}
	}

	@Override
	public List<Prijateljstvo> vrniOdprteProsnjeZaPrijateljstvo(int idUporabnika) {
		  
		Uporabnik u=null;
		UserTransaction ut=kon.getUserTransaction();
		try
		{
			ut.begin();
			u=em.find(Uporabnik.class, idUporabnika);
			ut.commit();
		}
		catch(Exception e1)
		{
			System.out.print("Exception - vrniOdprteProsnjeZaPrijateljstvo - iskanje uporabnika");
		}
		List<Prijateljstvo> p=u.getPrijateljstvoListPrejemnik();
		List<Prijateljstvo> p1=new ArrayList<Prijateljstvo>();
		for(Prijateljstvo pr:p)
		{
			if(pr.getStatus()==0)
				p1.add(pr);
		}
		
		  return p1;
	}

	@Override
	public List<Uporabnik> vrniVsePrijatelje(int idUporabnika) {
		  /*Query q;
		  List<Prijateljstvo> u=null;
		  try 
		  {
			  q=em.createNamedQuery("vrniVsePrijatelje");
			  q.setParameter("idUporabnik", em.find(Uporabnik.class, idUporabnika));
			  u=(List<Prijateljstvo>)q.getResultList();

		  }
		  catch (Exception e) {
		     //e.printStackTrace();
			  System.out.println("Napaka pri poizvedbi o vseh prijateljih "+e);
		  }

		  return u;*/
		List<Uporabnik> lu=new ArrayList<Uporabnik>();
		Uporabnik u=em.find(Uporabnik.class, idUporabnika);
		List<Prijateljstvo> l1=u.getPrijateljstvoListPobudnik();
		List<Prijateljstvo> l2=u.getPrijateljstvoListPrejemnik();
		for(Prijateljstvo p:l1)
			if(p.getStatus()==1)
				lu.add(p.getIdUporabnikPrejemnik());
		for(Prijateljstvo p:l2)
			if(p.getStatus()==1)
				lu.add(p.getIdUporabnikPobudnik());
		return lu;

	}
	
	//preverjanje ce je nekdo ze prijatelj nekoga
	public Prijateljstvo getPrijateljstva(int idPobudnik, int idPrejemnik) {
		//te poizvedbe z list prijateljstvo ne delajo
		  Query q;
		  List<Prijateljstvo> p=null;
		  
		  try 
		  {
			  Uporabnik uPo=em.find(Uporabnik.class, idPobudnik);
			  Uporabnik uPr=em.find(Uporabnik.class, idPrejemnik);
			  q=em.createNamedQuery("preveriPrijateljstvo");
			  q.setParameter("prejemnik", uPr);
			  q.setParameter("pobudnik", uPo);
			  p=(List<Prijateljstvo>)q.getResultList();
		  }
		  catch (Exception e) {
		     //e.printStackTrace();
			  System.out.println("Uporabnik ni bil najden! - getPrijateljstva()");
		  }
		  
		  if(p==null || p.isEmpty())
			  return null;
		  else return new Prijateljstvo();
	}
	
	public void vstaviPrijateljstvo(int idPr, int idPo) throws SQLException
	{
		Prijateljstvo p=null;
		try{
			Uporabnik uPo=em.find(Uporabnik.class, idPo);
			Uporabnik uPr=em.find(Uporabnik.class, idPr);
			p= new Prijateljstvo();
			p.setIdUporabnikPobudnik(uPo);
			p.setIdUporabnikPrejemnik(uPr);
		}
		catch(Exception e1)
		{
			System.out.print("Exception - vstavi Prijateljstvo");
		}
		
		UserTransaction ut=kon.getUserTransaction();
		
		try{
			ut.begin();
			em.persist(p);
			ut.commit();
		}
		catch(Exception e)
		{
			try{
				ut.rollback();
			}
			catch(Exception e1)
			{
				System.out.print("ROLLBACK - vstavi prijateljstvo");
			}
		}
	}
	
	@Override
	public void spremeniStatusPrijateljstvu(int idPrijateljstva, int noviStatus) {
		Prijateljstvo p=findPrijateljstvoById(idPrijateljstva);
		p.setStatus(noviStatus);
		UserTransaction ut=kon.getUserTransaction();
		try
		{
			ut.begin();
			em.merge(p);
			ut.commit();
		}
		catch(Exception e)
		{
			try{ut.rollback();}
			catch(Exception e1)
			{
				System.out.print("ROLLBACK - spremeni status");
			}
		}
	}
	
	public Prijateljstvo findPrijateljstvoById(int id)
	{
		UserTransaction ut=kon.getUserTransaction();
		Prijateljstvo p=null;
		try
		{
			ut.begin();
			p=em.find(Prijateljstvo.class, id);
			ut.commit();
		}
		catch(Exception e)
		{
			try{ut.rollback();}
			catch(Exception e1)
			{
				System.out.print("ROLLBACK - findPrijateljstvoById");
			}
		}
		
		return p;
	}
}
