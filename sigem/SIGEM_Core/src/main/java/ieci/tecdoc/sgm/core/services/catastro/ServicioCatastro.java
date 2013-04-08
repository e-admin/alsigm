package ieci.tecdoc.sgm.core.services.catastro;

public interface ServicioCatastro {
	
	/**
	 * Método que comprueba si una referencia catastral está dada de alta
	 * @param referenciaCatastral Valor de la referencia catastral
	 * @return Verdadero si está dada de alta la referencia castastral, Falso si no lo está
	 * @throws CatastroServicioException En caso de producirse algún error
	 */
	public  boolean validarReferenciaCatastral(String referenciaCatastral) throws CatastroServicioException;
	
	/**
	 * Método que obtiene la información asociada a una refrencia catastral
	 * @param referenciaCatastral Valor de la referencia catastral
	 * @return Datos de la referencia catastral
	 * @throws CatastroServicioException En caso de producirse algún error
	 */
	public  Parcelas consultarCatastro(String referenciaCatastral) throws CatastroServicioException;

}
