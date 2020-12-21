/**
 * 
 */
package it.ldaac.meteoOOP.exceptions;

/**
 * @author andreacivitarese
 *
 */
public class BadRequestException extends Exception {

	public BadRequestException() {
		super("La richiesta all'API di OpenWheather non è andata a buon fine");
	}
	

}
