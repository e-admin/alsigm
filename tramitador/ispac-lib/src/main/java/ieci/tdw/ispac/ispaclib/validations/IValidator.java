package ieci.tdw.ispac.ispaclib.validations;


public interface IValidator {

	
	/**
	 * 
	 * @param objeto Objeto a validar
	 * @return cierto si esta en el formato correcto en funcion del tipo de validacion que la instancia
	 */
	
	public boolean validate(Object objeto);
	
	
}
