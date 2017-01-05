package manager;

import java.sql.SQLException;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import javax.transaction.UserTransaction;

import dbJPA.Prijateljstvo;
import dbJPA.Uporabnik;


public class Manager {
	@PersistenceContext( unitName="CamixoJPA" )
	private EntityManager em;
	
	@Resource
	private UserTransaction ut;
	
	
	
	
}
