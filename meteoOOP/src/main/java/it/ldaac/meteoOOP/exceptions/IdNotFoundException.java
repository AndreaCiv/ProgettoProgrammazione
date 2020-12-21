/**
 * 
 */
package it.ldaac.meteoOOP.exceptions;

/**
 * @author andreacivitarese
 *
 */
public class IdNotFoundException extends Exception {

	public IdNotFoundException() {
		super("id della ricerca non trovato");
	}
}
