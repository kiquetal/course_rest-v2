package py.com.edge.conf;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import py.com.edge.models.Skill;
import py.com.edge.service.SkillsServiceEdge;
import py.com.edge.soap.skills.GetAllSkillsRequest;
import py.com.edge.soap.skills.GetAllSkillsResponse;
import py.com.edge.soap.skills.SkillItem;



@Endpoint
public class SkillsEndpoint {
	private static final String NAMESPACE_URI = "http://edge.com.py/soap/skills";

     @Autowired
     SkillsServiceEdge skillServices;
	@PayloadRoot(namespace= NAMESPACE_URI,localPart="getAllSkillsRequest")
     @ResponsePayload
     public GetAllSkillsResponse getAllSkills(@RequestPayload GetAllSkillsRequest request)
     {
		System.out.println("obtener todos los skills");
		 GetAllSkillsResponse al=new GetAllSkillsResponse();
		 List<SkillItem> skill=null;
		 try {
			 GetAllSkillsResponse.Skills SK=new GetAllSkillsResponse.Skills();
			 
			 skill=SK.getSkill();
			List<Skill>l=skillServices.getAllSkills();
			System.out.println("todos los skills del service");
			for(Skill skillObject:l)
			{
             SkillItem i=new SkillItem();
             i.setIdSkill(skillObject.getId());
             i.setSkillName(skillObject.getName());
             skill.add(i);
			}
			al.setSkills(SK);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		 
		 
		 return al;
		 
     }
	
}
