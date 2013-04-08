package ieci.tecdoc.sgm.tram.secretaria.app;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tecdoc.sgm.tram.secretaria.helper.SecretariaConstants;

import org.apache.log4j.Logger;

public  class TaskLibroActasJuntasDocumentListEntityApp extends TaskLibroDocumentListEntityApp {

	private static final Logger logger = Logger.getLogger(TaskLibroActasJuntasDocumentListEntityApp.class);

	public TaskLibroActasJuntasDocumentListEntityApp(ClientContext context) {
		super(context);

	}

	public String getAction() throws ISPACException {

		return "generateLibroActasJuntasGobiernoAction.do";
	}

	public String getIdTpDocLibro() throws ISPACException {
		try {
			return mContext.getAPI().getCatalogAPI().getIdTpDocByCode(SecretariaConstants.COD_TPDOC_ACTA_SP);


		} catch (ISPACException e) {
			if(logger.isDebugEnabled()){
				logger.debug("Error al obtener el valor de la variable de sistema" +
						SecretariaConstants.COD_TPDOC_ACTA_SP);

			}
			return "";
		}

	}




}