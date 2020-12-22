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

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * @author andreacivitarese
 * Parser per otenere le coordinate della città, centro del cerchio di ricerca, inserita
 * dall'utente
 */
public class CoordParser extends Parser {

	public CoordParser(String apiKey) {
		super(apiKey);
	}
	
	/**
	 * 
	 * @param nomeCitta Nome della città della quale vogliamo sapere le coordinate
	 * @return Le coordinate della città cercata in un vettore di double, nel quale, alla posizione 0 c'è 
	 * la latitudine, alla posizione 1 la longitudine;
	 * @throws IOException 
	 * @throws MalformedURLException 
	 * @throws ParseException 
	 */
	public double[] richiestaCoord(String nomeCitta) throws MalformedURLException, IOException, ParseException
	{
		JSONParser parser = new JSONParser();
		double coordinate[] = new double[2];
		URLConnection openWeather = new URL(this.URLGenerator(nomeCitta)).openConnection();
		BufferedReader in = new BufferedReader(new InputStreamReader(openWeather.getInputStream()));
		
		String inputLine = in.readLine();
		
		JSONObject risposta = (JSONObject) parser.parse(inputLine);
		JSONObject coord = (JSONObject) risposta.get("coord");
		
		coordinate[0] = Double.parseDouble(coord.get("lat").toString());
		coordinate[1] = Double.parseDouble(coord.get("lon").toString());

		return coordinate;
	}
	
	/**
	 * Metodo per generare l'URL per chiamare l'API di OpenWeather
	 * @param nomeCitta Nome della città di cui cercare le coordinate
	 * @return URL sotto forma di stringa per chiamare l'API di Openweather
	 */
	public String URLGenerator(String nomeCitta)
	{
		String URL = "https://api.openweathermap.org/data/2.5/weather";
		URL += ("?q=" + nomeCitta);
		URL += ("&appid=" + super.getApiKey());
		return URL;
	}

}
