package ieci.tdw.ispac.ispacmgr.action;

import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.ISearchAPI;
import ieci.tdw.ispac.api.errors.ISPACInfo;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.ispaclib.bean.BeanFormatter;
import ieci.tdw.ispac.ispaclib.bean.CacheFormatterFactory;
import ieci.tdw.ispac.ispaclib.bean.CollectionBean;
import ieci.tdw.ispac.ispaclib.search.objects.impl.SearchExpState;
import ieci.tdw.ispac.ispaclib.search.objects.impl.SearchInfo;
import ieci.tdw.ispac.ispaclib.search.vo.SearchResultVO;
import ieci.tdw.ispac.ispacmgr.action.form.SearchForm;
import ieci.tdw.ispac.ispacweb.manager.ISPACRewrite;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class SearchIntrayAction extends BaseAction {

	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			SessionAPI session) throws Exception {

		SearchForm searchForm = (SearchForm) form;

		IInvesflowAPI invesFlowAPI = session.getAPI();
		ISearchAPI searchAPI = invesFlowAPI.getSearchAPI();

		ISPACRewrite ispacPath = new ISPACRewrite(getServlet()
				.getServletContext());

		String xml = ispacPath.rewriteRealPath("xml/IntrayForm.xml");

		int domain = searchForm.getDomain();

		SearchInfo searchinfo = searchAPI.getSearchInfoPath(xml, domain,
				SearchExpState.ACTIVE);

		setQuery(searchinfo, searchForm);
		try{
			SearchResultVO searchResultVO= searchAPI.getLimitedSearchResults(searchinfo);
			List lResults = CollectionBean.getBeanList(searchResultVO.getResultados());
			if(lResults.size()< searchResultVO.getNumTotalRegistros()){ 
					request.setAttribute("maxResultados", String.valueOf(searchResultVO.getNumMaxRegistros())); 
					request.setAttribute("numTotalRegistros", String.valueOf(searchResultVO.getNumTotalRegistros()));
	
			} 
			request.setAttribute("ResultsList", lResults);
	
			CacheFormatterFactory factory = CacheFormatterFactory.getInstance();
			BeanFormatter formatter = factory.getFormatter(xml);
	
			request.setAttribute("Formatter", formatter);
	
			return mapping.findForward("success");
		}catch(ISPACInfo e){
					e.setRefresh(false);
					throw e;
		}
	}
}
