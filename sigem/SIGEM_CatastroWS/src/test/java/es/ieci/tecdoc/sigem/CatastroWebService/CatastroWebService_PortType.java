/**
 * CatastroWebService_PortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package es.ieci.tecdoc.sigem.CatastroWebService;

public interface CatastroWebService_PortType extends java.rmi.Remote {
    public es.ieci.tecdoc.sigem.CatastroWebService.RetornoLogico validarReferenciaCatastral(es.ieci.tecdoc.sigem.CatastroWebService.RetornoCadena referenciaCatastral) throws java.rmi.RemoteException;
    public es.ieci.tecdoc.sigem.CatastroWebService.Parcelas consultarCatastro(es.ieci.tecdoc.sigem.CatastroWebService.RetornoCadena referenciaCatastral) throws java.rmi.RemoteException;
}
