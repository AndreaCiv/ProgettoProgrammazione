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
	private long fine;
	
	public AggiornaDatiMeteoTask(Ricerca ricerca, long durata)
	{
		this.ricerca = ricerca;
		this.fine = System.currentTimeMillis()+durata;
	}
	@Override
	public void run() {
		
		if(System.currentTimeMillis() < this.fine)
			this.ricerca.aggiornaDatiMeteo();
		else
			this.cancel();
	}

}
