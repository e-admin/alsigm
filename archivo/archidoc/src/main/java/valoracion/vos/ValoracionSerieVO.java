package valoracion.vos;

import java.util.Date;
import java.util.List;

import common.vos.BaseVO;

/**
 * Clase que encapusla los datos de una valoracion de serie
 */
public class ValoracionSerieVO extends BaseVO {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	/** Estados Valoración */
	public final static int ESTADO_ABIERTA = 1;
	public final static int ESTADO_PENDIENTE_DE_VALIDAR = 2;
	public final static int ESTADO_VALIDADA = 3;
	public final static int ESTADO_VALIDACION_RECHAZADA = 4;
	public final static int ESTADO_EVALUADA = 5;
	public final static int ESTADO_EVALUACION_RECHAZADA = 6;
	public final static int ESTADO_DICTAMINADA = 7;
	public final static int ESTADO_DICTAMEN_RECHAZADO = 8;
	public final static int ESTADO_DICTAMINADA_HISTORICA = 9;

	/** Ordenacion udocs */
	public final static int ORDENACION_CRONOLOGICA = 1;
	public final static int ORDENACION_ALFABETICA_ONOMASTICA = 2;
	public final static int ORDENACION_ALFABETICA_GEOGRAFICA = 3;
	public final static int ORDENACION_NUMERICA = 4;
	public final static int ORDENACION_ALEATORIA = 5;
	public final static int ORDENACION_OTRA = 6;

	/** Valores administrativos */
	public final static int VALOR_ADMINISTRATIVO_TEMPORAL = 1;
	public final static int VALOR_ADMINISTRATIVO_PERMANENTE = 2;

	/** Valores legal */
	public final static int VALOR_LEGAL_TEMPORAL = 1;
	public final static int VALOR_LEGAL_PERMANENTE = 2;

	/** Valores informativo */
	public final static int VALOR_INFORMATIVO_EXISTE = 1;
	public final static int VALOR_INFORMATIVO_NO_EXISTE = 2;

	/** Valores informativo */
	public final static int VALOR_CIENTIFICO_EXISTE = 1;
	public final static int VALOR_CIENTIFICO_NO_EXISTE = 2;

	/** Valores informativo */
	public final static int VALOR_TECNOLOGICO_EXISTE = 1;
	public final static int VALOR_TECNOLOGICO_NO_EXISTE = 2;

	/** Valores informativo */
	public final static int VALOR_CULTURAL_EXISTE = 1;
	public final static int VALOR_CULTURAL_NO_EXISTE = 2;

	/** Valores histórico */
	public final static int VALOR_HISTORICO_EXISTE = 1;
	public final static int VALOR_HISTORICO_NO_EXISTE = 2;

	/** Técnicas de muestreo */
	public final static int TECNICA_MUESTREO_CRONOLOGICA = 1;
	public final static int TECNICA_MUESTREO_ALFABETICA = 2;
	public final static int TECNICA_MUESTREO_CUALITATIVA = 3;

	/** Valores del dictamen */
	public final static int VALOR_DICTAMEN_CONSERVACION_TOTAL = 1;
	public final static int VALOR_DICTAMEN_CONSERVACION_PARCIAL = 2;
	public final static int VALOR_DICTAMEN_ELIMINACION_TOTAL = 3;

	/** Tipos de regímenes de acceso. */

	public final static int REGIMEN_ACCESO_LIBRE = 3;
	public final static int REGIMEN_ACCESO_RESTRINGIDO_PERMANENTE = 1;
	public final static int REGIMEN_ACCESO_RESTRINGIDO_TEMPORAL = 2;
	public final static int SUBTIPO_REGIMEN_ACCESO_RESTRINGIDO_TEMPORAL_PARCIAL = 21;
	public final static int SUBTIPO_REGIMEN_ACCESO_RESTRINGIDO_TEMPORAL_TOTAL = 22;

