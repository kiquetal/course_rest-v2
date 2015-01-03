package py.com.edge.conf;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.context.MessageContext;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import org.springframework.ws.soap.saaj.SaajSoapMessage;
import org.springframework.ws.soap.server.endpoint.annotation.SoapAction;

import ch.qos.logback.core.pattern.util.RegularEscapeUtil;
import py.com.edge.exceptions.InvalidRequest;
import py.com.edge.models.Skill;
import py.com.edge.models.User;
import py.com.edge.service.UsersServicesEdge;
import py.com.edge.soap.GetAllUserResponse;
import py.com.edge.soap.GetUserAndSkillRequest;
import py.com.edge.soap.GetUserAndSkillResponse;
import py.com.edge.soap.ResponseUserSkill;
import py.com.edge.soap.SaveNewSkillRequest;
import py.com.edge.soap.SaveNewSkillResponse;
import py.com.edge.soap.SkillsUser;
import py.com.edge.soap.UsersArray;
import py.com.edge.soap.GetAllUserRequest;
@Endpoint
public class UsersEndpoint {
	private static final String NAMESPACE_URI = "http://edge.com.py/soap";

	  @Autowired
	  private UsersServicesEdge usersServicesEdge;
	
	 @PayloadRoot(namespace= NAMESPACE_URI,localPart="getAllUserRequest")
     @ResponsePayload
     public GetAllUserResponse getAllUsers(@RequestPayload GetAllUserRequest request)
      {
           GetAllUserResponse g=new GetAllUserResponse();
           System.out.println("request"+request.getName());
           List<UsersArray> original=g.getUsers();
                   
          List<User>l= usersServicesEdge.getAllUsers();
          for (User u : l)
         {
            UsersArray n=new UsersArray();
              n.setId(u.getId());
              n.setName(u.getName());
            original.add(n);

          }

          
        return g;

        }
     
	 @PayloadRoot(namespace= NAMESPACE_URI,localPart="saveNewSkillRequest")
     @ResponsePayload
     public SaveNewSkillResponse saveNewUserSkills(@RequestPayload SaveNewSkillRequest request)
     {
                SaveNewSkillResponse res=new SaveNewSkillResponse();
try
{
             int id=request.getIdUser();
             String skillName=request.getSkill().getSkillObject().getName();
            System.out.println("skill es"+ skillName);             
 User u=   usersServicesEdge.findUserById(id);
              
                usersServicesEdge.saveNewUserSkills(u,skillName);
                res.setStatus("OK");
                res.setResponse("User has new skill");
}
catch (Exception e)
{
 res.setResponse(e.getMessage());
 res.setStatus("ERROR");
}

		 return res;
     }
	 
	 
	 @PayloadRoot(namespace= NAMESPACE_URI,localPart="getUserAndSkillRequest")
     @ResponsePayload
     public GetUserAndSkillResponse getUserAndSkills(@RequestPayload GetUserAndSkillRequest request,MessageContext msg) throws InvalidRequest
     {
		 GetUserAndSkillResponse gs=new GetUserAndSkillResponse();
		 ResponseUserSkill res=new ResponseUserSkill();
		 System.out.println("Obtengo del request"+request.getNameUser() + "as "+ request.getIdUser());
		 ResponseUserSkill.Response responsEuser=new ResponseUserSkill.Response();
		 System.out.println("id parseado"+(request.getIdUser()).getClass());

		 List<SkillsUser>listSkillByUser=responsEuser.getSkillsUser();
		 String name=request.getNameUser();
		 System.out.println("name" + name);
		 if ((request.getIdUser().trim().length()<1  && name.trim().length()<1))
		 {
		     res.setStatus("ERROR");
		     
		 }
		 else
		 {
			 System.out.println("es el id");

			 int id=0;
			    if (request.getIdUser().trim().length()>0)
			    	 id=Integer.parseInt(request.getIdUser());
			    System.out.println("seteamos el id"+id);
			 try
			 {
				 User u=null;
				 if (id>0)
			       u=usersServicesEdge.findUserById(id);
				 else
					 if (name!=null)
						u= usersServicesEdge.UserFindUserByName(name);
				 
				 System.out.println("name es"+name);
				 System.out.println("El user es"+u.getName());
				responsEuser.setUserName(u.getName());
				 List<Skill> s=usersServicesEdge.getAllSkillsByUser(u);
				
				for(Skill skill:s)
				{
					SkillsUser sk=new SkillsUser();
				     sk.setSkill(skill.getName());
					listSkillByUser.add(sk);
					
				}
				
				res.setResponse(responsEuser);
				res.setStatus("OK");
				 
			 }
			 catch(Exception e)
			 {
				 e.printStackTrace();
				 res.setStatus("ERROR"+e.getMessage());
				 throw new InvalidRequest(e.getMessage());
			 }
			 
			 
		 }
		 gs.setResponse(res);
		 return gs;
		 
		 
     }
}
