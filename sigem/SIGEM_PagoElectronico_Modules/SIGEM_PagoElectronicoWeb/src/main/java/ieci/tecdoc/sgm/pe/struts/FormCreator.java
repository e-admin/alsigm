package ieci.tecdoc.sgm.pe.struts;

import ieci.tecdoc.sgm.base.file.FileManager;
import ieci.tecdoc.sgm.base.xml.core.XmlDocument;
import ieci.tecdoc.sgm.base.xml.core.XmlTransformer;
import ieci.tecdoc.sgm.base.xml.lite.XmlTextBuilder;
import ieci.tecdoc.sgm.core.services.pago.Liquidacion;
import ieci.tecdoc.sgm.core.services.pago.Tasa;
import ieci.tecdoc.sgm.pe.struts.cert.CertInfo;
import ieci.tecdoc.sgm.pe.struts.receipt.PDFReceiptCreator;

import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

public class FormCreator {

	// Constantes
	private static final String FORMS_PATH 	= "form";
	private static final String FORM_PREFIX = "LIQ_";
	private static final String FORM_SUFFIX = "_Form.xsl";
	private static final String RESUME_SUFFIX = "_Resume.xsl";
	private static final String FINAL_SUFFIX = "_Final.xsl";
	private static final String RECEIPT_SUFFIX = "_Receipt.pdf";
	private static final String DETAIL_SUFFIX = "_Detail.xsl";
	private static final String TEMP_FILE_PREFIX = "JUST_";
	private static final String TEMP_FILE_SUFFIX = ".pdf";
	private static final String LOCALE_SEPARATOR = "_";
	
	private static final String DEFAULT_LOCALE_LANGUAGE = "es";
	
	
	// NODOS XML
	public static final String LIQUIDACION_XML_TAG = 	"LIQUIDACION";
	public static final String REFERENCIA_XML_TAG = 	"REFERENCIA";
	public static final String ID_ENTIDAD_XML_TAG = 	"ID_ENTIDAD";
	public static final String ID_TASA_XML_TAG = 		"ID_TASA";
	public static final String NOMBRE_TASA_XML_TAG = 	"NOMBRE_TASA";
	public static final String TIPO_XML_TAG = 			"TIPO";
	public static final String MODELO_XML_TAG = 		"MODELO";
	public static final String CAPTURA_XML_TAG = 		"CAPTURA";
	public static final String EJERCICIO_XML_TAG = 		"EJERCICIO";
	public static final String VENCIMIENTO_XML_TAG = 	"VENCIMIENTO";
	public static final String DISCRIMINANTE_XML_TAG = 	"VENCIMIENTO";
	public static final String REMESA_XML_TAG = 		"REMESA";
	public static final String NIF_XML_TAG = 			"NIF";
	public static final String IMPORTE_XML_TAG = 		"IMPORTE";
	public static final String NRC_XML_TAG = 			"NRC";
	public static final String ESTADO_XML_TAG = 		"ESTADO";
	public static final String APELLIDOS_NOMBRE_XML_TAG = "APELLIDOSNOMBRE";
	public static final String DATOS_ESPECIFICOS_XML_TAG = "DATOSESPECIFICOS";
	

	public static File crearJustificantePago(Tasa poTasa, String pcXMLDatos, HttpServletRequest request) throws Exception{
        File oFile = null;
        
        byte[] oBytes = FileManager.readBytesFromFile(obtenerRutaJustificante(poTasa, request));
        byte[] oPdfBytes = PDFReceiptCreator.createReceipt(oBytes, pcXMLDatos);
	        
        // creamos el archivo temporal
        oFile = File.createTempFile(TEMP_FILE_PREFIX, TEMP_FILE_SUFFIX);
        FileManager.writeBytesToFile(oFile.getAbsolutePath(), oPdfBytes);

        return oFile;		
	}

	
	public static String crearFinalPago(Tasa poTasa, String pcXMLDatos, HttpServletRequest request) throws Exception{
        String         text = "";
        
        text = XmlTransformer.transformXmlDocumentToHtmlStringTextUsingXslFile(
            									cargarDocXmlStr(pcXMLDatos), 
            									obtenerRutaFinal(poTasa, request));            
        return text;		
	}


