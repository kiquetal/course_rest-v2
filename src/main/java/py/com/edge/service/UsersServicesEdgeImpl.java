package py.com.edge.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import py.com.edge.models.Skill;
import py.com.edge.models.User;
import py.com.edge.utils.ConnectionFactory;
import scala.annotation.meta.getter;

@Service
public class UsersServicesEdgeImpl implements UsersServicesEdge {


	@Autowired
	SkillsServiceEdge skillServiceEdge;
	public List<User> getAllUsers()
	{

	// User u=new User();

	List<User>l=new ArrayList<User>();
	  Connection con = null;
	  try {
		  con=new ConnectionFactory().getConnection();
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

	public void saveNewUserSkills(User u,String skill) throws Exception {
		// TODO Auto-generated method stub
                           System.out.println("debemos guardar el new skills");
                     
               User ue=null;
               Connection con=null;

               try {
         		  con=new ConnectionFactory().getConnection();
         		  if (this.hasUserSkill(skill,u))
	 throw new Exception("User Has Already Skill");
         		  int idSkill=skillServiceEdge.getSkillOrInsertNew(skill);
               PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO users_skills(id_user,id_skill) VALUES (?,?)");
         preparedStatement.setInt(1,u.getId());
         preparedStatement.setInt(2, idSkill);
                int rs=preparedStatement.executeUpdate();
        
                
                  
               }
               
               catch(Exception e)
            {
            	
            	   throw e;
            }
               finally
               {
            	   try {
            		    con.close();
            		    
            		   } catch (SQLException e) {
            		    e.printStackTrace();
            		   }    	   
               }






	}
	
	
	
      public User findUserById(int id) throws Exception
      {

Connection con = null;
User u=null;
	  try {
		  con=new ConnectionFactory().getConnection();
      PreparedStatement preparedStatement = con.prepareStatement("select * from users where id=?");
preparedStatement.setInt(1,id);
       ResultSet rs=preparedStatement.executeQuery();
       String name="";      

 if(rs.next())
        {
          u=new User();
          
         name=rs.getString("name");
        u.setName(name);
        u.setId(id);
         System.out.println("encotrado user"+name);
        }
else
{
  throw new Exception("Usuario no encontrado "+ id);
}


	  }
	  catch (SQLException e) {
	      e.printStackTrace();
	  }
	  catch (Exception e) {
	      e.printStackTrace();
             throw e;
	  }
	  finally {
	      try {
	    con.close();
	   } catch (SQLException e) {
	    e.printStackTrace();
	   }
	  }


         

    return  u;
}

	@Override
	public boolean hasUserSkill(String name,User u) {
		// TODO Auto-generated method stub
	    boolean found=false;
		
	    Connection con = null;
	     	  try {
	    		  con=new ConnectionFactory().getConnection();
	    		 Skill skill= skillServiceEdge.findSkillByName(name);
	    		if (skill==null)
	    			 return false;
	          PreparedStatement preparedStatement = con.prepareStatement("SELECT id_user,id_skill from users_skills where id_user=? and id_skill=?");
	    preparedStatement.setInt(1,u.getId());
	    preparedStatement.setInt(2, skill.getId());
	    System.out.println("id user"+u.getId() +"skill name"+name);
	           ResultSet rs=preparedStatement.executeQuery();
	         System.out.println("USER SKILLS");

	     if(rs.next())
	            {
             System.out.println("USER SKILLS FOUND!");

	               return true;
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

	    
      return found;
	}

	@Override
	public User UserFindUserByName(String name) throws Exception {
		// TODO Auto-generated method stub
		
		Connection con=null;
		User u=null;
		try
		{
		con=new ConnectionFactory().getConnection();
		
		PreparedStatement prs=con.prepareStatement("SELECT * from users where name like ?");
		prs.setString(1, "%"+name+"%");
		ResultSet res= prs.executeQuery();
		if (!res.next())
		    throw new Exception("User with name "+name +"has not found");
		else
		{
			u=new User();
			u.setName(res.getString("name"));
			u.setId(res.getInt("id"));
		}
		
		
		}
		catch(Exception e)
		{
			
		  e.printStackTrace();
		  throw e;
		}
		return u;
	}

	@Override
	public List<Skill> getAllSkillsByUser(User u) throws Exception {
		// TODO Auto-generated method stub
		
		Connection con=null;
		List<Skill>lis=null;
		try
		{
			
			con=new ConnectionFactory().getConnection();
			PreparedStatement stm=con.prepareStatement("Select * from users_skills where id_user = ?");
			stm.setInt(1, u.getId());
			ResultSet rs= stm.executeQuery();
			while(rs.next())
			{
				
			  PreparedStatement st2=con.prepareStatement("Select name from skills where id=?");
			  st2.setInt(1, rs.getInt("id_skill"));
				
			  ResultSet rs2=st2.executeQuery();
			  if(rs2.next())
			  {
				  if (lis==null)lis=new ArrayList<Skill>();
				  Skill s=new Skill();
				  s.setId(rs.getInt("id_skill"));
				  s.setName(rs2.getString("name"));
				  lis.add(s);
			  }
			  
			  
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			try
			{
				con.close();
			}
			catch(Exception e)
			{
				e.printStackTrace();
				throw e;
			}
		}
		
		
		
		
		return lis;
	}
	
	
}
