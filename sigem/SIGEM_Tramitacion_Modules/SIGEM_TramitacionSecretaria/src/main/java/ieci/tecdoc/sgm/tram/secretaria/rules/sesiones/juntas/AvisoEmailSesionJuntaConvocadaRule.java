package ieci.tecdoc.sgm.tram.secretaria.rules.sesiones.juntas;

import ieci.tecdoc.sgm.tram.secretaria.helper.SecretariaConstants;
import ieci.tecdoc.sgm.tram.secretaria.rules.sesiones.AvisoEmailSesionConvocadaRule;

import org.apache.log4j.Logger;

/**
 * Cuando se cierra el trámite Notificación Convocatria de pleno
 *  se crea el hito para que se proceda a su envio desde el publicador.
 *
 *
 * <p>Para el envío de correos electrónicos, es necesario definir las
 * siguientes variables del sistema en el Catálogo de Procedimientos:</p>
 *
 * <ul>
 * <li>AVISO_CNV_JG_EMAIL_FROM_CONTENT: Dirección de correo electrónico remitente.</li>
 * <li>AVISO_CNV_JG_EMAIL_FROM_SUBJECT: Asunto del correo electrónico.</li>
 * <li>AVISO_CNV_JG_EMAIL_FROM_CONTENT: Contenido del correo electrónico.</li>
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
 * <li>DOCUMENT_NAME: Nombre del documento.</li>
 * <li>DOCUMENT_DESC: Descripción documento.</li>
 * <li>DOCUMENT_DATE: Fecha del Documento.</li>
 * <li>DOCUMENT_DATE_SIGN: Fecha del Documento.</li>
 * </ul>
 *
 * <ul><li>Los destinatarios se obtienen de la entidad de participantes cuyo rol sea traslado y tenga el campo dirnot informado</li>
 * </ul>
 *
 */
public class AvisoEmailSesionJuntaConvocadaRule extends AvisoEmailSesionConvocadaRule {
	/**
	 * Logger de la clase.
	 */
	private static final Logger logger = Logger.getLogger(AvisoEmailSesionJuntaConvocadaRule.class);


	public String getEmailContent() {
		return SecretariaConstants.EMAIL_CONTENT_VAR_NAME_CNV_JG;
	}

	public String getEmailFrom() {
		return SecretariaConstants.EMAIL_FROM_VAR_NAME_CNV_JG;
	}

	public String getEmailSubject() {
		return SecretariaConstants.EMAIL_SUBJECT_VAR_NAME_CNV_JG;
	}

	public String getCodTipoDocumental() {
		return SecretariaConstants.COD_TPDOC_NOT_CNV_JG;
	}









}
