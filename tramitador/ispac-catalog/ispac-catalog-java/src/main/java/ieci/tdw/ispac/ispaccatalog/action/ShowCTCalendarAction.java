package ieci.tdw.ispac.ispaccatalog.action;

import ieci.tdw.ispac.api.ICatalogAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.ISPACEntities;
import ieci.tdw.ispac.api.ISecurityAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACInfo;
import ieci.tdw.ispac.api.errors.ISPACNullObject;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.deadline.CalendarDef;
import ieci.tdw.ispac.deadline.HolydayDef;
import ieci.tdw.ispac.ispaccatalog.action.form.CalendarForm;
import ieci.tdw.ispac.ispaccatalog.breadcrumbs.BreadCrumbsContainer;
import ieci.tdw.ispac.ispaccatalog.breadcrumbs.BreadCrumbsManager;
import ieci.tdw.ispac.ispaccatalog.helpers.FunctionHelper;
import ieci.tdw.ispac.ispaccatalog.messages.Messages;
import ieci.tdw.ispac.ispaclib.app.EntityApp;
import ieci.tdw.ispac.ispaclib.bean.ValidationError;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.utils.DBUtil;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispaclib.utils.XmlFacade;
import ieci.tdw.ispac.ispacweb.manager.ISPACRewrite;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.w3c.dom.Node;
import org.w3c.dom.traversal.NodeIterator;


public class ShowCTCalendarAction extends BaseDispatchAction {
	
	public static final String PATH_FILE_FIXED_HOLIDAYS = "xml/FixedHolidays.xml";
	public static final String DAYS_LIST = "allXmlFixedHolidays";
	public static final String YEAR ="year";
	
	//Tags
	public static final String TAG_HOLYDAYS = "Holydays";
	public static final String TAG_HOLYDAY = "Holyday";
	public static final String TAG_DATE = "Date";
	public static final String TAG_LABELS = "Labels";
	public static final String TAG_LABEL = "Label";
	
	//Atributos
	public static final String ATR_LANG = "Lang";

