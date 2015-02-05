package deposito.db.commonPostgreSQLServer;

import ieci.core.db.DbConnection;
import ieci.core.db.DbSelectFns;

import java.util.Iterator;
import java.util.List;

import common.db.DBCommand;
import common.db.DBEntityFactory;
import common.db.DBUtils;
import common.db.DbDataSource;
import common.db.IDBEntityFactory;
import common.lang.MutableBoolean;
import common.util.StringUtils;

import deposito.db.ElementoAsignableDBEntity;
import deposito.db.IElementoNoAsignableDBEntity;
import deposito.db.common.HuecoDbEntityImplBase;

public class HuecoDBEntityImpl extends HuecoDbEntityImplBase {

	/**
	 * @param dataSource
	 */
	public HuecoDBEntityImpl(DbDataSource dataSource) {
		super(dataSource);
	}

	public HuecoDBEntityImpl(DbConnection conn) {
		super(conn);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * deposito.db.HuecoDbEntityImplBase#checkHuecosByElementoNoAsignable(java
	 * .lang.String, java.lang.String[])
	 */
	// TODO ZMIGRACION BD - BY PRIOR (PROBADO CON ELIMINAR NO ASIGNABLE
	public boolean checkHuecosByElementoNoAsignable(
			final String idElementoNoAsignable, final String[] estados) {
		// final String idsDescendientes =
		// getIdsDescendientes(idElementoNoAsignable);
		final String idsDescendientes = getIdsDescendientes(idElementoNoAsignable);
		if (!StringUtils.isEmpty(idsDescendientes)) {
			final MutableBoolean hayHuecosEnEstados = new MutableBoolean(false);
			DBCommand command = new DBCommand(this) {
				public void codeLogic(DbConnection conn) throws Exception {
					final StringBuffer huecosOcupadosQual = new StringBuffer(
							"WHERE ")
							.append(DBUtils.generateInTokenField(CAMPO_ESTADO,
									estados))
							.append(" AND ")
							.append(IDELEMPADRE_COLUMN_NAME)
							.append(" in")
							.append(" (select ")
							.append(ElementoAsignableDBEntity.ID_COLUMN_NAME)
							.append(" from ")
							.append(ElementoAsignableDBEntity.TABLE_NAME)
							.append(" where ")
							.append(ElementoAsignableDBEntity.IDELEMNPADRE_COLUMN_NAME)
							.append(" in").append(" (")
							.append(idsDescendientes).append("))");
					hayHuecosEnEstados.setValue(DbSelectFns.rowExists(
							getConnection(), TABLE_NAME,
							huecosOcupadosQual.toString()));
				}
			};
			command.execute();
			return hayHuecosEnEstados.getValue();
		}
		return false;
	}

	private String getIdsDescendientes(String idElemento) {
		StringBuffer buff = new StringBuffer();

		IDBEntityFactory dbEntityFactory = DBEntityFactory
				.getInstance(getDbFactoryClass());
		IElementoNoAsignableDBEntity elementoNoAsignableDbEntity = dbEntityFactory
				.getElmentoNoAsignableDBEntity(getDataSource());

		List idsDescendientes = elementoNoAsignableDbEntity
				.getIdsDescendientes(idElemento);
		for (Iterator itDEscendientes = idsDescendientes.iterator(); itDEscendientes
				.hasNext();) {
			String element = (String) itDEscendientes.next();
			if (buff.length() > 0)
				buff.append(",");
			buff.append("'").append(element).append("'");
		}
		return buff.toString();
	}
}
