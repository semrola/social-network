package handler;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dbJPA.Uporabnik;

import ejb.view.UpravljalecPrijateljstevBeanLocal;

/**
 * Servlet implementation class PosiljanjeZahteveZaPrijateljstvoInitServlet
 */
@WebServlet("/PosiljanjeZahteveZaPrijateljstvoInitServlet")
public class PosiljanjeZahteveZaPrijateljstvoInitServlet extends HttpServlet {
	
	@EJB
	UpravljalecPrijateljstevBeanLocal up;
	
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PosiljanjeZahteveZaPrijateljstvoInitServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//varnost
		HttpSession session=request.getSession();
		Uporabnik u=(Uporabnik)session.getAttribute("prijava");
		int idPo=u.getIdUporabnik();
		int idPr=Integer.parseInt(request.getParameter("idPrejemnik"));
		String sms="";
		try{
			up.posljiProsnjoZaPrijateljstvo(idPo, idPr);
			sms="Prošnja uspešno poslana";
		}
		catch(Exception e)
		{
			sms="Prošnja neuspešno poslana";
		}
		request.setAttribute("sms_prosnja", sms);
		RequestDispatcher r=request.getRequestDispatcher("index.jsp?p=iskalnik.jsp");
		r.forward(request,response);
	}

}
