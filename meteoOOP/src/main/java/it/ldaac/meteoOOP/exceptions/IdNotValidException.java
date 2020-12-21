/**
 * 
 */
package it.ldaac.meteoOOP.exceptions;

/**
 * @author andreacivitarese
 *
 */
public class IdNotValidException extends Exception {

	public IdNotValidException() {
		super("id della ricerca non trovato");
	}
}
