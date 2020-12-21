/**
 * 
 */
package it.ldaac.meteoOOP.models;

import org.json.simple.JSONObject;

/**
 * @author andreacivitarese
 * @description Classe dove vengono salvati i dati della richiesta fatta dall'utente
 */
public class Richiesta {
	private String nomeCitta;
	private int raggio;
	private int cnt;
	private long durataRaccoltaDati;
	
	
	/**
	 * @constructor per Richiesta nel caso l'utente fornisse il numero di città da ricercare
	 * 
	 * @param nomeCitta Nome della città centro del cerchio di ricerca
	 * @param raggio    Raggio di ricerca in km
	 * @param cnt       Numero di città da ricercare
	 */
	public Richiesta(String nomeCitta, int raggio, int cnt, long durataRaccoltaDati)
	{
		this.nomeCitta = nomeCitta;
		this.raggio = raggio;
		this.cnt = cnt;
		this.durataRaccoltaDati = durataRaccoltaDati;
	}
	
	/**
	 * @constructor per Richiesta nel caso l'utente non fornisse il numero di città da ricercare
	 * 
	 * @param nomeCitta Nome della città centro del cerchio di ricerca
	 * @param raggio    Raggio di ricerca in km
	 */
	public Richiesta(String nomeCitta, int raggio, long durataRaccoltaDati)
	{
		this.nomeCitta = nomeCitta;
		this.raggio = raggio;
		this.cnt = 50;
		this.durataRaccoltaDati = durataRaccoltaDati;
	}
	
	public Richiesta(JSONObject richiesta)
	{
		this.nomeCitta = (String) richiesta.get("nome_citta");
		
		String raggio = richiesta.get("raggio").toString();
		this.raggio = Integer.parseInt(raggio);
		
		String cnt = richiesta.get("cnt").toString();
		this.cnt = Integer.parseInt(cnt);
		
		this.durataRaccoltaDati = (long) richiesta.get("durata_raccolta_dati");
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
	
	/**
	 * @return the durataRaccoltaDati
	 */
	public long getDurataAggiornamentoDati() {
		return durataRaccoltaDati;
	}

	/**
	 * @param durataRaccoltaDati the durataAggiornamentoDati to set
	 */
	public void setDurataAggiornamentoDati(long durataRaccoltaDati) {
		this.durataRaccoltaDati = durataRaccoltaDati;
	}

	public JSONObject toJSONObject()
	{
		JSONObject ritorno = new JSONObject();
		ritorno.put("nome_citta", this.nomeCitta);
		ritorno.put("raggio", this.raggio);
		ritorno.put("cnt", this.cnt);
		ritorno.put("durata_raccolta_dati", this.durataRaccoltaDati);
		return ritorno;
	}
}
