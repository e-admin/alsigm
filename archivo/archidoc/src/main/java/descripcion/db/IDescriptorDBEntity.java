package descripcion.db;

import java.util.List;
import java.util.Map;

import se.usuarios.GenericUserInfo;

import common.db.IDBEntity;
import common.exceptions.TooManyResultsException;
import common.pagination.PageInfo;

import descripcion.vos.BusquedaDescriptoresVO;
import descripcion.vos.BusquedaGeneralAutVO;
import descripcion.vos.DescriptorVO;

/**
 * Interfaz de comportamiento para la entidad de acceso a los descriptores.
 * Entidad: <b>ADDESCRIPTOR</b>
 */
public interface IDescriptorDBEntity extends IDBEntity {

	/**
	 * Obtiene la información de un descriptor.
	 * 
	 * @param idDescriptor
	 *            Identificador del descriptor.
	 * @return Descriptor.
	 */
	public DescriptorVO getDescriptor(String idDescriptor);

	/**
	 * Obtiene descriptores por lista acceso
	 * 
	 * @param idListaAcceso
	 * @return
	 */
	public List getDescriptoresXListaAcceso(String idListaAcceso);

	/**
	 * Obtiene la información de un descriptor con información extendida.
	 * 
	 * @param serviceClient
	 *            Cliente del servicio.
	 * @param idDescriptor
	 *            Identificador del descriptor.
	 * @return Descriptor.
	 */
	public DescriptorVO getDescriptorExt(GenericUserInfo serviceClient,
			String idDescriptor);

	/**
	 * Obtiene la información de un descriptor.
	 * 
	 * @param idListaDescriptora
	 *            Identificador de la lista descriptora.
	 * @param nombre
	 *            Nombre del descriptor.
	 * @return Descriptor.
	 */
	public DescriptorVO getDescriptor(String idListaDescriptora, String nombre);

	/**
	 * Obtiene la información de un descriptor.
	 * 
	 * @param idListaDescriptora
	 *            Identificador de la lista descriptora.
	 * @param idDescrSistExt
	 *            Identificador del descriptor en el sistema externo
	 * @return Descriptor.
	 */
	public DescriptorVO getDescriptorByIdDescr(String idListaDescriptora,
			String idDescrSistExt);

	/**
	 * Obtiene la lista de descriptores de una lista descriptora.
	 * 
	 * @param serviceClient
	 *            Cliente del servicio.
	 * @param idListaDescriptora
	 *            Identificador de la lista descriptora.
	 * @return Lista de descriptores.
	 */
	public List getDescriptores(GenericUserInfo serviceClient,
			String idListaDescriptora);

	/**
	 * Recupera los descriptores cuyo valor contiene el patrón de búsqueda
	 * indicado
	 * 
	 * @param pattern
	 *            Patrón que debe contener el valor de los descriptores
	 * @param idListaDescriptora
	 *            Identificador de la lista descriptora a la que deben
	 *            pertenecer los descriptores. Pude ser nulo.
	 * @return Conjunto de descriptores que verifican las condiciones
	 *         {@link DescriptorVO}
	 */
	public List findDescriptores(String pattern, String idListaDescriptora);

	/**
	 * Obtiene la lista de descriptores de una lista descriptora con información
	 * extendida.
	 * 
	 * @param serviceClient
	 *            Cliente del servicio.
	 * @param idListaDescriptora
	 *            Identificador de la lista descriptora.
	 * @param pageInfo
	 *            Información de la paginación.
	 * @return Lista de descriptores.
	 * @throws TooManyResultsException
	 *             si el número de resultados excede el máximo.
	 */
	public List getDescriptoresExt(GenericUserInfo serviceClient,
			String idListaDescriptora, PageInfo pageInfo)
			throws TooManyResultsException;

	/**
	 * Obtiene una lista de descriptores a partir de su nombre.
	 * 
	 * @param nombre
	 *            Nombre del descriptor.
	 * @return Lista de descriptores.
	 */
	public List getDescriptoresXNombre(String nombre);

	/**
	 * Obtiene una lista de descriptores a partir de su nombre y id de lista.
	 * 
	 * @param nombre
	 *            Nombre del descriptor, id de la lista.
	 * @return {@link DescriptorVO}
	 */
	public DescriptorVO getDescriptorXNombreYIdLista(String nombre,
			String idLista);

	/**
	 * Busca los descriptores en función de unos criterios.
	 * 
	 * @param serviceClient
	 *            Cliente del servicio.
	 * @param criterios
	 *            Criterios de búsqueda.
	 * @return Lista de descriptores.
	 * @throws TooManyResultsException
	 *             si el número de resultados es excesivo.
	 */
	public List getDescriptores(GenericUserInfo serviceClient,
			BusquedaDescriptoresVO criterios) throws TooManyResultsException;

