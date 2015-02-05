package ieci.tecdoc.sgm.pe.struts;
/*
 *  $Id: PagoElectronicoManagerHelper.java,v 1.2.2.2 2008/03/14 11:24:13 jnogales Exp $
 */
import ieci.tecdoc.sgm.base.datetime.DateTimeUtil;
import ieci.tecdoc.sgm.base.miscelanea.Goodies;
import ieci.tecdoc.sgm.base.xml.core.XmlDocument;
import ieci.tecdoc.sgm.base.xml.core.XmlElement;
import ieci.tecdoc.sgm.base.xml.lite.XmlTextBuilder;
import ieci.tecdoc.sgm.core.exception.SigemException;
import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.dto.Entidad;
import ieci.tecdoc.sgm.core.services.pago.CriterioBusquedaLiquidacion;
import ieci.tecdoc.sgm.core.services.pago.CriterioBusquedaTasa;
import ieci.tecdoc.sgm.core.services.pago.Liquidacion;
import ieci.tecdoc.sgm.core.services.pago.Pago;
import ieci.tecdoc.sgm.core.services.pago.ServicioPagoTelematico;
import ieci.tecdoc.sgm.core.services.pago.Tasa;
import ieci.tecdoc.sgm.core.user.web.SesionUserHelper;
import ieci.tecdoc.sgm.pe.struts.bean.LiquidacionBean;
import ieci.tecdoc.sgm.pe.struts.bean.TasaBean;
import ieci.tecdoc.sgm.pe.struts.cert.CertInfo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.axis.utils.StringUtils;
import org.apache.struts.validator.DynaValidatorForm;

public class PagoElectronicoManagerHelper {

	// formulario de pago
	public static final String CAMPO_REFERENCIA 		= "referencia";
	public static final String CAMPO_NIF				= "nif";
	public static final String CAMPO_CCC				= "ccc";
	public static final String CAMPO_CCCDOMICILIZAICON	= "domiciliacion";
	public static final String CAMPO_ENTIDAD_BANCARIA	= "entidadBancaria";
	public static final String CAMPO_MEDIO_PAGO			= "medioPago";
	public static final String CAMPO_ID_TASA			= "idTasa";
	public static final String CAMPO_ID_ENTIDAD_EMISORA = "idEntidadEmisora";
	public static final String CAMPO_NUM_TARJETA_CREDITO = "numeroTarjetaCredito";
	public static final String CAMPO_FEC_CADUCIDAD_TARJETA_CREDITO = "fechaCaducidadTarjetaCredito";
	public static final String CAMPO_FIRMA_SOLICITUD =		"firma";
	public static final String CAMPO_DATOS_FIRMADOS  =		"datosFirmados";
	public static final String CAMPO_TIPO				=	"tipo";
	public static final String CAMPO_DEVENGO			=	"fechaDevengo";
	public static final String CAMPO_INFO_ESPECIFICA	=	"informacionEspecifica";
	public static final String CAMPO_IMPORTE			=	"importe";
	public static final String CAMPO_NOMBRE				= 	"nombre";
	
	// formulario de busqueda
	public static final String CAMPO_EJERCICIO 			= "ejercicio";
	public static final String CAMPO_NRC				= "nrc";
	public static final String CAMPO_ESTADO				= "estado";
	
	public static final String ID_ENTIDAD_EMISORA 	= "idEntidadEmisora";
	public static final String REMESA 				= "remesa";
	public static final String NIF					= "nif";
	public static final String EJERCICIO 			= "ejercicio";
	public static final String ID_TASA				= "idTasa";
	public static final String VENCIMIENTO			= "vencimiento";
	public static final String IMPORTE 				= "importe";
	
	// NODOS XML
	public static final String SOLICITUD_PAGO_TAG 	= "SOLICITUD_PAGO";
	public static final String DATOS_FIRMADOS_TAG	= "DATOS_FIRMADOS";
	public static final String FIRMA_TAG			= "FIRMA";
	public static final String XPATH_NIF			= "LIQUIDACION/NIF";
	public static final String XPATH_NOMBRE			= "LIQUIDACION/NOMBRE";
	
