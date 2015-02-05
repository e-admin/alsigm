package ieci.tecdoc.sgm.tram.secretaria.action;

import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.entities.SpacEntities;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACInfo;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.api.item.IProcess;
import ieci.tdw.ispac.ispaclib.configuration.ConfigurationMgr;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.dao.join.TableJoinFactoryDAO;
import ieci.tdw.ispac.ispaclib.utils.DBUtil;
import ieci.tdw.ispac.ispaclib.utils.IItemHelper;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispacmgr.action.BaseAction;
import ieci.tdw.ispac.ispacweb.api.IManagerAPI;
import ieci.tdw.ispac.ispacweb.api.IState;
import ieci.tdw.ispac.ispacweb.api.ManagerAPIFactory;
import ieci.tecdoc.sgm.tram.secretaria.helper.SecretariaConstants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Incluye los integrantes en funcion del orgáno Sesión Plenaria o Junta de Gobierno
 * @author IECISA
 *
 */
public class ImportIntegrantesAction   extends BaseAction {

	private static final Logger logger = Logger.getLogger(ImportIntegrantesAction.class);


public ActionForward executeAction(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response,
		SessionAPI session) throws Exception {


	ClientContext ctx=session.getClientContext();
	///////////////////////////////////////////////
	// Se obtiene el estado de tramitación
	IManagerAPI managerAPI = ManagerAPIFactory.getInstance().getManagerAPI(ctx);

	// Estado actual
	IState state = managerAPI.currentState(getStateticket(request));
	String numexp = state.getNumexp();
	String tipo_organo="";
	IInvesflowAPI invesflowAPI = ctx.getAPI();
	IEntitiesAPI entitiesAPI=invesflowAPI.getEntitiesAPI();
	//A través del numexp obtenemos el cod_pcd para saber si estamos en un pleno o en una junta
	IItemCollection itemcol=entitiesAPI.getEntities(SpacEntities.SPAC_EXPEDIENTES, numexp);
	if(itemcol.next()){

		IItem expediente=itemcol.value();

		if(StringUtils.equalsIgnoreCase(expediente.getString("CODPROCEDIMIENTO"),ConfigurationMgr.getVarGlobal(ctx,SecretariaConstants.COD_PCD_SESIONES_PLENARIAS))){
			tipo_organo=SecretariaConstants.VALUE_ORGANO_GOBIERNO_PLENO;
		}
		else if(StringUtils.equalsIgnoreCase(expediente.getString("CODPROCEDIMIENTO"),ConfigurationMgr.getVarGlobal(ctx,SecretariaConstants.COD_PCD_JUNTA_GOBIERNO))){
			tipo_organo=SecretariaConstants.VALUE_ORGANO_GOBIERNO_JUNTA;
		}


	}

	String numExpGestionIntegrantes="";
	IItem item= null;
	IItem participante=null;

	if(logger.isDebugEnabled()){
		logger.debug("Vamos a importar los integrantes cuyo órgano de gobierno sea el valor "+tipo_organo);
	}





	TableJoinFactoryDAO tableJoinFactoryDAO = new TableJoinFactoryDAO();
	tableJoinFactoryDAO.addTable("SPAC_EXPEDIENTES","SPAC_EXPEDIENTES");
	tableJoinFactoryDAO.addTable("SPAC_PROCESOS","SPAC_PROCESOS");


	String codPcd= ConfigurationMgr.getVarGlobal(ctx,SecretariaConstants.COD_PCD_GESTION_INTEGRANTES);
	 itemcol= tableJoinFactoryDAO.queryDistinctTableJoin(ctx.getConnection(),
			" WHERE SPAC_EXPEDIENTES.CODPROCEDIMIENTO='"+codPcd+"' " +
			" AND SPAC_PROCESOS.ESTADO="+IProcess.OPEN+" AND " +
			" SPAC_PROCESOS.ID_PCD=SPAC_EXPEDIENTES.ID_PCD  AND"+
			" SPAC_PROCESOS.NUMEXP=SPAC_EXPEDIENTES.NUMEXP"
			, "SPAC_EXPEDIENTES.NUMEXP").disconnect();

	if(itemcol==null || !itemcol.next()){

		throw new ISPACInfo(getMessage(request, ctx.getLocale(), "noexiste.pcd.integrantes.abierto"));
	}
	item=itemcol.value();
	numExpGestionIntegrantes=item.getString("SPAC_EXPEDIENTES:NUMEXP");

	if(logger.isDebugEnabled()){
		logger.debug("Vamos a obtener los integrantes del expediente "+numExpGestionIntegrantes);
	}




	tableJoinFactoryDAO = new TableJoinFactoryDAO();
	tableJoinFactoryDAO.addTable("SPAC_DT_INTERVINIENTES","SPAC_DT_INTERVINIENTES");
	tableJoinFactoryDAO.addTable(SecretariaConstants.ENTITY_INTEGRANTE,SecretariaConstants.ENTITY_INTEGRANTE);
	tableJoinFactoryDAO.addTable(SecretariaConstants.TABLE_INTEGRANTE_MULTIVALUE_FIELDS_TYPE_STRING,
			SecretariaConstants.TABLE_INTEGRANTE_MULTIVALUE_FIELDS_TYPE_STRING);


	itemcol= tableJoinFactoryDAO.queryDistinctTableJoin(ctx.getConnection(),
							" WHERE "+SecretariaConstants.TABLE_INTEGRANTE_MULTIVALUE_FIELDS_TYPE_STRING+
							".REG_ID="+SecretariaConstants.ENTITY_INTEGRANTE+".ID AND "
							+SecretariaConstants.TABLE_INTEGRANTE_MULTIVALUE_FIELDS_TYPE_STRING+".FIELD='"+
							DBUtil.replaceQuotes(SecretariaConstants.FIELD_INTEGRANTE_ORGANO_GOBIERNO)+
							"' AND "+SecretariaConstants.ENTITY_INTEGRANTE+".ID_PARTICIPANTE=SPAC_DT_INTERVINIENTES.ID"+
							" AND value LIKE '"+tipo_organo+"-%' AND "+SecretariaConstants.ENTITY_INTEGRANTE+
							".NUMEXP='"+DBUtil.replaceQuotes(numExpGestionIntegrantes)+"' AND "+SecretariaConstants.ENTITY_INTEGRANTE+".NUMEXP=SPAC_DT_INTERVINIENTES.NUMEXP"
							, "SPAC_EXPEDIENTES.NUMEXP").disconnect();


	//Recorremos la lista de integrantes y los damos de alta en el expediente actual.
	// Ejecución en un contexto transaccional
	boolean ongoingTX = ctx.ongoingTX();
	boolean bCommit = false;

    try {
		if (!ongoingTX) {
			ctx.beginTX();
		}
		while(itemcol.next()){

			item=itemcol.value();
			participante=entitiesAPI.createEntity("SPAC_DT_INTERVINIENTES", numexp);
			IItemHelper.copyParticipantes(item, participante,"SPAC_DT_INTERVINIENTES:");
			participante.set("ROL", SecretariaConstants.ROL_TRASLADO);
			participante.store(ctx);
		}
		bCommit = true;
    	}catch (ISPACException e) {
			logger.error("ImportIntegrantesAction",e);
			throw(e);

		}finally {
			if (!ongoingTX) {
				ctx.endTX(bCommit);
		}
	}


	//Obtenemos el expediente de gestion de integrantes abierto (SOLO DEBERIA DE HABER UNO)
	request.setAttribute("refresh", "true");
	return mapping.findForward("success");
}

}