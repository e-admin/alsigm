package ieci.tdw.ispac.ispaccatalog.app;

import ieci.tdw.ispac.api.ICatalogAPI;
import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.IInvesflowAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.api.item.Properties;
import ieci.tdw.ispac.api.item.Property;
import ieci.tdw.ispac.ispaccatalog.messages.Messages;
import ieci.tdw.ispac.ispaclib.XMLUtil.XMLDocUtil;
import ieci.tdw.ispac.ispaclib.app.ExtendedEntityApp;
import ieci.tdw.ispac.ispaclib.bean.ValidationError;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.item.CompositeItem;
import ieci.tdw.ispac.ispaclib.item.GenericItem;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispaclib.utils.XPathUtil;
import ieci.tdw.ispac.ispaclib.utils.XmlTag;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.util.LabelValueBean;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

public class DeadlineApp extends ExtendedEntityApp {
	
	private static final String[][] timeUnits = {{"terms.unit.days", "1"},
												 {"terms.unit.workingDays", "2"},
												 {"terms.unit.months", "3"},
												 {"terms.unit.years", "4"}};

	private static final String[][] baseDateTypes =	{{"terms.date.entity", "ENTITY"},
													 {"terms.create.date", "CURRENTDATE"},
													 {"terms.rule.date", "RULE"}};

	private static final String[][] baseDateTypesForProcedure =	{{"terms.create.date", "CURRENTDATE"},
																 {"terms.rule.date", "RULE"}};

	private static final String[][] calendarTypesForProcedure =	{{"terms.catalog.calendar", "CALENDAR_CATALOG"},
																 {"terms.rule.calendar", "RULE"}};
	
	String language;

	public DeadlineApp(ClientContext context)
    {
        super(context);
    }

    public IItem processItem(IItem item)
	throws ISPACException
	{
	    CompositeItem composite = new CompositeItem("SPAC_P_PLAZO:ID");
		composite.addItem(item, "SPAC_P_PLAZOS:");

	    return item;
	}


