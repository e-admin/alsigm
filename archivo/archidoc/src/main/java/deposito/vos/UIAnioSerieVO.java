/**
 *
 */
package deposito.vos;

import java.util.Date;

import common.Constants;
import common.util.DateUtils;
import common.vos.BaseVO;

/**
 * @author Iecisa
 * @version $Revision$
 * 
 */
public class UIAnioSerieVO extends BaseVO {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Signatura de la Unidad de instalación
	 */
	private String signaturaui;

	/**
	 * Título de la Serie
	 */
	private String tituloSerie;

	/**
	 * Nombre del Productor
	 */
	private String nombreProductor;

	/**
	 * Fecha Inicial Menor de las unidades documentales que cumplen los
	 * criterios
	 */
	private Date fechaInicialMenor;

	/**
	 * Fecha Inicial Mayor de las unidades documentales que cumplen los criteros
	 */
	private Date fechaInicialMayor;

	/**
	 * Nombre del Formato de la Unidad de Instalación
	 */
	private String nombreFormato;

	/**
	 * Número de Años diferentes de las unidades de instalación
	 */
	private int countAnios;

	/**
	 * Número de Series a las que pertenecen las unidades documentales de la
	 * unidad de instalación.
	 */
	private int countSeries;

	/**
	 * Identificador de la Unidad de Instalación
	 */
	private String idUI;

	/**
	 * Identificador de la Unidad de la Serie
	 */
	private String idSerie;

	/**
	 * Identificador del Formato de la Unidad de Instalación
	 */
	private String idFormato;

	/**
	 * Identificador del Productor
	 */
	private String idProductor;

	private String idElementoAsignable;

	private Integer numOrdenHueco;

	public String getSignaturaui() {
		return signaturaui;
	}

	public void setSignaturaui(String signaturaui) {
		this.signaturaui = signaturaui;
	}

	public String getTituloSerie() {
		return tituloSerie;
	}

	public void setTituloSerie(String tituloSerie) {
		this.tituloSerie = tituloSerie;
	}

	public String getNombreProductor() {
		return nombreProductor;
	}

	public void setNombreProductor(String nombreProductor) {
		this.nombreProductor = nombreProductor;
	}

	public Date getFechaInicialMenor() {
		return fechaInicialMenor;
	}

	public void setFechaInicialMenor(Date fechaInicialMenor) {
		this.fechaInicialMenor = fechaInicialMenor;
	}

	public Date getFechaInicialMayor() {
		return fechaInicialMayor;
	}

	public void setFechaInicialMayor(Date fechaInicialMayor) {
		this.fechaInicialMayor = fechaInicialMayor;
	}

	public String getNombreFormato() {
		return nombreFormato;
	}

	public void setNombreFormato(String nombreFormato) {
		this.nombreFormato = nombreFormato;
	}

	public int getCountAnios() {
		return countAnios;
	}

	public void setCountAnios(int countAnios) {
		this.countAnios = countAnios;
	}

	public int getCountSeries() {
		return countSeries;
	}

	public void setCountSeries(int countSeries) {
		this.countSeries = countSeries;
	}

	public String getIdUI() {
		return idUI;
	}

	public void setIdUI(String idUI) {
		this.idUI = idUI;
	}

	public String getIdSerie() {
		return idSerie;
	}

	public void setIdSerie(String idSerie) {
		this.idSerie = idSerie;
	}

	public String getIdFormato() {
		return idFormato;
	}

	public void setIdFormato(String idFormato) {
		this.idFormato = idFormato;
	}

	public String getIdProductor() {
		return idProductor;
	}

	public void setIdProductor(String idProductor) {
		this.idProductor = idProductor;
	}

	public String getIdElementoAsignable() {
		return idElementoAsignable;
	}

	public void setIdElementoAsignable(String idElementoAsignable) {
		this.idElementoAsignable = idElementoAsignable;
	}

	public Integer getNumOrdenHueco() {
		return numOrdenHueco;
	}

	public void setNumOrdenHueco(Integer numOrdenHueco) {
		this.numOrdenHueco = numOrdenHueco;
	}

	// NO GENERADAS
	public String getFechaInicialMenorAsString() {
		return DateUtils.formatDate(fechaInicialMenor);
	}

	public String getFechaInicialMayorAsString() {
		return DateUtils.formatDate(fechaInicialMayor);
	}

	public boolean isMismoAnio() {
		if (this.countAnios > 1)
			return false;

		return true;
	}

	public boolean isOtrasSeries() {
		if (this.countSeries > 1)
			return true;

		return false;
	}

	public String getIdHueco() {
		return this.idElementoAsignable + Constants.DELIMITER_IDS
				+ this.numOrdenHueco.toString();
	}
	// FIN NO GENERADAS
}