	public ActionForward show(ActionMapping mapping,
 									   ActionForm form,
 									   HttpServletRequest request,
 									   HttpServletResponse response,
 									   SessionAPI session) throws Exception {

 		// Comprobar si el usuario tiene asignadas las funciones adecuadas
		FunctionHelper.checkFunctions(request, session.getClientContext(), new int[] {
				ISecurityAPI.FUNC_COMP_CALENDARS_READ,
				ISecurityAPI.FUNC_COMP_CALENDARS_EDIT });

		CalendarForm defaultForm = (CalendarForm) form;

		String parameter = request.getParameter("entityId");
 		if (parameter == null) {
 			parameter = defaultForm.getEntity();
 		}
 		int entityId = Integer.parseInt(parameter);

		parameter = request.getParameter("regId");
 		if (parameter == null) {
 			parameter = defaultForm.getKey();
 		}
 		int keyId = Integer.parseInt(parameter);
 		
 		// Indica si se viene de añadir un nuevo dia festivo o no
 		Boolean save = new Boolean(request.getParameter("save")); 		 		

		IInvesflowAPI invesFlowAPI = session.getAPI();
        ICatalogAPI catalogAPI = invesFlowAPI.getCatalogAPI();

        String strURL = null;
		EntityApp entityapp = catalogAPI.getCTDefaultEntityApp(entityId, keyId, getRealPath(""));

		try {
        	if (entityapp==null)
        		throw new ISPACNullObject();
            //Si se debe crear una entrada cuando se guarden los datos del registro,
    		//se marca debidamente el campo clave del registro como nulo.
    		if (keyId==ISPACEntities.ENTITY_NULLREGKEYID) {
    		    entityapp.getItem().setKey(ISPACEntities.ENTITY_NULLREGKEYID);
    		}
        }
        catch (ISPACNullObject e) {
            ISPACRewrite ispacPath = new ISPACRewrite(getServlet().getServletContext());
            strURL = ispacPath.rewriteRelativePath("common/missingapp.jsp");
        }
        catch (ISPACException eie){
        	throw new ISPACInfo(eie.getMessage());
        }

        if (strURL == null) {
            strURL = entityapp.getURL();
        }

		// Visualiza los datos de la entidad
        // Si hay errores no se recargan los datos de la entidad
        // manteniéndose los datos introducidos que generan los errores
		if (((defaultForm.getActions() == null) ||
		     (defaultForm.getActions().equals("success"))) &&
		     (request.getAttribute(Globals.ERROR_KEY) == null)) {
			
			defaultForm.setEntity(Integer.toString(entityId));
			defaultForm.setKey(Integer.toString(keyId));
			defaultForm.setReadonly("false");
			
			defaultForm.setEntityApp(entityapp);
		}
		else {
			// Establecer la aplicación para acceder a los valores extra en el formulario
			defaultForm.setValuesExtra(entityapp);
		}
		
		if (save.booleanValue())
			defaultForm.setProperty("NOMBRE", request.getParameter("nombre"));

		//Lista de festivos y fin de semana
		List holydaysList = null;
		List allDayList = null;
		
		
		IItem item = defaultForm.getEntityApp().getItem();
		if (keyId>0){
			CalendarDef caldef = new CalendarDef(item.getString("CALENDARIO"));
			//Dias festivos
			holydaysList = caldef.getHolydays();
			//Dias de la semana
			allDayList = caldef.getAllWeekDays();
			//Dias de la semana
			if (save.booleanValue()){
				String dias = request.getParameter("weekDaysSelect");
				String [] diasSeleccionados = new String[dias.length()];
				if(!dias.equals(""))
					diasSeleccionados = dias.split(",");
				caldef.addWeekEnd(diasSeleccionados);
			}

			defaultForm.setWeekDaysSelect(caldef.getWeekEndDays());
			
		}else{
			item.set("CALENDARIO", (new CalendarDef()).getXmlValues());
		}
		
		request.setAttribute("HOLYDAYS_LIST", holydaysList);
		request.setAttribute("WEEKDAYS_LIST", allDayList);
	
		
		// Página jsp asociada a la presentación de la entidad
		request.setAttribute("application", strURL);
		request.setAttribute("EntityId",Integer.toString(entityId));
		request.setAttribute("KeyId", Integer.toString(keyId));

		// Generamos la ruta de navegación hasta la pantalla actual.
		BreadCrumbsContainer bcm = BreadCrumbsManager.getInstance(catalogAPI).getBreadCrumbs(request);
 		request.setAttribute("BreadCrumbs", bcm.getList());

		//poner el item en session
 		request.getSession().setAttribute("CALENDAR", entityapp.getItem());
 		
 		return mapping.findForward("success_show");
	}
 
 	public ActionForward holydays(ActionMapping mapping,
			   ActionForm form,
			   HttpServletRequest request,
			   HttpServletResponse response,
			   SessionAPI session) throws Exception {
 		
 		// Comprobar si el usuario tiene asignadas las funciones adecuadas
		FunctionHelper.checkFunctions(request, session.getClientContext(), new int[] {
				ISecurityAPI.FUNC_COMP_CALENDARS_READ,
				ISecurityAPI.FUNC_COMP_CALENDARS_EDIT });
 		
 		//CalendarForm defaultForm = (CalendarForm) form;
 		
		//IItem item = (IItem)request.getSession().getAttribute("CALENDAR");
		
		//String calendarioXML = (String)item.getString("CALENDARIO");
		
		//CalendarDef calendarDef = new CalendarDef(calendarioXML);
		//String[] daysSelected = defaultForm.getWeekDaysSelect();
		
		//calendarDef.addWeekEnd(daysSelected);
		
		//item.set("CALENDARIO", calendarDef.getXmlValues());
		//item.store(session.getClientContext());
		
		//request.setAttribute("days", daysSelected);
 		return mapping.findForward("success_holydays");
 		
 	}
 	
