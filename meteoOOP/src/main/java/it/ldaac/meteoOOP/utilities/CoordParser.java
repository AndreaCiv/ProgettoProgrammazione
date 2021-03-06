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
 * 
 * Parser per otenere le coordinate della città, centro del cerchio di ricerca, inserita
 * dall'utente
 * 
 * @author andreacivitarese, lucadambrosio
 */
public class CoordParser extends Parser {

	/**
	 * Costruttore per CoordParser
	 * Passa al costruttore della superclasse Parser l'API key
	 * 
	 * @param apiKey Key dell'API di OpenWeather
	 */
	public CoordParser(String apiKey) {
		super(apiKey);
	}
	
	/**
	 * 
	 * Metodo per ottenere le coordinate di una città partendo dal suo nome tramite l'API di OpenWeather
	 * 
	 * @param nomeCitta Nome della città della quale vogliamo sapere le coordinate
	 * @return Le coordinate della città cercata in un vettore di double, nel quale, alla posizione 0 c'è 
	 * la latitudine, alla posizione 1 la longitudine;
	 * @throws IOException Se si verificano problemi durante l'input/output
	 * @throws MalformedURLException Se l'URL per la richiesta di dati all'API di OpenWeather non è corretto
	 * @throws ParseException Se il parsing del body genera eccezioni
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
	 * 
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
