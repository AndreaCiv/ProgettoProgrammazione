package it.ldaac.meteoOOP.testStatsAndFilters;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;
import java.util.Vector;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.ldaac.meteoOOP.models.Citta;
import it.ldaac.meteoOOP.models.DatoMeteo;
import it.ldaac.meteoOOP.statsAndFilters.Filters;

class TestFilters {

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testFiltroDistanza() {
		Filters filtri = new Filters();
		
		Citta roma = new Citta("Roma", 1, 41.89, 12.48);
		Citta milano = new Citta("Milano", 2, 45.46, 9.19);
		Citta ancona = new Citta("Ancona", 3, 40.62, 13.52);
		Citta bologna = new Citta("Bologna", 4, 44.43, 11.35);
		
		Vector<Citta> daFiltrare = new Vector<Citta>();
		daFiltrare.add(roma);
		daFiltrare.add(milano);
		daFiltrare.add(ancona);
		daFiltrare.add(bologna);
		
		Vector<Citta> filtrato = new Vector<Citta>();
		filtrato.add(roma);
		filtrato.add(ancona);
		filtrato.add(bologna);
		
		for(int i = 0; i<filtri.filtraCittaInRaggio(daFiltrare, 350).size(); i++)
		{
			assertEquals(filtri.filtraCittaInRaggio(daFiltrare, 350).elementAt(i), filtrato.elementAt(i));
		}
	}
	
	@Test
	void testFiltroData() {
		
		Filters filtri = new Filters();
		Vector<DatoMeteo> daFiltrare = new Vector<DatoMeteo>();
		Vector<DatoMeteo> filtratoInizio = new Vector<DatoMeteo>();
		Vector<DatoMeteo> filtratoInizioFine = new Vector<DatoMeteo>();
		
		DatoMeteo dato1 = new DatoMeteo(10, 10, 30, System.currentTimeMillis());
		DatoMeteo dato2 = new DatoMeteo(20, 20, 10, System.currentTimeMillis()-3600000L);
		DatoMeteo dato3 = new DatoMeteo(30, 30, 0, System.currentTimeMillis()-7200000L);
		
		daFiltrare.add(dato1);
		daFiltrare.add(dato2);
		daFiltrare.add(dato3);
		
		filtratoInizio.add(dato1);
		filtratoInizio.add(dato2);
		
		filtratoInizioFine.add(dato2);
		
		for(int i = 0; i<filtri.filtraDatiMeteo(daFiltrare, new Date(System.currentTimeMillis()-5400000L)).size(); i++)
		{
			assertEquals(filtri.filtraDatiMeteo(daFiltrare, new Date(System.currentTimeMillis()-5400000L)).elementAt(i), filtratoInizio.elementAt(i));
		}
		
		assertEquals(filtri.filtraDatiMeteo(daFiltrare, new Date(System.currentTimeMillis()-5400000L), new Date(System.currentTimeMillis()-1800000L)).elementAt(0), filtratoInizioFine.elementAt(0));
	}

}
