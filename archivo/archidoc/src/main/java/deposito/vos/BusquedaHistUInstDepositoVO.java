package deposito.vos;

import java.util.Date;

public class BusquedaHistUInstDepositoVO extends HistUInstDepositoVO {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public BusquedaHistUInstDepositoVO(String id, String idArchivo,
			String idFormato, String signaturaUI, String identificacion,
			Date feliminacion, Integer motivo) {
		super(id, idArchivo, idFormato, signaturaUI, identificacion,
				feliminacion, motivo);
		// TODO Auto-generated constructor stub
	}

	public BusquedaHistUInstDepositoVO() {
		super();
	}

	private String signatura_like = null;
	private String[] motivos = new String[0];
	private String[] archivos = new String[0];

	private Date fechaIni = null;
	private Date fechaFin = null;
	private String nombreArchivo = null;

	private String nombreFormato = null;

	public String[] getMotivos() {
		return motivos;
	}

	public void setMotivos(String[] motivos) {
		this.motivos = motivos;
	}

	public Date getFechaIni() {
		return fechaIni;
	}

	public void setFechaIni(Date fechaIni) {
		this.fechaIni = fechaIni;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public void setSignatura_like(String signatura_like) {
		this.signatura_like = signatura_like;
	}

	public String getSignatura_like() {
		return signatura_like;
	}

	public void setNombreFormato(String nombreFormato) {
		this.nombreFormato = nombreFormato;
	}

	public String getNombreFormato() {
		return nombreFormato;
	}

	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}

	public String getNombreArchivo() {
		return nombreArchivo;
	}

	public void setArchivos(String[] archivos) {
		this.archivos = archivos;
	}

	public String[] getArchivos() {
		return archivos;
	}
}
