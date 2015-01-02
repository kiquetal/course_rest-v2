package py.com.edge.conf;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import py.com.edge.models.User;
import py.com.edge.service.UsersServicesEdge;
import py.com.edge.soap.GetAllUserResponse;
import py.com.edge.soap.SaveNewSkillRequest;
import py.com.edge.soap.SaveNewSkillResponse;
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
                 System.out.println("id"+request.getIdUser()+"skill"+request.getSkill().getSkillObject().getName());
		 SaveNewSkillResponse res=new SaveNewSkillResponse();
		 res.setStatus("OK");
		 return res;
     }
	 
}
