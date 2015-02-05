package deposito.db;

import gcontrol.db.ArchivoDbEntityImpl;
import gcontrol.vos.ArchivoVO;
import ieci.core.db.DbColumnDef;
import ieci.core.db.DbConnection;
import ieci.core.db.DbDataType;
import ieci.core.db.DbInsertFns;
import ieci.core.db.DbUtil;

import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import common.db.DBCommand;
import common.db.DBEntity;
import common.db.DBUtils;
import common.db.DbDataSource;
import common.db.JoinDefinition;
import common.db.SigiaDbInputRecord;
import common.db.TableDef;
import common.util.ArrayUtils;
import common.util.ListUtils;

import deposito.db.oracle.HuecoDBEntityImpl;
import deposito.vos.DepositoVO;
import deposito.vos.ResumenOcupacionVO;

/**
 * Implementación de los métodos de acceso a datos referentes a ubicaciones del
 * fondo físico
 */
public class DepositoDBEntityImpl extends DBEntity implements IDepositoDbEntity {

	static Logger logger = Logger.getLogger(DepositoDBEntityImpl.class);

	public static final String TABLE_NAME = "ASGDDEPOSITO";

	private static final String ID_COLUMN_NAME = "id";
	private static final String IDTIPOELEMENTO_COLUMN_NAME = "idtipoelemento";
	private static final String NUMORDEN_COLUMN_NAME = "numorden";
	private static final String NOMBRE_COLUMN_NAME = "nombre";
	private static final String UBICACION_COLUMN_NAME = "ubicacion";
	private static final String MARCAS_COLUMN_NAME = "marcas";
	private static final String IDARCHIVO_COLUMN_NAME = "idarchivo";

	public static final DbColumnDef CAMPO_ID = new DbColumnDef(null,
			TABLE_NAME, ID_COLUMN_NAME, DbDataType.SHORT_TEXT, 32, false);

	public static final DbColumnDef CAMPO_IDTIPOELEMENTO = new DbColumnDef(
			null, TABLE_NAME, IDTIPOELEMENTO_COLUMN_NAME,
			DbDataType.SHORT_TEXT, 32, false);

	public static final DbColumnDef CAMPO_NUMORDEN = new DbColumnDef(null,
			TABLE_NAME, NUMORDEN_COLUMN_NAME, DbDataType.LONG_INTEGER, false);

	public static final DbColumnDef CAMPO_NOMBRE = new DbColumnDef(null,
			TABLE_NAME, NOMBRE_COLUMN_NAME, DbDataType.SHORT_TEXT, 32, false);

	public static final DbColumnDef CAMPO_UBICACION = new DbColumnDef(null,
			TABLE_NAME, UBICACION_COLUMN_NAME, DbDataType.SHORT_TEXT, 32, true);

	public static final DbColumnDef CAMPO_MARCAS = new DbColumnDef(null,
			TABLE_NAME, MARCAS_COLUMN_NAME, DbDataType.LONG_INTEGER, true);

	public static final DbColumnDef CAMPO_IDARCHIVO = new DbColumnDef(null,
			TABLE_NAME, IDARCHIVO_COLUMN_NAME, DbDataType.SHORT_TEXT, 32, true);

	private static final DbColumnDef[] COLUMN_DEFINITIONS = { CAMPO_ID,
			CAMPO_IDTIPOELEMENTO, CAMPO_NUMORDEN, CAMPO_NOMBRE,
			CAMPO_UBICACION, CAMPO_MARCAS, CAMPO_IDARCHIVO };
	protected static final String ALL_COLUMN_NAMES = DbUtil
			.getColumnNames(COLUMN_DEFINITIONS);

	/**
	 * Obtiene el nombre de la tabla
	 *
	 * @return
	 */
	public String getTableName() {
		return TABLE_NAME;
	}

	public DepositoDBEntityImpl(DbDataSource dataSource) {
		super(dataSource);
	}

	public DepositoDBEntityImpl(DbConnection conn) {
		super(conn);
	}

