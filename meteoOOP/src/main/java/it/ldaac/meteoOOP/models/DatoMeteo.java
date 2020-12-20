package it.ldaac.meteoOOP.models;

import java.util.Date;

import org.json.simple.JSONObject;

/**
 * @author andreacivitarese
 *
 * Classe che racchiude il dato meteo riguardante temperatura percepita e velocità del vento
 * in uno specifico momento ed assegnato ad una specifica città
 *  
 */
public class DatoMeteo {
	
	private double temperatura;
	private double temperaturaPercepita;
	private double velocitaVento;
	Date data;
	
	/**
	 * @Constructor per DatoMeteo
	 * 
	 * @param temperaura Temperatura in °C
	 * @param tempPerc   Temperatura percepita in °C
	 * @param velVento   Velocità del vento in km/h
	 * @param dataUnix   Data in formato Unix
	 */
	public DatoMeteo (double temperatura, double tempPerc, double velVento, long dataUnix)
	{
		this.temperatura = temperatura;
		this.temperaturaPercepita = tempPerc;
		this.velocitaVento = velVento;
		this.data = new Date(dataUnix);
	}
	
	public DatoMeteo(JSONObject datoMeteo)
	{
		String temperatura = datoMeteo.get("temperatura").toString();
		this.temperatura = Double.parseDouble(temperatura);
		
		String temperaturaPercepita = datoMeteo.get("temperatura_percepita").toString();
		this.temperaturaPercepita = Double.parseDouble(temperaturaPercepita);
		
		String velocitaVento = datoMeteo.get("velocita_vento").toString();
		this.velocitaVento = Double.parseDouble(velocitaVento);
		
		this.data = new Date((long)datoMeteo.get("data"));
	}
	
	/**
	 * @return Temperatura in °C
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
	 * @return La temperatura percepita in °C
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
	 * @return La velocità del vento in km/h
	 */
	public double getVelocitaVento() {
		return velocitaVento;
	}
	
	/**
	 * @param Velocità del vento da impostare in km/h
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
	 * @param Data della rilevazione da impostare
	 */
	public void setData(Date data) {
		this.data = data;
	}
	
	public boolean confrontaData (Date data)
	{
		if (this.data.compareTo(data)>=0)
			return true;
		else
			return false;
	}
	
	public JSONObject toJSONObject()
	{
		JSONObject ritorno = new JSONObject();
		ritorno.put("temperatura", this.temperatura);
		ritorno.put("temperatura_percepita", this.temperaturaPercepita);
		ritorno.put("velocita_vento", this.velocitaVento);
		ritorno.put("data", this.data.getTime());
		return ritorno;
	}
}