	public static String crearResumenPago(Tasa poTasa, String pcXMLDatos, HttpServletRequest request) throws Exception{
        String         text = "";
        
        text = XmlTransformer.transformXmlDocumentToHtmlStringTextUsingXslFile(
            									cargarDocXmlStr(pcXMLDatos), 
            									obtenerRutaResumen(poTasa, request));            
        return text;		
	}

	public static String crearConsultaPago(Tasa poTasa, String pcXMLDatos, HttpServletRequest request) throws Exception{
        String         text = "";
        
        text = XmlTransformer.transformXmlDocumentToHtmlStringTextUsingXslFile(
            									cargarDocXmlStr(pcXMLDatos), 
            									obtenerRutaConsulta(poTasa, request));            
        return text;		
	}

	
	public static String crearFormularioLiquidacion(Tasa poTasa, String pcXMLDatos, HttpServletRequest request) throws Exception{
        String         text = "";        

        text = XmlTransformer.transformXmlDocumentToHtmlStringTextUsingXslFile(
            									cargarDocXmlStr(pcXMLDatos), 
            									obtenerRutaFormulario(	poTasa, 
            															request));
        return text;		
	}
	
	public static String obtenerXML(Liquidacion poLiquidacion, CertInfo poCertInfo){
	  XmlTextBuilder bdr;
	
	  bdr = new XmlTextBuilder();
	  
	  bdr.setStandardHeader();
	  bdr.addOpeningTag(LIQUIDACION_XML_TAG);
	  bdr.addSimpleElement(REFERENCIA_XML_TAG, poLiquidacion.getReferencia());
	  bdr.addSimpleElement(ID_ENTIDAD_XML_TAG, poLiquidacion.getIdEntidadEmisora());
	  bdr.addSimpleElement(ID_TASA_XML_TAG, poLiquidacion.getIdTasa());
	  bdr.addSimpleElement(NOMBRE_TASA_XML_TAG, poLiquidacion.getTasa().getNombre());
	  bdr.addSimpleElement(TIPO_XML_TAG, poLiquidacion.getTasa().getTipo());
	  bdr.addSimpleElement(MODELO_XML_TAG, poLiquidacion.getTasa().getModelo());
	  bdr.addSimpleElement(CAPTURA_XML_TAG, poLiquidacion.getTasa().getCaptura());
	  bdr.addSimpleElement(EJERCICIO_XML_TAG, poLiquidacion.getEjercicio());	  
	  bdr.addSimpleElement(VENCIMIENTO_XML_TAG, poLiquidacion.getVencimiento());
	  bdr.addSimpleElement(DISCRIMINANTE_XML_TAG, poLiquidacion.getDiscriminante());
	  bdr.addSimpleElement(REMESA_XML_TAG, poLiquidacion.getRemesa());
	  bdr.addSimpleElement(NIF_XML_TAG, poLiquidacion.getNif());
	  bdr.addSimpleElement(APELLIDOS_NOMBRE_XML_TAG, poCertInfo.getM_nombre(), true);
	  bdr.addSimpleElement(IMPORTE_XML_TAG, PagoElectronicoManagerHelper.obtenerImporteSalida(poLiquidacion.getImporte()));
	  bdr.addSimpleElement(NRC_XML_TAG, poLiquidacion.getNrc());
	  bdr.addSimpleElement(ESTADO_XML_TAG, poLiquidacion.getEstado());
	  bdr.addOpeningTag(DATOS_ESPECIFICOS_XML_TAG);
	  	bdr.addFragment(poLiquidacion.getTasa().getDatosEspecificos());
	  bdr.addClosingTag(DATOS_ESPECIFICOS_XML_TAG);
	  bdr.addClosingTag(LIQUIDACION_XML_TAG);
	  
	  return bdr.getText();
	}
	
