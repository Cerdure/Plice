package com.project.team.plice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

@SpringBootApplication
public class PliceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PliceApplication.class, args);
	}
}
