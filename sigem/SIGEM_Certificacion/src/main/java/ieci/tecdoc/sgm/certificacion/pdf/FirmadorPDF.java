package ieci.tecdoc.sgm.certificacion.pdf;

/**
 * Clase que firma mediante un certificado un fichero PDF
 * @author José Antonio Nogales
 */
import ieci.tecdoc.sgm.base.base64.Base64Util;
import ieci.tecdoc.sgm.certificacion.bean.CertificadoFirma;
import ieci.tecdoc.sgm.certificacion.exception.CertificacionException;
import ieci.tecdoc.sgm.certificacion.exception.CodigosErrorCertificacionException;
import ieci.tecdoc.sgm.certificacion.util.Defs;
import ieci.tecdoc.sgm.certificacion.util.Utilidades;
import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.cripto.firma.CertificadoX509Info;
import ieci.tecdoc.sgm.core.services.cripto.firma.ServicioFirmaDigital;

import java.awt.Color;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfDate;
import com.lowagie.text.pdf.PdfDictionary;
import com.lowagie.text.pdf.PdfName;
import com.lowagie.text.pdf.PdfPKCS7;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfSignatureAppearance;
import com.lowagie.text.pdf.PdfStamper;
import com.lowagie.text.pdf.PdfString;

public class FirmadorPDF extends Firmador {
	private static final Logger logger = Logger.getLogger(FirmadorPDF.class);

	/**
	 * Método de inicialización de la firma
	 * @param data Datos a firmar
	 * @param codigoEntidad Código de la entidad de origen
	 * @param imagen Imagen que se va a incluir en la firma del PDF
	 * @return Datos firmados
	 * @throws CertificacionException En caso de producirse algún error
	 */
	public byte[] signReceipt(byte[] data, String descripcionEntidad, String imagen) throws CertificacionException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        try {
            ServicioFirmaDigital firmaDigital = LocalizadorServicios.getServicioFirmaDigital();
            CertificadoX509Info certInfo = firmaDigital.getcertInfo();

            PdfReader reader = new PdfReader(data);

            PdfStamper stp = PdfStamper.createSignature(reader, output, '\0');
            PdfSignatureAppearance sap = stp.getSignatureAppearance();
            sap.setCrypto(certInfo.getPrivateKey(), certInfo.getCertificates(),
                    null, PdfSignatureAppearance.SELF_SIGNED);
            Rectangle pagina = reader.getPageSize(reader.getNumberOfPages());
            Rectangle cuadroFirma = new Rectangle(pagina.getWidth() / 2 - 160, 20, pagina.getWidth() / 2 + 160, 60);
            sap.setVisibleSignature(cuadroFirma, reader.getNumberOfPages(), "NOMBRE");
            sap.setReason("Firma Certificación");
            sap.setLocation("España");
            sap.setLayer2Text("Firmado por: " + descripcionEntidad);
            sap.setLayer2Font(fuenteNormal10);

            if (imagen != null && !imagen.equals("") && !imagen.endsWith(Defs.BARRA) && !imagen.endsWith(Defs.NULO)){
            	try {
            		byte[] imagenFirma = Utilidades.leerFicheroRecursosArray(imagen);
            		sap.setImage(Image.getInstance(imagenFirma));
            	}catch(Exception ex) {
            		logger.error("La imagen de firma '" + imagen + "' no se ha encontrado");
            		throw ex;
            	}
            }

            sap.setExternalDigest(new byte[128], null, "RSA");
            sap.preClose();
            PdfPKCS7 sig = sap.getSigStandard().getSigner();

            InputStream inp = sap.getRangeStream();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte buf2[] = new byte[8192];
            int n;
            while ((n = inp.read(buf2)) > 0) {
                    out.write(buf2, 0, n);
            }
            byte[] base= Base64Util.encode(out.toByteArray()).getBytes();

            byte[] firma = Base64Util.decode(firmaDigital.firmar(base));
            sig.setExternalDigest(firma, null, "RSA");
            PdfDictionary dic = new PdfDictionary();
            dic.put(PdfName.CONTENTS, new PdfString(sig.getEncodedPKCS1()).setHexWriting(true));
            sap.close(dic);
        }catch(CertificacionException ce){
        	throw ce;
        }catch(Exception e){
        	logger.error("Se ha producido un error al firmar el PDF", e);
           	throw new CertificacionException(
              	CodigosErrorCertificacionException.ERROR_FIRMA_PDF,
               	e.getMessage(),
               	e.getCause());
        }

        return output.toByteArray();
	}

	/**
	 * Método que obtiene el contenido de un InputStream
	 * @param stream Datos a convertir
	 * @return Datos convertidos
	 * @throws CertificacionException
	 */
    private byte[] getBytes(InputStream stream) throws CertificacionException {
        try {
        	byte[] bytes = null;
	        byte[] buffer;
	        BufferedInputStream input = new BufferedInputStream(stream);
	        ByteArrayOutputStream output = new ByteArrayOutputStream();
	        int chunkSize = 10240;

	        buffer = new byte[chunkSize];

	       int c;
	       while ((c = input.read(buffer)) != -1) {
	              output.write(buffer, 0, c);
	              output.flush();
	       }
	       bytes = output.toByteArray();
	       output.close();

	        return bytes;
        } catch(Exception e){
        	logger.error("Se ha producido un error al firmar el PDF", e);
        	throw new CertificacionException(
        		CodigosErrorCertificacionException.ERROR_FIRMA_PDF,
        		e.getMessage(),
        		e.getCause());
        }
     }

    /**
     * Método para recuperar la ruta de los recursos
     * @return Ruta de los recursos
     */
    public String getRutaRecursos() {
		return rutaRecursos;
	}

    /**
     * Método para establecer la ruta de los recursos
     * @param rutaRecursos Ruta de los recursos
     */
	public void setRutaRecursos(String rutaRecursos) {
		this.rutaRecursos = rutaRecursos;
	}

    protected String rutaRecursos = "";
    private static final Font fuenteNormal10 = new Font(Font.TIMES_ROMAN, 10, Font.NORMAL);
}
