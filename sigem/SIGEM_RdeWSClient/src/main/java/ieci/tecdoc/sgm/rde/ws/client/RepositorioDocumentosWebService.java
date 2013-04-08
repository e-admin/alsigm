/**
 * RepositorioDocumentosWebService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.rde.ws.client;

public interface RepositorioDocumentosWebService extends java.rmi.Remote {
    public ieci.tecdoc.sgm.rde.ws.client.InfoDocumento recuperarDocumento(ieci.tecdoc.sgm.rde.ws.client.InfoDocumento poDocId, ieci.tecdoc.sgm.rde.ws.client.Entidad entidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.rde.ws.client.InfoDocumento guardarDocumento(ieci.tecdoc.sgm.rde.ws.client.InfoDocumento poDoc, ieci.tecdoc.sgm.rde.ws.client.Entidad entidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.rde.ws.client.InfoDocumento guardarDocumentoGuid(ieci.tecdoc.sgm.rde.ws.client.InfoDocumento poDoc, ieci.tecdoc.sgm.rde.ws.client.Entidad entidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.rde.ws.client.RetornoServicio eliminarDocumento(ieci.tecdoc.sgm.rde.ws.client.InfoDocumento poDoc, ieci.tecdoc.sgm.rde.ws.client.Entidad entidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.rde.ws.client.InfoDocumento obtenerHash(ieci.tecdoc.sgm.rde.ws.client.InfoDocumento poDoc, ieci.tecdoc.sgm.rde.ws.client.Entidad entidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.rde.ws.client.RetornoServicio almacenarDocumentos(ieci.tecdoc.sgm.rde.ws.client.InfoDocumentos poDocs, ieci.tecdoc.sgm.rde.ws.client.Entidad entidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.rde.ws.client.RetornoServicio almacenarDocumentoTemporal(ieci.tecdoc.sgm.rde.ws.client.InfoDocumentoTemporal poDoc, ieci.tecdoc.sgm.rde.ws.client.Entidad entidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.rde.ws.client.RetornoServicio eliminarDocumentoTemporal(ieci.tecdoc.sgm.rde.ws.client.InfoDocumentoTemporal poDoc, ieci.tecdoc.sgm.rde.ws.client.Entidad entidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.rde.ws.client.InfoDocumentosTemporales obtenerDocumentosTemporales(ieci.tecdoc.sgm.rde.ws.client.InfoDocumentoTemporal poDoc, ieci.tecdoc.sgm.rde.ws.client.Entidad entidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.rde.ws.client.InfoDocumentosTemporales obtenerDocumentosTemporalesCaducados(ieci.tecdoc.sgm.rde.ws.client.CriterioBusquedaDocs poDocs, ieci.tecdoc.sgm.rde.ws.client.Entidad entidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.rde.ws.client.ContenedorDocumento retrieveDocumentInfo(ieci.tecdoc.sgm.rde.ws.client.InfoDocumento poInfo, ieci.tecdoc.sgm.rde.ws.client.Entidad entidad) throws java.rmi.RemoteException;
}
