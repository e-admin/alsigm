package transferencias.db;

import fondos.db.ElementoCuadroClasificacionDBEntityImplBase;
import fondos.db.UnidadDocumentalDBEntityImpl;
import fondos.db.oracle.ElementoCuadroClasificacionDBEntityImpl;
import fondos.model.ElementoCuadroClasificacion;
import fondos.model.TipoNivelCF;
import gcontrol.db.ArchivoDbEntityImpl;
import gcontrol.db.CAOrganoDbEntityImpl;
import gcontrol.db.UsuarioDBEntityImpl;
import gcontrol.vos.UsuarioVO;
import ieci.core.db.DbColumnDef;
import ieci.core.db.DbConnection;
import ieci.core.db.DbDataType;
import ieci.core.db.DbDeleteFns;
import ieci.core.db.DbInsertFns;
import ieci.core.db.DbSelectFns;
import ieci.core.db.DbUtil;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import transferencias.ReservaPrevision;
import transferencias.TipoTransferencia;
import transferencias.model.EstadoREntrega;
import transferencias.vos.BusquedaRelacionesVO;
import transferencias.vos.RelacionEntregaVO;
import transferencias.vos.UDocElectronicaVO;
import xml.config.ConfiguracionSistemaArchivoFactory;

import common.CodigoTransferenciaUtils;
import common.Constants;
import common.db.DBCommand;
import common.db.DBEntity;
import common.db.DBUtils;
import common.db.DbDataSource;
import common.db.JoinDefinition;
import common.db.SigiaDbInputRecord;
import common.db.TableDef;
import common.exceptions.TooManyResultsException;
import common.lang.MutableBoolean;
import common.pagination.PageInfo;
import common.util.ArrayUtils;
import common.util.CustomDateFormat;
import common.util.StringUtils;
import common.util.TypeConverter;
import common.vos.ConsultaConnectBy;

import deposito.db.FormatoDBEntity;
import deposito.db.UDocEnUiDepositoDbEntityImpl;
import deposito.db.UInstalacionDepositoDBEntity;
import deposito.vos.UDocEnUiDepositoVO;
import descripcion.db.FechaDBEntityImpl;
import docelectronicos.db.UnidadDocumentalElectronicaDBEntityImpl;

/**
 * Clase con los metodos encargados de recuperar y almacenar datos referentes a
 * relaciones de entrega
 */
