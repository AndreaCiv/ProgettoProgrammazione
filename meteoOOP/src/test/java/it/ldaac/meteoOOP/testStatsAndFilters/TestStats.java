package it.ldaac.meteoOOP.testStatsAndFilters;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Vector;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.ldaac.meteoOOP.models.Citta;
import it.ldaac.meteoOOP.models.DatoMeteo;
import it.ldaac.meteoOOP.statsAndFilters.Stats;

class TestStats {
	
	private Stats statistiche;
	
	private Citta citta1;
	private Citta citta2;

	@BeforeEach
	void setUp() throws Exception {
		statistiche = new Stats();
		
		citta1 = new Citta("citta1", 1, 10, 10);
		citta2 = new Citta("citta2", 2, 20, 20);
		
		citta1.aggiungiDatoMeteo(new DatoMeteo(5, 5, 5, System.currentTimeMillis()));
		citta1.aggiungiDatoMeteo(new DatoMeteo(15, 15, 15, System.currentTimeMillis()));
		
		citta2.aggiungiDatoMeteo(new DatoMeteo(20, 20, 20, System.currentTimeMillis()));
		citta2.aggiungiDatoMeteo(new DatoMeteo(20, 20, 20, System.currentTimeMillis()));
		
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testCittaPiuCaldaEVentosa() {
		Vector<Citta> daAnalizzare = new Vector<Citta>();
		daAnalizzare.add(citta1);
		daAnalizzare.add(citta2);
		
		assertEquals(statistiche.maxTempMedia(daAnalizzare), citta2);
		assertEquals(statistiche.maxTempPercMedia(daAnalizzare), citta2);
		assertEquals(statistiche.maxVelVentoMedia(daAnalizzare), citta2);
	}
	
	@Test
	void testCittaPiuVariabile() {
		Vector<Citta> daAnalizzare = new Vector<Citta>();
		daAnalizzare.add(citta1);
		daAnalizzare.add(citta2);
		
		assertEquals(statistiche.maxVarTemp(daAnalizzare), citta1);
		assertEquals(statistiche.maxVarTempPerc(daAnalizzare), citta1);
		assertEquals(statistiche.maxVarVelVento(daAnalizzare), citta1);
	}

}
