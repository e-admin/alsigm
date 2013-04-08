package es.ieci.plusvalias.sigem.receipt;

import ieci.tecdoc.sgm.base.xml.core.XmlDocument;
import ieci.tecdoc.sgm.base.xml.core.XmlElement;

import java.io.ByteArrayOutputStream;

import com.lowagie.text.pdf.AcroFields;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;

public class PDFReceiptCreator
{
	public static byte[] signReceipt(KeyStoreData keystoreData, String reason, String field, String location, String text, byte[] data, String xmlData) throws Exception
	{
		XmlDocument xmlDoc = new XmlDocument();
		XmlElement root = null;
		XmlElement tipoTasa = null;
		XmlElement modeloTasa = null;

		try
		{
			xmlDoc.createFromStringText(xmlData);
			root = xmlDoc.getRootElement();
			tipoTasa = root.getDescendantElement("LIQUIDACION/TASA/TIPO_TASA");
			modeloTasa = root.getDescendantElement("LIQUIDACION/TASA/MODELO_TASA");
			ReceiptSigner oSigner = new ReceiptSigner(keystoreData);
			
			return oSigner.signReceipt(reason, field, location, text, data);			
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
	}

	private static byte[] fillReceipt(byte[] data, String xmlData) throws Exception
	{
		PdfReader reader = null;
		PdfStamper stamp = null;
		AcroFields formFlds;
		ByteArrayOutputStream output = null;
		XmlDocument xmlDoc = new XmlDocument();
		XmlElement root = null;
		XmlElement tipoTasa = null;
		XmlElement modeloTasa = null;

		try
		{
			reader = new PdfReader(data);
			output = new ByteArrayOutputStream();

			stamp = new PdfStamper(reader, output);
			formFlds = stamp.getAcroFields();

			xmlDoc.createFromStringText(xmlData);
			root = xmlDoc.getRootElement();
			tipoTasa = root.getDescendantElement("LIQUIDACION/TASA/TIPO_TASA");
			modeloTasa = root.getDescendantElement("LIQUIDACION/TASA/MODELO_TASA");
			ReceiptFiller oFiller = new ReceiptFillerAL2();
			oFiller.fillReceipt(formFlds, xmlDoc);

			stamp.setFormFlattening(true);
		}
		catch (Exception exc)
		{
			throw exc;
		}
		finally
		{
			if (stamp != null)
				stamp.close();
		}

		return output.toByteArray();
	}

	public static byte[] createReceipt(KeyStoreData keystoreData, String reason, String field, String location, String text, byte[] data, String xmlData) throws Exception
	{
		byte[] output;
		byte[] output1 = null;

		output1 = fillReceipt(data, xmlData);
		output = signReceipt(keystoreData, reason, field, location, text, output1, xmlData);

		return output;
	}
}