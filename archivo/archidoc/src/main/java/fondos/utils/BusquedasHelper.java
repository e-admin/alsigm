package fondos.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import se.NotAvailableException;
import se.procedimientos.exceptions.GestorCatalogoException;
import se.usuarios.AppUser;
import se.usuarios.ServiceClient;
import util.CollectionUtils;
import util.ErrorsTag;
import util.StringOwnTokenizer;
import xml.XMLObject;
import xml.config.Busqueda;
import xml.config.CampoBusqueda;
import xml.config.RestriccionCampoBusqueda;
import auditoria.ArchivoDetails;

import common.Constants;
import common.Messages;
import common.bi.GestionDescripcionBI;
import common.bi.GestionFondosBI;
import common.bi.ServiceRepository;
import common.util.ArrayUtils;
import common.util.CustomDate;
import common.util.CustomDateFormat;
import common.util.CustomDateRange;
import common.util.ListUtils;
import common.util.StringUtils;
import common.vos.IKeyValue;
import common.vos.KeyValueVO;

import descripcion.model.TipoCampo;
import descripcion.model.TipoNorma;
import fondos.FondosConstants;
import fondos.forms.IBusquedaForm;
import fondos.model.CamposBusquedas;
import fondos.model.ElementoCuadroClasificacion;
import fondos.model.PrecondicionesBusquedaFondosGenerica;
import fondos.model.RestriccionesCamposBusquedas;
import fondos.vos.BusquedaElementosVO;
import fondos.vos.INivelCFVO;

public class BusquedasHelper {

	/**
	 * Carga las listas necesarias según la búsqueda
	 * 
	 * @param busqueda
	 *            objeto de configuración de la búsqueda
	 * @param form
	 *            formulario de busqueda
	 * @param request
	 *            petición actual
	 * @param precondiciones
	 *            de la busqueda
	 */
	public static void loadListasBusqueda(Busqueda busqueda,
			IBusquedaForm form, HttpServletRequest request,
			PrecondicionesBusquedaFondosGenerica precondiciones) {

		AppUser appUser = (AppUser) request.getSession().getAttribute(
				Constants.USUARIOKEY);
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(appUser));
		String postBack = form.getPostBack();

		List nivelesSeleccionados = new ArrayList();

		if (busqueda.getMapEntrada().containsKey(
				CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_NIVELES_DESCRIPCION)) {

			List niveles = getNivelesCf(request, services);

			if (busqueda.isActivarNiveles()
					&& !Constants.TRUE_STRING.equals(postBack)) {
				form.setNiveles(getIdsAllNivelesCf(request, services));
			}

			String[] idsNiveles = form.getNiveles();

			// Seleccionar los valores
			if (ArrayUtils.isNotEmpty(idsNiveles)) {
				if (ListUtils.isNotEmpty(niveles)) {
					for (Iterator iterator = niveles.iterator(); iterator
							.hasNext();) {
						INivelCFVO nivelCfVO = (INivelCFVO) iterator.next();

						if (nivelCfVO != null
								&& ArrayUtils.contains(idsNiveles,
										nivelCfVO.getId())) {
							nivelesSeleccionados.add(nivelCfVO);
						}
					}
				}
			}
		}

		// Estados
		if (busqueda.getMapEntrada().containsKey(
				CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_ESTADO)
				&& !Constants.TRUE_STRING.equals(postBack)) {
			// Seleccionar los valores
			if (busqueda.isActivarEstados()) {
				form.setEstados(new String[] {
						FondosConstants.ESTADO_NO_VIGENTE,
						FondosConstants.ESTADO_VIGENTE });
			}
		}

		// Bloqueos
		if (busqueda.getMapEntrada().containsKey(
				CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_BLOQUEO)
				&& !Constants.TRUE_STRING.equals(postBack)) {
			// Seleccionar los valores
			if (busqueda.isActivarBloqueos()) {
				form.setBloqueos(new String[] {
						FondosConstants.UDOC_DESBLOQUEADA,
						FondosConstants.UDOC_BLOQUEADA });
			}
		}

		// Fondos vigentes
		if (busqueda.getMapEntrada().containsKey(
				CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_FONDO)) {
			request.getSession().setAttribute(FondosConstants.LISTA_FONDOS_KEY,
					services.lookupGestionFondosBI().getFondosVigentes());
		}

