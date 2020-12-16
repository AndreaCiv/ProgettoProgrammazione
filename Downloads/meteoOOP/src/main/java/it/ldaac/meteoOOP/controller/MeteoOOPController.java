package it.ldaac.meteoOOP.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import it.ldaac.meteoOOP.models.Citta;
import it.ldaac.meteoOOP.models.DatoMeteo;
import it.ldaac.meteoOOP.models.Richiesta;
import it.ldaac.meteoOOP.utilities.CoordParser;

@RestController
public class MeteoOOPController {
	
	@RequestMapping(value = "/testcitta/{nomeCitta}&{raggio}", method = RequestMethod.GET)
	public Richiesta test(Map<String, Object> model, @PathVariable String nomeCitta, @PathVariable int raggio)
	{
		model.put("nomeCitta", nomeCitta);
		model.put("raggio", raggio);
		Richiesta richiesta = new Richiesta(nomeCitta, raggio);
		return richiesta;
	}
}