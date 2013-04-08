package util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpSession;

import org.apache.commons.collections.FastHashMap;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForward;

public class NavigationContextStack {

	private final static String URI_PATTERN_ACTIONS;
	private final static Logger logger = Logger
			.getLogger(NavigationContextStack.class);

	final static ArrayList patternsIgnoredParams = new ArrayList();

	static {

		// TODO mejora: hacer un setURI que no sea para action
		// TODO meter estos datos de configuracion en un properties

		URI_PATTERN_ACTIONS = "/action";

		// patrones representados mediante una Expresion regular
		// que siguen los nombres de parametros a no tener en cuenta a la hora
		// de comparar la direccion
		// a añadir a la cima de la pila con la ultima que existe en la cima
		patternsIgnoredParams.add("d\\-");// empiezan por d-

	}

	private final static String NAVIGATION_CONTEX_STACK_BIND_NAME = "NAVIGATION_CONTEX_BY_LEVEL_BIND_NAME";

	ArrayList cancelURIS = new ArrayList();// array que funcionara a modo de
											// pila almacenando URIs

	private NavigationContextStack(HttpSession sesion) {
		// this.sesionUsuario = sesion;
	}

	public void setOnCancelURI(String URIPath, Map.Entry[] parametros) {
		StringBuffer queryString = null;
		if (parametros != null && parametros.length > 0) {
			queryString = new StringBuffer();
			int ordenParametro = 0;
			for (; ordenParametro < parametros.length - 1; ordenParametro++)
				queryString.append(parametros[ordenParametro].getKey())
						.append("=")
						.append(parametros[ordenParametro].getValue())
						.append("&");
			queryString.append(parametros[ordenParametro].getKey()).append("=")
					.append(parametros[ordenParametro].getValue());
		}

		setOnCancelURI(URIPath, queryString.toString());

	}

	private void addParam(StringBuffer string, String param) {
		if (string.length() > 0)
			string.append("&");
		string.append(param);
	}

	/*
	 * Elimina de la query aquellos los parametros que cumplan el patron de
	 * parametro a ignorar
	 */
	private String getQueryWithoutIgnoredParams(String queryString) {
		StringBuffer queryWithoutDisplayTagParams = new StringBuffer();
		String[] params = queryString.split("&");
		for (int i = 0; i < params.length; i++) {

			for (Iterator iter = patternsIgnoredParams.iterator(); iter
					.hasNext();) {
				String patternNameTemporalParam = (String) iter.next();
				if (params[i].length() >= patternNameTemporalParam.length()) {
					/*
					 * if
					 * (!params[i].subSequence(0,patternNameTemporalParam.length
					 * ()).
					 * toString().equalsIgnoreCase(patternNameTemporalParam)){
					 * addParam(queryWithoutDisplayTagParams,params[i]); }
					 */
					Pattern patron = Pattern.compile(patternNameTemporalParam);
					Matcher m = patron.matcher(params[i]);
					// StringBuffer sb = new StringBuffer();
					/* boolean result = */m.find();
					boolean alprincipio = m.lookingAt();
					if (!alprincipio)
						addParam(queryWithoutDisplayTagParams, params[i]);
					/*
					 * while(result){ m.appendReplacement(sb,""); result =
					 * m.find(); } m.appendTail(sb); if
					 * (sb.toString().equalsIgnoreCase(params[i])){
					 * addParam(queryWithoutDisplayTagParams,params[i]); }
					 */
				} else
					addParam(queryWithoutDisplayTagParams, params[i]);

			}
		}

		return queryWithoutDisplayTagParams.toString();
	}

	private FastHashMap getMapParamsFromQuery(String query) {
		FastHashMap map = new FastHashMap();
		String[] params = query.split("&");
		for (int i = 0; i < params.length; i++) {
			String[] tokensFromEqual = params[i].split("=");
			if (tokensFromEqual.length == 2) {
				map.put(tokensFromEqual[0], tokensFromEqual[1]);
			}
		}
		return map;
	}

