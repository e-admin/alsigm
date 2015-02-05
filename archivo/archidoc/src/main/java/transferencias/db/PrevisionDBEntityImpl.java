package transferencias.db;

import gcontrol.db.ArchivoDbEntityImpl;
import gcontrol.db.CAOrganoDbEntityImpl;
import gcontrol.db.UsuarioDBEntityImpl;
import gcontrol.vos.UsuarioVO;
import ieci.core.db.DbColumnDef;
import ieci.core.db.DbConnection;
import ieci.core.db.DbDataType;
import ieci.core.db.DbDeleteFns;
import ieci.core.db.DbInsertFns;
import ieci.core.db.DbUtil;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import transferencias.TipoTransferencia;
import transferencias.db.oracle.RelacionEntregaDBEntityImpl;
import transferencias.model.EstadoPrevision;
import transferencias.model.EstadoREntrega;
import transferencias.vos.BusquedaPrevisionesVO;
import transferencias.vos.PrevisionVO;

import common.CodigoTransferenciaUtils;
import common.ConfigConstants;
import common.db.DBCommand;
import common.db.DBEntity;
import common.db.DBUtils;
import common.db.DbDataSource;
import common.db.SigiaDbInputRecord;
import common.exceptions.TooManyResultsException;
import common.util.ArrayUtils;
import common.util.CustomDateFormat;
import common.util.DateUtils;
import common.util.StringUtils;

/**
 * Implementación de los métodos de acceso a datos referidos a previsiones de
 * transferencia
 */
