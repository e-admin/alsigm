package ieci.tdw.ispac.ispacmgr.action;

import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.ISearchAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACInfo;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.ispaclib.bean.BeanFormatter;
import ieci.tdw.ispac.ispaclib.bean.CacheFormatterFactory;
import ieci.tdw.ispac.ispaclib.bean.CollectionBean;
import ieci.tdw.ispac.ispaclib.bean.ItemBean;
import ieci.tdw.ispac.ispaclib.bean.SearchActionListFactory;
import ieci.tdw.ispac.ispaclib.bean.SearchResourceListFactory;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.search.PropertiesHelper;
import ieci.tdw.ispac.ispaclib.search.objects.impl.SearchInfo;
import ieci.tdw.ispac.ispaclib.search.vo.SearchResultVO;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispacmgr.action.form.SearchForm;
import ieci.tdw.ispac.ispacmgr.menus.MenuFactory;
import ieci.tdw.ispac.ispacweb.api.IActions;
import ieci.tdw.ispac.ispacweb.api.IManagerAPI;
import ieci.tdw.ispac.ispacweb.api.IState;
import ieci.tdw.ispac.ispacweb.api.IWorklist;
import ieci.tdw.ispac.ispacweb.api.ManagerAPIFactory;
import ieci.tdw.ispac.ispacweb.api.ManagerState;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;

public class ShowSearchResultsAction extends BaseAction {

	public ActionForward executeAction(ActionMapping mapping, 
									   ActionForm form,
									   HttpServletRequest request, 
									   HttpServletResponse response, 
									   SessionAPI session) throws Exception {
		
		ClientContext cct = session.getClientContext();
		
		IInvesflowAPI invesflowAPI = session.getAPI();
		ISearchAPI searchAPI = invesflowAPI.getSearchAPI();
		
		IManagerAPI managerAPI=ManagerAPIFactory.getInstance().getManagerAPI(cct);

		// Se ratifica el estado para evitar un bucle infinito
		// cuando se va para atrás con el botón del navegador
		// tras haber realizado una búsqueda y realizar otra
		// que genera algún error en los datos de búsqueda
		IState state = managerAPI.enterState(getStateticket(request), ManagerState.SEARCH, null);
		storeStateticket(state, response);
		
		// Formulario de búsqueda
		SearchForm searchForm = (SearchForm) form;
		
		SearchResultVO searchResultVO=new SearchResultVO();
				
		// Procesar los campos y sus datos
		int idxml = searchForm.getIdxml();
		int domain = searchForm.getDomain();
		int expState = searchForm.getExpState();
				
		IItem item = searchAPI.getSearchForm(idxml);
		String name = item.getString("DESCRIPCION");
		String configFile = item.getString("FRM_BSQ");
		
		SearchInfo searchinfo = searchAPI.getSearchInfo(configFile, domain, expState);
		searchinfo.setDescription(name);
		populateWithDataAndOperators(searchinfo, searchForm);
		
		// Ejecutar la búsqueda
		try {
			searchResultVO= searchAPI.getLimitedSearchResults(searchinfo);
		}
		catch (ISPACInfo ie) {
			throw ie;
		}
		catch (ISPACException ie) {
			throw new ISPACInfo("exception.searchforms.generate", new String[]{name});
		}

		///////////////////////////////////////////////
		// Cambio del estado de tramitación: después de buscar para evitar el bucle infinito
		state = managerAPI.enterState(getStateticket(request), ManagerState.SEARCHRESULTS, null);
		storeStateticket(state, response);
		
	
		List lResults = CollectionBean.getBeanList(searchResultVO.getResultados());
		if(lResults.size()< searchResultVO.getNumTotalRegistros()){ 
				request.setAttribute("maxResultados", String.valueOf(searchResultVO.getNumMaxRegistros())); 
				request.setAttribute("numTotalRegistros", String.valueOf(searchResultVO.getNumTotalRegistros()));

		} 	
		
	
		/*
		if ((lResults != null) &&
			(!lResults.isEmpty())) {
		*/
			request.setAttribute("ResultsList", lResults);
			request.setAttribute("idObj", item.getKey());
		/*
		}
		else {
			// TODO Mantener los datos de búsqueda en el formulario
			// Habría que hacer otra action específica para la búsqueda con el searchForm
			throw new ISPACInfo("No existen datos que mostrar");
		}
		*/
		
		// Ahora el formulario de búsqueda está en sesión y se mantienen los parámetros de la última búsqueda realizada
		if (lResults.isEmpty()) {
			searchForm.setDisplayTagParams(null);
		}
		else {
			// Parámetros al interactuar con la lista
			if (request.getQueryString() != null) {
				
				String queryString = request.getQueryString();
				
				// Eliminar el parámetro que indica una exportación del displayTag
				queryString = removeQueryStringParameter(queryString, TableTagParameters.PARAMETER_EXPORTING);
				
				// Eliminar el parámetro que indica el tipo de exportación  
				// ATENCIÓN: El id de la tabla debe de "object"
				queryString = removeQueryStringParameter(queryString, new ParamEncoder("object")
					.encodeParameterName(TableTagParameters.PARAMETER_EXPORTTYPE));
				
				searchForm.setDisplayTagParams(queryString);
			}
			else {
				searchForm.setDisplayTagParams("");
			}
		}
		
	    ///////////////////////////////////////////////
	    // Formateador
		CacheFormatterFactory factory = CacheFormatterFactory.getInstance();
		BeanFormatter formatter = factory.getBeanFormatter(configFile);
		request.setAttribute("Formatter", formatter);

	    ///////////////////////////////////////////////
	    // Recursos
		Properties properties = SearchResourceListFactory.getSearchResourceProperties(SearchResourceListFactory.SEARCH_RESOURCETYPE, configFile);
		ieci.tdw.ispac.ispaclib.search.PropertiesHelper propertiesHelper = new PropertiesHelper(cct.getLocale(), properties, ResourceBundle.getBundle(BUNDLE_NAME, cct.getLocale()));
		request.setAttribute("propertiesHelper", propertiesHelper);
	    ///////////////////////////////////////////////
		
		
		///////////////////////////////////////////////
	    // Acciones
		List ltSearchAction = SearchActionListFactory.getSearchActionList(configFile);
		
	    ///////////////////////////////////////////////
	    // Menus
	
		// Responsabilidades del usuario conectado
		IWorklist managerwl = managerAPI.getWorklistAPI();
		String resp = managerwl.getRespString(state);
		request.setAttribute("menus", MenuFactory.getSearchResultMenu(session.getClientContext(),
																	  getResources(request),
																	  properties,
																	  ltSearchAction,
																	  request.getParameterMap(),item.getInt("ID"),
																	  resp));
		
	//Almacenamos en sesión la lista de los expedientes que cumplen el frm de busqueda
		if(lResults!=null && lResults.size()>0 ){
		
	        IActions actions = managerAPI.getActionsAPI();
	        
	        if(actions.hasSearchReport(item.getInt("ID"))){
	          String [] numExps=new String[lResults.size()];
	          for(int i=0; i<lResults.size(); i++){
	        	  numExps[i]=(String) ((ItemBean)lResults.get(i)).getString("SPAC_EXPEDIENTES.NUMEXP");
	          }
	        //Almacenar en session
	          request.getSession().setAttribute("numExpsSearch", numExps);
			}
	       
		}															
				
		
		return mapping.findForward("success");
	}

