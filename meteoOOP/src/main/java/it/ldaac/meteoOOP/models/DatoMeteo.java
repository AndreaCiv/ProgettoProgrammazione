package it.ldaac.meteoOOP.models;

import java.util.Date;

import org.json.simple.JSONObject;

/**
 * 
 * Implements JSONAble, Cloneable
 * Classe che racchiude il dato meteo riguardante temperatura, temperatura percepita e velocità del vento
 * in uno specifico momento ed assegnato ad una specifica città
 * 
 * @author andreacivitarese, lucadambrosio
 */
public class DatoMeteo implements JSONAble, Cloneable {
	
	/**
	 * temperatura in °C del datometeo
	 */
	private double temperatura;
	
	/**
	 * temperatura percepita in °C del dato meteo
	 */
	private double temperaturaPercepita;
	
	/**
	 * velocità del vento in m/s del dato meteo
	 */
	private double velocitaVento;
	
	/**
	 * istante di rilevazione del dato meteo
	 */
	Date data;
	
	/**
	 * Costruttore per DatoMeteo
	 * 
	 * @param temperatura Temperatura in °C
	 * @param tempPerc   Temperatura percepita in °C
	 * @param velVento   Velocità del vento in m/s
	 * @param dataUnix   Data in formato Unix
	 */
	public DatoMeteo (double temperatura, double tempPerc, double velVento, long dataUnix)
	{
		this.temperatura = temperatura;
		this.temperaturaPercepita = tempPerc;
		this.velocitaVento = velVento;
		this.data = new Date(dataUnix);
	}
	
	/**
	 * Costruttore per DatoMeteo
	 * 
	 * @param datoMeteo JSONObject da parsare per costruire il dato meteo
	 * 
	 * Il JSONObject deve avere i seguenti campi
	 * "temperatura" 			temperatura in °C
	 * "temperatura_percepita"  temperatura percepita in °C
	 * "velocita_vento"			velocità del vento in m/s
	 * "data"					data del rilevamento in formato Unix 
	 * 
	 */
	public DatoMeteo(JSONObject datoMeteo)
	{
		this.temperatura = Double.parseDouble(datoMeteo.get("temperatura").toString());
		
		this.temperaturaPercepita = Double.parseDouble(datoMeteo.get("temperatura_percepita").toString());
		
		this.velocitaVento = Double.parseDouble(datoMeteo.get("velocita_vento").toString());
		
		this.data = new Date((long)datoMeteo.get("data"));
	}
	
	/**
	 * @return Temperatura del dato meteo in °C
	 */
	public double getTemperatura() {
		return temperatura;
	}

	/**
	 * @param temperatura Temperatura da impostare in °C
	 */
	public void setTemperatura(double temperatura) {
		this.temperatura = temperatura;
	}
	
	/**
	 * @return La temperatura percepita del dato meteo in °C
	 */
	public double getTemperaturaPercepita() {
		return temperaturaPercepita;
	}
	
	/**
	 * @param temperaturaPercepita La temperatura percepita da impostare in °C
	 */
	public void setTemperaturaPercepita(double temperaturaPercepita) {
		this.temperaturaPercepita = temperaturaPercepita;
	}
	
	/**
	 * @return La velocità del vento del dato meteo in m/s
	 */
	public double getVelocitaVento() {
		return velocitaVento;
	}
	
	/**
	 * @param velocitaVento Velocità del vento da impostare in m/s
	 */
	public void setVelocitaVento(double velocitaVento) {
		this.velocitaVento = velocitaVento;
	}
	
	/**
	 * @return La data della rilevazione
	 */
	public String getData() {
		return data.toString();
	}
	
	/**
	 * @param data Data della rilevazione da impostare
	 */
	public void setData(Date data) {
		this.data = data;
	}
	
	/**
	 * 
	 * Metodo per confrontare una data con quella del rilavamento
	 * 
	 * @param data Data con la quale confrontare la data del rilevamento
	 * @return true Se la data del rilevamento è successiva a quella passata come argomento, false se
	 * la data del rilevamento è precedente a quella passata come argomento
	 */
	public boolean confrontaData (Date data)
	{
		if (this.data.compareTo(data)>=0)
			return true;
		else
			return false;
	}
	
	/**
	 * 
	 * Metodo per ottenere un JSONObject con tutti i dati del rilevamento
	 * 
	 * @return JSONObject contente gli attributi del dato meteo
	 */
	public JSONObject toJSONObject()
	{
		JSONObject ritorno = new JSONObject();
		ritorno.put("temperatura", this.temperatura);
		ritorno.put("temperatura_percepita", this.temperaturaPercepita);
		ritorno.put("velocita_vento", this.velocitaVento);
		ritorno.put("data", this.data.getTime());
		return ritorno;
	}
	
	/**
	 * Crea una copia del dato meteo
	 * 
	 * @return una copia del dato meteo
	 */
	public DatoMeteo clone()
	{
		return new DatoMeteo(this.toJSONObject());
	}
}