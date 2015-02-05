package deposito.db;

import ieci.core.db.DbColumnDef;
import ieci.core.db.DbConnection;
import ieci.core.db.DbDataType;
import ieci.core.db.DbDeleteFns;
import ieci.core.db.DbInsertFns;
import ieci.core.db.DbOutputRecord;
import ieci.core.db.DbOutputStatement;
import ieci.core.db.DbSelectFns;
import ieci.core.db.DbSelectStatement;
import ieci.core.db.DbUpdateFns;
import ieci.core.db.DbUtil;
import ieci.core.exception.IeciTdException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import common.Constants;
import common.db.AbstractDbOutputRecordSet;
import common.db.DBCommand;
import common.db.DBEntity;
import common.db.DBUtils;
import common.db.DbDataSource;
import common.db.SigiaDbInputRecord;
import common.db.SigiaDbOutputRecord;
import common.exceptions.TooManyResultsException;
import common.util.CustomDateRange;
import common.util.DateUtils;
import common.util.StringUtils;
import common.vos.ConsultaConnectBy;

import deposito.db.common.HuecoDbEntityImplBase;
import deposito.vos.BusquedaUInsDepositoVO;
import deposito.vos.HuecoVO;
import deposito.vos.UInsDepositoVO;
import fondos.db.ElementoCuadroClasificacionDBEntityImplBase;
import fondos.db.NivelCFDBEntityImpl;
import fondos.db.TablaTemporalDBEntityImpl;
import fondos.model.TipoNivelCF;
import fondos.vos.BusquedaElementosVO;
import fondos.vos.TablaTemporalFondosVO;

