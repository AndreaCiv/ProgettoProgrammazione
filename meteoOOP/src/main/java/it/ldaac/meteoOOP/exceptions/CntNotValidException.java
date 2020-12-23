package it.ldaac.meteoOOP.exceptions;

/**
 * 
 * Eccezione nel caso il numero di città da cercare inserito dall'utente non sia valido
 * 
 * @author andreacivitarese, lucadambrosio
 */
public class CntNotValidException extends Exception {

	/**
	 * Costruttore per CntNotValidException
	 */
	public CntNotValidException() {
		super("cnt non valido, inserisci un numero tra 1 e 50");
	}
}
