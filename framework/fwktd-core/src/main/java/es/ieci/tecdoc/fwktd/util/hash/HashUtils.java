package es.ieci.tecdoc.fwktd.util.hash;

import java.io.InputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Clase de utilidad para la generación y verificación de códigos hash.
 * 
 * @author Iecisa
 * @version $Revision$
 * 
 */
public class HashUtils {

	/**
	 * Logger de la clase
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(HashUtils.class);

	/**
	 * Algoritmo de encriptación SHA-1
	 */
	public static final String SHA1_ALGORITHM = "SHA1";

	/**
	 * Algoritmo de encriptación MD5
	 */
	public static final String MD5_ALGORITHM = "MD5";

	/**
	 * Constructor.
	 */
	private HashUtils() {
	}

	/**
	 * Genera el código hash de un binario.
	 * 
	 * @param content
	 *            Binario.
	 * @param algorithm
	 *            Algoritmo de encriptación.
	 * @param provider
	 *            Proveedor.
	 * @return Código hash.
	 * @throws NoSuchProviderException
	 *             si el proveedor no está soportado.
	 * @throws NoSuchAlgorithmException
	 *             si el algoritmo no está soportado.
	 */
	public static byte[] generateHash(byte[] content, String algorithm,
			String provider) throws NoSuchAlgorithmException,
			NoSuchProviderException {

		if (logger.isDebugEnabled()) {
			logger.debug("generateHash: algorithm=[{}], provider=[{}]",
					algorithm, provider);
		}

		MessageDigest md = null;

		if (StringUtils.isBlank(provider)) {
			md = MessageDigest.getInstance(algorithm);
		} else {
			md = MessageDigest.getInstance(algorithm, provider);
		}

		md.update(content);
		byte[] hash = md.digest();

		if (logger.isDebugEnabled()) {
			Base64 base64 = new Base64();
			logger.debug("hashBase64 => [{}]", base64.encode(hash));
		}

		return hash;
	}

	/**
	 * Genera el código hash de un binario.
	 * 
	 * @param content
	 *            Binario.
	 * @param algorithm
	 *            Algoritmo de encriptación.
	 * @return Código hash.
	 * @throws NoSuchProviderException
	 *             si el proveedor no está soportado.
	 * @throws NoSuchAlgorithmException
	 *             si el algoritmo no está soportado.
	 */
	public static byte[] generateHash(byte[] content, String algorithm)
			throws NoSuchAlgorithmException, NoSuchProviderException {
		return generateHash(content, algorithm, null);
	}

	/**
	 * Genera el código hash de un binario utilizando el algoritmo SHA1 y el
	 * proveedor BC.
	 * 
	 * @param content
	 *            Binario.
	 * @return Código hash.
	 * @throws NoSuchProviderException
	 *             si el proveedor no está soportado.
	 * @throws NoSuchAlgorithmException
	 *             si el algoritmo no está soportado.
	 */
	public static byte[] generateHash(byte[] content)
			throws NoSuchAlgorithmException, NoSuchProviderException {
		return generateHash(content, SHA1_ALGORITHM, null);
	}

	/**
	 * Genera el código hash de un binario.
	 * 
	 * @param content
	 *            Binario.
	 * @param algorithm
	 *            Algoritmo de encriptación.
	 * @param provider
	 *            Proveedor.
	 * @return Código hash.
	 * @throws NoSuchProviderException
	 *             si el proveedor no está soportado.
	 * @throws NoSuchAlgorithmException
	 *             si el algoritmo no está soportado.
	 * @throws IOException
	 *             si ocurre alg�n error al leer el contenido.
	 */
	public static byte[] generateHash(InputStream content, String algorithm,
			String provider) throws NoSuchAlgorithmException,
			NoSuchProviderException, IOException {

		if (logger.isDebugEnabled()) {
			logger.debug("generateHash: algorithm=[{}], provider=[{}]",
					algorithm, provider);
		}

		MessageDigest md = null;

		if (StringUtils.isBlank(provider)) {
			md = MessageDigest.getInstance(algorithm);
		} else {
			md = MessageDigest.getInstance(algorithm, provider);
		}

		byte[] buffer = new byte[1024];
		int read;
		while ((read = content.read(buffer)) != -1) {
			md.update(buffer, 0, read);
			read = content.read(buffer);
		}

		byte[] hash = md.digest();

		if (logger.isDebugEnabled()) {
			Base64 base64 = new Base64();
			logger.debug("hashBase64 => [{}]", base64.encode(hash));
		}

		return hash;
	}