	/**
	 * Inserta un descriptor.
	 * 
	 * @param descriptor
	 *            Descriptor.
	 * @return Descriptor insertado.
	 */
	public DescriptorVO insertDescriptorVO(final DescriptorVO descriptorVO);

	/**
	 * Modifica un descriptor.
	 * 
	 * @param id
	 *            Identificador del descriptor.
	 * @param columnsToUpdate
	 *            Columnas a modificar.
	 */
	public void updateDescriptorVOXId(String id, Map columnsToUpdate);

	/**
	 * Modifica un descriptor.
	 * 
	 * @param descriptor
	 *            Descriptor.
	 */
	public void updateDescriptorVO(DescriptorVO descriptorVO);

	/**
	 * Establece si un descriptor tiene ficha de descripción.
	 * 
	 * @param idDescr
	 *            Identificador del descriptor.
	 * @param tieneDescr
	 *            Si el descriptor tiene ficha de descripción.
	 */
	public void setTieneDescr(String idDescr, String tieneDescr);

	/**
	 * Establece si se han creado los clasificadores por defecto de un
	 * descriptor.
	 * 
	 * @param idDescr
	 *            Identificador del descriptor.
	 * @param editClfDocs
	 *            Si se han creado los clasificadores por defecto.
	 */
	public void setEditClfDocs(String idDescr, String editClfDocs);

	/**
	 * Elimina un descriptor.
	 * 
	 * @param id
	 *            Identificador del descriptor.
	 */
	public void deleteDescriptor(String id);

	/**
	 * Elimina los descriptores.
	 * 
	 * @param listaIds
	 *            Lista de identificadores de descriptores.
	 */
	public void deleteDescriptores(String[] listaIds);

	/**
	 * Realiza la búsqueda de autoridades en función de los parámetros del
	 * formulario de búsquedas.
	 * 
	 * @param serviceClient
	 *            Cliente del servicio.
	 * @param busqueda
	 *            Parámetros del formulario de búsquedas.
	 * @return Lista de autoridades.
	 * @throws TooManyResultsException
	 *             si el número de resultados es excesivo.
	 */
	public List getAutoridades(GenericUserInfo serviceClient,
			BusquedaGeneralAutVO busqueda) throws TooManyResultsException;

	/**
	 * Recupera el descriptor de una determinada lista de descriptores a partir
	 * del identificador de ese descriptor en el sistema externo desde el que
	 * fue importado
	 * 
	 * @param idEnSistemaExterno
	 *            Identificador del descriptor en el sistema externo del que fue
	 *            importado
	 * @param listaDescriptores
	 *            Identificador de la lista de descriptores a la que debe
	 *            pertenecer el descriptor
	 * @return Datos del descriptor
	 */
	public DescriptorVO getDescriptorXIdEnSistemaExterno(
			String idEnSistemaExterno, String listaDescriptores);

	/**
	 * Obtiene los Descriptores de Productores de Relaciones de Entrega /Altas
	 * de Unidades Documentales
	 * 
	 * @param tiposRelacion
	 *            Tipos de Relacion
	 * @return Lista de
	 */
	public List getDescriptoresProductoresRelacion(
			BusquedaDescriptoresVO busquedaVO, Integer[] tiposRelacion)
			throws TooManyResultsException;

	/**
	 * Actualiza el estado los descriptores seleccionados
	 * 
	 * @param ids
	 */
	public void updateEstado(int estado, String[] ids);

	/**
	 * Obtiene los datos del descriptor que es productor de una serie
	 * 
	 * @param idSerie
	 *            Cadena que contiene el identificador de la serie.
	 * @param idLista
	 *            Cadena que contiene el identificador de la lista a la que
	 *            pertenece el descriptor
	 * @param codigoOrgano
	 *            Cadena que contiene el código del órgano
	 * @return {@link DescriptorVO}
	 */
	public DescriptorVO getProductorSerieByCodigoOrgano(String idSerie,
			String idLista, String codigoOrgano);

	/**
	 * Obtiene los datos del descriptor que es productor de una serie
	 * 
	 * @param idSerie
	 *            Cadena que contiene el identificador de la serie.
	 * @param idLista
	 *            Cadena que contiene el identificador de la lista a la que
	 *            pertenece el descriptor
	 * @param nombreOrgano
	 *            Cadena que contiene el nombre del órgano
	 * @return {@link DescriptorVO}
	 */
	public DescriptorVO getProductorSerieByNombreOrgano(String idSerie,
			String idLista, String nombreOrgano);
}