/**
 * 
 */
package it.ldaac.meteoOOP.utilities;

import java.util.TimerTask;

import it.ldaac.meteoOOP.models.Ricerca;

/**
 * @author andreacivitarese
 *
 */
public class AggiornaDatiMeteoTask extends TimerTask {
	
	private Ricerca ricerca;
	private DataParser dataParser;
	private long fine;
	
	public AggiornaDatiMeteoTask(Ricerca ricerca, long durata, DataParser dataParser)
	{
		this.ricerca = ricerca;
		this.fine = System.currentTimeMillis()+durata;
		this.dataParser = dataParser;
	}
	@Override
	public void run() {
		
		if(System.currentTimeMillis() < this.fine)
		{
			this.ricerca.aggiornaDatiMeteo(this.dataParser);
			System.out.println("Dati aggiornati");
		}
		else
			this.cancel();
	}

}
