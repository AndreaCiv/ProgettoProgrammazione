package it.ldaac.meteoOOP.testService;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.ldaac.meteoOOP.exceptions.IdNotFoundException;
import it.ldaac.meteoOOP.models.Richiesta;
import it.ldaac.meteoOOP.service.MeteoService;

class TestMeteoService {

	@BeforeEach
	void setUp() throws Exception {

	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testIdNotFoundException() {
		MeteoService service = new MeteoService();
		assertThrows(IdNotFoundException.class, ()->service.generaStats(1, "all", "31/12/20", null, 100, 50));
	}
}
