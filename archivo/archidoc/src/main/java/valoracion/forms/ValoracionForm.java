package valoracion.forms;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import valoracion.ValoracionConstants;
import valoracion.db.ValoracionDBEntityImpl;
import valoracion.vos.PlazoValoracionVO;
import valoracion.vos.ValoracionSerieVO;

import common.Constants;
import common.Messages;
import common.util.DateUtils;
import common.util.NumberUtils;
import common.util.StringUtils;
import common.util.TypeConverter;

import es.archigest.framework.web.action.ArchigestActionForm;
import gcontrol.ControlAccesoConstants;
import gcontrol.vos.NivelArchivoVO;

/**
 * Formulario para recogida de datos durante la gestion de valoraciones de
 * series documentales
 * 
 */
public class ValoracionForm extends ArchigestActionForm {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	protected static final Logger logger = Logger
			.getLogger(ValoracionForm.class);

	private String id = null;
	private String idSerie = null;
	private String titulo = null;
	private String estado = null;
	private String fechaEstado = null;
	private String motivoRechazo = null;
	private String ordenacionSerie1 = null;
	private String ordenacionSerie2 = null;
	private String seriesPrecedentes[] = null;
	private String seriesDescendientes[] = null;
	private String seriesRelacionadas[] = null;
	private String documentosRecopilatorios = null;
	private String tipoValorAdministrativo = null;
	private String anosVigenciaAdministrativa = null;
	private String valorAdministrativo = null;
	private String tipoValorLegal = null;
	private String anosVigenciaLegal = null;
	private String valorLegal = null;
	private String tipoValorInformativo = null;
	private String valorInformativo = null;
	private String tipoValorCientifico = null;
	private String valorCientifico = null;
	private String tipoValorTecnologico = null;
	private String valorTecnologico = null;
	private String tipoValorCultural = null;
	private String valorCultural = null;
	private String tipoValorHistorico = null;
	private String valorHistorico = null;
	private String tecnicaMuestreo = null;
	private String numRegistro = null;
	private String fechaValidacion = null;
	private String fechaEvaluacion = null;
	private String tipoDictamen = null;
	private String fechaDictamen = null;
	private String valorDictamen = null;
	private String valorDictamenValue = null;
	private String fechaResolucionDictamen = null;
	private String boletinDictamen = null;
	private String fechaBoletinDictamen = null;
	private String idUsuarioGestor = null;
	private String tipoRegimenAcceso = null;
	private String regimenAcceso = null;
	private String anosRegimenAcceso = null;
	private String tipoRegimenAccesoTemporal = null;

	private String[] campo_nivelArchivoOrigen = null;
	private String[] campo_nivelArchivoDestino = null;
	private String[] campo_plazo = null;

	private String[] valoracionSeleccionada = null;
	private String[] serieIDs = null;
	private boolean autorizarSolicitudValidacionAutomaticamente = false;

	/**
	 * Valida el formulario
	 * 
	 * @param mapping
	 *            {@link ActionMapping}con los mapeos asociado.
	 * @param request
	 *            {@link HttpServletRequest}
	 */
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		ActionErrors errors = new ActionErrors();

		// Titulo
		if (StringUtils.isBlank(this.titulo))
			errors.add(
					ActionErrors.GLOBAL_MESSAGE,
					new ActionError(
							Constants.ERROR_REQUIRED,
							Messages.getString(
									ValoracionConstants.LABEL_VALORACION_TITULO,
									request.getLocale())));

		// Valor Administrativo
		if (StringUtils.isBlank(this.tipoValorAdministrativo)) {
			errors.add(
					ActionErrors.GLOBAL_MESSAGE,
					new ActionError(
							Constants.ERROR_REQUIRED,
							Messages.getString(
									ValoracionConstants.LABEL_VALORACION_VALOR_ADMINISTRTIVO,
									request.getLocale())));
		} else if (this.tipoValorAdministrativo.equals(""
				+ ValoracionSerieVO.VALOR_ADMINISTRATIVO_TEMPORAL)) {
			if (StringUtils.isBlank(this.anosVigenciaAdministrativa))
				errors.add(
						ActionErrors.GLOBAL_MESSAGE,
						new ActionError(
								Constants.ERROR_REQUIRED,
								Messages.getString(
										Constants.VALORACION_PERIODOVIGENCIA_ADM,
										request.getLocale())));
			else if (!GenericValidator.isInt(this.anosVigenciaAdministrativa))
				errors.add(
						ActionErrors.GLOBAL_MESSAGE,
						new ActionError(
								Constants.ERROR_ANIO,
								Messages.getString(
										Constants.VALORACION_PERIODOVIGENCIA_ADM,
										request.getLocale())));
			else if (GenericValidator.isInt(this.anosVigenciaAdministrativa)
					&& Integer.parseInt(this.anosVigenciaAdministrativa) < 0) {
				errors.add(
						ActionErrors.GLOBAL_MESSAGE,
						new ActionError(
								Constants.ERROR_NEGATIVE_NUMBER,
								Messages.getString(
										Constants.VALORACION_PERIODOVIGENCIA_ADM,
										request.getLocale())));

			}
		} else
			this.anosVigenciaAdministrativa = null;

