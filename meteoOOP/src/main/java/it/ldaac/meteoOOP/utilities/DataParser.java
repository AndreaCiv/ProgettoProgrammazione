/**
 * 
 */
package it.ldaac.meteoOOP.utilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.util.Vector;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import it.ldaac.meteoOOP.exceptions.BadRequestException;
import it.ldaac.meteoOOP.models.Citta;

/**
 * @author andreacivitarese
 *
 * @description permette di richiamare l'API di Openwheather per ottenere i dati meteo riguardanti le
 * città all'interno di un cerchio
 */
public class DataParser extends Parser {

	public DataParser(String apiKey) {
		super(apiKey);
	}

	public Vector<Citta> PrimaRichiestaDatiMeteo (double lat, double lon, int cnt) throws org.json.simple.parser.ParseException, BadRequestException
	{
		JSONParser parser = new JSONParser();
		Vector<Citta> ritorno = new Vector<Citta>();

		try {
			URLConnection openWeather = new URL(this.URLGenerator(lat, lon, cnt)).openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(openWeather.getInputStream()));
			
			String inputLine = in.readLine();
			
			JSONObject risposta = (JSONObject)parser.parse(inputLine);
			
			int cod = (int) risposta.get("cod");
			if (cod == 200)
				throw new BadRequestException();
			
			JSONArray arrayCitta = (JSONArray) risposta.get("list");
			
			for(int i=0; i<arrayCitta.size(); i++)
			{
				JSONObject citta = (JSONObject) arrayCitta.get(i);
				int id = (int) citta.get("id");
				String nomeCitta = (String) citta.get("name");
				
				JSONObject coord = (JSONObject) citta.get("coord");
				double latitude = (double) coord.get("lat");
				double longitude = (double) coord.get("lon");
				
				
			}
		}catch (MalformedURLException e) {
			e.printStackTrace();
		}catch(IOException e) {
			e.printStackTrace();
		}
		
	}
	/**
	 * 
	 * @param lat Latitudine
	 * @param lon Longitudine
	 * @param cnt Città da cercare
	 * @return URL sotto forma di stringa per chiamare l'API di OpenWeather
	 */
	public String URLGenerator(double lat, double lon, int cnt)
	{
		String URL = "api.openweathermap.org/data/2.5/find?";
		URL += ("lat=" + lat);
		URL += ("&lon=" + lon);
		URL += ("&cnt=" + cnt);
		URL += ("&appid=" + super.getApiKey());
		URL += "&units=metric";
		return URL;
	}
}
