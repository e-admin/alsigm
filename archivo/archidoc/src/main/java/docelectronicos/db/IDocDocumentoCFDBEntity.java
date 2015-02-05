package docelectronicos.db;

import java.util.List;
import java.util.Map;

import common.db.IDBEntity;

import docelectronicos.vos.DocDocumentoVO;

/**
 * Entidad: <b>ADOCDOCUMENTOCF</b>
 *
 * @author IECISA
 *
 */
public interface IDocDocumentoCFDBEntity extends IDBEntity {
	/**
	 * Obtiene la lista de documentos electrónicos de un elemento del cuadro de
	 * clasificación.
	 *
	 * @param idElementoCF
	 *            Identificador del elemento del cuadro de clasificación.
	 * @param idClfPadre
	 *            Identificador del clasificador padre.
	 * @param estados
	 *            Lista de estados de los documentos.
	 * @return Listas de documentos electrónicos.
	 */

	public List getDocumentos(String idElementoCF, String idClfPadre,
			int[] estados);

	public List getDocumentos(String idElementoCF, int[] estados);

	/**
	 * Cuenta el número de documentos asociados a un elemento del cuadro de
	 * clasificación
	 *
	 * @param idElementoCF
	 *            Identificador de elemento del cuadro de clasificación
	 * @return Número de documentos asociados al elemento
	 */
	public int countNumDocumentos(String idElementoCF);

	/**
	 * Obtiene la lista de documentos electrónicos de un elemento del cuadro de
	 * clasificación.
	 *
	 * @param idElementoCF
	 *            Identificador del elemento del cuadro de clasificación.
	 * @return Listas de documentos electrónicos.
	 */
	public List getDocumentos(String idElementoCF);

	/**
	 * Obtiene el documento electrónico.
	 *
	 * @param id
	 *            Identificador del documento electrónico.
	 * @return Documento electrónico.
	 */
	public DocDocumentoVO getDocumento(String id);

	/**
	 * Crea un documento electrónico.
	 *
	 * @param documento
	 *            Documento electrónico.
	 * @return Documento.
	 */
	public DocDocumentoVO insertDocumento(DocDocumentoVO documento);

	/**
	 * Modifica un documento electrónico.
	 *
	 * @param documento
	 *            Documento electrónico.
	 */
	public void updateDocumento(DocDocumentoVO documento);

	/**
	 * Elimina un documento electrónico.
	 *
	 * @param id
	 *            Identificador del documento electrónico.
	 */
	public void deleteDocumento(String id);

	/**
	 * Elimina los documentos de un elemento del cuadro de clasificación.
	 *
	 * @param idElementoCF
	 *            Identificador del elemento del cuadro de clasificación.
	 */
	public void deleteDocumentos(String idElementoCF);

	/**
	 *
	 * @param idObj
	 * @param estadosAActualizar
	 * @param nuevoEstado
	 */
	public void update(String idObj, int[] estadosAActualizar, int nuevoEstado);

	/**
	 * Obtiene el número de documentos almacenados en un depósito electrónico.
	 *
	 * @param idExtDeposito
	 *            Identificador externo del depósito electrónico.
	 * @return Número de documentos.
	 */
	public int getCountDocumentosByIdExtDeposito(String idExtDeposito);

	/**
	 * Obtiene la lista de documentos electrónicos de un elemento del cuadro de
	 * clasificación.
	 *
	 * @param idElementoCF
	 *            Identificador del elemento del cuadro de clasificación.
	 * @param idClfPadre
	 *            Identificador del clasificador padre.
	 * @return Listas de documentos electrónicos.
	 */
	public int getCountDocumentosByIdClfPadre(String idElementoCF,
			String idClfPadre);

	/**
	 * Obtiene un Map cuyas claves son los ids de elementos del cuadro y sus
	 * valores la lista de identificadores de documentos electrónicos asociados
	 * a una lista de elementos del cuadro de clasificación.
	 *
	 * @param idsElementosCF
	 *            Identificador de la lista de ids de elementos del cuadro de
	 *            clasificación.
	 * @return Listas de identificadores de documentos electrónicos.
	 */
	public Map getDocumentos(List idsElementosCF);

	/**
	 * Obtiene el documento electrónico.
	 *
	 * @param id
	 *            Identificador interno del documento
	 * @return Documento electrónico.
	 */
	public DocDocumentoVO getDocumentoByIdInterno(String idInterno);

	public void updateIdElementocf(String idElementocfAntiguo,
			String idElementoCfNuevo, String[] idsInternosDocumentos);
}