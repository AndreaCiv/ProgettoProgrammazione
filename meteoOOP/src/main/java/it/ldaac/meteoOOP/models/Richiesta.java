/**
 * 
 */
package it.ldaac.meteoOOP.models;

/**
 * @author andreacivitarese
 * @description Classe dove vengono salvati i dati della richiesta fatta dall'utente
 */
public class Richiesta {
	private String nomeCitta;
	private int raggio;
	private int cnt;
	
	/**
	 * @constructor per Richiesta nel caso l'utente fornisse il numero di città da ricercare
	 * 
	 * @param nomeCitta Nome della città centro del cerchio di ricerca
	 * @param raggio    Raggio di ricerca in km
	 * @param cnt       Numero di città da ricercare
	 */
	public Richiesta(String nomeCitta, int raggio, int cnt)
	{
		this.nomeCitta = nomeCitta;
		this.raggio = raggio;
		this.cnt = cnt;
	}
	
	/**
	 * @constructor per Richiesta nel caso l'utente non fornisse il numero di città da ricercare
	 * 
	 * @param nomeCitta Nome della città centro del cerchio di ricerca
	 * @param raggio    Raggio di ricerca in km
	 */
	public Richiesta(String nomeCitta, int raggio)
	{
		this.nomeCitta = nomeCitta;
		this.raggio = raggio;
		this.cnt = 50;
	}

	/**
	 * @return Il nome della città inserito dall'utente
	 */
	public String getNomeCitta() {
		return nomeCitta;
	}

	/**
	 * @param nomeCitta Nome della città da impostare come centro del cerchio di ricerca
	 */
	public void setNomeCitta(String nomeCitta) {
		this.nomeCitta = nomeCitta;
	}

	/**
	 * @return Raggio di ricerca
	 */
	public int getRaggio() {
		return raggio;
	}

	/**
	 * @param raggio Raggio di ricerca
	 */
	public void setRaggio(int raggio) {
		this.raggio = raggio;
	}

	/**
	 * @return Numero di città da ricercare
	 */
	public int getCnt() {
		return cnt;
	}

	/**
	 * @param cnt Numero di città da ricercare
	 */
	public void setCnt(int cnt) {
		this.cnt = cnt;
	}
	
	
}
