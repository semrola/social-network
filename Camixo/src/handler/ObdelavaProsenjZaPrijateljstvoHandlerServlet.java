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
import javax.servlet.http.HttpSession;

import dbJPA.Uporabnik;

import ejb.view.UpravljalecPrijateljstevBeanLocal;

/**
 * Servlet implementation class ObdelavaProsenjZaPrijateljstvoHandlerServlet
 */
@WebServlet("/ObdelavaProsenjZaPrijateljstvoHandlerServlet")
public class ObdelavaProsenjZaPrijateljstvoHandlerServlet extends HttpServlet {
	@EJB
	UpravljalecPrijateljstevBeanLocal up;
	
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ObdelavaProsenjZaPrijateljstvoHandlerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(!Helper.logged(request))
			response.sendRedirect("index.jsp");
		
		HttpSession seja=request.getSession();
		String sms="";
		boolean error=false;
		
		int st=0, pr=0;
		try{	
			st=Integer.parseInt(request.getParameter("status"));
			pr=Integer.parseInt(request.getParameter("prosnja"));
		}
		catch(Exception e)
		{
			sms="NE igraj se s POST zahtevkom.";
			error=true;
		}
		
		if(st!=1 && st!=-1)
		{
			sms="NE igraj se s POST zahtevkom.";
			error=true;
		}
		
		if(!error)
		{
			try
			{
				up.spremeniStatusPrijateljstvu(pr, st);
				if(st==1)sms="Uspešno sprejeto.";
				if(st==-1)sms="Uspešno zavrnjeno.";
				Uporabnik u=(Uporabnik)seja.getAttribute("prijava");
				seja.setAttribute("steviloProsenj", up.vrniOdprteProsnjeZaPrijateljstvo(u.getIdUporabnik()).size()); 	//osvezimo cakajocih prosenj
			}
			catch(Exception e)
			{
				sms="Napaka pri obdelavi prijateljstva.";
			}
		}
		request.setAttribute("sms_reaction", sms);
		RequestDispatcher r=request.getRequestDispatcher("PregledProsenjZaPrijateljstvoInitServlet");
		r.forward(request, response);
	}

}
