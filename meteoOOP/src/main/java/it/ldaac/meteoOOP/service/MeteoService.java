/**
 * 
 */
package it.ldaac.meteoOOP.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.Vector;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import it.ldaac.meteoOOP.exceptions.BadRequestException;
import it.ldaac.meteoOOP.models.Ricerca;
import it.ldaac.meteoOOP.models.Richiesta;
import it.ldaac.meteoOOP.models.Risposta;
import it.ldaac.meteoOOP.utilities.CoordParser;
import it.ldaac.meteoOOP.utilities.DataParser;

/**
 * @author andreacivitarese
 *
 */

@Service
public class MeteoService {
	
	private Vector<Ricerca> ricerche;
	
	public MeteoService()
	{
		//try {
		//	this.caricaDaFile();
		//}catch(IOException | ParseException e) {
			this.ricerche = new Vector<Ricerca>();
		//	e.printStackTrace();
		//	System.out.println("Errore nel caricamento del database");
		//	System.out.println("Inizializzo un database vuoto");
		//}
	}
	
	public void aggiungiRicerca (Ricerca ricerca)
	{
		this.ricerche.add(ricerca);
	}
	
	public void salvaSuFile() throws IOException
	{
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("database.JSON")));
		
		JSONObject salvataggio = new JSONObject();
		JSONArray ricerche = new JSONArray();
		
		for(Ricerca r : this.ricerche)
		{
			ricerche.add(r.toJSONObject());
		}
		
		salvataggio.put("ricerche", ricerche);
		
		out.println(salvataggio.toJSONString());
		
		out.close();
		
	}
	
	public void caricaDaFile() throws IOException, ParseException
	{
		Scanner in = new Scanner(new BufferedReader(new FileReader("database.JSON")));
		
		String inputLine = in.nextLine();
		
		JSONParser parser = new JSONParser();
		
		JSONObject salvataggio = (JSONObject) parser.parse(inputLine);
		
		JSONArray ricerche = (JSONArray) salvataggio.get("ricerche");
		
		this.ricerche = new Vector<Ricerca>();
		
		for(int i=0; i<ricerche.size(); i++)
		{
			this.ricerche.add(new Ricerca((JSONObject) ricerche.get(i)));
		}
		
	}
	public Risposta avviaRicerca(Richiesta richiesta) throws BadRequestException, ParseException
	{
		Ricerca ricerca = new Ricerca(richiesta);
		ricerche.add(ricerca);
		return new Risposta("Prova", ricerca.getId(), ricerca.getCitta());
	}
}
