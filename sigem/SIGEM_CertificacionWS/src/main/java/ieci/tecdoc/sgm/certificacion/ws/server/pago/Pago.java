package ieci.tecdoc.sgm.certificacion.ws.server.pago;

import ieci.tecdoc.sgm.core.services.dto.RetornoServicio;

public class Pago extends RetornoServicio {

	// Estados del pago
	public static final String ESTADO_PENDIENTE = "00";
	public static final String ESTADO_PAGADO = "01";
	// Domiciliacion
	public static final String DOMICILIACION_SI = "1";
	public static final String DOMICILIACION_NO = "2";
	// Identificacion del medio de pago
	public static final String MEDIO_PAGO_CARGO_CUENTA = "1";
	public static final String MEDIO_PAGO_TARJETA = "2";
	// Idiomas
	public static final String IDIOMA_CASTELLANO = "0";
	public static final String IDIOMA_CATALAN = "1";
	// Acreditación
	public static final String ACREDITACION_TERCERO_AUTORIZADO = "1";
	public static final String ACREDITACION_NO_TERCERO_AUTORIZADO = "0";

	private String idEntidadEmisora;
	private String idTasa;
	private String importe;
	private String nif;
	private String idioma;
	private String fecha;
	private String hora;
	
	private String entidadBancaria;
	private String medioPago;
	private String ccc;
	private String numeroTarjetaCredito;
	private String fechaCaducidadTarjetaCredito;
	private String referencia; // justificante para 60_3
	private String domiciliacion;
	private String cccDomiciliacion;
	private String nrc;
	private String estado;
	private Tasa tasa;
	private Liquidacion liquidacion;
	
	// Especificos de Cuaderno60_12
	private String ejercicio;
	private String remesa;

	// Especificos de Cuaderno60_3
	private String acreditacion;
	private String fechaDevengo;
	private String informacionEspecifica;
	private String expediente;
	public String getAcreditacion() {
		return acreditacion;
	}
	public void setAcreditacion(String acreditacion) {
		this.acreditacion = acreditacion;
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
	public String getEjercicio() {
		return ejercicio;
	}
	public void setEjercicio(String ejercicio) {
		this.ejercicio = ejercicio;
	}
	public String getEntidadBancaria() {
		return entidadBancaria;
	}
	public void setEntidadBancaria(String entidadBancaria) {
		this.entidadBancaria = entidadBancaria;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getExpediente() {
		return expediente;
	}
	public void setExpediente(String expediente) {
		this.expediente = expediente;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public String getFechaCaducidadTarjetaCredito() {
		return fechaCaducidadTarjetaCredito;
	}
	public void setFechaCaducidadTarjetaCredito(String fechaCaducidadTarjetaCredito) {
		this.fechaCaducidadTarjetaCredito = fechaCaducidadTarjetaCredito;
	}
	public String getFechaDevengo() {
		return fechaDevengo;
	}
	public void setFechaDevengo(String fechaDevengo) {
		this.fechaDevengo = fechaDevengo;
	}
	public String getHora() {
		return hora;
	}
	public void setHora(String hora) {
		this.hora = hora;
	}
	public String getIdEntidadEmisora() {
		return idEntidadEmisora;
	}
	public void setIdEntidadEmisora(String idEntidadEmisora) {
		this.idEntidadEmisora = idEntidadEmisora;
	}
	public String getIdioma() {
		return idioma;
	}
	public void setIdioma(String idioma) {
		this.idioma = idioma;
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
	public String getInformacionEspecifica() {
		return informacionEspecifica;
	}
	public void setInformacionEspecifica(String informacionEspecifica) {
		this.informacionEspecifica = informacionEspecifica;
	}
	public Liquidacion getLiquidacion() {
		return liquidacion;
	}
	public void setLiquidacion(Liquidacion liquidacion) {
		this.liquidacion = liquidacion;
	}
	public String getMedioPago() {
		return medioPago;
	}
	public void setMedioPago(String medioPago) {
		this.medioPago = medioPago;
	}
	public String getNif() {
		return nif;
	}
	public void setNif(String nif) {
		this.nif = nif;
	}
	public String getNrc() {
		return nrc;
	}
	public void setNrc(String nrc) {
		this.nrc = nrc;
	}
	public String getNumeroTarjetaCredito() {
		return numeroTarjetaCredito;
	}
	public void setNumeroTarjetaCredito(String numeroTarjetaCredito) {
		this.numeroTarjetaCredito = numeroTarjetaCredito;
	}
	public String getReferencia() {
		return referencia;
	}
	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}
	public String getRemesa() {
		return remesa;
	}
	public void setRemesa(String remesa) {
		this.remesa = remesa;
	}
	public Tasa getTasa() {
		return tasa;
	}
	public void setTasa(Tasa tasa) {
		this.tasa = tasa;
	}

}
