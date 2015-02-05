package fondos.model;

import java.util.Map;

/*
 * En esta clase se pueden añadir campos que deben ir precargados para realizar una búsqueda genérica
 * en fondos. También se puede poner el forward al que debe redirigir la action para realizar el listado.
 *
 * NO ESTÁN AÑADIDOS TODOS, SÓLO LOS QUE SE NECESITAN, EN CASO DE AÑADIR MÁS HABRÍA QUE MODIFICAR LAS CLASES
 * BusquedaElementosAction y BusquedaElementosForm.
 */

/**
 * Clase para precargar condiciones de campos de búsqueda en la búsqueda
 * genérica de elementos del cuadro
 */
public class PrecondicionesBusquedaFondosGenerica {

	// public static final String BUSQUEDA_GENERICA_ELIMINACION_SELECCION_UDOCS
	// = "eliminacion/eliminacion_sel_udocs";
	// public static final String BUSQUEDA_GENERICA_REEMPLAZO_SIMPLE =
	// "descripcion/reemplazo_simple";

	/**
	 * Ruta al fichero XML de configuración de búsqueda
	 */
	String tipoBusqueda = null;

	/**
	 * Niveles a buscar
	 */
	private String[] niveles = new String[0];

	/**
	 * Estados a buscar
	 */
	private String[] estados = new String[0];

	/**
	 * Codigos de referencia de ambito
	 */
	private String[] idRefObjetoAmbito = new String[0];

	/**
	 * Tipo de objeto ámbito
	 */
	private String[] tipoObjetoAmbito = new String[0];

	/**
	 * Tipos de nivel de ficha a mostrar
	 */
	private int[] tiposNivelFicha = null;

	/**
	 * Forward a ejecutar para mostrar el listado
	 */
	private String forwardListado = null;

	/**
	 * Forward a ejecutar para retornar una vez seleccionados elementos
	 */
	private String forwardRetorno = null;

	/**
	 * Map con los elementos a excluir en la búsqueda, sería un map con clave
	 * nombre del Campo de la tabla principal de fondos (ASGFELEMENTOCF) y value
	 * una lista de valores a excluir
	 */
	private Map elementosExcluir = null;

	/**
	 * Map con los elementos a restringir en la búsqueda, sería un map con clave
	 * nombre del Campo de la tabla principal de fondos (ASGFELEMENTOCF) y value
	 * una lista de valores a restringir
	 */
	private Map elementosRestringir = null;

	/**
	 * Subquery para los elementos a restringir
	 */
	private Map subqueryElementosRestringir = null;

	private String entradaParaMigaPan = null;

	/**
	 * Tipo de elemento del cuadro a buscar
	 */
	private String tipoElemento = null;

	private String idArchivo = null;

	private String keyCfgBusqueda = null;

	private String keyElementos = null;

	private String[] bloqueos = new String[0];

	private String idPadre = null;

	private int numMaxResultados = 0;

	private int numResultadosPorPagina = 0;

	/**
	 * @return the tipoBusqueda
	 */
	public String getTipoBusqueda() {
		return tipoBusqueda;
	}

	/**
	 * @param tipoBusqueda
	 *            the tipoBusqueda to set
	 */
	public void setTipoBusqueda(String tipoBusqueda) {
		this.tipoBusqueda = tipoBusqueda;
	}

	/**
	 * @return el codRefObjetoAmbito
	 */
	public String[] getIdRefObjetoAmbito() {
		return idRefObjetoAmbito;
	}

	/**
	 * @param codRefObjetoAmbito
	 *            el codRefObjetoAmbito a establecer
	 */
	public void setIdRefObjetoAmbito(String[] idRefObjetoAmbito) {
		this.idRefObjetoAmbito = idRefObjetoAmbito;
	}

	/**
	 * @return el estados
	 */
	public String[] getEstados() {
		return estados;
	}

	/**
	 * @param estados
	 *            el estados a establecer
	 */
	public void setEstados(String[] estados) {
		this.estados = estados;
	}

	/**
	 * @return el niveles
	 */
	public String[] getNiveles() {
		return niveles;
	}

