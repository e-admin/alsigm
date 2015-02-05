package ieci.tdw.ispac.ispacweb.components;

import java.util.Map;

import ieci.tdw.ispac.api.ISessionAPI;
import ieci.tdw.ispac.api.errors.ISPACException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

/**
 * Interfaz para los componentes web.
 *
 */
public interface IWebComponent {

	/**
	 * Obtiene el identificador del componente.
	 * @return Identificador del componente.
	 */
	public String getId();

	/**
	 * Establece el identificador del componente.
	 * @param id Identificador del componente.
	 */
	public void setId(String id);

	/**
	 * Obtiene el título del componente.
	 * @return Título del componente.
	 */
	public String getTitle();

	/**
	 * Obtiene el contenido del componente.
	 * @return Contenido del componente.
	 */
	public String getContent();
	
	/**
	 * Obtiene los parámetros del componente.
	 * @return Parámetros del componente.
	 */
	public Map getParameters();

	/**
	 * Obtiene el valor de un parámetro del componente.
	 * @param key Clave del parámetro.
	 * @return Valor del parámetro.
	 */
	public Object getParameter(Object key);

	/**
	 * Esteblece el valor de un parámetro del componente.
	 * @param key Clave del parámetro.
	 * @param value Valor del parámetro.
	 */
	public void putParameter(Object key, Object value);

	
	/**
	 * Renderiza el componente.
	 * @param context Contexto de servlets.
	 * @param request Petición del cliente.
	 * @param sessionAPI API de sesión.
	 * @param params Parámetros de configuración.
	 * @return Código HTML para mostrar en pantalla.
	 * @throws ISPACException si ocurre algún error.
	 */
    public void render(ServletContext context, HttpServletRequest request, ISessionAPI sessionAPI) 
    		throws ISPACException; 
	
}
