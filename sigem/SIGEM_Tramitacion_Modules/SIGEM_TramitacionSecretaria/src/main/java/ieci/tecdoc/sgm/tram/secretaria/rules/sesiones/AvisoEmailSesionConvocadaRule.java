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


public abstract class AvisoEmailSesionConvocadaRule extends AvisoEmailRule {
	/**
	 * Logger de la clase.
	 */
	private static final Logger logger = Logger.getLogger(AvisoEmailSesionConvocadaRule.class);


	public  abstract String getEmailContent();

	public abstract String getEmailFrom();

	public abstract  String getEmailSubject();

	public abstract String getCodTipoDocumental();

	public String getQueryDestinario() {
		return "  ROL='"+DBUtil.replaceQuotes(SecretariaConstants.ROL_TRASLADO)+"' AND EMAIL!=''";
	}


	public String getQueryDocumento(IClientContext ctx,IItem item) throws ISPACException {
		String cadena;
		try {
			cadena = "SPAC_DT_DOCUMENTOS.ID_TRAMITE="+idTramite+
			" AND ID_TPDOC IN (SELECT ID FROM SPAC_CT_TPDOC WHERE COD_TPDOC='"+
			DBUtil.replaceQuotes(ConfigurationMgr.getVarGlobal(ctx, getCodTipoDocumental()))+"') "+
			"AND SPAC_DT_DOCUMENTOS.ESTADOFIRMA='"+DBUtil.replaceQuotes(ISignAPI.ESTADO_FIRMADO)+"'";
		} catch (ISPACException e) {
			logger.warn("Error en AvisoEmailSesionJuntaConvocadaRule:getQueryDocumento"+e);
			throw e;
		}
		return cadena;
	}


	public boolean oneEmailByEachDest() {
		return false;
	}


	public boolean isDocument() {
		return true;
	}





}
