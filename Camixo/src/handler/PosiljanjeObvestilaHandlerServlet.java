package handler;

import helper.Helper;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dbJPA.Obvestilo;
import dbJPA.Uporabnik;
import ejb.view.UpravljalecObvestilBeanLocal;

/**
 * Servlet implementation class PosiljanjeObvestilaHandlerServlet
 */
@WebServlet("/PosiljanjeObvestilaHandlerServlet")
public class PosiljanjeObvestilaHandlerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PosiljanjeObvestilaHandlerServlet() {
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
	@EJB
	UpravljalecObvestilBeanLocal uo;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(!Helper.logged(request))
			response.sendRedirect("index.jsp");
		if(!Helper.loggedAdmin(request))
			response.sendRedirect("index.jsp");
		
		String naziv=request.getParameter("naziv");
		naziv=new String(naziv.getBytes("8859_1"),"UTF8");
		
		String vsebina=request.getParameter("vsebina");
		vsebina=new String(vsebina.getBytes("8859_1"),"UTF8");
		
		Uporabnik u=(Uporabnik)request.getSession().getAttribute("prijava");
		Obvestilo o=new Obvestilo();
		o.setIdAvtor(u);
		o.setNaziv(naziv);
		o.setVsebina(vsebina);
		
		uo.vstaviObvestiloVVrsto(o);
		
		request.setAttribute("sms", "Obvestilo uspešno odddano v vrsto");
		RequestDispatcher r=request.getRequestDispatcher("index.jsp?p=vnosObvestil.jsp");
		r.forward(request, response);
	}

}
