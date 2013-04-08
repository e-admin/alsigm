package es.ieci.tecdoc.fwktd.core.exception;

import org.springframework.validation.ObjectError;

/*
 * 
 */


/**
 * Interfaz que deben implementar las excepciones internacionalizables
 * 
 */
public interface ManageableException {

	/**
	 * Enumerado con los tipos de errores del mensaje de la excepcion.
	 */
	public static enum LevelEnum {
		/**
		 * Máximo nivel de Error
		 */
		ERROR,
		/**
		 * Aviso
		 */
		WARN,
		/**
		 * Mensaje informativo
		 */
		INFO
	}

	/**
	 * Objeto asociado al error. Almacena el código del error, los parametros
	 * del error y un mensaje por defecto en caso de no poder resolver su código
	 * 
	 * @return ObjectError
	 */
	ObjectError getError();

	/**
	 * Tipo de mensaje de la excepción
	 * 
	 * @return nivel de error
	 */
	LevelEnum getLevel();
}