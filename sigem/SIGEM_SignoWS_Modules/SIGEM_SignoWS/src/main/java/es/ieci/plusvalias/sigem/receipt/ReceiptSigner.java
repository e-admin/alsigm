package es.ieci.plusvalias.sigem.receipt;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Security;
import java.security.cert.CertStore;
import java.security.cert.Certificate;
import java.security.cert.CollectionCertStoreParameters;
import java.security.cert.X509Certificate;
import java.util.ArrayList;

import org.bouncycastle.cms.CMSProcessable;
import org.bouncycastle.cms.CMSProcessableByteArray;
import org.bouncycastle.cms.CMSSignedData;
import org.bouncycastle.cms.CMSSignedDataGenerator;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class ReceiptSigner
{
	private KeyStoreData keystoreData;
	
	public ReceiptSigner(KeyStoreData keystoreData)
	{
		this.keystoreData = keystoreData;
	}
	
	public byte[] signReceipt(String reason, String field, String location, String text, byte[] data) throws Exception
	{
		//return ITextSignUtils.signPDF(reason, field, location, text, data);
		
		return ITextSignUtils.firmaPDF(keystoreData, reason, field, location, text, data);
	}

	public ByteArrayOutputStream sign(byte[] data, String path, String password, String alias) throws Exception
	{
		Security.addProvider(new BouncyCastleProvider());
		KeyStore keystore = null;
		ArrayList<Certificate> certList = new ArrayList<Certificate>();
		Certificate[] certChain = null;

		keystore = KeyStore.getInstance(keystoreData.getKeystoreType());
		keystore.load(new FileInputStream(path), password.toCharArray());
		certChain = keystore.getCertificateChain(alias);
		
		for (int i = 0; i < certChain.length; i++)
		{
			certList.add(certChain[i]);
		}

		return sign(data, CertStore.getInstance("Collection", new CollectionCertStoreParameters(certList), "BC"),
				(X509Certificate) keystore.getCertificate(alias),
				(PrivateKey)(keystore.getKey(alias, password.toCharArray())));
	}
	
	public ByteArrayOutputStream sign(byte[] data) throws Exception
	{
		Security.addProvider(new BouncyCastleProvider());
		KeyStore keystore = null;
		ArrayList<Certificate> certList = new ArrayList<Certificate>();
		Certificate[] certChain = null;

		keystore = KeyStore.getInstance(keystoreData.getKeystoreType());
		keystore.load(new FileInputStream(keystoreData.getKeystorePath()), keystoreData.getKeystorePassword().toCharArray());
		certChain = keystore.getCertificateChain(keystoreData.getKeyAlias());
		
		for (int i = 0; i < certChain.length; i++)
		{
			certList.add(certChain[i]);
		}

		return sign(data, CertStore.getInstance("Collection", new CollectionCertStoreParameters(certList), "BC"),
				(X509Certificate) keystore.getCertificate(keystoreData.getKeyAlias()),
				(PrivateKey)(keystore.getKey(keystoreData.getKeyAlias(), keystoreData.getKeyAliasPassword().toCharArray())));
	}

	public ByteArrayOutputStream sign(byte[] data, CertStore certStore, X509Certificate cert, PrivateKey key) throws Exception
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