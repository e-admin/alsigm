package es.ieci.tecdoc.isicres.api.business.manager;

import java.util.List;

import com.ieci.tecdoc.common.isicres.AxSfQueryResults;

import es.ieci.tecdoc.isicres.api.business.vo.BaseRegistroVO;
import es.ieci.tecdoc.isicres.api.business.vo.CampoGenericoRegistroVO;
import es.ieci.tecdoc.isicres.api.business.vo.CriterioBusquedaRegistroSqlVO;
import es.ieci.tecdoc.isicres.api.business.vo.DocumentoRegistroVO;
import es.ieci.tecdoc.isicres.api.business.vo.IdentificadorRegistroVO;
import es.ieci.tecdoc.isicres.api.business.vo.LibroEntradaVO;
import es.ieci.tecdoc.isicres.api.business.vo.LibroSalidaVO;
import es.ieci.tecdoc.isicres.api.business.vo.RegistroEntradaExternoVO;
import es.ieci.tecdoc.isicres.api.business.vo.RegistroEntradaVO;
import es.ieci.tecdoc.isicres.api.business.vo.RegistroSalidaExternoVO;
import es.ieci.tecdoc.isicres.api.business.vo.RegistroSalidaVO;
import es.ieci.tecdoc.isicres.api.business.vo.ResultadoBusquedaRegistroVO;
import es.ieci.tecdoc.isicres.api.business.vo.UsuarioVO;
import es.ieci.tecdoc.isicres.api.business.vo.enums.TipoLibroEnum;

/**
 * @author Iecisa
 * @version $Revision$
 * 
 */

public abstract class RegistroManager {

	/**
	 * Método para crear un registro de Entrada
	 * 
	 * @param usuario
	 * @param registro
	 * @return
	 */
	public abstract RegistroEntradaVO createRegistroEntrada(UsuarioVO usuario,
			RegistroEntradaVO registro);

	/**
	 * Método para crear un registro de Salida
	 * 
	 * @param usuario
	 * @param registro
	 * @return
	 */
	public abstract RegistroSalidaVO createRegistroSalida(UsuarioVO usuario,
			RegistroSalidaVO registro);

	/**
	 * Método para importar un registro de Entrada
	 * 
	 * @param usuario
	 * @param registro
	 * @return
	 */
	public abstract RegistroEntradaVO importRegistroEntrada(UsuarioVO usuario,
			RegistroEntradaExternoVO registro);

	/**
	 * Método para importar un registro de salida
	 * 
	 * @param usuario
	 * @param registro
	 * @return
	 */
	public abstract RegistroSalidaVO importRegistroSalida(UsuarioVO usuario,
			RegistroSalidaExternoVO registro);

	/**
	 * Metodo que obtiene la informacion de un registro de entrada a partir de
	 * su id
	 * 
	 * @param usuario
	 * @param id
	 *            - identificador del libro
	 * @return {@link RegistroEntradaVO}
	 */
	public abstract RegistroEntradaVO findRegistroEntradaById(
			UsuarioVO usuario, IdentificadorRegistroVO id);

	/**
	 * Metodo que obtiene la informacion de un registro de salida a partir de su
	 * id
	 * 
	 * @param usuario
	 * @param id
	 *            - identificador del libro
	 * @return {@link RegistroSalidaVO}
	 */
	public abstract RegistroSalidaVO findRegistroSalidaById(UsuarioVO usuario,
			IdentificadorRegistroVO id);

	/**
	 * 
	 * @param usuario
	 * @param libro
	 * @param criterioBusqueda
	 * @return
	 */
	public abstract ResultadoBusquedaRegistroVO findAllRegistroEntradaByCriterioWhereSql(
			UsuarioVO usuario, LibroEntradaVO libro,
			CriterioBusquedaRegistroSqlVO criterioBusqueda);

