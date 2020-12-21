/**
 * 
 */
package it.ldaac.meteoOOP.exceptions;

/**
 * @author andreacivitarese
 *
 */
public class DateNotValidException extends Exception {

	public DateNotValidException() {
		super("Errore nell'inserimento delle date, usa il formato [dd/mm/yy]");
	}
}