 	//En la aplicación se encuentra guardado un fichero, "FixedHolidays.xml", este fichero tiene almacenado todos los días festivos definidos por 
 	//el usuario. Esta función leera el fichero y almacenará en una lista todos los días del fichero. Si se produce algun error almacenará el error.
 	public ActionForward fixedHolydays(ActionMapping mapping,
			   ActionForm form,
			   HttpServletRequest request,
			   HttpServletResponse response,
			   SessionAPI session) throws Exception {

 		// Comprobar si el usuario tiene asignadas las funciones adecuadas
		FunctionHelper.checkFunctions(request, session.getClientContext(), new int[] {
				ISecurityAPI.FUNC_COMP_CALENDARS_READ,
				ISecurityAPI.FUNC_COMP_CALENDARS_EDIT });

  		//Se utilizan dos listas por si se produce un error de sintaxis en el fichero cuando ya se han almacenado valores en la lista.
 		List fixedHolidays = new ArrayList();
  		List allFixedHolidays = new ArrayList();
 		String language = request.getLocale().getLanguage();
 		File xmlFixedHolidaysFile = new File(getISPACPath(PATH_FILE_FIXED_HOLIDAYS));
 		if (!xmlFixedHolidaysFile.exists()) {

    		ActionMessages errors = new ActionMessages();
    		errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.calendar.xmlNoExist", new String[] {PATH_FILE_FIXED_HOLIDAYS}));
			addErrors(request, errors);		
		}
 		else {
 			
		 	try {
		 		XmlFacade xmlFacade = new XmlFacade(new FileInputStream(xmlFixedHolidaysFile));
		 		Node holydaysNode = xmlFacade.getSingleNode(TAG_HOLYDAYS);			 		
				NodeIterator itrHolydays = XmlFacade.getNodeIterator(holydaysNode, TAG_HOLYDAY); 
				for (Node nodeHolyday = itrHolydays.nextNode(); nodeHolyday != null; nodeHolyday = itrHolydays.nextNode()) { 			
					String date = XmlFacade.get(nodeHolyday, TAG_DATE);
					Node labelsNode = XmlFacade.getSingleNode(nodeHolyday, TAG_LABELS);
					NodeIterator itrLabels = XmlFacade.getNodeIterator(labelsNode, TAG_LABEL); 
					for (Node nodeLabel = itrLabels.nextNode(); nodeLabel != null; nodeLabel = itrLabels.nextNode()) {
						String xmlLanguage = XmlFacade.getAttributeValue(nodeLabel, ATR_LANG);
						if (language.equalsIgnoreCase(xmlLanguage)) {
							String name = XmlFacade.getNodeValue(nodeLabel);
							if (!date.equalsIgnoreCase("")&&!name.equalsIgnoreCase("")) {
								HolydayDef holydayDef = new HolydayDef();
								holydayDef.setName(name);
								holydayDef.setDate(date);
								fixedHolidays.add(holydayDef);
							}
						}
					}
				}
				allFixedHolidays.addAll(fixedHolidays);
		 	}
		 	catch (Exception e) {

	    		ActionMessages errors = new ActionMessages();
	    		errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.calendar.xmlBadFormat", new String[] {PATH_FILE_FIXED_HOLIDAYS}));
				addErrors(request, errors);			
		 	}
 		}
 		
 		request.setAttribute(DAYS_LIST, allFixedHolidays);
		
		return mapping.findForward("addFixedHolidays");	
	}
 	
