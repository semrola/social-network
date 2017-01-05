package handler;

import helper.Helper;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import db.UporabnikManager;
import dbJPA.Uporabnik;

/**
 * Servlet implementation class UrejanjeProfilServlet
 */
@WebServlet("/UrejanjeProfilServlet")
public class UrejanjeProfilServlet extends HttpServlet {
	@Inject 
	private UporabnikManager um;
	
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UrejanjeProfilServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session=request.getSession();
		Uporabnik u=(Uporabnik)session.getAttribute("prijava");
		if(u==null)
			response.sendRedirect("index.jsp");
		
		PrintWriter out=response.getWriter();
		
		if(request.getParameter("izvoz").equals("html"))
			response.setContentType("text/html");
		else 
		if(request.getParameter("izvoz").equals("excel"))
		{
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "Attachment;Filename=\"profil.xls\"");
		}
			out.println("<html>");
			out.println("<head>");			
			out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");
			out.println("</head>");
			out.println("<body>");
			out.println("<table>");
			out.println("<tr><td>Ime</td><td>"+Helper.sumniki(u.getIme())+"</td></tr>");
			out.println("<tr><td>Priimek</td><td>"+Helper.sumniki(u.getPriimek())+"</td></tr>");
			out.println("<tr><td>Spol</td><td>"+u.getSpol()+"</td></tr>");
			out.println("<tr><td>Naslov</td><td>"+Helper.sumniki(u.getNaslov())+"</td></tr>");
			out.println("<tr><td>Vrsta &#353;tudija</td><td>"+u.getVrstaStudija()+"</td></tr>");
			out.println("<tr><td>Stopnja &#353;tudija</td><td>"+u.getStopnjaStudija()+"</td></tr>");
			out.println("<tr><td>&#352;tudijska smer</td><td>"+u.getStudijskaSmer()+"</td></tr>");
			out.println("<tr><td>Leto rojstva</td><td>"+u.getDatumRojstva()+"</td></tr>");
			out.println("<tr><td>Leto diplomiranja</td><td>"+u.getDatumDiplome()+"</td></tr>");
			out.println("<tr><td>E-Mail</td><td>"+u.getEmail()+"</td></tr>");
			out.println("</table>");
			out.println("</body>");
			out.println("</html>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		HttpSession session=request.getSession();
		if(session==null)
		{
			response.sendRedirect("index.jsp");
		}
		
		Uporabnik u=(Uporabnik)session.getAttribute("prijava");		
		ArrayList<String> sms=new ArrayList<String>();
		boolean error=false;
		
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
		
		
		//--------VRSTA STUDIJA-----------
		String studij=request.getParameter("studij");
		if(!(studij.equals("redno") || studij.equals("izredno") || studij.equals("pavzer")))
		{
			error=true;
			sms.add("POST zahtevek je bil spremenjen. Neveljaven tip študija.");
		}

		
		//------STOPNJA STUDIJA----------
		String stopnja=request.getParameter("stopnja");
		if(!(stopnja.equals("prva") || stopnja.equals("druga") || stopnja.equals("tretja")))
		{
			error=true;
			sms.add("POST zahtevek je bil spremenjen. Neveljaven podatek o stopnji študija");
		}

		//-----------SMER-----------------
		String smer=request.getParameter("smer");
		smer=new String(smer.getBytes("8859_1"),"UTF8");
		if(!(smer.equals("UN") || smer.equals("VS") || smer.equals("VŠ")))
		{
			error=true;
			sms.add("POST zahtevek je bil spremenjen. Neveljaven podatek o študijski smeri");
		}


		//------DIPL IN ROJSTVO---------
		int rojstvo=1;
		int dipl=1;
		try{
			rojstvo=Integer.parseInt(request.getParameter("rojstvo"));
			dipl=Integer.parseInt(request.getParameter("dipl"));
		}catch (Exception e) {
			sms.add("Datumi niso pravilni");
			error=true;
		}
		
		//-----------EMAIL--------------
		String email=request.getParameter("email");
		String pattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";	//za email
		Pattern p = Pattern.compile(pattern);
		Matcher mail = p.matcher(email);
		if(!mail.matches()){
			sms.add("Napaèno vnešen email naslov");
			error=true;
		}
			
		
		//------------GESLO-----------------
		String pass1=request.getParameter("pass1");
		String pass2=request.getParameter("pass2");
		if(!pass1.equals(pass2) || pass1.equals("")){
			//sms.add("geslo" + pass1 +" "+pass2);
			if(pass1.equals(""))
			sms.add("Vnesi geslo");
			else
			sms.add("Gesli se ne ujemata");
			error=true;
		}
		if(naslov.equals("") || ime.equals("") || priimek.equals("") || rojstvo==0 || dipl==0){
			sms.add("Vnesite vse potrebne podatke");
			error=true;
		}
		if(!error){
			u.setIme(ime);
			u.setPriimek(priimek);
			u.setSpol(spol);
			u.setNaslov(naslov);
			u.setDatumRojstva(rojstvo);
			u.setDatumDiplome(dipl);
			u.setVrstaStudija(studij);
			u.setStopnjaStudija(stopnja);
			u.setStudijskaSmer(smer);
			u.setEmail(email);
			String salt=Helper.generirajSalt();
			u.setSalt(salt);
			u.setGeslo(Helper.kodirajGeslo(pass1, salt));
			um.updateUporabnika(u);
			sms.add("Uspešno spremenjeni podatki!");
			
			
			String css=request.getParameter("css");
			Cookie cookie=new Cookie("css1",css);
			response.addCookie(cookie);
			
			request.setAttribute("css", 1);
		}
		
		RequestDispatcher r=request.getRequestDispatcher("index.jsp?p=urejanje_profil.jsp");
		request.setAttribute("smsarray",sms);
		r.forward(request, response);
		
	}

}
