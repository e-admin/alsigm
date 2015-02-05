package ieci.tecdoc.sgm.pe;
/*
 * $Id: Liquidacion.java,v 1.1.2.1 2008/01/25 12:30:16 jconca Exp $
 */
import java.util.Date;

public interface Liquidacion {

	// Estados del pago
	public static final String ESTADO_PENDIENTE = "00";
	public static final String ESTADO_PAGADO = "01";
	
	// Discriminante de periodo
	public static final String DISCRIMINANTE_UN_PERIODO = "1";
	public static final String DISCRIMINANTE_DOS_PERIODOS_PRIMER_IMPORTE = "5";
	public static final String DISCRIMINANTE_DOS_PERIODOS_SEGUNDO_IMPORTE = "9";

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
	public TasaImpl getTasa();
	public void setTasa(TasaImpl tasa);	
	public String getVencimiento();
	public void setVencimiento(String vencimiento);
	public String getDiscriminante();
	public void setDiscriminante(String discriminante);
	public String getDatosEspecificos();
	public void setDatosEspecificos(String datosEspecificos);
	public String getNombre();
	public void setNombre(String nombre);
	public Date getFinPeriodo();
	public void setFinPeriodo(Date finPeriodo);
	public Date getInicioPeriodo();
	public void setInicioPeriodo(Date inicioPeriodo);
	public byte[] getSolicitud();
	public void setSolicitud(byte[] solicitud);
	public Date getFechaPago();
	public void setFechaPago(Date fechaPago);
	
}
