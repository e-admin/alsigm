/**
 * CatalogoTramitesWebService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package ieci.tecdoc.sgm.catalogo.ws.client;

public interface CatalogoTramitesWebService extends java.rmi.Remote {
    public ieci.tecdoc.sgm.catalogo.ws.client.Tramites query(ieci.tecdoc.sgm.catalogo.ws.client.TramiteConsulta query, ieci.tecdoc.sgm.catalogo.ws.client.Entidad entidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.catalogo.ws.client.Documento getDocument(java.lang.String documentId, ieci.tecdoc.sgm.catalogo.ws.client.Entidad entidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.catalogo.ws.client.Documentos getDocuments(ieci.tecdoc.sgm.catalogo.ws.client.Entidad entidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.catalogo.ws.client.RetornoServicio updateDocument(ieci.tecdoc.sgm.catalogo.ws.client.Documento document, ieci.tecdoc.sgm.catalogo.ws.client.Entidad entidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.catalogo.ws.client.RetornoServicio addProcedure(ieci.tecdoc.sgm.catalogo.ws.client.Tramite procedure, ieci.tecdoc.sgm.catalogo.ws.client.Entidad entidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.catalogo.ws.client.Tramite getProcedure(java.lang.String procedureId, ieci.tecdoc.sgm.catalogo.ws.client.RetornoLogico loadDocuments, ieci.tecdoc.sgm.catalogo.ws.client.Entidad entidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.catalogo.ws.client.RetornoServicio deleteProcedure(java.lang.String procedureId, ieci.tecdoc.sgm.catalogo.ws.client.Entidad entidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.catalogo.ws.client.RetornoServicio updateProcedure(ieci.tecdoc.sgm.catalogo.ws.client.Tramite procedure, ieci.tecdoc.sgm.catalogo.ws.client.Entidad entidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.catalogo.ws.client.RetornoLogico isDocumentReferenced(java.lang.String documentId, ieci.tecdoc.sgm.catalogo.ws.client.Entidad entidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.catalogo.ws.client.RetornoServicio addProcedureDocument(ieci.tecdoc.sgm.catalogo.ws.client.DocumentoTramite procedureDocument, ieci.tecdoc.sgm.catalogo.ws.client.Entidad entidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.catalogo.ws.client.RetornoServicio deleteProcedureDocument(ieci.tecdoc.sgm.catalogo.ws.client.DocumentoTramite procedureDocument, ieci.tecdoc.sgm.catalogo.ws.client.Entidad entidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.catalogo.ws.client.RetornoServicio addDocument(ieci.tecdoc.sgm.catalogo.ws.client.Documento document, ieci.tecdoc.sgm.catalogo.ws.client.Entidad entidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.catalogo.ws.client.RetornoServicio deleteDocument(java.lang.String documentId, ieci.tecdoc.sgm.catalogo.ws.client.Entidad entidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.catalogo.ws.client.Documentos getProcedureDocuments(java.lang.String procedureId, ieci.tecdoc.sgm.catalogo.ws.client.Entidad entidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.catalogo.ws.client.DocumentoTramite getProcedureDocument(java.lang.String procedureId, java.lang.String documentId, ieci.tecdoc.sgm.catalogo.ws.client.Entidad entidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.catalogo.ws.client.DocumentoTramite getProcedureDocumentByCode(java.lang.String procedureId, java.lang.String documentId, java.lang.String code, ieci.tecdoc.sgm.catalogo.ws.client.Entidad entidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.catalogo.ws.client.RetornoServicio updateProcedureDocument(ieci.tecdoc.sgm.catalogo.ws.client.DocumentoTramite procedureDocument, ieci.tecdoc.sgm.catalogo.ws.client.Entidad entidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.catalogo.ws.client.Tramites getProcedures(ieci.tecdoc.sgm.catalogo.ws.client.Entidad entidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.catalogo.ws.client.Documento getDocumentfromCode(java.lang.String code, ieci.tecdoc.sgm.catalogo.ws.client.Entidad entidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.catalogo.ws.client.OrganoDestinatario getAddressee(java.lang.String addresseeId, ieci.tecdoc.sgm.catalogo.ws.client.Entidad entidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.catalogo.ws.client.RetornoServicio addAddressee(ieci.tecdoc.sgm.catalogo.ws.client.OrganoDestinatario addressee, ieci.tecdoc.sgm.catalogo.ws.client.Entidad entidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.catalogo.ws.client.RetornoServicio deleteAddressee(java.lang.String addresseeId, ieci.tecdoc.sgm.catalogo.ws.client.Entidad entidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.catalogo.ws.client.RetornoServicio updateAddressee(ieci.tecdoc.sgm.catalogo.ws.client.OrganoDestinatario addressee, ieci.tecdoc.sgm.catalogo.ws.client.Entidad entidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.catalogo.ws.client.OrganosDestinatarios getAddressees(ieci.tecdoc.sgm.catalogo.ws.client.Entidad entidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.catalogo.ws.client.Conector getHook(java.lang.String hookId, ieci.tecdoc.sgm.catalogo.ws.client.Entidad entidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.catalogo.ws.client.RetornoServicio addHook(ieci.tecdoc.sgm.catalogo.ws.client.Conector hook, ieci.tecdoc.sgm.catalogo.ws.client.Entidad entidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.catalogo.ws.client.RetornoServicio deleteHook(java.lang.String hookId, ieci.tecdoc.sgm.catalogo.ws.client.Entidad entidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.catalogo.ws.client.RetornoServicio updateHook(ieci.tecdoc.sgm.catalogo.ws.client.Conector hook, ieci.tecdoc.sgm.catalogo.ws.client.Entidad entidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.catalogo.ws.client.Conectores getHooks(ieci.tecdoc.sgm.catalogo.ws.client.Entidad entidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.catalogo.ws.client.Conectores getHooksByType(java.lang.String hookType, ieci.tecdoc.sgm.catalogo.ws.client.Entidad entidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.catalogo.ws.client.TipoConector getHookType(java.lang.String typeId, ieci.tecdoc.sgm.catalogo.ws.client.Entidad entidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.catalogo.ws.client.RetornoServicio addHookType(ieci.tecdoc.sgm.catalogo.ws.client.TipoConector hookType, ieci.tecdoc.sgm.catalogo.ws.client.Entidad entidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.catalogo.ws.client.RetornoServicio deleteHookType(java.lang.String typeId, ieci.tecdoc.sgm.catalogo.ws.client.Entidad entidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.catalogo.ws.client.RetornoServicio updateHookType(ieci.tecdoc.sgm.catalogo.ws.client.TipoConector hookType, ieci.tecdoc.sgm.catalogo.ws.client.Entidad entidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.catalogo.ws.client.TiposConectores getHookTypes(ieci.tecdoc.sgm.catalogo.ws.client.Entidad entidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.catalogo.ws.client.ConectoresAutenticacion getAuthHooks(java.lang.String tramiteId, ieci.tecdoc.sgm.catalogo.ws.client.Entidad entidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.catalogo.ws.client.RetornoServicio addAuthHooks(ieci.tecdoc.sgm.catalogo.ws.client.ConectorAutenticacion authHook, ieci.tecdoc.sgm.catalogo.ws.client.Entidad entidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.catalogo.ws.client.RetornoServicio deleteAuthHooks(java.lang.String tramiteId, java.lang.String conectorId, ieci.tecdoc.sgm.catalogo.ws.client.Entidad entidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.catalogo.ws.client.RetornoServicio updateAuthHooks(ieci.tecdoc.sgm.catalogo.ws.client.ConectorAutenticacion conectorAutenticacion, java.lang.String oldHookId, ieci.tecdoc.sgm.catalogo.ws.client.Entidad entidad) throws java.rmi.RemoteException;
    public ieci.tecdoc.sgm.catalogo.ws.client.ConectorAutenticacion getAuthHook(java.lang.String tramiteId, java.lang.String conectorId, ieci.tecdoc.sgm.catalogo.ws.client.Entidad entidad) throws java.rmi.RemoteException;
}
