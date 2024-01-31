package Desafio_Tabela_Fipe_Application;

import Desafio_Tabela_Fipe_Application.main.Main;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
public void run (String... args) throws Exception {
	Main main = new Main();
	main.showMenu();
}

}
