package fondos.actions;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import se.usuarios.ServiceClient;
import transferencias.TransferenciasConstants;
import transferencias.vos.RangoVO;
import util.ErrorsTag;

import common.Constants;
import common.Messages;
import common.actions.ActionRedirect;
import common.actions.BaseAction;
import common.bi.GestionCuadroClasificacionBI;
import common.bi.GestionFraccionSerieBI;
import common.bi.ServiceRepository;
import common.exceptions.ActionNotAllowedException;
import common.navigation.KeysClientsInvocations;
import common.util.DateUtils;
import common.util.StringUtils;

import fondos.FondosConstants;
import fondos.forms.UDocEnDivisionFSForm;
import fondos.model.ElementoCuadroClasificacion;
import fondos.model.EstadoDivisionFS;
import fondos.vos.UDocEnFraccionSerieVO;

/**
 * Action que lleva a cabo las diferentes acciones que pueden ser realizadas
 * sobre las unidades documentales que forman parte de una fracción de serie
 * 
 */
public class GestionUnidadesDocumentalesEnFS extends BaseAction {

	static Logger logger = Logger
			.getLogger(GestionUnidadesDocumentalesEnFS.class);

	/**
	 * Logica ejecutada cuan el action es invocado con parametro method con
	 * valor 'addUdocs'. Si la relacion de entrega con la que se esta trabajando
	 * es ordinaria se presenta una lista con los expedientes que el usuario
	 * puede elegir para incorporar como unidades documentales de dicha
	 * relacion. En caso de que la relacion sea extraordinaria se presenta la
	 * usuario un formulario para que los datos de la unidad documental sean
	 * introducidos de forma manual
	 */
	public void addUdocsExecuteLogic(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		DivisionFraccionSeriePO divisionFSVO = (DivisionFraccionSeriePO) getFromTemporalSession(
				request, FondosConstants.DIVISION_FRACCION_SERIE);

		if (divisionFSVO.getNivelDocumental().getSubtipo() == ElementoCuadroClasificacion.SUBTIPO_CAJA)
			saveCurrentInvocation(
					KeysClientsInvocations.CUADRO_NUEVA_FS_EN_DIVISIONFS,
					request);
		else
			saveCurrentInvocation(
					KeysClientsInvocations.CUADRO_NUEVA_UDOC_EN_DIVISIONFS,
					request);

		setInTemporalSession(request, FondosConstants.BANDERA_CREACION_EDICION,
				FondosConstants.VALOR_BANDERA_CREACION);

		setReturnActionFordward(request,
				mappings.findForward("edicion_udocEnFS"));
	}

	/** Posiblemente necesaria en adelante **/
	public void nuevaUnidadDocumentalExecuteLogic(ActionMapping mappings,
			HttpServletRequest request, int subtipo, String idFicha) {

		if (subtipo == ElementoCuadroClasificacion.SUBTIPO_CAJA)
			saveCurrentInvocation(
					KeysClientsInvocations.CUADRO_NUEVA_FS_EN_DIVISIONFS,
					request);
		else
			saveCurrentInvocation(
					KeysClientsInvocations.CUADRO_NUEVA_UDOC_EN_DIVISIONFS,
					request);

		setReturnActionFordward(request,
				mappings.findForward("edicion_udocEnFS"));
	}

	public void eliminarUdocsExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		DivisionFraccionSeriePO divisionFSPO = (DivisionFraccionSeriePO) getFromTemporalSession(
				request, FondosConstants.DIVISION_FRACCION_SERIE);

		boolean tieneDescripcion = StringUtils.isNotEmpty(divisionFSPO
				.getIdFicha()) ? true : false;

