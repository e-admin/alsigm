package ieci.tecdoc.sgm.tram.secretaria.action;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.configuration.ConfigurationMgr;
import ieci.tdw.ispac.ispaclib.context.IClientContext;
import ieci.tdw.ispac.ispaclib.utils.DBUtil;
import ieci.tecdoc.sgm.tram.secretaria.helper.SecretariaConstants;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;



public class NotificacionUrgAcuerdosPropuestaAction extends GenerateAutomaticNotificationsAction {


	// Logger de la clase.
    private static final Logger logger =
    	Logger.getLogger(NotificacionUrgAcuerdosPropuestaAction.class);

	public String getAction() {
		return "//generateAutomaticAcuerdosPropuestaUrg.do";
	}
	public String getVarCodPlantilla(IClientContext cct, String numexp) throws ISPACException{
		String varCod=SecretariaConstants.COD_PLANTDOC_NOT_SE_SP_URG;

		if(StringUtils.containsIgnoreCase(numexp,"/"+ConfigurationMgr.getVarGlobal(cct,SecretariaConstants.COD_PCD_JUNTA_GOBIERNO)+"/")){
			varCod=SecretariaConstants.COD_PLANTDOC_NOT_SE_JG_URG;
		}
		return varCod;
	}

	public String getIdTipoDocumental(IClientContext cct) throws ISPACException {
		return cct.getAPI().getCatalogAPI().getIdTpDocByCode(SecretariaConstants.COD_TPDOC_NOT_SE_URG);

	}


/**
 * Para las notificaciones de los acuerdos de las propuestas , los participantes a los que se les va a adjuntar
 * la notificación son los participantes de la propuesta seleccionada en el trámite Notificación de acuerdos cuyo
 * rol sea notificado
 */
	public String getQuery(String numexp, int taskId) {

	String sql =" WHERE NUMEXP = '"+DBUtil.replaceQuotes(numexp)+"'"+
		 		" AND ROL='"+DBUtil.replaceQuotes(SecretariaConstants.ROL_NOTIFICADO)+"' AND ID IN (" +
		 		"SELECT psp."+SecretariaConstants.FIELD_PARTICIPANTES_SESION_ID_PARTICIPANTE+
		 		" FROM "+SecretariaConstants.ENTITY_PARTICIPANTES_SESION+" as psp , "+SecretariaConstants.ENTITY_NOT_ACUERDOS_PROPUESTA+" as nap"+
		 		" WHERE nap."+SecretariaConstants.FIELD_NOT_ACUERDOS_PROPUESTA_ID_TRAMITE+"="+taskId+
		 		" AND nap."+SecretariaConstants.FIELD_NOT_ACUERDOS_PROPUESTA_ID_PROPUESTA+"=psp."+SecretariaConstants.FIELD_PARTICIPANTES_SESION_ID_PROPUESTA+")";
	return sql;

	}





}