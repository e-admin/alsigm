package descripcion.forms;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import util.StringOwnTokenizer;
import auditoria.ArchivoDetails;

import common.Constants;
import common.Messages;
import common.forms.CustomForm;
import common.util.ArrayUtils;
import common.util.CustomDate;
import common.util.CustomDateFormat;
import common.util.CustomDateRange;
import common.util.StringUtils;

import fondos.vos.BusquedaElementosVO;

/**
 * Formulario para la búsqueda general de fondos.
 */
public class BusquedaGeneralForm extends CustomForm {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String codigoReferencia = null;
	private String titulo = null;
	private String texto = null;
	private String numeroComp = null;
	private String numero = null;
	private String tipoMedida = null;
	private String unidadMedida = null;
	private String fechaComp = null;
	private String fechaFormato = null;
	private String fechaA = null;
	private String fechaM = null;
	private String fechaD = null;
	private String fechaS = null;
	private String fechaIniFormato = null;
	private String fechaIniA = null;
	private String fechaIniM = null;
	private String fechaIniD = null;
	private String fechaIniS = null;
	private String fechaFinFormato = null;
	private String fechaFinA = null;
	private String fechaFinM = null;
	private String fechaFinD = null;
	private String fechaFinS = null;
	private String calificador = null;
	private String fondo = null;
	private String[] niveles = new String[0];
	private String descriptores = null;
	// private int tipoBusqueda = 0;//TIPO_BUSQUEDA_NORMAL;
	private boolean usaCache = false;

	/*
	 * public static final int TIPO_BUSQUEDA_NORMAL = 1; public static final int
	 * TIPO_BUSQUEDA_REDUCIDA = 2;
	 */

	/**
	 * Constructor.
	 */
	public BusquedaGeneralForm() {
		super();
	}

	/**
	 * @return Returns the codigoReferencia.
	 */
	public String getCodigoReferencia() {
		return codigoReferencia;
	}

	/**
	 * @param codigoReferencia
	 *            The codigoReferencia to set.
	 */
	public void setCodigoReferencia(String codigoReferencia) {
		this.codigoReferencia = codigoReferencia;
	}

	/**
	 * @return Returns the descriptores.
	 */
	public String getDescriptores() {
		return descriptores;
	}

	/**
	 * @param descriptores
	 *            The descriptores to set.
	 */
	public void setDescriptores(String descriptores) {
		this.descriptores = descriptores;
	}

	/**
	 * @return Returns the fechaA.
	 */
	public String getFechaA() {
		return fechaA;
	}

	/**
	 * @param fechaA
	 *            The fechaA to set.
	 */
	public void setFechaA(String fechaA) {
		this.fechaA = fechaA;
	}

	/**
	 * @return Returns the fechaComp.
	 */
	public String getFechaComp() {
		return fechaComp;
	}

	/**
	 * @param fechaComp
	 *            The fechaComp to set.
	 */
	public void setFechaComp(String fechaComp) {
		this.fechaComp = fechaComp;
	}

	/**
	 * @return Returns the fechaD.
	 */
	public String getFechaD() {
		return fechaD;
	}

	/**
	 * @param fechaD
	 *            The fechaD to set.
	 */
	public void setFechaD(String fechaD) {
		this.fechaD = fechaD;
	}

	/**
	 * @return Returns the fechaFinA.
	 */
	public String getFechaFinA() {
		return fechaFinA;
	}

	/**
	 * @param fechaFinA
	 *            The fechaFinA to set.
	 */
	public void setFechaFinA(String fechaFinA) {
		this.fechaFinA = fechaFinA;
	}

	/**
	 * @return Returns the fechaFinD.
	 */
	public String getFechaFinD() {
		return fechaFinD;
	}

	/**
	 * @param fechaFinD
	 *            The fechaFinD to set.
	 */
	public void setFechaFinD(String fechaFinD) {
		this.fechaFinD = fechaFinD;
	}

	/**
	 * @return Returns the fechaFinFormato.
	 */
	public String getFechaFinFormato() {
		return fechaFinFormato;
	}

	/**
	 * @param fechaFinFormato
	 *            The fechaFinFormato to set.
	 */
	public void setFechaFinFormato(String fechaFinFormato) {
		this.fechaFinFormato = fechaFinFormato;
	}

