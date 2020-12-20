/**
 * 
 */
package it.ldaac.meteoOOP.models;

import java.util.Vector;

/**
 * @author andreacivitarese
 *
 */
public class Risposta {
	private String message;
	private long cod;
	private Vector<Citta> citta;
	
	public Risposta (String message, long cod, Vector<Citta> citta)
	{
		this.message = message;
		this.cod = cod;
		this.citta = citta;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the cod
	 */
	public long getCod() {
		return cod;
	}

	/**
	 * @param cod the cod to set
	 */
	public void setCod(long cod) {
		this.cod = cod;
	}
	
	/**
	 * @return the citta
	 */
	public Vector<Citta> getCitta() {
		return citta;
	}

	/**
	 * @param citta the citta to set
	 */
	public void setCitta(Vector<Citta> citta) {
		this.citta = citta;
	}
}
