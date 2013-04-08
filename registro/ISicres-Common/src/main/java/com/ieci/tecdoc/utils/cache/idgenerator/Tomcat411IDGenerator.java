//
// FileName.: Tomcat411IDGenerator.java
// @DISCLAIMER_CLAUSE@
package com.ieci.tecdoc.utils.cache.idgenerator;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import org.apache.log4j.Logger;



/**
 * IDGenerator implementation for the Tomcat web server ver 4.1.1
 * algorithm.
 *
 * @author           @AUTHOR_NAME@
 * @version          @VERSION@
 * @internalAuthor   Luis Miguel Vicente Mosquera
 * @since            1.0
 * @lastUpdate       24/September/2002
 */
public class Tomcat411IDGenerator implements IDGenerator {

	//-------------------
	// Private attributes
	//-------------------
	private static Logger _logger = Logger.getLogger(Tomcat411IDGenerator.class);

	/**
	 * The default message digest algorithm to use if we cannot use
	 * the requested one.
	 */
	protected static final String DEFAULT_ALGORITHM = "MD5";

	/**
	 * The message digest algorithm to be used when generating session
	 * identifiers.  This must be an algorithm supported by the
	 * <code>java.security.MessageDigest</code> class on your platform.
	 */
	protected String algorithm = DEFAULT_ALGORITHM;

	/**
	 * The number of random bytes to include when generating a
	 * session identifier.
	 */
	protected static final int SESSION_ID_BYTES = 16;

	/**
	 * Return the MessageDigest implementation to be used when
	 * creating session identifiers.
	 */
	protected MessageDigest digest = null;

	/**
	 * A random number generator to use when generating session identifiers.
	 */
	protected Random random = null;

	/**
	 * A String initialization parameter used to increase the entropy of
	 * the initialization of our random number generator.
	 */
	protected String entropy = null;

	/**
	 * The Java class name of the random number generator class to be used
	 * when generating session identifiers.
	 */
	protected String randomClass = "java.security.SecureRandom";

	//---------------
	// Public methods
	//---------------

	/** @inheritAll */
	public String getSessionID() {
		// Generate a byte array containing a session identifier
		byte bytes[] = new byte[SESSION_ID_BYTES];
		getRandom().nextBytes(bytes);
		bytes = getDigest().digest(bytes);

		// Render the result as a String of hexadecimal digits
		StringBuffer result = new StringBuffer();
		for (int i = 0; i < bytes.length; i++) {
			byte b1 = (byte) ((bytes[i] & 0xf0) >> 4);
			byte b2 = (byte) (bytes[i] & 0x0f);
			if (b1 < 10)
				result.append((char) ('0' + b1));
			else
				result.append((char) ('A' + (b1 - 10)));
			if (b2 < 10)
				result.append((char) ('0' + b2));
			else
				result.append((char) ('A' + (b2 - 10)));
		}
		return (result.toString());
	}

	//----------------
	// Private methods
	//----------------

	private Random getRandom() {
		if (this.random == null) {
			synchronized (this) {
				if (this.random == null) {
					// Calculate the new random number generator seed
					long seed = System.currentTimeMillis();
					char enTropy[] = getEntropy().toCharArray();
					for (int i = 0; i < enTropy.length; i++) {
						long update = ((byte) enTropy[i]) << ((i % 8) * 8);
						seed ^= update;
					}
					try {
						// Construct and seed a new random number generator
						Class clazz = Class.forName(randomClass);
						this.random = (Random) clazz.newInstance();
						this.random.setSeed(seed);
					} catch (Exception e) {
						// Fall back to the simple case
						this.random = new java.util.Random();
						this.random.setSeed(seed);
					}
				}
			}
		}
		return this.random;
	}

	/**
	 * Return the entropy increaser value, or compute a semi-useful value
	 * if this String has not yet been set.
	 */
	private String getEntropy() {
		// Calculate a semi-useful value if this has not been set
		if (this.entropy == null)
			setEntropy(this.toString());

		return (this.entropy);

	}

	/**
	 * Set the entropy increaser value.
	 *
	 * @param entropy The new entropy increaser value
	 */
	private void setEntropy(String entropy) {
		this.entropy = entropy;
	}

	/**
	 * Return the MessageDigest object to be used for calculating
	 * session identifiers.  If none has been created yet, initialize
	 * one the first time this method is called.
	 */
	private MessageDigest getDigest() {
		if (this.digest == null) {
			try {
				this.digest = MessageDigest.getInstance(algorithm);
			} catch (NoSuchAlgorithmException e) {
				try {
					this.digest = MessageDigest.getInstance(DEFAULT_ALGORITHM);
				} catch (NoSuchAlgorithmException f) {
					this.digest = null;
				}
			}
		}

		return (this.digest);

	}

}
