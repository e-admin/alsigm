package ieci.tecdoc.sgm.certificacion.manager;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import ieci.tecdoc.sgm.certificacion.CertificacionManager;
import ieci.tecdoc.sgm.certificacion.bean.Fecha;
import ieci.tecdoc.sgm.certificacion.bean.Usuario;
import ieci.tecdoc.sgm.certificacion.bean.pago.Pago;
import ieci.tecdoc.sgm.certificacion.exception.CertificacionException;
import ieci.tecdoc.sgm.certificacion.exception.CodigosErrorCertificacionException;
import ieci.tecdoc.sgm.certificacion.pdf.RetornoPdf;
import ieci.tecdoc.sgm.certificacion.util.Defs;
import ieci.tecdoc.sgm.certificacion.util.Utilidades;
import ieci.tecdoc.sgm.certificacion.xml.bean.DatosComunes;
import ieci.tecdoc.sgm.certificacion.xml.filler.GeneradorXMLPago;
import ieci.tecdoc.sgm.core.services.dto.Entidad;

/**
 * Gestor para las certificaciones de pagos. Contempla la parte específica de pagos
 * @author José Antonio Nogales
 */
public class CertificacionPagoManager extends CertificacionManager{
	private static final Logger logger = Logger.getLogger(CertificacionPagoManager.class);
			
	/**
	 * Método que genera una certificación de pagos en formato PDF
	 * @param entidad Datos sobre la entidad de origen
	 * @param pagos Datos de los pagos realizados por el ciudadano
	 * @param usuario Datos sobre el usuario
	 * @return Array de bytes con el contenidode la certificación de pagos en formato PDF
	 * @throws CertificacionException En caso de producirse algún error
	 */
	public static RetornoPdf GenerarCertificacion(Entidad entidad, Pago[] pagos, Usuario usuario) throws CertificacionException {
		SimpleDateFormat sdf = new SimpleDateFormat(Defs.FORMATO_FECHA);
		String f = sdf.format(new Date());
		int index = f.indexOf(" ");
		Fecha fechaCertificacion = new Fecha(f.substring(0,index), f.substring(index + 1));

		try{
			//Se obtiene los datos comunes
			DatosComunes datosComunes = obtenerDatosComunes(entidad.getNombre(), usuario, fechaCertificacion);
			
			//Se genera el XML con el que se trabajará
			String xml = GeneradorXMLPago.GenerarXML(datosComunes, pagos, true);
			xml = new String(Utilidades.fromStrToUTF8(xml));
			
			//Se establecen las rutas para los valores múltiples
			rutaDatosMultiples = Defs.TAG_RUTA_MULTIPLES_PAGOS;
			objetoDatosMultiples = Defs.TAG_OBJETO_MULTIPLES_PAGOS;
			idioma = usuario.getIdioma();
			if (idioma == null)
				idioma = "";
			
			//Se genera la certificación
			return GenerarCertificacion(entidad, xml, Defs.CERTIFICACION_PAGOS, usuario.getNif());
		}catch(CertificacionException e){
			throw e;
		}catch(Exception e){
			logger.error("Se ha producido un error al generar la certificación", e);
			throw new CertificacionException(
					CodigosErrorCertificacionException.ERROR_GENERACION_XML,
					e.getMessage(), 
					e.getCause());
		}
	}
}
