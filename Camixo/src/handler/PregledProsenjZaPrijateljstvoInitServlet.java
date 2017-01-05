package handler;

import helper.Helper;

import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ejb.view.UpravljalecPrijateljstevBeanLocal;
import dbJPA.*;

/**
 * Servlet implementation class PregledProsenjZaPrijateljstvoInitServlet
 */
@WebServlet("/PregledProsenjZaPrijateljstvoInitServlet")
public class PregledProsenjZaPrijateljstvoInitServlet extends HttpServlet {
	@EJB
	UpravljalecPrijateljstevBeanLocal up;
	
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PregledProsenjZaPrijateljstvoInitServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(!Helper.logged(request))
			response.sendRedirect("index.jsp");
		
		Uporabnik u=(Uporabnik)request.getSession().getAttribute("prijava");
		List<Prijateljstvo> list=up.vrniOdprteProsnjeZaPrijateljstvo(u.getIdUporabnik());
		if(list.size()==0)
			request.setAttribute("sms_pregled", "Nimate odprtih prošenj");
		request.setAttribute("list_frendov", list);
		RequestDispatcher r=request.getRequestDispatcher("index.jsp?p=pregledPrijateljstev.jsp");
		r.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}

}
