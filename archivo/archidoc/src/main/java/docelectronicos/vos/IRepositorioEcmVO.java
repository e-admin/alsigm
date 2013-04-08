package docelectronicos.vos;

import java.util.Properties;

public interface IRepositorioEcmVO {
	// public static final String REPOSITORIO_ECM_INVESDOC = "invesdoc";
	public static final String REPOSITORIO_ECM_ALFRESCO = "alfresco";
	public static final String REPOSITORIO_ECM_BD = "bd";
	public static final String REPOSITORIO_ECM_INTERNO = "interno";

	public String getId();

	public String getNombre();

	public String getTipo();

	public void setId(String id);

	public void setNombre(String nombre);

	public void setTipo(String tipo);

	public boolean isInterno();

	/**
	 * Obtiene el IdReal del elemento. En el caso de listas de volúmenes
	 * devuelve el id de la lista.
	 *
	 * @return
	 */
	public String getIdReal();

	/**
	 * Obtiene los parámetros
	 * @return
	 */
	public Properties getParametros();

}
