package py.com.edge.conf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = "py.com.edge")

@EnableAutoConfiguration
public class Application {

	public static void main (String args[])
	{
		
		
		SpringApplication.run(Application.class);
	}
	
}
