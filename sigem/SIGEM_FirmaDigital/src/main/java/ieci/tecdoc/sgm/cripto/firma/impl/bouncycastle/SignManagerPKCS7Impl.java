package ieci.tecdoc.sgm.cripto.firma.impl.bouncycastle;

import ieci.tecdoc.sgm.core.base64.Base64;
import ieci.tecdoc.sgm.core.config.impl.spring.MultiEntityContextHolder;
import ieci.tecdoc.sgm.core.services.cripto.firma.CertificadoX509Info;
import ieci.tecdoc.sgm.core.services.cripto.firma.FirmaDigitalException;
import ieci.tecdoc.sgm.core.services.cripto.firma.ResultadoValidacionFirma;
import ieci.tecdoc.sgm.core.services.cripto.firma.ServicioFirmaDigital;
import ieci.tecdoc.sgm.cripto.firma.impl.bouncycastle.config.FirmaPKCS7Config;
import ieci.tecdoc.sgm.cripto.firma.impl.config.ConfigLoader;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.Serializable;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.Security;
import java.security.cert.CertStore;
import java.security.cert.CollectionCertStoreParameters;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;
import org.bouncycastle.cms.CMSProcessable;
import org.bouncycastle.cms.CMSProcessableByteArray;
import org.bouncycastle.cms.CMSSignedData;
import org.bouncycastle.cms.CMSSignedDataGenerator;
import org.bouncycastle.cms.SignerInformation;
import org.bouncycastle.cms.SignerInformationStore;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import sun.misc.BASE64Decoder;

public class SignManagerPKCS7Impl implements ServicioFirmaDigital, Serializable{

	private static final long serialVersionUID = 1L;
	final static String CADENA="C=ES,O=FNMT,OU=FNMT Clase 2 CA";
	final static String CN_NOMBRE="CN=NOMBRE ";
	final static String NIF="NIF ";

	private Map configuracionesPorEntidad = new HashMap();

	private String provider = Constants.PROVIDER;

	private static Logger log = Logger.getLogger(SignManagerPKCS7Impl.class);

	static{
		java.security.Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
	}

	public String firmar(byte[] pbaB64Source) throws FirmaDigitalException {
		/*String b64sign = null;
		try{
			PrivateKey privKey=(PrivateKey)(getKeyStoreData().getKey(getAlias(), getPassword().toCharArray()));
			Signature sign = Signature.getInstance("SHA1withRSA");
			sign.initSign(privKey);
			sign.update(Base64Util.decode(new String(pbaB64Source)));
			b64sign = Base64.encodeBytes(sign.sign());
		}catch(FirmaDigitalException fde){
			log.error("Error al firmar [firmar][FirmaDigitalException]", fde.fillInStackTrace());
			throw fde;
		}catch(Exception e){
			log.error("Error al firmar [firmar][Exception]", e.fillInStackTrace());
			throw new FirmaDigitalException(FirmaDigitalException.EXC_SIGN_EXCEPTION, e.getMessage(), e);
		}
		return b64sign;*/
		
		FirmaPKCS7Config config = loadConfig();

		   String b64sign = null;
	       Security.addProvider(new BouncyCastleProvider());

	       KeyStore ks = null;
	       PrivateKey priv = null;
	       java.security.cert.Certificate storecert = null;
	       java.security.cert.Certificate[] certChain = null;
	       ArrayList certList = new ArrayList();
	       CertStore certs = null;

	       try {
	           ks = KeyStore.getInstance(config.getKeyStoreType());
	           ks.load(new FileInputStream(config.getKeyStore()), config.getPassword().toCharArray());

	           certChain = ks.getCertificateChain(config.getAlias());
	           for (int i = 0; i < certChain.length; i++)
	               certList.add(certChain[i]);
	           certs = CertStore.getInstance("Collection", new CollectionCertStoreParameters(certList), config.getKeyStoreProvider());

	           priv = (PrivateKey) (ks.getKey(config.getAlias(), config.getPassword().toCharArray()));

	           storecert = ks.getCertificate(config.getAlias());
	       } catch (Exception exc) {
	    	   log.error("Error al firmar [firmar][Exception]", exc.fillInStackTrace());
	           throw new FirmaDigitalException(FirmaDigitalException.EXC_CERTIFICATE_EXCEPTION);
	       }

	       byte[] contentbytes = pbaB64Source;

	       try {
	           CMSSignedDataGenerator signGen = new CMSSignedDataGenerator();
	           signGen.addSigner(priv, (X509Certificate) storecert, CMSSignedDataGenerator.DIGEST_SHA1);
	           signGen.addCertificatesAndCRLs(certs);
	           CMSProcessable content = new CMSProcessableByteArray(contentbytes);
	           CMSSignedData signedData = signGen.generate(content, config.getKeyStoreProvider());
	           byte[] signeddata = signedData.getEncoded();
	           b64sign = Base64.encodeBytes(signeddata);
	       } catch (Exception ex) {
	    	   log.error("Error al firmar [firmar][Exception]", ex.fillInStackTrace());
	    	   throw new FirmaDigitalException(FirmaDigitalException.EXC_SIGN_EXCEPTION);
	       }
	       return b64sign;
		
	}