	/**
	 * 
	 * Realiza una búsqueda sobre registros de entrada a partir de un filtro SQL.
	 * 
	 * No tiene en cuenta la oficina en la que está logado el usuario.
	 * 
	 * @param usuario
	 * @param criterioBusqueda
	 * @return
	 */
	public abstract ResultadoBusquedaRegistroVO findAllRegistroEntradaByCriterioWhereSql(
			UsuarioVO usuario, CriterioBusquedaRegistroSqlVO criterioBusqueda);

	/**
	 * 
	 * Realiza una búsqueda sobre registros de entrada a partir de un filtro SQL.
	 * 
	 * Solo devuelve los registros de la oficina en la que está logado el usuario.
	 * 
	 * @param usuario
	 * @param criterioBusqueda
	 * @return
	 */
	public abstract ResultadoBusquedaRegistroVO findAllRegistroEntradaForUserByCriterioWhereSql(
			UsuarioVO usuario, CriterioBusquedaRegistroSqlVO criterioBusqueda);

	/**
	 * 
	 * @param usuario
	 * @param libro
	 * @param criterioBusqueda
	 * @return
	 */
	public abstract ResultadoBusquedaRegistroVO findAllRegistroSalidaByCriterioWhereSql(
			UsuarioVO usuario, LibroSalidaVO libro,
			CriterioBusquedaRegistroSqlVO criterioBusqueda);

	/**
	 * Realiza una búsqueda sobre registros de salida a partir de un filtro SQL.
	 * 
	 * No tiene en cuenta la oficina en la que está logado el usuario.
	 * 
	 * @param usuario
	 * @param criterioBusqueda
	 * @return
	 */
	public abstract ResultadoBusquedaRegistroVO findAllRegistroSalidaByCriterioWhereSql(
			UsuarioVO usuario, CriterioBusquedaRegistroSqlVO criterioBusqueda);

	/**
	 * Realiza una búsqueda sobre registros de entrada a partir de un filtro SQL.
	 * 
	 * Solo devuelve los registros de la oficina en la que está logado el usuario.
	 * 
	 * @param usuario
	 * @param criterioBusqueda
	 * @return
	 */
	public abstract ResultadoBusquedaRegistroVO findAllRegistroSalidaForUserByCriterioWhereSql(
			UsuarioVO usuario, CriterioBusquedaRegistroSqlVO criterioBusqueda);

	/**
	 * 
	 * @param usuario
	 * @param id
	 */
	public abstract void cancelRegistroEntradaById(UsuarioVO usuario,
			IdentificadorRegistroVO id);

	/**
	 * 
	 * @param usuario
	 * @param id
	 */
	public abstract void cancelRegistroSalidaById(UsuarioVO usuario,
			IdentificadorRegistroVO id);

	/**
	 * 
	 * @param usuario
	 * @param id
	 */
	public abstract void lockRegistro(UsuarioVO usuario,
			IdentificadorRegistroVO id);

	/**
	 * 
	 * @param usuario
	 * @param id
	 */
	public abstract void unlockRegistro(UsuarioVO usuario,
			IdentificadorRegistroVO id);

	/**
	 * actualiza los campos del registro de entrada
	 * 
	 * @param usuario
	 * @param id
	 * @param camposGenericos
	 *            campos a actualizar serán de tipo
	 *            {@link CampoGenericoRegistroVO}
	 */
	public abstract void updateRegistroEntrada(UsuarioVO usuario,
			IdentificadorRegistroVO id, List camposGenericos);
	
	/**
	 * Actualiza los campos del registro de entrada cuando se realiza un 
	 * intercambio registral.
	 * 
	 * Se comprueba que el usuario tenga permisos para realizar intercambios
	 * registrales.
	 * 
	 * No se comprueba que el usuario tenga permisos para modificar los campos
	 * protegidos del registro.
	 * 
	 * @param usuario
	 * @param id
	 * @param camposGenericos
	 * 		 campos a actualizar serán de tipo
	 *            {@link CampoGenericoRegistroVO}
	 */
	public abstract void updateRegistroEntradaIR(UsuarioVO usuario,
			IdentificadorRegistroVO id, List<CampoGenericoRegistroVO> camposGenericos);

