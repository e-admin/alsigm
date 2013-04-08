package docelectronicos.db;

import java.util.List;

import common.db.IDBEntity;

import docelectronicos.vos.DocClasificadorVO;

/**
 * Entidad: <b>ADOCCLASIFDESCR</b>
 *
 * @author IECISA
 *
 */
public interface IDocClasifDescrDBEntity extends IDBEntity {
	/**
	 * Obtiene la lista de clasificadores de un descriptor.
	 *
	 * @param idDescr
	 *            Identificador del descriptor.
	 * @param idClfPadre
	 *            Identificador del clasificador padre.
	 * @param estados
	 *            Lista de estados.
	 * @return Listas de clasificadores.
	 */
	public List getClasificadores(String idDescr, String idClfPadre,
			int[] estados);

	public List getClasificadores(String idObj, int[] estados);

	/**
	 * Obtiene el clasificador.
	 *
	 * @param id
	 *            Identificador del clasificador.
	 * @return Clasificador.
	 */
	public DocClasificadorVO getClasificador(String id);

	/**
	 * Obtiene el clasificador padre del clasificador indicado.
	 *
	 * @param id
	 *            Identificador del clasificador.
	 * @return Clasificador padre.
	 */
	public DocClasificadorVO getClasificadorPadre(String id);

	/**
	 * Crea un clasificador de documentos.
	 *
	 * @param clasificador
	 *            Clasificador de documentos.
	 * @return Clasificador.
	 */
	public DocClasificadorVO insertClasificador(DocClasificadorVO clasificador);

	/**
	 * Modifica un clasificador de documentos.
	 *
	 * @param clasificador
	 *            Clasificador de documentos.
	 */
	public void updateClasificador(DocClasificadorVO clasificador);

	/**
	 * Elimina un clasificador de documentos.
	 *
	 * @param id
	 *            Identificador del clasificador de documentos.
	 */
	public void deleteClasificador(String id);

	/**
	 * Elimina los clasificadores de un descriptor.
	 *
	 * @param idDescr
	 *            Identificador del descriptor.
	 */
	public void deleteClasificadores(String idDescr);

	/**
	 *
	 * @param idObj
	 * @param estadosAActualizar
	 * @param nuevoEstado
	 */
	public void update(String idObj, int[] estadosAActualizar, int nuevoEstado);

	/**
	 * Devuelve el número de clasificadores hijos del padre indicado
	 *
	 * @param idDescr
	 *            Identificador del descriptor
	 * @param idClfPadre
	 *            Identificador del clasificador padre.
	 * @return Listas de clasificadores.
	 */
	public int getCountClasificadoresByIdClfPadre(String idDescr,
			String idClfPadre);

	/**
	 * Elimina los elemento cuyo <b>ADOCCLASIFDESCR.IDDESCR</b> sea igual a los
	 * idsDescriptores
	 *
	 * @param idDescriptor
	 *            Identificador del descriptor
	 * @param idsAReemplazar
	 *            Identificadores de los descriptores a reemplazar
	 */
	public void deleteDescriptores(String[] idsDescriptores);

	/**
	 * Obtiene el clasificador.
	 *
	 * @param nombre
	 *            Nombre del clasificador.
	 * @param idClfPadre
	 * 			  Identificador del clasificador padre
	 * @return Clasificador.
	 */
	public DocClasificadorVO getClasificadorByNombre(String idObjeto, String nombre, String idClfPadre);

}