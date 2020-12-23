package it.ldaac.meteoOOP.exceptions;

/**
 * 
 * Eccezione nel caso di errata richiesta all'API di OpenWeather
 * 
 * @author andreacivitarese, lucadambrosio
 */
public class BadRequestException extends Exception {

	/**
	 * Costruttore per BarRequestException
	 */
	public BadRequestException() {
		super("La richiesta all'API di OpenWheather non è andata a buon fine");
	}
	

}