	public static Liquidacion altaLiquidacionAL3(HttpServletRequest request, DynaValidatorForm poForm) throws Exception{
		Liquidacion poLiquidacion = new Liquidacion();
		poLiquidacion.setIdEntidadEmisora((String)poForm.get(CAMPO_ID_ENTIDAD_EMISORA));
		poLiquidacion.setIdTasa((String)poForm.get(CAMPO_ID_TASA));
		String cImporte = (String)(String)poForm.get(CAMPO_IMPORTE);
		cImporte = cImporte.replaceAll(",", ".");
		poLiquidacion.setImporte(Util.formatearImporte(cImporte, 12));
		poLiquidacion.setNif((String)poForm.get(CAMPO_NIF));
		poLiquidacion.setDatosEspecificos((String)poForm.get(CAMPO_INFO_ESPECIFICA));
		poLiquidacion.setNombre((String)poForm.get(CAMPO_NOMBRE));

		String cXMLDatosFirmados = (String)poForm.get(PagoElectronicoManagerHelper.CAMPO_DATOS_FIRMADOS);
		// Recuperamos la firma
		String cFirma = (String)poForm.get(PagoElectronicoManagerHelper.CAMPO_FIRMA_SOLICITUD);
		// Creamos el documento de solicitud de pago
		String cSolicitud = PagoElectronicoManagerHelper.obtenerDocumentoSolicitudPago(cXMLDatosFirmados, cFirma);
		poLiquidacion.setSolicitud(Goodies.fromStrToUTF8(cSolicitud));
//		PagoElectronicoManager oManager = new PagoElectronicoManager();
		ServicioPagoTelematico oManager = getServicioPagoTelematico();
		Entidad entidad = obtenerEntidad(request);
		poLiquidacion = oManager.altaLiquidacion(poLiquidacion, entidad);
		poLiquidacion.setTasa(oManager.obtenerDatosTasa(poLiquidacion.getIdTasa(), poLiquidacion.getIdEntidadEmisora(), entidad));
		return poLiquidacion;
	}

	public static void actualizarLiquidacion(HttpServletRequest request, Liquidacion poLiq) throws SigemException{
		ServicioPagoTelematico oManager = getServicioPagoTelematico();
		oManager.modificarLiquidacion(poLiq, obtenerEntidad(request));
	}

	public static List buscarLiquidaciones(HttpServletRequest request, DynaValidatorForm poForm, CertInfo poCertInfo) throws SigemException{
		List oLista = new ArrayList();
		List oResultado = new ArrayList();
		ServicioPagoTelematico oManager = getServicioPagoTelematico();
		CriterioBusquedaLiquidacion oCriterio = obtenerCriterioBusquedaLiquidacion(poForm);
		oCriterio.setNif(poCertInfo.getM_nif());
		Entidad entidad = obtenerEntidad(request);
		oLista = oManager.buscarLiquidaciones(oCriterio, entidad);
		if( (oLista != null)
			&&(oLista.size() > 0)){
			Iterator oIterator = oLista.iterator();
			LiquidacionBean obean = null;
			Liquidacion oLiq = null;
			while(oIterator.hasNext()){
				oLiq = (Liquidacion)oIterator.next();
				
					obean = new LiquidacionBean();
					obean.setReferencia(oLiq.getReferencia());
					obean.setNombre(obtenerDatosTasa(oLiq.getIdTasa(), oLiq.getIdEntidadEmisora(), entidad).getNombre());
					obean.setImporte(obtenerImporteSalida(oLiq.getImporte()));
					obean.setEstado(oLiq.getEstado());
					if(	(oLiq.getInicioPeriodo() != null) && (oLiq.getFinPeriodo() != null) ){
						StringBuffer sbPeriodo = new StringBuffer();
						sbPeriodo.append(DateTimeUtil.getDateTime(oLiq.getInicioPeriodo(), "dd/MM/yyyy"));
						sbPeriodo.append("-").append(DateTimeUtil.getDateTime(oLiq.getFinPeriodo(), "dd/MM/yyyy"));
						obean.setPeriodo(sbPeriodo.toString());
						obean.setVencimiento(obtenerEstadoTemporalLiquidacion(oLiq.getInicioPeriodo(), oLiq.getFinPeriodo()));
					}
					oResultado.add(obean);					
				}				
			
		}
		return oResultado; 
	}