	private boolean areEqualQueryStringWithoutIgnoredParams(
			String queryString1, String queryString2) {
		// boolean ret = false;

		// StringBuffer URISinDisplayTagParams = new StringBuffer();
		String query1WithoutTemporalParams = getQueryWithoutIgnoredParams(queryString1);
		String query2WithoutTemporalParams = getQueryWithoutIgnoredParams(queryString2);

		if (query1WithoutTemporalParams.length() == query2WithoutTemporalParams
				.length()) {
			FastHashMap mapParamsQueryString1 = getMapParamsFromQuery(query1WithoutTemporalParams);
			FastHashMap mapParamsQueryString2 = getMapParamsFromQuery(query2WithoutTemporalParams);

			if (mapParamsQueryString1.size() == mapParamsQueryString2.size()) {
				for (Iterator iter = mapParamsQueryString1.keySet().iterator(); iter
						.hasNext();) {
					String paramName = (String) iter.next();
					String valueParamQueryString1 = ((String) mapParamsQueryString1
							.get(paramName));
					String valueParamQueryString2 = ((String) mapParamsQueryString2
							.get(paramName));
					if (!valueParamQueryString1
							.equalsIgnoreCase(valueParamQueryString2))
						return false;
				}
			} else
				return false;
		} else
			return false;

		return true;
	}

	private String getFullURI(String URIPathAction, String queryString) {
		StringBuffer uri = new StringBuffer();
		uri.append(URI_PATTERN_ACTIONS);
		if (!URIPathAction.startsWith("/"))
			uri.append("/");
		uri.append(URIPathAction);
		if (queryString != null)
			uri.append("?");
		uri.append(queryString);
		return uri.toString();
	}

	private String splitQueryStringFromURI(String URI) {
		String[] tmp = URI.split("\\?");
		if (tmp.length > 1)
			return tmp[1];
		return null;

	}

	public void setOnCancelURI(String newActionPath, String newQueryString) {
		String newUri = getFullURI(newActionPath, newQueryString);

		// compobar q el ultimo uri no este repetido(caso de los refresh y
		// reordenaciones de display tag)
		if (this.cancelURIS.size() > 0) {
			String ultimoURI = (String) (this.cancelURIS.get(this.cancelURIS
					.size() - 1));
			if (!ultimoURI.equalsIgnoreCase(newUri.toString())) {
				// si las uris son distintas puede deberse a los parametros
				// añadidos por el display tag (ordenacion,paginacion ..)
				// en este caso se debe sobreescribir la ultima uri de la pila
				String queryStringUltimoURI = splitQueryStringFromURI(ultimoURI);
				if ((queryStringUltimoURI != null) && (newQueryString != null)) {
					if (areEqualQueryStringWithoutIgnoredParams(
							queryStringUltimoURI, newQueryString)) {
						this.cancelURIS.set(cancelURIS.size() - 1, newUri);

					} else
						this.cancelURIS.add(newUri);
				} else
					this.cancelURIS.add(newUri);

			}

		} else
			this.cancelURIS.add(newUri);

	}

	public void setOnCancelURI(ActionForward forward) {
		String[] tokensPath = forward.getPath().split("\\?");
		if (tokensPath.length == 2) {
			setOnCancelURI(
					tokensPath[0].substring(URI_PATTERN_ACTIONS.length()),
					tokensPath[1]);
		} else {
			String stringNull = null;
			setOnCancelURI(forward.getPath(), stringNull);
		}
	}

	/**
	 * 
	 * @return Devuelve la ultima URI situada en el navegador
	 */
	public String getOnCancelURI() throws Exception {
		return getOnCancelURI(1);
	}

	/**
	 * 
	 * @param backStepsFromTop
	 * @return Dentro de la pila devolvera el URI que se encuentre backSteps
	 *         posiciones hacia abajo empezando por la cima
	 */
	public String getOnCancelURI(int backStepsFromTop) throws Exception {
		// Ej de cuando usarlo: la pagina a la q debemos volver esta 2
		// posiciones mas abajo del tope de la pila:
		// getOnCancelURI(1): la pagina actual en la que estamos(se coloco al
		// situarnos sobre ella)
		// getOnCancelURI(2): la pagina a donde volver
		int index = cancelURIS.size() - backStepsFromTop;

		if (index >= 0) {
			int size = cancelURIS.size();
			String returnURI = (String) cancelURIS.get(index);

			// desapilar por encima
			for (int i = index; i < size; i++)
				cancelURIS.remove(cancelURIS.size() - 1);

			return returnURI;
		} else {
			// borrar hast donde se pueda
			cancelURIS.clear();
		}

		logger.error("Cancel URI: no tiene valor=> devolvemos null");
		throw new Exception("Cancel URI: no tiene valor=> devolvemos null");

	}

	public static NavigationContextStack getInstance(HttpSession sesion) {
		NavigationContextStack contexto = (NavigationContextStack) sesion
				.getAttribute(NAVIGATION_CONTEX_STACK_BIND_NAME);
		if (contexto == null) {
			contexto = new NavigationContextStack(sesion);
			sesion.setAttribute(NAVIGATION_CONTEX_STACK_BIND_NAME, contexto);
		}
		return contexto;
	}

	public void clear() {
		cancelURIS.clear();
	}

}
