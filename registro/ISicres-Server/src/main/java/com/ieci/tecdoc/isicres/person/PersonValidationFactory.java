/**
 * 
 */
package com.ieci.tecdoc.isicres.person;

import org.apache.log4j.Logger;

import com.ieci.tecdoc.common.adapter.PersonValidation;
import com.ieci.tecdoc.common.keys.ConfigurationKeys;
import com.ieci.tecdoc.common.utils.Configurator;

/**
 * @author 79426599
 * 
 * Patrón AbstractFactory
 */
public class PersonValidationFactory {
    /*******************************************************************************************************************
     * Attributes
     ******************************************************************************************************************/

    private static final Logger log = Logger.getLogger(PersonValidationFactory.class);

    private static PersonValidation personValidation = null;

    /*******************************************************************************************************************
     * Constructors
     ******************************************************************************************************************/

    /*******************************************************************************************************************
     * Public methods
     ******************************************************************************************************************/

    public static PersonValidation getCurrentPersonValidation() throws Exception {
        return getPersonValidation(Configurator.getInstance().getProperty(ConfigurationKeys.KEY_SERVER_PERSON_VALIDATION_IMPLEMENTATION));
    }

    public static synchronized PersonValidation getPersonValidation(String personValidationClassName) throws Exception {
        if (personValidation == null) {
            try {
                Class authenticationClass = Class.forName(personValidationClassName);
                personValidation = (PersonValidation) authenticationClass.newInstance();
            } catch (ClassNotFoundException e) {
                log.fatal("Imposible instanciar [" + personValidationClassName + "].", e);
                throw new Exception(e);
            } catch (InstantiationException e) {
                log.fatal("Imposible instanciar [" + personValidationClassName + "].", e);
                throw new Exception(e);
            } catch (IllegalAccessException e) {
                log.fatal("Imposible instanciar [" + personValidationClassName + "].", e);
                throw new Exception(e);
            }
        }
        return personValidation;
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