 	//Esta función guarda en la base de datos correspondiente los dias seleccionados en la página.
 	public ActionForward saveFixedHolidays(ActionMapping mapping,
			   ActionForm form,
			   HttpServletRequest request,
			   HttpServletResponse response,
			   SessionAPI session) throws Exception {

 		// Comprobar si el usuario tiene asignadas las funciones adecuadas
		FunctionHelper.checkFunctions(request, session.getClientContext(), new int[] {
				ISecurityAPI.FUNC_COMP_CALENDARS_EDIT });

		ClientContext cct = session.getClientContext();
		CalendarForm defaultForm = (CalendarForm) form;		
		String[] selectFixedHolidays=(String[]) defaultForm.getSelectFixedHolidays();
		String year = (String) defaultForm.getProperty("YEAR");
		
        // Validación
		ActionMessages errors = new ActionMessages();	
		defaultForm.setEntityAppName("EditCTCalendar");
		errors = defaultForm.validate(mapping, request);
		
		if(errors.isEmpty()) {
			
		    String entityId = defaultForm.getEntity();
		 	String regId = defaultForm.getKey();
		 	String nombre = request.getParameter("nombre");
		 	String dias = request.getParameter("weekDaysSelect");
		 	
			// Ejecución en un contexto transaccional
			boolean bCommit = false;
				
			try {
				// Abrir transacción
				cct.beginTX();
				
		    	IInvesflowAPI invesFlowAPI = session.getAPI();
		    	ICatalogAPI catalogAPI = invesFlowAPI.getCatalogAPI();
		        	
		    	// Bloquear
		    	catalogAPI.queryCTEntities(ICatalogAPI.ENTITY_SPAC_CALENDARIOS, "");
				IItem item = (IItem)request.getSession().getAttribute("CALENDAR");			

				String calendarioXML = (String)item.getString("CALENDARIO");
				CalendarDef calendarDef = new CalendarDef(calendarioXML);
					
				for ( int i = 0; i<selectFixedHolidays.length;i++) {
					String[] date = selectFixedHolidays[i].split("---");
					ActionMessages errorsFixedHolidays = new ActionMessages();
					validateHolyday(item, date[0]+"/"+year, session, errorsFixedHolidays);
					
					if (errorsFixedHolidays.isEmpty()) {				
						calendarDef.addHolyday(date[1], date[0]+"/"+year);
					}
				}
									
				item.set("CALENDARIO", calendarDef.getXmlValues());
				item.store(session.getClientContext());
				
				// Si todo ha sido correcto se hace commit de la transacción
				bCommit = true;
								
				String url = "?method=show&entityId="+entityId+"&regId="+regId+"&save=true&nombre="+nombre+"&weekDaysSelect="+dias;
						
				request.setAttribute("target", "top");				
				request.setAttribute("url", url);
						
				return mapping.findForward("loadOnTarget");		
			}
			catch (ISPACException e) {
				
				throw new ISPACInfo(e.getMessage());
			}
			finally {
				cct.endTX(bCommit);
			}
		}
		
		saveErrors(request, errors);
		request.setAttribute(DAYS_LIST, request.getAttribute(DAYS_LIST));
		
		return mapping.findForward("errorYear");
	}
 	
 	public ActionForward copyCalendar(ActionMapping mapping,
			   ActionForm form,
			   HttpServletRequest request,
			   HttpServletResponse response,
			   SessionAPI session) throws Exception {
		
 		// Comprobar si el usuario tiene asignadas las funciones adecuadas
		FunctionHelper.checkFunctions(request, session.getClientContext(), new int[] {
				ISecurityAPI.FUNC_COMP_CALENDARS_EDIT });

		return mapping.findForward("copy_calendar");
	}
 	
 	
 	public ActionForward createCopyCalendar(ActionMapping mapping,
			   ActionForm form,
			   HttpServletRequest request,
			   HttpServletResponse response,
			   SessionAPI session) throws Exception {

 		// Comprobar si el usuario tiene asignadas las funciones adecuadas
		FunctionHelper.checkFunctions(request, session.getClientContext(), new int[] {
				ISecurityAPI.FUNC_COMP_CALENDARS_EDIT });
 		
		ClientContext cct = session.getClientContext();
		IInvesflowAPI invesFlowAPI = session.getAPI();
		ICatalogAPI catalogAPI = invesFlowAPI.getCatalogAPI();

		// Formulario asociado a la acción
		CalendarForm defaultForm = (CalendarForm) form;
 		
 		String entityId = request.getParameter("entityId");
 		String keyId = request.getParameter("regId");	
 		String name = defaultForm.getProperty("NEW_CALENDAR_NAME");
 		IItemCollection calendar=null;
 		IItem icalendar=null;
 		IItem newCalendar=null;
 		
		// Ejecución en un contexto transaccional
		boolean bCommit = false;
 		
		try {
			// Abrir transacción
			cct.beginTX();
			
			List errorList = validate(name, session);
					
			if (!errorList.isEmpty()) {
				
				ActionMessages errors = new ActionMessages();
				Iterator iteError = errorList.iterator();
				while (iteError.hasNext()) {
					ValidationError validError = (ValidationError) iteError.next();
					ActionMessage error = new ActionMessage(validError.getErrorKey(), validError.getArgs());
					errors.add("property(NEW_CALENDAR_NAME)", error);
				}
				saveErrors(request, errors);

				return mapping.findForward("validate");
			}
			
			calendar = (IItemCollection) catalogAPI.queryCTEntities(ICatalogAPI.ENTITY_SPAC_CALENDARIOS, "where ID="+keyId);
			icalendar = calendar.value();
			newCalendar = catalogAPI.createCTEntity(ICatalogAPI.ENTITY_SPAC_CALENDARIOS);
			newCalendar.set("NOMBRE", name);
			newCalendar.set("CALENDARIO", icalendar.get("CALENDARIO"));
			newCalendar.store(cct);
			
			// Si todo ha sido correcto se hace commit de la transacción
			bCommit = true;
		}
		catch (ISPACException e) {
			
			throw new ISPACInfo(e.getMessage());
		}
		finally {
			cct.endTX(bCommit);
		}
 		
		request.setAttribute("target", "top");
		request.setAttribute("url", "?method=show&entityId="+entityId+"&regId="+newCalendar.getKeyInteger().toString());
		
		return mapping.findForward("loadOnTarget");
	}
 	