		if (busqueda.getMapEntrada().containsKey(
				CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_CONDICIONES_AVANZADAS)) {
			GestionDescripcionBI descBI = services.lookupGestionDescripcionBI();

			boolean campoNiveles = false;
			if (busqueda.getMapEntrada().containsKey(
					CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_NIVELES_DESCRIPCION)) {
				campoNiveles = true;
			}

			List niveles = getNivelesCf(request, services);

			int numTotalNiveles = 0;
			if (niveles != null) {
				numTotalNiveles = niveles.size();
			}

			List fichas = cargarListaFichas(request, nivelesSeleccionados,
					precondiciones, descBI, numTotalNiveles, campoNiveles);
			request.getSession().setAttribute(FondosConstants.LISTA_FICHAS_KEY,
					fichas);

			// Lista de campos de la ficha
			String idFicha = form.getIdFicha();
			if (StringUtils.isBlank(idFicha)
					&& !CollectionUtils.isEmpty(fichas)) {
				// Por defecto mostrar la última (udocs)
				idFicha = ((IKeyValue) fichas.get(fichas.size() - 1)).getKey();

				form.setIdFicha(idFicha);
			}

			if (StringUtils.isNotBlank(idFicha)) {

				List listaCampos;

				if (idFicha.equals(Constants.STRING_CERO)) {
					listaCampos = descBI
							.getCamposBusquedaAvanzada(Constants.STRING_EMPTY);
				} else {
					listaCampos = descBI.getCamposBusquedaAvanzada(idFicha);
				}

				request.getSession().setAttribute(
						FondosConstants.LISTA_CAMPOS_FICHA_KEY, listaCampos);
			}
		}
	}

	private static List getNivelesCf(HttpServletRequest request,
			ServiceRepository services) {
		List niveles = (List) request.getSession().getAttribute(
				FondosConstants.LISTA_NIVELES_KEY);

		if (niveles == null) {
			niveles = services.lookupGestionCuadroClasificacionBI()
					.getNivelesCF();
			request.getSession().setAttribute(
					FondosConstants.LISTA_NIVELES_KEY, niveles);
		}

		return niveles;

	}

	/**
	 * Obtiene los identificadores de todos los niveles del cuadro.
	 * 
	 * @return
	 */
	private static String[] getIdsAllNivelesCf(HttpServletRequest request,
			ServiceRepository services) {
		String[] ids = null;

		List niveles = getNivelesCf(request, services);

		if (ListUtils.isNotEmpty(niveles)) {
			for (Iterator iterator = niveles.iterator(); iterator.hasNext();) {
				INivelCFVO nivelCFVO = (INivelCFVO) iterator.next();

				if (nivelCFVO != null) {
					ids = (String[]) ArrayUtils.add(ids, nivelCFVO.getId());
				}
			}
		}

		return ids;
	}

	private static List cargarListaFichas(HttpServletRequest request,
			List nivelesSeleccionados,
			PrecondicionesBusquedaFondosGenerica precondiciones,
			GestionDescripcionBI descBI, int numTotalNiveles,
			boolean campoNiveles) {
		List listaFichas = new ArrayList();

		if ((precondiciones != null)
				&& (!ArrayUtils.isEmpty(precondiciones.getTiposNivelFicha()))) {
			ArrayUtils.add(precondiciones.getTiposNivelFicha(),
					ElementoCuadroClasificacion.TIPO_ALL);
			listaFichas = descBI.getFichasByTiposNivel(precondiciones
					.getTiposNivelFicha());
		} else {
			if (ListUtils.isEmpty(nivelesSeleccionados)) {
				listaFichas = descBI.getFichasByTipoNormaYNiveles(
						TipoNorma.NORMA_ISADG,
						new int[] { ElementoCuadroClasificacion.TIPO_ALL });
			} else {
				listaFichas = descBI
						.getFichasByTiposNivelIdFichaPref(nivelesSeleccionados);
			}
		}

		if (ListUtils.isEmpty(listaFichas)
				|| (nivelesSeleccionados != null && nivelesSeleccionados.size() == numTotalNiveles)) {
			listaFichas.add(
					0,
					new KeyValueVO(Constants.OPCION_TODOS, Messages.getString(
							Constants.LABEL_TODOS_LOS_CAMPOS,
							request.getLocale())));
		}

		return listaFichas;
	}

	/**
	 * Método común para las búsquedas de procedimientos
	 * 
	 * @param mappings
	 * @param form
	 * @param request
	 * @param response
	 */
	public static void buscarProcedimientoComunLogic(ActionMapping mappings,
			IBusquedaForm form, HttpServletRequest request,
			HttpServletResponse response) {

		AppUser appUser = (AppUser) request.getSession().getAttribute(
				Constants.USUARIOKEY);
		ServiceRepository services = ServiceRepository
				.getInstance(ServiceClient.create(appUser));
		GestionFondosBI fondosBI = services.lookupGestionFondosBI();

		// Obtener el título y el tipo de procedimiento
		String pTitulo = request.getParameter("tituloProcedimiento");
		String pTipoProcedimiento = request.getParameter("tipoProcedimiento");
		ActionErrors errores = (ActionErrors) request.getSession()
				.getAttribute(ErrorsTag.KEY_ERRORS);
		try {

			List procedimientos = fondosBI.findProcedimientosBusqueda(
					Integer.parseInt(pTipoProcedimiento), pTitulo);
			request.setAttribute(FondosConstants.LISTA_PROCEDIMIENTOS_KEY,
					procedimientos);
		} catch (GestorCatalogoException gce) {
			if (errores == null) {
				errores = new ActionErrors();
				errores.add(ActionErrors.GLOBAL_ERROR, new ActionError(
						Constants.ERROR_GESTOR_CATALOGO));
				request.getSession()
						.setAttribute(ErrorsTag.KEY_ERRORS, errores);
			}
		} catch (NotAvailableException e) {
			if (errores == null) {
				errores = new ActionErrors();
				errores.add(ActionErrors.GLOBAL_ERROR, new ActionError(
						Constants.ERROR_GESTOR_CATALOGO));
				request.getSession()
						.setAttribute(ErrorsTag.KEY_ERRORS, errores);
			}
		}
		Busqueda busqueda = (Busqueda) request.getSession().getAttribute(
				FondosConstants.CFG_BUSQUEDA_KEY);
		loadListasBusqueda(busqueda, form, request, null);
	}

	/**
	 * Valida el formulario
	 * 
	 * @param mapping
	 *            {@link ActionMapping} con los mapeos asociado.
	 * @param request
	 *            {@link HttpServletRequest}
	 */
	public static ActionErrors validateCampos(ActionMapping mapping,
			HttpServletRequest request, Busqueda busqueda,
			IBusquedaForm busquedaForm) {
		ActionErrors errors = new ActionErrors();
		if (busqueda != null) {

			// Validar Campos Genéricos de Tipo Numérico
			if (!ArrayUtils.isEmpty(busquedaForm.getGenericoIdCampoNumerico())) {
				for (int i = 0; i < busquedaForm.getGenericoIdCampoNumerico().length; i++) {
					String valor = busquedaForm.getGenericoCampoNumerico()[i];
					String valorFin = busquedaForm
							.getGenericoCampoNumericoFin()[i];

					if (StringUtils.isNotBlank(valor)
							&& !NumberUtils.isNumber(valor)) {
						errors.add(
								ActionErrors.GLOBAL_MESSAGE,
								new ActionError(
										Constants.ERROR_INVALID,
										Messages.getString(
												busquedaForm
														.getGenericoEtiquetaCampoNumerico()[i],
												request.getLocale())));
					}

					// Comprobar si es Rango
					if (busquedaForm.getGenericoOperadorCampoNumerico()[i]
							.equals(CustomDateFormat.DATE_OPERATOR_RANGE)) {
						// Comprobar que el segundo valor sea numérico
						if (StringUtils.isNotBlank(valorFin)
								&& !NumberUtils.isNumber(valorFin)) {
							errors.add(
									ActionErrors.GLOBAL_MESSAGE,
									new ActionError(
											Constants.ERROR_INVALID,
											Messages.getString(
													busquedaForm
															.getGenericoEtiquetaCampoNumerico()[i],
													request.getLocale())));
						}
					}

				}
			}

			// Validar Campos Genéricos de Fecha
			if (!ArrayUtils.isEmpty(busquedaForm.getGenericoIdFecha())) {
				for (int i = 0; i < busquedaForm.getGenericoIdFecha().length; i++) {

					try {

						if (!new CustomDate(
								busquedaForm.getGenericoFechaFormato()[i],
								busquedaForm.getGenericoFechaA()[i],
								busquedaForm.getGenericoFechaM()[i],
								busquedaForm.getGenericoFechaD()[i],
								busquedaForm.getGenericoFechaS()[i]).validate()
								|| !new CustomDate(
										busquedaForm
												.getGenericoFechaIniFormato()[i],
										busquedaForm.getGenericoFechaIniA()[i],
										busquedaForm.getGenericoFechaIniM()[i],
										busquedaForm.getGenericoFechaIniD()[i],
										busquedaForm.getGenericoFechaIniS()[i])
										.validate()
								|| !new CustomDate(
										busquedaForm
												.getGenericoFechaFinFormato()[i],
										busquedaForm.getGenericoFechaFinA()[i],
										busquedaForm.getGenericoFechaFinM()[i],
										busquedaForm.getGenericoFechaFinD()[i],
										busquedaForm.getGenericoFechaFinS()[i])
										.validate()) {
							errors.add(
									ActionErrors.GLOBAL_MESSAGE,
									new ActionError(
											Constants.ERROR_DATE,
											Messages.getString(
													busquedaForm
															.getGenericoEtiquetaFecha()[i],
													request.getLocale())));
						}
					} catch (Exception e) {
						errors.add(
								ActionErrors.GLOBAL_MESSAGE,
								new ActionError(Constants.ERROR_DATE, Messages
										.getString(busquedaForm
												.getGenericoEtiquetaFecha()[i],
												request.getLocale())));
					}
				}
			}

			// Validar fecha inicial
			if (busqueda.getMapEntrada().containsKey(
					CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_FECHA_INICIAL)) {

				try {

					if (!new CustomDate(busquedaForm.getFechaFormatoIni(),
							busquedaForm.getFechaAIni(),
							busquedaForm.getFechaMIni(),
							busquedaForm.getFechaDIni(),
							busquedaForm.getFechaSIni()).validate()
							|| !new CustomDate(
									busquedaForm.getFechaIniFormatoIni(),
									busquedaForm.getFechaIniAIni(),
									busquedaForm.getFechaIniMIni(),
									busquedaForm.getFechaIniDIni(),
									busquedaForm.getFechaIniSIni()).validate()
							|| !new CustomDate(
									busquedaForm.getFechaFinFormatoIni(),
									busquedaForm.getFechaFinAIni(),
									busquedaForm.getFechaFinMIni(),
									busquedaForm.getFechaFinDIni(),
									busquedaForm.getFechaFinSIni()).validate()) {
						errors.add(
								ActionErrors.GLOBAL_MESSAGE,
								new ActionError(
										Constants.ERROR_DATE,
										Messages.getString(
												Constants.ETIQUETA_BUSQUEDA_FECHA_INICIO,
												request.getLocale())));
					}
				} catch (Exception e) {
					errors.add(
							ActionErrors.GLOBAL_MESSAGE,
							new ActionError(
									Constants.ERROR_DATE,
									Messages.getString(
											Constants.ETIQUETA_BUSQUEDA_FECHA_INICIO,
											request.getLocale())));
				}
			}

			// Validar fecha final
			if (busqueda.getMapEntrada().containsKey(
					CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_FECHA_FINAL)) {
				try {
					if (!new CustomDate(busquedaForm.getFechaFormatoFin(),
							busquedaForm.getFechaAFin(),
							busquedaForm.getFechaMFin(),
							busquedaForm.getFechaDFin(),
							busquedaForm.getFechaSFin()).validate()
							|| !new CustomDate(
									busquedaForm.getFechaIniFormatoFin(),
									busquedaForm.getFechaIniAFin(),
									busquedaForm.getFechaIniMFin(),
									busquedaForm.getFechaIniDFin(),
									busquedaForm.getFechaIniSFin()).validate()
							|| !new CustomDate(
									busquedaForm.getFechaFinFormatoFin(),
									busquedaForm.getFechaFinAFin(),
									busquedaForm.getFechaFinMFin(),
									busquedaForm.getFechaFinDFin(),
									busquedaForm.getFechaFinSFin()).validate()) {
						errors.add(
								ActionErrors.GLOBAL_MESSAGE,
								new ActionError(
										Constants.ERROR_DATE,
										Messages.getString(
												Constants.ETIQUETA_BUSQUEDA_FECHA_FINAL,
												request.getLocale())));
					}
				} catch (Exception e) {
					errors.add(
							ActionErrors.GLOBAL_MESSAGE,
							new ActionError(
									Constants.ERROR_DATE,
									Messages.getString(
											Constants.ETIQUETA_BUSQUEDA_FECHA_FINAL,
											request.getLocale())));
				}

			}

			// Validar fecha
			if (busqueda.getMapEntrada().containsKey(
					CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_FECHAS)) {
				try {
					if (!new CustomDate(busquedaForm.getFechaFormato(),
							busquedaForm.getFechaA(), busquedaForm.getFechaM(),
							busquedaForm.getFechaD(), busquedaForm.getFechaS())
							.validate()
							|| !new CustomDate(
									busquedaForm.getFechaIniFormato(),
									busquedaForm.getFechaIniA(),
									busquedaForm.getFechaIniM(),
									busquedaForm.getFechaIniD(),
									busquedaForm.getFechaIniS()).validate()
							|| !new CustomDate(
									busquedaForm.getFechaFinFormato(),
									busquedaForm.getFechaFinA(),
									busquedaForm.getFechaFinM(),
									busquedaForm.getFechaFinD(),
									busquedaForm.getFechaFinS()).validate()) {
						errors.add(
								ActionErrors.GLOBAL_MESSAGE,
								new ActionError(Constants.ERROR_DATE, Messages
										.getString(Constants.ETIQUETA_FECHA2,
												request.getLocale())));
					}
				} catch (Exception e) {
					errors.add(
							ActionErrors.GLOBAL_MESSAGE,
							new ActionError(Constants.ERROR_DATE, Messages
									.getString(Constants.ETIQUETA_FECHA2,
											request.getLocale())));
				}
			}

			// Validar número
			if (busqueda.getMapEntrada().containsKey(
					CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_DATO_NUMERICO)) {
				if (StringUtils.isNotBlank(busquedaForm.getNumero())
						&& !NumberUtils.isNumber(busquedaForm.getNumero())) {
					errors.add(
							ActionErrors.GLOBAL_MESSAGE,
							new ActionError(
									Constants.ERROR_INVALID,
									Messages.getString(
											Constants.ETIQUETA_BUSQUEDA_FORM_NUMERO,
											request.getLocale())));
				}
			}

			// Validar niveles
			if (busqueda.getMapEntrada().containsKey(
					CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_NIVELES_DESCRIPCION)) {
				if (busquedaForm.getNiveles().length == 0) {
					errors.add(
							ActionErrors.GLOBAL_MESSAGE,
							new ActionError(
									Constants.ERROR_REQUIRED,
									Messages.getString(
											ArchivoDetails.DESCRIPCION_BUSQUEDA_NIVELES,
											request.getLocale())));
				}
			}

			// Validar estados
			if (busqueda.getMapEntrada().containsKey(
					CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_ESTADO)) {
				if (busquedaForm.getEstados().length == 0)
					errors.add(
							ActionErrors.GLOBAL_MESSAGE,
							new ActionError(Constants.ERROR_REQUIRED, Messages
									.getString(FondosConstants.FONDOS_ESTADO,
											request.getLocale())));
			}

			// Validar bloqueos
			if (busqueda.getMapEntrada().containsKey(
					CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_BLOQUEO)) {
				if (busquedaForm.getBloqueos().length == 0)
					errors.add(
							ActionErrors.GLOBAL_MESSAGE,
							new ActionError(Constants.ERROR_REQUIRED, Messages
									.getString(FondosConstants.FONDOS_BLOQUEO,
											request.getLocale())));
			}

			// Validar fechas en la búsqueda avanzada
			if (busqueda
					.getMapEntrada()
					.containsKey(
							CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_CONDICIONES_AVANZADAS)) {
				for (int i = 0; i < busquedaForm.getTipoCampo().length; i++) {
					if (busquedaForm.isReemplazoValoresNulos()) {
						if (busquedaForm.getTipoCampo()[i].intValue() == 3) {
							if (StringUtils
									.isNotEmpty(busquedaForm.getCampo()[i])
									&& StringUtils.isEmpty(busquedaForm
											.getValor1D()[i])
									&& StringUtils.isEmpty(busquedaForm
											.getValor1M()[i])
									&& StringUtils.isEmpty(busquedaForm
											.getValor1A()[i])
									&& StringUtils.isEmpty(busquedaForm
											.getValor1S()[i])) {
								continue;
							}
						} else {
							if (StringUtils
									.isNotEmpty(busquedaForm.getCampo()[i])
									&& StringUtils.isEmpty(busquedaForm
											.getValor1()[i])) {
								continue;
							}
						}
					}

					// Comprobar que se ha introducido un valor
					String fila = new Integer(i + 1).toString();
					if (busquedaForm.getTipoCampo()[i].intValue() == 3) {

						if (StringUtils.isNotEmpty(busquedaForm.getCampo()[i])
								&& StringUtils.isEmpty(busquedaForm
										.getValor1D()[i])
								&& StringUtils.isEmpty(busquedaForm
										.getValor1M()[i])
								&& StringUtils.isEmpty(busquedaForm
										.getValor1A()[i])
								&& StringUtils.isEmpty(busquedaForm
										.getValor1S()[i])) {
							if (busquedaForm.getTipoCampo().length > 1) {
								errors.add(
										ActionErrors.GLOBAL_MESSAGE,
										new ActionError(
												Constants.ERROR_BUSQUEDA_AVANZADA_VALOR_OBLIGATORIO,
												fila));
							} else {
								errors.add(
										ActionErrors.GLOBAL_MESSAGE,
										new ActionError(
												Constants.ERROR_BUSQUEDA_AVANZADA_VALOR_OBLIGATORIO_SOLOUNO));
							}
						}

						// Comprobar la fecha1 es correcta
						try {
							CustomDate fecha1 = new CustomDate(
									busquedaForm.getFormatoFecha1()[i],
									busquedaForm.getValor1A()[i],
									busquedaForm.getValor1M()[i],
									busquedaForm.getValor1D()[i],
									busquedaForm.getValor1S()[i]);

							if (!fecha1.validate()) {
								errors.add(
										ActionErrors.GLOBAL_MESSAGE,
										new ActionError(
												Constants.ERROR_DATE,
												Messages.getString(
														Constants.ETIQUETA_CONDICIONES_AVANZADAS,
														request.getLocale())));
							}
						} catch (Exception e) {
							errors.add(
									ActionErrors.GLOBAL_MESSAGE,
									new ActionError(
											Constants.ERROR_DATE,
											Messages.getString(
													Constants.ETIQUETA_CONDICIONES_AVANZADAS,
													request.getLocale())));

						}

						// Comprobar la Fecha2 si el operador es rango
						try {
							if (busquedaForm.getOperador()[i]
									.equals(CustomDateFormat.DATE_OPERATOR_RANGE)) {
								CustomDate fecha2 = new CustomDate(
										busquedaForm.getFormatoFecha2()[i],
										busquedaForm.getValor2A()[i],
										busquedaForm.getValor2M()[i],
										busquedaForm.getValor2D()[i],
										busquedaForm.getValor2S()[i]);

								if (!fecha2.validate()) {
									errors.add(
											ActionErrors.GLOBAL_MESSAGE,
											new ActionError(
													Constants.ERROR_DATE,
													Messages.getString(
															Constants.ETIQUETA_CONDICIONES_AVANZADAS,
															request.getLocale())));
								}

							}
						} catch (Exception e) {
							errors.add(
									ActionErrors.GLOBAL_MESSAGE,
									new ActionError(
											Constants.ERROR_DATE,
											Messages.getString(
													Constants.ETIQUETA_CONDICIONES_AVANZADAS,
													request.getLocale())));
						}
					} else {
						if (busquedaForm.getTipoCampo()[i].intValue() == TipoCampo.NUMERICO_VALUE) {
							// hay que comprobar que el campo tenga solo numeros
							// y que ademas entre en el campo
							if (!StringUtils
									.isNumeric(busquedaForm.getValor1()[i])) {
								errors.add(
										ActionErrors.GLOBAL_MESSAGE,
										new ActionError(
												Constants.ERROR_BUSQUEDA_FILA_VALOR,
												fila));
							} else if (busquedaForm.getOperador()[i]
									.equals(CustomDateFormat.DATE_OPERATOR_RANGE)) {
								if (StringUtils.isEmpty(busquedaForm
										.getValor1()[i])) {
									errors.add(
											ActionErrors.GLOBAL_MESSAGE,
											new ActionError(
													Constants.ERROR_CAMPOS_OPERADOR_RANGO_OBLIGATORIOS,
													fila));
								} else if (!StringUtils.isNumeric(busquedaForm
										.getValor2()[i])) {
									errors.add(
											ActionErrors.GLOBAL_MESSAGE,
											new ActionError(
													Constants.ERROR_BUSQUEDA_FILA_VALOR,
													fila));
								}
							}
						} else if (StringUtils.isNotEmpty(busquedaForm
								.getCampo()[i])
								&& StringUtils
										.isEmpty(busquedaForm.getValor1()[i])) {
							if (busquedaForm.getTipoCampo().length > 1) {
								errors.add(
										ActionErrors.GLOBAL_MESSAGE,
										new ActionError(
												Constants.ERROR_BUSQUEDA_AVANZADA_VALOR_OBLIGATORIO,
												fila));
							} else {
								errors.add(
										ActionErrors.GLOBAL_MESSAGE,
										new ActionError(
												Constants.ERROR_BUSQUEDA_AVANZADA_VALOR_OBLIGATORIO_SOLOUNO));
							}
						}
					}
				}
			}

			// Validar los ámbitos
			if (busqueda.getMapEntrada().containsKey(
					CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_AMBITO)) {
				if (!ArrayUtils.isEmpty(busquedaForm.getIdObjetoAmbito())) {
					String[] codRefs = (String[]) ArrayUtils.clone(busquedaForm
							.getIdObjetoAmbito());
					for (int i = codRefs.length - 1; i >= 0; i--) {
						if (StringUtils.isBlank(codRefs[i]))
							codRefs = (String[]) ArrayUtils.remove(codRefs, i);
						else {
							for (int j = i - 1; j >= 0; j--) {
								if (codRefs[i].equals(codRefs[j])) {
									errors.add(
											ActionErrors.GLOBAL_MESSAGE,
											new ActionError(
													Constants.ERROR_AMBITO_REPETIDO,
													busquedaForm
															.getNombreObjetoAmbito()[i]));
								} else if (StringUtils.indexOf(codRefs[i],
										codRefs[j]) == 0) {
									errors.add(
											ActionErrors.GLOBAL_MESSAGE,
											new ActionError(
													Constants.ERROR_AMBITO_CONTENIDO,
													busquedaForm
															.getNombreObjetoAmbito()[i],
													busquedaForm
															.getNombreObjetoAmbito()[j]));
								} else if (StringUtils.indexOf(codRefs[j],
										codRefs[i]) == 0) {
									errors.add(
											ActionErrors.GLOBAL_MESSAGE,
											new ActionError(
													Constants.ERROR_AMBITO_CONTENIDO,
													busquedaForm
															.getNombreObjetoAmbito()[j],
													busquedaForm
															.getNombreObjetoAmbito()[i]));
								}
							}
						}
					}
				}
			}
		}
		return errors;
	}

	public static BusquedaElementosVO getBusquedaElementosVO(Busqueda busqueda,
			PrecondicionesBusquedaFondosGenerica precondiciones,
			IBusquedaForm busquedaForm) {
		BusquedaElementosVO vo = new BusquedaElementosVO();

		if (busqueda != null) {
			// TODO NACHO Búsquedas Mirar para que sirve este atributo y si no
			// se usa eliminarlo
			vo.setPageInfo(busquedaForm.getPageInfo());

			/* Establecer el tipo de la búsqueda */
			vo.setTipoBusqueda(busquedaForm.getTipoBusqueda());

			/* idArchivo */
			if ((precondiciones != null)
					&& (precondiciones.getIdArchivo() != null)) {
				vo.setIdArchivo(precondiciones.getIdArchivo());
				busquedaForm.getCamposRellenos().add(
						CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_IDARCHIVO);
			}

			/* Elementos a excluir */
			if ((precondiciones != null)
					&& (precondiciones.getElementosExcluir() != null)
					&& (!precondiciones.getElementosExcluir().isEmpty())) {
				vo.setElementosExcluir(precondiciones.getElementosExcluir());
				busquedaForm
						.getCamposRellenos()
						.add(CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_ELEMENTOS_EXCLUIR);
			}

			/* Elementos a restringir */
			if ((precondiciones != null)
					&& (precondiciones.getElementosRestringir() != null)
					&& (!precondiciones.getElementosRestringir().isEmpty())) {
				vo.setElementosRestringir(precondiciones
						.getElementosRestringir());
				busquedaForm
						.getCamposRellenos()
						.add(CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_ELEMENTOS_RESTRINGIR);
			}

			/* Subquerys elementos a restringir */
			if ((precondiciones != null)
					&& (precondiciones.getSubqueryElementosRestringir() != null)
					&& (!precondiciones.getSubqueryElementosRestringir()
							.isEmpty())) {
				vo.setSubqueryElementosRestringir(precondiciones
						.getSubqueryElementosRestringir());
				busquedaForm
						.getCamposRellenos()
						.add(CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_SUBQUERY_ELEMENTOS_RESTRINGIR);
			}

			/* Código de referencia */
			if (busqueda.getMapEntrada().containsKey(
					CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_CODIGO_REFERENCIA)) {
				vo.setCodigoReferencia(busquedaForm.getCodigoReferencia());
				if (StringUtils.isNotEmpty(vo.getCodigoReferencia()))
					busquedaForm
							.getCamposRellenos()
							.add(CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_CODIGO_REFERENCIA);
			}

			/* Título */
			if (busqueda.getMapEntrada().containsKey(
					CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_TITULO)) {
				vo.setTitulo(busquedaForm.getTitulo());
				if (StringUtils.isNotEmpty(vo.getTitulo()))
					busquedaForm.getCamposRellenos().add(
							CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_TITULO);

			}

			/* Rangos */
			if (busqueda.getMapEntrada().containsKey(
					CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_RANGOS)) {
				vo.setRango(busquedaForm.getRango());
				if (StringUtils.isNotEmpty(vo.getRango()))
					busquedaForm.getCamposRellenos().add(
							CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_RANGOS);
			}

			/* Número de expediente */
			if (busqueda.getMapEntrada().containsKey(
					CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_NUMERO_EXPEDIENTE)) {
				vo.setNumeroExpediente(busquedaForm.getNumeroExpediente());
				if (StringUtils.isNotEmpty(vo.getNumeroExpediente()))
					busquedaForm
							.getCamposRellenos()
							.add(CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_NUMERO_EXPEDIENTE);

				// Detectar si se rellena o no el calificador
				CampoBusqueda expediente = (CampoBusqueda) busqueda
						.getMapEntrada()
						.get(CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_NUMERO_EXPEDIENTE);
				if (XMLObject.TRUE.equals(expediente.getMostrarCalificadores())) {
					vo.setCalificadorNumeroExpediente(busquedaForm
							.getNumeroExpediente_like());
				}
			}

			/* Signatura */
			if (busqueda.getMapEntrada().containsKey(
					CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_SIGNATURA)) {
				vo.setSignatura(busquedaForm.getSignatura());
				if (StringUtils.isNotEmpty(vo.getSignatura())) {
					busquedaForm.getCamposRellenos().add(
							CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_SIGNATURA);
					vo.setComprobarPermisos(false);
				}

				// Detectar si se rellena o no el calificador
				CampoBusqueda signatura = (CampoBusqueda) busqueda
						.getMapEntrada()
						.get(CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_SIGNATURA);
				if (XMLObject.TRUE.equals(signatura.getMostrarCalificadores())) {
					vo.setCalificadorSignatura(busquedaForm.getSignatura_like());
				}
			}

			/* Texto */
			if (busqueda.getMapEntrada().containsKey(
					CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_TEXTO)) {
				vo.setTexto(busquedaForm.getTexto());
				if (StringUtils.isNotEmpty(vo.getTexto()))
					busquedaForm.getCamposRellenos().add(
							CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_TEXTO);

			}

			/* Fecha Inicial */
			if (busqueda.getMapEntrada().containsKey(
					CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_FECHA_INICIAL)) {
				// Obtener el rango de fechas para la búsqueda
				CustomDateRange range = CustomDateFormat.getDateRange(
						busquedaForm.getFechaCompIni(),
						new CustomDate(busquedaForm.getFechaFormatoIni(),
								busquedaForm.getFechaAIni(), busquedaForm
										.getFechaMIni(), busquedaForm
										.getFechaDIni(), busquedaForm
										.getFechaSIni()),
						new CustomDate(busquedaForm.getFechaIniFormatoIni(),
								busquedaForm.getFechaIniAIni(), busquedaForm
										.getFechaIniMIni(), busquedaForm
										.getFechaIniDIni(), busquedaForm
										.getFechaIniSIni()),
						new CustomDate(busquedaForm.getFechaFinFormatoIni(),
								busquedaForm.getFechaFinAIni(), busquedaForm
										.getFechaFinMIni(), busquedaForm
										.getFechaFinDIni(), busquedaForm
										.getFechaFinSIni()));

				vo.setFechaIniIni(range.getInitialDate());
				vo.setFechaIniFin(range.getFinalDate());

				vo.setFechaIniOperador(busquedaForm.getFechaCompIni());

				// Detectar si se rellena o no el calificador
				CampoBusqueda campoFecha = (CampoBusqueda) busqueda
						.getMapEntrada()
						.get(CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_FECHA_INICIAL);
				if (XMLObject.TRUE.equals(campoFecha.getMostrarCalificadores())) {
					vo.setCalificadorInicial(busquedaForm
							.getCalificadorInicial());
				}
				if (vo.getFechaIniIni() != null
						|| vo.getFechaIniFin() != null
						|| StringUtils.isNotEmpty(busquedaForm
								.getCalificadorInicial()))
					busquedaForm
							.getCamposRellenos()
							.add(CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_FECHA_INICIAL);

			}

			/* Fecha Final */
			if (busqueda.getMapEntrada().containsKey(
					CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_FECHA_FINAL)) {
				// Obtener el rango de fechas para la búsqueda
				CustomDateRange range = CustomDateFormat.getDateRange(
						busquedaForm.getFechaCompFin(),
						new CustomDate(busquedaForm.getFechaFormatoFin(),
								busquedaForm.getFechaAFin(), busquedaForm
										.getFechaMFin(), busquedaForm
										.getFechaDFin(), busquedaForm
										.getFechaSFin()),
						new CustomDate(busquedaForm.getFechaIniFormatoFin(),
								busquedaForm.getFechaIniAFin(), busquedaForm
										.getFechaIniMFin(), busquedaForm
										.getFechaIniDFin(), busquedaForm
										.getFechaIniSFin()),
						new CustomDate(busquedaForm.getFechaFinFormatoFin(),
								busquedaForm.getFechaFinAFin(), busquedaForm
										.getFechaFinMFin(), busquedaForm
										.getFechaFinDFin(), busquedaForm
										.getFechaFinSFin()));

				vo.setFechaFinIni(range.getInitialDate());
				vo.setFechaFinFin(range.getFinalDate());

				vo.setFechaFinOperador(busquedaForm.getFechaCompFin());

				// Detectar si se rellena o no el calificador
				CampoBusqueda campoFecha = (CampoBusqueda) busqueda
						.getMapEntrada()
						.get(CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_FECHA_FINAL);
				if (XMLObject.TRUE.equals(campoFecha.getMostrarCalificadores())) {
					vo.setCalificadorFinal(busquedaForm.getCalificadorFinal());
				}

				if (vo.getFechaFinIni() != null
						|| vo.getFechaFinFin() != null
						|| StringUtils.isNotEmpty(busquedaForm
								.getCalificadorFinal()))
					busquedaForm.getCamposRellenos().add(
							CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_FECHA_FINAL);

			}

			/* Fondo */
			if (busqueda.getMapEntrada().containsKey(
					CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_FONDO)) {
				vo.setFondo(busquedaForm.getFondo());
				if (StringUtils.isNotEmpty(busquedaForm.getFondo()))
					busquedaForm.getCamposRellenos().add(
							CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_FONDO);
			}

			/* Niveles de descripcion - Debe estar presente siempre ahora mismo */
			if ((precondiciones != null)
					&& (!ArrayUtils.isEmpty(precondiciones.getNiveles()))) {
				vo.setNiveles(precondiciones.getNiveles());
				busquedaForm
						.getCamposRellenos()
						.add(CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_NIVELES_DESCRIPCION);
			} else {
				CampoBusqueda campoNiveles = (CampoBusqueda) busqueda
						.getMapEntrada()
						.get(CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_NIVELES_DESCRIPCION);
				if (campoNiveles != null) {
					busquedaForm.setActivarNiveles(true);
					vo.setNiveles(busquedaForm.getNiveles());
					busquedaForm
							.getCamposRellenos()
							.add(CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_NIVELES_DESCRIPCION);
				}

			}

			/* Tipo de elemento */
			if ((precondiciones != null)
					&& (!StringUtils.isEmpty(precondiciones.getTipoElemento()))) {
				vo.setTipoElemento(precondiciones.getTipoElemento());
				busquedaForm.getCamposRellenos().add(
						CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_TIPO_ELEMENTO);
			} else {
				vo.setTipoElemento(busquedaForm.getTipoElemento());
				busquedaForm.getCamposRellenos().add(
						CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_TIPO_ELEMENTO);
			}

			/* Descriptores */
			if (busqueda.getMapEntrada().containsKey(
					CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_DESCRIPTORES)) {
				if (StringUtils.isNotBlank(busquedaForm.getDescriptores())) {
					StringOwnTokenizer tok = new StringOwnTokenizer(
							busquedaForm.getDescriptores(), "#");
					String[] listaDescriptores = new String[tok.countTokens()];
					String info;
					for (int i = 0; tok.hasMoreTokens(); i++) {
						info = tok.nextToken();
						String[] partes = info.split(":");
						if (partes.length > 0)
							listaDescriptores[i] = partes[0];
					}
					vo.setDescriptores(listaDescriptores);
					busquedaForm
							.getCamposRellenos()
							.add(CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_DESCRIPTORES);
				}
			}

			/* Productores */
			if (busqueda.getMapEntrada().containsKey(
					CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_PRODUCTOR)) {
				if (StringUtils.isNotBlank(busquedaForm.getProductores())) {
					StringOwnTokenizer tok = new StringOwnTokenizer(
							busquedaForm.getProductores(), "#");
					String[] listaProductores = new String[tok.countTokens()];
					String info;
					for (int i = 0; tok.hasMoreTokens(); i++) {
						info = tok.nextToken();
						String[] partes = info.split(":");
						if (partes.length > 0)
							listaProductores[i] = partes[0];
					}
					vo.setProductores(listaProductores);
					busquedaForm.getCamposRellenos().add(
							CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_PRODUCTOR);
				}
			}

			/* Fecha */
			if (busqueda.getMapEntrada().containsKey(
					CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_FECHAS)) {
				// Obtener el rango de fechas para la búsqueda
				CustomDateRange range = CustomDateFormat.getDateRange(
						busquedaForm.getFechaComp(),
						new CustomDate(busquedaForm.getFechaFormato(),
								busquedaForm.getFechaA(), busquedaForm
										.getFechaM(), busquedaForm.getFechaD(),
								busquedaForm.getFechaS()),
						new CustomDate(busquedaForm.getFechaIniFormato(),
								busquedaForm.getFechaIniA(), busquedaForm
										.getFechaIniM(), busquedaForm
										.getFechaIniD(), busquedaForm
										.getFechaIniS()),
						new CustomDate(busquedaForm.getFechaFinFormato(),
								busquedaForm.getFechaFinA(), busquedaForm
										.getFechaFinM(), busquedaForm
										.getFechaFinD(), busquedaForm
										.getFechaFinS()));

				vo.setFechaIni(range.getInitialDate());
				vo.setFechaFin(range.getFinalDate());

				vo.setFechaIniOperador(busquedaForm.getFechaComp());

				// Detectar si se rellena o no el calificador
				CampoBusqueda campoFecha = (CampoBusqueda) busqueda
						.getMapEntrada().get(
								CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_FECHAS);
				if (XMLObject.TRUE.equals(campoFecha.getMostrarCalificadores())) {
					vo.setCalificador(busquedaForm.getCalificador());
				}

				if (vo.getFechaIni() != null || vo.getFechaFin() != null)
					busquedaForm.getCamposRellenos().add(
							CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_FECHAS);
			}

			/* Ámbito */
			if (busqueda.getMapEntrada().containsKey(
					CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_AMBITO)) {
				if (!(busquedaForm.getIdObjetoAmbito().length == 1 && StringUtils
						.isBlank((busquedaForm.getIdObjetoAmbito())[0]))) {
					vo.setIdObjetoAmbito(busquedaForm.getIdObjetoAmbito());
					vo.setTipoObjetoAmbito(busquedaForm.getTipoObjetoAmbito());
				}
				if (StringUtils.isNotEmpty(vo.getIdObjetoAmbito())) {
					busquedaForm.getCamposRellenos().add(
							CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_AMBITO);
				}
			} else {
				if ((precondiciones != null)
						&& (!ArrayUtils.isEmpty(precondiciones
								.getIdRefObjetoAmbito()))) {
					vo.setIdObjetoAmbito(precondiciones.getIdRefObjetoAmbito());
					vo.setTipoObjetoAmbito(precondiciones.getTipoObjetoAmbito());
					busquedaForm.getCamposRellenos().add(
							CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_AMBITO);
				}
			}

			/* Dato Numérico */
			if (busqueda.getMapEntrada().containsKey(
					CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_DATO_NUMERICO)) {
				vo.setNumeroComp(busquedaForm.getNumeroComp());
				vo.setNumero(busquedaForm.getNumero());
				vo.setTipoMedida(busquedaForm.getTipoMedida());
				vo.setUnidadMedida(busquedaForm.getUnidadMedida());

				if (StringUtils.isNotEmpty(vo.getNumero()))
					busquedaForm
							.getCamposRellenos()
							.add(CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_DATO_NUMERICO);
			}

			/* Estado */
			if ((precondiciones != null)
					&& (!ArrayUtils.isEmpty(precondiciones.getEstados()))) {
				vo.setEstados(precondiciones.getEstados());
				busquedaForm.getCamposRellenos().add(
						CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_ESTADO);
			} else {
				vo.setEstados(busquedaForm.getEstados());
				if (!ArrayUtils.isEmpty(vo.getEstados()))
					busquedaForm.getCamposRellenos().add(
							CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_ESTADO);
			}

			/* Código de relación de entrega */
			if (busqueda.getMapEntrada().containsKey(
					CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_CODIGO_RELACION)) {
				vo.setCodigoRelacion(busquedaForm.getCodigoRelacion());
				if (StringUtils.isNotEmpty(vo.getCodigoRelacion()))
					busquedaForm
							.getCamposRellenos()
							.add(CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_CODIGO_RELACION);

			}

			/* Codigo */
			if (busqueda.getMapEntrada().containsKey(
					CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_CODIGO)) {
				vo.setCodigo(busquedaForm.getCodigo());
				if (StringUtils.isNotEmpty(vo.getCodigo()))
					busquedaForm.getCamposRellenos().add(
							CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_CODIGO);
			}

			/* Procedimiento */
			if (busqueda.getMapEntrada().containsKey(
					CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_PROCEDIMIENTO)) {
				vo.setProcedimiento(busquedaForm.getProcedimiento());
				if (StringUtils.isNotEmpty(vo.getProcedimiento()))
					busquedaForm
							.getCamposRellenos()
							.add(CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_PROCEDIMIENTO);
			}

			/* Contenido de Fichero */
			if (busqueda.getMapEntrada().containsKey(
					CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_CONTENIDO_FICHERO)) {
				vo.setContenidoFichero(busquedaForm.getContenidoFichero());
				if (StringUtils.isNotEmpty(vo.getContenidoFichero()))
					busquedaForm
							.getCamposRellenos()
							.add(CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_CONTENIDO_FICHERO);
			}

			/* Campo Genericos de Tipo Numérico */
			if (!ArrayUtils.isEmpty(busquedaForm.getGenericoIdCampoNumerico())) {
				String[] ids = new String[0];
				String[] valores = new String[0];
				String[] valoresFin = new String[0];
				String[] operadores = new String[0];

				for (int i = 0; i < busquedaForm.getGenericoIdCampoNumerico().length; i++) {
					String valor = busquedaForm.getGenericoCampoNumerico()[i];

					if (StringUtils.isNotEmpty(valor)) {
						ids = (String[]) ArrayUtils.add(ids,
								busquedaForm.getGenericoIdCampoNumerico()[i]);
						valores = (String[]) ArrayUtils.add(valores,
								busquedaForm.getGenericoCampoNumerico()[i]);
						valoresFin = (String[]) ArrayUtils.add(valoresFin,
								busquedaForm.getGenericoCampoNumericoFin()[i]);
						operadores = (String[]) ArrayUtils
								.add(operadores, busquedaForm
										.getGenericoOperadorCampoNumerico()[i]);
					}
				}

				// Comprobar si hay algun valor relleno
				if (ArrayUtils.isNotEmpty(ids)) {
					busquedaForm
							.getCamposRellenos()
							.add(CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_GENERICO_CAMPO_NUMERICO);
					vo.setGenericoIdCampoNumerico(ids);
					vo.setGenericoCampoNumerico(valores);
					vo.setGenericoCampoNumericoFin(valoresFin);
					vo.setGenericoOperadorCampoNumerico(operadores);
				}
			}

			/* Campo Genericos de Tipo Texto Corto */
			if (ArrayUtils.isNotEmpty(busquedaForm.getGenericoIdTextoCorto())) {
				String[] ids = new String[0];
				String[] valores = new String[0];
				String[] operadores = new String[0];

				for (int i = 0; i < busquedaForm.getGenericoIdTextoCorto().length; i++) {
					String valor = busquedaForm.getGenericoTextoCorto()[i];

					if (StringUtils.isNotEmpty(valor)) {
						ids = (String[]) ArrayUtils.add(ids,
								busquedaForm.getGenericoIdTextoCorto()[i]);
						valores = (String[]) ArrayUtils.add(valores,
								busquedaForm.getGenericoTextoCorto()[i]);
						operadores = (String[]) ArrayUtils
								.add(operadores, busquedaForm
										.getGenericoOperadorTextoCorto()[i]);
					}
				}

				// Comprobar si hay algun valor relleno
				if (ArrayUtils.isNotEmpty(ids)) {
					busquedaForm
							.getCamposRellenos()
							.add(CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_GENERICO_TEXTO_CORTO);
					vo.setGenericoIdTextoCorto(ids);
					vo.setGenericoTextoCorto(valores);
					vo.setGenericoOperadorTextoCorto(operadores);
				}
			}

			/* Campo Genericos de Tipo Texto Largo */
			if (ArrayUtils.isNotEmpty(busquedaForm.getGenericoIdTextoLargo())) {
				String[] ids = new String[0];
				String[] valores = new String[0];
				// String[] operadores = new String[0];

				for (int i = 0; i < busquedaForm.getGenericoIdTextoLargo().length; i++) {
					String valor = busquedaForm.getGenericoTextoLargo()[i];

					if (StringUtils.isNotEmpty(valor)) {
						ids = (String[]) ArrayUtils.add(ids,
								busquedaForm.getGenericoIdTextoLargo()[i]);
						valores = (String[]) ArrayUtils.add(valores,
								busquedaForm.getGenericoTextoLargo()[i]);
						// operadores = (String[])ArrayUtils.add(operadores,
						// genericoOperadorTextoLargo[i]);
					}
				}

				// Comprobar si hay algun valor relleno
				if (ArrayUtils.isNotEmpty(ids)) {
					busquedaForm
							.getCamposRellenos()
							.add(CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_GENERICO_TEXTO_LARGO);
					vo.setGenericoIdTextoLargo(ids);
					vo.setGenericoTextoLargo(valores);
					// vo.setGenericoOperadorTextoCorto(operadores);
				}
			}

			/* Campo Genericos de Tipo Fecha */
			if (ArrayUtils.isNotEmpty(busquedaForm.getGenericoIdFecha())) {
				String[] ids = new String[0];
				Date[] genericoFechasIni = new Date[0];
				Date[] genericoFechasFin = new Date[0];
				String[] operadores = new String[0];
				String[] calificadores = new String[0];

				for (int i = 0; i < busquedaForm.getGenericoIdFecha().length; i++) {
					String formato = busquedaForm.getGenericoFechaFormato()[i];

					if (StringUtils.isNotEmpty(formato)) {
						ids = (String[]) ArrayUtils.add(ids,
								busquedaForm.getGenericoIdFecha()[i]);

						CustomDateRange range = CustomDateFormat
								.getDateRange(
										busquedaForm.getGenericoFechaComp()[i],
										new CustomDate(
												busquedaForm
														.getGenericoFechaFormato()[i],
												busquedaForm
														.getGenericoFechaA()[i],
												busquedaForm
														.getGenericoFechaM()[i],
												busquedaForm
														.getGenericoFechaD()[i],
												busquedaForm
														.getGenericoFechaS()[i]),
										new CustomDate(
												busquedaForm
														.getGenericoFechaIniFormato()[i],
												busquedaForm
														.getGenericoFechaIniA()[i],
												busquedaForm
														.getGenericoFechaIniM()[i],
												busquedaForm
														.getGenericoFechaIniD()[i],
												busquedaForm
														.getGenericoFechaIniS()[i]),
										new CustomDate(
												busquedaForm
														.getGenericoFechaFinFormato()[i],
												busquedaForm
														.getGenericoFechaFinA()[i],
												busquedaForm
														.getGenericoFechaFinM()[i],
												busquedaForm
														.getGenericoFechaFinD()[i],
												busquedaForm
														.getGenericoFechaFinS()[i]));

						genericoFechasIni = (Date[]) ArrayUtils.add(
								genericoFechasIni, range.getInitialDate());
						genericoFechasFin = (Date[]) ArrayUtils.add(
								genericoFechasFin, range.getFinalDate());
						operadores = (String[]) ArrayUtils.add(operadores,
								busquedaForm.getGenericoFechaComp()[i]);
						calificadores = (String[]) ArrayUtils.add(
								calificadores,
								busquedaForm.getGenericoFechaCalificador()[i]);
					}
				}

				// Comprobar si hay algun valor relleno
				if (ArrayUtils.isNotEmpty(ids)
						&& (genericoFechasIni != null
								&& genericoFechasIni.length > 0 && genericoFechasIni[0] != null)
						&& (genericoFechasFin != null
								&& genericoFechasFin.length > 0 && genericoFechasFin[0] != null)) {
					busquedaForm
							.getCamposRellenos()
							.add(CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_GENERICO_CAMPO_FECHA);
					vo.setGenericoIdFecha(ids);
					vo.setGenericoFechaIni(genericoFechasIni);
					vo.setGenericoFechaFin(genericoFechasFin);
					vo.setGenericoFechaCalificador(calificadores);
					vo.setGenericoFechaOperador(operadores);
				}
			}

			/* Condiciones avanzadas */
			if (busqueda
					.getMapEntrada()
					.containsKey(
							CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_CONDICIONES_AVANZADAS)) {
				vo.setIdFicha(busquedaForm.getIdFicha());
				vo.setBooleano(busquedaForm.getBooleano());
				vo.setAbrirpar(busquedaForm.getAbrirpar());
				vo.setCampo(busquedaForm.getCampo());
				vo.setTipoCampo(ArrayUtils.toPrimitive(busquedaForm
						.getTipoCampo()));

				vo.setCerrarpar(busquedaForm.getCerrarpar());
				vo.setTipoReferencia(busquedaForm.getTipoReferencia());
				vo.setTiposReferencia(busquedaForm.getTiposReferencia());

				String[] listaValores1 = new String[busquedaForm.getCampo().length];
				String[] listaValores2 = new String[busquedaForm.getCampo().length];

				for (int i = 0; i < listaValores1.length; i++) {
					if (busquedaForm.getTipoCampo()[i].intValue() == 3) {
						CustomDateRange range = CustomDateFormat.getDateRange(
								busquedaForm.getOperador()[i], new CustomDate(
										busquedaForm.getFormatoFecha1()[i],
										busquedaForm.getValor1A()[i],
										busquedaForm.getValor1M()[i],
										busquedaForm.getValor1D()[i],
										busquedaForm.getValor1S()[i]),
								new CustomDate(
										busquedaForm.getFormatoFecha1()[i],
										busquedaForm.getValor1A()[i],
										busquedaForm.getValor1M()[i],
										busquedaForm.getValor1D()[i],
										busquedaForm.getValor1S()[i]),
								new CustomDate(
										busquedaForm.getFormatoFecha2()[i],
										busquedaForm.getValor2A()[i],
										busquedaForm.getValor2M()[i],
										busquedaForm.getValor2D()[i],
										busquedaForm.getValor2S()[i]));

						// Si existe fecha Inicial se asigna
						if (range.getInitialDate() != null) {
							listaValores1[i] = CustomDateFormat.SDF_YYYYMMDD
									.format(range.getInitialDate());

							// Si además existe fecha Final, asignarla al
							// operador2
							if (range.getFinalDate() != null) {
								listaValores2[i] = CustomDateFormat.SDF_YYYYMMDD
										.format(range.getFinalDate());
							} else {
								listaValores2[i] = null;
							}
						} else {
							if (range.getFinalDate() != null) {
								listaValores1[i] = CustomDateFormat.SDF_YYYYMMDD
										.format(range.getFinalDate());
							} else {
								listaValores1[i] = null;
							}
						}
					} else {
						if (busquedaForm.getValor1().length > i)
							listaValores1[i] = busquedaForm.getValor1()[i];
					}
				}
				vo.setValor1(listaValores1);
				vo.setValor1A(busquedaForm.getValor1A());
				vo.setValor1D(busquedaForm.getValor1D());
				vo.setValor1M(busquedaForm.getValor1M());
				vo.setValor1S(busquedaForm.getValor1S());

				String[] listaOperadores = new String[busquedaForm.getCampo().length];
				for (int i = 0; i < listaValores2.length; i++) {
					listaOperadores[i] = busquedaForm.getOperador()[i];
					if (busquedaForm.getTipoCampo()[i].intValue() != 3) {
						listaValores2[i] = null;
						if (busquedaForm.getValor2().length > i)
							listaValores2[i] = busquedaForm.getValor2()[i];
					}
				}
				vo.setValor2(listaValores2);
				vo.setValor2A(busquedaForm.getValor2A());
				vo.setValor2D(busquedaForm.getValor2D());
				vo.setValor2M(busquedaForm.getValor2M());
				vo.setOperador(listaOperadores);

				vo.setReemplazoValoresNulos(busquedaForm
						.isReemplazoValoresNulos());

				vo.setValor4(busquedaForm.getValor4());
				vo.setValor4A(busquedaForm.getValor4A());
				vo.setValor4D(busquedaForm.getValor4D());
				vo.setValor4M(busquedaForm.getValor4M());
				vo.setValor4S(busquedaForm.getValor4S());
				vo.setFormatoFecha4(busquedaForm.getFormatoFecha4());

				boolean creacionForzadaDeCondicionesAvanzadas = false;
				if (busqueda.getMapEntrada() != null) {
					Object campo = busqueda
							.getMapEntrada()
							.get(CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_CONDICIONES_AVANZADAS);
					if (campo != null && campo instanceof CampoBusqueda) {
						CampoBusqueda cb = (CampoBusqueda) campo;
						if (cb.getRestricciones().size() > 0) {
							Object res = cb
									.getRestriccion(RestriccionesCamposBusquedas.RESTRICCION_CAMPO_BUSQUEDA_FICHA);
							if (res != null
									&& res instanceof RestriccionCampoBusqueda) {
								RestriccionCampoBusqueda restriccion = (RestriccionCampoBusqueda) res;
								if (restriccion
										.getId()
										.equals(RestriccionesCamposBusquedas.RESTRICCION_CAMPO_BUSQUEDA_FICHA)
										&& restriccion.getTipo() == null) {
									creacionForzadaDeCondicionesAvanzadas = true;
								}
							}
						}
					}
				}

				if ((!ArrayUtils.isEmpty(listaValores1) || !ArrayUtils
						.isEmpty(listaValores2))
						|| creacionForzadaDeCondicionesAvanzadas)
					busquedaForm
							.getCamposRellenos()
							.add(CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_CONDICIONES_AVANZADAS);
			}

			/* Bloqueos */
			if ((precondiciones != null)
					&& (!ArrayUtils.isEmpty(precondiciones.getBloqueos()))) {
				vo.setBloqueos(precondiciones.getBloqueos());
				busquedaForm.getCamposRellenos().add(
						CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_BLOQUEO);
			} else {
				// Si estan seleccionados los dos tipos de bloqueo(bloqueadas y
				// desbloqueadas) no se hace nada
				if (ArrayUtils.isNotEmpty(busquedaForm.getBloqueos())
						&& busquedaForm.getBloqueos().length < 2) {
					vo.setBloqueos(busquedaForm.getBloqueos());
					if (!ArrayUtils.isEmpty(vo.getBloqueos()))
						busquedaForm.getCamposRellenos().add(
								CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_BLOQUEO);
				}
			}

			/* Conservada */
			if (StringUtils.isNotEmpty(busquedaForm.getConservada())) {
				vo.setConservada(busquedaForm.getConservada());
				busquedaForm.getCamposRellenos().add(
						CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_CONSERVADA);
			}
		}

		vo.setCamposRellenos(busquedaForm.getCamposRellenos());

		return vo;
	}
}