	/**
	 * actualiza los campos del registro de salida
	 * 
	 * @param usuario
	 * @param id
	 * @param camposGenericos
	 *            campos a actualizar serán de tipo
	 *            {@link CampoGenericoRegistroVO}
	 */
	public abstract void updateRegistroSalida(UsuarioVO usuario,
			IdentificadorRegistroVO id, List camposGenericos);
	
	/**
	 * Actualiza los campos del registro de salida cuando se realiza un 
	 * intercambio registral.
	 * 
	 * Se comprueba que el usuario tenga permisos para realizar intercambios
	 * registrales.
	 * 
	 * No se comprueba que el usuario tenga permisos para modificar los campos
	 * protegidos del registro.
	 * 
	 * @param usuario
	 * @param id
	 * @param camposGenericos
	 * 		 campos a actualizar serán de tipo
	 *            {@link CampoGenericoRegistroVO}
	 */
	public abstract void updateRegistroSalidaIR(UsuarioVO usuario,
			IdentificadorRegistroVO id, List<CampoGenericoRegistroVO> camposGenericos);

	/**
	 * 
	 * @param id
	 * @param documento
	 * @param usuario
	 * @return
	 */
	public abstract DocumentoRegistroVO attachDocument(
			IdentificadorRegistroVO id, DocumentoRegistroVO documento,
			UsuarioVO usuario);
	
	
	/**
	 * @param id
	 * @param documentName
	 * @return
	 */
	public abstract boolean existDocumentByName(IdentificadorRegistroVO id, String documentName,UsuarioVO usuario);

	/**
	 * 
	 * Adjunta un listado de documentos a un registro. 
	 *  
	 * @param id
	 * @param documentos
	 * @param usuario
	 * @return
	 */
	public abstract List attachDocuments(IdentificadorRegistroVO id,
			List documentos, UsuarioVO usuario);

	/**
	 * 
	 * @param identificadorRegistro
	 * @param documentIndex
	 * @param pageIndex
	 * @param usuario
	 * @return
	 */
	public abstract byte[] getPaginaByIndex(
			IdentificadorRegistroVO identificadorRegistro, int documentIndex,
			int pageIndex, UsuarioVO usuario);

	/**
	 * 
	 * @param identificadorRegistro
	 * @param documentId
	 * @param pageId
	 * @param usuario
	 * @return
	 */
	public abstract byte[] getPaginaById(
			IdentificadorRegistroVO identificadorRegistro, String documentId,
			String pageId, UsuarioVO usuario);

	/**
	 * Método para comprobar que el usuario <code>usuario</code> puede crear un
	 * registro con la información indicada en <code>registro</code>.
	 * 
	 * @param usuario
	 * @param registro
	 * @return
	 */
	public abstract Boolean canCreateRegistro(UsuarioVO usuario,
			BaseRegistroVO registro);

	/**
	 * Devuelve una <code>java.util.List</code> de objetos tipo
	 * <code>AxSfQueryResults</code>. Es necesario devolver este
	 * <code>Wrapper</code> para poder acceder al identificador del libro
	 * asociado a los registros obtenidos con la query.
	 * 
	 * @param usuario
	 * @param offset
	 * @param limit
	 * @param bookID
	 * @param bookType
	 * @param filter
	 * 
	 * @see AxSfQueryResults
	 * 
	 * @return
	 */
	public abstract List queryForAxSfQueryResults(UsuarioVO usuario,
			int offset, int limit, Integer bookID, TipoLibroEnum bookType,
			String filter);
	
	/**
	 * Devuelve un registro base {@link BaseRegistroVO} a partir del numero de registro
	 * 
	 * @param numRegistro Numero de registro
	 * @param usuario	Usuario
	 * @return El {@link BaseRegistroVO}
	 */
	public abstract BaseRegistroVO findRegistroByNumRegistro(UsuarioVO usuario, String numRegistro);
}
