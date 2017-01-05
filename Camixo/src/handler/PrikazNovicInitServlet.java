package handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import db.Entiteta;
import db.Novica;
import db.NoviceDaoImpl;
import db.UporabnikManager;

import dbJPA.Uporabnik;
/**
 * Servlet implementation class PrikazNovicInitServlet
 */
@WebServlet("/PrikazNovicInitServlet")
public class PrikazNovicInitServlet extends HttpServlet {
	@Inject
	private UporabnikManager um;
	
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PrikazNovicInitServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
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
		
		try
		{
			NoviceDaoImpl n=new NoviceDaoImpl();
			List<Entiteta> list=n.vrniVse();
			request.setAttribute("novice", list);
			
			ArrayList<String> avtorji=new ArrayList<String>();
			Novica nov;
			for(Entiteta ent:list)
			{
				nov=(Novica)ent;
				avtorji.add(um.findUporabnikById(nov.getIdAvtor()).getUsername());
			}
			request.setAttribute("avtorji", avtorji);
		}
		catch (Exception e) {
			request.setAttribute("sms_PrikazNovic", "Napaka pri izpisu novic");
		}
		
		RequestDispatcher r=request.getRequestDispatcher("novice.jsp");
		r.forward(request, response);
	}

}
