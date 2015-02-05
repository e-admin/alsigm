package transferencias.db;

import gcontrol.vos.UsuarioVO;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import transferencias.TipoTransferencia;
import transferencias.vos.BusquedaPrevisionesVO;
import transferencias.vos.PrevisionVO;

import common.db.IDBEntity;
import common.exceptions.TooManyResultsException;

/**
 * Metodos de acceso a datos referentes a previsiones de transferencia <br>
 * Entidad: <b>ASGTPREVISION</b>
 */
public interface IPrevisionDBEntity extends IDBEntity {

	/**
	 * Guarda en la base de datos un prevision de transferencia
	 *
	 * @param prevision
	 *            Datos de la prevision de transferencia a almacenar
	 */
	public void insertarPrevision(PrevisionVO prevision);

	/**
	 * Recupera de la base de datos una prevision de transferencia
	 *
	 * @param idPrevision
	 *            Identificador de prevision de transferencia
	 * @return Prevision de transferencia {@link PrevisionVO}
	 */
	public PrevisionVO getInfoPrevision(String idPrevision);

	/**
	 * Recupera de la base de datos un conjunto de previsiones de transferencia
	 * a partir de sus identificadores
	 *
	 * @param idPrevision
	 *            Conjunto de identificadores de transferencia
	 * @return Lista de previsiones de transferencia {@link PrevisionVO}
	 */
	public Collection getPrevisionesXIds(String[] idPrevision);

	public Collection getPrevisionesXIdOrgRemitenteYAno(String idorgremitente,
			String ano);

	/**
	 * Recupera el número de previsiones de transferencia dirigidas a un
	 * conjunto de archivos, que se encuentren en alguno de los estados
	 * indicados y que hayan sido elaboradas en el año especificado
	 *
	 * @param idArchivoReceptor
	 *            Lista de identificadores de archivo. Puede ser nulo
	 * @param estados
	 *            Lista de estados de prevision. Puede ser nulo
	 * @param ano
	 *            Año. Puede ser nulo
	 * @return Número de previsiones de transferencia
	 */
	public int getCountPrevisionesXArchivoReceptor(String[] idArchivoReceptor,
			int[] estados, String ano);

	/**
	 * Recupera las previsiones de transferencia dirigidas a un conjunto de
	 * archivos, que se encuentren en alguno de los estados indicados y que
	 * hayan sido elaboradas en el año especificado
	 *
	 * @param idArchivoReceptor
	 *            Lista de identificadores de archivo. Puede ser nulo
	 * @param estados
	 *            Lista de estados de prevision. Puede ser nulo
	 * @param ano
	 *            Año. Puede ser nulo
	 * @return Lista de previsiones de transferencia {@link PrevisionVO}
	 */
	public List getPrevisionesXArchivoReceptor(String[] idArchivoReceptor,
			int[] estados, String ano);

	public Collection getPrevisionesXIdOrgRemitenteYTTransfYTPrevYEstados(
			String idorgremitente, int tipotransferencia, int[] estados);

	/**
	 * Recupera de la base de datos las previsiones elaboradas por alguno de los
	 * organos remitentes indicados y que se encuentran en alguno de los estados
	 * señalados
	 *
	 * @param organoRemitente
	 *            Lista de identificadores de organo. Puede ser null
	 * @param estados
	 *            Lista de estados de prevision. Puede ser null
	 * @return Lista de previsiones de transferencia {@link PrevisionVO}
	 */
	public List getPrevisionesXIdOrgRemitenteYEstados(String[] organoRemitente,
			int[] estados);

	/**
	 * Permite recuperar de la base de datos las previsiones que se han
	 * elaborado en un año concreto
	 *
	 * @param estados
	 *            Conjunto de estados de prevision de transferencia validos. En
	 *            caso de ser nulo se devolveran las previsiones que cumplan las
	 *            demas condiciones sin tener en cuenta su estado
	 * @param ano
	 *            Año al que deben corresponder las previsiones a devolver.
	 *            Puede ser nulo
	 * @return Lista de previsiones de transferencia {@link PrevisionVO}
	 */
	public List getPrevisionesXAnio(int[] estados, String ano);

	/**
	 * Permite recuperar de la base de datos las previsiones que se han
	 * elaborado en un año concreto
	 *
	 * @param estados
	 *            Conjunto de estados de prevision de transferencia validos. En
	 *            caso de ser nulo se devolveran las previsiones que cumplan las
	 *            demas condiciones sin tener en cuenta su estado
	 * @param ano
	 *            Año al que deben corresponder las previsiones a devolver.
	 *            Puede ser nulo
	 * @return Lista de previsiones de transferencia {@link PrevisionVO}
	 */
	public List getPrevisionesXAnioYArchivo(int[] estados, String ano,
			String[] idArchivo);

	/**
	 * Permite recuperar de la base de datos las previsiones que tienen como
	 * gestor un deteminado usuario, se encuentran en alguno de los estados
	 * indicados y se han elaborado en un año concreto
	 *
	 * @param idUserGestor
	 *            Identificador de usuario
	 * @param estados
	 *            Conjunto de estados de prevision de transferencia validos. En
	 *            caso de ser nulo se devolveran las previsiones que cumplan las
	 *            demas condiciones sin tener en cuenta su estado
	 * @param ano
	 *            Año al que deben corresponder las previsiones a devolver.
	 *            Puede ser nulo
	 * @return Lista de previsiones de transferencia {@link PrevisionVO}
	 */
	public List getPrevisionesXGestor(String idUserGestor, int[] estados,
			String ano);

