package deposito.db;

import ieci.core.db.DbColumnDef;
import ieci.core.db.DbConnection;
import ieci.core.db.DbDataType;
import ieci.core.db.DbDeleteFns;
import ieci.core.db.DbInsertFns;
import ieci.core.db.DbSelectStatement;
import ieci.core.db.DbUpdateFns;
import ieci.core.db.DbUtil;

import java.util.HashMap;
import java.util.List;

import transferencias.db.UDocRelacionDBEntityImpl;
import transferencias.db.UdocEnUIDBEntityImpl;
import transferencias.db.UnidadInstalacionReeaDBEntityImpl;
import transferencias.vos.UnidadDocumentalVO;
import transferencias.vos.UnidadInstalacionVO;
import xml.config.ConfiguracionSistemaArchivoFactory;

import common.Constants;
import common.db.DBCommand;
import common.db.DBEntity;
import common.db.DBUtils;
import common.db.DbDataSource;
import common.db.JoinDefinition;
import common.db.SigiaDbInputRecord;
import common.db.SigiaDbOutputRecordset;
import common.db.TableDef;
import common.lang.MutableStringId;
import common.util.ArrayUtils;
import common.util.ListUtils;
import common.util.StringUtils;
import common.vos.ConsultaConnectBy;

import deposito.db.commonPostgreSQLServer.HuecoDBEntityImpl;
import deposito.vos.UDocEnUiDepositoVO;
import descripcion.db.FechaDBEntityImpl;
import fondos.db.ElementoCuadroClasificacionDBEntityImplBase;
import fondos.db.SerieDBEntityImpl;
import fondos.db.TablaTemporalDBEntityImpl;
import fondos.db.UDocEnDivisionFSDBEntityImpl;
import fondos.db.UnidadDocumentalDBEntityImpl;
import fondos.vos.ElementoCuadroClasificacionVO;
import fondos.vos.TablaTemporalFondosVO;