	public List validate(String name, SessionAPI session) throws ISPACException {
		
		ArrayList mErrorList = new ArrayList();

		ValidationError error = null;
	
		if (StringUtils.isBlank(name)) {
			
			error = new ValidationError("property(NEW_CALENDAR_NAME)", "errors.required", new String[]{Messages.getString(session.getClientContext().getAppLanguage(), "form.calendar.propertyLabel.name")});
			mErrorList.add(error);
		}
		else {
			IInvesflowAPI invesFlowAPI = session.getAPI();
			ICatalogAPI catalogAPI = invesFlowAPI.getCatalogAPI();
				
			// Bloqueo
			IItemCollection itemcol = catalogAPI.queryCTEntities(ICatalogAPI.ENTITY_SPAC_CALENDARIOS, "");
			// Nombre único de calendario
			
			itemcol = (IItemCollection)catalogAPI.queryCTEntities(ICatalogAPI.ENTITY_SPAC_CALENDARIOS, " WHERE NOMBRE = '" + DBUtil.replaceQuotes(name) + "'");
			if (itemcol.next()) {
				
				error = new ValidationError("property(NEW_CALENDAR_NAME)", "error.calendar.nameDuplicated", new String[]{name});
				mErrorList.add(error);
			}
	
		}
		return mErrorList;
	}
	
	
 	
 	public ActionForward saveholydays(ActionMapping mapping,
			   ActionForm form,
			   HttpServletRequest request,
			   HttpServletResponse response,
			   SessionAPI session) throws Exception {

 		// Comprobar si el usuario tiene asignadas las funciones adecuadas
		FunctionHelper.checkFunctions(request, session.getClientContext(), new int[] {
				ISecurityAPI.FUNC_COMP_CALENDARS_EDIT });

 		ClientContext cct = session.getClientContext();
 		CalendarForm defaultForm = (CalendarForm) form;
        // Validar el formulario
 		defaultForm.setEntityAppName("SaveHolydaysCalendar");
        ActionMessages errors = defaultForm.validate(mapping, request);
        
        String entityId = defaultForm.getEntity();
 		String regId = defaultForm.getKey();
 		String nombre = request.getParameter("nombre");
 		String dias = request.getParameter("weekDaysSelect");
 		
		// Ejecución en un contexto transaccional
		boolean bCommit = false;
		
		try {
			// Abrir transacción
			cct.beginTX();
        
	        if (errors.isEmpty()) {
	        	
	    		IInvesflowAPI invesFlowAPI = session.getAPI();
	    		ICatalogAPI catalogAPI = invesFlowAPI.getCatalogAPI();
	        	
	    		// Bloqueo
	    		catalogAPI.queryCTEntities(ICatalogAPI.ENTITY_SPAC_CALENDARIOS, "");
	    		
				IItem item = (IItem)request.getSession().getAttribute("CALENDAR");			
				//deberia ir abajo
				String calendarioXML = (String)item.getString("CALENDARIO");
				validateHolyday(item, defaultForm.getProperty("HOLYDAY_DATE"), session, errors);
				
				if (!errors.isEmpty()) {
										
					saveErrors(request, errors);
					return mapping.findForward("success_holydays");
				}
				
				CalendarDef calendarDef = new CalendarDef(calendarioXML);
				calendarDef.addHolyday(defaultForm.getProperty("HOLYDAY_NAME"), defaultForm.getProperty("HOLYDAY_DATE"));
				item.set("CALENDARIO", calendarDef.getXmlValues());
				item.store(session.getClientContext());
				
				// Si todo ha sido correcto se hace commit de la transacción
				bCommit = true;
				
				// Para que vaya a la direccion donde yo quiero				
				String url = "?method=show&entityId="+entityId+"&regId="+regId+"&save=true&nombre="+nombre+"&weekDaysSelect="+dias;
				
				request.setAttribute("target", "top");				
				request.setAttribute("url", url);
				
				return mapping.findForward("loadOnTarget");
	        }
	        else {
	        	saveErrors(request, errors);	        	
	        	return getActionForwardHolydays();
	        }
		}
		catch (ISPACException e) {
			
			throw new ISPACInfo(e.getMessage());
		}
		finally {
			cct.endTX(bCommit);
		}
	}
 	