		// Valor Legal
		if (StringUtils.isBlank(this.tipoValorLegal)) {
			errors.add(
					ActionErrors.GLOBAL_MESSAGE,
					new ActionError(
							Constants.ERROR_REQUIRED,
							Messages.getString(
									ValoracionConstants.LABEL_VALORACION_VALOR_LEGAL,
									request.getLocale())));
		} else if (this.tipoValorLegal.equals(""
				+ ValoracionSerieVO.VALOR_LEGAL_TEMPORAL)) {
			if (StringUtils.isBlank(this.anosVigenciaLegal))
				errors.add(
						ActionErrors.GLOBAL_MESSAGE,
						new ActionError(
								Constants.ERROR_REQUIRED,
								Messages.getString(
										Constants.VALORACION_PERIODOVIGENCIA_LEGAL,
										request.getLocale())));
			else if (!GenericValidator.isInt(this.anosVigenciaLegal))
				errors.add(
						ActionErrors.GLOBAL_MESSAGE,
						new ActionError(
								Constants.ERROR_ANIO,
								Messages.getString(
										Constants.VALORACION_PERIODOVIGENCIA_LEGAL,
										request.getLocale())));

			else if (GenericValidator.isInt(this.anosVigenciaLegal)
					&& Integer.parseInt(this.anosVigenciaLegal) < 0) {
				errors.add(
						ActionErrors.GLOBAL_MESSAGE,
						new ActionError(
								Constants.ERROR_NEGATIVE_NUMBER,
								Messages.getString(
										Constants.VALORACION_PERIODOVIGENCIA_LEGAL,
										request.getLocale())));

			}

			else if (this.tipoValorAdministrativo.equals(""
					+ ValoracionSerieVO.VALOR_ADMINISTRATIVO_TEMPORAL)) {
				if (!StringUtils.isBlank(this.anosVigenciaAdministrativa)
						&& GenericValidator
								.isInt(this.anosVigenciaAdministrativa)) {
					int anosVigenciaLegal = Integer
							.parseInt(this.anosVigenciaLegal);
					int anosVigenciaAdministrativa = Integer
							.parseInt(this.anosVigenciaAdministrativa);
					if (anosVigenciaLegal < anosVigenciaAdministrativa) {
						errors.add(
								ActionErrors.GLOBAL_MESSAGE,
								new ActionError(
										Constants.ERROR_VALOR_DEBE_SER_MAYOR_QUE_VALOR,
										Messages.getString(
												Constants.VALORACION_PERIODOVIGENCIA_LEGAL,
												request.getLocale()),
										Messages.getString(
												Constants.VALORACION_PERIODOVIGENCIA_ADM,
												request.getLocale())));
					}
				}
			}
		} else
			this.anosVigenciaLegal = null;

		// Valor Informativo
		if (this.tipoValorInformativo.equals(""
				+ ValoracionSerieVO.VALOR_INFORMATIVO_EXISTE)) {
			this.valorInformativo = null;

			if (tipoValorCientifico.equals(""
					+ ValoracionSerieVO.VALOR_CIENTIFICO_NO_EXISTE)
					&& tipoValorTecnologico.equals(""
							+ ValoracionSerieVO.VALOR_TECNOLOGICO_NO_EXISTE)
					&& tipoValorCultural.equals(""
							+ ValoracionSerieVO.VALOR_CULTURAL_NO_EXISTE)) {
				errors.add(
						ActionErrors.GLOBAL_MESSAGE,
						new ActionError(
								ValoracionConstants.ERROR_VALORACION_VALORINFORMATIVO_SI));
			} else {
				if ((tipoValorCientifico != null)
						&& tipoValorCientifico.equals(""
								+ ValoracionSerieVO.VALOR_CIENTIFICO_EXISTE)
						&& StringUtils.isBlank(valorCientifico))
					errors.add(
							ActionErrors.GLOBAL_MESSAGE,
							new ActionError(
									Constants.ERROR_REQUIRED,
									Messages.getString(
											Constants.VALORACION_VALORCIENTIFICO,
											request.getLocale())));

				if ((tipoValorTecnologico != null)
						&& tipoValorTecnologico.equals(""
								+ ValoracionSerieVO.VALOR_TECNOLOGICO_EXISTE)
						&& StringUtils.isBlank(valorTecnologico))
					errors.add(
							ActionErrors.GLOBAL_MESSAGE,
							new ActionError(
									Constants.ERROR_REQUIRED,
									Messages.getString(
											Constants.VALORACION_VALORTECNOLOGICO,
											request.getLocale())));

				if ((tipoValorCultural != null)
						&& tipoValorCultural.equals(""
								+ ValoracionSerieVO.VALOR_CULTURAL_EXISTE)
						&& StringUtils.isBlank(valorCultural))
					errors.add(
							ActionErrors.GLOBAL_MESSAGE,
							new ActionError(Constants.ERROR_REQUIRED, Messages
									.getString(
											Constants.VALORACION_VALORCULTURAL,
											request.getLocale())));
			}
		} else // if (this.tipoValorInformativo.equals("" +
				// ValoracionSerieVO.VALOR_INFORMATIVO_NO_EXISTE))
		{
			this.tipoValorCientifico = ""
					+ ValoracionSerieVO.VALOR_CIENTIFICO_NO_EXISTE;
			this.valorCientifico = null;
			this.tipoValorTecnologico = ""
					+ ValoracionSerieVO.VALOR_TECNOLOGICO_NO_EXISTE;
			this.valorTecnologico = null;
			this.tipoValorCultural = ""
					+ ValoracionSerieVO.VALOR_CULTURAL_NO_EXISTE;
			this.valorCultural = null;
		}

		// Régimen de Acceso
		if (StringUtils.isBlank(this.tipoRegimenAcceso)) {
			errors.add(
					ActionErrors.GLOBAL_MESSAGE,
					new ActionError(Constants.ERROR_REQUIRED, Messages
							.getString(Constants.VALORACION_REGIMENACCESO,
									request.getLocale())));
		} else if (this.tipoRegimenAcceso.equals(""
				+ ValoracionSerieVO.REGIMEN_ACCESO_RESTRINGIDO_TEMPORAL)) {
			if (StringUtils.isBlank(this.anosRegimenAcceso))
				errors.add(
						ActionErrors.GLOBAL_MESSAGE,
						new ActionError(
								Constants.ERROR_REQUIRED,
								Messages.getString(
										ValoracionConstants.LABEL_VALORACION_PERIODO_REGIMEN_ACCESO,
										request.getLocale())));
			else if (!GenericValidator.isInt(this.anosRegimenAcceso))
				errors.add(
						ActionErrors.GLOBAL_MESSAGE,
						new ActionError(
								Constants.ERROR_ANIO,
								Messages.getString(
										ValoracionConstants.LABEL_VALORACION_PERIODO_REGIMEN_ACCESO,
										request.getLocale())));
		} else
			this.anosRegimenAcceso = null;

