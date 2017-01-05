package handler;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dbJPA.Uporabnik;
import db.NoviceDaoImpl;

/**
 * Servlet implementation class BrisanjeNoviceHandlerServlet
 */
@WebServlet("/BrisanjeNoviceHandlerServlet")
public class BrisanjeNoviceHandlerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BrisanjeNoviceHandlerServlet() {
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
		if(u.getTipUporabnika()!=1)
			response.sendRedirect("index.jsp");
		
		try
		{
			NoviceDaoImpl n=new NoviceDaoImpl();
			n.odstrani(Integer.parseInt(request.getParameter("id_novica")));
			request.setAttribute("sms_Brisanje", "Brisanje novice uspešno!");
		}
		catch (Exception e) {
			request.setAttribute("sms_Brisanje", "Napaka pri brisanju novice!");
		}
		
		RequestDispatcher r=request.getRequestDispatcher("index.jsp?p=PrikazNovicInitServlet");
		r.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
