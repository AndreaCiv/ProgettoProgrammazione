package it.ldaac.meteoOOP.exceptions;

/**
 * 
 * Eccezione nel caso l'id inserito dall'utente non sia presente nel database
 * 
 * @author andreacivitarese, lucadambrosio
 *
 */
public class IdNotFoundException extends Exception {

	public IdNotFoundException() {
		super("id della ricerca non trovato");
	}
}
