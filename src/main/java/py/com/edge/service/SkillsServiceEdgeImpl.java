package py.com.edge.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import py.com.edge.models.Skill;
import py.com.edge.utils.ConnectionFactory;

@Service
public class SkillsServiceEdgeImpl implements SkillsServiceEdge {

	
	
	
	
	@Override
	public List<Skill> getAllSkills() throws Exception {
		// TODO Auto-generated method stub
		Connection con=null;
		List<Skill>l=null;
		try
		{
			 con=new ConnectionFactory().getConnection();
			  Statement stmt = con.createStatement();
		      ResultSet rs = stmt.executeQuery("select * from skills");
		      while(rs.next())
		      {
		    	  if(l==null)l=new ArrayList<Skill>();
		    	  Skill s=new Skill();
		    	  s.setId(rs.getInt("id"));
		    	  s.setName(rs.getString("name"));
		    	  l.add(s);
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
		return l;
	}

	@Override
	public int getSkillOrInsertNew(String skillName) throws Exception {
		// TODO Auto-generated method stub

		Connection con=null;
		try
		{
			con=new ConnectionFactory().getConnection();
			
			 Skill s=this.findSkillByName(skillName);
			 if (s!=null)
			 	  return s.getId();
			  else
			  {
				  Statement stmt5 = con.createStatement();
				ResultSet rs5=stmt5.executeQuery("Select MAX(id) as maxId from skills");
				int maxId=0;
				  if(rs5.next())
					  maxId= rs5.getInt("maxId");
				  
				  System.out.println("maximo Id"+maxId);
			       java.sql.PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO skills(name,id) VALUES (?,?)");
			        preparedStatement.setString(1,skillName);
			        preparedStatement.setInt(2, maxId+1);
			        int rss=preparedStatement.executeUpdate();
			        System.out.println("insertamos ok!!!");
			        Skill s2=this.findSkillByName(skillName);
					 if (s2!=null)
					 	  return s2.getId();
			        
			  }
			
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		
		
		
   return -1;		
		
	}

	@Override
	public Skill findSkillByName(String skillName) throws Exception {
		// TODO Auto-generated method stub
		Connection con=null;
		Skill s=null;
		try
		{
			con=new ConnectionFactory().getConnection();
		 java.sql.PreparedStatement stmt = con.prepareStatement("SELECT * FROM skills where name=?");
		  stmt.setString(1, skillName);
	    ResultSet rs=stmt.executeQuery();
		  if(rs.next()){
			  s=new Skill();
			  s.setId(rs.getInt("id"));
			  s.setName(rs.getString("name"));
			  
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
			}
		}
		return s;
	}

	@Override
	public Skill getSkillById(int id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
