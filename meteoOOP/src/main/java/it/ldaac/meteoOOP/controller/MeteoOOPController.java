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
import it.ldaac.meteoOOP.exceptions.IdNotValidException;
import it.ldaac.meteoOOP.exceptions.RaggioNotValidException;
import it.ldaac.meteoOOP.exceptions.StatsNotValidException;
import it.ldaac.meteoOOP.models.Ricerca;
import it.ldaac.meteoOOP.models.Richiesta;
import it.ldaac.meteoOOP.service.MeteoService;

@RestController
public class MeteoOOPController {
	
	@Autowired
	private MeteoService meteoservice;
	
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
			durataRaccolta = 86400000L;
		}
		
		Richiesta richiesta = new Richiesta(nomeCitta, raggio, cnt, durataRaccolta);
		return meteoservice.avviaRicerca(richiesta);
	}
	
	@RequestMapping(value = "/stats", method = RequestMethod.POST)
	public JSONObject stats(@RequestBody JSONObject body) throws DateNotValidException, StatsNotValidException, IdNotValidException, RaggioNotValidException, CntNotValidException
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
	
	@RequestMapping(value = "/getDataBase", method = RequestMethod.GET)
	public Vector<Ricerca> getDataBase()
	{
		return meteoservice.getDataBase();
	}
	
	@RequestMapping(value = "/removeAll", method = RequestMethod.GET)
	public boolean removeAll()
	{
		this.meteoservice.removeAll();
		return true;
	}
}