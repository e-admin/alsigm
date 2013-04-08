package ieci.tecdoc.sgm.pe;
/*
 * $Id: CriterioBusquedaPago.java,v 1.1.2.1 2008/01/25 12:30:16 jconca Exp $
 */
import java.util.Calendar;

public class CriterioBusquedaPago {

    private String tipo;

    private String referencia;

    private String NRC;

    private String nif;

    private String estado;

    private Calendar fechaDesde;

    private Calendar fechaHasta;
    
    private String entidad;

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Calendar getFechaDesde() {
		return fechaDesde;
	}

	public void setFechaDesde(Calendar fechaDesde) {
		this.fechaDesde = fechaDesde;
	}

	public Calendar getFechaHasta() {
		return fechaHasta;
	}

	public void setFechaHasta(Calendar fechaHasta) {
		this.fechaHasta = fechaHasta;
	}

	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	public String getNRC() {
		return NRC;
	}

	public void setNRC(String nrc) {
		NRC = nrc;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getEntidad() {
		return entidad;
	}

	public void setEntidad(String entidad) {
		this.entidad = entidad;
	}

}
