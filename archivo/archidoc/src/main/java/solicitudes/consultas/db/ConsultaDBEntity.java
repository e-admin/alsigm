package solicitudes.consultas.db;

import gcontrol.db.ArchivoDbEntityImpl;
import gcontrol.vos.UsuarioVO;
import ieci.core.db.DbColumnDef;
import ieci.core.db.DbConnection;
import ieci.core.db.DbDataType;
import ieci.core.db.DbDeleteFns;
import ieci.core.db.DbInsertFns;
import ieci.core.db.DbSelectFns;
import ieci.core.db.DbUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import solicitudes.consultas.ConsultasConstants;
import solicitudes.consultas.vos.BusquedaVO;
import solicitudes.consultas.vos.ConsultaVO;
import solicitudes.db.DetalleDBEntityImpl;
import solicitudes.prestamos.PrestamosConstants;
import solicitudes.utils.PropertyHelper;

import common.CodigoTransferenciaUtils;
import common.ConfigConstants;
import common.db.ConstraintType;
import common.db.DBCommand;
import common.db.DBEntity;
import common.db.DBUtils;
import common.db.DbDataSource;
import common.db.SigiaDbInputRecord;
import common.exceptions.TooManyResultsException;
import common.lang.MutableInt;
import common.util.ArrayUtils;
import common.util.CustomDateFormat;
import common.util.DateUtils;

/**
 * Clase que encapsula la persisntencia sobra la tabla de consultas
 */
public class ConsultaDBEntity extends DBEntity implements IConsultaDBEntity {

	public static final String TABLE_NAME = "ASGPCONSULTA";

	public static final String ID = "id";
	public static final String ANO = "ano";
	public static final String ORDEN = "orden";
	public static final String TEMA = "tema";
	public static final String TIPOENTCONSULTORA = "tipoentconsultora";
	public static final String NORGCONSULTOR = "norgconsultor";
	public static final String NUSRCONSULTOR = "nusrconsultor";
	public static final String FINICIALRESERVA = "finicialreserva";
	public static final String FENTREGA = "fentrega";
	public static final String FMAXFINCONSULTA = "fmaxfinconsulta";
	public static final String ESTADO = "estado";
	public static final String FESTADO = "festado";
	public static final String MOTIVO = "motivo";
	public static final String IDARCHIVO = "idarchivo";
	public static final String IDUSRSOLICITANTE = "idusrsolicitante";
	public static final String INFORMACION = "informacion";
	public static final String TIPO = "tipo";
	public static final String DATOSAUTORIZADO_COLUMN_NAME = "datosautorizado";
	public static final String DATOSSOLICITANTE_COLUMN_NAME = "datossolicitante";
	public static final String TIPOENTREGA_COLUMN_NAME = "tipoentrega";
	public static final String OBSERVACIONES = "observaciones";
	public static final String ID_MOTIVO = "idmotivo";
	public static final String IDUSRCSALA = "IDUSRCSALA";

	public static final DbColumnDef CAMPO_ID = new DbColumnDef(null,
			TABLE_NAME, ID, DbDataType.SHORT_TEXT, 32, false);
	public static final DbColumnDef CAMPO_ANO = new DbColumnDef(null,
			TABLE_NAME, ANO, DbDataType.SHORT_TEXT, 4, false);
	public static final DbColumnDef CAMPO_ORDEN = new DbColumnDef(null,
			TABLE_NAME, ORDEN, DbDataType.LONG_INTEGER, 10, false);
	public static final DbColumnDef CAMPO_TEMA = new DbColumnDef(null,
			TABLE_NAME, TEMA, DbDataType.SHORT_TEXT, 254, false);
	public static final DbColumnDef CAMPO_TIPOENTCONSULTORA = new DbColumnDef(
			null, TABLE_NAME, TIPOENTCONSULTORA, DbDataType.LONG_INTEGER, 2,
			false);
	public static final DbColumnDef CAMPO_NORGCONSULTOR = new DbColumnDef(null,
			TABLE_NAME, NORGCONSULTOR, DbDataType.SHORT_TEXT, 254, true);
	public static final DbColumnDef CAMPO_NUSRCONSULTOR = new DbColumnDef(null,
			TABLE_NAME, NUSRCONSULTOR, DbDataType.SHORT_TEXT, 254, false);
	public static final DbColumnDef CAMPO_FINICIALRESERVA = new DbColumnDef(
			null, TABLE_NAME, FINICIALRESERVA, DbDataType.DATE_TIME, true);
	public static final DbColumnDef CAMPO_FENTREGA = new DbColumnDef(null,
			TABLE_NAME, FENTREGA, DbDataType.DATE_TIME, true);
	public static final DbColumnDef CAMPO_FMAXFINCONSULTA = new DbColumnDef(
			null, TABLE_NAME, FMAXFINCONSULTA, DbDataType.DATE_TIME, true);
	public static final DbColumnDef CAMPO_ESTADO = new DbColumnDef(null,
			TABLE_NAME, ESTADO, DbDataType.LONG_INTEGER, 2, false);
	public static final DbColumnDef CAMPO_FESTADO = new DbColumnDef(null,
			TABLE_NAME, FESTADO, DbDataType.DATE_TIME, true);
	public static final DbColumnDef CAMPO_MOTIVO = new DbColumnDef(null,
			TABLE_NAME, MOTIVO, DbDataType.SHORT_TEXT, 254, true);
	public static final DbColumnDef CAMPO_IDARCHIVO = new DbColumnDef(null,
			TABLE_NAME, IDARCHIVO, DbDataType.SHORT_TEXT, 32, false);
	public static final DbColumnDef CAMPO_IDUSRSOLICITANTE = new DbColumnDef(
			null, TABLE_NAME, IDUSRSOLICITANTE, DbDataType.SHORT_TEXT, 32,
			false);
	public static final DbColumnDef CAMPO_INFORMACION = new DbColumnDef(null,
			TABLE_NAME, INFORMACION, DbDataType.SHORT_TEXT, 1024, true);
	public static final DbColumnDef CAMPO_TIPO = new DbColumnDef(null,
			TABLE_NAME, TIPO, DbDataType.LONG_INTEGER, 2, false);
	public static final DbColumnDef CAMPO_DATOSAUTORIZADO = new DbColumnDef(
			null, TABLE_NAME, DATOSAUTORIZADO_COLUMN_NAME,
			DbDataType.SHORT_TEXT, 254, true);
	public static final DbColumnDef CAMPO_DATOSSOLICITANTE = new DbColumnDef(
			null, TABLE_NAME, DATOSSOLICITANTE_COLUMN_NAME,
			DbDataType.SHORT_TEXT, 512, true);
	public static final DbColumnDef CAMPO_TIPOENTREGA = new DbColumnDef(null,
			TABLE_NAME, TIPOENTREGA_COLUMN_NAME, DbDataType.SHORT_TEXT, 254,
			true);
	public static final DbColumnDef CAMPO_OBSERVACIONES = new DbColumnDef(null,
			TABLE_NAME, OBSERVACIONES, DbDataType.SHORT_TEXT, 254, true);
	public static final DbColumnDef CAMPO_ID_MOTIVO = new DbColumnDef(null,
			TABLE_NAME, ID_MOTIVO, DbDataType.SHORT_TEXT, 254, false);
	public static final DbColumnDef CAMPO_IDUSRCSALA = new DbColumnDef(null,
			TABLE_NAME, IDUSRCSALA, DbDataType.SHORT_TEXT, 32, false);