	public CertificadoX509Info getcertInfo() throws FirmaDigitalException {
		
		FirmaPKCS7Config config = loadConfig();
		
		Security.addProvider(new BouncyCastleProvider());
		KeyStore keystore = null;
		CertificadoX509Info certInfo = new CertificadoX509Info();
		ArrayList certList = new ArrayList();
		java.security.cert.Certificate[] certChain = null;
		try{
			keystore = KeyStore.getInstance(config.getKeyStoreType());
			keystore.load(new FileInputStream(config.getKeyStore()), config.getPassword().toCharArray());
			certChain = keystore.getCertificateChain(config.getAlias());
			for (int i = 0; i < certChain.length;i++)
				certList.add(certChain[i]);

			certInfo.setCert((X509Certificate)keystore.getCertificate(config.getAlias()));
			certInfo.setPrivKey((PrivateKey)(keystore.getKey(config.getAlias(), config.getPassword().toCharArray())));
			certInfo.setCerts(certChain);
			certInfo.setCertStore(CertStore.getInstance("Collection", new CollectionCertStoreParameters(certList), config.getKeyStoreProvider()));
		}catch(Exception e){
			log.error("Error al obtener datos de certificado [getcertInfo][Exception]", e.fillInStackTrace());
			throw new FirmaDigitalException(FirmaDigitalException.EXC_SIGN_EXCEPTION, e.getMessage(), e);
		}
		return certInfo;
	}

	public ResultadoValidacionFirma validarFirma(byte[] pabB64SourceSigned, byte [] signedContentB64) throws FirmaDigitalException {
		ResultadoValidacionFirma resultado = new ResultadoValidacionFirma();
		resultado.setValidationResult(false);
		try {
			
			BASE64Decoder oDecoder = new BASE64Decoder();
			byte signedContent [] = oDecoder.decodeBuffer(new ByteArrayInputStream(signedContentB64)); 
			CMSProcessableByteArray signedContentCMS = new CMSProcessableByteArray(signedContent);
			byte [] signPk7 = oDecoder.decodeBuffer(new ByteArrayInputStream(pabB64SourceSigned));
			CMSSignedData signature = new CMSSignedData(signedContentCMS, new ByteArrayInputStream(signPk7));
			SignerInformationStore signers=signature.getSignerInfos();
			
			CertStore certs=signature.getCertificatesAndCRLs("Collection", provider);
		
			Collection c=signers.getSigners();
			Iterator it=c.iterator();
			
			resultado.setValidationResult(true);
			while(it.hasNext()) {
				SignerInformation signer=(SignerInformation)it.next();
				Collection certCollection=certs.getCertificates(signer.getSID());
				Iterator certIt=certCollection.iterator();
				X509Certificate cert=(X509Certificate)certIt.next();

				if(!signer.verify(cert, provider)) {
					resultado.setValidationResult(false);
					return resultado; 
				}

				String subjectDN=cert.getSubjectDN().toString();
				String issuerDN=cert.getIssuerDN().toString();
				ieci.tecdoc.sgm.core.services.cripto.firma.Certificado certificado = new ieci.tecdoc.sgm.core.services.cripto.firma.Certificado();
				certificado.setIssuer(issuerDN);
				certificado.setSubject(subjectDN);
				certificado.setSerialNumber(cert.getSerialNumber().toString());
				ieci.tecdoc.sgm.core.services.cripto.firma.Firmante firmante = new ieci.tecdoc.sgm.core.services.cripto.firma.Firmante();
				firmante.setName(certificado.getSubject());
				firmante.setCertificate(certificado);
				resultado.addSigner(firmante);	
			}	
		} catch(Throwable e) {
			log.error("Error al obtener datos de certificado [validarFirma][Throwable]", e.fillInStackTrace());
			throw new FirmaDigitalException(FirmaDigitalException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}

		return resultado;
	}

	private KeyStore getKeyStoreData() throws FirmaDigitalException{
		FirmaPKCS7Config config = loadConfig();
		KeyStore ks=null;
		try{
			ks=KeyStore.getInstance(config.getKeyStoreType(), config.getKeyStoreProvider());
			ks.load(new FileInputStream(config.getKeyStore()), config.getPassword().toCharArray());
		}catch (NoSuchProviderException pe){
			log.error("Error al obtener datos de certificado de servidor [getKeyStoreData][NoSuchProviderException]", pe.fillInStackTrace());
			throw new FirmaDigitalException(FirmaDigitalException.EXC_PROVIDER_EXCEPTION, pe.getMessage(), pe);
		}catch(KeyStoreException ke){
			log.error("Error al obtener datos de certificado de servidor [getKeyStoreData][KeyStoreException]", ke.fillInStackTrace());
			throw new FirmaDigitalException(FirmaDigitalException.EXC_PROVIDER_EXCEPTION, ke.getMessage(), ke);			
		}catch(Exception e){
			log.error("Error al obtener datos de certificado de servidor [getKeyStoreData][Exception]", e.fillInStackTrace());
			throw new FirmaDigitalException(FirmaDigitalException.EXC_PROVIDER_EXCEPTION, e.getMessage(), e);			
		}
		return ks;
	}


	public void setProvider(String p) {
		provider=p;
	}
	public String getProvider() {
		return provider;
	}

	public String registrarFirma(byte[] signatureValue,
			byte[] signCertificateValue, byte[] hash)
			throws FirmaDigitalException {
		// TODO Auto-generated method stub
		return null;
	}

	public Map getConfiguracionesPorEntidad() {
		return configuracionesPorEntidad;
	}

	public void setConfiguracionesPorEntidad(Map configuracionesPorEntidad) {
		this.configuracionesPorEntidad = configuracionesPorEntidad;
	}
	private FirmaPKCS7Config loadConfig()  throws FirmaDigitalException {
		String idEntidad = MultiEntityContextHolder.getEntity();
		FirmaPKCS7Config config = (FirmaPKCS7Config)configuracionesPorEntidad.get(idEntidad);
		if(config==null)
		{
			ConfigLoader configLoader = new ConfigLoader();
			config = configLoader.loadPKCS7Configuration(idEntidad);
			configuracionesPorEntidad.put(idEntidad, config);
		}
	
		return config;
	}
}