	/**
	 * @return Returns the fechaFinM.
	 */
	public String getFechaFinM() {
		return fechaFinM;
	}

	/**
	 * @param fechaFinM
	 *            The fechaFinM to set.
	 */
	public void setFechaFinM(String fechaFinM) {
		this.fechaFinM = fechaFinM;
	}

	/**
	 * @return Returns the fechaFinS.
	 */
	public String getFechaFinS() {
		return fechaFinS;
	}

	/**
	 * @param fechaFinS
	 *            The fechaFinS to set.
	 */
	public void setFechaFinS(String fechaFinS) {
		this.fechaFinS = fechaFinS;
	}

	/**
	 * @return Returns the fechaFormato.
	 */
	public String getFechaFormato() {
		return fechaFormato;
	}

	/**
	 * @param fechaFormato
	 *            The fechaFormato to set.
	 */
	public void setFechaFormato(String fechaFormato) {
		this.fechaFormato = fechaFormato;
	}

	/**
	 * @return Returns the fechaIniA.
	 */
	public String getFechaIniA() {
		return fechaIniA;
	}

	/**
	 * @param fechaIniA
	 *            The fechaIniA to set.
	 */
	public void setFechaIniA(String fechaIniA) {
		this.fechaIniA = fechaIniA;
	}

	/**
	 * @return Returns the fechaIniD.
	 */
	public String getFechaIniD() {
		return fechaIniD;
	}

	/**
	 * @param fechaIniD
	 *            The fechaIniD to set.
	 */
	public void setFechaIniD(String fechaIniD) {
		this.fechaIniD = fechaIniD;
	}

	/**
	 * @return Returns the fechaIniFormato.
	 */
	public String getFechaIniFormato() {
		return fechaIniFormato;
	}

	/**
	 * @param fechaIniFormato
	 *            The fechaIniFormato to set.
	 */
	public void setFechaIniFormato(String fechaIniFormato) {
		this.fechaIniFormato = fechaIniFormato;
	}

	/**
	 * @return Returns the fechaIniM.
	 */
	public String getFechaIniM() {
		return fechaIniM;
	}

	/**
	 * @param fechaIniM
	 *            The fechaIniM to set.
	 */
	public void setFechaIniM(String fechaIniM) {
		this.fechaIniM = fechaIniM;
	}

	/**
	 * @return Returns the fechaIniS.
	 */
	public String getFechaIniS() {
		return fechaIniS;
	}

	/**
	 * @param fechaIniS
	 *            The fechaIniS to set.
	 */
	public void setFechaIniS(String fechaIniS) {
		this.fechaIniS = fechaIniS;
	}

	/**
	 * @return Returns the fechaM.
	 */
	public String getFechaM() {
		return fechaM;
	}

	/**
	 * @param fechaM
	 *            The fechaM to set.
	 */
	public void setFechaM(String fechaM) {
		this.fechaM = fechaM;
	}

	/**
	 * @return Returns the fechaS.
	 */
	public String getFechaS() {
		return fechaS;
	}

	/**
	 * @param fechaS
	 *            The fechaS to set.
	 */
	public void setFechaS(String fechaS) {
		this.fechaS = fechaS;
	}

	/**
	 * @return Returns the niveles.
	 */
	public String[] getNiveles() {
		return niveles;
	}

	/**
	 * @param niveles
	 *            The niveles to set.
	 */
	public void setNiveles(String[] niveles) {
		this.niveles = niveles;
	}

	/**
	 * @return Returns the texto.
	 */
	public String getTexto() {
		return texto;
	}

	/**
	 * @param texto
	 *            The texto to set.
	 */
	public void setTexto(String texto) {
		this.texto = texto;
	}

	/**
	 * @return Returns the titulo.
	 */
	public String getTitulo() {
		return titulo;
	}

	/**
	 * @param titulo
	 *            The titulo to set.
	 */
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	/**
	 * @return Returns the fondo.
	 */
	public String getFondo() {
		return fondo;
	}

	/**
	 * @param fondo
	 *            The fondo to set.
	 */
	public void setFondo(String fondo) {
		this.fondo = fondo;
	}

	/**
	 * @return Returns the calificador.
	 */
	public String getCalificador() {
		return calificador;
	}

	/**
	 * @param calificador
	 *            The calificador to set.
	 */
	public void setCalificador(String calificador) {
		this.calificador = calificador;
	}

