package es.ieci.tecdoc.isicres.admin.core.ldap;

/**
 * Clase que obtiene la informacion del usuario administrador para LDAP
 * en el caso de que no exista ninguno en BBDD, mediante un fichero xml.
 */


import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import es.ieci.tecdoc.isicres.admin.core.ConfigFilePathResolverIsicresAdmin;
import es.ieci.tecdoc.isicres.admin.core.file.FileManager;
import es.ieci.tecdoc.isicres.admin.core.xml.lite.XmlTextParser;
import es.ieci.tecdoc.isicres.admin.sbo.config.CfgMisc;

public final class LdapUserConfigUtil {

    private static final Logger log = Logger.getLogger(LdapUserConfigUtil.class);

    private static final String IECI_LDAP_CFG_USER_ADMIN = "ISicresAdmin-Core/ISicresUserAdminLDAP.xml";

    private static final String EN_ROOT                 = "UserAdmin";
    private static final String USER					= "User";
    private static final String DN_USER_ADMIN			= "Dn";



    private LdapUserConfigUtil()
    {
    }

	/**
	 * Metodo que obtiene la informacion del fichero de configuracion del
	 * usuario administrador de ldap
	 *
	 * @return
	 * @throws Exception
	 */
	public static String getFileConfUserLDAPAdmin() throws Exception {

		LdapConnCfg connCfg = null;
		String fileLoc;
		String fileText = null;
		String fileName;

		fileName = IECI_LDAP_CFG_USER_ADMIN;

		//fileLoc = CfgMisc.getConfigFilePath(fileName);
        ConfigFilePathResolverIsicresAdmin pathResolver = ConfigFilePathResolverIsicresAdmin.getInstance();
        //obtenemos el path del fichero
        fileLoc = pathResolver.resolveFullPath(fileName);

		try {
			//si el path del fichero es distinto de nulo y ademas no esta dentro de un jar
			if (fileLoc != null && !StringUtils.contains(fileLoc, ".jar!")){
				fileText = FileManager.readStringFromFile(fileLoc);
			}
			else{
				fileText = FileManager.readStringFromResourceFile(fileName);
			}

		} catch (Exception e) {
			if (log.isDebugEnabled())
				log.debug("Leyendo fichero de configuración: " + e.getMessage());

			fileText = null;
		}

		return fileText;
	}

	public static String getUserLDAPAdmin(){
		XmlTextParser xtp;
		String value = null;

		try {
			String fileText = getFileConfUserLDAPAdmin();

			if(StringUtils.isNotBlank(fileText)){
				xtp = new XmlTextParser();
				xtp.createFromStringText(fileText);

					xtp.selectElement(EN_ROOT);

					value = readElementValue(xtp, USER);
					if(StringUtils.isNotBlank(value)){
						return value;
					}
			}

		}catch (Exception e) {
	          if (log.isDebugEnabled())
	              log.debug("LdapConfigUtil.getUserLDAPAdmin: " + e.getMessage());
		}
		return value;
	}

	public static String getDnUserLdapAdmin(){
		XmlTextParser xtp;
		String value = null;

		try {
			String fileText = getFileConfUserLDAPAdmin();

			if(StringUtils.isNotBlank(fileText)){
				xtp = new XmlTextParser();
				xtp.createFromStringText(fileText);

					xtp.selectElement(EN_ROOT);

					value = readElementValue(xtp, DN_USER_ADMIN);
					if(StringUtils.isNotBlank(value)){
						return value;
					}
			}

		}catch (Exception e) {
	          if (log.isDebugEnabled())
	              log.debug("LdapConfigUtil.getUserLDAPAdmin: " + e.getMessage());
		}
		return value;
	}

	private static String readElementValue(XmlTextParser xtp, String elementName)
	{
		String value = null;

		try {
			xtp.selectElement(elementName);
			value = xtp.getElementValue();
		} catch (Exception e) {
			if (log.isDebugEnabled())
				log.debug("LdapConfigUtil.readElementValue: " + e.getMessage());

		}

		return value;

	}

}
