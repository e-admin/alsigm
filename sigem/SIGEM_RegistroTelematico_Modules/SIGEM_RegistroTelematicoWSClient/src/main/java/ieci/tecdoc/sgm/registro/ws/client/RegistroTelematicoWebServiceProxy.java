package ieci.tecdoc.sgm.registro.ws.client;

import ieci.tecdoc.sgm.registro.ws.client.axis.Entidad;
import ieci.tecdoc.sgm.registro.ws.client.axis.StringB64;

import java.rmi.RemoteException;

public class RegistroTelematicoWebServiceProxy implements ieci.tecdoc.sgm.registro.ws.client.axis.RegistroTelematicoWebService_PortType {
  private String _endpoint = null;
  private ieci.tecdoc.sgm.registro.ws.client.axis.RegistroTelematicoWebService_PortType registroTelematicoWebService = null;

  public RegistroTelematicoWebServiceProxy() {
    _initRegistroTelematicoWebServiceProxy();
  }

  private void _initRegistroTelematicoWebServiceProxy() {
    try {
      registroTelematicoWebService = (new ieci.tecdoc.sgm.registro.ws.client.axis.RegistroTelematicoWebServiceServiceLocator()).getRegistroTelematicoWebService();
      if (registroTelematicoWebService != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)registroTelematicoWebService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)registroTelematicoWebService)._getProperty("javax.xml.rpc.service.endpoint.address");
      }

    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }

  public String getEndpoint() {
    return _endpoint;
  }

  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (registroTelematicoWebService != null)
      ((javax.xml.rpc.Stub)registroTelematicoWebService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);

  }

  public ieci.tecdoc.sgm.registro.ws.client.axis.RegistroTelematicoWebService_PortType getRegistroTelematicoWebService() {
    if (registroTelematicoWebService == null)
      _initRegistroTelematicoWebServiceProxy();
    return registroTelematicoWebService;
  }

  public ieci.tecdoc.sgm.registro.ws.client.axis.Registros query(java.lang.String sessionId, ieci.tecdoc.sgm.registro.ws.client.axis.RegistroConsulta query, ieci.tecdoc.sgm.registro.ws.client.axis.Entidad entidad) throws java.rmi.RemoteException{
    if (registroTelematicoWebService == null)
      _initRegistroTelematicoWebServiceProxy();
    return registroTelematicoWebService.query(sessionId, query, entidad);
  }

  public ieci.tecdoc.sgm.registro.ws.client.axis.StringB64 crearPeticionRegistro(java.lang.String sessionId, ieci.tecdoc.sgm.registro.ws.client.axis.RegistroPeticion requestInfo, java.lang.String idiom, java.lang.String organismo, java.lang.String numeroExpediente, ieci.tecdoc.sgm.registro.ws.client.axis.Entidad entidad) throws java.rmi.RemoteException{
    if (registroTelematicoWebService == null)
      _initRegistroTelematicoWebServiceProxy();
    return registroTelematicoWebService.crearPeticionRegistro(sessionId, requestInfo, idiom, organismo, numeroExpediente, entidad);
  }

  public ieci.tecdoc.sgm.registro.ws.client.axis.RetornoCadena establecerDocumentosSubsanacion(java.lang.String sessionId, ieci.tecdoc.sgm.registro.ws.client.axis.Documentos procedureDocuments, ieci.tecdoc.sgm.registro.ws.client.axis.PeticionDocumentos requestDocuments, ieci.tecdoc.sgm.registro.ws.client.axis.Entidad entidad) throws java.rmi.RemoteException{
    if (registroTelematicoWebService == null)
      _initRegistroTelematicoWebServiceProxy();
    return registroTelematicoWebService.establecerDocumentosSubsanacion(sessionId, procedureDocuments, requestDocuments, entidad);
  }

  public ieci.tecdoc.sgm.registro.ws.client.axis.StringB64 registrar(java.lang.String sessionId, ieci.tecdoc.sgm.registro.ws.client.axis.StringB64 registryRequest, java.lang.String additionalInfo, java.lang.String idiom, java.lang.String oficina, java.lang.String plantilla, java.lang.String certificado, ieci.tecdoc.sgm.registro.ws.client.axis.Entidad entidad) throws java.rmi.RemoteException{
    if (registroTelematicoWebService == null)
      _initRegistroTelematicoWebServiceProxy();
    return registroTelematicoWebService.registrar(sessionId, registryRequest, additionalInfo, idiom, oficina, plantilla, certificado, entidad);
  }

  public ieci.tecdoc.sgm.registro.ws.client.axis.StringB64 registrarTelematicoAndIniciarExpediente(java.lang.String sessionId, ieci.tecdoc.sgm.registro.ws.client.axis.StringB64 registryRequest, java.lang.String additionalInfo, java.lang.String idiom, java.lang.String oficina, java.lang.String plantilla, java.lang.String certificado, java.lang.String tramiteId, ieci.tecdoc.sgm.registro.ws.client.axis.Entidad entidad) throws java.rmi.RemoteException{
    if (registroTelematicoWebService == null)
      _initRegistroTelematicoWebServiceProxy();
    return registroTelematicoWebService.registrarTelematicoAndIniciarExpediente(sessionId, registryRequest, additionalInfo, idiom, oficina, plantilla, certificado, tramiteId, entidad);
  }

  public ieci.tecdoc.sgm.registro.ws.client.axis.RetornoServicio deshacerRegistro(java.lang.String sessionId, java.lang.String registryNumber, ieci.tecdoc.sgm.registro.ws.client.axis.Entidad entidad) throws java.rmi.RemoteException{
    if (registroTelematicoWebService == null)
      _initRegistroTelematicoWebServiceProxy();
    return registroTelematicoWebService.deshacerRegistro(sessionId, registryNumber, entidad);
  }

  public ieci.tecdoc.sgm.registro.ws.client.axis.RetornoCadena obtenerNumeroRegistro(ieci.tecdoc.sgm.registro.ws.client.axis.Entidad entidad) throws java.rmi.RemoteException{
    if (registroTelematicoWebService == null)
      _initRegistroTelematicoWebServiceProxy();
    return registroTelematicoWebService.obtenerNumeroRegistro(entidad);
  }

  public ieci.tecdoc.sgm.registro.ws.client.axis.RetornoServicio eliminarDocumentosTemporales(java.lang.String sessionId, java.lang.String registryNumber, ieci.tecdoc.sgm.registro.ws.client.axis.Entidad entidad) throws java.rmi.RemoteException{
    if (registroTelematicoWebService == null)
      _initRegistroTelematicoWebServiceProxy();
    return registroTelematicoWebService.eliminarDocumentosTemporales(sessionId, registryNumber, entidad);
  }

  public ieci.tecdoc.sgm.registro.ws.client.axis.Registros obtenerRegistrosParaMostrar(ieci.tecdoc.sgm.registro.ws.client.axis.RegistroConsulta query, ieci.tecdoc.sgm.registro.ws.client.axis.Entidad entidad) throws java.rmi.RemoteException{
    if (registroTelematicoWebService == null)
      _initRegistroTelematicoWebServiceProxy();
    return registroTelematicoWebService.obtenerRegistrosParaMostrar(query, entidad);
  }

  public ieci.tecdoc.sgm.registro.ws.client.axis.Registro obtenerRegistro(java.lang.String sessionId, java.lang.String registryNumber, ieci.tecdoc.sgm.registro.ws.client.axis.Entidad entidad) throws java.rmi.RemoteException{
    if (registroTelematicoWebService == null)
      _initRegistroTelematicoWebServiceProxy();
    return registroTelematicoWebService.obtenerRegistro(sessionId, registryNumber, entidad);
  }

  public ieci.tecdoc.sgm.registro.ws.client.axis.ContenedorDocumentos obtenerDatosDocumentosRegistro(java.lang.String registryNumber, ieci.tecdoc.sgm.registro.ws.client.axis.Entidad entidad) throws java.rmi.RemoteException{
    if (registroTelematicoWebService == null)
      _initRegistroTelematicoWebServiceProxy();
    return registroTelematicoWebService.obtenerDatosDocumentosRegistro(registryNumber, entidad);
  }

  public ieci.tecdoc.sgm.registro.ws.client.axis.StringB64 obtenerJustificanteRegistro(java.lang.String sessionId, java.lang.String registryNumber, ieci.tecdoc.sgm.registro.ws.client.axis.Entidad entidad) throws java.rmi.RemoteException{
    if (registroTelematicoWebService == null)
      _initRegistroTelematicoWebServiceProxy();
    return registroTelematicoWebService.obtenerJustificanteRegistro(sessionId, registryNumber, entidad);
  }

  public ieci.tecdoc.sgm.registro.ws.client.axis.StringB64 obtenerPeticionRegistro(java.lang.String registryNumber, ieci.tecdoc.sgm.registro.ws.client.axis.Entidad entidad) throws java.rmi.RemoteException{
    if (registroTelematicoWebService == null)
      _initRegistroTelematicoWebServiceProxy();
    return registroTelematicoWebService.obtenerPeticionRegistro(registryNumber, entidad);
  }

  public ieci.tecdoc.sgm.registro.ws.client.axis.Registros obtenerRegistrosConsolidados(ieci.tecdoc.sgm.registro.ws.client.axis.Entidad entidad) throws java.rmi.RemoteException{
    if (registroTelematicoWebService == null)
      _initRegistroTelematicoWebServiceProxy();
    return registroTelematicoWebService.obtenerRegistrosConsolidados(entidad);
  }

  public ieci.tecdoc.sgm.registro.ws.client.axis.RetornoLogico tieneDocumentos(java.lang.String registryNumber, ieci.tecdoc.sgm.registro.ws.client.axis.Entidad entidad) throws java.rmi.RemoteException{
    if (registroTelematicoWebService == null)
      _initRegistroTelematicoWebServiceProxy();
    return registroTelematicoWebService.tieneDocumentos(registryNumber, entidad);
  }

  public ieci.tecdoc.sgm.registro.ws.client.axis.ByteArrayB64 obtenerContenidoDocumento(java.lang.String sessionId, java.lang.String registryNumber, java.lang.String code, ieci.tecdoc.sgm.registro.ws.client.axis.Entidad entidad) throws java.rmi.RemoteException{
    if (registroTelematicoWebService == null)
      _initRegistroTelematicoWebServiceProxy();
    return registroTelematicoWebService.obtenerContenidoDocumento(sessionId, registryNumber, code, entidad);
  }

  public ieci.tecdoc.sgm.registro.ws.client.axis.StringB64 obtenerDocumento(java.lang.String registryNumber, java.lang.String code, ieci.tecdoc.sgm.registro.ws.client.axis.Entidad entidad) throws java.rmi.RemoteException{
    if (registroTelematicoWebService == null)
      _initRegistroTelematicoWebServiceProxy();
    return registroTelematicoWebService.obtenerDocumento(registryNumber, code, entidad);
  }

  public ieci.tecdoc.sgm.registro.ws.client.axis.RetornoServicio establecerEstadoRegistro(java.lang.String registryNumber, java.lang.String status, ieci.tecdoc.sgm.registro.ws.client.axis.Entidad entidad) throws java.rmi.RemoteException{
    if (registroTelematicoWebService == null)
      _initRegistroTelematicoWebServiceProxy();
    return registroTelematicoWebService.establecerEstadoRegistro(registryNumber, status, entidad);
  }

  public ieci.tecdoc.sgm.registro.ws.client.axis.RegistroDocumentos obtenerDocumentosRegistro(java.lang.String registryNumber, ieci.tecdoc.sgm.registro.ws.client.axis.Entidad entidad) throws java.rmi.RemoteException{
    if (registroTelematicoWebService == null)
      _initRegistroTelematicoWebServiceProxy();
    return registroTelematicoWebService.obtenerDocumentosRegistro(registryNumber, entidad);
  }

  public ieci.tecdoc.sgm.registro.ws.client.axis.RegistroDocumento obtenerDocumentoRegistro(java.lang.String sessionId, java.lang.String registryNumber, java.lang.String code, ieci.tecdoc.sgm.registro.ws.client.axis.Entidad entidad) throws java.rmi.RemoteException{
    if (registroTelematicoWebService == null)
      _initRegistroTelematicoWebServiceProxy();
    return registroTelematicoWebService.obtenerDocumentoRegistro(sessionId, registryNumber, code, entidad);
  }

  public ieci.tecdoc.sgm.registro.ws.client.axis.RegistroDocumento insertarDocumentoRegistro(ieci.tecdoc.sgm.registro.ws.client.axis.RegistroDocumento registryDocument, ieci.tecdoc.sgm.registro.ws.client.axis.Entidad entidad) throws java.rmi.RemoteException{
    if (registroTelematicoWebService == null)
      _initRegistroTelematicoWebServiceProxy();
    return registroTelematicoWebService.insertarDocumentoRegistro(registryDocument, entidad);
  }

  public ieci.tecdoc.sgm.registro.ws.client.axis.RetornoServicio actualizarDocumentoRegistro(ieci.tecdoc.sgm.registro.ws.client.axis.RegistroDocumento registryDocument, ieci.tecdoc.sgm.registro.ws.client.axis.Entidad entidad) throws java.rmi.RemoteException{
    if (registroTelematicoWebService == null)
      _initRegistroTelematicoWebServiceProxy();
    return registroTelematicoWebService.actualizarDocumentoRegistro(registryDocument, entidad);
  }

  public ieci.tecdoc.sgm.registro.ws.client.axis.RetornoServicio eliminarDocumentoRegistro(java.lang.String registryNumber, ieci.tecdoc.sgm.registro.ws.client.axis.Entidad entidad) throws java.rmi.RemoteException{
    if (registroTelematicoWebService == null)
      _initRegistroTelematicoWebServiceProxy();
    return registroTelematicoWebService.eliminarDocumentoRegistro(registryNumber, entidad);
  }

public StringB64 registrarConJustificante(String sessionId,
		StringB64 registryRequest, String additionalInfo, String idiom,
		String oficina, StringB64 plantilla, String certificado, Entidad entidad)
		throws RemoteException {
	// TODO Auto-generated method stub
	return null;
}

public StringB64 registrarTelematicoConJustificanteAndIniciarExpediente(
		String sessionId, StringB64 registryRequest, String additionalInfo,
		String idiom, String oficina, StringB64 plantilla, String certificado,
		String tramiteId, Entidad entidad) throws RemoteException {
	// TODO Auto-generated method stub
	return null;
}


}