	public void validateHolyday(IItem item, String date, SessionAPI session, ActionMessages errors) throws ISPACException {
			
		String calendarioXML = (String)item.getString("CALENDARIO");

		
		//A este validate le pasaremos el nombre del calendario, el nombre y la fecha.
		//Sacamos todos los dias festivos del calendario seleccionado.
		//Miramos si alguna fecha y nombre coincide con la que queremos meter.
		
		CalendarDef calendarDef = new CalendarDef(calendarioXML);
		List holydays = calendarDef.getHolydays();
		Iterator itr = holydays.iterator();
		while (itr.hasNext()) {
			HolydayDef holyday = (HolydayDef) itr.next();
			String dayHolyday = holyday.getDate();		
			
			if (dayHolyday.equalsIgnoreCase(date)) {
				ActionMessage error = new ActionMessage("error.calendar.date.dateDuplicated", new String[] {date});						 
				errors.add("property(HOLYDAY_DATE)", error);
			} 						
		}
	}
 	
 	
 	
 	
// 	public ActionForward deleteHolyday(ActionMapping mapping,
//			   ActionForm form,
//			   HttpServletRequest request,
//			   HttpServletResponse response,
//			   SessionAPI session) throws Exception {
// 		
// 		return mapping.findForward("success_deleteHolyday");
// 		
// 	}
 	
 	public ActionForward deleteCalendar(ActionMapping mapping,
			   ActionForm form,
			   HttpServletRequest request,
			   HttpServletResponse response,
			   SessionAPI session) throws Exception {

 		// Comprobar si el usuario tiene asignadas las funciones adecuadas
		FunctionHelper.checkFunctions(request, session.getClientContext(), new int[] {
				ISecurityAPI.FUNC_COMP_CALENDARS_EDIT });

 		CalendarForm defaultForm = (CalendarForm) form;
 		ICatalogAPI catalogAPI = session.getAPI().getCatalogAPI();
 		String [] multibox = defaultForm.getMultibox();
 		for (int i = 0; i < multibox.length; i++) {
 			String idCalendar = multibox[i];
 			IItem item = catalogAPI.getCTEntity(ICatalogAPI.ENTITY_SPAC_CALENDARIOS, new Integer(idCalendar).intValue());
 			item.delete(session.getClientContext());
 		}
 		return mapping.findForward("success_deleteCalendar");
 	}
 	