		String[] udocsToRemove = ((UDocEnDivisionFSForm) form)
				.getSelectedUdoc();
		// try {
		if (udocsToRemove != null) {
			ServiceRepository services = ServiceRepository
					.getInstance(ServiceClient.create(getAppUser(request)));

			GestionFraccionSerieBI fraccionSerieBI = services
					.lookupGestionFraccionSerieBI();

			fraccionSerieBI.deleteUDocsEnDivisionFS(udocsToRemove,
					tieneDescripcion);
		}

		goLastClientExecuteLogic(mappings, form, request, response);

		// TODO: Revisar si es necesario añadir la lógica de
		// permitidoEliminarUDocs, permitidoModificarFS ....
		/*
		 * } catch (ActionNotAllowedException anae) { guardarError(request,
		 * anae); goLastClientExecuteLogic(mappings, form, request, response);
		 * //setReturnActionFordward(request,
		 * mappings.findForward("info_udoc_relacion")); }
		 */
	}

	public List obtenerRangos(String[] rangosIniciales, String[] rangosFinales) {
		List rangos = new ArrayList();
		// String numExpRangos = null;

		// Reseteamos los rangos introducidos anteriormente y los volvemos a
		// rellenar
		// udoc.resetRangos();
		if (rangosIniciales != null && rangosFinales != null) {
			for (int i = 0; i < rangosIniciales.length; i++) {
				// numExpRangos = (numExpRangos == null ? "" : numExpRangos +
				// Constants.DELIMITADOR_RANGOS)
				// + rangosIniciales[i] +
				// Constants.DELIMITADOR_RANGO_INICIAL_FINAL + rangosFinales[i];
				// udoc.addRango(new
				// RangoVO(rangosIniciales[i],rangosFinales[i]));
				rangos.add(new RangoVO(rangosIniciales[i], rangosFinales[i]));
			}
			// udoc.setNumeroExpediente(numExpRangos);
		}

		return rangos;
	}

	private int getNextOrden(List udocsEnDivisionFS) {
		int orden = 1;
		if (udocsEnDivisionFS != null) {
			Iterator it = udocsEnDivisionFS.iterator();
			while (it.hasNext()) {
				UDocEnFraccionSerieVO udocEnFS = (UDocEnFraccionSerieVO) it
						.next();
				if (udocEnFS.getOrden() > orden)
					orden = udocEnFS.getOrden();
			}
		}
		return orden;
	}

	/**
	 * Crea una unidad documental a partir de la información introducida de
	 * forma manual por un usuario durante la elaboracion de una división de
	 * fracción de serie
	 */
	public void crearUnidadDocumentalEnFSExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		ActionRedirect forward;
		List rangos = null;
		ActionErrors errores = null;
		boolean isSubtipoCaja = false;

		UDocEnDivisionFSForm udocForm = (UDocEnDivisionFSForm) form;
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		GestionFraccionSerieBI fraccionSerieBI = services
				.lookupGestionFraccionSerieBI();

		DivisionFraccionSeriePO divisionFSPO = (DivisionFraccionSeriePO) getFromTemporalSession(
				request, FondosConstants.DIVISION_FRACCION_SERIE);

		List udocsEnDivisionFS = (List) getFromTemporalSession(request,
				FondosConstants.UDOCS_FRACCION_SERIE);

		// Movido aquí el punto de creación de la Unidad Documental para
		// permitir mantener los rangos de fracción de serie
		// String mantenerInfo=(String)getFromTemporalSession(request,
		// TransferenciasConstants.FLAG_CREACION_UDOC_REL_MANTENER_INFORMACION);
		UDocEnFraccionSerieVO uDocEnFS = null;
		// if(mantenerInfo!=null && Integer.parseInt(mantenerInfo)==1)
		// udoc=(UnidadDocumentalVO)getFromTemporalSession(request,
		// TransferenciasConstants.UNIDAD_DOCUMENTAL);
		if (uDocEnFS == null)
			uDocEnFS = new UDocEnFraccionSerieVO();

		// Aquí ya está creado el objeto UDocEnFraccionSerieVO, sea nuevo o a
		// editar
		int subtipo = (divisionFSPO.getNivelDocumental().getId() != null ? divisionFSPO
				.getNivelDocumental().getSubtipo()
				: ElementoCuadroClasificacion.SUBTIPO_UNIDAD_DOCUMENTAL_SIMPLE);
		isSubtipoCaja = (subtipo == ElementoCuadroClasificacion.SUBTIPO_CAJA);

		if (divisionFSPO.getNivelDocumental() != null && isSubtipoCaja) {

			// Limpiamos los rangos
			// removeInTemporalSession(request,
			// FondosConstants.LISTA_RANGOS_UDOC);

			// Una vez se ejecuta esto, se rellenan los nuevos rangos en la
			// unidad documental
			rangos = obtenerRangos(request.getParameterValues("campo_201"),
					request.getParameterValues("campo_202"));
			// String numExpRangos = null;
			if (rangos != null && rangos.size() > 0) {
				uDocEnFS.resetRangos();
				Iterator it = rangos.iterator();
				while (it.hasNext()) {
					RangoVO rango = (RangoVO) it.next();
					// numExpRangos = (numExpRangos == null ? "" : numExpRangos
					// + Constants.DELIMITADOR_RANGOS)
					// + rango.getDesde() +
					// Constants.DELIMITADOR_RANGO_INICIAL_FINAL +
					// rango.getHasta();
					uDocEnFS.addRango(rango);
				}
				// uDocEnFS.setNumExp(numExpRangos);
			}
		} else {
			uDocEnFS.setNumExp(udocForm.getNumeroExpediente());
		}

		if (isSubtipoCaja)
			errores = validate(rangos);

		errores = validate(request, udocForm, errores);

		if (errores.size() > 0) {
			// Aquí almacenamos los rangos independientemente en caso de error
			// en introducción de datos
			if (isSubtipoCaja)
				setInTemporalSession(request,
						FondosConstants.LISTA_RANGOS_UDOC, rangos);
			ErrorsTag.saveErrors(request, errores);
			setReturnActionFordward(request,
					mappings.findForward("edicion_udocEnFS"));
			// nuevaUnidadDocumentalExecuteLogic(mappings, request, subtipo,
			// divisionFSPO.getIdFicha());
		} else {

			// uDocEnFS.setOrganoProductor(divisionFSPO.getProductor());
			/*
			 * OrganoProductorVO organoProductorVO =
			 * getGestionRelacionesBI(request
			 * ).getOrganoProductor(udocForm.getIdProductor()); CAOrganoVO
			 * organo = null;
			 * 
			 * if (organoProductorVO != null) organo =
			 * getGestionControlUsuarios(
			 * request).getCAOrgProductorVOXId(organoProductorVO.getIdOrgano());
			 * 
			 * if (organo != null) udoc.setProductor(udocForm.getIdProductor(),
			 * udocForm.getNombreProductor(), organo.getCodigo()); else
			 * udoc.setProductor(udocForm.getIdProductor(),
			 * udocForm.getNombreProductor(), null);
			 */
			try {
				uDocEnFS.setFechaExtIni(udocForm.getFechaInicioAsDate());
				uDocEnFS.setFechaExtFin(udocForm.getFechaFinAsDate());
				// NO DEBERIA DARSE PUESTO QUE SE HA HECHO VALIDACION PREVIA
			} catch (ParseException pe) {
			}

			uDocEnFS.setAsunto(udocForm.getAsunto());
			uDocEnFS.setIdFS(divisionFSPO.getIdFS());

			// udoc.formato = relacionEntrega.getIdformatoui();
			// try {

			uDocEnFS.setOrden(getNextOrden(udocsEnDivisionFS) + 1);

			// Crear unidad documental en división de fracción de serie
			uDocEnFS = fraccionSerieBI.addUDocToDivisionFS(uDocEnFS,
					divisionFSPO, false);

			if (udocsEnDivisionFS == null)
				udocsEnDivisionFS = new ArrayList();

			// Rellenar los datos de la unidad documental en división de
			// fracción de serie que no vienen en su tabla
			uDocEnFS.setIdFichaDescr(divisionFSPO.getIdFicha());
			uDocEnFS.setIdNivelDocumental(divisionFSPO.getIdNivelDocumental());

			udocsEnDivisionFS.add(uDocEnFS);

			// Transformamos la lista de VOs a lista de POs
			CollectionUtils.transform(udocsEnDivisionFS,
					UDocEnFraccionSerieToPO.getInstance(services));

			setInTemporalSession(request, FondosConstants.UDOCS_FRACCION_SERIE,
					udocsEnDivisionFS);

			// Extraemos la última invocación de la pila para poder volver a la
			// pantalla de la división de fracción de serie
			popLastInvocation(request);
			// se fuerza la recarga de la unidad documental y la introduccion de
			// la invocacion en la pila de llamadas
			// removeInTemporalSession(request,
			// TransferenciasConstants.UNIDAD_DOCUMENTAL);

			if (StringUtils.isEmpty(divisionFSPO.getIdFicha())) {
				forward = new ActionRedirect(
						mappings.findForward("dividir_fraccionSerie"));
				/*
				 * forward = new
				 * ActionRedirect(mappings.findForward("redirect_to_view_udocEnFS"
				 * )); forward.setRedirect(true); forward.addParameter("udocID",
				 * uDocEnFS.getIdUDoc());
				 */
			} else {
				// CASO ESPECIAL: guardar en sesion la unidad documental
				setInTemporalSession(request,
						FondosConstants.UNIDAD_DOCUMENTAL_EN_FS,
						(UDocEnFraccionSeriePO) (new UDocEnFraccionSerieToPO(
								services).transform(uDocEnFS)));

				forward = new ActionRedirect(
						mappings.findForward("edicion_udocEnFS_con_ficha"));
				forward.setRedirect(true);
				forward.addParameter(Constants.ID_FICHA,
						divisionFSPO.getIdFicha());
				forward.addParameter(Constants.ID, uDocEnFS.getIdUDoc());
				if (divisionFSPO.getNivelDocumental() != null
						&& subtipo == ElementoCuadroClasificacion.SUBTIPO_CAJA)
					forward.addParameter(Constants.CURRENT_INVOCATION,
							Constants.FSFS);
				else
					forward.addParameter(Constants.CURRENT_INVOCATION,
							Constants.UDOCFS);
			}

			setReturnActionFordward(request, forward);

		}
	}

	public ActionErrors validate(List rangos) {

		ActionErrors errors = new ActionErrors();

		// Validar la introducción de rangos de expedientes
		if (rangos != null && rangos.size() > 0) {
			Iterator it = rangos.iterator();
			while (it.hasNext()) {
				RangoVO rango = (RangoVO) it.next();
				if (GenericValidator.isBlankOrNull(rango.getDesde())) {
					errors.add(
							TransferenciasConstants.NECESARIO_INTRODUCIR_RANGO_INICIAL,
							new ActionError(
									TransferenciasConstants.NECESARIO_INTRODUCIR_RANGO_INICIAL));
					break;
				}
				if (GenericValidator.isBlankOrNull(rango.getHasta())) {
					errors.add(
							TransferenciasConstants.NECESARIO_INTRODUCIR_RANGO_FINAL,
							new ActionError(
									TransferenciasConstants.NECESARIO_INTRODUCIR_RANGO_FINAL));
					break;
				}
			}
		}

		return errors;
	}

	/**
	 * Comprobacion de que el usuario ha proporcionado la informacion de unidad
	 * documental de manera adecuada
	 * 
	 */
	protected ActionErrors validate(HttpServletRequest request,
			UDocEnDivisionFSForm form, ActionErrors errores) {

		if (errores == null)
			errores = new ActionErrors();

		if (GenericValidator.isBlankOrNull(form.getAsunto()))
			errores.add(
					Constants.ERROR_REQUIRED,
					new ActionError(Constants.ERROR_REQUIRED, Messages
							.getString(Constants.ETIQUETA_ASUNTO,
									request.getLocale())));

		Date fechaInicio = null;
		try {
			fechaInicio = form.getFechaInicioAsDate();
			if (fechaInicio == null) {
				errores.add(
						Constants.ERROR_REQUIRED,
						new ActionError(Constants.ERROR_REQUIRED, Messages
								.getString(Constants.ETIQUETA_FECHA_INICIO,
										request.getLocale())));
			} else {
				if (!DateUtils.isDate(form.getFechaInicio())) {
					errores.add(
							Constants.ERROR_DATE,
							new ActionError(Constants.ERROR_DATE, Messages
									.getString(Constants.ETIQUETA_FECHA_INICIO,
											request.getLocale())));
				}
			}
		} catch (Exception e) {
			errores.add(
					Constants.ERROR_DATE,
					new ActionError(Constants.ERROR_DATE, Messages.getString(
							Constants.ETIQUETA_FECHA_INICIO,
							request.getLocale())));
		}
		Date fechaFin = null;
		try {
			fechaFin = form.getFechaFinAsDate();
			if (fechaFin == null) {
				errores.add(
						Constants.ERROR_REQUIRED,
						new ActionError(Constants.ERROR_REQUIRED, Messages
								.getString(Constants.ETIQUETA_FECHA_FIN,
										request.getLocale())));
			} else {
				if (!DateUtils.isDate(form.getFechaFin())) {
					errores.add(
							Constants.ERROR_DATE,
							new ActionError(Constants.ERROR_DATE, Messages
									.getString(Constants.ETIQUETA_FECHA_FIN,
											request.getLocale())));
				}
			}
		} catch (Exception e) {
			errores.add(
					Constants.ERROR_DATE,
					new ActionError(Constants.ERROR_DATE, Messages.getString(
							Constants.ETIQUETA_FECHA_FIN, request.getLocale())));
		}

		if (fechaFin != null) {
			if (fechaFin.compareTo(new Date()) > 0)
				errores.add(
						Constants.ERROR_DATE_AFTER_TODAY,
						new ActionError(Constants.ERROR_DATE_AFTER_TODAY,
								Messages.getString(
										Constants.ETIQUETA_FECHA_FIN,
										request.getLocale())));

			if (fechaInicio != null && fechaInicio.compareTo(fechaFin) > 0)
				errores.add(Constants.ERROR_INITDATE_AFTER_ENDDATE,
						new ActionError(Constants.ERROR_INITDATE_AFTER_ENDDATE));
		}

		return errores;
	}

	public void infoUnidadDocumentalExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		UDocEnDivisionFSForm frm = (UDocEnDivisionFSForm) form;

		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		DivisionFraccionSeriePO divisionFSVO = (DivisionFraccionSeriePO) getFromTemporalSession(
				request, FondosConstants.DIVISION_FRACCION_SERIE);
		GestionFraccionSerieBI fraccionSerieBI = services
				.lookupGestionFraccionSerieBI();
		// ActionForward forward = null;
		ActionRedirect redirect = null;
		int subtipo = ElementoCuadroClasificacion.SUBTIPO_UNIDAD_DOCUMENTAL_SIMPLE;
		// boolean tieneDescripcion =
		// StringUtils.isEmpty(divisionFSVO.getIdFicha()) ? false : true;

		String idUDoc = request.getParameter("udocID");
		UDocEnFraccionSerieVO udocEnFS = fraccionSerieBI
				.getUDocEnDivisionFSConInfoDesc(idUDoc);
		UDocEnFraccionSeriePO udocEnFSPO = (UDocEnFraccionSeriePO) (new UDocEnFraccionSerieToPO(
				services).transform(udocEnFS));
		setInTemporalSession(request, FondosConstants.UNIDAD_DOCUMENTAL_EN_FS,
				udocEnFSPO);

		// Eliminar de sesión información sobrante
		// removeInTemporalSession(request, FondosConstants.LISTA_RANGOS_UDOC);

		if (StringUtils.isNotEmpty(divisionFSVO.getIdNivelDocumental())) {
			GestionCuadroClasificacionBI cuadroBI = services
					.lookupGestionCuadroClasificacionBI();
			if (cuadroBI.isSubtipoCaja(divisionFSVO.getIdNivelDocumental()))
				subtipo = ElementoCuadroClasificacion.SUBTIPO_CAJA;
		}

		// Si es una división de fracción de serie con ficha
		if (!StringUtils.isEmpty(divisionFSVO.getIdFicha())) {
			if (divisionFSVO.getEstado() == EstadoDivisionFS.ABIERTA)
				redirect = new ActionRedirect(
						mappings.findForward("edicion_udocEnFS_con_ficha"));
			else
				redirect = new ActionRedirect(
						mappings.findForward("ver_udocEnFS_con_ficha"));
			redirect.setRedirect(true);
			redirect.addParameter(Constants.ID_FICHA, divisionFSVO.getIdFicha());
			redirect.addParameter(Constants.ID, idUDoc);
			if (subtipo == ElementoCuadroClasificacion.SUBTIPO_CAJA)
				redirect.addParameter(Constants.CURRENT_INVOCATION,
						Constants.FSFS);
			else
				redirect.addParameter(Constants.CURRENT_INVOCATION,
						Constants.UDOCFS);
		} else {
			// Si la división de fracción de serie está abierta, se permite
			// modificar sus unidades documentales, sino, se muestra su
			// información
			// en modo solo lectura
			if (divisionFSVO.getEstado() == EstadoDivisionFS.ABIERTA) {
				frm.populateForEdition(udocEnFS);

				setInTemporalSession(request,
						FondosConstants.BANDERA_CREACION_EDICION,
						FondosConstants.VALOR_BANDERA_EDICION);

				if (divisionFSVO.getNivelDocumental().getSubtipo() == ElementoCuadroClasificacion.SUBTIPO_CAJA)
					saveCurrentInvocation(
							KeysClientsInvocations.CUADRO_VER_FS_FRACCION_SERIE,
							request);
				else
					saveCurrentInvocation(
							KeysClientsInvocations.CUADRO_VER_UDOC_FRACCION_SERIE,
							request);

				redirect = new ActionRedirect(
						mappings.findForward("edicion_udocEnFS"));
			} else {
				saveCurrentInvocation(
						KeysClientsInvocations.CUADRO_DATOS_UDOC_EN_DIVISIONFS,
						request);
				redirect = new ActionRedirect(
						mappings.findForward("ver_udocEnFS"));
			}
		}

		setReturnActionFordward(request, redirect);

	}

	/**
	 * Guarda las modificaciones realizadas sobre la informacion de cabecera de
	 * una unidad documental tras haber realizado una edicion
	 */
	public void guardarUnidadDocumentalEnFSExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		List rangos = null;
		ActionErrors errores = null;
		boolean isSubtipoCaja = false;

		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		GestionFraccionSerieBI fraccionSerieBI = services
				.lookupGestionFraccionSerieBI();

		UDocEnDivisionFSForm udocForm = (UDocEnDivisionFSForm) form;
		UDocEnFraccionSerieVO udocEnDivisionFS = (UDocEnFraccionSerieVO) getFromTemporalSession(
				request, FondosConstants.UNIDAD_DOCUMENTAL_EN_FS);
		DivisionFraccionSeriePO divisionFS = (DivisionFraccionSeriePO) getFromTemporalSession(
				request, FondosConstants.DIVISION_FRACCION_SERIE);

		// Aquí ya está creado el objeto UDocEnFraccionSerieVO, sea nuevo o a
		// editar
		int subtipo = (divisionFS.getNivelDocumental().getId() != null ? divisionFS
				.getNivelDocumental().getSubtipo()
				: ElementoCuadroClasificacion.SUBTIPO_UNIDAD_DOCUMENTAL_SIMPLE);
		isSubtipoCaja = (subtipo == ElementoCuadroClasificacion.SUBTIPO_CAJA);

		if (divisionFS.getNivelDocumental() != null && isSubtipoCaja) {
			// Una vez se ejecuta esto, se rellenan los nuevos rangos en la
			// unidad documental
			rangos = obtenerRangos(request.getParameterValues("campo_201"),
					request.getParameterValues("campo_202"));
			// String numExpRangos = null;
			if (rangos != null && rangos.size() > 0) {
				udocEnDivisionFS.resetRangos();
				Iterator it = rangos.iterator();
				while (it.hasNext()) {
					RangoVO rango = (RangoVO) it.next();
					// numExpRangos = (numExpRangos == null ? "" : numExpRangos
					// + Constants.DELIMITADOR_RANGOS)
					// + rango.getDesde() +
					// Constants.DELIMITADOR_RANGO_INICIAL_FINAL +
					// rango.getHasta();
					udocEnDivisionFS.addRango(rango);
				}
			}
		} else {
			udocEnDivisionFS.setNumExp(udocForm.getNumeroExpediente());
		}

		if (isSubtipoCaja)
			errores = validate(rangos);

		errores = validate(request, udocForm, errores);

		if (errores.isEmpty()) {

			udocEnDivisionFS.setAsunto(udocForm.getAsunto());
			try {
				udocEnDivisionFS
						.setFechaExtIni(udocForm.getFechaInicioAsDate());
				udocEnDivisionFS.setFechaExtFin(udocForm.getFechaFinAsDate());
			} catch (ParseException pe) {
			}

			// boolean tieneDescripcion =
			// StringUtils.isEmpty(divisionFS.getIdFicha()) ? false : true;
			fraccionSerieBI.updateUDocEnDivisionFS(udocEnDivisionFS);

			goBackExecuteLogic(mappings, form, request, response);
		} else {
			// Aquí almacenamos los rangos independientemente en caso de error
			// en introducción de datos
			if (isSubtipoCaja)
				setInTemporalSession(request,
						FondosConstants.LISTA_RANGOS_UDOC, rangos);
			ErrorsTag.saveErrors(request, errores);
			setReturnActionFordward(request,
					mappings.findForward("edicion_udocEnFS"));
		}

	}

	public void guardarCambiosYNuevaConFichaExecuteLogic(
			ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		// ServiceRepository services =
		// ServiceRepository.getInstance(ServiceClient.create(getAppUser(request)));
		// GestionRelacionesEntregaBI relacionesBI =
		// services.lookupGestionRelacionesBI();
		UDocEnDivisionFSForm formulario = (UDocEnDivisionFSForm) form;
		// UnidadDocumentalVO udoc = (UnidadDocumentalVO)
		// getFromTemporalSession(request,
		// TransferenciasConstants.UNIDAD_DOCUMENTAL);
		// RelacionEntregaVO relacionEntrega = (RelacionEntregaVO)
		// getFromTemporalSession(request,
		// TransferenciasConstants.RELACION_KEY);
		UDocEnFraccionSerieVO udocEnDivisionFS = (UDocEnFraccionSerieVO) getFromTemporalSession(
				request, FondosConstants.UNIDAD_DOCUMENTAL_EN_FS);

		try {
			// relacionesBI.modificarInformacionUnidadDocumental(relacionEntrega,
			// udoc);
			String mantenerInformacion = request
					.getParameter("mantenerInformacion");
			// setInTemporalSession(request,
			// TransferenciasConstants.FLAG_CREACION_UDOC_REL_MANTENER_INFORMACION,
			// formulario.getMantenerInformacion());
			// Como la unidad documental pudo haber sido modificada justo antes
			// de dar a duplicar, volvemos a obtenerla de base de datos
			// udoc = relacionesBI.getUnidadDocumental(udoc.getId());
			// setInTemporalSession(request,
			// TransferenciasConstants.UNIDAD_DOCUMENTAL,udoc);
			setInTemporalSession(request,
					FondosConstants.FLAG_CREACION_UDOC_FS_MANTENER_INFORMACION,
					mantenerInformacion);
			if (formulario.getIntMantenerInformacion() == 0) {
				removeInTemporalSession(request,
						FondosConstants.UNIDAD_DOCUMENTAL_EN_FS);
				addUdocsExecuteLogic(mappings, form, request, response);
			} else {
				crearUnidadDocumentalConFicha(udocEnDivisionFS, mappings, form,
						request, response);
			}
		} catch (ActionNotAllowedException anae) {
			guardarError(request, anae);
			setReturnActionFordward(request,
					mappings.findForward("edicion_udocEnFS_con_ficha"));
		}
	}

	public void crearUnidadDocumentalConFicha(UDocEnFraccionSerieVO udocEnFSVO,
			ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ActionNotAllowedException {

		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		GestionFraccionSerieBI fraccionSerieBI = services
				.lookupGestionFraccionSerieBI();

		DivisionFraccionSeriePO divisionFSPO = (DivisionFraccionSeriePO) getFromTemporalSession(
				request, FondosConstants.DIVISION_FRACCION_SERIE);
		int subtipo = divisionFSPO.getNivelDocumental().getId() != null ? divisionFSPO
				.getNivelDocumental().getSubtipo()
				: ElementoCuadroClasificacion.SUBTIPO_UNIDAD_DOCUMENTAL_SIMPLE;
		ActionRedirect forward = null;

		UDocEnDivisionFSForm formulario = (UDocEnDivisionFSForm) form;
		String idUDocOriginal = udocEnFSVO.getIdUDoc();

		// Establecer el orden para la nueva unidad documental
		List udocsEnDivisionFS = fraccionSerieBI.getUDocsEnDivisionFS(
				udocEnFSVO.getIdFS(), false);
		udocEnFSVO.setOrden(getNextOrden(udocsEnDivisionFS) + 1);

		fraccionSerieBI.addUDocToDivisionFS(udocEnFSVO, divisionFSPO,
				formulario.getIntMantenerInformacion() == 1);
		fraccionSerieBI.conservarDescripcion(idUDocOriginal,
				udocEnFSVO.getIdUDoc());

		// Añadir la nueva unidad documental a la lista
		udocsEnDivisionFS.add(udocEnFSVO);
		setInTemporalSession(request, FondosConstants.UDOCS_FRACCION_SERIE,
				udocsEnDivisionFS);

		forward = new ActionRedirect(
				mappings.findForward("edicion_udocEnFS_con_ficha"));
		forward.setRedirect(true);
		forward.addParameter(Constants.ID_FICHA, divisionFSPO.getIdFicha());
		forward.addParameter(Constants.ID, udocEnFSVO.getIdUDoc());
		if (divisionFSPO.getNivelDocumental() != null
				&& subtipo == ElementoCuadroClasificacion.SUBTIPO_CAJA)
			forward.addParameter(Constants.CURRENT_INVOCATION, Constants.FSFS);
		else
			forward.addParameter(Constants.CURRENT_INVOCATION, Constants.UDOCFS);

		// Modificar en la invocación anterior el valor del objeto unidad
		// documental en división de fracción de serie
		popLastInvocation(request);
		setInTemporalSession(request, FondosConstants.UNIDAD_DOCUMENTAL_EN_FS,
				(UDocEnFraccionSeriePO) (new UDocEnFraccionSerieToPO(services)
						.transform(udocEnFSVO)));

		setReturnActionFordward(request, forward);

	}

}