public class UInstalacionDepositoDBEntity extends DBEntity implements
		IUInstalacionDepositoDBEntity {

	public static final String TABLE_NAME = "ASGDUINSTALACION";

	public static final String ID_COLUMN_NAME = "ID";

	public static final String IDFORMATO_COLUMN_NAME = "IDFORMATO";

	public static final String SIGNATURAUI_COLUMN_NAME = "SIGNATURAUI";

	public static final String IDENTIFICACION_COLUMN_NAME = "IDENTIFICACION";

	public static final String MARCAS_BLOQUEO_COLUMN_NAME = "MARCASBLOQUEO";

	public static final String FECHA_CREACION_COLUMN_NAME = "FCREACION";

	public static final DbColumnDef ID_FIELD = new DbColumnDef(null,
			TABLE_NAME, ID_COLUMN_NAME, DbDataType.SHORT_TEXT, 32, false);

	public static final DbColumnDef IDFORMATO_FIELD = new DbColumnDef(null,
			TABLE_NAME, IDFORMATO_COLUMN_NAME, DbDataType.SHORT_TEXT, 32, false);

	public static final DbColumnDef SIGNATURAUI_FIELD = new DbColumnDef(null,
			TABLE_NAME, SIGNATURAUI_COLUMN_NAME, DbDataType.SHORT_TEXT, 16,
			false);

	public static final DbColumnDef IDENTIFICACION_FIELD = new DbColumnDef(
			null, TABLE_NAME, IDENTIFICACION_COLUMN_NAME,
			DbDataType.SHORT_TEXT, 254, false);

	public static final DbColumnDef MARCAS_BLOQUEO_FIELD = new DbColumnDef(
			null, TABLE_NAME, MARCAS_BLOQUEO_COLUMN_NAME,
			DbDataType.LONG_INTEGER, true);

	public static final DbColumnDef FECHA_CREACION_FIELD = new DbColumnDef(
			null, TABLE_NAME, FECHA_CREACION_COLUMN_NAME, DbDataType.DATE_TIME,
			false);

	public static final DbColumnDef[] TABLE_COLUMNS = { ID_FIELD,
			IDFORMATO_FIELD, SIGNATURAUI_FIELD, IDENTIFICACION_FIELD,
			MARCAS_BLOQUEO_FIELD, FECHA_CREACION_FIELD };

	public static final String COLUM_NAMES_LIST = DbUtil
			.getColumnNames(TABLE_COLUMNS);

	/**
	 * Obtiene el nombre de la tabla
	 * 
	 * @return
	 */
	public String getTableName() {
		return TABLE_NAME;
	}

	public UInstalacionDepositoDBEntity(DbDataSource dataSource) {
		super(dataSource);
	}

	public UInstalacionDepositoDBEntity(DbConnection conn) {
		super(conn);
	}

	public UInsDepositoVO getUInstDepositoVOXIdEnDeposito(
			final String idUInstaEnDeposito) {
		String qual = new StringBuffer()
				.append(" WHERE ")
				.append(DBUtils.generateEQTokenField(ID_FIELD,
						idUInstaEnDeposito)).toString();
		return getUInstDepositoVO(qual);
	}

	private UInsDepositoVO getUInstDepositoVO(final String qual) {
		final UInsDepositoVO uinsVO = new UInsDepositoVO();
		DBCommand command = new DBCommand(this) {

			public void codeLogic(DbConnection conn) throws Exception {

				SigiaDbOutputRecord row = new SigiaDbOutputRecord(uinsVO,
						TABLE_COLUMNS);

				try {
					DbSelectFns.select(conn, TABLE_NAME, COLUM_NAMES_LIST,
							qual, row);
				} catch (IeciTdException e) {
					uinsVO.setId(null);
				}
			}
		};
		command.execute();
		return (uinsVO.getId() == null) ? null : uinsVO;
	}

	// private List getUdsInstDepositoVO(final String qual) {
	// final SigiaDbOutputRecordset rowSet = new
	// SigiaDbOutputRecordset(TABLE_COLUMNS,
	// UInsDepositoVO.class);
	// DBCommand command = new DBCommand(this) {
	// public void codeLogic(DbConnection conn) throws Exception {
	// DbSelectFns.select(conn, TABLE_NAME, COLUM_NAMES_LIST, qual, false,
	// rowSet);
	// }
	// };
	// command.execute();
	// return rowSet.size() > 0 ? rowSet : null;
	// }

	public void insertUInstDepositoVO(final UInsDepositoVO uinsDepositoVO) {
		DBCommand command = new DBCommand(this) {

			public void codeLogic(DbConnection conn) throws Exception {
				SigiaDbInputRecord inputRecord = new SigiaDbInputRecord(
						TABLE_COLUMNS, uinsDepositoVO);

				if (StringUtils.isEmpty(uinsDepositoVO.getId())) {
					uinsDepositoVO.setId(getGuid(uinsDepositoVO.getId()));
				}

				if (uinsDepositoVO.getFcreacion() == null) {
					uinsDepositoVO.setFcreacion(DateUtils.getFechaActual());
				}

				DbInsertFns.insert(conn, TABLE_NAME, COLUM_NAMES_LIST,
						inputRecord);
			}
		};
		command.execute();
	}

	/**
	 * Realiza el borrado de una unidad de instalacion por su identificador.
	 * 
	 * @param idUInstaEnDeposito
	 *            Identificador de la unidad.
	 */
	public void deleteUInstDeposito(final String idUInstaEnDeposito) {
		final String qual = new StringBuffer()
				.append(" WHERE ")
				.append(DBUtils.generateEQTokenField(ID_FIELD,
						idUInstaEnDeposito)).toString();

		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				DbDeleteFns.delete(conn, TABLE_NAME, qual);

			}
		};

		command.execute();
	}

	/**
	 * Actualiza la Marca de la unidad de Instalación
	 */
	public void updateMarcaUnidadInstalacion(String id, int marcas) {
		final String qual = new StringBuffer(DBUtils.WHERE).append(
				DBUtils.generateEQTokenField(ID_FIELD, id)).toString();

		UInsDepositoVO uInst = (UInsDepositoVO) getVO(qual, TABLE_NAME,
				TABLE_COLUMNS, UInsDepositoVO.class);
		uInst.setMarcasBloqueo(marcas);

		updateVO(qual, TABLE_NAME, TABLE_COLUMNS, uInst);
	}

	/**
	 * Actualiza la Identificación de la unidad de Instalación
	 */
	/*
	 * public void updateIdentificacionUI(String idUInst, String identificacion)
	 * { final String qual = new StringBuffer(DBUtils.WHERE).append(
	 * DBUtils.generateEQTokenField(ID_FIELD, idUInst)).toString();
	 * 
	 * HashMap columnsToUpdate = new HashMap();
	 * 
	 * columnsToUpdate.put(IDENTIFICACION_FIELD, identificacion);
	 * 
	 * updateFields(qual, columnsToUpdate, TABLE_NAME); }
	 */

	/**
	 * Actualiza los campos de la unidad de instalación en el depósito
	 */
	public void updateFieldsUInstDeposito(String idUInst,
			final Map columnsToUpdate) {
		final String qual = new StringBuffer(DBUtils.WHERE).append(
				DBUtils.generateEQTokenField(ID_FIELD, idUInst)).toString();

		updateFields(qual, columnsToUpdate, TABLE_NAME);
	}

	/**
	 * Actualiza los campos de la unidad de instalación en el depósito que es
	 * necesario modificar durante la validación
	 */
	public void updateFieldsUInstDepositoVREA(UInsDepositoVO uInst) {
		HashMap columnsToUpdate = new HashMap();

		columnsToUpdate.put(IDENTIFICACION_FIELD, uInst.getIdentificacion());
		columnsToUpdate.put(SIGNATURAUI_FIELD, uInst.getSignaturaUI());
		columnsToUpdate.put(MARCAS_BLOQUEO_FIELD,
				new Integer(uInst.getMarcasBloqueo()));

		updateFieldsUInstDeposito(uInst.getId(), columnsToUpdate);
	}

	// Utilizado para obtener los objetos a partir del id (lista con paginacion
	// externa)
	public List getUnidsInstalacion(List listaIds,
			BusquedaElementosVO busquedaElementosVO) {
		return getUnidsInstalacionIdPadreHuecos(listaIds, busquedaElementosVO);
	}

	/*
	 * public List getIdsUnidsInstalacion(List listaIdsAsignables, String
	 * like_sig, String signatura, String idFondo, int numMaxResults) throws
	 * TooManyResultsException { if(listaIdsAsignables==null ||
	 * listaIdsAsignables.size()==0) return null; String
	 * queryIdPadreHuecos=DBUtils
	 * .generateInTokenField(HuecoDbEntityImplBase.CAMPO_IDELEMPADRE,
	 * listaIdsAsignables); return
	 * getIdsUnidsInstalacionIdPadreHuecos(queryIdPadreHuecos
	 * ,like_sig,signatura,idFondo,null,numMaxResults);
	 * 
	 * }
	 */

	public List getIdsUnidsInstalacion(String idUbicacion, String like_sig,
			String signatura, String idFondo, String idFormato,
			int numMaxResults, CustomDateRange rangoFechas)
			throws TooManyResultsException {
		String queryIdPadreHuecos = DBUtils.generateEQTokenField(
				HuecoDbEntityImplBase.CAMPO_IDDEPOSITO, idUbicacion);
		return getIdsUnidsInstalacionIdPadreHuecos(queryIdPadreHuecos,
				like_sig, signatura, idFondo, idFormato, numMaxResults,
				rangoFechas);
	}

	public List getIdsUnidsInstalacion(String idUbicacion,
			List idsCodsOrdenAmbitos, String like_sig, String signatura,
			String idFondo, String idFormato, int numMaxResults,
			CustomDateRange rangoFechas) throws TooManyResultsException {
		// componer query para obtener los ids de los huecos a partir de los
		// asignables
		// que esten incluidos en los codigo de orden seleccionados

		// asgdhueco.idelemapadre = asgdelemasig.id and
		// ( strpos(asgdelemasig.codorden,asgdelemasig.codOrden)=1 OR ... )

		StringBuffer qual = new StringBuffer("");
		DbColumnDef CODORDEN_ASIGNABLE_FIELD = new DbColumnDef(null,
				ElementoAsignableDBEntity.TABLE_NAME,
				ElementoAsignableDBEntity.CODORDEN_COLUMN_NAME,
				DbDataType.SHORT_TEXT, 50, false);
		DbColumnDef IDUBICACION_ASIGNABLE_FIELD = new DbColumnDef(null,
				ElementoAsignableDBEntity.TABLE_NAME,
				ElementoAsignableDBEntity.IDDEPOSITO_COLUMN_NAME,
				DbDataType.SHORT_TEXT, 32, false);
		if (!StringUtils.isEmpty(idUbicacion)) {
			qual.append(DBUtils.generateEQTokenField(
					IDUBICACION_ASIGNABLE_FIELD, idUbicacion));
		}

		if (idsCodsOrdenAmbitos != null && idsCodsOrdenAmbitos.size() > 0) {
			// si se entra por aqui, siempre se entro por el if anterior.
			qual.append(" AND ");
			qual.append("(");
			for (Iterator it = idsCodsOrdenAmbitos.iterator(); it.hasNext();) {
				String codOrden = (String) it.next();
				String leftEqual = DBUtils.getNativeStrPosSyntax(
						getConnection(), CODORDEN_ASIGNABLE_FIELD, codOrden);
				qual.append(leftEqual + "=1");
				if (it.hasNext())
					qual.append(" OR ");
			}
			qual.append(")");
		}
		return getIdsUnidsInstalacionIdPadreHuecos(qual.toString(), like_sig,
				signatura, idFondo, idFormato, numMaxResults, true, rangoFechas);
	}

	private List getUnidsInstalacionIdPadreHuecos(List listaIds,
			BusquedaElementosVO busquedaElementosVO) {
		final String UBICACION_COLUMN = HuecoDbEntityImplBase.PATH_COLUMN_NAME;
		final String IDPADRE_HUECO_COLUMN = HuecoDbEntityImplBase.IDELEMPADRE_COLUMN_NAME;
		final String NUMORDEN_HUECO_COLUMN = HuecoDbEntityImplBase.NUMORDEN_COLUMN_NAME;
		final String HUECO_TABLE = HuecoDbEntityImplBase.TABLE_NAME;

		final String TITULOFONDO_COLUMN = ElementoCuadroClasificacionDBEntityImplBase.TITULO_COLUMN_NAME;
		final String FONDO_TABLE = ElementoCuadroClasificacionDBEntityImplBase.TABLE_NAME_ELEMENTO;
		final String NIVELCF_TABLE = NivelCFDBEntityImpl.NIVELCF_TABLE_NAME;

		final String NOMBREFORMATO_COLUMN = FormatoDBEntity.NOMBRE_COLUMN_NAME;
		final String FORMATO_TABLE = FormatoDBEntity.TABLE_NAME;

		final DbColumnDef UBICACION_FIELD = new DbColumnDef("path",
				HUECO_TABLE, UBICACION_COLUMN, DbDataType.SHORT_TEXT, 512,
				false);
		final DbColumnDef NOMBREFORMATO_FIELD = new DbColumnDef(
				"nombreFormato", FORMATO_TABLE, NOMBREFORMATO_COLUMN,
				DbDataType.SHORT_TEXT, 64, false);
		final DbColumnDef TITULOFONDO_FIELD = new DbColumnDef("tituloFondo",
				FONDO_TABLE, TITULOFONDO_COLUMN, DbDataType.SHORT_TEXT, 254,
				true);

		final DbColumnDef SIGNATURAUI_FIELD = UInstalacionDepositoDBEntity.SIGNATURAUI_FIELD;
		final DbColumnDef IDUINSTALACION_FIELD = HuecoDbEntityImplBase.CAMPO_IDUINSTALACION;
		// final DbColumnDef
		// FONDO_ID_FIELD=ElementoCuadroClasificacionDBEntityImplBase.ID_ELEMENTO_FIELD;
		final DbColumnDef FORMATO_ID_FIELD = FormatoDBEntity.CAMPO_ID;
		final DbColumnDef IDNIVEL_ELEMENTO_FIELD = ElementoCuadroClasificacionDBEntityImplBase.NIVEL_FIELD;
		final DbColumnDef IDNIVEL_NIVELCF_FIELD = NivelCFDBEntityImpl.ID_NIVEL_FIELD;
		final DbColumnDef TIPO_NIVELCF_FIELD = NivelCFDBEntityImpl.TIPO_NIVEL_FIELD;
		final DbColumnDef FECHA_CREACION_FIELD = UInstalacionDepositoDBEntity.FECHA_CREACION_FIELD;

		final DbColumnDef IDPADRE_HUECO_FIELD = new DbColumnDef("idPadreHueco",
				HUECO_TABLE, IDPADRE_HUECO_COLUMN, DbDataType.SHORT_TEXT, 32,
				true);
		final DbColumnDef NUMORDEN_HUECO_FIELD = new DbColumnDef(
				"numOrdenHueco", HUECO_TABLE, NUMORDEN_HUECO_COLUMN,
				DbDataType.SHORT_TEXT, 32, true);
		// el campo de la unidad de instalacion identificacion
		// esta compuesto por el codigo referencia del fondo +"."+ signatura
		// hay que extraer el codigo de referencia de ese campo y asociar ese
		// valor
		// valor al campo cod referencia de ASGFELEMENTOCF en el join.
		// hay que usar instr y substr

		final DbColumnDef[] QUERY_COLUMNS = { ID_FIELD, UBICACION_FIELD,
				SIGNATURAUI_FIELD, NOMBREFORMATO_FIELD, TITULOFONDO_FIELD,
				IDPADRE_HUECO_FIELD, NUMORDEN_HUECO_FIELD, FECHA_CREACION_FIELD };

		final String QUERY_COLUM_NAMES = DbUtil.getColumnNames(QUERY_COLUMNS);

		final String tablas = new StringBuffer(HUECO_TABLE).append(", ")
				.append(TABLE_NAME).append(", ").append(FONDO_TABLE)
				.append(", ").append(FORMATO_TABLE).append(", ")
				.append(NIVELCF_TABLE).toString();

		StringBuffer qual = new StringBuffer("WHERE ");
		qual.append(
				DBUtils.generateJoinCondition(IDUINSTALACION_FIELD, ID_FIELD))
				.append(" AND ")
				.append(DBUtils.generateJoinCondition(IDNIVEL_ELEMENTO_FIELD,
						IDNIVEL_NIVELCF_FIELD))
				.append(" AND ")
				.append(DBUtils.generateJoinCondition(IDFORMATO_FIELD,
						FORMATO_ID_FIELD));

		qual.append(" AND ");

		String posWhereQuery = DBUtils.getNativeStrPosSyntax(getConnection(),
				IDENTIFICACION_FIELD, ".") + "-1";
		String rightEQValue = DBUtils.getNativeSubstrSyntax(getConnection(),
				IDENTIFICACION_FIELD.getQualifiedName(), "1", posWhereQuery);
		qual.append(DBUtils
				.generateEQTokenFunction(
						ElementoCuadroClasificacionDBEntityImplBase.CODIGO_REFERENCIA_FIELD,
						rightEQValue));
		qual.append(" AND ")
				.append(DBUtils.generateEQTokenField(TIPO_NIVELCF_FIELD,
						TipoNivelCF.FONDO.getIdentificador())).append(" AND ")
				.append(DBUtils.generateInTokenField(ID_FIELD, listaIds));

		// añadir order by
		qual.append(" ORDER BY ");
		if (busquedaElementosVO.getOrderColumnName() != null) {
			qual.append(busquedaElementosVO.getOrderColumnName()).append(
					busquedaElementosVO.getTipoOrdenacion());
		} else {
			qual.append(HuecoDbEntityImplBase.CAMPO_CODORDEN.getQualifiedName());

			qual.append(" ,");
			qual.append(IDFORMATO_FIELD.getQualifiedName());
		}

		final String where = qual.toString();

		final ArrayList rows = new ArrayList();

		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				DbSelectFns.select(conn, tablas, QUERY_COLUM_NAMES, where,
						false, new AbstractDbOutputRecordSet() {
							DbOutputRecord aRow = new DbOutputRecord() {
								public void getStatementValues(
										DbOutputStatement stmt)
										throws Exception {
									BusquedaUInsDepositoVO vo = new BusquedaUInsDepositoVO();
									DBUtils.fillVoWithDbOutputStament(
											QUERY_COLUMNS, stmt, vo);
									rows.add(vo);
								}
							};

							public DbOutputRecord newRecord() throws Exception {
								return aRow;
							}
						});
			}
		};

		command.execute();
		return rows.size() > 0 ? rows : null;
	}

	private List getIdsUnidsInstalacionIdPadreHuecos(String queryIdPadreHuecos,
			String like_sig, String signatura, String idFondo,
			String idFormato, int numMaxResults, CustomDateRange rangoFechas)
			throws TooManyResultsException {
		return getIdsUnidsInstalacionIdPadreHuecos(queryIdPadreHuecos,
				like_sig, signatura, idFondo, idFormato, numMaxResults, false,
				rangoFechas);
	}

	private List getIdsUnidsInstalacionIdPadreHuecos(String queryIdPadreHuecos,
			String like_sig, String signatura, String idFondo,
			String idFormato, int numMaxResults, boolean withTablaAsignable,
			CustomDateRange rangoFechas) throws TooManyResultsException {
		final String HUECO_TABLE = HuecoDbEntityImplBase.TABLE_NAME;
		final String FONDO_TABLE = ElementoCuadroClasificacionDBEntityImplBase.TABLE_NAME_ELEMENTO;
		final String FORMATO_TABLE = FormatoDBEntity.TABLE_NAME;

		final DbColumnDef SIGNATURAUI_FIELD = UInstalacionDepositoDBEntity.SIGNATURAUI_FIELD;
		final DbColumnDef IDUINSTALACION_FIELD = HuecoDbEntityImplBase.CAMPO_IDUINSTALACION;
		final DbColumnDef FONDO_ID_FIELD = ElementoCuadroClasificacionDBEntityImplBase.ID_ELEMENTO_FIELD;
		final DbColumnDef FORMATO_ID_FIELD = FormatoDBEntity.CAMPO_ID;
		final DbColumnDef FECHA_CREACION_FIELD = UInstalacionDepositoDBEntity.FECHA_CREACION_FIELD;

		final DbColumnDef[] QUERY_COLUMNS = { ID_FIELD };
		final String QUERY_COLUM_NAMES = DbUtil.getColumnNames(QUERY_COLUMNS);

		// tablas de la consulta
		// FROM asgdhueco h, ASGDUINSTALACION, ASGFELEMENTOCF, agformato, (
		// select asgdhueco.IDELEMAPADRE,asgdhueco.NUMORDEN
		// FROM asgdhueco asgdhueco,ASGDUINSTALACION ASGDUINSTALACION
		// WHERE asgdhueco.iduinstalacion=ASGDUINSTALACION.ID
		// AND asgdhueco.estado= 'O') huecos

		// FROM ASGDHUECO h, ASGDUINSTALACION ui, ASGFELEMENTOCF f
		StringBuffer strTablas = new StringBuffer(HUECO_TABLE).append(", ")
				.append(TABLE_NAME);
		if (withTablaAsignable)
			strTablas.append(", ").append(ElementoAsignableDBEntity.TABLE_NAME);

		if (!StringUtils.isEmpty(idFondo))
			strTablas.append(", ").append(FONDO_TABLE);
		if (!StringUtils.isEmpty(idFormato))
			strTablas.append(", ").append(FORMATO_TABLE);

		final String tablas = strTablas.toString();

		// where de la consulta
		// WHERE h.ESTADO='O' AND h.IDUINSTALACION=ui.ID AND
		// f.CODREFERENCIA=substr(ui.IDENTIFICACION,0,instr(ui.IDENTIFICACION,'.')-1)
		// + comprobaciones con el idfondo, sig_like y signatura
		StringBuffer qual = new StringBuffer("WHERE ");
		qual.append(DBUtils.generateJoinCondition(IDUINSTALACION_FIELD,
				ID_FIELD));
		if (withTablaAsignable) {
			DbColumnDef ID_ASIGNABLE_FIELD = new DbColumnDef(null,
					ElementoAsignableDBEntity.TABLE_NAME,
					ElementoAsignableDBEntity.ID_COLUMN_NAME,
					DbDataType.SHORT_TEXT, 32, false);
			qual.append(" AND ").append(
					DBUtils.generateJoinCondition(
							HuecoDbEntityImplBase.CAMPO_IDELEMPADRE,
							ID_ASIGNABLE_FIELD));
		}

		if (!StringUtils.isEmpty(idFormato)) {
			qual.append(" AND ").append(
					DBUtils.generateJoinCondition(IDFORMATO_FIELD,
							FORMATO_ID_FIELD));
		}

		// generacion de la conectar la lista de huecs con formato y que cuelgan
		// de la ubicacion seleccionada
		// con el resto de la consulta. se esta haciendo un OR de AND. se
		// relacionan por los campos clave primaria
		// de la tabla de hueco. equivalente a un in. Se hace de esta forma para
		// asegurarse que utiliza los indices de la tabla

		qual.append(" AND ")
				.append(queryIdPadreHuecos)
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(
						HuecoDbEntityImplBase.CAMPO_ESTADO,
						HuecoVO.OCUPADO_STATE));

		if (!StringUtils.isEmpty(idFondo)) {
			qual.append(" AND ");
			String posWhereQuery = DBUtils.getNativeStrPosSyntax(
					getConnection(), IDENTIFICACION_FIELD, ".") + "-1";
			String rightEQValue = DBUtils.getNativeSubstrSyntax(
					getConnection(), IDENTIFICACION_FIELD.getQualifiedName(),
					"1", posWhereQuery);
			qual.append(DBUtils
					.generateEQTokenFunction(
							ElementoCuadroClasificacionDBEntityImplBase.CODIGO_REFERENCIA_FIELD,
							rightEQValue));
			qual.append(" AND ").append(
					DBUtils.generateEQTokenField(FONDO_ID_FIELD, idFondo));
		}

		if (!StringUtils.isEmpty(signatura)) {
			qual.append(" AND ").append(
					DBUtils.generateLikeFieldQualified(SIGNATURAUI_FIELD,
							signatura, TABLE_NAME, like_sig));
		}

		if (!StringUtils.isEmpty(idFormato)) {
			qual.append(" AND ").append(
					DBUtils.generateEQTokenField(IDFORMATO_FIELD, idFormato));
		}

		if (rangoFechas.getInitialDate() != null) {
			qual.append(DBUtils.AND)
					.append("(")
					.append(DBUtils.generateTokenFieldDateAnioMesDia(
							getConnection(), FECHA_CREACION_FIELD, ">=",
							rangoFechas.getInitialDate())).append(")");
		}

		if (rangoFechas.getFinalDate() != null) {
			qual.append(DBUtils.AND)
					.append("(")
					.append(DBUtils.generateTokenFieldDateAnioMesDia(
							getConnection(), FECHA_CREACION_FIELD, "<=",
							rangoFechas.getFinalDate())).append(")");
		}

		int count = getVOCount(qual.toString(), tablas);
		if (count > numMaxResults)
			throw new TooManyResultsException(count, numMaxResults);

		// añadir order by
		qual.append(" ORDER BY ");
		qual.append(HuecoDbEntityImplBase.CAMPO_CODORDEN.getQualifiedName());
		qual.append(" ,");
		qual.append(IDFORMATO_FIELD.getQualifiedName());

		final String where = qual.toString();

		final ArrayList rows = new ArrayList();

		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				DbSelectFns.select(conn, tablas, QUERY_COLUM_NAMES, where,
						false, new AbstractDbOutputRecordSet() {
							DbOutputRecord aRow = new DbOutputRecord() {
								public void getStatementValues(
										DbOutputStatement stmt)
										throws Exception {
									BusquedaUInsDepositoVO vo = new BusquedaUInsDepositoVO();
									DBUtils.fillVoWithDbOutputStament(
											QUERY_COLUMNS, stmt, vo);
									rows.add(vo);
								}
							};

							public DbOutputRecord newRecord() throws Exception {
								return aRow;
							}
						});
			}
		};

		command.execute();
		return rows.size() > 0 ? rows : null;
	}

	public boolean checkAndUpdateIdentificacionCajas(List idsElementosMovidos,
			String codRefFondo) {
		// obtener
		return false;
	}

	public void updateIdentificacion(List idsElementosAMover, String codRefFondo) {
		// UPDATE ASGDUINSTALACION
		// SET IDENTIFICACION=(SELECT DISTINCT 'ES/NA/AS/CFP1/FP2-'||
		// SIGNATURAUI
		// FROM ASGDUDOCENUI
		// WHERE ASGDUINSTALACION.ID=ASGDUDOCENUI.IDUINSTALACION AND
		// ((ASGDUDOCENUI.IDUNIDADDOC IN ('0a3e11febad5d0000000000000000008',
		// '0a3e12a2066c90000000000000000008','0a3e12a2a62f00000000000000000008',
		// '0a3e12a313e4f0000000000000000008','0a3e12a364eb20000000000000000008',
		// '0a3e11e51632f0000000000000000008')))
		// WHERE ASGDUINSTALACION.ID IN
		// (SELECT IDUINSTALACION FROM ASGDUDOCENUI
		// WHERE IDUNIDADDOC IN ('0a3f64ba6669e000000000000000000e',
		// '0a3f66232a5ee000000000000000000e','0a3f66239f19b000000000000000000e',
		// '0a3f6623f01fe000000000000000000e','0a3f662437892000000000000000000e','0a3f64af5be02000000000000000000e'))

		String concatSymbolDb = DBUtils.getNativeConcatSyntax(getConnection());

		String subQuery = DbSelectStatement.getSelectStmtText(
				UDocEnUiDepositoDbEntityImpl.TABLE_NAME,
				UDocEnUiDepositoDbEntityImpl.IDUINSTALACION_FIELD.getName(),
				"WHERE "
						+ DBUtils.generateInTokenField(
								UDocEnUiDepositoDbEntityImpl.IDUNIDADDOC_FIELD,
								idsElementosAMover), true);

		String identificacion = "'" + codRefFondo
				+ Constants.SEPARADOR_IDENTIFICADOR_UNIDAD_INSTALACION + "'"
				+ concatSymbolDb + " " + SIGNATURAUI_COLUMN_NAME;
		StringBuffer sqlUpdate = new StringBuffer("UPDATE " + TABLE_NAME + " ")
				.append("SET " + IDENTIFICACION_COLUMN_NAME + " = ")
				.append(" (SELECT DISTINCT " + identificacion + " ")
				.append(" FROM " + UDocEnUiDepositoDbEntityImpl.TABLE_NAME)
				.append(" WHERE "
						+ ID_FIELD.getQualifiedName()
						+ "="
						+ UDocEnUiDepositoDbEntityImpl.IDUINSTALACION_FIELD
								.getQualifiedName())
				.append(" AND ")
				.append(DBUtils.generateInTokenField(
						UDocEnUiDepositoDbEntityImpl.IDUNIDADDOC_FIELD,
						idsElementosAMover))
				.append(")")
				.append(" WHERE ")
				.append(DBUtils
						.generateInTokenFieldSubQuery(ID_FIELD, subQuery));

		try {
			DbUpdateFns.update(getConnection(), sqlUpdate.toString());
		} catch (Exception e) {
			logger.debug(e);
		}
	}

	public void updateIdentificacionUnidadesInstalacion(String codRefFondo,
			TablaTemporalFondosVO tablaTemporalFondosVO) {

		String concatSymbolDb = DBUtils.getNativeConcatSyntax(getConnection());

		StringBuffer qual = new StringBuffer();
		qual.append(DBUtils.WHERE)
				.append(DBUtils
						.generateInTokenFieldSubQuery(
								ID_FIELD,
								TablaTemporalDBEntityImpl
										.getSelectIdUinstalacionElementocfFromTemporal(tablaTemporalFondosVO)));

		try {
			DbUpdateFns
					.updateCustom(
							getConnection(),
							TABLE_NAME,
							IDENTIFICACION_FIELD.getName(),
							"'"
									+ codRefFondo
									+ Constants.SEPARADOR_IDENTIFICADOR_UNIDAD_INSTALACION
									+ "'" + concatSymbolDb + " "
									+ SIGNATURAUI_COLUMN_NAME, qual.toString());
		} catch (Exception e) {
			logger.debug(e);
		}
	}

	// private String generateAndIdHueco(HuecoVO vo){
	// return "("+DBUtils.generateANDTokens(new String[]{
	// DBUtils.generateEQTokenField(HuecoDbEntityImplBase.CAMPO_IDELEMPADRE,
	// vo.getIdElemAPadre()),
	// DBUtils.generateEQTokenField(HuecoDbEntityImplBase.CAMPO_NUMORDEN,
	// vo.getNumorden().intValue())
	// +")"
	// });
	// }

	/**
	 * {@inheritDoc}
	 * 
	 * @see deposito.db.IUInstalacionDepositoDBEntity#deleteUIsDeposito(java.lang.String[])
	 */
	public void deleteUIsDeposito(final String[] idsUIDeposito) {
		final String qual = new StringBuffer().append(DBUtils.WHERE)
				.append(DBUtils.generateInTokenField(ID_FIELD, idsUIDeposito))
				.toString();

		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				DbDeleteFns.delete(conn, TABLE_NAME, qual);
			}
		};
		command.execute();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see transferencias.db.IUnidadInstalacionDBEntity#getCountUnidadesInstalacion(common.vos.ConsultaConnectBy)
	 */
	public int getCountUnidadesInstalacion(ConsultaConnectBy consultaConnectBy) {

		StringBuffer qual = new StringBuffer(DBUtils.WHERE).append(DBUtils
				.generateInTokenFieldSubQuery(ID_FIELD,
						consultaConnectBy.getSqlCount()));

		return getVOCount(qual.toString(), TABLE_NAME);
	}

}