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
 * @description Parser per otennere le coordinate della città, centro del cerchio di ricerca, inserita
 * dall'utente
 */
public class CoordParser extends Parser {

	public CoordParser(String apiKey) {
		super(apiKey);
		super.setUrl("https://api.openweathermap.org/data/2.5/weather?q=");
	}
	
	public double[] richiestaCoord(String nomeCitta)
	{
		JSONParser parser = new JSONParser();
		double coordinate[] = new double[2];
		try {
			URLConnection openWeather = new URL(super.getUrl() + nomeCitta + "&appid=" + super.getApiKey()).openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(openWeather.getInputStream()));
			
			String inputLine = in.readLine();
			
			JSONObject risposta = (JSONObject) parser.parse(inputLine);
			JSONObject coord = (JSONObject) risposta.get("coord");
			
			coordinate[0] = (double) coord.get("lat");
			coordinate[1] = (double) coord.get("lon");
			
		}catch (ParseException e) {
			e.printStackTrace();
		}catch (MalformedURLException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
		return coordinate;
	}

}
