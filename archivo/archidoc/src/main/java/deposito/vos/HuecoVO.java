package deposito.vos;

import java.util.Date;
import java.util.HashMap;

/**
 * Value Object con la información de un hueco en el fondo físico
 */
public class HuecoVO {
	public final static String LIBRE_STATE = "L";

	public final static String OCUPADO_STATE = "O";

	public final static String INUTILIZADO_STATE = "I";

	public final static String RESERVADO_STATE = "R";

	HuecoID huecoID;
	String idpadre;
	Integer numorden;
	String iddeposito;
	String estado;
	String idformato;
	String iduinstalacion;
	String idrelacion;
	String path;
	Date fechaestado;
	String signaturaUI;
	String rentrega;
	Integer ordenenrelacion;
	String ano;
	Integer orden;
	String codorden;
	Integer tipoord;
	String codArchivo;
	String numeracion;
	Integer marcas = new Integer(0);
	private String idUIReeaCR;

	private HashMap mapElementosDepositoNombresElemento = null;

	public HuecoVO() {
	}

	public HuecoVO(String idPadre, int numOrden, String idDeposito) {
		this.idpadre = idPadre;
		this.numorden = new Integer(numOrden);
		this.huecoID = new HuecoID(idPadre, numOrden);
		this.iddeposito = idDeposito;
		this.estado = LIBRE_STATE;
		this.fechaestado = new Date();
	}

	public String getIdRelEntrega() {
		return idrelacion;
	}

	public void setIdRelEntrega(String idrelacion) {
		this.idrelacion = idrelacion;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getIddeposito() {
		return iddeposito;
	}

	public void setIddeposito(String iddeposito) {
		this.iddeposito = iddeposito;
	}

	public String getIdElemAPadre() {
		return idpadre;
	}

	public void setIdElemAPadre(String idpadre) {
		this.idpadre = idpadre;
	}

	public String getIduinstalacion() {
		return iduinstalacion;
	}

	public void setIduinstalacion(String iduinstalacion) {
		this.iduinstalacion = iduinstalacion;
	}

	public Integer getNumorden() {
		return numorden;
	}

	public void setNumorden(Integer numorden) {
		this.numorden = numorden;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getIdformato() {
		return idformato;
	}

	public void setIdformato(String idformato) {
		this.idformato = idformato;
	}

	public Date getFechaestado() {
		return fechaestado;
	}

	public void setFechaestado(Date fechaestado) {
		this.fechaestado = fechaestado;
	}

	public boolean equals(Object object) {
		return false;
	}

	public HuecoID getHuecoID() {
		if (idpadre != null && numorden != null)
			return new HuecoID(idpadre, numorden.intValue());
		else
			return this.huecoID;
	}

	public String getIdHueco() {
		if (idpadre != null && numorden != null) {
			return idpadre + ":" + numorden;
		} else {
			return null;
		}
	}

	public String getSignaturaUI() {
		return signaturaUI;
	}

	public void setSignaturaUI(String signaturaUI) {
		this.signaturaUI = signaturaUI;
	}

	public String getRentrega() {
		return rentrega;
	}

	public void setRentrega(String rentrega) {
		this.rentrega = rentrega;
	}

	public Integer getOrdenenrelacion() {
		return ordenenrelacion;
	}

	public void setOrdenenrelacion(Integer ordenenrelacion) {
		this.ordenenrelacion = ordenenrelacion;
	}

	public Integer getOrden() {
		return orden;
	}

	public void setOrden(Integer orden) {
		this.orden = orden;
	}

	public String getAno() {
		return ano;
	}

	public void setAno(String ano) {
		this.ano = ano;
	}

	public Integer getTipoord() {
		return tipoord;
	}

	public void setTipoord(Integer tipoord) {
		this.tipoord = tipoord;
	}

	public String getCodorden() {
		return codorden;
	}

	public void setCodorden(String codorden) {
		this.codorden = codorden;
	}

	/**
	 * @return the mapElementosDepositoNombresElemento
	 */
	public HashMap getMapElementosDepositoNombresElemento() {
		return mapElementosDepositoNombresElemento;
	}

	/**
	 * @param mapElementosDepositoNombresElemento
	 *            the mapElementosDepositoNombresElemento to set
	 */
	public void setMapElementosDepositoNombresElemento(
			HashMap mapElementosDepositoNombresElemento) {
		this.mapElementosDepositoNombresElemento = mapElementosDepositoNombresElemento;
	}

	public String getCodArchivo() {
		return codArchivo;
	}

	public void setCodArchivo(String codArchivo) {
		this.codArchivo = codArchivo;
	}

	public String getNumeracion() {
		return numeracion;
	}

	public void setNumeracion(String numeracion) {
		this.numeracion = numeracion;
	}

	public Integer getMarcas() {
		return marcas;
	}

	public void setMarcas(Integer marcas) {
		this.marcas = marcas;
	}

	public void setIdUIReeaCR(String idUIReeaCR) {
		this.idUIReeaCR = idUIReeaCR;
	}

	public String getIdUIReeaCR() {
		return idUIReeaCR;
	}
}