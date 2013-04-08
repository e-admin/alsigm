package ieci.tecdoc.sgm.core.services.certificacion;

import ieci.tecdoc.sgm.core.services.certificacion.pago.Pago;

public interface ServicioCertificacion {

	public RetornoPdf generarCertificacionPagos(ieci.tecdoc.sgm.core.services.dto.Entidad entidad, Pago[] pagos, Usuario usuario) throws CertificacionException;
	public void altaCertificacion(String idUsuario, String idFichero, ieci.tecdoc.sgm.core.services.dto.Entidad entidad) throws CertificacionException;
	public void eliminarCertificacion(String idFichero, ieci.tecdoc.sgm.core.services.dto.Entidad entidad) throws CertificacionException;
	public Certificacion obtenerCertificacion(String idUsuario, String idFichero, ieci.tecdoc.sgm.core.services.dto.Entidad entidad) throws CertificacionException;
}
