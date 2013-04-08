package ieci.tecdoc.sgm.tram.secretaria.rules.sesiones;

import ieci.tdw.ispac.api.ISignAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.rule.email.AvisoEmailRule;
import ieci.tdw.ispac.ispaclib.configuration.ConfigurationMgr;
import ieci.tdw.ispac.ispaclib.context.IClientContext;
import ieci.tdw.ispac.ispaclib.utils.DBUtil;
import ieci.tecdoc.sgm.tram.secretaria.helper.SecretariaConstants;

import org.apache.log4j.Logger;


public abstract class AvisoEmailCertificacionAcuerdoSesionPropuestaRule extends AvisoEmailRule {
	/**
	 * Logger de la clase.
	 */
	private static final Logger logger = Logger.getLogger(AvisoEmailCertificacionAcuerdoSesionPropuestaRule.class);



	public abstract  String getEmailContent() ;


	public abstract String getEmailFrom() ;

	public abstract String getEmailSubject() ;

	public String getQueryDestinario() {
		return "  ROL='"+DBUtil.replaceQuotes(SecretariaConstants.ROL_TRAMITADOR_PROPUESTA)+"' AND EMAIL!=''";
	}

	public boolean isDocument() {
		return false;
	}

	public boolean oneEmailByEachDest() {
		return true;
	}

	public String getQueryDocumento(IClientContext ctx, IItem item)
			throws ISPACException {
		String cadena;
		try {
			cadena = " ID in  (SELECT D.ID FROM SPAC_DT_DOCUMENTOS D ,"+SecretariaConstants.ENTITY_PARTICIPANTES_SESION+
						" ,"+SecretariaConstants.ENTITY_PROPUESTA_SESION +",SPAC_CT_TPDOC"+
						" WHERE ID_REG_ENTIDAD="+SecretariaConstants.ENTITY_PROPUESTA_SESION +".ID AND "+
						SecretariaConstants.ENTITY_PROPUESTA_SESION +"."+SecretariaConstants.FIELD_PROPUESTA_SESION_ID_PROPUESTA +
						"="+SecretariaConstants.ENTITY_PARTICIPANTES_SESION +"."+SecretariaConstants.FIELD_PARTICIPANTES_SESION_ID_PROPUESTA +
						" AND "+SecretariaConstants.ENTITY_PARTICIPANTES_SESION +".NUMEXP=D.NUMEXP AND D.ID_TPDOC=SPAC_CT_TPDOC.ID"+
						" AND SPAC_CT_TPDOC.COD_TPDOC='"+DBUtil.replaceQuotes(ConfigurationMgr.getVarGlobal(ctx, SecretariaConstants.COD_TPDOC_CERT_A_SP))+"'"+
						" AND D.ESTADOFIRMA='"+DBUtil.replaceQuotes(ISignAPI.ESTADO_FIRMADO)+"' "+
						" AND "+SecretariaConstants.ENTITY_PARTICIPANTES_SESION+"."+SecretariaConstants.FIELD_PARTICIPANTES_SESION_ID_PARTICIPANTE+"="+item.getKeyInt()+")";
		} catch (ISPACException e) {
			logger.warn("Error en AvisoEmailCertificacionAcuerdoSesionPropuestaRule:getQueryDocumento"+e);
			throw e;
		}

		return cadena;
	}



}
