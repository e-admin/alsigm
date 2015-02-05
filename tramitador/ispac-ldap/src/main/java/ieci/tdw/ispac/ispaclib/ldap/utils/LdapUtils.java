package ieci.tdw.ispac.ispaclib.ldap.utils;

public class LdapUtils {

	public static String getParentDN(String dn) {
		
		int posFirstEqual = dn.indexOf('=');
		String firstEqualInDn = dn.substring(posFirstEqual + 1, dn.length());
		
		int posSecondEqual = firstEqualInDn.indexOf('=');
		String name = firstEqualInDn.substring(0, posSecondEqual);
		int posLastComa = name.lastIndexOf(',');
		
		return dn.substring(posFirstEqual + 1 + posLastComa + 1, dn.length());
	}
}
