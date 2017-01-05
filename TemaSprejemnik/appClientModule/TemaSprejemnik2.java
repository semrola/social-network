

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.Topic;
import javax.naming.Context;
import javax.naming.InitialContext;

public class TemaSprejemnik2 implements MessageListener {
	public TemaSprejemnik2()
	{
		
	}
	
	public void subscribe()
	{
		Connection con = null;
		try{
			System.out.println("Start - prejemanje - topic - sprejemnik2");
			Context ctx = new InitialContext();

			ConnectionFactory cf = (ConnectionFactory) ctx.lookup("jms/CamixoCF");
			con = cf.createConnection();
			Session session = con.createSession( false, Session.AUTO_ACKNOWLEDGE );
			Topic topic = (Topic) ctx.lookup("jms/NotificationsTopic");
			MessageConsumer mc = session.createConsumer(topic);
			mc.setMessageListener(new TemaSprejemnik2());
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
			System.out.println("TemaSprejemnik2 - sprejeto: id="+sms.getId()+" vsebina="+sms.getVsebina());
		}
		catch (Exception e) {
			System.out.println("Napaka pri sprejemu sporocila - TS2");
		}
	}
}
