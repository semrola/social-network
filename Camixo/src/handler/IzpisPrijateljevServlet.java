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
import javax.servlet.http.HttpSession;

import dbJPA.*;
import ejb.view.UpravljalecPrijateljstevBeanLocal;

/**
 * Servlet implementation class IzpisPrijateljevServlet
 */
@WebServlet("/IzpisPrijateljevServlet")
public class IzpisPrijateljevServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	@EJB
	UpravljalecPrijateljstevBeanLocal up;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public IzpisPrijateljevServlet() {
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
		
		List<Uporabnik> prijatelji=up.vrniVsePrijatelje(u.getIdUporabnik());
		request.setAttribute("prijatelji", prijatelji);
		RequestDispatcher r;
		r=request.getRequestDispatcher("index.jsp?p=izpis_prijateljev.jsp");
		r.forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}

}