 	public ActionForward deleteHolyday(ActionMapping mapping,
			   ActionForm form,
			   HttpServletRequest request,
			   HttpServletResponse response,
			   SessionAPI session) throws Exception {

 		// Comprobar si el usuario tiene asignadas las funciones adecuadas
		FunctionHelper.checkFunctions(request, session.getClientContext(), new int[] {
				ISecurityAPI.FUNC_COMP_CALENDARS_EDIT });

 		CalendarForm defaultForm = (CalendarForm) form;
 		
		IItem item = (IItem)request.getSession().getAttribute("CALENDAR");
		
		String calendarioXML = (String)item.getString("CALENDARIO");
		CalendarDef calendarDef = new CalendarDef(calendarioXML);
		String[] datesSelected = defaultForm.getMultibox();
		
		if (datesSelected != null) {
			for (int i = 0; i < datesSelected.length; i++) {
				calendarDef.removeHolyday(datesSelected[i]);
			}
		
			item.set("CALENDARIO", calendarDef.getXmlValues());
			item.store(session.getClientContext());
		}
		
		return getActionForwardShowDelete(request.getParameter("entityId"),request.getParameter("regId"), request.getParameter("nombre"), request.getParameter("weekDaysSelect"));		
 		//return getActionForwardShow(request.getParameter("entityId"),request.getParameter("regId"));		
	}
 	
 	
 	public ActionForward updateWeekEnd(ActionMapping mapping,
			   ActionForm form,
			   HttpServletRequest request,
			   HttpServletResponse response,
			   SessionAPI session) throws Exception {
 		
 		// Comprobar si el usuario tiene asignadas las funciones adecuadas
		FunctionHelper.checkFunctions(request, session.getClientContext(), new int[] {
				ISecurityAPI.FUNC_COMP_CALENDARS_EDIT });

 		// Comprobar si el usuario tiene asignadas las funciones adecuadas
		FunctionHelper.checkFunctions(request, session.getClientContext(), new int[] {
				ISecurityAPI.FUNC_COMP_CALENDARS_EDIT });

 		CalendarForm defaultForm = (CalendarForm) form;
 		
		IItem item = (IItem)request.getSession().getAttribute("CALENDAR");
		
		String calendarioXML = (String)item.getString("CALENDARIO");
		
		CalendarDef calendarDef = new CalendarDef(calendarioXML);
		String[] daysSelected = defaultForm.getWeekDaysSelect();
		
		calendarDef.addWeekEnd(daysSelected);
		
		item.set("CALENDARIO", calendarDef.getXmlValues());
		item.store(session.getClientContext());
		
		String entityId = request.getParameter("entityId");
 		if (entityId == null) {
 			entityId = defaultForm.getEntity();
 		}
 		
		String regId = request.getParameter("regId");
 		if (regId == null) {
 			regId = defaultForm.getKey();
 		}
 		
 		return getActionForwardShow(entityId, regId);
 		
 	}
 	 	
 	
 	ActionForward getActionForwardShow(String entityId, String regId){
 		String actionForwardURL =  "/showCTCalendar.do?method=show&entityId=" + entityId + "&regId=" + regId;
 		ActionForward ret = new ActionForward();
 		ret.setPath(actionForwardURL);
 		ret.setRedirect(true);
 		return ret;
	}
 	
 	ActionForward getActionForwardShowDelete(String entityId, String regId, String nombre, String diasSeleccionados){
 		String actionForwardURL =  "/showCTCalendar.do?method=show&entityId=" + entityId + "&regId=" + regId;
 		actionForwardURL += "&save=true&nombre="+nombre+"&weekDaysSelect="+diasSeleccionados;
 		ActionForward ret = new ActionForward();
 		ret.setPath(actionForwardURL);
 		ret.setRedirect(true);
 		return ret;
	}
 	
 	ActionForward getActionForwardHolydays(){
 		String actionForwardURL =  "/showCTCalendar.do?method=holydays";
 		ActionForward ret = new ActionForward();
 		ret.setPath(actionForwardURL);
 		ret.setRedirect(false);
 		return ret;
	}
 	
