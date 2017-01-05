package ejb;

import java.util.Date;
import java.util.List;
import java.util.Properties;

import dbJPA.*;
import ejb.view.PosiljanjeObvestilBeanLocal;

import javax.annotation.Resource;
import javax.ejb.EJBContext;
import javax.ejb.Local;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.jms.ConnectionFactory;
import javax.jms.Queue;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;


/**
 * Session Bean implementation class PosiljanjeObvestilBean
 */
@Stateless
@Local(PosiljanjeObvestilBeanLocal.class)
@LocalBean
@TransactionManagement(TransactionManagementType.BEAN)
public class PosiljanjeObvestilBean implements PosiljanjeObvestilBeanLocal {

	@Resource
	private ConnectionFactory cf;
	
	@Resource
	private Queue vrsta;
	
	@PersistenceUnit( unitName="CamixoJPA" )
	private EntityManagerFactory emf;
    
	@Resource
	private EJBContext kon;
	
    /**
     * Default constructor. 
     */
    public PosiljanjeObvestilBean() {
        // TODO Auto-generated constructor stub
    }

	@Override
	public void razposljiObvestilo(Obvestilo o) {
		
		//vstavimo obvestilo v bazo
		EntityManager em=null;
		UserTransaction ut=kon.getUserTransaction();
		try
		{	
			ut.begin();
			em=emf.createEntityManager();
			em.persist(o);
			ut.commit();
		}
		catch (Exception e) {
			try {
				ut.rollback();
			} catch (IllegalStateException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SecurityException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SystemException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			em.close();
			System.out.println("Napaka pri vstavljanju obvestila razposljiObvestilo()");
		}
		
		EntityManager em1=null;
		try
		{
			em1=emf.createEntityManager();
			Query q=em1.createNamedQuery("vrniVseUporabnike");
			List<Uporabnik> list=(List<Uporabnik>) q.getResultList();
			for(Uporabnik u:list)
			{
				posljiObvestilo(u.getEmail(),o.getNaziv(),o.getVsebina());
			}
		}
		catch (Exception e) {
			System.out.println("Napaka pri vstavljanju obvestila razposljiObvestilo() vsem uporabnikom");
			System.out.println(e);
		}
		finally
		{
			em1.close();
		}
		
	}

	@Resource
	private Session session;
	
	@Override
	public void posljiObvestilo(String email, String naziv, String vsebina) throws AddressException, MessagingException {
		System.out.println("Email pošlji na:"+email+" obvestilo - ime:"+naziv+" in vsebina:"+vsebina);
		
		//Properties props=session.getProperties();
		//props.put(key, value)
		session.getProperties().put("mail.transport.protocol", "smtp");
		session.getProperties().put("mail.smtp.port", "25");
		session.getProperties().put("mail.smtp.host", "localhost");
		//session.getProperties().put("mail.smtp.starttls.enable", true);
		//session.getProperties().put("mail.smtp.starttls.required", true);
		
		/*Properties props = new Properties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.host", "");
        props.put("mail.smtp.auth", true);
        props.put("mail.smtp.starttls.enable", true);
        props.put("mail.smtp.starttls.required", true);
        Authenticator auth = new SMTPAuthenticator();
        Session mailSession = Session.getDefaultInstance(props, auth);
        Transport transport = mailSession.getTransport();

        MimeMessage message = new MimeMessage(mailSession);
        message.setContent("This is a test", "text/plain");
        message.setFrom(new InternetAddress("me@myhost.org"));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress("semrola007@gmail.com"));

        transport.connect();
        transport.sendMessage(message,
            message.getRecipients(Message.RecipientType.TO));
        transport.close();*/
		
		Message msg=new MimeMessage(session);
		msg.setFrom(new InternetAddress("admin_noreply@camixo.org"));
		//msg.setFrom();
		msg.setSentDate(new Date());
		msg.setRecipients(Message.RecipientType.TO,InternetAddress.parse(email,false));
		MimeBodyPart mbp=new MimeBodyPart();
		mbp.setText(vsebina);
		
		Multipart mp=new MimeMultipart();
		mp.addBodyPart(mbp);
		msg.setContent(mp);
		msg.setSubject(naziv);
		System.out.println("Chkpoint1");
		Transport.send(msg);
		/*Transport transport = session.getTransport();
		System.out.println("Chkpoint2");
		transport.connect();
		System.out.println("Chkpoint3");
		transport.sendMessage(msg, msg.getRecipients(Message.RecipientType.TO));
		System.out.println("Chkpoint4");
		transport.close();*/
		System.out.println("Done");
	}
	
	private class SMTPAuthenticator extends javax.mail.Authenticator {
		public PasswordAuthentication getPasswordAuthentication() {
	        String username = "semrola7@gmail.com";
	        String password = "PRPOvaje";
	        return new PasswordAuthentication(username, password);
		}
	}

}
