package common.util;

import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.axis.encoding.Base64;
import org.apache.log4j.Logger;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import xml.config.ConfiguracionGeneral;
import xml.config.ConfiguracionSistemaArchivoFactory;

import common.exceptions.CryptoException;

/**
 * Utilidad para encriptar y desencriptar claves.
 */
public class CryptoHelper {
	/** Logger de la clase. */
	private static final Logger logger = Logger.getLogger(CryptoHelper.class);

	/** Clave secreta para la encriptación/desencriptación. */
	private SecretKeySpec keySpec = null;

	/** Algoritmo de encriptación/desencriptación. */
	private String algorithm = null;

	/**
	 * Constructor.
	 */
	private CryptoHelper() {
		Security.addProvider(new BouncyCastleProvider());

		// Configuración general
		ConfiguracionGeneral cfgGral = ConfiguracionSistemaArchivoFactory
				.getConfiguracionSistemaArchivo().getConfiguracionGeneral();

		// Leer el algoritmo de encriptación/desencriptación
		algorithm = cfgGral.getAlgoritmoEncriptacion();

		// Construir un objeto con la clave secreta
		keySpec = new SecretKeySpec(Base64.decode(cfgGral
				.getClaveSecretaEncriptacion()), algorithm);
	}

	/**
	 * Devuelve el helper.
	 * 
	 * @return CryptoHelper.
	 */
	public static CryptoHelper getHelper() {
		return new CryptoHelper();
	}

	/**
	 * Encripta una lista de bytes.
	 * 
	 * @param cadena
	 *            Lista de bytes.
	 * @return Lista de bytes encriptada.
	 * @throws CryptoException
	 *             si ocurre algún error en la encriptación.
	 */
	public byte[] encrypt(byte[] cadena) throws CryptoException {
		byte[] res = null;

		try {
			Cipher cipher = Cipher.getInstance(algorithm);
			cipher.init(Cipher.ENCRYPT_MODE, keySpec);
			res = cipher.doFinal(cadena);
		} catch (Exception e) {
			logger.error("Error en la encriptaci\u00F3", e);
			throw new CryptoException(e.getLocalizedMessage());
		}

		return res;
	}

	/**
	 * Encripta una cadena.
	 * 
	 * @param cadena
	 *            Cadena a encriptar.
	 * @return Cadena encriptada.
	 * @throws CryptoException
	 *             si ocurre algún error en la encriptación.
	 */
	public String encrypt(String cadena) throws CryptoException {
		if (cadena == null)
			return null;
		else
			return Base64.encode(encrypt(cadena.getBytes()));
	}

	/**
	 * Desencripta una lista de bytes.
	 * 
	 * @param cadena
	 *            Lista de bytes.
	 * @return Lista de bytes desencriptada.
	 * @throws CryptoException
	 *             si ocurre algún error en la encriptación.
	 */
	public byte[] decrypt(byte[] cadena) throws CryptoException {
		byte[] res = null;

		try {
			Cipher cipher = Cipher.getInstance(algorithm);
			cipher.init(Cipher.DECRYPT_MODE, keySpec);
			res = cipher.doFinal(cadena);
		} catch (Exception e) {
			logger.error("Error en la desencriptaci\u00F3", e);
			throw new CryptoException(e.getLocalizedMessage());
		}

		return res;
	}

	/**
	 * Desencripta una cadena.
	 * 
	 * @param cadena
	 *            Cadena a desencriptar.
	 * @return Cadena desencriptada.
	 * @throws CryptoException
	 *             si ocurre algún error en la encriptación.
	 */
	public String decrypt(String cadena) throws CryptoException {
		if (cadena == null)
			return null;
		else
			return new String(decrypt(Base64.decode(cadena)));
	}
}
