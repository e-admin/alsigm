package deposito.vos;

import java.util.Date;
import java.util.List;

import common.util.DateUtils;
import common.vos.BaseVO;

import deposito.MarcaUtilUI;

/**
 * Value Object con la información referente a las unidades de instalación en
 * las que se almacena la documentación en el depósito físico
 */
public class UInsDepositoVO extends BaseVO {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	String id;
	String idformato;
	String signaturaui;
	String identificacion;
	String nombreFormato;
	String unidadesvalidas;
	String unidadestotales;
	int marcasBloqueo;
	Date fcreacion;
	String fcreacionString;

	public UInsDepositoVO() {
		super();
	}

	public UInsDepositoVO(String idformato, String signaturaui,
			String identificacion) {
		super();
		this.idformato = idformato;
		this.signaturaui = signaturaui;
		this.identificacion = identificacion;
	}

	public Date getFcreacion() {
		return fcreacion;
	}

	public void setFcreacion(Date fcreacion) {
		this.fcreacion = fcreacion;
	}

	/**
	 * Lista de ElementoCuadroClasificacion
	 */
	List listaUDocs;

	public void setIduinstalacion(String id) {
		this.id = id;
	}

	public String getIdentificacion() {
		return this.identificacion;
	}

	public void setIdentificacion(String identificacion) {
		this.identificacion = identificacion;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIdformato() {
		return this.idformato;
	}

	public void setIdformato(String idformato) {
		this.idformato = idformato;
	}

	public String getSignaturaui() {
		return this.signaturaui;
	}

	public String getSignaturaUI() {
		return this.signaturaui;
	}

	public void setSignaturaui(String signaturaui) {
		this.signaturaui = signaturaui;
	}

	public int getMarcasBloqueo() {
		return marcasBloqueo;
	}

	public void setMarcasBloqueo(int marcas) {
		this.marcasBloqueo = marcas;
	}

	public String getNombreFormato() {
		return nombreFormato;
	}

	public void setNombreFormato(String nombreFormato) {
		this.nombreFormato = nombreFormato;
	}

	public String getUnidadestotales() {
		return unidadestotales;
	}

	public void setUnidadestotales(String unidadestotales) {
		this.unidadestotales = unidadestotales;
	}

	public String getUnidadesvalidas() {
		return unidadesvalidas;
	}

	public void setUnidadesvalidas(String unidadesvalidas) {
		this.unidadesvalidas = unidadesvalidas;
	}

	/**
	 * Comparación de Igualdad entre objetos a través de su id.
	 */
	public boolean equals(Object obj) {
		if (obj == null)
			return false;

		if (obj instanceof UInsDepositoVO) {
			if (this.id != null) {
				return this.id.equals(((UInsDepositoVO) obj).id);
			}
		}
		return false;
	}

	/**
	 * Permite obtener si la unidad de instalación está bloqueada
	 * 
	 * @return si la unidad de instalación está bloqueada
	 */
	public boolean isBloqueada() {
		return MarcaUtilUI.isUnidadInstalacionBloqueada(this.marcasBloqueo);
	}

	/**
	 * @return el listaUDocs Lista de ElementoCuadroClasificacion
	 */
	public List getListaUDocs() {
		return listaUDocs;
	}

	/**
	 * @param listaUDocs
	 *            el listaUDocs a establecer
	 */
	public void setListaUDocs(List listaUDocs) {
		this.listaUDocs = listaUDocs;
	}

	public String getFcreacionString() {
		return DateUtils.formatDate(fcreacion);
	}

}