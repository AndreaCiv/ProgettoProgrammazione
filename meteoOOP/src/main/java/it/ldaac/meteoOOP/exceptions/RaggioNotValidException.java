/**
 * 
 */
package it.ldaac.meteoOOP.exceptions;

/**
 * @author andreacivitarese
 *
 */
public class RaggioNotValidException extends Exception {

	public RaggioNotValidException() {
		super("Raggio non valido, inserisci un raggio maggiore di 0");
	}
	
}
