package handler;

import java.io.IOException;

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
 * Servlet implementation class UrejanjeNoviceHandlerServlet
 */
@WebServlet("/UrejanjeNoviceInitServlet")
public class UrejanjeNoviceInitServlet extends HttpServlet {
	@Inject
	private UporabnikManager um;
	
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UrejanjeNoviceInitServlet() {
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
		if(u.getTipUporabnika()!=1)	//admini
			response.sendRedirect("index.jsp");
			
		try
		{
			int idNovica=Integer.parseInt(request.getParameter("id_novica"));
			NoviceDaoImpl n=new NoviceDaoImpl();
			Novica novica=(Novica) n.vrni(idNovica);
			request.setAttribute("novica", novica);
		}
		catch (Exception e) {
			request.setAttribute("sms", "Napaka pri urejanju novice!");
		}
		RequestDispatcher r=request.getRequestDispatcher("index.jsp?p=popravi_novico.jsp");
		r.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
