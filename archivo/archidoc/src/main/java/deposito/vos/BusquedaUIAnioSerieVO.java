/**
 *
 */
package deposito.vos;

import java.util.Date;

import common.vos.BaseVO;

/**
 * @author Iecisa
 * @version $Revision$
 * 
 */
public class BusquedaUIAnioSerieVO extends BaseVO {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private String idCampoFechaExtremaInicial;
	private String idCampoFechaExtremaFinal;
	private String idCampoProductor;

	private String fechaExtremaInicialOperador = null;
	private Date fechaExtremaInicialIni = null;
	private Date fechaExtremaInicialFin = null;

	// FECHA EXTREMA FINAL (4)
	private String fechaExtremaFinalOperador = null;
	private Date fechaExtremaFinalIni = null;
	private Date fechaExtremaFinalFin = null;

	private String[] idsArchivo = null;
	private String idFormato = null;
	private String[] productores = null;
	private String[] idsAmbitoSeries = null;
	private String[] idsAmbitoDeposito = null;

	private int numMaxRegistros = 0;

	private String[] idsSerie;

	private String idUbicacion;

	/**
	 * Ids de los elementos asignables donde están ubicadas las unidades de
	 * instalación.
	 */
	private String[] idsElementosAsignables = null;

	public String getFechaExtremaInicialOperador() {
		return fechaExtremaInicialOperador;
	}

	public void setFechaExtremaInicialOperador(
			String fechaExtremaInicialOperador) {
		this.fechaExtremaInicialOperador = fechaExtremaInicialOperador;
	}

	public Date getFechaExtremaInicialIni() {
		return fechaExtremaInicialIni;
	}

	public void setFechaExtremaInicialIni(Date fechaExtremaInicialIni) {
		this.fechaExtremaInicialIni = fechaExtremaInicialIni;
	}

	public Date getFechaExtremaInicialFin() {
		return fechaExtremaInicialFin;
	}

	public void setFechaExtremaInicialFin(Date fechaExtremaInicialFin) {
		this.fechaExtremaInicialFin = fechaExtremaInicialFin;
	}

	public String getFechaExtremaFinalOperador() {
		return fechaExtremaFinalOperador;
	}

	public void setFechaExtremaFinalOperador(String fechaExtremaFinalOperador) {
		this.fechaExtremaFinalOperador = fechaExtremaFinalOperador;
	}

	public Date getFechaExtremaFinalIni() {
		return fechaExtremaFinalIni;
	}

	public void setFechaExtremaFinalIni(Date fechaExtremaFinalIni) {
		this.fechaExtremaFinalIni = fechaExtremaFinalIni;
	}

	public Date getFechaExtremaFinalFin() {
		return fechaExtremaFinalFin;
	}

	public void setFechaExtremaFinalFin(Date fechaExtremaFinalFin) {
		this.fechaExtremaFinalFin = fechaExtremaFinalFin;
	}

	public String[] getIdsArchivo() {
		return idsArchivo;
	}

	public void setIdsArchivo(String[] idsArchivo) {
		this.idsArchivo = idsArchivo;
	}

	public String getIdFormato() {
		return idFormato;
	}

	public void setIdFormato(String idFormato) {
		this.idFormato = idFormato;
	}

	public String[] getProductores() {
		return productores;
	}

	public void setProductores(String[] productores) {
		this.productores = productores;
	}

	public String[] getIdsSerie() {
		return idsSerie;
	}

	public void setIdsSerie(String[] idsSerie) {
		this.idsSerie = idsSerie;
	}

	public void setIdsElementosAsignables(String[] idsElementosAsignables) {
		this.idsElementosAsignables = idsElementosAsignables;
	}

	public String[] getIdsElementosAsignables() {
		return idsElementosAsignables;
	}

	public void setIdUbicacion(String idUbicacion) {
		this.idUbicacion = idUbicacion;
	}

	public String getIdUbicacion() {
		return idUbicacion;
	}

	public void setIdsAmbitoSeries(String[] idsAmbitoSeries) {
		this.idsAmbitoSeries = idsAmbitoSeries;
	}

	public String[] getIdsAmbitoSeries() {
		return idsAmbitoSeries;
	}

	public void setIdsAmbitoDeposito(String[] idsAmbitoDeposito) {
		this.idsAmbitoDeposito = idsAmbitoDeposito;
	}

	public String[] getIdsAmbitoDeposito() {
		return idsAmbitoDeposito;
	}

	public void setNumMaxRegistros(int numMaxRegistros) {
		this.numMaxRegistros = numMaxRegistros;
	}

	public int getNumMaxRegistros() {
		return numMaxRegistros;
	}

	/**
	 * @return Entity que gestiona la tabla<b></b>
	 */
	public String getIdCampoFechaExtremaInicial() {
		return idCampoFechaExtremaInicial;
	}

	/**
	 * @param idCampoFechaExtremaInicial
	 *            the idCampoFechaExtremaInicial to set
	 */
	public void setIdCampoFechaExtremaInicial(String idCampoFechaExtremaInicial) {
		this.idCampoFechaExtremaInicial = idCampoFechaExtremaInicial;
	}

	/**
	 * @return Entity que gestiona la tabla<b></b>
	 */
	public String getIdCampoFechaExtremaFinal() {
		return idCampoFechaExtremaFinal;
	}

	/**
	 * @param idCampoFechaExtremaFinal
	 *            the idCampoFechaExtremaFinal to set
	 */
	public void setIdCampoFechaExtremaFinal(String idCampoFechaExtremaFinal) {
		this.idCampoFechaExtremaFinal = idCampoFechaExtremaFinal;
	}

	/**
	 * @return Entity que gestiona la tabla<b></b>
	 */
	public String getIdCampoProductor() {
		return idCampoProductor;
	}

	/**
	 * @param idCampoProductor
	 *            the idCampoProductor to set
	 */
	public void setIdCampoProductor(String idCampoProductor) {
		this.idCampoProductor = idCampoProductor;
	}

}
