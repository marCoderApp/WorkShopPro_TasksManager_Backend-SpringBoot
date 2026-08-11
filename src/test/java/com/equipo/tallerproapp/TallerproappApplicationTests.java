package com.equipo.tallerproapp;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TallerproappApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void printJvmTimeZone(){
		System.out.println(java.util.TimeZone.getDefault().getID());
	}
}
