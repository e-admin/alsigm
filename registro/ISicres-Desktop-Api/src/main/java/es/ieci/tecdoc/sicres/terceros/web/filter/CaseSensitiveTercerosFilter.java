package es.ieci.tecdoc.sicres.terceros.web.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.filter.RequestContextFilter;
import org.springframework.web.util.WebUtils;

import com.ieci.tecdoc.common.isicres.SessionInformation;
import com.ieci.tecdoc.isicres.desktopweb.Keys;
import com.ieci.tecdoc.isicres.session.book.BookSessionUtil;
import com.ieci.tecdoc.isicres.usecase.UseCaseConf;

/**
 *
 * @author IECISA
 *
 */
public class CaseSensitiveTercerosFilter extends RequestContextFilter {

	public CaseSensitiveTercerosFilter() {
		setExcludeParameters(new String[] { "content.provincia.nombre",
				"content.ciudad.nombre" });
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		UseCaseConf useCaseConf = (UseCaseConf) request.getSession()
				.getAttribute(Keys.J_USECASECONF);
		try {
			SessionInformation sessionInformation = BookSessionUtil
					.getSessionInformation(useCaseConf.getSessionID(),
							useCaseConf.getLocale(), useCaseConf.getEntidadId());
			String caseSensitive = sessionInformation.getCaseSensitive();

			exposeTextTransformation(request, caseSensitive);

			HttpServletRequest overridenRequest = transformCaseParameters(
					request, caseSensitive);

			super.doFilterInternal(overridenRequest, response, filterChain);
		} catch (Exception e) {
			logger.error(
					"Se ha producido un error estableciendo el modo de case para los terceros",
					e);
		}
	}

	/**
	 *
	 * @param request
	 * @param caseSensitive
	 */
	protected HttpServletRequest transformCaseParameters(
			HttpServletRequest request, final String caseSensitive) {
		HttpServletRequestWrapper wrapper = new HttpServletRequestWrapper(
				request) {
			@Override
			public String[] getParameterValues(String name) {
				String[] parameterValues = super.getParameterValues(name);
				List<String> casedParameterValues = new ArrayList<String>();

				for (int i = 0; i < parameterValues.length; i++) {
					if (CASE_SENSITIVE.equals(caseSensitive)
							&& !ArrayUtils.contains(getExcludeParameters(),
									name)) {
						casedParameterValues.add(parameterValues[i]
								.toUpperCase());
					} else {
						casedParameterValues.add(parameterValues[i]);
					}

				}

				return (String[]) casedParameterValues
						.toArray(new String[casedParameterValues.size()]);
			}
		};
		return wrapper;
	}

	/**
	 *
	 * @param request
	 */
	protected void exposeTextTransformation(HttpServletRequest request,
			String caseSensitive) {
		String textTransformation = (String) WebUtils.getSessionAttribute(
				request, TEXT_TRANSFORM);

		if (StringUtils.isBlank(textTransformation)) {

			if (CASE_SENSITIVE.equals(caseSensitive)) {
				WebUtils.setSessionAttribute(request, TEXT_TRANSFORM,
						"uppercase");
			} else {
				WebUtils.setSessionAttribute(request, TEXT_TRANSFORM,
						"lowercase");
			}

		}
	}

	public String[] getExcludeParameters() {
		return excludeParameters;
	}

	public void setExcludeParameters(String[] excludeParameters) {
		this.excludeParameters = excludeParameters;
	}

	// Members
	protected static final String CASE_SENSITIVE = "CS";
	protected static final String TEXT_TRANSFORM = "texttransform";

	protected String[] excludeParameters;

}
