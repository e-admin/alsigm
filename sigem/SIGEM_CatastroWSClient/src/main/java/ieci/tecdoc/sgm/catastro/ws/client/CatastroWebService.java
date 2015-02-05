/**
 * CatastroWebService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.catastro.ws.client;

public interface CatastroWebService extends java.rmi.Remote {
    public ieci.tecdoc.sgm.catastro.ws.client.RetornoLogico validarReferenciaCatastral(ieci.tecdoc.sgm.catastro.ws.client.RetornoCadena referenciaCatastral) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.catastro.ws.client.Parcelas consultarCatastro(ieci.tecdoc.sgm.catastro.ws.client.RetornoCadena referenciaCatastral) throws java.rmi.RemoteException;
}
