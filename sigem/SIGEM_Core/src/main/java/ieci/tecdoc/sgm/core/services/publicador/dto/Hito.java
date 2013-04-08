package ieci.tecdoc.sgm.core.services.publicador.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * Información de un hito de publicación.
 *
 */
public class Hito implements Serializable {

	private int idHito = 0;
	private int idPcd = 0;
	private int idFase = 0;
	private int idTramite = 0;
	private int tipoDoc = 0;
	private String idObjeto = null;
	private int idEvento = 0;
	private int estado = 0;
	private int idAplicacion = 0;
	private String ipMaquina = null;
	private Date fecha = null;
	private String idSistema = null;
	private String infoAux = null;
	private int idInfo = 0;

	public Hito() {
		super();
	}

	public Hito(int idHito, int idPcd, int idFase, int idTramite, int tipoDoc,
			String idObjeto, int idEvento, int estado, int idAplicacion,
			String ipMaquina, Date fecha, String idSistema, String infoAux,
			int idInfo) {
		super();
		this.idHito = idHito;
		this.idPcd = idPcd;
		this.idFase = idFase;
		this.idTramite = idTramite;
		this.tipoDoc = tipoDoc;
		this.idObjeto = idObjeto;
		this.idEvento = idEvento;
		this.estado = estado;
		this.idAplicacion = idAplicacion;
		this.ipMaquina = ipMaquina;
		this.fecha = fecha;
		this.idSistema = idSistema;
		this.infoAux = infoAux;
		this.idInfo = idInfo;
	}

	public int getIdHito() {
		return idHito;
	}

	public void setIdHito(int idHito) {
		this.idHito = idHito;
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

	public String getIdObjeto() {
		return idObjeto;
	}

	public void setIdObjeto(String idObjeto) {
		this.idObjeto = idObjeto;
	}

	public int getIdEvento() {
		return idEvento;
	}

	public void setIdEvento(int idEvento) {
		this.idEvento = idEvento;
	}

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}

	public int getIdAplicacion() {
		return idAplicacion;
	}

	public void setIdAplicacion(int idAplicacion) {
		this.idAplicacion = idAplicacion;
	}

	public String getIpMaquina() {
		return ipMaquina;
	}

	public void setIpMaquina(String ipMaquina) {
		this.ipMaquina = ipMaquina;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getIdSistema() {
		return idSistema;
	}

	public void setIdSistema(String idSistema) {
		this.idSistema = idSistema;
	}

	public String getInfoAux() {
		return infoAux;
	}

	public void setInfoAux(String infoAux) {
		this.infoAux = infoAux;
	}

	public int getIdInfo() {
		return idInfo;
	}

	public void setIdInfo(int idInfo) {
		this.idInfo = idInfo;
	}

}
