/*
 * Created on 26-abr-2005
 *
 */
package ieci.tdw.ispac.ispaclib.util;

/**
 * @author juanin
 *
 */
public final class PrefixBuilder
{
    static public final String PREFIX_ISPAC = ":";
    static public final String PREFIX_DB 	= ".";

    static public String buildPrefix(String prefix)
    {
        return prefix+PREFIX_ISPAC;
    }

    static public String buildRawPrefix(String prefix)
    {
         return prefix.replace(':','_');
    }

    static public String buildDBPrefix(String prefix)
    {
         return prefix.replace(':','_')+PREFIX_DB;
    }


    static public String buildName(String prefix,String name)
    {
        return prefix+PREFIX_ISPAC+name;
    }

    static public String buildRawName(String prefix,String name)
    {
         return prefix.replace(':','_')+PREFIX_DB+name;
    }

}