	public void initiate() throws ISPACException {
		
		IInvesflowAPI invesFlowAPI = mContext.getAPI();
		IEntitiesAPI entitiesAPI = invesFlowAPI.getEntitiesAPI();
		ICatalogAPI catalogAPI = invesFlowAPI.getCatalogAPI();
		
		language = mContext.getAppLanguage();
		
		IItem item = catalogAPI.getDeadLine(getObjectType(), mitem.getKeyInt());

		//Añadimos las propiedades del plazo y parseamos
		//el XML para obtener las restantes
		((CompositeItem) mitem).addItem(item, "SPAC_P_PLAZO:");
		setProperty("SPAC_P_PLAZO:ID", item.getString("ID"));
		setPropertiesFromXML(item.getString("PLAZO"));

		//Obtener la lista de unidades temporales
		valuesExtra.put("TIME_UNITS", getComboTimeUnits(true));

		//Obtener la lista de tipos de fecha base
		valuesExtra.put("BASEDATE_TYPES", getComboBaseDateTypes(getObjectType(), true));
		
		//Obtener la lista de tipos de calendarios
		valuesExtra.put("CALENDAR_TYPES", getComboCalendarTypes( true));

		
		// Añade la etiqueta de la entidad en caso de tenerla
		Properties props = new Properties();
		props.addProperties(new Property [] {
				new Property(1, "CTENTITY:ID"),
				new Property(2, "ETIQUETA"),
				new Property(3, "ETIQUETA_CAMPO"),
				new Property(4, "RULE_NAME"),
				new Property(5, "RULECALENDAR_NAME"),
				new Property(6, "CALENDAR_NAME"),
				new Property(7, "CALENDAR_ID"),
				new Property(8, "CALENDARRULE_NAME")});
		IItem itemExtra = new GenericItem(props, "ID");
		
    	String entityId = (String)getProperty("SPAC_P_PLAZO:BASEDATE_ENTITY_ID");
    	String fieldName  = (String)getProperty("SPAC_P_PLAZO:BASEDATE_ENTITY_FIELD");
    	if (entityId != null && entityId.length() > 0) {
    		
    		IItemCollection entities = catalogAPI.queryCTEntities(ICatalogAPI.ENTITY_CT_ENTITY, "WHERE ID = " + entityId );
    		IItem entity = (IItem)entities.iterator().next();
    		String nombre = entity.getString("NOMBRE");
    		
    		String etiqueta = entitiesAPI.getEntityResourceValue(entity.getKeyInt(), language, nombre.toUpperCase());
    		String etiquetaCampo = entitiesAPI.getEntityResourceValue(entity.getKeyInt(), language, fieldName.toUpperCase());
    		
    		itemExtra.set("CTENTITY:ID", nombre);
    		itemExtra.set("ETIQUETA", etiqueta);
    		itemExtra.set("ETIQUETA_CAMPO", etiquetaCampo);

    	}
    	else {
    		itemExtra.set("CTENTITY:ID", "");
    		itemExtra.set("ETIQUETA", "");
    		itemExtra.set("ETIQUETA_CAMPO", "");
    	}
    	
    	String ruleId = (String)getProperty("SPAC_P_PLAZO:BASEDATE_RULE_ID");
		if (ruleId!=null && ruleId.length()>0){
	    	StringBuffer query = new StringBuffer();
			query.append("WHERE ID=").append(ruleId);
			query.append(" ORDER BY ID ");
		
	        IItemCollection rules = catalogAPI.queryCTEntities(ICatalogAPI.ENTITY_CT_RULE,query.toString());
	        if (rules.next()){
	        	IItem rule = (IItem)rules.iterator().next();
	        	String ruleName = rule.getString("NOMBRE");
	        	itemExtra.set("RULE_NAME", ruleName);
	        }else{
	        	itemExtra.set("RULE_NAME", "");
	        }
		}
    	String calendarRuleId = (String)getProperty("SPAC_P_PLAZO:CALENDAR_RULE_ID");
		if (calendarRuleId!=null && calendarRuleId.length()>0){
	    	StringBuffer query = new StringBuffer();
			query.append("WHERE ID=").append(calendarRuleId);
			query.append(" ORDER BY ID ");
		
	        IItemCollection rules = catalogAPI.queryCTEntities(ICatalogAPI.ENTITY_CT_RULE,query.toString());
	        if (rules.next()){
	        	IItem rule = (IItem)rules.iterator().next();
	        	String ruleName = rule.getString("NOMBRE");
	        	itemExtra.set("CALENDARRULE_NAME", ruleName);
	        }else{
	        	itemExtra.set("CALENDARRULE_NAME", "");
	        }
		}
		String calendarId = (String)getProperty("SPAC_P_PLAZO:CALENDAR_ID");
		if (calendarId!=null && calendarId.length()>0){
	    	StringBuffer query = new StringBuffer();
			query.append("WHERE ID=").append(calendarId);
			query.append(" ORDER BY ID ");
		
	        IItemCollection calendarios = catalogAPI.queryCTEntities(ICatalogAPI.ENTITY_SPAC_CALENDARIOS,query.toString());
	        if (calendarios.next()){
	        	IItem calendario = (IItem)calendarios.iterator().next();
	        	String calendarioName = calendario.getString("NOMBRE");
	        	itemExtra.set("CALENDAR_NAME", calendarioName);
	        }else{
	        	itemExtra.set("CALENDAR_NAME", "");
	        }
		}
		
		
		((CompositeItem) mitem).addItem(itemExtra, "EXTRA:", false);
	}