	public static List obtenerListaAutoliquidacionesNIF(HttpServletRequest request, DynaValidatorForm poForm, CertInfo poCertInfo) throws SigemException{
		List oLista = new ArrayList();
		List oResultado = new ArrayList();
		ServicioPagoTelematico oManager = getServicioPagoTelematico();
		CriterioBusquedaLiquidacion oCriterio = obtenerCriterioBusquedaLiquidacion(poForm);
		oCriterio.setNif(poCertInfo.getM_nif());
		oCriterio.setEstado(Liquidacion.ESTADO_PAGADO);
		Entidad entidad = obtenerEntidad(request);
		oLista = oManager.buscarLiquidaciones(oCriterio, entidad);
		if( (oLista != null)
			&&(oLista.size() > 0)){
			Iterator oIterator = oLista.iterator();
			LiquidacionBean obean = null;
			Liquidacion oLiq = null;
			while(oIterator.hasNext()){
					oLiq = (Liquidacion)oIterator.next();
				if(oLiq.getEjercicio() == null){					
					obean = new LiquidacionBean();
					obean.setReferencia(oLiq.getReferencia());
					obean.setNombre(obtenerDatosTasa(oLiq.getIdTasa(), oLiq.getIdEntidadEmisora(), entidad).getNombre());
					obean.setImporte(obtenerImporteSalida(oLiq.getImporte()));		
					if(oLiq.getFechaPago() != null){
						obean.setFechaPago(DateTimeUtil.getDateTime(oLiq.getFechaPago(), "dd/MM/yyyy HH:mm:ss"));	
					}
					oResultado.add(obean);					
				}
			}
		}
		return oResultado; 
	}

	public static List obtenerListaLiquidacionesPendientesNIF(HttpServletRequest request, String pcNIF) throws SigemException{
		List oLista = new ArrayList();
		List oResultado = new ArrayList();
		ServicioPagoTelematico oManager = getServicioPagoTelematico();
		CriterioBusquedaLiquidacion oCriterio = new CriterioBusquedaLiquidacion();
		oCriterio.setNif(pcNIF);
		oCriterio.setEstado(Liquidacion.ESTADO_PENDIENTE);
		Entidad entidad = obtenerEntidad(request);
		oLista = oManager.buscarLiquidaciones(oCriterio, entidad);
		if( (oLista != null)
			&&(oLista.size() > 0)){
			Iterator oIterator = oLista.iterator();
			LiquidacionBean obean = null;
			Liquidacion oLiq = null;
			while(oIterator.hasNext()){
					oLiq = (Liquidacion)oIterator.next();
				if(oLiq.getEjercicio() != null){					
					obean = new LiquidacionBean();
					obean.setReferencia(oLiq.getReferencia());
					obean.setNombre(obtenerDatosTasa(oLiq.getIdTasa(), oLiq.getIdEntidadEmisora(), entidad).getNombre());
					obean.setImporte(obtenerImporteSalida(oLiq.getImporte()));					
					if(	(oLiq.getInicioPeriodo() != null) && (oLiq.getFinPeriodo() != null)
					){
						StringBuffer sbPeriodo = new StringBuffer();
						sbPeriodo.append(DateTimeUtil.getDateTime(oLiq.getInicioPeriodo(), "dd/MM/yyyy"));
						sbPeriodo.append("-").append(DateTimeUtil.getDateTime(oLiq.getFinPeriodo(), "dd/MM/yyyy"));
						obean.setPeriodo(sbPeriodo.toString());					
						obean.setVencimiento(obtenerEstadoTemporalLiquidacion(oLiq.getInicioPeriodo(), oLiq.getFinPeriodo()));					
					}
					oResultado.add(obean);					
				}
			}
		}
		return oResultado; 
	}
	

