package it.ldaac.meteoOOP.exceptions;

/**
 * 
 * Eccezione nel caso il tipo di statistiche inserite dall'utente non sia valido
 * 
 * @author andreacivitarese, lucadambrosio
 *
 */
public class StatsNotValidException extends Exception {

	/**
	 * Costruttore per StatsNotValidException
	 */
	public StatsNotValidException() {
		super("Il campo stats non è valido, utilizzare [all/temp/tempPerc/vento]");
	}
	
}
