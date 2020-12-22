package it.ldaac.meteoOOP.utilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Vector;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import it.ldaac.meteoOOP.exceptions.BadRequestException;
import it.ldaac.meteoOOP.models.Citta;
import it.ldaac.meteoOOP.models.DatoMeteo;

/**
 * 
 * Permette di richiamare l'API di Openwheather per ottenere i dati meteo riguardanti le
 * città all'interno di un cerchio
 * 
 * @author andreacivitarese, lucadambrosio
 */
public class DataParser extends Parser {

	private String units;
	
	/**
	 * Costruttore per DataParser
	 * 
	 * @param apiKey API Key da utilizzare per le chiamate all'API di OpenWeather
	 * @param units Tipo di unità che deve utilizzare l'API di OpenWeather nella risposta
	 */
	public DataParser(String apiKey, String units) {
		super(apiKey);
		this.units = units;
	}
	
	/**
	 * Metodo che restituisce un vettore di città ognuna con il proprio dato meteo istantaneo
	 * 
	 * @param lat Latitudine del punto centrale della ricerca
	 * @param lon Longitudine del punto centrale della ricerca
	 * @param cnt Numero di città da ricercare
	 * @return Vettore di città con relativi dati meteo istantanei
	 * @throws org.json.simple.parser.ParseException
	 * @throws BadRequestException
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	public Vector<Citta> richiestaDatiMeteo (double lat, double lon, int cnt) throws org.json.simple.parser.ParseException, BadRequestException, MalformedURLException, IOException
	{
		JSONParser parser = new JSONParser();
		Vector<Citta> ritorno = new Vector<Citta>();

		URLConnection openWeather = new URL(this.URLGenerator(lat, lon, cnt, this.units)).openConnection();
		BufferedReader in = new BufferedReader(new InputStreamReader(openWeather.getInputStream()));
		
		String inputLine = in.readLine();
		
		JSONObject risposta = (JSONObject)parser.parse(inputLine);
			
		String cod = (String) risposta.get("cod");
		if (cod == "200")
			throw new BadRequestException();
		
		JSONArray arrayCitta = (JSONArray) risposta.get("list");
		
		for(int i=0; i<arrayCitta.size(); i++)
		{
			JSONObject citta = (JSONObject) arrayCitta.get(i);
			long id = (long) citta.get("id");
			String nomeCitta = (String) citta.get("name");
			
			JSONObject coord = (JSONObject) citta.get("coord");	
			double latitude = Double.parseDouble(coord.get("lat").toString());
			double longitude = Double.parseDouble(coord.get("lon").toString());	
			
			Citta city = new Citta(nomeCitta, id, latitude, longitude);
			
			JSONObject main = (JSONObject) citta.get("main");
			double temperatura = Double.parseDouble(main.get("temp").toString());
			double temperaturaPerc = Double.parseDouble(main.get("feels_like").toString());	
			long dt = (long) citta.get("dt");
				
			JSONObject wind = (JSONObject) citta.get("wind");
			double velVento = Double.parseDouble(wind.get("speed").toString());
				
			DatoMeteo datoMeteo = new DatoMeteo(temperatura, temperaturaPerc, velVento, (dt*1000L));
				
			city.aggiungiDatoMeteo(datoMeteo);
				
			ritorno.add(city);
		}
	return ritorno;
}
	
	
	/**
	 * Genera l'URL per chimare l'API di Openwheather Cities in circle
	 * 
	 * @param lat Latitudine
	 * @param lon Longitudine
	 * @param cnt Numero di città da cercare
	 * @return URL sotto forma di stringa per chiamare l'API di OpenWeather
	 */
	public String URLGenerator(double lat, double lon, int cnt, String units)
	{
		String URL = "https://api.openweathermap.org/data/2.5/find?"; //Crea URL 
		URL += ("lat=" + lat);										  //Concateno i pezzi di stringa e i vari parametri per formare l'URL
		URL += ("&lon=" + lon);											
		URL += ("&cnt=" + cnt);
		URL += ("&appid=" + super.getApiKey());
		URL += ("&units=" + units);
		return URL;
	}
}
