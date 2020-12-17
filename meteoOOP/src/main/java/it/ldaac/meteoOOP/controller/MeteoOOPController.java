package it.ldaac.meteoOOP.controller;

import java.text.ParseException;
import java.util.Map;
import java.util.Vector;

import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import it.ldaac.meteoOOP.exceptions.BadRequestException;
import it.ldaac.meteoOOP.models.Citta;
import it.ldaac.meteoOOP.models.DatoMeteo;
import it.ldaac.meteoOOP.models.Richiesta;
import it.ldaac.meteoOOP.utilities.CoordParser;
import it.ldaac.meteoOOP.utilities.DataParser;

@RestController
public class MeteoOOPController {
	
	@RequestMapping(value = "/testcitta", method = RequestMethod.POST)
	public Vector<Citta> test(@RequestBody JSONObject richiesta) throws org.json.simple.parser.ParseException
	{
		CoordParser coordParser = new CoordParser("1517261fd57d49d69ffd42658f042ff9");
		String nomeCitta = (String) richiesta.get("name");
		double coord[] = new double[2];
		coord = coordParser.richiestaCoord(nomeCitta);
		
		DataParser dataParser = new DataParser("1517261fd57d49d69ffd42658f042ff9");
		Vector<Citta> risposta = new Vector<Citta>();
		try {
			risposta = dataParser.richiestaDatiMeteo(coord[0], coord[1], 50);
		}catch (BadRequestException e) {
		e.printStackTrace();
		}
		return risposta;
	}
}