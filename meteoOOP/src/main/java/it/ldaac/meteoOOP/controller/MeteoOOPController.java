package it.ldaac.meteoOOP.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Vector;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import it.ldaac.meteoOOP.exceptions.BadRequestException;
import it.ldaac.meteoOOP.exceptions.CntNotValidException;
import it.ldaac.meteoOOP.exceptions.DateNotValidException;
import it.ldaac.meteoOOP.exceptions.IdNotFoundException;
import it.ldaac.meteoOOP.exceptions.RaggioNotValidException;
import it.ldaac.meteoOOP.exceptions.StatsNotValidException;
import it.ldaac.meteoOOP.models.Ricerca;
import it.ldaac.meteoOOP.models.Richiesta;
import it.ldaac.meteoOOP.service.MeteoService;

/**
 * 
 * Classe controller per il programma
 * 
 * @author andreacivitarese, lucadambrosio
 */
@RestController
public class MeteoOOPController {
	
	/**
	 * Servizio su cui si basa il programma
	 */
	@Autowired
	private MeteoService meteoservice;
	
	/**
	 * Rotta per avviare una ricerca con i dati forniti dall'utente
	 * 
	 * @param body JSONObject contente i parametri di ricerca forniti dall'utente
	 * @return JSONObject contenente le città coinvolte nella ricerca e i relativi dati meteo istantanei
	 * @throws org.json.simple.parser.ParseException
	 * @throws BadRequestException
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws RaggioNotValidException
	 * @throws CntNotValidException
	 */
	@RequestMapping(value = "/avviaRicerca", method = RequestMethod.POST)
	public JSONObject avviaRicerca(@RequestBody JSONObject body) throws org.json.simple.parser.ParseException, BadRequestException, MalformedURLException, IOException, RaggioNotValidException, CntNotValidException
	{
		int cnt;
		try {
			cnt = Integer.parseInt(body.get("cnt").toString());
			if (cnt > 50 || cnt < 1)
				throw new CntNotValidException();
		} catch (NullPointerException e) {
			cnt = 50;
		}
		
		String nomeCitta = (String) body.get("nome");
		
		int raggio;
		try {
			raggio = Integer.parseInt(body.get("raggio").toString());
			if (raggio <= 0)
				throw new RaggioNotValidException();
		} catch (NullPointerException e) {
			raggio = 1000;
		}
		
		long durataRaccolta;
		try {
			durataRaccolta = Long.parseLong(body.get("durata_raccolta").toString());
		} catch (NullPointerException e) {
			durataRaccolta = 172800000L;
		}
		
		Richiesta richiesta = new Richiesta(nomeCitta, raggio, cnt, durataRaccolta*3600L*1000L);
		return meteoservice.avviaRicerca(richiesta);
	}
	
	/**
	 * 
	 * Rotta per generare delle statistiche richieste dall'utente sui dati raccolti dal servizio
	 * 
	 * @param body JSONObject contenente i parametri forniti dall'utente per generare le statistiche
	 * @return JSONObject contenente le statistiche desiderate dall'utente
	 * @throws DateNotValidException
	 * @throws StatsNotValidException
	 * @throws IdNotFoundException
	 * @throws RaggioNotValidException
	 * @throws CntNotValidException
	 */
	@RequestMapping(value = "/stats", method = RequestMethod.POST)
	public JSONObject stats(@RequestBody JSONObject body) throws DateNotValidException, StatsNotValidException, IdNotFoundException, RaggioNotValidException, CntNotValidException
	{	
		long idRicerca = Long.parseLong(body.get("id").toString());
		String tipoStats = (String) body.get("tipo");
		
		String data1;
		String data2;
		
		try {
			data1 = (String) body.get("from");
			data2 = (String) body.get("to");
		} catch (NullPointerException e) {
			data1 = (String) body.get("from");
			data2 = null;
		} catch (Exception e) {
			throw new DateNotValidException();
		}
		
		int raggio;
		try {
			raggio = Integer.parseInt(body.get("raggio").toString());
			if (raggio <= 0)
				throw new RaggioNotValidException();
		} catch (NullPointerException e) {
			raggio = 1000;
		}
		
		int cnt;
		try {
			cnt = Integer.parseInt(body.get("cnt").toString());
			if (cnt > 50 || cnt < 1)
				throw new CntNotValidException();
		} catch (NullPointerException e) {
			cnt = 50;
		}
		
		return meteoservice.generaStats(idRicerca, tipoStats, data1, data2, raggio, cnt);
	}
	
	/**
	 * Rotta per salvare le ricerche effettuate in formato JSON su un file locale
	 * 
	 * @return true se il salvataggio è avvenuto correttamente
	 * @return false se il salvataggio non è avvenuto
	 */
	@RequestMapping(value = "/save", method = RequestMethod.GET)
	public boolean save()
	{
		try {
			meteoservice.salvaSuFile();
		}catch(IOException e) {
			return false;
		}
		return true;
	}
	
	/**
	 * Rotta che permette all'utente di caricare un database da un file
	 * 
	 * @return true se il caricamento è stato effettuato con successo
	 * @return false se il caricamento non è stato effettuato
	 */
	@RequestMapping(value = "/getFromFile", method = RequestMethod.GET)
	public boolean caricaDaFile()
	{
		try {
			meteoservice.caricaDaFile();
		}catch (Exception e) {
			return false;
		}
		return true;
	}
	
	/**
	 * Rotta che permette all'utente di ottenere un JSON contenente tutte le richrche effettuate
	 * 
	 * @return JSON contente tutte le ricerche effettuate
	 */
	@RequestMapping(value = "/getDataBase", method = RequestMethod.GET)
	public Vector<Ricerca> getDataBase()
	{
		return meteoservice.getDataBase();
	}
	
	/**
	 * Rotta che permette all'utente di cancellare tutte le ricerche effettuate
	 * 
	 * @return true quando la rimozione è stata completata
	 */
	@RequestMapping(value = "/removeAll", method = RequestMethod.GET)
	public boolean removeAll()
	{
		this.meteoservice.removeAll();
		return true;
	}
}