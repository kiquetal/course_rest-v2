package py.com.edge.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import py.com.edge.models.User;

@Service
public class UsersServicesEdgeImpl implements UsersServicesEdge {

	String connectionURL = "jdbc:mysql://localhost:3306/course_rest";  

	public List<User> getAllUsers()
	{

	// User u=new User();

	List<User>l=new ArrayList<User>();
	  Connection con = null;  
	  try {  
	      Class.forName("com.mysql.jdbc.Driver");  
	      con = DriverManager.getConnection (connectionURL,"kiquetal","paraguay");  
	      Statement stmt = con.createStatement();  
	      ResultSet rs = stmt.executeQuery("select * from users");  
	      System.out.println("Succeded !");  
	     while(rs.next())
	{
	User u=new User(); 
	u.setId(rs.getInt("id"));
	u.setName(rs.getString("name"));
	l.add(u);
	System.out.println("he encontrado el user"+ rs.getString("name"));
	}
	  }  
	  catch (SQLException e) {  
	      e.printStackTrace();  
	  }  
	  catch (Exception e) {  
	      e.printStackTrace();  
	  }  
	  finally {  
	      try {  
	    con.close();  
	   } catch (SQLException e) {  
	    e.printStackTrace();  
	   }  
	  }  
	  return l; 
	 }

	public void saveNewUserSkills() {
		// TODO Auto-generated method stub
System.out.println("debemos guardar el new skills");
	}  

	}
			

	
	

