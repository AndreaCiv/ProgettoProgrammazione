package it.ldaac.meteoOOP.models;

import org.json.simple.JSONObject;

/**
 * 
 * Interfaccia che mostra la funzione toJSONObject() e viene implementata da tutte le classi 
 * che possono essere salvate e poi ricreate tramite JSONObject
 * 
 * @author andreacivitarese, lucadambrosio
 */
public interface JSONAble {
	
	/**
	 * 
	 * Metodo per ottenere un JSONObject contenente tutti i dati dalla classe che implementa l'interfaccia
	 * 
	 * @return JSONObject contenente i dati della classe
	 */
	public JSONObject toJSONObject();
}
