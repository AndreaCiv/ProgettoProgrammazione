/**
 * 
 */
package it.ldaac.meteoOOP.utilities;

/**
 * 
 * Superclasse dalla quale erediteranno i parser per ottenere i dati dalle API
 * di Openweather
 * 
 * @author andreacivitarese, lucadambrosio
 */
public class Parser {
	
	/**
	 * Stringa contenente la key per l'API di OpenWeather
	 */
	private String apiKey;
	
	/**
	 * Costruttore per Parser
	 * 
	 * @param apiKey  Key dell'API
	 */
	public Parser( String apiKey)
	{
		this.apiKey = apiKey;
	}

	/**
	 * @return Key dell'API
	 */
	public String getApiKey() {
		return apiKey;
	}

	/**
	 * @param apiKey Key dell'API
	 */
	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}
	
}
