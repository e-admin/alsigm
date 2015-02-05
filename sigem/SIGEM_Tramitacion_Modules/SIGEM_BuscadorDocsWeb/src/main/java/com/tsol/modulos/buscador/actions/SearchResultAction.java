package com.tsol.modulos.buscador.actions;

import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.impl.SessionAPIFactory;
import ieci.tdw.ispac.audit.business.vo.AuditContext;
import ieci.tdw.ispac.audit.context.AuditContextHolder;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tsol.modulos.buscador.beans.SearchBean;
import com.tsol.modulos.buscador.beans.SearchForm;
import com.tsol.modulos.buscador.dao.SearchDAO;

public class SearchResultAction extends Action {

	private static final Logger LOGGER = Logger.getLogger(SearchResultAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		SearchForm searchForm = (SearchForm) form;

		// Componer el código de cotejo
		String codCotejo = new StringBuffer()
			.append(searchForm.getCotejo1())
			.append(searchForm.getCotejo2())
			.append(searchForm.getCotejo3())
			.append(searchForm.getCotejo4())
			.toString();
		if (LOGGER.isInfoEnabled()) {
			LOGGER.debug("Código de cotejo: [" + codCotejo + "]");
		}

		try {

			// API de sesión de iSPAC
			SessionAPI sessionAPI = SessionAPIFactory.getSessionAPI(request, response);

	        // Contexto para la auditoría
			setAuditContext(request, sessionAPI);

			// Obtener la información del documento
			SearchBean searchBean = SearchDAO.searchDocument(sessionAPI.getClientContext(), codCotejo);
			request.setAttribute("searchBean", searchBean);

		} catch (Exception e) {
			LOGGER.error("Se ha producido un error inesperado", e);

			ActionErrors errors = new ActionErrors();
			errors.add("error", new ActionError("search.error"));

			saveErrors(request, errors);
		}

		return mapping.findForward("success");
	}

	/**
	 * @param request
	 */
    protected void setAuditContext(HttpServletRequest request, SessionAPI session) {
		AuditContext auditContext = new AuditContext();
		auditContext.setUserHost(request.getRemoteHost());
		auditContext.setUserIP(request.getRemoteAddr());
		auditContext.setUser(session.getUserName());
		auditContext.setUserId(session.getClientContext().getUser().getUID());
		AuditContextHolder.setAuditContext(auditContext);
	}
}
