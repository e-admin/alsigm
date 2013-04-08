package ieci.tdw.ispac.ispacweb.tag.context;

public interface IStaticContext {
	
	/**
	 * Obtiene la url base para los elementos estáticos de la presentación
	 * a partir del contexto de la aplicación.
	 * 
	 * @param context Contexto de la aplicación.
	 * @return Url base para los elementos estáticos de la presentación.
	 */
	public String getBaseUrl(String context);

}