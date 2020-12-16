/**
 * 
 */
package it.ldaac.meteoOOP.utilities;

/**
 * @author andreacivitarese
 * Superclasse dalla quale erediteranno i parser per ottenere i dati dalle API
 * di Openweather
 */
public class Parser {
	
	private String url;
	private String apiKey;
	
	/**
	 * @Constructor per Parser
	 * 
	 * @param apiKey  Key dell'API
	 */
	public Parser( String apiKey)
	{
		this.apiKey = apiKey;
	}

	/**
	 * @return URL dell'API
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url URL dell'API
	 */
	public void setUrl(String url) {
		this.url = url;
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
