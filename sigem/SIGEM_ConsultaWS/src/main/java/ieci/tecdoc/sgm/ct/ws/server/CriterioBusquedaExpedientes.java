package ieci.tecdoc.sgm.ct.ws.server;
/*
 * $Id: CriterioBusquedaExpedientes.java,v 1.1.2.1 2008/01/28 11:20:47 jconca Exp $
 */
public class CriterioBusquedaExpedientes {

	private String NIF;
	private String fechaDesde;
	private String fechaHasta;
	private String operadorConsulta;
	private String estado;

	private String expediente;
	private String procedimiento;
	private String numeroRegistroInicial;
	private String fechaRegistroInicialDesde;
	private String fechaRegistroInicialHasta;
	private String operadorConsultaFechaInicial;

	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getFechaDesde() {
		return fechaDesde;
	}
	public void setFechaDesde(String fechaDesde) {
		this.fechaDesde = fechaDesde;
	}
	public String getFechaHasta() {
		return fechaHasta;
	}
	public void setFechaHasta(String fechaHasta) {
		this.fechaHasta = fechaHasta;
	}
	public String getNIF() {
		return NIF;
	}
	public void setNIF(String nif) {
		NIF = nif;
	}
	public String getOperadorConsulta() {
		return operadorConsulta;
	}
	public void setOperadorConsulta(String operadorConsulta) {
		this.operadorConsulta = operadorConsulta;
	}
	public String getExpediente() {
		return expediente;
	}
	public void setExpediente(String expediente) {
		this.expediente = expediente;
	}
	public String getProcedimiento() {
		return procedimiento;
	}
	public void setProcedimiento(String procedimiento) {
		this.procedimiento = procedimiento;
	}
	public String getNumeroRegistroInicial() {
		return numeroRegistroInicial;
	}
	public void setNumeroRegistroInicial(String numeroRegistroInicial) {
		this.numeroRegistroInicial = numeroRegistroInicial;
	}
	public String getFechaRegistroInicialDesde() {
		return fechaRegistroInicialDesde;
	}
	public void setFechaRegistroInicialDesde(String fechaRegistroInicialDesde) {
		this.fechaRegistroInicialDesde = fechaRegistroInicialDesde;
	}
	public String getFechaRegistroInicialHasta() {
		return fechaRegistroInicialHasta;
	}
	public void setFechaRegistroInicialHasta(String fechaRegistroInicialHasta) {
		this.fechaRegistroInicialHasta = fechaRegistroInicialHasta;
	}
	public String getOperadorConsultaFechaInicial() {
		return operadorConsultaFechaInicial;
	}
	public void setOperadorConsultaFechaInicial(String operadorConsultaFechaInicial) {
		this.operadorConsultaFechaInicial = operadorConsultaFechaInicial;
	}

}