	/**
	 * Permite recuperar de la base de datos las previsiones que tienen como
	 * gestor un deteminado usuario, se encuentran en alguno de los estados
	 * indicados, se han elaborado en un año concreto y pertenecen a ciertos
	 * tipos de transferencias
	 *
	 * @param idUserGestor
	 *            Identificador de usuario
	 * @param estados
	 *            Conjunto de estados de prevision de transferencia validos. En
	 *            caso de ser nulo se devolveran las previsiones que cumplan las
	 *            demas condiciones sin tener en cuenta su estado
	 * @param tipos
	 *            Conjunto de tipos de transferencias
	 * @param ano
	 *            Año al que deben corresponder las previsiones a devolver.
	 *            Puede ser nulo
	 * @return Lista de previsiones de transferencia {@link PrevisionVO}
	 */
	public List getPrevisionesXGestorYTipos(String idUserGestor, int[] estados,
			int[] tipos, String ano);

	/**
	 * Permite recuperar de la base de datos las previsiones que tienen como
	 * gestor un deteminado usuario, se encuentran en alguno de los estados
	 * indicados, se han elaborado en un año concreto y son de cierto tipo
	 *
	 * @param idUserGestor
	 *            Identificador de usuario
	 * @param tipotransferencia
	 *            Tipo de transferencia
	 * @param estados
	 *            Conjunto de estados de prevision de transferencia validos. En
	 *            caso de ser nulo se devolveran las previsiones que cumplan las
	 *            demas condiciones sin tener en cuenta su estado
	 * @param ano
	 *            Año al que deben corresponder las previsiones a devolver.
	 *            Puede ser nulo
	 * @return Lista de previsiones de transferencia {@link PrevisionVO}
	 */
	public List getPrevisionesXGestorYTipoTrans(String idUserGestor,
			int tipotransferencia, int[] estados, String ano);

	/**
	 * Obtiene el número de previsiones que tienen como gestor un deteminado
	 * usuario, se encuentran en alguno de los estados indicados y se han
	 * elaborado en un año concreto
	 *
	 * @param idUserGestor
	 *            Identificador de usuario
	 * @param estados
	 *            Conjunto de estados de prevision de transferencia validos. En
	 *            caso de ser nulo se devolveran las previsiones que cumplan las
	 *            demas condiciones sin tener en cuenta su estado
	 * @param ano
	 *            Año al que deben corresponder las previsiones a devolver.
	 *            Puede ser nulo
	 * @return Número de previsiones que verifican las condiciones indicadas
	 */
	public int getCountPrevisionesXGestor(String idUser,
			int[] estadosPrevision, String string);

	/**
	 * Actualiza en la base de datos la informacion de una prevision de
	 * transferencia
	 *
	 * @param prevision
	 *            Datos de prevision de transferencia
	 */
	public void updatePrevision(PrevisionVO prevision);

	public void updateNUnidadesInstalacion(String idPrevision,
			int nUnidadesInstalacion);

	public void updateEstado_FechaEstado_FechasTransf_motivoRechazo(
			String idPrevision, int estado, Date fechaEstado,
			Date fechaIniTransf, Date fechaFinTransf, String motivoRechazo);

	public void updateFechasTransf(String idPrevision, Date fechaIniTransf,
			Date fechaFinTransf);

	public void updateUsrgestor(String[] codigosPrevisiones, String idNewUser);

	public void updateEstadoAndFechaEstado(String idPrevision, int nuevoEstado,
			Date nuevaFecha);

	public void updateEstado_FechaEstado_observaciones(String idPrevision,
			int estado, Date fechaEstado, String observaciones);

	public void updateEstado_FechaEstado_motivoRechazo(String idPrevision,
			int estado, Date fechaEstado, String motivoRechazo);

	public void deletePrevision(String idPrevision);

	public void cerrarPrevision(String codigoPrevision);

	/**
	 * Busca las previsiones que cumplan unos criterios concretos.
	 *
	 * @param vo
	 *            Criterios de la búsqueda.
	 * @return Lista de previsiones.
	 * @throws TooManyResultsException
	 *             si el número de resultados es excesivo.
	 */
	public List getPrevisiones(BusquedaPrevisionesVO vo)
			throws TooManyResultsException;

	/**
	 * Obtiene la lista de gestores con previsiones.
	 *
	 * @param idOrgano
	 *            Identificador del órgano del gesto.
	 * @param tiposTransferencia
	 *            Tipo de transferencia ({@link TipoTransferencia}).
	 * @return Lista de Gestores ({@link UsuarioVO}).
	 */
	public List getGestoresConPrevision(String idOrgano,
			int[] tiposTransferencia);

	/**
	 * Obtiene la lista de previsiones caducadas.
	 *
	 * @return Lista de previsiones.
	 */
	public List getPrevisionesCaducadas();

	/**
	 * Obtiene una previsión, con los datos que recibe en el objeto {@link PrevisionVO}
	 * @param prevision Objeto que contiene los datos de la previsión
	 * @return
	 */
	public PrevisionVO getPrevisionByVO(PrevisionVO prevision);

}