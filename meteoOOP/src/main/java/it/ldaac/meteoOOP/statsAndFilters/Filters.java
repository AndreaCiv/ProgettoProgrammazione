package it.ldaac.meteoOOP.statsAndFilters;

import java.util.Date;
import java.util.Vector;

import it.ldaac.meteoOOP.models.Citta;
import it.ldaac.meteoOOP.models.DatoMeteo;

public class Filters {
	
	public Vector<DatoMeteo> filtraDatiMeteo(Vector<DatoMeteo> daFiltrare, long giorni)
	{
		Vector<DatoMeteo> risposta = new Vector<DatoMeteo>();
		long millis = System.currentTimeMillis() - giorni*24L*3600L*100L;
		Date data = new Date(millis);
		for (int i = 0; i<daFiltrare.size(); i++)
		{
			if (daFiltrare.elementAt(i).confrontaData(data))
				risposta.add(daFiltrare.elementAt(i));
		}
		return risposta;
	}
	
	public Vector<Citta> filtraCitta(Vector<Citta> daFiltrare, int raggio)
	{
		Vector<Citta> cittaFiltrate = new Vector<Citta>();
		for(Citta c : daFiltrare)
			if(c.calcolaDistanza(daFiltrare.elementAt(0)) < raggio)
				cittaFiltrate.add(c);
		return cittaFiltrate;
	}
}
