/**
 * 
 */
package it.ldaac.meteoOOP.models;

import java.util.Iterator;
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
	public Ricerca(Richiesta richiesta, CoordParser coordParser, DataParser dataParser) throws BadRequestException, ParseException
	{
		this.richiesta = richiesta;
		
		double coordCentrali[] = new double[2];
		coordCentrali = coordParser.richiestaCoord(richiesta.getNomeCitta());
		
		this.citta = dataParser.richiestaDatiMeteo(coordCentrali[0], coordCentrali[1], 50);
		
		this.id = this.citta.elementAt(0).getId();
	}
	
	public Ricerca (JSONObject ricerca)
	{
		this.id = (long) ricerca.get("id_ricerca");
		this.richiesta = new Richiesta((JSONObject) ricerca.get("info_richiesta"));
		
		this.citta = new Vector<Citta>();
		
		JSONArray dati = (JSONArray) ricerca.get("dati");
		for(int i = 0; i<dati.size(); i++)
		{
			citta.add(new Citta((JSONObject) dati.get(i)));
		}
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
	
	public boolean aggiornaDatiMeteo(DataParser dataParser)
	{
		Vector<Citta> cittaAggiornate = new Vector<Citta>();
		try {
		cittaAggiornate = dataParser.richiestaDatiMeteo(this.citta.elementAt(0).getLat(), this.citta.elementAt(0).getLon(), this.richiesta.getCnt());
		} catch(BadRequestException | ParseException e) {
			return false;
		}
		
		for (Citta c : cittaAggiornate)
		{
			String nomeCitta = c.getNomeCitta();
			Vector<DatoMeteo> nuovoDato = c.getDatiMeteo();
			
			for(int j=0; j<this.citta.size(); j++)
			{
				if(this.citta.elementAt(j).getNomeCitta().equals(nomeCitta))
					this.citta.elementAt(j).aggiungiDatoMeteo(nuovoDato.elementAt(0));
			}
			
		}
		
		return true;
	}
}