	private String id = null;
	private String idSerie = null;
	private String tituloSerie = null;
	// private String codigoSerie = null;
	private String titulo = null;
	private int estado = 0;
	private Date fechaEstado = null;
	private String motivoRechazo = null;
	private int ordenacionSerie1 = 0;
	private int ordenacionSerie2 = 0;
	private String seriesPrecedentes = null;
	private String seriesDescendientes = null;
	private String seriesRelacionadas = null;
	private String documentosRecopilatorios = null;
	private int tipoValorAdministrativo = 0;
	private int anosVigenciaAdministrativa = 0;
	private String valorAdministrativo = null;
	private int tipoValorLegal = 0;
	private int anosVigenciaLegal = 0;
	private String valorLegal = null;
	private int tipoValorInformativo = VALOR_INFORMATIVO_NO_EXISTE;
	private String valorInformativo = null;
	private int tipoValorCientifico = VALOR_CIENTIFICO_NO_EXISTE;
	private String valorCientifico = null;
	private int tipoValorTecnologico = VALOR_TECNOLOGICO_NO_EXISTE;
	private String valorTecnologico = null;
	private int tipoValorCultural = VALOR_CULTURAL_NO_EXISTE;
	private String valorCultural = null;
	// private int tipoValorHistorico = 0;
	// private String valorHistorico = null;
	private int tecnicaMuestreo = 0;
	private int numRegistro = 0;
	private Date fechaValidacion = null;
	private Date fechaEvaluacion = null;
	private Date fechaDictamen = null;
	private int valorDictamen = 0;
	private String valorDictamenValue = null;
	private Date fechaResolucionDictamen = null;
	private String boletinDictamen = null;
	private Date fechaBoletinDictamen = null;
	private String idUsuarioGestor = null;
	private int tipoRegimenAcceso = 0;
	private String regimenAcceso = null;
	private int anosRegimenAcceso = 0;
	private int tipoRegimenAccesoTemporal = 0;

	/* Operaciones que se pueden llevar a cabo sobre la valoración */
	boolean puedeSerEliminada = false;
	boolean puedeSerEditada = false;
	boolean permitidaSolicitudValidacion = false;
	boolean puedeSerValidada = false;
	boolean puedeSerEvaluada = false;
	boolean puedeSerDictaminada = false;
	boolean dictamenPuedeSerCerrado = false;

	private List listaPlazos = null;

	/* Información completa de la serie */
	private String infoSerie = null;

	public int getAnosVigenciaAdministrativa() {
		return anosVigenciaAdministrativa;
	}

	public void setAnosVigenciaAdministrativa(int anosVigenciaAdministrativa) {
		this.anosVigenciaAdministrativa = anosVigenciaAdministrativa;
	}

	public int getAnosVigenciaLegal() {
		return anosVigenciaLegal;
	}

	public void setAnosVigenciaLegal(int anosVigenciaLegal) {
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

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}

	public Date getFechaBoletinDictamen() {
		return fechaBoletinDictamen;
	}

	public void setFechaBoletinDictamen(Date fechaBoletinDictamen) {
		this.fechaBoletinDictamen = fechaBoletinDictamen;
	}

	public Date getFechaDictamen() {
		return fechaDictamen;
	}

	public void setFechaDictamen(Date fechaDictamen) {
		this.fechaDictamen = fechaDictamen;
	}

	public Date getFechaEstado() {
		return fechaEstado;
	}

	public void setFechaEstado(Date fechaEstado) {
		this.fechaEstado = fechaEstado;
	}

	public Date getFechaEvaluacion() {
		return fechaEvaluacion;
	}

	public void setFechaEvaluacion(Date fechaEvaluacion) {
		this.fechaEvaluacion = fechaEvaluacion;
	}

	public Date getFechaResolucionDictamen() {
		return fechaResolucionDictamen;
	}

	public void setFechaResolucionDictamen(Date fechaResolucionDictamen) {
		this.fechaResolucionDictamen = fechaResolucionDictamen;
	}

	public Date getFechaValidacion() {
		return fechaValidacion;
	}

