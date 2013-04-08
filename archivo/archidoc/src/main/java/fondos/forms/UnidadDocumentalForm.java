package fondos.forms;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import common.Constants;
import common.Messages;
import common.pagination.PageInfo;
import common.util.CustomDate;
import common.util.CustomDateFormat;
import common.util.CustomDateRange;
import common.util.StringUtils;

import es.archigest.framework.web.action.ArchigestActionForm;
import fondos.vos.BusquedaUdocsSerieVO;
import fondos.vos.UnidadDocumentalVO;
import gcontrol.model.NivelAcceso;

/**
 * Formulario para la recogida de datos en la modificación de la información de
 * una unidad documental.
 */
public class UnidadDocumentalForm extends ArchigestActionForm implements
		IBusquedaForm {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String id = null;
	private int nivelAcceso = -1;
	private String idLCA = null;
	private String productor;
	private String idSerie;

	private String titulo = null;
	private String numeroExpediente = null;
	private String numeroExpediente_like = null;
	private String codigo = null;

	/* Fecha inicial */
	private String fechaCompIni = null;

	private String fechaFormatoIni = null;
	private String fechaAIni = null;
	private String fechaMIni = null;
	private String fechaDIni = null;
	private String fechaSIni = null;
	private String fechaIniFormatoIni = null;
	private String fechaIniAIni = null;
	private String fechaIniMIni = null;
	private String fechaIniDIni = null;
	private String fechaIniSIni = null;
	private String fechaFinFormatoIni = null;
	private String fechaFinAIni = null;
	private String fechaFinMIni = null;
	private String fechaFinDIni = null;
	private String fechaFinSIni = null;

	/* Fecha final */
	private String fechaCompFin = null;
	private String fechaFormatoFin = null;
	private String fechaAFin = null;
	private String fechaMFin = null;
	private String fechaDFin = null;
	private String fechaSFin = null;
	private String fechaIniFormatoFin = null;
	private String fechaIniAFin = null;
	private String fechaIniMFin = null;
	private String fechaIniDFin = null;
	private String fechaIniSFin = null;
	private String fechaFinFormatoFin = null;
	private String fechaFinAFin = null;
	private String fechaFinMFin = null;
	private String fechaFinDFin = null;
	private String fechaFinSFin = null;

	private String[] ids = null;

	/**
	 * Constructor.
	 */
	public UnidadDocumentalForm() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIdLCA() {
		return idLCA;
	}

	public void setIdLCA(String idLCA) {
		this.idLCA = idLCA;
	}

	public int getNivelAcceso() {
		return nivelAcceso;
	}

	public void setNivelAcceso(int nivelAcceso) {
		this.nivelAcceso = nivelAcceso;
	}

	public String getProductor() {
		return productor;
	}

	public void setProductor(String productor) {
		this.productor = productor;
	}

	public String getIdSerie() {
		return idSerie;
	}

	public void setIdSerie(String idSerie) {
		this.idSerie = idSerie;
	}

	public String getTitulo() {
		return titulo;
	}

	public String getNumeroExpediente() {
		return numeroExpediente;
	}

	public String getNumeroExpediente_like() {
		return numeroExpediente_like;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public void setNumeroExpediente(String numeroExpediente) {
		this.numeroExpediente = numeroExpediente;
	}

	public void setNumeroExpediente_like(String numeroExpedienteLike) {
		numeroExpediente_like = numeroExpedienteLike;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getFechaCompIni() {
		return fechaCompIni;
	}

	public String getFechaFormatoIni() {
		return fechaFormatoIni;
	}

	public String getFechaAIni() {
		return fechaAIni;
	}

	public String getFechaMIni() {
		return fechaMIni;
	}

	public String getFechaDIni() {
		return fechaDIni;
	}

	public String getFechaSIni() {
		return fechaSIni;
	}

	public String getFechaIniFormatoIni() {
		return fechaIniFormatoIni;
	}

	public String getFechaIniAIni() {
		return fechaIniAIni;
	}

	public String getFechaIniMIni() {
		return fechaIniMIni;
	}

	public String getFechaIniDIni() {
		return fechaIniDIni;
	}

	public String getFechaIniSIni() {
		return fechaIniSIni;
	}

	public String getFechaFinFormatoIni() {
		return fechaFinFormatoIni;
	}

	public String getFechaFinAIni() {
		return fechaFinAIni;
	}

	public String getFechaFinMIni() {
		return fechaFinMIni;
	}

	public String getFechaFinDIni() {
		return fechaFinDIni;
	}

	public String getFechaFinSIni() {
		return fechaFinSIni;
	}

	public String getFechaCompFin() {
		return fechaCompFin;
	}

	public String getFechaFormatoFin() {
		return fechaFormatoFin;
	}

	public String getFechaAFin() {
		return fechaAFin;
	}

	public String getFechaMFin() {
		return fechaMFin;
	}

	public String getFechaDFin() {
		return fechaDFin;
	}

	public String getFechaSFin() {
		return fechaSFin;
	}

	public String getFechaIniFormatoFin() {
		return fechaIniFormatoFin;
	}

	public String getFechaIniAFin() {
		return fechaIniAFin;
	}

	public String getFechaIniMFin() {
		return fechaIniMFin;
	}

	public String getFechaIniDFin() {
		return fechaIniDFin;
	}

	public String getFechaIniSFin() {
		return fechaIniSFin;
	}

	public String getFechaFinFormatoFin() {
		return fechaFinFormatoFin;
	}

	public String getFechaFinAFin() {
		return fechaFinAFin;
	}

	public String getFechaFinMFin() {
		return fechaFinMFin;
	}

	public String getFechaFinDFin() {
		return fechaFinDFin;
	}

	public String getFechaFinSFin() {
		return fechaFinSFin;
	}

	public void setFechaCompIni(String fechaCompIni) {
		this.fechaCompIni = fechaCompIni;
	}

	public void setFechaFormatoIni(String fechaFormatoIni) {
		this.fechaFormatoIni = fechaFormatoIni;
	}

	public void setFechaAIni(String fechaAIni) {
		this.fechaAIni = fechaAIni;
	}

	public void setFechaMIni(String fechaMIni) {
		this.fechaMIni = fechaMIni;
	}

	public void setFechaDIni(String fechaDIni) {
		this.fechaDIni = fechaDIni;
	}

	public void setFechaSIni(String fechaSIni) {
		this.fechaSIni = fechaSIni;
	}

	public void setFechaIniFormatoIni(String fechaIniFormatoIni) {
		this.fechaIniFormatoIni = fechaIniFormatoIni;
	}

	public void setFechaIniAIni(String fechaIniAIni) {
		this.fechaIniAIni = fechaIniAIni;
	}

	public void setFechaIniMIni(String fechaIniMIni) {
		this.fechaIniMIni = fechaIniMIni;
	}

	public void setFechaIniDIni(String fechaIniDIni) {
		this.fechaIniDIni = fechaIniDIni;
	}

	public void setFechaIniSIni(String fechaIniSIni) {
		this.fechaIniSIni = fechaIniSIni;
	}

	public void setFechaFinFormatoIni(String fechaFinFormatoIni) {
		this.fechaFinFormatoIni = fechaFinFormatoIni;
	}

	public void setFechaFinAIni(String fechaFinAIni) {
		this.fechaFinAIni = fechaFinAIni;
	}

	public void setFechaFinMIni(String fechaFinMIni) {
		this.fechaFinMIni = fechaFinMIni;
	}

	public void setFechaFinDIni(String fechaFinDIni) {
		this.fechaFinDIni = fechaFinDIni;
	}

	public void setFechaFinSIni(String fechaFinSIni) {
		this.fechaFinSIni = fechaFinSIni;
	}

	public void setFechaCompFin(String fechaCompFin) {
		this.fechaCompFin = fechaCompFin;
	}

	public void setFechaFormatoFin(String fechaFormatoFin) {
		this.fechaFormatoFin = fechaFormatoFin;
	}

	public void setFechaAFin(String fechaAFin) {
		this.fechaAFin = fechaAFin;
	}

	public void setFechaMFin(String fechaMFin) {
		this.fechaMFin = fechaMFin;
	}

	public void setFechaDFin(String fechaDFin) {
		this.fechaDFin = fechaDFin;
	}

	public void setFechaSFin(String fechaSFin) {
		this.fechaSFin = fechaSFin;
	}

	public void setFechaIniFormatoFin(String fechaIniFormatoFin) {
		this.fechaIniFormatoFin = fechaIniFormatoFin;
	}

	public void setFechaIniAFin(String fechaIniAFin) {
		this.fechaIniAFin = fechaIniAFin;
	}

	public void setFechaIniMFin(String fechaIniMFin) {
		this.fechaIniMFin = fechaIniMFin;
	}

	public void setFechaIniDFin(String fechaIniDFin) {
		this.fechaIniDFin = fechaIniDFin;
	}

	public void setFechaIniSFin(String fechaIniSFin) {
		this.fechaIniSFin = fechaIniSFin;
	}

	public void setFechaFinFormatoFin(String fechaFinFormatoFin) {
		this.fechaFinFormatoFin = fechaFinFormatoFin;
	}

	public void setFechaFinAFin(String fechaFinAFin) {
		this.fechaFinAFin = fechaFinAFin;
	}

	public void setFechaFinMFin(String fechaFinMFin) {
		this.fechaFinMFin = fechaFinMFin;
	}

	public void setFechaFinDFin(String fechaFinDFin) {
		this.fechaFinDFin = fechaFinDFin;
	}

	public void setFechaFinSFin(String fechaFinSFin) {
		this.fechaFinSFin = fechaFinSFin;
	}

	public void set(UnidadDocumentalVO udoc) {
		if (udoc != null) {
			setId(udoc.getId());
			setNivelAcceso(udoc.getNivelAcceso());
			setIdLCA(udoc.getIdLCA());
		}
	}

	/**
	 * Valida el formulario
	 * 
	 * @param mapping
	 *            {@link ActionMapping} con los mapeos asociado.
	 * @param request
	 *            {@link HttpServletRequest}
	 */
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		ActionErrors errors = new ActionErrors();

		// Nivel de acceso
		if (nivelAcceso < 0) {
			errors.add(
					ActionErrors.GLOBAL_MESSAGE,
					new ActionError(Constants.ERROR_REQUIRED, Messages
							.getString(Constants.ETIQUETA_NIVEL_ACCESO,
									request.getLocale())));
		}

		// Si el Nivel de acceso es RESTRINGIDO, debe tener lista de control de
		// acceso
		if ((nivelAcceso == NivelAcceso.RESTRINGIDO)
				&& StringUtils.isBlank(idLCA)) {
			errors.add(
					ActionErrors.GLOBAL_MESSAGE,
					new ActionError(Constants.ERROR_REQUIRED, Messages
							.getString(Constants.ETIQUETA_LISTA_CONTROL_ACCESO,
									request.getLocale())));
		}

		return errors;
	}

	public ActionErrors validateCamposBusqueda(ActionMapping mapping,
			HttpServletRequest request) {
		ActionErrors errors = new ActionErrors();

		// Fechas
		if (!new CustomDate(getFechaFormatoIni(), getFechaAIni(),
				getFechaMIni(), getFechaDIni(), getFechaSIni()).validate()
				|| !new CustomDate(getFechaIniFormatoIni(), getFechaIniAIni(),
						getFechaIniMIni(), getFechaIniDIni(), getFechaIniSIni())
						.validate()
				|| !new CustomDate(getFechaFinFormatoIni(), getFechaFinAIni(),
						getFechaFinMIni(), getFechaFinDIni(), getFechaFinSIni())
						.validate()) {

			errors.add(
					ActionErrors.GLOBAL_MESSAGE,
					new ActionError(Constants.ERROR_DATE, Messages.getString(
							Constants.ETIQUETA_FECHA_INICIAL,
							request.getLocale())));
		}

		if (!new CustomDate(getFechaFormatoFin(), getFechaAFin(),
				getFechaMFin(), getFechaDFin(), getFechaSFin()).validate()
				|| !new CustomDate(getFechaFinFormatoIni(), getFechaFinAIni(),
						getFechaFinMIni(), getFechaFinDIni(), getFechaFinSIni())
						.validate()
				|| !new CustomDate(getFechaFinFormatoFin(), getFechaFinAFin(),
						getFechaFinMFin(), getFechaFinDFin(), getFechaFinSFin())
						.validate()) {
			errors.add(
					ActionErrors.GLOBAL_MESSAGE,
					new ActionError(Constants.ERROR_DATE,
							Messages.getString(Constants.ETIQUETA_FECHA_FINAL,
									request.getLocale())));
		}

		return errors;
	}

	public BusquedaUdocsSerieVO getBusquedaUdocsSerieVO() {

		BusquedaUdocsSerieVO vo = new BusquedaUdocsSerieVO();

		// FECHA INICIAL
		CustomDateRange range = CustomDateFormat
				.getDateRange(getFechaCompIni(), new CustomDate(
						getFechaFormatoIni(), getFechaAIni(), getFechaMIni(),
						getFechaDIni(), getFechaSIni()),
						new CustomDate(getFechaIniFormatoIni(),
								getFechaIniAIni(), getFechaIniMIni(),
								getFechaIniDIni(), getFechaIniSIni()),
						new CustomDate(getFechaFinFormatoIni(),
								getFechaFinAIni(), getFechaFinMIni(),
								getFechaFinDIni(), getFechaFinSIni()));

		vo.setFechaIniIni(range.getInitialDate());
		vo.setFechaIniFin(range.getFinalDate());

		// FECHA FINAL
		CustomDateRange range2 = CustomDateFormat
				.getDateRange(getFechaCompFin(), new CustomDate(
						getFechaFormatoFin(), getFechaAFin(), getFechaMFin(),
						getFechaDFin(), getFechaSFin()),
						new CustomDate(getFechaFinFormatoIni(),
								getFechaFinAIni(), getFechaFinMIni(),
								getFechaFinDIni(), getFechaFinSIni()),
						new CustomDate(getFechaFinFormatoFin(),
								getFechaFinAFin(), getFechaFinMFin(),
								getFechaFinDFin(), getFechaFinSFin()));

		vo.setFechaFinIni(range2.getInitialDate());
		vo.setFechaFinFin(range2.getFinalDate());

		// TITULO
		if (StringUtils.isNotEmpty(getTitulo())) {
			vo.setTitulo(getTitulo());
		}

		// PRODUCTOR
		vo.setProductor(getProductor());

		// CODIGO
		if (StringUtils.isNotEmpty(getCodigo())) {
			vo.setCodigo(codigo);
		}

		// NUMERO EXPEDIENTE
		if (StringUtils.isNotEmpty(getNumeroExpediente())) {
			vo.setNumeroExpediente(numeroExpediente);
		}

		return vo;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#getAbrirpar()
	 */
	public String[] getAbrirpar() {
		// TODO Plantilla de método auto-generado
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#getBloqueos()
	 */
	public String[] getBloqueos() {
		// TODO Plantilla de método auto-generado
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#getBooleano()
	 */
	public String[] getBooleano() {
		// TODO Plantilla de método auto-generado
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#getCalificador()
	 */
	public String getCalificador() {
		// TODO Plantilla de método auto-generado
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#getCalificadorFinal()
	 */
	public String getCalificadorFinal() {
		// TODO Plantilla de método auto-generado
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#getCalificadorInicial()
	 */
	public String getCalificadorInicial() {
		// TODO Plantilla de método auto-generado
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#getCampo()
	 */
	public String[] getCampo() {
		// TODO Plantilla de método auto-generado
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#getCamposRellenos()
	 */
	public ArrayList getCamposRellenos() {
		// TODO Plantilla de método auto-generado
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#getCerrarpar()
	 */
	public String[] getCerrarpar() {
		// TODO Plantilla de método auto-generado
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#getCodigoReferencia()
	 */
	public String getCodigoReferencia() {
		// TODO Plantilla de método auto-generado
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#getCodigoRelacion()
	 */
	public String getCodigoRelacion() {
		// TODO Plantilla de método auto-generado
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#getContenidoFichero()
	 */
	public String getContenidoFichero() {
		// TODO Plantilla de método auto-generado
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#getDescriptores()
	 */
	public String getDescriptores() {
		// TODO Plantilla de método auto-generado
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#getEstados()
	 */
	public String[] getEstados() {
		// TODO Plantilla de método auto-generado
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#getFechaA()
	 */
	public String getFechaA() {
		// TODO Plantilla de método auto-generado
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#getFechaComp()
	 */
	public String getFechaComp() {
		// TODO Plantilla de método auto-generado
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#getFechaD()
	 */
	public String getFechaD() {
		// TODO Plantilla de método auto-generado
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#getFechaFinA()
	 */
	public String getFechaFinA() {
		// TODO Plantilla de método auto-generado
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#getFechaFinD()
	 */
	public String getFechaFinD() {
		// TODO Plantilla de método auto-generado
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#getFechaFinFormato()
	 */
	public String getFechaFinFormato() {
		// TODO Plantilla de método auto-generado
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#getFechaFinM()
	 */
	public String getFechaFinM() {
		// TODO Plantilla de método auto-generado
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#getFechaFinS()
	 */
	public String getFechaFinS() {
		// TODO Plantilla de método auto-generado
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#getFechaFormato()
	 */
	public String getFechaFormato() {
		// TODO Plantilla de método auto-generado
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#getFechaIniA()
	 */
	public String getFechaIniA() {
		// TODO Plantilla de método auto-generado
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#getFechaIniD()
	 */
	public String getFechaIniD() {
		// TODO Plantilla de método auto-generado
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#getFechaIniFormato()
	 */
	public String getFechaIniFormato() {
		// TODO Plantilla de método auto-generado
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#getFechaIniM()
	 */
	public String getFechaIniM() {
		// TODO Plantilla de método auto-generado
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#getFechaIniS()
	 */
	public String getFechaIniS() {
		// TODO Plantilla de método auto-generado
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#getFechaM()
	 */
	public String getFechaM() {
		// TODO Plantilla de método auto-generado
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#getFechaS()
	 */
	public String getFechaS() {
		// TODO Plantilla de método auto-generado
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#getFondo()
	 */
	public String getFondo() {
		// TODO Plantilla de método auto-generado
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#getFormatoFecha1()
	 */
	public String[] getFormatoFecha1() {
		// TODO Plantilla de método auto-generado
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#getFormatoFecha2()
	 */
	public String[] getFormatoFecha2() {
		// TODO Plantilla de método auto-generado
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#getFormatoFecha4()
	 */
	public String getFormatoFecha4() {
		// TODO Plantilla de método auto-generado
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#getGenericoCampoNumerico()
	 */
	public String[] getGenericoCampoNumerico() {
		// TODO Plantilla de método auto-generado
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#getGenericoCampoNumericoFin()
	 */
	public String[] getGenericoCampoNumericoFin() {
		// TODO Plantilla de método auto-generado
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#getGenericoEtiquetaCampoNumerico()
	 */
	public String[] getGenericoEtiquetaCampoNumerico() {
		// TODO Plantilla de método auto-generado
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#getGenericoEtiquetaFecha()
	 */
	public String[] getGenericoEtiquetaFecha() {
		// TODO Plantilla de método auto-generado
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#getGenericoEtiquetaTextoCorto()
	 */
	public String[] getGenericoEtiquetaTextoCorto() {
		// TODO Plantilla de método auto-generado
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#getGenericoEtiquetaTextoLargo()
	 */
	public String[] getGenericoEtiquetaTextoLargo() {
		// TODO Plantilla de método auto-generado
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#getGenericoFechaA()
	 */
	public String[] getGenericoFechaA() {
		// TODO Plantilla de método auto-generado
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#getGenericoFechaCalificador()
	 */
	public String[] getGenericoFechaCalificador() {
		// TODO Plantilla de método auto-generado
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#getGenericoFechaComp()
	 */
	public String[] getGenericoFechaComp() {
		// TODO Plantilla de método auto-generado
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#getGenericoFechaD()
	 */
	public String[] getGenericoFechaD() {
		// TODO Plantilla de método auto-generado
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#getGenericoFechaFinA()
	 */
	public String[] getGenericoFechaFinA() {
		// TODO Plantilla de método auto-generado
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#getGenericoFechaFinD()
	 */
	public String[] getGenericoFechaFinD() {
		// TODO Plantilla de método auto-generado
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#getGenericoFechaFinFormato()
	 */
	public String[] getGenericoFechaFinFormato() {
		// TODO Plantilla de método auto-generado
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#getGenericoFechaFinM()
	 */
	public String[] getGenericoFechaFinM() {
		// TODO Plantilla de método auto-generado
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#getGenericoFechaFinS()
	 */
	public String[] getGenericoFechaFinS() {
		// TODO Plantilla de método auto-generado
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#getGenericoFechaFormato()
	 */
	public String[] getGenericoFechaFormato() {
		// TODO Plantilla de método auto-generado
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#getGenericoFechaIniA()
	 */
	public String[] getGenericoFechaIniA() {
		// TODO Plantilla de método auto-generado
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#getGenericoFechaIniD()
	 */
	public String[] getGenericoFechaIniD() {
		// TODO Plantilla de método auto-generado
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#getGenericoFechaIniFormato()
	 */
	public String[] getGenericoFechaIniFormato() {
		// TODO Plantilla de método auto-generado
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#getGenericoFechaIniM()
	 */
	public String[] getGenericoFechaIniM() {
		// TODO Plantilla de método auto-generado
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#getGenericoFechaIniS()
	 */
	public String[] getGenericoFechaIniS() {
		// TODO Plantilla de método auto-generado
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#getGenericoFechaM()
	 */
	public String[] getGenericoFechaM() {
		// TODO Plantilla de método auto-generado
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#getGenericoFechaS()
	 */
	public String[] getGenericoFechaS() {
		// TODO Plantilla de método auto-generado
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#getGenericoIdCampoNumerico()
	 */
	public String[] getGenericoIdCampoNumerico() {
		// TODO Plantilla de método auto-generado
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#getGenericoIdFecha()
	 */
	public String[] getGenericoIdFecha() {
		// TODO Plantilla de método auto-generado
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#getGenericoIdTextoCorto()
	 */
	public String[] getGenericoIdTextoCorto() {
		// TODO Plantilla de método auto-generado
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#getGenericoIdTextoLargo()
	 */
	public String[] getGenericoIdTextoLargo() {
		// TODO Plantilla de método auto-generado
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#getGenericoOperadorCampoNumerico()
	 */
	public String[] getGenericoOperadorCampoNumerico() {
		// TODO Plantilla de método auto-generado
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#getGenericoOperadorTextoCorto()
	 */
	public String[] getGenericoOperadorTextoCorto() {
		// TODO Plantilla de método auto-generado
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#getGenericoTextoCorto()
	 */
	public String[] getGenericoTextoCorto() {
		// TODO Plantilla de método auto-generado
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#getGenericoTextoLargo()
	 */
	public String[] getGenericoTextoLargo() {
		// TODO Plantilla de método auto-generado
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#getIdFicha()
	 */
	public String getIdFicha() {
		// TODO Plantilla de método auto-generado
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#getIdObjetoAmbito()
	 */
	public String[] getIdObjetoAmbito() {
		// TODO Plantilla de método auto-generado
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#getNiveles()
	 */
	public String[] getNiveles() {
		// TODO Plantilla de método auto-generado
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#getNombreObjetoAmbito()
	 */
	public String[] getNombreObjetoAmbito() {
		// TODO Plantilla de método auto-generado
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#getNumero()
	 */
	public String getNumero() {
		// TODO Plantilla de método auto-generado
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#getNumeroComp()
	 */
	public String getNumeroComp() {
		// TODO Plantilla de método auto-generado
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#getOperador()
	 */
	public String[] getOperador() {
		// TODO Plantilla de método auto-generado
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#getPageInfo()
	 */
	public PageInfo getPageInfo() {
		// TODO Plantilla de método auto-generado
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#getPostBack()
	 */
	public String getPostBack() {
		// TODO Plantilla de método auto-generado
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#getProcedimiento()
	 */
	public String getProcedimiento() {
		// TODO Plantilla de método auto-generado
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#getProductores()
	 */
	public String getProductores() {
		// TODO Plantilla de método auto-generado
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#getRango()
	 */
	public String getRango() {
		// TODO Plantilla de método auto-generado
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#getReemplazoSimple()
	 */
	public String getReemplazoSimple() {
		// TODO Plantilla de método auto-generado
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#getSignatura()
	 */
	public String getSignatura() {
		// TODO Plantilla de método auto-generado
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#getSignatura_like()
	 */
	public String getSignatura_like() {
		// TODO Plantilla de método auto-generado
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#getTexto()
	 */
	public String getTexto() {
		// TODO Plantilla de método auto-generado
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#getTipoBusqueda()
	 */
	public String getTipoBusqueda() {
		// TODO Plantilla de método auto-generado
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#getTipoCampo()
	 */
	public Integer[] getTipoCampo() {
		// TODO Plantilla de método auto-generado
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#getTipoElemento()
	 */
	public String getTipoElemento() {
		// TODO Plantilla de método auto-generado
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#getTipoMedida()
	 */
	public String getTipoMedida() {
		// TODO Plantilla de método auto-generado
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#getTipoObjetoAmbito()
	 */
	public String[] getTipoObjetoAmbito() {
		// TODO Plantilla de método auto-generado
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#getTipoReferencia()
	 */
	public String getTipoReferencia() {
		// TODO Plantilla de método auto-generado
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#getTiposReferencia()
	 */
	public String[] getTiposReferencia() {
		// TODO Plantilla de método auto-generado
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#getUnidadMedida()
	 */
	public String getUnidadMedida() {
		// TODO Plantilla de método auto-generado
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#getValor1()
	 */
	public String[] getValor1() {
		// TODO Plantilla de método auto-generado
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#getValor1A()
	 */
	public String[] getValor1A() {
		// TODO Plantilla de método auto-generado
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#getValor1D()
	 */
	public String[] getValor1D() {
		// TODO Plantilla de método auto-generado
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#getValor1M()
	 */
	public String[] getValor1M() {
		// TODO Plantilla de método auto-generado
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#getValor1S()
	 */
	public String[] getValor1S() {
		// TODO Plantilla de método auto-generado
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#getValor2()
	 */
	public String[] getValor2() {
		// TODO Plantilla de método auto-generado
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#getValor2A()
	 */
	public String[] getValor2A() {
		// TODO Plantilla de método auto-generado
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#getValor2D()
	 */
	public String[] getValor2D() {
		// TODO Plantilla de método auto-generado
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#getValor2M()
	 */
	public String[] getValor2M() {
		// TODO Plantilla de método auto-generado
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#getValor2S()
	 */
	public String[] getValor2S() {
		// TODO Plantilla de método auto-generado
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#getValor4()
	 */
	public String getValor4() {
		// TODO Plantilla de método auto-generado
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#getValor4A()
	 */
	public String getValor4A() {
		// TODO Plantilla de método auto-generado
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#getValor4D()
	 */
	public String getValor4D() {
		// TODO Plantilla de método auto-generado
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#getValor4M()
	 */
	public String getValor4M() {
		// TODO Plantilla de método auto-generado
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#getValor4S()
	 */
	public String getValor4S() {
		// TODO Plantilla de método auto-generado
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#isReemplazoSimple()
	 */
	public boolean isReemplazoSimple() {
		// TODO Plantilla de método auto-generado
		return false;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#isReemplazoValoresNulos()
	 */
	public boolean isReemplazoValoresNulos() {
		// TODO Plantilla de método auto-generado
		return false;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#setAbrirpar(java.lang.String[])
	 */
	public void setAbrirpar(String[] abrirpar) {
		// TODO Plantilla de método auto-generado

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#setActivarBloqueos(boolean)
	 */
	public void setActivarBloqueos(boolean activarBloqueos) {
		// TODO Plantilla de método auto-generado

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#setActivarEstados(boolean)
	 */
	public void setActivarEstados(boolean activarEstados) {
		// TODO Plantilla de método auto-generado

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#setActivarNiveles(boolean)
	 */
	public void setActivarNiveles(boolean activarNiveles) {
		// TODO Plantilla de método auto-generado

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#setBloqueos(java.lang.String[])
	 */
	public void setBloqueos(String[] bloqueos) {
		// TODO Plantilla de método auto-generado

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#setBooleano(java.lang.String[])
	 */
	public void setBooleano(String[] booleano) {
		// TODO Plantilla de método auto-generado

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#setCalificador(java.lang.String)
	 */
	public void setCalificador(String calificador) {
		// TODO Plantilla de método auto-generado

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#setCalificadorFinal(java.lang.String)
	 */
	public void setCalificadorFinal(String calificadorFinal) {
		// TODO Plantilla de método auto-generado

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#setCalificadorInicial(java.lang.String)
	 */
	public void setCalificadorInicial(String calificadorInicial) {
		// TODO Plantilla de método auto-generado

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#setCampo(java.lang.String[])
	 */
	public void setCampo(String[] campo) {
		// TODO Plantilla de método auto-generado

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#setCerrarpar(java.lang.String[])
	 */
	public void setCerrarpar(String[] cerrarpar) {
		// TODO Plantilla de método auto-generado

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#setCodigoReferencia(java.lang.String)
	 */
	public void setCodigoReferencia(String codigoReferencia) {
		// TODO Plantilla de método auto-generado

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#setCodigoRelacion(java.lang.String)
	 */
	public void setCodigoRelacion(String codigoRelacion) {
		// TODO Plantilla de método auto-generado

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#setContenidoFichero(java.lang.String)
	 */
	public void setContenidoFichero(String contenidoFichero) {
		// TODO Plantilla de método auto-generado

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#setDescriptores(java.lang.String)
	 */
	public void setDescriptores(String descriptores) {
		// TODO Plantilla de método auto-generado

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#setEstados(java.lang.String[])
	 */
	public void setEstados(String[] estados) {
		// TODO Plantilla de método auto-generado

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#setFechaA(java.lang.String)
	 */
	public void setFechaA(String fechaA) {
		// TODO Plantilla de método auto-generado

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#setFechaComp(java.lang.String)
	 */
	public void setFechaComp(String fechaComp) {
		// TODO Plantilla de método auto-generado

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#setFechaD(java.lang.String)
	 */
	public void setFechaD(String fechaD) {
		// TODO Plantilla de método auto-generado

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#setFechaFinA(java.lang.String)
	 */
	public void setFechaFinA(String fechaFinA) {
		// TODO Plantilla de método auto-generado

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#setFechaFinD(java.lang.String)
	 */
	public void setFechaFinD(String fechaFinD) {
		// TODO Plantilla de método auto-generado

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#setFechaFinFormato(java.lang.String)
	 */
	public void setFechaFinFormato(String fechaFinFormato) {
		// TODO Plantilla de método auto-generado

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#setFechaFinM(java.lang.String)
	 */
	public void setFechaFinM(String fechaFinM) {
		// TODO Plantilla de método auto-generado

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#setFechaFinS(java.lang.String)
	 */
	public void setFechaFinS(String fechaFinS) {
		// TODO Plantilla de método auto-generado

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#setFechaFormato(java.lang.String)
	 */
	public void setFechaFormato(String fechaFormato) {
		// TODO Plantilla de método auto-generado

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#setFechaIniA(java.lang.String)
	 */
	public void setFechaIniA(String fechaIniA) {
		// TODO Plantilla de método auto-generado

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#setFechaIniD(java.lang.String)
	 */
	public void setFechaIniD(String fechaIniD) {
		// TODO Plantilla de método auto-generado

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#setFechaIniFormato(java.lang.String)
	 */
	public void setFechaIniFormato(String fechaIniFormato) {
		// TODO Plantilla de método auto-generado

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#setFechaIniM(java.lang.String)
	 */
	public void setFechaIniM(String fechaIniM) {
		// TODO Plantilla de método auto-generado

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#setFechaIniS(java.lang.String)
	 */
	public void setFechaIniS(String fechaIniS) {
		// TODO Plantilla de método auto-generado

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#setFechaM(java.lang.String)
	 */
	public void setFechaM(String fechaM) {
		// TODO Plantilla de método auto-generado

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#setFechaS(java.lang.String)
	 */
	public void setFechaS(String fechaS) {
		// TODO Plantilla de método auto-generado

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#setFondo(java.lang.String)
	 */
	public void setFondo(String fondo) {
		// TODO Plantilla de método auto-generado

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#setFormatoFecha1(java.lang.String[])
	 */
	public void setFormatoFecha1(String[] formatoFecha1) {
		// TODO Plantilla de método auto-generado

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#setFormatoFecha2(java.lang.String[])
	 */
	public void setFormatoFecha2(String[] formatoFecha2) {
		// TODO Plantilla de método auto-generado

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#setFormatoFecha4(java.lang.String)
	 */
	public void setFormatoFecha4(String formatoFecha4) {
		// TODO Plantilla de método auto-generado

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#setGenericoCampoNumerico(java.lang.String[])
	 */
	public void setGenericoCampoNumerico(String[] genericoCampoNumerico) {
		// TODO Plantilla de método auto-generado

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#setGenericoCampoNumericoFin(java.lang.String[])
	 */
	public void setGenericoCampoNumericoFin(String[] genericoCampoNumericoFin) {
		// TODO Plantilla de método auto-generado

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#setGenericoEtiquetaCampoNumerico(java.lang.String[])
	 */
	public void setGenericoEtiquetaCampoNumerico(
			String[] genericoEtiquetaCampoNumerico) {
		// TODO Plantilla de método auto-generado

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#setGenericoEtiquetaFecha(java.lang.String[])
	 */
	public void setGenericoEtiquetaFecha(String[] genericoEtiquetaFecha) {
		// TODO Plantilla de método auto-generado

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#setGenericoEtiquetaTextoCorto(java.lang.String[])
	 */
	public void setGenericoEtiquetaTextoCorto(
			String[] genericoEtiquetaTextoCorto) {
		// TODO Plantilla de método auto-generado

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#setGenericoEtiquetaTextoLargo(java.lang.String[])
	 */
	public void setGenericoEtiquetaTextoLargo(
			String[] genericoEtiquetaTextoLargo) {
		// TODO Plantilla de método auto-generado

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#setGenericoFechaA(java.lang.String[])
	 */
	public void setGenericoFechaA(String[] genericoFechaA) {
		// TODO Plantilla de método auto-generado

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#setGenericoFechaCalificador(java.lang.String[])
	 */
	public void setGenericoFechaCalificador(String[] genericoFechaCalificador) {
		// TODO Plantilla de método auto-generado

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#setGenericoFechaComp(java.lang.String[])
	 */
	public void setGenericoFechaComp(String[] genericoFechaComp) {
		// TODO Plantilla de método auto-generado

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#setGenericoFechaD(java.lang.String[])
	 */
	public void setGenericoFechaD(String[] genericoFechaD) {
		// TODO Plantilla de método auto-generado

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#setGenericoFechaFinA(java.lang.String[])
	 */
	public void setGenericoFechaFinA(String[] genericoFechaFinA) {
		// TODO Plantilla de método auto-generado

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#setGenericoFechaFinD(java.lang.String[])
	 */
	public void setGenericoFechaFinD(String[] genericoFechaFinD) {
		// TODO Plantilla de método auto-generado

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#setGenericoFechaFinFormato(java.lang.String[])
	 */
	public void setGenericoFechaFinFormato(String[] genericoFechaFinFormato) {
		// TODO Plantilla de método auto-generado

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#setGenericoFechaFinM(java.lang.String[])
	 */
	public void setGenericoFechaFinM(String[] genericoFechaFinM) {
		// TODO Plantilla de método auto-generado

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#setGenericoFechaFinS(java.lang.String[])
	 */
	public void setGenericoFechaFinS(String[] genericoFechaFinS) {
		// TODO Plantilla de método auto-generado

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#setGenericoFechaFormato(java.lang.String[])
	 */
	public void setGenericoFechaFormato(String[] genericoFechaFormato) {
		// TODO Plantilla de método auto-generado

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#setGenericoFechaIniA(java.lang.String[])
	 */
	public void setGenericoFechaIniA(String[] genericoFechaIniA) {
		// TODO Plantilla de método auto-generado

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#setGenericoFechaIniD(java.lang.String[])
	 */
	public void setGenericoFechaIniD(String[] genericoFechaIniD) {
		// TODO Plantilla de método auto-generado

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#setGenericoFechaIniFormato(java.lang.String[])
	 */
	public void setGenericoFechaIniFormato(String[] genericoFechaIniFormato) {
		// TODO Plantilla de método auto-generado

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#setGenericoFechaIniM(java.lang.String[])
	 */
	public void setGenericoFechaIniM(String[] genericoFechaIniM) {
		// TODO Plantilla de método auto-generado

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#setGenericoFechaIniS(java.lang.String[])
	 */
	public void setGenericoFechaIniS(String[] genericoFechaIniS) {
		// TODO Plantilla de método auto-generado

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#setGenericoFechaM(java.lang.String[])
	 */
	public void setGenericoFechaM(String[] genericoFechaM) {
		// TODO Plantilla de método auto-generado

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#setGenericoFechaS(java.lang.String[])
	 */
	public void setGenericoFechaS(String[] genericoFechaS) {
		// TODO Plantilla de método auto-generado

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#setGenericoIdCampoNumerico(java.lang.String[])
	 */
	public void setGenericoIdCampoNumerico(String[] genericoIdCampoNumerico) {
		// TODO Plantilla de método auto-generado

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#setGenericoIdFecha(java.lang.String[])
	 */
	public void setGenericoIdFecha(String[] genericoIdFecha) {
		// TODO Plantilla de método auto-generado

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#setGenericoIdTextoCorto(java.lang.String[])
	 */
	public void setGenericoIdTextoCorto(String[] genericoIdTextoCorto) {
		// TODO Plantilla de método auto-generado

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#setGenericoIdTextoLargo(java.lang.String[])
	 */
	public void setGenericoIdTextoLargo(String[] genericoIdTextoLargo) {
		// TODO Plantilla de método auto-generado

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#setGenericoOperadorCampoNumerico(java.lang.String[])
	 */
	public void setGenericoOperadorCampoNumerico(
			String[] genericoOperadorCampoNumerico) {
		// TODO Plantilla de método auto-generado

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#setGenericoOperadorTextoCorto(java.lang.String[])
	 */
	public void setGenericoOperadorTextoCorto(
			String[] genericoOperadorTextoCorto) {
		// TODO Plantilla de método auto-generado

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#setGenericoTextoCorto(java.lang.String[])
	 */
	public void setGenericoTextoCorto(String[] genericoTextoCorto) {
		// TODO Plantilla de método auto-generado

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#setGenericoTextoLargo(java.lang.String[])
	 */
	public void setGenericoTextoLargo(String[] genericoTextoLargo) {
		// TODO Plantilla de método auto-generado

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#setIdFicha(java.lang.String)
	 */
	public void setIdFicha(String idFicha) {
		// TODO Plantilla de método auto-generado

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#setIdObjetoAmbito(java.lang.String[])
	 */
	public void setIdObjetoAmbito(String[] idObjetoAmbito) {
		// TODO Plantilla de método auto-generado

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#setNiveles(java.lang.String[])
	 */
	public void setNiveles(String[] niveles) {
		// TODO Plantilla de método auto-generado

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#setNombreObjetoAmbito(java.lang.String[])
	 */
	public void setNombreObjetoAmbito(String[] nombreObjetoAmbito) {
		// TODO Plantilla de método auto-generado

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#setNumero(java.lang.String)
	 */
	public void setNumero(String numero) {
		// TODO Plantilla de método auto-generado

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#setNumeroComp(java.lang.String)
	 */
	public void setNumeroComp(String numeroComp) {
		// TODO Plantilla de método auto-generado

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#setOperador(java.lang.String[])
	 */
	public void setOperador(String[] operador) {
		// TODO Plantilla de método auto-generado

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#setPageInfo(common.pagination.PageInfo)
	 */
	public void setPageInfo(PageInfo pageInfo) {
		// TODO Plantilla de método auto-generado

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#setPostBack(java.lang.String)
	 */
	public void setPostBack(String postBack) {
		// TODO Plantilla de método auto-generado

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#setProcedimiento(java.lang.String)
	 */
	public void setProcedimiento(String procedimiento) {
		// TODO Plantilla de método auto-generado

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#setProductores(java.lang.String)
	 */
	public void setProductores(String productores) {
		// TODO Plantilla de método auto-generado

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#setRango(java.lang.String)
	 */
	public void setRango(String rango) {
		// TODO Plantilla de método auto-generado

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#setReemplazoSimple(boolean)
	 */
	public void setReemplazoSimple(boolean reemplazoSimple) {
		// TODO Plantilla de método auto-generado

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#setSignatura(java.lang.String)
	 */
	public void setSignatura(String signatura) {
		// TODO Plantilla de método auto-generado

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#setSignatura_like(java.lang.String)
	 */
	public void setSignatura_like(String signaturaLike) {
		// TODO Plantilla de método auto-generado

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#setTexto(java.lang.String)
	 */
	public void setTexto(String texto) {
		// TODO Plantilla de método auto-generado

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#setTipoBusqueda(java.lang.String)
	 */
	public void setTipoBusqueda(String tipoBusqueda) {
		// TODO Plantilla de método auto-generado

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#setTipoCampo(java.lang.Integer[])
	 */
	public void setTipoCampo(Integer[] tipoCampo) {
		// TODO Plantilla de método auto-generado

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#setTipoElemento(java.lang.String)
	 */
	public void setTipoElemento(String tipoElemento) {
		// TODO Plantilla de método auto-generado

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#setTipoMedida(java.lang.String)
	 */
	public void setTipoMedida(String tipoMedida) {
		// TODO Plantilla de método auto-generado

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#setTipoObjetoAmbito(java.lang.String[])
	 */
	public void setTipoObjetoAmbito(String[] tipoObjetoAmbito) {
		// TODO Plantilla de método auto-generado

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#setTipoReferencia(java.lang.String)
	 */
	public void setTipoReferencia(String tipoReferencia) {
		// TODO Plantilla de método auto-generado

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#setTiposReferencia(java.lang.String[])
	 */
	public void setTiposReferencia(String[] tiposReferencia) {
		// TODO Plantilla de método auto-generado

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#setUnidadMedida(java.lang.String)
	 */
	public void setUnidadMedida(String unidadMedida) {
		// TODO Plantilla de método auto-generado

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#setValor1(java.lang.String[])
	 */
	public void setValor1(String[] valor1) {
		// TODO Plantilla de método auto-generado

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#setValor1A(java.lang.String[])
	 */
	public void setValor1A(String[] valor1a) {
		// TODO Plantilla de método auto-generado

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#setValor1D(java.lang.String[])
	 */
	public void setValor1D(String[] valor1d) {
		// TODO Plantilla de método auto-generado

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#setValor1M(java.lang.String[])
	 */
	public void setValor1M(String[] valor1m) {
		// TODO Plantilla de método auto-generado

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#setValor1S(java.lang.String[])
	 */
	public void setValor1S(String[] valor1s) {
		// TODO Plantilla de método auto-generado

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#setValor2(java.lang.String[])
	 */
	public void setValor2(String[] valor2) {
		// TODO Plantilla de método auto-generado

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#setValor2A(java.lang.String[])
	 */
	public void setValor2A(String[] valor2a) {
		// TODO Plantilla de método auto-generado

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#setValor2D(java.lang.String[])
	 */
	public void setValor2D(String[] valor2d) {
		// TODO Plantilla de método auto-generado

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#setValor2M(java.lang.String[])
	 */
	public void setValor2M(String[] valor2m) {
		// TODO Plantilla de método auto-generado

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#setValor2S(java.lang.String[])
	 */
	public void setValor2S(String[] valor2s) {
		// TODO Plantilla de método auto-generado

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#setValor4(java.lang.String)
	 */
	public void setValor4(String valor4) {
		// TODO Plantilla de método auto-generado

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#setValor4A(java.lang.String)
	 */
	public void setValor4A(String valor4a) {
		// TODO Plantilla de método auto-generado

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#setValor4D(java.lang.String)
	 */
	public void setValor4D(String valor4d) {
		// TODO Plantilla de método auto-generado

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#setValor4M(java.lang.String)
	 */
	public void setValor4M(String valor4m) {
		// TODO Plantilla de método auto-generado

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#setValor4S(java.lang.String)
	 */
	public void setValor4S(String valor4s) {
		// TODO Plantilla de método auto-generado

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#getConservada()
	 */
	public String getConservada() {
		// TODO Plantilla de método auto-generado
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#setConservada(java.lang.String)
	 */
	public void setConservada(String conservada) {
		// TODO Plantilla de método auto-generado

	}

	public void setIds(String[] ids) {
		this.ids = ids;
	}

	public String[] getIds() {
		return ids;
	}

}
