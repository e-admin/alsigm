package docelectronicos.db;

import java.util.List;

import common.db.IDBEntity;

import docelectronicos.vos.DocDocumentoVO;

/**
 * Entidad: <b>ADOCDOCUMENTODESCR</b>
 * 
 * @author IECISA
 * 
 */
public interface IDocDocumentoDescrDBEntity extends IDBEntity {
	/**
	 * Obtiene la lista de documentos electrónicos de un elemento del cuadro de
	 * clasificación.
	 * 
	 * @param idDescr
	 *            Identificador del descriptor.
	 * @param idClfPadre
	 *            Identificador del clasificador padre.
	 * @param estados
	 *            Lista de estados de los documentos.
	 * @return Listas de documentos electrónicos.
	 */
	public List getDocumentos(String idDescr, String idClfPadre, int[] estados);

	/**
	 * Obtiene la lista de documentos electrónicos de un elemento del cuadro de
	 * clasificación.
	 * 
	 * @param idDescr
	 *            Identificador del descriptor.
	 * @param estados
	 *            Lista de estados de los documentos.
	 * @return Listas de documentos electrónicos.
	 */
	public List getDocumentos(String idDescr, int[] estados);

	/**
	 * Obtiene la lista de documentos electrónicos de un descriptor.
	 * 
	 * @param idDescr
	 *            Identificador del descriptor.
	 * @return Listas de documentos electrónicos.
	 */
	public List getDocumentos(String idDescr);

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
	 * @return Documento.S
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
	 * Elimina los documentos de un descriptor.
	 * 
	 * @param idDescr
	 *            Identificador del descriptor.
	 */
	public void deleteDocumentos(String idDescr);

	/**
	 * Cuenta el número de documentos asociados a un descriptor del cuadro de
	 * clasificación
	 * 
	 * @param idElementoCF
	 *            Identificador de descriptor
	 * @return Número de documentos asociados al descriptor
	 */
	public int countNumDocumentos(String id);

	/**
	 * 
	 * @param idObj
	 * @param estadosAActualizar
	 * @param nuevoEstado
	 */
	public void update(String idObj, int[] estadosAActualizar, int nuevoEstado);

	/**
	 * Obtiene la lista de documentos electrónicos de un elemento del cuadro de
	 * clasificación.
	 * 
	 * @param idDescr
	 *            Identificador del descriptor.
	 * @param idClfPadre
	 *            Identificador del clasificador padre.
	 * @return número de documentos electrónicos del descriptor cuyo padre es el
	 *         clasificador indicado
	 */
	public int getCountDocumentosByIdClfPadre(String idDescr, String idClfPadre);

	/**
	 * Elimina los elemento cuyo <b>ADOCDOCUMENTODESCR.IDDESCR</b> sea igual a
	 * los idsDescriptores
	 * 
	 * @param idDescriptor
	 *            Identificador del descriptor
	 * @param idsAReemplazar
	 *            Identificadores de los descriptores a reemplazar
	 */
	public void deleteDescriptores(String[] idsDescriptores);

}