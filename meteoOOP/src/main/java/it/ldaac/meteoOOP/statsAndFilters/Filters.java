package it.ldaac.meteoOOP.statsAndFilters;

import java.util.Date;
import java.util.Vector;

import it.ldaac.meteoOOP.models.Citta;
import it.ldaac.meteoOOP.models.DatoMeteo;

public class Filters {
	
	public Vector<DatoMeteo> filtraDatiMeteo(Vector<DatoMeteo> daFiltrare, Date inizio)
	{
		Vector<DatoMeteo> datiFiltrati = new Vector<DatoMeteo>();
		
		for (DatoMeteo d : daFiltrare)
			if (d.confrontaData(inizio))
				datiFiltrati.add(d);
		
		return datiFiltrati;
	}
	
	public Vector<DatoMeteo> filtraDatiMeteo(Vector<DatoMeteo> daFiltrare, Date inizio, Date fine)
	{
		Vector<DatoMeteo> datiFiltrati = new Vector<DatoMeteo>();
		for(DatoMeteo d : daFiltrare)
			if (d.confrontaData(inizio) && !d.confrontaData(fine))
				datiFiltrati.add(d);
		return datiFiltrati;
		
	}
	
	public Vector<Citta> filtraCittaInRaggio(Vector<Citta> daFiltrare, int raggio)
	{
		Vector<Citta> cittaFiltrate = new Vector<Citta>();
		for(Citta c : daFiltrare)
			if(c.calcolaDistanza(daFiltrare.elementAt(0)) < (double) raggio)
				cittaFiltrate.add(c);
		return cittaFiltrate;
	}
	
	public Vector<Citta> filtraCittaInNumero(Vector<Citta> daFiltrare, int numero)
	{
		Vector<Citta> cittaFiltrate = new Vector<Citta>();
		for(int i=0; i<numero && i<daFiltrare.size(); i++)
			cittaFiltrate.add(daFiltrare.elementAt(i));
		return cittaFiltrate;
	}
	
}
