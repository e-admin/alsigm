package ieci.tecdoc.sgm.nt.conectores;


import ieci.tecdoc.sgm.nt.bean.DetalleEstadoBean;
import ieci.tecdoc.sgm.nt.conectores.types.ResultadoAltaNotificacion;
import ieci.tecdoc.sgm.nt.database.datatypes.Notificacion;
import ieci.tecdoc.sgm.nt.exception.ServicioWebExcepcion;

public interface ConectorNotificacion {
	boolean checkConnection(int timeout,String url);
	ResultadoAltaNotificacion crearNotificacion(Object notificacion,String entidad,String idSession) throws Exception;
	DetalleEstadoBean obtenerEstado(Notificacion notificacion,String entidad) throws ServicioWebExcepcion;
	boolean isEstadoFallo(Integer estadoNotificacion);
	Integer getEstadoNotificacionCreada();
	//DocumentoInfo recuperaDocumento(NotificacionBean notificacion);
}
