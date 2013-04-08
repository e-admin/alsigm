package ieci.tecdoc.sgm.tram.secretaria.action;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.configuration.ConfigurationMgr;
import ieci.tecdoc.sgm.tram.secretaria.helper.SecretariaConstants;

import org.apache.log4j.Logger;


public class GenerateLibroActasSesionesPlenariasAction extends GenerateLibroActasSesionesAction {

	private static final Logger logger = Logger.getLogger(GenerateLibroActasSesionesPlenariasAction.class);


	public String getCodPcd() throws ISPACException {
		return ConfigurationMgr.getVarGlobal(ctx,SecretariaConstants.COD_PCD_SESIONES_PLENARIAS);
	}


	protected String getIdTpDocLibro() throws ISPACException{
		return ctx.getAPI().getCatalogAPI().getIdTpDocByCode(SecretariaConstants.COD_TPDOC_LIBRO_ACTAS_SP);

	}




}
