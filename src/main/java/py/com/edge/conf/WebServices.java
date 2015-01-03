package py.com.edge.conf;

import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

@EnableWs
@Configuration
public class WebServices extends WsConfigurerAdapter{

	@Bean
	public ServletRegistrationBean dispatcherServlet(ApplicationContext applicationContext)
	{
		MessageDispatcherServlet servlet=new MessageDispatcherServlet();
		servlet.setApplicationContext(applicationContext);
		servlet.setTransformWsdlLocations(true);
		servlet.setTransformSchemaLocations(true);
		return new ServletRegistrationBean(servlet,"/ws/soap/*");
	}
	@Bean(name="users")
	public DefaultWsdl11Definition getUsersWsdl(XsdSchema userSchema)
	{
		
		DefaultWsdl11Definition wsdl1=new DefaultWsdl11Definition();
		wsdl1.setPortTypeName("UsersWsdl");
		wsdl1.setLocationUri("/ws/soap");
		wsdl1.setTargetNamespace("http://edge.com.py/soap");
		wsdl1.setSchema(userSchema);
		return wsdl1;
		
	}
	
	@Bean(name="skills")
	public DefaultWsdl11Definition getSkillsWsdl(XsdSchema skillsSchema)
	{
		
		DefaultWsdl11Definition wsdl1=new DefaultWsdl11Definition();
		wsdl1.setPortTypeName("UsersWsdl");
		wsdl1.setLocationUri("/ws/soap/skills");
		wsdl1.setTargetNamespace("http://edge.com.py/soap");
		wsdl1.setSchema(skillsSchema);
		return wsdl1;
		
	}
	
	@Bean
	public XsdSchema userSchema()
	{
		return new SimpleXsdSchema(new ClassPathResource("users.xsd"));
	}
	@Bean
        public XsdSchema skillsSchema()
       {
         return new SimpleXsdSchema(new ClassPathResource("skills.xsd"));
       }
}
