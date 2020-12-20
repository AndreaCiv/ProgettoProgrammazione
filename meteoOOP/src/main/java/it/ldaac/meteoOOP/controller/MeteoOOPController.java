package it.ldaac.meteoOOP.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.Map;
import java.util.Vector;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import it.ldaac.meteoOOP.exceptions.BadRequestException;
import it.ldaac.meteoOOP.models.Citta;
import it.ldaac.meteoOOP.models.DatoMeteo;
import it.ldaac.meteoOOP.models.Richiesta;
import it.ldaac.meteoOOP.models.Risposta;
import it.ldaac.meteoOOP.service.MeteoService;
import it.ldaac.meteoOOP.utilities.CoordParser;
import it.ldaac.meteoOOP.utilities.DataParser;

@RestController
public class MeteoOOPController {
	
	@Autowired
	private MeteoService meteoservice;
	
	@RequestMapping(value = "/testcitta", method = RequestMethod.POST)
	public Risposta test(@RequestBody JSONObject body) throws org.json.simple.parser.ParseException, BadRequestException
	{
		Richiesta richiesta = new Richiesta((String) body.get("nome"), (int) body.get("raggio"));
		return meteoservice.avviaRicerca(richiesta);
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
		}catch (IOException | org.json.simple.parser.ParseException e) {
			return false;
		}
		return true;
	}
}