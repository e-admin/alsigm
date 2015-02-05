package ieci.tecdoc.sgm.pe.struts.receipt;

import ieci.tecdoc.sgm.base.base64.Base64Util;
import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.cripto.firma.ServicioFirmaDigital;
import ieci.tecdoc.sgm.core.services.tiempos.ServicioTiempos;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Calendar;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfDate;
import com.lowagie.text.pdf.PdfDictionary;
import com.lowagie.text.pdf.PdfName;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfSignature;
import com.lowagie.text.pdf.PdfSignatureAppearance;
import com.lowagie.text.pdf.PdfStamper;
import com.lowagie.text.pdf.PdfString;

public class ITextSignUtils {
	
	private static final Logger logger= Logger.getLogger(ITextSignUtils.class);
			
	public static byte[] signPDF(byte[] data) throws Exception {
		
      ByteArrayOutputStream output = new ByteArrayOutputStream();
	  PdfStamper stp = null;
	  PdfReader reader = null;
      
      ServicioFirmaDigital firmaDigital = LocalizadorServicios.getServicioFirmaDigital();
      ServicioTiempos servicioTiempos = LocalizadorServicios.getServicioTiempos();
      //CertificadoX509Info certInfo = firmaDigital.getcertInfo();
      
      final int SIZE = 16000;

      try {
          reader = new PdfReader(data);

          stp = PdfStamper.createSignature(reader, output, '\0');
          PdfSignatureAppearance sap = stp.getSignatureAppearance();
          
          sap.setVisibleSignature(new Rectangle(650, 80, 850, 150), 1, Configuracion.getProperty(Configuracion.PDF_SIG_FIELD_KEY));
          sap.setReason(Configuracion.getProperty(Configuracion.PDF_SIG_REASON_KEY));
          sap.setLocation(Configuracion.getProperty(Configuracion.PDF_SIG_LOCATION_KEY));
          sap.setLayer2Text(Configuracion.getProperty(Configuracion.PDF_SIG_AUX1_KEY));
          
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
	      System.arraycopy(firma, 0 , outc, 0, firma.length);
	      PdfDictionary dic2 = new PdfDictionary();
	      dic2.put(PdfName.CONTENTS, new PdfString(outc).setHexWriting(true));
	      sap.close(dic2);
      }
      catch(Exception exc)
      {
         logger.error("Error en la firma del PDF [sign][Exception]", exc.fillInStackTrace());
         throw exc;
      }
      finally 
      {
         //if (stp != null) stp.close();
         if (reader != null) reader.close();
      }
      
      return output.toByteArray();
	}
	
	private static byte[] streamToByteArray(InputStream stream) throws Exception {
		   ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
		   byte buffer1[] = new byte[8192];
		   int c = 0;
		   while ((c = stream.read(buffer1)) > 0) {
			   byteArray.write(buffer1, 0, c);
		   }
		   byteArray.flush();
		   return byteArray.toByteArray();
	   }

}
