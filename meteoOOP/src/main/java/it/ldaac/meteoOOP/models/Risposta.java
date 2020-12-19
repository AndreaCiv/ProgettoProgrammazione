/**
 * 
 */
package it.ldaac.meteoOOP.models;

/**
 * @author andreacivitarese
 *
 */
public class Risposta {
	private String message;
	private long cod;
	
	public Risposta (String message, long cod)
	{
		this.message = message;
		this.cod = cod;
	}
}
