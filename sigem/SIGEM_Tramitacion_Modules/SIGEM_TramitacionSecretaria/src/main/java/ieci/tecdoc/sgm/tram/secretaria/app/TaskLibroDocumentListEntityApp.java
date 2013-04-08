package ieci.tecdoc.sgm.tram.secretaria.app;

import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.ISPACEntities;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.ispaclib.common.constants.SignStatesConstants;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.utils.DBUtil;
import ieci.tdw.ispac.ispacmgr.app.TaskDocumentListEntityApp;
import ieci.tecdoc.sgm.tram.secretaria.helper.SecretariaConstants;

public abstract class TaskLibroDocumentListEntityApp extends TaskDocumentListEntityApp {

	public TaskLibroDocumentListEntityApp(ClientContext context)
	{
		super(context);
	}

	public void initiate() throws ISPACException {

		super.initiate();

		String idTpDocDiligenciaCierre=getIdTpDocDiligenciaCierre();
		String idTpDocDiligenciaApertura=getIdTpDocDiligenciaApertura();
		String idTpDocLibroDecretos=getIdTpDocLibro();
		String numexp=(String) getProperty("SPAC_DT_TRAMITES:NUMEXP");

	    //Obtengo los documentos del trámite
		IEntitiesAPI entitiesAPI = mContext.getAPI().getEntitiesAPI();
		IItemCollection itemCollectionApertura = entitiesAPI.queryEntities(
	        		ISPACEntities.DT_ID_DOCUMENTOS,
	        		"WHERE NUMEXP = '" + DBUtil.replaceQuotes(numexp) +"' AND ID_TPDOC="+idTpDocDiligenciaApertura+
	        		" AND ESTADOFIRMA='"+DBUtil.replaceQuotes(SignStatesConstants.FIRMADO)+"'");
		IItemCollection itemCollectionCierre = entitiesAPI.queryEntities(
        		ISPACEntities.DT_ID_DOCUMENTOS,
        		"WHERE NUMEXP = '" + DBUtil.replaceQuotes(numexp) +"' AND ID_TPDOC="+idTpDocDiligenciaCierre+
        		" AND ESTADOFIRMA='"+DBUtil.replaceQuotes(SignStatesConstants.FIRMADO)+"' ");

		IItemCollection itemCollectionLibro = entitiesAPI.queryEntities(
        		ISPACEntities.DT_ID_DOCUMENTOS,
        		"WHERE NUMEXP = '" + DBUtil.replaceQuotes(numexp) +"' AND ID_TPDOC="+idTpDocLibroDecretos);
		//Si ya tenemos firmado el documento de apertura y de cierre y aún no hemos generado el libro mostramos enlace
		if(itemCollectionApertura.next() && itemCollectionCierre.next() && !itemCollectionLibro.next()){
			setProperty("GENERATE_BOOK", "1");
			setProperty("ACTION" , getAction());
		}


	}

	protected String getIdTpDocDiligenciaCierre() throws ISPACException{
		return mContext.getAPI().getCatalogAPI().getIdTpDocByCode(SecretariaConstants.COD_TPDOC_DILIGENCIA_CIERRE);

	}

	protected String getIdTpDocDiligenciaApertura() throws ISPACException{
		return mContext.getAPI().getCatalogAPI().getIdTpDocByCode(SecretariaConstants.COD_TPDOC_DILIGENCIA_APERTURA);
	}

	public abstract String getIdTpDocLibro()throws ISPACException;

	public abstract String getAction()throws ISPACException;


}