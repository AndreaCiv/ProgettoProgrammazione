package it.ldaac.meteoOOP.models;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Timer;
import java.util.Vector;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import it.ldaac.meteoOOP.exceptions.BadRequestException;
import it.ldaac.meteoOOP.utilities.AggiornaDatiMeteoTask;
import it.ldaac.meteoOOP.utilities.CoordParser;
import it.ldaac.meteoOOP.utilities.DataParser;

/**
 * 
 * Implements JSONAble, Cloneable
 * Classe che rappresenta una ricerca di dati
 * 
 * @author andreacivitarese, lucadambrosio
 */
public class Ricerca implements JSONAble, Cloneable {
	
	/**
	 * id della ricerca che corrisponde all'id della città centrale della ricerca fornito da OpenWeather
	 */
	private long id;
	
	/**
	 * Vettore delle città coinvolte nella ricerca
	 */
	private Vector<Citta> citta;
	
	/**
	 * 
	 * Costruttore per Ricerca
	 * 
	 * @param richiesta Richiesta tramite la quale viene generata la ricerca
	 * @param dataParser DataParser dal quale ottenere i dati meteo
	 * @param coordParser CoordParser dal quale ottenere le coordinate della città centrale della ricerca
	 * @throws BadRequestException Se la richiesta all'API di OpenWeather non va a buon fine
	 * @throws ParseException Se il parsing del body genera eccezioni
	 * @throws IOException Se si sono verificati errori durante la lettura/scrittura di file
	 * @throws MalformedURLException Se l'URL per la richiesta di dati all'API di OpenWeather non è corretto
	 */
	public Ricerca(Richiesta richiesta, CoordParser coordParser, DataParser dataParser) throws BadRequestException, ParseException, MalformedURLException, IOException
	{
		double coordCentrali[] = new double[2];
		coordCentrali = coordParser.richiestaCoord(richiesta.getNomeCitta());
		
		this.citta = dataParser.richiestaDatiMeteo(coordCentrali[0], coordCentrali[1], 50);
		
		this.id = this.citta.elementAt(0).getId();
	}
	
	/**
	 * 
	 * Costruttore per Ricerca
	 * 
	 * @param ricerca JSONObject contenente i seguenti campi
	 * "id_ricerca"		id della ricerca
	 * "dati"			JSONArray contenente le città coinvolte nella ricerca
	 */
	public Ricerca (JSONObject ricerca)
	{
		this.id = (long) ricerca.get("id_ricerca");
		this.citta = new Vector<Citta>();
		
		JSONArray dati = (JSONArray) ricerca.get("dati");
		for(int i = 0; i<dati.size(); i++)
		{
			citta.add(new Citta((JSONObject) dati.get(i)));
		}
	}
	
	/**
	 * @return id della ricerca
	 */
	public long getId() {
		return id;
	}

	/**
	 * @return Vettore di città che sono coinvolte nella ricerca
	 */
	public Vector<Citta> getCitta() {
		return this.citta;
	}
	
	/**
	 * 
	 * Metodo per ottenere un JSONObject contenente tutti i dati della ricerca
	 * 
	 * @return JSONObject contenente l'id della ricerca e il JSONArray contente le città coinvolte
	 */
	public JSONObject toJSONObject()
	{
		JSONObject ritorno = new JSONObject();
		ritorno.put("id_ricerca", this.id);
		
		JSONArray dati = new JSONArray();
		for(Citta c : this.citta)
		{
			dati.add(c.toJSONObject());
		}
		
		ritorno.put("dati", dati);
		
		return ritorno;
	}
	
	/**
	 * 
	 * Metodo per aggiungere dati meteo a tutte le città della ricerca ottenendoli dall'API di OpenWeather
	 * 
	 * @param dataParser DataParser dal quale ottenere i dati meteo
	 * @return true Se l'aggiunta dei dati è andata a buon fine, false Se l'aggiunta dei dati non è andata a buon fine
	 */
	public boolean aggiungiDatiMeteo(DataParser dataParser)
	{
		Vector<Citta> cittaAggiornate = new Vector<Citta>();
		try {
			cittaAggiornate = dataParser.richiestaDatiMeteo(this.citta.elementAt(0).getLat(), this.citta.elementAt(0).getLon(), 50);
		} catch(BadRequestException | ParseException | IOException e) {
			return false;
		}
		
		for (Citta c : cittaAggiornate)
		{
			String nomeCitta = c.getNomeCitta();
			long idCitta = c.getId();
			Vector<DatoMeteo> nuovoDato = c.getDatiMeteo();
			
			for(int j=0; j<this.citta.size(); j++)
			{
				if(this.citta.elementAt(j).getNomeCitta().equals(nomeCitta) && this.citta.elementAt(j).getId() == idCitta)
					this.citta.elementAt(j).aggiungiDatoMeteo(nuovoDato.elementAt(0));
			}
		}
		return true;
	}
	
	/**
	 * 
	 * Metodo che crea un timer al quale viene assegnata la task di aggiungere dati meteo, questa task
	 * viene eseguita ogni periodo di aggiornamento, che di default è impostato ogni 2 ore, per la durata della ricerca
	 * 
	 * @param periodoAggiornamentoDati periodo in millisecondi con il quale vengono aggiunti i dati alle città
	 * @param durata durata in millisecondi della ricerca e aggiunta dei dati
	 * @param dataParser DataParser dal quale ottenere i dati da aggiungere
	 */
	public void AggiungiDatiDueOre(long periodoAggiornamentoDati,long durata, DataParser dataParser)
	{
		Timer timer = new Timer();
		AggiornaDatiMeteoTask task = new AggiornaDatiMeteoTask(this, durata, dataParser);
		timer.scheduleAtFixedRate(task, periodoAggiornamentoDati, periodoAggiornamentoDati);
	}
	
	/**
	 * Crea un clone della ricerca
	 * @return Clone della ricerca
	 */
	public Ricerca clone()
	{
		return new Ricerca(this.toJSONObject());
	}
}
