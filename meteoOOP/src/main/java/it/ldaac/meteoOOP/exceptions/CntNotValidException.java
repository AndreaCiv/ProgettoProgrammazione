/**
 * 
 */
package it.ldaac.meteoOOP.exceptions;

/**
 * @author andreacivitarese
 *
 */
public class CntNotValidException extends Exception {

	public CntNotValidException() {
		super("cnt non valido, inserisci un numero tra 1 e 50");
	}
}
