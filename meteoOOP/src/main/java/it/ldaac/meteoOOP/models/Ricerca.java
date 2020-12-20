/**
 * 
 */
package it.ldaac.meteoOOP.models;

import java.util.Vector;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import it.ldaac.meteoOOP.exceptions.BadRequestException;
import it.ldaac.meteoOOP.utilities.CoordParser;
import it.ldaac.meteoOOP.utilities.DataParser;

/**
 * @author andreacivitarese
 *
 */
public class Ricerca {
	private long id;
	private Richiesta richiesta;
	private Vector<Citta> citta;
	
	/**
	 * 
	 * @param richiesta Richiesta tramite la quale viene generata la ricerca
	 * @throws BadRequestException
	 * @throws ParseException
	 */
	public Ricerca(Richiesta richiesta) throws BadRequestException, ParseException
	{
		this.richiesta = richiesta;
		
		CoordParser coordParser = new CoordParser("1517261fd57d49d69ffd42658f042ff9");
		double coordCentrali[] = new double[2];
		coordCentrali = coordParser.richiestaCoord(richiesta.getNomeCitta());
		
		DataParser dataParser = new DataParser("1517261fd57d49d69ffd42658f042ff9");
		this.citta = dataParser.richiestaDatiMeteo(coordCentrali[0], coordCentrali[1], 50);
		
		this.id = this.citta.elementAt(0).getId();
	}

	/**
	 * @return ID della ricerca
	 */
	public long getId() {
		return id;
	}

	/**
	 * @return La richiesta con la quale è stata generata questa ricerca
	 */
	public Richiesta getRichiesta() {
		return richiesta;
	}

	/**
	 * @return Vettore di città che fanno parte della ricerca
	 */
	public Vector<Citta> getCitta() {
		return citta;
	}
	
	public JSONObject toJSONObject()
	{
		JSONObject ritorno = new JSONObject();
		ritorno.put("id_ricerca", this.id);
		ritorno.put("info_richiesta", this.richiesta.toJSONObject());
		
		JSONArray dati = new JSONArray();
		for(Citta c : this.citta)
		{
			dati.add(c.toJSONObject());
		}
		
		ritorno.put("dati", dati);
		
		return ritorno;
	}
}
