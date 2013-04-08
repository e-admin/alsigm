package es.ieci.tecdoc.isicres.api.audit.business.manager.handlers.impl;

import org.apache.commons.lang.StringUtils;

import es.ieci.tecdoc.fwktd.audit.core.vo.TrazaAuditoriaVO;
import es.ieci.tecdoc.isicres.api.audit.business.vo.IsicresAuditoriaValorModificadoVO;
import es.ieci.tecdoc.isicres.api.audit.business.vo.events.IsicresAuditAbstractBasicRegistroUserEventVO;
import es.ieci.tecdoc.isicres.api.audit.business.vo.events.IsicresAuditAbstractBasicUserEventVO;
import es.ieci.tecdoc.isicres.api.audit.business.vo.events.IsicresAuditAbstractCreacionModificacionRegistroEventVO;

/**
 * Clase helper para la generación de parte de información relacionada con las trazas de auditoría
 * @author IECISA
 *
 */
public class IsicresAuditEventTrazaBuilderHandlerHelper {

	/**
	 * metodo para popular a partir del parametro evento el parametro traza con
	 * los datos referentes al usuario generador del evento y fecha
	 *
	 * @param evento
	 * @param traza
	 */
	public static  void populateBasicUserEventTrazaAuditoria(
			IsicresAuditAbstractBasicUserEventVO evento, TrazaAuditoriaVO traza) {
		String appDescription = evento.getAppDescription();
		traza.setAppDescription(appDescription);

		Long appId = evento.getAppId();
		traza.setAppId(appId);

		// fecha de creacion
		traza.setEventDate(evento.getFecha());

		String userName = evento.getUser();
		traza.setUserName(userName);

		String userId = evento.getIdUser();
		traza.setUserId(userId);
		
		String oficinaId = evento.getIdOficina();
		if (StringUtils.isNotEmpty(oficinaId)) {
			traza.setUserId(oficinaId + "-" + userId);
		}

		String userHostName = evento.getUserHostName();
		traza.setUserHostName(userHostName);

		String userIp = evento.getUserIp();
		traza.setUserIp(userIp);

	}

	/**
	 * Método que devolverá la representación del ObjectId que representará un objeto de tipo registro
	 * @param eventoRegistro
	 * @return
	 */
	public static String generateRegistroObjectID(IsicresAuditAbstractBasicRegistroUserEventVO eventoRegistro){
		// TODO ver como generamos este identificador de objeto
		String result = eventoRegistro.getIdLibro() + "-"
					+ eventoRegistro.getNumRegistro();
		return result;
	}

	/**
	 * Método que devolverá el valor auditado para los eventos de creación o modificación de un registro o consulta
	 * @param eventoRegistro
	 * @return
	 */
	public static String generateRegistroValorAuditado(IsicresAuditAbstractBasicRegistroUserEventVO eventoRegistro){
		//TODO ver que valor auditamos q formato --> ¿xml con los valores del registro? INSTANCEOF
		StringBuffer sb = new StringBuffer();
		sb.append("Acceso al registro [")
				.append(eventoRegistro.getNumRegistro())
				.append("] por el usuario [").append(eventoRegistro.getUser())
				.append("] desde la oficina [").append(eventoRegistro.getOficina())
				.append("] a fecha [").append(eventoRegistro.getFecha()).append("]");
		String result = sb.toString();
		return result;
	}

	/**
	 * Método que devolverá la representación del ObjectId que representará el campo de registro que es modificado o creado
	 *
	 * @param eventoCreacionModificacionRegistro
	 * @param campoRegistro
	 * @return
	 */
	public static String generateRegistroFieldObject(IsicresAuditAbstractCreacionModificacionRegistroEventVO eventoCreacionModificacionRegistro,IsicresAuditoriaValorModificadoVO campoRegistro ){
		// TODO ver que ponemos aqui como objecField -->fld1???? fld2?? ...
		String result= campoRegistro.getFieldName();

		return result;
	}
}
