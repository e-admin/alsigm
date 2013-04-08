/*
 * Created on 07-nov-2005
 *
 */
package ieci.tdw.ispac.ispacpublicador.business.service;

import ieci.tdw.ispac.ispacpublicador.business.bi.IPublisherBI;

/**
 * @author Ildefonso Noreña
 * Localizador de Servicios. 
 */
public class ServiceLocator {
    
    /**
     * @return un servicio que implemente las funciones ofrecidas por el Business Interface del Publicador.
     */
    public static IPublisherBI lookupPublisherBI(){
        return new PublisherService();
    }
}