	public void setFechaValidacion(Date fechaValidacion) {
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

	public int getNumRegistro() {
		return numRegistro;
	}

	public void setNumRegistro(int numRegistro) {
		this.numRegistro = numRegistro;
	}

	public int getOrdenacionSerie1() {
		return ordenacionSerie1;
	}

	public void setOrdenacionSerie1(int ordenacionSerie1) {
		this.ordenacionSerie1 = ordenacionSerie1;
	}

	public int getOrdenacionSerie2() {
		return ordenacionSerie2;
	}

	public void setOrdenacionSerie2(int ordenacionSerie2) {
		this.ordenacionSerie2 = ordenacionSerie2;
	}

	public String getSeriesDescendientes() {
		return seriesDescendientes;
	}

	public void setSeriesDescendientes(String seriesDescendientes) {
		this.seriesDescendientes = seriesDescendientes;
	}

	public String getSeriesPrecedentes() {
		return seriesPrecedentes;
	}

	public void setSeriesPrecedentes(String seriesPrecedentes) {
		this.seriesPrecedentes = seriesPrecedentes;
	}

	public String getSeriesRelacionadas() {
		return seriesRelacionadas;
	}

	public void setSeriesRelacionadas(String seriesRelacionadas) {
		this.seriesRelacionadas = seriesRelacionadas;
	}

	public int getTecnicaMuestreo() {
		return tecnicaMuestreo;
	}

	public void setTecnicaMuestreo(int tecnicaMuestreo) {
		this.tecnicaMuestreo = tecnicaMuestreo;
	}

	public int getTipoValorAdministrativo() {
		return tipoValorAdministrativo;
	}

	public void setTipoValorAdministrativo(int tipoValorAdministrativo) {
		this.tipoValorAdministrativo = tipoValorAdministrativo;
	}

	// public int getTipoValorHistorico() {
	// return tipoValorHistorico;
	// }
	// public void setTipoValorHistorico(int tipoValorHistorico) {
	// this.tipoValorHistorico = tipoValorHistorico;
	// }
	public int getTipoValorInformativo() {
		return tipoValorInformativo;
	}

	public void setTipoValorInformativo(int tipoValorInformativo) {
		this.tipoValorInformativo = tipoValorInformativo;
	}

	public int getTipoValorLegal() {
		return tipoValorLegal;
	}

	public void setTipoValorLegal(int tipoValorLegal) {
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

	public int getValorDictamen() {
		return valorDictamen;
	}

	public void setValorDictamen(int valorDictamen) {
		this.valorDictamen = valorDictamen;
	}

	// public String getValorHistorico() {
	// return valorHistorico;
	// }
	// public void setValorHistorico(String valorHistorico) {
	// this.valorHistorico = valorHistorico;
	// }
	public String getValorInformativo() {
		return valorInformativo;
	}

	public void setValorInformativo(String valorInformativo) {
		this.valorInformativo = valorInformativo;
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

	public String getValorLegal() {
		return valorLegal;
	}

	public void setValorLegal(String valorLegal) {
		this.valorLegal = valorLegal;
	}

	public boolean isAbierta() {
		return estado == ESTADO_ABIERTA;
	}

	public boolean isRechazada() {
		return estado == ESTADO_VALIDACION_RECHAZADA;
	}

	/*
	 * Establecimiento y acceso a las operaciones que se pueden llevar a cabo
	 * sobre la valoración
	 */
	public void setPuedeSerEditada(boolean value) {
		this.puedeSerEditada = value;
	}

	public boolean getPuedeSerEditada() {
		return puedeSerEditada;
	}

	public boolean getPermitidaSolicitudValidacion() {
		return permitidaSolicitudValidacion;
	}

	public void setPermitidaSolicitudValidacion(
			boolean permitidaSolicitudValidacion) {
		this.permitidaSolicitudValidacion = permitidaSolicitudValidacion;
	}

	public boolean getPuedeSerDictaminada() {
		return puedeSerDictaminada;
	}

	public void setPuedeSerDictaminada(boolean puedeSerDictaminada) {
		this.puedeSerDictaminada = puedeSerDictaminada;
	}

	public boolean getDictamenPuedeSerCerrado() {
		return dictamenPuedeSerCerrado;
	}

	public void setDictamenPuedeSerCerrado(boolean dictamenPuedeSerCerrado) {
		this.dictamenPuedeSerCerrado = dictamenPuedeSerCerrado;
	}

	public boolean getPuedeSerEliminada() {
		return puedeSerEliminada;
	}

	public void setPuedeSerEliminada(boolean puedeSerEliminada) {
		this.puedeSerEliminada = puedeSerEliminada;
	}

	public boolean getPuedeSerEvaluada() {
		return puedeSerEvaluada;
	}

	public void setPuedeSerEvaluada(boolean puedeSerEvaluada) {
		this.puedeSerEvaluada = puedeSerEvaluada;
	}

	public boolean getPuedeSerValidada() {
		return puedeSerValidada;
	}

	public void setPuedeSerValidada(boolean puedeSerValidada) {
		this.puedeSerValidada = puedeSerValidada;
	}

	public boolean getValidacionRechazada() {
		return this.estado == ESTADO_VALIDACION_RECHAZADA;
	}

	public boolean getEvaluacionRechazada() {
		return this.estado == ESTADO_EVALUACION_RECHAZADA;
	}

	public boolean getDictamenRechazado() {
		return this.estado == ESTADO_DICTAMEN_RECHAZADO;
	}

	public boolean getPuedeImprimirse() {
		boolean resultado = false;

		if (this.estado >= ESTADO_PENDIENTE_DE_VALIDAR)
			resultado = true;

		return resultado;
	}

	public boolean equals(Object obj) {
		boolean result = false;
		if (obj == this)
			result = true;
		else if (obj.getClass().isAssignableFrom(ValoracionSerieVO.class)) {
			ValoracionSerieVO objAsValoracion = (ValoracionSerieVO) obj;
			result = getId().equals(objAsValoracion.getId());
		}
		return result;
	}

	/**
	 * Calcula el número de conservación que se derivan de la valoración
	 */
	public int getAnosConservacion() {
		return Math
				.max(getAnosVigenciaAdministrativa(), getAnosVigenciaLegal());
	}

	public int getTipoValorCientifico() {
		return tipoValorCientifico;
	}

	public void setTipoValorCientifico(int tipoValorCientifico) {
		this.tipoValorCientifico = tipoValorCientifico;
	}

	public int getTipoValorCultural() {
		return tipoValorCultural;
	}

	public void setTipoValorCultural(int tipoValorCultural) {
		this.tipoValorCultural = tipoValorCultural;
	}

	public int getTipoValorTecnologico() {
		return tipoValorTecnologico;
	}

	public void setTipoValorTecnologico(int tipoValorTecnologico) {
		this.tipoValorTecnologico = tipoValorTecnologico;
	}

	public int getAnosRegimenAcceso() {
		return anosRegimenAcceso;
	}

	public void setAnosRegimenAcceso(int anosRegimenAcceso) {
		this.anosRegimenAcceso = anosRegimenAcceso;
	}

	public String getRegimenAcceso() {
		return regimenAcceso;
	}

	public void setRegimenAcceso(String regimenAcceso) {
		this.regimenAcceso = regimenAcceso;
	}

	public int getTipoRegimenAcceso() {
		return tipoRegimenAcceso;
	}

	public void setTipoRegimenAcceso(int tipoRegimenAcceso) {
		this.tipoRegimenAcceso = tipoRegimenAcceso;
	}

	public String getValorDictamenValue() {
		return valorDictamenValue;
	}

	public void setValorDictamenValue(String valorDictamenValue) {
		this.valorDictamenValue = valorDictamenValue;
	}

	// TODO SUSTITUIR POR OBTENCION DE INFORMACION ADICIONAL DESDE PO's
	public String getTituloSerie() {
		return tituloSerie;
	}

	public void setTituloSerie(String tituloSerie) {
		this.tituloSerie = tituloSerie;
	}

	// public String getCodigoSerie() {
	// return codigoSerie;
	// }
	// public void setCodigoSerie(String codigoSerie) {
	// this.codigoSerie = codigoSerie;
	// }
	public String getInfoSerie() {
		return infoSerie;
	}

	public void setInfoSerie(String infoSerie) {
		this.infoSerie = infoSerie;
	}

	public int getTipoRegimenAccesoTemporal() {
		return tipoRegimenAccesoTemporal;
	}

	public void setTipoRegimenAccesoTemporal(int tipoRegimenAccesoTemporal) {
		this.tipoRegimenAccesoTemporal = tipoRegimenAccesoTemporal;
	}

	public List getListaPlazos() {
		return listaPlazos;
	}

	public void setListaPlazos(List listaPlazos) {
		this.listaPlazos = listaPlazos;
	}
}
