package ieci.tecdoc.sgm.pe.struts.receipt;

import ieci.tecdoc.sgm.core.config.impl.spring.SigemConfigFilePathResolver;
import ieci.tecdoc.sgm.pe.struts.action.BuscarLiquidacionesAction;

import java.io.IOException;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;


public class Configuracion {
	
	/**
	 * Constantes
	 */
	private static final String CONFIG_DIR="SIGEM_PagoElectronicoWeb";
	private static final String PROPERTIES_FILE = "ReceiptCreator.properties";
	
	private static final String RECEIPT_FILLERS_PROPERTIES_PREFIX = "ieci.tecdoc.sgm.pe.struts.receipt.resources.receiptFiller.";
	private static final String RECEIPT_SIGNERS_PROPERTIES_PREFIX = "ieci.tecdoc.sgm.pe.struts.receipt.resources.receiptSigner.";
	private static final String PAYMENT_TYPE_XPATH_EXPRESSION = "ieci.tecdoc.sgm.pe.struts.receipt.resources.paymentType.xpath";
	private static final String PAYMENT_MODEL_XPATH_EXPRESSION = "ieci.tecdoc.sgm.pe.struts.receipt.resources.paymentModel.xpath";
	
		
	public static final String PDF_SIG_REASON_KEY = "ieci.tecdoc.sgm.pe.struts.receipt.resources.pdf.sign.reason";
	public static final String PDF_SIG_LOCATION_KEY = "ieci.tecdoc.sgm.pe.struts.receipt.resources.pdf.sign.location";
	public static final String PDF_SIG_AUX1_KEY = "ieci.tecdoc.sgm.pe.struts.receipt.resources.pdf.sign.aux1";
	public static final String PDF_SIG_FIELD_KEY = "ieci.tecdoc.sgm.pe.struts.receipt.resources.pdf.sign.field";
	public static final String PDF_SIG_KEYSTORE_KEY = "ieci.tecdoc.sgm.pe.struts.receipt.resources.pdf.sign.keystore";
	public static final String PDF_SIG_KEYSTORE_PASS_KEY = "ieci.tecdoc.sgm.pe.struts.receipt.resources.pdf.sign.keystore.password";
	public static final String PDF_SIG_KEYSTORE_ALIAS_KEY = "ieci.tecdoc.sgm.pe.struts.receipt.resources.pdf.sign.keystore.alias";
	public static final String DEFAULT="DEFAULT";
	
	
	private static Properties propiedades = new Properties(); 
		
	static{
		try {
			SigemConfigFilePathResolver pathResolver = SigemConfigFilePathResolver.getInstance();
			propiedades=pathResolver.loadProperties(PROPERTIES_FILE, CONFIG_DIR);
			//propiedades.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(PROPERTIES_FILE));
		} catch (Exception e) {
			Logger.getLogger(Configuracion.class).error(e);
		}
	}
	
	public static String getReceiptFillerImplementationClassName(String pcType, String pcModel){
		String cReturn = null;
		if(pcType != null){
			StringBuffer sbKey = new StringBuffer(RECEIPT_FILLERS_PROPERTIES_PREFIX);
			sbKey.append(pcType.toUpperCase());
			if( (pcModel != null) &&(!"".equals(pcModel))){
				sbKey.append(".").append(pcModel.toUpperCase());
			}
			cReturn = propiedades.getProperty(sbKey.toString());
			if(StringUtils.isEmpty(cReturn)){
				sbKey = new StringBuffer(RECEIPT_FILLERS_PROPERTIES_PREFIX);
				sbKey.append(DEFAULT);
				cReturn = propiedades.getProperty(sbKey.toString());
			}
		}
		return cReturn;
	}

	public static String getReceiptSignerImplementationClassName(String pcType, String pcModel){
		String cReturn = null;
		if(pcType != null){
			StringBuffer sbKey = new StringBuffer(RECEIPT_SIGNERS_PROPERTIES_PREFIX);
			sbKey.append(pcType.toUpperCase());
			if( (pcModel != null) &&(!"".equals(pcModel))){
				sbKey.append(".").append(pcModel.toUpperCase());
			}
			cReturn = propiedades.getProperty(sbKey.toString());
			if(StringUtils.isEmpty(cReturn)){
				sbKey = new StringBuffer(RECEIPT_SIGNERS_PROPERTIES_PREFIX);
				sbKey.append(DEFAULT);
				cReturn = propiedades.getProperty(sbKey.toString());
			}
		}
		return cReturn;
	}

	
	public static String getPaymentTypeXPathExpression(){
		return propiedades.getProperty(PAYMENT_TYPE_XPATH_EXPRESSION);
	}
	
	public static String getPaymentModelXPathExpression(){
		return propiedades.getProperty(PAYMENT_MODEL_XPATH_EXPRESSION);
	}
	
	public static String getProperty(String pcKey){
		return propiedades.getProperty(pcKey);
	}
}
