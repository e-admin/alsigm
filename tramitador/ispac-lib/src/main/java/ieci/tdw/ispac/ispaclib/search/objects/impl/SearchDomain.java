/*
 * Created on Jan 4, 2005 by IECISA
 *
 */
package ieci.tdw.ispac.ispaclib.search.objects.impl;

import java.util.HashSet;


public class SearchDomain
{
	/**
	 * Todos los expedientes
	 */
	public static final int ALLEXP = 1;
	
	/**
	 * Solo los expedientes de mi responsabilidad
	 */
	public static final int ONLYMYEXP = 2;
	
	/**
	 * Domimios validos
	 */
	public static HashSet DOMAINS = new HashSet ();	
	static
	{
		DOMAINS.add(new Integer(ALLEXP));
		DOMAINS.add(new Integer(ONLYMYEXP));
	}
	
	/**
	 * Dice si es un dominio valido o no
	 */
	public static boolean isDomain (int domain)
	{
		return DOMAINS.contains(new Integer(domain));
	}
}