	public static final DbColumnDef[] COLUMN_DEFINITIONS = { CAMPO_ID,
			CAMPO_ANO, CAMPO_ORDEN, CAMPO_TEMA, CAMPO_TIPOENTCONSULTORA,
			CAMPO_NORGCONSULTOR, CAMPO_NUSRCONSULTOR, CAMPO_FINICIALRESERVA,
			CAMPO_FENTREGA, CAMPO_FMAXFINCONSULTA, CAMPO_ESTADO, CAMPO_FESTADO,
			CAMPO_MOTIVO, CAMPO_IDARCHIVO, CAMPO_IDUSRSOLICITANTE,
			CAMPO_INFORMACION, CAMPO_TIPO, CAMPO_DATOSAUTORIZADO,
			CAMPO_DATOSSOLICITANTE, CAMPO_TIPOENTREGA, CAMPO_OBSERVACIONES,
			CAMPO_ID_MOTIVO, CAMPO_IDUSRCSALA };

	public static final String COLUM_NAMES_LIST = DbUtil
			.getColumnNames(COLUMN_DEFINITIONS);

	/**
	 * Obtiene el nombre de la tabla
	 * 
	 * @return
	 */
	public String getTableName() {
		return TABLE_NAME;
	}

	/**
	 * Constructor por defecto de la clase
	 * 
	 * @param dataSource
	 */
	public ConsultaDBEntity(DbDataSource dataSource) {
		super(dataSource);
	}

	public ConsultaDBEntity(DbConnection conn) {
		super(conn);
	}

	/**
	 * Obtitene las consultas para el usuario gestor a partir del nombre del
	 * organismo consultor y de sus descendientes. Si ademas tiene filtros de
	 * busqueda, filtra los anteriores resultados por: fecha de entrega,usuario
	 * solicitante, fecha estado y estado.
	 * 
	 * @param busqueda
	 *            Objeto con los filtros para aplicar en la busqueda
	 * @return Listado de las consultas que se ajustan a los criterios indicados
	 * @throws TooManyResultsException
	 *             si el número de resultados es excesivo.
	 */
	public List getConsultas(BusquedaVO busqueda, String[] idsArchivo)
			throws TooManyResultsException {

		// if(ArrayUtils.isEmpty(idsArchivo)) return new ArrayList();

		List consultas = null;
		Map pairsTableNamesColsDefs = null;
		ConsultaObject consulta = null;
		StringBuffer qual = new StringBuffer();

		// Comprobamos si tiene restricciones para añardirlas
		if (busqueda != null) {
			consulta = getWhereClause(busqueda);

			qual.append(DBUtils.WHERE).append(consulta.getWhere());

			pairsTableNamesColsDefs = consulta.getPairsTableNamesColsDefs();
		} else {
			pairsTableNamesColsDefs = new HashMap();
			pairsTableNamesColsDefs.put(TABLE_NAME, COLUMN_DEFINITIONS);
		}

		if (ConfigConstants.getInstance()
				.getPermitirTransferenciasEntreArchivos()) {
			// Añadir la join
			qual.append(" AND ").append(
					DBUtils.generateJoinCondition(CAMPO_IDARCHIVO,
							ArchivoDbEntityImpl.ID_FIELD));
		}

		if (ConfigConstants.getInstance()
				.getPermitirTransferenciasEntreArchivos()) {
			pairsTableNamesColsDefs.put(ArchivoDbEntityImpl.TABLE_NAME,
					new DbColumnDef[] {});
		}

		// Datos autorizado
		if (StringUtils.isNotEmpty(busqueda.getDatosautorizado())) {
			qual.append(" AND ").append(
					DBUtils.generateLikeFieldQualified(CAMPO_DATOSAUTORIZADO,
							busqueda.getDatosautorizado(), TABLE_NAME,
							DBUtils.CONTIENE_LIKE));
		}

		// Tipo entrega
		if (StringUtils.isNotEmpty(busqueda.getTipoentrega())) {
			qual.append(" AND ").append(
					DBUtils.generateLikeFieldQualified(CAMPO_TIPOENTREGA,
							busqueda.getTipoentrega(), TABLE_NAME,
							DBUtils.CONTIENE_LIKE));
		}

		// IdUsrSolicitante
		if (StringUtils.isNotEmpty(busqueda.getIdSolicitante())) {
			qual.append(" AND ").append(
					DBUtils.generateEQTokenField(CAMPO_IDUSRSOLICITANTE,
							busqueda.getIdSolicitante()));
		}

		// Restriccion Usuarios
		if (StringUtils.isNotEmpty(busqueda.getIdAppUser())) {
			String subQuery = getSubQueryRestriccionAppUser(
					busqueda.getIdAppUser(), busqueda.getIdsArchivosUser());
			qual.append(DBUtils.AND).append(
					DBUtils.generateInTokenFieldSubQuery(CAMPO_ID, subQuery));
		}

		// Archivos
		if (ArrayUtils.isNotEmpty(idsArchivo)) {
			qual.append(" AND ").append(
					DBUtils.generateORTokens(CAMPO_IDARCHIVO, idsArchivo));
		}

		// Número de expediente
		if (StringUtils.isNotEmpty(busqueda.getExpedienteudoc())) {
			// Añadimos la tabla de ASGPSOLICITUDUDOC al conjunto de tablas
			pairsTableNamesColsDefs.put(DetalleDBEntityImpl.TABLE_NAME, null);

			// Añadir la join
			qual.append(DBUtils.AND)
					.append(DBUtils.generateJoinCondition(CAMPO_ID,
							DetalleDBEntityImpl.IDSOLICITUD_FIELD))
					.append(DBUtils.AND)
					.append(DBUtils.generateLikeTokenField(
							DetalleDBEntityImpl.EXPEDIENTEUDOC_FIELD,
							busqueda.getExpedienteudoc()));
		}

		if (busqueda != null && busqueda.getPageInfo() != null) {
			Map criteriosOrdenacion = new HashMap();
			criteriosOrdenacion.put("codigo", new DbColumnDef[] { CAMPO_ANO,
					CAMPO_ORDEN });
			criteriosOrdenacion.put("norgconsultor", CAMPO_NORGCONSULTOR);
			criteriosOrdenacion.put("nusrconsultor", CAMPO_NUSRCONSULTOR);
			criteriosOrdenacion.put("estado", CAMPO_ESTADO);
			criteriosOrdenacion.put("festado", CAMPO_FESTADO);

			/*
			 * consultas = getVOS(qual.toString(),
			 * busqueda.getPageInfo().getOrderBy(criteriosOrdenacion),
			 * pairsTableNamesColsDefs, ConsultaVO.class,
			 * busqueda.getPageInfo());
			 */

			consultas = getDistinctVOS(qual.toString(), busqueda.getPageInfo()
					.getOrderBy(criteriosOrdenacion), pairsTableNamesColsDefs,
					ConsultaVO.class, busqueda.getPageInfo());
		} else {
			// Añadir la ordenacion
			qual.append(" ORDER BY  ");
			qual.append(CAMPO_FESTADO.getName());
			qual.append(" DESC");

			// consultas = getVOS(qual.toString(), pairsTableNamesColsDefs,
			// ConsultaVO.class);

			consultas = getDistinctVOS(qual.toString(),
					pairsTableNamesColsDefs, ConsultaVO.class);
		}

		return consultas;
	}

