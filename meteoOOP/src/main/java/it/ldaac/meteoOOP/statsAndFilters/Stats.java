package it.ldaac.meteoOOP.statsAndFilters;

import java.util.Vector;

import it.ldaac.meteoOOP.models.Citta;
import it.ldaac.meteoOOP.models.DatoMeteo;

/**
 * 
 * Classe che implementa funzioni per generare statistiche su città e dati meteo

 * @author andreacivitarese, lucadambrosio
 */
public class Stats {
	
	/**
	 * Calcola la temperatura media di un set di dati meteo
	 * 
	 * @param dati Dati meteo sui quali calcolare la statistica
	 * @return Temperatura media dei dati
	 */
	public double calcolaTempMedia(Vector<DatoMeteo> dati)
	{
		double sommaTemp = 0;
		for(DatoMeteo d : dati)
			sommaTemp += d.getTemperatura();
		return (sommaTemp/dati.size());
	}
	
	/**
	 * Calcola la temperatura percepita media di un set di dati meteo
	 * 
	 * @param dati Dati meteo sui quali calcolare la statistica
	 * @return Temperatura percepita media dei dati
	 */
	public double calcolaTempPercMedia(Vector<DatoMeteo> dati)
	{
		double sommaTempPerc = 0;
		for(DatoMeteo d : dati)
			sommaTempPerc += d.getTemperaturaPercepita();
		return (sommaTempPerc/dati.size());
	}
	
	/**
	 * Calcola la velocità del vento media di un set di dati meteo
	 * 
	 * @param dati Dati meteo sui quali calcolare la statistica
	 * @return Velocità del vento media dei dati
	 */
	public double calcolaVelVentoMedia(Vector<DatoMeteo> dati)
	{
		double sommaVelVento = 0;
		for(DatoMeteo d : dati)
			sommaVelVento += d.getVelocitaVento();
		return (sommaVelVento/dati.size());
	}
	
	/**
	 * Calcola la varianza dalla temperatura media di un set di dati meteo
	 * 
	 * @param dati Dati meteo sui quali calcolare la statistica
	 * @return Varianza delle temperature dei dati
	 */
	public double calcolaVarianzaTemp(Vector<DatoMeteo> dati)
	{
		double sommaScartiQuadMedi = 0;
		double tempMedia = this.calcolaTempMedia(dati);
		for(DatoMeteo d : dati)
			sommaScartiQuadMedi += Math.pow(tempMedia-d.getTemperatura(), 2);
		return (sommaScartiQuadMedi/dati.size());
	}
	
	/**
	 * Calcola la varianza dalla temperatura percepita media di un set di dati meteo
	 * 
	 * @param dati Dati meteo sui quali calcolare la statistica
	 * @return Varianza delle temperature percepite dei dati
	 */
	public double calcolaVarianzaTempPerc(Vector<DatoMeteo> dati) 
	{
		double sommaScartiQuadMedi = 0;
		double tempMedia = this.calcolaTempPercMedia(dati);
		for(DatoMeteo d : dati)
			sommaScartiQuadMedi += Math.pow(tempMedia-d.getTemperaturaPercepita(), 2);
		return (sommaScartiQuadMedi/dati.size());
	}
	
	/**
	 * Calcola la varianza dalla velocità del vento media di un set di dati meteo
	 * 
	 * @param dati Dati meteo sui quali calcolare la statistica
	 * @return Varianza delle velocità del vento dei dati
	 */
	public double calcolaVarianzaVelVento(Vector<DatoMeteo> dati) 
	{
		double sommaScartiQuadMedi = 0;
		double velVentoMedia = this.calcolaVelVentoMedia(dati);
		for(DatoMeteo d : dati)
			sommaScartiQuadMedi += Math.pow(velVentoMedia-d.getTemperaturaPercepita(), 2);
		return (sommaScartiQuadMedi/dati.size());
	}

	/**
	 * Restituisce la città con la massima temperatura media
	 * 
	 * @param daAnalizzare Città da analizzare
	 * @return Città con la massima temperatura media
	 */
	public Citta maxTempMedia(Vector<Citta> daAnalizzare)
	{
		Citta ritorno = daAnalizzare.elementAt(0);
		for(Citta c : daAnalizzare)
			if(this.calcolaTempMedia(c.getDatiMeteo()) > this.calcolaTempMedia(ritorno.getDatiMeteo()))
				ritorno = c;
		return ritorno;
	}
	
