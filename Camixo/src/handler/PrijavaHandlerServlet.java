package handler;

import helper.Helper;

import java.io.IOException;

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

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * Servlet implementation class PrijavaHandlerServlet
 */
@WebServlet("/PrijavaHandlerServlet")
public class PrijavaHandlerServlet extends HttpServlet {
	
	@Inject
	private UporabnikManager um;
	@EJB
	UpravljalecPrijateljstevBeanLocal up;
	
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PrijavaHandlerServlet() {
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
		
		boolean error=false;
		String sms="";
		
		//preberemo vnesena polja
		String uime=request.getParameter("uime");
		String pass=request.getParameter("phrase");
		uime = new String(uime.getBytes("8859_1"), "UTF8");
		pass = new String(pass.getBytes("8859_1"), "UTF8");
		
		//dovolimo le alfanumericne znake in podcrtaj
		String pattern = "\\A[A-Za-z_0-9]+\\z";
		
		Pattern p = Pattern.compile(pattern);
		Matcher m_ime = p.matcher(uime);
		Matcher m_pass = p.matcher(pass);
		if(!(m_ime.matches() && m_pass.matches()))
		{
			sms="Samo alfanumerièni znaki in podèrtaj";
			error=true;
		}

		if(!error)
		{
			Uporabnik up=um.getUporabnikByUsername(uime);
		
			if(up==null)
			{
				sms="Nepravilno uporaniško ime in/ali geslo";
				error=true;
			}
			else
			{
				pass=Helper.kodirajGeslo(pass, up.getSalt());
			}
		}
		
		Uporabnik u=um.avtenticirajUporabnika(uime, pass);

		if(u!=null && !error)
		{
			//ime in geslo pravilna
			int steviloProsenj=0;
			HttpSession session = request.getSession();	//ustvarimo sejo
			session.setAttribute("prijava", u);
			
			List<Prijateljstvo> list=up.vrniOdprteProsnjeZaPrijateljstvo(u.getIdUporabnik());
			if(list!=null)
				steviloProsenj=list.size();
			else
				steviloProsenj=0;
			session.setAttribute("steviloProsenj", steviloProsenj);
			
			sms="Prijava uspešna";			
			request.setAttribute("odjava", u);
		}
		else
		{
			//nepravilni podatki
			sms="Nepravilni podatki za dostop!";			
		}
		
		request.setAttribute("sms", sms);
		RequestDispatcher r;
		r= request.getRequestDispatcher("index.jsp?p=predstavitev.jsp");
		r.forward(request, response);
		
		
	}
	
	@Deprecated
	public boolean check(String x, String y)
	{
		//testni uporabniki
		String[] imena={"sandi","test","admin"};
		String[] gesla={"sandi","test","admin"};
		
		for(int i=0;i<imena.length;i++)
		{
			if(imena[i].equals(x))
			{ 
				if(gesla[i].equals(y))
					return true;
			}
		}
		return false;	
	}

}
