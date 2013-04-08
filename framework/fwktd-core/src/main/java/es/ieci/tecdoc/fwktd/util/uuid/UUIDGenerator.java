/*
 * $ID$
 * 
 * Copyright 2009 Informatica El Corte Ingles, S.A. 
 * Todos los derechos reservados.
 */
package es.ieci.tecdoc.fwktd.util.uuid;

import java.util.UUID;

/**
 * Clase de utilidad para la generación de UUID en base a la RFC 4122 (
 * {@link http://tools.ietf.org/html/rfc4122})
 * 
 * @author IECISA
 * 
 */
public class UUIDGenerator {

	private UUIDGenerator() {
	}

	/**
	 * 
	 * @return
	 */
	public static UUID generateRandomBasedUUID() {
		org.safehaus.uuid.UUID uuid = org.safehaus.uuid.UUIDGenerator
				.getInstance().generateRandomBasedUUID();

		return adaptUUID(uuid);
	}

	/**
	 * 
	 * @return
	 */
	public static UUID generateTimeBasedUUID() {
		org.safehaus.uuid.UUID uuid = org.safehaus.uuid.UUIDGenerator
				.getInstance().generateTimeBasedUUID();
		return adaptUUID(uuid);
	}

	/**
	 * 
	 * @return
	 */
	public static UUID generateNameBasedUUID() {
//		org.safehaus.uuid.UUID uuid = org.safehaus.uuid.UUIDGenerator.getInstance().generateTimeBasedUUID().
		return null;
	}

	/**
	 * 
	 * @param uuid
	 * @return
	 */
	protected static UUID adaptUUID(org.safehaus.uuid.UUID uuid) {
		return UUID.fromString(uuid.toString());
	}

}