	public ActionForward store(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response, SessionAPI session) throws Exception {

 		// Comprobar si el usuario tiene asignadas las funciones adecuadas
		FunctionHelper.checkFunctions(request, session.getClientContext(), new int[] {
				ISecurityAPI.FUNC_COMP_CALENDARS_EDIT });

		ClientContext cct = session.getClientContext();

		IInvesflowAPI invesFlowAPI = session.getAPI();
		ICatalogAPI catalogAPI = invesFlowAPI.getCatalogAPI();

		// Formulario asociado a la acción
		CalendarForm defaultForm = (CalendarForm) form;

		//int keyId = Integer.parseInt(defaultForm.getKey());
		//int entityId = Integer.parseInt(defaultForm.getEntity());
		
		String parameter = request.getParameter("entityId");
 		if (parameter == null) {
 			parameter = defaultForm.getEntity();
 		}
 		int entityId = Integer.parseInt(parameter);
 		
		parameter = request.getParameter("regId");
 		int condicion = Integer.parseInt(parameter);
 		
 		if (parameter == null) {
 			parameter = defaultForm.getKey();
 		}
 		int keyId = Integer.parseInt(parameter);
		
		EntityApp entityapp = null;
		String path = getRealPath("");
		
		// Ejecución en un contexto transaccional
		boolean bCommit = false;

		try {
			// Abrir transacción
			cct.beginTX();

			// Obtener la aplicación que gestiona la entidad
			if (keyId == ISPACEntities.ENTITY_NULLREGKEYID) {

				entityapp = catalogAPI.newCTDefaultEntityApp(entityId, path);
				keyId = entityapp.getEntityRegId();
			} else {
				entityapp = catalogAPI.getCTDefaultEntityApp(entityId, path);
			}

			// Permite modificar los datos del formulario
			defaultForm.setReadonly("false");
			// Salva el identificador de la entidad
			defaultForm.setEntity(Integer.toString(entityId));
			// Salva el identificador del registro
			defaultForm.setKey(Integer.toString(keyId));
			defaultForm.processEntityApp(entityapp);

			// Se le asigna la clave del registro. Es necesario ya que el
			// item al que hace referencia puede estar recien creado y por tanto
			// tendría su campo clave a -1 (ISPACEntities.ENTITY_REGKEYID)
			entityapp.getItem().setKey(keyId);
			
			//entityapp.setAppName("EditCalendar");
			if (!entityapp.validate()) {
				
				ActionMessages errors = new ActionMessages();
				List errorList = entityapp.getErrors();
				Iterator iteError = errorList.iterator();
				while (iteError.hasNext()) {

					ValidationError validError = (ValidationError) iteError.next();
					ActionMessage error = new ActionMessage(validError.getErrorKey(), validError.getArgs());
					errors.add("property(NOMBRE)", error);
				}
				saveErrors(request, errors);

				return new ActionForward(mapping.getInput());
			}

			// Guardar la entidad
			entityapp.store();

			// Si todo ha sido correcto se hace commit de la transacción
			bCommit = true;
		}
		catch (ISPACException e) {

			if (entityapp != null) {

				// Establecer la aplicación para acceder a los valores extra en
				// el formulario
				defaultForm.setValuesExtra(entityapp);

				// Página jsp asociada a la presentación de la entidad
				request.setAttribute("application", entityapp.getURL());
				request.setAttribute("EntityId", Integer.toString(entityId));
				request.setAttribute("KeyId", Integer.toString(keyId));

				throw new ISPACInfo(e.getMessage());
			}
			else {
				// Suele producirse error en las secuencias al estar mal
				// inicializadas
				// provocando una duplicación de keys
				throw e;
			}
		}
		finally {
			cct.endTX(bCommit);
		}
		
		if (condicion == ISPACEntities.ENTITY_NULLREGKEYID) {
			return getActionForwardShow(String.valueOf(entityId), String.valueOf(keyId));
		}
		
		return updateWeekEnd(mapping, defaultForm, request, response, session);

//		ActionForward forward = mapping.findForward("ShowEntity" + entityId);
//		if (forward == null) {
//
//			forward = mapping.findForward("reloadShowEntity" + entityId);
//			if (forward == null) {
//				forward = mapping.findForward("reload");
//			}
//
//			String redirected = forward.getPath() + "?entityId=" + entityId + "&regId=" + keyId;
//			if (request.getQueryString() != null) {
//				redirected += "&" + request.getQueryString();
//			}
//			forward = new ActionForward(forward.getName(), redirected, true);
//		}
//
//		return forward;
	}

}

