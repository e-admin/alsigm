/*
 * 
 */
package es.ieci.tecdoc.fwktd.util;

import org.springframework.validation.ObjectError;

import es.ieci.tecdoc.fwktd.core.exception.ManageableException;
import es.ieci.tecdoc.fwktd.core.exception.ManageableException.LevelEnum;

/**
 * Clase de utilidad para obtener un <code> ObjectError </code> para
 * internacionalizar el mensaje asociado a una excepción. Si la excepción es
 * manageable ya lo contiene, en caso contrario se compone con el nombre
 * cualificado de la excepción y como parámetro el mensaje de la misma.
 * 
 */
public class ErrorUtils {

	/**
	 * Nivel de error de la excepción. Si es manageable devuelve el nivel de la
	 * manageable en caso contrario ERROR.
	 * 
	 * @param ex
	 *            Excepción
	 * @return Nivel de error de la excepción
	 */
	public static LevelEnum getLevel(Exception ex) {
		if (ex instanceof ManageableException) {
			ManageableException manageableEx = (ManageableException) ex;
			return manageableEx.getLevel();
		} else {
			return LevelEnum.ERROR;
		}
	}

	/**
	 * ObjectError de la Excepción
	 * 
	 * @param ex
	 *            Excepción producida
	 * @return ObjectError de la Excepción
	 */
	public static ObjectError getObjectError(Exception ex) {
		if (ex instanceof ManageableException) {
			ManageableException manageableEx = (ManageableException) ex;
			return manageableEx.getError();
		} else {
			ObjectError error = new ObjectError(ex.getClass().getName(),
					new String[] { ex.getMessage() }, new String[0], ex
							.getLocalizedMessage());
			return error;
		}
	}

	/**
	 * ObjectError de la Excepción
	 * 
	 * @param objectId
	 *            Nombre del objeto asociado a la excepcion
	 * @param ex
	 *            Excepcion producida
	 * @return ObjectError
	 */
	public static ObjectError getObjectError(String objectId, Exception ex) {
		ObjectError exError = getObjectError(ex);
		return new ObjectError(objectId, exError.getCodes(), exError
				.getArguments(), exError.getDefaultMessage());
	}

}