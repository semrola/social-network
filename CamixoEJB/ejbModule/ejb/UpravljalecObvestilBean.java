package ejb;

import dbJPA.Obvestilo;
import ejb.view.UpravljalecObvestilBeanLocal;

import javax.annotation.Resource;
import javax.ejb.Local;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;

/**
 * Session Bean implementation class UpravljalecObvestilBean
 */
@Stateless
@Local(UpravljalecObvestilBeanLocal.class)
@LocalBean
public class UpravljalecObvestilBean implements UpravljalecObvestilBeanLocal {
	
	@Resource
	private ConnectionFactory cf;
	
	@Resource
	private Queue vrsta;
    /**
     * Default constructor. 
     */
    public UpravljalecObvestilBean() {
        // TODO Auto-generated constructor stub
    }

	@Override
	public void vstaviObvestiloVVrsto(Obvestilo o) {
		// TODO Auto-generated method stub
		Connection con=null;
		try
		{
			con=cf.createConnection();
			Session seja=con.createSession(false, Session.AUTO_ACKNOWLEDGE);
			ObjectMessage om=seja.createObjectMessage();
			MessageProducer msgProducer=seja.createProducer(vrsta);
			om.setObject(o);
			msgProducer.send(om);
			System.out.println("Obvestilo vstavljeno v vrsto");
			//Thread.sleep(100000);
		}
		catch (Exception e) {
			System.out.println("Napaka pri vstaviObvestiloVVrsto");
		}
		finally
		{
			if(con!=null)
				try{
					con.close();
				}
			catch (Exception e2) {
				System.out.println("Napaka pri zapiranju povezave");
			}
		}
	}

}
