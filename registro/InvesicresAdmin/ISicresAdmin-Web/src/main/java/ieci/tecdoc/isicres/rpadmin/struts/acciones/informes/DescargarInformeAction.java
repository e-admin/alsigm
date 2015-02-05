package ieci.tecdoc.isicres.rpadmin.struts.acciones.informes;

import ieci.tecdoc.isicres.rpadmin.struts.acciones.RPAdminWebAction;
import ieci.tecdoc.isicres.rpadmin.struts.acciones.keys.InformesKeys;
import ieci.tecdoc.isicres.rpadmin.struts.acciones.transportes.GuardarNuevoTransporteAction;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import es.ieci.tecdoc.fwktd.core.spring.configuration.jdbc.datasource.MultiEntityContextHolder;
import es.ieci.tecdoc.isicres.admin.beans.Entidad;
import es.ieci.tecdoc.isicres.admin.beans.InformeBean;
import es.ieci.tecdoc.isicres.admin.estructura.adapter.ISicresServicioRPAdminAdapter;
import es.ieci.tecdoc.isicres.admin.service.ISicresServicioRPAdmin;

public class DescargarInformeAction   extends RPAdminWebAction {
	private static final Logger logger = Logger.getLogger(GuardarNuevoTransporteAction.class);

	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		ISicresServicioRPAdmin oServicio = new ISicresServicioRPAdminAdapter();
	    String idInforme = (String)request.getParameter(InformesKeys.ID_INFORME);

		Entidad entidad = new Entidad();
		entidad.setIdentificador(MultiEntityContextHolder.getEntity());

	    InformeBean informe = oServicio.descargarInforme(new Integer(idInforme).intValue(), entidad);

		response.setContentType("application/octet-stream");
	    response.setHeader("Content-Disposition","attachment;filename="+informe.getReport());
	    ServletOutputStream ouputStream = response.getOutputStream();
	    ouputStream.write(informe.getFileData());
	    ouputStream.flush();
	    ouputStream.close();

		if( logger.isDebugEnabled())
			logger.debug("El informe descargado es: " + informe.toString());

		ActionMessages messages = new ActionMessages();
		ActionMessage mesage = new ActionMessage("ieci.tecdoc.sgm.rpadmin.informes.resultado.descargadoOK");
		messages.add("Result: ", mesage);
		saveMessages(request, messages);

		return mapping.findForward("success");
	}
}
