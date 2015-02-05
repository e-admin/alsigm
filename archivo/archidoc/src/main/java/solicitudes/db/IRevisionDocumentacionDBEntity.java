package solicitudes.db;

import java.util.List;

import solicitudes.prestamos.vos.RevisionDocumentacionVO;

import common.db.IDBEntity;
import common.exceptions.TooManyResultsException;

/**
 * Entidad: <b>ASGPREVDOCUDOC</b>
 * 
 * @author IECISA
 * 
 */
public interface IRevisionDocumentacionDBEntity extends IDBEntity {

	/**
	 * Inserta la revisión de la Documentación
	 * 
	 * @param revDocVO
	 */
	public void insertRevisionDocumentacion(RevisionDocumentacionVO revDocVO);

	/**
	 * Obtiene todas revisiones de documentación existentes.
	 * 
	 * @return Lista de {@link RevisionDocumentacionVO}
	 */
	public List getAllRevisionDocumentacion();

	/**
	 * Obtiene todas revisiones de documentación que tengan el estado pasado por
	 * parametro.
	 * 
	 * @param idUserGestor
	 *            String
	 * @param estado
	 *            int
	 * @return Lista de {@link RevisionDocumentacionVO}
	 */
	public List getRevisionDocumentacionByEstado(String idUserGestor, int estado);

	/**
	 * Obtiene la revisión cuyo id coincide con el que se le pasa
	 * 
	 * @param idRevDoc
	 *            Identificador de la revisión de la documentación
	 * @return {@link RevisionDocumentacionVO}
	 */
	public RevisionDocumentacionVO getRevisionDocumentacionById(String idRevDoc);

	/**
	 * Obtiene la revisión cuyo idAlta concide con el que se le pasa
	 * 
	 * @param idAlta
	 *            Identificador del alta de unidad documental
	 * @return {@link RevisionDocumentacionVO}
	 */
	public RevisionDocumentacionVO getRevisionDocumentacionByIdAlta(
			String idAlta);

	/**
	 * Obtiene las revisiones cuyo idUdoc concide con el que se le pasa y que
	 * están en cierto estado
	 * 
	 * @param idUdoc
	 *            Identificador de la unidad documental
	 * @param estados
	 *            Estados de revisiones
	 * @return Lista de {@link RevisionDocumentacionVO}
	 */
	public List getRevisionesDocumentacionByIdUdocYEstado(String idUdoc,
			int[] estados);

	/**
	 * Actualiza el registro
	 * 
	 * @param revDocVO
	 *            Objeto con los valores para actualizar
	 */
	public void update(RevisionDocumentacionVO revDocVO);

	/**
	 * Actualiza el Identificador del Alta al con el que está relacionado.
	 * 
	 * @param idRevDoc
	 *            Identificador de la Revisión de Documentación
	 * @param idAlta
	 *            Identficador del Alta de Unidades Documentales.
	 */
	public void updateIdAlta(String idRevDoc, String idAlta);

	/**
	 * Establece a Null el valor de IdAlta.
	 * 
	 * @param idAlta
	 *            Identificador del Alta.
	 */
	public void removeIdAlta(String idAlta);

	/**
	 * Devuelve el número de documentacion a revisar en unos estados
	 * determinados.
	 * 
	 * @param idUser
	 *            String
	 * @param estados
	 *            int[]
	 * @return Número de documentacion en revision en estados determinados
	 */
	public int getCountRevisionDocXEstados(String idUser, int[] estados);

	List getGestoresConRevisionesDocumentacion();

	List getRevisionesDocumentacion(RevisionDocumentacionVO revDocVo)
			throws TooManyResultsException;
}