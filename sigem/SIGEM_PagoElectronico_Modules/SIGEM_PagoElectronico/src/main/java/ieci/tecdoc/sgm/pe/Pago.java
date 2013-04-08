package ieci.tecdoc.sgm.pe;
/*
 * $Id: Pago.java,v 1.1.2.1 2008/01/25 12:30:16 jconca Exp $
 */
public interface Pago {

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
	
	public String getEjercicio();
	public void setEjercicio(String ejercicio) ;
	public String getEstado();
	public void setEstado(String estado);
	public String getIdEntidadEmisora();
	public void setIdEntidadEmisora(String idEntidadEmisora);
	public String getIdTasa();
	public void setIdTasa(String idTasa);
	public String getImporte();
	public void setImporte(String importe);
	public String getNrc();
	public void setNrc(String nrc);
	public String getReferencia();
	public void setReferencia(String referencia);
	public String getNif();
	public void setNif(String nif);
	public String getRemesa();
	public void setRemesa(String remesa);
	public String getCcc();
	public void setCcc(String ccc);
	public String getCccDomiciliacion();
	public void setCccDomiciliacion(String cccDomiciliacion);
	public String getDomiciliacion();
	public void setDomiciliacion(String domiciliacion);
	public String getEntidadBancaria();
	public void setEntidadBancaria(String entidadBancaria);
	public String getFechaCaducidadTarjetaCredito();
	public void setFechaCaducidadTarjetaCredito(String fechaCaducidadTarjetaCredito);
	public String getIdioma();
	public void setIdioma(String idioma);
	public String getMedioPago();
	public void setMedioPago(String medioPago);
	public String getNumeroTarjetaCredito();
	public void setNumeroTarjetaCredito(String numeroTarjetaCredito);
	public String getFecha();
	public void setFecha(String fecha);
	public String getHora();
	public void setHora(String hora);
	public LiquidacionImpl getLiquidacion();
	public void setLiquidacion(LiquidacionImpl liquidacion);
	public String getAcreditacion();
	public void setAcreditacion(String acreditacion);
	public String getFechaDevengo();
	public void setFechaDevengo(String fechaDevengo);
	public String getInformacionEspecifica();
	public void setInformacionEspecifica(String informacionEspecifica);
	public String getExpediente();
	public void setExpediente(String expediente);
	
}
