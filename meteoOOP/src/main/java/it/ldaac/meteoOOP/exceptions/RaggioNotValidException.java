package it.ldaac.meteoOOP.exceptions;

/**
 * 
 * Eccezione nel caso il raggio inserito dall'utente sia minore o uguale a zero
 * 
 * @author andreacivitarese, lucadambrosio
 *
 */
public class RaggioNotValidException extends Exception {

	/**
	 * Costruttore per RaggioNotValidException
	 */
	public RaggioNotValidException() {
		super("Raggio non valido, inserisci un raggio maggiore di 0");
	}
	
}
