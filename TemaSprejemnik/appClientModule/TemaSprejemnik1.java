

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.ibm.ejs.jms.DurableSubscription;

public class TemaSprejemnik1 implements MessageListener {
	public TemaSprejemnik1()
	{
		
	}
	
	public void subscribe()
	{
		Connection con = null;
		try{
			System.out.println("Start - prejemanje - topic - sprejemnik1");
			Context ctx = new InitialContext();

			ConnectionFactory cf = (ConnectionFactory) ctx.lookup("jms/CamixoCF");
			con = cf.createConnection();
			Session session = con.createSession( false, Session.AUTO_ACKNOWLEDGE );
			Topic topic = (Topic) ctx.lookup("jms/NotificationsTopic");
			MessageConsumer mc = session.createConsumer(topic);
			mc.setMessageListener(new TemaSprejemnik1());
			con.start();
			
		}
		catch (Exception e) {
			System.out.println("Context problem!" + e);
		}
		
	}

	@Override
	public void onMessage(Message message) {
		ObjectMessage obm=(ObjectMessage) message;
		try{
			Sporocilo sms=(Sporocilo)obm.getObject();
			System.out.println("TemaSprejemnik1 - sprejeto: id="+sms.getId()+" vsebina="+sms.getVsebina());
		}
		catch (Exception e) {
			System.out.println("Napaka pri sprejemu sporocila - TS1");
		}
		
	}
}
