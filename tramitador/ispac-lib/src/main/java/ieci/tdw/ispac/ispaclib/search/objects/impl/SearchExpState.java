/*
 * Created on Jan 4, 2005 by IECISA
 *
 */
package ieci.tdw.ispac.ispaclib.search.objects.impl;

import java.util.HashSet;


public class SearchExpState
{
	/**
	 * Todos los expedientes
	 */
	public final static int ALL = 0;

	/**
	 * Expedientes activos
	 */
	public final static int ACTIVE  = 1;
	/**
	 * Expedientes finalizados
	 */
	public final static int END = 2;

	/**
	 * Expedientes archivador
	 */
	public final static int ARCHIVE = 4;

	/**
	 * Estados de expediente válidos
	 */
	public static HashSet STATES = new HashSet ();
	static
	{
		STATES.add(new Integer(ALL));
		STATES.add(new Integer(ACTIVE));
		STATES.add(new Integer(END));
		STATES.add(new Integer(ARCHIVE));
	}

	/**
	 * Dice si es un dominio valido o no
	 */
	public static boolean isDomain (int domain)
	{
		return STATES.contains(new Integer(domain));
	}
}

