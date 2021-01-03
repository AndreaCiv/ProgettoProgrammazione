package it.ldaac.meteoOOP.utilities;

import java.util.TimerTask;

import it.ldaac.meteoOOP.models.Ricerca;

/**
 * 
 * Extends TimerTask
 * Task personazlizzata da assegnare ad un timer per aggiornare e aggiungere dati meteo alle ricerche
 * 
 * @author andreacivitarese, lucadambrosio
 */
public class AggiornaDatiMeteoTask extends TimerTask {
	
	/**
	 * Ricerca alla quale vogliamo aggiungere dati
	 */
	private Ricerca ricerca;
	
	/**
	 * DataParser dal quale otteniamo i dati da aggiungere
	 */
	private DataParser dataParser;
	
	/**
	 * Tempo in formato Unix fi fine della task
	 */
	private long fine;
	
	/**
	 * Costruttore per AggiornaDatiMeteoTask
	 * 
	 * @param ricerca Ricerca alla quale vogliamo aggiungere dati
	 * @param durata Durata della ricerca e aggiunta dei nuvi dati in millisecondi
	 * @param dataParser DataParser dal quale otteniamo i dati da aggiungere alla ricerca
	 */
	public AggiornaDatiMeteoTask(Ricerca ricerca, long durata, DataParser dataParser)
	{
		this.ricerca = ricerca;
		this.fine = System.currentTimeMillis()+durata;
		this.dataParser = dataParser;
	}
	
	/**
	 * Metodo che viene eseguito dal timer par aggiornare e aggiungere i dati alla ricerca
	 */
	@Override
	public void run() {
		
		if(System.currentTimeMillis() < this.fine)
		{
			this.ricerca.aggiungiDatiMeteo(this.dataParser);
			System.out.println("Ricerca " + this.ricerca.getId() + ": dati meteo aggiunti");
		}
		else
			this.cancel();
	}

}
