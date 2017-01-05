

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class VrstaPosiljatelj {
	
	private Connection con=null;
	private ConnectionFactory conFac=null;
	private Queue  vrsta=null;
	private Session session=null;
	private ObjectMessage obSms=null;
	private MessageProducer msgProducer=null;
	
	private QueueConnectionFactory queueConFac=null;
	private QueueSession queueSession = null;
	private QueueSender queueSender=null;

	
	public VrstaPosiljatelj()
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
	
	
	
	public void sendIntoQueueWithMessageProducer(Sporocilo msg)
	{
		getJmsConFactory();
		try{
			System.out.println("Start - sendIntoQueueWithMessageProducer");
			con = conFac.createConnection();
			session = (Session) con.createSession(false, Session.AUTO_ACKNOWLEDGE);
			
			obSms = session.createObjectMessage();
			msgProducer=(MessageProducer) session.createProducer(vrsta);

			obSms.setObject(msg);
			msgProducer.send(obSms);
			System.out.print("Pošiljanje: id="+msg.id+" vsebina="+msg.vsebina);
		}
		catch (Exception e) {
			System.out.println("Exception v sendIntoQueueWithMessageProducer()" + e);
		}
		finally
		{
			if(con!=null)
			try {
				con.close();
			} catch (JMSException e) {
				System.out.println("Ne morem zapreti povezave v sendIntoQueueWithMessageProducer()" + e);
			}
		}
	}
	
	public void sendIntoQueueWithQueueSender(Sporocilo msg)
	{
		getJmsQueueConFactory();
		try{
			System.out.println("Start - sendIntoQueueWithQueueSender()");
			con = queueConFac.createConnection();
			queueSession = (QueueSession) con.createSession(false, Session.AUTO_ACKNOWLEDGE);
			
			obSms = queueSession.createObjectMessage();
			queueSender= (QueueSender) queueSession.createProducer(vrsta);

			obSms.setObject(msg);
			queueSender.send(obSms);
			System.out.print("Pošiljanje: id="+msg.id+" vsebina="+msg.vsebina);
		}
		catch (Exception e) {
			System.out.println("Exception v sendIntoQueueWithQueueSender()" + e);
		}
		finally
		{
			if(con!=null)
			try {
				con.close();
			} catch (JMSException e) {
				System.out.println("Ne morem zapreti povezave v sendIntoQueueWithQueueSender()" + e);
			}
		}
	}
}
