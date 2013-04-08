/**
 * ClienteLigero.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package es.ieci.scsp.verifdata.services;

public interface ClienteLigero extends java.rmi.Remote {
    public es.ieci.scsp.verifdata.model.dao.Servicio[] consultaProcedimientoByNIF(java.lang.String nifFuncionario, java.lang.String codigoProcedimiento) throws java.rmi.RemoteException;
}
