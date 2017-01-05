package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class NoviceDaoImpl implements BaseDao {

	@Override
	public Connection getConnection() throws SQLException {
		Connection con = null;
		try
		{ 
			Context initCtx = new InitialContext();
			//Context envCtx = (Context) initCtx.lookup("localhost"); 
			// DataSource pridobimo glede na JNDI 
			DataSource ds = (DataSource) initCtx.lookup("jdbc/CamixoDS"); 
			con = ds.getConnection(); 
		}
		catch(NamingException e)
		{ 
			System.out.println("JDBC vir ne obstaja!");
			e.printStackTrace();
		} 
		
		return con;
	}

	@Override
	public Entiteta vrni(int id) {
		Connection conn=null;
		try {
			conn = getConnection();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Novica novica=new Novica();
		
		PreparedStatement stmt = null;
		try{ 
			
			String sql = "SELECT * FROM novice where id_novica=?";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1,id);
			ResultSet rs = stmt.executeQuery(); // obdelava rezultatov 
			if(rs.next())
			{ 
				novica.setIdNovica(rs.getInt("id_novica"));
				novica.setIdAvtor(rs.getInt("id_avtor"));
				novica.setNaziv(rs.getString("naziv"));
				novica.setVsebina(rs.getString("vsebina"));
				novica.setDatumObjave(rs.getTimestamp("datum_objave"));
				//System.out.println(rs.getInt("id_novica"));
			}
			//else { System.out.println("Ne najdem novic"); } 
		}
		catch(SQLException e)
		{ 
			System.out.println("Napaka pri SQL povezavi - vrni() metoda");
		}
		finally
		{ 
			if(stmt != null)
				try {
					stmt.close();
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		return novica;
	}

	@Override
	public void vstavi(Entiteta ent) {
		// TODO Auto-generated method stub
		Connection conn=null;
		try {
			conn = getConnection();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		Novica novica=(Novica)ent;
		PreparedStatement ps = null;
		try
		{
			String sql = "insert into novice (naziv,id_avtor,vsebina) values (?,?,?)";
			ps = conn.prepareStatement(sql);
			ps.setString(1,novica.getNaziv());
			ps.setInt(2,novica.getIdAvtor());
			ps.setString(3,novica.getVsebina());
			//ps.setDate(4,novica.getDatumObjave());
			int s=ps.executeUpdate();
			System.out.print("Stevilo spremenjenih vrstic:"+s);
		}
		catch(SQLException e)
		{
			System.out.println("Napaka pri SQL povezavi - vstavi() metoda");
		}
		finally
		{
			if(ps != null)
				try {
					ps.close();
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}
	
	@Override
	public List<Entiteta> vrniVse() {
		Connection conn=null;
		try {
			conn = getConnection();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		ArrayList<Entiteta> vseNovice=new ArrayList<Entiteta>();
		
		Statement stmt = null;
		try{ 
			stmt = conn.createStatement();
			String sql = "SELECT * FROM novice ORDER BY datum_objave DESC";
			ResultSet rs = stmt.executeQuery(sql); // obdelava rezultatov 
			
			while(rs.next())
			{ 
				Novica novica=new Novica();
				novica.setIdNovica(rs.getInt("id_novica"));
				novica.setIdAvtor(rs.getInt("id_avtor"));
				novica.setNaziv(rs.getString("naziv"));
				novica.setVsebina(rs.getString("vsebina"));
				novica.setDatumObjave(rs.getTimestamp("datum_objave"));
				vseNovice.add(novica);
				//System.out.println(rs.getInt("id_novica"));
			}
			//else { System.out.println("Ne najdem novic"); } 
		}
		catch(SQLException e)
		{ 
			System.out.println("Napaka pri SQL povezavi - vrniVse() metoda");
		}
		finally
		{ 
			if(stmt != null)
				try {
					stmt.close();
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		return vseNovice;
	}

	@Override
	public void odstrani(int id) {
		// TODO Auto-generated method stub
		Connection conn=null;
		try {
			conn = getConnection();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		PreparedStatement ps = null;
		try
		{
			String sql = "delete from novice where id_novica=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1,id);
			int s=ps.executeUpdate();
			System.out.println("Odstranjenih vrstic"+s);
		}
		catch(SQLException e)
		{
			System.out.println("Napaka pri SQL povezavi - odstrani() metoda");
		}
		finally
		{
			if(ps != null)
				try {
					ps.close();
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}

	@Override
	public void posodobi(Entiteta ent) {
		Connection conn=null;
		try {
			conn = getConnection();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		Novica novica=(Novica)ent;
		PreparedStatement ps = null;
		try
		{
			System.out.print("podatki od novice "+ novica.getIdNovica());
			String sql = "update novice set naziv=?,id_avtor=?,vsebina=? where id_novica=?";
			ps = conn.prepareStatement(sql);
			ps.setString(1,novica.getNaziv());
			ps.setInt(2,novica.getIdAvtor());
			ps.setString(3,novica.getVsebina());
			//ps.setDate(4,novica.getDatumObjave());
			ps.setInt(4,novica.getIdNovica());
			//System.out.println("id: " + novica.getIdNovica());
			int s= ps.executeUpdate();
			System.out.print("Stevilo spremenjenih vrstic"+s);
		}
		catch(SQLException e)
		{
			System.out.println("Napaka pri SQL povezavi - posodobi() metoda");
		}
		finally
		{
			if(ps != null)
				try {
					ps.close();
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		
	}
	
	public void transakcija(Entiteta ent1, Entiteta ent2)
	{
		Connection conn = null;
		try {
			conn=getConnection();
		} catch (SQLException e) {
			System.out.println("POVEZAVAAAAAAAAAAA");
		}
		
		Novica n1=(Novica)ent1;
		Novica n2=(Novica)ent2;
		
		//String sql1="INSERT INTO novice (naziv,id_avtor,vsebina) VALUES("+n1.getNaziv()+","+n1.getIdAvtor()+","+n1.getVsebina() +")";
		String sql1="insert into novice (naziv,id_avtor,vsebina) values (?,?,?)";
		String sql2="insert into novice (naziv,id_avtor,vsebina) values (?,?,?)";
		//String sql3="insert into novice (naziv,id_avtor,vsebina) values (ehja,2,ehja)";
		
		PreparedStatement ps1=null,ps2=null;
		try {
			conn.setAutoCommit(false);
			ps1 = conn.prepareStatement(sql1);
			ps2 = conn.prepareStatement(sql2);
			ps1.setString(1, n1.getNaziv());
			ps1.setInt(2, n1.getIdAvtor());
			ps1.setString(3, n1.getVsebina());
			ps1.executeUpdate();
			ps2.setString(1, n2.getNaziv());
			ps2.setInt(2, n2.getIdAvtor());
			//ps2.setString(3, n2.getVsebina());	//napaka, ker je zakomentirano se naredi ROLLBACK
			ps2.executeUpdate();
			conn.commit();
		} catch (Exception e) {
			try {conn.rollback(); System.out.println("ROLLBACKEEEEED");} 
			catch (SQLException e1) {System.out.println("ROLLBACK NOT possible");}
		}
		finally
		{
			try{
				if(ps1 != null) ps1.close(); 
				if(ps2 != null) ps2.close(); 
				if(conn != null) conn.close();}
			catch(SQLException s) {System.out.println("WASU");}
		}
		
	}
	

}