		// Dictamen
		if (StringUtils.isBlank(this.valorDictamen)) {
			errors.add(
					ActionErrors.GLOBAL_MESSAGE,
					new ActionError(
							Constants.ERROR_REQUIRED,
							Messages.getString(
									ValoracionConstants.LABEL_VALORACION_VALORES_DICTAMEN,
									request.getLocale())));
		} else if ((this.tipoValorAdministrativo.equals(""
				+ ValoracionSerieVO.VALOR_ADMINISTRATIVO_PERMANENTE) || this.tipoValorLegal
				.equals("" + ValoracionSerieVO.VALOR_LEGAL_PERMANENTE))
				&& !valorDictamen.equalsIgnoreCase(""
						+ ValoracionSerieVO.VALOR_DICTAMEN_CONSERVACION_TOTAL))
			errors.add(
					ActionErrors.GLOBAL_MESSAGE,
					new ActionError(
							ValoracionConstants.ERROR_VALORACION_VALORDICTAMEN_NO_VALIDO));

		// Esto no sería un error, es posible conservar totalmente una serie
		// aunque su vigencia legal o administrativa sea temporal
		/*
		 * else if ( this.tipoValorAdministrativo.equals("" +
		 * ValoracionSerieVO.VALOR_ADMINISTRATIVO_TEMPORAL) &&
		 * this.tipoValorLegal.equals("" +
		 * ValoracionSerieVO.VALOR_LEGAL_TEMPORAL) &&
		 * valorDictamen.equalsIgnoreCase
		 * (""+ValoracionSerieVO.VALOR_DICTAMEN_CONSERVACION_TOTAL) )
		 * errors.add(ActionErrors.GLOBAL_MESSAGE, new
		 * ActionError(ValoracionConstants
		 * .ERROR_VALORACION_VALORDICTAMEN_NO_VALIDO_2));
		 */
		if (errors.size() == 0)
			validateLengthTextArea(request, errors);
		validatePlazosValoracion(request, errors);

