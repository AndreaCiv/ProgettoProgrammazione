package it.ldaac.meteoOOP.models;

import java.util.Vector;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * 
 * Implements JSONAble, Cloneable
 * Classe che definisce una città e i suoi dati principali, con la sua raccolta di dati meteo
 * 
 * @author andreacivitarese, lucadambrosio
 */
public class Citta implements JSONAble, Cloneable {
	
	/**
	 * Nome della città
	 */
	private String nomeCitta;
	
	/**
	 * id della città
	 * Le viene assegnato dalle API di Openweather
	 */
	private long id;
	
	/**
	 * Latitudine della città in gradi sessagesimali
	 */
	private double lat;
	
	/**
	 * Longitudine della città in gradi sessagesimali
	 */
	private double lon;
	
	/**
	 * Vettore contenente tutti i dati meteo relativi a quella città
	 */
	private Vector<DatoMeteo> datiMeteo;
	
	/**
	 * Costruttore per Citta
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
	
	/**
	 * Costruttore per Citta
	 * 
	 * @param citta JSONObject contenente i seguenti campi
	 * "nome_citta" 	nome della città
	 * "id"				id della città
	 * "lat"			latitudine della città in gradi sessagesimali
	 * "lon"			longitudine della città in gradi sessagesimali
	 * "dati_meteo" 	JSONArray contenente tutti i dati meteo della città
	 */
	public Citta(JSONObject citta)
	{
		this.nomeCitta = (String) citta.get("nome_citta");
		
		this.id = (long) citta.get("id");
		
		this.lat = Double.parseDouble(citta.get("lat").toString());
		
		this.lon = Double.parseDouble(citta.get("lon").toString());
		
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
	 * @return Latitudine della città in gradi sessagesimali
	 */
	public double getLat() {
		return lat;
	}

	/**
	 * @param lat Latitudine della città in gradi sessagesimali
	 */
	public void setLat(double lat) {
		this.lat = lat;
	}

	/**
	 * @return Longitudine della città in gradi sessagesimali
	 */
	public double getLon() {
		return lon;
	}

	/**
	 * @param lon Longitudine della città in gradi sessagesimali
	 */
	public void setLon(double lon) {
		this.lon = lon;
	}
	
	/**
	 * @return Vettore contenente i dati meteo della città
	 */
	public Vector<DatoMeteo> getDatiMeteo()
	{
		return this.datiMeteo;
	}
	
	/**
	 * @param datiMeteo Vettore di dati meteo da associare alla città
	 */
	public void setDatiMeteo(Vector<DatoMeteo> datiMeteo) {
		this.datiMeteo = datiMeteo;
	}
	
	/**
	 * Metodo per aggiungere un dato meteo al vettore dei dati della città
	 * 
	 * @param datoDaIns Dato meteo da inserire nella città
	 */
	public void aggiungiDatoMeteo(DatoMeteo datoDaIns)
	{
		this.datiMeteo.add(datoDaIns);
	}
	
	/**
	 * Metodo per ottenere un JSONObject contenente tutti i dati riguardanti la città
	 * 
	 * @return JSONObject contenente gli attributi della città e il JSONArray dei dati meteo a essa relativi
	 */
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
	
	/**
	 * Metodo per calcolare la distanza tra questa città e quella passata per argomento approssimando
	 * la terra ad una sfera
	 * 
	 * @param citta Città dalla quale si vuole calcolare la distanza
	 * @return Distanza tra le due città in km
	 */
	public double calcolaDistanza(Citta citta)
	{
		double fiA = Math.toRadians(this.lat);
		double fiB = Math.toRadians(citta.lat);
		double deltaLambda = Math.abs(Math.toRadians(this.lon - citta.lon));
		
		double d0 = Math.acos(Math.sin(fiA)*Math.sin(fiB) + Math.cos(fiA)*Math.cos(fiB)*Math.cos(deltaLambda));
		
		double distanza = Math.toDegrees(d0)*60D*1.852D;
		
		return distanza;
	}
	
	/**
	 * Crea un clone della città
	 * @return Clone della città
	 */
	public Citta clone()
	{
		return new Citta(this.toJSONObject());
	}

	
}