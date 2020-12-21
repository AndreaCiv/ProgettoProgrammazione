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
import it.ldaac.meteoOOP.statsAndFilters.Filters;
import it.ldaac.meteoOOP.statsAndFilters.Stats;
import it.ldaac.meteoOOP.utilities.CoordParser;
import it.ldaac.meteoOOP.utilities.DataParser;

/**
 * @author andreacivitarese
 *
 */

@Service
public class MeteoService {
	
	private Vector<Ricerca> ricerche;
	private DataParser dataParser;
	private CoordParser coordParser;
	private long periodoAggiornamentoDati = 7200000;
	private Filters filtri;
	private Stats statistiche;
	
	public MeteoService()
	{
		try {
			this.ricerche = new Vector<Ricerca>();
			this.caricaDaFile();
			System.out.println("Database caricato dal file");
		}catch(IOException | ParseException e) {
			this.ricerche = new Vector<Ricerca>();
			e.printStackTrace();
			System.out.println("Errore nel caricamento del database");
			System.out.println("Inizializzo un database vuoto");
		}
		
		try {
			Scanner in = new Scanner(new BufferedReader(new FileReader("config.JSON")));
			String inputLine = in.nextLine();
			in.close();
			
			JSONParser parser = new JSONParser();
			JSONObject config = (JSONObject) parser.parse(inputLine);
			
			this.coordParser = new CoordParser((String) config.get("API_key"));
			this.dataParser = new DataParser((String) config.get("API_key"));
			this.periodoAggiornamentoDati = (long) config.get("periodo_aggiornamento");
		}
		catch(IOException | ParseException e) {
			this.periodoAggiornamentoDati = 7200000;
			this.coordParser = new CoordParser("1517261fd57d49d69ffd42658f042ff9");
			this.dataParser = new DataParser("1517261fd57d49d69ffd42658f042ff9");
		}
		
		this.filtri = new Filters();
		this.statistiche = new Stats();
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
		Ricerca ricerca = new Ricerca(richiesta, this.coordParser, this.dataParser);
		ricerche.add(ricerca);
		ricerca.RicercaDatiDueOre(periodoAggiornamentoDati, richiesta.getDurataAggiornamentoDati(), dataParser);
		return new Risposta("Prova", ricerca.getId(), filtri.filtraCittaInNumero(filtri.filtraCittaInRaggio(ricerca.getCitta(), richiesta.getRaggio()), richiesta.getCnt()));
	}
	
	public Vector<Ricerca> getDataBase()
	{
		return this.ricerche;
	}
	
	public void removeAll()
	{
		this.ricerche.removeAllElements();
	}
	
}
