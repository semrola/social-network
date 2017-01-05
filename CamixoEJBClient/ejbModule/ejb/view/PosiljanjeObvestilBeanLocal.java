package ejb.view;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import dbJPA.Obvestilo;

public interface PosiljanjeObvestilBeanLocal {
	public void razposljiObvestilo(Obvestilo o);
    public void posljiObvestilo(String email, String naziv, String  vsebina) throws AddressException, MessagingException;
}
