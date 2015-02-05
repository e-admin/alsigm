/**
 * ClienteLigeroSoapBindingImpl.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package es.ieci.scsp.verifdata.services;

import java.util.List;

import es.ieci.scsp.verifdata.model.dao.ClienteLigeroDAO;
import es.ieci.scsp.verifdata.model.dao.ScspDAO;
import es.ieci.scsp.verifdata.model.dao.Servicio;
import es.ieci.scsp.verifdata.model.mapping.scsp.CoreServicio;

public class ClienteLigeroSoapBindingImpl implements es.ieci.scsp.verifdata.services.ClienteLigero{
    public es.ieci.scsp.verifdata.model.dao.Servicio[] consultaProcedimientoByNIF(java.lang.String nifFuncionario, java.lang.String codigoProcedimiento) throws java.rmi.RemoteException {
    	 ClienteLigeroDAO clienteLigero = new ClienteLigeroDAO();
         Servicio resProc[] = null;
         List vProcedimiento = clienteLigero.getProcedimientoByDNI(nifFuncionario, codigoProcedimiento);
         if(vProcedimiento != null)
         {
             ScspDAO scspDAO = new ScspDAO();
             List vCoreServicio = scspDAO.getDescripcionProcedimiento(vProcedimiento);
             resProc = new Servicio[vProcedimiento.size()];
             for(int i = 0; i < vProcedimiento.size(); i++)
             {
                 CoreServicio coreServicio = (CoreServicio)vCoreServicio.get(i);
                 String codCertificado = coreServicio.getCodcertificado();
                 String descrp = coreServicio.getDescripcion();
                 String emisor = coreServicio.getCoreEmisorCertificado().getNombre();
                 resProc[i] = new Servicio(codCertificado, emisor, descrp);
             }

         } else
         {
             resProc = new Servicio[0];
         }
         return resProc;

    }

}
