//
// Generated By:JAX-WS RI IBM 2.2.1-11/28/2011 08:28 AM(foreman)- (JAXB RI IBM 2.2.3-11/28/2011 06:21 AM(foreman)-)
//


package com.camixo.posiljanjeobvestilstoritev.shema;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.camixo.posiljanjeobvestilstoritev.shema package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _PosljiSporocilo_QNAME = new QName("http://www.camixo.com/PosiljanjeObvestilStoritev/shema", "posljiSporocilo");
    private final static QName _PosljiSporociloResponse_QNAME = new QName("http://www.camixo.com/PosiljanjeObvestilStoritev/shema", "posljiSporociloResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.camixo.posiljanjeobvestilstoritev.shema
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link SporociloTip }
     * 
     */
    public SporociloTip createSporociloTip() {
        return new SporociloTip();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SporociloTip }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.camixo.com/PosiljanjeObvestilStoritev/shema", name = "posljiSporocilo")
    public JAXBElement<SporociloTip> createPosljiSporocilo(SporociloTip value) {
        return new JAXBElement<SporociloTip>(_PosljiSporocilo_QNAME, SporociloTip.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RezultatTip }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.camixo.com/PosiljanjeObvestilStoritev/shema", name = "posljiSporociloResponse")
    public JAXBElement<RezultatTip> createPosljiSporociloResponse(RezultatTip value) {
        return new JAXBElement<RezultatTip>(_PosljiSporociloResponse_QNAME, RezultatTip.class, null, value);
    }

}