	private static String obtenerRutaFormulario(Tasa poTasa, HttpServletRequest poRequest){
		StringBuffer sbRuta = new StringBuffer(poRequest.getSession().getServletContext().getRealPath(FORMS_PATH));		
		sbRuta.append(File.separator).append(FORM_PREFIX).append(poTasa.getIdEntidadEmisora()).append(poTasa.getCodigo());
		if(Tasa.TIPO_AL3.equals(poTasa.getTipo())){
			sbRuta.append(poTasa.getModelo());
		}
		// Identificamos los locales del usuario.
		//Enumeration listaLocales = poRequest.getLocales();
		Locale oSesionLocale = (Locale)poRequest.getSession().getAttribute("org.apache.struts.action.LOCALE");
		List listaTotalLocales = getListaLocales(oSesionLocale, poRequest.getLocales());
		
		Locale oAux = null;
		StringBuffer sbAux = null;
		File oFileAux = null;
		for(int i=0; i<listaTotalLocales.size(); i++) {
			sbAux = new StringBuffer(sbRuta.toString());
			oAux = (Locale) listaTotalLocales.get(i);
			sbAux.append(LOCALE_SEPARATOR).append(oAux.getLanguage().toLowerCase());
			sbAux.append(FORM_SUFFIX);
			oFileAux = new File(sbAux.toString());
			if(	(oFileAux.exists()) && (!oFileAux.isDirectory())){
				return sbAux.toString();
			}
		}
		
		// No hemos encontrado un formulario específico
		// para el lenguaje del usuario. Devolvemos el de por defecto
		sbAux.append(LOCALE_SEPARATOR).append(DEFAULT_LOCALE_LANGUAGE);		
		sbRuta.append(FORM_SUFFIX);
		return sbRuta.toString();
	}
	
	
	private static ArrayList getListaLocales(Locale oSesionLocale, Enumeration oRequestLocales){
		ArrayList locales = new ArrayList();
		if (oSesionLocale != null)
			locales.add(oSesionLocale);
		while(oRequestLocales.hasMoreElements())
			locales.add((Locale) oRequestLocales.nextElement());
		return locales;
	}
	
	private static String obtenerRutaResumen(Tasa poTasa, HttpServletRequest poRequest){
		StringBuffer sbRuta = new StringBuffer(poRequest.getSession().getServletContext().getRealPath(FORMS_PATH));
		sbRuta.append(File.separator).append(FORM_PREFIX).append(poTasa.getIdEntidadEmisora()).append(poTasa.getCodigo());
		if(Tasa.TIPO_AL3.equals(poTasa.getTipo())){
			sbRuta.append(poTasa.getModelo());
		}		
		// Identificamos los locales del usuario.
		//Enumeration listaLocales = poRequest.getLocales();
		Locale oSesionLocale = (Locale)poRequest.getSession().getAttribute("org.apache.struts.action.LOCALE");
		List listaTotalLocales = getListaLocales(oSesionLocale, poRequest.getLocales());
		
		Locale oAux = null;
		StringBuffer sbAux = null;
		File oFileAux = null;
		for(int i=0; i<listaTotalLocales.size(); i++) {
			sbAux = new StringBuffer(sbRuta.toString());
			oAux = (Locale) listaTotalLocales.get(i);
			sbAux.append(LOCALE_SEPARATOR).append(oAux.getLanguage().toLowerCase());
			sbAux.append(RESUME_SUFFIX);
			oFileAux = new File(sbAux.toString());
			if(	(oFileAux.exists()) && (!oFileAux.isDirectory())){
				return sbAux.toString();
			}
		}
		sbAux.append(LOCALE_SEPARATOR).append(DEFAULT_LOCALE_LANGUAGE);		
		sbRuta.append(RESUME_SUFFIX);
		return sbRuta.toString();
	}

	private static String obtenerRutaConsulta(Tasa poTasa, HttpServletRequest poRequest){
		StringBuffer sbRuta = new StringBuffer(poRequest.getSession().getServletContext().getRealPath(FORMS_PATH));
		sbRuta.append(File.separator).append(FORM_PREFIX).append(poTasa.getIdEntidadEmisora()).append(poTasa.getCodigo());
		if(Tasa.TIPO_AL3.equals(poTasa.getTipo())){
			sbRuta.append(poTasa.getModelo());
		}		
		// Identificamos los locales del usuario.
		//Enumeration listaLocales = poRequest.getLocales();
		Locale oSesionLocale = (Locale)poRequest.getSession().getAttribute("org.apache.struts.action.LOCALE");
		List listaTotalLocales = getListaLocales(oSesionLocale, poRequest.getLocales());
		
		Locale oAux = null;
		StringBuffer sbAux = null;
		File oFileAux = null;
		for(int i=0; i<listaTotalLocales.size(); i++) {
			sbAux = new StringBuffer(sbRuta.toString());
			oAux = (Locale) listaTotalLocales.get(i);
			sbAux.append(LOCALE_SEPARATOR).append(oAux.getLanguage().toLowerCase());
			sbAux.append(DETAIL_SUFFIX);
			oFileAux = new File(sbAux.toString());
			if(	(oFileAux.exists()) && (!oFileAux.isDirectory())
			){
				return sbAux.toString();
			}
		}
		sbAux.append(LOCALE_SEPARATOR).append(DEFAULT_LOCALE_LANGUAGE);		
		sbRuta.append(DETAIL_SUFFIX);
		return sbRuta.toString();
	}

