package ieci.tecdoc.sgm.certificacion.pdf;

import ieci.tecdoc.sgm.certificacion.bean.CertificadoFirma;

import java.io.ByteArrayOutputStream;
import java.security.PrivateKey;
import java.security.cert.CertStore;
import java.security.cert.X509Certificate;

import org.bouncycastle.cms.CMSProcessable;
import org.bouncycastle.cms.CMSProcessableByteArray;
import org.bouncycastle.cms.CMSSignedData;
import org.bouncycastle.cms.CMSSignedDataGenerator;

/**
 * Clase que firma mediante un certificado un array de datos
 * @author José Antonio Nogales
  */
public abstract class Firmador {

		/**
		 * Método abstracto de inicialización de la firma
		 * @param data Datos a firmar
		 * @param codigoEntidad Código de la entidad de origen
		 * @param imagen Imagen que se va a incluir en la firma del PDF
		 * @return Datos firmados
		 * @throws Exception En caso de producirse algún error
		 */
	 	public abstract byte[] signReceipt(byte[] data, String codigoEntidad, String imagen) throws Exception;
	 
	 	/**
	 	 * Método que firma un conjunto de datos
	 	 * @param data Datos a firmar
	 	 * @param certificado Datos del certificado a usar para firmar
	 	 * @return Datos firmados
	 	 * @throws Exception En caso de producirse algún error
	 	 */
	    protected ByteArrayOutputStream sign(byte[] data, CertificadoFirma certificado) throws Exception
	    {
	       return sign(data, certificado.getCertificateStore(), 
	                certificado.getCertificado(), 
	                certificado.getPrivateKey());
	    }

	    /**
	     * Método que firma un conjunto de datos
	     * @param data Datos a firmar
	     * @param certStore Almacen del certificado
	     * @param cert Certificado
	     * @param key Clave privada
	     * @return Datos firmados
	     * @throws Exception En caso de producirse algún error
	     */
	    protected ByteArrayOutputStream sign(byte[] data, CertStore certStore, X509Certificate cert, PrivateKey key) throws Exception
	    {
	       CMSSignedDataGenerator signGen = new CMSSignedDataGenerator();
	       CMSSignedData signedData; 
	       CMSProcessable content = new CMSProcessableByteArray(data);
	       ByteArrayOutputStream output = new ByteArrayOutputStream();
	       
	       signGen.addSigner(key, cert, CMSSignedDataGenerator.DIGEST_SHA1);
	       signGen.addCertificatesAndCRLs(certStore);

	       signedData = signGen.generate(content, "BC");
	       output.write(signedData.getEncoded());

	       return output;
	    }
}
