//
// FileName: CryptoUtils.java
//
package com.ieci.tecdoc.idoc.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.Security;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class CryptoUtils {

	public static String encryptPassword(String decPwd, Integer userId)
			throws Exception {
		String encPwd = null;
		String passwordToGenKey = "IUSER" + userId;

		// Obtener el texto que va a dar el hash inicial
		byte buf[] = passwordToGenKey.getBytes("ISO-8859-1");

		Security.addProvider(new BouncyCastleProvider());

		// Obtener el hash
		MessageDigest md = MessageDigest.getInstance("MD5", "BC");

		md.update(buf);
		byte hash[] = md.digest();

		// Aunque no lo diga en ninguna parte, el cryptoapi usa una clave de 128
		// bits con todo ceros
		// menos los 5 primeros bytes para cifrar....
		byte newHash[] = { (byte) hash[0], (byte) hash[1], (byte) hash[2],
				(byte) hash[3], (byte) hash[4], (byte) 0x00, (byte) 0x00,
				(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
				(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00 };

		// Obtener la key basada en ese hash
		SecretKeySpec key = new SecretKeySpec(newHash, "RC4");

		// Obtener el objeto Cipher e inicializarlo con la key
		Cipher cipher = Cipher.getInstance("RC4", "BC");
		cipher.init(Cipher.ENCRYPT_MODE, key);

		// Obtener los datos a cifrar
		byte bufPlain[] = decPwd.getBytes("ISO-8859-1");

		// cifrar
		byte bufCipher[] = cipher.doFinal(bufPlain);

		encPwd = Base64Util.encode(bufCipher);

		return encPwd;
	}

	public static String encryptHashDocument(byte[] data) throws Exception {
		String encPwd = null;
		String passwordToGenKey = "IUSER0";
		byte[] buffer;

		Security.addProvider(new BouncyCastleProvider());

		MessageDigest gen = MessageDigest.getInstance("SHA-256", "BC");

		gen.update(data, 0, data.length);
		buffer = gen.digest();
		// ////////////////////////
		String buffer1 = Base64Util.encode(buffer);
		// ///////////////////////
		// Obtener el texto que va a dar el hash inicial
		byte buf[] = passwordToGenKey.getBytes("ISO-8859-1");

		// Obtener el hash
		MessageDigest md = MessageDigest.getInstance("MD5", "BC");

		md.update(buf);
		byte hash[] = md.digest();

		// Aunque no lo diga en ninguna parte, el cryptoapi usa una clave de 128
		// bits con todo ceros
		// menos los 5 primeros bytes para cifrar....
		byte newHash[] = { (byte) hash[0], (byte) hash[1], (byte) hash[2],
				(byte) hash[3], (byte) hash[4], (byte) 0x00, (byte) 0x00,
				(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
				(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00 };

		// Obtener la key basada en ese hash
		SecretKeySpec key = new SecretKeySpec(newHash, "RC4");

		// Obtener el objeto Cipher e inicializarlo con la key
		Cipher cipher = Cipher.getInstance("RC4", "BC");
		cipher.init(Cipher.ENCRYPT_MODE, key);

		// cifrar
		// byte bufCipher[] = cipher.doFinal(buffer);
		// ///////////////////////////////
		byte bufCipher[] = cipher.doFinal(buffer1.getBytes());
		// ///////////////////////////////
		encPwd = Base64Util.encode(bufCipher);

		return encPwd;
	}

	public static String encryptHashDocument(File fileForHash) throws Exception {
		String encPwd = null;
		String passwordToGenKey = "IUSER0";
		byte[] buffer;
		InputStream fis = null;

		Security.addProvider(new BouncyCastleProvider());

		fis = new FileInputStream(fileForHash);

		byte[] bufferTemp = new byte[1024];
		MessageDigest gen = MessageDigest.getInstance("SHA-256", "BC");
		int numRead;
		do {
			numRead = fis.read(bufferTemp);
			if (numRead > 0) {
				gen.update(bufferTemp, 0, numRead);
			}
		} while (numRead != -1);
		fis.close();

		// gen.update(data, 0, data.length);
		buffer = gen.digest();
		// ////////////////////////
		String buffer1 = Base64Util.encode(buffer);
		// ///////////////////////
		// Obtener el texto que va a dar el hash inicial
		byte buf[] = passwordToGenKey.getBytes("ISO-8859-1");

		// Obtener el hash
		MessageDigest md = MessageDigest.getInstance("MD5", "BC");

		md.update(buf);
		byte hash[] = md.digest();

		// Aunque no lo diga en ninguna parte, el cryptoapi usa una clave de 128
		// bits con todo ceros
		// menos los 5 primeros bytes para cifrar....
		byte newHash[] = { (byte) hash[0], (byte) hash[1], (byte) hash[2],
				(byte) hash[3], (byte) hash[4], (byte) 0x00, (byte) 0x00,
				(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
				(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00 };

		// Obtener la key basada en ese hash
		SecretKeySpec key = new SecretKeySpec(newHash, "RC4");

		// Obtener el objeto Cipher e inicializarlo con la key
		Cipher cipher = Cipher.getInstance("RC4", "BC");
		cipher.init(Cipher.ENCRYPT_MODE, key);

		// cifrar
		// byte bufCipher[] = cipher.doFinal(buffer);
		// ///////////////////////////////
		byte bufCipher[] = cipher.doFinal(buffer1.getBytes());
		// ///////////////////////////////
		encPwd = Base64Util.encode(bufCipher);

		return encPwd;
	}

	public static String decryptPasswordLDAP(String pwdLdap) throws Exception {
		String passwordToGenKey = "IDOCLDAP";

		byte dPasswordLDAP[] = Base64Util.decode(pwdLdap);
		// Obtener el texto que va a dar el hash inicial
		byte buf[] = passwordToGenKey.getBytes("ISO-8859-1");

		Security.addProvider(new BouncyCastleProvider());

		// Obtener el hash
		MessageDigest md = MessageDigest.getInstance("MD5", "BC");

		md.update(buf);
		byte hash[] = md.digest();

		// Aunque no lo diga en ninguna parte, el cryptoapi usa una clave de 128
		// bits con todo ceros
		// menos los 5 primeros bytes para cifrar....
		byte newHash[] = { (byte) hash[0], (byte) hash[1], (byte) hash[2],
				(byte) hash[3], (byte) hash[4], (byte) 0x00, (byte) 0x00,
				(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
				(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00 };

		// Obtener la key basada en ese hash
		SecretKeySpec key = new SecretKeySpec(newHash, "RC4");

		// Obtener el objeto Cipher e inicializarlo con la key
		Cipher cipher = Cipher.getInstance("RC4", "BC");
		cipher.init(Cipher.DECRYPT_MODE, key);

		// descifrar
		byte bufCipher[] = cipher.doFinal(dPasswordLDAP);

		String descPwdLdap = new String(bufCipher);

		return descPwdLdap;
	}

	public static String getDecodeDn(String userDn) throws Exception {
		String dn = null;
		int ni = 0;
		int n = 0;
		int caracterDn = 0;
		char c;
		List s = new ArrayList();
		int iMsk = 0x20;
		boolean bSigue = true;
		String sTbCodes = "-*0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
		while (bSigue) {
			n = 0;
			c = userDn.charAt(caracterDn);
			ni = sTbCodes.indexOf(c);
			for (int nBit = 0; nBit < 8; nBit++) {
				n <<= 1;
				int aux = (ni & iMsk);
				n |= aux > 0 ? 1 : 0;
				iMsk >>= 1;
				if (iMsk == 0) { // Siguiente caracter
					caracterDn++;
					c = userDn.charAt(caracterDn);
					if (c == '\0') {
						bSigue = false;
						break;
					}
					ni = sTbCodes.indexOf(c);
					iMsk = 0x20;
				}
			}
			s.add(new Integer(n));
		}
		byte[] result = new byte[s.size()];
		for (int i = 0; i < s.size(); i++) {
			Integer caracter = (Integer) s.get(i);
			result[i] = caracter.byteValue();
		}
		dn = new String(result);
		return dn;
	}

}