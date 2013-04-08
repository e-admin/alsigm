package ieci.tecdoc.sgm.tram.secretaria.action;

import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.entities.SpacEntities;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.ispaclib.bean.BeanFormatter;
import ieci.tdw.ispac.ispaclib.bean.CacheFormatterFactory;
import ieci.tdw.ispac.ispaclib.bean.CollectionBean;
import ieci.tdw.ispac.ispaclib.configuration.ConfigurationMgr;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.dao.join.TableJoinFactoryDAO;
import ieci.tdw.ispac.ispaclib.utils.DBUtil;
import ieci.tdw.ispac.ispacmgr.action.BaseDispatchAction;
import ieci.tdw.ispac.ispacmgr.action.form.EntityForm;
import ieci.tdw.ispac.ispactx.TXConstants;
import ieci.tdw.ispac.ispacweb.api.IManagerAPI;
import ieci.tdw.ispac.ispacweb.api.IState;
import ieci.tdw.ispac.ispacweb.api.ManagerAPIFactory;
import ieci.tdw.ispac.ispacweb.manager.ISPACRewrite;
import ieci.tecdoc.sgm.tram.secretaria.helper.SecretariaConstants;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class SendToSesionAction extends BaseDispatchAction{
	/** Logger de la clase. */
    private static final Logger logger =
    	Logger.getLogger( SendToSesionAction.class);

	 public ActionForward search(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response,
				SessionAPI session) throws Exception {

		ClientContext ctx=session.getClientContext();
		List lResults=new ArrayList();
		 ///////////////////////////////////////////////
		// Se obtiene el estado de tramitación
        IManagerAPI managerAPI = ManagerAPIFactory.getInstance().getManagerAPI(ctx);
   	    IState currentState = managerAPI.currentState(getStateticket(request));

		String codPcd="";
		ISPACRewrite ispacPath = new ISPACRewrite(getServlet()
				.getServletContext());
		String xml = ispacPath.rewriteRealPath("/digester/secretaria/sendPropuestaToSesionformatter.xml");


		EntityForm defaultForm = (EntityForm) form;
		String relacion="";

		String valor_envio_sesion=defaultForm.getProperty( SecretariaConstants.ENTITY_PROPUESTA+":"+SecretariaConstants.FIELD_PROPUESTA_ENVIO_SESION);
		if(StringUtils.equalsIgnoreCase(valor_envio_sesion, SecretariaConstants.VALUE_SESION_PLENO)){
				request.setAttribute("titleKey", "sesiones.title");
				codPcd= ConfigurationMgr.getVarGlobal(ctx,SecretariaConstants.COD_PCD_SESIONES_PLENARIAS);
				relacion = ConfigurationMgr.getVarGlobal(ctx,
						SecretariaConstants.VALUE_RELACION_SESION_PROPUESTA);
		}
		else if(StringUtils.equalsIgnoreCase(valor_envio_sesion,SecretariaConstants.VALUE_SESION_JUNTA_GOBIERNO)){
			request.setAttribute("titleKey", "junta.title");
			codPcd= ConfigurationMgr.getVarGlobal(ctx,SecretariaConstants.COD_PCD_JUNTA_GOBIERNO);
			relacion = ConfigurationMgr.getVarGlobal(ctx,
					SecretariaConstants.VALUE_RELACION_JUNTA_PROPUESTA);
		}


		if(logger.isDebugEnabled()){
			logger.debug("Se buscan los expedientes cuyo pcd sea : "+codPcd);
		}
		TableJoinFactoryDAO tableJoinFactoryDAO = new TableJoinFactoryDAO();
		tableJoinFactoryDAO.addTable("SPAC_EXPEDIENTES","SPAC_EXPEDIENTES");
		tableJoinFactoryDAO.addTable("SPAC_CT_PROCEDIMIENTOS","SPAC_CT_PROCEDIMIENTOS");
		tableJoinFactoryDAO.addTable("SPAC_P_PROCEDIMIENTOS","SPAC_P_PROCEDIMIENTOS");
		tableJoinFactoryDAO.addTable("SPAC_TBL_004","ESTADO");
		tableJoinFactoryDAO.addTable("SPAC_PROCESOS","PROCESOS");

		IItemCollection itemcol= tableJoinFactoryDAO.queryDistinctTableJoin(ctx.getConnection(),
				" WHERE  SPAC_CT_PROCEDIMIENTOS.COD_PCD='"+codPcd+"' " +
				" AND SPAC_CT_PROCEDIMIENTOS.ID=SPAC_P_PROCEDIMIENTOS.ID AND" +
				" SPAC_EXPEDIENTES.ID_PCD=SPAC_P_PROCEDIMIENTOS.ID AND "+
				" ESTADO.VALOR=SPAC_EXPEDIENTES.ESTADOADM AND "+
				" PROCESOS.NUMEXP=SPAC_EXPEDIENTES.NUMEXP AND"+
				" PROCESOS.ESTADO="+TXConstants.STATUS_OPEN +" AND "+
				" SPAC_EXPEDIENTES.NUMEXP NOT IN "+
				"( SELECT SPAC_EXP_RELACIONADOS.NUMEXP_PADRE FROM SPAC_EXP_RELACIONADOS  WHERE SPAC_EXP_RELACIONADOS.NUMEXP_HIJO='"+  currentState.getNumexp()+"'"+
					"   AND RELACION='"+DBUtil.replaceQuotes(relacion)+"') "
				 , "SPAC_EXPEDIENTES.NUMEXP").disconnect();


		lResults = CollectionBean.getBeanList(itemcol);
		request.setAttribute("ResultsList", lResults);

		CacheFormatterFactory factory = CacheFormatterFactory.getInstance();
		BeanFormatter formatter = factory.getFormatter(xml);
		request.setAttribute("Formatter", formatter);

		return mapping.findForward("results");


	}
	 public ActionForward relate(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response,
		SessionAPI session) throws Exception {

		 ClientContext cct = session.getClientContext();

		IInvesflowAPI invesFlowAPI = session.getAPI();
		IEntitiesAPI entitiesAPI = invesFlowAPI.getEntitiesAPI();
		IManagerAPI managerAPI = ManagerAPIFactory.getInstance().getManagerAPI(cct);

		// Estado actual
		IState state = managerAPI.currentState(getStateticket(request));
		String numexpHijo = state.getNumexp();
		String numexpPadre = request.getParameter("numexp");



		String relacion="";

		IItemCollection itemcol=entitiesAPI.getEntities(SpacEntities.SPAC_EXPEDIENTES, numexpPadre);
		if(itemcol.next()){

			IItem expediente=itemcol.value();

			if(StringUtils.equalsIgnoreCase(expediente.getString("CODPROCEDIMIENTO"),ConfigurationMgr.getVarGlobal(cct,SecretariaConstants.COD_PCD_SESIONES_PLENARIAS))){
				relacion=ConfigurationMgr.getVarGlobal(cct,SecretariaConstants.VALUE_RELACION_SESION_PROPUESTA);
			}
			else if(StringUtils.equalsIgnoreCase(expediente.getString("CODPROCEDIMIENTO"),ConfigurationMgr.getVarGlobal(cct,SecretariaConstants.COD_PCD_JUNTA_GOBIERNO))){
				relacion=ConfigurationMgr.getVarGlobal(cct,SecretariaConstants.VALUE_RELACION_JUNTA_PROPUESTA);
			}

		}


		// Ejecución en un contexto transaccional
		boolean bCommit = false;

		try {
				// Abrir transacción
				cct.beginTX();

				// Generar la relación entre expedientes
				IItem expRelacionados = entitiesAPI.createEntity(SpacEntities.SPAC_EXP_RELACIONADOS);
				expRelacionados.set("NUMEXP_PADRE", numexpPadre);
				expRelacionados.set("NUMEXP_HIJO", numexpHijo);

				if(logger.isDebugEnabled()){
					logger.debug("Relacion de expedientes : "+relacion);
				}
				expRelacionados.set("RELACION", relacion);

				expRelacionados.store(cct);

				// Si todo es correcta se hace commit de la transacción
				bCommit = true;
			}
		finally {
			cct.endTX(bCommit);
		}

		return mapping.findForward("success");


	 }

}