	private String getSubQueryRestriccionAppUser(String idAppUsr,
			String[] idsArchivo) {
		StringBuffer subQuery = new StringBuffer();

		int[] estadosIniciales = new int[] { PrestamosConstants.ESTADO_PRESTAMO_ABIERTO };

		if (StringUtils.isNotEmpty(idAppUsr)) {
			subQuery.append(DBUtils.SELECT)
					.append(CAMPO_ID.getQualifiedName())
					.append(DBUtils.FROM)
					.append(TABLE_NAME)
					.append(DBUtils.WHERE)
					.append(DBUtils.ABRIR_PARENTESIS)
					.append(DBUtils.generateInTokenField(CAMPO_ESTADO,
							estadosIniciales))
					.append(DBUtils.AND)
					.append(DBUtils.generateEQTokenField(
							CAMPO_IDUSRSOLICITANTE, idAppUsr))
					.append(DBUtils.CERRAR_PARENTESIS);

			subQuery.append(DBUtils.OR).append(
					DBUtils.generateEQTokenField(CAMPO_IDUSRSOLICITANTE,
							idAppUsr));

			if (ArrayUtils.isNotEmptyOrBlank(idsArchivo)) {
				subQuery.append(DBUtils.OR)
						.append(DBUtils.ABRIR_PARENTESIS)
						.append(DBUtils.generateInTokenField(CAMPO_IDARCHIVO,
								idsArchivo)).append(DBUtils.CERRAR_PARENTESIS);
			}
		}

		return subQuery.toString();
	}

	/**
	 * Obtiene un consulta a partir de su identificador
	 * 
	 * @param codigoConsulta
	 *            Identificador de la consulta que deseamos recuperar
	 */
	public ConsultaVO getConsulta(String codigoConsulta) {
		final String qual = new StringBuffer().append(" WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_ID, codigoConsulta))
				.toString();

		return (ConsultaVO) getVO(qual, TABLE_NAME, COLUMN_DEFINITIONS,
				ConsultaVO.class);
	}

	/**
	 * Indica si un usuario tiene consultas en los estados determinados.
	 * 
	 * @param idUsuario
	 *            Identificador de usuario.
	 * @param estados
	 *            Estados de las consultas.
	 * @return true si el usuario tiene consultas en curso.
	 */
	public boolean hasConsultas(String idUsuario, String[] estados) {
		StringBuffer qual = new StringBuffer();

		if (StringUtils.isNotBlank(idUsuario)) {
			qual.append(DBUtils.getCondition(qual.toString())).append(
					DBUtils.generateEQTokenField(CAMPO_IDUSRSOLICITANTE,
							idUsuario));
		}

		if (!ArrayUtils.isEmpty(estados)) {
			qual.append(DBUtils.getCondition(qual.toString())).append(
					DBUtils.generateInTokenField(CAMPO_ESTADO, estados));
		}

		return getVOCount(qual.toString(), TABLE_NAME) > 0;
	}

	/**
	 * Devuelve el sql para obtener un listado de las consultas en las que el
	 * usuario figura como solicitante y que se encuentran en alguno de los
	 * estados que se indican.
	 * 
	 * @param nUsuarioSolicitante
	 *            Identificador del usuario solicitante
	 * @param estados
	 *            Conjunto de posibles estados para una consulta
	 * @return Sql
	 */
	private String getConsultasXUsuarioConsultorSql(String nUsuarioConsultor,
			int[] estados, String[] idsArchivos) {
		StringBuffer qual = new StringBuffer(" WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_IDUSRSOLICITANTE,
						nUsuarioConsultor));
		if (estados != null)
			qual.append(" AND ").append(
					DBUtils.generateORTokens(CAMPO_ESTADO, estados));

		if (ArrayUtils.isNotEmpty(idsArchivos))
			qual.append(" AND ").append(
					DBUtils.generateORTokens(CAMPO_IDARCHIVO, idsArchivos));

		return qual.toString();
	}

