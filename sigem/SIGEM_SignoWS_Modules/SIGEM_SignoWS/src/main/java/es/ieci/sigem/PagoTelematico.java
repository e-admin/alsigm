package es.ieci.sigem;

import ieci.tecdoc.sgm.base.file.FileManager;
import ieci.tecdoc.sgm.pe.ws.client.Entidad;
import ieci.tecdoc.sgm.pe.ws.client.RetornoServicio;
import ieci.tecdoc.sgm.pe.ws.client.CriterioBusquedaLiquidacion;
import ieci.tecdoc.sgm.pe.ws.client.DocumentoPago;
import ieci.tecdoc.sgm.pe.ws.client.Liquidacion;
import ieci.tecdoc.sgm.pe.ws.client.ListaLiquidaciones;
import ieci.tecdoc.sgm.pe.ws.client.Pago;
import ieci.tecdoc.sgm.pe.ws.client.PagoTelematicoWebServiceServiceLocator;
import ieci.tecdoc.sgm.pe.ws.client.PagoTelematicoWebServiceSoapBindingStub;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.axis.AxisFault;
import org.apache.log4j.Logger;

import es.ieci.plusvalias.config.Configuration;
import es.ieci.plusvalias.sigem.receipt.KeyStoreData;
import es.ieci.plusvalias.sigem.receipt.PDFReceiptCreator;

public class PagoTelematico
{
	public static final String ESTADO_PENDIENTE = "00";
	public static final String ESTADO_PAGADO = "01";
	
	public static final String MEDIO_PAGO_CUENTA = "1";
	public static final String MEDIO_PAGO_TARJETA = "2";
	
	public static final String DOMICILIACION_SI = "1";
	public static final String DOMICILIACION_NO = "2";
	
	public static final Logger logger = Logger.getLogger(PagoTelematico.class);
	
	private PagoTelematicoWebServiceSoapBindingStub servicio;
	
	private String endpoint;
	
	public PagoTelematico(String endpoint)
	{
		this.endpoint = endpoint;
	}
	
	private PagoTelematicoWebServiceSoapBindingStub getServicio() throws AxisFault, MalformedURLException
	{
		if (servicio == null)
		{
			servicio = creaServicio(getEndpoint());
		}
		
		return servicio;
	}
	
	private PagoTelematicoWebServiceSoapBindingStub creaServicio(String endpoint) throws AxisFault, MalformedURLException
	{
		return new PagoTelematicoWebServiceSoapBindingStub(new URL(endpoint), new PagoTelematicoWebServiceServiceLocator());
	}
	
