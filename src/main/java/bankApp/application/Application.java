package bankApp.application;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import database.SessionManager;

@ComponentScan(basePackages = {"web", "bankApp.repositories", "model", "security", "communication"})
@SpringBootApplication
public class Application {
	public static ExecutorService executorService;

	public static void main(String[] args) {
		SessionManager.init();
		executorService = Executors.newFixedThreadPool(10);
		SpringApplication.run(Application.class, args);
	}

}