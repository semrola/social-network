package db;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface BaseDao {
	public Connection getConnection() throws SQLException;
	public Entiteta vrni(int id);
	public void vstavi(Entiteta ent);
	public void odstrani(int id);
	public void posodobi(Entiteta ent);
	public List<Entiteta> vrniVse();
}
