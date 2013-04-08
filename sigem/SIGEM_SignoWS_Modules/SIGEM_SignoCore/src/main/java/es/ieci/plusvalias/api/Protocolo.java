package es.ieci.plusvalias.api;

import java.io.Serializable;
import java.util.Date;

public class Protocolo implements Serializable{
	private static final long serialVersionUID = -8077989647121493814L;
	
	private String numColegiado;
	private String nombreCompletoNotario;
	private Date fechaEjercicio;
	private long numProtocolo;

	public Protocolo() {
		super();
	}

	public Protocolo(String numColegiado, String nombreCompletoNotario,
			Date fechaEjercicio, long numProtocolo) {
		super();
		this.numColegiado = numColegiado;
		this.nombreCompletoNotario = nombreCompletoNotario;
		this.fechaEjercicio = fechaEjercicio;
		this.numProtocolo = numProtocolo;
	}

	public String getNumColegiado() {
		return numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}

	public String getNombreCompletoNotario() {
		return nombreCompletoNotario;
	}

	public void setNombreCompletoNotario(String nombreCompletoNotario) {
		this.nombreCompletoNotario = nombreCompletoNotario;
	}

	public Date getFechaEjercicio() {
		return fechaEjercicio;
	}

	public void setFechaEjercicio(Date fechaEjercicio) {
		this.fechaEjercicio = fechaEjercicio;
	}

	public long getNumProtocolo() {
		return numProtocolo;
	}

	public void setNumProtocolo(long numProtocolo) {
		this.numProtocolo = numProtocolo;
	}

	public String toString() {
		return "DatosProtocolo [fechaEjercicio=" + fechaEjercicio
				+ ", nombreCompletoNotario=" + nombreCompletoNotario
				+ ", numColegiado=" + numColegiado + ", numProtocolo="
				+ numProtocolo + "]";
	}
}