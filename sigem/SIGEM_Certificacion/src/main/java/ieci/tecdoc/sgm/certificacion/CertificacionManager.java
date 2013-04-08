package ieci.tecdoc.sgm.certificacion;

import java.util.ArrayList;
import java.util.Properties;

import org.apache.log4j.Logger;

import ieci.tecdoc.sgm.certificacion.bean.Fecha;
import ieci.tecdoc.sgm.certificacion.bean.Usuario;
import ieci.tecdoc.sgm.certificacion.database.CertificacionDatos;
import ieci.tecdoc.sgm.certificacion.datatype.CertificacionImpl;
import ieci.tecdoc.sgm.certificacion.exception.CertificacionException;
import ieci.tecdoc.sgm.certificacion.exception.CodigosErrorCertificacionException;
import ieci.tecdoc.sgm.certificacion.pdf.CertificacionPDF;
import ieci.tecdoc.sgm.certificacion.pdf.FirmadorPDF;
import ieci.tecdoc.sgm.certificacion.pdf.LectorPropiedadesPDF;
import ieci.tecdoc.sgm.certificacion.pdf.RetornoPdf;
import ieci.tecdoc.sgm.certificacion.pdf.bean.DatosPropiedades;
import ieci.tecdoc.sgm.certificacion.util.Defs;
import ieci.tecdoc.sgm.certificacion.util.Utilidades;
import ieci.tecdoc.sgm.certificacion.xml.bean.Campo;
import ieci.tecdoc.sgm.certificacion.xml.bean.DatosComunes;
import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.dto.Entidad;
import ieci.tecdoc.sgm.core.services.repositorio.RepositorioDocumentosRdeExcepcion;
import ieci.tecdoc.sgm.core.services.repositorio.ServicioRepositorioDocumentosTramitacion;

/**
 * Gestor base para la generación de certificaciones en formato PDF
 * @author José Antonio Nogales
 */
public class CertificacionManager extends CertificacionPDF {
	private static final Logger logger = Logger.getLogger(CertificacionManager.class);
	
	/**
	 * Método que genera una certificación en formato PDF
	 * @param entidad Datos de la entidad de origen
	 * @param xml Datos a incluir en la certificación
	 * @param tipo Tipo de certificación
	 * @return Array de bytes con la certificación en formato PDF
	 * @throws CertificacionException En caso de producirse algún error
	 */
	protected static RetornoPdf GenerarCertificacion(Entidad entidad, String xml, String tipo, String idUsuario) throws CertificacionException {
		RetornoPdf retorno = new RetornoPdf();;
		
		//Se carga el fichero de configuración del PDF para la entidad
		Properties propiedades = new Properties(); 
		rutaRecursos = Defs.RECURSOS + Defs.SEPARADOR + Defs.CONF + entidad.getIdentificador() + Defs.SEPARADOR;
		try{
			propiedades.load(Utilidades.leerFicheroRecursos(rutaRecursos + Defs.FILE_PROPERTIES1 + tipo + "_" + idioma + Defs.FILE_PROPERTIES2));
		}catch(Exception e){
			try {
				propiedades.load(Utilidades.leerFicheroRecursos(rutaRecursos + Defs.FILE_PROPERTIES1 + tipo + Defs.FILE_PROPERTIES2));
			} catch(Exception ex) {
				logger.error("Se ha producido un error en la lectura del fichero de configuración [GenerarCertificacion][Exception][Entidad:"+entidad+"]", e.fillInStackTrace());
				throw new CertificacionException(
					CodigosErrorCertificacionException.ERROR_LECTURA_FICHERO_CONFIGURACION,
					e.getMessage(), 
					e.getCause());
			}
		}
		
		try{
			byte[] firmado = null;
			
			//Se obtienen los datos de configuracion del PDF
			DatosPropiedades datosPropiedades = LectorPropiedadesPDF.LeerPropiedades(propiedades);
			
			//Se genera la certificación con el xml de datos y la configuración del PDF
			retorno = GenerarCertificacion(datosPropiedades, xml);
			byte[] bytes = retorno.getContenido();
			
			//Se firma el PDF
			FirmadorPDF firmador = new FirmadorPDF();
			firmador.setRutaRecursos(rutaRecursos);
			firmado = firmador.signReceipt(bytes, entidad.getNombre(), rutaRecursos + datosPropiedades.getImagenes().getImagenFirma());
			
			retorno.setContenido(firmado);
			
			try {
				ServicioRepositorioDocumentosTramitacion oServicioRde = LocalizadorServicios.getServicioRepositorioDocumentosTramitacion();
				oServicioRde.storeDocumentGuid(null, retorno.getIdentificador(), retorno.getContenido(), "pdf", entidad);
			}catch (RepositorioDocumentosRdeExcepcion rdee) {
				logger.error("Se ha producido un error al almacenar la certificacion [GenerarCertificacion][RepositorioDocumentosRdeExcepcion][Entidad:"+entidad+"]", rdee.fillInStackTrace());
				throw new CertificacionException(CodigosErrorCertificacionException.ERROR_ALMACENAR_CERTIFICACION, rdee.getCause());
			}catch (Exception se) {
				logger.error("Se ha producido un error al almacenar la certificacion [GenerarCertificacion][Excepcion][Entidad:"+entidad+"]", se.fillInStackTrace());
				throw new CertificacionException(CodigosErrorCertificacionException.ERROR_ALMACENAR_CERTIFICACION, se.getCause());
			}
			
			AltaCertificacion(idUsuario, retorno.getIdentificador(), entidad.getIdentificador());
		}catch(CertificacionException e2){
			throw e2;
		}

		return retorno;
	}
	
