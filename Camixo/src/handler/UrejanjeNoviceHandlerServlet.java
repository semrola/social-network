package handler;

import java.io.IOException;
import java.util.ArrayList;

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

/**
 * Servlet implementation class UrejanjeNoviceHandlerServlet
 */
@WebServlet("/UrejanjeNoviceHandlerServlet")
public class UrejanjeNoviceHandlerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UrejanjeNoviceHandlerServlet() {
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
		ime=new String(ime.getBytes("8859_1"),"UTF8");
		if(ime.equals(""))
		{
			sms.add("Prazno polje Ime novice");
			error=true;
		}
		
		int id_novica=0;
		try{
			id_novica=Integer.parseInt(request.getParameter("id_novica"));
		}
		catch (Exception e) {
			sms.add("Nepravilen ID novice");
			error=true;
		}

		String vsebina=request.getParameter("vsebina");
		vsebina=new String(vsebina.getBytes("8859_1"),"UTF8");
		if(vsebina.equals(""))
		{
			sms.add("Prazna vsebina");
			error=true;
		}
		
		if(!error)
		try{
			Novica novica=new Novica();
			novica.setIdNovica(id_novica);
			novica.setIdAvtor(u.getIdUporabnik());
			novica.setNaziv(ime);
			novica.setVsebina(vsebina);
			//Date d=new java.sql.Date(new java.util.Date().getTime());
			//novica.setDatumObjave(d);
			n.posodobi(novica);
			sms.add("Novica uspešno posodobljena");
		}
		catch (Exception e) {
			sms.add("Prišlo je do napake pri urejanju novice");
		}
		
		request.setAttribute("sms_Urejanje", sms);
		RequestDispatcher r=request.getRequestDispatcher("index.jsp?p=PrikazNovicInitServlet");
		r.forward(request, response);
		
	}

}