	private void populateWithDataAndOperators (SearchInfo searchinfo, 
											   SearchForm searchForm) throws ISPACException {
		
		Map values = searchForm.getValuesMap();
		Map operators = searchForm.getOperatorsMap();
		Set valueKeys = values.keySet();
		Iterator iter = valueKeys.iterator();
		while (iter.hasNext())
		{
			String key = (String) iter.next();
			String[] keys = key.split(":");
			
			if(values.get(key) instanceof List) {
				
				List value=(List) values.get(key);
				searchinfo.setFieldValueForEntity(keys[0], keys[1], value);
			}
			else {
				String value = ((String) values.get(key)).replaceAll("'","").trim();
				searchinfo.setFieldValueForEntity(keys[0], keys[1], value);
			}
			
			String operator = (String) operators.get(key);
			//ValuesMulti=
			
			searchinfo.setFieldOperatorForEntity(keys[0], keys[1], operator);		
		}	
	}
	
	private static String removeQueryStringParameter(String queryString, String parameter) {
		
		if (StringUtils.isNotBlank(queryString) && StringUtils.isNotBlank(parameter)) {
			
			if (queryString.startsWith(parameter + "=")) {
				int ix = queryString.indexOf("&");
				if (ix > 0) {
					queryString = queryString.substring(ix + 1);
				} else {
					queryString = "";
				}
			} else {
				int ix = queryString.indexOf("&" + parameter + "=");
				if (ix > 0) {
					String aux = queryString.substring(0, ix);
					int ix2 = queryString.indexOf("&", ix + 1);
					if (ix2 > 0) {
						aux += queryString.substring(ix2);
					}
					
					queryString = aux;
				}
			}
		}
		
		return queryString;
	}
}