	/**
	 * Método que obtiene los datos comunes a todas las certificaciones (entidad, nombre, apellidos, ...)
	 * @param descripcionEntidad Descripción de la entidad de origen
	 * @param usuario Datos de usuario
	 * @param fecha Fecha de generación de la certificación
	 * @return Objeto con los datos comunes de la certificación
	 * @throws CertificacionException En caso de producirse algún error
	 */
	protected static DatosComunes obtenerDatosComunes(String descripcionEntidad, Usuario usuario, Fecha fecha) throws CertificacionException{
		try{
			Campo campo1 = new Campo (Defs.TAG_XML_ENTIDAD, descripcionEntidad, null);
			
			ArrayList campos1 = new ArrayList();
			campos1.add(new Campo(Defs.TAG_XML_NOMBRE, usuario.getNombre(), null));
			campos1.add(new Campo(Defs.TAG_XML_APELLIDOS, usuario.getApellidos(), null));
			campos1.add(new Campo(Defs.TAG_XML_ID, usuario.getNif(), null));
			
			Campo campo2 = new Campo (Defs.TAG_XML_USUARIO, null, campos1);
			
			ArrayList datosComunesArray = new ArrayList();
			datosComunesArray.add(campo1);
			datosComunesArray.add(campo2);
			datosComunesArray.add(new Campo(Defs.TAG_XML_MAIL, usuario.getEmail(), null));
			datosComunesArray.add(new Campo(Defs.TAG_XML_TELEFONO, usuario.getTelefono(), null));
			datosComunesArray.add(new Campo(Defs.TAG_XML_DOMICILIO, usuario.getDomicilio(), null));
			datosComunesArray.add(new Campo(Defs.TAG_XML_LOCALIDAD, usuario.getLocalidad(), null));
			datosComunesArray.add(new Campo(Defs.TAG_XML_PROVINCIA, usuario.getProvincia(), null));
			datosComunesArray.add(new Campo(Defs.TAG_XML_CP, usuario.getCp(), null));
			datosComunesArray.add(new Campo(Defs.TAG_XML_FECHA, fecha.getFecha(), null));
			datosComunesArray.add(new Campo(Defs.TAG_XML_HORA, fecha.getHora(), null));
			
			return new DatosComunes(datosComunesArray);
		}catch(Exception e){
			logger.error("Se ha producido un error al generar los datos comunes de la certificación [obtenerDatosComunes][Exception]", e.fillInStackTrace());
			throw new CertificacionException(
					CodigosErrorCertificacionException.ERROR_GENERACION_DATOS_COMUNES,
					e.getMessage(), 
					e.getCause());
		}
	}
	
