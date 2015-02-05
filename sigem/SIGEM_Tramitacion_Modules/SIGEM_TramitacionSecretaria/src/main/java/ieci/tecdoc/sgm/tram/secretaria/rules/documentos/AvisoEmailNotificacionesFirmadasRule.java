package ieci.tecdoc.sgm.tram.secretaria.rules.documentos;

import ieci.tdw.ispac.api.ICatalogAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.rule.email.AvisoEmailRule;
import ieci.tdw.ispac.ispaclib.common.constants.SignStatesConstants;
import ieci.tdw.ispac.ispaclib.context.IClientContext;
import ieci.tdw.ispac.ispaclib.utils.DBUtil;
import ieci.tecdoc.sgm.tram.secretaria.helper.SecretariaConstants;

import org.apache.log4j.Logger;


/**
 * Cuando se firmen todas los documentos de tipo 'Notificacion decreto' del trámite Notificación
 * se crea un hito que indica que se enviará por un email un aviso con todas las notificaciones firmadas como adjunto:
 *
 *
 * <p>Para el envío de correos electrónicos, es necesario definir las
 * siguientes variables del sistema en el Catálogo de Procedimientos:</p>
 *
 * <ul>
 * <li>AVISO_NOTIFICACIONES_FIRMADAS_EMAIL_FROM: Dirección de correo electrónico remitente.</li>
 * <li>AVISO_NOTIFICACIONES_FIRMADAS_SUBJECT: Asunto del correo electrónico.</li>
 * <li>AVISO_NOTIFICACIONES_FIRMADAS_EMAIL_CONTENT: Contenido del correo electrónico.</li>
 * </ul>
 *
 *
 *
 * <p>Los mensajes definidos para el asunto y contenido de los correos electrónicos
 *  El formato de las variables es
 * ${NOMBRE_VARIABLE}. Las variables definidas son:</p>
 *
 * <ul>
 * <li>NUMEXP: Número de expediente al que pertenece el documento.</li>
 * </ul>
 *
 * <ul>Los destinatarios se obtienen de la entidad de participantes cuyo rol sea traslado y tenga el campo dirnot informado y de la entidad
 * expedientes el rol sea traslado y el campo dirtelematica esté informado/li>
 * </ul>
 *
 */
public class AvisoEmailNotificacionesFirmadasRule extends AvisoEmailRule{

	/**
	 * Logger de la clase.
	 */
	private static final Logger logger = Logger.getLogger(AvisoEmailNotificacionesFirmadasRule.class);


	public String getEmailContent() {

		return SecretariaConstants.EMAIL_CONTENT_VAR_NAME_NOTIFICACIONES;
	}

	public String getEmailFrom() {

		return SecretariaConstants.EMAIL_FROM_VAR_NAME_NOTIFICACIONES;
	}

	public String getEmailSubject() {

		return SecretariaConstants.EMAIL_SUBJECT_VAR_NAME_NOTIFICACIONES;
	}


	public String getQueryDestinario() {
		return "  ROL='"+DBUtil.replaceQuotes(SecretariaConstants.ROL_TRAMITADOR)+"' AND EMAIL!=''";
	}


	public String getQueryDocumento(IClientContext ctx, IItem item)
	throws ISPACException {
		String cadena;
		try {
			ICatalogAPI catalogAPI = ctx.getAPI().getCatalogAPI();
			String idTpDoc=catalogAPI.getIdTpDocByCode(SecretariaConstants.COD_TPDOC_NOTIFICACION_DECRETO);
			cadena = "SPAC_DT_DOCUMENTOS.ID_TRAMITE="+idTramite+" AND ID_TPDOC="+idTpDoc+
						" AND ESTADOFIRMA='"+DBUtil.replaceQuotes(SignStatesConstants.FIRMADO)+"'";
		} catch (ISPACException e) {
			logger.warn("Error en AvisoEmailNotificacionesFirmadasRule:getQueryDocumento"+e);
			throw e;
		}
		return cadena;
}


	public boolean isDocument() {
		return true;
	}


	public boolean oneEmailByEachDest() {
		return false;
	}

}
