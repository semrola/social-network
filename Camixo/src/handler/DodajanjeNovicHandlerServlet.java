package handler;

import java.io.IOException;
import java.util.ArrayList;

import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dbJPA.Uporabnik;

import db.Novica;
import db.NoviceDaoImpl;
import db.UporabnikManager;

/**
 * Servlet implementation class DodajanjeNovicHandlerServlet
 */
@WebServlet("/DodajanjeNovicHandlerServlet")
public class DodajanjeNovicHandlerServlet extends HttpServlet {
	@Inject
	private UporabnikManager um;
	
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DodajanjeNovicHandlerServlet() {
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
		HttpSession session=request.getSession();
		Uporabnik u=(Uporabnik)session.getAttribute("prijava");
		if(u==null)
			response.sendRedirect("index.jsp");
		if(u.getTipUporabnika()!=1)
			response.sendRedirect("index.jsp");
		
		boolean error=false;
		ArrayList<String> sms=new ArrayList<String>();
		
		//novico vpisal v podatkovno bazo ter izvedel preusmeritev na JSP stran za prikaz novic.
		NoviceDaoImpl n=new NoviceDaoImpl();
		
		String ime=request.getParameter("imeNovice");
		ime=new String(ime.getBytes("8859_1"), "UTF8");
		if(ime.equals(""))
		{
			sms.add("Prazno polje Ime novice");
			error=true;
		}

		String vsebina=request.getParameter("vsebina");
		vsebina=new String(vsebina.getBytes("8859_1"), "UTF8");
		if(vsebina.equals(""))
		{
			sms.add("Prazna vsebina");
			error=true;
		}
		
		if(!error)
		try{
			Novica novica=new Novica();
			novica.setIdAvtor(u.getIdUporabnik());
			novica.setNaziv(ime);
			novica.setVsebina(vsebina);
			n.vstavi(novica);
			sms.add("Novica uspešno dodana!");
		}
		catch (Exception e) {
			sms.add("Prišlo je do napake pri dodajanju novice");
		}
		
		request.setAttribute("sms", sms);
		RequestDispatcher r=request.getRequestDispatcher("index.jsp?p=vnosNovic.jsp");
		r.forward(request, response);
		
		
		
	}

}
