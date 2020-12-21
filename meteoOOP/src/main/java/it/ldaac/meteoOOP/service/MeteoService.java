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
import java.net.MalformedURLException;
import java.text.DateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.Vector;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import it.ldaac.meteoOOP.exceptions.BadRequestException;
import it.ldaac.meteoOOP.exceptions.DateNotValidException;
import it.ldaac.meteoOOP.exceptions.IdNotValidException;
import it.ldaac.meteoOOP.exceptions.StatsNotValidException;
import it.ldaac.meteoOOP.models.Citta;
import it.ldaac.meteoOOP.models.Ricerca;
import it.ldaac.meteoOOP.models.Richiesta;
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
	public JSONObject avviaRicerca(Richiesta richiesta) throws BadRequestException, ParseException, MalformedURLException, IOException
	{
		Ricerca ricerca = new Ricerca(richiesta, this.coordParser, this.dataParser);
		
		for(int i = 0; i<this.ricerche.size(); i++)
			if (this.ricerche.elementAt(i).getId() == ricerca.getId())
				this.ricerche.remove(i);
		
		this.ricerche.add(ricerca);
				
		ricerca.AggiungiDatiDueOre(periodoAggiornamentoDati, richiesta.getDurataAggiornamentoDati(), dataParser);
		
		JSONObject risposta = new JSONObject();
		risposta.put("id_ricerca", ricerca.getId());
		JSONArray dati = new JSONArray();
		for(Citta c : filtri.filtraCittaInNumero(filtri.filtraCittaInRaggio(ricerca.getCitta(), richiesta.getRaggio()), richiesta.getCnt()))
			dati.add(c.toJSONObject());
		risposta.put("dati", dati);
		return risposta;
	}
	
	public Vector<Ricerca> getDataBase()
	{
		return this.ricerche;
	}
	
	public void removeAll()
	{
		this.ricerche.removeAllElements();
	}

	public Ricerca ottieniRicerca (long idRicerca)
	{
		for(Ricerca r : this.ricerche)
			if(r.getId() == idRicerca)
				return r;
		
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public JSONObject generaStats(long idRicerca, String tipoStats, String data1, String data2, int raggio, int cnt) throws StatsNotValidException, IdNotValidException, DateNotValidException 
	{
		Vector<Citta> cittaFiltrate = new Vector<Citta>();
		try {
			if(cnt == 50)
				cittaFiltrate = filtri.filtraCittaInRaggio(this.ottieniRicerca(idRicerca).getCitta(), raggio);
			else
				cittaFiltrate = filtri.filtraCittaInNumero(filtri.filtraCittaInRaggio(this.ottieniRicerca(idRicerca).getCitta(), raggio), cnt);
		} catch (Exception e) {
			throw new IdNotValidException();
		}
		
		for(Citta c : cittaFiltrate)
		{
			if (data2 == null){
				try {
					DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.SHORT);
					Date data = dateFormat.parse(data1);
					c.setDatiMeteo(filtri.filtraDatiMeteo(c.getDatiMeteo(), data));
				} catch (Exception e) {
					throw new DateNotValidException();
				}
			} else {
				try {
					DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.SHORT);
					Date dataInizio = dateFormat.parse(data1);
					Date dataFine = dateFormat.parse(data2);
					c.setDatiMeteo(filtri.filtraDatiMeteo(c.getDatiMeteo(), dataInizio, dataFine));
				} catch (Exception e) {
					throw new DateNotValidException();
				}
			}
		}
		
		JSONObject stats = new JSONObject();
		if(tipoStats.equals("all") || tipoStats.equals("temp")) {
			JSONObject temperatura = new JSONObject();
			JSONObject temperaturaAlta = new JSONObject();
			JSONObject temperaturaBassa = new JSONObject();
			JSONObject maxVarTemp = new JSONObject();
			
			Citta piuCalda = this.statistiche.maxTempMedia(cittaFiltrate);
			temperaturaAlta.put("nome_citta", piuCalda.getNomeCitta());
			temperaturaAlta.put("temp_media", this.statistiche.calcolaTempMedia(piuCalda.getDatiMeteo()));
			temperatura.put("citta_piu_calda", temperaturaAlta);
			
			Citta piuFredda = this.statistiche.minTempMedia(cittaFiltrate);
			temperaturaBassa.put("nome_citta", piuFredda.getNomeCitta());
			temperaturaBassa.put("temp_media", this.statistiche.calcolaTempMedia(piuFredda.getDatiMeteo()));
			temperatura.put("citta_piu_fredda", temperaturaBassa);
			
			Citta piuVariabile = this.statistiche.maxVarTemp(cittaFiltrate);
			maxVarTemp.put("nome_citta", piuVariabile.getNomeCitta());
			maxVarTemp.put("var_temp_media", this.statistiche.calcolaVarianzaTemp(piuVariabile.getDatiMeteo()));
			temperatura.put("citta_piu_variabile", maxVarTemp);
			
			stats.put("stats_temperatura", temperatura);
		}
		
		if(tipoStats.equals("all") || tipoStats.equals("tempPerc")) {
			JSONObject temperaturaPerc = new JSONObject();
			JSONObject temperaturaPercAlta = new JSONObject();
			JSONObject temperaturaPercBassa = new JSONObject();
			JSONObject maxVarTempPerc = new JSONObject();
			
			Citta piuCalda = this.statistiche.maxTempPercMedia(cittaFiltrate);
			temperaturaPercAlta.put("nome_citta", piuCalda.getNomeCitta());
			temperaturaPercAlta.put("temp_perc_media", this.statistiche.calcolaTempPercMedia(piuCalda.getDatiMeteo()));
			temperaturaPerc.put("citta_piu_calda", temperaturaPercAlta);
			
			Citta piuFredda = this.statistiche.minTempPercMedia(cittaFiltrate);
			temperaturaPercBassa.put("nome_citta", piuFredda.getNomeCitta());
			temperaturaPercBassa.put("temp_perc_media", this.statistiche.calcolaTempPercMedia(piuFredda.getDatiMeteo()));
			temperaturaPerc.put("citta_piu_fredda", temperaturaPercBassa);
			
			Citta piuVariabile = this.statistiche.maxVarTempPerc(cittaFiltrate);
			maxVarTempPerc.put("nome_citta", piuVariabile.getNomeCitta());
			maxVarTempPerc.put("var_temp_perc", this.statistiche.calcolaVarianzaTempPerc(piuVariabile.getDatiMeteo()));
			temperaturaPerc.put("citta_piu_variabile", maxVarTempPerc);
			
			stats.put("stats_temperatura_perc", temperaturaPerc);
		}
		
		if(tipoStats.equals("all") || tipoStats.equals("vento")) {
			JSONObject velVento = new JSONObject();
			JSONObject velVentoAlta = new JSONObject();
			JSONObject velVentoBassa = new JSONObject();
			JSONObject maxVarTempPerc = new JSONObject();
			
			Citta piuVentosa = this.statistiche.maxVelVentoMedia(cittaFiltrate);
			velVentoAlta.put("nome_citta", piuVentosa.getNomeCitta());
			velVentoAlta.put("vel_vento_media", this.statistiche.calcolaVelVentoMedia(piuVentosa.getDatiMeteo()));
			velVento.put("citta_piu_ventosa", velVentoAlta);
			
			Citta menoVentosa = this.statistiche.minVelVentoMedia(cittaFiltrate);
			velVentoBassa.put("nome_citta", menoVentosa.getNomeCitta());
			velVentoBassa.put("vel_vento_media", this.statistiche.calcolaVelVentoMedia(menoVentosa.getDatiMeteo()));
			velVento.put("citta_meno_ventosa", velVentoBassa);
			
			Citta piuVariabile = this.statistiche.maxVarVelVento(cittaFiltrate);
			maxVarTempPerc.put("nome_citta", piuVariabile.getNomeCitta());
			maxVarTempPerc.put("var_vel_vento", this.statistiche.calcolaVarianzaVelVento(piuVariabile.getDatiMeteo()));
			velVento.put("citta_piu_variabile", maxVarTempPerc);
			
			stats.put("stats_velocita_vento", velVento);
		}
		
		if(stats.isEmpty())
			throw new StatsNotValidException();
		
		return stats;
	}

	
	
}