public class PrevisionDBEntityImpl extends DBEntity implements
		IPrevisionDBEntity {

	public static final String TABLE_NAME = "ASGTPREVISION";

	public static final String ID_COLUMN_NAME = "id";
	public static final String TIPOTRANSFERENCIA__COLUMN_NAME = "tipotransferencia";
	public static final String TIPOPREVISION__COLUMN_NAME = "tipoprevision";
	public static final String IDORGANO_COLUMN_NAME = "idorgremitente";
	public static final String ANO_COLUMN_NAME = "ano";
	public static final String ORDEN_COLUMN_NAME = "orden";
	public static final String FONDODESTINO_COLUMN_NAME = "idfondodestino";
	public static final String FECHAINICIO_COLUMN_NAME = "fechainitrans";
	public static final String FECHAFIN_COLUMN_NAME = "fechafintrans";
	public static final String NUMUINSTALACION_COLUMN_NAME = "numuinstalacion";
	public static final String ESTADO_COLUMN_NAME = "estado";
	public static final String FECHAESTADO_COLUMN_NAME = "fechaestado";
	public static final String ARCHIVORECEPTOR_COLUMN_NAME = "idarchivoreceptor";
	public static final String GESTOR_COLUMN_NAME = "idusrgestor";
	public static final String MOTIVORECHAZO_COLUMN_NAME = "motivorechazo";
	public static final String OBSERVACIONES_COLUMN_NAME = "observaciones";
	public static final String ARCHIVOREMITENTE_COLUMN_NAME = "idarchivoremitente";

	public static final DbColumnDef CAMPO_ID = new DbColumnDef(null,
			TABLE_NAME, ID_COLUMN_NAME, DbDataType.SHORT_TEXT, 64, false);

	public static final DbColumnDef CAMPO_TIPOTRANSEFERENCIA = new DbColumnDef(
			null, TABLE_NAME, TIPOTRANSFERENCIA__COLUMN_NAME,
			DbDataType.LONG_INTEGER, false);

	public static final DbColumnDef CAMPO_TIPOPREVISION = new DbColumnDef(null,
			TABLE_NAME, TIPOPREVISION__COLUMN_NAME, DbDataType.LONG_INTEGER,
			false);

	public static final DbColumnDef CAMPO_IDORGREMITENTE = new DbColumnDef(
			null, TABLE_NAME, IDORGANO_COLUMN_NAME, DbDataType.SHORT_TEXT, 32,
			false);

	public static final DbColumnDef CAMPO_ANO = new DbColumnDef(null,
			TABLE_NAME, ANO_COLUMN_NAME, DbDataType.SHORT_TEXT, 4, false);

	public static final DbColumnDef CAMPO_ORDEN = new DbColumnDef(null,
			TABLE_NAME, ORDEN_COLUMN_NAME, DbDataType.LONG_INTEGER, 10, false);

	public static final DbColumnDef CAMPO_IDFONDODESTINO = new DbColumnDef(
			null, TABLE_NAME, FONDODESTINO_COLUMN_NAME, DbDataType.SHORT_TEXT,
			64, false);

	public static final DbColumnDef CAMPO_FECHAINITRANS = new DbColumnDef(null,
			TABLE_NAME, FECHAINICIO_COLUMN_NAME, DbDataType.DATE_TIME, true);

	public static final DbColumnDef CAMPO_FECHAFINTRANS = new DbColumnDef(null,
			TABLE_NAME, FECHAFIN_COLUMN_NAME, DbDataType.DATE_TIME, true);

	public static final DbColumnDef CAMPO_NUMUINSTALACION = new DbColumnDef(
			null, TABLE_NAME, NUMUINSTALACION_COLUMN_NAME,
			DbDataType.LONG_INTEGER, false);

	public static final DbColumnDef CAMPO_ESTADO = new DbColumnDef(null,
			TABLE_NAME, ESTADO_COLUMN_NAME, DbDataType.LONG_INTEGER, false);

	public static final DbColumnDef CAMPO_FECHAESTADO = new DbColumnDef(null,
			TABLE_NAME, FECHAESTADO_COLUMN_NAME, DbDataType.DATE_TIME, false);

	public static final DbColumnDef CAMPO_IDARCHIVORECEPTOR = new DbColumnDef(
			null, TABLE_NAME, ARCHIVORECEPTOR_COLUMN_NAME,
			DbDataType.SHORT_TEXT, 32, false);

	public static final DbColumnDef CAMPO_IDUSRGESTOR = new DbColumnDef(null,
			TABLE_NAME, GESTOR_COLUMN_NAME, DbDataType.SHORT_TEXT, 32, false);

	public static final DbColumnDef CAMPO_MOTIVORECHAZO = new DbColumnDef(null,
			TABLE_NAME, MOTIVORECHAZO_COLUMN_NAME, DbDataType.SHORT_TEXT, 254,
			true);

	public static final DbColumnDef CAMPO_OBSERVACIONES = new DbColumnDef(null,
			TABLE_NAME, OBSERVACIONES_COLUMN_NAME, DbDataType.SHORT_TEXT, 254,
			true);

	public static final DbColumnDef CAMPO_IDARCHIVOREMITENTE = new DbColumnDef(
			null, TABLE_NAME, ARCHIVOREMITENTE_COLUMN_NAME,
			DbDataType.SHORT_TEXT, 32, false);

	public static final DbColumnDef[] COLUMN_DEFINITIONS = new DbColumnDef[] {
			CAMPO_ID, CAMPO_TIPOTRANSEFERENCIA, CAMPO_TIPOPREVISION,
			CAMPO_IDORGREMITENTE, CAMPO_ANO, CAMPO_ORDEN, CAMPO_IDFONDODESTINO,
			CAMPO_FECHAINITRANS, CAMPO_FECHAFINTRANS, CAMPO_NUMUINSTALACION,
			CAMPO_ESTADO, CAMPO_FECHAESTADO, CAMPO_IDARCHIVORECEPTOR,
			CAMPO_IDUSRGESTOR, CAMPO_MOTIVORECHAZO, CAMPO_OBSERVACIONES,
			CAMPO_IDARCHIVOREMITENTE };

	public static final String COLUM_NAMES_LIST = DbUtil
			.getColumnNames(COLUMN_DEFINITIONS);

	public static final DbColumnDef CAMPO_ANO_PREVISION = new DbColumnDef(
			"anoPrevision", TABLE_NAME, ANO_COLUMN_NAME, DbDataType.SHORT_TEXT,
			4, false);

	public static final DbColumnDef CAMPO_ORDEN_PREVISION = new DbColumnDef(
			"ordenPrevision", TABLE_NAME, ORDEN_COLUMN_NAME,
			DbDataType.LONG_INTEGER, 10, false);

	/**
	 * Obtiene el nombre de la tabla
	 *
	 * @return
	 */
	public String getTableName() {
		return TABLE_NAME;
	}

	public PrevisionDBEntityImpl(DbDataSource dataSource) {
		super(dataSource);
	}

	public PrevisionDBEntityImpl(DbConnection conn) {
		super(conn);
	}

	/**
	 * Guarda en la base de datos un prevision de transferencia
	 *
	 * @param prevision
	 *            Datos de la prevision de transferencia a almacenar
	 */
	public void insertarPrevision(final PrevisionVO prevision) {
		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				prevision.setId(getGuid(prevision.getId()));
				SigiaDbInputRecord inputRecord = new SigiaDbInputRecord(
						COLUMN_DEFINITIONS, prevision);
				DbInsertFns.insert(conn, TABLE_NAME, COLUM_NAMES_LIST,
						inputRecord);
			}
		};
		command.execute();
	}

	protected PrevisionVO getPrevision(String qual) {
		return (PrevisionVO) getVO(qual, TABLE_NAME, COLUMN_DEFINITIONS,
				PrevisionVO.class);
	}

	protected List getPrevisiones(String qual) {
		return getVOS(qual, TABLE_NAME, COLUMN_DEFINITIONS, PrevisionVO.class);
	}

	/**
	 * Recupera de la base de datos una prevision de transferencia
	 *
	 * @param idPrevision
	 *            Identificador de prevision de transferencia
	 * @return Prevision de transferencia {@link PrevisionVO}
	 */
	public PrevisionVO getInfoPrevision(String id) {
		StringBuilder qual = new StringBuilder("WHERE ").append(DBUtils
				.generateEQTokenField(CAMPO_ID, id));
		return getPrevision(qual.toString());
	}

	/**
	 * Recupera de la base de datos un conjunto de previsiones de transferencia
	 * a partir de sus identificadores
	 *
	 * @param idPrevision
	 *            Conjunto de identificadores de transferencia
	 * @return Lista de previsiones de transferencia {@link PrevisionVO}
	 */
	public Collection getPrevisionesXIds(String[] ids) {
		StringBuilder qual = new StringBuilder("WHERE").append(DBUtils
				.generateORTokens(CAMPO_ID, ids));

		return getPrevisiones(qual.toString());
	}

	public Collection getPrevisionesXIdOrgRemitenteYAno(String idorgremitente,
			String ano) {
		StringBuilder qual = new StringBuilder("WHERE")
				.append(DBUtils.generateEQTokenField(CAMPO_IDORGREMITENTE,
						idorgremitente)).append(" AND ")
				.append(DBUtils.generateEQTokenField(CAMPO_ANO, ano))
				.append(" ORDER BY ").append(ID_COLUMN_NAME).append(" DESC ");

		return getPrevisiones(qual.toString());
	}

	/**
	 * Obtiene el sql común para las previsiones de transferencia dirigidas a un
	 * conjunto de archivos, que se encuentren en alguno de los estados
	 * indicados y que hayan sido elaboradas en el año especificado
	 *
	 * @param idArchivoReceptor
	 *            Lista de identificadores de archivo. Puede ser nulo
	 * @param estados
	 *            Lista de estados de prevision. Puede ser nulo
	 * @param ano
	 *            Año. Puede ser nulo
	 * @return Sql
	 */
	private String getPrevisionesXArchivoReceptorSql(
			String[] idArchivoReceptor, int[] estados, String ano) {
		StringBuilder qual = new StringBuilder("WHERE 1=1 ");
		if (idArchivoReceptor != null)
			qual.append(" AND ").append(
					DBUtils.generateORTokens(CAMPO_IDARCHIVORECEPTOR,
							idArchivoReceptor));
		if (estados != null)
			qual.append(" AND ").append(
					DBUtils.generateORTokens(CAMPO_ESTADO, estados));
		if (ano != null)
			qual.append(" AND ").append(
					DBUtils.generateEQTokenField(CAMPO_ANO, ano));
		return qual.toString();
	}

	/*
	 * (sin Javadoc)
	 *
	 * @see
	 * transferencias.db.IPrevisionDBEntity#getCountPrevisionesXArchivoReceptor
	 * (java.lang.String[], int[], java.lang.String)
	 */
	public int getCountPrevisionesXArchivoReceptor(String[] idArchivoReceptor,
			int[] estados, String ano) {
		String qual = getPrevisionesXArchivoReceptorSql(idArchivoReceptor,
				estados, ano);
		return getVOCount(qual.toString(), TABLE_NAME);
	}

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
			int[] estados, String ano) {
		StringBuilder qual = new StringBuilder(
				getPrevisionesXArchivoReceptorSql(idArchivoReceptor, estados,
						ano));
		qual.append(" ORDER BY ").append(FECHAESTADO_COLUMN_NAME)
				.append(" DESC ");
		return getPrevisiones(qual.toString());
	}

	public Collection getPrevisionesXIdOrgRemitenteYTTransfYTPrevYEstados(
			String idorgremitente, int tipotransferencia, int[] estados) {
		StringBuilder qual = new StringBuilder("WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_IDORGREMITENTE,
						idorgremitente))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(CAMPO_TIPOTRANSEFERENCIA,
						tipotransferencia))
				// .append(" AND ")
				// .append(DBUtils.generateEQTokenField(CAMPO_TIPOPREVISION,
				// tipoprevision))
				.append(" AND (")
				.append(DBUtils.generateORTokens(CAMPO_ESTADO, estados))
				.append(")");

		return getPrevisiones(qual.toString());
	}

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
			int[] estados) {
		StringBuilder qual = new StringBuilder("WHERE 1=1 ");
		if (organoRemitente != null)
			qual.append(" AND ").append(
					DBUtils.generateORTokens(CAMPO_IDORGREMITENTE,
							organoRemitente));
		if (estados != null)
			qual.append(" AND ").append(
					DBUtils.generateORTokens(CAMPO_ESTADO, estados));
		return getPrevisiones(qual.toString());
	}

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
			String ano) {
		StringBuilder qual = new StringBuilder("WHERE").append(DBUtils
				.generateEQTokenField(CAMPO_IDUSRGESTOR, idUserGestor));
		if (ano != null)
			qual.append(" AND ").append(
					DBUtils.generateEQTokenField(CAMPO_ANO, ano));
		if (estados != null)
			qual.append(" AND (")
					.append(DBUtils.generateORTokens(CAMPO_ESTADO, estados))
					.append(")");
		return getPrevisiones(qual.toString());
	}

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
			int[] tipos, String ano) {
		StringBuilder qual = new StringBuilder("WHERE").append(DBUtils
				.generateEQTokenField(CAMPO_IDUSRGESTOR, idUserGestor));
		if (ano != null)
			qual.append(" AND ").append(
					DBUtils.generateEQTokenField(CAMPO_ANO, ano));
		if (tipos != null)
			qual.append(" AND ").append(
					DBUtils.generateORTokens(CAMPO_TIPOTRANSEFERENCIA, tipos));
		if (estados != null)
			qual.append(" AND (")
					.append(DBUtils.generateORTokens(CAMPO_ESTADO, estados))
					.append(")");
		return getPrevisiones(qual.toString());
	}

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
			int tipotransferencia, int[] estados, String ano) {
		StringBuilder qual = new StringBuilder("WHERE")
				.append(DBUtils.generateEQTokenField(CAMPO_IDUSRGESTOR,
						idUserGestor))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(CAMPO_TIPOTRANSEFERENCIA,
						tipotransferencia));
		if (ano != null)
			qual.append(" AND ").append(
					DBUtils.generateEQTokenField(CAMPO_ANO, ano));
		if (estados != null)
			qual.append(" AND (")
					.append(DBUtils.generateORTokens(CAMPO_ESTADO, estados))
					.append(")");
		return getPrevisiones(qual.toString());
	}

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
	public List getPrevisionesXAnio(int[] estados, String ano) {
		StringBuilder qual = new StringBuilder("WHERE").append(DBUtils
				.generateORTokens(CAMPO_ESTADO, estados));
		if (ano != null)
			qual.append(" AND ").append(
					DBUtils.generateEQTokenField(CAMPO_ANO, ano));

		return getPrevisiones(qual.toString());
	}

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
			String[] idsArchivo) {
		StringBuilder qual = new StringBuilder("WHERE").append(DBUtils
				.generateORTokens(CAMPO_ESTADO, estados));
		if (ano != null)
			qual.append(" AND ").append(
					DBUtils.generateEQTokenField(CAMPO_ANO, ano));

		if (idsArchivo != null && idsArchivo.length > 0) {
			qual.append(" AND ").append(
					DBUtils.generateORTokens(CAMPO_IDARCHIVORECEPTOR,
							idsArchivo));
		} else {
			return null;
		}

		return getPrevisiones(qual.toString());
	}

	/*
	 * (sin Javadoc)
	 *
	 * @see
	 * transferencias.db.IPrevisionDBEntity#getCountPrevisionesXGestor(java.
	 * lang.String, int[], java.lang.String)
	 */
	public int getCountPrevisionesXGestor(String idUser,
			int[] estadosPrevision, String ano) {
		StringBuilder qual = new StringBuilder("WHERE").append(DBUtils
				.generateEQTokenField(CAMPO_IDUSRGESTOR, idUser));
		if (ano != null)
			qual.append(" AND ").append(
					DBUtils.generateEQTokenField(CAMPO_ANO, ano));
		if (estadosPrevision != null)
			qual.append(" AND (")
					.append(DBUtils.generateInTokenField(CAMPO_ESTADO,
							estadosPrevision)).append(")");
		return getVOCount(qual.toString(), TABLE_NAME);
	}

	/**
	 * Actualiza en la base de datos la informacion de una prevision de
	 * transferencia
	 *
	 * @param prevision
	 *            Datos de prevision de transferencia
	 */
	public void updatePrevision(PrevisionVO prevision) {
		StringBuilder qual = new StringBuilder("WHERE ").append(DBUtils
				.generateEQTokenField(CAMPO_ID, prevision.getId()));
		updateVO(qual.toString(), TABLE_NAME, COLUMN_DEFINITIONS, prevision);
	}

	public void updateNUnidadesInstalacion(String idPrevision,
			int nUnidadesInstalacion) {
		StringBuilder qual = new StringBuilder("WHERE ").append(DBUtils
				.generateEQTokenField(CAMPO_ID, idPrevision));

		Map colToUpdate = Collections.singletonMap(CAMPO_NUMUINSTALACION,
				new Integer(nUnidadesInstalacion));
		updateFields(qual.toString(), colToUpdate, TABLE_NAME);
	}

	public void updateEstado_FechaEstado_FechasTransf_motivoRechazo(
			String idPrevision, int estado, Date fechaEstado,
			Date fechaIniTransf, Date fechaFinTransf, String motivoRechazo) {
		StringBuilder qual = new StringBuilder("WHERE ").append(DBUtils
				.generateEQTokenField(CAMPO_ID, idPrevision));

		Map colsToUpdate = new HashMap();
		colsToUpdate.put(CAMPO_ESTADO, new Integer(estado));
		colsToUpdate.put(CAMPO_FECHAESTADO, fechaEstado);
		colsToUpdate.put(CAMPO_FECHAINITRANS, fechaIniTransf);
		colsToUpdate.put(CAMPO_FECHAFINTRANS, fechaFinTransf);
		colsToUpdate.put(CAMPO_MOTIVORECHAZO, motivoRechazo);

		updateFields(qual.toString(), colsToUpdate, TABLE_NAME);

	}

	public void updateFechasTransf(String idPrevision, Date fechaIniTransf,
			Date fechaFinTransf) {
		StringBuilder qual = new StringBuilder("WHERE ").append(DBUtils
				.generateEQTokenField(CAMPO_ID, idPrevision));

		Map colsToUpdate = new HashMap();
		colsToUpdate.put(CAMPO_FECHAINITRANS, fechaIniTransf);
		colsToUpdate.put(CAMPO_FECHAFINTRANS, fechaFinTransf);

		updateFields(qual.toString(), colsToUpdate, TABLE_NAME);

	}

	public void updateUsrgestor(String[] codigosPrevisiones, String idNewUser) {
		StringBuilder qual = new StringBuilder("WHERE").append(DBUtils
				.generateORTokens(CAMPO_ID, codigosPrevisiones));
		Map colToUpdate = Collections
				.singletonMap(CAMPO_IDUSRGESTOR, idNewUser);
		updateFields(qual.toString(), colToUpdate, TABLE_NAME);
	}

	public void updateEstadoAndFechaEstado(String idPrevision, int nuevoEstado,
			Date nuevaFecha) {
		StringBuilder qual = new StringBuilder("WHERE ").append(DBUtils
				.generateEQTokenField(CAMPO_ID, idPrevision));

		Map colsToUpdate = new HashMap();
		colsToUpdate.put(CAMPO_ESTADO, String.valueOf(nuevoEstado));
		colsToUpdate.put(CAMPO_FECHAESTADO, nuevaFecha);

		updateFields(qual.toString(), colsToUpdate, TABLE_NAME);
	}

	public void updateEstado_FechaEstado_observaciones(String idPrevision,
			int estado, Date fechaEstado, String observaciones) {
		StringBuilder qual = new StringBuilder("WHERE ").append(DBUtils
				.generateEQTokenField(CAMPO_ID, idPrevision));

		Map colsToUpdate = new HashMap();
		colsToUpdate.put(CAMPO_ESTADO, new Integer(estado));
		colsToUpdate.put(CAMPO_FECHAESTADO, fechaEstado);
		colsToUpdate.put(CAMPO_OBSERVACIONES, observaciones);

		updateFields(qual.toString(), colsToUpdate, TABLE_NAME);

	}

	public void updateEstado_FechaEstado_motivoRechazo(String idPrevision,
			int estado, Date fechaEstado, String motivoRechazo) {
		StringBuilder qual = new StringBuilder("WHERE ").append(DBUtils
				.generateEQTokenField(CAMPO_ID, idPrevision));

		Map colsToUpdate = new HashMap();
		colsToUpdate.put(CAMPO_ESTADO, new Integer(estado));
		colsToUpdate.put(CAMPO_FECHAESTADO, fechaEstado);
		colsToUpdate.put(CAMPO_MOTIVORECHAZO, motivoRechazo);

		updateFields(qual.toString(), colsToUpdate, TABLE_NAME);
	}

	public void deletePrevision(String idPrevision) {
		final StringBuilder qual = new StringBuilder("WHERE ").append(DBUtils
				.generateEQTokenField(CAMPO_ID, idPrevision));
		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				DbDeleteFns
						.delete(getConnection(), TABLE_NAME, qual.toString());
			}
		};
		command.execute();
	}

	public void cerrarPrevision(String codigoPrevision) {
		updateEstadoAndFechaEstado(codigoPrevision,
				EstadoPrevision.CERRADA.getIdentificador(),
				DateUtils.getFechaActual());
	}

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
			throws TooManyResultsException {
		Map pairs = new HashMap();
		pairs.put(TABLE_NAME, COLUMN_DEFINITIONS);
		pairs.put(CAOrganoDbEntityImpl.TABLE_NAME_ORG,
				new DbColumnDef[] { new DbColumnDef("nombreOrgano",
						CAOrganoDbEntityImpl.CAMPO_NOMBRE_LARGO) });
		pairs.put(UsuarioDBEntityImpl.TABLE_NAME, new DbColumnDef[] {
				UsuarioDBEntityImpl.CAMPO_NOMBRE_USUARIO,
				UsuarioDBEntityImpl.CAMPO_APELLIDOS_USUARIO });

		if (ConfigConstants.getInstance()
				.getPermitirTransferenciasEntreArchivos()) {
			pairs.put(ArchivoDbEntityImpl.TABLE_NAME,
					new DbColumnDef[] { new DbColumnDef(
							PrevisionDBEntityImpl.ARCHIVORECEPTOR_COLUMN_NAME,
							ArchivoDbEntityImpl.ID_FIELD) });
		}

		StringBuilder qual = new StringBuilder()
				.append("WHERE ")
				.append(DBUtils.generateJoinCondition(CAMPO_IDORGREMITENTE,
						CAOrganoDbEntityImpl.CAMPO_ID))
				.append(" AND ")
				.append(DBUtils.generateJoinCondition(CAMPO_IDUSRGESTOR,
						UsuarioDBEntityImpl.CAMPO_ID))
				.append(getFiltroArchivosOrganos(vo))
				.append(getFiltroIdGestor(vo.getIdGestor()));

		if (ConfigConstants.getInstance()
				.getPermitirTransferenciasEntreArchivos()) {
			// Añadir la join
			qual.append(" AND ").append(
					DBUtils.generateJoinCondition(CAMPO_IDARCHIVORECEPTOR,
							ArchivoDbEntityImpl.ID_FIELD));
		}

		// Tipos de transferencias
		if (!ArrayUtils.isEmpty(vo.getTipos()))
			qual.append(" AND ").append(
					DBUtils.generateInTokenField(CAMPO_TIPOTRANSEFERENCIA,
							vo.getTipos()));

		// Año
		if (StringUtils.isNotBlank(vo.getAnio()))
			qual.append(" AND ").append(
					DBUtils.generateEQTokenField(CAMPO_ANO, vo.getAnio()));

		// Estados
		if (!ArrayUtils.isEmpty(vo.getEstados()))
			qual.append(" AND ")
					.append(DBUtils.generateInTokenField(CAMPO_ESTADO,
							vo.getEstados()));

		// Fondo
		if (StringUtils.isNotBlank(vo.getFondo()))
			qual.append(" AND ").append(
					DBUtils.generateEQTokenField(CAMPO_IDFONDODESTINO,
							vo.getFondo()));

		// Órgano remitente
		if (StringUtils.isNotBlank(vo.getOrgano()))
			qual.append(" AND ").append(
					DBUtils.generateLikeTokenField(
							// CAOrganoDbEntityImpl.CAMPO_NOMBRE,
							CAOrganoDbEntityImpl.CAMPO_NOMBRE_LARGO,
							vo.getOrgano(), true));
		String concatSymbol = DBUtils.getNativeConcatSyntax(getConnection());
		// Usuario gestor X Nombre
		if (StringUtils.isNotBlank(vo.getUsuario()))
			qual.append(" AND UPPER(")
					.append(UsuarioDBEntityImpl.CAMPO_NOMBRE.getQualifiedName())
					.append(concatSymbol)
					.append("' '")
					.append(concatSymbol)
					.append(DBUtils.getNativeIfNullSintax(getConnection(),
							UsuarioDBEntityImpl.CAMPO_APELLIDOS
									.getQualifiedName(), "''"))
					.append(") LIKE '%").append(vo.getUsuario().toUpperCase())
					.append("%'");

		// Usuario gestor x Id
		/*
		 * if (StringUtils.isNotBlank(vo.getIdGestor())){ qual.append(" AND ")
		 * .append(DBUtils.generateEQTokenField(UsuarioDBEntityImpl.CAMPO_ID,
		 * vo.getIdGestor())); }
		 */

		// Código
		if (StringUtils.isNotBlank(vo.getCodigo())) {
			qual.append(" AND ")
					.append(CodigoTransferenciaUtils
							.textSqlCodigoTransferenciaPrevision(
									CAMPO_ANO.getQualifiedName(),
									CAMPO_ORDEN.getQualifiedName(),
									getConnection())).append(" LIKE '%")
					.append(vo.getCodigo()).append("%'");
		}
		// Fechas
		if ((vo.getFechaInicio() != null) || (vo.getFechaFin() != null)) {
			qual.append(" AND (");

			if (vo.getFechaInicio() != null) {
				// qual.append("(")
				// .append(DBUtils.generateEQTokenField(CAMPO_FECHAFINTRANS,
				// null))
				// .append(" OR ")
				// .append(CAMPO_FECHAFINTRANS.getQualifiedName())
				// .append(">=TO_DATE('")
				// .append(CustomDateFormat.SDF_YYYYMMDD_HHMMSS.format(vo.getFechaInicio()))
				// .append("','YYYY-MM-DD HH24:MI:SS'))");

				qual.append("(")
						.append(DBUtils.generateEQTokenField(
								CAMPO_FECHAFINTRANS, null))
						.append(" OR ")
						.append(CAMPO_FECHAFINTRANS.getQualifiedName())
						.append(">=")
						.append(DBUtils.getNativeToDateSyntax(getConnection(),
								vo.getFechaInicio(),
								CustomDateFormat.SDF_YYYYMMDD_HHMMSS))
						.append(")");

			}

			if (vo.getFechaFin() != null)

			{
				if (vo.getFechaInicio() != null)
					qual.append(" AND ");
				// qual.append("(")
				// .append(DBUtils.generateEQTokenField(CAMPO_FECHAINITRANS,
				// null))
				// .append(" OR ")
				// .append(CAMPO_FECHAINITRANS.getQualifiedName())
				// .append("<=TO_DATE('")
				// .append(CustomDateFormat.SDF_YYYYMMDD_HHMMSS.format(vo.getFechaFin()))
				qual.append("(")
						.append(DBUtils.generateEQTokenField(
								CAMPO_FECHAINITRANS, null))
						.append(" OR ")
						.append(CAMPO_FECHAINITRANS.getQualifiedName())
						.append("<=")
						.append(DBUtils.getNativeToDateSyntax(getConnection(),
								vo.getFechaFin(),
								CustomDateFormat.SDF_YYYYMMDD_HHMMSS))
						.append(")");

			}

			qual.append(")");
		}

		// Order by
		if (vo.getPageInfo() != null) {
			Map criteriosOrdenacion = new HashMap();
			criteriosOrdenacion.put("codigo", new DbColumnDef[] { CAMPO_ANO,
					CAMPO_ORDEN });
			criteriosOrdenacion.put("tipoTransferencia",
					CAMPO_TIPOTRANSEFERENCIA);
			criteriosOrdenacion.put("fechainitrans", CAMPO_FECHAINITRANS);
			criteriosOrdenacion.put("fechafintrans", CAMPO_FECHAFINTRANS);
			criteriosOrdenacion.put("usuario", new DbColumnDef[] {
					UsuarioDBEntityImpl.CAMPO_APELLIDOS_USUARIO,
					UsuarioDBEntityImpl.CAMPO_NOMBRE_USUARIO });
			criteriosOrdenacion.put("organo",
					CAOrganoDbEntityImpl.CAMPO_NOMBRE_ORGANO);
			criteriosOrdenacion.put("estado", CAMPO_ESTADO);

			return getVOS(qual.toString(),
					vo.getPageInfo().getOrderBy(criteriosOrdenacion), pairs,
					PrevisionVO.class, vo.getPageInfo());
		} else {
			qual.append(" ORDER BY ").append(CAMPO_ANO.getQualifiedName())
					.append(" DESC,").append(CAMPO_ORDEN.getQualifiedName())
					.append(" DESC");

			return getVOS(qual.toString(), pairs, PrevisionVO.class);
		}
	}

	/**
	 * Añade el filtro por archivos y organos.
	 *
	 * @param vo
	 *            Información de la consulta.
	 * @return Condiciones SQL.
	 */
	private String getFiltroArchivosOrganos(BusquedaPrevisionesVO vo) {
		StringBuilder qual = new StringBuilder();

		if (!ArrayUtils.isEmpty(vo.getArchivosReceptores())
				|| !ArrayUtils.isEmpty(vo.getOrganosUsuario())) {
			boolean empty = true;
			qual.append(" AND (");

			// Lista de órganos del usuario
			if (!ArrayUtils.isEmpty(vo.getOrganosUsuario())) {
				if (!empty)
					qual.append(" OR ");
				qual.append(DBUtils.generateInTokenField(CAMPO_IDORGREMITENTE,
						vo.getOrganosUsuario()));
				empty = false;
			}

			// Archivos Receptores
			if (!ArrayUtils.isEmpty(vo.getArchivosReceptores())) {
				if (!empty)
					qual.append(" OR ");
				qual.append(DBUtils.generateInTokenField(
						CAMPO_IDARCHIVORECEPTOR, vo.getArchivosReceptores()));
			}

			qual.append(")");
		}

		return qual.toString();
	}

	/**
	 * Añade el filtro por id de usuario gestor.
	 *
	 * @param vo
	 *            Información de la consulta.
	 * @return Condiciones SQL.
	 */
	private String getFiltroIdGestor(String idGestor) {
		StringBuilder qual = new StringBuilder();

		if (!StringUtils.isEmpty(idGestor)) {
			qual.append(" AND ((");
			qual.append(
					DBUtils.generateEQTokenField(CAMPO_ESTADO,
							EstadoPrevision.ABIERTA.getIdentificador()))
					.append(" AND ")
					.append(DBUtils.generateEQTokenField(CAMPO_IDUSRGESTOR,
							idGestor))
					.append(") OR (")
					.append(DBUtils.generateNEQTokenField(CAMPO_ESTADO,
							EstadoPrevision.ABIERTA.getIdentificador()))
					.append("))");
		}

		return qual.toString();
	}

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
			int[] tiposTransferencia) {
		Map pairs = new HashMap();
		pairs.put(TABLE_NAME, null);
		pairs.put(UsuarioDBEntityImpl.TABLE_NAME, UsuarioDBEntityImpl.COL_DEFS);

		StringBuilder qual = new StringBuilder().append("WHERE ").append(
				DBUtils.generateInTokenField(CAMPO_ESTADO, new int[] {
						EstadoPrevision.ABIERTA.getIdentificador(),
						EstadoPrevision.ENVIADA.getIdentificador(),
						EstadoPrevision.RECHAZADA.getIdentificador() }));

		// Tipos de transferencia
		if ((tiposTransferencia != null) && (tiposTransferencia.length > 0))
			qual.append(" AND ").append(
					DBUtils.generateInTokenField(CAMPO_TIPOTRANSEFERENCIA,
							tiposTransferencia));

		// Órgano
		if (StringUtils.isNotBlank(idOrgano))
			qual.append(" AND ")
					.append(DBUtils.generateEQTokenField(CAMPO_IDORGREMITENTE,
							idOrgano));

		qual.append(" AND ").append(
				DBUtils.generateJoinCondition(CAMPO_IDUSRGESTOR,
						UsuarioDBEntityImpl.CAMPO_ID));

		qual.append(" ORDER BY ")
				.append(UsuarioDBEntityImpl.CAMPO_APELLIDOS.getQualifiedName())
				.append(",")
				.append(UsuarioDBEntityImpl.CAMPO_NOMBRE.getQualifiedName());

		return getDistinctVOS(qual.toString(), pairs, UsuarioVO.class);
	}

	/**
	 * Obtiene la lista de previsiones caducadas.
	 *
	 * @return Lista de previsiones.
	 */
	public List getPrevisionesCaducadas() {
		Map pairs = new HashMap();
		pairs.put(TABLE_NAME, COLUMN_DEFINITIONS);
		pairs.put(CAOrganoDbEntityImpl.TABLE_NAME_ORG,
				new DbColumnDef[] { new DbColumnDef("nombreOrgano",
						CAOrganoDbEntityImpl.CAMPO_NOMBRE_LARGO) });
		pairs.put(UsuarioDBEntityImpl.TABLE_NAME, new DbColumnDef[] {
				UsuarioDBEntityImpl.CAMPO_NOMBRE_USUARIO,
				UsuarioDBEntityImpl.CAMPO_APELLIDOS_USUARIO });
		StringBuilder qual = new StringBuilder()
				.append("WHERE ")
				.append(DBUtils.generateJoinCondition(CAMPO_IDORGREMITENTE,
						CAOrganoDbEntityImpl.CAMPO_ID))
				.append(" AND ")
				.append(DBUtils.generateJoinCondition(CAMPO_IDUSRGESTOR,
						UsuarioDBEntityImpl.CAMPO_ID))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(CAMPO_ESTADO,
						EstadoPrevision.ACEPTADA.getIdentificador()))
				.append(" AND ")
				.append(CAMPO_FECHAFINTRANS)
				.append("<")
				.append(DBUtils.getNativeSysDateSyntax(getConnection()))
				.append(" AND ")
				.append(CAMPO_ID)
				.append(" NOT IN (SELECT ")
				.append(RelacionEntregaDBEntityImpl.CAMPO_IDPREVISION)
				.append(" FROM ")
				.append(RelacionEntregaDBEntityImpl.TABLE_NAME)
				.append(" WHERE ")
				.append(DBUtils.generateInTokenField(
						RelacionEntregaDBEntityImpl.CAMPO_ESTADO,
						new int[] {
								EstadoREntrega.ABIERTA.getIdentificador(),
								EstadoREntrega.ENVIADA.getIdentificador(),
								EstadoREntrega.RECIBIDA.getIdentificador(),
								EstadoREntrega.CON_ERRORES_COTEJO
										.getIdentificador(),
								EstadoREntrega.CORREGIDA_ERRORES
										.getIdentificador() }))
				.append(") ORDER BY ").append(CAMPO_ANO.getQualifiedName())
				.append(" DESC,").append(CAMPO_ORDEN.getQualifiedName())
				.append(" DESC");

		return getVOS(qual.toString(), pairs, PrevisionVO.class);
	}

	public PrevisionVO getPrevisionByVO(PrevisionVO prevision) {
		StringBuilder qual = new StringBuilder(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(CAMPO_IDORGREMITENTE,
						prevision.getIdorgremitente()))
				.append(DBUtils.AND)
				.append(DBUtils.generateEQTokenField(CAMPO_ANO,
						prevision.getAno()))
				.append(DBUtils.AND)
				.append(DBUtils.generateEQTokenField(CAMPO_IDFONDODESTINO,
						prevision.getIdfondodestino()))
				.append(DBUtils.AND)
				.append(DBUtils.generateEQTokenField(CAMPO_IDARCHIVORECEPTOR,
						prevision.getIdarchivoreceptor()))
				.append(DBUtils.AND)
				.append(DBUtils.generateEQTokenField(CAMPO_IDUSRGESTOR,
						prevision.getIdusrgestor()))
				.append(DBUtils.AND)
				.append(DBUtils.generateEQTokenField(CAMPO_ESTADO,
						prevision.getEstado()));

		return getPrevision(qual.toString());

	}

}