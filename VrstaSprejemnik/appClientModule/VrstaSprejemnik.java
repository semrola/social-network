

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.naming.InitialContext;
import javax.naming.NamingException;


public class VrstaSprejemnik  implements MessageListener  {
	private Connection con=null;
	private ConnectionFactory conFac=null;
	private Queue vrsta=null;
	private Session session=null;
	private ObjectMessage obSms=null;
	private MessageConsumer msgConsumer=null;
	
	private QueueConnectionFactory queueConFac=null;
	private QueueSession queueSession = null;
	private QueueReceiver queueReceiver=null;
	
	public VrstaSprejemnik()
	{
	}
	
	private void getJmsConFactory()
	{
		try {
			InitialContext context=new InitialContext();
			conFac = (ConnectionFactory)context.lookup("jms/CamixoCF");
			vrsta = (Queue)context.lookup("jms/NotificationsQueue");
		} catch (NamingException e) {
			System.out.println("JNDI lookup failed "+e);
		}
	}
	
	private void getJmsQueueConFactory()
	{
		try {
			InitialContext context=new InitialContext();
			queueConFac = (QueueConnectionFactory)context.lookup("jms/QueueCF");
			vrsta = (Queue)context.lookup("jms/NotificationsQueue");
		} catch (NamingException e) {
			System.out.println("JNDI lookup failed "+e);
		}
	}
	
	public void getFromQueueWithMessageConsumer()
	{
		getJmsConFactory();
		try{
			System.out.println("Start - getFromQueueWithMessageConsumer()");
			con = conFac.createConnection();
			session = (Session) con.createSession(false, Session.AUTO_ACKNOWLEDGE);
			
			msgConsumer = session.createConsumer(vrsta);
			con.start();
			
			while(true) 
			{
				obSms = (ObjectMessage) msgConsumer.receive();
				Sporocilo msg = (Sporocilo)obSms.getObject();
				System.out.println("Prejeto sporocilo id="+msg.getId()+" vsebina="+msg.getVsebina());
			}
		}
		catch (Exception e) {
			System.out.println("Exception v getFromQueueWithMessageConsumer()" + e);
		}
		finally
		{
			if(con!=null)
			try {
				con.close();
			} catch (JMSException e) {
				System.out.println("Ne morem zapreti povezave v getFromQueueWithMessageConsumer()" + e);
			}
		}
	}
	
	public void getFromQueueWithQueueReceiver()
	{
		getJmsQueueConFactory();
		try{
			System.out.println("Start - getFromQueueWithQueueReceiver()");
			con = queueConFac.createConnection();
			queueSession = (QueueSession) con.createSession(false, Session.AUTO_ACKNOWLEDGE);
			
			while(true) 
			{
				obSms = (ObjectMessage) queueReceiver.receive();
				Sporocilo msg = (Sporocilo)obSms.getObject();
				System.out.println("Prejeto sporocilo id="+msg.getId()+" vsebina="+msg.getVsebina());
			}
		}
		catch (Exception e) {
			System.out.println("Exception v getFromQueueWithQueueReceiver()" + e);
		}
		finally
		{
			if(con!=null)
			try {
				con.close();
			} catch (JMSException e) {
				System.out.println("Ne morem zapreti povezave v getFromQueueWithQueueReceiver()" + e);
			}
		}
	}

	@Override
	public void onMessage(Message message) {
		ObjectMessage obm=(ObjectMessage) message;
		try{
			Sporocilo sms=(Sporocilo)obm.getObject();
			System.out.println("VrstaSprejemnik1 - sprejeto: id="+sms.getId()+" vsebina="+sms.getVsebina());
		}
		catch (Exception e) {
			System.out.println("Napaka pri sprejemu sporocila - VS1");
		}
	}
}
