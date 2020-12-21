/**
 * 
 */
package it.ldaac.meteoOOP.models;

import java.util.Vector;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * @author andreacivitarese
 * @description Classe che definisce una città con la sua raccolta di dati meteo
 */
public class Citta {
	
	private String nomeCitta;
	private long id;
	private double lat;
	private double lon;
	private Vector<DatoMeteo> datiMeteo;
	
	/**
	 * @Constructor per Citta
	 * 
	 * @param nomeCitta Nome della città
	 * @param id        Codice identificativo per le API di Openwheather
	 * @param lat	    Latitudine
	 * @param lon       Longitudine
	 */
	public Citta (String nomeCitta, long id, double lat, double lon)
	{
		this.nomeCitta = nomeCitta;
		this.id = id;
		this.lat = lat;
		this.lon = lon;
		this.datiMeteo = new Vector<DatoMeteo>();
	}
	
	public Citta(JSONObject citta)
	{
		this.nomeCitta = (String) citta.get("nome_citta");
		
		this.id = (long) citta.get("id");
		
		String lat = citta.get("lat").toString();
		this.lat = Double.parseDouble(lat);
		
		String lon = citta.get("lon").toString();
		this.lon = Double.parseDouble(lon);
		
		this.datiMeteo = new Vector<DatoMeteo>();
		
		JSONArray datiMeteo = (JSONArray) citta.get("dati_meteo");
		for(int i = 0; i<datiMeteo.size(); i++)
		{
			this.datiMeteo.add(new DatoMeteo((JSONObject) datiMeteo.get(i)));
		}
		
	}
	/**
	 * @return Il nome della città
	 */
	public String getNomeCitta() {
		return nomeCitta;
	}

	/**
	 * @param nomeCitta Nome della Città
	 */
	public void setNomeCitta(String nomeCitta) {
		this.nomeCitta = nomeCitta;
	}

	/**
	 * @return ID per le API di Openweather
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id ID per le API di Openweather
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return La latitudine
	 */
	public double getLat() {
		return lat;
	}

	/**
	 * @param lat Latitudine
	 */
	public void setLat(double lat) {
		this.lat = lat;
	}

	/**
	 * @return Longitudine
	 */
	public double getLon() {
		return lon;
	}

	/**
	 * @param lon Longitudine
	 */
	public void setLon(double lon) {
		this.lon = lon;
	}
	
	/**
	 * @return Vettore dei dati meteo della città
	 */
	public Vector<DatoMeteo> getDatiMeteo()
	{
		return this.datiMeteo;
	}
	
	/**
	 * @param datiMeteo the datiMeteo to set
	 */
	public void setDatiMeteo(Vector<DatoMeteo> datiMeteo) {
		this.datiMeteo = datiMeteo;
	}
	
	/**
	 * Metodo per aggiungere un dato meteo al vettore dei dati della città
	 * @param datoDaIns Dato meteo da inserire nella città
	 */
	public void aggiungiDatoMeteo(DatoMeteo datoDaIns)
	{
		this.datiMeteo.add(datoDaIns);
	}
	
	public JSONObject toJSONObject()
	{
		JSONObject ritorno = new JSONObject();
		ritorno.put("nome_citta", this.nomeCitta);
		ritorno.put("id", this.id);
		ritorno.put("lat", this.lat);
		ritorno.put("lon", this.lon);
		JSONArray datiMeteo = new JSONArray();
		for(DatoMeteo d:this.datiMeteo)
		{
			datiMeteo.add(d.toJSONObject());
		}
		ritorno.put("dati_meteo", datiMeteo);
		return ritorno;
	}
	
	
	public double calcolaDistanza(Citta citta)
	{
		double fiA = Math.toRadians(this.lat);
		double fiB = Math.toRadians(citta.lat);
		double deltaLambda = Math.abs(Math.toRadians(this.lon - citta.lon));
		
		double d0 = Math.acos(Math.sin(fiA)*Math.sin(fiB) + Math.cos(fiA)*Math.cos(fiB)*Math.cos(deltaLambda));
		
		double distanza = Math.toDegrees(d0)*60D*1.852D;
		
		return distanza;
	}

	
}