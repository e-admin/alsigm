package docelectronicos.db;

import java.util.List;

import common.db.IDBEntity;

import docelectronicos.vos.DocClasificadorVO;

/**
 * Entidad: <b>ADOCCLASIFCF</b>
 *
 * @author IECISA
 *
 */
public interface IDocClasifCFDBEntity extends IDBEntity {
	/**
	 * Obtiene la lista de clasificadores de un elemento del cuadro de
	 * clasificación.
	 *
	 * @param idElementoCF
	 *            Identificador del elemento del cuadro de clasificación.
	 * @param idClfPadre
	 *            Identificador del clasificador padre.
	 * @param estados
	 *            Lista de estados.
	 * @return Listas de clasificadores.
	 */
	public List getClasificadores(String idElementoCF, String idClfPadre,
			int[] estados);

	public List getClasificadores(String idElementoCF, int[] estados);

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
	 * Elimina los clasificadores de un elemento del cuadro de clasificación.
	 *
	 * @param idElementoCF
	 *            Identificador del elemento del cuadro de clasificación.
	 */
	public void deleteClasificadores(String idElementoCF);

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
	 * @param idElementoCF
	 *            Identificador del elemento del cuadro de clasificación.
	 * @param idClfPadre
	 *            Identificador del clasificador padre.
	 * @return Listas de clasificadores.
	 */
	public int getCountClasificadoresByIdClfPadre(String idElementoCF,
			String idClfPadre);


	/**
	 * Obtiene el clasificador por su nombre
	 *
	 * @param idObjeto
	 * 			Cadena que contiene la identifiación del objeto al que hace referencia.
	 * @param nombre
	 *            Nombre del clasificador.
	 * @param idClfPadre
	 * @return Clasificador.
	 */
	public DocClasificadorVO getClasificadorByNombre(String idObjeto, String nombre, String idClfPadre);

	public void updateIdElementocf(String idElementocfAntiguo,
			String idElementoCfNuevo, String[] idsClasificadores);
}