package ieci.tdw.ispac.ispacmgr.action;

import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.ISPACEntities;
import ieci.tdw.ispac.api.entities.SpacEntities;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACInfo;
import ieci.tdw.ispac.api.impl.SessionAPI;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.Properties;
import ieci.tdw.ispac.api.item.Property;
import ieci.tdw.ispac.audit.IspacAuditConstants;
import ieci.tdw.ispac.audit.business.IspacAuditoriaManager;
import ieci.tdw.ispac.audit.business.manager.impl.IspacAuditoriaManagerImpl;
import ieci.tdw.ispac.audit.business.vo.AuditContext;
import ieci.tdw.ispac.audit.business.vo.IspacAuditoriaValorModificado;
import ieci.tdw.ispac.audit.business.vo.events.IspacAuditEventEntidadAltaVO;
import ieci.tdw.ispac.audit.business.vo.events.IspacAuditEventEntidadModificacionVO;
import ieci.tdw.ispac.audit.business.vo.events.IspacAuditEventTramiteModificacionVO;
import ieci.tdw.ispac.audit.context.AuditContextHolder;
import ieci.tdw.ispac.ispaclib.app.EntityApp;
import ieci.tdw.ispac.ispaclib.bean.ValidationError;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.utils.ArrayUtils;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;
import ieci.tdw.ispac.ispaclib.utils.TypeConverter;
import ieci.tdw.ispac.ispacmgr.action.form.EntityForm;
import ieci.tdw.ispac.ispacweb.api.IManagerAPI;
import ieci.tdw.ispac.ispacweb.api.IScheme;
import ieci.tdw.ispac.ispacweb.api.IState;
import ieci.tdw.ispac.ispacweb.api.ManagerAPIFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

public class StoreEntityAction extends BaseAction {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(StoreEntityAction.class);

	IspacAuditoriaManager auditoriaManager = new IspacAuditoriaManagerImpl();
	
