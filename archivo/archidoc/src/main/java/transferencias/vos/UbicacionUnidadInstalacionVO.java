package transferencias.vos;

import java.util.HashMap;

/**
 * Informacion referente a la ubicación de una unidad de instalación.
 */
public class UbicacionUnidadInstalacionVO extends UnidadInstalacionVO {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/** Signatura. */
	public String signaturaUI;

	/** Ruta de ubicación. */
	public String path = null;

	// campos para el informe de cartelas de unidades de instalacion
	private String codOrden;

	/** Identificador del padre del elemento en el que está ubicada la ui **/
	private String idelemapadre;

	private HashMap mapElementosDepositoNombresElemento = null;

	// private HashMap tablaCodsOrden=null;
	// private HashMap tablaNombresED=null;

	/**
	 * Constructor.
	 */
	public UbicacionUnidadInstalacionVO() {
		super();
	}

	/**
	 * Obtiene la signatura.
	 * 
	 * @return Signatura
	 */
	public String getSignaturaUI() {
		return signaturaUI;
	}

	/**
	 * Establece la signatura.
	 * 
	 * @param signatura
	 *            Signatura.
	 */
	public void setSignaturaUI(String signatura) {
		this.signaturaUI = signatura;
	}

	/**
	 * Obtiene la ruta de ubicación.
	 * 
	 * @return Ruta de ubicación.
	 */
	public String getPath() {
		return path;
	}

	/**
	 * Establece la ruta de ubicación.
	 * 
	 * @param path
	 *            Ruta de ubicación.
	 */
	public void setPath(String path) {
		this.path = path;
	}

	public String getCodOrden() {
		return codOrden;
	}

	public void setCodOrden(String codOrdenUI) {
		this.codOrden = codOrdenUI;
		// rellenartablas();
		// rellenartablaNombresED();
	}

	public String getIdelemapadre() {
		return idelemapadre;
	}

	public void setIdelemapadre(String idelemapadre) {
		this.idelemapadre = idelemapadre;
	}

	public HashMap getMapElementosDepositoNombresElemento() {
		return mapElementosDepositoNombresElemento;
	}

	public void setMapElementosDepositoNombresElemento(
			HashMap mapElementosDepositoNombresElemento) {
		this.mapElementosDepositoNombresElemento = mapElementosDepositoNombresElemento;
	}

	/*
	 * private void rellenartablas(){ if(tablaCodsOrden==null)
	 * tablaCodsOrden=new HashMap(); if (tablaNombresED==null)
	 * tablaNombresED=new HashMap(); String [] stPath = null;
	 * 
	 * if(!StringUtils.isEmpty(codOrden)){ StringTokenizer st = new
	 * StringTokenizer(codOrden,Constants.SEPARADOR_COD_ORDEN); if
	 * (!StringUtils.isEmpty(path)) stPath = path.split(Constants.SLASH);
	 * 
	 * // Empezamos a contar los elementos del path en 1 porque nos saltamos la
	 * ubicación int contPath = 1; while(st.hasMoreTokens()){ String
	 * cad=st.nextToken(); String letra=""+cad.charAt(0); String
	 * codigo=cad.substring(1); tablaCodsOrden.put(letra,
	 * StringUtils.eliminarCerosIzquierda(codigo));
	 * 
	 * if (stPath != null && stPath.length >= contPath) { // Nos saltamos la
	 * ubicación String nombreCompleto = stPath[contPath]; // Le quitamos la
	 * parte de nombre de elemento de depósito (depósito, balda, cuerpo, ...) if
	 * (nombreCompleto != null){ int posPrimerBlanco =
	 * nombreCompleto.indexOf(Constants.STRING_SPACE); if (posPrimerBlanco != -1
	 * && nombreCompleto.length() >= posPrimerBlanco+1) { String nombreElemento
	 * = nombreCompleto.substring(posPrimerBlanco+1); tablaNombresED.put(letra,
	 * nombreElemento); } } }
	 * 
	 * contPath++; } } }
	 */
}