	/**
	 * @return Returns the numero.
	 */
	public String getNumero() {
		return numero;
	}

	/**
	 * @param numero
	 *            The numero to set.
	 */
	public void setNumero(String numero) {
		this.numero = numero;
	}

	/**
	 * @return Returns the numeroComp.
	 */
	public String getNumeroComp() {
		return numeroComp;
	}

	/**
	 * @param numeroComp
	 *            The numeroComp to set.
	 */
	public void setNumeroComp(String numeroComp) {
		this.numeroComp = numeroComp;
	}

	/**
	 * @return Returns the tipoMedida.
	 */
	public String getTipoMedida() {
		return tipoMedida;
	}

	/**
	 * @param tipoMedida
	 *            The tipoMedida to set.
	 */
	public void setTipoMedida(String tipoMedida) {
		this.tipoMedida = tipoMedida;
	}

	/**
	 * @return Returns the unidadesMedida.
	 */
	public String getUnidadMedida() {
		return unidadMedida;
	}

	/**
	 * @param unidadesMedida
	 *            The unidadesMedida to set.
	 */
	public void setUnidadMedida(String unidadesMedida) {
		this.unidadMedida = unidadesMedida;
	}

	/**
	 * @return Returns the usaCache.
	 */
	public boolean isUsaCache() {
		return usaCache;
	}

	/**
	 * @param usaCache
	 *            The usaCache to set.
	 */
	public void setUsaCache(boolean usaCache) {
		this.usaCache = usaCache;
	}

	/**
	 * Construye un VO con la información del formulario.
	 * 
	 * @param vo
	 *            Value Object.
	 * @throws Exception
	 *             si ocurre algún error.
	 */
	// public void populate(BusquedaGeneralVO vo)
	public void populate(BusquedaElementosVO vo) {
		vo.setCodigoReferencia(this.codigoReferencia);
		vo.setTitulo(this.titulo);
		vo.setNiveles(this.niveles);

		/*
		 * if (tipoBusqueda == TIPO_BUSQUEDA_NORMAL) {
		 */
		vo.setTexto(this.texto);
		vo.setNumeroComp(this.numeroComp);
		vo.setNumero(this.numero);
		vo.setTipoMedida(this.tipoMedida);
		vo.setUnidadMedida(this.unidadMedida);
		vo.setFondo(this.fondo);

		// Obtener el rango de fechas para la búsqueda
		CustomDateRange range = CustomDateFormat.getDateRange(this.fechaComp,
				new CustomDate(this.fechaFormato, this.fechaA, this.fechaM,
						this.fechaD, this.fechaS), new CustomDate(
						this.fechaIniFormato, this.fechaIniA, this.fechaIniM,
						this.fechaIniD, this.fechaIniS), new CustomDate(
						this.fechaFinFormato, this.fechaFinA, this.fechaFinM,
						this.fechaFinD, this.fechaFinS));

		if (range != null) {
			vo.setFechaIni(range.getInitialDate());
			vo.setFechaFin(range.getFinalDate());
		}

		vo.setCalificador(this.getCalificador());

		// Descriptores
		if (StringUtils.isNotBlank(descriptores)) {
			StringOwnTokenizer tok = new StringOwnTokenizer(this.descriptores,
					"#");
			String[] listaDescriptores = new String[tok.countTokens()];
			String info;
			for (int i = 0; tok.hasMoreTokens(); i++) {
				info = tok.nextToken();
				String[] partes = info.split(":");
				if (partes.length > 0)
					listaDescriptores[i] = partes[0];
			}
			vo.setDescriptores(listaDescriptores);
		}
		// }
	}

