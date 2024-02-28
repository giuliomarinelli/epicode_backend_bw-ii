package it.epicode.backend.bwii.epic_energy_services;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootTest
class EpicEnergyServicesApplicationTests {


	static AnnotationConfigApplicationContext ctx;

	@BeforeAll
	static void openContext(){
		ctx = new AnnotationConfigApplicationContext();
	}



	@AfterAll
	static void closeContext(){
		ctx.close();
	}
}
