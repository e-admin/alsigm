package ieci.tecdoc.sgm.tram.secretaria.app;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tecdoc.sgm.tram.secretaria.helper.SecretariaConstants;

public  class TaskLibroDecretoDocumentListEntityApp extends TaskLibroDocumentListEntityApp {

	public TaskLibroDecretoDocumentListEntityApp(ClientContext context) {
		super(context);

	}

	public String getAction() throws ISPACException {

		return "generateLibroDecretos.do";
	}

	public String getIdTpDocLibro() throws ISPACException {

		return mContext.getAPI().getCatalogAPI().getIdTpDocByCode(SecretariaConstants.COD_TPDOC_LIBRO_DECRETOS);


	}




}