//
// FileName.: Tomcat324IDGenerator.java
// @DISCLAIMER_CLAUSE@
package com.ieci.tecdoc.utils.cache.idgenerator;

import org.apache.log4j.Logger;


/**
 * IDGenerator implementation for the Tomcat web server ver 3.2.4
 * algorithm.
 *
 * This class generates a unique 10+ character id. This is good
 * for authenticating users or tracking users around.
 * <p>
 * This code was borrowed from Apache JServ.JServServletManager.java.
 * It is what Apache JServ uses to generate session ids for users.
 * Unfortunately, it was not included in Apache JServ as a class
 * so I had to create one here in order to use it.
 *
 * @author           @AUTHOR_NAME@
 * @version          @VERSION@
 * @internalAuthor   Luis Miguel Vicente Mosquera
 * @internalAuthor James Duncan Davidson [duncan@eng.sun.com]
 * @internalAuthor Jason Hunter [jhunter@acm.org]
 * @internalAuthor Jon S. Stevens <a href="mailto:jon@latchkey.com">jon@latchkey.com</a>
 * @internalAuthor Shai Fultheim [shai@brm.com]
 */

public class Tomcat324IDGenerator implements IDGenerator {

	//-------------------
	// Private attributes
	//-------------------
	private static final Logger log = Logger.getLogger(Tomcat324IDGenerator.class);

	/**
	 * Create a suitable string for session identification
	 * Use synchronized count and time to ensure uniqueness.
	 * Use random string to ensure timestamp cannot be guessed
	 * by programmed attack.
	 *
	 * format of id is <6 chars random><3 chars time><1+ char count>
	 */
	static private int session_count = 0;
	static private long lastTimeVal = 0;
	static private java.util.Random randomSource;

	// MAX_RADIX is 36
	/**
	 * we want to have a random string with a length of
	 * 6 characters. Since we encode it BASE 36, we've to
	 * modulo it with the following value:
	 */
	public final static long maxRandomLen = 2176782336L; // 36 ** 6

	/**
	 * The session identifier must be unique within the typical lifespan
	 * of a Session, the value can roll over after that. 3 characters:
	 * (this means a roll over after over an day which is much larger
	 *  than a typical lifespan)
	 */
	public final static long maxSessionLifespanTics = 46656; // 36 ** 3

	/**
	 *  millisecons between different tics. So this means that the
	 *  3-character time string has a new value every 2 seconds:
	 */
	public final static long ticDifference = 2000;

	/**
	 * A String initialization parameter used to increase the entropy of
	 * the initialization of our random number generator.
	 */
	private static String entropy = null;

	//-------------
	// Constructors
	//-------------

	public Tomcat324IDGenerator() {
		initialize();
	}

	//---------------
	// Public methods
	//---------------

	/** @inheritAll */
	public String getSessionID() {
		return getIdentifier(null);
	}

	//----------------
	// Private methods
	//----------------

	private String getEntropy() {
		return entropy;
	}

	private void setEntropy(String entropy) {
		Tomcat324IDGenerator.entropy = entropy;
	}

	private String getIdentifier(String jsIdent) {
		StringBuffer sessionId = new StringBuffer();

		initialize();

		// random value ..
		long n = randomSource.nextLong();
		if (n < 0)
			n = -n;
		n %= maxRandomLen;
		// add maxLen to pad the leading characters with '0'; remove
		// first digit with substring.
		n += maxRandomLen;
		sessionId.append(Long.toString(n, Character.MAX_RADIX).substring(1));

		long timeVal = (System.currentTimeMillis() / ticDifference);
		// cut..
		timeVal %= maxSessionLifespanTics;
		// padding, see above
		timeVal += maxSessionLifespanTics;

		sessionId.append(
			Long.toString(timeVal, Character.MAX_RADIX).substring(1));

		/*
		 * make the string unique: append the session count since last
		 * time flip.
		 */
		// count sessions only within tics. So the 'real' session count
		// isn't exposed to the public ..
		if (lastTimeVal != timeVal) {
			lastTimeVal = timeVal;
			session_count = 0;
		}
		sessionId.append(Long.toString(++session_count, Character.MAX_RADIX));

		if (jsIdent != null && jsIdent.length() > 0) {
			return sessionId.toString() + "." + jsIdent;
		}

		return sessionId.toString();
	}

	private void initialize() {
		if (randomSource == null) {
			String className =
				System.getProperty("tomcat.sessionid.randomclass");
			if (className != null) {
				try {
					Class randomClass = Class.forName(className);
					randomSource = (java.util.Random) randomClass.newInstance();
				} catch (Exception e) {
					 log.fatal("Imposible crear el identificador de la sesion", e);
				}
			}
			if (randomSource == null)
				randomSource = new java.security.SecureRandom();

			String entropyValue = getEntropy();
			if (entropyValue != null) {
				/*
				 *  We only do the manual seed generation if there is a user
				 * supplied entropy value.  This is only for backwards
				 * compatibility.  It is expected that very few people
				 * ever took advantage of this feature and defaulting
				 * to the normal PRNG seed generator is more secure than this
				 * calculation.
				 */
				long seed = System.currentTimeMillis();
				char enTropy[] = entropyValue.toCharArray();
				for (int i = 0; i < enTropy.length; i++) {
					long update = ((byte) enTropy[i]) << ((i % 8) * 8);
					seed ^= update;
				}
				randomSource.setSeed(seed);
			} else {
				randomSource.nextInt();
			}
		}
	}

}
