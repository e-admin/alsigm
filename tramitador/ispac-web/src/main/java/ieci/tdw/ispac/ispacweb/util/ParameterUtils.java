/*
 * invesflow Java - ISPAC
 * $Source: /TEST/SIGEM/ispacweb/src/ieci/tdw/ispac/ispacweb/util/ParameterUtils.java,v $
 * $Revision: 1.1 $
 * $Date: 2007/04/24 11:27:58 $
 * $Author: luismimg $
 *
 */
package ieci.tdw.ispac.ispacweb.util;

public class ParameterUtils
{

    public static int[] toIntArray(String params[])
    {
        int intParams[]=new int[params.length];
        for (int i = 0; i < params.length; i++)
        {
            intParams[i]=Integer.parseInt(params[i]);
        }
        return intParams;
    }
}
