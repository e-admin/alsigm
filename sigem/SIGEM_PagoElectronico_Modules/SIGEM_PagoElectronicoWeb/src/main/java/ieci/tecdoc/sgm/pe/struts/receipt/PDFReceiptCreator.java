package ieci.tecdoc.sgm.pe.struts.receipt;

import ieci.tecdoc.sgm.base.xml.core.XmlDocument;
import ieci.tecdoc.sgm.base.xml.core.XmlElement;

import java.io.ByteArrayOutputStream;

import org.apache.log4j.Logger;

import com.lowagie.text.pdf.AcroFields;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;

public class PDFReceiptCreator {
	
    public static byte[] signReceipt(byte[] data, String xmlData) throws Exception{
    	
		XmlDocument xmlDoc = new XmlDocument();
		XmlElement root = null;
		XmlElement tipoTasa = null;
		XmlElement modeloTasa = null;

		try {
			xmlDoc.createFromStringText(xmlData);
			root = xmlDoc.getRootElement();
			tipoTasa = root.getDescendantElement(Configuracion.getPaymentTypeXPathExpression());
			modeloTasa = root.getDescendantElement(Configuracion.getPaymentModelXPathExpression());
			ReceiptSigner oSigner = getReceiptSigner(tipoTasa.getValue(), modeloTasa.getValue());
			return oSigner.signReceipt(data);
		} catch (Exception e) {
			Logger.getLogger(PDFReceiptCreator.class).error(e);
			throw e;
		}
    }
    
	private static byte[] fillReceipt(byte[] data,
			String xmlData) throws Exception {
		PdfReader reader = null;
		PdfStamper stamp = null;
		AcroFields formFlds;
		ByteArrayOutputStream output = null;
		XmlDocument xmlDoc = new XmlDocument();
		XmlElement root = null;
		XmlElement tipoTasa = null;
		XmlElement modeloTasa = null;

		try {
			reader = new PdfReader(data);
			output = new ByteArrayOutputStream();

			stamp = new PdfStamper(reader, output);
			formFlds = stamp.getAcroFields();

			xmlDoc.createFromStringText(xmlData);
			root = xmlDoc.getRootElement();
			tipoTasa = root.getDescendantElement(Configuracion.getPaymentTypeXPathExpression());
			modeloTasa = root.getDescendantElement(Configuracion.getPaymentModelXPathExpression());
			ReceiptFiller oFiller = getReceiptFiller(tipoTasa.getValue(), modeloTasa.getValue());			
			oFiller.fillReceipt(formFlds, xmlDoc);
			

			stamp.setFormFlattening(true);
		} catch (Exception exc) {
			throw exc;
		} finally {
			if (stamp != null)
				stamp.close();
		}

		return output.toByteArray();
	}

	public static byte[] createReceipt(byte[] data,
			String xmlData) throws Exception {
		byte[] output;
		byte[] output1 = null;
		
		output1 = fillReceipt(data, xmlData);
		output = signReceipt(output1, xmlData);

		return output;
	}
	
	private static ReceiptFiller getReceiptFiller(String pcTipoLiquidacion, String pcModelo) throws Exception{
		String cClassName = Configuracion.getReceiptFillerImplementationClassName(pcTipoLiquidacion, pcModelo);
		if(cClassName != null){
			Object result = null;

			try {
				result =
					Thread
						.currentThread()
						.getContextClassLoader()
						.loadClass(cClassName)
						.newInstance();
			} catch (InstantiationException e) {
				Logger.getLogger(PDFReceiptCreator.class).error(e);
				throw e;
			} catch (IllegalAccessException e) {
				Logger.getLogger(PDFReceiptCreator.class).error(e);
				throw e;				
			} catch (ClassNotFoundException e) {
				Logger.getLogger(PDFReceiptCreator.class).error(e);
				throw e;				
			}
			return (ReceiptFiller)result;
		}
		return null;
	}

	private static ReceiptSigner getReceiptSigner(String pcTipoLiquidacion, String pcModelo) throws Exception{
		String cClassName = Configuracion.getReceiptSignerImplementationClassName(pcTipoLiquidacion, pcModelo);
		if(cClassName != null){
			Object result = null;

			try {
				result =
					Thread
						.currentThread()
						.getContextClassLoader()
						.loadClass(cClassName)
						.newInstance();
			} catch (InstantiationException e) {
				Logger.getLogger(PDFReceiptCreator.class).error(e);
				throw e;
			} catch (IllegalAccessException e) {
				Logger.getLogger(PDFReceiptCreator.class).error(e);
				throw e;				
			} catch (ClassNotFoundException e) {
				Logger.getLogger(PDFReceiptCreator.class).error(e);
				throw e;				
			}
			return (ReceiptSigner)result;
		}
		return null;
	}

	
}