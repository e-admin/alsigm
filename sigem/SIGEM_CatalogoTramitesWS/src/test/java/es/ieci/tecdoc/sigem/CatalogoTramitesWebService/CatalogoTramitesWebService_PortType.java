/**
 * CatalogoTramitesWebService_PortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package es.ieci.tecdoc.sigem.CatalogoTramitesWebService;

public interface CatalogoTramitesWebService_PortType extends java.rmi.Remote {
    public es.ieci.tecdoc.sigem.CatalogoTramitesWebService.Tramites query(es.ieci.tecdoc.sigem.CatalogoTramitesWebService.TramiteConsulta query, es.ieci.tecdoc.sigem.CatalogoTramitesWebService.Entidad entidad) throws java.rmi.RemoteException;
    public es.ieci.tecdoc.sigem.CatalogoTramitesWebService.Documento getDocument(java.lang.String documentId, es.ieci.tecdoc.sigem.CatalogoTramitesWebService.Entidad entidad) throws java.rmi.RemoteException;
    public es.ieci.tecdoc.sigem.CatalogoTramitesWebService.Documentos getDocuments(es.ieci.tecdoc.sigem.CatalogoTramitesWebService.Entidad entidad) throws java.rmi.RemoteException;
    public es.ieci.tecdoc.sigem.CatalogoTramitesWebService.RetornoServicio updateDocument(es.ieci.tecdoc.sigem.CatalogoTramitesWebService.Documento document, es.ieci.tecdoc.sigem.CatalogoTramitesWebService.Entidad entidad) throws java.rmi.RemoteException;
    public es.ieci.tecdoc.sigem.CatalogoTramitesWebService.RetornoServicio addProcedure(es.ieci.tecdoc.sigem.CatalogoTramitesWebService.Tramite procedure, es.ieci.tecdoc.sigem.CatalogoTramitesWebService.Entidad entidad) throws java.rmi.RemoteException;
    public es.ieci.tecdoc.sigem.CatalogoTramitesWebService.Tramite getProcedure(java.lang.String procedureId, es.ieci.tecdoc.sigem.CatalogoTramitesWebService.RetornoLogico loadDocuments, es.ieci.tecdoc.sigem.CatalogoTramitesWebService.Entidad entidad) throws java.rmi.RemoteException;
    public es.ieci.tecdoc.sigem.CatalogoTramitesWebService.RetornoServicio deleteProcedure(java.lang.String procedureId, es.ieci.tecdoc.sigem.CatalogoTramitesWebService.Entidad entidad) throws java.rmi.RemoteException;
    public es.ieci.tecdoc.sigem.CatalogoTramitesWebService.RetornoServicio updateProcedure(es.ieci.tecdoc.sigem.CatalogoTramitesWebService.Tramite procedure, es.ieci.tecdoc.sigem.CatalogoTramitesWebService.Entidad entidad) throws java.rmi.RemoteException;
    public es.ieci.tecdoc.sigem.CatalogoTramitesWebService.RetornoLogico isDocumentReferenced(java.lang.String documentId, es.ieci.tecdoc.sigem.CatalogoTramitesWebService.Entidad entidad) throws java.rmi.RemoteException;
    public es.ieci.tecdoc.sigem.CatalogoTramitesWebService.RetornoServicio addProcedureDocument(es.ieci.tecdoc.sigem.CatalogoTramitesWebService.DocumentoTramite procedureDocument, es.ieci.tecdoc.sigem.CatalogoTramitesWebService.Entidad entidad) throws java.rmi.RemoteException;
    public es.ieci.tecdoc.sigem.CatalogoTramitesWebService.RetornoServicio deleteProcedureDocument(es.ieci.tecdoc.sigem.CatalogoTramitesWebService.DocumentoTramite procedureDocument, es.ieci.tecdoc.sigem.CatalogoTramitesWebService.Entidad entidad) throws java.rmi.RemoteException;
    public es.ieci.tecdoc.sigem.CatalogoTramitesWebService.RetornoServicio addDocument(es.ieci.tecdoc.sigem.CatalogoTramitesWebService.Documento document, es.ieci.tecdoc.sigem.CatalogoTramitesWebService.Entidad entidad) throws java.rmi.RemoteException;
    public es.ieci.tecdoc.sigem.CatalogoTramitesWebService.RetornoServicio deleteDocument(java.lang.String documentId, es.ieci.tecdoc.sigem.CatalogoTramitesWebService.Entidad entidad) throws java.rmi.RemoteException;
    public es.ieci.tecdoc.sigem.CatalogoTramitesWebService.Documentos getProcedureDocuments(java.lang.String procedureId, es.ieci.tecdoc.sigem.CatalogoTramitesWebService.Entidad entidad) throws java.rmi.RemoteException;
    public es.ieci.tecdoc.sigem.CatalogoTramitesWebService.DocumentoTramite getProcedureDocument(java.lang.String procedureId, java.lang.String documentId, es.ieci.tecdoc.sigem.CatalogoTramitesWebService.Entidad entidad) throws java.rmi.RemoteException;
    public es.ieci.tecdoc.sigem.CatalogoTramitesWebService.DocumentoTramite getProcedureDocumentByCode(java.lang.String procedureId, java.lang.String documentId, java.lang.String code, es.ieci.tecdoc.sigem.CatalogoTramitesWebService.Entidad entidad) throws java.rmi.RemoteException;
    public es.ieci.tecdoc.sigem.CatalogoTramitesWebService.RetornoServicio updateProcedureDocument(es.ieci.tecdoc.sigem.CatalogoTramitesWebService.DocumentoTramite procedureDocument, es.ieci.tecdoc.sigem.CatalogoTramitesWebService.Entidad entidad) throws java.rmi.RemoteException;
    public es.ieci.tecdoc.sigem.CatalogoTramitesWebService.Tramites getProcedures(es.ieci.tecdoc.sigem.CatalogoTramitesWebService.Entidad entidad) throws java.rmi.RemoteException;
    public es.ieci.tecdoc.sigem.CatalogoTramitesWebService.Documento getDocumentfromCode(java.lang.String code, es.ieci.tecdoc.sigem.CatalogoTramitesWebService.Entidad entidad) throws java.rmi.RemoteException;
    public es.ieci.tecdoc.sigem.CatalogoTramitesWebService.OrganoDestinatario getAddressee(java.lang.String addresseeId, es.ieci.tecdoc.sigem.CatalogoTramitesWebService.Entidad entidad) throws java.rmi.RemoteException;
    public es.ieci.tecdoc.sigem.CatalogoTramitesWebService.RetornoServicio addAddressee(es.ieci.tecdoc.sigem.CatalogoTramitesWebService.OrganoDestinatario addressee, es.ieci.tecdoc.sigem.CatalogoTramitesWebService.Entidad entidad) throws java.rmi.RemoteException;
    public es.ieci.tecdoc.sigem.CatalogoTramitesWebService.RetornoServicio deleteAddressee(java.lang.String addresseeId, es.ieci.tecdoc.sigem.CatalogoTramitesWebService.Entidad entidad) throws java.rmi.RemoteException;
    public es.ieci.tecdoc.sigem.CatalogoTramitesWebService.RetornoServicio updateAddressee(es.ieci.tecdoc.sigem.CatalogoTramitesWebService.OrganoDestinatario addressee, es.ieci.tecdoc.sigem.CatalogoTramitesWebService.Entidad entidad) throws java.rmi.RemoteException;
    public es.ieci.tecdoc.sigem.CatalogoTramitesWebService.OrganosDestinatarios getAddressees(es.ieci.tecdoc.sigem.CatalogoTramitesWebService.Entidad entidad) throws java.rmi.RemoteException;
    public es.ieci.tecdoc.sigem.CatalogoTramitesWebService.Conector getHook(java.lang.String hookId, es.ieci.tecdoc.sigem.CatalogoTramitesWebService.Entidad entidad) throws java.rmi.RemoteException;
    public es.ieci.tecdoc.sigem.CatalogoTramitesWebService.RetornoServicio addHook(es.ieci.tecdoc.sigem.CatalogoTramitesWebService.Conector hook, es.ieci.tecdoc.sigem.CatalogoTramitesWebService.Entidad entidad) throws java.rmi.RemoteException;
    public es.ieci.tecdoc.sigem.CatalogoTramitesWebService.RetornoServicio deleteHook(java.lang.String hookId, es.ieci.tecdoc.sigem.CatalogoTramitesWebService.Entidad entidad) throws java.rmi.RemoteException;
    public es.ieci.tecdoc.sigem.CatalogoTramitesWebService.RetornoServicio updateHook(es.ieci.tecdoc.sigem.CatalogoTramitesWebService.Conector hook, es.ieci.tecdoc.sigem.CatalogoTramitesWebService.Entidad entidad) throws java.rmi.RemoteException;
    public es.ieci.tecdoc.sigem.CatalogoTramitesWebService.Conectores getHooks(es.ieci.tecdoc.sigem.CatalogoTramitesWebService.Entidad entidad) throws java.rmi.RemoteException;
    public es.ieci.tecdoc.sigem.CatalogoTramitesWebService.Conectores getHooksByType(java.lang.String hookType, es.ieci.tecdoc.sigem.CatalogoTramitesWebService.Entidad entidad) throws java.rmi.RemoteException;
    public es.ieci.tecdoc.sigem.CatalogoTramitesWebService.TipoConector getHookType(java.lang.String typeId, es.ieci.tecdoc.sigem.CatalogoTramitesWebService.Entidad entidad) throws java.rmi.RemoteException;
    public es.ieci.tecdoc.sigem.CatalogoTramitesWebService.RetornoServicio addHookType(es.ieci.tecdoc.sigem.CatalogoTramitesWebService.TipoConector hookType, es.ieci.tecdoc.sigem.CatalogoTramitesWebService.Entidad entidad) throws java.rmi.RemoteException;
    public es.ieci.tecdoc.sigem.CatalogoTramitesWebService.RetornoServicio deleteHookType(java.lang.String typeId, es.ieci.tecdoc.sigem.CatalogoTramitesWebService.Entidad entidad) throws java.rmi.RemoteException;
    public es.ieci.tecdoc.sigem.CatalogoTramitesWebService.RetornoServicio updateHookType(es.ieci.tecdoc.sigem.CatalogoTramitesWebService.TipoConector hookType, es.ieci.tecdoc.sigem.CatalogoTramitesWebService.Entidad entidad) throws java.rmi.RemoteException;
    public es.ieci.tecdoc.sigem.CatalogoTramitesWebService.TiposConectores getHookTypes(es.ieci.tecdoc.sigem.CatalogoTramitesWebService.Entidad entidad) throws java.rmi.RemoteException;
    public es.ieci.tecdoc.sigem.CatalogoTramitesWebService.ConectoresAutenticacion getAuthHooks(java.lang.String tramiteId, es.ieci.tecdoc.sigem.CatalogoTramitesWebService.Entidad entidad) throws java.rmi.RemoteException;
    public es.ieci.tecdoc.sigem.CatalogoTramitesWebService.RetornoServicio addAuthHooks(es.ieci.tecdoc.sigem.CatalogoTramitesWebService.ConectorAutenticacion authHook, es.ieci.tecdoc.sigem.CatalogoTramitesWebService.Entidad entidad) throws java.rmi.RemoteException;
    public es.ieci.tecdoc.sigem.CatalogoTramitesWebService.RetornoServicio deleteAuthHooks(java.lang.String tramiteId, java.lang.String conectorId, es.ieci.tecdoc.sigem.CatalogoTramitesWebService.Entidad entidad) throws java.rmi.RemoteException;
    public es.ieci.tecdoc.sigem.CatalogoTramitesWebService.RetornoServicio updateAuthHooks(es.ieci.tecdoc.sigem.CatalogoTramitesWebService.ConectorAutenticacion conectorAutenticacion, java.lang.String oldHookId, es.ieci.tecdoc.sigem.CatalogoTramitesWebService.Entidad entidad) throws java.rmi.RemoteException;
    public es.ieci.tecdoc.sigem.CatalogoTramitesWebService.ConectorAutenticacion getAuthHook(java.lang.String tramiteId, java.lang.String conectorId, es.ieci.tecdoc.sigem.CatalogoTramitesWebService.Entidad entidad) throws java.rmi.RemoteException;
}
