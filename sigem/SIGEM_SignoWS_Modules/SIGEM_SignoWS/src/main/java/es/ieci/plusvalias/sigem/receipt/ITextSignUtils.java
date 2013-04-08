package es.ieci.plusvalias.sigem.receipt;

import ieci.tecdoc.sgm.base.base64.Base64Util;
import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.cripto.firma.ServicioFirmaDigital;
import ieci.tecdoc.sgm.core.services.tiempos.ServicioTiempos;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Calendar;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.springframework.ws.soap.security.WsSecuritySecurementException;
import org.springframework.ws.soap.security.wss4j.Wss4jSecuritySecurementException;

import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfDate;
import com.lowagie.text.pdf.PdfDictionary;
import com.lowagie.text.pdf.PdfName;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfSignature;
import com.lowagie.text.pdf.PdfSignatureAppearance;
import com.lowagie.text.pdf.PdfStamper;
import com.lowagie.text.pdf.PdfString;

public class ITextSignUtils
{
	private static final Logger logger = Logger.getLogger(ITextSignUtils.class);

	public static byte[] signPDF(String reason, String field, String location, String text, byte[] data) throws Exception
	{
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		PdfStamper stp = null;
		PdfReader reader = null;

		ServicioFirmaDigital firmaDigital = LocalizadorServicios.getServicioFirmaDigital();
		ServicioTiempos servicioTiempos = LocalizadorServicios.getServicioTiempos();

		final int SIZE = 16000;

		try
		{
			reader = new PdfReader(data);

			stp = PdfStamper.createSignature(reader, output, '\0');
			PdfSignatureAppearance sap = stp.getSignatureAppearance();

			sap.setVisibleSignature(new Rectangle(100, 100, 200, 200), 1, field);
			sap.setReason(reason);
			sap.setLocation(location);
			sap.setLayer2Text(text);

			Calendar signDate = Calendar.getInstance();
			signDate.setTime(servicioTiempos.getCurrentDate());
			sap.setSignDate(signDate);

			PdfSignature dic = new PdfSignature(PdfName.ADOBE_PPKMS, PdfName.ADBE_PKCS7_DETACHED);

			if (sap.getReason() != null)
				dic.setReason(sap.getReason());
			if (sap.getLocation() != null)
				dic.setLocation(sap.getLocation());

			dic.setDate(new PdfDate(sap.getSignDate()));

			sap.setCryptoDictionary(dic);

			HashMap exc = new HashMap();
			exc.put(PdfName.CONTENTS, new Integer(SIZE * 2 + 2));

			sap.preClose(exc);

			byte[] base = streamToByteArray(sap.getRangeStream());

			byte[] firma = Base64Util.decode(firmaDigital.firmar(base));

			byte[] outc = new byte[SIZE];
			System.arraycopy(firma, 0, outc, 0, firma.length);
			PdfDictionary dic2 = new PdfDictionary();
			dic2.put(PdfName.CONTENTS, new PdfString(outc).setHexWriting(true));
			sap.close(dic2);
		}
		catch (Exception exc)
		{
			logger.error("Error en la firma del PDF [sign][Exception]", exc.fillInStackTrace());
			throw exc;
		}
		finally
		{
			// if (stp != null) stp.close();
			if (reader != null)
				reader.close();
		}

		return output.toByteArray();
	}
	
	public static byte[] firmaPDF(KeyStoreData keyStoreData, String reason, String field, String location, String text, byte[] data)
		throws Exception
	{		
		KeyStore keyStore = KeyStore.getInstance(keyStoreData.getKeystoreType());
		keyStore.load(new FileInputStream(keyStoreData.getKeystorePath()), keyStoreData.getKeystorePassword().toCharArray());

		// Obtencion del certificado para firmar.
		X509Certificate certificate = getCertificate(keyStore, keyStoreData.getKeyAlias());

		// Obtención de la clave privada asociada al certificado
		PrivateKey privateKey;
		
		privateKey = ((KeyStore.PrivateKeyEntry)keyStore.getEntry(keyStoreData.getKeyAlias(),
				new KeyStore.PasswordProtection(keyStoreData.getKeyAliasPassword().toCharArray()))).getPrivateKey();		
		
		Certificate[] chain = keyStore.getCertificateChain(keyStoreData.getKeyAlias());
		PdfReader reader = new PdfReader(data);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PdfStamper stp = PdfStamper.createSignature(reader, baos, '\0');
		PdfSignatureAppearance sap = stp.getSignatureAppearance();
		sap.setCertificationLevel(PdfSignatureAppearance.CERTIFIED_NO_CHANGES_ALLOWED);
		sap.setCrypto(privateKey, chain, null, PdfSignatureAppearance.WINCER_SIGNED);
		sap.setReason(reason);
		sap.setLocation(location);
		// comment next line to have an invisible signature
		sap.setVisibleSignature(new Rectangle(670, 80, 790, 150), 1, field);
		stp.close();
		
		return baos.toByteArray();
	}

	private static byte[] streamToByteArray(InputStream stream) throws Exception
	{
		ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
		byte buffer1[] = new byte[8192];
		int c = 0;
		
		while ((c = stream.read(buffer1)) > 0)
		{
			byteArray.write(buffer1, 0, c);
		}
		
		byteArray.flush();
		
		return byteArray.toByteArray();
	}
	
	// Recupera del almacén de certificados, el certificado con el alias indicado
	private static X509Certificate getCertificate(KeyStore keystore, String certificateAlias) throws WsSecuritySecurementException
	{
		X509Certificate certificate = null;
		
		try
		{
			certificate = (X509Certificate)keystore.getCertificate(certificateAlias);			
			
		}
		catch (KeyStoreException e)
		{
			throw new Wss4jSecuritySecurementException(e.getMessage(), e);
		}
		
		return certificate;
	}
}