public abstract class RelacionEntregaDBEntityBaseImpl extends DBEntity
		implements IRelacionEntregaDBEntity {

	/** Nombre de la tabla donde se almacenan las relaciones de entrega */
	public static final String TABLE_NAME = "ASGTRENTREGA";

	public static final String ID = "id";
	public static final String TIPOTRANSFERENCIA = "tipotransferencia";
	public static final String DETALLEPREVISION = "iddetprevision";
	public static final String IDORGREMITENTE = "idorganoremitente";
	public static final String IDARCHIVORECEPTOR = "idarchivoreceptor";
	public static final String ANO = "ano";
	public static final String IDFONDODESTINO = "idfondodestino";
	public static final String CODSISTPRODUCTOR = "codsistproductor";
	public static final String NOMBRESISTPRODUCTOR = "nombresistproductor";
	public static final String IDPROC = "idprocedimiento";
	public static final String IDSERIEDESTINO = "idseriedestino";
	public static final String ESTADO = "estado";
	public static final String FECHAESTADO = "fechaestado";
	public static final String FECHARECEPCION = "fecharecepcion";
	public static final String IDUSRGESTORREM = "idusrgestorrem";
	public static final String IDUSRGESTORARCHIVOREC = "idusrgestorarchivorec";
	public static final String IDDEPOSITO = "iddeposito";
	public static final String UIENDEPOSITO = "uiendeposito";
	public static final String DEVOLUCIONUI = "devolucionui";
	public static final String REGENTRADA = "regentrada";
	public static final String OBSERVACIONES = "observaciones";
	public static final String IDPREVISION = "idprevision";
	public static final String ORDEN = "orden";
	public static final String IDFORMATO = "idformatoui";
	public static final String TIPODOCUMENTAL = "tipodocumental";
	public static final String RESERVADEPOSITO = "reservadeposito";
	public static final String IDELMTODRESERVA = "idelmtodreserva";
	public static final String IDDESCRORGPRODUCTOR = "iddescrorgproductor";
	public static final String IDTIPOELMTODRESERVA = "idtipoelmtodreserva";
	public static final String IDARCHIVOREMITENTE = "idarchivoremitente";
	public static final String IDFONDOORIGEN = "idfondoorigen";
	public static final String IDSERIEORIGEN = "idserieorigen";
	public static final String CORRECCIONENARCHIVO = "correccionenarchivo";
	public static final String IDNIVELDOCUMENTAL = "idniveldocumental";
	public static final String SINDOCSFISICOS = "sindocsfisicos";
	public static final String IDFICHA = "idficha";
	public static final String CONREENCAJADO = "conreencajado";
	public static final String IDFORMATORE = "idformatore";

	public static final DbColumnDef CAMPO_ID = new DbColumnDef(null,
			TABLE_NAME, ID, DbDataType.SHORT_TEXT, 64, false);

	public static final DbColumnDef CAMPO_TIPOTRANSFERENCIA = new DbColumnDef(
			null, TABLE_NAME, TIPOTRANSFERENCIA, DbDataType.LONG_INTEGER, false);

	public static final DbColumnDef CAMPO_DETALLEPREVISION = new DbColumnDef(
			null, TABLE_NAME, DETALLEPREVISION, DbDataType.SHORT_TEXT, false);

	public static final DbColumnDef CAMPO_IDORGREMITENTE = new DbColumnDef(
			null, TABLE_NAME, IDORGREMITENTE, DbDataType.SHORT_TEXT, 32, false);

	public static final DbColumnDef CAMPO_IDARCHIVORECEPTOR = new DbColumnDef(
			null, TABLE_NAME, IDARCHIVORECEPTOR, DbDataType.SHORT_TEXT, 32,
			false);

	public static final DbColumnDef CAMPO_ANO = new DbColumnDef(null,
			TABLE_NAME, ANO, DbDataType.SHORT_TEXT, false);

	public static final DbColumnDef CAMPO_IDFONDODESTINO = new DbColumnDef(
			null, TABLE_NAME, IDFONDODESTINO, DbDataType.SHORT_TEXT, 32, false);

	public static final DbColumnDef CAMPO_CODSISTPRODUCTOR = new DbColumnDef(
			null, TABLE_NAME, CODSISTPRODUCTOR, DbDataType.SHORT_TEXT, 64,
			false);

	public static final DbColumnDef CAMPO_NOMBRESISTPRODUCTOR = new DbColumnDef(
			null, TABLE_NAME, NOMBRESISTPRODUCTOR, DbDataType.SHORT_TEXT, 64,
			false);

	public static final DbColumnDef CAMPO_IDPROC = new DbColumnDef(null,
			TABLE_NAME, IDPROC, DbDataType.SHORT_TEXT, 254, false);

	public static final DbColumnDef CAMPO_IDSERIEDESTINO = new DbColumnDef(
			null, TABLE_NAME, IDSERIEDESTINO, DbDataType.SHORT_TEXT, 254, false);

	public static final DbColumnDef CAMPO_ESTADO = new DbColumnDef(null,
			TABLE_NAME, ESTADO, DbDataType.LONG_INTEGER, false);

	public static final DbColumnDef CAMPO_FECHAESTADO = new DbColumnDef(null,
			TABLE_NAME, FECHAESTADO, DbDataType.DATE_TIME, false);

	public static final DbColumnDef CAMPO_FECHARECEPCION = new DbColumnDef(
			null, TABLE_NAME, FECHARECEPCION, DbDataType.DATE_TIME, false);

	public static final DbColumnDef CAMPO_IDUSRGESTORREM = new DbColumnDef(
			null, TABLE_NAME, IDUSRGESTORREM, DbDataType.SHORT_TEXT, 32, false);

	public static final DbColumnDef CAMPO_IDUSRGESTORARCHIVOREC = new DbColumnDef(
			null, TABLE_NAME, IDUSRGESTORARCHIVOREC, DbDataType.SHORT_TEXT, 32,
			false);

	public static final DbColumnDef CAMPO_IDDEPOSITO = new DbColumnDef(null,
			TABLE_NAME, IDDEPOSITO, DbDataType.SHORT_TEXT, 32, false);

	public static final DbColumnDef CAMPO_UIENDEPOSITO = new DbColumnDef(null,
			TABLE_NAME, UIENDEPOSITO, DbDataType.SHORT_TEXT, 1, false);

	public static final DbColumnDef CAMPO_DEVOLUCIONUI = new DbColumnDef(null,
			TABLE_NAME, DEVOLUCIONUI, DbDataType.SHORT_TEXT, 1, false);

	public static final DbColumnDef CAMPO_REGENTRADA = new DbColumnDef(null,
			TABLE_NAME, REGENTRADA, DbDataType.SHORT_TEXT, 64, false);

	public static final DbColumnDef CAMPO_OBSERVACIONES = new DbColumnDef(null,
			TABLE_NAME, OBSERVACIONES, DbDataType.SHORT_TEXT, 254, false);

	public static final DbColumnDef CAMPO_IDPREVISION = new DbColumnDef(null,
			TABLE_NAME, IDPREVISION, DbDataType.SHORT_TEXT, 64, false);

	public static final DbColumnDef CAMPO_ORDEN = new DbColumnDef(null,
			TABLE_NAME, ORDEN, DbDataType.LONG_INTEGER, 64, false);

	public static final DbColumnDef CAMPO_IDFORMATO = new DbColumnDef(null,
			TABLE_NAME, IDFORMATO, DbDataType.SHORT_TEXT, 64, false);

	public static final DbColumnDef CAMPO_TIPODOCUMENTAL = new DbColumnDef(
			null, TABLE_NAME, TIPODOCUMENTAL, DbDataType.SHORT_TEXT, 254, false);

	public static final DbColumnDef CAMPO_RESERVADEPOSITO = new DbColumnDef(
			null, TABLE_NAME, RESERVADEPOSITO, DbDataType.LONG_INTEGER, 64,
			false);

	public static final DbColumnDef CAMPO_IDELMTODRESERVA = new DbColumnDef(
			null, TABLE_NAME, IDELMTODRESERVA, DbDataType.SHORT_TEXT, 64, false);

	public static final DbColumnDef CAMPO_IDDESCRORGPRODUCTOR = new DbColumnDef(
			null, TABLE_NAME, IDDESCRORGPRODUCTOR, DbDataType.SHORT_TEXT, 32,
			false);

	public static final DbColumnDef CAMPO_IDTIPOELMTODRESERVA = new DbColumnDef(
			null, TABLE_NAME, IDTIPOELMTODRESERVA, DbDataType.SHORT_TEXT, 32,
			false);

	public static final DbColumnDef CAMPO_IDARCHIVOREMITENTE = new DbColumnDef(
			null, TABLE_NAME, IDARCHIVOREMITENTE, DbDataType.SHORT_TEXT, 32,
			false);

	public static final DbColumnDef CAMPO_IDFONDOORIGEN = new DbColumnDef(null,
			TABLE_NAME, IDFONDOORIGEN, DbDataType.SHORT_TEXT, 32, false);

	public static final DbColumnDef CAMPO_IDSERIEORIGEN = new DbColumnDef(null,
			TABLE_NAME, IDSERIEORIGEN, DbDataType.SHORT_TEXT, 32, false);

	public static final DbColumnDef CAMPO_CORRECCIONENARCHIVO = new DbColumnDef(
			null, TABLE_NAME, CORRECCIONENARCHIVO, DbDataType.SHORT_TEXT, 1,
			false);

	public static final DbColumnDef CAMPO_IDNIVELDOCUMENTAL = new DbColumnDef(
			null, TABLE_NAME, IDNIVELDOCUMENTAL, DbDataType.SHORT_TEXT, 32,
			false);

	public static final DbColumnDef CAMPO_SINDOCSFISICOS = new DbColumnDef(
			null, TABLE_NAME, SINDOCSFISICOS, DbDataType.SHORT_TEXT, 1, false);

	public static final DbColumnDef CAMPO_IDFICHA = new DbColumnDef(null,
			TABLE_NAME, IDFICHA, DbDataType.SHORT_TEXT, 32, true);

	/**
	 * Identificador de Relacion de Entrega Con Reencajado
	 */
	public static final DbColumnDef CAMPO_CONREENCAJADO = new DbColumnDef(null,
			TABLE_NAME, CONREENCAJADO, DbDataType.SHORT_TEXT, 1, true);

	/**
	 * Identificador del Formato del Reencajado
	 */
	public static final DbColumnDef CAMPO_IDFORMATORE = new DbColumnDef(null,
			TABLE_NAME, IDFORMATORE, DbDataType.SHORT_TEXT, 32, true);

	public static final DbColumnDef[] COLUMN_DEFINITIONS = { CAMPO_ID,
			CAMPO_TIPOTRANSFERENCIA, CAMPO_DETALLEPREVISION,
			CAMPO_IDORGREMITENTE, CAMPO_IDARCHIVORECEPTOR, CAMPO_ANO,
			CAMPO_IDFONDODESTINO, CAMPO_CODSISTPRODUCTOR, CAMPO_IDPROC,
			CAMPO_IDSERIEDESTINO, CAMPO_ESTADO, CAMPO_FECHAESTADO,
			CAMPO_FECHARECEPCION, CAMPO_IDUSRGESTORREM,
			CAMPO_IDUSRGESTORARCHIVOREC, CAMPO_IDDEPOSITO, CAMPO_UIENDEPOSITO,
			CAMPO_DEVOLUCIONUI, CAMPO_REGENTRADA, CAMPO_OBSERVACIONES,
			CAMPO_IDPREVISION, CAMPO_ORDEN, CAMPO_IDFORMATO,
			CAMPO_TIPODOCUMENTAL, CAMPO_NOMBRESISTPRODUCTOR,
			CAMPO_RESERVADEPOSITO, CAMPO_IDELMTODRESERVA,
			CAMPO_IDDESCRORGPRODUCTOR, CAMPO_IDTIPOELMTODRESERVA,
			CAMPO_IDARCHIVOREMITENTE, CAMPO_IDFONDOORIGEN, CAMPO_IDSERIEORIGEN,
			CAMPO_CORRECCIONENARCHIVO, CAMPO_IDNIVELDOCUMENTAL,
			CAMPO_SINDOCSFISICOS, CAMPO_IDFICHA, CAMPO_CONREENCAJADO,
			CAMPO_IDFORMATORE };

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

	public RelacionEntregaDBEntityBaseImpl(DbDataSource dataSource) {
		super(dataSource);
	}

	public RelacionEntregaDBEntityBaseImpl(DbConnection conn) {
		super(conn);
	}

	/**
	 * Comprueba si existen relaciones creadas para una prevision de
	 * transferencia
	 *
	 * @param idPrevision
	 *            Identificador de la prevision
	 * @return true si se han creado relaciones de entrega sobre la prevision,
	 *         false en caso de no existir ninguna relacion para la prevision
	 */
	public boolean previsionTieneRelaciones(final String idPrevision) {
		final MutableBoolean tieneRelacionesAsociadas = new MutableBoolean(
				false);
		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				StringBuilder qual = new StringBuilder(DBUtils.WHERE)
						.append(DBUtils.generateEQTokenField(CAMPO_IDPREVISION,
								idPrevision));
				tieneRelacionesAsociadas.setValue(DbSelectFns.rowExists(conn,
						TABLE_NAME, qual.toString()));
			}
		};
		command.execute();
		return tieneRelacionesAsociadas.getValue();
	}

	/**
	 * Comprueba si se han creado relaciones de entrega con el formato indicado
	 * sobre una una linea de detalle de una previsión
	 *
	 * @param idDetallePrevision
	 *            Identificador de línea de detalle de previsión
	 * @param tipoDocumental
	 *            Tipo documental. Puede ser nulo.
	 * @return true si se han creado relaciones de entrega sobre la línea de
	 *         detalle y false en caso contrario
	 */
	public boolean detallePrevisionTieneRelaciones(
			final String idDetallePrevision, final String tipoDocumental) {
		final MutableBoolean existenRelaciones = new MutableBoolean(false);
		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				StringBuilder qual = new StringBuilder("WHERE").append(DBUtils
						.generateEQTokenField(CAMPO_DETALLEPREVISION,
								idDetallePrevision));
				if (tipoDocumental != null)
					qual.append(" AND ").append(
							DBUtils.generateEQTokenField(CAMPO_TIPODOCUMENTAL,
									tipoDocumental));
				existenRelaciones.setValue(DbSelectFns.rowExists(conn,
						TABLE_NAME, qual.toString()));
			}
		};
		command.execute();
		return existenRelaciones.getValue();
	}

	private List getRelaciones(String qual) {
		return getVOS(qual, TABLE_NAME, COLUMN_DEFINITIONS,
				RelacionEntregaVO.class);
	}

	private RelacionEntregaVO getRelacion(String qual) {
		return (RelacionEntregaVO) getVO(qual, TABLE_NAME, COLUMN_DEFINITIONS,
				RelacionEntregaVO.class);
	}

	/**
	 * Permite obtener el sql para las relaciones a reservar
	 *
	 * @return Sql
	 */
	private String getRelacionesAReservarSql(String[] archivosCustodia) {
		int[] estados = new int[] { EstadoREntrega.RECIBIDA.getIdentificador(),
				EstadoREntrega.RECIBIDA.getIdentificador(),
				EstadoREntrega.CON_ERRORES_COTEJO.getIdentificador(),
				EstadoREntrega.CORREGIDA_ERRORES.getIdentificador() };
		StringBuilder qual = new StringBuilder("WHERE")
				.append(DBUtils.generateEQTokenField(CAMPO_RESERVADEPOSITO,
						ReservaPrevision.PENDIENTE.getIdentificador()))
				.append(" AND ")
				.append(DBUtils.generateORTokens(CAMPO_ESTADO, estados))
				.append(DBUtils.AND)
				.append(DBUtils.generateInTokenField(CAMPO_IDARCHIVORECEPTOR,
						archivosCustodia));

		return qual.toString();
	}

	public int getCountRelacionesAReservar(String[] archivosCustodia) {
		String qual = getRelacionesAReservarSql(archivosCustodia);
		return getVOCount(qual, TABLE_NAME);
	}

	public Collection getRelacionesAReservar(String[] archivosCustodia) {
		String qual = getRelacionesAReservarSql(archivosCustodia);
		return getRelaciones(qual);
	}

	private String getRelacionesAUbicarSql(String[] archivosCustodia) {
		int[] estados = new int[] { EstadoREntrega.SIGNATURIZADA
				.getIdentificador() };

		StringBuilder qual = new StringBuilder(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(CAMPO_UIENDEPOSITO,
						Constants.FALSE_STRING))
				.append(DBUtils.AND)
				.append(DBUtils.generateORTokens(CAMPO_IDARCHIVORECEPTOR,
						archivosCustodia))
				.append(DBUtils.AND)
				.append(DBUtils.generateNEQTokenField(CAMPO_SINDOCSFISICOS,
						Constants.TRUE_STRING)).append(DBUtils.AND)
				.append(DBUtils.generateORTokens(CAMPO_ESTADO, estados));
		return qual.toString();
	}

	public int getCountRelacionesAUbicar(String[] archivosCustodia) {
		if (archivosCustodia == null)
			return 0;
		String qual = getRelacionesAUbicarSql(archivosCustodia);
		return getVOCount(qual, TABLE_NAME);
	}

	public Collection getRelacionesAUbicar(String[] archivosCustodia) {
		if (archivosCustodia == null)
			return null;
		String qual = getRelacionesAUbicarSql(archivosCustodia);
		return getRelaciones(qual);
	}

	/**
	 * Recupera las relaciones enviadas por alguno de los órganos indicados
	 *
	 * @param idOrgano
	 *            Conjundo de identificadores de órgano remitente
	 * @return Lista de relaciones de entrega {@link RelacionEntregaVO}
	 */
	public Collection getRelacionesXOrganoRemitente(String[] idOrgano) {
		StringBuilder qual = new StringBuilder("WHERE").append(DBUtils
				.generateORTokens(CAMPO_IDORGREMITENTE, idOrgano));

		return getRelaciones(qual.toString());
	}

	/**
	 * Genera el SQL para recuperar de la base de datos las relaciones que
	 * tienen como gestor en archivo receptor el indicado, se encuentran en
	 * alguno de los estados solicitados y pertenecen al año indicado
	 *
	 * @param idUser
	 *            Identificador de usuario
	 * @param estados
	 *            Conjunto de estados de relacion de entrega. Puede ser nulo
	 * @param ano
	 *            Año. Puede ser nulo
	 * @return Sql
	 *
	 */
	private String getRelacionesXGestorEnArchivoSql(String idUser,
			int[] estados, String ano) {
		StringBuilder qual = new StringBuilder("WHERE").append(DBUtils
				.generateEQTokenField(CAMPO_IDUSRGESTORARCHIVOREC, idUser));
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
	 * transferencias.db.IRelacionEntregaDBEntity#getCountRelacionesXGestorEnArchivo
	 * (java.lang.String, int[], java.lang.String)
	 */
	public int getCountRelacionesXGestorEnArchivo(String idUser, int[] estados,
			String ano) {
		String qual = getRelacionesXGestorEnArchivoSql(idUser, estados, ano);
		return getVOCount(qual, TABLE_NAME);
	}

	/**
	 * Recupera de la base de datos las relaciones que tienen como gestor en
	 * archivo receptor el indicado, se encuentran en alguno de los estados
	 * solicitados y pertenecen al año indicado
	 *
	 * @param idUser
	 *            Identificador de usuario
	 * @param estados
	 *            Conjunto de estados de relacion de entrega. Puede ser nulo
	 * @param ano
	 *            Año. Puede ser nulo
	 * @return Lista de relaciones de entrega {@link RelacionEntregaVO}
	 */
	public List getRelacionesXGestorEnArchivo(String idUser, int[] estados,
			String ano) {
		String qual = getRelacionesXGestorEnArchivoSql(idUser, estados, ano);
		return getRelaciones(qual);
	}

	/*
	 * (sin Javadoc)
	 *
	 * @see transferencias.db.IRelacionEntregaDBEntity#
	 * getCountRelacionesXGestorEnOrganoRemitente(java.lang.String, int[],
	 * java.lang.String, boolean)
	 */
	public int getCountRelacionesXGestorEnOrganoRemitente(String idUser,
			int[] estados, String ano, boolean excluirIngresosDirectos) {
		String qual = getRelacionesXGestorEnOrganoRemitenteSql(idUser, estados,
				ano, false, excluirIngresosDirectos);
		return getVOCount(qual, TABLE_NAME);
	}

	/*
	 * (sin Javadoc)
	 *
	 * @see transferencias.db.IRelacionEntregaDBEntity#
	 * getRelacionesXGestorEnOrganoRemitente(java.lang.String, int[],
	 * java.lang.String, boolean)
	 */
	public List getRelacionesXGestorEnOrganoRemitente(String idUser,
			int[] estados, String ano, boolean excluirIngresosDirectos) {
		String qual = getRelacionesXGestorEnOrganoRemitenteSql(idUser, estados,
				ano, false, excluirIngresosDirectos);
		return getRelaciones(qual.toString());
	}

	/**
	 * Devuelve el sql para recuperar de la base de datos las relaciones que
	 * tienen como gestor en organo remitente el indicado, se encuentran en
	 * alguno de los estados solicitados, pertenecen al año indicado y son de
	 * determinados tipos
	 *
	 * @param idUser
	 *            Identificador de usuario
	 * @param estados
	 *            Conjunto de estados de relacion de entrega. Puede ser nulo
	 * @param ano
	 *            Año. Puede ser nulo
	 * @param tipos
	 *            Tipos de transferencia
	 * @return Sql
	 */
	private String getRelacionesXGestorYTipoEnOrganoRemitenteSql(String idUser,
			int[] estados, String ano, int[] tipos) {
		StringBuilder qual = new StringBuilder("WHERE ").append(DBUtils
				.generateEQTokenField(CAMPO_IDUSRGESTORREM, idUser));
		if (estados != null)
			qual.append(" AND ").append(
					DBUtils.generateORTokens(CAMPO_ESTADO, estados));
		if (ano != null)
			qual.append(" AND ").append(
					DBUtils.generateEQTokenField(CAMPO_ANO, ano));
		if (!ArrayUtils.isEmpty(tipos)) {
			qual.append(" AND ")
					.append(DBUtils.generateInTokenField(
							CAMPO_TIPOTRANSFERENCIA, tipos));
		}
		return qual.toString();
	}

	/*
	 * (sin Javadoc)
	 *
	 * @see transferencias.db.IRelacionEntregaDBEntity#
	 * getCountRelacionesXGestorYTipoEnOrganoRemitente(java.lang.String, int[],
	 * java.lang.String, int[])
	 */
	public int getCountRelacionesXGestorYTipoEnOrganoRemitente(String idUser,
			int[] estados, String ano, int[] tipos) {
		String qual = getRelacionesXGestorYTipoEnOrganoRemitenteSql(idUser,
				estados, ano, tipos);
		return getVOCount(qual, TABLE_NAME);
	}

	/*
	 * (sin Javadoc)
	 *
	 * @see transferencias.db.IRelacionEntregaDBEntity#
	 * getRelacionesXGestorYTipoEnOrganoRemitente(java.lang.String, int[],
	 * java.lang.String, java.lang.String[])
	 */
	public List getRelacionesXGestorYTipoEnOrganoRemitente(String idUser,
			int[] estados, String ano, int[] tipos) {
		String qual = getRelacionesXGestorYTipoEnOrganoRemitenteSql(idUser,
				estados, ano, tipos);
		return getRelaciones(qual);
	}

	/**
	 * Devuelve el sql para recuperar de la base de datos las relaciones que
	 * tienen como gestor en organo remitente el indicado, se encuentran en
	 * alguno de los estados solicitados y pertenecen al año indicado
	 *
	 * @param idUser
	 *            Identificador de usuario
	 * @param estados
	 *            Conjunto de estados de relacion de entrega. Puede ser nulo
	 * @param ano
	 *            Año. Puede ser nulo
	 * @param excluirConErroresYCorreccionEnArchivo
	 *            Indicador de si se excluyen las que están con errores y están
	 *            marcadas para correción en archivo.
	 * @param excluirIngresosDirectos
	 *            Indicador de exclusión de los ingresos directos
	 * @return Numero de relaciones de entrega
	 */
	private String getRelacionesXGestorEnOrganoRemitenteSql(String idUser,
			int[] estados, String ano,
			boolean excluirConErroresYCorreccionEnArchivo,
			boolean excluirIngresosDirectos) {
		StringBuilder qual = new StringBuilder(DBUtils.WHERE).append(DBUtils
				.generateEQTokenField(CAMPO_IDUSRGESTORREM, idUser));
		if (estados != null)
			qual.append(DBUtils.AND).append(
					DBUtils.generateORTokens(CAMPO_ESTADO, estados));
		if (ano != null)
			qual.append(DBUtils.AND).append(
					DBUtils.generateEQTokenField(CAMPO_ANO, ano));

		if (excluirConErroresYCorreccionEnArchivo) {
			qual.append(DBUtils.AND).append(
					DBUtils.generateEQTokenField(CAMPO_CORRECCIONENARCHIVO,
							Constants.FALSE_STRING));
		}
		if (excluirIngresosDirectos) {
			qual.append(DBUtils.AND).append(
					DBUtils.generateNEQTokenField(CAMPO_TIPOTRANSFERENCIA,
							TipoTransferencia.INGRESO_DIRECTO
									.getIdentificador()));
		}
		return qual.toString();
	}

	/*
	 * (sin Javadoc)
	 *
	 * @see transferencias.db.IRelacionEntregaDBEntity#
	 * getCountRelacionesXGestorEnOrganoRemitente(java.lang.String, int[],
	 * java.lang.String, boolean, boolean)
	 */
	public int getCountRelacionesXGestorEnOrganoRemitente(String idUser,
			int[] estados, String ano,
			boolean excluirConErroresYCorreccionEnArchivo,
			boolean excluirIngresosDirectos) {
		String qual = getRelacionesXGestorEnOrganoRemitenteSql(idUser, estados,
				ano, excluirConErroresYCorreccionEnArchivo,
				excluirIngresosDirectos);
		return getVOCount(qual, TABLE_NAME);
	}

	/*
	 * (sin Javadoc)
	 *
	 * @see transferencias.db.IRelacionEntregaDBEntity#
	 * getRelacionesXGestorEnOrganoRemitente(java.lang.String, int[],
	 * java.lang.String, boolean, boolean)
	 */
	public List getRelacionesXGestorEnOrganoRemitente(String idUser,
			int[] estados, String ano,
			boolean excluirConErroresYCorreccionEnArchivo,
			boolean excluirIngresosDirectos) {
		String qual = getRelacionesXGestorEnOrganoRemitenteSql(idUser, estados,
				ano, excluirConErroresYCorreccionEnArchivo,
				excluirIngresosDirectos);

		return getRelaciones(qual.toString());
	}

	public List getRelacionesXGestorYTipoYArchivoEnOrganoRemitente(
			String idUser, int[] estados, String ano, int tipo, String idArchivo) {
		StringBuilder qual = new StringBuilder("WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_IDUSRGESTORREM,
						idUser))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(CAMPO_TIPOTRANSFERENCIA,
						tipo))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(CAMPO_IDARCHIVORECEPTOR,
						idArchivo));
		if (estados != null)
			qual.append(" AND ").append(
					DBUtils.generateORTokens(CAMPO_ESTADO, estados));
		if (ano != null)
			qual.append(" AND ").append(
					DBUtils.generateEQTokenField(CAMPO_ANO, ano));
		return getRelaciones(qual.toString());
	}

	public Collection getRelacionesXIds(String[] ids) {
		StringBuilder qual = new StringBuilder("WHERE").append(DBUtils
				.generateORTokens(CAMPO_ID, ids));
		return getRelaciones(qual.toString());
	}

	/**
	 * Genera el sql para obtener la relaciones destinadas a un conjundo de
	 * archivos, que se encuentren en los estados indicados y que correspondan
	 * al año que se especifica
	 *
	 * @param idArchReceptor
	 *            Lista de identificadores de archivos
	 * @param estados
	 *            Lista de estados de relacion de entrega
	 * @param ano
	 *            Año
	 * @return Sql
	 */
	private String getRelacionesXArchivoReceptorSql(String[] idArchReceptor,
			int[] estados, String ano) {
		StringBuilder qual = new StringBuilder(" WHERE 1=1 ");
		if (idArchReceptor != null && idArchReceptor.length > 0)
			qual.append(" AND ").append(
					DBUtils.generateORTokens(CAMPO_IDARCHIVORECEPTOR,
							idArchReceptor));
		if (estados != null && estados.length > 0)
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
	 * transferencias.db.IRelacionEntregaDBEntity#getCountRelacionesXArchivoReceptor
	 * (java.lang.String[], int[], java.lang.String)
	 */
	public int getCountRelacionesXArchivoReceptor(String[] idArchReceptor,
			int[] estados, String ano) {
		String qual = getRelacionesXArchivoReceptorSql(idArchReceptor, estados,
				ano);
		return getVOCount(qual, TABLE_NAME);
	}

	/**
	 * Recupera la relaciones destinadas a un conjundo de archivos, que se
	 * encuentren en los estados indicados y que correspondan al año que se
	 * especifica
	 *
	 * @param idArchReceptor
	 *            Lista de identificadores de archivos
	 * @param estados
	 *            Lista de estados de relacion de entrega
	 * @param ano
	 *            Año
	 * @return Lista de relaciones de entrega {@link RelacionEntregaVO}
	 */
	public List getRelacionesXArchivoReceptor(String[] idArchReceptor,
			int[] estados, String ano) {
		StringBuilder qual = new StringBuilder(getRelacionesXArchivoReceptorSql(
				idArchReceptor, estados, ano));
		qual.append(" ORDER BY ").append(FECHAESTADO).append(" DESC ");
		return getRelaciones(qual.toString());
	}

	public List getRelacionesXPrevision(String idPrevision, String idDetalle) {
		return getRelacionesXPrevisionYEstado(idPrevision, idDetalle, null);
	}

	public List getRelacionesXPrevisionYEstado(String idPrevision,
			String idDetalle, int[] estados) {
		StringBuilder qual = new StringBuilder("WHERE").append(DBUtils
				.generateEQTokenField(CAMPO_IDPREVISION, idPrevision));
		if (estados != null && estados.length > 0) {
			qual.append(" AND ").append(
					DBUtils.generateInTokenField(CAMPO_ESTADO, estados));
		}

		if (idDetalle != null)
			qual.append(" and ").append(
					DBUtils.generateEQTokenField(CAMPO_DETALLEPREVISION,
							idDetalle));
		return getRelaciones(qual.toString());
	}

	/**
	 * Obtiene las relaciones de entrega asociadas a un fondo.
	 *
	 * @param idFondo
	 *            Identificador del fondo.
	 * @param pageInfo
	 *            Información de la paginación.
	 * @return Relaciones de entrega.
	 * @throws TooManyResultsException
	 *             si el número de resultados excede el máximo.
	 */
	public List getRelacionesXFondo(String idFondo, PageInfo pageInfo)
			throws TooManyResultsException {
		Map pairs = new HashMap();
		pairs.put(TABLE_NAME, COLUMN_DEFINITIONS);
		pairs.put(
				ElementoCuadroClasificacionDBEntityImpl.TABLE_NAME_ELEMENTO,
				new DbColumnDef[] {
						new DbColumnDef(
								"codigoSerie",
								ElementoCuadroClasificacionDBEntityImpl.CODIGO_FIELD),
						new DbColumnDef(
								"tituloSerie",
								ElementoCuadroClasificacionDBEntityImpl.TITULO_FIELD) });

		StringBuilder qual = new StringBuilder()
				.append("WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_IDFONDODESTINO,
						idFondo))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(CAMPO_ESTADO,
						EstadoREntrega.VALIDADA.getIdentificador()))
				.append(" AND ")
				.append(DBUtils
						.generateJoinCondition(
								CAMPO_IDSERIEDESTINO,
								ElementoCuadroClasificacionDBEntityImpl.ID_ELEMENTO_FIELD));

		if (pageInfo != null) {
			Map criteriosOrdenacion = new HashMap();
			criteriosOrdenacion.put("codigo", new DbColumnDef[] { CAMPO_ANO,
					CAMPO_ORDEN });
			criteriosOrdenacion.put("tipoTransferencia",
					CAMPO_TIPOTRANSFERENCIA);
			criteriosOrdenacion.put("estado", CAMPO_ESTADO);
			criteriosOrdenacion.put("fechaestado", CAMPO_FECHAESTADO);
			criteriosOrdenacion.put("procedimiento", CAMPO_IDPROC);
			criteriosOrdenacion.put("serie",
					ElementoCuadroClasificacionDBEntityImpl.CODIGO_FIELD);

			return getVOS(qual.toString(),
					pageInfo.getOrderBy(criteriosOrdenacion), pairs,
					RelacionEntregaVO.class, pageInfo);
		} else {
			qual.append(" ORDER BY ").append(CAMPO_ANO.getQualifiedName())
					.append(" DESC,").append(CAMPO_ORDEN.getQualifiedName())
					.append(" DESC");

			return getVOS(qual.toString(), pairs, RelacionEntregaVO.class);
		}
	}

	/**
	 * Obtiene las relaciones de entrega asociadas a un clasificador de series.
	 *
	 * @param idClfSeries
	 *            Identificador del clasificador de series.
	 * @param pageInfo
	 *            Información de la paginación.
	 * @return Relaciones de entrega.
	 * @throws TooManyResultsException
	 *             si el número de resultados excede el máximo.
	 */
	public abstract List getRelacionesXClasificadorSeries(String idClfSeries,
			PageInfo pageInfo) throws TooManyResultsException;

	/**
	 * Obtiene las relaciones de entrega asociadas a una serie.
	 *
	 * @param idSerie
	 *            Identificador de la serie.
	 * @param pageInfo
	 *            Información de la paginación.
	 * @return Relaciones de entrega.
	 * @throws TooManyResultsException
	 *             si el número de resultados excede el máximo.
	 */
	public List getRelacionesXSerie(String idSerie, PageInfo pageInfo)
			throws TooManyResultsException {
		Map pairs = new HashMap();
		pairs.put(TABLE_NAME, COLUMN_DEFINITIONS);
		pairs.put(
				ElementoCuadroClasificacionDBEntityImpl.TABLE_NAME_ELEMENTO,
				new DbColumnDef[] {
						new DbColumnDef(
								"codigoSerie",
								ElementoCuadroClasificacionDBEntityImpl.CODIGO_FIELD),
						new DbColumnDef(
								"tituloSerie",
								ElementoCuadroClasificacionDBEntityImpl.TITULO_FIELD) });

		StringBuilder qual = new StringBuilder()
				.append("WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_IDSERIEDESTINO,
						idSerie))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(CAMPO_ESTADO,
						EstadoREntrega.VALIDADA.getIdentificador()))
				.append(" AND ")
				.append(DBUtils
						.generateJoinCondition(
								CAMPO_IDSERIEDESTINO,
								ElementoCuadroClasificacionDBEntityImpl.ID_ELEMENTO_FIELD));

		if (pageInfo != null) {
			Map criteriosOrdenacion = new HashMap();
			criteriosOrdenacion.put("codigo", new DbColumnDef[] { CAMPO_ANO,
					CAMPO_ORDEN });
			criteriosOrdenacion.put("tipoTransferencia",
					CAMPO_TIPOTRANSFERENCIA);
			criteriosOrdenacion.put("estado", CAMPO_ESTADO);
			criteriosOrdenacion.put("fechaestado", CAMPO_FECHAESTADO);
			criteriosOrdenacion.put("procedimiento", CAMPO_IDPROC);
			criteriosOrdenacion.put("serie",
					ElementoCuadroClasificacionDBEntityImpl.CODIGO_FIELD);

			return getVOS(qual.toString(),
					pageInfo.getOrderBy(criteriosOrdenacion), pairs,
					RelacionEntregaVO.class, pageInfo);
		} else {
			qual.append(" ORDER BY ").append(CAMPO_ANO.getQualifiedName())
					.append(" DESC,").append(CAMPO_ORDEN.getQualifiedName())
					.append(" DESC");

			return getVOS(qual.toString(), pairs, RelacionEntregaVO.class);
		}
	}

	/**
	 * Obtiene las relaciones de entrega asociadas a una unidad documental.
	 *
	 * @param idUdoc
	 *            Identificador de la unidad documental.
	 * @param pageInfo
	 *            Información de la paginación.
	 * @return Relaciones de entrega.
	 * @throws TooManyResultsException
	 *             si el número de resultados excede el máximo.
	 */
	public List getRelacionesXUdoc(String idUdoc, PageInfo pageInfo)
			throws TooManyResultsException {

		DbColumnDef columnaCodigoSerie = new DbColumnDef("codigoSerie",
				ElementoCuadroClasificacionDBEntityImpl.CODIGO_FIELD);
		DbColumnDef columnaTituloSerie = new DbColumnDef("tituloSerie",
				ElementoCuadroClasificacionDBEntityImpl.TITULO_FIELD);

		Class classObjectVO = RelacionEntregaVO.class;

		// Union 1
		// TABLAS
		String TABLE_NAME1 = TABLE_NAME + ","
				+ UDocEnUiDepositoDbEntityImpl.TABLE_NAME + ","
				+ UDocRelacionDBEntityImpl.TABLE_NAME + ","
				+ ElementoCuadroClasificacionDBEntityImpl.TABLE_NAME_ELEMENTO;

		// COLUMNAS
		DbColumnDef[] COLS_DEFS1 = COLUMN_DEFINITIONS;
		COLS_DEFS1 = (DbColumnDef[]) ArrayUtils.addAll(COLS_DEFS1,
				new DbColumnDef[] { columnaCodigoSerie, columnaTituloSerie });

		// WHERE
		String qual1 = new StringBuilder()
				.append("WHERE ")
				.append(DBUtils.generateEQTokenField(
						UDocEnUiDepositoDbEntityImpl.IDUNIDADDOC_FIELD, idUdoc))
				.append(" AND ")
				.append(DBUtils.generateJoinCondition(
						UDocEnUiDepositoDbEntityImpl.IDUDOCRE_FIELD,
						UDocRelacionDBEntityImpl.ID_FIELD))
				.append(" AND ")
				.append(DBUtils.generateJoinCondition(
						UDocRelacionDBEntityImpl.ID_RELACION_FIELD, CAMPO_ID))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(CAMPO_ESTADO,
						EstadoREntrega.VALIDADA.getIdentificador()))
				.append(" AND ")
				.append(DBUtils
						.generateJoinCondition(
								CAMPO_IDSERIEDESTINO,
								ElementoCuadroClasificacionDBEntityImpl.ID_ELEMENTO_FIELD))
				.toString();

		// UNION 2
		String TABLE_NAME2 = TABLE_NAME + ", "
				+ UDocEnUiDepositoDbEntityImpl.TABLE_NAME + ", "
				+ UDocRelacionDBEntityImpl.TABLE_NAME + ", "
				+ ElementoCuadroClasificacionDBEntityImpl.TABLE_NAME_ELEMENTO
				+ ", " + UnidadInstalacionReeaDBEntityImpl.TABLE_NAME;

		DbColumnDef[] COLS_DEFS2 = COLUMN_DEFINITIONS;
		COLS_DEFS2 = (DbColumnDef[]) ArrayUtils.addAll(COLS_DEFS2,
				new DbColumnDef[] { columnaCodigoSerie, columnaTituloSerie });

		String qual2 = new StringBuilder(DBUtils.WHERE)
				// ASGDUDOCENUI.IDUNIDADDOC= idUdoc AND
				.append(DBUtils.generateEQTokenField(
						UDocEnUiDepositoDbEntityImpl.IDUNIDADDOC_FIELD, idUdoc))
				.append(DBUtils.AND)
				// asgtrentrega.estado = 7 AND
				.append(DBUtils.generateEQTokenField(CAMPO_ESTADO,
						EstadoREntrega.VALIDADA.getIdentificador()))
				.append(DBUtils.AND)
				// ASGDUDOCENUI.IDUNIDADDOC = ASGFELEMENTOCF.ID AND
				.append(DBUtils
						.generateJoinCondition(
								UDocEnUiDepositoDbEntityImpl.IDUNIDADDOC_FIELD,
								ElementoCuadroClasificacionDBEntityImpl.ID_ELEMENTO_FIELD))
				.append(DBUtils.AND)

				// ASGDUDOCENUI.IDUINSTALACION =
				// ASGTUINSTALACIONREEA.IDUIDEPOSITO AND
				.append(DBUtils.generateJoinCondition(
						UDocEnUiDepositoDbEntityImpl.IDUINSTALACION_FIELD,
						UnidadInstalacionReeaDBEntityImpl.ID_UIDEPOSITO_FIELD))
				// ASGTRENTREGA.ID = ASGTUINSTALACIONREEA.IDRELENTREGA
				.append(DBUtils.AND)
				.append(DBUtils
						.generateJoinCondition(
								CAMPO_ID,
								UnidadInstalacionReeaDBEntityImpl.ID_RELACIONENTREGA_FIELD))
				.toString();

		String orderBy = new StringBuilder(" ORDER BY ")
				.append(CAMPO_ANO.getName()).append(" DESC,")
				.append(CAMPO_ORDEN.getName()).append(" DESC").toString();

		if (pageInfo != null) {
			Map criteriosOrdenacion = new HashMap();
			criteriosOrdenacion.put("codigo", new DbColumnDef[] { CAMPO_ANO,
					CAMPO_ORDEN });
			criteriosOrdenacion.put("tipoTransferencia",
					CAMPO_TIPOTRANSFERENCIA);
			criteriosOrdenacion.put("estado", CAMPO_ESTADO);
			criteriosOrdenacion.put("fechaestado", CAMPO_FECHAESTADO);
			criteriosOrdenacion.put("procedimiento", CAMPO_IDPROC);
			criteriosOrdenacion.put("serie",
					ElementoCuadroClasificacionDBEntityImpl.CODIGO_FIELD);

			return getUnionVOS(qual1, TABLE_NAME1, COLS_DEFS1, classObjectVO,
					qual2, TABLE_NAME2, COLS_DEFS2, orderBy);
		} else {
			return getUnionVOS(qual1, TABLE_NAME1, COLS_DEFS1, classObjectVO,
					qual2, TABLE_NAME2, COLS_DEFS2, orderBy);
		}
	}

	/**
	 * Obtiene las relaciones de entrega asociadas a una división de una
	 * fracción de serie.
	 *
	 * @param idFSEnREntrega
	 *            Identificador de la fracción de serie en la relación de
	 *            entrega de la que provino
	 * @param pageInfo
	 *            Información de la paginación.
	 * @return Relaciones de entrega.
	 * @throws TooManyResultsException
	 *             si el número de resultados excede el máximo.
	 */
	public List getRelacionesXIdFSEnREntrega(String idFSEnREntrega,
			PageInfo pageInfo) throws TooManyResultsException {

		DbColumnDef columnaCodigoSerie = new DbColumnDef("codigoSerie",
				ElementoCuadroClasificacionDBEntityImpl.CODIGO_FIELD);
		DbColumnDef columnaTituloSerie = new DbColumnDef("tituloSerie",
				ElementoCuadroClasificacionDBEntityImpl.TITULO_FIELD);

		Class classObjectVO = RelacionEntregaVO.class;

		// Union 1
		// TABLAS
		String TABLE_NAME1 = TABLE_NAME + ","
				+ UDocRelacionDBEntityImpl.TABLE_NAME + ","
				+ ElementoCuadroClasificacionDBEntityImpl.TABLE_NAME_ELEMENTO;

		// COLUMNAS
		DbColumnDef[] COLS_DEFS1 = COLUMN_DEFINITIONS;
		COLS_DEFS1 = (DbColumnDef[]) ArrayUtils.addAll(COLS_DEFS1,
				new DbColumnDef[] { columnaCodigoSerie, columnaTituloSerie });

		// WHERE
		String qual1 = new StringBuilder()
				.append("WHERE ")
				.append(DBUtils.generateEQTokenField(
						UDocRelacionDBEntityImpl.ID_FIELD, idFSEnREntrega))
				.append(DBUtils.AND)
				.append(DBUtils.generateJoinCondition(
						UDocRelacionDBEntityImpl.ID_RELACION_FIELD, CAMPO_ID))
				.append(DBUtils.AND)
				.append(DBUtils.generateEQTokenField(CAMPO_ESTADO,
						EstadoREntrega.VALIDADA.getIdentificador()))
				.append(DBUtils.AND)
				.append(DBUtils
						.generateJoinCondition(
								CAMPO_IDSERIEDESTINO,
								ElementoCuadroClasificacionDBEntityImpl.ID_ELEMENTO_FIELD))
				.toString();

		return getVOS(qual1, TABLE_NAME1, COLS_DEFS1, classObjectVO);
	}

	public RelacionEntregaVO getRelacionXId(String idRelacion) {
		StringBuilder qual = new StringBuilder("WHERE").append(DBUtils
				.generateEQTokenField(CAMPO_ID, idRelacion));
		return getRelacion(qual.toString());
	}

	public void insertRelacion(final RelacionEntregaVO relacion) {
		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				relacion.setId(getGuid(relacion.getId()));
				SigiaDbInputRecord inputRecord = new SigiaDbInputRecord(
						COLUMN_DEFINITIONS, relacion);
				DbInsertFns.insert(conn, TABLE_NAME, COLUM_NAMES_LIST,
						inputRecord);
			}
		};
		command.execute();
	}

	public void updateRelacionEntrega(RelacionEntregaVO relacionVO) {
		StringBuilder qual = new StringBuilder("WHERE ").append(DBUtils
				.generateEQTokenField(CAMPO_ID, relacionVO.getId()));

		updateVO(qual.toString(), TABLE_NAME, COLUMN_DEFINITIONS, relacionVO);
	}

	/**
	 * Actualiza el gestor en el archivo al que están asignadas una relaciones
	 * de entrega
	 *
	 * @param relaciones
	 *            Conjunto de relaciones a las que se le va a asignar gestor
	 * @param idNewUser
	 *            Identificador de usuario
	 */
	public void updateUsrGestorArchivo(String[] relaciones, String idNewUser) {
		StringBuilder qual = new StringBuilder("WHERE ").append(DBUtils
				.generateORTokens(CAMPO_ID, relaciones));
		Map colToUpdate = Collections.singletonMap(CAMPO_IDUSRGESTORARCHIVOREC,
				idNewUser);
		updateFields(qual.toString(), colToUpdate, TABLE_NAME);
	}

	/**
	 * Actualiza la ubicación del fondo físico en la que será depositada la
	 * documentación incluida en la relación entrega al concluir el proceso de
	 * transferencia.
	 *
	 * @param idRelacion
	 *            Identificador de relación de entrega
	 * @param uiEndeposito
	 *            <b>true</b> si la documentación se encuenta ya depositada en
	 *            la ubicación y <b>false</b> en caso contrario
	 * @param idUbicacion
	 *            Identificador de ubicación del fondo físico gestionado por el
	 *            sistema
	 */
	public void updateUbicacion(String idRelacion, boolean uiEndeposito,
			String idUbicacion) {
		StringBuilder qual = new StringBuilder("WHERE ").append(DBUtils
				.generateEQTokenField(CAMPO_ID, idRelacion));
		Map colsToUpdate = new HashMap();
		colsToUpdate.put(CAMPO_UIENDEPOSITO,
				DBUtils.getStringValue(uiEndeposito));
		colsToUpdate.put(CAMPO_IDDEPOSITO, idUbicacion);
		// colsToUpdate.put(CAMPO_RESERVADEPOSITO, new
		// Integer(ReservaPrevision.NO_RESERVADA.getIdentificador()));
		// colsToUpdate.put(CAMPO_IDELMTODRESERVA, null);
		updateFields(qual.toString(), colsToUpdate, TABLE_NAME);
	}

	/**
	 * Actualiza la ubicación del fondo físico en la que será depositada la
	 * documentación incluida en la relación entrega al concluir el proceso de
	 * transferencia.
	 *
	 * @param idRelacion
	 *            Identificador de relación de entrega
	 * @param idUbicacion
	 *            Identificador de ubicación del fondo físico gestionado por el
	 *            sistema
	 */
	public void updateUbicacion(String idRelacion, String idUbicacion) {
		StringBuilder qual = new StringBuilder(DBUtils.WHERE).append(DBUtils
				.generateEQTokenField(CAMPO_ID, idRelacion));
		Map colsToUpdate = new HashMap();
		colsToUpdate.put(CAMPO_IDDEPOSITO, idUbicacion);

		updateFields(qual.toString(), colsToUpdate, TABLE_NAME);
	}

	/**
	 * Actualiza el gestor en órgano remitente al que están asignadas un
	 * conjunto de relaciones
	 *
	 * @param idsRelaciones
	 *            Identificadores de relaciones de entrega
	 * @param idUsuario
	 *            Identificador de usuario
	 */
	public void updateUsrGestorRem(String[] idsRelaciones, String idUsuario) {
		StringBuilder qual = new StringBuilder("WHERE ").append(DBUtils
				.generateORTokens(CAMPO_ID, idsRelaciones));
		Map colToUpdate = Collections.singletonMap(CAMPO_IDUSRGESTORREM,
				idUsuario);
		updateFields(qual.toString(), colToUpdate, TABLE_NAME);
	}

	/**
	 * Actualiza el estado de una relación de entrega
	 *
	 * @param idRelacion
	 *            Identificador de relación de entrega
	 * @param estado
	 *            Estado a establecer a las relaciones de entrega
	 * @param fechaEstado
	 *            Fecha con la que se lleva a cabo la asignación de estado
	 * @param devolucionCajas
	 *            Estado de devolución de cajas. Puede ser nulo con lo que el
	 *            campo no será modificado
	 */
	public void updateEstado(String idRelacion, int estado, Date fechaEstado,
			Boolean devolucionCajas) {
		StringBuilder qual = new StringBuilder("WHERE ").append(DBUtils
				.generateEQTokenField(CAMPO_ID, idRelacion));
		Map colsToUpdate = new HashMap();
		colsToUpdate.put(CAMPO_ESTADO, String.valueOf(estado));
		colsToUpdate.put(CAMPO_FECHAESTADO, fechaEstado);
		if (devolucionCajas != null)
			colsToUpdate.put(CAMPO_DEVOLUCIONUI,
					DBUtils.getStringValue(devolucionCajas.booleanValue()));

		updateFields(qual.toString(), colsToUpdate, TABLE_NAME);
	}

	/**
	 * Actualiza el estado de una relación de entrega
	 *
	 * @param idRelacion
	 *            Identificador de relación de entrega
	 * @param estado
	 *            Estado a establecer a las relaciones de entrega
	 * @param fechaEstado
	 *            Fecha con la que se lleva a cabo la asignación de estado
	 * @param devolucionCajas
	 *            Estado de devolución de cajas. Puede ser nulo con lo que el
	 *            campo no será modificado
	 * @param sindocsfisicos
	 *            Indicador de Sin Documentos Físicos.
	 */
	public void updateEstado(String idRelacion, int estado, Date fechaEstado,
			Boolean devolucionCajas, Boolean sindocsfisicos) {
		StringBuilder qual = new StringBuilder("WHERE ").append(DBUtils
				.generateEQTokenField(CAMPO_ID, idRelacion));
		Map colsToUpdate = new HashMap();
		colsToUpdate.put(CAMPO_ESTADO, String.valueOf(estado));
		colsToUpdate.put(CAMPO_FECHAESTADO, fechaEstado);

		if (devolucionCajas != null)
			colsToUpdate.put(CAMPO_DEVOLUCIONUI,
					DBUtils.getStringValue(devolucionCajas.booleanValue()));

		if (devolucionCajas != null)
			colsToUpdate.put(CAMPO_DEVOLUCIONUI,
					DBUtils.getStringValue(devolucionCajas.booleanValue()));

		if (sindocsfisicos != null)
			colsToUpdate.put(CAMPO_SINDOCSFISICOS,
					DBUtils.getStringValue(sindocsfisicos.booleanValue()));

		updateFields(qual.toString(), colsToUpdate, TABLE_NAME);
	}

	/*
	 * (sin Javadoc)
	 *
	 * @see
	 * transferencias.db.IRelacionEntregaDBEntity#updateIndicadorCorreccionEnArchivo
	 * (boolean)
	 */
	public void updateIndicadorCorreccionEnArchivo(String idRelacion,
			String correccionenarchivo) {

		StringBuilder qual = new StringBuilder(DBUtils.WHERE).append(DBUtils
				.generateEQTokenField(CAMPO_ID, idRelacion));
		Map colsToUpdate = new HashMap();

		colsToUpdate.put(CAMPO_CORRECCIONENARCHIVO, correccionenarchivo);

		updateFields(qual.toString(), colsToUpdate, TABLE_NAME);
	}

	/**
	 * Actualiza id de descriptor del órgano de productor de una relación de
	 * entrega extraordinaria
	 *
	 * @param idRelacion
	 *            Identificador de relación de entrega
	 * @param idDescrOrganoProductor
	 */
	public void updateIdDescrorgproductor(String idRelacion,
			String idDescrorgproductor) {
		StringBuilder qual = new StringBuilder("WHERE ").append(DBUtils
				.generateEQTokenField(CAMPO_ID, idRelacion));
		Map colsToUpdate = new HashMap();
		colsToUpdate.put(CAMPO_IDDESCRORGPRODUCTOR, idDescrorgproductor);

		updateFields(qual.toString(), colsToUpdate, TABLE_NAME);
	}

	/**
	 * Borra de la base de datos una relación de entrega
	 *
	 * @param idRelacion
	 *            Identificador de la relación de entrega a borrar
	 */
	public void deleteRelacion(final String idRelacion) {
		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				StringBuilder qual = new StringBuilder("WHERE ").append(DBUtils
						.generateEQTokenField(CAMPO_ID, idRelacion));
				DbDeleteFns
						.delete(getConnection(), TABLE_NAME, qual.toString());
			}
		};
		command.execute();
	}

	/**
	 * Actualiza la reserva de espacio para una relación de entrega
	 *
	 * @param idRelacionEntrega
	 *            Identificador de la relación de entrega
	 * @param estadoReserva
	 *            Valor a establecer como estado de la reserva para la relación
	 * @param depositoReserva
	 *            Identificador del elemento del fondo físico en el que se
	 *            efectua la reserva
	 */
	public int updateEstadoReserva(String idRelacionEntrega, int estadoReserva,
			String idElementoReserva, String idTipoElementoReserva) {
		StringBuilder qual = new StringBuilder("WHERE ").append(DBUtils
				.generateEQTokenField(CAMPO_ID, idRelacionEntrega));
		Map colsToUpdate = new HashMap();
		colsToUpdate.put(CAMPO_RESERVADEPOSITO, String.valueOf(estadoReserva));
		colsToUpdate.put(CAMPO_IDELMTODRESERVA, idElementoReserva);
		colsToUpdate.put(CAMPO_IDTIPOELMTODRESERVA, idTipoElementoReserva);
		return updateFields(qual.toString(), colsToUpdate, TABLE_NAME);
	}

	/**
	 * Recupera las relaciones de entrega destinadas a un fondo y que se
	 * encuentren en alguno de los estados que se indican
	 *
	 * @param idFondo
	 *            Identificador de fondo en el cuadro de clasificación de fondos
	 *            documentales. Puede ser nulo
	 * @param estados
	 *            Conjunto de estados de relación de entrega. Puede ser nulo
	 * @return Lista de relaciones que verifican las condiciones
	 *         {@link RelacionEntregaVO}
	 */
	public Collection getRelacionXFondo(String idFondo, int[] estados) {
		StringBuilder qual = new StringBuilder("WHERE 1=1 ");
		if (idFondo != null)
			qual.append(" AND ")
					.append(DBUtils.generateEQTokenField(CAMPO_IDFONDODESTINO,
							idFondo));
		if (estados != null)
			qual.append(" AND (")
					.append(DBUtils.generateORTokens(CAMPO_ESTADO, estados))
					.append(")");

		return getRelaciones(qual.toString());
	}

	/**
	 * Recupera las relaciones de entrega destinadas a ser ubicadas en una
	 * ubicacion
	 *
	 * @param idUbicacion
	 *            Identificador de ubicación
	 * @param estadosRelacion
	 *            Cojunto de estados de relacion. Puede ser nulo.
	 * @param formato
	 *            Identificador de formato. Puede ser nulo
	 * @param uisEnDeposito
	 *            <b>true</b> para obtener relaciones cuyas unidades de
	 *            instalación se encuentren ya ubicadas en el deposito y
	 *            <b>false</b> para obtener relaciones no ubicadas
	 * @return Relaciones de entrga {@link RelacionEntregaVO}
	 */
	public List getRelacionXUbicacion(String idUbicacion,
			int[] estadosRelacion, String formato, boolean uisEnDeposito) {
		StringBuilder qual = new StringBuilder("WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_IDDEPOSITO,
						idUbicacion))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(CAMPO_UIENDEPOSITO,
						TypeConverter.toString(uisEnDeposito)));
		if (formato != null)
			qual.append(" AND ").append(
					DBUtils.generateEQTokenField(CAMPO_IDFORMATO, formato));
		if (estadosRelacion != null)
			qual.append(" AND ")
					.append(DBUtils.generateInTokenField(CAMPO_ESTADO,
							estadosRelacion));

		return getRelaciones(qual.toString());
	}

	/**
	 * Busca las relaciones que cumplan unos criterios concretos.
	 *
	 * @param vo
	 *            Criterios de la búsqueda.
	 * @return Lista de relaciones.
	 * @throws TooManyResultsException
	 *             si el número de resultados es excesivo.
	 */
	public List getRelaciones(BusquedaRelacionesVO vo)
			throws TooManyResultsException {

		final DbColumnDef[] COLS_BUSQUEDA = (DbColumnDef[]) ArrayUtils
				.addAll(COLUMN_DEFINITIONS,
						new DbColumnDef[] {
								new DbColumnDef("nombreFormato",
										FormatoDBEntity.CAMPO_NOMBRE),
								new DbColumnDef("nombreorgano",
										CAOrganoDbEntityImpl.CAMPO_NOMBRE_LARGO),
								new DbColumnDef(
										"codigoSerie",
										ElementoCuadroClasificacionDBEntityImpl.CODIGO_FIELD),
								new DbColumnDef(
										"tituloSerie",
										ElementoCuadroClasificacionDBEntityImpl.TITULO_FIELD),
								UsuarioDBEntityImpl.CAMPO_NOMBRE_USUARIO,
								UsuarioDBEntityImpl.CAMPO_APELLIDOS_USUARIO,
								new DbColumnDef(
										PrevisionDBEntityImpl.ARCHIVORECEPTOR_COLUMN_NAME,
										ArchivoDbEntityImpl.ID_FIELD),
								PrevisionDBEntityImpl.CAMPO_ANO_PREVISION,
								PrevisionDBEntityImpl.CAMPO_ORDEN_PREVISION });

		StringBuilder selectClause = new StringBuilder(DBUtils.SELECT)
				.append(DbUtil.getColumnNames(COLS_BUSQUEDA));

		// Map pairs = new HashMap();

		// LEFT OUTER JOIN ASGFELEMENTOCF ON
		// ASGTRENTREGA.IDSERIEDESTINO=ASGFELEMENTOCF.ID
		JoinDefinition joinElementocf = new JoinDefinition(
				CAMPO_IDSERIEDESTINO,
				ElementoCuadroClasificacionDBEntityImplBase.ID_ELEMENTO_FIELD);

		// LEFT OUTER JOIN ASCAUSUARIO ON
		// ASGTRENTREGA.IDUSRGESTORREM=ASCAUSUARIO.ID
		JoinDefinition joinUsuarios = new JoinDefinition(CAMPO_IDUSRGESTORREM,
				UsuarioDBEntityImpl.CAMPO_ID);

		// LEFT OUTER JOIN ASCAORGANO ON
		// ASGTRENTREGA.IDORGANOREMITENTE=ASCAORGANO.ID
		JoinDefinition joinOrgano = new JoinDefinition(CAMPO_IDORGREMITENTE,
				CAOrganoDbEntityImpl.CAMPO_ID);

		// LEFT OUTER JOIN AGARCHIVO ON
		// ASGTRENTREGA.IDARCHIVORECEPTOR=AGARCHIVO.ID
		JoinDefinition joinArchivo = new JoinDefinition(
				CAMPO_IDARCHIVORECEPTOR, ArchivoDbEntityImpl.ID_FIELD);

		// LEFT OUTER JOIN AGFORMATO ON ASGTRENTREGA.IDFORMATOUI=AGFORMATO.ID
		JoinDefinition joinFormato = new JoinDefinition(CAMPO_IDFORMATO,
				FormatoDBEntity.CAMPO_ID);

		// LEFT OUTER JOIN ASGTPREVISION ON ASGTRENTREGA.IDPREVISION =
		// ASGTPREVISION.ID
		JoinDefinition joinPrevision = new JoinDefinition(CAMPO_IDPREVISION,
				PrevisionDBEntityImpl.CAMPO_ID);

		StringBuilder fromClause = new StringBuilder(DBUtils.FROM).append(DBUtils
				.generateLeftOuterJoinCondition(new TableDef(TABLE_NAME),
						new JoinDefinition[] { joinElementocf, joinUsuarios,
								joinOrgano, joinArchivo, joinFormato,
								joinPrevision }));

		StringBuilder qual = new StringBuilder()

		.append(getFiltroArchivosOrganos(vo));

		qual.append(getFiltroIdGestor(vo.getIdGestor(), qual.toString()));

		// Tipos de transferencias
		if (!ArrayUtils.isEmpty(vo.getTipos())) {
			qual.append(DBUtils.getAnd(qual.toString()));
			qual.append(DBUtils.generateInTokenField(CAMPO_TIPOTRANSFERENCIA,
					vo.getTipos()));
		}

		// Órgano remitente
		if (StringUtils.isNotBlank(vo.getOrgano())) {
			qual.append(DBUtils.getAnd(qual.toString()));
			qual.append(DBUtils.generateLikeTokenField(
					CAOrganoDbEntityImpl.CAMPO_NOMBRE_LARGO, vo.getOrgano(),
					true));
		}

		// Formato
		if (StringUtils.isNotBlank(vo.getIdFormato())) {
			qual.append(DBUtils.getAnd(qual.toString()));
			qual.append(DBUtils.generateEQTokenField(CAMPO_IDFORMATO,
					vo.getIdFormato()));
		}

		// Organo Productor
		if (StringUtils.isNotEmpty(vo.getIdsProductores())) {
			qual.append(DBUtils.getAnd(qual.toString()));
			qual.append(DBUtils.generateInTokenField(CAMPO_IDDESCRORGPRODUCTOR,
					vo.getIdsProductores()));
		}

		// Observaciones
		if (StringUtils.isNotEmpty(vo.getObservaciones())) {
			qual.append(DBUtils.getAnd(qual.toString()));
			qual.append(DBUtils.generateLikeTokenField(CAMPO_OBSERVACIONES,
					vo.getObservaciones(), true));
		}

		String concatSymbol = DBUtils.getNativeConcatSyntax(getConnection());
		// Gestor de órgano remitente
		if (StringUtils.isNotBlank(vo.getGestorOficina())) {
			qual.append(DBUtils.getAnd(qual.toString()));
			qual.append(" UPPER(")
					.append(UsuarioDBEntityImpl.CAMPO_NOMBRE_USUARIO
							.getQualifiedName())
					.append(concatSymbol)
					.append("' '")
					.append(concatSymbol)
					.append(UsuarioDBEntityImpl.CAMPO_APELLIDOS_USUARIO
							.getQualifiedName()).append(") LIKE '%")
					.append(vo.getGestorOficina().toUpperCase()).append("%'");
		}
		// Gestor de archivo
		if (StringUtils.isNotBlank(vo.getGestorArchivo())) {
			qual.append(DBUtils.getAnd(qual.toString()));
			qual.append(CAMPO_IDUSRGESTORARCHIVOREC.getQualifiedName())
					.append(" IN (SELECT ")
					.append(UsuarioDBEntityImpl.CAMPO_ID.getQualifiedName())
					.append(" FROM ")
					.append(UsuarioDBEntityImpl.TABLE_NAME)
					.append(" WHERE UPPER(")
					.append(UsuarioDBEntityImpl.CAMPO_NOMBRE.getQualifiedName())
					.append(concatSymbol)
					.append("' '")
					.append(concatSymbol)
					.append(DBUtils.getNativeIfNullSintax(getConnection(),
							UsuarioDBEntityImpl.CAMPO_APELLIDOS
									.getQualifiedName(), "''"))
					.append(") LIKE '%")
					.append(vo.getGestorArchivo().toUpperCase()).append("%')");
		}

		// Año
		if (StringUtils.isNotBlank(vo.getAnio())) {
			qual.append(DBUtils.getAnd(qual.toString()));
			qual.append(DBUtils.generateEQTokenField(CAMPO_ANO, vo.getAnio()));
		}
		// Estados
		if (!ArrayUtils.isEmpty(vo.getEstados())) {
			qual.append(DBUtils.getAnd(qual.toString()));
			qual.append(DBUtils.generateInTokenField(CAMPO_ESTADO,
					vo.getEstados()));
		}
		// Código
		if (StringUtils.isNotBlank(vo.getCodigo())) {
			qual.append(DBUtils.getAnd(qual.toString()));
			qual.append(
					CodigoTransferenciaUtils
							.textSqlCodigoTransferenciaRelacionEntrega(
									CAMPO_ANO.getQualifiedName(),
									CAMPO_ORDEN.getQualifiedName(),
									concatSymbol, getConnection()))
					.append(" LIKE '%").append(vo.getCodigo()).append("%'");
		}

		// Fechas
		if (vo.getFechaInicio() != null) {
			qual.append(DBUtils.getAnd(qual.toString()));
			qual.append(CAMPO_FECHARECEPCION.getQualifiedName())
					.append(">=")
					.append(DBUtils.getNativeToDateSyntax(getConnection(),
							vo.getFechaInicio(),
							CustomDateFormat.SDF_YYYYMMDD_HHMMSS));
		}

		if (vo.getFechaFin() != null) {
			qual.append(DBUtils.getAnd(qual.toString()));
			qual.append(CAMPO_FECHARECEPCION.getQualifiedName())
					.append("<=")
					.append(DBUtils.getNativeToDateSyntax(getConnection(),
							vo.getFechaFin(),
							CustomDateFormat.SDF_YYYYMMDD_HHMMSS));
		}

		// Fechas de Estado
		if (vo.getFechaEstadoInicio() != null) {
			qual.append(DBUtils.getAnd(qual.toString()));
			qual.append(CAMPO_FECHAESTADO.getQualifiedName())
					.append(">=")
					.append(DBUtils.getNativeToDateSyntax(getConnection(),
							vo.getFechaEstadoInicio(),
							CustomDateFormat.SDF_YYYYMMDD_HHMMSS));
		}

		if (vo.getFechaEstadoFin() != null) {
			qual.append(DBUtils.getAnd(qual.toString()));
			qual.append(CAMPO_FECHAESTADO.getQualifiedName())
					.append("<=")
					.append(DBUtils.getNativeToDateSyntax(getConnection(),
							vo.getFechaEstadoFin(),
							CustomDateFormat.SDF_YYYYMMDD_HHMMSS));
		}

		// Serie
		if (StringUtils.isNotBlank(vo.getSerie())) {
			qual.append(DBUtils.getAnd(qual.toString()));
			qual.append(DBUtils.generateContainsTokenField(getConnection(),
					ElementoCuadroClasificacionDBEntityImpl.TITULO_FIELD,
					ElementoCuadroClasificacionDBEntityImpl.IDXTITULO_FIELD, vo
							.getSerie().toUpperCase()));

		}

		// Código de la serie
		if (StringUtils.isNotBlank(vo.getCodigoSerie())) {
			qual.append(DBUtils.getAnd(qual.toString()));
			qual.append(DBUtils.generateLikeTokenField(
					ElementoCuadroClasificacionDBEntityImpl.CODIGO_FIELD,
					vo.getCodigoSerie()));
		}
		// Procedimiento
		if (StringUtils.isNotBlank(vo.getProcedimiento())) {
			qual.append(DBUtils.getAnd(qual.toString()));
			qual.append(DBUtils.generateLikeTokenField(CAMPO_IDPROC, vo
					.getProcedimiento().toUpperCase()));
		}

		qual.insert(0, DBUtils.WHERE);

		StringBuilder orderByClause = new StringBuilder();
		// Order by
		if (vo.getPageInfo() != null) {
			Map criteriosOrdenacion = new HashMap();
			criteriosOrdenacion.put("codigo", new DbColumnDef[] { CAMPO_ANO,
					CAMPO_ORDEN });
			criteriosOrdenacion.put("codigoPrevision", new DbColumnDef[] {
					PrevisionDBEntityImpl.CAMPO_ANO_PREVISION,
					PrevisionDBEntityImpl.CAMPO_ORDEN_PREVISION });
			criteriosOrdenacion.put("usuario", new DbColumnDef[] {
					UsuarioDBEntityImpl.CAMPO_APELLIDOS_USUARIO,
					UsuarioDBEntityImpl.CAMPO_NOMBRE_USUARIO });
			criteriosOrdenacion.put("tipoTransferencia",
					CAMPO_TIPOTRANSFERENCIA);
			criteriosOrdenacion.put("estado", CAMPO_ESTADO);
			criteriosOrdenacion.put("fechaestado", CAMPO_FECHAESTADO);
			criteriosOrdenacion.put("organo",
					CAOrganoDbEntityImpl.CAMPO_NOMBRE_LARGO);
			criteriosOrdenacion.put("serie", new DbColumnDef[] {
					ElementoCuadroClasificacionDBEntityImpl.CODIGO_FIELD,
					ElementoCuadroClasificacionDBEntityImpl.TITULO_FIELD });
			criteriosOrdenacion.put("procedimiento", CAMPO_IDPROC);

			orderByClause.append(vo.getPageInfo().getOrderBy(
					criteriosOrdenacion));
		} else {
			orderByClause.append(DBUtils.ORDER_BY)
					.append(CAMPO_ANO.getQualifiedName()).append(DBUtils.DESC)
					.append(Constants.COMMA)
					.append(CAMPO_ORDEN.getQualifiedName())
					.append(DBUtils.DESC);
		}

		final ConsultaConnectBy sql = new ConsultaConnectBy(
				selectClause.toString(), fromClause.toString(),
				qual.toString(), null, orderByClause.toString());

		return getVOS(COLS_BUSQUEDA, sql, RelacionEntregaVO.class);
	}

	/**
	 * Añade el filtro por archivos y órganos.
	 *
	 * @param vo
	 *            Información de la consulta.
	 * @return Condiciones SQL.
	 */
	private String getFiltroArchivosOrganos(BusquedaRelacionesVO vo) {
		StringBuilder qual = new StringBuilder();

		if (!ArrayUtils.isEmpty(vo.getArchivosReceptores())
				|| !ArrayUtils.isEmpty(vo.getOrganosUsuario())
				|| !StringUtils.isEmpty(vo.getIdGestorArchivo())) {
			boolean empty = true;
			qual.append(" (");

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

			// Archivos Receptores
			if (!StringUtils.isEmpty(vo.getIdGestorArchivo())) {
				if (!empty)
					qual.append(" OR ");
				qual.append(DBUtils.generateEQTokenField(
						CAMPO_IDUSRGESTORARCHIVOREC, vo.getIdGestorArchivo()));
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
	private String getFiltroIdGestor(String idGestor, String consulta) {
		StringBuilder qual = new StringBuilder();

		if (!StringUtils.isEmpty(idGestor)) {

			qual.append(DBUtils.getAnd(consulta));
			qual.append("((");
			qual.append(
					DBUtils.generateEQTokenField(CAMPO_ESTADO,
							EstadoREntrega.ABIERTA.getIdentificador()))
					.append(" AND ")
					.append(DBUtils.generateEQTokenField(CAMPO_IDUSRGESTORREM,
							idGestor))
					.append(") OR (")
					.append(DBUtils.generateNEQTokenField(CAMPO_ESTADO,
							EstadoREntrega.ABIERTA.getIdentificador()))
					.append("))");
		}

		return qual.toString();
	}

	/**
	 * Obtiene la lista de gestores en archivo con relaciones de entrega.
	 *
	 * @return Lista de Gestores ({@link UsuarioVO}).
	 */
	public List getGestoresArchivoConRelaciones() {
		int[] estados = new int[] { EstadoREntrega.RECIBIDA.getIdentificador(),
				EstadoREntrega.SIGNATURIZADA.getIdentificador(),
				EstadoREntrega.CON_ERRORES_COTEJO.getIdentificador(),
				EstadoREntrega.CORREGIDA_ERRORES.getIdentificador() };

		return getGestoresConRelaciones(null, null, estados,
				CAMPO_IDUSRGESTORARCHIVOREC, null);
	}

	/**
	 * Obtiene la lista de gestores en órgano remitente con relaciones de
	 * entrega.
	 *
	 * @param idOrgano
	 *            Identificador del órgano del gestor.
	 * @param tiposTransferencia
	 *            Tipo de transferencia ({@link TipoTransferencia}).
	 * @return Lista de Gestores ({@link UsuarioVO}).
	 */
	public List getGestoresOrgRemitenteConRelaciones(String idOrgano,
			int[] tiposTransferencia) {
		int[] estados = new int[] { EstadoREntrega.ABIERTA.getIdentificador(),
				EstadoREntrega.ENVIADA.getIdentificador(),
				EstadoREntrega.CON_ERRORES_COTEJO.getIdentificador(),
				EstadoREntrega.CORREGIDA_ERRORES.getIdentificador() };

		return getGestoresConRelaciones(idOrgano, tiposTransferencia, estados,
				CAMPO_IDUSRGESTORREM, null);
	}

	/**
	 * Obtiene la lista de gestores en órgano remitente con relaciones de
	 * entrega.
	 *
	 * @param idOrgano
	 *            Identificador del órgano del gestor.
	 * @param tiposTransferencia
	 *            Tipo de transferencia ({@link TipoTransferencia}).
	 * @param idsExcluir
	 *            Ids de gestores a excluir
	 * @return Lista de Gestores ({@link UsuarioVO}).
	 */
	public List getGestoresOrgRemitenteConRelaciones(String idOrgano,
			int[] tiposTransferencia, String[] idsExcluir) {
		int[] estados = new int[] { EstadoREntrega.ABIERTA.getIdentificador(),
				EstadoREntrega.ENVIADA.getIdentificador(),
				EstadoREntrega.CON_ERRORES_COTEJO.getIdentificador(),
				EstadoREntrega.CORREGIDA_ERRORES.getIdentificador() };

		return getGestoresConRelaciones(idOrgano, tiposTransferencia, estados,
				CAMPO_IDUSRGESTORREM, idsExcluir);
	}

	/**
	 * Obtiene la lista de gestores con relaciones de entrega.
	 *
	 * @param idOrgano
	 *            Identificador del órgano del gestor.
	 * @param tiposTransferencia
	 *            Tipo de transferencia ({@link TipoTransferencia}).
	 * @param estados
	 *            Lista de estados de las relaciones de entrega
	 * @return Lista de Gestores ({@link UsuarioVO}).
	 */
	private List getGestoresConRelaciones(String idOrgano,
			int[] tiposTransferencia, int[] estados, DbColumnDef enlaceUsuario,
			String[] idsExcluir) {
		Map pairs = new HashMap();
		pairs.put(TABLE_NAME, null);
		pairs.put(UsuarioDBEntityImpl.TABLE_NAME, UsuarioDBEntityImpl.COL_DEFS);

		StringBuilder qual = new StringBuilder().append("WHERE ").append(
				DBUtils.generateInTokenField(CAMPO_ESTADO, estados));

		// Tipos de transferencia
		if ((tiposTransferencia != null) && (tiposTransferencia.length > 0))
			qual.append(" AND ").append(
					DBUtils.generateInTokenField(CAMPO_TIPOTRANSFERENCIA,
							tiposTransferencia));

		// Órgano
		if (StringUtils.isNotBlank(idOrgano))
			qual.append(" AND ")
					.append(DBUtils.generateEQTokenField(CAMPO_IDORGREMITENTE,
							idOrgano));

		qual.append(" AND ").append(
				DBUtils.generateJoinCondition(enlaceUsuario,
						UsuarioDBEntityImpl.CAMPO_ID));

		// Ids a excluir
		if (!ArrayUtils.isEmpty(idsExcluir)) {
			qual.append(" AND ").append(
					DBUtils.generateNotInTokenField(
							UsuarioDBEntityImpl.CAMPO_ID, idsExcluir));
		}

		qual.append(" ORDER BY ")
				.append(UsuarioDBEntityImpl.CAMPO_APELLIDOS.getQualifiedName())
				.append(",")
				.append(UsuarioDBEntityImpl.CAMPO_NOMBRE.getQualifiedName());

		return getDistinctVOS(qual.toString(), pairs, UsuarioVO.class);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * transferencias.db.IRelacionEntregaDBEntity#countRelacionesXFormato(java
	 * .lang.String)
	 */
	public int countRelacionesXFormato(String idFormato) {
		StringBuilder qual = new StringBuilder("WHERE").append(DBUtils
				.generateEQTokenField(CAMPO_IDFORMATO, idFormato));
		return getVOCount(qual.toString(), TABLE_NAME);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * transferencias.db.IRelacionEntregaDBEntity#countRelacionesXFormato(java
	 * .lang.String)
	 */
	public int countRelacionesNoValidadasXFormato(String idFormato,
			int[] estados) {
		StringBuilder qual = new StringBuilder(DBUtils.WHERE)
				.append(DBUtils
						.generateEQTokenField(CAMPO_IDFORMATO, idFormato))
				.append(DBUtils.AND)
				.append(DBUtils.generateNotInTokenField(CAMPO_ESTADO, estados));
		return getVOCount(qual.toString(), TABLE_NAME);
	}

	/**
	 *
	 * @param fechaInicial
	 * @param fechaFinal
	 * @param idArchivoEmisor
	 * @param idPadre
	 * @param idFormato
	 * @param idCampoFechaExtremaFinal
	 * @return
	 */
	public List getUInstXArchivoYSerieConFechas(Date fechaInicial,
			Date fechaFinal, String idArchivoEmisor, String idPadre,
			String idFormato, String idCampoFechaExtremaFinal) {
		JoinDefinition[] joinsElementoCFUDocEnUi = new JoinDefinition[] { new JoinDefinition(
				new DbColumnDef(
						new TableDef(
								ElementoCuadroClasificacionDBEntityImplBase.TABLE_NAME_ELEMENTO),
						ElementoCuadroClasificacionDBEntityImplBase.ID_ELEMENTO_FIELD),
				new DbColumnDef(new TableDef(
						UDocEnUiDepositoDbEntityImpl.TABLE_NAME),
						UDocEnUiDepositoDbEntityImpl.IDUNIDADDOC_FIELD))

		};

		JoinDefinition[] joinsDocenuiUinstalacion = new JoinDefinition[] { new JoinDefinition(
				new DbColumnDef(new TableDef(
						UDocEnUiDepositoDbEntityImpl.TABLE_NAME),
						UDocEnUiDepositoDbEntityImpl.IDUINSTALACION_FIELD),
				new DbColumnDef(new TableDef(
						UInstalacionDepositoDBEntity.TABLE_NAME),
						UInstalacionDepositoDBEntity.ID_FIELD)) };

		JoinDefinition[] joinsElementoCFFecha = new JoinDefinition[] { new JoinDefinition(
				new DbColumnDef(
						new TableDef(
								ElementoCuadroClasificacionDBEntityImplBase.TABLE_NAME_ELEMENTO),
						ElementoCuadroClasificacionDBEntityImplBase.ID_ELEMENTO_FIELD),
				new DbColumnDef(new TableDef(FechaDBEntityImpl.TABLE_NAME),
						FechaDBEntityImpl.CAMPO_ID_ELEMENTO)) };

		String qual = new StringBuilder()
				.append(DBUtils
						.generateInnerJoinChainedCondition(joinsElementoCFUDocEnUi))
				.append(" AND ")
				.append(DBUtils
						.generateEQTokenField(
								ElementoCuadroClasificacionDBEntityImplBase.TIPO_ELEMENTO_FIELD,
								TipoNivelCF.UNIDAD_DOCUMENTAL
										.getIdentificador()))
				.append(" AND ")
				.append(DBUtils
						.generateEQTokenField(
								ElementoCuadroClasificacionDBEntityImplBase.IDPADRE_FIELD,
								idPadre))
				.append(" AND ")
				.append(DBUtils
						.generateEQTokenField(
								ElementoCuadroClasificacionDBEntityImplBase.ARCHIVO_FIELD,
								idArchivoEmisor))
				.append(" ")
				.append(DBUtils
						.generateInnerJoinChainedCondition(joinsDocenuiUinstalacion))
				.append(" AND ")
				.append(DBUtils
						.generateEQTokenField(
								UInstalacionDepositoDBEntity.IDFORMATO_FIELD,
								idFormato))
				.append(" ")
				.append(DBUtils
						.generateInnerJoinChainedCondition(joinsElementoCFFecha))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(
						FechaDBEntityImpl.CAMPO_ID_CAMPO,
						idCampoFechaExtremaFinal))
				.append(" AND ")
				.append(FechaDBEntityImpl.CAMPO_FECHA_FINAL)
				.append(" BETWEEN ")
				.append(DBUtils.getNativeToDateSyntax(getConnection(),
						fechaInicial, CustomDateFormat.SDF_DDMMYYYY))
				.append(" AND ")
				.append(DBUtils.getNativeToDateSyntax(getConnection(),
						fechaFinal, CustomDateFormat.SDF_DDMMYYYY)).toString();

		DbColumnDef[] COLS_DEFS = { ElementoCuadroClasificacionDBEntityImplBase.ID_ELEMENTO_FIELD };
		return getVOS(
				qual,
				ElementoCuadroClasificacionDBEntityImplBase.TABLE_NAME_ELEMENTO,
				COLS_DEFS, ElementoCuadroClasificacion.class);
	}

	/**
	 *
	 * @param idsUnidadesDocumentales
	 * @param idUnidadInstalacion
	 * @return
	 */
	public List getUInstUdocsXUdocsCompletasYNoCompletas(
			List idsUnidadesDocumentales, String idUnidadInstalacion) {

		TableDef asgdudocenui1 = new TableDef(
				UDocEnUiDepositoDbEntityImpl.TABLE_NAME);
		DbColumnDef idUnidadDoc = new DbColumnDef(asgdudocenui1,
				UDocEnUiDepositoDbEntityImpl.IDUNIDADDOC_FIELD);
		DbColumnDef enRelacionS = new DbColumnDef("enRelacion",
				"'S' AS ENRELACION");
		DbColumnDef enRelacionN = new DbColumnDef("enRelacion",
				"'N' AS ENRELACION");

		DbColumnDef[] COLS1 = {
				UDocEnUiDepositoDbEntityImpl.IDUINSTALACION_FIELD,
				UDocEnUiDepositoDbEntityImpl.IDUNIDADDOC_FIELD,
				UDocEnUiDepositoDbEntityImpl.SIGNATURAUDOC_FIELD,
				ElementoCuadroClasificacionDBEntityImplBase.TITULO_FIELD,
				enRelacionS };

		String tables1 = UDocEnUiDepositoDbEntityImpl.TABLE_NAME
				+ ","
				+ ElementoCuadroClasificacionDBEntityImplBase.TABLE_NAME_ELEMENTO
				+ "," + UInstalacionDepositoDBEntity.TABLE_NAME;

		String qual1 = new StringBuilder(" WHERE ")
				.append(DBUtils.generateInTokenField(idUnidadDoc,
						idsUnidadesDocumentales))
				.append(" AND ")
				.append(UDocEnUiDepositoDbEntityImpl.IDUINSTALACION_FIELD
						.getQualifiedName()
						+ "="
						+ UInstalacionDepositoDBEntity.ID_FIELD
								.getQualifiedName())
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(
						UInstalacionDepositoDBEntity.ID_FIELD,
						idUnidadInstalacion))
				.append(" AND ")
				.append(UDocEnUiDepositoDbEntityImpl.IDUNIDADDOC_FIELD
						.getQualifiedName()
						+ "="
						+ ElementoCuadroClasificacionDBEntityImplBase.ID_ELEMENTO_FIELD
								.getQualifiedName()).toString();

		DbColumnDef[] COLS2 = {
				UDocEnUiDepositoDbEntityImpl.IDUINSTALACION_FIELD,
				UDocEnUiDepositoDbEntityImpl.IDUNIDADDOC_FIELD,
				UDocEnUiDepositoDbEntityImpl.SIGNATURAUDOC_FIELD,
				ElementoCuadroClasificacionDBEntityImplBase.TITULO_FIELD,
				enRelacionN };

		String tables2 = UDocEnUiDepositoDbEntityImpl.TABLE_NAME
				+ ","
				+ ElementoCuadroClasificacionDBEntityImplBase.TABLE_NAME_ELEMENTO
				+ "," + UInstalacionDepositoDBEntity.TABLE_NAME;

		String lastSubSelect = new StringBuilder(" SELECT ")
				.append(UDocEnUiDepositoDbEntityImpl.IDUINSTALACION_FIELD
						.getQualifiedName())
				.append(" FROM ")
				.append(UDocEnUiDepositoDbEntityImpl.TABLE_NAME)
				.append(",")
				.append(UInstalacionDepositoDBEntity.TABLE_NAME)
				.append(" WHERE ")
				.append(UInstalacionDepositoDBEntity.ID_FIELD
						.getQualifiedName()
						+ "="
						+ UDocEnUiDepositoDbEntityImpl.IDUINSTALACION_FIELD
								.getQualifiedName())
				.append(" AND ")
				.append(DBUtils.generateInTokenField(idUnidadDoc,
						idsUnidadesDocumentales))
				.append(" GROUP BY ")
				.append(UDocEnUiDepositoDbEntityImpl.IDUINSTALACION_FIELD
						.getName()).toString();

		String qual2 = new StringBuilder(" WHERE ")
				.append(UDocEnUiDepositoDbEntityImpl.IDUINSTALACION_FIELD
						.getName())
				.append(" IN (")
				.append(lastSubSelect + " )")
				.append(" AND ")
				.append(UInstalacionDepositoDBEntity.ID_FIELD
						.getQualifiedName()
						+ "="
						+ UDocEnUiDepositoDbEntityImpl.IDUINSTALACION_FIELD
								.getQualifiedName())
				.append(" AND ")
				.append(DBUtils.generateNotInTokenField(
						UDocEnUiDepositoDbEntityImpl.IDUNIDADDOC_FIELD,
						idsUnidadesDocumentales))
				.append(" AND ")
				.append(UDocEnUiDepositoDbEntityImpl.IDUINSTALACION_FIELD
						.getQualifiedName()
						+ "="
						+ UInstalacionDepositoDBEntity.ID_FIELD
								.getQualifiedName())
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(
						UInstalacionDepositoDBEntity.ID_FIELD,
						idUnidadInstalacion))
				.append(" AND ")
				.append(UDocEnUiDepositoDbEntityImpl.IDUNIDADDOC_FIELD
						.getQualifiedName()
						+ "="
						+ ElementoCuadroClasificacionDBEntityImplBase.ID_ELEMENTO_FIELD)
				.toString();

		return getUnionVOS(qual1, tables1, COLS1, UDocEnUiDepositoVO.class,
				qual2, tables2, COLS2, null);
	}

	public List getUDocsXArchivoYSerieYFormatoConFechas(Date fechaFinalDesde,
			Date fechaFinalHasta, String idArchivoEmisor, String idSerie,
			String idFormato, String idNivelDocumental) {

		JoinDefinition[] joinsElementoCFUDocEnUi = new JoinDefinition[] { new JoinDefinition(
				new DbColumnDef(
						new TableDef(
								ElementoCuadroClasificacionDBEntityImplBase.TABLE_NAME_ELEMENTO),
						ElementoCuadroClasificacionDBEntityImplBase.ID_ELEMENTO_FIELD),
				new DbColumnDef(new TableDef(
						UDocEnUiDepositoDbEntityImpl.TABLE_NAME),
						UDocEnUiDepositoDbEntityImpl.IDUNIDADDOC_FIELD))

		};

		JoinDefinition[] joinsElementoCFFecha = new JoinDefinition[] { new JoinDefinition(
				new DbColumnDef(
						new TableDef(
								ElementoCuadroClasificacionDBEntityImplBase.TABLE_NAME_ELEMENTO),
						ElementoCuadroClasificacionDBEntityImplBase.ID_ELEMENTO_FIELD),
				new DbColumnDef(new TableDef(FechaDBEntityImpl.TABLE_NAME),
						FechaDBEntityImpl.CAMPO_ID_ELEMENTO)) };

		JoinDefinition[] joinsUinstalacion = new JoinDefinition[] { new JoinDefinition(
				new DbColumnDef(new TableDef(
						UDocEnUiDepositoDbEntityImpl.TABLE_NAME),
						UDocEnUiDepositoDbEntityImpl.IDUINSTALACION_FIELD),
				new DbColumnDef(new TableDef(
						UInstalacionDepositoDBEntity.TABLE_NAME),
						UInstalacionDepositoDBEntity.ID_FIELD))

		};

		StringBuilder qual = new StringBuilder()
				.append(DBUtils
						.generateInnerJoinChainedCondition(joinsElementoCFUDocEnUi))
				.append(DBUtils.AND)
				.append(DBUtils
						.generateEQTokenField(
								ElementoCuadroClasificacionDBEntityImplBase.TIPO_ELEMENTO_FIELD,
								TipoNivelCF.UNIDAD_DOCUMENTAL
										.getIdentificador()))
				.append(DBUtils.AND)
				.append(DBUtils
						.generateEQTokenField(
								ElementoCuadroClasificacionDBEntityImplBase.IDPADRE_FIELD,
								idSerie))
				.append(DBUtils.AND)
				.append(DBUtils
						.generateEQTokenField(
								ElementoCuadroClasificacionDBEntityImplBase.ARCHIVO_FIELD,
								idArchivoEmisor));

		// Si el nivel documental no es nulo
		if (!StringUtils.isBlank(idNivelDocumental)) {
			qual.append(DBUtils.AND)
					.append(DBUtils
							.generateEQTokenField(
									ElementoCuadroClasificacionDBEntityImplBase.NIVEL_FIELD,
									idNivelDocumental));
		}

		qual.append(" ")
				.append(DBUtils
						.generateInnerJoinChainedCondition(joinsElementoCFFecha))
				.append(DBUtils.AND)
				.append(DBUtils.generateEQTokenField(
						FechaDBEntityImpl.CAMPO_ID_CAMPO,
						ConfiguracionSistemaArchivoFactory
								.getConfiguracionSistemaArchivo()
								.getConfiguracionDescripcion()
								.getFechaExtremaFinal()))
				.append(DBUtils.AND)
				.append(FechaDBEntityImpl.CAMPO_FECHA_FINAL)
				.append(DBUtils.BETWEEN)
				.append(DBUtils.getNativeToDateSyntax(getConnection(),
						fechaFinalDesde, CustomDateFormat.SDF_DDMMYYYY))
				.append(DBUtils.AND)
				.append(DBUtils.getNativeToDateSyntax(getConnection(),
						fechaFinalHasta, CustomDateFormat.SDF_DDMMYYYY));

		// Si idFormato no es nulo
		if (!StringUtils.isBlank(idFormato)) {
			qual.append(
					DBUtils.generateInnerJoinChainedCondition(joinsUinstalacion))
					.append(DBUtils.AND)
					.append(DBUtils.generateEQTokenField(
							UInstalacionDepositoDBEntity.IDFORMATO_FIELD,
							idFormato));
		}

		qual.append(DBUtils
				.generateOrderBy(UDocEnUiDepositoDbEntityImpl.SIGNATURAUDOC_FIELD));

		DbColumnDef[] COLS_DEFS = { ElementoCuadroClasificacionDBEntityImplBase.ID_ELEMENTO_FIELD };
		return getVOS(
				qual.toString(),
				ElementoCuadroClasificacionDBEntityImplBase.TABLE_NAME_ELEMENTO,
				COLS_DEFS, ElementoCuadroClasificacion.class);

	}

	/**
	 *
	 * @param idsUnidadesDocumentales
	 * @param idUnidadInstalacion
	 * @return
	 */
	public List getUdocsCompletasYNoCompletas(List idsUnidadesDocumentales) {

		TableDef asgdudocenui1 = new TableDef(
				UDocEnUiDepositoDbEntityImpl.TABLE_NAME);
		DbColumnDef idUnidadDoc = new DbColumnDef(asgdudocenui1,
				UDocEnUiDepositoDbEntityImpl.IDUNIDADDOC_FIELD);

		DbColumnDef enRelacionS = new DbColumnDef("enRelacion",
				"'S' AS ENRELACION", DbDataType.SHORT_TEXT);
		DbColumnDef enRelacionN = new DbColumnDef("enRelacion",
				"'N' AS ENRELACION", DbDataType.SHORT_TEXT);

		DbColumnDef[] COLS1 = {
				UDocEnUiDepositoDbEntityImpl.IDUINSTALACION_FIELD,
				UDocEnUiDepositoDbEntityImpl.IDUNIDADDOC_FIELD,
				UDocEnUiDepositoDbEntityImpl.SIGNATURAUDOC_FIELD,
				ElementoCuadroClasificacionDBEntityImplBase.TITULO_FIELD,
				enRelacionS };

		String tables1 = UDocEnUiDepositoDbEntityImpl.TABLE_NAME
				+ ","
				+ ElementoCuadroClasificacionDBEntityImplBase.TABLE_NAME_ELEMENTO
				+ "," + UInstalacionDepositoDBEntity.TABLE_NAME;

		String qual1 = new StringBuilder(" WHERE ")
				.append(DBUtils.generateInTokenField(idUnidadDoc,
						idsUnidadesDocumentales))
				.append(" AND ")
				.append(UDocEnUiDepositoDbEntityImpl.IDUINSTALACION_FIELD
						.getQualifiedName()
						+ "="
						+ UInstalacionDepositoDBEntity.ID_FIELD
								.getQualifiedName())
				.append(" AND ")
				.append(UDocEnUiDepositoDbEntityImpl.IDUNIDADDOC_FIELD
						.getQualifiedName()
						+ "="
						+ ElementoCuadroClasificacionDBEntityImplBase.ID_ELEMENTO_FIELD
								.getQualifiedName()).toString();

		DbColumnDef[] COLS2 = {
				UDocEnUiDepositoDbEntityImpl.IDUINSTALACION_FIELD,
				UDocEnUiDepositoDbEntityImpl.IDUNIDADDOC_FIELD,
				UDocEnUiDepositoDbEntityImpl.SIGNATURAUDOC_FIELD,
				ElementoCuadroClasificacionDBEntityImplBase.TITULO_FIELD,
				enRelacionN };

		String tables2 = UDocEnUiDepositoDbEntityImpl.TABLE_NAME
				+ ","
				+ ElementoCuadroClasificacionDBEntityImplBase.TABLE_NAME_ELEMENTO
				+ "," + UInstalacionDepositoDBEntity.TABLE_NAME;

		String lastSubSelect = new StringBuilder(" SELECT ")
				.append(UDocEnUiDepositoDbEntityImpl.IDUINSTALACION_FIELD
						.getQualifiedName())
				.append(" FROM ")
				.append(UDocEnUiDepositoDbEntityImpl.TABLE_NAME)
				.append(",")
				.append(UInstalacionDepositoDBEntity.TABLE_NAME)
				.append(" WHERE ")
				.append(UInstalacionDepositoDBEntity.ID_FIELD
						.getQualifiedName()
						+ "="
						+ UDocEnUiDepositoDbEntityImpl.IDUINSTALACION_FIELD
								.getQualifiedName())
				.append(" AND ")
				.append(DBUtils.generateInTokenField(idUnidadDoc,
						idsUnidadesDocumentales))
				.append(" GROUP BY ")
				.append(UDocEnUiDepositoDbEntityImpl.IDUINSTALACION_FIELD
						.getName()).toString();

		String qual2 = new StringBuilder(" WHERE ")
				.append(UDocEnUiDepositoDbEntityImpl.IDUINSTALACION_FIELD
						.getName())
				.append(" IN (")
				.append(lastSubSelect + " )")
				.append(" AND ")
				.append(UInstalacionDepositoDBEntity.ID_FIELD
						.getQualifiedName()
						+ "="
						+ UDocEnUiDepositoDbEntityImpl.IDUINSTALACION_FIELD
								.getQualifiedName())
				.append(" AND ")
				.append(DBUtils.generateNotInTokenField(
						UDocEnUiDepositoDbEntityImpl.IDUNIDADDOC_FIELD,
						idsUnidadesDocumentales))
				.append(" AND ")
				.append(UDocEnUiDepositoDbEntityImpl.IDUINSTALACION_FIELD
						.getQualifiedName()
						+ "="
						+ UInstalacionDepositoDBEntity.ID_FIELD
								.getQualifiedName())
				.append(" AND ")
				.append(UDocEnUiDepositoDbEntityImpl.IDUNIDADDOC_FIELD
						.getQualifiedName()
						+ "="
						+ ElementoCuadroClasificacionDBEntityImplBase.ID_ELEMENTO_FIELD)
				.toString();

		return getUnionVOS(qual1, tables1, COLS1, UDocEnUiDepositoVO.class,
				qual2, tables2, COLS2, null);
	}

	/*
	 * (sin Javadoc)
	 *
	 * @see transferencias.db.IRelacionEntregaDBEntity#
	 * getUDocsElectronicasXArchivoYSerieConFechas(java.util.Date,
	 * java.util.Date, java.lang.String, java.lang.String, java.lang.String)
	 */
	public List getUDocsElectronicasXArchivoYSerieConFechas(
			Date fechaFinalDesde, Date fechaFinalHasta, String idArchivoEmisor,
			String idSerie) {
		JoinDefinition[] joinsElementoCFUDocSDF = new JoinDefinition[] { new JoinDefinition(
				new DbColumnDef(
						new TableDef(
								ElementoCuadroClasificacionDBEntityImplBase.TABLE_NAME_ELEMENTO),
						ElementoCuadroClasificacionDBEntityImplBase.ID_ELEMENTO_FIELD),
				new DbColumnDef(
						new TableDef(
								UnidadDocumentalElectronicaDBEntityImpl.TABLE_NAME),
						UnidadDocumentalElectronicaDBEntityImpl.IDELEMENTOCF_FIELD))

		};

		JoinDefinition[] joinsElementoCFFecha = new JoinDefinition[] { new JoinDefinition(
				new DbColumnDef(
						new TableDef(
								ElementoCuadroClasificacionDBEntityImplBase.TABLE_NAME_ELEMENTO),
						ElementoCuadroClasificacionDBEntityImplBase.ID_ELEMENTO_FIELD),
				new DbColumnDef(new TableDef(FechaDBEntityImpl.TABLE_NAME),
						FechaDBEntityImpl.CAMPO_ID_ELEMENTO)) };

		StringBuilder qual = new StringBuilder()
				.append(DBUtils
						.generateInnerJoinChainedCondition(joinsElementoCFUDocSDF))
				.append(DBUtils.AND)
				.append(DBUtils
						.generateEQTokenField(
								ElementoCuadroClasificacionDBEntityImplBase.TIPO_ELEMENTO_FIELD,
								TipoNivelCF.UNIDAD_DOCUMENTAL
										.getIdentificador()))
				.append(DBUtils.AND)
				.append(DBUtils
						.generateEQTokenField(
								ElementoCuadroClasificacionDBEntityImplBase.IDPADRE_FIELD,
								idSerie))
				.append(DBUtils.AND)
				.append(DBUtils
						.generateEQTokenField(
								ElementoCuadroClasificacionDBEntityImplBase.ARCHIVO_FIELD,
								idArchivoEmisor));

		qual.append(Constants.STRING_EMPTY)
				.append(DBUtils
						.generateInnerJoinChainedCondition(joinsElementoCFFecha))
				.append(DBUtils.AND)
				.append(DBUtils.generateEQTokenField(
						FechaDBEntityImpl.CAMPO_ID_CAMPO,
						ConfiguracionSistemaArchivoFactory
								.getConfiguracionSistemaArchivo()
								.getConfiguracionDescripcion()
								.getFechaExtremaFinal()))
				.append(DBUtils.AND)
				.append(FechaDBEntityImpl.CAMPO_FECHA_FINAL)
				.append(DBUtils.BETWEEN)
				.append(DBUtils.getNativeToDateSyntax(getConnection(),
						fechaFinalDesde, CustomDateFormat.SDF_DDMMYYYY))
				.append(DBUtils.AND)
				.append(DBUtils.getNativeToDateSyntax(getConnection(),
						fechaFinalHasta, CustomDateFormat.SDF_DDMMYYYY))
				.append(DBUtils.AND)
				.append(DBUtils
						.generateEQTokenField(
								UnidadDocumentalElectronicaDBEntityImpl.MARCAS_BLOQUEO_FIELD,
								0));

		qual.append(DBUtils
				.generateOrderBy(ElementoCuadroClasificacionDBEntityImplBase.CODIGO_FIELD));

		DbColumnDef[] COLS_DEFS = ElementoCuadroClasificacionDBEntityImplBase.COLS_DEFS_ELEMENTO;
		return getVOS(
				qual.toString(),
				ElementoCuadroClasificacionDBEntityImplBase.TABLE_NAME_ELEMENTO,
				COLS_DEFS, ElementoCuadroClasificacion.class);

	}

	private static final DbColumnDef[] COLS_TO_SELECT_EA = (DbColumnDef[]) ArrayUtils
			.concat(UdocElectronicaDBEntityImpl.TABLE_COLUMNS,
					new DbColumnDef[] {
							new DbColumnDef(
									"asunto",
									ElementoCuadroClasificacionDBEntityImplBase.TITULO_FIELD),
							new DbColumnDef(
									"expediente",
									UnidadDocumentalDBEntityImpl.CAMPO_NUMERO_EXPEDIENTE),
							new DbColumnDef(
									"marcasbloqueo",
									UnidadDocumentalDBEntityImpl.CAMPO_MARCAS_BLOQUEO)
					/*
					 * new DbColumnDef("fechaInicio",FechaDBEntityImpl.
					 * CAMPO_FECHA_INICIAL), new DbColumnDef("fechaFin",
					 * FechaDBEntityImpl.CAMPO_FECHA_FINAL)
					 */
					});

	/*
	 * (sin Javadoc)
	 *
	 * @see transferencias.db.IRelacionEntregaDBEntity#
	 * getUDocsElectronicasByIdRelacionEntreArhivos(java.lang.String)
	 */
	public List getUDocsElectronicasByIdRelacionEntreArhivos(String idRelacion) {

		JoinDefinition[] joinsElementoCFUDocSDF = new JoinDefinition[] { new JoinDefinition(
				new DbColumnDef(
						new TableDef(
								ElementoCuadroClasificacionDBEntityImplBase.TABLE_NAME_ELEMENTO),
						ElementoCuadroClasificacionDBEntityImplBase.ID_ELEMENTO_FIELD),
				new DbColumnDef(new TableDef(
						UdocElectronicaDBEntityImpl.TABLE_NAME),
						UdocElectronicaDBEntityImpl.ID_FIELD)) };

		/*
		 * JoinDefinition[] joinsElementoCFFecha = new JoinDefinition[]{ new
		 * JoinDefinition( new DbColumnDef(new
		 * TableDef(ElementoCuadroClasificacionDBEntityImplBase
		 * .TABLE_NAME_ELEMENTO
		 * ),ElementoCuadroClasificacionDBEntityImplBase.ID_ELEMENTO_FIELD), new
		 * DbColumnDef(new
		 * TableDef(FechaDBEntityImpl.TABLE_NAME),FechaDBEntityImpl
		 * .CAMPO_ID_ELEMENTO)) };
		 */
		JoinDefinition[] joinsElementoCFUnidadDoc = new JoinDefinition[] { new JoinDefinition(
				new DbColumnDef(
						new TableDef(
								ElementoCuadroClasificacionDBEntityImplBase.TABLE_NAME_ELEMENTO),
						ElementoCuadroClasificacionDBEntityImplBase.ID_ELEMENTO_FIELD),
				new DbColumnDef(
						new TableDef(
								UnidadDocumentalDBEntityImpl.TABLE_NAME_UNIDAD_DOCUMENTAL),
						UnidadDocumentalDBEntityImpl.CAMPO_ID)) };

		StringBuilder qual = new StringBuilder()
				.append(DBUtils
						.generateInnerJoinChainedCondition(joinsElementoCFUDocSDF))
				/*
				 * .append(Constants.STRING_EMPTY)
				 * .append(DBUtils.generateInnerJoinChainedCondition
				 * (joinsElementoCFFecha))
				 */
				.append(Constants.STRING_EMPTY)
				.append(DBUtils
						.generateInnerJoinChainedCondition(joinsElementoCFUnidadDoc))

				// Condición de Relación de Entrega
				.append(DBUtils.AND)
				.append(DBUtils.generateEQTokenField(
						UdocElectronicaDBEntityImpl.IDRELENTREGA_FIELD,
						idRelacion))

				// Order By
				.append(DBUtils
						.generateOrderBy(ElementoCuadroClasificacionDBEntityImplBase.CODIGO_FIELD));

		return getVOS(
				qual.toString(),
				ElementoCuadroClasificacionDBEntityImplBase.TABLE_NAME_ELEMENTO,
				COLS_TO_SELECT_EA, UDocElectronicaVO.class);
	}

	/*
	 * (sin Javadoc)
	 *
	 * @see transferencias.db.IRelacionEntregaDBEntity#
	 * getCountUDocsElectronicasXArchivoYSerieConFechas(java.util.Date,
	 * java.util.Date, java.lang.String, java.lang.String)
	 */
	public int getCountUDocsElectronicasXArchivoYSerieConFechas(
			Date fechaFinalDesde, Date fechaFinalHasta, String idArchivoEmisor,
			String idSerie) {

		JoinDefinition[] joinsElementoCFUDocSDF = new JoinDefinition[] { new JoinDefinition(
				new DbColumnDef(
						new TableDef(
								ElementoCuadroClasificacionDBEntityImplBase.TABLE_NAME_ELEMENTO),
						ElementoCuadroClasificacionDBEntityImplBase.ID_ELEMENTO_FIELD),
				new DbColumnDef(
						new TableDef(
								UnidadDocumentalElectronicaDBEntityImpl.TABLE_NAME),
						UnidadDocumentalElectronicaDBEntityImpl.IDELEMENTOCF_FIELD))

		};

		JoinDefinition[] joinsElementoCFFecha = new JoinDefinition[] { new JoinDefinition(
				new DbColumnDef(
						new TableDef(
								ElementoCuadroClasificacionDBEntityImplBase.TABLE_NAME_ELEMENTO),
						ElementoCuadroClasificacionDBEntityImplBase.ID_ELEMENTO_FIELD),
				new DbColumnDef(new TableDef(FechaDBEntityImpl.TABLE_NAME),
						FechaDBEntityImpl.CAMPO_ID_ELEMENTO)) };

		StringBuilder qual = new StringBuilder()
				.append(DBUtils
						.generateInnerJoinChainedCondition(joinsElementoCFUDocSDF))
				.append(DBUtils.AND)
				.append(DBUtils
						.generateEQTokenField(
								ElementoCuadroClasificacionDBEntityImplBase.TIPO_ELEMENTO_FIELD,
								TipoNivelCF.UNIDAD_DOCUMENTAL
										.getIdentificador()))
				.append(DBUtils.AND)
				.append(DBUtils
						.generateEQTokenField(
								ElementoCuadroClasificacionDBEntityImplBase.IDPADRE_FIELD,
								idSerie))
				.append(DBUtils.AND)
				.append(DBUtils
						.generateEQTokenField(
								ElementoCuadroClasificacionDBEntityImplBase.ARCHIVO_FIELD,
								idArchivoEmisor));

		qual.append(Constants.STRING_EMPTY)
				.append(DBUtils
						.generateInnerJoinChainedCondition(joinsElementoCFFecha))
				.append(DBUtils.AND)
				.append(DBUtils.generateEQTokenField(
						FechaDBEntityImpl.CAMPO_ID_CAMPO,
						ConfiguracionSistemaArchivoFactory
								.getConfiguracionSistemaArchivo()
								.getConfiguracionDescripcion()
								.getFechaExtremaFinal()))
				.append(DBUtils.AND)
				.append(FechaDBEntityImpl.CAMPO_FECHA_FINAL)
				.append(DBUtils.BETWEEN)
				.append(DBUtils.getNativeToDateSyntax(getConnection(),
						fechaFinalDesde, CustomDateFormat.SDF_DDMMYYYY))
				.append(DBUtils.AND)
				.append(DBUtils.getNativeToDateSyntax(getConnection(),
						fechaFinalHasta, CustomDateFormat.SDF_DDMMYYYY))
				.append(DBUtils.AND)
				.append(DBUtils
						.generateEQTokenField(
								UnidadDocumentalElectronicaDBEntityImpl.MARCAS_BLOQUEO_FIELD,
								0));

		return getVOCount(qual.toString(),
				ElementoCuadroClasificacionDBEntityImplBase.TABLE_NAME_ELEMENTO);
	}

	public void unificarDescriptor(String idDescriptor, String[] idsAReemplazar) {
		String qual = new StringBuilder()
				.append(" WHERE ")
				.append(DBUtils.generateInTokenField(CAMPO_IDDESCRORGPRODUCTOR,
						idsAReemplazar)).toString();

		Map cols = Collections.singletonMap(CAMPO_IDDESCRORGPRODUCTOR,
				idDescriptor);

		updateFields(qual, cols, TABLE_NAME);
	}

	public int getCountRelacionesByIdUser(String idUser) {
		StringBuilder qual = new StringBuilder(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(
						CAMPO_IDUSRGESTORARCHIVOREC, idUser))
				.append(DBUtils.OR)
				.append(DBUtils.generateEQTokenField(CAMPO_IDUSRGESTORREM,
						idUser));

		return getVOCount(qual.toString(), TABLE_NAME);
	}

	public void updateReencajado(String idRelacion, String conReencajado,
			String idFormatoRe) {
		StringBuilder qual = new StringBuilder(DBUtils.WHERE).append(DBUtils
				.generateEQTokenField(CAMPO_ID, idRelacion));
		Map colsToUpdate = new HashMap();
		colsToUpdate.put(CAMPO_CONREENCAJADO, conReencajado);
		colsToUpdate.put(CAMPO_IDFORMATORE, idFormatoRe);

		updateFields(qual.toString(), colsToUpdate, TABLE_NAME);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * transferencias.db.IRelacionEntregaDBEntity#getRelacionesByRegEntrada(
	 * java.lang.String)
	 */
	public List getRelacionesByRegEntrada(String regEntrada) {
		StringBuilder qual = new StringBuilder(DBUtils.WHERE).append(DBUtils
				.generateEQTokenField(CAMPO_REGENTRADA, regEntrada));
		return getRelaciones(qual.toString());
	}

	/**
	 * {@inheritDoc}
	 * @see transferencias.db.IRelacionEntregaDBEntity#getRelacionVO(transferencias.vos.RelacionEntregaVO)
	 */
	public RelacionEntregaVO getRelacionVO(RelacionEntregaVO relacion) {
		StringBuilder qual = new StringBuilder();

		qual.append(DBUtils.WHERE)
		.append(DBUtils.generateEQTokenField(CAMPO_ESTADO, relacion.getEstado()))
		.append(DBUtils.AND)
		.append(DBUtils.generateEQTokenField(CAMPO_IDORGREMITENTE, relacion.getIdorganoremitente()))
		.append(DBUtils.AND)
		.append(DBUtils.generateEQTokenField(CAMPO_CODSISTPRODUCTOR, relacion.getCodsistproductor()))
		.append(DBUtils.AND)
		.append(DBUtils.generateEQTokenField(CAMPO_NOMBRESISTPRODUCTOR, relacion.getNombresistproductor()))
		.append(DBUtils.AND)
		.append(DBUtils.generateEQTokenField(CAMPO_IDFONDODESTINO, relacion.getIdfondodestino()))
		.append(DBUtils.AND)
		.append(DBUtils.generateEQTokenField(CAMPO_IDARCHIVORECEPTOR, relacion.getIdarchivoreceptor()))
		.append(DBUtils.AND)
		.append(DBUtils.generateEQTokenField(CAMPO_ANO, relacion.getAno()))
		.append(DBUtils.AND)
		.append(DBUtils.generateEQTokenField(CAMPO_IDPROC, relacion.getIdprocedimiento()));

		return getRelacion(qual.toString());
	}
}