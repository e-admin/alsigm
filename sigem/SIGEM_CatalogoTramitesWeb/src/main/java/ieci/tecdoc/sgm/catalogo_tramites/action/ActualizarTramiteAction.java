package ieci.tecdoc.sgm.catalogo_tramites.action;

/*
 * $Id: ActualizarTramiteAction.java,v 1.4.2.3 2008/10/13 08:53:23 jnogales Exp $
 */
import ieci.tecdoc.sgm.catalogo_tramites.utils.Defs;
import ieci.tecdoc.sgm.core.admin.web.SesionAdminHelper;
import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.catalogo.CatalogoTramitesExcepcion;
import ieci.tecdoc.sgm.core.services.catalogo.DocumentoExtendido;
import ieci.tecdoc.sgm.core.services.catalogo.DocumentoTramite;
import ieci.tecdoc.sgm.core.services.catalogo.Documentos;
import ieci.tecdoc.sgm.core.services.catalogo.ServicioCatalogoTramites;
import ieci.tecdoc.sgm.core.services.catalogo.Tramite;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ActualizarTramiteAction extends CatalogoTramitesWebAction {

	private static final String SUCCESS_FORWARD = "success";
	private static final String FAILURE_FORWARD = "failure";

	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		String code, mandatory, docId;
		String id = request.getParameter("procedures");
		String description = request.getParameter("description");
		String topic = request.getParameter("topic");
		String addressee = request.getParameter("addressee");
		String limitDocs = request.getParameter("limitDocs");
		String firma = request.getParameter("firma");
		String oficina = request.getParameter("oficina");
		String idProcedimiento = request.getParameter("idProcedimiento");

		try {
			Tramite procedure = new Tramite();
			procedure.setId(id);
			procedure.setDescription(description);
			procedure.setTopic(topic);
			procedure.setAddressee(addressee);
			if (limitDocs.compareTo("1") == 0)
				procedure.setLimitDocs(true);
			else
				procedure.setLimitDocs(false);
			if (firma.compareTo("1") == 0)
				procedure.setFirma(true);
			else
				procedure.setFirma(false);
			if (oficina != null && !"".equals(oficina))
				procedure.setOficina(oficina);
			if (idProcedimiento != null && !"".equals(idProcedimiento))
				procedure.setIdProcedimiento(idProcedimiento);
			ServicioCatalogoTramites oServicio = LocalizadorServicios
					.getServicioCatalogoTramites();
			oServicio.updateProcedure(procedure, SesionAdminHelper
					.obtenerEntidad(request));

			// GUARDAMOS LOS DOCUMENTOS
			// borramos la asociacion actual
			Documentos documents = null;
			documents = oServicio.getProcedureDocuments(procedure.getId(),
					SesionAdminHelper.obtenerEntidad(request));
			for (int i = 0; i < documents.count(); i++) {
				DocumentoExtendido doc = (DocumentoExtendido) documents.get(i);
				DocumentoTramite procdoc = new DocumentoTramite();
				procdoc.setCode(doc.getCode());
				procdoc.setDocumentId(doc.getId());
				procdoc.setProcedureId(procedure.getId());
				oServicio.deleteProcedureDocument((DocumentoTramite) procdoc,
						SesionAdminHelper.obtenerEntidad(request));
			}

			// asociamos los documentos
			DocumentoTramite document;
			String[] docIds = request.getParameterValues("docIds");
			if (docIds != null) {
				for (int i = 0; i < docIds.length; i++) {
					docId = docIds[i];
					code = request.getParameter(docId + "_" + i + "_code");
					mandatory = request.getParameter(docId + "_" + i
							+ "_mandatory");

					document = new DocumentoTramite();
					document.setProcedureId(id);
					document.setDocumentId(docId);
					document.setCode(code);
					if (mandatory.compareTo("1") == 0)
						document.setMandatory(true);
					else
						document.setMandatory(false);
					oServicio.addProcedureDocument(document, SesionAdminHelper
							.obtenerEntidad(request));
				}
			}

			boolean docLimit = procedure.getLimitDocs();
			if (docLimit)
				request.setAttribute("limit", "1");
			else
				request.setAttribute("limit", "0");
			boolean procFirma = procedure.getFirma();
			if (procFirma)
				request.setAttribute("firmap", "1");
			else
				request.setAttribute("firmap", "0");
			request.setAttribute("procedure", procedure);

		} catch (CatalogoTramitesExcepcion cte) {
			request.setAttribute(Defs.MENSAJE_ERROR,
					Defs.MENSAJE_ERROR_TRAMITES);
			request.setAttribute(Defs.MENSAJE_ERROR_DETALLE, cte.getCause().getMessage());
			return mapping.findForward(FAILURE_FORWARD);

		} catch (Exception e) {
			request.setAttribute(Defs.MENSAJE_ERROR,
					Defs.MENSAJE_ERROR_TRAMITES);
			request.setAttribute(Defs.MENSAJE_ERROR_DETALLE, e.toString());
			return mapping.findForward(FAILURE_FORWARD);
		}

		return mapping.findForward(SUCCESS_FORWARD);
	}
}