	/**
	 * Genera el código hash de un binario.
	 * 
	 * @param content
	 *            Binario.
	 * @param algorithm
	 *            Algoritmo de encriptación.
	 * @return Código hash.
	 * @throws NoSuchProviderException
	 *             si el proveedor no está soportado.
	 * @throws NoSuchAlgorithmException
	 *             si el algoritmo no está soportado.
	 * @throws IOException
	 *             si ocurre alg�n error al leer el contenido.
	 */
	public static byte[] generateHash(InputStream content, String algorithm)
			throws NoSuchAlgorithmException, NoSuchProviderException,
			IOException {
		return generateHash(content, algorithm, null);
	}

	/**
	 * Genera el código hash de un binario utilizando el algoritmo SHA1 y el
	 * proveedor BC.
	 * 
	 * @param content
	 *            Binario.
	 * @return Código hash.
	 * @throws NoSuchProviderException
	 *             si el proveedor no está soportado.
	 * @throws NoSuchAlgorithmException
	 *             si el algoritmo no está soportado.
	 * @throws IOException
	 *             si ocurre alg�n error al leer el contenido.
	 */
	public static byte[] generateHash(InputStream content)
			throws NoSuchAlgorithmException, NoSuchProviderException,
			IOException {
		return generateHash(content, SHA1_ALGORITHM, null);
	}

	/**
	 * Genera el código hash en Base64 de un binario.
	 * 
	 * @param content
	 *            Binario.
	 * @param algorithm
	 *            Algoritmo de encriptación.
	 * @param provider
	 *            Proveedor.
	 * @return Código hash en Base64.
	 * @throws NoSuchProviderException
	 *             si el proveedor no está soportado.
	 * @throws NoSuchAlgorithmException
	 *             si el algoritmo no está soportado.
	 */
	public static String generateHashBase64(byte[] content, String algorithm,
			String provider) throws NoSuchAlgorithmException,
			NoSuchProviderException {

		Base64 base64 = new Base64();
		return new String(base64.encode(generateHash(content, algorithm,
				provider)));
	}

	/**
	 * Genera el código hash en Base64 de un binario.
	 * 
	 * @param content
	 *            Binario.
	 * @param algorithm
	 *            Algoritmo de encriptación.
	 * @return Código hash en Base64.
	 * @throws NoSuchProviderException
	 *             si el proveedor no está soportado.
	 * @throws NoSuchAlgorithmException
	 *             si el algoritmo no está soportado.
	 */
	public static String generateHashBase64(byte[] content, String algorithm)
			throws NoSuchAlgorithmException, NoSuchProviderException {
		return generateHashBase64(content, algorithm, null);
	}

	/**
	 * Genera el código hash en Base64 de un binario utilizando el algoritmo
	 * SHA1 y el proveedor BC.
	 * 
	 * @param content
	 *            Binario.
	 * @return Código hash en Base64.
	 * @throws NoSuchProviderException
	 *             si el proveedor no está soportado.
	 * @throws NoSuchAlgorithmException
	 *             si el algoritmo no está soportado.
	 */
	public static String generateHashBase64(byte[] content)
			throws NoSuchAlgorithmException, NoSuchProviderException {
		return generateHashBase64(content, SHA1_ALGORITHM, null);
	}