	public static void AltaCertificacion (String idUsuario, String idFichero, String entidad) throws CertificacionException {
		try  {
			CertificacionDatos oCertificacion = new CertificacionDatos();
			oCertificacion.setIdUsuario(idUsuario);
			oCertificacion.setIdFichero(idFichero);
			oCertificacion.add(entidad);
		} catch (CertificacionException ce){
			logger.error("Error al dar de alta certificacion [AltaCertificacion][CertificacionException]["+entidad+"]", ce.fillInStackTrace());
			throw ce;
		} catch(Exception e) {
			logger.error("Error al dar de alta certificacion [AltaCertificacion][Exception]["+entidad+"]", e.fillInStackTrace());
			throw new CertificacionException(CodigosErrorCertificacionException.ERROR_INSERTAR_CERTIFICACION, e.getCause());
		}
	}
	
	public static void EliminarCertificacion(String idFichero, String entidad) throws CertificacionException {
		try {
			CertificacionDatos oCertificacion = new CertificacionDatos();
			oCertificacion.setIdFichero(idFichero);
			oCertificacion.delete(entidad);
		} catch(CertificacionException ce) {
			logger.error("Error al eliminar certificacion [EliminarCertificacion][CertificacionException]["+entidad+"]", ce.fillInStackTrace());
			throw ce;
		} catch(Exception e) {
			logger.error("Error al eliminar certificacion [EliminarCertificacion][Exception]["+entidad+"]", e.fillInStackTrace());
			throw new CertificacionException(CodigosErrorCertificacionException.ERROR_ELIMINAR_CERTIFICACION, e.getCause());
		}
	}
	
	public static CertificacionImpl ObtenerCertificacion(String idUsuario, String idFichero, String entidad) throws CertificacionException{
		CertificacionImpl poCertificacion = null;
		try {
			CertificacionDatos oCertificacion = new CertificacionDatos();
			oCertificacion.setIdFichero(idFichero);
			oCertificacion.setIdUsuario(idUsuario);
			oCertificacion.load(entidad);
			poCertificacion = obtenerCertificacionImpl(oCertificacion);
		} catch(CertificacionException ce) {
			logger.error("Error al obtener certificacion [ObtenerCertificacion][CertificacionException]["+entidad+"]", ce.fillInStackTrace());
			throw ce;
		} catch(Exception e) {
			logger.error("Error al obtener certificacion [ObtenerCertificacion][Exception]["+entidad+"]", e.fillInStackTrace());
			throw new CertificacionException(CodigosErrorCertificacionException.ERROR_OBTENER_CERTIFICACION, e.getCause());
		}
		return poCertificacion;
	}
	
	private static CertificacionImpl obtenerCertificacionImpl(CertificacionDatos certificacionDatos){
		if (certificacionDatos == null)
			return null;
		if (certificacionDatos.getIdFichero() == null || certificacionDatos.getIdFichero().equals("") || 
			certificacionDatos.getIdUsuario() == null || certificacionDatos.getIdUsuario().equals("")) 
			return null;
		CertificacionImpl oCertificacion = new CertificacionImpl();
		oCertificacion.setIdFichero(certificacionDatos.getIdFichero());
		oCertificacion.setIdUsuario(certificacionDatos.getIdUsuario());
		return oCertificacion;
	}
	
}
