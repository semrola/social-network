package handler;

import helper.Helper;

import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import db.UporabnikManager;
import dbJPA.Prijateljstvo;
import dbJPA.Uporabnik;
import ejb.view.UpravljalecPrijateljstevBeanLocal;

/**
 * Servlet implementation class BrisiPrijateljstvoHandlerServlet
 */
@WebServlet("/BrisiPrijateljstvoHandlerServlet")
public class BrisiPrijateljstvoHandlerServlet extends HttpServlet {
	@Inject
	private UporabnikManager um;
	
	@EJB
	UpravljalecPrijateljstevBeanLocal up;
	
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BrisiPrijateljstvoHandlerServlet() {
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
		if(!Helper.logged(request))
			response.sendRedirect("index.jsp");
		
		Uporabnik u=(Uporabnik)request.getSession().getAttribute("prijava");
		boolean error=false;
		String sms="";
		String sid=request.getParameter("id_frend");	//id uporabnika
		int id=0;
		try
		{
			id=Integer.parseInt(sid);
		}
		catch(Exception e)
		{
			sms="Napaka pri brisanju";
			error=true;
		}
		//System.out.println("WWWWWWW"+id+"asd"+sid);
		if(!error)
		{
			try{
				boolean vPrvi=false;
				Prijateljstvo pp=null;
				List<Prijateljstvo> l1=u.getPrijateljstvoListPobudnik();
				List<Prijateljstvo> l2=u.getPrijateljstvoListPrejemnik();
				for(Prijateljstvo p:l1)
					if(p.getIdUporabnikPrejemnik().getIdUporabnik()==id)
					{
						pp=p;
						vPrvi=true;
						break;
					}
				if(!vPrvi)
					for(Prijateljstvo p:l2)
						if(p.getIdUporabnikPobudnik().getIdUporabnik()==id)
						{
							pp=p;
							break;
						}
				um.brisiPrijateljstvo(pp.getIdPrijateljstvo());
				sms="Uspešno izbrisano prijateljstvo";
			}
			catch(Exception e){sms="Napaka pri brisanju";}
		}
		
		request.setAttribute("sms_brisi", sms);
		RequestDispatcher r=request.getRequestDispatcher("IzpisPrijateljevServlet");
		r.forward(request, response);
	}

}