	public static Liquidacion obtenerDatosLiquidacion(HttpServletRequest request, DynaValidatorForm poFormPago) throws SigemException{
		return obtenerDatosLiquidacion(request, (String)poFormPago.get(CAMPO_REFERENCIA));
	}

	
	public static Liquidacion obtenerDatosLiquidacion(HttpServletRequest request, String pcNumRef) throws SigemException{
		ServicioPagoTelematico oManager = getServicioPagoTelematico();
		CriterioBusquedaLiquidacion oCriterio = new CriterioBusquedaLiquidacion();
		oCriterio.setReferencia(pcNumRef);
		Entidad entidad = obtenerEntidad(request);
		List oLista = oManager.buscarLiquidaciones(oCriterio, entidad);
		if(  (oLista != null)
			 &&(oLista.size() > 0)
		){
			Liquidacion oLiquidacion = (Liquidacion)oLista.get(0);
			Tasa oTasa = oManager.obtenerDatosTasa(oLiquidacion.getIdTasa(), oLiquidacion.getIdEntidadEmisora(), entidad);
			oLiquidacion.setTasa(oTasa);
			return oLiquidacion;
		}
		return null;
	}
	
	public static String obtenerDocumentoPago(HttpServletRequest request, String pcReferencia) throws SigemException{
		
		ServicioPagoTelematico oManager = getServicioPagoTelematico();
		Entidad entidad = obtenerEntidad(request);
		Pago oPago = oManager.detallePago(pcReferencia, entidad);
		return obtenerDocumentoPago(request, oPago);
	}
	
	public static String obtenerDocumentoPago(HttpServletRequest request, DynaValidatorForm poFormPago) throws SigemException{
		Pago oPago = new Pago();
		oPago.setReferencia((String)poFormPago.get(CAMPO_REFERENCIA));
		oPago.setCcc((String)poFormPago.get(CAMPO_CCC));
		oPago.setCccDomiciliacion((String)poFormPago.get(CAMPO_CCC));
		oPago.setDomiciliacion((String)poFormPago.get(CAMPO_CCCDOMICILIZAICON));
		oPago.setEntidadBancaria((String)poFormPago.get(CAMPO_ENTIDAD_BANCARIA));
		oPago.setMedioPago((String)poFormPago.get(CAMPO_MEDIO_PAGO));
		oPago.setNif((String)poFormPago.get(CAMPO_NIF));
		oPago.setIdTasa((String)poFormPago.get(CAMPO_ID_TASA));		
		oPago.setEjercicio((String)poFormPago.get(CAMPO_EJERCICIO));
		oPago.setIdEntidadEmisora((String)poFormPago.get(CAMPO_ID_ENTIDAD_EMISORA));
		oPago.setNumeroTarjetaCredito((String)poFormPago.get(CAMPO_NUM_TARJETA_CREDITO));
		oPago.setFechaCaducidadTarjetaCredito((String)poFormPago.get(CAMPO_FEC_CADUCIDAD_TARJETA_CREDITO));
		oPago.setFechaDevengo((String)poFormPago.get(CAMPO_DEVENGO));
		oPago.setInformacionEspecifica((String)poFormPago.get(CAMPO_INFO_ESPECIFICA));
		String cImporte = (String)poFormPago.get(CAMPO_IMPORTE);
		cImporte = cImporte.replaceAll(",", ".");
		oPago.setImporte(Util.formatearImporte(cImporte, 12));
		
		if(StringUtils.isEmpty(oPago.getIdTasa())){
			Liquidacion liquidacion=obtenerDatosLiquidacion(request, poFormPago);
			oPago.setIdTasa(liquidacion.getIdTasa());
			oPago.setIdEntidadEmisora(liquidacion.getIdEntidadEmisora());
		}
		return obtenerDocumentoPago(request, oPago);
	}
	
	
	public static Pago obtenerPagoBean(DynaValidatorForm poFormPago) throws Exception{
		Pago oPago = new Pago();
		oPago.setReferencia((String)poFormPago.get(CAMPO_REFERENCIA));
		oPago.setCcc((String)poFormPago.get(CAMPO_CCC));
		oPago.setCccDomiciliacion((String)poFormPago.get(CAMPO_CCC));
		oPago.setDomiciliacion((String)poFormPago.get(CAMPO_CCCDOMICILIZAICON));
		oPago.setEntidadBancaria((String)poFormPago.get(CAMPO_ENTIDAD_BANCARIA));
		oPago.setMedioPago((String)poFormPago.get(CAMPO_MEDIO_PAGO));
		oPago.setNif((String)poFormPago.get(CAMPO_NIF));
		oPago.setIdTasa((String)poFormPago.get(CAMPO_ID_TASA));
		oPago.setEjercicio((String)poFormPago.get(CAMPO_EJERCICIO));	
		oPago.setIdEntidadEmisora((String)poFormPago.get(CAMPO_ID_ENTIDAD_EMISORA));
		oPago.setNumeroTarjetaCredito((String)poFormPago.get(CAMPO_NUM_TARJETA_CREDITO));
		oPago.setFechaCaducidadTarjetaCredito((String)poFormPago.get(CAMPO_FEC_CADUCIDAD_TARJETA_CREDITO));
		String cAux = (String)poFormPago.get(CAMPO_DEVENGO);
		if( (cAux != null) && (!"".equals(cAux))){
			Date oFecha = DateTimeUtil.getDate(cAux, "dd/MM/yyyy");
			Calendar cal = GregorianCalendar.getInstance();
			cal.setTime(oFecha);
			oPago.setFechaDevengo(getFechaFormateada(cal));
		}
		oPago.setInformacionEspecifica((String)poFormPago.get(CAMPO_INFO_ESPECIFICA));
		oPago.setImporte((String)poFormPago.get(CAMPO_IMPORTE));
		return oPago;
	}
	
