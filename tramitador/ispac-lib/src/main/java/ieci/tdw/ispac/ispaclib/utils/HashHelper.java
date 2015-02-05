package ieci.tdw.ispac.ispaclib.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.naming.ConfigurationException;

import org.bouncycastle.util.encoders.Base64;


/**
 * Utilidad para la codificación hash.
 */
public class HashHelper {

	/** Algoritmo para generar el código hash. */
	private static final String ALGORITMO_HASH = "SHA-1";
	
	
	/**
	 * Genera un código hash en base-64 para una cadena determinada.
	 * @param cadena Cadena a codificar.
	 * @return Código hash en base-64.
	 * @throws ConfigurationException si ocurre algún error al generar el 
	 * código hash.
	 */
	public static String generateHashCode(String cadena) 
			throws NoSuchAlgorithmException {
		
		// Crea un digest con el algoritmo SHA-1
        MessageDigest md = MessageDigest.getInstance(ALGORITMO_HASH);

        // Genera el código hash de la cadena
        byte[] hash = md.digest(cadena.getBytes());
        
        // Convertir el array de bytes a un String
        return new String(Base64.encode(hash));
	}
	
	/**
	 * Genera un código hash en base-64 para un binario determinado.
	 * @param content Binario.
	 * @return Código hash en base-64.
	 * @throws ConfigurationException si ocurre algún error al generar el 
	 * código hash.
	 */
	public static String generateHashCode(byte[] content) 
			throws NoSuchAlgorithmException {
		String contentBase64 = new String(Base64.encode(content));
		return generateHashCode(contentBase64);
	}
}
