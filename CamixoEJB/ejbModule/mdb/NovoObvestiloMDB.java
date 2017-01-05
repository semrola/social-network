package mdb;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import dbJPA.Obvestilo;
import ejb.PosiljanjeObvestilBean;

/**
 * Message-Driven Bean implementation class for: NovoObvestiloMDB
 *
 */
@MessageDriven(activationConfig = { @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")}, 
		mappedName = "jms/NotificationsQueue")
@TransactionManagement(TransactionManagementType.BEAN)
public class NovoObvestiloMDB implements MessageListener {

    /**
     * Default constructor. 
     */
    public NovoObvestiloMDB() {
        // TODO Auto-generated constructor stub
    }
	
	/**
     * @see MessageListener#onMessage(Message)
     */
    @EJB
    private PosiljanjeObvestilBean pob;
    public void onMessage(Message message) {
    	ObjectMessage om=(ObjectMessage) message;
    	try {
			Obvestilo o=(Obvestilo)om.getObject();
			pob.razposljiObvestilo(o);
		} catch (JMSException e) {
			System.out.println("Napaka aasdasd");
		}
    	
    	
    }

}