	/**
	 * @param niveles
	 *            el niveles a establecer
	 */
	public void setNiveles(String[] niveles) {
		this.niveles = niveles;
	}

	/**
	 * @return el tiposNivelFicha
	 */
	public int[] getTiposNivelFicha() {
		return tiposNivelFicha;
	}

	/**
	 * @param tiposNivelFicha
	 *            el tiposNivelFicha a establecer
	 */
	public void setTiposNivelFicha(int[] tiposNivelFicha) {
		this.tiposNivelFicha = tiposNivelFicha;
	}

	/**
	 * @return el forwardListado
	 */
	public String getForwardListado() {
		return forwardListado;
	}

	/**
	 * @param forwardListado
	 *            el forwardListado a establecer
	 */
	public void setForwardListado(String forwardListado) {
		this.forwardListado = forwardListado;
	}

	/**
	 * @return el elementosExcluir
	 */
	public Map getElementosExcluir() {
		return elementosExcluir;
	}

	/**
	 * @param elementosExcluir
	 *            el elementosExcluir a establecer
	 */
	public void setElementosExcluir(Map elementosExcluir) {
		this.elementosExcluir = elementosExcluir;
	}

	/**
	 * @return el elementosRestringir
	 */
	public Map getElementosRestringir() {
		return elementosRestringir;
	}

	/**
	 * @param elementosExcluir
	 *            el elementosExcluir a establecer
	 */
	public void setElementosRestringir(Map elementosRestringir) {
		this.elementosRestringir = elementosRestringir;
	}

	/**
	 * @return el tipoElemento
	 */
	public String getTipoElemento() {
		return tipoElemento;
	}

	/**
	 * @param tipoElemento
	 *            el tipoElemento a establecer
	 */
	public void setTipoElemento(String tipoElemento) {
		this.tipoElemento = tipoElemento;
	}

	/**
	 * @return el forwardRetorno
	 */
	public String getForwardRetorno() {
		return forwardRetorno;
	}

	/**
	 * @param forwardRetorno
	 *            el forwardRetorno a establecer
	 */
	public void setForwardRetorno(String forwardRetorno) {
		this.forwardRetorno = forwardRetorno;
	}

	public String getEntradaParaMigaPan() {
		return entradaParaMigaPan;
	}

	public void setEntradaParaMigaPan(String entradaParaMigaPan) {
		this.entradaParaMigaPan = entradaParaMigaPan;
	}

	public String getIdArchivo() {
		return idArchivo;
	}

	public void setIdArchivo(String idArchivo) {
		this.idArchivo = idArchivo;
	}

	public void setKeyCfgBusqueda(String keyCfgBusqueda) {
		this.keyCfgBusqueda = keyCfgBusqueda;
	}

	public String getKeyCfgBusqueda() {
		return keyCfgBusqueda;
	}

	public void setKeyElementos(String keyElementos) {
		this.keyElementos = keyElementos;
	}

	public String getKeyElementos() {
		return keyElementos;
	}

	public String[] getBloqueos() {
		return bloqueos;
	}

	public void setBloqueos(String[] bloqueos) {
		this.bloqueos = bloqueos;
	}

	public Map getSubqueryElementosRestringir() {
		return subqueryElementosRestringir;
	}

	public void setSubqueryElementosRestringir(Map subqueryElementosRestringir) {
		this.subqueryElementosRestringir = subqueryElementosRestringir;
	}

	public void setIdPadre(String idPadre) {
		this.idPadre = idPadre;
	}

	public String getIdPadre() {
		return idPadre;
	}

	public void setTipoObjetoAmbito(String[] tipoObjetoAmbito) {
		this.tipoObjetoAmbito = tipoObjetoAmbito;
	}

	public String[] getTipoObjetoAmbito() {
		return tipoObjetoAmbito;
	}

	public void setNumMaxResultados(int numMaxResultados) {
		this.numMaxResultados = numMaxResultados;
	}

	public int getNumMaxResultados() {
		return numMaxResultados;
	}

	public void setNumResultadosPorPagina(int numResultadosPorPagina) {
		this.numResultadosPorPagina = numResultadosPorPagina;
	}

	public int getNumResultadosPorPagina() {
		return numResultadosPorPagina;
	}

}