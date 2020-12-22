package it.ldaac.meteoOOP.statsAndFilters;

import java.util.Date;
import java.util.Vector;

import it.ldaac.meteoOOP.models.Citta;
import it.ldaac.meteoOOP.models.DatoMeteo;

/**
 * 
 * Classe che implementa funzioni per filtrare città e dati meteo
 * 
 * @author andreacivitarese, lucadambrosio
 */
public class Filters {
	
	/**
	 * Filtra i dati meteo a partire da una data
	 * 
	 * @param daFiltrare Vettore contenente i dati meteo da filtrare
	 * @param inizio Data dalla quale prendere in considerazione i dati meteo
	 * @return Vettore contenente i dati meteo filtrati
	 */
	public Vector<DatoMeteo> filtraDatiMeteo(Vector<DatoMeteo> daFiltrare, Date inizio)
	{
		Vector<DatoMeteo> datiFiltrati = new Vector<DatoMeteo>();
		
		for (DatoMeteo d : daFiltrare)
			if (d.confrontaData(inizio))
				datiFiltrati.add(d);
		
		return datiFiltrati;
	}
	
	/**
	 * Filtra i dati meteo e restituisce quelli compresi tra le due date
	 * 
	 * @param daFiltrare Vettore contenente i dati meteo da filtrare
	 * @param inizio Data dalla quale prendere in considerazione i dati meteo
	 * @param fine Data fino alla quale prendere in considerazione i dati meteo
	 * @return Vettore contenente i dati meteo filtrati
	 */
	public Vector<DatoMeteo> filtraDatiMeteo(Vector<DatoMeteo> daFiltrare, Date inizio, Date fine)
	{
		Vector<DatoMeteo> datiFiltrati = new Vector<DatoMeteo>();
		for(DatoMeteo d : daFiltrare)
			if (d.confrontaData(inizio) && !d.confrontaData(fine))
				datiFiltrati.add(d);
		return datiFiltrati;
		
	}
	
	/**
	 * Filtra le città in base alla loro distanza da quella centrale
	 * 
	 * @param daFiltrare Vettore contenente le città da filtrare
	 * @param raggio Raggio entro il quale devono trovarsi le città
	 * @return Vettore contenente le città filtrate
	 */
	public Vector<Citta> filtraCittaInRaggio(Vector<Citta> daFiltrare, int raggio)
	{		
		Vector<Citta> cittaFiltrate = new Vector<Citta>();
		for(Citta c : daFiltrare)
			if(c.calcolaDistanza(daFiltrare.elementAt(0)) < (double) raggio)
				cittaFiltrate.add(c);
		return cittaFiltrate;
	}
	
	/**
	 * Filtra le città in base al loro numero
	 * 
	 * @param daFiltrare Vettore contenente le città da filtrare
	 * @param numero Numero di città che si vuole prendere
	 * @return Vettore contenente le città filtrate
	 */
	public Vector<Citta> filtraCittaInNumero(Vector<Citta> daFiltrare, int numero)
	{
		Vector<Citta> cittaFiltrate = new Vector<Citta>();
		for(int i=0; i<numero && i<daFiltrare.size(); i++)
			cittaFiltrate.add(daFiltrare.elementAt(i));
		return cittaFiltrate;
	}
	
}
