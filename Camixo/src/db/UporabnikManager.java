package db;

import java.util.List;

import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

import dbJPA.Uporabnik;
import dbJPA.Prijateljstvo;

@Named
@RequestScoped 
public class UporabnikManager {

	@PersistenceUnit( unitName="CamixoJPA" )
	private EntityManagerFactory emf;
   
	@Resource
	private UserTransaction ut;
	

	public Uporabnik avtenticirajUporabnika(String upIme, String geslo) {

      EntityManager em = emf.createEntityManager();
      Query q;
      List<Uporabnik> list=null;
      try 
      {
    	  q=em.createNamedQuery("vrniUporabnika");
    	  q.setParameter("user", upIme);
    	  q.setParameter("pass", geslo);
    	  list=(List<Uporabnik>)(q.getResultList());
      }
      catch (Exception e) {
         e.printStackTrace();
      }
      finally {
    	  em.close();
      }
      
      if(list==null)
    	  return null;
      else if(list.isEmpty())
    	  return null;	
      else
    	  return list.get(0);
   }
   
	public Uporabnik findUporabnikById(int id) {
		EntityManager em = null;

		Uporabnik uporabnik = null;
		try {
			ut.begin();
			em = emf.createEntityManager();
			uporabnik = (Uporabnik) em.find(Uporabnik.class, id);
			ut.commit();
		} catch (Exception e) {
			try {
				ut.rollback();
			} catch (Exception e1) {
				System.out.println("Uporabnik ni najden - findUporabnikById ROLLBACK");
				e1.printStackTrace();
			}
			System.out.println("Uporabnik ni najden - findUporabnikById");
		} finally {
			em.close();
		}
		return uporabnik;
	}
   
   public List<Uporabnik> searchUporabnik(String search)
   {
	   EntityManager em=emf.createEntityManager();
	   Query q;
	   List<Uporabnik> list=null;
	   try
	   {
		   q=em.createNamedQuery("isciUporabnika");
		   q.setParameter("search", "%"+search+"%");
		   list=(List<Uporabnik>)q.getResultList();
	   }
	   catch(Exception e)
	   {
		   e.printStackTrace();
	   }
	   finally
	   {
		   em.close();
	   }
	   
	   if(list==null || list.isEmpty())
		   return null;
	   else
		   return list;
   }
   
   public void createUporabnik(Uporabnik uporabnik){
		EntityManager em=null;
		try 
		{
			ut.begin();
			em =emf.createEntityManager();
			em.persist(uporabnik);
			ut.commit();
		} 
		catch (Exception e) 
		{	
			e.printStackTrace();
			try {ut.rollback();} 
			catch (Exception e1) {e1.printStackTrace(); System.out.println("ROLLBACKED - createUser");}
		} 
		finally {
			em.close();
		}
	}
   
   public void updateUporabnika(Uporabnik up) { 

		EntityManager em =null;

		try {
			ut.begin();
			em =emf.createEntityManager();
			up = em.merge(up);
			ut.commit();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				ut.rollback();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		} finally {
			em.close();
		}
	}

   public Uporabnik getUporabnikByUsername (String upIme) {
      EntityManager em = emf.createEntityManager();
      Query q;
     // Uporabnik u=null;
      List<Uporabnik> l=null;
      try 
      {
    	  q=em.createNamedQuery("getUporabnikByUsername");
    	  q.setParameter("user", upIme);
    	  //u=(Uporabnik)q.getSingleResult();
    	  l=(List<Uporabnik>)q.getResultList();
      }
      catch (Exception e) {
         //e.printStackTrace();
    	  System.out.println("Uporabnik ni bil najden! - getUporabnikByUsername");
      }
      finally {
    	  em.close();
      }
      /*System.out.println("List:"+l.size());
      for(Uporabnik u:l)
      {
    	  System.out.println("Uporabnik "+u.getUsername()+" "+u.getIdUporabnik());
      }*/
      if(l==null || l.isEmpty())
    	  return null;
	  else return l.get(0);
   }
   
   public int checkStatus(Uporabnik u1, Uporabnik u2)
   {
	   int x=10;
	   EntityManager em = emf.createEntityManager();
	   Query q;
	   try
	   {
		   q=em.createQuery("SELECT p.status FROM Prijateljstvo p WHERE (p.idUporabnikPrejemnik=:up1 AND p.idUporabnikPobudnik=:up2) OR (p.idUporabnikPrejemnik=:up2 AND p.idUporabnikPobudnik=:up1)");
		   q.setParameter("up1", u1);
		   q.setParameter("up2", u2);
		   x=(Integer)q.getSingleResult();
	   }
	   catch (Exception e) {
		   System.out.print("checkStatus - Exception");
		   x=10;
	   }
	   finally
	   {
		   em.close();
	   }
	   
	   return x;
   }
   
   public void brisiPrijateljstvo(int id)
   {
	   EntityManager em=null;
	   try
	   {
		   ut.begin();
		   em=emf.createEntityManager();
		   Prijateljstvo p=em.find(Prijateljstvo.class, id);
		   em.remove(p);
		   ut.commit();
	   }
	   catch(Exception e1)
	   {
		   try {
			ut.rollback();
		} catch (Exception e) {}
		   System.out.println("Napaka pri brisanju prijateljstva - brisiPrijateljstvo");
	   }
	   finally{em.close();}
   }
}
