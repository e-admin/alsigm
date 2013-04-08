package ieci.tecdoc.sgm.catalogo.ws.client;

public class CatalogoTramitesWebServiceProxy implements ieci.tecdoc.sgm.catalogo.ws.client.CatalogoTramitesWebService {
  private String _endpoint = null;
  private ieci.tecdoc.sgm.catalogo.ws.client.CatalogoTramitesWebService catalogoTramitesWebService = null;
  
  public CatalogoTramitesWebServiceProxy() {
    _initCatalogoTramitesWebServiceProxy();
  }
  
  private void _initCatalogoTramitesWebServiceProxy() {
    try {
      catalogoTramitesWebService = (new ieci.tecdoc.sgm.catalogo.ws.client.CatalogoTramitesWebServiceServiceLocator()).getCatalogoTramitesWebService();
      if (catalogoTramitesWebService != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)catalogoTramitesWebService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)catalogoTramitesWebService)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (catalogoTramitesWebService != null)
      ((javax.xml.rpc.Stub)catalogoTramitesWebService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public ieci.tecdoc.sgm.catalogo.ws.client.CatalogoTramitesWebService getCatalogoTramitesWebService() {
    if (catalogoTramitesWebService == null)
      _initCatalogoTramitesWebServiceProxy();
    return catalogoTramitesWebService;
  }
  
  public ieci.tecdoc.sgm.catalogo.ws.client.Tramites query(ieci.tecdoc.sgm.catalogo.ws.client.TramiteConsulta query, ieci.tecdoc.sgm.catalogo.ws.client.Entidad entidad) throws java.rmi.RemoteException{
    if (catalogoTramitesWebService == null)
      _initCatalogoTramitesWebServiceProxy();
    return catalogoTramitesWebService.query(query, entidad);
  }
  
  public ieci.tecdoc.sgm.catalogo.ws.client.Documento getDocument(java.lang.String documentId, ieci.tecdoc.sgm.catalogo.ws.client.Entidad entidad) throws java.rmi.RemoteException{
    if (catalogoTramitesWebService == null)
      _initCatalogoTramitesWebServiceProxy();
    return catalogoTramitesWebService.getDocument(documentId, entidad);
  }
  
  public ieci.tecdoc.sgm.catalogo.ws.client.Documentos getDocuments(ieci.tecdoc.sgm.catalogo.ws.client.Entidad entidad) throws java.rmi.RemoteException{
    if (catalogoTramitesWebService == null)
      _initCatalogoTramitesWebServiceProxy();
    return catalogoTramitesWebService.getDocuments(entidad);
  }
  
  public ieci.tecdoc.sgm.catalogo.ws.client.RetornoServicio updateDocument(ieci.tecdoc.sgm.catalogo.ws.client.Documento document, ieci.tecdoc.sgm.catalogo.ws.client.Entidad entidad) throws java.rmi.RemoteException{
    if (catalogoTramitesWebService == null)
      _initCatalogoTramitesWebServiceProxy();
    return catalogoTramitesWebService.updateDocument(document, entidad);
  }
  
  public ieci.tecdoc.sgm.catalogo.ws.client.RetornoServicio addProcedure(ieci.tecdoc.sgm.catalogo.ws.client.Tramite procedure, ieci.tecdoc.sgm.catalogo.ws.client.Entidad entidad) throws java.rmi.RemoteException{
    if (catalogoTramitesWebService == null)
      _initCatalogoTramitesWebServiceProxy();
    return catalogoTramitesWebService.addProcedure(procedure, entidad);
  }
  
  public ieci.tecdoc.sgm.catalogo.ws.client.Tramite getProcedure(java.lang.String procedureId, ieci.tecdoc.sgm.catalogo.ws.client.RetornoLogico loadDocuments, ieci.tecdoc.sgm.catalogo.ws.client.Entidad entidad) throws java.rmi.RemoteException{
    if (catalogoTramitesWebService == null)
      _initCatalogoTramitesWebServiceProxy();
    return catalogoTramitesWebService.getProcedure(procedureId, loadDocuments, entidad);
  }
  
  public ieci.tecdoc.sgm.catalogo.ws.client.RetornoServicio deleteProcedure(java.lang.String procedureId, ieci.tecdoc.sgm.catalogo.ws.client.Entidad entidad) throws java.rmi.RemoteException{
    if (catalogoTramitesWebService == null)
      _initCatalogoTramitesWebServiceProxy();
    return catalogoTramitesWebService.deleteProcedure(procedureId, entidad);
  }
  
  public ieci.tecdoc.sgm.catalogo.ws.client.RetornoServicio updateProcedure(ieci.tecdoc.sgm.catalogo.ws.client.Tramite procedure, ieci.tecdoc.sgm.catalogo.ws.client.Entidad entidad) throws java.rmi.RemoteException{
    if (catalogoTramitesWebService == null)
      _initCatalogoTramitesWebServiceProxy();
    return catalogoTramitesWebService.updateProcedure(procedure, entidad);
  }
  
  public ieci.tecdoc.sgm.catalogo.ws.client.RetornoLogico isDocumentReferenced(java.lang.String documentId, ieci.tecdoc.sgm.catalogo.ws.client.Entidad entidad) throws java.rmi.RemoteException{
    if (catalogoTramitesWebService == null)
      _initCatalogoTramitesWebServiceProxy();
    return catalogoTramitesWebService.isDocumentReferenced(documentId, entidad);
  }
  
  public ieci.tecdoc.sgm.catalogo.ws.client.RetornoServicio addProcedureDocument(ieci.tecdoc.sgm.catalogo.ws.client.DocumentoTramite procedureDocument, ieci.tecdoc.sgm.catalogo.ws.client.Entidad entidad) throws java.rmi.RemoteException{
    if (catalogoTramitesWebService == null)
      _initCatalogoTramitesWebServiceProxy();
    return catalogoTramitesWebService.addProcedureDocument(procedureDocument, entidad);
  }
  
  public ieci.tecdoc.sgm.catalogo.ws.client.RetornoServicio deleteProcedureDocument(ieci.tecdoc.sgm.catalogo.ws.client.DocumentoTramite procedureDocument, ieci.tecdoc.sgm.catalogo.ws.client.Entidad entidad) throws java.rmi.RemoteException{
    if (catalogoTramitesWebService == null)
      _initCatalogoTramitesWebServiceProxy();
    return catalogoTramitesWebService.deleteProcedureDocument(procedureDocument, entidad);
  }
  
  public ieci.tecdoc.sgm.catalogo.ws.client.RetornoServicio addDocument(ieci.tecdoc.sgm.catalogo.ws.client.Documento document, ieci.tecdoc.sgm.catalogo.ws.client.Entidad entidad) throws java.rmi.RemoteException{
    if (catalogoTramitesWebService == null)
      _initCatalogoTramitesWebServiceProxy();
    return catalogoTramitesWebService.addDocument(document, entidad);
  }
  
  public ieci.tecdoc.sgm.catalogo.ws.client.RetornoServicio deleteDocument(java.lang.String documentId, ieci.tecdoc.sgm.catalogo.ws.client.Entidad entidad) throws java.rmi.RemoteException{
    if (catalogoTramitesWebService == null)
      _initCatalogoTramitesWebServiceProxy();
    return catalogoTramitesWebService.deleteDocument(documentId, entidad);
  }
  
  public ieci.tecdoc.sgm.catalogo.ws.client.Documentos getProcedureDocuments(java.lang.String procedureId, ieci.tecdoc.sgm.catalogo.ws.client.Entidad entidad) throws java.rmi.RemoteException{
    if (catalogoTramitesWebService == null)
      _initCatalogoTramitesWebServiceProxy();
    return catalogoTramitesWebService.getProcedureDocuments(procedureId, entidad);
  }
  
  public ieci.tecdoc.sgm.catalogo.ws.client.DocumentoTramite getProcedureDocument(java.lang.String procedureId, java.lang.String documentId, ieci.tecdoc.sgm.catalogo.ws.client.Entidad entidad) throws java.rmi.RemoteException{
    if (catalogoTramitesWebService == null)
      _initCatalogoTramitesWebServiceProxy();
    return catalogoTramitesWebService.getProcedureDocument(procedureId, documentId, entidad);
  }
  
  public ieci.tecdoc.sgm.catalogo.ws.client.DocumentoTramite getProcedureDocumentByCode(java.lang.String procedureId, java.lang.String documentId, java.lang.String code, ieci.tecdoc.sgm.catalogo.ws.client.Entidad entidad) throws java.rmi.RemoteException{
    if (catalogoTramitesWebService == null)
      _initCatalogoTramitesWebServiceProxy();
    return catalogoTramitesWebService.getProcedureDocumentByCode(procedureId, documentId, code, entidad);
  }
  
  public ieci.tecdoc.sgm.catalogo.ws.client.RetornoServicio updateProcedureDocument(ieci.tecdoc.sgm.catalogo.ws.client.DocumentoTramite procedureDocument, ieci.tecdoc.sgm.catalogo.ws.client.Entidad entidad) throws java.rmi.RemoteException{
    if (catalogoTramitesWebService == null)
      _initCatalogoTramitesWebServiceProxy();
    return catalogoTramitesWebService.updateProcedureDocument(procedureDocument, entidad);
  }
  
  public ieci.tecdoc.sgm.catalogo.ws.client.Tramites getProcedures(ieci.tecdoc.sgm.catalogo.ws.client.Entidad entidad) throws java.rmi.RemoteException{
    if (catalogoTramitesWebService == null)
      _initCatalogoTramitesWebServiceProxy();
    return catalogoTramitesWebService.getProcedures(entidad);
  }
  
  public ieci.tecdoc.sgm.catalogo.ws.client.Documento getDocumentfromCode(java.lang.String code, ieci.tecdoc.sgm.catalogo.ws.client.Entidad entidad) throws java.rmi.RemoteException{
    if (catalogoTramitesWebService == null)
      _initCatalogoTramitesWebServiceProxy();
    return catalogoTramitesWebService.getDocumentfromCode(code, entidad);
  }
  
  public ieci.tecdoc.sgm.catalogo.ws.client.OrganoDestinatario getAddressee(java.lang.String addresseeId, ieci.tecdoc.sgm.catalogo.ws.client.Entidad entidad) throws java.rmi.RemoteException{
    if (catalogoTramitesWebService == null)
      _initCatalogoTramitesWebServiceProxy();
    return catalogoTramitesWebService.getAddressee(addresseeId, entidad);
  }
  
  public ieci.tecdoc.sgm.catalogo.ws.client.RetornoServicio addAddressee(ieci.tecdoc.sgm.catalogo.ws.client.OrganoDestinatario addressee, ieci.tecdoc.sgm.catalogo.ws.client.Entidad entidad) throws java.rmi.RemoteException{
    if (catalogoTramitesWebService == null)
      _initCatalogoTramitesWebServiceProxy();
    return catalogoTramitesWebService.addAddressee(addressee, entidad);
  }
  
  public ieci.tecdoc.sgm.catalogo.ws.client.RetornoServicio deleteAddressee(java.lang.String addresseeId, ieci.tecdoc.sgm.catalogo.ws.client.Entidad entidad) throws java.rmi.RemoteException{
    if (catalogoTramitesWebService == null)
      _initCatalogoTramitesWebServiceProxy();
    return catalogoTramitesWebService.deleteAddressee(addresseeId, entidad);
  }
  
  public ieci.tecdoc.sgm.catalogo.ws.client.RetornoServicio updateAddressee(ieci.tecdoc.sgm.catalogo.ws.client.OrganoDestinatario addressee, ieci.tecdoc.sgm.catalogo.ws.client.Entidad entidad) throws java.rmi.RemoteException{
    if (catalogoTramitesWebService == null)
      _initCatalogoTramitesWebServiceProxy();
    return catalogoTramitesWebService.updateAddressee(addressee, entidad);
  }
  
  public ieci.tecdoc.sgm.catalogo.ws.client.OrganosDestinatarios getAddressees(ieci.tecdoc.sgm.catalogo.ws.client.Entidad entidad) throws java.rmi.RemoteException{
    if (catalogoTramitesWebService == null)
      _initCatalogoTramitesWebServiceProxy();
    return catalogoTramitesWebService.getAddressees(entidad);
  }
  
  public ieci.tecdoc.sgm.catalogo.ws.client.Conector getHook(java.lang.String hookId, ieci.tecdoc.sgm.catalogo.ws.client.Entidad entidad) throws java.rmi.RemoteException{
    if (catalogoTramitesWebService == null)
      _initCatalogoTramitesWebServiceProxy();
    return catalogoTramitesWebService.getHook(hookId, entidad);
  }
  
  public ieci.tecdoc.sgm.catalogo.ws.client.RetornoServicio addHook(ieci.tecdoc.sgm.catalogo.ws.client.Conector hook, ieci.tecdoc.sgm.catalogo.ws.client.Entidad entidad) throws java.rmi.RemoteException{
    if (catalogoTramitesWebService == null)
      _initCatalogoTramitesWebServiceProxy();
    return catalogoTramitesWebService.addHook(hook, entidad);
  }
  
  public ieci.tecdoc.sgm.catalogo.ws.client.RetornoServicio deleteHook(java.lang.String hookId, ieci.tecdoc.sgm.catalogo.ws.client.Entidad entidad) throws java.rmi.RemoteException{
    if (catalogoTramitesWebService == null)
      _initCatalogoTramitesWebServiceProxy();
    return catalogoTramitesWebService.deleteHook(hookId, entidad);
  }
  
  public ieci.tecdoc.sgm.catalogo.ws.client.RetornoServicio updateHook(ieci.tecdoc.sgm.catalogo.ws.client.Conector hook, ieci.tecdoc.sgm.catalogo.ws.client.Entidad entidad) throws java.rmi.RemoteException{
    if (catalogoTramitesWebService == null)
      _initCatalogoTramitesWebServiceProxy();
    return catalogoTramitesWebService.updateHook(hook, entidad);
  }
  
  public ieci.tecdoc.sgm.catalogo.ws.client.Conectores getHooks(ieci.tecdoc.sgm.catalogo.ws.client.Entidad entidad) throws java.rmi.RemoteException{
    if (catalogoTramitesWebService == null)
      _initCatalogoTramitesWebServiceProxy();
    return catalogoTramitesWebService.getHooks(entidad);
  }
  
  public ieci.tecdoc.sgm.catalogo.ws.client.Conectores getHooksByType(java.lang.String hookType, ieci.tecdoc.sgm.catalogo.ws.client.Entidad entidad) throws java.rmi.RemoteException{
    if (catalogoTramitesWebService == null)
      _initCatalogoTramitesWebServiceProxy();
    return catalogoTramitesWebService.getHooksByType(hookType, entidad);
  }
  
  public ieci.tecdoc.sgm.catalogo.ws.client.TipoConector getHookType(java.lang.String typeId, ieci.tecdoc.sgm.catalogo.ws.client.Entidad entidad) throws java.rmi.RemoteException{
    if (catalogoTramitesWebService == null)
      _initCatalogoTramitesWebServiceProxy();
    return catalogoTramitesWebService.getHookType(typeId, entidad);
  }
  
  public ieci.tecdoc.sgm.catalogo.ws.client.RetornoServicio addHookType(ieci.tecdoc.sgm.catalogo.ws.client.TipoConector hookType, ieci.tecdoc.sgm.catalogo.ws.client.Entidad entidad) throws java.rmi.RemoteException{
    if (catalogoTramitesWebService == null)
      _initCatalogoTramitesWebServiceProxy();
    return catalogoTramitesWebService.addHookType(hookType, entidad);
  }
  
  public ieci.tecdoc.sgm.catalogo.ws.client.RetornoServicio deleteHookType(java.lang.String typeId, ieci.tecdoc.sgm.catalogo.ws.client.Entidad entidad) throws java.rmi.RemoteException{
    if (catalogoTramitesWebService == null)
      _initCatalogoTramitesWebServiceProxy();
    return catalogoTramitesWebService.deleteHookType(typeId, entidad);
  }
  
  public ieci.tecdoc.sgm.catalogo.ws.client.RetornoServicio updateHookType(ieci.tecdoc.sgm.catalogo.ws.client.TipoConector hookType, ieci.tecdoc.sgm.catalogo.ws.client.Entidad entidad) throws java.rmi.RemoteException{
    if (catalogoTramitesWebService == null)
      _initCatalogoTramitesWebServiceProxy();
    return catalogoTramitesWebService.updateHookType(hookType, entidad);
  }
  
  public ieci.tecdoc.sgm.catalogo.ws.client.TiposConectores getHookTypes(ieci.tecdoc.sgm.catalogo.ws.client.Entidad entidad) throws java.rmi.RemoteException{
    if (catalogoTramitesWebService == null)
      _initCatalogoTramitesWebServiceProxy();
    return catalogoTramitesWebService.getHookTypes(entidad);
  }
  
  public ieci.tecdoc.sgm.catalogo.ws.client.ConectoresAutenticacion getAuthHooks(java.lang.String tramiteId, ieci.tecdoc.sgm.catalogo.ws.client.Entidad entidad) throws java.rmi.RemoteException{
    if (catalogoTramitesWebService == null)
      _initCatalogoTramitesWebServiceProxy();
    return catalogoTramitesWebService.getAuthHooks(tramiteId, entidad);
  }
  
  public ieci.tecdoc.sgm.catalogo.ws.client.RetornoServicio addAuthHooks(ieci.tecdoc.sgm.catalogo.ws.client.ConectorAutenticacion authHook, ieci.tecdoc.sgm.catalogo.ws.client.Entidad entidad) throws java.rmi.RemoteException{
    if (catalogoTramitesWebService == null)
      _initCatalogoTramitesWebServiceProxy();
    return catalogoTramitesWebService.addAuthHooks(authHook, entidad);
  }
  
  public ieci.tecdoc.sgm.catalogo.ws.client.RetornoServicio deleteAuthHooks(java.lang.String tramiteId, java.lang.String conectorId, ieci.tecdoc.sgm.catalogo.ws.client.Entidad entidad) throws java.rmi.RemoteException{
    if (catalogoTramitesWebService == null)
      _initCatalogoTramitesWebServiceProxy();
    return catalogoTramitesWebService.deleteAuthHooks(tramiteId, conectorId, entidad);
  }
  
  public ieci.tecdoc.sgm.catalogo.ws.client.RetornoServicio updateAuthHooks(ieci.tecdoc.sgm.catalogo.ws.client.ConectorAutenticacion conectorAutenticacion, java.lang.String oldHookId, ieci.tecdoc.sgm.catalogo.ws.client.Entidad entidad) throws java.rmi.RemoteException{
    if (catalogoTramitesWebService == null)
      _initCatalogoTramitesWebServiceProxy();
    return catalogoTramitesWebService.updateAuthHooks(conectorAutenticacion, oldHookId, entidad);
  }
  
  public ieci.tecdoc.sgm.catalogo.ws.client.ConectorAutenticacion getAuthHook(java.lang.String tramiteId, java.lang.String conectorId, ieci.tecdoc.sgm.catalogo.ws.client.Entidad entidad) throws java.rmi.RemoteException{
    if (catalogoTramitesWebService == null)
      _initCatalogoTramitesWebServiceProxy();
    return catalogoTramitesWebService.getAuthHook(tramiteId, conectorId, entidad);
  }
  
  
}