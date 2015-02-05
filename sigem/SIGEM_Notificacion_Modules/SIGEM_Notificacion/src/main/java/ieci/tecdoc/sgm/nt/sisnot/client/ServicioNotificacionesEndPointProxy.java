package ieci.tecdoc.sgm.nt.sisnot.client;

public class ServicioNotificacionesEndPointProxy implements ieci.tecdoc.sgm.nt.sisnot.client.ServicioNotificacionesEndPoint {
	  private String _endpoint = null;
	  private ieci.tecdoc.sgm.nt.sisnot.client.ServicioNotificacionesEndPoint servicioNotificacionesEndPoint = null;
	  
	  public ServicioNotificacionesEndPointProxy() {
	    _initServicioNotificacionesEndPointProxy();
	  }
	  
	  private void _initServicioNotificacionesEndPointProxy() {
	    try {
	      servicioNotificacionesEndPoint = (new ieci.tecdoc.sgm.nt.sisnot.client.ServicioNotificacionesLocator()).getServicioNotificacionesEndPointPort();
	      if (servicioNotificacionesEndPoint != null) {
	        if (_endpoint != null)
	          ((javax.xml.rpc.Stub)servicioNotificacionesEndPoint)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
	        else
	          _endpoint = (String)((javax.xml.rpc.Stub)servicioNotificacionesEndPoint)._getProperty("javax.xml.rpc.service.endpoint.address");
	      }
	      
	    }
	    catch (javax.xml.rpc.ServiceException serviceException) {}
	  }
	  
	  public String getEndpoint() {
	    return _endpoint;
	  }
	  
	  public void setEndpoint(String endpoint) {
	    _endpoint = endpoint;
	    if (servicioNotificacionesEndPoint != null)
	      ((javax.xml.rpc.Stub)servicioNotificacionesEndPoint)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
	    
	  }
	  
	  public ieci.tecdoc.sgm.nt.sisnot.client.ServicioNotificacionesEndPoint getServicioNotificacionesEndPoint() {
	    if (servicioNotificacionesEndPoint == null)
	      _initServicioNotificacionesEndPointProxy();
	    return servicioNotificacionesEndPoint;
	  }
	  
	  public java.lang.String calcularAnagrama(java.lang.String string_1) throws java.rmi.RemoteException{
	    if (servicioNotificacionesEndPoint == null)
	      _initServicioNotificacionesEndPointProxy();
	    return servicioNotificacionesEndPoint.calcularAnagrama(string_1);
	  }
	  
	  public ieci.tecdoc.sgm.nt.sisnot.client.types.EstadoNotificacion consultaEstadoNotificacion(long long_1) throws java.rmi.RemoteException{
	    if (servicioNotificacionesEndPoint == null)
	      _initServicioNotificacionesEndPointProxy();
	    return servicioNotificacionesEndPoint.consultaEstadoNotificacion(long_1);
	  }
	  
	  public ieci.tecdoc.sgm.nt.sisnot.client.types.InfoSuscripcion consultaSuscripcion(java.lang.String string_1, java.lang.String string_2) throws java.rmi.RemoteException{
	    if (servicioNotificacionesEndPoint == null)
	      _initServicioNotificacionesEndPointProxy();
	    return servicioNotificacionesEndPoint.consultaSuscripcion(string_1, string_2);
	  }
	  
	  public java.lang.String consultaSuscripcionSimple(java.lang.String string_1, java.lang.String string_2) throws java.rmi.RemoteException{
	    if (servicioNotificacionesEndPoint == null)
	      _initServicioNotificacionesEndPointProxy();
	    return servicioNotificacionesEndPoint.consultaSuscripcionSimple(string_1, string_2);
	  }
	  
	  public ieci.tecdoc.sgm.nt.sisnot.client.types.ConfirmacionAltaNotificacion crearNotificacion(ieci.tecdoc.sgm.nt.sisnot.client.types.AltaNotificacion altaNotificacion_1) throws java.rmi.RemoteException{
	    if (servicioNotificacionesEndPoint == null)
	      _initServicioNotificacionesEndPointProxy();
	    return servicioNotificacionesEndPoint.crearNotificacion(altaNotificacion_1);
	  }
	  
	  public ieci.tecdoc.sgm.nt.sisnot.client.types.map.ConfirmacionAltaNotificacion[] crearNotificacionMultidestinatario(ieci.tecdoc.sgm.nt.sisnot.client.types.AltaNotificacionMultidestinatario altaNotificacionMultidestinatario_1) throws java.rmi.RemoteException{
	    if (servicioNotificacionesEndPoint == null)
	      _initServicioNotificacionesEndPointProxy();
	    return servicioNotificacionesEndPoint.crearNotificacionMultidestinatario(altaNotificacionMultidestinatario_1);
	  }
	  
	  public java.lang.String[] crearNotificacionMultidestinatarioSimple(java.lang.String string_1, java.lang.String[] arrayOfString_2, java.lang.String string_3, java.lang.String string_4, java.lang.String string_5, java.lang.String string_6, java.lang.String string_7, java.lang.String string_8, java.lang.String string_9, java.lang.String string_10, java.lang.String string_11) throws java.rmi.RemoteException{
	    if (servicioNotificacionesEndPoint == null)
	      _initServicioNotificacionesEndPointProxy();
	    return servicioNotificacionesEndPoint.crearNotificacionMultidestinatarioSimple(string_1, arrayOfString_2, string_3, string_4, string_5, string_6, string_7, string_8, string_9, string_10, string_11);
	  }
	  
	  public java.lang.String crearNotificacionSimple(java.lang.String string_1, java.lang.String string_2, java.lang.String string_3, java.lang.String string_4, java.lang.String string_5, java.lang.String string_6, java.lang.String string_7, java.lang.String string_8, java.lang.String string_9, java.lang.String string_10, java.lang.String string_11) throws java.rmi.RemoteException{
	    if (servicioNotificacionesEndPoint == null)
	      _initServicioNotificacionesEndPointProxy();
	    return servicioNotificacionesEndPoint.crearNotificacionSimple(string_1, string_2, string_3, string_4, string_5, string_6, string_7, string_8, string_9, string_10, string_11);
	  }
	  
	  public ieci.tecdoc.sgm.nt.sisnot.client.types.map.ConfirmacionAltaNotificacion[] crearNotificaciones(ieci.tecdoc.sgm.nt.sisnot.client.types.AltaNotificacion[] arrayOfAltaNotificacion_1) throws java.rmi.RemoteException{
	    if (servicioNotificacionesEndPoint == null)
	      _initServicioNotificacionesEndPointProxy();
	    return servicioNotificacionesEndPoint.crearNotificaciones(arrayOfAltaNotificacion_1);
	  }
	  
	  public ieci.tecdoc.sgm.nt.sisnot.client.types.ConfirmacionAltaNotificacion[][] crearNotificacionesMultidestinatario(ieci.tecdoc.sgm.nt.sisnot.client.types.AltaNotificacionMultidestinatario[] arrayOfAltaNotificacionMultidestinatario_1) throws java.rmi.RemoteException{
	    if (servicioNotificacionesEndPoint == null)
	      _initServicioNotificacionesEndPointProxy();
	    return servicioNotificacionesEndPoint.crearNotificacionesMultidestinatario(arrayOfAltaNotificacionMultidestinatario_1);
	  }
	  
	  
	}