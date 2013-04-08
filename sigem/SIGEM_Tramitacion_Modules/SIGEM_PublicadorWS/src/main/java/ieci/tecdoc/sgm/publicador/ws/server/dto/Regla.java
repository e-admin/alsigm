package ieci.tecdoc.sgm.publicador.ws.server.dto;

import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;

public class Regla extends RetornoServicio {
	
	private int id = 0;
	private int idPcd = 0;
	private int idFase = 0;
	private int idTramite = 0;
	private int tipoDoc = 0;
	private int idEvento = 0;
	private int idAccion = 0;
	private int idCondicion = 0;
	private String atributos = null;
	private int idAplicacion = 0;
	private int orden = 0;
	private int idInfo = 0;
	
	
	public Regla() {
		super();
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public int getIdPcd() {
		return idPcd;
	}


	public void setIdPcd(int idPcd) {
		this.idPcd = idPcd;
	}


	public int getIdFase() {
		return idFase;
	}


	public void setIdFase(int idFase) {
		this.idFase = idFase;
	}


	public int getIdTramite() {
		return idTramite;
	}


	public void setIdTramite(int idTramite) {
		this.idTramite = idTramite;
	}


	public int getTipoDoc() {
		return tipoDoc;
	}


	public void setTipoDoc(int tipoDoc) {
		this.tipoDoc = tipoDoc;
	}


	public int getIdEvento() {
		return idEvento;
	}


	public void setIdEvento(int idEvento) {
		this.idEvento = idEvento;
	}


	public int getIdAccion() {
		return idAccion;
	}


	public void setIdAccion(int idAccion) {
		this.idAccion = idAccion;
	}


	public int getIdCondicion() {
		return idCondicion;
	}


	public void setIdCondicion(int idCondicion) {
		this.idCondicion = idCondicion;
	}


	public String getAtributos() {
		return atributos;
	}


	public void setAtributos(String atributos) {
		this.atributos = atributos;
	}


	public int getIdAplicacion() {
		return idAplicacion;
	}


	public void setIdAplicacion(int idAplicacion) {
		this.idAplicacion = idAplicacion;
	}


	public int getOrden() {
		return orden;
	}


	public void setOrden(int orden) {
		this.orden = orden;
	}


	public int getIdInfo() {
		return idInfo;
	}


	public void setIdInfo(int idInfo) {
		this.idInfo = idInfo;
	}

}
