package com.camixo.posiljanjeobvestilstoritev;

import javax.ejb.EJB;
import ejb.PosiljanjeObvestilBean;

import com.camixo.posiljanjeobvestilstoritev.shema.RezultatTip;
import com.camixo.posiljanjeobvestilstoritev.shema.SporociloTip;


@javax.jws.WebService (endpointInterface="com.camixo.posiljanjeobvestilstoritev.PosiljanjeObvestilStoritev", targetNamespace="http://www.camixo.com/PosiljanjeObvestilStoritev/", serviceName="PosiljanjeObvestilStoritev", portName="PosiljanjeObvestilStoritevSOAP")
public class PosiljanjeObvestilStoritevSOAPImpl{

	@EJB
	private PosiljanjeObvestilBean pob;
	
    public RezultatTip posljiSporocilo(SporociloTip parameters) {
        try
    	{
        	pob.posljiObvestilo(parameters.getEmail(), parameters.getNaziv(), parameters.getVsebina());
        	return RezultatTip.OK;
    	}
        catch(Exception e)
        {
        	return RezultatTip.ERROR;
        }
        
    }

}