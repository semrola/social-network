package handler;

import helper.Helper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import db.UporabnikManager;
import dbJPA.Uporabnik;

/**
 * Servlet implementation class RegistracijaHandlerServlet
 */
@WebServlet("/RegistracijaHandlerServlet")
public class RegistracijaHandlerServlet extends HttpServlet {
	
	@Inject
	private UporabnikManager um;
	
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegistracijaHandlerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		ArrayList<String> sms=new ArrayList<String>();
		boolean error=false;
		
		String username=request.getParameter("username");
		username = new String(username.getBytes("8859_1"), "UTF8");
		String ime=request.getParameter("ime");
		ime = new String(ime.getBytes("8859_1"), "UTF8");
		String priimek=request.getParameter("priimek");
		priimek = new String(priimek.getBytes("8859_1"), "UTF8");
		String spol=request.getParameter("spol");
		try{
		spol = new String(spol.getBytes("8859_1"), "UTF8");
		}
		catch (Exception e) {
			spol="";
		}
		String naslov=request.getParameter("naslov");
		naslov = new String(naslov.getBytes("8859_1"), "UTF8");
		
		if(username.equals("")){
			error=true;
			sms.add("Podatek uporabniško ime ni bil vnešen");
		}
		if(ime.equals("")){
			error=true;
			sms.add("Podatek ime ni bil vnešen");
		}
		if(priimek.equals("")){
			error=true;
			sms.add("Podatek priimek ni bil vnešen");
		}
		//spol ni obvezen
		/*if(spol.equals("")){
			error=true;
			sms.add("Podatek spol ni bil vnešen");
		}*/
		if(naslov.equals("")){
			error=true;
			sms.add("Podatek naslov ni bil vnešen");
		}
		//vrsta studija
		String studij=request.getParameter("studij");
		if(!studij.equals("redno") && !studij.equals("izredno") && !studij.equals("pavzer"))
		{
			error=true;
			sms.add("POST zahtevek je bil spremenjen. Neveljaven tip študija.");
		}
		
		//stopnja studija
		String stopnja=request.getParameter("stopnja");
		if(!stopnja.equals("prva") && !stopnja.equals("druga") && !stopnja.equals("tretja"))
		{
			error=true;
			sms.add("POST zahtevek je bil spremenjen. Neveljaven podatek o stopnji študija");
		}
		
		//smer
		String smer=request.getParameter("smer");
		smer=new String(smer.getBytes("8859_1"),"UTF8");
		if(!smer.equals("UN") && !smer.equals("VS") && !smer.equals("VŠ"))
		{
			error=true;
			sms.add("POST zahtevek je bil spremenjen. Neveljaven podatek o študijski smeri");
		}
		
		//rojstvo ni obvezno
		int rojstvo=0;
		String roj=request.getParameter("rojstvo");
		if(!roj.equals(""))
		{
			try{
				rojstvo=Integer.parseInt(roj);
			}
			catch (Exception e) {
				sms.add("Rojstni datum ni pravilen");
				error=true;
			}
		}
		
		int dipl=0;
		try{
		dipl=Integer.parseInt(request.getParameter("dipl"));
		}catch (Exception e) {
			sms.add("Datum diplomiranja ni pravilen");
			error=true;
		}
		
		//EMAIL
		String email=request.getParameter("email");
		String pattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";	//za email
		Pattern p = Pattern.compile(pattern);
		Matcher mail = p.matcher(email);
		if(!mail.matches()){
			sms.add("Napaèno vnešen email naslov");
			error=true;
		}
		
		
		String pass1=request.getParameter("pass1");
		String pass2=request.getParameter("pass2");
		if(!pass1.equals(pass2) || pass1.equals("")){
			sms.add("Gesli se ne ujemata");
			error=true;
		}
		
		if(!error){
			Uporabnik u = new Uporabnik();
			u.setIme(ime);
			u.setPriimek(priimek);
			u.setSpol(spol);
			u.setDatumRojstva(rojstvo);
			u.setNaslov(naslov);
			u.setDatumDiplome(dipl);
			u.setVrstaStudija(studij);
			u.setStudijskaSmer(smer);
			u.setEmail(email);
			u.setStopnjaStudija(stopnja);
			String salt=Helper.generirajSalt();
			u.setSalt(salt);
			u.setTipUporabnika(2);
			u.setGeslo(Helper.kodirajGeslo(pass1, salt));
			u.setUsername(username);
			
			try {um.createUporabnik(u);} 
			catch (Exception e) {e.printStackTrace();}
			
			sms.add("Uspešna registracija uporabnika!");
			HttpSession seja=request.getSession();
			seja.setAttribute("prijava", u);
		}
		
		RequestDispatcher r=request.getRequestDispatcher("index.jsp?p=predstavitev.jsp");
		request.setAttribute("smsarray",sms);
		r.forward(request, response);
	}

}
