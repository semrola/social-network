package db;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class DemonstracijaTransakcijeServlet
 */
@WebServlet("/DemonstracijaTransakcijeServlet")
public class DemonstracijaTransakcijeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DemonstracijaTransakcijeServlet() {
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
		
		NoviceDaoImpl n=new NoviceDaoImpl();
		Novica n1=new Novica();
		Novica n2=new Novica();
		n1.setIdAvtor(1);
		n1.setNaziv("Test1");
		n1.setVsebina("Test1");
		n2.setIdAvtor(2);
		n2.setNaziv("Test2");
		n2.setVsebina("Test2");
		n.transakcija(n1, n2);
		
		
	}

}