	public static Pago realizarPago(HttpServletRequest request, DynaValidatorForm poFormPago) throws Exception{
		Pago oPago = obtenerPagoBean(poFormPago);
		return realizarPago(request, oPago);
	}
	
	public static Pago realizarPago(HttpServletRequest request, Pago poPago) throws SigemException{
		ServicioPagoTelematico oManager = getServicioPagoTelematico();
		poPago = oManager.realizarPago(poPago, obtenerEntidad(request));
		return poPago;
	}
	
	public static CriterioBusquedaLiquidacion obtenerCriterioBusquedaLiquidacion(DynaValidatorForm poForm){
		CriterioBusquedaLiquidacion oCriterio = new CriterioBusquedaLiquidacion();
		oCriterio.setEjercicio((String)poForm.get(CAMPO_EJERCICIO));
		oCriterio.setEstado((String)poForm.get(CAMPO_ESTADO));
		oCriterio.setIdEntidadEmisora((String)poForm.get(CAMPO_ID_ENTIDAD_EMISORA));
		oCriterio.setIdTasa((String)poForm.get(CAMPO_ID_TASA));
		oCriterio.setNrc((String)poForm.get(CAMPO_NRC));
		oCriterio.setReferencia((String)poForm.get(CAMPO_REFERENCIA));
		return oCriterio;
	}
	

	public static CriterioBusquedaTasa obtenerCriterioBusquedaTasa(DynaValidatorForm poForm){
		CriterioBusquedaTasa oCriterio = new CriterioBusquedaTasa();
		oCriterio.setTipo((String)poForm.get(CAMPO_TIPO));
		return oCriterio;
	}

	
	public static String obtenerDocumentoPago(HttpServletRequest request, Pago poPago) throws SigemException{
		ServicioPagoTelematico oManager = getServicioPagoTelematico();
		Entidad entidad = obtenerEntidad(request);
		return oManager.obtenerDocumentoPago(poPago, entidad);
	}
	
	public static String obtenerDocumentoSolicitudPago(String pcDatosFirmados, String pcFirma){
		XmlTextBuilder bdr;
	
		bdr = new XmlTextBuilder();
		
		bdr.setStandardHeader();			  
		bdr.addOpeningTag(SOLICITUD_PAGO_TAG);
			bdr.addOpeningTag(DATOS_FIRMADOS_TAG);
				bdr.addFragment(pcDatosFirmados);
			bdr.addClosingTag(DATOS_FIRMADOS_TAG);
			bdr.addSimpleElement(FIRMA_TAG, pcFirma);
		bdr.addClosingTag(SOLICITUD_PAGO_TAG);
				  
		return bdr.getText();
	}
	
	
	public static String incluirInformacionCertificado(CertInfo poCert, String pcXMLPago) throws Exception{
		XmlDocument oDoc = new XmlDocument();
		oDoc.createFromStringText(pcXMLPago);
		XmlElement oElem = oDoc.getRootElement();
		oElem.getDescendantElement(XPATH_NIF).setValue(poCert.getM_nif());
		oElem.getDescendantElement(XPATH_NOMBRE).setValue(poCert.getM_nombre(), true);
		return oDoc.getStringText(true);
	}
	
