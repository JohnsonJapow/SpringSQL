package backend.javaproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
public class JavaprojectApplication {

	@GetMapping("/message")
	public String message(){
		return "Congrats, your app is deployed completely";
	}
	public static void main(String[] args) {
		SpringApplication.run(JavaprojectApplication.class, args);
	}
	

}
