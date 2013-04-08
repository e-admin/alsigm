package ieci.tecdoc.sgm.cripto.validacion.impl.bouncycastle;


import ieci.tecdoc.sgm.core.config.impl.spring.MultiEntityContextHolder;
import ieci.tecdoc.sgm.core.services.cripto.validacion.CriptoValidacionException;
import ieci.tecdoc.sgm.core.services.cripto.validacion.InfoCertificado;
import ieci.tecdoc.sgm.core.services.cripto.validacion.ResultadoValidacion;
import ieci.tecdoc.sgm.core.services.cripto.validacion.ServicioCriptoValidacion;
import ieci.tecdoc.sgm.cripto.validacion.impl.afirma.config.AFirmaValidacionConfiguration;
import ieci.tecdoc.sgm.cripto.validacion.impl.bouncycastle.config.ValidacionBCConfiguration;
import ieci.tecdoc.sgm.cripto.validacion.impl.config.ConfigValidacionLoader;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.cert.Certificate;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateFactory;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;



public class ValidationManagerBCImpl implements ServicioCriptoValidacion{

    static{
    	java.security.Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
    }

    private static org.apache.log4j.Logger log=org.apache.log4j.Logger.getLogger(ValidationManagerBCImpl.class);
    private Map configuracionesPorEntidad = new HashMap();
    final static String CADENA="C=ES,O=FNMT,OU=FNMT Clase 2 CA";
    final static String CN="CN=NOMBRE ";
    final static String CEDX="CN=CEX TD-WF";
    final static String NIF_="NIF ";

    final public static String PROVIDER =	"BC";
    final public static String ALGORITHM =	"SHA1";
//    
//    // configurable con spring
//    private String provider = PROVIDER;
//    private String algorithm = ALGORITHM;

//    public String getAlgorithm() {
//		return algorithm;
//	}
//
//
//	public void setAlgorithm(String algorithm) {
//		this.algorithm = algorithm;
//	}
//
//
//	public String getProvider() {
//		return provider;
//	}
//
//
//	public void setProvider(String provider) {
//		this.provider = provider;
//	}


	public String createHash(String psBase64Document) throws CriptoValidacionException{
    	String b64 = null;
    	ValidacionBCConfiguration config = loadConfig();
    	try{
    		BASE64Decoder oDecoder = new BASE64Decoder();
    		byte[]res = crearResumenReal(config.getAlgorithm(), oDecoder.decodeBuffer(psBase64Document));
    	    BASE64Encoder encoder=new BASE64Encoder();
    	    b64 = encoder.encodeBuffer(res);
    	} catch (Throwable e) {
    	    log.error("Error al crear hash de documento [createHash] [Throwable]", e.fillInStackTrace());  
    	    throw new CriptoValidacionException(CriptoValidacionException.EXC_HASH_GEN, e.getMessage(), e);
        }    

    	return b64;
    }

	
    public boolean validateHash(String psBase64Document, String psB64Hash) throws CriptoValidacionException {
    	try {
    		ValidacionBCConfiguration config = loadConfig();
    		BASE64Decoder oDecoder = new BASE64Decoder();
    		String cParamHash = new String(oDecoder.decodeBuffer(psB64Hash));
    	    String resumenDoc = new String(crearResumenReal(config.getAlgorithm(), oDecoder.decodeBuffer(psBase64Document)));
    	    if(resumenDoc.equals(cParamHash))
    	    	return true;
    	    else
    	    	return false;
    	} catch (Throwable e) {
    		log.error("Error al validar hash de documento [validateHash] [Throwable]", e.fillInStackTrace());      
    	    throw new CriptoValidacionException(CriptoValidacionException.EXC_HASH_VALIDATION, e.getMessage(), e);    	    
        }
    }
	
    public ResultadoValidacion validateCertificate(String psB64Certificate) throws CriptoValidacionException {
    	ValidacionBCConfiguration config = loadConfig();
    	InfoCertificado infoCertificado = null;
    	ResultadoValidacion oResultado = new ResultadoValidacion();
    	try {
    	    
    	    BASE64Decoder decoder=new BASE64Decoder();
    	    byte[]b=decoder.decodeBuffer(psB64Certificate);
    	    
    	    java.io.InputStream is=new java.io.ByteArrayInputStream(b);
    	    java.io.BufferedInputStream bis=new java.io.BufferedInputStream(is);

    	    CertificateFactory cf = CertificateFactory.getInstance("X.509");
    	    java.util.Collection c=cf.generateCertificates(bis);

    	    if(c.isEmpty())
    		throw new Exception("no hay certificados");
    	    
    	    Certificate certificados[]=(Certificate[])c.toArray(new Certificate[c.size()]);

    	    X509Certificate cert=(X509Certificate)certificados[0];

    	    cert.checkValidity();
    	    ArrayList certReaders = new ArrayList();
    	    certReaders.add(new DNIEReader());
    	    certReaders.add(new FnmtCertReader());
    	    certReaders.add(new CEXCertReader());
    	    certReaders.add(new CamerFirmaReader());
    	    for (Iterator itCertReaders = certReaders.iterator(); itCertReaders.hasNext();) {
				IReaderCert aReader = (IReaderCert) itCertReaders.next();
				if (aReader.isTypeOf(cert)){
					infoCertificado = aReader.getInfo(cert);
					break;
				}
			}


    	    oResultado.setResultadoValidacion(ResultadoValidacion.VALIDACION_OK);
    	    oResultado.setCertificado(infoCertificado);

    	} catch(CertificateExpiredException e){
    		log.error("Error al validar certificado [validateCertificate] [CertificateExpiredException]", e.fillInStackTrace());
    		oResultado.setResultadoValidacion(ResultadoValidacion.VALIDACION_ERROR);
    		oResultado.setMensajeValidacion("El certificado ha expirado.");
    		return oResultado;
    	} catch(CertificateNotYetValidException e){ 
    		log.error("Error al validar certificado [validateCertificate] [CertificateNotYetValidException]", e.fillInStackTrace());
    		oResultado.setResultadoValidacion(ResultadoValidacion.VALIDACION_ERROR);
       		oResultado.setMensajeValidacion("El certificado no ha sido validado todavía.");
       		return oResultado;
    	} catch (Throwable e) {
    		log.error("Error al validar certificado [validateCertificate] [Throwable]", e.fillInStackTrace());
    	    throw new CriptoValidacionException(CriptoValidacionException.EXC_CERT_VALIDATION, e.getMessage(), e);
        }    
    	return oResultado;
    }

    
	private byte[] crearResumenReal(String tipo, byte[] pbaDocument) throws NoSuchAlgorithmException, NoSuchProviderException, CriptoValidacionException {
		ValidacionBCConfiguration config = loadConfig();
		if(tipo==null || "".equals(tipo))
		    tipo=config.getAlgorithm();
	
		MessageDigest md=MessageDigest.getInstance(tipo, config.getProvider());
		md.update(pbaDocument);
		return md.digest();
	}
	public ValidacionBCConfiguration loadConfig()  throws CriptoValidacionException {
		String idEntidad = MultiEntityContextHolder.getEntity();
		ValidacionBCConfiguration config = (ValidacionBCConfiguration)configuracionesPorEntidad.get(idEntidad);
		if(config==null)
		{
			ConfigValidacionLoader configLoader = new ConfigValidacionLoader();
			config = configLoader.loadBCConfiguration(idEntidad);
			configuracionesPorEntidad.put(idEntidad, config);
		}
	
		return config;
	}
}
