package ieci.tecdoc.sgm.pe.struts.receipt;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Security;
import java.security.cert.CertStore;
import java.security.cert.CollectionCertStoreParameters;
import java.security.cert.X509Certificate;
import java.util.ArrayList;

import org.bouncycastle.cms.CMSProcessable;
import org.bouncycastle.cms.CMSProcessableByteArray;
import org.bouncycastle.cms.CMSSignedData;
import org.bouncycastle.cms.CMSSignedDataGenerator;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

public abstract class ReceiptSigner {

	 public abstract byte[] signReceipt(byte[] data) throws Exception;
	 
	 
	    protected ByteArrayOutputStream sign(byte[] data, String path, String password, String alias) throws Exception
	    {
	       Security.addProvider(new BouncyCastleProvider());
	       KeyStore keystore = null;
	       ArrayList certList = new ArrayList();
	       java.security.cert.Certificate[] certChain = null;

	       keystore = KeyStore.getInstance("PKCS12");
	       keystore.load(new FileInputStream(path), password.toCharArray());
	       certChain = keystore.getCertificateChain(alias);
	       for (int i = 0; i < certChain.length;i++)
	          certList.add(certChain[i]);
	       
	       return sign(data, CertStore.getInstance("Collection", new CollectionCertStoreParameters(certList), "BC"), 
	                (X509Certificate)keystore.getCertificate(alias), 
	                (PrivateKey)(keystore.getKey(alias, password.toCharArray())));
	       
	    }

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
