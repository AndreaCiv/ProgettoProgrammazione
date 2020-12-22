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
	public JSONObject toJSONObject();
}
