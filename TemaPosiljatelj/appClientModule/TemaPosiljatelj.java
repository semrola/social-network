

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class TemaPosiljatelj {
	public TemaPosiljatelj()
	{
		
	}
	
	public void publish()
	{
		Connection topicCon = null;
		try{
			System.out.println("Start - posiljanje - topic");
			Context ctx = new InitialContext();

			ConnectionFactory qcf = (ConnectionFactory) ctx.lookup("jms/CamixoCF");
			topicCon = qcf.createConnection();
			Session topicSession = topicCon.createSession( false, Session.AUTO_ACKNOWLEDGE );
			Topic topic = (Topic) ctx.lookup("jms/NotificationsTopic");
			MessageProducer mp = topicSession.createProducer(topic);
			
			TextMessage sporocilo = topicSession.createTextMessage();
			sporocilo.setText("Vsebina");
			mp.send(sporocilo);
			//qs.send(sporocilo);
			/*
			ObjectMessage obSms = session.createObjectMessage();
			Sporocilo sms=null;
			for(int i=0;i<10;i++)
			{
				sms=new Sporocilo();
				sms.setId(i+1);
				sms.setVsebina("To je "+i+". sporoèilo");
				obSms.setObject(sms);
				qs.send(obSms);
			}*/
			System.out.print("Konec vstavljanja - topic!");
		}
		catch (Exception e) {
			System.out.println("Context problem!" + e);
		}
		finally
		{
			try {
				topicCon.close();
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void publishTopic()
	{
		TopicConnection topicCon = null;
		Context ctx=null;
		TopicConnectionFactory tcf=null;
		Topic topic = null;
		TopicSession topicSession=null;
		TopicPublisher publisher=null;
		TextMessage sporocilo=null;
		
		System.out.println("Start - pošiljanje - publishTopic()");
			
		try {
			ctx = new InitialContext();
		} catch (NamingException e1) {
			System.out.println("Could not create JNDI API context: " + e1.toString());
		}
		
		try {
			tcf = (TopicConnectionFactory) ctx.lookup("jms/TopicCF");
			topic = (Topic) ctx.lookup("jms/NotificationsTopic");
		} catch (NamingException e1) {
			 System.out.println("JNDI API lookup failed: "+e1.toString());
		}
		
		try
		{
			topicCon = tcf.createTopicConnection();
			topicSession = topicCon.createTopicSession(false, Session.AUTO_ACKNOWLEDGE );
			publisher = topicSession.createPublisher(topic);
			sporocilo = topicSession.createTextMessage();
			sporocilo.setText("Vsebina");
			System.out.println("Pošliljam sporoèilo...");
			publisher.publish(sporocilo);			
			System.out.print("Topic objavljen!");
		}
		catch (JMSException e) {
			System.out.println("Context problem!" + e.toString());
		}
		finally
		{
			if(topicCon!=null)
				try {
					topicCon.close();
				} catch (JMSException e) {
					e.printStackTrace();
				}
		}
	}
}
