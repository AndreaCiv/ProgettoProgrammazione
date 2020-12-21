/**
 * 
 */
package it.ldaac.meteoOOP.statsAndFilters;

import java.util.Vector;

import org.json.simple.JSONObject;

import it.ldaac.meteoOOP.models.Citta;
import it.ldaac.meteoOOP.models.DatoMeteo;

/**
 * @author andreacivitarese
 *
 */
public class Stats {
	
	public double calcolaTempMedia(Vector<DatoMeteo> dati)
	{
		double sommaTemp = 0;
		for(DatoMeteo d : dati)
			sommaTemp += d.getTemperatura();
		return (sommaTemp/dati.size());
	}
	
	public double calcolaTempPercMedia(Vector<DatoMeteo> dati)
	{
		double sommaTempPerc = 0;
		for(DatoMeteo d : dati)
			sommaTempPerc += d.getTemperaturaPercepita();
		return (sommaTempPerc/dati.size());
	}
	
	public double calcolaVelVentoMedia(Vector<DatoMeteo> dati)
	{
		double sommaVelVento = 0;
		for(DatoMeteo d : dati)
			sommaVelVento += d.getVelocitaVento();
		return (sommaVelVento/dati.size());
	}
	
	public double calcolaVarianzaTemp(Vector<DatoMeteo> dati)
	{
		double sommaScartiQuadMedi = 0;
		double tempMedia = this.calcolaTempMedia(dati);
		for(DatoMeteo d : dati)
			sommaScartiQuadMedi += Math.pow(tempMedia-d.getTemperatura(), 2);
		return (sommaScartiQuadMedi/dati.size());
	}
	
	public double calcolaVarianzaTempPerc(Vector<DatoMeteo> dati) 
	{
		double sommaScartiQuadMedi = 0;
		double tempMedia = this.calcolaTempPercMedia(dati);
		for(DatoMeteo d : dati)
			sommaScartiQuadMedi += Math.pow(tempMedia-d.getTemperaturaPercepita(), 2);
		return (sommaScartiQuadMedi/dati.size());
	}
	
	public double calcolaVarianzaVelVento(Vector<DatoMeteo> dati) 
	{
		double sommaScartiQuadMedi = 0;
		double velVentoMedia = this.calcolaVelVentoMedia(dati);
		for(DatoMeteo d : dati)
			sommaScartiQuadMedi += Math.pow(velVentoMedia-d.getTemperaturaPercepita(), 2);
		return (sommaScartiQuadMedi/dati.size());
	}
	
	public Vector<Double> calcolaStats(Vector<DatoMeteo> dati)
	{
		Vector<Double> stats = new Vector<Double>();
		
		stats.add(this.calcolaTempMedia(dati));
		stats.add(this.calcolaVarianzaTemp(dati));	

		stats.add(this.calcolaTempPercMedia(dati));
		stats.add(this.calcolaVarianzaTempPerc(dati));
		
		stats.add(this.calcolaVelVentoMedia(dati));
		stats.add(this.calcolaVarianzaVelVento(dati));
			
		return stats;
	}
	
	public Citta maxTempMedia(Vector<Citta> daAnalizzare)
	{
		Citta ritorno = daAnalizzare.elementAt(0);
		for(Citta c : daAnalizzare)
			if(this.calcolaTempMedia(c.getDatiMeteo()) > this.calcolaTempMedia(ritorno.getDatiMeteo()))
				ritorno = c;
		return ritorno;
	}
	
	public Citta minTempMedia(Vector<Citta> daAnalizzare)
	{
		Citta ritorno = daAnalizzare.elementAt(0);
		for(Citta c : daAnalizzare)
			if(this.calcolaTempMedia(c.getDatiMeteo()) < this.calcolaTempMedia(ritorno.getDatiMeteo()))
				ritorno = c;
		return ritorno;
	}
	
	public Citta maxTempPercMedia(Vector<Citta> daAnalizzare)
	{
		Citta ritorno = daAnalizzare.elementAt(0);
		for(Citta c : daAnalizzare)
			if(this.calcolaTempPercMedia(c.getDatiMeteo()) > this.calcolaTempPercMedia(ritorno.getDatiMeteo()))
				ritorno = c;	
		return ritorno;
	}
	
	public Citta minTempPercMedia(Vector<Citta> daAnalizzare)
	{
		Citta ritorno = daAnalizzare.elementAt(0);
		for(Citta c : daAnalizzare)
			if(this.calcolaTempPercMedia(c.getDatiMeteo()) < this.calcolaTempPercMedia(ritorno.getDatiMeteo()))
				ritorno = c;
		return ritorno;
	}
	
	public Citta maxVelVentoMedia(Vector<Citta> daAnalizzare)
	{
		Citta ritorno = daAnalizzare.elementAt(0);
		for(Citta c : daAnalizzare)
			if(this.calcolaVelVentoMedia(c.getDatiMeteo()) > this.calcolaVelVentoMedia(ritorno.getDatiMeteo()))
				ritorno = c;
		return ritorno;
	}
	
	public Citta minVelVentoMedia(Vector<Citta> daAnalizzare)
	{
		Citta ritorno = daAnalizzare.elementAt(0);
		for(Citta c : daAnalizzare)
			if(this.calcolaVelVentoMedia(c.getDatiMeteo()) < this.calcolaVelVentoMedia(ritorno.getDatiMeteo()))
				ritorno = c;	
		return ritorno;
	}
	
	public Citta maxVarTemp(Vector<Citta> daAnalizzare)
	{
		Citta ritorno = daAnalizzare.elementAt(0);
		for(Citta c : daAnalizzare)
			if(this.calcolaVarianzaTemp(c.getDatiMeteo()) > this.calcolaVarianzaTemp(ritorno.getDatiMeteo()))
				ritorno = c;
		return ritorno;
	}
	
	public Citta maxVarTempPerc(Vector<Citta> daAnalizzare)
	{
		Citta ritorno = daAnalizzare.elementAt(0);
		for(Citta c : daAnalizzare)
			if(this.calcolaVarianzaTempPerc(c.getDatiMeteo()) > this.calcolaVarianzaTempPerc(ritorno.getDatiMeteo()))
				ritorno = c;
		return ritorno;
	}
	
	public Citta maxVarVelVento(Vector<Citta> daAnalizzare)
	{
		Citta ritorno = daAnalizzare.elementAt(0);
		for(Citta c : daAnalizzare)
			if(this.calcolaVarianzaVelVento(c.getDatiMeteo()) > this.calcolaVarianzaVelVento(ritorno.getDatiMeteo()))
				ritorno = c;
		return ritorno;
	}
}
