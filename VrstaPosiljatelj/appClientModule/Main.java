import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class Main {
	public static void main(String[] args) {
		
		Sporocilo msg=new Sporocilo();
		msg.setId(1);
		msg.setVsebina("vsebina");
		
		Connection con=null;
		ConnectionFactory conFac=null;
		Queue  vrsta=null;
		Session session=null;
		ObjectMessage obSms=null;
		MessageProducer msgProducer=null;
		
		
		try {
			InitialContext context=new InitialContext();
			conFac = (ConnectionFactory)context.lookup("jms/CamixoCF");
			vrsta = (Queue)context.lookup("jms/NotificationsQueue");
		} catch (NamingException e) {
			System.out.println("JNDI lookup failed "+e);
		}
		
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

	/* (non-Java-doc)
	 * @see java.lang.Object#Object()
	 */
	public Main() {
		super();
	}

}