	public boolean validate() throws ISPACException
	{
		ValidationError error = null;

		String sUnits = (String)valuesExtra.get("SPAC_P_PLAZO:UNITS");
		if(StringUtils.isNotBlank(sUnits) && !"-1".equals(sUnits)){
			String sMagnitude = (String)valuesExtra.get("SPAC_P_PLAZO:MAGNITUDE");
			if("".equals(sMagnitude)){
				error = new ValidationError("SPAC_P_PLAZO:MAGNITUDE", "form.deadline.error.required.magnitude");
			}else{
				if (!StringUtils.isNumeric(sMagnitude)) {
					error = new ValidationError("SPAC_P_PLAZO:MAGNITUDE", "form.deadline.error.invalid.magnitude");
				} else {
					String sBaseType = (String)valuesExtra.get("SPAC_P_PLAZO:BASEDATE_TYPE");
					if(!"-1".equals(sBaseType)){
						if("ENTITY".equals(sBaseType)){
							if(
									("".equals(valuesExtra.get("SPAC_P_PLAZO:BASEDATE_ENTITY_FIELD"))) ||
									("".equals(valuesExtra.get("SPAC_P_PLAZO:BASEDATE_ENTITY_ID")))
								){
								error = new ValidationError("SPAC_P_PLAZO:BASEDATE_ENTITY_FIELD", "form.deadline.error.required.entityAndField");
							}
						}else if("RULE".equals(sBaseType)){
							if("".equals(valuesExtra.get("SPAC_P_PLAZO:BASEDATE_RULE_ID"))){
								error = new ValidationError("SPAC_P_PLAZO:BASEDATE_RULE_ID", "form.deadline.error.required.rule");
							}
						}
						String sCalendarType = (String)valuesExtra.get("SPAC_P_PLAZO:CALENDAR_TYPE");
						if(!"-1".equals(sCalendarType)){
							if(calendarTypesForProcedure[0][1].equals(sCalendarType)){
								if ("".equals(valuesExtra.get("SPAC_P_PLAZO:CALENDAR_ID")))	{
									error = new ValidationError("SPAC_P_PLAZO:CALENDAR_ID", "form.deadline.error.required.calendario");
								}
							}else if(calendarTypesForProcedure[1][1].equals(sCalendarType)){
								if("".equals(valuesExtra.get("SPAC_P_PLAZO:CALENDAR_RULE_ID"))){
									error = new ValidationError("SPAC_P_PLAZO:CALENDAR_RULE_ID", "form.deadline.error.required.calendarrule");
								}
							}
						}
					}else{
						error = new ValidationError("SPAC_P_PLAZO:BASEDATE_TYPE", "form.deadline.error.required.baseDateType");
					}
				}
			}
		}else{
			String sMagnitude = (String)valuesExtra.get("SPAC_P_PLAZO:MAGNITUDE");
			if(!"".equals(sMagnitude)){
				error = new ValidationError("SPAC_P_PLAZO:UNITS", "form.deadline.error.required.units");
			}else{
				String sBaseType = (String)valuesExtra.get("SPAC_P_PLAZO:BASEDATE_TYPE");
				if(!"-1".equals(sBaseType)&&!"".equals(sBaseType)){
					error = new ValidationError("SPAC_P_PLAZO:UNITS", "form.deadline.error.required.units");	
				}else{
					String calendarType = (String)valuesExtra.get("SPAC_P_PLAZO:CALENDAR_TYPE");
					if(!"-1".equals(calendarType)&&!"".equals(calendarType)){
						error = new ValidationError("SPAC_P_PLAZO:UNITS", "form.deadline.error.required.units");
					}
				}
			}
			
		}
		if(error != null){
			addError(error);
			return false;
		}
		return true;
	}

