package gcontrol.db;

import gcontrol.vos.DestinatarioPermisosListaVO;
import gcontrol.vos.ListaAccesoVO;
import gcontrol.vos.PermisosListaVO;
import ieci.core.db.DbColumnDef;
import ieci.core.db.DbConnection;
import ieci.core.db.DbDataType;
import ieci.core.db.DbDeleteFns;
import ieci.core.db.DbInsertFns;
import ieci.core.db.DbUtil;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import common.db.DBCommand;
import common.db.DBEntity;
import common.db.DBUtils;
import common.db.DbDataSource;
import common.db.SigiaDbInputRecord;

public class PermisosListaDbEntityImpl extends DBEntity implements
		IPermisosListaDbEntity {

	/**
	 * IDLISTCA VARCHAR2(32), TIPODEST NUMBER(1), IDDEST VARCHAR2(64), PERM
	 * NUMBER(3)
	 */
	public static final String TABLE_NAME_PERMISOS_LISTA_CONTROL = "ASCAPERMLISTCA";

	public static final String IDLISTCA_COLUMN_NAME = "IDLISTCA";

	public static final String TIPODEST_COLUMN_NAME = "TIPODEST";

	public static final String IDDEST_COLUMN_NAME = "IDDEST";

	public static final String PERM_COLUMN_NAME = "PERM";

	public static final DbColumnDef CAMPO_IDLISTCA = new DbColumnDef(null,
			TABLE_NAME_PERMISOS_LISTA_CONTROL, IDLISTCA_COLUMN_NAME,
			DbDataType.SHORT_TEXT, 32, false);

	public static final DbColumnDef CAMPO_TIPODEST = new DbColumnDef(null,
			TABLE_NAME_PERMISOS_LISTA_CONTROL, TIPODEST_COLUMN_NAME,
			DbDataType.LONG_INTEGER, false);

	public static final DbColumnDef CAMPO_IDDEST = new DbColumnDef(null,
			TABLE_NAME_PERMISOS_LISTA_CONTROL, IDDEST_COLUMN_NAME,
			DbDataType.SHORT_TEXT, 64, false);

	public static final DbColumnDef CAMPO_PERM = new DbColumnDef(null,
			TABLE_NAME_PERMISOS_LISTA_CONTROL, PERM_COLUMN_NAME,
			DbDataType.LONG_INTEGER, false);

	public static final DbColumnDef[] COLS_DEFS_PERMISOS_LISTA = {
			CAMPO_IDLISTCA, CAMPO_TIPODEST, CAMPO_IDDEST, CAMPO_PERM };

	public static final String COLS_NAMES_PERMISOS_LISTA = DbUtil
			.getColumnNames(COLS_DEFS_PERMISOS_LISTA);

	/**
	 * Obtiene el nombre de la tabla
	 * 
	 * @return
	 */
	public String getTableName() {
		return TABLE_NAME_PERMISOS_LISTA_CONTROL;
	}

	/**
	 * Constructor.
	 * 
	 * @param dataSource
	 *            Pool de conexiones de base de datos.
	 */
	public PermisosListaDbEntityImpl(DbDataSource dataSource) {
		super(dataSource);
	}

	public PermisosListaDbEntityImpl(DbConnection conn) {
		super(conn);
	}

	public PermisosListaVO insertPermisosListaVO(
			final PermisosListaVO permisosListaVO) {

		DBCommand command = new DBCommand(this) {

			public void codeLogic(DbConnection conn) throws Exception {

				SigiaDbInputRecord inputRecord = new SigiaDbInputRecord(
						COLS_DEFS_PERMISOS_LISTA, permisosListaVO);
				DbInsertFns.insert(conn, TABLE_NAME_PERMISOS_LISTA_CONTROL,
						COLS_NAMES_PERMISOS_LISTA, inputRecord);

			}

		};

		command.execute();

		return permisosListaVO;

	}

	public void deletePermisosListaVOXIdListaCA(String id) {
		final String qual = new StringBuffer().append(" WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_IDLISTCA, id))
				.toString();

		DBCommand command = new DBCommand(this) {
			public void codeLogic(DbConnection conn) throws Exception {
				DbDeleteFns.delete(conn, TABLE_NAME_PERMISOS_LISTA_CONTROL,
						qual.toString());

			}

		};

		command.execute();
	}

	// public ListaAccesoVO getPermisosListaVOXIdListaCA(final String idLista) {
	// String qual = new StringBuffer()
	// .append("WHERE")
	// .append(DBUtils.generateEQTokenField(CAMPO_IDLISTCA, idLista))
	// .toString();
	//
	// return getListaAccesoVO(qual);
	// }

	protected List getListasAccesoVO(final String qual) {
		return getVOS(qual, TABLE_NAME_PERMISOS_LISTA_CONTROL,
				COLS_DEFS_PERMISOS_LISTA, PermisosListaVO.class);
	}

	protected ListaAccesoVO getListaAccesoVO(final String qual) {
		return (ListaAccesoVO) getVO(qual, TABLE_NAME_PERMISOS_LISTA_CONTROL,
				COLS_DEFS_PERMISOS_LISTA, PermisosListaVO.class);
	}

	/**
	 * Obtiene las listas de acceso para un grupo de destinatarios.
	 * 
	 * @param destinatariosPermisosLista
	 *            Lista de destinatarios ({@link DestinatarioPermisosListaVO}).
	 * @return Listas de acceso ({@link PermisosListaVO});
	 */
	public List getListasAcceso(List destinatariosPermisosLista) {
		List listas = null;

		if ((destinatariosPermisosLista != null)
				&& (destinatariosPermisosLista.size() > 0)) {
			StringBuffer qual = new StringBuffer().append("WHERE ");

			DestinatarioPermisosListaVO destinatario = null;
			for (int i = 0; i < destinatariosPermisosLista.size(); i++) {
				destinatario = (DestinatarioPermisosListaVO) destinatariosPermisosLista
						.get(i);

				if (i > 0)
					qual.append(" OR ");

				qual.append("(");
				qual.append(DBUtils.generateEQTokenField(CAMPO_TIPODEST,
						destinatario.getTipoDest()));
				qual.append(" AND ");
				qual.append(DBUtils.generateInTokenField(CAMPO_IDDEST,
						destinatario.getIdDestList()));
				qual.append(")");
			}

			qual.append(" ORDER BY " + IDLISTCA_COLUMN_NAME);

			listas = getVOS(qual.toString(), TABLE_NAME_PERMISOS_LISTA_CONTROL,
					COLS_DEFS_PERMISOS_LISTA, PermisosListaVO.class);
		}

		return listas;
	}

	/**
	 * Obtiene las listas de acceso para un grupo de destinatarios.
	 * 
	 * @param destinatariosPermisosLista
	 *            Lista de destinatarios ({@link DestinatarioPermisosListaVO}).
	 * @return Listas de acceso ({@link PermisosListaVO});
	 */
	public List getPermisosListaAcceso(String idListaAcceso,
			List destinatariosPermisosLista) {
		List listas = null;

		if ((destinatariosPermisosLista != null)
				&& (destinatariosPermisosLista.size() > 0)) {
			StringBuffer qual = new StringBuffer().append("WHERE ");

			DestinatarioPermisosListaVO destinatario = null;
			for (int i = 0; i < destinatariosPermisosLista.size(); i++) {
				destinatario = (DestinatarioPermisosListaVO) destinatariosPermisosLista
						.get(i);

				if (i > 0)
					qual.append(" OR ");

				qual.append("(");
				qual.append(DBUtils.generateEQTokenField(CAMPO_TIPODEST,
						destinatario.getTipoDest()));
				qual.append(" AND ");
				qual.append(DBUtils.generateInTokenField(CAMPO_IDDEST,
						destinatario.getIdDestList()));
				qual.append(")");
			}

			qual.append(" AND ")
					.append(DBUtils.generateEQTokenField(CAMPO_IDLISTCA,
							idListaAcceso));

			listas = getVOS(qual.toString(), TABLE_NAME_PERMISOS_LISTA_CONTROL,
					COLS_DEFS_PERMISOS_LISTA, PermisosListaVO.class);
		}

		return listas;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gcontrol.db.IPermisosListaDbEntity#getPermisosXIdListaAccesoYTipo(java
	 * .lang.String, java.lang.String)
	 */
	public List getPermisosXIdListaAccesoYTipo(String idLista, int tipoDestino) {
		String qual = new StringBuffer()
				.append(" WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_IDLISTCA, idLista))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(CAMPO_TIPODEST,
						tipoDestino)).toString();

		return getVOS(qual.toString(), TABLE_NAME_PERMISOS_LISTA_CONTROL,
				COLS_DEFS_PERMISOS_LISTA, PermisosListaVO.class);
	}

	public List getPermisosXIdListaIdDestino(String idLista, String idDestino) {
		String qual = new StringBuffer().append(" WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_IDLISTCA, idLista))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(CAMPO_IDDEST, idDestino))
				.toString();

		return getVOS(qual.toString(), TABLE_NAME_PERMISOS_LISTA_CONTROL,
				COLS_DEFS_PERMISOS_LISTA, PermisosListaVO.class);

	}

	public List getPermisosXIdLista(String idLista) {
		String qual = new StringBuffer().append(" WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_IDLISTCA, idLista))
				.toString();

		return getVOS(qual.toString(), TABLE_NAME_PERMISOS_LISTA_CONTROL,
				COLS_DEFS_PERMISOS_LISTA, PermisosListaVO.class);

	}

	public PermisosListaVO getPermisosListaVO(PermisosListaVO permisosListaVO) {
		String qual = new StringBuffer()
				.append(" WHERE ")
				.append(DBUtils.generateEQTokenField(CAMPO_IDLISTCA,
						permisosListaVO.getIdListCA()))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(CAMPO_TIPODEST,
						permisosListaVO.getTipoDest()))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(CAMPO_IDDEST,
						permisosListaVO.getIdDest()))
				.append(" AND ")
				.append(DBUtils.generateEQTokenField(CAMPO_PERM,
						permisosListaVO.getPerm())).toString();
		return (PermisosListaVO) getVO(qual.toString(),
				TABLE_NAME_PERMISOS_LISTA_CONTROL, COLS_DEFS_PERMISOS_LISTA,
				PermisosListaVO.class);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gcontrol.db.IPermisosListaDbEntity#deletePermisosListaVOXIdListaCAYIdDest
	 * (java.lang.String, java.lang.String)
	 */
	public void deletePermisosLista(String idLista, int tipoDestinatarios,
			String[] idDestinatarios) {
		StringBuffer qual = new StringBuffer("WHERE 1=1 ");
		if (StringUtils.isNotBlank(idLista))
			qual.append(" AND ").append(
					DBUtils.generateEQTokenField(CAMPO_IDLISTCA, idLista));
		if (idDestinatarios != null && idDestinatarios.length > 0)
			qual.append(" AND ")
					.append(DBUtils.generateEQTokenField(CAMPO_TIPODEST,
							tipoDestinatarios))
					.append(" AND ")
					.append(DBUtils.generateInTokenField(CAMPO_IDDEST,
							idDestinatarios));

		deleteVO(qual.toString(), TABLE_NAME_PERMISOS_LISTA_CONTROL);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see gcontrol.db.IPermisosListaDbEntity#getCountPermisosXIdLista(java.lang.String)
	 */
	public int getCountPermisosXIdLista(String idLista) {
		String qual = new StringBuffer().append(DBUtils.WHERE)
				.append(DBUtils.generateEQTokenField(CAMPO_IDLISTCA, idLista))
				.toString();

		return getVOCount(qual.toString(), TABLE_NAME_PERMISOS_LISTA_CONTROL);
	}
}
