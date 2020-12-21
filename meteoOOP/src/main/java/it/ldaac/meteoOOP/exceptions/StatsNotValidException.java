/**
 * 
 */
package it.ldaac.meteoOOP.exceptions;

/**
 * @author andreacivitarese
 *
 */
public class StatsNotValidException extends Exception {

	public StatsNotValidException() {
		super("Il campo stats non è valido, utilizzare [all/temp/tempPerc/vento]");
	}
	
}
