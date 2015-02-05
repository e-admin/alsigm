// 
// FileName: AuthenticationFactory.java
//
package com.ieci.tecdoc.common.entity.dao;

import org.apache.log4j.Logger;

import com.ieci.tecdoc.common.keys.ConfigurationKeys;
import com.ieci.tecdoc.common.utils.Configurator;

/**
 * Patrón AbstractFactory.
 * 
 * @author lmvicente
 * @version @since @creationDate 24-mar-2004
 */

public class DBEntityDAOFactory {

    /*******************************************************************************************************************
     * Attributes
     ******************************************************************************************************************/

    private static final Logger log = Logger.getLogger(DBEntityDAOFactory.class);

    private static DBEntityDAO dbEntityDAO = null;

    /*******************************************************************************************************************
     * Constructors
     ******************************************************************************************************************/

    /*******************************************************************************************************************
     * Public methods
     ******************************************************************************************************************/

    public static DBEntityDAO getCurrentDBEntityDAO() throws Exception {
        return getDBEntityDAO(Configurator.getInstance().getProperty(ConfigurationKeys.KEY_SERVER_DAO_IMPLEMENTATION));
    }

    public static synchronized DBEntityDAO getDBEntityDAO(String dbEntityDAOClassName) throws Exception {
        if (dbEntityDAO == null) {
            try {
                Class authenticationClass = Class.forName(dbEntityDAOClassName);
                dbEntityDAO = (DBEntityDAO) authenticationClass.newInstance();
            } catch (ClassNotFoundException e) {
                log.fatal("Imposible instanciar [" + dbEntityDAOClassName + "].", e);
                throw new Exception(e);
            } catch (InstantiationException e) {
                log.fatal("Imposible instanciar [" + dbEntityDAOClassName + "].", e);
                throw new Exception(e);
            } catch (IllegalAccessException e) {
                log.fatal("Imposible instanciar [" + dbEntityDAOClassName + "].", e);
                throw new Exception(e);
            }
        }
        return dbEntityDAO;
    }

    /*******************************************************************************************************************
     * Protected methods
     ******************************************************************************************************************/

    /*******************************************************************************************************************
     * Private methods
     ******************************************************************************************************************/

    /*******************************************************************************************************************
     * Inner classes
     ******************************************************************************************************************/

    /*******************************************************************************************************************
     * Test brench
     ******************************************************************************************************************/

}