package ieci.tecdoc.sgm.mensajesCortos.ws.client;

public class MensajesCortosWebServiceProxy implements ieci.tecdoc.sgm.mensajesCortos.ws.client.MensajesCortosWebService {
  private String _endpoint = null;
  private ieci.tecdoc.sgm.mensajesCortos.ws.client.MensajesCortosWebService mensajesCortosWebService = null;
  
  public MensajesCortosWebServiceProxy() {
    _initMensajesCortosWebServiceProxy();
  }
  
  public MensajesCortosWebServiceProxy(String endpoint) {
    _endpoint = endpoint;
    _initMensajesCortosWebServiceProxy();
  }
  
  private void _initMensajesCortosWebServiceProxy() {
    try {
      mensajesCortosWebService = (new ieci.tecdoc.sgm.mensajesCortos.ws.client.MensajesCortosWebServiceServiceLocator()).getMensajesCortosWebService();
      if (mensajesCortosWebService != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)mensajesCortosWebService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)mensajesCortosWebService)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (mensajesCortosWebService != null)
      ((javax.xml.rpc.Stub)mensajesCortosWebService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public ieci.tecdoc.sgm.mensajesCortos.ws.client.MensajesCortosWebService getMensajesCortosWebService() {
    if (mensajesCortosWebService == null)
      _initMensajesCortosWebServiceProxy();
    return mensajesCortosWebService;
  }
  
  public ieci.tecdoc.sgm.core.services.dto.RetornoServicio sendMail(java.lang.String from, java.lang.String[] to, java.lang.String[] cc, java.lang.String[] bcc, java.lang.String subject, java.lang.String content, ieci.tecdoc.sgm.core.services.mensajes_cortos.dto.Attachment[] attachments) throws java.rmi.RemoteException{
    if (mensajesCortosWebService == null)
      _initMensajesCortosWebServiceProxy();
    return mensajesCortosWebService.sendMail(from, to, cc, bcc, subject, content, attachments);
  }
  
  public ieci.tecdoc.sgm.mensajesCortos.ws.client.dto.String sendCertSMS(java.lang.String user, java.lang.String pwd, java.lang.String src, java.lang.String dst, java.lang.String txt, java.lang.String lang) throws java.rmi.RemoteException{
    if (mensajesCortosWebService == null)
      _initMensajesCortosWebServiceProxy();
    return mensajesCortosWebService.sendCertSMS(user, pwd, src, dst, txt, lang);
  }
  
  public ieci.tecdoc.sgm.mensajesCortos.ws.client.dto.ListaString sendSMS_Multiple(java.lang.String user, java.lang.String pwd, java.lang.String src, java.lang.String[] dst, java.lang.String txt) throws java.rmi.RemoteException{
    if (mensajesCortosWebService == null)
      _initMensajesCortosWebServiceProxy();
    return mensajesCortosWebService.sendSMS_Multiple(user, pwd, src, dst, txt);
  }
  
  public ieci.tecdoc.sgm.mensajesCortos.ws.client.dto.Entero getSMSStatus(java.lang.String user, java.lang.String pwd, java.lang.String id) throws java.rmi.RemoteException{
    if (mensajesCortosWebService == null)
      _initMensajesCortosWebServiceProxy();
    return mensajesCortosWebService.getSMSStatus(user, pwd, id);
  }
  
  public ieci.tecdoc.sgm.mensajesCortos.ws.client.dto.String sendSMS(java.lang.String user, java.lang.String pwd, java.lang.String src, java.lang.String dst, java.lang.String txt) throws java.rmi.RemoteException{
    if (mensajesCortosWebService == null)
      _initMensajesCortosWebServiceProxy();
    return mensajesCortosWebService.sendSMS(user, pwd, src, dst, txt);
  }
  
  public ieci.tecdoc.sgm.mensajesCortos.ws.client.dto.Entero getCertSMSSignatureStatus(java.lang.String user, java.lang.String pwd, java.lang.String id) throws java.rmi.RemoteException{
    if (mensajesCortosWebService == null)
      _initMensajesCortosWebServiceProxy();
    return mensajesCortosWebService.getCertSMSSignatureStatus(user, pwd, id);
  }
  
  public ieci.tecdoc.sgm.mensajesCortos.ws.client.dto.String getCertSMSSignatureXML(java.lang.String user, java.lang.String pwd, java.lang.String id) throws java.rmi.RemoteException{
    if (mensajesCortosWebService == null)
      _initMensajesCortosWebServiceProxy();
    return mensajesCortosWebService.getCertSMSSignatureXML(user, pwd, id);
  }
  
  public ieci.tecdoc.sgm.mensajesCortos.ws.client.dto.ListaBytes getCertSMSSignatureDocument(java.lang.String user, java.lang.String pwd, java.lang.String id) throws java.rmi.RemoteException{
    if (mensajesCortosWebService == null)
      _initMensajesCortosWebServiceProxy();
    return mensajesCortosWebService.getCertSMSSignatureDocument(user, pwd, id);
  }
  
  
}