	public ActionForward executeAction(ActionMapping mapping,
									   ActionForm form,
									   HttpServletRequest request,
									   HttpServletResponse response,
									   SessionAPI session) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("executeAction(ActionMapping, ActionForm, HttpServletRequest, HttpServletResponse, SessionAPI) - start");
		}

		ClientContext cct = session.getClientContext();

		IEntitiesAPI entapi = session.getAPI().getEntitiesAPI();
		
				
		// Estado del contexto de tramitación
		IManagerAPI managerAPI = ManagerAPIFactory.getInstance().getManagerAPI(cct);
		IState currentstate = managerAPI.currentState(getStateticket(request));
		
		IScheme scheme = managerAPI.getSchemeAPI();

		// Formulario asociado a la acción
		EntityForm defaultForm = (EntityForm) form;
		
		// Actualizar el estado con los datos del formulario (almacenando el id de entidad y el regId del registro de la entidad)
		String entity = defaultForm.getEntity();
		if (!StringUtils.isEmpty(entity)) {
			currentstate.setEntityId(TypeConverter.parseInt(entity, ISPACEntities.ENTITY_NULLREGKEYID));
		}
		String key = defaultForm.getKey();
		if (!StringUtils.isEmpty(key)) {
			currentstate.setEntityRegId(TypeConverter.parseInt(key, ISPACEntities.ENTITY_NULLREGKEYID));
		}

		// Si el estado esta en solo lectura, no se procedera a guardar los datos.
		// No se deberia dar el caso de intentar guardar estando en solo lectura, 
		// pero si se diera el caso nos aseguramos que no se permita guardar datos.
		// Y evitar guardar cuando se ha perdido la sesión.
		if ((!currentstate.getReadonly()) &&
			(currentstate.getEntityId() != ISPACEntities.ENTITY_NULLREGKEYID)) {

			int entityId = currentstate.getEntityId();
			int keyId = currentstate.getEntityRegId();

			EntityApp entityapp = null;
			EntityApp oldEntityapp = null;
			
			// Ejecución en un contexto transaccional
			boolean bCommit = false;
			boolean noDefault = false;

			try {
				// Abrir transacción
				cct.beginTX();

				// Si el identificador del registro es nulo (-1) (alta de registro en la entidad) se debe
				// crear una nueva entrada
				if (keyId == ISPACEntities.ENTITY_NULLREGKEYID) {
					
					//creacion de un registro de entidad (al llamar a create se obtiene un id de la secuencia de la entidad)
					IItem newitem = entapi.createEntity(entityId);
					
					//se almacena en el estado el id del objeto q se va a crear
					currentstate.setEntityRegId(newitem.getKeyInt());
					
					// Se inserta el Numero de expediente en el registro de la entidad (si tiene campo NUMEXP)
					IItem itemCat = entapi.getCatalogEntity(entityId);
					String fieldNumExp = itemCat.getString("CAMPO_NUMEXP");
					if (StringUtils.isNotEmpty(fieldNumExp)) {
						newitem.set(fieldNumExp, currentstate.getNumexp());
					}
					
					
					newitem.store(cct);
					entityapp = scheme.getEntityApp(currentstate, getRealPath(""), currentstate.getEntityRegId(), noDefault);
					//TODO: Auditar el alta de una entidad
					this.auditAltaEntidad(newitem, cct,entityapp);
				}
				
				// No utilizar el formulario por defecto
				// (para ver los documentos del expediente en el trámite)
				
				if (request.getParameter("nodefault")!= null && request.getParameter("nodefault").equals("true")) {
					noDefault = true;
				}

				// Obtener la aplicación que gestiona la entidad
				entityapp = scheme.getEntityApp(currentstate, getRealPath(""), currentstate.getEntityRegId(), noDefault);
				oldEntityapp = scheme.getEntityApp(currentstate, getRealPath(""), currentstate.getEntityRegId(), noDefault);

				// Permite modificar los datos del formulario
				defaultForm.setReadonly("false");
				// Salva el identificador de la entidad
				defaultForm.setEntity(Integer.toString(entityId));
				// Salva el identificador del registro
				defaultForm.setKey(Integer.toString(keyId));
				//recoger los datos del formulario y pasarlos a la entidad a traves
				//del entityApp invocando a su metodo setValue
				defaultForm.processEntityApp(entityapp);
								
				//el entityApp ya tiene los valores de la entidad. Ahora se validan
				if (!entityapp.validate()) {

					ActionMessages errors = new ActionMessages();
					List errorList = entityapp.getErrors();
					Iterator iteError = errorList.iterator();
					while (iteError.hasNext()) {
						
						// Mostrar los errores de la validación del entityapp
						ValidationError validError = (ValidationError) iteError.next();
						ActionMessage error = new ActionMessage(validError.getErrorKey(), validError.getArgs());
						errors.add(validError.getProperty(), error);
					}
					
					//saveErrors(request, errors);
					request.getSession().setAttribute(Globals.ERROR_KEY, errors);
					//sAction = "error";
										
					// como hubo error si el identificador del registro era nulo (-1) (se estaba creando un registro nuevo)
					//se anula la clave generada del estado
					
					if (keyId == ISPACEntities.ENTITY_NULLREGKEYID) {
						currentstate.setEntityRegId(ISPACEntities.ENTITY_NULLREGKEYID);
					}
				}
				else {
					// Guardar la entidad
					entityapp.store();
					String numExp = currentstate.getNumexp();
														
					// Si la validación es correcta se hace commit de la transacción
					bCommit = true;
					
					List<IspacAuditoriaValorModificado> valoresModificados = getModifiedFields(entityapp,oldEntityapp);
					//Modificación de un trámite
					if (entityId==SpacEntities.SPAC_DT_TRAMITES){
						this.auditModificacionTramite(numExp, cct, valoresModificados, entityapp);					
					}else{
						//Modificación de una entidad diferente a un trámite
						this.auditModificacionEntidad(numExp, cct, valoresModificados,entityapp);
					}
				}
			}
			catch (ISPACException e) {
				logger.error(
						"executeAction(ActionMapping, ActionForm, HttpServletRequest, HttpServletResponse, SessionAPI)",
						e);
				
				// Y si el identificador del registro es nulo (-1) se anula la clave generada
				if (keyId == ISPACEntities.ENTITY_NULLREGKEYID) {
					currentstate.setEntityRegId(ISPACEntities.ENTITY_NULLREGKEYID);
				}
				
				// Guardar el estado actual para evitar que se muestre otra entidad, esto ocurre
				// cuando anteriormente se ha cargado esa otra entidad utilizando el workframe
				setStateticket(request, currentstate);

				if (entityapp != null) {
					
					throw new ISPACInfo(e.getMessage());
				}
				else {
					// Suele producirse error en las secuencias al estar mal inicializadas
					// provocando una duplicación de keys
					throw e;
				}
			}
			finally {
				cct.endTX(bCommit);
			}
		}

		ActionForward action = mapping.findForward(currentstate.getLabel());
		String param = currentstate.getQueryString();

		/*
		if (StringUtils.isEmpty(param)) {
		param = "?actions=" + sAction;
		}
		else {
		param = param + "&actions=" + sAction;
		}
		*/
		
		// Mantener la ordenación de la lista si existe
		String displayTagOrderParams = getDisplayTagOrderParams(request);
		if (!StringUtils.isEmpty(displayTagOrderParams)) {
			param = param + "&" + displayTagOrderParams;
		}
		
		// Mantener la pestaña de datos activa
    	if (request.getParameter("block") != null){
    		String block = "&block="+request.getParameter("block");
    		param = param + block;
    	}
    	
    	// Mantener la forma de presentar el formulario
    	if (request.getParameter("form") != null){
    		String paramForm = "&form="+request.getParameter("form");
    		param = param + paramForm;
    	}
    	
    	// Mantener no utilizar el formulario por defecto
    	if (request.getParameter("nodefault") != null){
    		String paramForm = "&nodefault="+request.getParameter("nodefault");
    		param = param + paramForm;
    	}
    	
		ActionForward returnActionForward = new ActionForward(action.getName(), action.getPath()
				+ param, action.getRedirect());
		if (logger.isDebugEnabled()) {
			logger.debug("executeAction(ActionMapping, ActionForm, HttpServletRequest, HttpServletResponse, SessionAPI) - end");
		}
		return returnActionForward;
	}

	/**
	 * @param newEntityApp
	 * @throws ISPACException
	 */
	private List<IspacAuditoriaValorModificado> getModifiedFields(EntityApp newEntityApp, EntityApp oldEntityApp) throws ISPACException {
		List<IspacAuditoriaValorModificado> modifiedFields = new ArrayList<IspacAuditoriaValorModificado>();
		Properties properties = newEntityApp.getProperties();
		Iterator iterator = properties.iterator();
		while (iterator.hasNext())
		{
			Property property = (Property) iterator.next();
			String keyProperty = property.getName();
			if (property.isMultivalue()){
				//setProperty(property.getName(), entity.getMainItem().get(key));
				//Object[] array = (Object[])entity.getMainItem().get(property.getRawName());
				Object[] newValuesArray = (Object[])newEntityApp.getMultivalue(property.getName());
				StringBuffer values= new StringBuffer();
				if(!ArrayUtils.isEmpty(newValuesArray)){
					String[] st = new String[newValuesArray.length];
					for (int i = 0; i < newValuesArray.length; i++) {
						if (newValuesArray[i] == null){
							values.append("-");
						}else{
							values.append(newValuesArray[i].toString());
						}
					}
					
				}
				
				Object[] oldValuesArray = (Object[])oldEntityApp.getMultivalue(property.getName());
				StringBuffer oldValues= new StringBuffer();
				if(!ArrayUtils.isEmpty(oldValuesArray)){
					String[] st = new String[oldValuesArray.length];
					for (int i = 0; i < oldValuesArray.length; i++) {
						if (oldValuesArray[i] == null){
							oldValues.append("-");
						}else{
							oldValues.append(oldValuesArray[i].toString());
						}
					}										
				}
				if (oldValues.toString()!=values.toString()){
					IspacAuditoriaValorModificado valorModificado = new IspacAuditoriaValorModificado();
					valorModificado.setFieldName(keyProperty);
					valorModificado.setNewValue(values.toString());
					valorModificado.setOldValue(oldValues.toString());
					modifiedFields.add(valorModificado);
				}
				
			}else{
				
				String value = newEntityApp.getValue(keyProperty);
				String oldValue = oldEntityApp.getValue(keyProperty);
				if (value==null){
					value="";
				}
				if (oldValue==null){
					oldValue="";
				}
				if (!value.equalsIgnoreCase(oldValue)){
					IspacAuditoriaValorModificado valorModificado = new IspacAuditoriaValorModificado();
					valorModificado.setFieldName(keyProperty);
					valorModificado.setNewValue(value);
					valorModificado.setOldValue(oldValue);
					modifiedFields.add(valorModificado);
				}
							
			}
		}
		return modifiedFields;
	}
	
	/**
	 * @param nIdTaskPCD
	 * @param numExp
	 * @throws ISPACException 
	 */
	private void auditModificacionTramite(String numExp, ClientContext cct, List<IspacAuditoriaValorModificado> valoresModificados, EntityApp entityapp) throws ISPACException {
		if (logger.isDebugEnabled()) {
			logger.debug("auditConsultaTramite(int, ClientContext) - start");
		}

		IspacAuditEventTramiteModificacionVO evento = new IspacAuditEventTramiteModificacionVO();
		setCommonEventValues(numExp, cct, valoresModificados, entityapp, evento);
		
		logger.info("Auditando la modificación del trámite");
		auditoriaManager.audit(evento);

		if (logger.isDebugEnabled()) {
			logger.debug("auditConsultaTramite(int, ClientContext) - end");
		}
	}
	
	/**
	 * @param nIdTaskPCD
	 * @param numExp
	 * @throws ISPACException 
	 */
	private void auditModificacionEntidad(String numExp, ClientContext cct, List<IspacAuditoriaValorModificado> valoresModificados, EntityApp entityapp) throws ISPACException {
		if (logger.isDebugEnabled()) {
			logger.debug("auditConsultaTramite(int, ClientContext) - start");
		}
		
		IspacAuditEventEntidadModificacionVO evento = new IspacAuditEventEntidadModificacionVO();
		setCommonEventValues(numExp, cct, valoresModificados, entityapp, evento);
		logger.info("Auditando la modificación de la entidad");
		auditoriaManager.audit(evento);

		if (logger.isDebugEnabled()) {
			logger.debug("auditConsultaTramite(int, ClientContext) - end");
		}
	}
	
	/**
	 * @param nIdTaskPCD
	 * @param numExp
	 * @throws ISPACException 
	 */
	private void auditAltaEntidad(IItem newItem, ClientContext cct,EntityApp entityapp) throws ISPACException {
		if (logger.isDebugEnabled()) {
			logger.debug("auditConsultaTramite(int, ClientContext) - start");
		}
		
		IspacAuditEventEntidadAltaVO evento = new IspacAuditEventEntidadAltaVO();
		AuditContext auditContext = AuditContextHolder.getAuditContext();
		
		evento.setAppDescription(IspacAuditConstants.APP_DESCRIPTION);
		evento.setAppId(IspacAuditConstants.getAppId());
		
		evento.setIdUser("");
		evento.setUser("");
		evento.setUserHostName("");
		evento.setUserIp("");
		
		evento.setEntidadAppName(entityapp.getAppName());
		evento.setEntidadAppId(String.valueOf(entityapp.getAppId()));
		
		//Añadir también el número de expediente del trámite
		String numExp = newItem.getString("NUMEXP");
		evento.setNumExpediente(numExp);
		evento.setId(String.valueOf(newItem.getKeyInt()));

		evento.setFecha(new Date());

		if (auditContext != null) {
			evento.setUserHostName(auditContext.getUserHost());
			evento.setUserIp(auditContext.getUserIP());
			evento.setUser(auditContext.getUser());
			evento.setIdUser(auditContext.getUserId());
		} else {
			logger.error("ERROR EN LA AUDITORÍA. No está disponible el contexto de auditoría en el thread local. Faltan los siguientes valores por auditar: userId, user, userHost y userIp");
		}
		logger.info("Auditando la modificación de la entidad");
		auditoriaManager.audit(evento);

		if (logger.isDebugEnabled()) {
			logger.debug("auditConsultaTramite(int, ClientContext) - end");
		}
	}

	/**
	 * @param id
	 * @param cct
	 * @param valoresModificados
	 * @param entityapp
	 * @param auditContext
	 * @param evento
	 * @throws ISPACException 
	 */
	private void setCommonEventValues(String numExp, ClientContext cct,
			List<IspacAuditoriaValorModificado> valoresModificados, EntityApp entityapp,
			IspacAuditEventEntidadModificacionVO evento) throws ISPACException {
		
		AuditContext auditContext = AuditContextHolder.getAuditContext();
		
		evento.setAppDescription(IspacAuditConstants.APP_DESCRIPTION);
		evento.setAppId(IspacAuditConstants.getAppId());
		
		evento.setIdUser("");
		evento.setUser("");
		evento.setUserHostName("");
		evento.setUserIp("");
		
		evento.setEntidadAppName(entityapp.getAppName());
		evento.setEntidadAppId(String.valueOf(entityapp.getAppId()));
		
		//Añadir también el número de expediente del trámite
		evento.setNumExpediente(numExp);
		evento.setId(String.valueOf(entityapp.getEntityRegId()));
				

		evento.setValoresModificados(valoresModificados);
		evento.setFecha(new Date());

		if (auditContext != null) {
			evento.setUserHostName(auditContext.getUserHost());
			evento.setUserIp(auditContext.getUserIP());
			evento.setUser(auditContext.getUser());
			evento.setIdUser(auditContext.getUserId());
		} else {
			logger.error("ERROR EN LA AUDITORÍA. No está disponible el contexto de auditoría en el thread local. Faltan los siguientes valores por auditar: userId, user, userHost y userIp");
		}
	}



}