	/*
	 * (sin Javadoc)
	 * 
	 * @see
	 * solicitudes.consultas.db.IConsultaDBEntity#getCountConsultasXUsuarioConsultor
	 * (java.lang.String, int[])
	 */
	public int getCountConsultasXUsuarioConsultor(String nUsuarioConsultor,
			int[] estados) {
		String qual = getConsultasXUsuarioConsultorSql(nUsuarioConsultor,
				estados, null);
		return getVOCount(qual, TABLE_NAME);
	}

	/*
	 * (sin Javadoc)
	 * 
	 * @see
	 * solicitudes.consultas.db.IConsultaDBEntity#getCountConsultasXUsuarioConsultor
	 * (java.lang.String, int[])
	 */
	public int getCountConsultasXUsuarioConsultor(String nUsuarioConsultor,
			int[] estados, String[] idsArchivo) {
		String qual = getConsultasXUsuarioConsultorSql(nUsuarioConsultor,
				estados, idsArchivo);
		return getVOCount(qual, TABLE_NAME);
	}

	/*
	 * (sin Javadoc)
	 * 
	 * @see
	 * solicitudes.consultas.db.IConsultaDBEntity#getConsultasXUsuarioConsultor
	 * (java.lang.String, int[])
	 */
	public List getConsultasXUsuarioConsultor(String nUsuarioConsultor,
			int[] estados) {
		String qual = getConsultasXUsuarioConsultorSql(nUsuarioConsultor,
				estados, null);
		return getVOS(qual, TABLE_NAME, COLUMN_DEFINITIONS, ConsultaVO.class);
	}

