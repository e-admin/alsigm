package es.ieci.tecdoc.fwktd.web.controller.crud;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.InternalPathMethodNameResolver;
import org.springframework.web.servlet.mvc.multiaction.NoSuchRequestHandlingMethodException;
import org.springframework.web.util.WebUtils;

import es.ieci.tecdoc.fwktd.core.exception.ManageableException;
import es.ieci.tecdoc.fwktd.core.model.Entity;
import es.ieci.tecdoc.fwktd.core.services.messages.MessagesService;
import es.ieci.tecdoc.fwktd.util.ErrorUtils;
import es.ieci.tecdoc.fwktd.web.controller.BaseFormController;
import es.ieci.tecdoc.fwktd.web.delegate.CRUDDelegate;
import es.ieci.tecdoc.fwktd.web.servlet.handler.FlashScopeInterceptor;

/**
 * 
 * @author IECISA
 * 
 * @param <T>
 */
@SuppressWarnings("deprecation")
public abstract class CRUDController<T extends Entity> extends
		BaseFormController {

	/**
	 * 
	 * @param clazz
	 */
	public CRUDController() {
		super();
		// setSessionForm(true);
		setCommandName(FORM_NAME);
		setCommandClass(CRUDCommand.class);
		setBindOnNewForm(true);
	}

	@Override
	protected void onBind(HttpServletRequest request, Object command)
			throws Exception {
		super.onBind(request, command);

		String method = getMethod(request);

		CRUDCommand<Entity> crudCommand = castCommand(command);

		crudCommand.setMethod(method);
		crudCommand.setRequested(method);
	}

	/**
	 * Fuerza un cast del objeto que llega en el parámetro <code>command</code>
	 * al tipo <code>CRUDCommand</code>.
	 * 
	 * @param command
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private CRUDCommand<Entity> castCommand(Object command) {
		if (!(command instanceof CRUDCommand)) {
			throw new RuntimeException(
					"CRUDController only works with commands of type "
							+ CRUDCommand.class);
		}

		return (CRUDCommand<Entity>) command;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected Object formBackingObject(HttpServletRequest request)
			throws Exception {
		Object command = super.formBackingObject(request);

		CRUDCommand crudCommand = castCommand(command);
		initializeForm(crudCommand);

		return crudCommand;
	}

	/**
	 * 
	 * @param crudCommand
	 */
	@SuppressWarnings("unchecked")
	private void initializeForm(CRUDCommand crudCommand) {
		if (getEntityClass() != null) {
			Object bean = BeanUtils.instantiateClass(getEntityClass());
			crudCommand.setContent((Entity) bean);
		}

	}

	@SuppressWarnings("unchecked")
	protected Class<T> getEntityClass() {
		Type genericSuperclass = getGenericSuperclass(getClass());
		return (Class<T>) ((ParameterizedType) genericSuperclass)
				.getActualTypeArguments()[0];
	}

	@SuppressWarnings("unchecked")
	private Type getGenericSuperclass(Class clazz) {
		Type genericSuperclass = clazz.getGenericSuperclass();
		if (genericSuperclass instanceof ParameterizedType) {
			return genericSuperclass;
		}
		return getGenericSuperclass(clazz.getSuperclass());
	}

	/**
	 * Devuelve el nombre del método a ejecutar.
	 * 
	 * @param request
	 * @return
	 * @throws NoSuchRequestHandlingMethodException
	 */
	protected String getMethod(HttpServletRequest request) {
		String method = null;
		try {
			method = this.methodNameResolver.getHandlerMethodName(request);
		} catch (NoSuchRequestHandlingMethodException e) {
			StringBuffer sb = new StringBuffer();
			sb.append("Method ").append(method).append(" not supported in ")
					.append(this.getClass().getSimpleName());

			throw new UnsupportedOperationException(sb.toString());
		}

		return method;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void onBindOnNewForm(HttpServletRequest request, Object command)
			throws Exception {
		onBind(request, command);

		CRUDCommand form = castCommand(command);

		if (CRUDCommand.RETRIEVE_METHOD.equals(form.getRequested())) {
			String id = request.getParameter(CRUDCommand.ID);

			if (StringUtils.isEmpty(id.trim())) {
				throw new ServletException(
						"Intentando recuperar entidad con Id nulo. Verifique que el parametro '"
								+ CRUDCommand.ID + "'  existe");
			}

			if (logger.isDebugEnabled()) {
				logger.debug("Recuperando entidad " + id + ", utilizando "
						+ "CRUDController");

			}

			// Asignar la siguiente operacion...
			form.setMethod(CRUDCommand.UPDATE_METHOD);

			Entity entity = delegate.retrieve(id);

			if (entity != null) {
				form.setContent(entity);
			}

		} else if (CRUDCommand.NEW_METHOD.equals(form.getRequested())) {
			if (logger.isDebugEnabled()) {
				logger
						.debug("Recibida solicitud para trabajar con una nueva entidad");
			}

			// Asignar la siguiente operacion...
			form.setMethod(CRUDCommand.CREATE_METHOD);

		} else if (CRUDCommand.DELETE_METHOD.equals(form.getRequested())) {
			String id = request.getParameter(CRUDCommand.ID);

			if (StringUtils.isEmpty(id)) {
				throw new ServletException(
						"Intentando eliminar una entidad con Id nulo. Verifique que el parametro '"
								+ CRUDCommand.ID + "'  existe");
			}

			form.getContent().setId(id);
			form.setMethod(CRUDCommand.DELETE_METHOD);
		} else {
			logger.warn("Metodo " + form.getRequested()
					+ " no soportado para formularios CRUD.");
		}

	}

	@Override
	protected boolean suppressValidation(HttpServletRequest request,
			Object command) {
		return this.suppressValidation(request);
	}

	@Override
	protected boolean suppressValidation(HttpServletRequest request) {
		boolean supress = super.suppressValidation(request);

		if (supress) {
			return supress;
		}

		if (CRUDCommand.DELETE_METHOD.equals(getMethod(request))) {
			return true;
		}

		return false;
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @param command
	 * @param errors
	 * @return
	 */
	public ModelAndView create(T entity) {
		T create = getDelegate().create(entity);

		ModelAndView mav = new ModelAndView(
				getSuccessView(CRUDCommand.CREATE_METHOD));

		onCreateSuccess(create, mav);

		String createStatusMessage = getCreateStatusMessage(entity);
		if (!StringUtils.isEmpty(createStatusMessage)) {
			mav.addObject(FLASH_SCOPE_STATUS_MESSAGE, createStatusMessage);
		}

		return mav;
	}

	/**
	 * 
	 * @param create
	 * @param mav
	 */
	protected void onCreateSuccess(T create, ModelAndView mav) {
		mav.addObject(CONTENT_ID, create.getId());
	}

	/**
	 * 
	 * @param entity
	 * @return
	 */
	protected String getCreateStatusMessage(T entity) {
		return getMessageSourceAccessor().getMessage(
				entity.getClass().getName() + ".create.success",
				new String[] {},
				"La entidad con identificador " + entity.getId()
						+ " se ha creado con éxito");
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @param command
	 * @param errors
	 * @return
	 */
	public ModelAndView retrieve(T entity) {

		T retrieve = getDelegate().retrieve(((Entity) entity).getId());

		ModelAndView mav = new ModelAndView(getFormView());

		onRetrieveSuccess(retrieve, mav);

		String retrieveStatusMessage = getRetrieveStatusMessage(entity);
		if (!StringUtils.isEmpty(retrieveStatusMessage)) {
			mav.addObject(FLASH_SCOPE_STATUS_MESSAGE, retrieveStatusMessage);
		}

		return mav;
	}

	/**
	 * 
	 * @param retrieve
	 * @param mav
	 */
	protected void onRetrieveSuccess(T retrieve, ModelAndView mav) {
		mav.addObject(CONTENT_ID, retrieve.getId());
	}

	/**
	 * 
	 * @param entity
	 * @return
	 */
	protected String getRetrieveStatusMessage(T entity) {
		return getMessageSourceAccessor().getMessage(
				entity.getClass() + ".retrieve.success",
				new String[] {},
				"La entidad con identificador " + entity.getId()
						+ " se ha recuperado con éxito");
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @param command
	 * @param errors
	 * @return
	 */
	public ModelAndView update(T entity) {
		T update = getDelegate().update(entity);

		ModelAndView mav = new ModelAndView(
				getSuccessView(CRUDCommand.UPDATE_METHOD));

		onUpdateSuccess(update, mav);

		String updateStatusMessage = getUpdateStatusMessage(entity);
		if (!StringUtils.isEmpty(updateStatusMessage)) {
			mav.addObject(FLASH_SCOPE_STATUS_MESSAGE, updateStatusMessage);
		}

		return mav;
	}

	/**
	 * Método que se ejecuta ante la actualización exitosa de la entidad
	 * <code>T</code>. Se permite acceso a la instancia <code>mav</code> de tipo
	 * <code>ModelAndView</code> para poder exponer
	 * 
	 * @param update
	 * @param mav
	 */
	protected void onUpdateSuccess(T update, ModelAndView mav) {
		mav.addObject(CONTENT_ID, update.getId());
	}

	/**
	 * Devuelve el mensaje de éxito asociado a la actualización de la entidad
	 * <code>entity</code>
	 * 
	 * @param entity
	 * @return
	 */
	protected String getUpdateStatusMessage(T entity) {
		return getMessageSourceAccessor().getMessage(
				entity.getClass().getName() + ".update.success",
				new String[] {},
				"La entidad con identificador " + entity.getId()
						+ " se ha actualizado con éxito");
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @param command
	 * @param errors
	 * @return
	 */
	public ModelAndView delete(T entity) {

		getDelegate().delete(((Entity) entity).getId());

		ModelAndView mav = new ModelAndView(
				getSuccessView(CRUDCommand.DELETE_METHOD));

		onDeleteSuccess(entity, mav);

		String deleteStatusMessage = getDeleteStatusMessage(entity);
		if (!StringUtils.isEmpty(deleteStatusMessage)) {
			mav.addObject(FLASH_SCOPE_STATUS_MESSAGE, deleteStatusMessage);
		}

		return mav;
	}

	/**
	 * 
	 * @param entity
	 * @param mav
	 */
	protected void onDeleteSuccess(T entity, ModelAndView mav) {

	}

	/**
	 * 
	 * @param entity
	 * @return
	 */
	protected String getDeleteStatusMessage(T entity) {
		return getMessageSourceAccessor().getMessage(
				entity.getClass() + ".delete.success",
				new String[] {},
				"La entidad con identificador " + entity.getId()
						+ " se ha eliminado con éxito");
	}

	@SuppressWarnings("unchecked")
	@Override
	protected ModelAndView onSubmit(Object command) throws Exception {

		CRUDCommand<Entity> crudCommand = (CRUDCommand<Entity>) command;
		Entity content = crudCommand.getContent();

		Method m = (Method) this.getClass().getMethod(
				crudCommand.getRequested(), new Class[] { Entity.class });
		if (m == null) {
			throw new NoSuchRequestHandlingMethodException(crudCommand
					.getRequested(), getClass());
		}

		return (ModelAndView) m.invoke(this, new Object[] { content });
	}

	@SuppressWarnings("unchecked")
	@Override
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {

		String method = getMethod(request);

		try {
			return super.onSubmit(request, response, command, errors);
		} catch (InvocationTargetException ite) {
			if (ite.getTargetException() instanceof ManageableException) {
				errors.addError(ErrorUtils.getObjectError((Exception) ite
						.getTargetException()));
				Map<String, Object> model = new HashMap<String, Object>();
				model.putAll(referenceData(request, command, errors));
				model.putAll(errors.getModel());

				return new ModelAndView(getFailureView(method), model);
			} else {
				ReflectionUtils.rethrowException(ite.getTargetException());
			}
		} catch (Exception ex) {
			errors.addError(ErrorUtils.getObjectError(ex));
			Map<String, Object> model = new HashMap<String, Object>();
			model.putAll(referenceData(request, command, errors));
			model.putAll(errors.getModel());

			return new ModelAndView(getFailureView(method), model);
		}

		return null;
	}

	@Override
	protected boolean isFormSubmission(HttpServletRequest request) {

		String method = getMethod(request);

		if (CRUDCommand.NEW_METHOD.equals(method)) {
			return false;
		}
		if (CRUDCommand.RETRIEVE_METHOD.equals(method)) {
			return false;
		}

		return true;
	}

	public ModelAndView processFormSubmission(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		if (WebUtils.hasSubmitParameter(request, CANCEL)) {
			return new ModelAndView(getCancelView());
		}

		return super.processFormSubmission(request, response, command, errors);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected ModelAndView showForm(HttpServletRequest request,
			HttpServletResponse response, BindException errors, Map controlModel)
			throws Exception {

		final String method = getMethod(request);

		if (errors.hasErrors()) {
			Map<String, Object> model = errors.getModel();
			if (null != controlModel) {
				controlModel.putAll(model);
			} else {
				controlModel = model;
			}

			return showForm(request, errors, getFailureView(method),
					controlModel);
		} else {
			return super.showForm(request, response, errors, controlModel);
		}
	}

	/**
	 * Set up a custom property editor for converting form inputs to real
	 * objects
	 */
	protected void initBinder(HttpServletRequest request,
			ServletRequestDataBinder binder) {
		binder.registerCustomEditor(Integer.class, null,
				new CustomNumberEditor(Integer.class, null, true));
		binder.registerCustomEditor(Long.class, null, new CustomNumberEditor(
				Long.class, null, true));
		binder.registerCustomEditor(byte[].class,
				new ByteArrayMultipartFileEditor());
		SimpleDateFormat dateFormat = new SimpleDateFormat(MessagesService
				.getInstance().getMessage("date.format"));
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, null, new CustomDateEditor(
				dateFormat, true));
	}

	@SuppressWarnings("unchecked")
	@Override
	protected final Map referenceData(HttpServletRequest request)
			throws Exception {

		Map referenceData = super.referenceData(request);

		if (MapUtils.isEmpty(referenceData)) {
			referenceData = new HashMap<String, Object>();
		}

		doReferenceData(request, referenceData);

		return referenceData;
	}

	/**
	 * Permite exponer objetos en el modelo a través de
	 * <code>referenceData</code>.
	 * 
	 * @param request
	 *            petición HTTP en curso
	 * @param referenceData
	 *            mapa en el que almacenar los objetos a exponer en el modelo
	 */
	protected void doReferenceData(HttpServletRequest request,
			Map<String, Object> referenceData) {
	}

	public InternalPathMethodNameResolver getMethodNameResolver() {
		return methodNameResolver;
	}

	public CRUDDelegate<T> getDelegate() {
		return delegate;
	}

	public void setDelegate(CRUDDelegate<T> delegate) {
		this.delegate = delegate;
	}

	/**
	 * Devuelve la vista de éxito asociada al método <code>method</code>. En
	 * caso de no tener una vista específica asociada se devuelve la vista de
	 * éxito por defecto (<code>successView</code>).
	 * 
	 * @param method
	 * @return
	 */
	public String getSuccessView(String method) {
		String view = getSuccessViews().get(method);
		if (!StringUtils.isEmpty(view)) {
			return view;
		} else {
			return getSuccessView();
		}
	}

	/**
	 * 
	 * @param method
	 * @return
	 */
	public String getFailureView(String method) {
		String view = getFailureViews().get(method);
		if (!StringUtils.isEmpty(view)) {
			return view;
		} else {
			return getFormView();
		}
	}

	public Map<String, String> getSuccessViews() {
		return successViews;
	}

	public void setSuccessViews(Map<String, String> successViews) {
		this.successViews = successViews;
	}

	public String getCancelView() {
		if (StringUtils.isEmpty(this.cancelView)) {
			return getSuccessView();
		}

		return cancelView;
	}

	public void setCancelView(String cancelView) {
		this.cancelView = cancelView;
	}

	public Map<String, String> getFailureViews() {
		return failureViews;
	}

	public void setFailureViews(Map<String, String> failureViews) {
		this.failureViews = failureViews;
	}

	// Members
	protected static final String FLASH_SCOPE_STATUS_MESSAGE = FlashScopeInterceptor.DEFAULT_ATTRIBUTE_NAME
			+ ".statusMessage";

	protected static final String CONTENT_ID = "content.id";

	protected static final String CANCEL = "cancel";

	protected String FORM_NAME = "form";

	protected CRUDDelegate<T> delegate;

	protected String cancelView;

	// Mapa que contiene las diferentes succesViews del controller
	protected Map<String, String> successViews = new HashMap<String, String>();

	// Mapa que contiene las diferentes vistas de error del controller
	protected Map<String, String> failureViews = new HashMap<String, String>();

	// default name resolver (based on method request parameter)
	private InternalPathMethodNameResolver methodNameResolver = new InternalPathMethodNameResolver();
}
