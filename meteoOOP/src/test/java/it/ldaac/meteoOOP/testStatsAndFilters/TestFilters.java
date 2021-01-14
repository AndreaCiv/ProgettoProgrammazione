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
	
	private Filters filtri;
	
	private Citta roma;
	private Citta milano;
	private Citta ancona;
	private Citta bologna;
	
	private DatoMeteo dato1;
	private DatoMeteo dato2;
	private DatoMeteo dato3;
	
	@BeforeEach
	void setUp() throws Exception {
		filtri = new Filters();
		
		roma = new Citta("Roma", 1, 41.89, 12.48);
		milano = new Citta("Milano", 2, 45.46, 9.19);
		ancona = new Citta("Ancona", 3, 40.62, 13.52);
		bologna = new Citta("Bologna", 4, 44.43, 11.35);
		
		dato1 = new DatoMeteo(10, 10, 30, System.currentTimeMillis());
		dato2 = new DatoMeteo(20, 20, 10, System.currentTimeMillis()-3600000L);
		dato3 = new DatoMeteo(30, 30, 0, System.currentTimeMillis()-7200000L);
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testFiltroDistanza() {
		
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
		
		Vector<DatoMeteo> daFiltrare = new Vector<DatoMeteo>();
		daFiltrare.add(dato1);
		daFiltrare.add(dato2);
		daFiltrare.add(dato3);
		
		Vector<DatoMeteo> filtratoInizio = new Vector<DatoMeteo>();
		filtratoInizio.add(dato1);
		filtratoInizio.add(dato2);
		
		Vector<DatoMeteo> filtratoInizioFine = new Vector<DatoMeteo>();
		filtratoInizioFine.add(dato2);
		
		for(int i = 0; i<filtri.filtraDatiMeteo(daFiltrare, new Date(System.currentTimeMillis()-5400000L)).size(); i++)
		{
			assertEquals(filtri.filtraDatiMeteo(daFiltrare, new Date(System.currentTimeMillis()-5400000L)).elementAt(i), filtratoInizio.elementAt(i));
		}
		
		assertEquals(filtri.filtraDatiMeteo(daFiltrare, new Date(System.currentTimeMillis()-5400000L), new Date(System.currentTimeMillis()-1800000L)).elementAt(0), filtratoInizioFine.elementAt(0));
	}

}
