package server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.Banner;

@SpringBootApplication
public class LibraryServer {

	public static void main(String[] args) throws Exception {
		SpringApplication server = new SpringApplication(LibraryServer.class);
		// Hide the banner mode
		server.setBannerMode(Banner.Mode.OFF);
		server.run(args);
	}
}
