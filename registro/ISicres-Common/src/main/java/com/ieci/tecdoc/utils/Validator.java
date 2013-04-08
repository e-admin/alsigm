// 
// FileName: Validator.java
//
package com.ieci.tecdoc.utils;

import org.apache.log4j.Logger;

import com.ieci.tecdoc.common.exception.ValidationException;

/**
 * @author lmvicente
 * @version @since @creationDate 29-mar-2004
 */

public class Validator {

	/***************************************************************************************************************************************
	 * Attributes
	 **************************************************************************************************************************************/
	private static final Logger log = Logger.getLogger(Validator.class);

	/***************************************************************************************************************************************
	 * Constructors
	 **************************************************************************************************************************************/

	/***************************************************************************************************************************************
	 * Public methods
	 **************************************************************************************************************************************/

    public static void validate_NotNull(Object object, String attributeName) throws ValidationException {
        if (object == null) {
            throw new ValidationException(ValidationException.ERROR_ATTRIBUTE_VALIDATION, new String[] { attributeName });
        }
    }
    
	/**
	 * Valida que un parámetro no sea null y su tamaño mayor que cero.
	 * @param value
	 * @param attributeName
	 * @throws ValidationException
	 */
	public static void validate_String_NotNull_LengthMayorZero(String value, String attributeName) throws ValidationException {
		validate_String_NotNull(value, attributeName);
		if (value.length() == 0) {
			throw new ValidationException(ValidationException.ERROR_ATTRIBUTE_VALIDATION, new String[] { attributeName });
		}
	}

	public static void validate_Integer(Integer value, String attributeName) throws ValidationException {
		validate_NotNull(value, attributeName);
		try {
			new Integer(value.toString());
		} catch (Exception e) {
			throw new ValidationException(ValidationException.ERROR_ATTRIBUTE_VALIDATION, new String[] { attributeName });
		}
	}
	
	/**
	 * Valida que un parámetro no sea null y su tamaño mayor (>) que la longitud pasada por parámetro.
	 * @param value
	 * @param attributeName
	 * @param length
	 * @throws ValidationException
	 */
	public static void validate_String_NotNull_WithLength(String value, String attributeName, int length) throws ValidationException {
		validate_String_NotNull(value, attributeName);
		if (value.length() > length) {
			throw new ValidationException(ValidationException.ERROR_ATTRIBUTE_VALIDATION, new String[] { attributeName });
		}
	}
	
	/**
	 * Valida que un parámetro no sea null.
	 * @param value
	 * @param attributeName
	 * @throws ValidationException
	 */
	public static void validate_String_NotNull(String value, String attributeName) throws ValidationException {
		validate_NotNull(value, attributeName);
	}

	/***************************************************************************************************************************************
	 * Protected methods
	 **************************************************************************************************************************************/

	/***************************************************************************************************************************************
	 * Private methods
	 **************************************************************************************************************************************/
	
	/***************************************************************************************************************************************
	 * Inner classes
	 **************************************************************************************************************************************/

	/***************************************************************************************************************************************
	 * Test brench
	 **************************************************************************************************************************************/

}
