package ieci.tdw.ispac.ispacmgr.action;

import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.ispaclib.bean.CacheFormatterFactory;
import ieci.tdw.ispac.ispaclib.bean.CollectionBean;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.dao.join.TableJoinFactoryDAO;
import ieci.tdw.ispac.ispaclib.utils.DBUtil;
import ieci.tdw.ispac.ispacmgr.mgr.SpacMgr;
import ieci.tdw.ispac.ispactx.TXConstants;
import ieci.tdw.ispac.ispacweb.api.IManagerAPI;
import ieci.tdw.ispac.ispacweb.api.IState;
import ieci.tdw.ispac.ispacweb.api.ManagerAPIFactory;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ShowRelatedExpedientsAction extends BaseAction {

    public ActionForward executeAction(ActionMapping mapping, 
    								   ActionForm form,
    								   HttpServletRequest request,
    								   HttpServletResponse response,
    								   SessionAPI session) throws Exception {

        ClientContext cct = session.getClientContext();
        IInvesflowAPI invesFlowAPI = session.getAPI();
        IEntitiesAPI entitiesAPI = invesFlowAPI.getEntitiesAPI();
        IManagerAPI managerAPI = ManagerAPIFactory.getInstance().getManagerAPI(cct);

        // Estado actual
        IState state = managerAPI.currentState(getStateticket(request));
        String numExp = state.getNumexp();

        Map map = new LinkedHashMap();
        map.put("EXP", "SPAC_EXPEDIENTES");
        map.put("EXP_REL", "SPAC_EXP_RELACIONADOS");
        map.put("SPAC_PROCESOS", "SPAC_PROCESOS");
        

        // Obtenemos los expedientes descendientes del actual
        IItemCollection itemCol = entitiesAPI.queryEntities(map,"WHERE EXP_REL.NUMEXP_HIJO = EXP.NUMEXP AND  " +
        														"SPAC_PROCESOS.NUMEXP=EXP.NUMEXP AND " +
        														"SPAC_PROCESOS.ESTADO!="+TXConstants.STATUS_DELETED+
        														" AND EXP_REL.NUMEXP_PADRE = '" + DBUtil.replaceQuotes(numExp) + "'"); 
        //request.setAttribute("ChildValueList", CollectionBean.getBeanList(itemCol));
        List expRelacionados = CollectionBean.getBeanList(itemCol);
        
        // Obtenemos los expedientes antecesores del actual
        itemCol = entitiesAPI.queryEntities(map,"WHERE EXP_REL.NUMEXP_PADRE = EXP.NUMEXP AND" +
        										" SPAC_PROCESOS.NUMEXP=EXP.NUMEXP AND " +
        										" SPAC_PROCESOS.ESTADO!="+TXConstants.STATUS_DELETED+
        										" AND EXP_REL.NUMEXP_HIJO = '" + DBUtil.replaceQuotes(numExp) + "'");
        
        SpacMgr.organizeRelation(itemCol, "EXP_REL:RELACION", 1);
        //request.setAttribute("ParentValueList", CollectionBean.getBeanList(itemCol));
        expRelacionados.addAll(CollectionBean.getBeanList(itemCol));
        request.setAttribute("ValueList", expRelacionados);
        
        // Cargamos el formateador
        CacheFormatterFactory factory = CacheFormatterFactory.getInstance();
		request.setAttribute("Formatter", factory.getFormatter(getISPACPath("/digester/exprelshowformatter.xml")));
        
        return mapping.findForward("success");
    }

}