	private static String obtenerRutaFinal(Tasa poTasa, HttpServletRequest poRequest){
		StringBuffer sbRuta = new StringBuffer(poRequest.getSession().getServletContext().getRealPath(FORMS_PATH));
		sbRuta.append(File.separator).append(FORM_PREFIX).append(poTasa.getIdEntidadEmisora()).append(poTasa.getCodigo());
		if(Tasa.TIPO_AL3.equals(poTasa.getTipo())){
			sbRuta.append(poTasa.getModelo());
		}		
		// Identificamos los locales del usuario.
		//Enumeration listaLocales = poRequest.getLocales();
		Locale oSesionLocale = (Locale)poRequest.getSession().getAttribute("org.apache.struts.action.LOCALE");
		List listaTotalLocales = getListaLocales(oSesionLocale, poRequest.getLocales());
		
		Locale oAux = null;
		StringBuffer sbAux = null;
		File oFileAux = null;
		for(int i=0; i<listaTotalLocales.size(); i++) {
			sbAux = new StringBuffer(sbRuta.toString());
			oAux = (Locale) listaTotalLocales.get(i);
			sbAux.append(LOCALE_SEPARATOR).append(oAux.getLanguage().toLowerCase());
			sbAux.append(FINAL_SUFFIX);
			oFileAux = new File(sbAux.toString());
			if(	(oFileAux.exists()) && (!oFileAux.isDirectory())
			){
				return sbAux.toString();
			}
		}		
		sbAux.append(LOCALE_SEPARATOR).append(DEFAULT_LOCALE_LANGUAGE);		
		sbRuta.append(FINAL_SUFFIX);
		return sbRuta.toString();
	}

	private static String obtenerRutaJustificante(Tasa poTasa, HttpServletRequest poRequest){
		StringBuffer sbRuta = new StringBuffer(poRequest.getSession().getServletContext().getRealPath(FORMS_PATH));
		sbRuta.append(File.separator).append(FORM_PREFIX).append(poTasa.getIdEntidadEmisora()).append(poTasa.getCodigo());
		if(Tasa.TIPO_AL3.equals(poTasa.getTipo())){
			sbRuta.append(poTasa.getModelo());
		}		
		// Identificamos los locales del usuario.
		//Enumeration listaLocales = poRequest.getLocales();
		Locale oSesionLocale = (Locale)poRequest.getSession().getAttribute("org.apache.struts.action.LOCALE");
		List listaTotalLocales = getListaLocales(oSesionLocale, poRequest.getLocales());
		
		Locale oAux = null;
		StringBuffer sbAux = null;
		File oFileAux = null;
		for(int i=0; i<listaTotalLocales.size(); i++) {
			sbAux = new StringBuffer(sbRuta.toString());
			oAux = (Locale) listaTotalLocales.get(i);
			sbAux.append(LOCALE_SEPARATOR).append(oAux.getLanguage().toLowerCase());
			sbAux.append(RECEIPT_SUFFIX);
			oFileAux = new File(sbAux.toString());
			if(	(oFileAux.exists()) && (!oFileAux.isDirectory())
			){
				return sbAux.toString();
			}
		}		
		sbAux.append(LOCALE_SEPARATOR).append(DEFAULT_LOCALE_LANGUAGE);
		sbRuta.append(RECEIPT_SUFFIX);
		return sbRuta.toString();
	}
	
	private static XmlDocument cargarDocXmlStr(String StrXml)
    throws Exception  {
        XmlDocument xmlDoc = new XmlDocument();
        
        xmlDoc.createFromStringText(StrXml);
        
        return(xmlDoc);
    }

}
