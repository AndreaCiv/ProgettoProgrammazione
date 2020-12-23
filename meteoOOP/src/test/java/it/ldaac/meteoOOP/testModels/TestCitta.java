package it.ldaac.meteoOOP.testModels;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.ldaac.meteoOOP.models.Citta;

class TestCitta {

	private Citta citta1;
	private Citta citta2;
	
	@BeforeEach
	void setUp() throws Exception {
		citta1 = new Citta("Roma", 1, 41.89, 12.48);
		citta2 = new Citta("Milano", 2, 45.46, 9.19);
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void test() {
		assertEquals(477.58, citta1.calcolaDistanza(citta2), 2);
	}

}
