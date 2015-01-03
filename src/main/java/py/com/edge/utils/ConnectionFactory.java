package py.com.edge.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

public ConnectionFactory() {
	}
	
private  Connection con=null;
public  Connection getConnection () throws SQLException{
		if (con==null)
	       con= DriverManager.getConnection("jdbc:mysql://localhost:3306/course_rest","kiquetal","paraguay");
		return con;
}
	

	
}