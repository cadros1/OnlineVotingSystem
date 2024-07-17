package top.cadros.onlinevotingsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class OnlinevotingsystemApplication {
	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(OnlinevotingsystemApplication.class, args);
	}
}
