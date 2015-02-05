package ieci.tecdoc.sgm.cripto.firma.ws.client;

import java.rmi.RemoteException;

public class ServicioFirmaDigitalWebServiceProxy implements ieci.tecdoc.sgm.cripto.firma.ws.client.ServicioFirmaDigitalWebService {
  private String _endpoint = null;
  private ieci.tecdoc.sgm.cripto.firma.ws.client.ServicioFirmaDigitalWebService servicioFirmaDigitalWebService = null;
  
  public ServicioFirmaDigitalWebServiceProxy() {
    _initServicioFirmaDigitalWebServiceProxy();
  }
  
  private void _initServicioFirmaDigitalWebServiceProxy() {
    try {
      servicioFirmaDigitalWebService = (new ieci.tecdoc.sgm.cripto.firma.ws.client.ServicioFirmaDigitalWebServiceServiceLocator()).getFirmaDigitalWebService();
      if (servicioFirmaDigitalWebService != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)servicioFirmaDigitalWebService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)servicioFirmaDigitalWebService)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (servicioFirmaDigitalWebService != null)
      ((javax.xml.rpc.Stub)servicioFirmaDigitalWebService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public ieci.tecdoc.sgm.cripto.firma.ws.client.ServicioFirmaDigitalWebService getServicioFirmaDigitalWebService() {
    if (servicioFirmaDigitalWebService == null)
      _initServicioFirmaDigitalWebServiceProxy();
    return servicioFirmaDigitalWebService;
  }
  
  public ieci.tecdoc.sgm.cripto.firma.ws.client.Firma firmar(ieci.tecdoc.sgm.cripto.firma.ws.client.Contenido poContenido) throws java.rmi.RemoteException{
    if (servicioFirmaDigitalWebService == null)
      _initServicioFirmaDigitalWebServiceProxy();
    return servicioFirmaDigitalWebService.firmar(poContenido);
  }
  
  public ieci.tecdoc.sgm.cripto.firma.ws.client.ResultadoValidacionFirma validarFirma(ieci.tecdoc.sgm.cripto.firma.ws.client.Contenido pabB64Sign, ieci.tecdoc.sgm.cripto.firma.ws.client.Contenido pabB64Content) throws java.rmi.RemoteException{
    if (servicioFirmaDigitalWebService == null)
      _initServicioFirmaDigitalWebServiceProxy();
    return servicioFirmaDigitalWebService.validarFirma(pabB64Sign, pabB64Content);
  }



public ieci.tecdoc.sgm.cripto.firma.ws.client.RegistroFirma registrarFirma(ieci.tecdoc.sgm.cripto.firma.ws.client.Contenido signature, ieci.tecdoc.sgm.cripto.firma.ws.client.Contenido certificate, ieci.tecdoc.sgm.cripto.firma.ws.client.Contenido hash) throws RemoteException{
    if (servicioFirmaDigitalWebService == null)
      _initServicioFirmaDigitalWebServiceProxy();
    return servicioFirmaDigitalWebService.registrarFirma(signature, certificate, hash);
  }

public Firma firmarByEntidad(Contenido poContenido, String idEntidad)
		throws RemoteException {
	// TODO Auto-generated method stub
	return null;
}

public X509CertificadoInfo getCertInfo(Contenido signature)
		throws RemoteException {
	// TODO Auto-generated method stub
	return null;
}

public X509CertificadoInfo getCertInfoByEntidad(Contenido signature,
		String idEntidad) throws RemoteException {
	// TODO Auto-generated method stub
	return null;
}

public RegistroFirma registrarFirmaByEntidad(Contenido signature,
		Contenido certificate, Contenido hash, String idEntidad)
		throws RemoteException {
	// TODO Auto-generated method stub
	return null;
}

public ResultadoValidacionFirma validarFirmaByEntidad(Contenido signature,
		Contenido contenido, String idEntidad) throws RemoteException {
	// TODO Auto-generated method stub
	return null;
}
  
  
}