	private static ServicioPagoTelematico getServicioPagoTelematico() throws SigemException{
		return LocalizadorServicios.getServicioPagoTelematico();
	}
	
	public static Tasa obtenerDatosTasa(HttpServletRequest request, DynaValidatorForm poForm) throws SigemException{
		String cIdEntidad = (String)poForm.get(CAMPO_ID_ENTIDAD_EMISORA);
		String cIdTasa = (String)poForm.get(CAMPO_ID_TASA);

		ServicioPagoTelematico oManager = getServicioPagoTelematico();
		return oManager.obtenerDatosTasa(cIdTasa, cIdEntidad, obtenerEntidad(request));
	}
	
	private static Tasa obtenerDatosTasa(String pcIdTasa, String pcIdEntidadEmisora, Entidad entidad) throws SigemException{
		ServicioPagoTelematico oManager = getServicioPagoTelematico();
		return oManager.obtenerDatosTasa(pcIdTasa, pcIdEntidadEmisora, entidad);
	}
	
	public static List buscarAutoliquidaciones(HttpServletRequest request, DynaValidatorForm poForm) throws SigemException {
		List oLista = new ArrayList();
		List oResultado = new ArrayList();
		ServicioPagoTelematico oManager = getServicioPagoTelematico();
		CriterioBusquedaTasa oCriterio = obtenerCriterioBusquedaTasa(poForm);
		oCriterio.setTipo(Tasa.TIPO_AL3);
		oLista = oManager.buscarTasas(oCriterio, obtenerEntidad(request));
		if( (oLista != null)
			&&(oLista.size() > 0)){
			Iterator oIterator = oLista.iterator();
			TasaBean obean = null;
			Tasa oLiq = null;
			while(oIterator.hasNext()){
				oLiq = (Tasa)oIterator.next();
				obean = new TasaBean();
				obean.setCodigo(oLiq.getCodigo());
				obean.setIdEntidad(oLiq.getIdEntidadEmisora());
				obean.setModelo(oLiq.getModelo());
				obean.setNombre(oLiq.getNombre());
				obean.setTipo(oLiq.getTipo());
				obean.setIdTasa(oLiq.getCodigo());
				oResultado.add(obean);
			}
		}
		return oResultado; 
	}

	public static String obtenerImporteSalida(String pcImporte){
		int enCentimos = Integer.valueOf(pcImporte).intValue();
		double enEuros = enCentimos / 100.0;
		return String.valueOf(enEuros);
	}
	
	private static String obtenerEstadoTemporalLiquidacion(Date pdFechaInicio, Date pdFechaFin){
		Date oActual = DateTimeUtil.getCurrentDateTime();
		if(		(pdFechaInicio.before(oActual))
				&&(pdFechaFin.after(oActual))
		){
			// estamos en plazo
			if( pdFechaFin.getTime() - oActual.getTime() < new Long("2678400000").intValue()){
				return LiquidacionBean.PLAZO_INMINENTE;
			}else{
				return LiquidacionBean.PLAZO_CORRECTO;
			}
		}else{
			return LiquidacionBean.PLAZO_VENCIDO;
		}
	}
	
	   public static String getFechaFormateada(Calendar cal){
		   StringBuffer sbFecha = new StringBuffer();
		   String cAux = String.valueOf(cal.get(GregorianCalendar.YEAR));
		   sbFecha.append(cAux);

		   cAux = String.valueOf(cal.get(GregorianCalendar.MONTH) + 1);
		   if(cAux.length() < 2){
			   sbFecha.append("0");
		   }
		   sbFecha.append(cAux);
		   cAux = String.valueOf(cal.get(GregorianCalendar.DAY_OF_MONTH));
		   if(cAux.length() < 2){
			   sbFecha.append("0");
		   }
		   sbFecha.append(cAux);
		   return sbFecha.toString();
	   }

		public static Entidad obtenerEntidad(HttpServletRequest request){
			Entidad oEntidad = new Entidad();
			oEntidad.setIdentificador(SesionUserHelper.obtenerIdentificadorEntidad(request));
			return oEntidad;
		}
}
