/**
 *
 */
package salas.model;

import java.util.Locale;

import salas.vos.EdificioVO;
import salas.vos.MesaVO;
import salas.vos.RegistroConsultaSalaVO;
import salas.vos.SalaVO;
import salas.vos.TipoDocumentoIdentificativoVO;
import salas.vos.UsuarioSalasConsultaVO;
import auditoria.ArchivoDetails;
import auditoria.logger.DataLoggingEvent;
import auditoria.logger.LoggingEvent;

import common.Constants;
import common.Messages;
import common.bi.IServiceBase;
import common.definitions.ArchivoActions;
import common.definitions.ArchivoModules;
import common.definitions.ArchivoObjects;
import common.util.DateUtils;

/**
 * @author Iecisa
 * @version $Revision$
 * 
 */
public class AuditSalasConsulta {

	/**
	 * Genera un evento de auditoria para una determinada acción a auditar
	 * llevada a cabo por una instancia de servicio
	 * 
	 * @param serviceToAudit
	 *            Instancia de servicio mediante el que se lleva a cabo la
	 *            acción a auditar
	 * @param action
	 *            Acción a auditar {@link ArchivoActions}
	 * @return Evento de auditoria
	 */
	public static LoggingEvent getLogginEvent(IServiceBase serviceToAudit,
			int action) {

		LoggingEvent event = new LoggingEvent(ArchivoModules.SALAS_MODULE,
				action, serviceToAudit.getServiceClient(), false);

		serviceToAudit.getLogger().add(event);

		return event;
	}

	/**
	 * Incorpora el identificador de un edificio como dato de un evento de
	 * auditoria
	 * 
	 * @param event
	 *            Evento de auditoria
	 * @param idUInstalacion
	 *            Identificador de unidad de instalación
	 */
	public static DataLoggingEvent addDataLogInfoEdificio(Locale locale,
			LoggingEvent event, EdificioVO edificioVO) {
		if (edificioVO != null) {
			DataLoggingEvent data = event.getDataLoggingEvent(
					ArchivoObjects.OBJECT_EDIFICIO, edificioVO.getId());
			data.addDetalle(locale, ArchivoDetails.SALAS_EDIFICIO_ID,
					edificioVO.getId());
			data.addDetalle(locale, ArchivoDetails.SALAS_EDIFICIO_NOMBRE,
					edificioVO.getNombre());
			data.addDetalle(locale, ArchivoDetails.SALAS_EDIFICIO_UBICACION,
					edificioVO.getUbicacion());
			data.addDetalle(locale, ArchivoDetails.SALAS_EDIFICIO_IDARCHIVO,
					edificioVO.getIdArchivo());
			data.addDetalle(locale,
					ArchivoDetails.SALAS_EDIFICIO_NOMBREARCHIVO,
					edificioVO.getNombreArchivo());
		}
		return null;
	}

	/**
	 * Incorpora el identificador de una sala como dato de un evento de
	 * auditoria
	 * 
	 * @param event
	 *            Evento de auditoria
	 * @param idUInstalacion
	 *            Identificador de unidad de instalación
	 * @param salaVO
	 *            Datos a incoporar de la sala
	 */
	public static DataLoggingEvent addDataLogInfoSala(Locale locale,
			LoggingEvent event, SalaVO salaVO) {
		if (salaVO != null) {
			DataLoggingEvent data = event.getDataLoggingEvent(
					ArchivoObjects.OBJECT_SALA, salaVO.getId());
			data.addDetalle(locale, ArchivoDetails.SALAS_SALA_ID,
					salaVO.getId());
			data.addDetalle(locale, ArchivoDetails.SALAS_SALA_NOMBRE,
					salaVO.getNombre());
			data.addDetalle(locale, ArchivoDetails.SALAS_SALA_DESCRIPCION,
					salaVO.getDescripcion());
			data.addDetalle(locale,
					ArchivoDetails.SALAS_SALA_EQUIPOINFORMATICO,
					salaVO.getEquipoInformatico());
			data.addDetalle(locale, ArchivoDetails.SALAS_SALA_IDEDIFICIO,
					salaVO.getIdEdificio());
		}
		return null;
	}

