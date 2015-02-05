package ieci.tecdoc.sgm.pe;
/*
 * $Id: LiquidacionImpl.java,v 1.1.2.1 2008/01/25 12:30:16 jconca Exp $
 */
import java.util.Date;


public class LiquidacionImpl implements
		Liquidacion {

	protected String referencia;
	protected String idEntidadEmisora;
	protected String idTasa;
	protected String ejercicio;
	protected String vencimiento;
	protected String discriminante;
	protected String remesa;
	protected String importe;
	protected String nif;
	protected String nrc;
	protected String estado;
	protected TasaImpl tasa;
	protected String nombre;
	protected String datosEspecificos;
	protected Date inicioPeriodo;
	protected Date finPeriodo;
	protected byte[] solicitud;
	protected Date fechaPago;
	
	public byte[] getSolicitud() {
		return solicitud;
	}
	public void setSolicitud(byte[] solicitud) {
		this.solicitud = solicitud;
	}
	public TasaImpl getTasa() {
		return tasa;
	}
	public void setTasa(TasaImpl tasa) {
		this.tasa = tasa;
	}
	public String getEjercicio() {
		return ejercicio;
	}
	public void setEjercicio(String ejercicio) {
		this.ejercicio = ejercicio;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getIdEntidadEmisora() {
		return idEntidadEmisora;
	}
	public void setIdEntidadEmisora(String idEntidadEmisora) {
		this.idEntidadEmisora = idEntidadEmisora;
	}
	public String getIdTasa() {
		return idTasa;
	}
	public void setIdTasa(String idTasa) {
		this.idTasa = idTasa;
	}
	public String getImporte() {
		return importe;
	}
	public void setImporte(String importe) {
		this.importe = importe;
	}
	public String getNrc() {
		return nrc;
	}
	public void setNrc(String nrc) {
		this.nrc = nrc;
	}
	public String getReferencia() {
		return referencia;
	}
	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}
	public String getNif() {
		return nif;
	}
	public void setNif(String nif) {
		this.nif = nif;
	}
	public String getRemesa() {
		return remesa;
	}
	public void setRemesa(String remesa) {
		this.remesa = remesa;
	}
	public String getVencimiento() {
		return vencimiento;
	}
	public void setVencimiento(String vencimiento) {
		this.vencimiento = vencimiento;
	}
	public String getDiscriminante() {
		return discriminante;
	}
	public void setDiscriminante(String discriminante) {
		this.discriminante = discriminante;
	}
	public String getDatosEspecificos() {
		return datosEspecificos;
	}
	public void setDatosEspecificos(String datosEspecificos) {
		this.datosEspecificos = datosEspecificos;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Date getFinPeriodo() {
		return finPeriodo;
	}
	public void setFinPeriodo(Date finPeriodo) {
		this.finPeriodo = finPeriodo;
	}
	public Date getInicioPeriodo() {
		return inicioPeriodo;
	}
	public void setInicioPeriodo(Date inicioPeriodo) {
		this.inicioPeriodo = inicioPeriodo;
	}
	public Date getFechaPago() {
		return fechaPago;
	}
	public void setFechaPago(Date fechaPago) {
		this.fechaPago = fechaPago;
	}
}