	public List getDepositos() {
		StringBuffer qual = new StringBuffer(" ORDER BY ").append(
				CAMPO_NUMORDEN.getName()).append(" ASC ");
		return getVOS(qual.toString(), TABLE_NAME, COLUMN_DEFINITIONS,
				DepositoVO.class);
	}

	public List getDepositos(String nombre) {
		StringBuffer qual = new StringBuffer(" WHERE ").append(DBUtils
				.generateEQTokenField(CAMPO_NOMBRE, nombre));
		return getVOS(qual.toString(), TABLE_NAME, COLUMN_DEFINITIONS,
				DepositoVO.class);

	}

	public void insertDeposito(final DepositoVO deposito) {
		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				boolean idFound = true;
				String idUbicacion = null;
				while (idFound) {
					idUbicacion = getGuid(null);
					idFound = (loadDeposito(idUbicacion) != null);
				}
				deposito.setId(idUbicacion);
				deposito.setIddeposito(idUbicacion);
				SigiaDbInputRecord inputRecord = new SigiaDbInputRecord(
						COLUMN_DEFINITIONS, deposito);
				DbInsertFns.insert(conn, TABLE_NAME,
						DbUtil.getColumnNames(COLUMN_DEFINITIONS), inputRecord);
			}
		};
		command.execute();
	}

	public DepositoVO loadDeposito(String id) {
		StringBuffer qual = new StringBuffer(" WHERE ").append(DBUtils
				.generateEQTokenField(CAMPO_ID, id));
		return (DepositoVO) getVO(qual.toString(), TABLE_NAME,
				COLUMN_DEFINITIONS, DepositoVO.class);
	}

	public void updateDeposito(DepositoVO depositoVO) {
		StringBuffer qual = new StringBuffer(" WHERE ").append(DBUtils
				.generateEQTokenField(CAMPO_ID, depositoVO.getId()));

		updateVO(qual.toString(), TABLE_NAME, COLUMN_DEFINITIONS, depositoVO);
	}

	public void delete(String id) {
		StringBuffer qual = new StringBuffer(" WHERE ").append(DBUtils
				.generateEQTokenField(CAMPO_ID, id));
		deleteVO(qual.toString(), TABLE_NAME);
	}

	private static final DbColumnDef ESTADO = new DbColumnDef("estado",
			(String) null, HuecoDBEntityImpl.ESTADO_COLUMN_NAME,
			DbDataType.SHORT_TEXT, false);
	private static final DbColumnDef NUMERO_HUECOS = new DbColumnDef(
			"numHuecos", (String) null, "count(*)", DbDataType.LONG_INTEGER,
			false);

	private static final DbColumnDef[] COLUMNAS_OCUPACION = { ESTADO,
			NUMERO_HUECOS };

	public ResumenOcupacionVO getOcupacion(String idUbicacion) {
		StringBuffer qual = new StringBuffer();
		if (idUbicacion != null)
			qual.append(" WHERE ").append(
					DBUtils.generateEQTokenField(
							HuecoDBEntityImpl.CAMPO_IDDEPOSITO, idUbicacion));
		qual.append(" GROUP BY ").append(HuecoDBEntityImpl.ESTADO_COLUMN_NAME);
		List datosOcupacion = getVOS(qual.toString(),
				HuecoDBEntityImpl.TABLE_NAME, COLUMNAS_OCUPACION,
				HuecosEnEstado.class);
		ResumenOcupacionVO resumenOcupacion = new ResumenOcupacionVO();
		HuecosEnEstado huecosEnEstado = null;
		for (Iterator i = datosOcupacion.iterator(); i.hasNext();) {
			huecosEnEstado = (HuecosEnEstado) i.next();
			resumenOcupacion.setNumHuecos(huecosEnEstado.getEstado(),
					huecosEnEstado.getNumHuecos());
		}
		return resumenOcupacion;
	}

	public ResumenOcupacionVO getOcupacionByIdsUbicacion(List idsUbicaciones) {
		StringBuffer qual = new StringBuffer();
		ResumenOcupacionVO resumenOcupacion = new ResumenOcupacionVO();

		if (!ListUtils.isEmpty(idsUbicaciones)) {
			qual.append(" WHERE ")
					.append(DBUtils.generateInTokenField(
							HuecoDBEntityImpl.CAMPO_IDDEPOSITO, idsUbicaciones));

			qual.append(" GROUP BY ").append(
					HuecoDBEntityImpl.ESTADO_COLUMN_NAME);
			List datosOcupacion = getVOS(qual.toString(),
					HuecoDBEntityImpl.TABLE_NAME, COLUMNAS_OCUPACION,
					HuecosEnEstado.class);

			HuecosEnEstado huecosEnEstado = null;
			for (Iterator i = datosOcupacion.iterator(); i.hasNext();) {
				huecosEnEstado = (HuecosEnEstado) i.next();
				resumenOcupacion.setNumHuecos(huecosEnEstado.getEstado(),
						huecosEnEstado.getNumHuecos());
			}
		}
		return resumenOcupacion;
	}

	/*
	 * (sin Javadoc)
	 *
	 * @see
	 * deposito.db.IDepositoDbEntity#getUbicacionesXIdsArchivo(java.lang.String
	 * [])
	 */
	public List getUbicacionesXIdsArchivo(String[] idsArchivo) {
		if (ArrayUtils.isEmpty(idsArchivo))
			return null;

		StringBuffer qual = new StringBuffer()
				.append(DBUtils.WHERE)
				.append(DBUtils.generateORTokensQualified(TABLE_NAME,
						CAMPO_IDARCHIVO, idsArchivo))
				.append(DBUtils.generateOrderBy(CAMPO_NUMORDEN));

		return getVOS(qual.toString(), TABLE_NAME, COLUMN_DEFINITIONS,
				DepositoVO.class);
	}

	/**
	 * Obtiene las ubicaciones que pertenecen al archivo.
	 *
	 * @param idArchivo
	 *            Identificador del archivo
	 * @return List {depositoVO}
	 */
	public List getUbicacionXIdArchivo(String idArchivo) {
		StringBuffer qual = new StringBuffer().append(DBUtils.WHERE).append(
				DBUtils.generateEQTokenField(CAMPO_IDARCHIVO, idArchivo));

		return getVOS(qual.toString(), TABLE_NAME, COLUMN_DEFINITIONS,
				DepositoVO.class);
	}

	/**
	 * Devuelve el tipo de signaturacion del archivo al que pertenece el
	 * deposito cuyo id coincide con el pasado como parametro.
	 *
	 * @param idDeposito
	 * @return
	 */
	public int getTipoSignaturacionDeposito(String idDeposito) {
		/*
		 * SELECT AGARCHIVO.* FROM ASGDDEPOSITO INNER JOIN AGARCHIVO ON
		 * AGARCHIVO.ID = ASGDDEPOSITO.IDARCHIVO AND ASGDDEPOSITO.ID =
		 * 'idDeposito'
		 */

		JoinDefinition[] joinDepositoArchivo = new JoinDefinition[] { new JoinDefinition(
				new DbColumnDef(new TableDef(TABLE_NAME), CAMPO_IDARCHIVO),
				new DbColumnDef(new TableDef(ArchivoDbEntityImpl.TABLE_NAME),
						ArchivoDbEntityImpl.ID_FIELD)) };

		StringBuffer qual = new StringBuffer(
				DBUtils.generateInnerJoinChainedCondition(joinDepositoArchivo))
				.append(" AND ").append(
						DBUtils.generateEQTokenField(CAMPO_ID, idDeposito));

		ArchivoVO archivo = (ArchivoVO) getVO(qual.toString(), TABLE_NAME,
				ArchivoDbEntityImpl.COLS_DEFS, ArchivoVO.class);
		return archivo.getTiposignaturacion();
	}

	/**
	 * Comprueba que el numero de orden no exista ya en la base de datos
	 */
	public boolean isNumOrdenEnUso(int numOrden) {
		StringBuffer qual = new StringBuffer(" WHERE ").append(DBUtils
				.generateEQTokenField(CAMPO_NUMORDEN, numOrden));

		if (getVOCount(qual.toString(), TABLE_NAME) > 0)
			return true;
		return false;
	}
}