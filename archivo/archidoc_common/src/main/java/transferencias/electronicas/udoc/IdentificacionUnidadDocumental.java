package transferencias.electronicas.udoc;

import transferencias.electronicas.ficha.CampoFecha;
import transferencias.electronicas.serie.Productor;

public class IdentificacionUnidadDocumental {
	private String id;
	private String numExp;
	private String titulo;
	private CampoFecha fechaInicio;
	private CampoFecha fechaFin;
	private Productor productor;
	private String codigoProcedimiento;
	/**
	 * @return el id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id el id a fijar
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return el numExp
	 */
	public String getNumExp() {
		return numExp;
	}
	/**
	 * @param numExp el numExp a fijar
	 */
	public void setNumExp(String numExp) {
		this.numExp = numExp;
	}
	/**
	 * @return el titulo
	 */
	public String getTitulo() {
		return titulo;
	}
	/**
	 * @param titulo el titulo a fijar
	 */
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	/**
	 * @return el fechaInicio
	 */
	public CampoFecha getFechaInicio() {
		return fechaInicio;
	}
	/**
	 * @param fechaInicio el fechaInicio a fijar
	 */
	public void setFechaInicio(CampoFecha fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	/**
	 * @return el fechaFin
	 */
	public CampoFecha getFechaFin() {
		return fechaFin;
	}
	/**
	 * @param fechaFin el fechaFin a fijar
	 */
	public void setFechaFin(CampoFecha fechaFin) {
		this.fechaFin = fechaFin;
	}
	/**
	 * @return el codigProcedimiento
	 */
	public String getCodigoProcedimiento() {
		return codigoProcedimiento;
	}
	/**
	 * @param codigProcedimiento el codigProcedimiento a fijar
	 */
	public void setCodigoProcedimiento(String codigoProcedimiento) {
		this.codigoProcedimiento = codigoProcedimiento;
	}
	/**
	 * @return el productor
	 */
	public Productor getProductor() {
		return productor;
	}
	/**
	 * @param productor el productor a fijar
	 */
	public void setProductor(Productor productor) {
		this.productor = productor;
	}
}
