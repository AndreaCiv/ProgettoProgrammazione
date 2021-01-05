package it.ldaac.meteoOOP.testModels;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.ldaac.meteoOOP.models.DatoMeteo;

class TestDatoMeteo {

	private DatoMeteo dato1;
	
	@BeforeEach
	void setUp() throws Exception {
		dato1 = new DatoMeteo(20, 18, 40, System.currentTimeMillis());
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testConfrontaData() {
		assertEquals(dato1.confrontaData(new Date(System.currentTimeMillis()-20)), true);
	}
	
	@Test
	void testToJSONObject() {
		assertEquals(20D, dato1.toJSONObject().get("temperatura"));
		assertEquals(18D, dato1.toJSONObject().get("temperatura_percepita"));
		assertEquals(40D, dato1.toJSONObject().get("velocita_vento"));
	}
}