	public static DataLoggingEvent addDataLogInfoMesa(Locale locale,
			LoggingEvent event, MesaVO mesaVO) {
		if (mesaVO != null) {
			DataLoggingEvent data = event.getDataLoggingEvent(
					ArchivoObjects.OBJECT_MESA, mesaVO.getId());
			data.addDetalle(locale, ArchivoDetails.SALAS_MESA_ID,
					mesaVO.getId());
			data.addDetalle(locale, ArchivoDetails.SALAS_MESA_CODIGO,
					mesaVO.getCodigo());
			data.addDetalle(locale, ArchivoDetails.SALAS_MESA_NUM_ORDEN,
					String.valueOf(mesaVO.getNumOrden()));
			data.addDetalle(locale, ArchivoDetails.SALAS_MESA_IDSALA,
					mesaVO.getIdSala());
			data.addDetalle(locale, ArchivoDetails.SALAS_MESA_ESTADO,
					mesaVO.getEstado());
			data.addDetalle(locale, ArchivoDetails.SALAS_MESA_FECHAESTADO,
					DateUtils.formatDate(mesaVO.getFechaEstado()));
			if (mesaVO.getIdUsrCSala() != null)
				data.addDetalle(locale, ArchivoDetails.SALAS_MESA_IDSCAUSR,
						mesaVO.getIdUsrCSala());
		}
		return null;
	}

	public static DataLoggingEvent addDataLogInfoRegistro(Locale locale,
			LoggingEvent event, RegistroConsultaSalaVO registroConsultaSalaVO) {
		if (registroConsultaSalaVO != null) {
			DataLoggingEvent data = event.getDataLoggingEvent(
					ArchivoObjects.OBJECT_REGISTRO_CONSULTA,
					registroConsultaSalaVO.getId());
			data.addDetalle(locale, ArchivoDetails.SALAS_REGISTRO_ID,
					registroConsultaSalaVO.getId());
			data.addDetalle(locale, ArchivoDetails.SALAS_REGISTRO_ID_USRCSALA,
					registroConsultaSalaVO.getIdUsrCSala());
			data.addDetalle(locale,
					ArchivoDetails.SALAS_REGISTRO_FECHA_ENTRADA, DateUtils
							.formatDate(registroConsultaSalaVO
									.getFechaEntrada()));
			data.addDetalle(locale,
					ArchivoDetails.SALAS_REGISTRO_USUARIO_NOMBRE_APELLIDOS,
					registroConsultaSalaVO.getNombreApellidos());
			data.addDetalle(locale,
					ArchivoDetails.SALAS_REGISTRO_USUARIO_NUMDOC, String
							.valueOf(registroConsultaSalaVO
									.getNumDocIdentificacion()));
			data.addDetalle(locale, ArchivoDetails.SALAS_REGISTRO_ARCHIVO,
					registroConsultaSalaVO.getIdArchivo());
			data.addDetalle(locale,
					ArchivoDetails.SALAS_REGISTRO_MESA_ASIGNADA,
					registroConsultaSalaVO.getMesaAsignada());
		}
		return null;
	}

	public static DataLoggingEvent addDataLogInfoUsuario(Locale locale,
			LoggingEvent event, UsuarioSalasConsultaVO usuarioVO) {
		if (usuarioVO != null) {
			DataLoggingEvent data = event.getDataLoggingEvent(
					ArchivoObjects.OBJECT_USUARIO_SALA_CONSULTA,
					usuarioVO.getId());
			String tipoDocIdentificacion = Constants.STRING_EMPTY;

			if (usuarioVO.getTipoDocIdentificacion() != null) {
				tipoDocIdentificacion = Messages
						.getString(TipoDocumentoIdentificativoVO
								.getKey(usuarioVO.getTipoDocIdentificacion()
										.toString()), locale);
			}
			data.addDetalle(locale,
					ArchivoDetails.SALAS_USUARIO_TIPO_DOC_IDENTIFICACION,
					tipoDocIdentificacion);
			data.addDetalle(locale,
					ArchivoDetails.SALAS_USUARIO_NUM_DOC_IDENTIFICACION,
					usuarioVO.getNumDocIdentificacion());
			data.addDetalle(locale, ArchivoDetails.SALAS_USUARIO_NOMBRE,
					usuarioVO.getNombre());
			data.addDetalle(locale, ArchivoDetails.SALAS_USUARIO_APELLIDOS,
					usuarioVO.getApellidos());
			data.addDetalle(locale, ArchivoDetails.SALAS_USUARIO_NACIONALIDAD,
					usuarioVO.getNacionalidad());
			data.addDetalle(locale, ArchivoDetails.SALAS_USUARIO_TELEFONOS,
					usuarioVO.getTelefonos());
			data.addDetalle(locale, ArchivoDetails.SALAS_USUARIO_EMAIL,
					usuarioVO.getEmail());
			data.addDetalle(locale, ArchivoDetails.SALAS_USUARIO_DIRECCION,
					usuarioVO.getDireccion());
			data.addDetalle(locale, ArchivoDetails.SALAS_USUARIO_VIGENTE,
					usuarioVO.getVigente());
			data.addDetalle(locale,
					ArchivoDetails.SALAS_USUARIO_IDUSUARIO_EXTERNO,
					usuarioVO.getIdscausr());

		}
		return null;
	}
}