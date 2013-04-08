package ieci.tecdoc.sgm.pe;
/*
 * $Id: PagoImpl.java,v 1.1.2.1 2008/01/25 12:30:16 jconca Exp $
 */
public class PagoImpl implements
		Pago {

	// Comprobados
	protected String idEntidadEmisora;
	protected String idTasa;
	protected String importe;
	protected String nif;
	protected String idioma;
	protected String fecha;
	protected String hora;
	
	protected String entidadBancaria;
	protected String medioPago;
	protected String ccc;
	protected String numeroTarjetaCredito;
	protected String fechaCaducidadTarjetaCredito;
	protected String referencia; // justificante para 60_3
	protected String domiciliacion;
	protected String cccDomiciliacion;
	protected String nrc;
	protected String estado;
	protected Tasa tasa;
	protected LiquidacionImpl liquidacion;
	
	// Especificos de Cuaderno60_12
	protected String ejercicio;
	protected String remesa;

	// Especificos de Cuaderno60_3
	protected String acreditacion;
	protected String fechaDevengo;
	protected String informacionEspecifica;
	protected String expediente;
	
	//Especifico de pasarela
	protected String peticionPagoPasarelaExternaConRedireccion;
	
	public String getExpediente() {
		return expediente;
	}
	public void setExpediente(String expediente) {
		this.expediente = expediente;
	}
	public String getInformacionEspecifica() {
		return informacionEspecifica;
	}
	public void setInformacionEspecifica(String informacionEspecifica) {
		this.informacionEspecifica = informacionEspecifica;
	}
	public LiquidacionImpl getLiquidacion() {
		return liquidacion;
	}
	public void setLiquidacion(LiquidacionImpl liquidacion) {
		this.liquidacion = liquidacion;
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
	public String getCcc() {
		return ccc;
	}
	public void setCcc(String ccc) {
		this.ccc = ccc;
	}
	public String getCccDomiciliacion() {
		return cccDomiciliacion;
	}
	public void setCccDomiciliacion(String cccDomiciliacion) {
		this.cccDomiciliacion = cccDomiciliacion;
	}
	public String getDomiciliacion() {
		return domiciliacion;
	}
	public void setDomiciliacion(String domiciliacion) {
		this.domiciliacion = domiciliacion;
	}
	public String getEntidadBancaria() {
		return entidadBancaria;
	}
	public void setEntidadBancaria(String entidadBancaria) {
		this.entidadBancaria = entidadBancaria;
	}
	public String getFechaCaducidadTarjetaCredito() {
		return fechaCaducidadTarjetaCredito;
	}
	public void setFechaCaducidadTarjetaCredito(String fechaCaducidadTarjetaCredito) {
		this.fechaCaducidadTarjetaCredito = fechaCaducidadTarjetaCredito;
	}
	public String getIdioma() {
		return idioma;
	}
	public void setIdioma(String idioma) {
		this.idioma = idioma;
	}
	public String getMedioPago() {
		return medioPago;
	}
	public void setMedioPago(String medioPago) {
		this.medioPago = medioPago;
	}
	public String getNumeroTarjetaCredito() {
		return numeroTarjetaCredito;
	}
	public void setNumeroTarjetaCredito(String numeroTarjetaCredito) {
		this.numeroTarjetaCredito = numeroTarjetaCredito;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public String getHora() {
		return hora;
	}
	public void setHora(String hora) {
		this.hora = hora;
	}
	public String getAcreditacion() {
		return acreditacion;
	}
	public void setAcreditacion(String acreditacion) {
		this.acreditacion = acreditacion;
	}
	public String getFechaDevengo() {
		return fechaDevengo;
	}
	public void setFechaDevengo(String fechaDevengo) {
		this.fechaDevengo = fechaDevengo;
	}
	public String getPeticionPagoPasarelaExternaConRedireccion() {
		return peticionPagoPasarelaExternaConRedireccion;
	}
	public void setPeticionPagoPasarelaExternaConRedireccion(String peticionPagoPasarelaExternaConRedireccion) {
		this.peticionPagoPasarelaExternaConRedireccion = peticionPagoPasarelaExternaConRedireccion;
	}	
}