	/**
	 * Restituisce la città con la minima temperatura media
	 * 
	 * @param daAnalizzare Città da analizzare
	 * @return Città con la minima temperatura media
	 */
	public Citta minTempMedia(Vector<Citta> daAnalizzare)
	{
		Citta ritorno = daAnalizzare.elementAt(0);
		for(Citta c : daAnalizzare)
			if(this.calcolaTempMedia(c.getDatiMeteo()) < this.calcolaTempMedia(ritorno.getDatiMeteo()))
				ritorno = c;
		return ritorno;
	}
	
	/**
	 * Restituisce la città con la massima temperatura percepita media
	 * 
	 * @param daAnalizzare Città da analizzare
	 * @return Città con la massima temperatura percepita media
	 */
	public Citta maxTempPercMedia(Vector<Citta> daAnalizzare)
	{
		Citta ritorno = daAnalizzare.elementAt(0);
		for(Citta c : daAnalizzare)
			if(this.calcolaTempPercMedia(c.getDatiMeteo()) > this.calcolaTempPercMedia(ritorno.getDatiMeteo()))
				ritorno = c;	
		return ritorno;
	}
	
	/**
	 * Restituisce la città con la minima temperatura percepita media
	 * 
	 * @param daAnalizzare Città da analizzare
	 * @return Città con la minima temperatura percepita media
	 */
	public Citta minTempPercMedia(Vector<Citta> daAnalizzare)
	{
		Citta ritorno = daAnalizzare.elementAt(0);
		for(Citta c : daAnalizzare)
			if(this.calcolaTempPercMedia(c.getDatiMeteo()) < this.calcolaTempPercMedia(ritorno.getDatiMeteo()))
				ritorno = c;
		return ritorno;
	}
	
	/**
	 * Restituisce la città con la massima velocità del vento media
	 * 
	 * @param daAnalizzare Città da analizzare
	 * @return Città con la massima velocità del vento media
	 */
	public Citta maxVelVentoMedia(Vector<Citta> daAnalizzare)
	{
		Citta ritorno = daAnalizzare.elementAt(0);
		for(Citta c : daAnalizzare)
			if(this.calcolaVelVentoMedia(c.getDatiMeteo()) > this.calcolaVelVentoMedia(ritorno.getDatiMeteo()))
				ritorno = c;
		return ritorno;
	}
	
	/**
	 * Restituisce la città con la minima velocità del vento media
	 * 
	 * @param daAnalizzare Città da analizzare
	 * @return Città con la minima velocità del vento media
	 */
	public Citta minVelVentoMedia(Vector<Citta> daAnalizzare)
	{
		Citta ritorno = daAnalizzare.elementAt(0);
		for(Citta c : daAnalizzare)
			if(this.calcolaVelVentoMedia(c.getDatiMeteo()) < this.calcolaVelVentoMedia(ritorno.getDatiMeteo()))
				ritorno = c;	
		return ritorno;
	}
	
	/**
	 * Restituisce la città con la massima varianza delle temperature
	 * 
	 * @param daAnalizzare Città da analizzare
	 * @return Città con la massima varianza delle temperature
	 */
	public Citta maxVarTemp(Vector<Citta> daAnalizzare)
	{
		Citta ritorno = daAnalizzare.elementAt(0);
		for(Citta c : daAnalizzare)
			if(this.calcolaVarianzaTemp(c.getDatiMeteo()) > this.calcolaVarianzaTemp(ritorno.getDatiMeteo()))
				ritorno = c;
		return ritorno;
	}
	
	/**
	 * Restituisce la città con la massima varianza delle temperature percepite
	 * 
	 * @param daAnalizzare Città da analizzare
	 * @return Città con la massima varianza delle temperature percepite
	 */
	public Citta maxVarTempPerc(Vector<Citta> daAnalizzare)
	{
		Citta ritorno = daAnalizzare.elementAt(0);
		for(Citta c : daAnalizzare)
			if(this.calcolaVarianzaTempPerc(c.getDatiMeteo()) > this.calcolaVarianzaTempPerc(ritorno.getDatiMeteo()))
				ritorno = c;
		return ritorno;
	}
	
	/**
	 * Restituisce la città con la massima varianza delle velocità del vento
	 * 
	 * @param daAnalizzare Città da analizzare
	 * @return Città con la massima varianza delle velocità del vento
	 */
	public Citta maxVarVelVento(Vector<Citta> daAnalizzare)
	{
		Citta ritorno = daAnalizzare.elementAt(0);
		for(Citta c : daAnalizzare)
			if(this.calcolaVarianzaVelVento(c.getDatiMeteo()) > this.calcolaVarianzaVelVento(ritorno.getDatiMeteo()))
				ritorno = c;
		return ritorno;
	}
}
