/**
 * 
 */
package it.ldaac.meteoOOP.models;

import org.json.simple.JSONObject;

/**
 * @author andreacivitarese, lucadambrosio
 *
 * Interfaccia che mostra la funzione toJSONObject() e viene implementata da tutte le classi 
 * che possono essere salvate e poi ricreate tramite JSONObject
 *
 */
public interface JSONAble {
	public JSONObject toJSONObject();
}