	/***
	 * 
	 * Quitar de aquí cuando se vea si es neceseria o no en ElementosAction o si
	 * basta con el vo BusquedaElementosVO !!!!!!!
	 * 
	 */
	/**
	 * Construye un VO con la información del formulario.
	 * 
	 * @param vo
	 *            Value Object.
	 * @throws Exception
	 *             si ocurre algún error.
	 */
	/*
	 * public void populate(BusquedaGeneralVO vo) {
	 * vo.setCodigoReferencia(this.codigoReferencia); vo.setTitulo(this.titulo);
	 * vo.setNiveles(this.niveles);
	 * 
	 * if (tipoBusqueda == TIPO_BUSQUEDA_NORMAL) { vo.setTexto(this.texto);
	 * vo.setNumeroComp(this.numeroComp); vo.setNumero(this.numero);
	 * vo.setTipoMedida(this.tipoMedida); vo.setUnidadMedida(this.unidadMedida);
	 * vo.setFondo(this.fondo);
	 * 
	 * // Obtener el rango de fechas para la búsqueda CustomDateRange range =
	 * CustomDateFormat.getDateRange( this.fechaComp, new
	 * CustomDate(this.fechaFormato, this.fechaA, this.fechaM, this.fechaD,
	 * this.fechaS), new CustomDate(this.fechaIniFormato, this.fechaIniA,
	 * this.fechaIniM, this.fechaIniD, this.fechaIniS), new
	 * CustomDate(this.fechaFinFormato, this.fechaFinA, this.fechaFinM,
	 * this.fechaFinD, this.fechaFinS));
	 * 
	 * vo.setFechaIni(range.getInitialDate());
	 * vo.setFechaFin(range.getFinalDate());
	 * vo.setCalificador(this.getCalificador());
	 * 
	 * // Descriptores if (StringUtils.isNotBlank(descriptores)) {
	 * StringOwnTokenizer tok = new StringOwnTokenizer(this.descriptores, "#");
	 * String [] listaDescriptores = new String [tok.countTokens()]; String
	 * info; for (int i = 0; tok.hasMoreTokens(); i++) { info = tok.nextToken();
	 * String [] partes = info.split(":"); if (partes.length > 0)
	 * listaDescriptores[i] = partes[0]; }
	 * vo.setDescriptores(listaDescriptores); } } }
	 */
	/**
	 * Inicia el formulario.
	 * 
	 * @param mapping
	 *            {@link ActionMapping} con los mapeos asociado.
	 * @param request
	 *            {@link HttpServletRequest}
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		niveles = new String[0];
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

		/*
		 * if (tipoBusqueda == TIPO_BUSQUEDA_NORMAL) {
		 */// Fechas

		if (this.fechaFormato != null && this.fechaA != null
				&& this.fechaM != null && this.fechaD != null
				&& this.fechaS != null && this.fechaIniFormato != null
				&& this.fechaIniA != null && this.fechaIniM != null
				&& this.fechaIniD != null && this.fechaIniS != null
				&& this.fechaFinFormato != null && this.fechaFinA != null
				&& this.fechaFinM != null && this.fechaFinD != null
				&& this.fechaFinS != null) {
			if (!new CustomDate(this.fechaFormato, this.fechaA, this.fechaM,
					this.fechaD, this.fechaS).validate()
					|| !new CustomDate(this.fechaIniFormato, this.fechaIniA,
							this.fechaIniM, this.fechaIniD, this.fechaIniS)
							.validate()
					|| !new CustomDate(this.fechaFinFormato, this.fechaFinA,
							this.fechaFinM, this.fechaFinD, this.fechaFinS)
							.validate()) {
				errors.add(
						ActionErrors.GLOBAL_MESSAGE,
						new ActionError(Constants.ERROR_DATE, Messages
								.getString(Constants.ETIQUETA_FECHA2,
										request.getLocale())));
			}
		}

		// Número
		if (StringUtils.isNotBlank(numero) && !NumberUtils.isNumber(numero)) {
			errors.add(
					ActionErrors.GLOBAL_MESSAGE,
					new ActionError(Constants.ERROR_INVALID, Messages
							.getString(Constants.ETIQUETA_BUSQUEDA_FORM_NUMERO,
									request.getLocale())));
		}

		// Niveles
		if (!ArrayUtils.isEmpty(niveles)) {
			if (niveles.length == 0) {
				errors.add(
						ActionErrors.GLOBAL_MESSAGE,
						new ActionError(
								Constants.ERROR_REQUIRED,
								Messages.getString(
										ArchivoDetails.DESCRIPCION_BUSQUEDA_NIVELES,
										request.getLocale())));
			}
		}
		/*
		 * } else if (tipoBusqueda == TIPO_BUSQUEDA_REDUCIDA) {
		 */
		// Tiene que haber un criterio como mínimo
		if (StringUtils.isBlank(codigoReferencia)
				&& StringUtils.isBlank(titulo))
			errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError(
					Constants.ERROR_NO_SEARCH_TOKEN));
		// }

		return errors;
	}

}
