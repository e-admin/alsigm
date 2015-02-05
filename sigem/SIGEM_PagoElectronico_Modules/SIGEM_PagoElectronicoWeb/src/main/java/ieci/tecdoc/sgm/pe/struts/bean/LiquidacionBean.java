package ieci.tecdoc.sgm.pe.struts.bean;

public class LiquidacionBean {
	
	public static final String PLAZO_VENCIDO = "01";
	public static final String PLAZO_CORRECTO = "00";
	public static final String PLAZO_INMINENTE = "02";
	
	public static final String ESTADO_PAGADO = "01";
	public static final String ESTADO_PENDIENTE = "00";
	
	private String referencia;
	private String nombre;
	private String importe;
	private String periodo;
	private String vencimiento;
	private String estado;
	private String fechaPago;
	
	public String getFechaPago() {
		return fechaPago;
	}
	public void setFechaPago(String fechaPago) {
		this.fechaPago = fechaPago;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getVencimiento() {
		return vencimiento;
	}
	public void setVencimiento(String vencimiento) {
		this.vencimiento = vencimiento;
	}
	public String getPeriodo() {
		return periodo;
	}
	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}
	public String getImporte() {
		return importe;
	}
	public void setImporte(String importe) {
		this.importe = importe;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getReferencia() {
		return referencia;
	}
	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}
}
