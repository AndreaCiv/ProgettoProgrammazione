/**
 * 
 */
package it.ldaac.meteoOOP.statsAndFilters;

import java.util.Vector;

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
}