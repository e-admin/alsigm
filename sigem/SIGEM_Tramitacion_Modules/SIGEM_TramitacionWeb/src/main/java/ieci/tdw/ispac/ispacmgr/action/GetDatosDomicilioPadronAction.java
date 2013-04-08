package ieci.tdw.ispac.ispacmgr.action;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACInfo;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.Properties;
import ieci.tdw.ispac.api.item.Property;
import ieci.tdw.ispac.ispaclib.bean.ItemBean;
import ieci.tdw.ispac.ispaclib.item.GenericItem;
import ieci.tdw.ispac.ispacmgr.action.form.EntityForm;

import java.sql.Types;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action para buscar datos de padron de un participante
 * 
 * Parámetros:
 * - field => nombre del campo nif en el formulario de entidad
 *
 */
public class GetDatosDomicilioPadronAction extends BaseAction {

	/**
	 * Logger de la clase.
	 */
	private static final Logger logger = Logger.getLogger(GetDatosDomicilioPadronAction.class);

	@Override
	public ActionForward executeAction(ActionMapping mapping, 
			ActionForm form,
			HttpServletRequest request, 
			HttpServletResponse response,
			SessionAPI session)
			throws Exception {
		
//		ClientContext cct = session.getClientContext();

//		IInvesflowAPI invesFlowAPI = session.getAPI();
		
		// Obtener el nif
		String nif = ((EntityForm)form).getProperty(request.getParameter("field"));	

		// Simular que unas veces encuentra registro y otras no
		if (((int)(Math.random()*2)%2)==1){
			throw new ISPACInfo("exception.info.tercero.noDataPadron", new Object[] {nif},false);
		}
		
		// generar el item
		IItem item = generateDatosDomicilioPadronItem();
		request.setAttribute("Value", new ItemBean(item));
				
		/*
		 * Nombre de la variable de sesión que mantiene los parámetros
		 * del tag de búsqueda
		 */
		String parameters = request.getParameter("parameters");

		// Obtiene los parametros del tag y los salva en la request
		Map sParameters = (Map)request.getSession().getAttribute(parameters);
		if (sParameters != null) {
			request.setAttribute("parameters", sParameters);
		}

		return mapping.findForward("success");
	}

	private IItem generateDatosDomicilioPadronItem() throws ISPACException {

		Properties properties = getProperties();
		IItem item = new GenericItem(properties, "ID");

		item.set("VIAL","VIAL-V");
		item.set("NUMERO","1");
		item.set("KM","2");
		item.set("BLOQUE","BLOQUE-V");
		item.set("PORTAL","PORTAL-V");
		item.set("ESCALERA","ESCALERA-V");
		item.set("PISO","3");
		item.set("PUERTA","PUERTA-V");
		item.set("CP","7");
		item.set("DISTRITO","6");
		item.set("SECCION","4");
		item.set("HOJA","5");

		return item;
	}

	private Properties getProperties() {

		int ordinal = 0;
		Properties properties = new Properties();
		properties.add( new Property(ordinal++, "ID", Types.VARCHAR));       

		properties.add( new Property(ordinal++, "VIAL", Types.VARCHAR));
		properties.add( new Property(ordinal++, "NUMERO", Types.VARCHAR));
		properties.add( new Property(ordinal++, "KM", Types.VARCHAR));
		properties.add( new Property(ordinal++, "BLOQUE", Types.VARCHAR));
		properties.add( new Property(ordinal++, "PORTAL", Types.VARCHAR));
		properties.add( new Property(ordinal++, "ESCALERA", Types.VARCHAR));
		properties.add( new Property(ordinal++, "PISO", Types.VARCHAR));
		properties.add( new Property(ordinal++, "PUERTA", Types.VARCHAR));
		properties.add( new Property(ordinal++, "CP", Types.VARCHAR));
		properties.add( new Property(ordinal++, "DISTRITO", Types.VARCHAR));
		properties.add( new Property(ordinal++, "SECCION", Types.VARCHAR));
		properties.add( new Property(ordinal++, "HOJA", Types.VARCHAR));

		return properties;
	}

}
