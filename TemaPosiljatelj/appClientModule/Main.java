import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.naming.Context;
import javax.naming.InitialContext;



public class Main {
	public static void main(String[] args) {
		Connection con = null;
		try{
			System.out.println("Start - posiljanje - topic");
			Context ctx = new InitialContext();

			ConnectionFactory cf = (ConnectionFactory) ctx.lookup("jms/CamixoCF");
			con = cf.createConnection();
			Session session = con.createSession( false, Session.AUTO_ACKNOWLEDGE );
			Topic topic = (Topic) ctx.lookup("jms/NotificationsTopic");
			MessageProducer mp = session.createProducer(topic);
			
			ObjectMessage obmsg=session.createObjectMessage();
			
			Sporocilo sms=null;
			for(int i=0;i<10;i++)
			{
				sms=new Sporocilo();
				sms.setId(i+1);
				sms.setVsebina("To je "+(i+1)+". sporocilo");
				obmsg.setObject(sms);
				mp.send(obmsg);
			}
			
			System.out.print("Konec vstavljanja - topic!");
		}
		catch (Exception e) {
			System.out.println("Context problem!" + e);
		}
		finally
		{
			try {
				con.close();
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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