public class UDocEnUiDepositoDbEntityImpl extends DBEntity implements
		IUDocEnUiDepositoDbEntity {

	public static final String TABLE_NAME = "ASGDUDOCENUI";

	public static final String IDUNIDADDOC_COLUMN_NAME = "IDUNIDADDOC";

	public static final String IDUINSTALACION_COLUMN_NAME = "IDUINSTALACION";

	public static final String POSUDOCENUI_COLUMN_NAME = "POSUDOCENUI";

	public static final String SIGNATURAUDOC_COLUMN_NAME = "SIGNATURAUDOC";

	public static final String IDENTIFICACION_COLUMN_NAME = "IDENTIFICACION";

	public static final String IDUDOCRE_COLUMN_NAME = "IDUDOCRE";

	public static final String DESCRIPCION_COLUMN_NAME = "DESCRIPCION";

	public static final DbColumnDef IDUNIDADDOC_FIELD = new DbColumnDef(null,
			TABLE_NAME, IDUNIDADDOC_COLUMN_NAME, DbDataType.SHORT_TEXT, 32,
			false);

	public static final DbColumnDef IDUINSTALACION_FIELD = new DbColumnDef(
			null, TABLE_NAME, IDUINSTALACION_COLUMN_NAME,
			DbDataType.SHORT_TEXT, 32, false);

	public static final DbColumnDef POSUDOCENUI_FIELD = new DbColumnDef(null,
			TABLE_NAME, POSUDOCENUI_COLUMN_NAME, DbDataType.LONG_INTEGER, false);

	public static final DbColumnDef SIGNATURAUDOC_FIELD = new DbColumnDef(null,
			TABLE_NAME, SIGNATURAUDOC_COLUMN_NAME, DbDataType.SHORT_TEXT, 32,
			false);

	public static final DbColumnDef IDENTIFICACION_FIELD = new DbColumnDef(
			null, TABLE_NAME, IDENTIFICACION_COLUMN_NAME,
			DbDataType.SHORT_TEXT, 254, false);

	public static final DbColumnDef IDUDOCRE_FIELD = new DbColumnDef(null,
			TABLE_NAME, IDUDOCRE_COLUMN_NAME, DbDataType.SHORT_TEXT, 32, false);

	public static final DbColumnDef DESCRIPCION_FIELD = new DbColumnDef(null,
			TABLE_NAME, DESCRIPCION_COLUMN_NAME, DbDataType.SHORT_TEXT, 254,
			false);

	public static final DbColumnDef[] TABLE_COLUMNS = { IDUNIDADDOC_FIELD,
			IDUINSTALACION_FIELD, POSUDOCENUI_FIELD, SIGNATURAUDOC_FIELD,
			IDENTIFICACION_FIELD, IDUDOCRE_FIELD, DESCRIPCION_FIELD };

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

	public UDocEnUiDepositoDbEntityImpl(DbDataSource dataSource) {
		super(dataSource);
	}

	public UDocEnUiDepositoDbEntityImpl(DbConnection conn) {
		super(conn);
	}

	public void insertUDocEnUiDeposito(
			final UDocEnUiDepositoVO uDocEnUiDepositoVO) {

		DBCommand command = new DBCommand(this) {

			public void codeLogic(DbConnection conn) throws Exception {

				SigiaDbInputRecord inputRecord = new SigiaDbInputRecord(
						TABLE_COLUMNS, uDocEnUiDepositoVO);
				DbInsertFns.insert(conn, TABLE_NAME, COLUM_NAMES_LIST,
						inputRecord);

			}

		};

		command.execute();

	}

	/**
	 * Establece a una unidad documental ubicada en el depósito físico el
	 * identificador que le ha sido asignado al validarla en el cuadro de
	 * clasificación de fondos documentales
	 *
	 * @param idUnidadDocumental
	 *            Identificador asignado a la unidad documental cuando fue
	 *            incorporada a la relación de entrega mediante la que fue
	 *            incorporada al sistema
	 * @param idElementoCF
	 *            Identificador asignado a la unidad documental al validarla en
	 *            el cuadro de clasificación de fondos documentales
	 */
	public void setIDEnCF(final String idUnidadDocumental,
			final String idElementoCF) {
		final StringBuffer qual = new StringBuffer().append(" WHERE ")
				.append(DBUtils.generateEQTokenField(IDUDOCRE_FIELD,
						idUnidadDocumental));
		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				DbUpdateFns.updateShortText(conn, TABLE_NAME,
						IDUNIDADDOC_COLUMN_NAME, idElementoCF, qual.toString());
			}
		};
		command.execute();
	}

	/**
	 * Recupera las signaturas que contiene una unidad de instalación del
	 * depósito físico
	 *
	 * @param idUInstalacion
	 *            Identificador de unidad de instalación
	 * @return Lista de signaturas que contiene la unidad de instalación
	 *         {@link UDocEnUiDepositoVO}
	 */
	public List getUDocsVOXIdUInstalacion(String idUInstalacion) {
		String qual = new StringBuffer(" WHERE ")
				.append(DBUtils.generateEQTokenField(IDUINSTALACION_FIELD,
						idUInstalacion)).append(" ORDER BY ")
				.append(POSUDOCENUI_FIELD).toString();

		return getUDocsVO(qual);
	}

	/**
	 * Recupera una unidad documental instalada en una determinada posición
	 * dentro de una unidad de instalacion
	 *
	 * @param idUInstalacion
	 *            Identificador de unidad de instalación instalada en un hueco
	 *            del depósito físico
	 * @param posUdoc
	 *            Posición de unidad documental dentro de la unidad de
	 *            instalación
	 */
	public UDocEnUiDepositoVO getUdocEnUI(String idUInstalacion, int posUdoc) {
		String qual = new StringBuffer(" WHERE ")
				.append(DBUtils.generateEQTokenField(IDUINSTALACION_FIELD,
						idUInstalacion))
				.append(" AND ")
				.append(DBUtils
						.generateEQTokenField(POSUDOCENUI_FIELD, posUdoc))
				.toString();
		return getUdocVO(qual.toString());
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * deposito.db.IUDocEnUiDepositoDbEntity#getUdocEnUIById(java.lang.String,
	 * java.lang.String)
	 */
	public UDocEnUiDepositoVO getUdocEnUIById(String idUInstalacion,
			String idUdoc) {
		String qual = new StringBuffer(" WHERE ")
				.append(DBUtils.generateEQTokenField(IDUINSTALACION_FIELD,
						idUInstalacion))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(IDUNIDADDOC_FIELD, idUdoc))
				.toString();
		return getUdocVO(qual.toString());
	}

	/**
	 * Obtiene el conjunto de signaturas en las que se descompone una unidad
	 * documental
	 *
	 * @param idUnidadDocumental
	 *            Identificador de unidad documental en el cuadro de
	 *            clasificación de fondos documentales
	 * @return Lista de signaturas {@link UDocEnUiDepositoVO}
	 */
	public List getPartesUdocByIDElementoCF(String idUnidadDocumental) {
		String qual = new StringBuffer()
				.append(" WHERE ")
				.append(DBUtils.generateEQTokenField(IDUNIDADDOC_FIELD,
						idUnidadDocumental))
				.append(DBUtils.generateOrderBy(SIGNATURAUDOC_FIELD))
				.toString();

		return getUDocsVO(qual);
	}

	public List getIdsUIsPartesUdocByIDElementoCF(String idUnidadDocumental) {
		String qual = new StringBuffer()
				.append(" WHERE ")
				.append(DBUtils.generateEQTokenField(IDUNIDADDOC_FIELD,
						idUnidadDocumental)).toString();

		return getFieldValues(qual, TABLE_NAME, IDUINSTALACION_FIELD);
	}

	public HashMap getUINumPartesUdocs(
			TablaTemporalFondosVO tablaTemporalFondosVO) {
		// SELECT IDUINSTALACION,COUNT(IDUNIDADDOC)
		// FROM ASGDUDOCENUI
		// WHERE IDUNIDADDOC IN (SELECT ID FROM TMP )
		// GROUP BY IDUINSTALACION

		StringBuffer qual = new StringBuffer()
				.append(" WHERE ")
				.append(DBUtils
						.generateInTokenFieldSubQuery(
								IDUNIDADDOC_FIELD,
								TablaTemporalDBEntityImpl
										.getSelectIdElementocfFromTemporal(tablaTemporalFondosVO)))
				.append(" GROUP BY ").append(IDUINSTALACION_COLUMN_NAME);

		return getPairsIdValue(qual.toString(), TABLE_NAME,
				IDUINSTALACION_FIELD,
				new DbColumnDef(TABLE_NAME, "COUNT(" + IDUNIDADDOC_COLUMN_NAME
						+ ")", DbDataType.MUTABLE_INT));
	}

	/**
	 * Obtiene el conjunto de signaturas en las que se descompone una unidad
	 * documental
	 *
	 * @param idUnidadDocumental
	 *            Identificador de unidad documental en la relación de entrega
	 *            mediante la que fue transferida al archivo
	 * @return Lista de signaturas {@link UDocEnUiDepositoVO}
	 */
	public List getPartesUdocByIDUdocEnRelacionEntrega(String idUnidadDocumental) {
		String qual = new StringBuffer(" WHERE ")
				.append(DBUtils.generateEQTokenField(IDUDOCRE_FIELD,
						idUnidadDocumental))
				.append(" AND ")
				.append(DBUtils.generateJoinCondition(IDUDOCRE_FIELD,
						UdocEnUIDBEntityImpl.UDOC_FIELD)).append(" ORDER BY ")
				.append(UdocEnUIDBEntityImpl.PARTEUDOC_COLUMN_NAME).toString();
		String[] tableList = { TABLE_NAME, UdocEnUIDBEntityImpl.TABLE_NAME };
		return getVOS(qual, ArrayUtils.join(tableList, ","), TABLE_COLUMNS,
				UDocEnUiDepositoVO.class);
	}

	private UDocEnUiDepositoVO getUdocVO(String qual) {
		return (UDocEnUiDepositoVO) getVO(qual, TABLE_NAME, TABLE_COLUMNS,
				UDocEnUiDepositoVO.class);
	}

	/**
	 * Obtiene los VO de las Unidades Documentales
	 *
	 * @param qual
	 * @return Lista de UDocEnUiDepositoVO.class
	 */
	private List getUDocsVO(String qual) {
		return getVOS(qual.toString(), TABLE_NAME, TABLE_COLUMNS,
				UDocEnUiDepositoVO.class);
	}

	public void bloquearUDoc(String idUdoc, String signaturaUdoc) {
		final String qual = new StringBuffer(" WHERE ")
				.append(DBUtils.generateEQTokenField(IDUNIDADDOC_FIELD, idUdoc))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(SIGNATURAUDOC_FIELD,
						signaturaUdoc)).toString();

		UDocEnUiDepositoVO uDoc = (UDocEnUiDepositoVO) getVO(qual, TABLE_NAME,
				TABLE_COLUMNS, UDocEnUiDepositoVO.class);

		updateVO(qual, TABLE_NAME, TABLE_COLUMNS, uDoc);
	}

	/**
	 * Borra las entradas en la tabla de una unidad de instalacion
	 * dada(identificada por su id de udoc)
	 *
	 * @param idUdoc
	 *            Identificador de la udoc que se desea eliminar está duplicado.
	 *            Utilizar deleteUdoc(String iduinstalacion, String idUdoc,
	 *            String signaturaudoc);
	 */
	public void deleteUdoc(String idUdoc) {
		final String qual = new StringBuffer()
				.append(" WHERE ")
				.append(DBUtils.generateEQTokenField(IDUNIDADDOC_FIELD, idUdoc))
				.toString();
		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				DbDeleteFns.delete(conn, TABLE_NAME, qual);
			}
		};
		command.execute();
	}

	/*
	 * (sin Javadoc)
	 *
	 * @see deposito.db.IUDocEnUiDepositoDbEntity#deleteUdoc(java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	public void deleteUdoc(String iduinstalacion, String idUdoc,
			String signaturaudoc) {
		final String qual = new StringBuffer()
				.append(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(IDUINSTALACION_FIELD,
						iduinstalacion))
				.append(DBUtils.AND)
				.append(DBUtils.generateEQTokenField(IDUNIDADDOC_FIELD, idUdoc))
				.append(DBUtils.AND)
				.append(DBUtils.generateEQTokenField(SIGNATURAUDOC_FIELD,
						signaturaudoc)).toString();
		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				DbDeleteFns.delete(conn, TABLE_NAME, qual);
			}
		};

		command.execute();

	}

	/**
	 * Obtiene las Uniades Validadas de una Unidad de Instalación
	 *
	 * @param idUInstalacion
	 *            Identificador unidad documental
	 * @return Lista de UDocEnUiDepositoVO
	 */
	public List getUDocsVOValidadasXIdUInstalacion(String idUInstalacion) {

		String qual = new StringBuffer(" WHERE ")
				.append(DBUtils.generateEQTokenField(IDUINSTALACION_FIELD,
						idUInstalacion)).append(" AND ")
				.append(DBUtils.generateNEQTokenField(IDUNIDADDOC_FIELD, null))
				.append(" ORDER BY ").append(POSUDOCENUI_FIELD).toString();

		return getUDocsVO(qual);

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * deposito.db.IUDocEnUiDepositoDbEntity#getUDocsVOXId(java.lang.String[])
	 */
	public List getUDocsVOXId(String[] idUdocs) {
		String qual = new StringBuffer(" WHERE ")
				.append(DBUtils.generateORTokens(IDUNIDADDOC_FIELD, idUdocs))
				.append(" ORDER BY ").append(POSUDOCENUI_FIELD).toString();

		return getUDocsVO(qual);
	}

	/*
	 * (sin Javadoc)
	 *
	 * @see
	 * deposito.db.IUDocEnUiDepositoDbEntity#getUDocsVOXId(java.lang.String[],
	 * java.lang.String[])
	 */
	public List getUDocsVOXId(String[] idUdocs, String[] signaturas) {
		String qual = new StringBuffer(DBUtils.WHERE)
				.append(DBUtils.generateORTokens(IDUNIDADDOC_FIELD, idUdocs,
						SIGNATURAUDOC_FIELD, signaturas))
				.append(DBUtils.generateOrderBy(POSUDOCENUI_FIELD)).toString();
		return getUDocsVO(qual);
	}

	public List getUdocsVOXId(String idUdoc) {
		String qual = new StringBuffer(" WHERE ")
				.append(DBUtils.generateEQTokenField(IDUNIDADDOC_FIELD, idUdoc))
				.append(" ORDER BY ").append(POSUDOCENUI_FIELD).toString();

		return getUDocsVO(qual);
	}

	public void updateSignaturaEIdentificacionYPosUdocYIdUIns(String idUI,
			String idUnidadDoc, String signatura, String nuevaSignatura,
			String nuevaIdentificacion, int posUdoc, String idNuevaUI) {
		String qual = new StringBuffer(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(IDUNIDADDOC_FIELD,
						idUnidadDoc))
				.append(DBUtils.AND)
				.append(DBUtils.generateEQTokenField(SIGNATURAUDOC_FIELD,
						signatura))
				.append(DBUtils.AND)
				.append(DBUtils
						.generateEQTokenField(IDUINSTALACION_FIELD, idUI))
				.toString();
		UDocEnUiDepositoVO uDoc = new UDocEnUiDepositoVO();
		uDoc.setSignaturaudoc(nuevaSignatura);
		uDoc.setIdentificacion(nuevaIdentificacion);
		uDoc.setPosudocenui(posUdoc);
		uDoc.setIduinstalacion(idNuevaUI);

		DbColumnDef[] colsToUpdate = new DbColumnDef[] { SIGNATURAUDOC_FIELD,
				IDENTIFICACION_FIELD, POSUDOCENUI_FIELD, IDUINSTALACION_FIELD };
		updateVO(qual, TABLE_NAME, colsToUpdate, uDoc);

	}

	public void updateSignaturaEIdentificacionYPosUdoc(String idUI,
			String idUnidadDoc, String signatura, String nuevaSignatura,
			String nuevaIdentificacion, int posUdoc) {
		String qual = new StringBuffer(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(IDUNIDADDOC_FIELD,
						idUnidadDoc))
				.append(DBUtils.AND)
				.append(DBUtils.generateEQTokenField(SIGNATURAUDOC_FIELD,
						signatura))
				.append(DBUtils.AND)
				.append(DBUtils
						.generateEQTokenField(IDUINSTALACION_FIELD, idUI))
				.toString();
		UDocEnUiDepositoVO uDoc = new UDocEnUiDepositoVO();
		uDoc.setSignaturaudoc(nuevaSignatura);
		uDoc.setIdentificacion(nuevaIdentificacion);
		uDoc.setPosudocenui(posUdoc);

		DbColumnDef[] colsToUpdate = new DbColumnDef[] { SIGNATURAUDOC_FIELD,
				IDENTIFICACION_FIELD, POSUDOCENUI_FIELD };
		updateVO(qual, TABLE_NAME, colsToUpdate, uDoc);

	}

	public void updateIdentificacion(
			ElementoCuadroClasificacionVO elementoCuadroClasificacionVO,
			String idUdoc) {
		updateIdentificacion(elementoCuadroClasificacionVO.getCodRefFondo(),
				idUdoc);
	}

	// metodo duplicado para evitar que se duplique el uso de memoria usada al
	// tratar de convertir
	// el array a lista.
	public void updateIdentificacion(String codRefFondo,
			TablaTemporalFondosVO tablaTemporalFondosVO) throws Exception {

		// String TABLE_NAME_ASGDUDOCENUI = "ASGDUDOCENUI";
		//
		// String IDUNIDADDOC_COLUMN_NAME = "IDUNIDADDOC";
		// DbColumnDef IDUNIDADDOC_FIELD = new DbColumnDef(
		// null, TABLE_NAME_ASGDUDOCENUI, IDUNIDADDOC_COLUMN_NAME,
		// DbDataType.SHORT_TEXT, 32, false);
		//
		//
		// String SIGNATURAUDOC_COLUMN_NAME = "SIGNATURAUDOC";
		// DbColumnDef SIGNATURAUDOC_FIELD = new DbColumnDef(
		// null, TABLE_NAME_ASGDUDOCENUI, SIGNATURAUDOC_COLUMN_NAME,
		// DbDataType.SHORT_TEXT, 32, false);
		StringBuffer qual = new StringBuffer();
		qual.append(" WHERE ")
				.append(DBUtils
						.generateInTokenFieldSubQuery(
								IDUNIDADDOC_FIELD,
								TablaTemporalDBEntityImpl
										.getSelectIdElementocfFromTemporal(tablaTemporalFondosVO)));

		// udoc.setIdentificacion(codRefFondo + "-" + udoc.getSignaturaudoc());
		// DbColumnDef[] colsToUpdate = new DbColumnDef[] { IDENTIFICACION_FIELD
		// };
		String concatSymbolDb = DBUtils.getNativeConcatSyntax(getConnection());
		DbUpdateFns.updateCustom(getConnection(), TABLE_NAME,
					IDENTIFICACION_FIELD.getName(), "'" + codRefFondo + "-'"
							+ concatSymbolDb + " " + SIGNATURAUDOC_COLUMN_NAME,
					qual.toString());

		// updateVO(qual.toString(), , colsToUpdate, udoc);
	}

	public void updateIdentificacion(String codRefFondo, List idsUdoc) throws Exception {
		StringBuffer qual = new StringBuffer();
		qual.append(DBUtils.WHERE).append(
				DBUtils.generateInTokenField(IDUNIDADDOC_FIELD, idsUdoc));

		String concatSymbolDb = DBUtils.getNativeConcatSyntax(getConnection());
			DbUpdateFns
					.updateCustom(
							getConnection(),
							TABLE_NAME,
							IDENTIFICACION_FIELD.getName(),
							"'"
									+ codRefFondo
									+ Constants.SEPARADOR_IDENTIFICADOR_UNIDAD_DOCUMENTAL
									+ "'" + concatSymbolDb + " "
									+ SIGNATURAUDOC_COLUMN_NAME,
							qual.toString());
	}

	public void updateIdentificacion(String codRefFondo, String idUdoc) {

		// String TABLE_NAME_ASGDUDOCENUI = "ASGDUDOCENUI";
		//
		// String IDUNIDADDOC_COLUMN_NAME = "IDUNIDADDOC";
		// DbColumnDef IDUNIDADDOC_FIELD = new DbColumnDef(
		// null, TABLE_NAME_ASGDUDOCENUI, IDUNIDADDOC_COLUMN_NAME,
		// DbDataType.SHORT_TEXT, 32, false);
		//
		//
		// String SIGNATURAUDOC_COLUMN_NAME = "SIGNATURAUDOC";
		// DbColumnDef SIGNATURAUDOC_FIELD = new DbColumnDef(
		// null, TABLE_NAME_ASGDUDOCENUI, SIGNATURAUDOC_COLUMN_NAME,
		// DbDataType.SHORT_TEXT, 32, false);

		List uDocs = getUdocsVOXId(idUdoc);
		if (!ListUtils.isEmpty(uDocs)) {

			for (int i = 0; i < uDocs.size(); i++) {
				UDocEnUiDepositoVO udoc = (UDocEnUiDepositoVO) uDocs.get(i);
				StringBuffer qual = new StringBuffer();
				qual.append(" WHERE ").append(
						DBUtils.generateEQTokenField(SIGNATURAUDOC_FIELD,
								udoc.getSignaturaudoc()));

				udoc.setIdentificacion(codRefFondo + "-"
						+ udoc.getSignaturaudoc());
				DbColumnDef[] colsToUpdate = new DbColumnDef[] { IDENTIFICACION_FIELD };
				updateVO(qual.toString(), TABLE_NAME, colsToUpdate, udoc);
			}
		}
	}

	/*
	 * (sin Javadoc)
	 *
	 * @see
	 * deposito.db.IUDocEnUiDepositoDbEntity#updatePosicionUDoc(deposito.vos
	 * .UDocEnUiDepositoVO)
	 */
	public void updatePosicionUDoc(UDocEnUiDepositoVO udoc) {
		String qual = new StringBuffer(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(IDUNIDADDOC_FIELD,
						udoc.getIdunidaddoc()))
				.append(DBUtils.AND)
				.append(DBUtils.generateEQTokenField(SIGNATURAUDOC_FIELD,
						udoc.getSignaturaudoc()))
				.append(DBUtils.AND)
				.append(DBUtils.generateEQTokenField(IDUINSTALACION_FIELD,
						udoc.getIduinstalacion())).toString();

		DbColumnDef[] colsToUpdate = new DbColumnDef[] { POSUDOCENUI_FIELD };
		updateVO(qual, TABLE_NAME, colsToUpdate, udoc);
	}

	/*
	 * (sin Javadoc)
	 *
	 * @see
	 * deposito.db.IUDocEnUiDepositoDbEntity#updateIdentificacionYSignaturaUDoc
	 * (deposito.vos.UDocEnUiDepositoVO)
	 */
	public void updateIdentificacionYSignaturaUDoc(UDocEnUiDepositoVO udoc) {
		String qual = new StringBuffer(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(IDUNIDADDOC_FIELD,
						udoc.getIdunidaddoc()))
				.append(DBUtils.AND)
				.append(DBUtils.generateEQTokenField(POSUDOCENUI_FIELD,
						udoc.getPosudocenui()))
				.append(DBUtils.AND)
				.append(DBUtils.generateEQTokenField(IDUINSTALACION_FIELD,
						udoc.getIduinstalacion())).toString();

		DbColumnDef[] colsToUpdate = new DbColumnDef[] { SIGNATURAUDOC_FIELD,
				IDENTIFICACION_FIELD };
		updateVO(qual, TABLE_NAME, colsToUpdate, udoc);

	}

	public List getUdocsUInst(String idUnidadInstalacion) {
		JoinDefinition[] joinsUDocEnUiElementoCF = new JoinDefinition[] { new JoinDefinition(
				new DbColumnDef(new TableDef(
						UDocEnUiDepositoDbEntityImpl.TABLE_NAME),
						UDocEnUiDepositoDbEntityImpl.IDUNIDADDOC_FIELD),
				new DbColumnDef(
						new TableDef(
								ElementoCuadroClasificacionDBEntityImplBase.TABLE_NAME_ELEMENTO),
						ElementoCuadroClasificacionDBEntityImplBase.ID_ELEMENTO_FIELD))

		};

		DbColumnDef[] COLS = {
				UDocEnUiDepositoDbEntityImpl.IDUINSTALACION_FIELD,
				UDocEnUiDepositoDbEntityImpl.IDUNIDADDOC_FIELD,
				UDocEnUiDepositoDbEntityImpl.SIGNATURAUDOC_FIELD,
				ElementoCuadroClasificacionDBEntityImplBase.TITULO_FIELD };

		String qual = new StringBuffer()
				.append(DBUtils
						.generateInnerJoinChainedCondition(joinsUDocEnUiElementoCF))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(
						UDocEnUiDepositoDbEntityImpl.IDUINSTALACION_FIELD,
						idUnidadInstalacion)).toString();

		return getVOS(qual.toString(), UDocEnUiDepositoDbEntityImpl.TABLE_NAME,
				COLS, UnidadInstalacionVO.class);

	}

	/*
	 * (sin Javadoc)
	 *
	 * @see
	 * deposito.db.IUDocEnUiDepositoDbEntity#getUdocsXidRelacionEntreArchivos
	 * (java.lang.String)
	 */
	public List getUdocsXidRelacionEntreArchivos(String idRelacion) {
		/*
		 * SELECT A.IDUNIDADDOC AS ID, B.TITULO AS ASUNTO FROM FROM ASGDUDOCENUI
		 * A, ASGFELEMENTOCF B, ASGFUNIDADDOC C WHERE A.IDUNIDADDOC = B.ID AND
		 * A.IDUINSTALACION IN( SELECT asgtuinstalacionreea.IDUIDEPOSITO FROM
		 * asgtuinstalacionreea WHERE asgtuinstalacionreea.IDRELENTREGA =
		 * idRelacion ) AND B.ID = C.IDELEMENTOCF ORDER BY A.SIGNATURAUDOC,
		 * A.POSUDOCENUI
		 */

		DbColumnDef idUnidadDocumental = new DbColumnDef("ID", TABLE_NAME,
				UDocEnUiDepositoDbEntityImpl.IDUNIDADDOC_FIELD);
		DbColumnDef idAsunto = new DbColumnDef(
				"ASUNTO",
				ElementoCuadroClasificacionDBEntityImplBase.TABLE_NAME_ELEMENTO,
				ElementoCuadroClasificacionDBEntityImplBase.TITULO_FIELD);
		// DbColumnDef numExp = new DbColumnDef("NUMEROEXPEDIENTE",
		// UnidadDocumentalDBEntityImpl.TABLE_NAME_UNIDAD_DOCUMENTAL,
		// UnidadDocumentalDBEntityImpl.CAMPO_NUMERO_EXPEDIENTE);

		String tables = DBUtils
				.generateTableSet(new String[] {
						TABLE_NAME,
						ElementoCuadroClasificacionDBEntityImplBase.TABLE_NAME_ELEMENTO,
						UnidadDocumentalDBEntityImpl.TABLE_NAME_UNIDAD_DOCUMENTAL });

		DbColumnDef[] COLS = { idUnidadDocumental, idAsunto,
				UDocRelacionDBEntityImpl.NUMERO_EXPEDIENTE_FIELD,
				UnidadDocumentalDBEntityImpl.CAMPO_MARCAS_BLOQUEO };

		String sqlRelacion = DbSelectStatement
				.getSelectStmtText(
						UnidadInstalacionReeaDBEntityImpl.TABLE_NAME,
						UnidadInstalacionReeaDBEntityImpl.ID_UIDEPOSITO_FIELD
								.getQualifiedName(),
						DBUtils.WHERE
								+ DBUtils
										.generateEQTokenField(
												UnidadInstalacionReeaDBEntityImpl.ID_RELACIONENTREGA_FIELD,
												idRelacion), false);

		StringBuffer qual = new StringBuffer(DBUtils.WHERE)
				.append(DBUtils
						.generateJoinCondition(
								IDUNIDADDOC_FIELD,
								ElementoCuadroClasificacionDBEntityImplBase.ID_ELEMENTO_FIELD))
				.append(DBUtils.AND)
				.append(DBUtils
						.generateJoinCondition(
								UnidadDocumentalDBEntityImpl.CAMPO_ID,
								ElementoCuadroClasificacionDBEntityImplBase.ID_ELEMENTO_FIELD))
				.append(DBUtils.AND).append(IDUINSTALACION_FIELD)
				.append(DBUtils.IN).append("(").append(sqlRelacion).append(")")
				.append(DBUtils.GROUPBY).append(DbUtil.getColumnNames(COLS))
				.append(DBUtils.generateOrderBy(idAsunto));

		return getVOS(qual.toString(), tables, COLS, UnidadDocumentalVO.class);
	}

	/*
	 * (sin Javadoc)
	 *
	 * @see
	 * deposito.db.IUDocEnUiDepositoDbEntity#getUdocsXIdUinstalacion(java.lang
	 * .String)
	 */
	public List getUdocsXIdUinstalacion(String idUnidadInstalacion) {
		/*
		 * SELECT A.IDUNIDADDOC AS ID, B.TITULO AS ASUNTO FROM ASGDUDOCENUI A,
		 * ASGFELEMENTOCF B WHERE A.IDUNIDADDOC = B.ID A.IDUINSTALACION =
		 * idUnidadInstalacion ORDER BY A.POSUDOCENUI
		 */

		DbColumnDef idUnidadDocumental = new DbColumnDef("ID", TABLE_NAME,
				UDocEnUiDepositoDbEntityImpl.IDUNIDADDOC_FIELD);
		DbColumnDef idAsunto = new DbColumnDef(
				"ASUNTO",
				ElementoCuadroClasificacionDBEntityImplBase.TABLE_NAME_ELEMENTO,
				ElementoCuadroClasificacionDBEntityImplBase.TITULO_FIELD);

		String tables = DBUtils
				.generateTableSet(new String[] {
						TABLE_NAME,
						ElementoCuadroClasificacionDBEntityImplBase.TABLE_NAME_ELEMENTO,
						UnidadDocumentalDBEntityImpl.TABLE_NAME_UNIDAD_DOCUMENTAL });

		DbColumnDef[] COLS = { idUnidadDocumental, idAsunto,
				UnidadDocumentalDBEntityImpl.CAMPO_MARCAS_BLOQUEO };

		StringBuffer qual = new StringBuffer(DBUtils.WHERE)
				.append(DBUtils
						.generateJoinCondition(
								IDUNIDADDOC_FIELD,
								ElementoCuadroClasificacionDBEntityImplBase.ID_ELEMENTO_FIELD))
				.append(DBUtils.AND)
				.append(DBUtils
						.generateJoinCondition(
								UnidadDocumentalDBEntityImpl.CAMPO_ID,
								ElementoCuadroClasificacionDBEntityImplBase.ID_ELEMENTO_FIELD))
				.append(DBUtils.AND)
				.append(DBUtils.generateEQTokenField(IDUINSTALACION_FIELD,
						idUnidadInstalacion))
				.append(DBUtils.generateOrderBy(POSUDOCENUI_FIELD));

		return getVOS(qual.toString(), tables, COLS, UnidadDocumentalVO.class);
	}

	/*
	 * (sin Javadoc)
	 *
	 * @see
	 * deposito.db.IUDocEnUiDepositoDbEntity#getUdocsXIdUinstalacion(java.lang
	 * .String)
	 */
	public List getUdocsXIdUinstalacionWithNumExp(String idUnidadInstalacion) {

		DbColumnDef idUnidadDocumental = new DbColumnDef("ID", TABLE_NAME,
				UDocEnUiDepositoDbEntityImpl.IDUNIDADDOC_FIELD);
		DbColumnDef idAsunto = new DbColumnDef(
				"ASUNTO",
				ElementoCuadroClasificacionDBEntityImplBase.TABLE_NAME_ELEMENTO,
				ElementoCuadroClasificacionDBEntityImplBase.TITULO_FIELD);
		// DbColumnDef numExp = new DbColumnDef("NUMEROEXPEDIENTE",
		// UnidadDocumentalDBEntityImpl.TABLE_NAME_UNIDAD_DOCUMENTAL,
		// UnidadDocumentalDBEntityImpl.CAMPO_NUMERO_EXPEDIENTE);

		String tables = DBUtils
				.generateTableSet(new String[] {
						TABLE_NAME,
						ElementoCuadroClasificacionDBEntityImplBase.TABLE_NAME_ELEMENTO,
						UnidadDocumentalDBEntityImpl.TABLE_NAME_UNIDAD_DOCUMENTAL });

		DbColumnDef[] COLS = { idUnidadDocumental, idAsunto,
				UnidadDocumentalDBEntityImpl.CAMPO_MARCAS_BLOQUEO };

		StringBuffer qual = new StringBuffer(DBUtils.WHERE)
				.append(DBUtils
						.generateJoinCondition(
								IDUNIDADDOC_FIELD,
								ElementoCuadroClasificacionDBEntityImplBase.ID_ELEMENTO_FIELD))
				.append(DBUtils.AND)
				.append(DBUtils
						.generateJoinCondition(
								UnidadDocumentalDBEntityImpl.CAMPO_ID,
								ElementoCuadroClasificacionDBEntityImplBase.ID_ELEMENTO_FIELD))
				.append(DBUtils.AND)
				.append(DBUtils.generateEQTokenField(IDUINSTALACION_FIELD,
						idUnidadInstalacion))
				.append(DBUtils.generateOrderBy(POSUDOCENUI_FIELD));

		return getVOS(qual.toString(), tables, COLS, UnidadDocumentalVO.class);
	}

	/*
	 * (sin Javadoc)
	 *
	 * @see
	 * deposito.db.IUDocEnUiDepositoDbEntity#getUdocsIncompletasEnRelacion(java
	 * .lang.String, java.lang.String[], java.lang.String)
	 */
	public List getPartesUdocsNoIncluidasEnRelacion(String idRelacion,
			List idsUdocsRelacion, List idsUinstRelacion) {
		/*
		 * SELECT * FROM ASGDUDOCENUI WHERE ASGDUDOCENUI.IDUNIDADDOC IN (
		 * idsUdocs[] ) AND IDUINSTALACION NOT IN IN( idUinst[] )
		 */
		if (!ListUtils.isEmpty(idsUdocsRelacion)
				&& !ListUtils.isEmpty(idsUinstRelacion)) {

			StringBuffer qual = new StringBuffer(DBUtils.WHERE)
					.append(DBUtils.generateInTokenField(IDUNIDADDOC_FIELD,
							idsUdocsRelacion))
					.append(DBUtils.AND)
					.append(DBUtils.generateNotInTokenField(
							IDUINSTALACION_FIELD, idsUinstRelacion));

			return getUDocsVO(qual.toString());
		}

		return null;
	}

	/*
	 * (sin Javadoc)
	 *
	 * @see
	 * deposito.db.IUDocEnUiDepositoDbEntity#getUdocsIncompletasEnRelacion(java
	 * .util.List, java.util.List)
	 */
	public List getPartesUdocsIncompletasEnRelacion(List idsUdocs, List idsUInst) {
		/*
		 * SELECT * FROM ASGDUDOCENUI WHERE ASGDUDOCENUI.IDUNIDADDOC IN (
		 * idsUdocs[] ) AND IDUINSTALACION IN IN( idUinst[] )
		 */

		StringBuffer qual = new StringBuffer(DBUtils.WHERE)
				.append(DBUtils.generateInTokenField(IDUNIDADDOC_FIELD,
						idsUdocs))
				.append(DBUtils.AND)
				.append(DBUtils.generateInTokenField(IDUINSTALACION_FIELD,
						idsUInst));

		return getUDocsVO(qual.toString());
	}

	public boolean isBloqueada(String idUnidadDocumental) {

		JoinDefinition[] joinsUDocEnUiInstalacion = new JoinDefinition[] { new JoinDefinition(
				new DbColumnDef(new TableDef(
						UDocEnUiDepositoDbEntityImpl.TABLE_NAME),
						UDocEnUiDepositoDbEntityImpl.IDUINSTALACION_FIELD),
				new DbColumnDef(new TableDef(
						UInstalacionDepositoDBEntity.TABLE_NAME),
						UInstalacionDepositoDBEntity.ID_FIELD))

		};

		StringBuffer qual = new StringBuffer()
				.append(DBUtils
						.generateInnerJoinChainedCondition(joinsUDocEnUiInstalacion))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(
						UInstalacionDepositoDBEntity.MARCAS_BLOQUEO_FIELD, 1))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(
						UDocEnUiDepositoDbEntityImpl.IDUNIDADDOC_FIELD,
						idUnidadDocumental));

		List list = getUDocsVO(qual.toString());
		return !ListUtils.isEmpty(list);

	}

	public UDocEnUiDepositoVO getUdocByIDUdocEnDivisionFS(
			String idUdocEnDivisionFS) {

		StringBuffer qual = new StringBuffer(" WHERE ").append(DBUtils
				.generateEQTokenField(IDUDOCRE_FIELD, idUdocEnDivisionFS));

		return getUdocVO(qual.toString());
	}

	public UDocEnUiDepositoVO getUdocByIdUnidadDoc(String idUnidadDoc) {

		StringBuffer qual = new StringBuffer(DBUtils.WHERE).append(DBUtils
				.generateEQTokenField(IDUNIDADDOC_FIELD, idUnidadDoc));

		return getUdocVO(qual.toString());
	}

	public List getPartesUdocByIDUdocEnDivisionFS(String idUDoc) {

		String qual = new StringBuffer(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(IDUDOCRE_FIELD, idUDoc))
				.append(" AND ")
				.append(DBUtils.generateJoinCondition(IDUDOCRE_FIELD,
						UDocEnDivisionFSDBEntityImpl.CAMPO_ID_UDOC)).toString();

		String[] tableList = { TABLE_NAME,
				UDocEnDivisionFSDBEntityImpl.TABLE_NAME };
		return getVOS(qual, ArrayUtils.join(tableList, ","), TABLE_COLUMNS,
				UDocEnUiDepositoVO.class);
	}

	// método llamado si cambia el fondo durante el movimiento del elemento del
	// cuadro.
	// o se mueven todos las udocs a la vez de la UI, o no se puede mover
	// ninguna
	public List checkValidMoverUdocsMismoFondo(List idsElementosAMover) {
		// SELECT DISTINCT IDUINSTALACION
		// FROM ASGDUDOCENUI
		// WHERE IDUINSTALACION IN (SELECT DISTINCT IDUINSTALACION
		// FROM ASGDUDOCENUI
		// WHERE ASGDUDOCENUI.IDUNIDADDOC IN ()) AND
		// ASGDUDOCENUI.IDUNIDADDOC NOT IN ()

		StringBuffer qual = new StringBuffer(" WHERE ")
				.append(IDUINSTALACION_COLUMN_NAME)
				.append(" IN (")
				.append("SELECT DISTINCT ")
				.append(IDUINSTALACION_COLUMN_NAME)
				.append(" FROM ")
				.append(TABLE_NAME)
				.append(" WHERE ")
				.append(DBUtils.generateInTokenField(IDUNIDADDOC_FIELD,
						idsElementosAMover))
				.append(") AND ")
				.append(DBUtils.generateNotInTokenField(IDUNIDADDOC_FIELD,
						idsElementosAMover));

		List noValidasUIs = null;
		try {
			noValidasUIs = getDistinctFieldValues(qual.toString(), TABLE_NAME,
					IDUINSTALACION_FIELD);
		} catch (Exception e) {
			logger.debug(e);
		}
		return noValidasUIs;
	}

	public void updateDescripcionUDoc(UDocEnUiDepositoVO udoc) {
		String qual = new StringBuffer(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(IDUNIDADDOC_FIELD,
						udoc.getIdunidaddoc()))
				.append(DBUtils.AND)
				.append(DBUtils.generateEQTokenField(SIGNATURAUDOC_FIELD,
						udoc.getSignaturaudoc()))
				.append(DBUtils.AND)
				.append(DBUtils.generateEQTokenField(IDUINSTALACION_FIELD,
						udoc.getIduinstalacion())).toString();

		DbColumnDef[] colsToUpdate = new DbColumnDef[] { DESCRIPCION_FIELD };
		updateVO(qual, TABLE_NAME, colsToUpdate, udoc);
	}

	public UDocEnUiDepositoVO getUDocByIdAndSignatura(String idUdoc,
			String signaturaUdoc) {
		StringBuffer qual = new StringBuffer(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(IDUNIDADDOC_FIELD, idUdoc))
				.append(DBUtils.AND)
				.append(DBUtils.generateEQTokenField(SIGNATURAUDOC_FIELD,
						signaturaUdoc));

		return getUdocVO(qual.toString());
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see deposito.db.IUDocEnUiDepositoDbEntity#getUDocsEnUIByUdocsRe(java.lang.String[])
	 */
	public List getUDocsEnUIByUdocsRe(String[] idsUdocRe) {
		StringBuffer qual = new StringBuffer(DBUtils.WHERE);
		qual.append(DBUtils.generateInTokenField(IDUDOCRE_FIELD, idsUdocRe));
		return getUDocsVO(qual.toString());
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see deposito.db.IUDocEnUiDepositoDbEntity#deleteByIdsUdoc(java.lang.String[])
	 */
	public void deleteByIdsUdoc(String[] idsUdoc) {
		final StringBuffer qual = new StringBuffer().append(DBUtils.WHERE)
				.append(DBUtils.generateInTokenField(IDUDOCRE_FIELD, idsUdoc));
		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				DbDeleteFns.delete(conn, TABLE_NAME, qual.toString());
			}
		};
		command.execute();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see deposito.db.IUDocEnUiDepositoDbEntity#deleteByIdUi(java.lang.String)
	 */
	public void deleteByIdUi(String idUi) {
		final StringBuffer qual = new StringBuffer().append(DBUtils.WHERE)
				.append(DBUtils
						.generateEQTokenField(IDUINSTALACION_FIELD, idUi));
		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				DbDeleteFns.delete(conn, TABLE_NAME, qual.toString());
			}
		};
		command.execute();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see deposito.db.IUDocEnUiDepositoDbEntity#getUdocsEnUIWithAsuntoAndExpediente(java.lang.String)
	 */
	public List getUdocsEnUIWithAsuntoAndExpediente(String idUinstalacion) {

		/*
		 *
		 * SELECT ASGDUDOCENUI.IDUNIDADDOC, ASGFUNIDADDOC.NUMEXP,
		 * ASGFELEMENTOCF.TITULO FROM ASGDUDOCENUI LEFT OUTER JOIN
		 * ASGFELEMENTOCF ON ASGFELEMENTOCF.ID = ASGDUDOCENUI.IDUNIDADDOC LEFT
		 * OUTER JOIN ASGFUNIDADDOC ON ASGFUNIDADDOC.IDELEMENTOCF =
		 * ASGDUDOCENUI.IDUNIDADDOC LEFT OUTER JOIN ADVCFECHACF ADVCFECHACF1 ON
		 * ASGFUNIDADDOC.IDELEMENTOCF = ADVCFECHACF1.IDELEMENTOCF AND
		 * ADVCFECHACF1.IDCAMPO = '3' LEFT OUTER JOIN ADVCFECHACF ADVCFECHACF2
		 * ON ASGFUNIDADDOC.IDELEMENTOCF = ADVCFECHACF2.IDELEMENTOCF AND
		 * ADVCFECHACF2.IDCAMPO = '4' WHERE
		 * IDUNIDADDOC='u0000000000000000000000000042261'
		 */

		TableDef tablaFechaIni = new TableDef(FechaDBEntityImpl.TABLE_NAME,
				FechaDBEntityImpl.TABLE_NAME + "INI");
		TableDef tablaFechaFin = new TableDef(FechaDBEntityImpl.TABLE_NAME,
				FechaDBEntityImpl.TABLE_NAME + "FIN");

		DbColumnDef asuntoField = new DbColumnDef(
				"titulo",
				ElementoCuadroClasificacionDBEntityImplBase.TABLE_NAME_ELEMENTO,
				ElementoCuadroClasificacionDBEntityImplBase.TITULO_FIELD);
		DbColumnDef numExpField = new DbColumnDef("numExp",
				UnidadDocumentalDBEntityImpl.TABLE_NAME_UNIDAD_DOCUMENTAL,
				UnidadDocumentalDBEntityImpl.CAMPO_NUMERO_EXPEDIENTE);
		DbColumnDef fechaIniField = new DbColumnDef("fechaIni",
				tablaFechaIni.getAlias(), FechaDBEntityImpl.CAMPO_VALOR);
		DbColumnDef fechaFinField = new DbColumnDef("fechaFin",
				tablaFechaFin.getAlias(), FechaDBEntityImpl.CAMPO_VALOR);

		DbColumnDef idElementoFechaIniField = new DbColumnDef(tablaFechaIni,
				FechaDBEntityImpl.CAMPO_ID_ELEMENTO);
		DbColumnDef idElementoFechaFinField = new DbColumnDef(tablaFechaFin,
				FechaDBEntityImpl.CAMPO_ID_ELEMENTO);

		DbColumnDef idCampoFechaIniField = new DbColumnDef(tablaFechaIni,
				FechaDBEntityImpl.CAMPO_ID_CAMPO);
		DbColumnDef idCampoFechaFinField = new DbColumnDef(tablaFechaFin,
				FechaDBEntityImpl.CAMPO_ID_CAMPO);

		DbColumnDef[] COLS = (DbColumnDef[]) ArrayUtils.add(TABLE_COLUMNS,
				asuntoField);
		COLS = (DbColumnDef[]) ArrayUtils.add(COLS, numExpField);
		COLS = (DbColumnDef[]) ArrayUtils.add(COLS, fechaIniField);
		COLS = (DbColumnDef[]) ArrayUtils.add(COLS, fechaFinField);

		JoinDefinition joinASGFELEMENTOCF = new JoinDefinition(
				IDUNIDADDOC_FIELD,
				ElementoCuadroClasificacionDBEntityImplBase.ID_ELEMENTO_FIELD);
		JoinDefinition joinASGFUNIDADDOC = new JoinDefinition(
				IDUNIDADDOC_FIELD, UnidadDocumentalDBEntityImpl.CAMPO_ID);

		String idFechaExtremaInicial = ConfiguracionSistemaArchivoFactory
				.getConfiguracionSistemaArchivo().getConfiguracionDescripcion()
				.getFechaExtremaInicial();
		String idFechaExtremaFinal = ConfiguracionSistemaArchivoFactory
				.getConfiguracionSistemaArchivo().getConfiguracionDescripcion()
				.getFechaExtremaFinal();

		String sqlAppendFechaIni = new StringBuffer(DBUtils.AND).append(
				DBUtils.generateEQTokenField(idCampoFechaIniField,
						idFechaExtremaInicial)).toString();

		String sqlAppendFechaFin = new StringBuffer(DBUtils.AND).append(
				DBUtils.generateEQTokenField(idCampoFechaFinField,
						idFechaExtremaFinal)).toString();

		JoinDefinition joinADVCFECHAINI = new JoinDefinition(
				ElementoCuadroClasificacionDBEntityImplBase.ID_ELEMENTO_FIELD,
				idElementoFechaIniField, sqlAppendFechaIni);
		JoinDefinition joinADVCFECHAFIN = new JoinDefinition(
				ElementoCuadroClasificacionDBEntityImplBase.ID_ELEMENTO_FIELD,
				idElementoFechaFinField, sqlAppendFechaFin);

		String selectClause = new StringBuffer(DBUtils.SELECT).append(
				DbUtil.getColumnNames(COLS)).toString();

		String fromClause = new StringBuffer(DBUtils.FROM).append(
				DBUtils.generateLeftOuterJoinCondition(
						new TableDef(TABLE_NAME), new JoinDefinition[] {
								joinASGFELEMENTOCF, joinASGFUNIDADDOC,
								joinADVCFECHAINI, joinADVCFECHAFIN }))
				.toString();

		String whereClause = new StringBuffer(DBUtils.WHERE).append(
				DBUtils.generateEQTokenField(IDUINSTALACION_FIELD,
						idUinstalacion)).toString();
		String orderByClause = new StringBuffer(
				DBUtils.generateOrderBy(POSUDOCENUI_FIELD)).toString();
		StringBuffer sqlCompleta = new StringBuffer(selectClause)
				.append(fromClause).append(whereClause).append(orderByClause);

		final SigiaDbOutputRecordset rowSet = new SigiaDbOutputRecordset(COLS,
				UDocEnUiDepositoVO.class);
		return getVOS(sqlCompleta.toString(), UDocEnUiDepositoVO.class, rowSet);
	}

	public int getCountUnidadesInstalacionByIdSerie(String idSerie) {
		/*
		 * SELECT COUNT(DISTINCT ASGDUDOCENUI.IDUINSTALACION) FROM ASGDUDOCENUI
		 * LEFT OUTER JOIN ASGDUINSTALACION ON ASGDUDOCENUI.IDUINSTALACION =
		 * ASGDUINSTALACION.ID LEFT OUTER JOIN ASGFUNIDADDOC ON
		 * ASGDUDOCENUI.IDUNIDADDOC = ASGFUNIDADDOC.IDELEMENTOCF WHERE
		 * ASGFUNIDADDOC.IDSERIE = '0c47a7ae8f11100ARCHIVOPRODU0005e'
		 */

		JoinDefinition[] joins = new JoinDefinition[] {
				new JoinDefinition(IDUINSTALACION_FIELD,
						UInstalacionDepositoDBEntity.ID_FIELD),
				new JoinDefinition(IDUNIDADDOC_FIELD,
						UnidadDocumentalDBEntityImpl.CAMPO_ID) };

		String selectClause = new StringBuffer(DBUtils.SELECT).append(
				DBUtils.getCountDistinct(IDUINSTALACION_FIELD)).toString();

		String fromClause = new StringBuffer(DBUtils.FROM).append(
				DBUtils.generateLeftOuterJoinCondition(
						new TableDef(TABLE_NAME), joins)).toString();

		String whereClause = new StringBuffer(DBUtils.WHERE).append(
				DBUtils.generateEQTokenField(
						UnidadDocumentalDBEntityImpl.CAMPO_IDSERIE, idSerie))
				.toString();

		ConsultaConnectBy consulta = new ConsultaConnectBy(selectClause,
				fromClause, whereClause, null, null);

		return getVOCountGeneral(consulta);
	}

	public double getVolumenSerie(String idSerie) {

		/*
		 * SELECT COALESCE(SUM(asgdelemasig.longitud /
		 * asgdelemasig.numhuecos),0) AS VOLUMEN FROM ASGDUINSTALACION
		 * ASGDUINSTALACION LEFT OUTER JOIN asgdhueco asgdhueco ON
		 * ASGDUINSTALACION.ID=asgdhueco.iduinstalacion LEFT OUTER JOIN
		 * asgdelemasig asgdelemasig ON asgdhueco.idelemapadre=asgdelemasig.id
		 * WHERE (ASGDUINSTALACION.id IN ( SELECT DISTINCT
		 * ASGDUDOCENUI.iduinstalacion FROM ASGDUINSTALACION ASGDUINSTALACION
		 * LEFT OUTER JOIN ASGDUDOCENUI ASGDUDOCENUI ON
		 * ASGDUINSTALACION.ID=ASGDUDOCENUI.IDUINSTALACION LEFT OUTER JOIN
		 * asgfunidaddoc asgfunidaddoc ON
		 * ASGDUDOCENUI.IDUNIDADDOC=asgfunidaddoc.idelementocf WHERE
		 * asgfunidaddoc.idserie= '0c494001325be0000000000000000009' ) )
		 */

		JoinDefinition[] joinsSQ = new JoinDefinition[] {
				new JoinDefinition(UInstalacionDepositoDBEntity.ID_FIELD,
						UDocEnUiDepositoDbEntityImpl.IDUINSTALACION_FIELD),
				new JoinDefinition(
						UDocEnUiDepositoDbEntityImpl.IDUNIDADDOC_FIELD,
						UnidadDocumentalDBEntityImpl.CAMPO_ID) };

		// SUBQUERY
		String selectClauseSubQuery = new StringBuffer(DBUtils.SELECT)
				.append(DBUtils.DISTINCT)
				.append(UDocEnUiDepositoDbEntityImpl.IDUINSTALACION_FIELD
						.getQualifiedName()).toString();

		String fromClauseSubQuery = new StringBuffer(DBUtils.FROM).append(
				DBUtils.generateLeftOuterJoinCondition(new TableDef(
						UInstalacionDepositoDBEntity.TABLE_NAME), joinsSQ))
				.toString();

		String whereClauseSubQuery = new StringBuffer(DBUtils.WHERE).append(
				DBUtils.generateEQTokenField(
						UnidadDocumentalDBEntityImpl.CAMPO_IDSERIE, idSerie))
				.toString();

		ConsultaConnectBy consultaConnectBySQ = new ConsultaConnectBy(
				selectClauseSubQuery, fromClauseSubQuery, whereClauseSubQuery);

		// QUERY

		JoinDefinition[] joins = new JoinDefinition[] {
				new JoinDefinition(UInstalacionDepositoDBEntity.ID_FIELD,
						HuecoDBEntityImpl.CAMPO_IDUINSTALACION),
				new JoinDefinition(HuecoDBEntityImpl.CAMPO_IDELEMPADRE,
						new DbColumnDef(new TableDef(
								ElementoAsignableDBEntity.TABLE_NAME),
								ElementoAsignableDBEntity.CAMPO_ID)),
				new JoinDefinition(
						UInstalacionDepositoDBEntity.IDFORMATO_FIELD,
						FormatoDBEntity.CAMPO_ID) };

		String columna = new StringBuffer(new DbColumnDef(new TableDef(
				ElementoAsignableDBEntity.TABLE_NAME),
				ElementoAsignableDBEntity.CAMPO_LONGITUD).getQualifiedName())
				.append(DBUtils.OPERADOR_DIVISION)
				.append(new DbColumnDef(new TableDef(
						ElementoAsignableDBEntity.TABLE_NAME),
						ElementoAsignableDBEntity.CAMPO_NUMHUECOS)
						.getQualifiedName()).toString();

		columna = new StringBuffer(DBUtils.getNativeIfNullSintax(
				getConnection(), new DbColumnDef(new TableDef(
						FormatoDBEntity.TABLE_NAME),
						FormatoDBEntity.CAMPO_LONGITUD).getQualifiedName(),
				columna)).toString();

		String selectClause = new StringBuffer(DBUtils.SELECT).append(
				DBUtils.getNativeIfNullSintaxSUM(getConnection(), columna,
						SerieDBEntityImpl.VOLUMEN_COLUMN_NAME, "0")).toString();

		String fromClause = new StringBuffer(DBUtils.FROM).append(
				DBUtils.generateLeftOuterJoinCondition(new TableDef(
						UInstalacionDepositoDBEntity.TABLE_NAME), joins))
				.toString();

		String whereClause = new StringBuffer(DBUtils.WHERE).append(
				DBUtils.generateInTokenFieldSubQuery(
						UInstalacionDepositoDBEntity.ID_FIELD,
						consultaConnectBySQ.getSqlCompuesta())).toString();

		ConsultaConnectBy consulta = new ConsultaConnectBy(selectClause,
				fromClause, whereClause);

		return getVOSumGeneral(consulta);

	}

	private String getQualConsultaUDocsNotInUinstalacion(String[] idsElementos) {
		/*
		 * SELECT DISTINCT D.IDUINSTALACION FROM ASGDUDOCENUI D
		 *
		 * WHERE D.IDUNIDADDOC IN( <IDSELEMENTOSSELECCIONADOS> ) AND
		 * D.IDUINSTALACION IN(
		 *
		 * SELECT DISTINCT D1.IDUINSTALACION FROM ASGDUDOCENUI D1 WHERE
		 * D1.IDUNIDADDOC NOT IN( <IDSELEMENTOSELIMINADOS> ) )
		 */

		TableDef tableAux = new TableDef(TABLE_NAME, "D1");
		DbColumnDef columnaIdUinstalacion = new DbColumnDef(tableAux,
				IDUINSTALACION_FIELD);
		DbColumnDef columnaIdunidadoc = new DbColumnDef(tableAux,
				IDUNIDADDOC_FIELD);

		String subquery = new StringBuffer(DBUtils.SELECT)
				.append(columnaIdUinstalacion.getQualifiedName())
				.append(DBUtils.FROM)
				.append(tableAux.getDeclaration())
				.append(DBUtils.WHERE)
				.append(DBUtils.generateNotInTokenField(columnaIdunidadoc,
						idsElementos)).toString();

		StringBuffer qual = new StringBuffer(DBUtils.WHERE)
				.append(DBUtils.generateInTokenField(IDUNIDADDOC_FIELD,
						idsElementos))
				.append(DBUtils.AND)
				.append(DBUtils.generateInTokenFieldSubQuery(
						IDUINSTALACION_FIELD, subquery));

		return qual.toString();
	}

	public int getCountUInstalacionIncompletas(String[] idsElementos) {
		if (StringUtils.isNotEmpty(idsElementos)) {
			return getVOCount(
					getQualConsultaUDocsNotInUinstalacion(idsElementos),
					TABLE_NAME);
		} else {
			return 0;
		}
	}

	public List getUInstalacionIncompletas(String[] idsElementos) {
		if (StringUtils.isNotEmpty(idsElementos)) {

			DbColumnDef[] COLUMNAS = new DbColumnDef[] { new DbColumnDef("id",
					IDUINSTALACION_FIELD) };
			return getDistinctVOS(
					getQualConsultaUDocsNotInUinstalacion(idsElementos),
					TABLE_NAME, COLUMNAS, MutableStringId.class);
		} else {
			return null;
		}
	}
}