	public Liquidacion altaLiquidacion(String idEntidad, Date fechaActual, String idEntidadEmisora, String idTasa,
			String nif, String nombre, String diasPlazo, double importe, String detalle)
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("Alta de liquidacion: " + idEntidad + ", " + fechaActual + ", " + idEntidadEmisora + ", " + idTasa + ", "
					+ nif + ", " + nombre + ", " + diasPlazo + ", " + importe  + ", " + detalle);
		}
		
		Liquidacion liquidacion = new Liquidacion();
		
		try
		{
			liquidacion.setEjercicio(new SimpleDateFormat("yyyy").format(fechaActual));
			liquidacion.setEstado(ESTADO_PENDIENTE);
			liquidacion.setIdEntidadEmisora(idEntidadEmisora);
			liquidacion.setIdTasa(idTasa);
			liquidacion.setNif(nif);
			liquidacion.setNombre(nombre);
			liquidacion.setRemesa("00");
			liquidacion.setDatosEspecificos("<DETALLE>" + detalle + "</DETALLE>");
			
			// TODO
			//liquidacion.setSolicitud(solicitud);
			
			Calendar fechaInicioPeriodo = Calendar.getInstance();
			fechaInicioPeriodo.setTime(fechaActual);
			liquidacion.setInicioPeriodo(fechaInicioPeriodo);
			
			Calendar fechaVencimiento = Calendar.getInstance();
			fechaVencimiento.setTime(fechaActual);
			fechaVencimiento.add(Calendar.DATE, Integer.parseInt(diasPlazo));
			liquidacion.setVencimiento(new SimpleDateFormat("yyyyMMdd").format(fechaVencimiento.getTime()));
			
			Calendar fechaFinPeriodo = Calendar.getInstance();
			fechaFinPeriodo.setTime(fechaVencimiento.getTime());
			liquidacion.setFinPeriodo(fechaFinPeriodo);
			
			DecimalFormat formateador = new DecimalFormat("#0.00");
			String importeFormateado = formateador.format(importe);
			liquidacion.setImporte(importeFormateado.replaceAll(",", ""));
			
			Entidad entidad = new Entidad();
			entidad.setIdentificador(idEntidad);
			
			liquidacion = getServicio().altaLiquidacion(liquidacion, entidad);
			
			if (!liquidacion.getReturnCode().equals("OK"))
			{
				logger.error("Error al dar de alta la liquidación. ErrorCode:" + liquidacion.getErrorCode());
				
			}
		}
		catch (Exception e)
		{
			liquidacion.setReturnCode("ERROR");
			liquidacion.setErrorCode("1310010000");
			
			logger.error(e.getMessage(), e);
		}
		
		return liquidacion;
	}
	
	public Liquidacion buscarLiquidacion(String idEntidad, String referencia) throws Exception
	{
		Liquidacion liquidacion = null;
		
		try
		{
			CriterioBusquedaLiquidacion cbl = new CriterioBusquedaLiquidacion();
			cbl.setReferencia(referencia);
			
			Entidad entidad = new Entidad();
			entidad.setIdentificador(idEntidad);
			
			ListaLiquidaciones lista = getServicio().buscarLiquidaciones(cbl, entidad);
			
			if (lista.getReturnCode().equals("OK"))
			{
				if (lista.getLiquidaciones().length > 0)
				{				
					liquidacion = lista.getLiquidaciones()[0];
				}				
			}
		}
		catch (Exception e)
		{
			throw new Exception(e.getMessage(), e);
		}
		
		return liquidacion;
	}
	
	
	public boolean modificarLiquidacion(String idEntidad, Liquidacion liquidacion) throws Exception
	{
		if (liquidacion.getEstado().equals(ESTADO_PAGADO))
		{
			throw new Exception("El pago de la plusvalia ya se ha realizado");
		}
		
		try
		{			
			liquidacion.setEstado(ESTADO_PAGADO);
			
			Calendar fechaPago = Calendar.getInstance();
			fechaPago.setTime(new Date());
			liquidacion.setFechaPago(fechaPago);
			
			Entidad entidad = new Entidad();
			entidad.setIdentificador(idEntidad);
			
			Liquidacion l = getServicio().modificarLiquidacion(liquidacion, entidad);
			
			if (!l.getReturnCode().equals("OK"))
			{
				System.out.println("Error en modificacion de liquidacion. ErrorCode:" + l.getErrorCode());				
			}
			else
			{
				return true;
			}
		}
		catch (Exception e)
		{
			throw new Exception(e.getMessage(), e);
		}
		
		return false;
	}
	
	public Pago realizarPago(String idEntidad, String expediente, String referencia, String entidadBancaria, String oficina,
			String digitoControl, String numeroCuenta, String nifSujetoPasivo, String nombreSujetoPasivo) throws Exception
	{
		String ccc = entidadBancaria + oficina + digitoControl + numeroCuenta;
		
		Liquidacion liquidacion = buscarLiquidacion(idEntidad, referencia);
		
		Pago poPago = new Pago();
		poPago.setCcc(ccc);
		poPago.setEjercicio(liquidacion.getEjercicio());
		poPago.setEntidadBancaria(entidadBancaria);
		poPago.setEstado(liquidacion.getEstado());
		poPago.setExpediente(expediente);
		poPago.setMedioPago(MEDIO_PAGO_CUENTA);
		poPago.setIdEntidadEmisora(liquidacion.getIdEntidadEmisora());
		poPago.setIdTasa(liquidacion.getIdTasa());
		poPago.setNif(nifSujetoPasivo);
		poPago.setDomiciliacion(DOMICILIACION_NO);
		poPago.setReferencia(liquidacion.getReferencia());
		poPago.setImporte(liquidacion.getImporte());
		poPago.setRemesa(liquidacion.getRemesa());
		poPago.setIdioma("0");
		
		liquidacion.setNif(nifSujetoPasivo);
		liquidacion.setNombre(nombreSujetoPasivo);
		
		poPago.setLiquidacion(liquidacion);
		
		Entidad entidad = new Entidad();
		entidad.setIdentificador(idEntidad);
		
		Pago p = getServicio().realizarPago(poPago, entidad);
		
		return p;
	}
	
	public Pago getDetallePago(String idEntidad, String referencia) throws Exception
	{
		Entidad entidad = new Entidad();
		entidad.setIdentificador(idEntidad);
		
		Pago p = new Pago();
		p.setReferencia(referencia);
		
		p = getServicio().detallePago(p, entidad);
		
		return p;
	}
	
	public DocumentoPago obtenerDocumentoPago(String idEntidad, Pago pago) throws Exception
	{		
		Entidad entidad = new Entidad();
		entidad.setIdentificador(idEntidad);
		
		DocumentoPago doc = getServicio().obtenerDocumentoPago(pago, entidad);
		
		return doc;
	}
	
	public DocumentoPago obtenerDocumentoPago(String idEntidad, String referencia) throws Exception
	{		
		Entidad entidad = new Entidad();
		entidad.setIdentificador(idEntidad);
		
		DocumentoPago doc = getServicio().obtenerDocumentoPago(getDetallePago(idEntidad, referencia), entidad);
		
		return doc;
	}
	
	public RetornoServicio bajaLiquidacion(String idEntidad, Liquidacion liquidacion)  throws Exception
	{
		Entidad entidad = new Entidad();
		entidad.setIdentificador(idEntidad);
		
		return getServicio().bajaLiquidacion(liquidacion, entidad);
	}
	
	public RetornoServicio bajaLiquidacion(String idEntidad, String referencia)  throws Exception
	{
		Entidad entidad = new Entidad();
		entidad.setIdentificador(idEntidad);
		
		Liquidacion liquidacion = new Liquidacion();
		liquidacion.setReferencia(referencia);
		
		return getServicio().bajaLiquidacion(liquidacion, entidad);
	}
	
	public String getEndpoint()
	{
		return endpoint;
	}

	public void setEndpoint(String endpoint)
	{
		this.endpoint = endpoint;
	}
	
	public byte[] getResguardoPago(KeyStoreData keystoreData, String idEntidad, Pago pago, String receiptPath,
			String reason, String field, String location, String text) throws Exception
	{
		byte[] oPdfBytes = null;
		byte[] oBytes=Configuration.getPlantillaJustificantePago();
		
		DocumentoPago dp = obtenerDocumentoPago(idEntidad, pago);
		
		String docPago = dp.getDocumentoPago();
		
		oPdfBytes = PDFReceiptCreator.createReceipt(keystoreData, reason, field, location, text, oBytes, docPago);
		
		return oPdfBytes;
	}
	
	public static void main(String args[])
	{
		PagoTelematico pt = new PagoTelematico("http://10.228.69.223:8080/SIGEM_PagoElectronicoWS/services/PagoTelematicoWebService");
		KeyStoreData keystoreData = new KeyStoreData("c:/aytobadajozFirma.pfx", "PKCS12", "adojac", "alias", "adojac");
		
		try
		{
			String idEntidad = "000";
			String referencia= "000000004391";
			
			byte[] oPdfBytes = pt.getResguardoPago(keystoreData, idEntidad, pt.getDetallePago(idEntidad, referencia), "c:/receipt.pdf", "Firma justificante", "NOMBRE", "España",
					"Texto configurable");
			
			FileManager.writeBytesToFile("c:/pdfResultado.pdf", oPdfBytes);
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.exit(0);
	}
}
