package transferencias.actions;

import ieci.core.types.IeciTdType;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.namespace.QName;
import javax.xml.rpc.ServiceException;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import se.NotAvailableException;
import se.instituciones.exceptions.GestorOrganismosException;
import se.tramites.exceptions.SistemaTramitadorException;
import se.usuarios.ServiceClient;
import transferencias.TipoTransferencia;
import transferencias.TransferenciasConstants;
import transferencias.forms.UnidadDocumentalForm;
import transferencias.model.EstadoREntrega;
import transferencias.vos.DetallePrevisionVO;
import transferencias.vos.DocumentoVO;
import transferencias.vos.IParteUnidadDocumentalVO;
import transferencias.vos.RangoVO;
import transferencias.vos.RelacionEntregaVO;
import transferencias.vos.UnidadDocumentalVO;
import util.ErrorsTag;
import xml.config.ConfiguracionSistemaArchivo;
import xml.config.ConfiguracionSistemaArchivoFactory;

import common.ConfigConstants;
import common.Constants;
import common.Messages;
import common.actions.ActionRedirect;
import common.actions.BaseAction;
import common.bi.GestionCuadroClasificacionBI;
import common.bi.GestionDescripcionBI;
import common.bi.GestionPrevisionesBI;
import common.bi.GestionRelacionesEntregaBI;
import common.bi.GestionSeriesBI;
import common.bi.ServiceRepository;
import common.exceptions.ActionNotAllowedException;
import common.exceptions.UncheckedArchivoException;
import common.navigation.ClientInvocation;
import common.navigation.KeysClientsInvocations;
import common.util.DateUtils;
import common.util.ListUtils;
import common.util.StringUtils;
import common.util.TypeConverter;

import deposito.model.GestorEstructuraDepositoBI;
import descripcion.model.xml.card.TipoFicha;
import descripcion.model.xsl.TemplateFactory;
import descripcion.vos.CampoReferenciaVO;
import descripcion.vos.DescriptorVO;
import fondos.model.ElementoCuadroClasificacion;
import fondos.vos.OrganoProductorVO;
import gcontrol.model.TipoAcceso;
import gcontrol.vos.CAOrganoVO;

/**
 * Action que lleva a cabo las diferentes acciones que pueden ser realizadas
 * sobre las unidades documentales que forman parte de una relacion de entrega
 */
public class GestionUnidadesDocumentales extends BaseAction {

	static Logger logger = Logger.getLogger(GestionUnidadesDocumentales.class);

	private final String IMPORT_XML_FILE_SUFFIX = "_SALIDA_";

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

		RelacionEntregaVO relacionEntrega = (RelacionEntregaVO) request
				.getSession()
				.getAttribute(TransferenciasConstants.RELACION_KEY);
		TipoTransferencia tipoTransferencia = TipoTransferencia
				.getTipoTransferencia(relacionEntrega.getTipotransferencia());
		UnidadDocumentalForm udocForm = (UnidadDocumentalForm) form;

		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		removeInTemporalSession(request,
				TransferenciasConstants.CONTADOR_UNIDADES_DOCUMENTALES);
		removeInTemporalSession(request, TransferenciasConstants.ORDEN_UDOC);
		removeInTemporalSession(request, TransferenciasConstants.POSICION_UDOC);

		// Contador de unidades documentales por relacion de entrega
		int unidadesDocRe = services.lookupGestionRelacionesBI()
				.getCountUnidadesDocRe(relacionEntrega.getId());
		setInTemporalSession(request,
				TransferenciasConstants.CONTADOR_UNIDADES_DOCUMENTALES,
				new Integer(unidadesDocRe));

		// Posicion que va tener la nueva unidad documental
		String[] udocsSelected = udocForm.getSelectedUdoc();
		InfoAsignacionUdocs infoAsignacionUdocs = (InfoAsignacionUdocs) getFromTemporalSession(
				request, TransferenciasConstants.ASIGNACION_UDOC2UI);
		int uDocPos;
		// Si no se selecciono ninguna U.Doc. entonces se insertar� al final
		if (udocsSelected == null || udocsSelected.length == 0)
			uDocPos = services.lookupGestionRelacionesBI()
					.getCountUnidadesDocRe(relacionEntrega.getId()) + 1;
		else {
			// Si selecciono alguno se obtiene la unidad documental se mira su
			// posicion en la vista y se le asigna dicha posicion
			// a la nueva unidad documental, modificandose tambien el orden de
			// las mismas en la base de datos.
			UnidadDocumentalVO uDoc = services.lookupGestionRelacionesBI()
					.getUnidadDocumental(udocsSelected[0]);
			uDocPos = infoAsignacionUdocs.getUdocsRelacion().indexOf(uDoc) + 1;
			setInTemporalSession(request, TransferenciasConstants.ORDEN_UDOC,
					new Integer(uDoc.getOrden()));
		}
		setInTemporalSession(request, TransferenciasConstants.POSICION_UDOC,
				new Integer(uDocPos));

