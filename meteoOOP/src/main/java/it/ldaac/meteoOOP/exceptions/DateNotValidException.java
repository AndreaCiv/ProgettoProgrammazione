package it.ldaac.meteoOOP.exceptions;

/**
 * 
 * Eccezione nel caso la data inserita dall'utente non sia nel formato esatto
 * 
 * @author andreacivitarese, lucadambrosio
 *
 */
public class DateNotValidException extends Exception {

	/**
	 * Costruttore per DateNotValidException
	 */
	public DateNotValidException() {
		super("Errore nell'inserimento delle date, usa il formato [dd/mm/yy]");
	}
}
