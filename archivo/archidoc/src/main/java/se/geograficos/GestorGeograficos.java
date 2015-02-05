package se.geograficos;

import java.util.List;
import java.util.Map;

import se.Parametrizable;
import se.geograficos.exceptions.GestorGeograficosException;

/**
 * Interfaz para la gestión de geográficos.
 */
public interface GestorGeograficos extends Parametrizable {
	public static final int PAIS = 0;
	public static final int PROVINCIA = 1;
	public static final int MUNICIPIO = 2;
	public static final int POBLACION = 3;
	public static final int[] tipos = new int[] { PAIS, PROVINCIA, MUNICIPIO,
			POBLACION };

	/**
	 * Recupera la lista de países ordenados por orden alfabético.
	 * 
	 * @return Lista de países ({@link Pais}).
	 * @throws GestorGeograficosException
	 *             si ocurre algún error.
	 */
	public List recuperarPaises() throws GestorGeograficosException;

	/**
	 * Recupera la lista de países ordenados por orden alfabético.
	 * 
	 * @param pattern
	 *            Patrón para la búsqueda por nombre.
	 * @return Lista de países ({@link Pais}).
	 * @throws GestorGeograficosException
	 *             si ocurre algún error.
	 */
	public List recuperarPaises(String pattern)
			throws GestorGeograficosException;

	/**
	 * Recupera el país.
	 * 
	 * @param codigo
	 *            Código del país.
	 * @return País.
	 * @throws GestorGeograficosException
	 *             si ocurre algún error.
	 */
	public Pais recuperarPais(String codigo) throws GestorGeograficosException;

	/**
	 * Recupera la lista de provincias de un país.
	 * 
	 * @param pais
	 *            País.
	 * @return Lista de provincias ({@link Provincia}).
	 * @throws GestorGeograficosException
	 *             si ocurre algún error.
	 */
	public List recuperarProvincias(Pais pais)
			throws GestorGeograficosException;

	/**
	 * Recupera la lista de provincias de un país.
	 * 
	 * @param pais
	 *            País.
	 * @param pattern
	 *            Patrón para la búsqueda por nombre.
	 * @return Lista de provincias ({@link Provincia}).
	 * @throws GestorGeograficosException
	 *             si ocurre algún error.
	 */
	public List recuperarProvincias(Pais pais, String pattern)
			throws GestorGeograficosException;

	/**
	 * Recupera la provincia.
	 * 
	 * @param pais
	 *            País.
	 * @param codigo
	 *            Código de la provincia.
	 * @return Provincia.
	 * @throws GestorGeograficosException
	 *             si ocurre algún error.
	 */
	public Provincia recuperarProvincia(Pais pais, String codigo)
			throws GestorGeograficosException;

	/**
	 * Recupera la lista de municipios de una provincia.
	 * 
	 * @param provincia
	 *            Provincia.
	 * @return Lista de municipios ({@link Municipio}).
	 * @throws GestorGeograficosException
	 *             si ocurre algún error.
	 */
	public List recuperarMunicipios(Provincia provincia)
			throws GestorGeograficosException;

	/**
	 * Recupera la lista de municipios de una provincia.
	 * 
	 * @param provincia
	 *            Provincia.
	 * @param pattern
	 *            Patrón para la búsqueda por nombre.
	 * @return Lista de municipios ({@link Municipio}).
	 * @throws GestorGeograficosException
	 *             si ocurre algún error.
	 */
	public List recuperarMunicipios(Provincia provincia, String pattern)
			throws GestorGeograficosException;

	/**
	 * Recupera el municipio.
	 * 
	 * @param provincia
	 *            Provincia.
	 * @param codigo
	 *            Código del municipio.
	 * @return Municipio.
	 * @throws GestorGeograficosException
	 *             si ocurre algún error.
	 */
	public Municipio recuperarMunicipio(Provincia provincia, String codigo)
			throws GestorGeograficosException;

	/**
	 * Recupera la lista de poblaciones de un municipio.
	 * 
	 * @param municipio
	 *            .
	 * @return Lista de poblaciones ({@link Poblacion}).
	 * @throws GestorGeograficosException
	 *             si ocurre algún error.
	 */
	public List recuperarPoblaciones(Municipio municipio)
			throws GestorGeograficosException;

	/**
	 * Recupera la lista de poblaciones de un municipio.
	 * 
	 * @param municipio
	 *            .
	 * @param pattern
	 *            Patrón para la búsqueda por nombre.
	 * @return Lista de poblaciones ({@link Poblacion}).
	 * @throws GestorGeograficosException
	 *             si ocurre algún error.
	 */
	public List recuperarPoblaciones(Municipio municipio, String pattern)
			throws GestorGeograficosException;

	/**
	 * Recupera la población.
	 * 
	 * @param municipio
	 *            Municipio.
	 * @param codigo
	 *            Código de la población.
	 * @return Población.
	 * @throws GestorGeograficosException
	 *             si ocurre algún error.
	 */
	public Poblacion recuperarPoblacion(Municipio municipio, String codigo)
			throws GestorGeograficosException;

	/**
	 * Indica si podemos realizar una busqueda extendida en elementos
	 * geograficos para buscar tal y como estaba antes o para buscar por el
	 * pattern sin depender de lo que halla introducio anteriormente.
	 * 
	 * @return boolean
	 */
	public boolean soportaBusquedaExtendida();

	/**
	 * Devuelve una lista de elementos geograficos con los resultados de la
	 * busqueda.
	 * 
	 * @param tipoBusqueda
	 * @param ids
	 * @param pattern
	 * @return {@link ElementoGeograficoVO}
	 * @throws GestorGeograficosException
	 */
	public List busquedaElementos(int tipoBusqueda, Map ids, String pattern)
			throws GestorGeograficosException;
}