		if (tipoTransferencia.equals(TipoTransferencia.ORDINARIA)) {
			if (relacionEntrega.getIdprocedimiento() != null)
				udocForm.setProcedimiento(relacionEntrega.getIdprocedimiento());
			if (relacionEntrega.getIdseriedestino() != null)
				udocForm.setSerie(relacionEntrega.getIdseriedestino());
			listarExpedientesExecuteLogic(mappings, form, request, response);
		} else {
			GestionCuadroClasificacionBI cuadroBI = services
					.lookupGestionCuadroClasificacionBI();
			String idDescriptor = relacionEntrega.getIddescrorgproductor();
			if (!udocForm.getMantenerInformacion().equals("1")
					&& (idDescriptor != null)
					&& (!idDescriptor.equals(Constants.STRING_EMPTY))) {
				GestionRelacionesEntregaBI relacionesBI = services
						.lookupGestionRelacionesBI();
				OrganoProductorVO organoProductorVO = relacionesBI
						.getOrganoProductor(idDescriptor);
				if (organoProductorVO != null) {
					udocForm.setIdProductor(organoProductorVO.getId());
					udocForm.setNombreProductor(organoProductorVO.getNombre());
				} else {
					GestionDescripcionBI descripcionBI = services
							.lookupGestionDescripcionBI();
					// Intentar buscarlo en descriptores
					DescriptorVO descriptorVO = descripcionBI
							.getDescriptor(idDescriptor);
					if (descriptorVO != null) {
						udocForm.setIdProductor(descriptorVO.getId());
						udocForm.setNombreProductor(descriptorVO.getNombre());
					}
				}
			}
			setInTemporalSession(request,
					TransferenciasConstants.banderaEdicionCreacion, "C");

			int subtipo = cuadroBI.getSubtipo(relacionEntrega
					.getIdNivelDocumental());

			if (!udocForm.getMantenerInformacion().equals("1")) {
				cargarDatosRevisionUdoc(request, services, udocForm,
						relacionEntrega.getId(), subtipo);
			}

			nuevaUnidadDocumentalExecuteLogic(mappings, request, subtipo);
		}
	}

	/**
	 * Logica ejecutada cuan el action es invocado con parametro method con
	 * valor 'lockUdocs'. Se encarga de bloquear las unidades documentales
	 * seleccionadas
	 */
	public void lockUdocsExecuteLogic(ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		RelacionEntregaVO relacionEntrega = (RelacionEntregaVO) getFromTemporalSession(
				request, TransferenciasConstants.RELACION_KEY);

		String[] udocsToLock = ((UnidadDocumentalForm) form).getSelectedUdoc();
		try {
			if (udocsToLock != null) {
				ServiceRepository services = ServiceRepository
						.getInstance(ServiceClient.create(getAppUser(request)));
				GestionRelacionesEntregaBI relacionesBI = services
						.lookupGestionRelacionesBI();
				relacionesBI.lockUnidadesDocumentales(relacionEntrega,
						udocsToLock);
			}
			goLastClientExecuteLogic(mappings, form, request, response);
		} catch (ActionNotAllowedException anae) {
			guardarError(request, anae);
			goLastClientExecuteLogic(mappings, form, request, response);
		}
	}

	/**
	 * Logica ejecutada cuan el action es invocado con parametro method con
	 * valor 'unlockUdocs'. Se encarga de desbloquear las unidades documentales
	 * seleccionadas
	 */
	public void unlockUdocsExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		RelacionEntregaVO relacionEntrega = (RelacionEntregaVO) getFromTemporalSession(
				request, TransferenciasConstants.RELACION_KEY);

		String[] udocsToUnlock = ((UnidadDocumentalForm) form)
				.getSelectedUdoc();
		try {
			if (udocsToUnlock != null) {
				ServiceRepository services = ServiceRepository
						.getInstance(ServiceClient.create(getAppUser(request)));
				GestionRelacionesEntregaBI relacionesBI = services
						.lookupGestionRelacionesBI();
				relacionesBI.unlockUnidadesDocumentales(relacionEntrega,
						udocsToUnlock);
			}
			goLastClientExecuteLogic(mappings, form, request, response);
		} catch (ActionNotAllowedException anae) {
			guardarError(request, anae);
			goLastClientExecuteLogic(mappings, form, request, response);
		}
	}

	/**
	 * Logica ejecutada cuando el action es invocado con parametro method con
	 * valor 'formImportUdocs'. Redirige a una nueva pantalla que permite
	 * importar unidades documentales en la relaci�n.
	 */
	public void formImportUdocsExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		saveCurrentInvocation(
				KeysClientsInvocations.TRANSFERENCIAS_IMPORT_UNIDADES_DOCUMENTALES,
				request);
		setReturnActionFordward(request,
				mappings.findForward("import_udocs_relacion"));

	}

	/**
	 * Valida la url del ws de transferencias
    *
	 * @param request
	 *            {@link HttpServletRequest}
	 */
	private ActionErrors validateWsTransferenciasUrl(String url,
			HttpServletRequest request) {
		ActionErrors errors = new ActionErrors();

		if ((url == null) || StringUtils.isBlank(url)) {
			errors.add(
					ActionErrors.GLOBAL_MESSAGE,
					new ActionError(
							TransferenciasConstants.ERROR_TRANSFERENCIAS_RELACIONES_IMPORT_XML_URL_WS_EMPTY));
		}

		return errors;
	}

	/**
	 * Genera un error por la importacion de unidades documentales a partir de
	 * un xml
    *
	 * @param error
	 *            Error a generar
	 * @param mappings
	 *            Objeto mapping
	 * @param request
	 *            Peticion actual
	 */
	private void generateImportError(ActionError error, ActionMapping mappings,
			HttpServletRequest request) {

		// A�adir los errores al request
		ActionErrors errors = new ActionErrors();
		errors.add(ActionErrors.GLOBAL_MESSAGE, error);

		obtenerErrores(request, true).add(errors);
		setReturnActionFordward(request,
				mappings.findForward("import_udocs_relacion"));
	}

	/**
	 * Logica ejecutada cuando el action es invocado con parametro method con
	 * valor 'importUdocs'. Redirige a una nueva pantalla que permite importar
	 * unidades documentales en la relaci�n.
	 */
	public void importUdocsExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		logger.info("Inicio de importUdocsExecuteLogic");

		// Validar el formulario
		UnidadDocumentalForm unidadDocumentalForm = (UnidadDocumentalForm) form;
		ActionErrors errores = unidadDocumentalForm.validateFile(mappings,
				request);
		if ((errores == null) || errores.isEmpty()) {

			logger.info("Formulario validado");

			// Obtener la configuraci�n de Archivo
			ConfiguracionSistemaArchivo csa = ConfiguracionSistemaArchivoFactory
					.getConfiguracionSistemaArchivo();
			String wsUrl = csa.getConfiguracionWsTransferencias().getUrl();

			errores = validateWsTransferenciasUrl(wsUrl, request);
			if ((errores == null) || errores.isEmpty()) {
				try {
					// conectarse al servicio web
					Service  service = new Service();
					Call     call    = (Call) service.createCall();

					call.setTargetEndpointAddress( new java.net.URL(wsUrl) );
					call.setOperationName(new QName("http://servicioWeb.transferirExpedientes", "transferirExpedientes"));
					call.setTimeout(new Integer(-1));

					FormFile fichero = unidadDocumentalForm.getFichero();

					// crear array de bytes
					byte[] contenidoXml = fichero.getFileData();

					// extraer nombres ficheros
					String nombreXml = FilenameUtils.getName(fichero
							.getFileName());
					String nombreXmlRespuesta = IMPORT_XML_FILE_SUFFIX
							+ nombreXml;

					RelacionEntregaPO relacionEntrega = (RelacionEntregaPO) request
							.getSession().getAttribute(
									TransferenciasConstants.RELACION_KEY);

					// llamada
					int tipoEjecucion = 1;
					String procesarConErrores = "S";
					int nivelVerificacion = 0;

					int anadirExpREntregaConUdocs = 0;

					logger.info("Se procede a la llamada del servicio web");
					byte[] respuesta = null;
					String entity = getAppUser(request).getEntity();
					if (StringUtils.isNotEmpty(entity)) {
						respuesta = (byte[]) call.invoke( new Object[] { nombreXml,
								contenidoXml, nombreXmlRespuesta,
								relacionEntrega.getAno(), relacionEntrega
										.getArchivoReceptor().getCodigo(),
								relacionEntrega.getOrden(), tipoEjecucion,
								procesarConErrores, nivelVerificacion,
								anadirExpREntregaConUdocs } );
					} else {
						respuesta = (byte[]) call.invoke( new Object[] { nombreXml,
								contenidoXml, nombreXmlRespuesta,
								relacionEntrega.getAno(), relacionEntrega
										.getArchivoReceptor().getCodigo(),
								relacionEntrega.getOrden(), tipoEjecucion,
								procesarConErrores, nivelVerificacion,
								anadirExpREntregaConUdocs } );
					}

					// XML del resultado
					String xml = new String(respuesta);
					request.setAttribute(
							TransferenciasConstants.IMPORT_RESULT_XML_KEY, xml);

					// Plantilla XSL para presentar el resultado
					request.setAttribute(
							TransferenciasConstants.IMPORT_RESULT_XSL_KEY,
							TemplateFactory.getInstance(
									getServiceClient(request)).getTemplate(
									TipoAcceso.IMPORTACION_WS));

					popLastInvocation(request);
					saveCurrentInvocation(
							KeysClientsInvocations.TRANSFERENCIAS_IMPORT_UNIDADES_DOCUMENTALES_RESULT,
							request);

					setReturnActionFordward(
							request,
							mappings.findForward("import_udocs_relacion_result"));
				} catch (FileNotFoundException e) {
					generateImportError(
							new ActionError(
									TransferenciasConstants.ERROR_TRANSFERENCIAS_RELACIONES_IMPORT_XML_NOT_FOUND),
							mappings, request);
				} catch (RemoteException e) {
					generateImportError(
							new ActionError(
									TransferenciasConstants.ERROR_TRANSFERENCIAS_RELACIONES_IMPORT_XML_REMOTE_ERROR),
							mappings, request);
				} catch (ServiceException e) {
					generateImportError(
							new ActionError(
									TransferenciasConstants.ERROR_TRANSFERENCIAS_RELACIONES_IMPORT_XML_SERVICE_ERROR),
							mappings, request);
				} catch (IOException e) {
					generateImportError(
							new ActionError(
									TransferenciasConstants.ERROR_TRANSFERENCIAS_RELACIONES_IMPORT_XML_INPUT_ERROR),
							mappings, request);
				}
			} else {
				logger.info("Url del ws de transferencias no definido");

				// A�adir los errores al request
				obtenerErrores(request, true).add(errores);

				setReturnActionFordward(request,
						mappings.findForward("import_udocs_relacion"));
			}
		} else {
			logger.info("Formulario inv\u00E1lido");

			// A�adir los errores al request
			obtenerErrores(request, true).add(errores);

			setReturnActionFordward(request,
					mappings.findForward("import_udocs_relacion"));
		}
	}

	public void eliminarUdocsExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		RelacionEntregaVO relacionEntrega = (RelacionEntregaVO) getFromTemporalSession(
				request, TransferenciasConstants.RELACION_KEY);
		String[] udocsToRemove = ((UnidadDocumentalForm) form)
				.getSelectedUdoc();
		try {
			if (udocsToRemove != null) {
				ServiceRepository services = ServiceRepository
						.getInstance(ServiceClient.create(getAppUser(request)));
				GestionRelacionesEntregaBI relacionesBI = services
						.lookupGestionRelacionesBI();
				GestionCuadroClasificacionBI cuadroBI = services
						.lookupGestionCuadroClasificacionBI();
				relacionesBI.eliminarUnidadDocumental(relacionEntrega,
						udocsToRemove, cuadroBI.getSubtipo(relacionEntrega
								.getIdNivelDocumental()));
			}
			goLastClientExecuteLogic(mappings, form, request, response);
		} catch (ActionNotAllowedException anae) {
			guardarError(request, anae);
			goLastClientExecuteLogic(mappings, form, request, response);
			// setReturnActionFordward(request,
			// mappings.findForward("info_udoc_relacion"));
		}
	}

	public void listarExpedientesExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		// los expedientes se listan solo en transferencias ordinarias. Enlas
		// extraordinarias hay que
		// introducir la informacion de las unidades documentales de forma
		// manual
		RelacionEntregaVO relacionEntrega = (RelacionEntregaVO) getFromTemporalSession(
				request, TransferenciasConstants.RELACION_KEY);
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		GestionRelacionesEntregaBI relacionBI = services
				.lookupGestionRelacionesBI();
		try {
			List expedientes = relacionBI
					.getExpedientesParaRelacion(relacionEntrega.getId());
			setInTemporalSession(request,
					TransferenciasConstants.LISTA_EXPEDIENTES, expedientes);
			if (expedientes != null)
				setInTemporalSession(request,
						TransferenciasConstants.NUM_EXPEDIENTES, new Integer(
								expedientes.size()));
			else
				setInTemporalSession(request,
						TransferenciasConstants.NUM_EXPEDIENTES, new Integer(
								Constants.STRING_CERO));
			saveCurrentInvocation(
					KeysClientsInvocations.TRANSFERENCIAS_SELECCION_EXPEDIENTES,
					request);
			setReturnActionFordward(request,
					mappings.findForward("seleccion_expedientes"));
		} catch (SistemaTramitadorException ste) {
			obtenerErrores(request, true).add(ActionErrors.GLOBAL_ERROR,
					new ActionError(Constants.ERROR_SISTEMA_TRAMITADOR));
			goLastClientExecuteLogic(mappings, form, request, response);
		} catch (NotAvailableException nae) {
			obtenerErrores(request, true)
					.add(ActionErrors.GLOBAL_ERROR,
							new ActionError(
									Constants.ERROR_FUNCIONALIDAD_NO_DISPONIBLE));
			goLastClientExecuteLogic(mappings, form, request, response);
		}
	}

	/**
	 * Incorpora los expedientes seleccionados por el usuario como unidades
	 * documentales de la relacion de entrega ordinaria con la que se esta
	 * trabajando. Logica ejecutada cuando el action es invocado con parametro
	 * method con valor 'seleccionExpedientes'
	 */
	public void seleccionExpedientesExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		RelacionEntregaVO relacionEntrega = (RelacionEntregaVO) getFromTemporalSession(
				request, TransferenciasConstants.RELACION_KEY);
		List expedientes = (List) getFromTemporalSession(request,
				TransferenciasConstants.LISTA_EXPEDIENTES);
		UnidadDocumentalForm udocForm = (UnidadDocumentalForm) form;
		String[] expedientesSeleccionados = udocForm.getExpediente();
		if (expedientesSeleccionados != null) {
			ServiceRepository services = ServiceRepository
					.getInstance(ServiceClient.create(getAppUser(request)));
			GestionRelacionesEntregaBI relacionesBI = services
					.lookupGestionRelacionesBI();

			int nExpedientes = expedientesSeleccionados.length;
			List listaExpedientes = new ArrayList();
			for (int i = 0; i < nExpedientes; i++)
				listaExpedientes.add(expedientes.get(Integer
						.parseInt(expedientesSeleccionados[i])));

			try {
				// Obtenemos el orden en el que deseamos insertar los
				// expedientes, null si se inserta al final
				Integer orden = (Integer) getFromTemporalSession(request,
						TransferenciasConstants.ORDEN_UDOC);

				// Como estamos en una relaci�n de entrega ordinaria, el subtipo
				// de los expedientes es unidad documental simple
				int retorno = relacionesBI
						.crearUnidadesDocumentales(
								orden,
								relacionEntrega,
								listaExpedientes,
								ElementoCuadroClasificacion.SUBTIPO_UNIDAD_DOCUMENTAL_SIMPLE);

				if (retorno == GestionRelacionesEntregaBI.ERROR_RELACION_SIN_DOCS_FISICOS) {
					ActionErrors errors = new ActionErrors();
					errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
							Constants.ERROR_RELACION_SOLO_DOCS_ELECTRONICOS));
					ErrorsTag.saveErrors(request, errors);
				}
				setInTemporalSession(request,
						TransferenciasConstants.LISTA_EXPEDIENTES_A_ELIMINAR,
						listaExpedientes);
			} catch (ActionNotAllowedException anae) {
				guardarError(request, anae);
			} catch (SistemaTramitadorException ste) {
				obtenerErrores(request, true).add(ActionErrors.GLOBAL_ERROR,
						new ActionError(Constants.ERROR_SISTEMA_TRAMITADOR));
			} catch (GestorOrganismosException gte) {
				obtenerErrores(request, true).add(ActionErrors.GLOBAL_ERROR,
						new ActionError(Constants.ERROR_GESTOR_ORGANIZACION));
			} catch (NotAvailableException nae) {
				obtenerErrores(request, true).add(
						ActionErrors.GLOBAL_ERROR,
						new ActionError(
								Constants.ERROR_FUNCIONALIDAD_NO_DISPONIBLE));
			}
		}
		goBackExecuteLogic(mappings, form, request, response);
	}

	public void nuevaUnidadDocumentalExecuteLogic(ActionMapping mappings,
			HttpServletRequest request, int subtipo) {

		if (subtipo == ElementoCuadroClasificacion.SUBTIPO_CAJA) {
			saveCurrentInvocation(
					KeysClientsInvocations.TRANSFERENCIAS_NUEVA_FRACCION_SERIE,
					request);

		} else {
			saveCurrentInvocation(
					KeysClientsInvocations.TRANSFERENCIAS_NUEVA_UNIDAD_DOCUMENTAL,
					request);
		}

		setReturnActionFordward(request,
				mappings.findForward("edicion_udoc_relacion"));
	}

	private void cargarDatosRevisionUdoc(HttpServletRequest request,
			ServiceRepository services, UnidadDocumentalForm frm,
			String idAlta, int subtipo) {
		GestionRelacionesEntregaBI relacionBI = services
				.lookupGestionRelacionesBI();
		String id = relacionBI.getIdUnidadDocumentalRevisionByIdAlta(idAlta);

		if (StringUtils.isNotBlank(id)) {
			GestionDescripcionBI descripcionBI = services
					.lookupGestionDescripcionBI();

			if (subtipo == ElementoCuadroClasificacion.SUBTIPO_CAJA) {
				List rangos = descripcionBI.getRangosFS(
						TipoFicha.FICHA_ELEMENTO_CF, id);
				if (ListUtils.isNotEmpty(rangos)) {
					setInTemporalSession(request,
							TransferenciasConstants.LISTA_RANGOS_UDOC, rangos);
				}
			} else {
				frm.setNumeroExpediente(descripcionBI.getTituloDocumento(id));
			}

			frm.setAsunto(descripcionBI.getAsunto(id));
			frm.setFechaInicioAsDate(descripcionBI.getFechaInicial(
					TipoFicha.FICHA_ELEMENTO_CF, id));
			frm.setFechaFinAsDate(descripcionBI.getFechaFinal(
					TipoFicha.FICHA_ELEMENTO_CF, id));
			CampoReferenciaVO productor = descripcionBI.getProductor(
					TipoFicha.FICHA_ELEMENTO_CF, id);

			if (productor != null) {
				frm.setIdProductor(productor.getIdObjRef());
				frm.setNombreProductor(productor.getNombre());
			}
		}
	}

	public List obtenerRangos(String[] rangosIniciales, String[] rangosFinales) {
		List rangos = new ArrayList();
		// Reseteamos los rangos introducidos anteriormente y los volvemos a
		// rellenar
		if (rangosIniciales != null && rangosFinales != null) {
			for (int i = 0; i < rangosIniciales.length; i++) {
				rangos.add(new RangoVO(rangosIniciales[i], rangosFinales[i]));
			}
		}
		return rangos;
	}

	public int getNextOrden(List udocsRelacion) {

		int orden = 1;
		if (udocsRelacion != null && udocsRelacion.size() > 0) {
			UnidadDocumentalVO unidadDocumentalVO = (UnidadDocumentalVO) udocsRelacion
					.get(udocsRelacion.size() - 1);
			orden = unidadDocumentalVO.getOrden();
			if (orden == IeciTdType.NULL_LONG_INTEGER)
				orden = 1;
			else
				orden++;
		}

		return orden;
	}

	// public HashMap obtenerCamposAIgnorar(String idUDoc,
	// GestionDescripcionBIImpl descripcionBI) {
	//
	// // ConfiguracionDescripcion configDesc =
	// ConfiguracionSistemaArchivoFactory.getConfiguracionSistemaArchivo().getConfiguracionDescripcion();
	//
	// // String idCampoFI = configDesc.getFechaExtremaInicial();
	// // String idCampoFF = configDesc.getFechaExtremaFinal();
	// // String idCampoProductor = configDesc.getProductor();
	// // String idCampoRI = configDesc.getRangoInicial();
	// // String idCampoRIN = configDesc.getRangoInicialNormalizado();
	// // String idCampoRF = configDesc.getRangoFinal();
	// // String idCampoRFN = configDesc.getRangoFinalNormalizado();
	//
	// HashMap mapCamposAIgnorar = new HashMap();
	// ArrayList listaCamposTC = new ArrayList(), listaCamposFecha = new
	// ArrayList(), listaCamposReferencia = new ArrayList();
	// // listaCamposFecha.add(idCampoFI); listaCamposFecha.add(idCampoFF);
	// // listaCamposReferencia.add(idCampoProductor);
	// // listaCamposTC.add(idCampoRI); listaCamposTC.add(idCampoRIN);
	// listaCamposTC.add(idCampoRF); listaCamposTC.add(idCampoRFN);
	//
	//
	//
	// mapCamposAIgnorar.put(new
	// Integer(TipoCampo.TEXTO_CORTO_VALUE).toString(), listaCamposTC);
	// mapCamposAIgnorar.put(new Integer(TipoCampo.FECHA_VALUE).toString(),
	// listaCamposFecha);
	// mapCamposAIgnorar.put(new Integer(TipoCampo.REFERENCIA_VALUE).toString(),
	// listaCamposReferencia);

	//
	// return mapCamposAIgnorar;
	//
	// }

	/**
	 * Crea una unidad documental a partir de la informaci�n introducida de
	 * forma manual por un usuario durante la elaboracion de una relacion de
	 * entrega sobre una transferencia extraordinaria
    *
	 */
	public void crearUnidadDocumentalExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		UnidadDocumentalForm udocForm = (UnidadDocumentalForm) form;
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));

		GestionPrevisionesBI previsionesBI = services
				.lookupGestionPrevisionesBI();

		RelacionEntregaPO infoRelacion = (RelacionEntregaPO) getFromTemporalSession(
				request, TransferenciasConstants.RELACION_KEY);
		DetallePrevisionVO detallePrevision = null;
		List rangos = null;

		if (infoRelacion != null && infoRelacion.getIddetprevision() != null) {
			detallePrevision = previsionesBI.getDetallePrevision(infoRelacion
					.getIddetprevision());
		}

		// Movido aqu� el punto de creaci�n de la Unidad Documental para
		// permitir mantener los rangos de fracci�n de serie
		String mantenerInfo = (String) getFromTemporalSession(
				request,
				TransferenciasConstants.FLAG_CREACION_UDOC_REL_MANTENER_INFORMACION);
		UnidadDocumentalVO udoc = null;
		if (mantenerInfo != null && Integer.parseInt(mantenerInfo) == 1)
			udoc = (UnidadDocumentalVO) getFromTemporalSession(request,
					TransferenciasConstants.UNIDAD_DOCUMENTAL);

		if (udoc == null) {
			udoc = new UnidadDocumentalVO(ConfiguracionSistemaArchivoFactory
					.getConfiguracionSistemaArchivo()
					.getConfiguracionDescripcion().getFechaExtremaInicial(),
					ConfiguracionSistemaArchivoFactory
							.getConfiguracionSistemaArchivo()
							.getConfiguracionDescripcion()
							.getFechaExtremaFinal(), ConfigConstants
							.getInstance().getSeparadorDefectoFechasRelacion());
		}

		// Aqu� ya est� creado el objeto UnidadDocumentalVO, sea nuevo o a
		// editar

		int subtipo = (infoRelacion.getNivelDocumental().getId() != null ? infoRelacion
				.getNivelDocumental().getSubtipo()
				: ElementoCuadroClasificacion.SUBTIPO_UNIDAD_DOCUMENTAL_SIMPLE);

		if (infoRelacion.getNivelDocumental() != null
				&& subtipo == ElementoCuadroClasificacion.SUBTIPO_CAJA) {
			// Una vez se ejecuta esto, se rellenan los nuevos rangos en la
			// unidad documental
			rangos = obtenerRangos(request.getParameterValues("campo_201"),
					request.getParameterValues("campo_202"));
			String numExpRangos = null;
			if (rangos != null && rangos.size() > 0) {
				udoc.resetRangos();
				Iterator it = rangos.iterator();
				while (it.hasNext()) {
					RangoVO rango = (RangoVO) it.next();

					numExpRangos = (numExpRangos == null ? "" : numExpRangos
							+ Constants.DELIMITADOR_RANGOS)
							+ rango.getDesde()
							+ Constants.DELIMITADOR_RANGO_INICIAL_FINAL
							+ rango.getHasta();
					udoc.addRango(rango);
				}
				udoc.setNumeroExpediente(numExpRangos);
			}
		} else {
			udoc.setNumeroExpediente(udocForm.getNumeroExpediente());
		}

		ActionErrors errores = validarUdocForm(udocForm,
				detallePrevision != null ? detallePrevision.getAnoIniUdoc()
						: null,
				detallePrevision != null ? detallePrevision.getAnoFinUdoc()
						: null, infoRelacion, request);

		if (errores.size() > 0) {
			// Aqu� almacenamos los rangos independientemente en caso de error
			// en introducci�n de datos
			if (rangos != null && rangos.size() > 0)
				setInTemporalSession(request,
						TransferenciasConstants.LISTA_RANGOS_UDOC, rangos);
			ErrorsTag.saveErrors(request, errores);
			nuevaUnidadDocumentalExecuteLogic(mappings, request, subtipo);
		} else {
			GestionRelacionesEntregaBI relacionesBI = services
					.lookupGestionRelacionesBI();
			OrganoProductorVO organoProductorVO = getGestionRelacionesBI(
					request).getOrganoProductor(udocForm.getIdProductor());
			CAOrganoVO organo = null;

			if (organoProductorVO != null)
				organo = getGestionControlUsuarios(request)
						.getCAOrgProductorVOXId(organoProductorVO.getIdOrgano());

			if (organo != null)
				udoc.setProductor(udocForm.getIdProductor(),
						udocForm.getNombreProductor(), organo.getCodigo());
			else
				udoc.setProductor(udocForm.getIdProductor(),
						udocForm.getNombreProductor(), null);
			try {
				udoc.setFechaInicio(udocForm.getFechaInicioAsDate());
				udoc.setFechaFin(udocForm.getFechaFinAsDate());
				// NO DEBERIA DARSE PUESTO QUE SE HA HECHO VALIDACION PREVIA
			} catch (ParseException pe) {
				logger.error(pe);
			}
			udoc.setAsunto(udocForm.getAsunto());
			try {
				// Establezco el orden que viene en la request, si es null
				// entonces se trata del primer elemento
				Integer orden = (Integer) getFromTemporalSession(request,
						TransferenciasConstants.ORDEN_UDOC);
				int incremento = 0;

				if (orden != null) {
					udoc.setOrden(orden.intValue());
					incremento = 1;
				} else {
					List udocsRelacion = relacionesBI
							.fetchRowsByCodigoRelacionOrderByOrden(infoRelacion
									.getId());
					udoc.setOrden(getNextOrden(udocsRelacion));
				}

				boolean copiarUdocsRelacionadas = false;
				if (StringUtils.isNotEmpty(infoRelacion.getIdFicha())) {
					if (mantenerInfo == null
							|| Integer.parseInt(mantenerInfo) == 0) {
						copiarUdocsRelacionadas = true;
					}
				}

				if (infoRelacion.isSignaturaSolictableEnUDoc()) {

					relacionesBI.nuevaUnidadDocumental(infoRelacion, udoc,
							udocForm.getSignaturaUDoc(), subtipo, incremento,
							copiarUdocsRelacionadas);
					// Si era una relaci�n con ficha en la que hab�a que
					// mantener la informaci�n, la conservamos
					if (StringUtils.isNotEmpty(infoRelacion.getIdFicha())) {
						if (mantenerInfo != null
								&& Integer.parseInt(mantenerInfo) == 1) {
							GestionDescripcionBI descripcionBI = services
									.lookupGestionDescripcionBI();
							UnidadDocumentalVO udocADuplicar = (UnidadDocumentalVO) getFromTemporalSession(
									request,
									TransferenciasConstants.UNIDAD_DOCUMENTAL_A_DUPLICAR);

							HashMap mapCamposAIgnorar = descripcionBI
									.getValoresFichaConDatos(
											TipoFicha.FICHA_UDOCRE,
											udoc.getId());
							relacionesBI.conservarDescripcion(
									udocADuplicar.getId(), udoc.getId(),
									mapCamposAIgnorar);

							// Limpiamos el valor de la sesi�n
							removeInTemporalSession(
									request,
									TransferenciasConstants.UNIDAD_DOCUMENTAL_A_DUPLICAR);
						}
					}
				} else {
					relacionesBI.nuevaUnidadDocumental(infoRelacion, udoc,
							null, subtipo, incremento, copiarUdocsRelacionadas);
				}

				popLastInvocation(request);

				// se fuerza la recarga de la unidad documental y la
				// introduccion de la invocacion en la pila de llamadas
				removeInTemporalSession(request,
						TransferenciasConstants.UNIDAD_DOCUMENTAL);

				// Contador de unidades documentales por relacion de entrega
				int unidadesDocRe = services.lookupGestionRelacionesBI()
						.getCountUnidadesDocRe(infoRelacion.getId());
				setInTemporalSession(request,
						TransferenciasConstants.CONTADOR_UNIDADES_DOCUMENTALES,
						new Integer(unidadesDocRe));

				ActionRedirect forward;

				if (StringUtils.isEmpty(infoRelacion.getIdFicha())) {
					forward = new ActionRedirect(
							mappings.findForward("redirect_to_view_udoc"));
					forward.setRedirect(true);
					forward.addParameter("udocID", udoc.getId());
				} else {
					// CASO ESPECIAL: no se estaba metiendo en este caso la
					// unidad documental en sesi�n
					setInTemporalSession(
							request,
							TransferenciasConstants.UNIDAD_DOCUMENTAL,
							TransferenciasUnidadDocumentalToPO.getInstance(
									services).transform(udoc));

					forward = new ActionRedirect(
							mappings.findForward("nueva_udoc_relacion_con_ficha"));
					forward.setRedirect(true);
					forward.addParameter(Constants.ID_FICHA,
							infoRelacion.getIdFicha());
					forward.addParameter(Constants.ID, udoc.getId());
					forward.addParameter("numOrden",
							String.valueOf(udoc.getOrden()));
					if (infoRelacion.getNivelDocumental() != null
							&& subtipo == ElementoCuadroClasificacion.SUBTIPO_CAJA)
						forward.addParameter(Constants.CURRENT_INVOCATION,
								Constants.FSRE);
					else
						forward.addParameter(Constants.CURRENT_INVOCATION,
								Constants.UDOCRE);
				}

				setReturnActionFordward(request, forward);
			} catch (ActionNotAllowedException anae) {
				// Aqu� almacenamos los rangos independientemente en caso de
				// error en introducci�n de datos
				if (rangos != null && rangos.size() > 0)
					setInTemporalSession(request,
							TransferenciasConstants.LISTA_RANGOS_UDOC, rangos);

				guardarError(request, anae);
				nuevaUnidadDocumentalExecuteLogic(mappings, request, subtipo);
			}
		}
	}

	/**
	 * Comprobacion de que el usuario ha proporcionado la informacion de unidad
	 * documental de manera adecuada
    *
	 */
	protected ActionErrors validarUdocForm(UnidadDocumentalForm form,
			String endAfterYear, String endBeforeYear,
			RelacionEntregaPO relacion, HttpServletRequest request) {
		ActionErrors errores = new ActionErrors();

		if (relacion.isSignaturaSolictableEnUDoc()) {
			if (GenericValidator.isBlankOrNull(form.getSignaturaUDoc())) {
				errores.add(
						TransferenciasConstants.NECESARIO_INTRODUCIR_UNA_SIGNATURA,
						new ActionError(
								TransferenciasConstants.NECESARIO_INTRODUCIR_UNA_SIGNATURA));
			}
			if (!ConfigConstants.getInstance()
					.getPermitirSignaturaAlfanumerica()) {
				if (!GenericValidator.isBlankOrNull(form.getSignaturaUDoc()))
					if (!GenericValidator.isInt(form.getSignaturaUDoc()))
						errores.add(
								TransferenciasConstants.SIGNATURA_DEBE_SER_UN_VALOR_NUMERICO,
								new ActionError(
										TransferenciasConstants.SIGNATURA_DEBE_SER_UN_VALOR_NUMERICO));
			}
		}

		if (relacion.isNivelDocumentalFraccionSerie()) {
			String[] rangosIniciales = request.getParameterValues("campo_201");
			String[] rangosFinales = request.getParameterValues("campo_202");

			if (rangosIniciales != null && rangosFinales != null) {
				if (rangosIniciales.length != rangosFinales.length) {
					errores.add(
							TransferenciasConstants.NECESARIO_INTRODUCIR_AMBOS_RANGOS,
							new ActionError(
									TransferenciasConstants.NECESARIO_INTRODUCIR_AMBOS_RANGOS));
				} else {
					// Validamos rangos iniciales
					for (int i = 0; i < rangosIniciales.length; i++) {
						if (GenericValidator.isBlankOrNull(rangosIniciales[i])) {
							errores.add(
									TransferenciasConstants.NECESARIO_INTRODUCIR_RANGO_INICIAL,
									new ActionError(
											TransferenciasConstants.NECESARIO_INTRODUCIR_RANGO_INICIAL));
							break;
						}
					}

					// Validamos rangos finales
					for (int i = 0; i < rangosFinales.length; i++) {
						if (GenericValidator.isBlankOrNull(rangosFinales[i])) {
							errores.add(
									TransferenciasConstants.NECESARIO_INTRODUCIR_RANGO_FINAL,
									new ActionError(
											TransferenciasConstants.NECESARIO_INTRODUCIR_RANGO_FINAL));
							break;
						}
					}
				}
			} else {
				// No puede haber uno relleno y el otro no => error
				if (rangosIniciales == null && rangosIniciales != rangosFinales)
					errores.add(
							TransferenciasConstants.NECESARIO_INTRODUCIR_AMBOS_RANGOS,
							new ActionError(
									TransferenciasConstants.NECESARIO_INTRODUCIR_AMBOS_RANGOS));
			}
		}

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
			else {
				Calendar cal = Calendar.getInstance();
				if (endAfterYear != null) {
					cal.set(Integer.parseInt(endAfterYear), 0, 1, 0, 0, 0);
					if (DateUtils.isFechaMenor(fechaFin, cal.getTime()))
						errores.add(Constants.ERROR_UDOCEND_TOO_EARLY,
								new ActionError(
										Constants.ERROR_UDOCEND_TOO_EARLY,
										endAfterYear));
				}
				if (endBeforeYear != null) {
					cal.set(Integer.parseInt(endBeforeYear), 11, 31, 0, 0, 0);
					if (DateUtils.isFechaMayor(fechaFin, cal.getTime()))
						errores.add(Constants.ERROR_UDOCEND_TOO_LATE,
								new ActionError(
										Constants.ERROR_UDOCEND_TOO_LATE,
										endBeforeYear));
				}
			}
			if (fechaInicio != null && fechaInicio.compareTo(fechaFin) > 0)
				errores.add(Constants.ERROR_INITDATE_AFTER_ENDDATE,
						new ActionError(Constants.ERROR_INITDATE_AFTER_ENDDATE));
		}
		return errores;
	}

	public void infoUnidadDocumentalDesdeDepositoExecuteLogic(
			ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		ActionRedirect forward = new ActionRedirect(
				mappings.findForward("info_udoc_relacion_desde_deposito"));
		int subtipo = ElementoCuadroClasificacion.SUBTIPO_UNIDAD_DOCUMENTAL_SIMPLE;
		String udocID = request.getParameter("udocID");
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		GestionRelacionesEntregaBI relacionBI = services
				.lookupGestionRelacionesBI();

		// Informaci�n de la unidad documental
		UnidadDocumentalVO unidadDocumental = relacionBI
				.abrirUnidadDocumental(udocID);
		// saveCurrentInvocation(KeysClientsInvocations.TRANSFERENCIAS_VER_UNIDAD_DOCUMENTAL,
		// request);

		setInTemporalSession(request,
				TransferenciasConstants.UNIDAD_DOCUMENTAL,
				TransferenciasUnidadDocumentalToPO.getInstance(services)
						.transform(unidadDocumental));

		// Informaci�n de la relaci�n de entrega
		RelacionEntregaVO relacionVO = relacionBI
				.abrirRelacionEntrega(unidadDocumental.getIdRelEntrega());
		setInTemporalSession(request, TransferenciasConstants.RELACION_KEY,
				RelacionToPO.getInstance(services).transform(relacionVO));

		// Contador de unidades documentales por relacion de entrega
		int unidadesDocRe = services.lookupGestionRelacionesBI()
				.getCountUnidadesDocRe(relacionVO.getId());
		setInTemporalSession(request,
				TransferenciasConstants.CONTADOR_UNIDADES_DOCUMENTALES,
				new Integer(unidadesDocRe));

		setInTemporalSession(request, TransferenciasConstants.POSICION_UDOC,
				new Integer(unidadDocumental.getOrden()));

		if (relacionVO.getIdNivelDocumental() != null) {
			GestionCuadroClasificacionBI cuadroBI = services
					.lookupGestionCuadroClasificacionBI();
			if (cuadroBI.isSubtipoCaja(relacionVO.getIdNivelDocumental())) {
				subtipo = ElementoCuadroClasificacion.SUBTIPO_CAJA;
			}
		}

		// Si es una relaci�n con ficha
		if (!StringUtils.isEmpty(relacionVO.getIdFicha())) {

			if ((relacionVO.isAbierta() && !relacionVO.getIsIngresoDirecto())
					|| (relacionVO.isAbierta()
							&& relacionVO.getIsIngresoDirecto() && StringUtils
							.isEmpty(relacionVO.getIddeposito()))
					|| (relacionVO.isConErroresCotejo() && unidadDocumental
							.getPermitidoRealizarCambios()))
				forward = new ActionRedirect(
						mappings.findForward("nueva_udoc_relacion_con_ficha"));
			else
				forward = new ActionRedirect(
						mappings.findForward("ver_udoc_relacion_con_ficha"));

			forward.setRedirect(true);
			forward.addParameter(Constants.ID_FICHA, relacionVO.getIdFicha());
			forward.addParameter(Constants.ID, unidadDocumental.getId());
			if (subtipo == ElementoCuadroClasificacion.SUBTIPO_CAJA)
				forward.addParameter(Constants.CURRENT_INVOCATION,
						Constants.FSRE);
			else
				forward.addParameter(Constants.CURRENT_INVOCATION,
						Constants.UDOCRE);
		} else {
			String keyClientInvocation = KeysClientsInvocations.TRANSFERENCIAS_VER_UNIDAD_DOCUMENTAL;
			if (subtipo == ElementoCuadroClasificacion.SUBTIPO_CAJA)
				keyClientInvocation = KeysClientsInvocations.TRANSFERENCIAS_VER_FRACCION_SERIE;

			saveCurrentInvocation(keyClientInvocation, request);
		}
		setReturnActionFordward(request, forward);
	}

	public void infoUnidadDocumentalExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		boolean udocEnSesion = false;
		int subtipo = ElementoCuadroClasificacion.SUBTIPO_UNIDAD_DOCUMENTAL_SIMPLE;
		ActionRedirect forward = new ActionRedirect(
				mappings.findForward("info_udoc_relacion"));
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		GestionRelacionesEntregaBI relacionBI = services
				.lookupGestionRelacionesBI();
		UnidadDocumentalVO unidadDocumental = (UnidadDocumentalVO) getFromTemporalSession(
				request, TransferenciasConstants.UNIDAD_DOCUMENTAL);
		if (unidadDocumental != null)
			udocEnSesion = true;

		String navegacion = request.getParameter("navegacion");
		boolean isPrevOrNext = Constants.TRUE_STRING.equals(navegacion);

		if (!udocEnSesion || isPrevOrNext) {
			String udocID = request.getParameter("udocID");
			udocID = StringUtils.decodeUrlParameterValue(udocID);
			unidadDocumental = relacionBI.abrirUnidadDocumental(udocID);
		}

		// Informaci�n de la relaci�n de entrega
		RelacionEntregaVO relacionVO = relacionBI
				.abrirRelacionEntrega(unidadDocumental.getIdRelEntrega());

		// Contador de unidades documentales por relacion de entrega
		int unidadesDocRe = services.lookupGestionRelacionesBI()
				.getCountUnidadesDocRe(relacionVO.getId());
		setInTemporalSession(request,
				TransferenciasConstants.CONTADOR_UNIDADES_DOCUMENTALES,
				new Integer(unidadesDocRe));

		// Posicion que tiene la nueva unidad documental
		String posicion = (String) request.getParameter("numOrden");
		if (!StringUtils.isEmpty(posicion))
			setInTemporalSession(request,
					TransferenciasConstants.POSICION_UDOC,
					new Integer(posicion));
		else
			setInTemporalSession(request,
					TransferenciasConstants.POSICION_UDOC, new Integer(
							unidadDocumental.getOrden()));

		if (relacionVO.getIdNivelDocumental() != null) {
			GestionCuadroClasificacionBI cuadroBI = services
					.lookupGestionCuadroClasificacionBI();
			if (cuadroBI.isSubtipoCaja(relacionVO.getIdNivelDocumental()))
				subtipo = ElementoCuadroClasificacion.SUBTIPO_CAJA;
		}

		if (!udocEnSesion || isPrevOrNext) {
			// Si no es una relaci�n con ficha
			if (StringUtils.isEmpty(relacionVO.getIdFicha())) {
				ClientInvocation invocation = null;
				String keyClientInvocation = KeysClientsInvocations.TRANSFERENCIAS_VER_UNIDAD_DOCUMENTAL;

				if (relacionVO.getIdNivelDocumental() != null) {
					if (subtipo == ElementoCuadroClasificacion.SUBTIPO_CAJA)
						keyClientInvocation = KeysClientsInvocations.TRANSFERENCIAS_VER_FRACCION_SERIE;
				}
				invocation = saveCurrentInvocation(keyClientInvocation, request);
				invocation.setAsReturnPoint(true);
			}

			setInTemporalSession(request,
					TransferenciasConstants.UNIDAD_DOCUMENTAL,
					TransferenciasUnidadDocumentalToPO.getInstance(services)
							.transform(unidadDocumental));

		}

		// Si es una relaci�n con ficha
		if (!StringUtils.isEmpty(relacionVO.getIdFicha())) {
			if (((relacionVO.isAbierta() || relacionVO.isRechazada()) && !relacionVO
					.getIsIngresoDirecto())
					|| (relacionVO.isAbierta()
							&& relacionVO.getIsIngresoDirecto() && StringUtils
							.isEmpty(relacionVO.getIddeposito()))
					|| (relacionVO.isConErroresCotejo() && unidadDocumental
							.getPermitidoRealizarCambios()))
				forward = new ActionRedirect(
						mappings.findForward("nueva_udoc_relacion_con_ficha"));
			else
				forward = new ActionRedirect(
						mappings.findForward("ver_udoc_relacion_con_ficha"));
			forward.setRedirect(true);
			forward.addParameter(Constants.ID_FICHA, relacionVO.getIdFicha());
			forward.addParameter(Constants.ID, unidadDocumental.getId());
			if (subtipo == ElementoCuadroClasificacion.SUBTIPO_CAJA)
				forward.addParameter(Constants.CURRENT_INVOCATION,
						Constants.FSRE);
			else
				forward.addParameter(Constants.CURRENT_INVOCATION,
						Constants.UDOCRE);
		}

		setInTemporalSession(request,
				TransferenciasConstants.MODIFICACIONES_EN_TABS_DE_EXPEDIENTE,
				Boolean.FALSE);
		setReturnActionFordward(request, forward);

		// setReturnActionFordward(request,
		// mappings.findForward("info_udoc_relacion"));
	}

	public void editarUnidadDocumentalExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		UnidadDocumentalForm udocForm = (UnidadDocumentalForm) form;
		UnidadDocumentalVO udoc = (UnidadDocumentalVO) getFromTemporalSession(
				request, TransferenciasConstants.UNIDAD_DOCUMENTAL);

		// obtener la signatura de la udoc

		List partesUdoc = getServiceRepository(request)
				.lookupGestionRelacionesBI().getPartesUnidadDocumental(udoc);
		udocForm.populateForEdition(udoc, partesUdoc);
		setInTemporalSession(request,
				TransferenciasConstants.banderaEdicionCreacion, "M");
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		request.setAttribute("formatos", getPosiblesFormatos(services));

		GestionCuadroClasificacionBI cuadroBI = services
				.lookupGestionCuadroClasificacionBI();
		RelacionEntregaVO relacionEntrega = (RelacionEntregaVO) request
				.getSession()
				.getAttribute(TransferenciasConstants.RELACION_KEY);
		int subtipo = cuadroBI.getSubtipo(relacionEntrega
				.getIdNivelDocumental());
		if (subtipo == ElementoCuadroClasificacion.SUBTIPO_CAJA)
			saveCurrentInvocation(
					KeysClientsInvocations.TRANSFERENCIAS_VER_DATOS_FRACCION_SERIE,
					request);
		else
			saveCurrentInvocation(
					KeysClientsInvocations.TRANSFERENCIAS_NUEVA_UNIDAD_DOCUMENTAL,
					request);

		setReturnActionFordward(request,
				mappings.findForward("edicion_udoc_relacion"));
	}

	/**
	 * Guarda las modificaciones realizadas sobre la informacion de cabecera de
	 * una unidad documental tras haber realizado una edicion
	 */
	public void guardarUnidadDocumentalExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		UnidadDocumentalForm udocForm = (UnidadDocumentalForm) form;
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		GestionPrevisionesBI previsionesBI = services
				.lookupGestionPrevisionesBI();
		RelacionEntregaPO relacionEntrega = (RelacionEntregaPO) request
				.getSession()
				.getAttribute(TransferenciasConstants.RELACION_KEY);
		DetallePrevisionVO detallePrevision = null;
		List rangos = null;
		int subtipo = relacionEntrega.getNivelDocumental().getId() != null ? relacionEntrega
				.getNivelDocumental().getSubtipo()
				: ElementoCuadroClasificacion.SUBTIPO_UNIDAD_DOCUMENTAL_SIMPLE;

		if (relacionEntrega != null
				&& relacionEntrega.getIddetprevision() != null) {
			detallePrevision = previsionesBI
					.getDetallePrevision(relacionEntrega.getIddetprevision());
		}

		UnidadDocumentalVO udoc = (UnidadDocumentalVO) getFromTemporalSession(
				request, TransferenciasConstants.UNIDAD_DOCUMENTAL);

		/*
		 * if (relacionEntrega.getNivelDocumental() != null &&
		 * relacionEntrega.getNivelDocumental().getId()!=null &&
		 * relacionEntrega.getNivelDocumental().getId()
		 * .equals(ConfiguracionSistemaArchivoFactory
		 * .getConfiguracionSistemaArchivo
		 * ().getConfiguracionGeneral().getIdNivelFraccionSerie()))
		 */
		if (relacionEntrega.getNivelDocumental() != null
				&& subtipo == ElementoCuadroClasificacion.SUBTIPO_CAJA) {
			rangos = obtenerRangos(request.getParameterValues("campo_201"),
					request.getParameterValues("campo_202"));
			String numExpRangos = null;
			if (rangos != null && rangos.size() > 0) {
				udoc.resetRangos();
				Iterator it = rangos.iterator();
				while (it.hasNext()) {
					RangoVO rango = (RangoVO) it.next();

					numExpRangos = (numExpRangos == null ? "" : numExpRangos
							+ Constants.DELIMITADOR_RANGOS)
							+ rango.getDesde()
							+ Constants.DELIMITADOR_RANGO_INICIAL_FINAL
							+ rango.getHasta();
					udoc.addRango(rango);
				}
				udoc.setNumeroExpediente(numExpRangos);
			} else {
				udoc.resetRangos();
				udoc.setNumeroExpediente(null);
			}
		} else
			udoc.setNumeroExpediente(udocForm.getNumeroExpediente());

		ActionErrors errores = validarUdocForm(udocForm,
				detallePrevision != null ? detallePrevision.getAnoIniUdoc()
						: null,
				detallePrevision != null ? detallePrevision.getAnoFinUdoc()
						: null, relacionEntrega, request);
		if (errores.size() > 0) {
			// Aqu� almacenamos los rangos independientemente en caso de error
			// en introducci�n de datos
			if (rangos != null && rangos.size() > 0)
				setInTemporalSession(request,
						TransferenciasConstants.LISTA_RANGOS_UDOC, rangos);
			ErrorsTag.saveErrors(request, errores);
			nuevaUnidadDocumentalExecuteLogic(mappings, request, subtipo);
			return;
		}

		GestionRelacionesEntregaBI relacionesBI = services
				.lookupGestionRelacionesBI();

		udoc.setProductor(udocForm.getIdProductor(),
				udocForm.getNombreProductor(), null);

		try {
			udoc.setFechaInicio(udocForm.getFechaInicioAsDate());
			udoc.setFechaFin(udocForm.getFechaFinAsDate());
		} catch (ParseException pe) {
			// NO DEBERIA DARSE PORQUE SE HA HECHO VALIDACION PREVIA
		}
		udoc.setAsunto(udocForm.getAsunto());
		try {
			String signaturaUdoc = udocForm.getSignaturaUDoc() != null ? udocForm
					.getSignaturaUDoc() : null;
			String signaturaUI = udocForm.getSignaturaUI() != null ? udocForm
					.getSignaturaUI() : signaturaUdoc;
			relacionesBI.modificarUnidadDocumental(relacionEntrega, udoc,
					signaturaUI, signaturaUdoc);
			// goBackExecuteLogic(mappings, form, request, response);
			popLastInvocation(request);

			udoc = relacionesBI.getUnidadDocumental(udoc.getId());
			udoc.setPermitidoRealizarCambios(true);
			setInTemporalSession(request,
					TransferenciasConstants.UNIDAD_DOCUMENTAL,
					TransferenciasUnidadDocumentalToPO.getInstance(services)
							.transform(udoc));

			setReturnActionFordward(request,
					mappings.findForward("redirect_to_view_udoc"));

		} catch (ActionNotAllowedException anae) {
			guardarError(request, anae);
			setReturnActionFordward(request,
					mappings.findForward("edicion_udoc_relacion"));
		}

	}

	/**
	 * Guarda los cambios realizado sobre los interesados, emplazamientos y
	 * documentos asociados a una unidad documental
	 */
	public void guardarCambiosExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		UnidadDocumentalVO udoc = (UnidadDocumentalVO) getFromTemporalSession(
				request, TransferenciasConstants.UNIDAD_DOCUMENTAL);
		RelacionEntregaVO relacionEntrega = (RelacionEntregaVO) getFromTemporalSession(
				request, TransferenciasConstants.RELACION_KEY);
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		GestionRelacionesEntregaBI relacionesBI = services
				.lookupGestionRelacionesBI();
		try {
			relacionesBI.modificarInformacionUnidadDocumental(relacionEntrega,
					udoc);
		} catch (ActionNotAllowedException anae) {
			guardarError(request, anae);
		}
		setInTemporalSession(request,
				TransferenciasConstants.MODIFICACIONES_EN_TABS_DE_EXPEDIENTE,
				Boolean.FALSE);
		// if (StringUtils.isEmpty(relacionEntrega.getIdFicha()))
		setReturnActionFordward(request,
				mappings.findForward("info_udoc_relacion"));
		/*
		 * else setReturnActionFordward(request,
		 * mappings.findForward("nueva_udoc_relacion_con_ficha"));
		 */
	}

	public void guardarCambiosYNuevaConFichaExecuteLogic(
			ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		// ServiceRepository services =
		// ServiceRepository.getInstance(ServiceClient.create(getAppUser(request)));
		// GestionRelacionesEntregaBI relacionesBI =
		// services.lookupGestionRelacionesBI();
		UnidadDocumentalForm formulario = (UnidadDocumentalForm) form;
		UnidadDocumentalVO udoc = (UnidadDocumentalVO) getFromTemporalSession(
				request, TransferenciasConstants.UNIDAD_DOCUMENTAL);
		udoc.setNumeroPartes(1);
		// RelacionEntregaVO relacionEntrega = (RelacionEntregaVO)
		// getFromTemporalSession(request,
		// TransferenciasConstants.RELACION_KEY);

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
			setInTemporalSession(
					request,
					TransferenciasConstants.FLAG_CREACION_UDOC_REL_MANTENER_INFORMACION,
					mantenerInformacion);
			if (formulario.getIntMantenerInformacion() == 0) {
				// removeInTemporalSession(request,
				// TransferenciasConstants.UNIDAD_DOCUMENTAL);
				addUdocsExecuteLogic(mappings, form, request, response);
			} else {
				crearUnidadDocumentalConFicha(udoc, mappings, form, request,
						response);
			}
			ServiceRepository services = ServiceRepository
					.getInstance(ServiceClient.create(getAppUser(request)));
			GestionRelacionesEntregaBI relacionBI = services
					.lookupGestionRelacionesBI();
			RelacionEntregaVO relacion = relacionBI.getRelacionXIdRelacion(udoc
					.getIdRelEntrega());
			if (relacion != null) {
				if (relacion.isRechazada()) {
					relacion.setEstado(EstadoREntrega.ABIERTA
							.getIdentificador());
					relacionBI.updateRelacion(relacion);
				}
			}
			relacion = (RelacionEntregaVO) getFromTemporalSession(request,
					TransferenciasConstants.RELACION_KEY);
			if (relacion != null && relacion.isRechazada())
				relacion.setEstado(EstadoREntrega.ABIERTA.getIdentificador());

		} catch (ActionNotAllowedException anae) {
			guardarError(request, anae);
			setReturnActionFordward(request,
					mappings.findForward("nueva_udoc_relacion_con_ficha"));
		}
	}

	public void crearUnidadDocumentalConFicha(UnidadDocumentalVO udoc,
			ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ActionNotAllowedException {

		UnidadDocumentalForm udocForm = (UnidadDocumentalForm) form;
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		GestionRelacionesEntregaBI relacionesBI = services
				.lookupGestionRelacionesBI();
		// UnidadDocumentalVO udoc = (UnidadDocumentalVO)
		// getFromTemporalSession(request,
		// TransferenciasConstants.UNIDAD_DOCUMENTAL);
		RelacionEntregaPO infoRelacion = (RelacionEntregaPO) getFromTemporalSession(
				request, TransferenciasConstants.RELACION_KEY);
		int subtipo = infoRelacion.getNivelDocumental().getId() != null ? infoRelacion
				.getNivelDocumental().getSubtipo()
				: ElementoCuadroClasificacion.SUBTIPO_UNIDAD_DOCUMENTAL_SIMPLE;
		ActionRedirect forward = null;

		if (infoRelacion.isSignaturaSolictableEnUDoc()) {
			popLastInvocation(request);
			removeInTemporalSession(request,
					TransferenciasConstants.UNIDAD_DOCUMENTAL);
			// Prueba para intentar conservar los valores de fracciones de serie
			// o formatos q no sean multidoc con ficha en el caso
			// de relaciones con signatura
			// Si estamos manejando una fracci�n de serie, obtenemos de nuevo la
			// unidad documental para poder tener su informaci�n descriptiva
			// porque necesitamos los rangos que no est�n en el objeto de sesi�n
			if (subtipo == ElementoCuadroClasificacion.SUBTIPO_CAJA) {
				udoc = relacionesBI
						.getUnidadDocumentalConInfoDesc(udoc.getId());
				setInTemporalSession(request,
						TransferenciasConstants.LISTA_RANGOS_UDOC, udoc
								.getExtraInfo().getRangos());
			}

			setInTemporalSession(request,
					TransferenciasConstants.UNIDAD_DOCUMENTAL_A_DUPLICAR, udoc);

			//
			udocForm.populateForEdition(udoc, new ArrayList());
			addUdocsExecuteLogic(mappings, form, request, response);
		} else {
			String idUDocOriginal = udoc.getId();

			List udocsRelacion = relacionesBI
					.fetchRowsByCodigoRelacionOrderByOrden(infoRelacion.getId());
			udoc.setOrden(getNextOrden(udocsRelacion));

			relacionesBI.nuevaUnidadDocumental(infoRelacion, udoc, null,
					subtipo, 1, false);
			relacionesBI.conservarDescripcion(idUDocOriginal, udoc.getId());

			// Contador de unidades documentales por relacion de entrega
			int unidadesDocRe = services.lookupGestionRelacionesBI()
					.getCountUnidadesDocRe(infoRelacion.getId());
			setInTemporalSession(request,
					TransferenciasConstants.CONTADOR_UNIDADES_DOCUMENTALES,
					new Integer(unidadesDocRe));

			forward = new ActionRedirect(
					mappings.findForward("nueva_udoc_relacion_con_ficha"));
			forward.setRedirect(true);
			forward.addParameter(Constants.ID_FICHA, infoRelacion.getIdFicha());
			forward.addParameter(Constants.ID, udoc.getId());
			if (infoRelacion.getNivelDocumental() != null
					&& subtipo == ElementoCuadroClasificacion.SUBTIPO_CAJA)
				forward.addParameter(Constants.CURRENT_INVOCATION,
						Constants.FSRE);
			else
				forward.addParameter(Constants.CURRENT_INVOCATION,
						Constants.UDOCRE);

			// Modificar en la invocaci�n anterior el valor del objeto unidad
			// documental
			// popLastInvocation(request);
			setInTemporalSession(request,
					TransferenciasConstants.UNIDAD_DOCUMENTAL,
					TransferenciasUnidadDocumentalToPO.getInstance(services)
							.transform(udoc));

			setReturnActionFordward(request, forward);
		}

	}

	public void guardarCambiosYNuevaExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		UnidadDocumentalVO udoc = (UnidadDocumentalVO) getFromTemporalSession(
				request, TransferenciasConstants.UNIDAD_DOCUMENTAL);
		RelacionEntregaVO relacionEntrega = (RelacionEntregaVO) getFromTemporalSession(
				request, TransferenciasConstants.RELACION_KEY);
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		UnidadDocumentalForm formulario = (UnidadDocumentalForm) form;
		GestionRelacionesEntregaBI relacionesBI = services
				.lookupGestionRelacionesBI();
		// popLastInvocation(request);

		// Eliminar problema al duplicar con el n�mero de partes
		udoc.setNumeroPartes(1);
		setInTemporalSession(request,
				TransferenciasConstants.UNIDAD_DOCUMENTAL,
				TransferenciasUnidadDocumentalToPO.getInstance(services)
						.transform(udoc));

		try {
			relacionesBI.modificarInformacionUnidadDocumental(relacionEntrega,
					udoc);
			setInTemporalSession(
					request,
					TransferenciasConstants.FLAG_CREACION_UDOC_REL_MANTENER_INFORMACION,
					formulario.getMantenerInformacion());
			if (formulario.getIntMantenerInformacion() == 0)
				removeInTemporalSession(request,
						TransferenciasConstants.UNIDAD_DOCUMENTAL);
			else {
				formulario.populateForEdition(udoc, new ArrayList());
			}

			addUdocsExecuteLogic(mappings, form, request, response);
			// ActionRedirect forwardToNewUdoc = new
			// ActionRedirect(mappings.findForward("edicion_udoc_relacion"));
			// forwardToNewUdoc.setRedirect(true);
			// forwardToNewUdoc.addParameter("method", "addUdocs");
			// setReturnActionFordward(request, forwardToNewUdoc);
		} catch (ActionNotAllowedException anae) {
			guardarError(request, anae);
			setReturnActionFordward(request,
					mappings.findForward("info_udoc_relacion"));
		}
		setInTemporalSession(request,
				TransferenciasConstants.MODIFICACIONES_EN_TABS_DE_EXPEDIENTE,
				Boolean.FALSE);
	}

	public void nuevoDocumentoExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		saveCurrentInvocation(KeysClientsInvocations.DOCUMENTO, request);
		setReturnActionFordward(request,
				mappings.findForward("edicion_documento"));
	}

	public void infoDocumentoExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String itemIndexParameter = (String) request.getParameter("itemIndex");
		String pTipoDocumento = request.getParameter("tipoDocumento");
		if (itemIndexParameter != null) {
			UnidadDocumentalVO udoc = (UnidadDocumentalVO) getFromTemporalSession(
					request, TransferenciasConstants.UNIDAD_DOCUMENTAL);
			List documentos = udoc.getDocumentos(Integer
					.parseInt(pTipoDocumento));
			request.setAttribute(TransferenciasConstants.DOCUMENTO,
					documentos.get(Integer.parseInt(itemIndexParameter)));
			saveCurrentInvocation(KeysClientsInvocations.DOCUMENTO, request);
			setReturnActionFordward(request,
					mappings.findForward("info_documento"));
		} else
			setReturnActionFordward(request,
					mappings.findForward("info_udoc_relacion"));
	}

	public void editarDocumentoExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String itemIndexParameter = request.getParameter("itemIndex");
		String pTipoDocumento = request.getParameter("tipoDocumento");
		if (itemIndexParameter != null) {
			UnidadDocumentalVO udoc = (UnidadDocumentalVO) getFromTemporalSession(
					request, TransferenciasConstants.UNIDAD_DOCUMENTAL);
			DocumentoVO documento = (DocumentoVO) udoc.getDocumentos(
					Integer.parseInt(pTipoDocumento)).get(
					Integer.parseInt(itemIndexParameter));
			request.setAttribute(TransferenciasConstants.DOCUMENTO, documento);
			UnidadDocumentalForm udocForm = (UnidadDocumentalForm) form;
			udocForm.setTituloDocumento(documento.getNombre());
			udocForm.setDescripcionDocumento(documento.getDescripcion());
			saveCurrentInvocation(KeysClientsInvocations.DOCUMENTO, request);
			setReturnActionFordward(request,
					mappings.findForward("edicion_documento"));
		} else
			setReturnActionFordward(request,
					mappings.findForward("info_udoc_relacion"));

	}

	public void agregarDocumentoExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		UnidadDocumentalForm udocForm = (UnidadDocumentalForm) form;
		if (udocForm.getTituloDocumento() == null) {
			obtenerErrores(request, true).add(
					Constants.ERROR_REQUIRED,
					new ActionError(Constants.ERROR_REQUIRED,
							"Tipo de documento"));
			setReturnActionFordward(request,
					mappings.findForward("edicion_documento"));
		} else {
			UnidadDocumentalVO udoc = (UnidadDocumentalVO) request.getSession()
					.getAttribute(TransferenciasConstants.UNIDAD_DOCUMENTAL);
			udoc.addDocumento(new DocumentoVO(udocForm.getTituloDocumento(),
					udocForm.getDescripcionDocumento()));
			setInTemporalSession(request,
					TransferenciasConstants.FLAG_MODIFICADA_UDOC_RELACION, "1");
			popLastInvocation(request);
			setReturnActionFordward(request,
					mappings.findForward("info_udoc_relacion"));
		}
		setInTemporalSession(request,
				TransferenciasConstants.MODIFICACIONES_EN_TABS_DE_EXPEDIENTE,
				Boolean.TRUE);
	}

	public void guardarDocumentoExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String itemIndexParameter = (String) request.getParameter("itemIndex");
		String pTipoDocumento = request.getParameter("tipoDocumento");
		UnidadDocumentalForm udocForm = (UnidadDocumentalForm) form;
		if (udocForm.getTituloDocumento() == null) {
			obtenerErrores(request, true).add(
					Constants.ERROR_REQUIRED,
					new ActionError(Constants.ERROR_REQUIRED,
							"Tipo de documento"));
			setReturnActionFordward(request,
					mappings.findForward("edicion_documento"));
		} else {
			UnidadDocumentalVO udoc = (UnidadDocumentalVO) request.getSession()
					.getAttribute(TransferenciasConstants.UNIDAD_DOCUMENTAL);
			List documentos = udoc.getDocumentos(Integer
					.parseInt(pTipoDocumento));
			DocumentoVO documento = (DocumentoVO) documentos.get(Integer
					.parseInt(itemIndexParameter));
			documento.setNombre(udocForm.getTituloDocumento());
			documento.setDescripcion(udocForm.getDescripcionDocumento());

			setInTemporalSession(request,
					TransferenciasConstants.FLAG_MODIFICADA_UDOC_RELACION, "1");
			popLastInvocation(request);
			setReturnActionFordward(request,
					mappings.findForward("info_udoc_relacion"));
		}
	}

	public void eliminarDocumentosExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String pTipoDocumento = request.getParameter("tipoDocumento");
		UnidadDocumentalForm udocForm = (UnidadDocumentalForm) form;
		String[] documentosSeleccionados = udocForm.getSeleccionDocumento();
		if (documentosSeleccionados != null) {
			UnidadDocumentalVO udoc = (UnidadDocumentalVO) request.getSession()
					.getAttribute(TransferenciasConstants.UNIDAD_DOCUMENTAL);

			List documentos = udoc.getDocumentos(Integer
					.parseInt(pTipoDocumento));
			List documentosToRemove = new ArrayList();
			for (int i = 0; i < documentosSeleccionados.length; i++)
				documentosToRemove.add(documentos.get(Integer
						.parseInt(documentosSeleccionados[i]) - 1));
			for (Iterator i = documentosToRemove.iterator(); i.hasNext();)
				documentos.remove(i.next());
			setInTemporalSession(request,
					TransferenciasConstants.FLAG_MODIFICADA_UDOC_RELACION, "1");
		}
		setInTemporalSession(request,
				TransferenciasConstants.MODIFICACIONES_EN_TABS_DE_EXPEDIENTE,
				Boolean.TRUE);
		setReturnActionFordward(request,
				mappings.findForward("info_udoc_relacion"));
	}

	public void verPosiblesProductoresExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(getAppUser(request)));
		RelacionEntregaVO relacionEntrega = (RelacionEntregaVO) request
				.getSession()
				.getAttribute(TransferenciasConstants.RELACION_KEY);
		GestionSeriesBI seriesBI = services.lookupGestionSeriesBI();
		GestionCuadroClasificacionBI cuadroBI = services
				.lookupGestionCuadroClasificacionBI();

		List posiblesProductores = seriesBI.getProductoresSerie(
				relacionEntrega.getIdseriedestino(), true);
		request.setAttribute(TransferenciasConstants.LISTA_PRODUCTORES,
				posiblesProductores);

		nuevaUnidadDocumentalExecuteLogic(mappings, request,
				cuadroBI.getSubtipo(relacionEntrega.getIdNivelDocumental()));
	}

	public void updateDocumentosIncluidosExecuteLogic(ActionMapping mappings,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		UnidadDocumentalForm udocForm = (UnidadDocumentalForm) form;
		String[] documentosSeleccionados = udocForm.getSeleccionDocumento();
		if (documentosSeleccionados != null) {
			UnidadDocumentalVO udoc = (UnidadDocumentalVO) request.getSession()
					.getAttribute(TransferenciasConstants.UNIDAD_DOCUMENTAL);
			String pTipoDocumento = request.getParameter("tipoDocumento");
			List documentos = udoc.getDocumentos(Integer
					.parseInt(pTipoDocumento));
			Arrays.sort(documentosSeleccionados);
			for (int i = 0, count = 1; i < documentosSeleccionados.length; i++, count++) {
				int numSiguienteDocumentoPresente = Integer
						.parseInt(documentosSeleccionados[i]);
				for (; count < numSiguienteDocumentoPresente; count++)
					((DocumentoVO) documentos.get(count - 1))
							.setIncluidoEnTransferencia(false);
			}
		}
		setReturnActionFordward(request,
				mappings.findForward("info_udoc_relacion"));
	}

	public void volverAvistaUnidadDocumentalExecuteLogic(
			ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		popLastInvocation(request);
		setReturnActionFordward(request,
				mappings.findForward("info_udoc_relacion"));
	}

	public void editarDescripcionContenidoCajaExecuteLogic(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		String idUInstalacion = request.getParameter("idUInstalacion");

		// Informaci�n de la unidad de instalaci�n
		UnidadInstalacionPO2 uInstalacion = (UnidadInstalacionPO2) UnidadInstalacionToPO2
				.getInstance(getServiceRepository(request)).transform(
						getGestionRelacionesBI(request).getUnidadInstalacion(
								idUInstalacion));

		// Informaci�n de la caja
		setInTemporalSession(request, TransferenciasConstants.CAJA_KEY,
				uInstalacion);

		saveCurrentInvocation(
				KeysClientsInvocations.TRANSFERENCIAS_EDICION_DESCRIPCION_CONTENIDO_CAJA,
				request);
		setReturnActionFordward(request,
				mapping.findForward("edicion_descripcion_contenido_caja"));
	}

	public void editarDescripcionContenidoParteExecuteLogic(
			ActionMapping mappings, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		UnidadDocumentalForm unidadDocumentalForm = (UnidadDocumentalForm) form;
		ServiceRepository services = getServiceRepository(request);
		/* GestionRelacionesEntregaBI relacionBI = */services
				.lookupGestionRelacionesBI();
		String[] udocsSeleccionadas = unidadDocumentalForm.getSelectedUdoc();
		if (udocsSeleccionadas != null && udocsSeleccionadas.length == 1) {
			UnidadInstalacionPO2 cajaPO = (UnidadInstalacionPO2) getFromTemporalSession(
					request, TransferenciasConstants.CAJA_KEY);

			String posSeleccionada = udocsSeleccionadas[0];
			IParteUnidadDocumentalVO parteUDoc = (IParteUnidadDocumentalVO) CollectionUtils
					.find(cajaPO.getContenido(), new PredicateFinderByPos(
							TypeConverter.toInt(posSeleccionada)));

			setInTemporalSession(request,
					TransferenciasConstants.PARTE_UDOC_SELECCIONADA, parteUDoc);

			setReturnActionFordward(request,
					mappings.findForward("edicion_descripcion_contenido_parte"));

			saveCurrentInvocation(
					KeysClientsInvocations.TRANSFERENCIAS_EDICION_DESCRIPCION_PARTE,
					request);

		} else if (udocsSeleccionadas == null || udocsSeleccionadas.length == 0) {
			ActionErrors errors = new ActionErrors();
			errors.add(
					ActionErrors.GLOBAL_MESSAGE,
					new ActionError(
							Messages.getString(
									TransferenciasConstants.NECESARIO_SELECCIONAR_EXPEDIENTES_PARA_LA_CAJA,
									request.getLocale())));
			ErrorsTag.saveErrors(request, errors);
			setReturnActionFordward(request,
					mappings.findForward("edicion_descripcion_contenido_caja"));
		} else {
			ActionErrors errors = new ActionErrors();
			errors.add(
					ActionErrors.GLOBAL_MESSAGE,
					new ActionError(
							Messages.getString(
									TransferenciasConstants.ERROR_TRANSFERENCIAS_SEL_UN_EXPEDIENTE,
									request.getLocale())));
			ErrorsTag.saveErrors(request, errors);
			setReturnActionFordward(request,
					mappings.findForward("edicion_descripcion_contenido_caja"));
		}
	}

	private class PredicateFinderByPos implements Predicate {
		int posToFind;

		PredicateFinderByPos(int pos) {
			posToFind = pos;
		}

		/*
		 * (non-Javadoc)
			*
		 * @see
		 * org.apache.commons.collections.Predicate#evaluate(java.lang.Object)
		 */
		public boolean evaluate(Object arg0) {
			IParteUnidadDocumentalVO aPart = (IParteUnidadDocumentalVO) arg0;
			return aPart.getPosUdocEnUI() == posToFind;
		}

	}

	private List getPosiblesFormatos(ServiceRepository services) {
		GestorEstructuraDepositoBI depositoBI = services
				.lookupGestorEstructuraDepositoBI();
		try {
			return depositoBI.getFormatosVigentes();
		} catch (Exception e) {
			throw new UncheckedArchivoException(e);
		}
	}

}