		return errors;
	}

	/**
	 * Comprueba si la longitud de una cadena es menor que un tamaño. Si no lo
	 * es se añade un nuevo ActionError explicativo con el nombre del campo.
	 * 
	 * @param errors
	 *            El contenedor de los errores
	 * @param maxLength
	 *            La anchura maxima de la cadena
	 * @param campo
	 *            La cadena a comprobar
	 * @param campoNombre
	 *            El nombre del campo que se esta comprobando
	 * @return El contenedor de los errores (con el nuevo error error añadido si
	 *         la cadena Era demasiado larga.
	 */
	private ActionErrors checkLengthTextArea(ActionErrors errors,
			int maxLength, String campo, String nombreCampo) {
		if (!GenericValidator.maxLength(campo, maxLength)) {
			errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError(
					Constants.ERROR_CAMPO_DEMASIADO_LARGO, new Object[] {
							nombreCampo, new Integer(maxLength),
							new Integer(campo.length()) }));
		}
		return errors;
	}

	/**
	 * Valida las longitudes de los campos mapeados sobre componentes textarea.
	 * 
	 * @param errors
	 *            El contenedor de los errores.
	 * @return El contenedor de errores actualizado
	 */
	public ActionErrors validateLengthTextArea(HttpServletRequest request,
			ActionErrors errors) {
		// mapeado sobre un campo CLOB -> no es necesario validacion
		// this.valorDictamenValue

		checkLengthTextArea(errors,
				ValoracionDBEntityImpl.CAMPO_DOCSRECOPILATORIOS.getMaxLen(),
				documentosRecopilatorios, Messages.getString(
						Constants.VALORACION_DOCUMENTOSRECOPILATORIOS,
						request.getLocale()));

		checkLengthTextArea(
				errors,
				ValoracionDBEntityImpl.CAMPO_VALORADMJUST.getMaxLen(),
				valorAdministrativo,
				Messages.getString(Constants.VALORACION_JUSTIFICACION,
						request.getLocale())
						+ " "
						+ Messages.getString(
								Constants.VALORACION_PERIODOVIGENCIA_ADM,
								request.getLocale()));

		checkLengthTextArea(
				errors,
				ValoracionDBEntityImpl.CAMPO_VALORLEGALJUST.getMaxLen(),
				valorLegal,
				Messages.getString(Constants.VALORACION_JUSTIFICACION,
						request.getLocale())
						+ " "
						+ Messages.getString(
								Constants.VALORACION_PERIODOVIGENCIA_LEGAL,
								request.getLocale()));

		if ((tipoValorInformativo != null && valorInformativo != null)
				&& (tipoValorInformativo.equals(""
						+ ValoracionSerieVO.VALOR_INFORMATIVO_EXISTE))) {
			checkLengthTextArea(
					errors,
					ValoracionDBEntityImpl.CAMPO_VALORINFJUST.getMaxLen(),
					valorInformativo,
					Messages.getString(Constants.VALORACION_JUSTIFICACION,
							request.getLocale())
							+ " "
							+ Messages.getString(
									Constants.VALORACION_VALORESINFORMATIVOS,
									request.getLocale()));
		}

		if ((tipoValorCientifico != null)
				&& (tipoValorCientifico.equals(""
						+ ValoracionSerieVO.VALOR_CIENTIFICO_EXISTE))) {
			checkLengthTextArea(errors,
					ValoracionDBEntityImpl.CAMPO_VALORCIENTIFICO.getMaxLen(),
					valorCientifico, Messages.getString(
							Constants.VALORACION_VALORCIENTIFICO,
							request.getLocale()));
		}

		if ((tipoValorTecnologico != null)
				&& (tipoValorTecnologico.equals(""
						+ ValoracionSerieVO.VALOR_TECNOLOGICO_EXISTE))) {
			checkLengthTextArea(errors,
					ValoracionDBEntityImpl.CAMPO_VALORTECNOLOGICO.getMaxLen(),
					valorTecnologico, Messages.getString(
							Constants.VALORACION_VALORTECNOLOGICO,
							request.getLocale()));
		}

		if ((tipoValorCultural != null)
				&& (tipoValorCultural.equals(""
						+ ValoracionSerieVO.VALOR_CULTURAL_EXISTE))) {
			checkLengthTextArea(errors,
					ValoracionDBEntityImpl.CAMPO_VALORCULTURAL.getMaxLen(),
					valorCultural, Messages.getString(
							Constants.VALORACION_VALORCULTURAL,
							request.getLocale()));
		}

		checkLengthTextArea(
				errors,
				ValoracionDBEntityImpl.CAMPO_REGIMENACCESOJUST.getMaxLen(),
				regimenAcceso,
				Messages.getString(Constants.VALORACION_JUSTIFICACION,
						request.getLocale())
						+ " "
						+ Messages.getString(
								Constants.VALORACION_REGIMENACCESO,
								request.getLocale()));

		return errors;
	}

	/**
	 * Valida el formulario para la autorización del dictamen.
	 * 
	 * @param mapping
	 *            {@link ActionMapping}con los mapeos asociado.
	 * @param request
	 *            {@link HttpServletRequest}
	 * @return Errores en la validación.
	 */
	public ActionErrors validateAutorizacionDictamen(ActionMapping mapping,
			HttpServletRequest request) {
		ActionErrors errors = new ActionErrors();

		Date dateFechaDictamen = DateUtils.getDate(this.getFechaDictamen());
		Date dateFechaResolucionDictamen = DateUtils.getDate(this
				.getFechaResolucionDictamen());
		Date dateFechaBoletinDictamen = DateUtils.getDate(this
				.getFechaBoletinDictamen());
		Date fechaActual = DateUtils.getFechaActual();

		if (dateFechaDictamen == null)
			errors.add(
					ActionErrors.GLOBAL_ERROR,
					new ActionError(
							Constants.ERROR_DATE,
							Messages.getString(
									ValoracionConstants.LABEL_VALORACION_FECHA_DICTAMEN,
									request.getLocale())));
		else if (dateFechaDictamen.compareTo(fechaActual) > 0)
			errors.add(
					ActionErrors.GLOBAL_ERROR,
					new ActionError(
							Constants.ERROR_DATE_AFTER_TODAY,
							Messages.getString(
									ValoracionConstants.LABEL_VALORACION_FECHA_DICTAMEN,
									request.getLocale())));

		if (dateFechaResolucionDictamen == null)
			errors.add(
					ActionErrors.GLOBAL_ERROR,
					new ActionError(
							Constants.ERROR_DATE,
							Messages.getString(
									ValoracionConstants.LABEL_VALORACION_FECHA_RESOLUCION_DICTAMEN,
									request.getLocale())));
		else if (dateFechaResolucionDictamen.compareTo(fechaActual) > 0)
			errors.add(
					ActionErrors.GLOBAL_ERROR,
					new ActionError(
							Constants.ERROR_DATE_AFTER_TODAY,
							Messages.getString(
									ValoracionConstants.LABEL_VALORACION_FECHA_RESOLUCION_DICTAMEN,
									request.getLocale())));

		if (StringUtils.isBlank(this.getBoletinDictamen()))
			errors.add(
					ActionErrors.GLOBAL_ERROR,
					new ActionError(
							Constants.ERROR_REQUIRED,
							Messages.getString(
									ValoracionConstants.LABEL_VALORACION_BOLETIN_DICTAMEN,
									request.getLocale())));

		if (dateFechaBoletinDictamen == null)
			errors.add(
					ActionErrors.GLOBAL_ERROR,
					new ActionError(
							Constants.ERROR_DATE,
							Messages.getString(
									ValoracionConstants.LABEL_VALORACION_FECHA_BOLETIN_DICTAMEN,
									request.getLocale())));
		else if (dateFechaBoletinDictamen.compareTo(fechaActual) > 0)
			errors.add(
					ActionErrors.GLOBAL_ERROR,
					new ActionError(
							Constants.ERROR_DATE_AFTER_TODAY,
							Messages.getString(
									ValoracionConstants.LABEL_VALORACION_FECHA_BOLETIN_DICTAMEN,
									request.getLocale())));

		/*
		 * Comprobar que la fecha de publicación sea posterior a la fecha de
		 * resolución.
		 */
		if ((dateFechaBoletinDictamen != null)
				&& (dateFechaResolucionDictamen != null)
				&& (dateFechaBoletinDictamen
						.compareTo(dateFechaResolucionDictamen) < 0))
			errors.add(
					ActionErrors.GLOBAL_ERROR,
					new ActionError(
							Constants.ERROR_DATE_AFTER,
							new Object[] {
									Messages.getString(
											ValoracionConstants.LABEL_VALORACION_FECHA_BOLETIN_DICTAMEN,
											request.getLocale()),
									Messages.getString(
											ValoracionConstants.LABEL_VALORACION_FECHA_RESOLUCION_DICTAMEN,
											request.getLocale()) }));

		/*
		 * Comprobar que la fecha de resolución sea posterior a la fecha de
		 * dictamen.
		 */
		if ((dateFechaResolucionDictamen != null)
				&& (dateFechaDictamen != null)
				&& (dateFechaResolucionDictamen.compareTo(dateFechaDictamen) < 0))
			errors.add(
					ActionErrors.GLOBAL_ERROR,
					new ActionError(
							Constants.ERROR_DATE_AFTER,
							new Object[] {
									Messages.getString(
											ValoracionConstants.LABEL_VALORACION_FECHA_RESOLUCION_DICTAMEN,
											request.getLocale()),
									Messages.getString(
											ValoracionConstants.LABEL_VALORACION_FECHA_DICTAMEN,
											request.getLocale()) }));

		return errors;
	}

	/**
	 * Valida el formulario para el rechazo del dictamen.
	 * 
	 * @param mapping
	 *            {@link ActionMapping}con los mapeos asociado.
	 * @param request
	 *            {@link HttpServletRequest}
	 * @return Errores en la validación.
	 */
	public ActionErrors validateRechazoDictamen(ActionMapping mapping,
			HttpServletRequest request) {
		ActionErrors errors = new ActionErrors();

		Date dateFechaDictamen = DateUtils.getDate(this.getFechaDictamen());
		Date fechaActual = DateUtils.getFechaActual();

		if (dateFechaDictamen == null)
			errors.add(
					ActionErrors.GLOBAL_ERROR,
					new ActionError(
							Constants.ERROR_DATE,
							Messages.getString(
									ValoracionConstants.LABEL_VALORACION_FECHA_DICTAMEN,
									request.getLocale())));
		else if (dateFechaDictamen.compareTo(fechaActual) > 0)
			errors.add(
					ActionErrors.GLOBAL_ERROR,
					new ActionError(
							Constants.ERROR_DATE_AFTER_TODAY,
							Messages.getString(
									ValoracionConstants.LABEL_VALORACION_FECHA_DICTAMEN,
									request.getLocale())));

		if (StringUtils.isBlank(this.getMotivoRechazo()))
			errors.add(
					ActionErrors.GLOBAL_ERROR,
					new ActionError(
							Constants.ERROR_REQUIRED,
							Messages.getString(
									ValoracionConstants.LABEL_VALORACION_MOTIVO_RECHAZO,
									request.getLocale())));

		return errors;
	}

	/**
	 * Construye un VO con la información del formulario.
	 * 
	 * @param vo
	 *            Value Object.
	 */
	public void populate(ValoracionSerieVO vo) {
		vo.setOrdenacionSerie1(TypeConverter.toInt(ordenacionSerie1, 0));
		vo.setOrdenacionSerie2(TypeConverter.toInt(ordenacionSerie2, 0));
		vo.setDocumentosRecopilatorios(documentosRecopilatorios);
		vo.setTipoValorAdministrativo(TypeConverter.toInt(
				tipoValorAdministrativo, 0));
		vo.setAnosVigenciaAdministrativa(TypeConverter.toInt(
				anosVigenciaAdministrativa, 0));
		vo.setValorAdministrativo(valorAdministrativo);
		vo.setTipoValorLegal(TypeConverter.toInt(tipoValorLegal, 0));
		vo.setAnosVigenciaLegal(TypeConverter.toInt(anosVigenciaLegal, 0));
		vo.setValorLegal(valorLegal);
		vo.setTipoValorInformativo(TypeConverter.toInt(tipoValorInformativo, 0));
		vo.setValorInformativo(valorInformativo);
		vo.setTipoValorCientifico(TypeConverter.toInt(tipoValorCientifico, 0));
		vo.setValorCientifico(valorCientifico);
		vo.setTipoValorTecnologico(TypeConverter.toInt(tipoValorTecnologico, 0));
		vo.setValorTecnologico(valorTecnologico);
		vo.setTipoValorCultural(TypeConverter.toInt(tipoValorCultural, 0));
		vo.setValorCultural(valorCultural);
		// vo.setTipoValorHistorico(TypeConverter.toInt(tipoValorHistorico, 0));
		// vo.setValorHistorico(valorHistorico);
		vo.setTecnicaMuestreo(TypeConverter.toInt(tecnicaMuestreo, 0));
		vo.setValorDictamen(TypeConverter.toInt(valorDictamen, 0));
		vo.setValorDictamenValue(valorDictamenValue);
		int intTipoRegimenAcceso = TypeConverter.toInt(tipoRegimenAcceso, 0);
		vo.setTipoRegimenAcceso(intTipoRegimenAcceso);
		vo.setRegimenAcceso(regimenAcceso);
		vo.setAnosRegimenAcceso(TypeConverter.toInt(anosRegimenAcceso, 0));
		if (intTipoRegimenAcceso == ValoracionSerieVO.REGIMEN_ACCESO_RESTRINGIDO_TEMPORAL)
			vo.setTipoRegimenAccesoTemporal(TypeConverter.toInt(
					tipoRegimenAccesoTemporal, 0));
		else
			vo.setTipoRegimenAccesoTemporal(0);
	}

	/**
	 * Asigna al formulatio los datos de la valoracion
	 * 
	 * @param vo
	 *            Value Object con los datos de la valoracion
	 */
	public void setValoracion(ValoracionSerieVO vo) {
		try {
			BeanUtils.copyProperties(this, vo);
		} catch (Exception e) {
		}
		setPlazosValoracionInForm(vo.getListaPlazos());
	}

	public String getAnosVigenciaAdministrativa() {
		return anosVigenciaAdministrativa;
	}

	public void setAnosVigenciaAdministrativa(String anosVigenciaAdministrativa) {
		this.anosVigenciaAdministrativa = anosVigenciaAdministrativa;
	}

	public String getAnosVigenciaLegal() {
		return anosVigenciaLegal;
	}

	public void setAnosVigenciaLegal(String anosVigenciaLegal) {
		this.anosVigenciaLegal = anosVigenciaLegal;
	}

	public String getBoletinDictamen() {
		return boletinDictamen;
	}

	public void setBoletinDictamen(String boletinDictamen) {
		this.boletinDictamen = boletinDictamen;
	}

	public String getDocumentosRecopilatorios() {
		return documentosRecopilatorios;
	}

	public void setDocumentosRecopilatorios(String documentosRecopilatorios) {
		this.documentosRecopilatorios = documentosRecopilatorios;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getFechaBoletinDictamen() {
		return fechaBoletinDictamen;
	}

	public void setFechaBoletinDictamen(String fechaBoletinDictamen) {
		this.fechaBoletinDictamen = fechaBoletinDictamen;
	}

	public String getFechaDictamen() {
		return fechaDictamen;
	}

	public void setFechaDictamen(String fechaDictamen) {
		this.fechaDictamen = fechaDictamen;
	}

	public String getFechaEstado() {
		return fechaEstado;
	}

	public void setFechaEstado(String fechaEstado) {
		this.fechaEstado = fechaEstado;
	}

	public String getFechaEvaluacion() {
		return fechaEvaluacion;
	}

	public void setFechaEvaluacion(String fechaEvaluacion) {
		this.fechaEvaluacion = fechaEvaluacion;
	}

	public String getFechaResolucionDictamen() {
		return fechaResolucionDictamen;
	}

	public void setFechaResolucionDictamen(String fechaResolucionDictamen) {
		this.fechaResolucionDictamen = fechaResolucionDictamen;
	}

	public String getFechaValidacion() {
		return fechaValidacion;
	}

	public void setFechaValidacion(String fechaValidacion) {
		this.fechaValidacion = fechaValidacion;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIdSerie() {
		return idSerie;
	}

	public void setIdSerie(String idSerie) {
		this.idSerie = idSerie;
	}

	public String getIdUsuarioGestor() {
		return idUsuarioGestor;
	}

	public void setIdUsuarioGestor(String idUsuarioGestor) {
		this.idUsuarioGestor = idUsuarioGestor;
	}

	public String getMotivoRechazo() {
		return motivoRechazo;
	}

	public void setMotivoRechazo(String motivoRechazo) {
		this.motivoRechazo = motivoRechazo;
	}

	public String getNumRegistro() {
		return numRegistro;
	}

	public void setNumRegistro(String numRegistro) {
		this.numRegistro = numRegistro;
	}

	public String getOrdenacionSerie1() {
		return ordenacionSerie1;
	}

	public void setOrdenacionSerie1(String ordenacionSerie1) {
		this.ordenacionSerie1 = ordenacionSerie1;
	}

	public String getOrdenacionSerie2() {
		return ordenacionSerie2;
	}

	public void setOrdenacionSerie2(String ordenacionSerie2) {
		this.ordenacionSerie2 = ordenacionSerie2;
	}

	public String[] getSeriesDescendientes() {
		return seriesDescendientes;
	}

	public void setSeriesDescendientes(String[] seriesDescendientes) {
		this.seriesDescendientes = seriesDescendientes;
	}

	public String[] getSeriesPrecedentes() {
		return seriesPrecedentes;
	}

	public void setSeriesPrecedentes(String[] seriesPrecedentes) {
		this.seriesPrecedentes = seriesPrecedentes;
	}

	public String[] getSeriesRelacionadas() {
		return seriesRelacionadas;
	}

	public void setSeriesRelacionadas(String[] seriesRelacionadas) {
		this.seriesRelacionadas = seriesRelacionadas;
	}

	public String getTecnicaMuestreo() {
		return tecnicaMuestreo;
	}

	public void setTecnicaMuestreo(String tecnicaMuestreo) {
		this.tecnicaMuestreo = tecnicaMuestreo;
	}

	public String getTipoValorAdministrativo() {
		return tipoValorAdministrativo;
	}

	public void setTipoValorAdministrativo(String tipoValorAdministrativo) {
		this.tipoValorAdministrativo = tipoValorAdministrativo;
	}

	public String getTipoValorHistorico() {
		return tipoValorHistorico;
	}

	public void setTipoValorHistorico(String tipoValorHistorico) {
		this.tipoValorHistorico = tipoValorHistorico;
	}

	public String getTipoValorInformativo() {
		return tipoValorInformativo;
	}

	public void setTipoValorInformativo(String tipoValorInformativo) {
		this.tipoValorInformativo = tipoValorInformativo;
	}

	public String getTipoValorLegal() {
		return tipoValorLegal;
	}

	public void setTipoValorLegal(String tipoValorLegal) {
		this.tipoValorLegal = tipoValorLegal;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getValorAdministrativo() {
		return valorAdministrativo;
	}

	public void setValorAdministrativo(String valorAdministrativo) {
		this.valorAdministrativo = valorAdministrativo;
	}

	public String getValorDictamen() {
		return valorDictamen;
	}

	public void setValorDictamen(String valorDictamen) {
		this.valorDictamen = valorDictamen;
	}

	public String getValorHistorico() {
		return valorHistorico;
	}

	public void setValorHistorico(String valorHistorico) {
		this.valorHistorico = valorHistorico;
	}

	public String getValorInformativo() {
		return valorInformativo;
	}

	public void setValorInformativo(String valorInformativo) {
		this.valorInformativo = valorInformativo;
	}

	public String getValorLegal() {
		return valorLegal;
	}

	public void setValorLegal(String valorLegal) {
		this.valorLegal = valorLegal;
	}

	public String[] getValoracionSeleccionada() {
		return valoracionSeleccionada;
	}

	public void setValoracionSeleccionada(String[] valoracionSeleccionada) {
		this.valoracionSeleccionada = valoracionSeleccionada;
	}

	public boolean getAutorizarSolicitudValidacionAutomaticamente() {
		return autorizarSolicitudValidacionAutomaticamente;
	}

	public void setAutorizarSolicitudValidacionAutomaticamente(
			boolean autorizarSolicitudValidacionAutomaticamente) {
		this.autorizarSolicitudValidacionAutomaticamente = autorizarSolicitudValidacionAutomaticamente;
	}

	public String[] getSerieIDs() {
		return serieIDs;
	}

	public void setSerieIDs(String[] serieIDs) {
		this.serieIDs = serieIDs;
	}

	public String getValorCientifico() {
		return valorCientifico;
	}

	public void setValorCientifico(String valorCientifico) {
		this.valorCientifico = valorCientifico;
	}

	public String getValorCultural() {
		return valorCultural;
	}

	public void setValorCultural(String valorCultural) {
		this.valorCultural = valorCultural;
	}

	public String getValorTecnologico() {
		return valorTecnologico;
	}

	public void setValorTecnologico(String valorTecnologico) {
		this.valorTecnologico = valorTecnologico;
	}

	public String getTipoValorCientifico() {
		return tipoValorCientifico;
	}

	public void setTipoValorCientifico(String tipoValorCientifico) {
		this.tipoValorCientifico = tipoValorCientifico;
	}

	public String getTipoValorCultural() {
		return tipoValorCultural;
	}

	public void setTipoValorCultural(String tipoValorCultural) {
		this.tipoValorCultural = tipoValorCultural;
	}

	public String getTipoValorTecnologico() {
		return tipoValorTecnologico;
	}

	public void setTipoValorTecnologico(String tipoValorTecnologico) {
		this.tipoValorTecnologico = tipoValorTecnologico;
	}

	public String getAnosRegimenAcceso() {
		return anosRegimenAcceso;
	}

	public void setAnosRegimenAcceso(String anosRegimenAcceso) {
		this.anosRegimenAcceso = anosRegimenAcceso;
	}

	public String getRegimenAcceso() {
		return regimenAcceso;
	}

	public void setRegimenAcceso(String regimenAcceso) {
		this.regimenAcceso = regimenAcceso;
	}

	public String getTipoRegimenAcceso() {
		return tipoRegimenAcceso;
	}

	public void setTipoRegimenAcceso(String tipoRegimenAcceso) {
		this.tipoRegimenAcceso = tipoRegimenAcceso;
	}

	public String getTipoDictamen() {
		return tipoDictamen;
	}

	public void setTipoDictamen(String tipoDictamen) {
		this.tipoDictamen = tipoDictamen;
	}

	public String getValorDictamenValue() {
		return valorDictamenValue;
	}

	public void setValorDictamenValue(String valorDictamenValue) {
		this.valorDictamenValue = valorDictamenValue;
	}

	public String getTipoRegimenAccesoTemporal() {
		return tipoRegimenAccesoTemporal;
	}

	public void setTipoRegimenAccesoTemporal(String tipoRegimenAccesoTemporal) {
		this.tipoRegimenAccesoTemporal = tipoRegimenAccesoTemporal;
	}

	public String[] getCampo_nivelArchivoOrigen() {
		return campo_nivelArchivoOrigen;
	}

	public void setCampo_nivelArchivoOrigen(String[] campo_nivelArchivoOrigen) {
		this.campo_nivelArchivoOrigen = campo_nivelArchivoOrigen;
	}

	public String[] getCampo_nivelArchivoDestino() {
		return campo_nivelArchivoDestino;
	}

	public void setCampo_nivelArchivoDestino(String[] campo_nivelArchivoDestino) {
		this.campo_nivelArchivoDestino = campo_nivelArchivoDestino;
	}

	public String[] getCampo_plazo() {
		return campo_plazo;
	}

	public void setCampo_plazo(String[] campo_plazo) {
		this.campo_plazo = campo_plazo;
	}

	public ActionErrors validatePlazosValoracion(HttpServletRequest request,
			ActionErrors errors) {
		if (campo_plazo == null)
			return errors;

		if (campo_plazo.length != campo_nivelArchivoOrigen.length
				|| campo_plazo.length != campo_nivelArchivoDestino.length) {
			// error si rellena algun campo de una fila en plazos de valoracion,
			// el resto de los campos de esa fila son obligatorios
			errors.add(
					ActionErrors.GLOBAL_ERROR,
					new ActionError(
							ValoracionConstants.ERROR_VALORACION_RESTO_CAMPOSFILA_OBLIGATORIOS));
		}

		if (campo_plazo.length == 1
				&& StringUtils.isEmpty(campo_nivelArchivoOrigen[0])
				&& StringUtils.isEmpty(campo_nivelArchivoDestino[0])
				&& StringUtils.isEmpty(campo_plazo[0])) {
			return errors;
		}

		// todas los arrays de valores de plazos tienen la misma longitud
		List nivelesArchivo = (List) request.getSession().getAttribute(
				ControlAccesoConstants.LISTA_NIVELES_ARCHIVO);
		HashMap nivelesArchivoHashMap = new HashMap();
		for (Iterator it = nivelesArchivo.iterator(); it.hasNext();) {
			NivelArchivoVO nivelArchivo = (NivelArchivoVO) it.next();
			nivelesArchivoHashMap.put(nivelArchivo.getId(), nivelArchivo);
		}

		boolean filaConAlgunError = false;
		for (int i = 0; i < campo_plazo.length; i++) {
			if (StringUtils.isEmpty(campo_nivelArchivoOrigen[i])) {
				// error el nivel de archivo origen de la fila N no puede estar
				// vacío
				errors.add(
						ActionErrors.GLOBAL_ERROR,
						new ActionError(
								ValoracionConstants.ERROR_VALORACION_PLAZOS_CAMPOVACIO,
								Messages.getString(
										ValoracionConstants.LABEL_VALORACION_PLAZOS_NIVELORIGEN,
										request.getLocale()).toLowerCase(), ""
										+ (i + 1)));
				filaConAlgunError = true;
			}

			if (StringUtils.isEmpty(campo_nivelArchivoDestino[i])) {
				// error el nivel de archivo destino de la fila N no puede estar
				// vacío
				errors.add(
						ActionErrors.GLOBAL_ERROR,
						new ActionError(
								ValoracionConstants.ERROR_VALORACION_PLAZOS_CAMPOVACIO,
								Messages.getString(
										ValoracionConstants.LABEL_VALORACION_PLAZOS_NIVELDESTINO,
										request.getLocale()).toLowerCase(), ""
										+ (i + 1)));
				filaConAlgunError = true;
			}

			if (StringUtils.isEmpty(campo_plazo[i])) {
				// error el plazo de la fila N no puede estar vacío
				errors.add(
						ActionErrors.GLOBAL_ERROR,
						new ActionError(
								ValoracionConstants.ERROR_VALORACION_PLAZOS_CAMPOVACIO,
								Messages.getString(
										ValoracionConstants.LABEL_VALORACION_PLAZOS_PLAZOANIOS,
										request.getLocale()).toLowerCase(), ""
										+ (i + 1)));
				filaConAlgunError = true;
			} else if (!StringUtils.isNumeric(campo_plazo[i])) {
				// error el plazo de años de la fila N debe ser un numero
				errors.add(
						ActionErrors.GLOBAL_ERROR,
						new ActionError(
								ValoracionConstants.ERROR_VALORACION_PLAZOS_PLAZOANIOS_NONUMERICO,
								"" + (i + 1)));
				filaConAlgunError = true;
			} else if (!NumberUtils.isShortInteger(campo_plazo[i])) {
				// error el numero en el campo X de la fila Y deberia ser un
				// entero corto
				errors.add(
						ActionErrors.GLOBAL_ERROR,
						new ActionError(
								ValoracionConstants.ERROR_GENERAL_FILA_NO_ENTERO_CORTO,
								Messages.getString(
										ValoracionConstants.LABEL_VALORACION_PLAZOS_PLAZOANIOS,
										request.getLocale()), "" + (i + 1)));
				filaConAlgunError = true;
			} else if (!NumberUtils.isNumeroMayorCero(campo_plazo[i])) {
				// error el numero en el campo X de la fila Y deberia ser un
				// entero positivo
				errors.add(
						ActionErrors.GLOBAL_ERROR,
						new ActionError(
								ValoracionConstants.ERROR_GENERAL_FILA_NO_ENTERO_POSITIVO,
								Messages.getString(
										ValoracionConstants.LABEL_VALORACION_PLAZOS_PLAZOANIOS,
										request.getLocale()), "" + (i + 1)));
				filaConAlgunError = true;
			}

			if (filaConAlgunError) {
				filaConAlgunError = false;
				continue;
			}

			// el nivel destino del archivo de una fila debe ser mayor que el id
			// del archivo origen de esa fila
			int ordenNivelArchivoOrigen = ((NivelArchivoVO) nivelesArchivoHashMap
					.get(campo_nivelArchivoOrigen[i])).getOrden().intValue();
			int ordenNivelArchivoDestino = ((NivelArchivoVO) nivelesArchivoHashMap
					.get(campo_nivelArchivoDestino[i])).getOrden().intValue();
			if (ordenNivelArchivoOrigen >= ordenNivelArchivoDestino) {
				// error en la fila N el orden del nivel del archivo origen debe
				// ser menor que el nivel del archivo destino
				errors.add(
						ActionErrors.GLOBAL_ERROR,
						new ActionError(
								ValoracionConstants.ERROR_VALORACION_ORDENORIGEN_MAYORIGUAL_ORDENDESTINO,
								"" + (i + 1)));
			}

			if (i > 0) {
				// comprobar que el orden del id del archivo origen de una fila
				// del plazo es
				// superior al id archivo origen de la fila anterior
				if (StringUtils.isEmpty(campo_nivelArchivoOrigen[i - 1]))
					continue;
				int ordenNivelArchivoOrigenAnterior = ((NivelArchivoVO) nivelesArchivoHashMap
						.get(campo_nivelArchivoOrigen[i - 1])).getOrden()
						.intValue();
				if (ordenNivelArchivoOrigenAnterior >= ordenNivelArchivoOrigen) {
					// error el orden del archivo origen de la fila N debe ser
					// mayor que el archivo origen de la fila anterior
					errors.add(
							ActionErrors.GLOBAL_ERROR,
							new ActionError(
									ValoracionConstants.ERROR_VALORACION_ORDENORIGENANTERIOR_MAYORIGUAL_ORDENORIGEN,
									"" + (i + 1)));
				}
			}
		}

		return errors;
	}

	/**
	 * Devuelve una lista de VOs rellenados con los valores en la misma posicion
	 * de los arrays de valores el formulario
	 * 
	 * @return la lista de VOs
	 */
	public List populateToListPlazosValoracion() {
		List listaPlazosVO = new ArrayList();
		if (campo_plazo == null
				|| (campo_plazo.length == 1
						&& StringUtils.isEmpty(campo_nivelArchivoOrigen[0])
						&& StringUtils.isEmpty(campo_nivelArchivoDestino[0]) && StringUtils
						.isEmpty(campo_plazo[0]))) {
			return listaPlazosVO;
		}

		if (campo_plazo == null)
			return listaPlazosVO;
		for (int i = 0; i < campo_plazo.length; i++) {
			PlazoValoracionVO vo = new PlazoValoracionVO();
			vo.setIdValSerie(id);
			vo.setIdNivelOrigen(campo_nivelArchivoOrigen[i]);
			vo.setIdNivelDestino(campo_nivelArchivoDestino[i]);
			vo.setPlazo(Integer.parseInt(campo_plazo[i]));
			vo.setOrden(i + 1);
			listaPlazosVO.add(vo);
		}
		return listaPlazosVO;
	}

	/**
	 * rellena los campos array del formalario para los plazos de valoracion con
	 * los valores de los VOs de la lista que recibe como parametro
	 * 
	 * param plazos la lista de plazos (plazosValoracionVO) ordenada por su
	 * campo orden
	 */
	public void setPlazosValoracionInForm(List plazos) {
		if (plazos == null || plazos.size() == 0)
			return;
		campo_nivelArchivoDestino = new String[plazos.size()];
		campo_nivelArchivoOrigen = new String[plazos.size()];
		campo_plazo = new String[plazos.size()];
		int i = 0;
		for (Iterator it = plazos.iterator(); it.hasNext();) {
			PlazoValoracionVO plazo = (PlazoValoracionVO) it.next();
			campo_nivelArchivoOrigen[i] = plazo.getIdNivelOrigen();
			campo_nivelArchivoDestino[i] = plazo.getIdNivelDestino();
			campo_plazo[i] = "" + plazo.getPlazo();
			i++;
		}
	}
}