package ieci.tecdoc.sgm.tram.secretaria.action;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.configuration.ConfigurationMgr;
import ieci.tecdoc.sgm.tram.secretaria.helper.SecretariaConstants;

import org.apache.log4j.Logger;


public class GenerateLibroActasJuntasGobiernoAction extends GenerateLibroActasSesionesAction {

	private static final Logger logger = Logger.getLogger(GenerateLibroActasJuntasGobiernoAction.class);





	protected String getIdTpDocLibro() throws ISPACException{
		return ctx.getAPI().getCatalogAPI().getIdTpDocByCode(SecretariaConstants.COD_TPDOC_LIBRO_ACTAS_JG);

	}


	public String getCodPcd() throws ISPACException {
		return ConfigurationMgr.getVarGlobal(ctx,SecretariaConstants.COD_PCD_JUNTA_GOBIERNO);
	}

}