	private void setPropertiesFromXML(String sXML) throws ISPACException{
		setProperty("SPAC_P_PLAZO:UNITS","");
		setProperty("SPAC_P_PLAZO:MAGNITUDE","");
		setProperty("SPAC_P_PLAZO:BASEDATE_TYPE", "");
		setProperty("SPAC_P_PLAZO:BASEDATE_ENTITY_ID", "");
		setProperty("SPAC_P_PLAZO:BASEDATE_ENTITY_FIELD", "");
		setProperty("SPAC_P_PLAZO:BASEDATE_RULE_ID", "");
		setProperty("SPAC_P_PLAZO:CALENDAR_TYPE", "");
		setProperty("SPAC_P_PLAZO:CALENDAR_ID", "");
		setProperty("SPAC_P_PLAZO:CALENDAR_RULE_ID", "");

		if(sXML != null){
			Document doc = XMLDocUtil.newDocument(sXML);
			setProperty("SPAC_P_PLAZO:UNITS",XPathUtil.selectNode(doc, "/deadline/units").getFirstChild().getNodeValue());
			setProperty("SPAC_P_PLAZO:MAGNITUDE",XPathUtil.selectNode(doc, "/deadline/magnitude").getFirstChild().getNodeValue());
			Node nodoAux = XPathUtil.selectNode(doc, "/deadline/basedate/entity");
			if( nodoAux != null){
				setProperty("SPAC_P_PLAZO:BASEDATE_ENTITY_ID", nodoAux.getAttributes().getNamedItem("id").getNodeValue());
				setProperty("SPAC_P_PLAZO:BASEDATE_ENTITY_FIELD", nodoAux.getAttributes().getNamedItem("field").getNodeValue());
				setProperty("SPAC_P_PLAZO:BASEDATE_TYPE", "ENTITY");
			}else{
				nodoAux = XPathUtil.selectNode(doc, "/deadline/basedate/rule");
				if( nodoAux != null ){
					setProperty("SPAC_P_PLAZO:BASEDATE_RULE_ID", nodoAux.getAttributes().getNamedItem("id").getNodeValue());
					setProperty("SPAC_P_PLAZO:BASEDATE_TYPE", "RULE");
				}else{
					setProperty("SPAC_P_PLAZO:BASEDATE_TYPE", "CURRENTDATE");
				}
			}
			Node nodoAux2 = XPathUtil.selectNode(doc, "/deadline/calendar/rule");
			if( nodoAux2 != null ){
				setProperty("SPAC_P_PLAZO:CALENDAR_RULE_ID", nodoAux2.getAttributes().getNamedItem("id").getNodeValue());
				setProperty("SPAC_P_PLAZO:CALENDAR_TYPE", "RULE");
			}else{
				nodoAux2 = XPathUtil.selectNode(doc, "/deadline/calendar/ctcalendar");
				if (nodoAux2!=null){
					setProperty("SPAC_P_PLAZO:CALENDAR_ID", nodoAux2.getAttributes().getNamedItem("id").getNodeValue());
					setProperty("SPAC_P_PLAZO:CALENDAR_TYPE", "CALENDAR_CATALOG");
				}
			}
			
		}
	}

	private int getObjectType() throws ISPACException{
		if ((ICatalogAPI.ENTITY_P_PROCEDURE == mMainEntityId) 
				|| (ICatalogAPI.ENTITY_P_SUBPROCEDURE == mMainEntityId)){
			return 1;
		} else if ((ICatalogAPI.ENTITY_P_STAGE == mMainEntityId)
				|| (ICatalogAPI.ENTITY_P_ACTIVITIES == mMainEntityId)) {
			return 2;
		} else if (ICatalogAPI.ENTITY_P_TASK == mMainEntityId) {
			return 3;
		} else {
			throw new ISPACException("Tipo de objeto erroneo en asignacion de plazo.");
		}
	}

