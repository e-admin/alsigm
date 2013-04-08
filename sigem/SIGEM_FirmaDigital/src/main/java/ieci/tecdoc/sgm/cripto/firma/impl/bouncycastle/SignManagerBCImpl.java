package ieci.tecdoc.sgm.cripto.firma.impl.bouncycastle;

import ieci.tecdoc.sgm.core.config.impl.spring.MultiEntityContextHolder;
import ieci.tecdoc.sgm.core.services.cripto.firma.CertificadoX509Info;
import ieci.tecdoc.sgm.core.services.cripto.firma.FirmaDigitalException;
import ieci.tecdoc.sgm.core.services.cripto.firma.ServicioFirmaDigital;
import ieci.tecdoc.sgm.core.services.cripto.firma.ResultadoValidacionFirma;
import ieci.tecdoc.sgm.cripto.firma.impl.bouncycastle.config.FirmaBCConfiguration;
import ieci.tecdoc.sgm.cripto.firma.impl.bouncycastle.config.FirmaPKCS7Config;
import ieci.tecdoc.sgm.cripto.firma.impl.config.ConfigLoader;

import java.security.cert.CertStore;
import java.security.cert.Certificate;
import java.security.cert.CollectionCertStoreParameters;
import java.security.cert.X509Certificate;
import java.security.*;
import java.security.cert.*;
import java.io.FileInputStream;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Arrays;
import java.util.Map;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import org.apache.log4j.Logger;
import org.bouncycastle.cms.*;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
  $Id: SignManagerBCImpl.java,v 1.5 2008/05/07 11:43:35 afernandez Exp $

  servicio firmar con BC
 */

public class SignManagerBCImpl implements ServicioFirmaDigital, Serializable{

	private static final long serialVersionUID = -4606044678277155482L;
	final static String CADENA="C=ES,O=FNMT,OU=FNMT Clase 2 CA";
	final static String CN_NOMBRE="CN=NOMBRE ";
	final static String NIF="NIF ";
	private Map configuracionesPorEntidad = new HashMap();
	private String provider = Constants.PROVIDER;

	private static Logger log = Logger.getLogger(SignManagerBCImpl.class);

	static{
		java.security.Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
	}

	public String firmar(byte[] pbaB64Source) throws FirmaDigitalException {
		String b64 = null;
		try {
			FirmaBCConfiguration config = loadConfig();
			KeyStore ks=KeyStore.getInstance(config.getKeyStoreType(), config.getKeyStoreProvider());
			ks.load(new FileInputStream(config.getKeyStore()), config.getPassword().toCharArray());

			Certificate[]chain=ks.getCertificateChain(config.getAlias());
			if(chain==null)
				throw new Exception("no existe alias");
			PrivateKey privKey=(PrivateKey)(ks.getKey(config.getAlias(), config.getPassword().toCharArray()));

			CertStore certsAndCRLs=CertStore.getInstance("Collection", new CollectionCertStoreParameters(Arrays.asList(chain)), provider);
			X509Certificate cert=(X509Certificate)chain[0];

			CMSSignedDataGenerator gen=new CMSSignedDataGenerator();
			gen.addSigner(privKey, cert, CMSSignedDataGenerator.DIGEST_SHA1);
			gen.addCertificatesAndCRLs(certsAndCRLs);

			// BufferedInputStream bis=new BufferedInputStream(new ByteArrayInputStream(b));
			CMSProcessable data=new CMSProcessableByteArray(pbaB64Source);

			CMSSignedData signed=gen.generate(data, true, provider);

			BASE64Encoder encoder=new BASE64Encoder();
			b64=encoder.encodeBuffer(signed.getEncoded());
			// String b64=Base64.encodeBytes(signed.getEncoded());

		} catch(Throwable e) {
			log.error(e);
			throw new FirmaDigitalException(FirmaDigitalException.EXC_GENERIC_EXCEPCION, e.getMessage(), e);
		}

		return b64;
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
	public CertificadoX509Info getcertInfo() throws FirmaDigitalException {
		FirmaBCConfiguration config = loadConfig();
		Security.addProvider(new BouncyCastleProvider());
		KeyStore keystore = null;
		CertificadoX509Info certInfo = new CertificadoX509Info();
		ArrayList certList = new ArrayList();
		java.security.cert.Certificate[] certChain = null;
		try{
			keystore = KeyStore.getInstance("PKCS12");
			keystore.load(new FileInputStream(config.getKeyStore()), config.getPassword().toCharArray());
			certChain = keystore.getCertificateChain(config.getAlias());
			for (int i = 0; i < certChain.length;i++)
				certList.add(certChain[i]);

			certInfo.setCert((X509Certificate)keystore.getCertificate(config.getAlias()));
			certInfo.setPrivKey((PrivateKey)(keystore.getKey(config.getAlias(), config.getPassword().toCharArray())));
			certInfo.setCerts(certChain);
			certInfo.setCertStore(CertStore.getInstance("Collection", new CollectionCertStoreParameters(certList), "BC"));
		}catch(Exception e){
			log.error(e);
			throw new FirmaDigitalException(FirmaDigitalException.EXC_SIGN_EXCEPTION, e.getMessage(), e);
		}
		return certInfo;
	}

	private FirmaBCConfiguration loadConfig()  throws FirmaDigitalException {
		String idEntidad = MultiEntityContextHolder.getEntity();
		FirmaBCConfiguration config = (FirmaBCConfiguration)configuracionesPorEntidad.get(idEntidad);
		if(config==null)
		{
			ConfigLoader configLoader = new ConfigLoader();
			config = configLoader.loadBCConfiguration(idEntidad);
			configuracionesPorEntidad.put(idEntidad, config);
		}
	
		return config;
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

}
