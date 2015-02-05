package es.ieci.tecdoc.isicres.document.connector;
/** 
 * @author Iecisa 
 * @version $$Revision$$ 
 * 
 * ${tags} 
 */ 

import java.util.List;

import es.ieci.tecdoc.isicres.document.connector.vo.ISicresAbstractCriterioBusquedaVO;
import es.ieci.tecdoc.isicres.document.connector.vo.ISicresAbstractDocumentVO;

/**
 * Interfaz CRUD de conexión de isicres con gestores documentales.
 * 
 */
public interface ISicresDocumentConnector {

	/**
	 * 
	 * Crea un documento en el gestor documental
	 * 
	 * @param <code>IsicresAbstractDocumentVO</code> documento que se quiere
	 *            crear en el gestor documental
	 * @return <code>IsicresAbstractDocumentVO</code> documento que se creo,
	 *         pudiendo tener campos populado, como puede ser el id
	 */
	public ISicresAbstractDocumentVO create(ISicresAbstractDocumentVO document);
	

	/**
	 * Obtiene un documento a partir de los datos proporcionados por el
	 * parametro
	 * 
	 * @param document
	 * @return
	 */
	public ISicresAbstractDocumentVO retrieve(ISicresAbstractDocumentVO document);
	

	/**
	 * Devolverá lista de objetos de tipo <code>IsicresAbstractDocumentVO</code>
	 * que coincidan con el criterio pasado como parámetro
	 * 
	 * @param criterioBusqueda
	 * @return lista de objetos de tipo <code>IsicresAbstractDocumentVO</code>
	 */
	public List find(ISicresAbstractCriterioBusquedaVO criterioBusqueda);
	

	/**
	 * Actualiza el documento en el gestor documental
	 * 
	 * @param document
	 * @return
	 */
	public ISicresAbstractDocumentVO update(ISicresAbstractDocumentVO document);

	/**
	 * elimina documento del gestor documental
	 * 
	 * @param document
	 */
	public void delete(ISicresAbstractDocumentVO document) ;

}