	public void store()
	throws ISPACException {

		try
		{
			ICatalogAPI catalogAPI = mContext.getAPI().getCatalogAPI();
			String sUnits = (String)valuesExtra.get("SPAC_P_PLAZO:UNITS");
			if(StringUtils.isNotBlank(sUnits) && !"-1".equals(sUnits)){
				catalogAPI.setDeadLine(mitem.getInt("SPAC_P_PLAZO:ID"),
										getObjectType(),
										mitem.getKeyInt(),
										getPlazoXML((String)valuesExtra.get("SPAC_P_PLAZO:UNITS"),
													(String)valuesExtra.get("SPAC_P_PLAZO:MAGNITUDE"),
													(String)valuesExtra.get("SPAC_P_PLAZO:BASEDATE_TYPE"),
													((String)valuesExtra.get("SPAC_P_PLAZO:BASEDATE_ENTITY_FIELD")).toUpperCase(),
													(String)valuesExtra.get("SPAC_P_PLAZO:BASEDATE_ENTITY_ID"),
													(String)valuesExtra.get("SPAC_P_PLAZO:BASEDATE_RULE_ID"),
													(String)valuesExtra.get("SPAC_P_PLAZO:CALENDAR_TYPE"),
													(String)valuesExtra.get("SPAC_P_PLAZO:CALENDAR_RULE_ID"),
													(String)valuesExtra.get("SPAC_P_PLAZO:CALENDAR_ID")));
			}else{
				//borrar el plazo del SPAC_P_RELPLAZOS
				catalogAPI.dropDeadLine(getObjectType(), mitem.getKeyInt());
				
			}
		}
		catch (Exception ie)
		{
			throw new ISPACException("Error en EntityBean:store()", ie);
		}
	}

	private String getPlazoXML(String units, String magnitude, String baseDateType, 
			String entityField, String entityId, String ruleId, String calendarType, String calendarRuleId, String calendarId){
		String baseDate = null;
		if(DeadlineApp.baseDateTypes[0][1].equals(baseDateType)){
			baseDate = XmlTag.newTag("entity","", XmlTag.newAttr("id", entityId) + XmlTag.newAttr("field", entityField));
		}else if(DeadlineApp.baseDateTypes[1][1].equals(baseDateType)){
			baseDate = XmlTag.newTag("currentdate", "");
		}else if(DeadlineApp.baseDateTypes[2][1].equals(baseDateType)){
			baseDate = XmlTag.newTag("rule", "", XmlTag.newAttr("id", ruleId));
		}
		String calendar=null;
		if(DeadlineApp.calendarTypesForProcedure[0][1].equals(calendarType)){
			calendar = XmlTag.newTag("ctcalendar","", XmlTag.newAttr("id", calendarId));
		}else if(DeadlineApp.calendarTypesForProcedure[1][1].equals(calendarType)){
			calendar = XmlTag.newTag("rule","", XmlTag.newAttr("id", calendarRuleId));
		}
		

		String aux = XmlTag.getXmlInstruction("ISO-8859-1") +
						XmlTag.newTag("deadline",
											XmlTag.newTag("units", units) +
											XmlTag.newTag("magnitude", magnitude) +
											XmlTag.newTag("basedate", baseDate)+
											XmlTag.newTag("calendar", calendar));
		return aux;
	}

	private List getCombo(String[][] array, boolean noValue){
		
		List aux = new ArrayList();
		if(noValue){
			aux.add(new LabelValueBean(Messages.getString(language, "terms.select"), "-1"));
		}
		for(int i=0; i<array.length;i++){
			aux.add(new LabelValueBean(Messages.getString(language, array[i][0]), array[i][1]));
		}
		return aux;
	}

	private List getComboTimeUnits(boolean noValue){
		return getCombo(DeadlineApp.timeUnits, noValue);
	}

	private List getComboBaseDateTypes(int objectType, boolean noValue){
//		if (objectType == 1) {
//			return getCombo(DeadlineApp.baseDateTypesForProcedure, noValue);
//		} else  {
//			return getCombo(DeadlineApp.baseDateTypes, noValue);
//		}
		return getCombo(DeadlineApp.baseDateTypesForProcedure, noValue);
		
	}
	private List getComboCalendarTypes( boolean noValue){
		return getCombo(DeadlineApp.calendarTypesForProcedure, noValue);
		
	}

}
