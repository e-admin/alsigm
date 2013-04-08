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
public class GetDatosPersonalesAction extends BaseAction {

	/**
	 * Logger de la clase.
	 */
	private static final Logger logger = Logger.getLogger(GetDatosPersonalesAction.class);

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

		item.set("DNI","DNI-V");
		item.set("APELLIDOS","APELLIDOS-V");
		item.set("NOMBRE","NOMBRE-V");
		item.set("FECHA_NACIMIENTO","10/10/2010");
		item.set("SEXO_VALOR","01");
		item.set("SEXO_SUSTITUTO","Masculino");
		item.set("NACIONALIDAD","NACIONALIDAD-V");
		item.set("PROVINCIA_VALOR","02");
		item.set("PROVINCIA_SUSTITUTO","Albacete");
		item.set("MUNICIPIO","MUNICIPIO-V");
		item.set("ESTUDIOS","ESTUDIOS");

		return item;
	}

	private Properties getProperties() {

		int ordinal = 0;
		Properties properties = new Properties();
		properties.add( new Property(ordinal++, "ID", Types.VARCHAR));       

		properties.add( new Property(ordinal++, "DNI", Types.VARCHAR));
		properties.add( new Property(ordinal++, "APELLIDOS", Types.VARCHAR));
		properties.add( new Property(ordinal++, "NOMBRE", Types.VARCHAR));
		properties.add( new Property(ordinal++, "FECHA_NACIMIENTO", Types.VARCHAR));
		properties.add( new Property(ordinal++, "SEXO_VALOR", Types.VARCHAR));
		properties.add( new Property(ordinal++, "SEXO_SUSTITUTO", Types.VARCHAR));
		properties.add( new Property(ordinal++, "NACIONALIDAD", Types.VARCHAR));
		properties.add( new Property(ordinal++, "PROVINCIA_VALOR", Types.VARCHAR));
		properties.add( new Property(ordinal++, "PROVINCIA_SUSTITUTO", Types.VARCHAR));
		properties.add( new Property(ordinal++, "MUNICIPIO", Types.VARCHAR));
		properties.add( new Property(ordinal++, "ESTUDIOS", Types.VARCHAR));

		return properties;
	}

}
