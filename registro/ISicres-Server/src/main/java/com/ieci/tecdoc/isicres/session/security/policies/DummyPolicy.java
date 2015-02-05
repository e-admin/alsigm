//
// FileName: DummyPolicy.java
//
package com.ieci.tecdoc.isicres.session.security.policies;

import com.ieci.tecdoc.common.AuthenticationPolicy;
import com.ieci.tecdoc.common.AuthenticationUser;
import com.ieci.tecdoc.common.exception.SecurityException;
import com.ieci.tecdoc.common.exception.ValidationException;

/**
 * @author lmvicente
 * @version @since @creationDate 24-mar-2004
 */

public class DummyPolicy implements AuthenticationPolicy {

    /*******************************************************************************************************************
     * Attributes
     ******************************************************************************************************************/

    private static int index = 0;

    /*******************************************************************************************************************
     * Constructors
     ******************************************************************************************************************/

    public DummyPolicy() {
    }

    /*******************************************************************************************************************
     * Public methods
     ******************************************************************************************************************/

    public AuthenticationUser validate(String login, String password, String entidad) throws SecurityException, ValidationException {
        index++;
        if (index % 2 == 0) {
            throw new SecurityException(SecurityException.ERROR_USER_NOTFOUND, new String[] { login});
        }

        return null;
    }
    public AuthenticationUser validate(String userDn, String entidad) throws SecurityException, ValidationException {
        index++;
        if (index % 2 == 0) {
            throw new SecurityException(SecurityException.ERROR_USER_NOTFOUND, new String[] { userDn});
        }

        return null;
    }

    public void changePassword(String login, String newPassword, String oldPassword, String entidad) throws SecurityException,
            ValidationException {
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