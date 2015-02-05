//
// FileName.: JettyIDGenerator.java
// @DISCLAIMER_CLAUSE@
package com.ieci.tecdoc.utils.cache.idgenerator;

import java.util.Random;



/**
 * IDGenerator implementation from the Jetty web server algorithm.
 *
 * @author           @AUTHOR_NAME@
 * @version          @VERSION@
 * @internalAuthor   Luis Miguel Vicente Mosquera
 * @since            1.0
 * @lastUpdate       24/September/2002
 */
public class JettyIDGenerator implements IDGenerator {

	//-------------------
	// Private attributes
	//-------------------

	private Random _random;

	//-------------
	// Constructors
	//-------------

	public JettyIDGenerator() {
		_random = new Random();
	}

	//---------------
	// Public methods
	//---------------

	/** @inheritAll */
	public String getSessionID() {
		return newSessionId(System.currentTimeMillis());
	}

	//----------------
	// Private methods
	//----------------

	private String newSessionId(long created) {
		String id = null;
		long r = _random.nextLong();
		if (r < 0)
			r = -r;
		id = Long.toString(r, 30 + (int) (created % 7));

		return id;
	}

}
