import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.Topic;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class Main {
	public static void main(String[] args) {
		/*Connection con=null;
		ConnectionFactory conFac=null;
		Queue vrsta=null;
		Session session=null;
		ObjectMessage obSms=null;
		MessageConsumer msgConsumer=null;
		
		try {
			InitialContext context=new InitialContext();
			conFac = (ConnectionFactory)context.lookup("jms/CamixoCF");
			vrsta = (Queue)context.lookup("jms/NotificationsQueue");
		} catch (NamingException e) {
			System.out.println("JNDI lookup failed "+e);
		}
		
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
		}*/
		Connection con = null;
		try{
			System.out.println("Start - prejemanje - vrsta - sprejemnik1");
			Context ctx = new InitialContext();

			ConnectionFactory cf = (ConnectionFactory) ctx.lookup("jms/CamixoCF");
			con = cf.createConnection();
			Session session = con.createSession( false, Session.AUTO_ACKNOWLEDGE );
			Queue queue= (Queue) ctx.lookup("jms/NotificationsQueue");
			MessageConsumer mc = session.createConsumer(queue);
			mc.setMessageListener(new VrstaSprejemnik());
			con.start();
		}
		catch (Exception e) {
			System.out.println("Context problem!" + e);
		}
		
		try {
			// Zaspimo, da lahko kaj dobimo
			Thread.sleep(150000); // 150s
		} catch (InterruptedException e) {
			System.out.println("Napaka - thread: ");
			e.printStackTrace();
		} 
	}

	/* (non-Java-doc)
	 * @see java.lang.Object#Object()
	 */
	public Main() {
		super();
	}

}