	/**
	 * Genera el código hash en Base64 de un binario.
	 * 
	 * @param content
	 *            Binario.
	 * @param algorithm
	 *            Algoritmo de encriptación.
	 * @param provider
	 *            Proveedor.
	 * @return Código hash en Base64.
	 * @throws NoSuchProviderException
	 *             si el proveedor no está soportado.
	 * @throws NoSuchAlgorithmException
	 *             si el algoritmo no está soportado.
	 * @throws IOException
	 *             si ocurre alg�n error al leer el contenido.
	 */
	public static String generateHashBase64(InputStream content,
			String algorithm, String provider) throws NoSuchAlgorithmException,
			NoSuchProviderException, IOException {

		Base64 base64 = new Base64();
		return new String(base64.encode(generateHash(content, algorithm,
				provider)));
	}

	/**
	 * Genera el código hash en Base64 de un binario.
	 * 
	 * @param content
	 *            Binario.
	 * @param algorithm
	 *            Algoritmo de encriptación.
	 * @return Código hash en Base64.
	 * @throws NoSuchProviderException
	 *             si el proveedor no está soportado.
	 * @throws NoSuchAlgorithmException
	 *             si el algoritmo no está soportado.
	 * @throws IOException
	 *             si ocurre alg�n error al leer el contenido.
	 */
	public static String generateHashBase64(InputStream content,
			String algorithm) throws NoSuchAlgorithmException,
			NoSuchProviderException, IOException {
		return generateHashBase64(content, algorithm, null);
	}

	/**
	 * Genera el código hash en Base64 de un binario utilizando el algoritmo
	 * SHA1 y el proveedor BC.
	 * 
	 * @param content
	 *            Binario.
	 * @return Código hash en Base64.
	 * @throws NoSuchProviderException
	 *             si el proveedor no está soportado.
	 * @throws NoSuchAlgorithmException
	 *             si el algoritmo no está soportado.
	 * @throws IOException
	 *             si ocurre alg�n error al leer el contenido.
	 */
	public static String generateHashBase64(InputStream content)
			throws NoSuchAlgorithmException, NoSuchProviderException,
			IOException {
		return generateHashBase64(content, SHA1_ALGORITHM, null);
	}

	/**
	 * Verifica el código hash de un binario.
	 * 
	 * @param content
	 *            Binario.
	 * @param hashBase64
	 *            Código hash en Base64.
	 * @param algorithm
	 *            Algoritmo de encriptación.
	 * @param provider
	 *            Proveedor.
	 * @return true si el código hash es correcto, false en caso contrario.
	 * @throws NoSuchProviderException
	 *             si el proveedor no está soportado.
	 * @throws NoSuchAlgorithmException
	 *             si el algoritmo no está soportado.
	 */
	public static boolean validateHash(byte[] content, String hashBase64,
			String algorithm, String provider) throws NoSuchAlgorithmException,
			NoSuchProviderException {
		return StringUtils.equals(
				generateHashBase64(content, algorithm, provider), hashBase64);
	}

	/**
	 * Verifica el código hash de un binario.
	 * 
	 * @param content
	 *            Binario.
	 * @param hashBase64
	 *            Código hash en Base64.
	 * @param algorithm
	 *            Algoritmo de encriptación.
	 * @return true si el código hash es correcto, false en caso contrario.
	 * @throws NoSuchProviderException
	 *             si el proveedor no está soportado.
	 * @throws NoSuchAlgorithmException
	 *             si el algoritmo no está soportado.
	 */
	public static boolean validateHash(byte[] content, String hashBase64,
			String algorithm) throws NoSuchAlgorithmException,
			NoSuchProviderException {
		return StringUtils.equals(generateHashBase64(content, algorithm),
				hashBase64);
	}

	/**
	 * Verifica el código hash de un binario.
	 * 
	 * @param content
	 *            Binario.
	 * @param hashBase64
	 *            Código hash en Base64.
	 * @return true si el código hash es correcto, false en caso contrario.
	 * @throws NoSuchProviderException
	 *             si el proveedor no está soportado.
	 * @throws NoSuchAlgorithmException
	 *             si el algoritmo no está soportado.
	 */
	public static boolean validateHash(byte[] content, String hashBase64)
			throws NoSuchAlgorithmException, NoSuchProviderException {
		return StringUtils.equals(generateHashBase64(content), hashBase64);
	}

}
