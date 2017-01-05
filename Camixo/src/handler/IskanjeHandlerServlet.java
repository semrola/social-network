package handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import db.UporabnikManager;
import dbJPA.Prijateljstvo;
import dbJPA.Uporabnik;
import ejb.view.UpravljalecPrijateljstevBeanLocal;

/**
 * Servlet implementation class IskanjeHandlerServlet
 */
@WebServlet("/IskanjeHandlerServlet")
public class IskanjeHandlerServlet extends HttpServlet {
	@Inject
	private UporabnikManager um;
	
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public IskanjeHandlerServlet() {
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
		
		String sms="";
		String search=request.getParameter("search");
		search=new String(search.getBytes("8859_1"), "UTF8");
		
		List<Uporabnik> list=um.searchUporabnik(search);
		if(list==null)
			sms="Poizvedba ni obrodila sadov";
		else
		{
			/*List<Uporabnik> newList=new ArrayList<Uporabnik>();
			for(Uporabnik oldUp:list)
				if(oldUp.getIdUporabnik()!=u.getIdUporabnik())
					newList.add(oldUp);*/
			
			int[] tab=new int[list.size()];
			for(int i=0; i<list.size();i++)
				tab[i]=um.checkStatus(u, list.get(i));
			
			request.setAttribute("statusi", tab);
			request.setAttribute("result", list);
			sms="Poizvedba je obrodila sadove (rezultatov: "+list.size()+")";
		}
		
		request.setAttribute("sms", sms);
		RequestDispatcher r=request.getRequestDispatcher("index.jsp?p=iskalnik.jsp");
		r.forward(request, response);
	}

}