	public Collection getConsultasXUsuarioConsultorAbiertos(String nUsuario) {
		final String qual = new StringBuffer()
				.append(" WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_NUSRCONSULTOR,
						nUsuario))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(CAMPO_ESTADO,
						ConsultasConstants.ESTADO_CONSULTA_ABIERTA))
				.append(" ORDER BY  ").append(CAMPO_FESTADO.getName())
				.append(" DESC ").toString();

		return getVOS(qual, TABLE_NAME, COLUMN_DEFINITIONS, ConsultaVO.class);
	}

	/*
	 * (sin Javadoc)
	 * 
	 * @see
	 * solicitudes.consultas.db.IConsultaDBEntity#getCountConsultasXEstados(
	 * java.lang.String[])
	 */
	public int getCountConsultasXEstados(String[] estados, String[] idsArchivo) {
		StringBuffer qual = new StringBuffer().append(" WHERE 1=1");

		if (!ArrayUtils.isEmpty(estados)) {
			qual.append(" AND ")
					.append(DBUtils.generateORTokens(CAMPO_ESTADO, estados))
					.toString();
		}
		if (!ArrayUtils.isEmpty(idsArchivo)) {
			qual.append(" AND ")
					.append(DBUtils.generateORTokens(CAMPO_IDARCHIVO,
							idsArchivo)).toString();

		} else
			return 0;

		return getVOCount(qual.toString(), TABLE_NAME);
	}

	/**
	 * Obtiene un listado de consultas que se encuentran en alguno de los
	 * estados indicados.
	 * 
	 * @param estados
	 *            Listado de los posibles estados en que deseamos que se
	 *            encuentre las consultas buscadas
	 * @param busqueda
	 *            Objeto con las restriccion para los resultados de la busqueda:
	 *            fecha de estado, entrega y usuario solicitante.
	 * @return Listado de las consultas encontradas que se encontraban en alguno
	 *         de los estados solicitados.
	 * @throws TooManyResultsException
	 *             si el número de resultados excede el máximo.
	 */
	public List getConsultasXEstados(List estados, BusquedaVO busqueda,
			String[] idsArchivo) throws TooManyResultsException {

		List consultas = null;
		Map pairsTableNamesColsDefs = null;
		ConsultaObject consulta = null;
		StringBuffer qual = new StringBuffer();
		// Transformamos la lista en array
		String los_estados[] = new String[estados.size()];
		for (int i = 0; i < estados.size(); i++) {
			los_estados[i] = estados.get(i).toString();
		}

		qual.append("WHERE");
		qual.append(DBUtils.generateORTokens(CAMPO_ESTADO, los_estados));

		if (ArrayUtils.isNotEmpty(idsArchivo)) {
			qual.append(" AND ");
			qual.append(DBUtils.generateORTokens(CAMPO_IDARCHIVO, idsArchivo));
		} else {
			return new ArrayList();
		}

		// Comprobamos si tiene restricciones para añardirlas
		if (busqueda != null) {
			consulta = getWhereClause(busqueda);

			qual.append(consulta.getWhere());
			pairsTableNamesColsDefs = consulta.getPairsTableNamesColsDefs();
		} else {
			pairsTableNamesColsDefs = new HashMap();
			pairsTableNamesColsDefs.put(TABLE_NAME, COLUMN_DEFINITIONS);
		}

		if (busqueda != null && busqueda.getPageInfo() != null) {
			Map criteriosOrdenacion = new HashMap();
			criteriosOrdenacion.put("codigo", new DbColumnDef[] { CAMPO_ANO,
					CAMPO_ORDEN });
			criteriosOrdenacion.put("norgconsultor", CAMPO_NORGCONSULTOR);
			criteriosOrdenacion.put("nusrconsultor", CAMPO_NUSRCONSULTOR);
			criteriosOrdenacion.put("estado", CAMPO_ESTADO);
			criteriosOrdenacion.put("festado", CAMPO_FESTADO);

			consultas = getVOS(qual.toString(), busqueda.getPageInfo()
					.getOrderBy(criteriosOrdenacion), pairsTableNamesColsDefs,
					ConsultaVO.class, busqueda.getPageInfo());
		} else {
			// Añadir la ordenacion
			qual.append(" ORDER BY  ");
			qual.append(CAMPO_FESTADO.getName());
			qual.append(" DESC  ");

			consultas = getVOS(qual.toString(), pairsTableNamesColsDefs,
					ConsultaVO.class);
		}

		return consultas;
	}

	/**
	 * Obtiene las consultas que pertenecen al usuario indicador(como
	 * solicitante) y se encuentra en unos de los estado pasados. En caso de que
	 * se pase el filtro de busqueda estos criterios deberan ser tambien
	 * cumplidos: fecha entrega,fescha estado.
	 * 
	 * @param idUsuario
	 *            Identificador del usuario del que deseamos obtener las
	 *            consultas
	 * @param estados
	 *            Listado de los estados por los que deseamo restringir
	 * @param busqueda
	 *            Objeto busqueda con las restricciones posibles, o null en caso
	 *            de no haber
	 * @return Listado de las consultas que cumplen el filtro
	 * @throws TooManyResultsException
	 *             si el número de resultados excede el máximo.
	 */
	public Collection getConsultasXEstadosYUsuario(String idUsuario,
			List estados, BusquedaVO busqueda) throws TooManyResultsException {
		List consultas = null;
		Map pairsTableNamesColsDefs = null;
		ConsultaObject consulta = null;
		StringBuffer qual = new StringBuffer();

		// Transformamos la lista en array
		String los_estados[] = new String[estados.size()];
		for (int i = 0; i < estados.size(); i++) {
			los_estados[i] = estados.get(i).toString();
		}

		qual.append("WHERE");
		if (estados != null && estados.size() > 0) {
			qual.append(DBUtils.generateORTokens(CAMPO_ESTADO, los_estados));
			qual.append(" AND ");
		}
		qual.append(" (" + CAMPO_IDUSRSOLICITANTE.getName() + "='" + idUsuario
				+ "') ");

		// Comprobamos si tiene restricciones para añardirlas
		if (busqueda != null) {
			consulta = getWhereClause(busqueda);

			qual.append(consulta.getWhere());
			pairsTableNamesColsDefs = consulta.getPairsTableNamesColsDefs();
		} else {
			pairsTableNamesColsDefs = new HashMap();
			pairsTableNamesColsDefs.put(TABLE_NAME, COLUMN_DEFINITIONS);
		}

		if (busqueda != null && busqueda.getPageInfo() != null) {
			Map criteriosOrdenacion = new HashMap();
			criteriosOrdenacion.put("codigo", new DbColumnDef[] { CAMPO_ANO,
					CAMPO_ORDEN });
			criteriosOrdenacion.put("norgconsultor", CAMPO_NORGCONSULTOR);
			criteriosOrdenacion.put("nusrconsultor", CAMPO_NUSRCONSULTOR);
			criteriosOrdenacion.put("estado", CAMPO_ESTADO);
			criteriosOrdenacion.put("festado", CAMPO_FESTADO);

			consultas = getVOS(qual.toString(), busqueda.getPageInfo()
					.getOrderBy(criteriosOrdenacion), pairsTableNamesColsDefs,
					ConsultaVO.class, busqueda.getPageInfo());
		} else {
			// Añadir la ordenacion
			qual.append(" ORDER BY  ");
			qual.append(CAMPO_FESTADO.getName());
			qual.append(" DESC  ");

			consultas = getVOS(qual.toString(), pairsTableNamesColsDefs,
					ConsultaVO.class);
		}

		return consultas;
	}

	/**
	 * Obtiene un listado de las consultas que tiene abiertas un usuario como
	 * usuario solicitante.
	 * 
	 * @param idUsuario
	 *            Identificador del usuario del que deseamos obtener las
	 *            consultas
	 */
	public Collection getConsultasXUsuarioGestorAbiertos(String idUsuario) {
		final String qual = new StringBuffer()
				.append("WHERE")
				.append(DBUtils.generateEQTokenField(CAMPO_IDUSRSOLICITANTE,
						idUsuario))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(CAMPO_ESTADO,
						ConsultasConstants.ESTADO_CONSULTA_ABIERTA))
				.append(" ORDER BY  ").append(CAMPO_FESTADO.getName())
				.append(" DESC ").toString();

		return getVOS(qual, TABLE_NAME, COLUMN_DEFINITIONS, ConsultaVO.class);
	}

	/**
	 * Realiza la actualizacion de una consulta.
	 * 
	 * @param consultaVO
	 *            Consulta que deseamos actualizar
	 */
	public void updateConsulta(ConsultaVO consultaVO) {
		final String qual = new StringBuffer()
				.append("WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_ID,
						consultaVO.getId())).toString();

		HashMap mapColumnsToUpdate = new HashMap();

		mapColumnsToUpdate.put(CAMPO_ANO, consultaVO.getAno());
		mapColumnsToUpdate.put(CAMPO_ORDEN, new Integer(consultaVO.getOrden()));
		mapColumnsToUpdate.put(CAMPO_TEMA, consultaVO.getTema());
		mapColumnsToUpdate.put(CAMPO_TIPOENTCONSULTORA,
				consultaVO.getTipoentconsultora());
		mapColumnsToUpdate.put(CAMPO_NORGCONSULTOR,
				consultaVO.getNorgconsultor());
		mapColumnsToUpdate.put(CAMPO_NUSRCONSULTOR,
				consultaVO.getNusrconsultor());
		mapColumnsToUpdate.put(CAMPO_FINICIALRESERVA,
				consultaVO.getFinicialreserva());
		mapColumnsToUpdate.put(CAMPO_FENTREGA, consultaVO.getFentrega());
		mapColumnsToUpdate.put(CAMPO_FMAXFINCONSULTA,
				consultaVO.getFmaxfinconsulta());
		mapColumnsToUpdate.put(CAMPO_ESTADO,
				new Integer(consultaVO.getEstado()));
		mapColumnsToUpdate.put(CAMPO_FESTADO, consultaVO.getFestado());
		mapColumnsToUpdate.put(CAMPO_MOTIVO, consultaVO.getMotivo());
		mapColumnsToUpdate.put(CAMPO_IDARCHIVO, consultaVO.getIdarchivo());
		mapColumnsToUpdate.put(CAMPO_IDUSRSOLICITANTE,
				consultaVO.getIdusrsolicitante());
		mapColumnsToUpdate.put(CAMPO_INFORMACION, consultaVO.getInformacion());
		mapColumnsToUpdate.put(CAMPO_TIPO, new Integer(consultaVO.getTipo()));
		mapColumnsToUpdate.put(CAMPO_DATOSAUTORIZADO,
				consultaVO.getDatosautorizado());
		mapColumnsToUpdate.put(CAMPO_DATOSSOLICITANTE,
				consultaVO.getDatossolicitante());
		mapColumnsToUpdate.put(CAMPO_TIPOENTREGA, consultaVO.getTipoentrega());
		mapColumnsToUpdate.put(CAMPO_OBSERVACIONES,
				consultaVO.getObservaciones());
		mapColumnsToUpdate.put(CAMPO_ID_MOTIVO, consultaVO.getIdMotivo());
		mapColumnsToUpdate.put(CAMPO_IDUSRCSALA, consultaVO.getIdusrcsala());

		updateFields(qual, mapColumnsToUpdate, TABLE_NAME);
	}

	/**
	 * Realiza la inserción de una consulta en la base de datos.
	 * 
	 * @param consulta
	 *            Consulta con los datos que deseamos insertar en la bd.
	 */
	public void insertConsulta(final ConsultaVO consulta) {
		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				consulta.setId(getGuid(consulta.getId()));

				SigiaDbInputRecord inputRecord = new SigiaDbInputRecord(
						COLUMN_DEFINITIONS, consulta);
				DbInsertFns.insert(conn, TABLE_NAME, COLUM_NAMES_LIST,
						inputRecord);
			}
		};
		command.execute();
	}

	/**
	 * Realiza el borrado de las consultas seleccionadas.
	 * 
	 * @param idConsultaonsulta
	 *            identificador de la consulta a eliminar.
	 */
	public void deleteConsulta(String idConsulta) {
		final String qual = new StringBuffer().append(" WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_ID, idConsulta))
				.toString();

		DBCommand command = new DBCommand(this) {

			public void codeLogic(DbConnection conn) throws Exception {
				DbDeleteFns.delete(conn, TABLE_NAME, qual);
			}

		};

		command.execute();
	}

	/**
	 * Obtiene un listado de consultas a partir de sus identificadores.
	 * 
	 * @param codigos
	 *            Listado de los identificadores de las consultas que deseamo
	 *            recuperar
	 * @return Listado de las consultas
	 */
	public Collection getConsultas(String[] codigos) {
		String qual = new StringBuffer().append("WHERE")
				.append(DBUtils.generateORTokens(CAMPO_ID, codigos))
				.append(" ORDER BY  ").append(CAMPO_FESTADO.getName())
				.append(" DESC  ").toString();

		return getVOS(qual, TABLE_NAME, COLUMN_DEFINITIONS, ConsultaVO.class);
	}

	/**
	 * Obtiene el número de detalles que están asociados a un determinada
	 * consulta.
	 * 
	 * @param idConsulta
	 *            Identificador de la consulta del que deseamos conocer su
	 *            numero de detalles asociados.
	 * @return Numero de detalles de consulta asociados.
	 */
	public int numeroDetallesConsulta(final String idConsulta) {
		final MutableInt nDetalles = new MutableInt(0);

		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				String qual = new StringBuffer()
						.append(" WHERE ")
						.append(DBUtils.generateEQTokenField(CAMPO_ID,
								idConsulta)).toString();

				nDetalles.setValue(DbSelectFns.selectCount(conn, TABLE_NAME,
						qual.toString()));
			}
		};

		command.execute();

		return nDetalles.getValue();
	}

	/**
	 * Realiza el envío de una consulta
	 * 
	 * @param consulta
	 *            Consulta que se va a enviar.
	 * @param tipoUsuario
	 *            Tipo del usuario que va realiza el envio
	 */
	public void enviarConsulta(ConsultaVO consulta, String tipoUsuario) {
		String qual = new StringBuffer(" WHERE ").append(
				DBUtils.generateTokenFieldQualified(CAMPO_ID, consulta.getId(),
						TABLE_NAME, ConstraintType.EQUAL)).toString();

		consulta.setEstado(ConsultasConstants.ESTADO_CONSULTA_SOLICITADA);
		consulta.setFestado(DateUtils.getFechaActual());

		// Comprobamos si es una reserva
		if (!consulta.tieneReserva()) {
			String dias_reserva = PropertyHelper
					.getProperty(PropertyHelper.PLAZO_CONSULTA);

			Date hoy = Calendar.getInstance().getTime();
			Calendar fechaFinal = new GregorianCalendar();
			fechaFinal.setTime(hoy);
			fechaFinal.add(Calendar.HOUR, (Integer.parseInt(dias_reserva))
					* ConsultasConstants.HORAS_DIA);

			consulta.setFmaxfinconsulta(fechaFinal.getTime());
		} else {
			consulta.setFmaxfinconsulta(consulta.getFfinalreserva());
		}

		DbColumnDef[] columnas = { CAMPO_ESTADO, CAMPO_FESTADO,
				CAMPO_FMAXFINCONSULTA };

		updateVO(qual, TABLE_NAME, columnas, consulta);
	}

	/**
	 * Obtiene la clásula WHERE para la búsqueda con las restricciones del
	 * formulario de búsqueda.
	 * 
	 * @param busquedaVO
	 *            Información del formulario de búsqueda.
	 * @return Objeto con la consulta generada compuesta por la clausula WHERE y
	 *         las tabla necesarias para su correcto funcionamiento(siempre se
	 *         incluye por defecto la tabla PRESTAMO)
	 */
	protected ConsultaObject getWhereClause(BusquedaVO busquedaVO) {
		ConsultaObject consulta = new ConsultaObject();
		StringBuffer tables = new StringBuffer(TABLE_NAME);
		StringBuffer clausulaWhere = new StringBuffer();

		// Identificador del solicitante
		if (StringUtils.isNotBlank(busquedaVO.getIdSolicitante()))
			clausulaWhere.append(DBUtils.generateEQTokenField(
					CAMPO_IDUSRSOLICITANTE, busquedaVO.getIdSolicitante()));

		comprobarEstados(busquedaVO, clausulaWhere);

		// Código
		if (StringUtils.isNotBlank(busquedaVO.getCodigo()))
			clausulaWhere
					.append(" AND ")
					.append(CodigoTransferenciaUtils
							.textSqlCodigoTransferenciaConsulta(
									CAMPO_ANO.getQualifiedName(),
									CAMPO_ORDEN.getQualifiedName(),
									getConnection())).append(" LIKE '%")
					.append(busquedaVO.getCodigo()).append("%'");

		comprobarSolicitante(busquedaVO, clausulaWhere);

		comprobarFechaEntrega(busquedaVO, clausulaWhere);

		consulta.setTables(tables.toString());
		consulta.addPair(TABLE_NAME, COLUMN_DEFINITIONS);
		consulta.setWhere(clausulaWhere.toString());
		consulta.setOrderBy(null);

		return consulta;
	}

	// /**
	// * Realiza la comprobacion de si se debe añadir a la clausula where de una
	// busqueda la
	// * restricción por fecha de estado, añadiendolo en caso positivo.
	// * @param busquedaVO
	// * @param clausulaWhere
	// */
	// private void comprobarFechaEstado(BusquedaVO busquedaVO, StringBuffer
	// clausulaWhere) {
	// //Fecha Estado
	// if ((busquedaVO.getFechaInicioEstado() != null) ||
	// (busquedaVO.getFechaFinEstado() != null))
	// {
	// SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
	//
	// if (busquedaVO.getFechaInicioEstado() != null)
	// {
	// clausulaWhere.append(TABLE_NAME).append(".")
	// .append(CAMPO_FESTADO.getName())
	// .append("<=TO_DATE('");
	// clausulaWhere.append(df.format(busquedaVO.getFechaInicioEstado()));
	// clausulaWhere.append("','yyyy/mm/dd')");
	// }
	//
	// if (busquedaVO.getFechaFinEstado() != null)
	// {
	// if (busquedaVO.getFechaInicioEstado() != null)
	// clausulaWhere.append("AND ");
	//
	// clausulaWhere.append(TABLE_NAME).append(".")
	// .append(CAMPO_FESTADO.getName())
	// .append("<=TO_DATE('");
	// clausulaWhere.append(df.format(busquedaVO.getFechaFinEstado()));
	// clausulaWhere.append("','yyyy/mm/dd')");
	// }
	// }
	// }

	/**
	 * Realiza la comprobacion de si se debe añadir a la clausula where de una
	 * busqueda la restricción por fecha de entrega, añadiendolo en caso
	 * positivo.
	 * 
	 * @param busquedaVO
	 * @param clausulaWhere
	 */
	private void comprobarFechaEntrega(BusquedaVO busquedaVO,
			StringBuffer clausulaWhere) {

		// Fecha Entrega
		if ((busquedaVO.getFechaInicioEntrega() != null)
				|| (busquedaVO.getFechaFinEntrega() != null)) {
			// SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");

			if (busquedaVO.getFechaInicioEntrega() != null) {
				// clausulaWhere.append(" AND ")
				// .append(TABLE_NAME).append(".")
				// .append(CAMPO_FENTREGA.getName())
				// .append(">=TO_DATE('");
				// clausulaWhere.append(df.format(busquedaVO.getFechaInicioEntrega()));
				// clausulaWhere.append("','yyyy/mm/dd')");

				clausulaWhere
						.append(" AND ")
						.append(TABLE_NAME)
						.append(".")
						.append(CAMPO_FENTREGA.getName())
						.append(">=")
						.append(DBUtils.getNativeToDateSyntax(getConnection(),
								busquedaVO.getFechaInicioEntrega(),
								CustomDateFormat.SDF_YYYYMMDD));

			}

			if (busquedaVO.getFechaFinEntrega() != null) {
				// clausulaWhere.append(" AND ")
				// .append(TABLE_NAME).append(".")
				// .append(CAMPO_FENTREGA.getName())
				// .append("<=TO_DATE('");
				// clausulaWhere.append(df.format(busquedaVO.getFechaFinEntrega()));
				// clausulaWhere.append("','yyyy/mm/dd')");
				clausulaWhere
						.append(" AND ")
						.append(TABLE_NAME)
						.append(".")
						.append(CAMPO_FENTREGA.getName())
						.append("<=")
						.append(DBUtils.getNativeToDateSyntax(getConnection(),
								busquedaVO.getFechaFinEntrega(),
								CustomDateFormat.SDF_YYYYMMDD));

			}
		}
	}

	/**
	 * Realiza la comprobacion de si se debe añadir a la clausula where de una
	 * busqueda la restricción por estados, añadiendolo en caso positivo
	 * 
	 * @param busquedaVO
	 * @param clausulaWhere
	 */
	private void comprobarEstados(BusquedaVO busquedaVO,
			StringBuffer clausulaWhere) {
		// Estados
		if (!ArrayUtils.isEmpty(busquedaVO.getEstados())) {
			if (clausulaWhere.length() > 0)
				clausulaWhere.append(" AND ");

			clausulaWhere.append(DBUtils.generateInTokenField(CAMPO_ESTADO,
					busquedaVO.getEstados()));
		}
	}

	/**
	 * Realiza la comprobacion si se debe añadir a la clausula where de una
	 * busqueda la restricción por usuario solicitante, añadiendolo en caso
	 * afirmativo.
	 * 
	 * @param busquedaVO
	 * @param clausulaWhere
	 */
	private void comprobarSolicitante(BusquedaVO busquedaVO,
			StringBuffer clausulaWhere) {
		// Usuario consultor
		if (StringUtils.isNotBlank(busquedaVO.getSolicitante())) {
			clausulaWhere.append(" AND ").append(
					DBUtils.generateLikeTokenField(CAMPO_NUSRCONSULTOR,
							busquedaVO.getSolicitante(), true));
		}

		// Órgano
		if (StringUtils.isNotBlank(busquedaVO.getOrgano())) {
			clausulaWhere.append(" AND ").append(
					DBUtils.generateLikeTokenField(CAMPO_NORGCONSULTOR,
							busquedaVO.getOrgano(), true));
		}
	}

	/**
	 * Obtiene un listado de los usuarios que realizan una consulta
	 * 
	 * @return Listado de los nombres de los usuarios consultores
	 */
	public Collection getUsuariosBusqueda() {
		StringBuffer qual = new StringBuffer();

		DbColumnDef[] columnas = {
				new DbColumnDef("nombre", TABLE_NAME, NUSRCONSULTOR,
						DbDataType.SHORT_TEXT, 254, false),
				new DbColumnDef("id", TABLE_NAME, NUSRCONSULTOR,
						DbDataType.SHORT_TEXT, 254, false) };

		return getDistinctVOS(qual.toString(), TABLE_NAME, columnas,
				UsuarioVO.class);
	}

	/**
	 * Actualiza las observacioens de la Consulta.
	 */
	public void updateObservaciones(String id, String observaciones) {
		final String qual = new StringBuffer().append("WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_ID, id)).toString();

		HashMap mapColumnsToUpdate = new HashMap();
		mapColumnsToUpdate.put(CAMPO_OBSERVACIONES, observaciones);
		updateFields(qual, mapColumnsToUpdate, TABLE_NAME);
	}

	public int getCountConsultasByIdMotivo(String idMotivo) {
		StringBuffer qual = new StringBuffer();
		if (StringUtils.isNotEmpty(idMotivo))
			qual.append(DBUtils.WHERE).append(
					DBUtils.generateEQTokenField(CAMPO_ID_MOTIVO, idMotivo));

		return getVOCount(qual.toString(), TABLE_NAME);
	}

	public List getConsultasByIdarchivoAndIdUsuarioSolicitante(
			String idArchivo, String idUsuarioSolicitante, String[] estados) {

		String qual = new StringBuffer()
				.append(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(CAMPO_IDUSRSOLICITANTE,
						idUsuarioSolicitante))
				.append(DBUtils.AND)
				.append(DBUtils.generateORTokens(CAMPO_ESTADO, estados))
				.append(DBUtils.AND)
				.append(DBUtils
						.generateEQTokenField(CAMPO_IDARCHIVO, idArchivo))
				.toString();

		return getVOS(qual, TABLE_NAME, COLUMN_DEFINITIONS, ConsultaVO.class);
	}

	public int getCountConsultasByIdUsrSalaNotInEstados(String idcsusrsala,
			int[] estadosNotIn) {
		StringBuffer qual = new StringBuffer(DBUtils.WHERE).append(DBUtils
				.generateEQTokenField(CAMPO_IDUSRCSALA, idcsusrsala));

		if (estadosNotIn != null && estadosNotIn.length > 0) {
			qual.append(DBUtils.AND)
					.append(DBUtils.generateNotInTokenField(CAMPO_ESTADO,
							estadosNotIn));
		}
		return getVOCount(qual.toString(), TABLE_NAME);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see solicitudes.consultas.db.IConsultaDBEntity#getConsultasByIdarchivo(java.lang.String)
	 */
	public List getConsultasByIdArchivo(String idArchivo) {
		StringBuffer qual = new StringBuffer().append(DBUtils.WHERE).append(
				DBUtils.generateEQTokenField(CAMPO_IDARCHIVO, idArchivo));
		return getVOS(qual.toString(), TABLE_NAME, COLUMN_DEFINITIONS,
				ConsultaVO.class);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see solicitudes.consultas.db.IConsultaDBEntity#getConsultasByIdUsrCSala(java.lang.String,
	 *      int[])
	 */
	public List getConsultasByIdUsrCSala(String idUsrCSala, int[] estados) {
		StringBuffer qual = new StringBuffer().append(DBUtils.WHERE).append(
				DBUtils.generateEQTokenField(CAMPO_IDUSRCSALA, idUsrCSala));
		if (estados != null && estados.length > 0) {
			qual.append(DBUtils.AND).append(
					DBUtils.generateInTokenField(CAMPO_ESTADO, estados));
		}
		return getVOS(qual.toString(), TABLE_NAME, COLUMN_DEFINITIONS,
				ConsultaVO.class);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see solicitudes.consultas.db.IConsultaDBEntity#getConsultasPendientesByIdUsrCSala(java.lang.String)
	 */
	public List getConsultasPendientesByIdUsrCSala(String idUsrCSala) {
		StringBuffer qual = new StringBuffer()
				.append(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(CAMPO_IDUSRCSALA,
						idUsrCSala))
				.append(DBUtils.AND)
				.append(DBUtils
						.generateNotInTokenField(
								CAMPO_ESTADO,
								new int[] { ConsultasConstants.ESTADO_CONSULTA_DEVUELTA }));

		return getVOS(qual.toString(), TABLE_NAME, COLUMN_DEFINITIONS,
				ConsultaVO.class);
	}
}